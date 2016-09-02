package com.sap.mentors.lemonaid.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.SearchMetadata;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Tweets {

	private List<Tweet> statuses;
	private SearchMetadata search_metadata;

	public Tweets(SearchResults results) {
		for (int i = 0; i < results.getTweets().size(); i++) {
			if (statuses == null) this.statuses = new ArrayList<Tweet>(); 
			this.statuses.add(results.getTweets().get(i));
		}
		search_metadata = results.getSearchMetadata();
	}

	public List<Tweet> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Tweet> statuses) {
		this.statuses = statuses;
	}

	public SearchMetadata getSearch_metadata() {
		return search_metadata;
	}

	public void setSearch_metadata(SearchMetadata search_metadata) {
		this.search_metadata = search_metadata;
	}

}
