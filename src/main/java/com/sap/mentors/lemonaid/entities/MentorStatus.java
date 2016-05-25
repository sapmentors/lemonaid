package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mentor_status")
public class MentorStatus {

	public static final String ACTIVE = "active";
	public static final String ALUMNI = "alumni"; 
	
	@Id
	private String id;
    private String name;
    
	public MentorStatus() {}

    public MentorStatus(String id) {
    	this.id = id;
    }

    public MentorStatus(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "MentorStatus[id=%d, name='%s']",
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
