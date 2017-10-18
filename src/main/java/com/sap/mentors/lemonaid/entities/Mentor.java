package com.sap.mentors.lemonaid.entities;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sap.mentors.lemonaid.annotations.SAP;
import com.sap.mentors.lemonaid.odata.authorization.ODataAuthorization;
import com.sap.mentors.lemonaid.repository.MentorRepository;
import com.sap.mentors.lemonaid.utils.types.Point;

@Entity
@Table(name="mentors")
public class Mentor {

	static MentorRepository mentorRepository;

	@Id
	@SAP(fieldGroup="Key") private String id;
	@SAP(fieldGroup="BasicInfo") private String fullName;
	@SAP(fieldGroup="BasicInfo") private String photoUrl;
	@SAP(fieldGroup="BasicInfo") @JoinColumn(name="statusId") @ManyToOne private MentorStatus statusId;
	@SAP(fieldGroup="BasicInfo") @Temporal(TemporalType.DATE) private Calendar mentorSince;

    @SAP(fieldGroup="BasicInfo") private String jobTitle;
    @SAP(fieldGroup="BasicInfo")  @Column(nullable = true ) private Boolean jobTitlePublic = false;
    @SAP(fieldGroup="BasicInfo") private String company;
     @SAP(fieldGroup="BasicInfo")  @Column(nullable = true ) private Boolean companyPublic = false;
	@SAP(fieldGroup="BasicInfo") @JoinColumn(name="relationshipToSapId") @ManyToOne private RelationshipToSap relationshipToSapId;

	@SAP(fieldGroup="BasicInfo") @Column(length = 5000) private String bio;
    @SAP(fieldGroup="BasicInfo") private String email1;
        @SAP(fieldGroup="BasicInfo")  @Column(nullable = true ) private Boolean email1Public = false;
    @SAP(fieldGroup="BasicInfo") private String email2;
        @SAP(fieldGroup="BasicInfo")  @Column(nullable = true ) private Boolean email2Public = false;

	@SAP(fieldGroup="BasicInfo") @JoinColumn(name="Language1Id") @ManyToOne private Language language1Id;
	@SAP(fieldGroup="BasicInfo") @JoinColumn(name="Language2Id") @ManyToOne private Language language2Id;
	@SAP(fieldGroup="BasicInfo") @JoinColumn(name="Language3Id") @ManyToOne private Language language3Id;

	@SAP(fieldGroup="Expertise") @JoinColumn(name="lineOfBusiness1Id") @ManyToOne private LineOfBusiness lineOfBusiness1Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="lineOfBusiness2Id") @ManyToOne private LineOfBusiness lineOfBusiness2Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="lineOfBusiness3Id") @ManyToOne private LineOfBusiness lineOfBusiness3Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="industry1Id") @ManyToOne private Industry industry1Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="industry2Id") @ManyToOne private Industry industry2Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="industry3Id") @ManyToOne private Industry industry3Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="sapExpertise1Id") @ManyToOne private SapSoftwareSolution sapExpertise1Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="sapExpertise1LevelId") @ManyToOne private ExpertiseLevel sapExpertise1LevelId;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="sapExpertise2Id") @ManyToOne private SapSoftwareSolution sapExpertise2Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="sapExpertise2LevelId") @ManyToOne private ExpertiseLevel sapExpertise2LevelId;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="sapExpertise3Id") @ManyToOne private SapSoftwareSolution sapExpertise3Id;
    @SAP(fieldGroup="Expertise") @JoinColumn(name="sapExpertise3LevelId") @ManyToOne private ExpertiseLevel sapExpertise3LevelId;
     @SAP(fieldGroup="Expertise")  @Column(nullable = true ) private Boolean softSkillsPublic = false;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="softSkill1Id") @ManyToOne private SoftSkill softSkill1Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="softSkill2Id") @ManyToOne private SoftSkill softSkill2Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="softSkill3Id") @ManyToOne private SoftSkill softSkill3Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="softSkill4Id") @ManyToOne private SoftSkill softSkill4Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="softSkill5Id") @ManyToOne private SoftSkill softSkill5Id;
	@SAP(fieldGroup="Expertise") @JoinColumn(name="softSkill6Id") @ManyToOne private SoftSkill softSkill6Id;

    @SAP(fieldGroup="Address") private String address1;
    @SAP(fieldGroup="Address")  @Column(nullable = true ) private Boolean address1Public = true;
    @SAP(fieldGroup="Address") private String address2;
    @SAP(fieldGroup="Address")  @Column(nullable = true ) private Boolean address2Public = true;
    @SAP(fieldGroup="Address") private String city;
    @SAP(fieldGroup="Address")  @Column(nullable = true ) private Boolean cityPublic = true;
    @SAP(fieldGroup="Address") private String state;
    @SAP(fieldGroup="Address")  @Column(nullable = true ) private Boolean statePublic = true;
    @SAP(fieldGroup="Address") private String zip;
    @SAP(fieldGroup="Address")  @Column(nullable = true ) private Boolean zipPublic = true;
    @SAP(fieldGroup="Address") @JoinColumn(name="countryId") @ManyToOne() private Country countryId;
    @SAP(fieldGroup="Address")  @Column(nullable = true ) private Boolean countryPublic = true;
    @SAP(fieldGroup="Address") private String phone;
    @SAP(fieldGroup="Address")  @Column(nullable = true ) private Boolean phonePublic = true;
    @SAP(fieldGroup="Address") private Double latitude;
    @SAP(fieldGroup="Address") private Double longitude;
    @SAP(fieldGroup="Address") private Double publicLatitude;
    @SAP(fieldGroup="Address") private Double publicLongitude;

    @SAP(fieldGroup="Address") @JoinColumn(name="regionId") @ManyToOne private Region regionId;

    @SAP(fieldGroup="Shirt") private String shirtNumber;
    @SAP(fieldGroup="Shirt") private String shirtText;
    @SAP(fieldGroup="Shirt") @JoinColumn(name="shirtSizeId") @ManyToOne private Size shirtSizeId;
    @SAP(fieldGroup="Shirt") @JoinColumn(name="shirtMFId") @ManyToOne private Gender shirtMFId;

    @SAP(fieldGroup="SocialMedia") private String scnUrl;
    @SAP(fieldGroup="SocialMedia") private String twitterId;
    @SAP(fieldGroup="SocialMedia") private String linkedInUrl;
    @SAP(fieldGroup="SocialMedia") private String xingUrl;
    @SAP(fieldGroup="SocialMedia") private String facebookUrl;
    @SAP(fieldGroup="SocialMedia") private String slackId;

    @SAP(fieldGroup="MentorManagement") private boolean interestInMentorCommunicationStrategy;
    @SAP(fieldGroup="MentorManagement") private boolean interestInMentorManagementModel;
    @SAP(fieldGroup="MentorManagement") private boolean interestInMentorMix;
    @SAP(fieldGroup="MentorManagement") private boolean interestInOtherIdeas;
    @SAP(fieldGroup="MentorManagement") private int hoursAvailable;

    @SAP(fieldGroup="TopicLead") @JoinColumn(name="topicLeadRegionId") @ManyToOne private Region topicLeadRegionId;
    @SAP(fieldGroup="TopicLead") @JoinColumn(name="topic1Id") @ManyToOne private Topic topic1Id;
    @SAP(fieldGroup="TopicLead") private String topic1Executive;
    @SAP(fieldGroup="TopicLead") @JoinColumn(name="topic2Id") @ManyToOne private Topic topic2Id;
    @SAP(fieldGroup="TopicLead") private String topic2Executive;
    @SAP(fieldGroup="TopicLead") @JoinColumn(name="topic3Id") @ManyToOne private Topic topic3Id;
    @SAP(fieldGroup="TopicLead") private String topic3Executive;
    @SAP(fieldGroup="TopicLead") @JoinColumn(name="topic4Id") @ManyToOne private Topic topic4Id;
    @SAP(fieldGroup="TopicLead") private String topic4Executive;
    @SAP(fieldGroup="TopicLead") private boolean topicLeadInterest;
    @SAP(fieldGroup="TopicLead") @JoinColumn(name="topicInterestId") @ManyToOne private Topic topicInterestId;

    @SAP(fieldGroup="JamBand") private boolean jambandMusician;
    @SAP(fieldGroup="JamBand") private boolean jambandLasVegas;
    @SAP(fieldGroup="JamBand") private boolean jambandBarcelona;
    @SAP(fieldGroup="JamBand") private String jambandInstrument;

    @Transient private boolean mayEdit;
    private boolean publicProfile;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="mentorId")
    private List<Attachment> attachments;
     @Column(nullable = true ) private Boolean attachmentsPublic= true;

	@Temporal(TemporalType.TIMESTAMP) private Calendar createdAt;
	private String createdBy;
	@SAP(fieldGroup="MentorManagement") @Temporal(TemporalType.TIMESTAMP) private Calendar updatedAt;
	@SAP(fieldGroup="MentorManagement") private String updatedBy;

    public Mentor() {}

    public Mentor(
    		String id, String fullName, MentorStatus status,
    		String jobTitle, String company, RelationshipToSap relationshipToSap,
    		LineOfBusiness lineOfBusiness1Id, LineOfBusiness lineOfBusiness2Id, LineOfBusiness lineOfBusiness3Id,
    		Industry industry1Id, Industry industry2Id, Industry industry3Id,
    		SapSoftwareSolution sapExpertise1Id, ExpertiseLevel sapExpertise1LevelId, SapSoftwareSolution sapExpertise2Id, ExpertiseLevel sapExpertise2LevelId, SapSoftwareSolution sapExpertise3Id, ExpertiseLevel sapExpertise3LevelId,
    		SoftSkill softSkill1Id, SoftSkill softSkill2Id, SoftSkill softSkill3Id, SoftSkill softSkill4Id, SoftSkill softSkill5Id, SoftSkill softSkill6Id,
    		String bio,
    		String email1, String email2,
    		String address, String city, String state, String zip, Country countryId, String phone,
    		Double latitude, Double longitude, Double publicLatitude, Double publicLongitude,
    		Region regionId,
    		String shirtNumber, String shirtText, Size shirtSizeId, Gender shirtMFId,
    		String scnUrl, String twitterId, String linkedInUrl, String xingUrl, String facebookUrl, String slackId,
    		boolean interestInMentorCommunicationStrategy, boolean interestInMentorManagementModel, boolean interestInMentorMix, boolean interestInOtherIdeas, int hoursAvailable,
    		Region topicLeadRegionId, Topic topic1Id, String topic1Executive, Topic topic2Id, String topic2Executive, Topic topic3Id, String topic3Executive, Topic topic4Id, String topic4Executive, boolean topicLeadInterest, Topic topicInterestId,
    		boolean jambandMusician, boolean jambandLasVegas, boolean jambandBarcelona, String jambandInstrument,
            boolean publicProfile, Boolean jobTitlePublic, Boolean companyPublic, Boolean address1Public, Boolean address2Public, Boolean cityPublic, Boolean statePublic, Boolean zipPublic, Boolean countryPublic,Boolean phonePublic, Boolean email1Public,
            Boolean email2Public, Boolean attachmentsPublic, Boolean softSkillsPublic )
    {
    	this.id = id;
        this.fullName = fullName;
        this.statusId = status;

        this.jobTitle = jobTitle;
        this.jobTitlePublic = jobTitlePublic;
        this.company = company;
        this.companyPublic = companyPublic;
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
        this.email1Public = email1Public;
        this.email2 = email2;
        this.email2Public = email2Public;

        this.address1 = address;
        this.address1Public = address1Public;
        this.address2Public = address2Public;
        this.city = city;
        this.cityPublic = cityPublic;
        this.state = state;
        this.statePublic = statePublic;
        this.zip = zip;
        this.zipPublic = zipPublic;
        this.countryId = countryId;
        this.countryPublic = countryPublic;
        this.phone = phone;
        this.phonePublic = phonePublic;

        this.latitude = latitude;
        this.longitude = longitude;

        this.publicLatitude = publicLatitude;
        this.publicLongitude = publicLongitude;

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
        this.slackId = slackId;

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

		this.jambandMusician = jambandMusician;
		this.jambandLasVegas = jambandLasVegas;
		this.jambandBarcelona = jambandBarcelona;
		this.jambandInstrument = jambandInstrument;

        this.publicProfile = publicProfile;

        this.attachmentsPublic = attachmentsPublic;
        this.softSkillsPublic = softSkillsPublic;
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

	public Calendar getMentorSince() {
		return mentorSince;
	}

	public void setMentorSince(Calendar mentorSince) {
		this.mentorSince = mentorSince;
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

	public Language getLanguage1Id() {
		return language1Id;
	}

	public void setLanguage1Id(Language language1Id) {
		this.language1Id = language1Id;
	}

	public Language getLanguage2Id() {
		return language2Id;
	}

	public void setLanguage2Id(Language language2Id) {
		this.language2Id = language2Id;
	}

	public Language getLanguage3Id() {
		return language3Id;
	}

	public void setLanguage3Id(Language language3Id) {
		this.language3Id = language3Id;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

    	public Double getPublicLatitude() {
		return publicLatitude;
	}

	public void setPublicLatitude(Double publicLatitude) {
		this.publicLatitude = publicLatitude;
	}

	public Double getPublicLongitude() {
		return publicLongitude;
	}

	public void setPublicLongitude(Double publicLongitude) {
		this.publicLongitude = publicLongitude;
	}

	public Region getRegionId() {
		return regionId;
	}

	public void setRegionId(Region regionId) {
		this.regionId = regionId;
	}

	public String getShirtNumber() {
		return shirtNumber;
	}

	public void setShirtNumber(String shirtNumber) {
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

	public String getSlackId() {
		return slackId;
	}

	public void setSlackId(String slackId) {
		this.slackId = slackId;
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

	public boolean isJambandMusician() {
		return jambandMusician;
	}

	public void setJambandMusician(boolean jambandMusician) {
		this.jambandMusician = jambandMusician;
	}

	public boolean isJambandLasVegas() {
		return jambandLasVegas;
	}

	public void setJambandLasVegas(boolean jambandLasVegas) {
		this.jambandLasVegas = jambandLasVegas;
	}

	public boolean isJambandBarcelona() {
		return jambandBarcelona;
	}

	public void setJambandBarcelona(boolean jambandBarcelona) {
		this.jambandBarcelona = jambandBarcelona;
	}

	public String getJambandInstrument() {
		return jambandInstrument;
	}

	public void setJambandInstrument(String jambandInstrument) {
		this.jambandInstrument = jambandInstrument;
	}

    public boolean isPublicProfile() {
		return publicProfile;
	}

	public void setPublicProfile(boolean publicProfile) {
		this.publicProfile = publicProfile;
	}

	public boolean isMayEdit() {
		return mayEdit;
	}

	public void setMayEdit(boolean mayEdit) {
		this.mayEdit = mayEdit;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void setLocation(Point location) {
		if (location == null) {
			this.latitude = null;
			this.longitude = null;
		} else {
			this.latitude = location.getLatitude();
			this.longitude = location.getLongitude();
		}

    }

    	public void setPublicLocation(Point location) {
		if (location == null) {
			this.publicLatitude = null;
			this.publicLongitude = null;
		} else {
			this.publicLatitude = location.getLatitude();
			this.publicLongitude = location.getLongitude();
		}

	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
    }

    public boolean getJobTitlePublic(){
        if(jobTitlePublic == null){
            jobTitlePublic = false;
        }
        return jobTitlePublic;
    }

    public void setJobTitlePublic(Boolean jobTitlePublic){
        this.jobTitlePublic = jobTitlePublic;
    }

        public boolean getCompanyPublic(){
        if(companyPublic == null){
            companyPublic = false;
        }
        return companyPublic;
    }

    public void setCompanyPublic(Boolean companyPublic){
        this.companyPublic = companyPublic;
    }
    public Boolean getAddress1Public(){
         if(address1Public == null){
            address1Public = false;
        }
        return address1Public;
    }

    public void setAddress1Public(Boolean address1Public){
        this.address1Public = address1Public;
    }
    public Boolean getAddress2Public(){
        if(address2Public == null){
            address2Public = false;
        }
        return address2Public;
    }

    public void setAddress2Public(Boolean address2Public){
        this.address2Public = address2Public;
    }

        public Boolean getCityPublic(){
         if(cityPublic == null){
            cityPublic = false;
        }
        return cityPublic;
    }

    public void setCityPublic(Boolean cityPublic){
        this.cityPublic =  cityPublic;
    }

        public Boolean getStatePublic(){
        if(statePublic == null){
            statePublic = false;
        }
        return statePublic;
    }

    public void setStatePublic(Boolean statePublic){
        this.statePublic = statePublic;
    }

        public Boolean getZipPublic(){
        if(zipPublic == null){
            zipPublic = false;
        }
        return zipPublic;
    }

    public void setZipPublic(Boolean zipPublic){
        this.zipPublic = zipPublic;
    }

        public Boolean getCountryPublic(){
         if(countryPublic == null){
            countryPublic = false;
        }
        return countryPublic;
    }

    public void setCountryPublic(Boolean countryPublic){
        this.countryPublic = countryPublic;
    }

        public Boolean getPhonePublic(){
        if(phonePublic == null){
            phonePublic = false;
        }
        return phonePublic;
    }

    public void setPhonePublic(Boolean phonePublic){
        this.phonePublic = phonePublic;
    }

            public Boolean getEmail1Public(){
        if(email1Public == null){
            email1Public = false;
        }
        return email1Public;
    }

    public void setEmail1Public(Boolean email1Public){
        this.email1Public = email1Public;
    }

            public Boolean getEmail2Public(){
        if(email2Public == null){
            email2Public = false;
        }
        return email2Public;
    }

    public void setEmail2Public(Boolean email2Public){
        this.email2Public = email2Public;
    }
            public Boolean getAttachmentsPublic(){
        if(attachmentsPublic == null){
            attachmentsPublic = false;
        }
        return attachmentsPublic;
    }

    public void setAttachmentsPublic(Boolean attachmentsPublic){
        this.attachmentsPublic = attachmentsPublic;
    }
            public Boolean getSoftSkillsPublic(){
        if(softSkillsPublic == null){
            softSkillsPublic = false;
        }
        return softSkillsPublic;
    }

    public void setSoftSkillsPublic(Boolean softSkillsPublic){
        this.softSkillsPublic = softSkillsPublic;
    }

	@PrePersist
	private void persist() {
		String userName = (String) ODataAuthorization.getThreadLocalData().get().get("UserName");
		this.createdAt = Calendar.getInstance();
		this.updatedAt = Calendar.getInstance();
		if (userName == null) {
			this.createdBy = "SYSTEM";
			this.updatedBy = "SYSTEM";
		} else {
			this.createdBy = userName;
			this.updatedBy = userName;
		}
	}

	@PreUpdate
	private void update() {
		String userName = null;
		try {
			userName = (String) ODataAuthorization.getThreadLocalData().get().get("UserName");
		} catch (RuntimeException e) {};
		if (userName != null) {
			this.updatedAt = Calendar.getInstance();
			this.updatedBy = userName;
		}
	}

}
