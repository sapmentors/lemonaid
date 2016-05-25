package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="regions")
public class Region {
	
	public static final String APJ = "APJ";
	public static final String EUR = "EUR";
	public static final String MEA = "MEA";
	public static final String NA = "NA";
	public static final String SA = "SA";
	
	@Id
	private String id;
    private String name;
    
	public Region() {}

    public Region(String id) {
    	this.id = id;
    }

    public Region(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Region[id=%d, name='%s']",
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
