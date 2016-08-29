package com.sap.mentors.lemonaid.jobs;

import java.io.IOException;

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
import com.sap.mentors.lemonaid.external.Gravatar;
import com.sap.mentors.lemonaid.repository.MentorRepository;
import com.sap.mentors.lemonaid.utils.MentorUtils;

@Service
@Transactional
@DisallowConcurrentExecution
public class GravatarJob implements Job {
    
	private static final Logger log = LoggerFactory.getLogger(GravatarJob.class);

	@Autowired Gravatar gravatar;
	@Autowired MentorRepository mentorRepository;
	@Autowired MentorUtils mentorUtils;

    @Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Setting photoUrl of mentors - Start");
		long startTime = System.currentTimeMillis();
    	for (Mentor mentor : mentorRepository.findAll()) {
    		try {
				mentor.setPhotoUrl(mentorUtils.getImageOfMentor(mentor));
	    		mentorRepository.save(mentor);
			} catch (IOException e) {
				log.warn("Error occurred while refreshing photo of mentor '" + mentor.getId() + "': " + e.getMessage());
			}
    	}
		log.info("Setting photoUrl of mentors - End. Duration: " + Long.toString(System.currentTimeMillis() - startTime) + "ms");
	}

}