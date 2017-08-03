package com.sap.mentors.lemonaid.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.external.GravatarImage;
import com.sap.mentors.lemonaid.external.ScnImage;
import com.sap.mentors.lemonaid.external.TwitterImage;
import com.sap.mentors.lemonaid.external.XingImage;
import com.sap.mentors.lemonaid.utils.types.Point;

@Component
public class MentorUtils {

	@Autowired GravatarImage gravatar;
	@Autowired TwitterImage twitter;
	@Autowired ScnImage scn;
	@Autowired XingImage xing;

	private static final Logger log = LoggerFactory.getLogger(MentorUtils.class);

	public String getImageOfMentor(Mentor mentor) throws IOException {
		String photoUrl = null;

		// Try email based Gravatar first
		ArrayList<String> emails = new ArrayList<String>();
		if (mentor.getEmail1() != null && mentor.getEmail1().length() > 0) {
			emails.add(mentor.getEmail1());
		}
		if (mentor.getEmail2() != null && mentor.getEmail1().length() > 0) {
			emails.add(mentor.getEmail2());
		}
		HashMap<String, Boolean> exist = gravatar.emailsExist(emails);
		if (mentor.getEmail1() != null && exist.get(mentor.getEmail1())) {
			photoUrl = gravatar.getUrlForEmail(mentor.getEmail1()) + "?s=144";
		} else if (mentor.getEmail2() != null && exist.get(mentor.getEmail2())) {
			photoUrl = gravatar.getUrlForEmail(mentor.getEmail2()) + "?s=144";
		}

		// Try Twitter Photo
		if (photoUrl == null && mentor.getTwitterId() != null && mentor.getTwitterId().length() > 0) {
			photoUrl = twitter.getAvatar(mentor.getTwitterId());
		}

		// Try Xing Photo
		if (photoUrl == null && mentor.getXingUrl() != null && mentor.getXingUrl().length() > 0) {
			photoUrl = xing.getProfilePhoto(mentor.getXingUrl());
		}

		// Try SCN Photo
		if (photoUrl == null && mentor.getScnUrl() != null && mentor.getScnUrl().length() > 0) {
			photoUrl = scn.getProfilePhoto(mentor.getScnUrl());
		}

		// If still no photo was found, use the SAP Mentors lemon
		if (photoUrl == null) {
			photoUrl = gravatar.getUrlOfUser() + "?s=144";
		}

		return photoUrl;
	}

    	public Point getLocationOfMentor(Mentor mentor) {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyC9hT7x8gTBdXcTSEy6XU_EWpr_WDe8lSY");
		try {
			String address = "";
			if (mentor.getAddress1() != null && mentor.getAddress1().length() > 0 ) {
				address += (address.length() > 0 ? ", " : "") + mentor.getAddress1();
			}
			if (mentor.getAddress2() != null && mentor.getAddress2().length() > 0 ) {
				address += (address.length() > 0 ? ", " : "") + mentor.getAddress2();
			}
			if (mentor.getZip() != null && mentor.getZip().length() > 0 ) {
				address += (address.length() > 0 ? ", " : "") + mentor.getZip();
			}
			if (mentor.getCity() != null && mentor.getCity().length() > 0 ) {
				address += (address.length() > 0 ? ", " : "") + mentor.getCity();
			}
			if (mentor.getState() != null && mentor.getState().length() > 0 ) {
				address += (address.length() > 0 ? ", " : "") + mentor.getState();
			}
			if (mentor.getCountryId() != null && mentor.getCountryId().getName() != null && mentor.getCountryId().getName().length() > 0 ) {
				address += (address.length() > 0 ? ", " : "") + mentor.getCountryId().getName();
			}
			if (address.length() > 0) {
				GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
				return new Point(results[0].geometry.location.lat, results[0].geometry.location.lng);
			} else {
				log.error("Unable to geocode address of " + mentor.getFullName() + ": No address available");
				return null;
			}
		} catch (Exception e) {
			log.error("Unable to geocode address of " + mentor.getFullName() + ": " + e.toString());
			return null;
		}

	}

	public Point getPublicLocationOfMentor(Mentor mentor) {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyC9hT7x8gTBdXcTSEy6XU_EWpr_WDe8lSY");
		try {
			String address = "";
			if (mentor.getAddress1() != null && mentor.getAddress1().length() > 0 && mentor.getAddress1Public()) {
				address += (address.length() > 0 ? ", " : "") + mentor.getAddress1();
			}
			if (mentor.getAddress2() != null && mentor.getAddress2().length() > 0 && mentor.getAddress2Public()) {
				address += (address.length() > 0 ? ", " : "") + mentor.getAddress2();
			}
			if (mentor.getZip() != null && mentor.getZip().length() > 0 && mentor.getZipPublic()) {
				address += (address.length() > 0 ? ", " : "") + mentor.getZip();
			}
			if (mentor.getCity() != null && mentor.getCity().length() > 0 && mentor.getCityPublic()) {
				address += (address.length() > 0 ? ", " : "") + mentor.getCity();
			}
			if (mentor.getState() != null && mentor.getState().length() > 0 && mentor.getStatePublic()) {
				address += (address.length() > 0 ? ", " : "") + mentor.getState();
			}
			if (mentor.getCountryId() != null && mentor.getCountryId().getName() != null && mentor.getCountryId().getName().length() > 0 && mentor.getCountryPublic()) {
				address += (address.length() > 0 ? ", " : "") + mentor.getCountryId().getName();
			}
			if (address.length() > 0) {
				GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
				return new Point(results[0].geometry.location.lat, results[0].geometry.location.lng);
			} else {
				log.error("Unable to geocode address of " + mentor.getFullName() + ": No address available");
				return null;
			}
		} catch (Exception e) {
			log.error("Unable to geocode address of " + mentor.getFullName() + ": " + e.toString());
			return null;
		}

	}

}
