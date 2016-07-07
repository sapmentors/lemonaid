package com.sap.mentors.lemonaid.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.repository.MentorRepository;

@Service
@Transactional
@DisallowConcurrentExecution
public class LocationJob implements Job {
    
	private static final Logger log = LoggerFactory.getLogger(LocationJob.class);

	@Autowired MentorRepository mentorRepository;

    @Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

    	log.info("Setting geolocation of mentors - Start");
		long startTime = System.currentTimeMillis();
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyC9hT7x8gTBdXcTSEy6XU_EWpr_WDe8lSY");
		for (Mentor mentor : mentorRepository.findAll()) {
			try {
				String address = "";
				if (mentor.getAddress1() != null && mentor.getAddress1().length() > 0) {
					address += (address.length() > 0 ? ", " : "") + mentor.getAddress1();
				}
				if (mentor.getAddress2() != null && mentor.getAddress2().length() > 0) {
					address += (address.length() > 0 ? ", " : "") + mentor.getAddress2();
				}
				if (mentor.getZip() != null && mentor.getZip().length() > 0) {
					address += (address.length() > 0 ? ", " : "") + mentor.getZip();
				}
				if (mentor.getCity() != null && mentor.getCity().length() > 0) {
					address += (address.length() > 0 ? ", " : "") + mentor.getCity();
				}
				if (mentor.getState() != null && mentor.getState().length() > 0) {
					address += (address.length() > 0 ? ", " : "") + mentor.getState();
				}
				if (mentor.getCountryId() != null && mentor.getCountryId().getName() != null && mentor.getCountryId().getName().length() > 0) {
					address += (address.length() > 0 ? ", " : "") + mentor.getCountryId().getName();
				}
				if (address.length() > 0) {
					GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
					mentor.setLatitude(results[0].geometry.location.lat);
					mentor.setLongitude(results[0].geometry.location.lng);
				} else {
					mentor.setLatitude(null);
					mentor.setLongitude(null);
					log.error("Unable to geocode address of " + mentor.getFullName() + ": No address available");
				}
			} catch (Exception e) {
				mentor.setLatitude(null);
				mentor.setLongitude(null);
				log.error("Unable to geocode address of " + mentor.getFullName() + ": " + e.toString());
			}
    		mentorRepository.save(mentor);
    	}
		log.info("Setting geolocation of mentors - End. Duration: " + Long.toString(System.currentTimeMillis() - startTime) + "ms");
	}

}