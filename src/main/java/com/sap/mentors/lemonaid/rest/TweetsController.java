package com.sap.mentors.lemonaid.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.mentors.lemonaid.client.TwitterClient;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

	@Autowired TwitterClient twitterClient;

	private static final Logger log = LoggerFactory.getLogger(TweetsController.class);

    @RequestMapping(method = RequestMethod.POST)
    Tweets getTweets(HttpServletRequest request) {
	    if (twitterClient.isAuthenticated()) {
	    	log.warn("Retrieving tweets");
	    	String host = request.getParameter("request[host]");
	    	String url = request.getParameter("request[url]");
	    	String query = request.getParameter("request[parameters][q]");
	    	try {
				boolean includeEntities = Integer.parseInt(request.getParameter("request[parameters][include_entities]")) > 0;
			} catch (NumberFormatException e) {
				boolean includeEntities = true;
			}
	    	try {
				int count = Integer.parseInt(request.getParameter("request[parameters][count]"));
			} catch (NumberFormatException e) {
				int count = 5;
			}
	    	return new Tweets(twitterClient.search(query));
	    } else {
	    	log.error("Twitter client not connected, Pleasec connect using /connect/twitter)");
	    }
	    return null;
    }

}
