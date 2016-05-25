package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="genders")
public class Gender {
	
	public static final String M = "M";
	public static final String F = "F";
	
	@Id
	private String id;
    private String name;
    
	public Gender() {}

    public Gender(String id) {
    	this.id = id;
    }

    public Gender(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Gender[id=%d, name='%s']",
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
