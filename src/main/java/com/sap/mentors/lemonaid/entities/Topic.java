package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="topics")
public class Topic {
	
	public static final String SAP_RUN_SAP = "sap_run_sap";
	public static final String HANA_CLOUD_PLATFORM = "hana_cloud_platform";
	public static final String SAP_HANA_PLATFORM = "sap_hana_platform";
	public static final String HANA_CLOUD_INTEGRATION = "hana_cloud_integration";
	public static final String SAP_HANA_VORA = "sap_hana_vora";
	public static final String PRODUCTS_INNOVATION = "products_innovation";
	public static final String CLOUD_FOR_CUSTOMER = "cloud_for_customer";
	public static final String S4HANA = "s4hana";
	public static final String ABAP_AND_WORKFLOW = "abap_and_workflow";
	public static final String GLOBAL_BUSINESS_NETWORK = "global_business_network";
	public static final String UX = "ux";
	public static final String ANALYTICS = "analytics";
	public static final String TECHNOLOGY_STRATEGY = "technology_strategy";
	public static final String SAP_SUPPORT = "sap_support";
	public static final String PLATFORM_SOLUTIONS = "platform_solutions";
	public static final String TECHNOLOGY = "technology";
	public static final String IOT = "iot";
	public static final String MOBILITY = "mobility";
	public static final String SAP_DIGITAL = "sap_digital";
	public static final String FINANCE = "finance";
	public static final String SECURITY = "security";
	public static final String SAP_PROCESS_ORCHESTRATION = "sap_process_orchestration";
	public static final String CIO_AMERICAS = "cio_americas";
	public static final String HUMAN_RESOURCES = "human_resources";
	public static final String GRC = "grc";
	public static final String CLOUD_INFRASTRUCTURE = "cloud_infrastructure";
	public static final String SAP_GCO = "sap_gco";
	public static final String HANA_HADOOP_INTEGRATION = "hana_hadoop_integration";
	public static final String SAP_DATAWAREHOUSING = "sap_datawarehousing";
	public static final String BOARDROOM_REDEFINED ="boardroom_redefined";
	public static final String ABAP_DEVELOPMENT ="abap_development";
	public static final String PREDICTIVE_ANALYTICS="predictive_analytics";
	public static final String SAP_APPLICATION_INTERFACE_FRAMEWORK="sap_application_interface_framework";
	public static final String SOLUTIONS_MANAGER="solution_manager";
	public static final String SAPUI5="sapui5";
	public static final String SAP_PORTALS="sap_portals";
	public static final String ASUG="asug";
	public static final String INDUSTRY_CLOUD="industry_cloud";
	public static final String DEVELOPMENT ="development";
	public static final String BUSINESS_ONE="business_one";
	public static final String OUTPUT_MANAGEMENT_IFBA = "output_management_ifba";
	
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
