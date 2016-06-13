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
    @ManyToOne private ExpertiseLevel sapExpertise1Level;
    @ManyToOne private SapSoftwareSolution sapExpertise2;
    @ManyToOne private ExpertiseLevel sapExpertise2Level;
    @ManyToOne private SapSoftwareSolution sapExpertise3;
    @ManyToOne private ExpertiseLevel sapExpertise3Level;
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
    @ManyToOne private Size shirtSize;
    @ManyToOne private Gender shirtMF;

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
    
    @ManyToOne private Region topicLeadRegion;
    @ManyToOne private Topic topic1;
    private String topic1Executive;
    @ManyToOne private Topic topic2;
    private String topic2Executive;
    @ManyToOne private Topic topic3;
    private String topic3Executive;
    @ManyToOne private Topic topic4;
    private String topic4Executive;
    private boolean topicLeadInterest;
    @ManyToOne private Topic topicInterest;
    
    public Mentor() {}

    public Mentor(
    		String id, String fullName, MentorStatus status, 
    		String jobTitle, String company, RelationshipToSap relationshipToSap,
    		LineOfBusiness lineOfBusiness1, LineOfBusiness lineOfBusiness2, LineOfBusiness lineOfBusiness3,
    		Industry industry1, Industry industry2, Industry industry3,
    		SapSoftwareSolution sapExpertise1, ExpertiseLevel sapExpertise1Level, SapSoftwareSolution sapExpertise2, ExpertiseLevel sapExpertise2Level, SapSoftwareSolution sapExpertise3, ExpertiseLevel sapExpertise3Level,
    		SoftSkill softSkill1, SoftSkill softSkill2, SoftSkill softSkill3, SoftSkill softSkill4, SoftSkill softSkill5, SoftSkill softSkill6,
    		String bio,
    		String email1, String email2, int preferredEmail,
    		String address, String city, String state, String zip, Country country, String phone,
    		Region region,
    		int shirtNumber, String shirtText, Size shirtSize, Gender shirtMF,
    		String scnUrl, String twitterId, String linkedInUrl, String xingUrl, String facebookUrl,
    		boolean interestInMentorCommunicationStrategy, boolean interestInMentorManagementModel, boolean interestInMentorMix, boolean interestInOtherIdeas, int hoursAvailable,
    		Region topicLeadRegion, Topic topic1, String topic1Executive, Topic topic2, String topic2Executive, Topic topic3, String topic3Executive, Topic topic4, String topic4Executive, boolean topicLeadInterest, Topic topicInterest)
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
        this.sapExpertise1Level = sapExpertise1Level;
        this.sapExpertise2 = sapExpertise2;
        this.sapExpertise2Level = sapExpertise2Level;
        this.sapExpertise3 = sapExpertise3;
        this.sapExpertise3Level = sapExpertise3Level;
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
        this.shirtSize = shirtSize;
        this.shirtMF = shirtMF;
        
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
        
        this.topicLeadRegion = topicLeadRegion;
        this.topic1 = topic1;
        this.topic1Executive = topic1Executive;
        this.topic2 = topic2;
        this.topic2Executive = topic2Executive;
        this.topic3 = topic3;
        this.topic3Executive = topic3Executive;
        this.topic4 = topic4;
        this.topic4Executive = topic4Executive;
        this.topicLeadInterest = topicLeadInterest;
        this.topicInterest = topicInterest;
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

	public String getStatusId() {
		return status.getId();
	}

	public void setStatusId(String statusId) {
		status = new Status(statusId);
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

	public ExpertiseLevel getSapExpertise1Level() {
		return sapExpertise1Level;
	}

	public void setSapExpertise1Level(ExpertiseLevel sapExpertise1Level) {
		this.sapExpertise1Level = sapExpertise1Level;
	}

	public SapSoftwareSolution getSapExpertise2() {
		return sapExpertise2;
	}

	public void setSapExpertise2(SapSoftwareSolution sapExpertise2) {
		this.sapExpertise2 = sapExpertise2;
	}

	public ExpertiseLevel getSapExpertise2Level() {
		return sapExpertise2Level;
	}

	public void setSapExpertise2Level(ExpertiseLevel sapExpertise2Level) {
		this.sapExpertise2Level = sapExpertise2Level;
	}

	public SapSoftwareSolution getSapExpertise3() {
		return sapExpertise3;
	}

	public void setSapExpertise3(SapSoftwareSolution sapExpertise3) {
		this.sapExpertise3 = sapExpertise3;
	}

	public ExpertiseLevel getSapExpertise3Level() {
		return sapExpertise3Level;
	}

	public void setSapExpertise3Level(ExpertiseLevel sapExpertise3Level) {
		this.sapExpertise3Level = sapExpertise3Level;
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

	public String getCountryId() {
		return country.getId();
	}

	public void setCountryId(String countryId) {
		country = new Country(countryId);
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

	public Size getShirtSize() {
		return shirtSize;
	}

	public void setShirtSize(Size shirtSize) {
		this.shirtSize = shirtSize;
	}

	public Gender getShirtMF() {
		return shirtMF;
	}

	public void setShirtMF(Gender shirtMF) {
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

	public Region getTopicLeadRegion() {
		return topicLeadRegion;
	}

	public void setTopicLeadRegion(Region topicLeadRegion) {
		this.topicLeadRegion = topicLeadRegion;
	}

	public Topic getTopic1() {
		return topic1;
	}

	public void setTopic1(Topic topic1) {
		this.topic1 = topic1;
	}

	public String getTopic1Executive() {
		return topic1Executive;
	}

	public void setTopic1Executive(String topic1Executive) {
		this.topic1Executive = topic1Executive;
	}

	public Topic getTopic2() {
		return topic2;
	}

	public void setTopic2(Topic topic2) {
		this.topic2 = topic2;
	}

	public String getTopic2Executive() {
		return topic2Executive;
	}

	public void setTopic2Executive(String topic2Executive) {
		this.topic2Executive = topic2Executive;
	}

	public Topic getTopic3() {
		return topic3;
	}

	public void setTopic3(Topic topic3) {
		this.topic3 = topic3;
	}

	public String getTopic3Executive() {
		return topic3Executive;
	}

	public void setTopic3Executive(String topic3Executive) {
		this.topic3Executive = topic3Executive;
	}

	public Topic getTopic4() {
		return topic4;
	}

	public void setTopic4(Topic topic4) {
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

	public Topic getTopicInterest() {
		return topicInterest;
	}

	public void setTopicInterest(Topic topicInterest) {
		this.topicInterest = topicInterest;
	}

	public String getLineOfBusiness1Id() {
		if (this.getLineOfBusiness1() == null) {
			return null;
		} else {
			return this.getLineOfBusiness1().getId();
		}
	}

	public void setLineOfBusiness1Id(String lineOfBusiness1Id) {
		this.lineOfBusiness1 = new LineOfBusiness(lineOfBusiness1Id);
	}

	public String getLineOfBusiness2Id() {
		if (this.getLineOfBusiness2() == null) {
			return null;
		} else {
			return this.getLineOfBusiness2().getId();
		}
	}

	public void setLineOfBusiness2Id(String lineOfBusiness2Id) {
		this.lineOfBusiness2 = new LineOfBusiness(lineOfBusiness2Id);
	}

	public String getLineOfBusiness3Id() {
		if (this.getLineOfBusiness3() == null) {
			return null;
		} else {
			return this.getLineOfBusiness3().getId();
		}
	}

	public void setLineOfBusiness3Id(String lineOfBusiness3Id) {
		this.lineOfBusiness3 = new LineOfBusiness(lineOfBusiness3Id);
	}

	public String getIndustry1Id() {
		if (this.getIndustry1() == null) {
			return null;
		} else {
			return this.getIndustry1().getId();
		}
	}

	public void setIndustry1Id(String industry1Id) {
		this.industry1 = new Industry(industry1Id);
	}

	public String getIndustry2Id() {
		if (this.getIndustry2() == null) {
			return null;
		} else {
			return this.getIndustry2().getId();
		}
	}

	public void setIndustry2Id(String industry2Id) {
		this.industry2 = new Industry(industry2Id);
	}

	public String getIndustry3Id() {
		if (this.getIndustry3() == null) {
			return null;
		} else {
			return this.getIndustry3().getId();
		}
	}

	public void setIndustry3Id(String industry3Id) {
		this.industry3 = new Industry(industry3Id);
	}

	public String getSapExpertise1Id() {
		if (this.getSapExpertise1() == null) {
			return null;
		} else {
			return this.getSapExpertise1().getId();
		}
	}

	public void setSapExpertise1Id(String sapExpertise1Id) {
		this.sapExpertise1 = new SapSoftwareSolution(sapExpertise1Id);
	}

	public String getSapExpertise1LevelId() {
		if (this.getSapExpertise1Level() == null) {
			return null;
		} else {
			return this.getSapExpertise1Level().getId();
		}
	}

	public void setSapExpertise1LevelId(String sapExpertise1LevelId) {
		this.sapExpertise1Level = new ExpertiseLevel(sapExpertise1LevelId);
	}

	public String getSapExpertise2Id() {
		if (this.getSapExpertise2() == null) {
			return null;
		} else {
			return this.getSapExpertise2().getId();
		}
	}

	public void setSapExpertise2Id(String sapExpertise2Id) {
		this.sapExpertise2 = new SapSoftwareSolution(sapExpertise2Id);
	}

	public String getSapExpertise2LevelId() {
		if (this.getSapExpertise2Level() == null) {
			return null;
		} else {
			return this.getSapExpertise2Level().getId();
		}
	}

	public void setSapExpertise2LevelId(String sapExpertise2LevelId) {
		this.sapExpertise2Level = new ExpertiseLevel(sapExpertise2LevelId);
	}

	public String getSapExpertise3Id() {
		if (this.getSapExpertise3() == null) {
			return null;
		} else {
			return this.getSapExpertise3().getId();
		}
	}

	public void setSapExpertise3Id(String sapExpertise3Id) {
		this.sapExpertise3 = new SapSoftwareSolution(sapExpertise3Id);
	}

	public String getSapExpertise3LevelId() {
		if (this.getSapExpertise3Level() == null) {
			return null;
		} else {
			return this.getSapExpertise3Level().getId();
		}
	}

	public void setSapExpertise3LevelId(String sapExpertise3LevelId) {
		this.sapExpertise3Level = new ExpertiseLevel(sapExpertise3LevelId);
	}

	public String getSoftSkill1Id() {
		if (this.getSoftSkill1() == null) {
			return null;
		} else {
			return this.getSoftSkill1().getId();
		}
	}

	public void setSoftSkill1Id(String softSkill1Id) {
		this.softSkill1 = new SoftSkill(softSkill1Id);
	}

	public String getSoftSkill2Id() {
		if (this.getSoftSkill2() == null) {
			return null;
		} else {
			return this.getSoftSkill2().getId();
		}
	}

	public void setSoftSkill2Id(String softSkill2Id) {
		this.softSkill2 = new SoftSkill(softSkill2Id);
	}

	public String getSoftSkill3Id() {
		if (this.getSoftSkill3() == null) {
			return null;
		} else {
			return this.getSoftSkill3().getId();
		}
	}

	public void setSoftSkill3Id(String softSkill3Id) {
		this.softSkill3 = new SoftSkill(softSkill3Id);
	}

	public String getSoftSkill4Id() {
		if (this.getSoftSkill4() == null) {
			return null;
		} else {
			return this.getSoftSkill4().getId();
		}
	}

	public void setSoftSkill4Id(String softSkill4Id) {
		this.softSkill4 = new SoftSkill(softSkill4Id);
	}

	public String getSoftSkill5Id() {
		if (this.getSoftSkill5() == null) {
			return null;
		} else {
			return this.getSoftSkill5().getId();
		}
	}

	public void setSoftSkill5Id(String softSkill5Id) {
		this.softSkill5 = new SoftSkill(softSkill5Id);
	}

	public String getSoftSkill6Id() {
		if (this.getSoftSkill6() == null) {
			return null;
		} else {
			return this.getSoftSkill6().getId();
		}
	}

	public void setSoftSkill6Id(String softSkill6Id) {
		this.softSkill6 = new SoftSkill(softSkill6Id);
	}

	public String getRegionId() {
		if (this.getRegion() == null) {
			return null;
		} else {
			return this.getRegion().getId();
		}
	}

	public void setRegionId(String regionId) {
		this.region = new Region(regionId);
	}

	public String getShirtSizeId() {
		if (this.getShirtSize() == null) {
			return null;
		} else {
			return this.getShirtSize().getId();
		}
	}

	public void setShirtSizeId(String shirtSizeId) {
		this.shirtSize = new Size(shirtSizeId);
	}

	public String getShirtMFId() {
		if (this.getShirtMF() == null) {
			return null;
		} else {
			return this.getShirtMF().getId();
		}
	}

	public void setShirtMFId(String shirtMFId) {
		this.shirtMF = new Gender(shirtMFId);
	}

	public String getTopicLeadRegionId() {
		if (this.getTopicLeadRegion() == null) {
			return null;
		} else {
			return this.getTopicLeadRegion().getId();
		}
	}

	public void setTopicLeadRegionId(String topicLeadRegionId) {
		this.topicLeadRegion = new Region(topicLeadRegionId);
	}

	public String getTopic1Id() {
		if (this.getTopic1() == null) {
			return null;
		} else {
			return this.getTopic1().getId();
		}
	}

	public void setTopic1Id(String topic1Id) {
		this.topic1 = new Topic(topic1Id);
	}

	public String getTopic2Id() {
		if (this.getTopic2() == null) {
			return null;
		} else {
			return this.getTopic2().getId();
		}
	}

	public void setTopic2Id(String topic2Id) {
		this.topic2 = new Topic(topic2Id);
	}

	public String getTopic3Id() {
		if (this.getTopic3() == null) {
			return null;
		} else {
			return this.getTopic3().getId();
		}
	}

	public void setTopic3Id(String topic3Id) {
		this.topic3 = new Topic(topic3Id);
	}

	public String getTopic4Id() {
		if (this.getTopic4() == null) {
			return null;
		} else {
			return this.getTopic4().getId();
		}
	}

	public void setTopic4Id(String topic4Id) {
		this.topic4 = new Topic(topic4Id);
	}

	public String getTopicInterestId() {
		if (this.getTopicInterest() == null) {
			return null;
		} else {
			return this.getTopicInterest().getId();
		}
	}

	public void setTopicInterestId(String topicInterestId) {
		this.topicInterest = new Topic(topicInterestId);
	}

}