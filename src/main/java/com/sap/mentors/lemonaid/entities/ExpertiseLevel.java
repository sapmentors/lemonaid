package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="expertise_levels")
public class ExpertiseLevel {

	public static final String ADVANCE = "advance";
	public static final String EXPERT = "expert";
	
	@Id
	private String id;
    private String name;
    
	public ExpertiseLevel() {}

    public ExpertiseLevel(String id) {
    	this.id = id;
    }

    public ExpertiseLevel(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Industry[id=%d, name='%s']",
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
