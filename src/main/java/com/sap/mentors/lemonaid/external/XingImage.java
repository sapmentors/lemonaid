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
public class XingImage {

	private final Logger logger = LoggerFactory.getLogger(XingImage.class);
	
	public String getProfilePhoto(final String xingUrl) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(xingUrl);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				Pattern pattern = Pattern.compile("<meta name=\"twitter:image\" content=\"(.+)\">");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					return matcher.group(1);
				}
			}
		} catch (UnsupportedOperationException | IOException e) {
			logger.error("Unable to retrieve Xing url '{}'", xingUrl);
		}
		return null;
	}

}
