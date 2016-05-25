package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sizes")
public class Size {

	public static final String S = "S";
	public static final String M = "M";
	public static final String L = "L";
	public static final String XL = "XL";
	public static final String XXL = "XXL";
	public static final String XXXL = "XXXL";
	
	@Id
	private String id;
    private String name;
    
	public Size() {}

    public Size(String id) {
    	this.id = id;
    }

    public Size(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Size[id=%d, name='%s']",
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
