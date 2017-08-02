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

import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.repository.MentorRepository;
import com.sap.mentors.lemonaid.utils.MentorUtils;
import com.sap.mentors.lemonaid.utils.types.Point;

@Service
@Transactional
@DisallowConcurrentExecution
public class LocationJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(LocationJob.class);

	@Autowired MentorRepository mentorRepository;
	@Autowired MentorUtils mentorUtils;

    @Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

    	log.info("Setting geolocation of mentors - Start");
		long startTime = System.currentTimeMillis();
		for (Mentor mentor : mentorRepository.findAll()) {
            mentor.setLocation(mentorUtils.getLocationOfMentor(mentor));
            mentor.setPublicLocation(mentorUtils.getPublicLocationOfMentor(mentor));
    		mentorRepository.save(mentor);
    	}
		log.info("Setting geolocation of mentors - End. Duration: " + Long.toString(System.currentTimeMillis() - startTime) + "ms");
	}

}
