package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="config")
public class Config {
	
	@Id
	private String id;
    private String name;
    
    public Config() {}
    
    public Config(String key, String value) {
    	this.id = key;
    	this.name = value;
    }
    
    public String toString() {
        return String.format(
                "Config[id=%d, name='%s']",
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
