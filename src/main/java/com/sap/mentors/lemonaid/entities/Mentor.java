package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mentors")
public class Mentor {

	@Id
	private String id;
    private String fullName;
    @ManyToOne private MentorStatus status;

    private String jobTitle;
    private String company;
    @ManyToOne private RelationshipToSap relationshipToSap;

    @ManyToOne private LineOfBusiness lineOfBusiness1;
    @ManyToOne private LineOfBusiness lineOfBusiness2;
    @ManyToOne private LineOfBusiness lineOfBusiness3;
    @ManyToOne private Industry industry1;
    @ManyToOne private Industry industry2;
    @ManyToOne private Industry industry3;
    @ManyToOne private SapSoftwareSolution sapExpertise1;
    @ManyToOne private ExpertiseLevel sapExpertise1level;
    @ManyToOne private SapSoftwareSolution sapExpertise2;
    @ManyToOne private ExpertiseLevel sapExpertise2level;
    @ManyToOne private SapSoftwareSolution sapExpertise3;
    @ManyToOne private ExpertiseLevel sapExpertise3level;
    @ManyToOne private SoftSkill softSkill1;
    @ManyToOne private SoftSkill softSkill2;
    @ManyToOne private SoftSkill softSkill3;
    @ManyToOne private SoftSkill softSkill4;
    @ManyToOne private SoftSkill softSkill5;
    @ManyToOne private SoftSkill softSkill6;
    
    @Lob private String bio; 
    private String email1;
    private String email2;
    private int preferredEmail;
    
    private String address;
    private String city;
    private String state;
    private String zip;
    @ManyToOne private Country country;
    private String phone;
    
    @ManyToOne private Region region;
    
    private int shirtNumber;
    private String shirtText;
    private String shirtSize;
    private String shirtMF;

    private String scnUrl;
    private String twitterId;
    private String linkedInUrl;
    private String xingUrl;
    private String facebookUrl;
        
    private boolean interestInMentorCommunicationStrategy;
    private boolean interestInMentorManagementModel;
    private boolean interestInMentorMix;
    private boolean interestInOtherIdeas;
    private int hoursAvailable;
    
    private String topicLeadRegion;
    private String topic1;
    private String topic1Executive;
    private String topic2;
    private String topic2Executive;
    private String topic3;
    private String topic3Executive;
    private String topic4;
    private String topic4Executive;
    private boolean topicLeadInterest;
    private String topicInterest;
    
    public Mentor() {}

    public Mentor(
    		String id, String fullName, MentorStatus status, 
    		String jobTitle, String company, RelationshipToSap relationshipToSap,
    		LineOfBusiness lineOfBusiness1, LineOfBusiness lineOfBusiness2, LineOfBusiness lineOfBusiness3,
    		Industry industry1, Industry industry2, Industry industry3,
    		SapSoftwareSolution sapExpertise1, ExpertiseLevel sapExpertise1level, SapSoftwareSolution sapExpertise2, ExpertiseLevel sapExpertise2level, SapSoftwareSolution sapExpertise3, ExpertiseLevel sapExpertise3level,
    		SoftSkill softSkill1, SoftSkill softSkill2, SoftSkill softSkill3, SoftSkill softSkill4, SoftSkill softSkill5, SoftSkill softSkill6,
    		String bio,
    		String email1, String email2, int preferredEmail,
    		String address, String city, String state, String zip, Country country, String phone,
    		Region region,
    		int shirtNumber, String shirtText)
    {
    	this.id = id;
        this.fullName = fullName;
        this.status = status;
        
        this.jobTitle = jobTitle;
        this.company = company;
        this.relationshipToSap = relationshipToSap;
        
        this.lineOfBusiness1 = lineOfBusiness1;
        this.lineOfBusiness2 = lineOfBusiness2;
        this.lineOfBusiness3 = lineOfBusiness3;
        this.industry1 = industry1;
        this.industry2 = industry2;
        this.industry3 = industry3;
        this.sapExpertise1 = sapExpertise1;
        this.sapExpertise1level = sapExpertise1level;
        this.sapExpertise2 = sapExpertise2;
        this.sapExpertise2level = sapExpertise2level;
        this.sapExpertise3 = sapExpertise3;
        this.sapExpertise3level = sapExpertise3level;
        this.softSkill1 = softSkill1;
        this.softSkill2 = softSkill2;
        this.softSkill3 = softSkill3;
        this.softSkill4 = softSkill4;
        this.softSkill5 = softSkill5;
        this.softSkill6 = softSkill6;
        
        this.bio = bio;
        this.email1 = email1;
        this.email2 = email2;
        this.preferredEmail = 1;
        
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.phone = phone;
        
        this.region = region;
        
        this.shirtNumber = shirtNumber;
        this.shirtText = shirtText;
    }

    @Override
    public String toString() {
        return String.format(
                "Mentor[id=%d, fullName='%s']",
                id, fullName);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public MentorStatus getStatus() {
		return status;
	}

	public void setStatus(MentorStatus status) {
		this.status = status;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public RelationshipToSap getRelationshipToSap() {
		return relationshipToSap;
	}

	public void setRelationshipToSap(RelationshipToSap relationshipToSap) {
		this.relationshipToSap = relationshipToSap;
	}

	public LineOfBusiness getLineOfBusiness1() {
		return lineOfBusiness1;
	}

	public void setLineOfBusiness1(LineOfBusiness lineOfBusiness1) {
		this.lineOfBusiness1 = lineOfBusiness1;
	}

	public LineOfBusiness getLineOfBusiness2() {
		return lineOfBusiness2;
	}

	public void setLineOfBusiness2(LineOfBusiness lineOfBusiness2) {
		this.lineOfBusiness2 = lineOfBusiness2;
	}

	public LineOfBusiness getLineOfBusiness3() {
		return lineOfBusiness3;
	}

	public void setLineOfBusiness3(LineOfBusiness lineOfBusiness3) {
		this.lineOfBusiness3 = lineOfBusiness3;
	}

	public Industry getIndustry1() {
		return industry1;
	}

	public void setIndustry1(Industry industry1) {
		this.industry1 = industry1;
	}

	public Industry getIndustry2() {
		return industry2;
	}

	public void setIndustry2(Industry industry2) {
		this.industry2 = industry2;
	}

	public Industry getIndustry3() {
		return industry3;
	}

	public void setIndustry3(Industry industry3) {
		this.industry3 = industry3;
	}

	public SapSoftwareSolution getSapExpertise1() {
		return sapExpertise1;
	}

	public void setSapExpertise1(SapSoftwareSolution sapExpertise1) {
		this.sapExpertise1 = sapExpertise1;
	}

	public ExpertiseLevel getSapExpertise1level() {
		return sapExpertise1level;
	}

	public void setSapExpertise1level(ExpertiseLevel sapExpertise1level) {
		this.sapExpertise1level = sapExpertise1level;
	}

	public SapSoftwareSolution getSapExpertise2() {
		return sapExpertise2;
	}

	public void setSapExpertise2(SapSoftwareSolution sapExpertise2) {
		this.sapExpertise2 = sapExpertise2;
	}

	public ExpertiseLevel getSapExpertise2level() {
		return sapExpertise2level;
	}

	public void setSapExpertise2level(ExpertiseLevel sapExpertise2level) {
		this.sapExpertise2level = sapExpertise2level;
	}

	public SapSoftwareSolution getSapExpertise3() {
		return sapExpertise3;
	}

	public void setSapExpertise3(SapSoftwareSolution sapExpertise3) {
		this.sapExpertise3 = sapExpertise3;
	}

	public ExpertiseLevel getSapExpertise3level() {
		return sapExpertise3level;
	}

	public void setSapExpertise3level(ExpertiseLevel sapExpertise3level) {
		this.sapExpertise3level = sapExpertise3level;
	}

	public SoftSkill getSoftSkill1() {
		return softSkill1;
	}

	public void setSoftSkill1(SoftSkill softSkill1) {
		this.softSkill1 = softSkill1;
	}

	public SoftSkill getSoftSkill2() {
		return softSkill2;
	}

	public void setSoftSkill2(SoftSkill softSkill2) {
		this.softSkill2 = softSkill2;
	}

	public SoftSkill getSoftSkill3() {
		return softSkill3;
	}

	public void setSoftSkill3(SoftSkill softSkill3) {
		this.softSkill3 = softSkill3;
	}

	public SoftSkill getSoftSkill4() {
		return softSkill4;
	}

	public void setSoftSkill4(SoftSkill softSkill4) {
		this.softSkill4 = softSkill4;
	}

	public SoftSkill getSoftSkill5() {
		return softSkill5;
	}

	public void setSoftSkill5(SoftSkill softSkill5) {
		this.softSkill5 = softSkill5;
	}

	public SoftSkill getSoftSkill6() {
		return softSkill6;
	}

	public void setSoftSkill6(SoftSkill softSkill6) {
		this.softSkill6 = softSkill6;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public int getPreferredEmail() {
		return preferredEmail;
	}

	public void setPreferredEmail(int preferredEmail) {
		this.preferredEmail = preferredEmail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getScnUrl() {
		return scnUrl;
	}

	public void setScnUrl(String scnUrl) {
		this.scnUrl = scnUrl;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	public void setLinkedInUrl(String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}

	public String getXingUrl() {
		return xingUrl;
	}

	public void setXingUrl(String xingUrl) {
		this.xingUrl = xingUrl;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public int getShirtNumber() {
		return shirtNumber;
	}

	public void setShirtNumber(int shirtNumber) {
		this.shirtNumber = shirtNumber;
	}

	public String getShirtText() {
		return shirtText;
	}

	public void setShirtText(String shirtText) {
		this.shirtText = shirtText;
	}

	public String getShirtSize() {
		return shirtSize;
	}

	public void setShirtSize(String shirtSize) {
		this.shirtSize = shirtSize;
	}

	public String getShirtMF() {
		return shirtMF;
	}

	public void setShirtMF(String shirtMF) {
		this.shirtMF = shirtMF;
	}

	public boolean isInterestInMentorCommunicationStrategy() {
		return interestInMentorCommunicationStrategy;
	}

	public void setInterestInMentorCommunicationStrategy(boolean interestInMentorCommunicationStrategy) {
		this.interestInMentorCommunicationStrategy = interestInMentorCommunicationStrategy;
	}

	public boolean isInterestInMentorManagementModel() {
		return interestInMentorManagementModel;
	}

	public void setInterestInMentorManagementModel(boolean interestInMentorManagementModel) {
		this.interestInMentorManagementModel = interestInMentorManagementModel;
	}

	public boolean isInterestInMentorMix() {
		return interestInMentorMix;
	}

	public void setInterestInMentorMix(boolean interestInMentorMix) {
		this.interestInMentorMix = interestInMentorMix;
	}

	public boolean isInterestInOtherIdeas() {
		return interestInOtherIdeas;
	}

	public void setInterestInOtherIdeas(boolean interestInOtherIdeas) {
		this.interestInOtherIdeas = interestInOtherIdeas;
	}

	public int getHoursAvailable() {
		return hoursAvailable;
	}

	public void setHoursAvailable(int hoursAvailable) {
		this.hoursAvailable = hoursAvailable;
	}

	public String getTopicLeadRegion() {
		return topicLeadRegion;
	}

	public void setTopicLeadRegion(String topicLeadRegion) {
		this.topicLeadRegion = topicLeadRegion;
	}

	public String getTopic1() {
		return topic1;
	}

	public void setTopic1(String topic1) {
		this.topic1 = topic1;
	}

	public String getTopic1Executive() {
		return topic1Executive;
	}

	public void setTopic1Executive(String topic1Executive) {
		this.topic1Executive = topic1Executive;
	}

	public String getTopic2() {
		return topic2;
	}

	public void setTopic2(String topic2) {
		this.topic2 = topic2;
	}

	public String getTopic2Executive() {
		return topic2Executive;
	}

	public void setTopic2Executive(String topic2Executive) {
		this.topic2Executive = topic2Executive;
	}

	public String getTopic3() {
		return topic3;
	}

	public void setTopic3(String topic3) {
		this.topic3 = topic3;
	}

	public String getTopic3Executive() {
		return topic3Executive;
	}

	public void setTopic3Executive(String topic3Executive) {
		this.topic3Executive = topic3Executive;
	}

	public String getTopic4() {
		return topic4;
	}

	public void setTopic4(String topic4) {
		this.topic4 = topic4;
	}

	public String getTopic4Executive() {
		return topic4Executive;
	}

	public void setTopic4Executive(String topic4Executive) {
		this.topic4Executive = topic4Executive;
	}

	public boolean isTopicLeadInterest() {
		return topicLeadInterest;
	}

	public void setTopicLeadInterest(boolean topicLeadInterest) {
		this.topicLeadInterest = topicLeadInterest;
	}

	public String getTopicInterest() {
		return topicInterest;
	}

	public void setTopicInterest(String topicInterest) {
		this.topicInterest = topicInterest;
	}

}
