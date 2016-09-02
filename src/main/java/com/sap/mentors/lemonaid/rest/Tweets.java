package com.sap.mentors.lemonaid.rest;

import java.util.List;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;

public class Tweets {

	public Tweets(SearchResults results) {
		for (int i = 0; i < results.getTweets().size(); i++) {
			statuses.add(new Status(results.getTweets().get(i)));
		}
		search_metadata = new SearchMetadata(results.getSearchMetadata());
	}

	List<Status> statuses;
	SearchMetadata search_metadata;
	
	public class Status {
		
		String coordinates;
		boolean favorited;
		boolean truncated;
		String created_at;
		String id_str;

		public Status(Tweet tweet) {
			this.id_str = tweet.getIdStr();
		}

	}
	
	public class SearchMetadata {
		
		long max_id;
		long since_id;
		String refresh_url;
		int count;
		float completed_in;
		String since_id_str;
		String query;
		String max_id_str;

		public SearchMetadata(org.springframework.social.twitter.api.SearchMetadata searchMetadata) {
			this.max_id = searchMetadata.getMaxId();
			this.max_id_str = Long.toString(max_id);
			this.since_id = searchMetadata.getSinceId();
			this.since_id_str = Long.toString(this.since_id);
		}
		
	}
	
}
