package com.sap.mentors.lemonaid.client;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

@Component
public class TwitterClient {

	private Twitter twitter;
	private ConnectionRepository connectionRepository;
	
	@Inject
    public TwitterClient(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }
	
	public boolean isAuthenticated() {
		return connectionRepository.findPrimaryConnection(Twitter.class) != null;
	}
	
	public SearchResults search(String query) { 
		return twitter.searchOperations().search(query);
	}
	
	public String getScreenName() {
		return twitter.userOperations().getScreenName();
	}
	
}
