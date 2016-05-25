package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="relationships")
public class RelationshipToSap {

	public static final String ANALYST = "analyst";
	public static final String CUSTOMER = "customer";
	public static final String EMPLOYEE = "employee";
	public static final String FREELANCE = "freelance";
	public static final String PARTNER = "partner";
	public static final String UNIVERSITY_ALLIANCE = "university_alliance";

	@Id
	private String id;
    private String name;
    
	public RelationshipToSap() {}

    public RelationshipToSap(String id) {
    	this.id = id;
    }

    public RelationshipToSap(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "RelationshipToSap[id=%d, name='%s']",
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