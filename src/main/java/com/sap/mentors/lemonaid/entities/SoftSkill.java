package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="softskills")
public class SoftSkill {

	public static final String DESIGN_THINKING = "design_thinking";
	public static final String ASUG = "asug";
	public static final String DSAG = "dsag";
	public static final String INSIDETRACKS = "insidetracks";
	public static final String INTERNET_OF_THINGS = "internet_of_things";
	public static final String LEAN_METHODOLOGY = "lean_methodology";
	public static final String COMMUNICATION_SKILLS = "communication_skills";
	public static final String INTERPERSONAL_SKILLS = "interpersonal_skills";
	public static final String PROJECT_MANAGEMENT_SKILLS = "project_management_skills";
	public static final String PROCESS_IMPROVEMENT_EXPERTISE = "process_improvement_expertise";
	public static final String EMOTIONAL_INTELLIGENCE = "emotional_intelligence";
	public static final String CRITICAL_OBSERVATION_SKILLS = "critical_observation_skills";
	public static final String CONFLICT_RESOLUTION = "conflict_resolution";
	public static final String DECISIONMAKING = "decisionmaking";
	public static final String LEADERSHIP_SKILLS = "leadership_skills";
	public static final String CHANGE_MANAGEMENT = "change_management";
	
	@Id
	private String id;
    private String name;
    
	public SoftSkill() {}

    public SoftSkill(String id) {
    	this.id = id;
    }

    public SoftSkill(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "SoftSkill[id=%d, name='%s']",
                id, name);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
