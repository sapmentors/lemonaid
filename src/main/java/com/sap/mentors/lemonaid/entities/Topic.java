package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="topics")
public class Topic {
	
	public static final String SAP_RUN_SAP = "sap_run_sap";
	public static final String HANA_CLOUD_PLATFORM = "hana_cloud_platform";
	public static final String PRODUCTS_INNOVATION = "products_innovation";
	public static final String CLOUD_FOR_CUSTOMERS = "cloud_for_customers";
	public static final String S4HANA_SAP_ACTIVATE = "s4hana_sap_activate";
	public static final String ABAP_AND_WORKFLOW = "abap_and_workflow";
	public static final String GLOBAL_BUSINESS_NETWORK = "global_business_network";
	public static final String UX_DESIGN = "ux_design";
	public static final String ANALYTICS = "analytics";
	public static final String TECHNOLOGY_STRATEGY = "technology_strategy";
	public static final String SAP_SUPPORT = "sap_support";
	public static final String PLATFORM_SOLUTIONS = "platform_solutions";
	public static final String TECHNOLOGY = "technology";
	public static final String IOT = "iot";
	
	@Id
	private String id;
    private String name;
    
	public Topic() {}

    public Topic(String id) {
    	this.id = id;
    }

    public Topic(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Topic[id=%d, name='%s']",
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
