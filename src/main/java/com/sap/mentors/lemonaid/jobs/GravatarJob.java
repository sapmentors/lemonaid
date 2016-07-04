package com.sap.mentors.lemonaid.jobs;

import java.util.ArrayList;
import java.util.HashMap;

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

@Service
@Transactional
public class GravatarJob implements Job {
    
	private static final Logger log = LoggerFactory.getLogger(GravatarJob.class);

	@Autowired Gravatar gravatar;
	@Autowired MentorRepository mentorRepository;

    @Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Setting photoUrl of mentors - Start");
		long startTime = System.currentTimeMillis();
    	for (Mentor mentor : mentorRepository.findAll()) {
    		ArrayList<String> emails = new ArrayList<String>();
    		if (mentor.getEmail1() != null && mentor.getEmail1().length() > 0) {
    			emails.add(mentor.getEmail1());
    		}
    		if (mentor.getEmail2() != null && mentor.getEmail1().length() > 0) {
    			emails.add(mentor.getEmail2());
    		}
    		HashMap<String, Boolean> exist = gravatar.emailsExist(emails);
    		if (exist.get(mentor.getEmail1())) {
    			mentor.setPhotoUrl(gravatar.getUrlForEmail(mentor.getEmail1()) + "?s=144");
    		} else if (exist.get(mentor.getEmail2())) {
    			mentor.setPhotoUrl(gravatar.getUrlForEmail(mentor.getEmail2()) + "?s=144");
    		} else { 
    			mentor.setPhotoUrl(gravatar.getUrlOfUser() + "?s=144");
    		}
    		mentorRepository.save(mentor);
    	}
		log.info("Setting photoUrl of mentors - End. Duration: " + Long.toString(System.currentTimeMillis() - startTime) + "ms");
	}

}