package com.sap.mentors.lemonaid.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.mentors.lemonaid.entities.Event;
import com.sap.mentors.lemonaid.repository.EventRepository;

@Service
@Transactional
@DisallowConcurrentExecution
public class InsideTrackJob implements Job {
    
	private static final Logger log = LoggerFactory.getLogger(InsideTrackJob.class);
	
	public static final String SERVICE_URL = "https://sapmentorsa5a504e08.hana.ondemand.com/com/sap/sapmentors/sitreg/odatapublic/service.xsodata";
	
	public static final String HTTP_METHOD_PUT = "PUT";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_GET = "GET";

	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HTTP_HEADER_ACCEPT = "Accept";

	public static final String APPLICATION_JSON = "application/json";
	public static final String APPLICATION_XML = "application/xml";
	public static final String APPLICATION_ATOM_XML = "application/atom+xml";
	public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
	public static final String METADATA = "$metadata";
	public static final String INDEX = "/index.jsp";
	public static final String SEPARATOR = "/";

	public static final boolean PRINT_RAW_CONTENT = true;

	@Autowired EventRepository eventRepository;

    @Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Pulling inside track events - Start");
		long startTime = System.currentTimeMillis();
		ArrayList<Event> events = getEvents();
		if (events != null) {
			for (Event event : events) {
				eventRepository.save(event);
			}
		}
		log.info("Pulling inside track events  - End. Duration: " + Long.toString(System.currentTimeMillis() - startTime) + "ms");
	}

	private ArrayList<Event> getEvents() {
		try {
			Edm edm = readEdm(SERVICE_URL);
			log.warn("Default EntityContainer: " + edm.getDefaultEntityContainer().getName());
			ODataFeed feed = readFeed(edm, SERVICE_URL, APPLICATION_JSON, "Events");
			ArrayList<Event> events = new ArrayList<Event>();
			for (ODataEntry entry : feed.getEntries()) {
				Event event = new Event();
				event.setSourceId(entry.getProperties().get("ID").toString());
				event.setSource("SITREG");
				event.setId(event.getSource() + "-" + event.getSourceId());
				event.setLocation((String) entry.getProperties().get("Location"));
				event.setStartDate(((GregorianCalendar) entry.getProperties().get("StartTime")).getTime());
				event.setEndDate(((GregorianCalendar) entry.getProperties().get("EndTime")).getTime());
				event.setUrl((String) entry.getProperties().get("HomepageURL"));
				events.add(event);
			}
			return events;
		} catch (IOException | ODataException e) {
			log.warn("Unable to retrieve Inside Track events list: " + e.getMessage());
			return null;
		}
	}

	public Edm readEdm(String serviceUrl) throws IOException, ODataException {
		InputStream content = execute(serviceUrl + "/" + METADATA, APPLICATION_XML, HTTP_METHOD_GET);
		return EntityProvider.readMetadata(content, false);
	}

	public ODataFeed readFeed(Edm edm, String serviceUri, String contentType, String entitySetName)
			throws IOException, ODataException {
		EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
		String absolutUri = createUri(serviceUri, entitySetName, null);

		InputStream content = (InputStream) connect(absolutUri, contentType, HTTP_METHOD_GET).getContent();
		return EntityProvider.readFeed(contentType, entityContainer.getEntitySet(entitySetName), content,
				EntityProviderReadProperties.init().build());
	}
	
	private String createUri(String serviceUri, String entitySetName, String id) {
		final StringBuilder absolutUri = new StringBuilder(serviceUri).append("/").append(entitySetName);
		if (id != null) {
			absolutUri.append("(").append(id).append(")");
		}
		return absolutUri.toString();
	}
	
	private HttpURLConnection connect(String relativeUri, String contentType, String httpMethod) throws IOException {
		HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

		connection.connect();
		checkStatus(connection);

		return connection;
	}
	
	private HttpURLConnection initializeConnection(String absolutUri, String contentType, String httpMethod)
			throws MalformedURLException, IOException {
		URL url = new URL(absolutUri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod(httpMethod);
		connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
		if (HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
			connection.setDoOutput(true);
			connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
		}

		return connection;
	}
	
	private HttpStatusCodes checkStatus(HttpURLConnection connection) throws IOException {
		HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
		if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) {
			throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " "
					+ httpStatusCode.toString());
		}
		return httpStatusCode;
	}
	
	private InputStream execute(String relativeUri, String contentType, String httpMethod) throws IOException {
		HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

		connection.connect();
		checkStatus(connection);

		InputStream content = connection.getInputStream();
		content = logRawContent(httpMethod + " request:\n  ", content, "\n");
		return content;
	}
	
	private InputStream logRawContent(String prefix, InputStream content, String postfix) throws IOException {
		if (PRINT_RAW_CONTENT) {
			byte[] buffer = streamToArray(content);
			content.close();
			log.debug(prefix + new String(buffer) + postfix);
			return new ByteArrayInputStream(buffer);
		}
		return content;
	}

	private byte[] streamToArray(InputStream stream) throws IOException {
		byte[] result = new byte[0];
		byte[] tmp = new byte[8192];
		int readCount = stream.read(tmp);
		while (readCount >= 0) {
			byte[] innerTmp = new byte[result.length + readCount];
			System.arraycopy(result, 0, innerTmp, 0, result.length);
			System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
			result = innerTmp;
			readCount = stream.read(tmp);
		}
		return result;
	}

}