package com.sap.mentors.lemonaid.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitterImage {

	private final Logger logger = LoggerFactory.getLogger(TwitterImage.class);
	
	public String getAvatar(final String twitterId) {
		String url;
		try {
			url = "https://twitter.com/" + twitterId;
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				Pattern pattern = Pattern.compile("ProfileAvatar-image\\s*\" src=\"([^\"]+)\"");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					return matcher.group(1);
				}
			}
		} catch (UnsupportedOperationException | IOException e) {
			logger.error("Unable to retrieve home page of Twitter user '{}'", twitterId);
		}
		return null;
	}

}
