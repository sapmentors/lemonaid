package com.sap.mentors.lemonaid.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="mentors")
public class Mentor {

	@Id
	private String id;
    private String fullName;
    private String photoUrl;
    @JoinColumn(name="statusId") @ManyToOne private MentorStatus statusId;

    private String jobTitle;
    private String company;
    @JoinColumn(name="relationshipToSapId") @ManyToOne private RelationshipToSap relationshipToSapId;

    @JoinColumn(name="lineOfBusiness1Id") @ManyToOne private LineOfBusiness lineOfBusiness1Id;
    @JoinColumn(name="lineOfBusiness2Id") @ManyToOne private LineOfBusiness lineOfBusiness2Id;
    @JoinColumn(name="lineOfBusiness3Id") @ManyToOne private LineOfBusiness lineOfBusiness3Id;
    @JoinColumn(name="industry1Id") @ManyToOne private Industry industry1Id;
    @JoinColumn(name="industry2Id") @ManyToOne private Industry industry2Id;
    @JoinColumn(name="industry3Id") @ManyToOne private Industry industry3Id;
    @JoinColumn(name="sapExpertise1Id") @ManyToOne private SapSoftwareSolution sapExpertise1Id;
    @JoinColumn(name="sapExpertise1LevelId") @ManyToOne private ExpertiseLevel sapExpertise1LevelId;
    @JoinColumn(name="sapExpertise2Id") @ManyToOne private SapSoftwareSolution sapExpertise2Id;
    @JoinColumn(name="sapExpertise2LevelId") @ManyToOne private ExpertiseLevel sapExpertise2LevelId;
    @JoinColumn(name="sapExpertise3Id") @ManyToOne private SapSoftwareSolution sapExpertise3Id;
    @JoinColumn(name="sapExpertise3LevelId") @ManyToOne private ExpertiseLevel sapExpertise3LevelId;
    @JoinColumn(name="softSkill1Id") @ManyToOne private SoftSkill softSkill1Id;
    @JoinColumn(name="softSkill2Id") @ManyToOne private SoftSkill softSkill2Id;
    @JoinColumn(name="softSkill3Id") @ManyToOne private SoftSkill softSkill3Id;
    @JoinColumn(name="softSkill4Id") @ManyToOne private SoftSkill softSkill4Id;
    @JoinColumn(name="softSkill5Id") @ManyToOne private SoftSkill softSkill5Id;
    @JoinColumn(name="softSkill6Id") @ManyToOne private SoftSkill softSkill6Id;
    
    @Lob private String bio; 
    private String email1;
    private String email2;
    private int preferredEmail;
    
    private String address;
    private String city;
    private String state;
    private String zip;
    @JoinColumn(name="countryId") @ManyToOne() private Country countryId;    
    private String phone;
    private Double latitude;
    private Double longitude;
    
    @JoinColumn(name="regionId") @ManyToOne private Region regionId;
    
    private int shirtNumber;
    private String shirtText;
    @JoinColumn(name="shirtSizeId") @ManyToOne private Size shirtSizeId;
    @JoinColumn(name="shirtMFId") @ManyToOne private Gender shirtMFId;

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
    
    @JoinColumn(name="topicLeadRegionId") @ManyToOne private Region topicLeadRegionId;
    @JoinColumn(name="topic1Id") @ManyToOne private Topic topic1Id;
    private String topic1Executive;
    @JoinColumn(name="topic2Id") @ManyToOne private Topic topic2Id;
    private String topic2Executive;
    @JoinColumn(name="topic3Id") @ManyToOne private Topic topic3Id;
    private String topic3Executive;
    @JoinColumn(name="topic4Id") @ManyToOne private Topic topic4Id;
    private String topic4Executive;
    private boolean topicLeadInterest;
    @JoinColumn(name="topicInterestId") @ManyToOne private Topic topicInterestId;
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy="mentorId")
    private List<Attachment> attachments;    
    
    public Mentor() {}

    public Mentor(
    		String id, String fullName, MentorStatus status, 
    		String jobTitle, String company, RelationshipToSap relationshipToSap,
    		LineOfBusiness lineOfBusiness1Id, LineOfBusiness lineOfBusiness2Id, LineOfBusiness lineOfBusiness3Id,
    		Industry industry1Id, Industry industry2Id, Industry industry3Id,
    		SapSoftwareSolution sapExpertise1Id, ExpertiseLevel sapExpertise1LevelId, SapSoftwareSolution sapExpertise2Id, ExpertiseLevel sapExpertise2LevelId, SapSoftwareSolution sapExpertise3Id, ExpertiseLevel sapExpertise3LevelId,
    		SoftSkill softSkill1Id, SoftSkill softSkill2Id, SoftSkill softSkill3Id, SoftSkill softSkill4Id, SoftSkill softSkill5Id, SoftSkill softSkill6Id,
    		String bio,
    		String email1, String email2, int preferredEmail,
    		String address, String city, String state, String zip, Country countryId, String phone,
    		Double latitude, Double longitude,
    		Region regionId,
    		int shirtNumber, String shirtText, Size shirtSizeId, Gender shirtMFId,
    		String scnUrl, String twitterId, String linkedInUrl, String xingUrl, String facebookUrl,
    		boolean interestInMentorCommunicationStrategy, boolean interestInMentorManagementModel, boolean interestInMentorMix, boolean interestInOtherIdeas, int hoursAvailable,
    		Region topicLeadRegionId, Topic topic1Id, String topic1Executive, Topic topic2Id, String topic2Executive, Topic topic3Id, String topic3Executive, Topic topic4Id, String topic4Executive, boolean topicLeadInterest, Topic topicInterestId)
    {
    	this.id = id;
        this.fullName = fullName;
        this.statusId = status;
        
        this.jobTitle = jobTitle;
        this.company = company;
        this.relationshipToSapId = relationshipToSap;
        
        this.lineOfBusiness1Id = lineOfBusiness1Id;
        this.lineOfBusiness2Id = lineOfBusiness2Id;
        this.lineOfBusiness3Id = lineOfBusiness3Id;
        this.industry1Id = industry1Id;
        this.industry2Id = industry2Id;
        this.industry3Id = industry3Id;
        this.sapExpertise1Id = sapExpertise1Id;
        this.sapExpertise1LevelId = sapExpertise1LevelId;
        this.sapExpertise2Id = sapExpertise2Id;
        this.sapExpertise2LevelId = sapExpertise2LevelId;
        this.sapExpertise3Id = sapExpertise3Id;
        this.sapExpertise3LevelId = sapExpertise3LevelId;
        this.softSkill1Id = softSkill1Id;
        this.softSkill2Id = softSkill2Id;
        this.softSkill3Id = softSkill3Id;
        this.softSkill4Id = softSkill4Id;
        this.softSkill5Id = softSkill5Id;
        this.softSkill6Id = softSkill6Id;
        
        this.bio = bio;
        this.email1 = email1;
        this.email2 = email2;
        this.preferredEmail = 1;
        
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.countryId = countryId;
        this.phone = phone;
        
        this.latitude = latitude;
        this.longitude = longitude;
        
        this.regionId = regionId;
        
        this.shirtNumber = shirtNumber;
        this.shirtText = shirtText;
        this.shirtSizeId = shirtSizeId;
        this.shirtMFId = shirtMFId;
        
        this.scnUrl = scnUrl; 
        this.twitterId = twitterId;
        this.linkedInUrl = linkedInUrl;
        this.xingUrl = xingUrl;
        this.facebookUrl = facebookUrl;
        
        this.interestInMentorCommunicationStrategy = interestInMentorCommunicationStrategy;
        this.interestInMentorManagementModel = interestInMentorManagementModel;
        this.interestInMentorMix = interestInMentorMix;
        this.interestInOtherIdeas = interestInOtherIdeas;
        this.hoursAvailable = hoursAvailable;
        
        this.topicLeadRegionId = topicLeadRegionId;
        this.topic1Id = topic1Id;
        this.topic1Executive = topic1Executive;
        this.topic2Id = topic2Id;
        this.topic2Executive = topic2Executive;
        this.topic3Id = topic3Id;
        this.topic3Executive = topic3Executive;
        this.topic4Id = topic4Id;
        this.topic4Executive = topic4Executive;
        this.topicLeadInterest = topicLeadInterest;
        this.topicInterestId = topicInterestId;
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public MentorStatus getStatusId() {
		return statusId;
	}

	public void setStatusId(MentorStatus statusId) {
		this.statusId = statusId;
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

	public RelationshipToSap getRelationshipToSapId() {
		return relationshipToSapId;
	}

	public void setRelationshipToSapId(RelationshipToSap relationshipToSapId) {
		this.relationshipToSapId = relationshipToSapId;
	}

	public LineOfBusiness getLineOfBusiness1Id() {
		return lineOfBusiness1Id;
	}

	public void setLineOfBusiness1Id(LineOfBusiness lineOfBusiness1Id) {
		this.lineOfBusiness1Id = lineOfBusiness1Id;
	}

	public LineOfBusiness getLineOfBusiness2Id() {
		return lineOfBusiness2Id;
	}

	public void setLineOfBusiness2Id(LineOfBusiness lineOfBusiness2Id) {
		this.lineOfBusiness2Id = lineOfBusiness2Id;
	}

	public LineOfBusiness getLineOfBusiness3Id() {
		return lineOfBusiness3Id;
	}

	public void setLineOfBusiness3Id(LineOfBusiness lineOfBusiness3Id) {
		this.lineOfBusiness3Id = lineOfBusiness3Id;
	}

	public Industry getIndustry1Id() {
		return industry1Id;
	}

	public void setIndustry1Id(Industry industry1Id) {
		this.industry1Id = industry1Id;
	}

	public Industry getIndustry2Id() {
		return industry2Id;
	}

	public void setIndustry2Id(Industry industry2Id) {
		this.industry2Id = industry2Id;
	}

	public Industry getIndustry3Id() {
		return industry3Id;
	}

	public void setIndustry3Id(Industry industry3Id) {
		this.industry3Id = industry3Id;
	}

	public SapSoftwareSolution getSapExpertise1Id() {
		return sapExpertise1Id;
	}

	public void setSapExpertise1Id(SapSoftwareSolution sapExpertise1Id) {
		this.sapExpertise1Id = sapExpertise1Id;
	}

	public ExpertiseLevel getSapExpertise1LevelId() {
		return sapExpertise1LevelId;
	}

	public void setSapExpertise1LevelId(ExpertiseLevel sapExpertise1LevelId) {
		this.sapExpertise1LevelId = sapExpertise1LevelId;
	}

	public SapSoftwareSolution getSapExpertise2Id() {
		return sapExpertise2Id;
	}

	public void setSapExpertise2Id(SapSoftwareSolution sapExpertise2Id) {
		this.sapExpertise2Id = sapExpertise2Id;
	}

	public ExpertiseLevel getSapExpertise2LevelId() {
		return sapExpertise2LevelId;
	}

	public void setSapExpertise2LevelId(ExpertiseLevel sapExpertise2LevelId) {
		this.sapExpertise2LevelId = sapExpertise2LevelId;
	}

	public SapSoftwareSolution getSapExpertise3Id() {
		return sapExpertise3Id;
	}

	public void setSapExpertise3Id(SapSoftwareSolution sapExpertise3Id) {
		this.sapExpertise3Id = sapExpertise3Id;
	}

	public ExpertiseLevel getSapExpertise3LevelId() {
		return sapExpertise3LevelId;
	}

	public void setSapExpertise3LevelId(ExpertiseLevel sapExpertise3LevelId) {
		this.sapExpertise3LevelId = sapExpertise3LevelId;
	}

	public SoftSkill getSoftSkill1Id() {
		return softSkill1Id;
	}

	public void setSoftSkill1Id(SoftSkill softSkill1Id) {
		this.softSkill1Id = softSkill1Id;
	}

	public SoftSkill getSoftSkill2Id() {
		return softSkill2Id;
	}

	public void setSoftSkill2Id(SoftSkill softSkill2Id) {
		this.softSkill2Id = softSkill2Id;
	}

	public SoftSkill getSoftSkill3Id() {
		return softSkill3Id;
	}

	public void setSoftSkill3Id(SoftSkill softSkill3Id) {
		this.softSkill3Id = softSkill3Id;
	}

	public SoftSkill getSoftSkill4Id() {
		return softSkill4Id;
	}

	public void setSoftSkill4Id(SoftSkill softSkill4Id) {
		this.softSkill4Id = softSkill4Id;
	}

	public SoftSkill getSoftSkill5Id() {
		return softSkill5Id;
	}

	public void setSoftSkill5Id(SoftSkill softSkill5Id) {
		this.softSkill5Id = softSkill5Id;
	}

	public SoftSkill getSoftSkill6Id() {
		return softSkill6Id;
	}

	public void setSoftSkill6Id(SoftSkill softSkill6Id) {
		this.softSkill6Id = softSkill6Id;
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

	public Country getCountryId() {
		return countryId;
	}

	public void setCountryId(Country countryId) {
		this.countryId = countryId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Region getRegionId() {
		return regionId;
	}

	public void setRegionId(Region regionId) {
		this.regionId = regionId;
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

	public Size getShirtSizeId() {
		return shirtSizeId;
	}

	public void setShirtSizeId(Size shirtSizeId) {
		this.shirtSizeId = shirtSizeId;
	}

	public Gender getShirtMFId() {
		return shirtMFId;
	}

	public void setShirtMFId(Gender shirtMFId) {
		this.shirtMFId = shirtMFId;
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

	public Region getTopicLeadRegionId() {
		return topicLeadRegionId;
	}

	public void setTopicLeadRegionId(Region topicLeadRegionId) {
		this.topicLeadRegionId = topicLeadRegionId;
	}

	public Topic getTopic1Id() {
		return topic1Id;
	}

	public void setTopic1Id(Topic topic1Id) {
		this.topic1Id = topic1Id;
	}

	public String getTopic1Executive() {
		return topic1Executive;
	}

	public void setTopic1Executive(String topic1Executive) {
		this.topic1Executive = topic1Executive;
	}

	public Topic getTopic2Id() {
		return topic2Id;
	}

	public void setTopic2Id(Topic topic2Id) {
		this.topic2Id = topic2Id;
	}

	public String getTopic2Executive() {
		return topic2Executive;
	}

	public void setTopic2Executive(String topic2Executive) {
		this.topic2Executive = topic2Executive;
	}

	public Topic getTopic3Id() {
		return topic3Id;
	}

	public void setTopic3Id(Topic topic3Id) {
		this.topic3Id = topic3Id;
	}

	public String getTopic3Executive() {
		return topic3Executive;
	}

	public void setTopic3Executive(String topic3Executive) {
		this.topic3Executive = topic3Executive;
	}

	public Topic getTopic4Id() {
		return topic4Id;
	}

	public void setTopic4Id(Topic topic4Id) {
		this.topic4Id = topic4Id;
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

	public Topic getTopicInterestId() {
		return topicInterestId;
	}

	public void setTopicInterestId(Topic topicInterestId) {
		this.topicInterestId = topicInterestId;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

}