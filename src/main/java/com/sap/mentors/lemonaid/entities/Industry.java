package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="industries")
public class Industry {

	public static final String AEROSPACE_AND_DEFENSE = "aerospace_and_defense";
	public static final String AUTOMOTIVE = "automotive";
	public static final String BANKING = "banking";
	public static final String CHEMICALS = "chemicals";
	public static final String CONSUMER_PRODUCTS = "consumer_products";
	public static final String DEFENSE_AND_SECURITY = "defense_and_security";
	public static final String ENGINEERING_CONSTRUCTION_AND_OPERATIONS = "engineering_construction_and_operations";
	public static final String HEALTHCARE = "healthcare";
	public static final String HIGH_TECH = "high_tech";
	public static final String HIGHER_EDUCATION_AND_RESEARCH = "higher_education_and_research";
	public static final String INDUSTRIAL_MACHINERY_AND_COMPONENTS = "industrial_machinery_and_components";
	public static final String INSURANCE = "insurance";
	public static final String LIFE_SCIENCES = "life_sciences";
	public static final String MEDIA = "media";
	public static final String MILL_PRODUCTS = "mill_products";
	public static final String MINING = "mining";
	public static final String OIL_AND_GAS = "oil_and_gas";
	public static final String PROFESSIONAL_SERVICES = "professional_services";
	public static final String PUBLIC_SECTOR = "public_sector";
	public static final String RETAIL = "retail";
	public static final String SPORTS_AND_ENTERTAINMENT = "sports_and_entertainment";
	public static final String TELECOMMUNICATIONS = "telecommunications";
	public static final String TRAVEL_AND_TRANSPORTATION = "travel_and_transportation";
	public static final String UTILITIES = "utilities";
	public static final String WHOLESALE_DISTRIBUTION = "wholesale_distribution";
	
	@Id
	private String id;
    private String name;
    
	public Industry() {}

    public Industry(String id) {
    	this.id = id;
    }

    public Industry(String id, String name) {
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
