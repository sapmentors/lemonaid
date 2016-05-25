package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="line_of_business")
public class LineOfBusiness {

	public static final String PLATFORM_AND_TECHNOLOGY = "platform_and_technology";
	public static final String ASSET_MANAGEMENT = "asset_management";
	public static final String COMMERCE = "commerce";
	public static final String FINANCE = "finance";
	public static final String HUMAN_RESOURCES = "human_resources";
	public static final String MANUFACTURING = "manufacturing";
	public static final String MARKETING = "marketing";
	public static final String ENGINEERING = "engineering";
	public static final String SALES = "sales";
	public static final String SERVICE = "service";
	public static final String SOURCING_AND_PROCUREMENT = "sourcing_and_procurement";
	public static final String SUPPLY_CHAIN = "supply_chain";
	public static final String SUSTAINABILITY = "sustainability";
	
	@Id
	private String id;
    private String name;
    
	public LineOfBusiness() {}

    public LineOfBusiness(String id) {
    	this.id = id;
    }

    public LineOfBusiness(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "LineOfBusiness[id=%d, name='%s']",
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
