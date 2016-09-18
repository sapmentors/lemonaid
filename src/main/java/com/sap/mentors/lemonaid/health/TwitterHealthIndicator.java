package com.sap.mentors.lemonaid.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

import com.sap.mentors.lemonaid.client.TwitterClient;

@Component
public class TwitterHealthIndicator extends AbstractHealthIndicator {
	
	@Autowired TwitterClient twitter;

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		if (twitter.isAuthenticated()) {
			builder.withDetail("handle", twitter.getScreenName()).up();
		} else {
			builder.status("NotAuthenticated");
		}
	}

}
