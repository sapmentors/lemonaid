package com.sap.mentors.lemonaid.jobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

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

import com.sap.mentors.lemonaid.client.SitRegClient;
import com.sap.mentors.lemonaid.entities.Event;
import com.sap.mentors.lemonaid.repository.EventRepository;

@Service
@Transactional
@DisallowConcurrentExecution
public class InsideTrackJob implements Job {
    
	private static final Logger log = LoggerFactory.getLogger(InsideTrackJob.class);
	
	
	@Autowired EventRepository eventRepository;
	@Autowired SitRegClient sitregClient;

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
			ODataFeed feed = sitregClient.readFeed("Events");
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

}