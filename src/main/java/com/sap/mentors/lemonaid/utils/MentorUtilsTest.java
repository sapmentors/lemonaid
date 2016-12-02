package com.sap.mentors.lemonaid.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sap.mentors.lemonaid.entities.Country;
import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.utils.types.Point;

public class MentorUtilsTest {

	@Test
	public void testGeocodingForGermany() {
		Mentor mentor = new Mentor();
		mentor.setAddress1("Hasso-Plattner-Ring 7");
		mentor.setCity("Walldorf");
		mentor.setZip("69190");
		Country countryId = new Country("DE");
		mentor.setCountryId(countryId);
		MentorUtils mentorUtils = new MentorUtils();
		Point point = mentorUtils.getLocationOfMentor(mentor);
		double lat = new Double(49.2944697);
		double lon = new Double(8.6367358);
		assertEquals(lat, point.getLatitude(), 0.001);
		assertEquals(lon, point.getLongitude(), 0.001);
	}
	@Test
	public void testGeocodingForMalaysia() {
		Mentor mentor = new Mentor();
		mentor.setAddress1("22, Gtower, 199, Jalan Tun Razak");
		mentor.setCity("Kuala Lumpur");
		mentor.setState("Wilayah Persekutuan Kuala Lumpur");
		mentor.setZip("50450");
		Country countryId = new Country("MY");
		mentor.setCountryId(countryId);
		MentorUtils mentorUtils = new MentorUtils();
		Point point = mentorUtils.getLocationOfMentor(mentor);
		double lat = new Double(3.1589771);
		double lon = new Double(101.719424);
		assertEquals(lat, point.getLatitude(), 0.001);
		assertEquals(lon, point.getLongitude(), 0.001);
	}
}
