package com.sap.mentors.lemonaid.client;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sap.mentors.lemonaid.entities.Event;

@Component
public class SitRegClient extends GenericODataClient {

	public static final String SERVICE_URL = "https://sapmentorsa5a504e08.hana.ondemand.com/com/sap/sapmentors/sitreg/odatapublic/service.xsodata";

	private static final Logger log = LoggerFactory.getLogger(SitRegClient.class);

	public ArrayList<Event> getEvents() {
		try {
			ODataFeed feed = readFeed("Events");
			ArrayList<Event> events = new ArrayList<Event>();
			for (ODataEntry entry : feed.getEntries()) {
				Event event = new Event();
				event.setSourceId(entry.getProperties().get("ID").toString());
				event.setSource("SITREG");
				event.setId(event.getSource() + "-" + event.getSourceId());
				event.setName((String) entry.getProperties().get("Location"));
				event.setLocation((String) entry.getProperties().get("Location"));
				event.setStartDate((Calendar) entry.getProperties().get("StartTime"));
				event.setEndDate((Calendar) entry.getProperties().get("EndTime"));
//				Okay, this is really kinda sucky. Unfortunately the SitReg app sometimes has the date included in the StartTime/EndTime and sometimes doesn't.
//				Hopefully it'll be possible to remove this in the future
				if (event.getStartDate().getTime().getTime() < 86400000) {
					Calendar date = event.getStartDate();
					date.add(Calendar.MINUTE, (int) (((Calendar) entry.getProperties().get("EventDate")).getTime().getTime() / 1000 / 60));
					event.setStartDate(date);
				}
				if (event.getEndDate().getTime().getTime() < 86400000) {
					Calendar date = event.getEndDate();
					date.add(Calendar.MINUTE, (int) (((Calendar) entry.getProperties().get("EventDate")).getTime().getTime() / 1000 / 60));
					event.setEndDate(date);
				}
				event.setUrl((String) entry.getProperties().get("HomepageURL"));
				events.add(event);
			}
			return events;
		} catch (IOException | ODataException e) {
			log.warn("Unable to retrieve Inside Track events list: " + e.getMessage());
			return null;
		}
	}

	private ODataFeed readFeed(String entitySetName)
			throws IOException, ODataException {
		return readFeed(SERVICE_URL, APPLICATION_JSON, entitySetName);
	}

}
