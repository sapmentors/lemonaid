package com.sap.mentors.lemonaid.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.sap.mentors.lemonaid.entities.Country;
import com.sap.mentors.lemonaid.entities.ExpertiseLevel;
import com.sap.mentors.lemonaid.entities.Gender;
import com.sap.mentors.lemonaid.entities.Industry;
import com.sap.mentors.lemonaid.entities.LineOfBusiness;
import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.entities.MentorStatus;
import com.sap.mentors.lemonaid.entities.Region;
import com.sap.mentors.lemonaid.entities.RelationshipToSap;
import com.sap.mentors.lemonaid.entities.SapSoftwareSolution;
import com.sap.mentors.lemonaid.entities.Size;
import com.sap.mentors.lemonaid.entities.SoftSkill;
import com.sap.mentors.lemonaid.entities.Topic;
import com.sap.mentors.lemonaid.repository.CountryRepository;
import com.sap.mentors.lemonaid.repository.MentorRepository;

@Component
public class Importer {

	private final String fileName = "mentors.json";
	private final Logger logger = LoggerFactory.getLogger(Importer.class);
	
	@Autowired private MentorRepository mentorRepository;
	@Autowired private CountryRepository countryRepository;
	@Autowired private MentorUtils mentorUtils;

	public void importMentors() {
		try {
			JsonReader jsonReader = new JsonReader(
					new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)));
			jsonReader.beginObject();
			while (jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				if (name.equals("Mentors")) {
					jsonReader.beginArray();
					while (jsonReader.hasNext()) {
						readMentor(jsonReader);
					}
					jsonReader.endArray();
				}
			}
			jsonReader.endObject();
			jsonReader.close();
		} catch (IOException e) {
			logger.error(e.getClass() + " - " + e.getMessage());
		}
	}

	private void readMentor(JsonReader jsonReader) throws IOException {
		Mentor mentor = new Mentor();
		mentor.setId(UUID.randomUUID().toString());
		jsonReader.beginObject();
		while (jsonReader.hasNext()) {
			String key = jsonReader.nextName();
			if (jsonReader.peek().equals(JsonToken.NULL)) {
				jsonReader.nextNull();
			} else {
				String value = jsonReader.nextString();
				if (key.equals("Full Name")) { mentor.setFullName(value); }
				else if (key.equals("Mentor Status")) { mentor.setStatusId(new MentorStatus(value)); }
				else if (key.equals("Shirt Number")) { mentor.setShirtNumber(Integer.parseInt(value));}
				else if (key.equals("Shirt Text")) { mentor.setShirtText(value); }
				else if (key.equals("Shirt Size")) { mentor.setShirtSizeId(new Size(value)); }
				else if (key.equals("Shirt M/F")) { mentor.setShirtMFId(new Gender(value));}
				else if (key.equals("Would you like to work with us on Mentor Communication Strategy")) { mentor.setInterestInMentorCommunicationStrategy("YES".equals(value.toUpperCase())); }
				else if (key.equals("Would you like to work with us on Mentor Management Model")) { mentor.setInterestInMentorManagementModel("YES".equals(value.toUpperCase())); }
				else if (key.equals("Would you like to work with us on Mentor Mix")) { mentor.setInterestInMentorMix("YES".equals(value.toUpperCase())); }
				else if (key.equals("Other Ideas you would like to work on with us")) { mentor.setInterestInOtherIdeas("YES".equals(value.toUpperCase())); }
				else if (key.equals("How much time will you be able to dedicate to this effort per week? ")) { mentor.setHoursAvailable(Integer.parseInt(value)); }
				else if (key.equals("Topic Lead Region")) { mentor.setTopicLeadRegionId(new Region(value)); }
				else if (key.equals("Topic 1")) { mentor.setTopic1Id(new Topic(value.toLowerCase().replace(" ", "_"))); }
				else if (key.equals("Topic 1 Executive")) { mentor.setTopic1Executive(value); }
				else if (key.equals("Topic 2")) { mentor.setTopic2Id(new Topic(value.toLowerCase().replace(" ", "_"))); }
				else if (key.equals("Topic 2 Executive")) { mentor.setTopic2Executive(value); }
				else if (key.equals("Topic 3")) { mentor.setTopic3Id(new Topic(value.toLowerCase().replace(" ", "_"))); }
				else if (key.equals("Topic 3 Executive")) { mentor.setTopic3Executive(value); }
				else if (key.equals("Topic 4")) { mentor.setTopic4Id(new Topic(value.toLowerCase().replace(" ", "_"))); }
				else if (key.equals("Topic 4 Executive")) { mentor.setTopic4Executive(value); }
				else if (key.equals("Would you like to be a topic lead?")) { mentor.setTopicLeadInterest("YES".equals(value.toUpperCase()));}
				else if (key.equals("What topic area are you interested in?")) { mentor.setTopicInterestId(new Topic(value.toLowerCase().replace(" ", "_"))); }
				else if (key.equals("Job Title ")) { mentor.setJobTitle(value); }
				else if (key.equals("email 1")) { mentor.setEmail1(value); }
				else if (key.equals("email 2")) { mentor.setEmail2(value); }
				else if (key.equals("Preferred email (1 or 2)")) {}
				else if (key.equals("SCN User ID URL")) { mentor.setScnUrl(value); }
				else if (key.equals("Your SCN bio ")) { mentor.setBio(value); }
				else if (key.equals("Twitter Handle")) { mentor.setTwitterId(value); }
				else if (key.equals("LinkedIn")) { mentor.setLinkedInUrl(value); }
				else if (key.equals("Xing profile")) { mentor.setXingUrl(value); }
				else if (key.equals("Facebook")) { mentor.setFacebookUrl(value); }
				else if (key.equals("Address Street / P.O. Box / Appartment ")) { mentor.setAddress1(value); }
				else if (key.equals("City")) { mentor.setCity(value); }
				else if (key.equals("State/ Province")) { mentor.setState(value); }
				else if (key.equals("Zip/Postal Code")) { mentor.setZip(value); }
				else if (key.equals("Country")) { mentor.setCountryId((Country) countryRepository.findByName(value).toArray()[0]); }
				else if (key.equals("Overall Region")) { mentor.setRegionId(new Region(value)); }
				else if (key.equals("Company")) { mentor.setCompany(value); }
				else if (key.equals("Relationship to SAP")) { mentor.setRelationshipToSapId(new RelationshipToSap(value.toLowerCase().trim().replace(" ", "_"))); }
				else if (key.equals("Phone Number ")) { mentor.setPhone(value); }
				else if (key.equals("1st Line of Business")) { mentor.setLineOfBusiness1Id(new LineOfBusiness(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", ""))); }
				else if (key.equals("2st Line of Business")) { mentor.setLineOfBusiness2Id(new LineOfBusiness(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", ""))); }
				else if (key.equals("3st Line of Business")) { mentor.setLineOfBusiness3Id(new LineOfBusiness(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", ""))); }
				else if (key.equals("1st Industry")) { mentor.setIndustry1Id(new Industry(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", "")));}
				else if (key.equals("2nd Industry")) { mentor.setIndustry2Id(new Industry(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", "")));}
				else if (key.equals("3rd Industry")) { mentor.setIndustry3Id(new Industry(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", "")));}
				else if (key.equals("1st SAP Sofware Solution")) { mentor.setSapExpertise1Id(new SapSoftwareSolution(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); } 
				else if (key.equals("Expertise1")) { mentor.setSapExpertise1LevelId(new ExpertiseLevel(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", ""))); }
				else if (key.equals("2nd SAP Sofware Solution"))  { mentor.setSapExpertise2Id(new SapSoftwareSolution(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); }
				else if (key.equals("Expertise2")) { mentor.setSapExpertise2LevelId(new ExpertiseLevel(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", ""))); }
				else if (key.equals("3rd Sap Software Solution"))  { mentor.setSapExpertise3Id(new SapSoftwareSolution(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); }
				else if (key.equals("Expertise3")) { mentor.setSapExpertise3LevelId(new ExpertiseLevel(value.toLowerCase().trim().replace(" ", "_").replace("&", "").replace(",", ""))); }
				else if (key.equals("1st Soft Skills ")) { mentor.setSoftSkill1Id(new SoftSkill(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); }
				else if (key.equals("2st Soft Skills ")) { mentor.setSoftSkill2Id(new SoftSkill(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); }
				else if (key.equals("3st Soft Skills ")) { mentor.setSoftSkill3Id(new SoftSkill(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); }
				else if (key.equals("4st Soft Skills ")) { mentor.setSoftSkill4Id(new SoftSkill(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); }
				else if (key.equals("5st Soft Skills ")) { mentor.setSoftSkill5Id(new SoftSkill(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); }
				else if (key.equals("6st Soft Skills ")) { mentor.setSoftSkill6Id(new SoftSkill(value.toLowerCase().trim().replace(" ", "_").replace("(", "").replace(")", "").replace("&", "").replace("/", "").replace("-", "").replace(",", ""))); }
			}
		}
		jsonReader.endObject();
		mentor.setPhotoUrl(mentorUtils.getImageOfMentor(mentor));
//		mentor.setLocation(mentorUtils.getLocationOfMentor(mentor));
		if (mentor.getFullName().equals("Jan Penninkhof") || 
			mentor.getFullName().equals("Robin van het Hof") ||
			mentor.getFullName().equals("Twan van den Broek") ||
			mentor.getFullName().equals("Fred Verheul"))
				mentor.setPublicProfile(true);
		logger.info("Adding {} to repository", mentor.getFullName());
		mentorRepository.save(mentor);
	}

}
