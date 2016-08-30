package com.sap.mentors.lemonaid.jobs;

import java.util.ArrayList;

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
		ArrayList<Event> events = sitregClient.getEvents();
		if (events != null) {
			eventRepository.deleteAll();
			for (Event event : events) {
				eventRepository.save(event);
			}
		}
		log.info("Pulling inside track events  - End. Duration: " + Long.toString(System.currentTimeMillis() - startTime) + "ms");
	}

}