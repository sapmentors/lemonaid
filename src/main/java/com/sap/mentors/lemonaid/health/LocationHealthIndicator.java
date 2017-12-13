package com.sap.mentors.lemonaid.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.repository.MentorRepository;
import com.sap.mentors.lemonaid.utils.MentorUtils;
import com.sap.mentors.lemonaid.utils.types.Point;

@Component
public class LocationHealthIndicator extends AbstractHealthIndicator {
	
	private static final Logger log = LoggerFactory.getLogger(LocationHealthIndicator.class);

	@Autowired MentorRepository mentorRepository;
	@Autowired MentorUtils mentorUtils;

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
    	log.info("Test geolocation service health - start.");
		long startTime = System.currentTimeMillis();
		Mentor mentor = mentorRepository.findAll().iterator().next();
		Point point = mentorUtils.getLocationOfMentor(mentor);
		if (point.getLatitude() != 0) {
			builder.withDetail("handle", point.toString()).up();
		} else {
			builder.status("GeocodingError");
		}
		log.info("Test geolocation service health - end. Duration: " + Long.toString(System.currentTimeMillis() - startTime) + "ms");
	}

}
