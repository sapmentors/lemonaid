package com.sap.mentors.lemonaid.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.repository.MentorRepository;
import com.sap.mentors.lemonaid.utils.MentorUtils;

@RestController
@RequestMapping("/control")
public class ControlController {

	@Autowired MentorRepository mentorRepository;
	@Autowired MentorUtils mentorUtils;

    @RequestMapping(method = RequestMethod.GET)
    String getTweets(HttpServletRequest request) {
		for (Mentor mentor : mentorRepository.findAll()) {
			mentor.setLocation(mentorUtils.getLocationOfMentor(mentor));
    		mentorRepository.save(mentor);
    	}
    	return null;
    }

}
