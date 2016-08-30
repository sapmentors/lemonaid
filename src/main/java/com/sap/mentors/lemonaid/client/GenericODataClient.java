package com.sap.mentors.lemonaid.client;

import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericODataClient {

	private static final String METADATA = "$metadata";
	private static final String SEPARATOR = "/";
	protected static final boolean PRINT_RAW_CONTENT = true;
	private static final Logger log = LoggerFactory.getLogger(GenericODataClient.class);
	
	private Edm edm = null;

	protected ODataFeed readFeed(String serviceUri, String contentType, String entitySetName)
			throws IOException, ODataException {
		initialize(serviceUri);
		EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
		String absolutUri = createUri(serviceUri, entitySetName, null);

		InputStream content = (InputStream) connect(absolutUri, contentType, GET).getContent();
		return EntityProvider.readFeed(contentType, entityContainer.getEntitySet(entitySetName), content,
				EntityProviderReadProperties.init().build());
	}

	private void initialize(String serviceUrl) throws IOException, ODataException {
		if (edm == null) {
			edm = readEdm(serviceUrl);
		}
	}

	private Edm readEdm(String serviceUrl) throws IOException, ODataException {
		InputStream content = execute(serviceUrl + SEPARATOR + METADATA, APPLICATION_XML, GET);
		return EntityProvider.readMetadata(content, false);
	}
	
	private String createUri(String serviceUri, String entitySetName, String id) {
		final StringBuilder absolutUri = new StringBuilder(serviceUri).append(SEPARATOR).append(entitySetName);
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
		connection.setRequestProperty(ACCEPT, contentType);
		if (POST.equals(httpMethod) || PUT.equals(httpMethod)) {
			connection.setDoOutput(true);
			connection.setRequestProperty(CONTENT_TYPE, contentType);
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