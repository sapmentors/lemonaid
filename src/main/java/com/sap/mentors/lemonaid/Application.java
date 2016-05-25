package com.sap.mentors.lemonaid;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.sap.mentors.lemonaid.entities.Country;
import com.sap.mentors.lemonaid.entities.ExpertiseLevel;
import com.sap.mentors.lemonaid.entities.Industry;
import com.sap.mentors.lemonaid.entities.LineOfBusiness;
import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.entities.MentorStatus;
import com.sap.mentors.lemonaid.entities.Region;
import com.sap.mentors.lemonaid.entities.RelationshipToSap;
import com.sap.mentors.lemonaid.entities.SapSoftwareSolution;
import com.sap.mentors.lemonaid.entities.SoftSkill;
import com.sap.mentors.lemonaid.repository.CountryRepository;
import com.sap.mentors.lemonaid.repository.ExpertiseLevelRepository;
import com.sap.mentors.lemonaid.repository.IndustryRepository;
import com.sap.mentors.lemonaid.repository.LineOfBusinessRepository;
import com.sap.mentors.lemonaid.repository.MentorRepository;
import com.sap.mentors.lemonaid.repository.MentorStatusRepository;
import com.sap.mentors.lemonaid.repository.RegionRepository;
import com.sap.mentors.lemonaid.repository.RelationshipRepository;
import com.sap.mentors.lemonaid.repository.SapSoftwareSolutionRepository;
import com.sap.mentors.lemonaid.repository.SoftSkillRepository;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	// TODO: Needs to be removed in final version: takes care of some sample data and population of some master data
	@Bean
	public CommandLineRunner initDatabase(
			final MentorRepository mentorRepository,
			final MentorStatusRepository mentorStatusRepository,
			final RelationshipRepository relationshipToSapRepository,
			final LineOfBusinessRepository lineOfBusinessRepository,
			final IndustryRepository industryRepository,
			final SapSoftwareSolutionRepository sapSoftwareSolutionRepository,
			final ExpertiseLevelRepository expertiseLevelRepository,
			final SoftSkillRepository softSkillRepository,
			final CountryRepository countryRepository,
			final RegionRepository regionRepository
		) {

		return new CommandLineRunner() {
			public void run(String... args) throws Exception {

				if (mentorStatusRepository.count() == 0) {
					log.info("Mentor status table is still empty. Prepopulating it");
					mentorStatusRepository.save(new MentorStatus(MentorStatus.ACTIVE, "Active mentor"));
					mentorStatusRepository.save(new MentorStatus(MentorStatus.ALUMNI, "Alumni"));
				}

				if (relationshipToSapRepository.count() == 0) {
					log.info("Relationships table is still empty. Prepopulating it");
					relationshipToSapRepository.save(new RelationshipToSap(RelationshipToSap.ANALYST, "Analyst / Press"));
					relationshipToSapRepository.save(new RelationshipToSap(RelationshipToSap.CUSTOMER, "Customer"));
					relationshipToSapRepository.save(new RelationshipToSap(RelationshipToSap.EMPLOYEE, "Employee"));
					relationshipToSapRepository.save(new RelationshipToSap(RelationshipToSap.FREELANCE, "Freelance"));
					relationshipToSapRepository.save(new RelationshipToSap(RelationshipToSap.PARTNER, "Partner"));
					relationshipToSapRepository.save(new RelationshipToSap(RelationshipToSap.UNIVERSITY_ALLIANCE, "University Alliance"));
				}

				if (lineOfBusinessRepository.count() == 0) {
					log.info("Lines of business table is still empty. Prepopulating it");
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.PLATFORM_AND_TECHNOLOGY, "Platform and Technology"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.ASSET_MANAGEMENT, "Asset Management"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.COMMERCE, "Commerce"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.FINANCE, "Finance"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.HUMAN_RESOURCES, "Finance"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.MANUFACTURING, "Manufacturing"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.MARKETING, "Marketing"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.ENGINEERING, "R&D / Engineering"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.SALES, "Sales"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.SERVICE, "Service"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.SOURCING_AND_PROCUREMENT, "Sourcing and Procurement"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.SUPPLY_CHAIN, "Supply Chain"));
					lineOfBusinessRepository.save(new LineOfBusiness(LineOfBusiness.SUSTAINABILITY, "Sustainability"));
				}

				if (industryRepository.count() == 0) {
					log.info("Industries table is still empty. Prepopulating it");
					industryRepository.save(new Industry(Industry.AEROSPACE_AND_DEFENSE, "Aerospace and Defense"));
					industryRepository.save(new Industry(Industry.AUTOMOTIVE, "Automotive"));
					industryRepository.save(new Industry(Industry.BANKING, "Banking"));
					industryRepository.save(new Industry(Industry.CHEMICALS, "Chemicals"));
					industryRepository.save(new Industry(Industry.CONSUMER_PRODUCTS, "Consumer Products"));
					industryRepository.save(new Industry(Industry.DEFENSE_AND_SECURITY, "Defense and Security"));
					industryRepository.save(new Industry(Industry.ENGINEERING_CONSTRUCTION_AND_OPERATIONS, "Engineering, Construction, and Operations"));
					industryRepository.save(new Industry(Industry.HEALTHCARE, "Healthcare"));
					industryRepository.save(new Industry(Industry.HIGH_TECH, "High Tech"));
					industryRepository.save(new Industry(Industry.HIGHER_EDUCATION_AND_RESEARCH, "Higher Education and Research"));
					industryRepository.save(new Industry(Industry.INDUSTRIAL_MACHINERY_AND_COMPONENTS, "Industrial Machinery and Components"));
					industryRepository.save(new Industry(Industry.INSURANCE, "Insurance"));
					industryRepository.save(new Industry(Industry.LIFE_SCIENCES, "Life Sciences"));
					industryRepository.save(new Industry(Industry.MEDIA, "Media"));
					industryRepository.save(new Industry(Industry.MILL_PRODUCTS, "Mill Products"));
					industryRepository.save(new Industry(Industry.MINING, "Mining"));
					industryRepository.save(new Industry(Industry.OIL_AND_GAS, "Oil and Gas"));
					industryRepository.save(new Industry(Industry.PROFESSIONAL_SERVICES, "Professional Services"));
					industryRepository.save(new Industry(Industry.PUBLIC_SECTOR, "Public Sector"));
					industryRepository.save(new Industry(Industry.RETAIL, "Retail"));
					industryRepository.save(new Industry(Industry.SPORTS_AND_ENTERTAINMENT, "Sports and Entertainment"));
					industryRepository.save(new Industry(Industry.TELECOMMUNICATIONS, "Telecommunications"));
					industryRepository.save(new Industry(Industry.TRAVEL_AND_TRANSPORTATION, "Travel and Transportation"));
					industryRepository.save(new Industry(Industry.UTILITIES, "Utilities"));
					industryRepository.save(new Industry(Industry.WHOLESALE_DISTRIBUTION, "Wholesale Distribution"));
				}
				
				if (sapSoftwareSolutionRepository.count() == 0) {
					log.info("Sap software solutions table is still empty. Prepopulating it");
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ACCELERATED_APPLICATION_DELIVERY, "Accelerated application delivery"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.APPLICATION_DEVELOPMENT_PLATFORM, "Application Development Platform"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.APPLICATION_LIFECYCLE_MANAGEMENT, "Application Lifecycle Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.APPLICATIONS_POWERED_BY_SAP_HANA, "Applications (powered by SAP HANA)"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_CLOUD_INTEGRATION, "Ariba Cloud Integration"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_CONTRACT_MANAGEMENT, "Ariba Contract Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_NETWORK, "Ariba Network"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_NETWORK_FINANCE, "Ariba Network Finance"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_NETWORK_INTEGRATION_FOR_SAP_BUSINESS_SUITE, "Ariba Network Integration for SAP Business Suite"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_NETWORK_PROCUREMENT, "Ariba Network Procurement"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_NETWORK_SOURCING, "Ariba Network Sourcing, "));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_PROCUREMENT, "Ariba Procurement"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ARIBA_SOURCING, "Ariba Sourcing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.BIG_DATA_ANALYTICS, "Big Data Analytics"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.BIG_DATA_APPLICATIONS, "Big Data Applications"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.BIG_DATA_PLATFORM, "Big Data Platform"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.BIG_DATA_SERVICES, "Big Data Services"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.BUSINESS_INTELLIGENCE, "Business Intelligence"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.BUSINESS_NETWORKS, "Business Networks"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.CHOAI_ROUTE_MANAGEMENT_LOCALIZATION_FOR_JAPAN, "Choai route management, localization for Japan"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.COLLABORATION_TOOLS, "Collaboration Tools"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.COLLABORATIVE_QUOTE_TO_CASH, "Collaborative Quote to Cash"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.CONCUR_TRAVEL_AND_EXPENSE_MANAGEMENT, "Concur Travel and Expense Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.CONSUMER_INSIGHTS, "Consumer Insights"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.CUSTOMER_ENGAGEMENT_AND_CRM, "CUSTOMER ENGAGEMENT AND CRM"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.DATA_MANAGEMENT, "Data Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.DATA_WAREHOUSING, "Data Warehousing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ENTERPRISE_INFORMATION_MANAGEMENT, "Enterprise Information Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ENTERPRISE_MOBILITY_MANAGEMENT, "Enterprise Mobility Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ENTERPRISE_PERFORMANCE_MANAGEMENT, "Enterprise Performance Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.ENTERPRISE_TECHNOLOGY, "Enterprise Technology"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.FIELDGLASS_FLEXIBLE_LABOR_MANAGEMENT, "Fieldglass Flexible Labor Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.GOVERNANCE_RISK_AND_COMPLIANCE, "Governance, Risk and Compliance"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.INMEMORY_TECHNOLOGY, "In-Memory Technology"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.IT_INFRASTRUCTURE_MANAGEMENT, "IT Infrastructure Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.LOGISTICS_ADDON_FOR_IMPORTEXPORT_LOCALIZATION_FOR_INDIA, "Logistics add-on for import/export, localization for India"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MANAGED_MOBILITY, "Managed Mobility"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MANAGER_SELFSERVICES_ADDON, "Manager self-services add-on"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MASTER_DATA_MANAGEMENT_FOR_COMMERCE, "Master Data Management for Commerce "));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MIDDLEWARE, "Middleware"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MOBILE_ENTERPRISE_SERVICES, "Mobile Enterprise Services"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MOBILE_INDUSTRY_APPS, "Mobile Industry Apps"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MOBILE_LINE_OF_BUSINESS_APPS, "Mobile Line of Business Apps"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MOBILE_OPERATOR_SERVICES, "Mobile Operator Services"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.MOBILE_TECHNOLOGY, "Mobile Technology"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.OMNICHANNEL_COMMERCE_MANAGEMENT, "Omni-Channel Commerce Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.PARTNER_MANAGED_CLOUD, "Partner Managed Cloud"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.PREDICTIVE_ANALYTICS, "Predictive Analytics"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.S4HANA, "S/4HANA"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SALES_FORCE_AUTOMATION, "Sales Force Automation"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SALES_PERFORMANCE_MANAGEMENT, "Sales Performance Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ACCELERATED_TRADE_PROMOTION_PLANNING, "SAP Accelerated Trade Promotion Planning"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ACCESS_APPROVER, "SAP Access Approver"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ACCESS_CONTROL, "SAP Access Control"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ACCESS_VIOLATION_MANAGEMENT_BY_GREENLIGHT, "SAP Access Violation Management by Greenlight"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ADAPTIVE_SERVER_ENTERPRISE, "SAP Adaptive Server Enterprise"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ADDRESS_DIRECTORIES, "SAP Address Directories"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ADVANCED_PLANNING_AND_OPTIMIZATION, "SAP Advanced Planning and Optimization"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ADVANCED_TRACK_AND_TRACE_FOR_PHARMACEUTICALS, "SAP Advanced Track and Trace for Pharmaceuticals"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_AFARIA, "SAP Afaria"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_AGRICULTURAL_CONTRACT_MANAGEMENT, "SAP Agricultural Contract Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ANYWHERE, "SAP Anywhere"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_API_MANAGEMENT, "SAP API Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_APPAREL_AND_FOOTWEAR, "SAP Apparel and Footwear"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_APPLICATION_INTERFACE_FRAMEWORK, "SAP Application Interface Framework"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_APPLICATION_VISUALIZATION_BY_IRISE, "SAP Application Visualization by iRise"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_AR_SERVICE_TECHNICIAN, "SAP AR Service Technician"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_AR_WAREHOUSE_PICKER, "SAP AR Warehouse Picker"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ARCHIVING_AND_DOCUMENT_ACCESS_BY_OPENTEXT, "SAP Archiving and Document Access by OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ASSESSMENT_MANAGEMENT_BY_QUESTIONMARK, "SAP Assessment Management by Questionmark"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ASSET_LIFECYCLE_ACCOUNTING, "SAP Asset Lifecycle Accounting"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ASSET_RETIREMENT_OBLIGATION_MANAGEMENT, "SAP Asset Retirement Obligation Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ASSURANCE_AND_COMPLIANCE_SOFTWARE, "SAP Assurance and Compliance Software"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_AUGMENTED_REALITY_APPS, "SAP Augmented Reality Apps"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BANK_ANALYZER, "SAP Bank Analyzer"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BULK_FUEL_MANAGEMENT, "SAP Bulk Fuel Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESS_ALLINONE, "SAP Business All-in-One"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESS_BYDESIGN, "SAP Business ByDesign"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESS_ONE, "SAP Business One"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESS_ONE_CLOUD, "SAP Business One Cloud"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESS_PLANNING_AND_CONSOLIDATION, "SAP Business Planning and Consolidation"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESS_PROCESS_AUTOMATION_BY_REDWOOD, "SAP Business Process Automation by Redwood"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESS_WAREHOUSE_ACCELERATOR, "SAP Business Warehouse Accelerator"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESSOBJECTS_ANALYSIS_EDITION_FOR_MICROSOFT_OFFICE, "SAP BusinessObjects Analysis, edition for Microsoft Office"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESSOBJECTS_BUSINESS_INTELLIGENCE, "SAP BusinessObjects Business Intelligence"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESSOBJECTS_DASHBOARDS, "SAP BusinessObjects Dashboards"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESSOBJECTS_DESIGN_STUDIO, "SAP BusinessObjects Design Studio"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_BUSINESSOBJECTS_EXPLORER, "SAP BusinessObjects Explorer"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CAPITAL_YIELD_TAX_MANAGEMENT, "SAP Capital Yield Tax Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CASH_FLOW_MANAGEMENT_FOR_BANKING, "SAP Cash Flow Management for Banking"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CLOUD_APPLIANCE_LIBRARY, "SAP Cloud Appliance Library"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CLOUD_APPLICATIONS_STUDIO, "SAP Cloud Applications Studio"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CLOUD_FOR_ANALYTICS, "SAP Cloud for Analytics"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CLOUD_FOR_CUSTOMER_CRM_CLOUD, "SAP Cloud for Customer (CRM cloud)"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CLOUD_FOR_TRAVEL_AND_EXPENSE, "SAP Cloud for Travel and Expense"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CLOUD_IDENTITY, "SAP Cloud Identity"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_COLLABORATION_TOOLS, "SAP Collaboration Tools"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_COMMERCIAL_PROJECT_MANAGEMENT, "SAP Commercial Project Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_COMMON_AREA_MAINTENANCE_EXPENSE_RECOVERY, "SAP Common Area Maintenance Expense Recovery"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_COMMUNICATION_CENTER_BY_ANCILE, "SAP Communication Center by ANCILE"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_COMPLEX_ASSEMBLY_MANUFACTURING, "SAP Complex Assembly Manufacturing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_COMPLEX_MANUFACTURING_ACCELERATOR, "SAP Complex Manufacturing Accelerator"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_COMPOSITION_ENVIRONMENT, "SAP Composition Environment"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONFIGURE_PRICE_AND_QUOTE, "SAP Configure, Price, and Quote"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONNECTED_LOGISTICS, "SAP Connected Logistics"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONNECTED_MANUFACTURING, "SAP Connected Manufacturing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONSUMER_INSIGHT_365, "SAP Consumer Insight 365"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONTACT_CENTER, "SAP Contact Center"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONTENT_MANAGEMENT_FOR_MICROSOFT_SHAREPOINT_BY_OPENTEXT, "SAP Content Management for Microsoft SharePoint by OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONVERGENT_CHARGING, "SAP Convergent Charging"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONVERGENT_MEDIATION_BY_DIGITALROUTE, "SAP Convergent Mediation by DigitalRoute"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CONVERGENT_PRICING_SIMULATION, "SAP Convergent Pricing Simulation"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CPROJECT_SUITE, "SAP cProject Suite"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CRM_SERVICE_MANAGER, "SAP CRM Service Manager"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CROSS_CHANNEL_ORDER_MANAGEMENT_FOR_RETAIL, "SAP Cross Channel Order Management for Retail"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CRYSTAL_REPORTS, "SAP Crystal Reports"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CRYSTAL_SERVER, "SAP Crystal Server"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CUSTOMER_ACTIVITY_REPOSITORY, "SAP Customer Activity Repository"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CUSTOMER_BRIEFING, "SAP Customer Briefing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CUSTOMER_CHECKOUT, "SAP Customer Checkout"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CUSTOMER_FINANCIAL_FACTSHEET, "SAP Customer Financial Factsheet"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_CUSTOMER_RELATIONSHIP_MANAGEMENT_CRM_ONPREMISE, "SAP Customer Relationship Management (CRM on-premise)"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DATA_MAINTENANCE_FOR_SAP_ERP_BY_VISTEX, "SAP Data Maintenance for SAP ERP by Vistex"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DATA_QUALITY_MANAGEMENT, "SAP Data Quality Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DATA_SERVICES, "SAP Data Services"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DECISION_SERVICE_MANAGEMENT, "SAP Decision Service Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DEMAND_SIGNAL_MANAGEMENT, "SAP Demand Signal Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DEPOSITS_MANAGEMENT, "SAP Deposits Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DIGITAL_ASSET_MANAGEMENT_BY_OPENTEXT, "SAP Digital Asset Management by OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DIRECT_STORE_DELIVERY, "SAP Direct Store Delivery"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DISCLOSURE_MANAGEMENT, "SAP Disclosure Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DOCUMENT_BUILDER, "SAP Document Builder"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DOCUMENT_PRESENTMENT_BY_OPENTEXT, "SAP Document Presentment by OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_DYNAMIC_AUTHORIZATION_MANAGEMENT_BY_NEXTLABS, "SAP Dynamic Authorization Management by NextLabs"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EAM_AND_SERVICE_MOBILE_APP_SDK, "SAP EAM and service mobile app SDK"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EHS_REGULATORY_CONTENT, "SAP EHS Regulatory Content"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EHS_SAFETY_ISSUE, "SAP EHS Safety Issue"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ELECTRONIC_INVOICING_FOR_BRAZIL, "SAP Electronic Invoicing for Brazil"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EMPLOYEE_FILE_MANAGEMENT_BY_OPENTEXT, "SAP Employee File Management by OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EMR_UNWIRED, "SAP EMR Unwired"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENERGY_DATA_MANAGEMENT, "SAP Energy Data Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENERGY_PORTFOLIO_MANAGEMENT, "SAP Energy Portfolio Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENGINEERING_CONTROL_CENTER, "SAP Engineering Control Center"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENHANCED_MAINTENANCE_AND_SERVICE_PLANNING, "SAP Enhanced Maintenance and Service Planning"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENTERPRISE_DEMAND_SENSING, "SAP Enterprise Demand Sensing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENTERPRISE_INVENTORY_AND_SERVICELEVEL_OPTIMIZATION, "SAP Enterprise Inventory and Service-Level Optimization"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENTERPRISE_LEARNING, "SAP Enterprise Learning"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENTERPRISE_MODELING_BY_SOFTWARE_AG, "SAP Enterprise Modeling by Software AG"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENTERPRISE_PORTAL, "SAP Enterprise Portal"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENTERPRISE_PROJECT_CONNECTION, "SAP Enterprise Project Connection"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENTERPRISE_THREAT_DETECTION, "SAP Enterprise Threat Detection"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENVIRONMENT_HEALTH_AND_SAFETY_MANAGEMENT, "SAP Environment, Health, and Safety Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ENVIRONMENTAL_COMPLIANCE, "SAP Environmental Compliance"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ERP, "SAP ERP"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EVENT_MANAGEMENT, "SAP Event Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EVENT_STREAM_PROCESSOR, "SAP Event Stream Processor"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EVENT_TICKETING, "SAP Event Ticketing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EXTENDED_DIAGNOSTICS_BY_CA, "SAP Extended Diagnostics by CA"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EXTENDED_ENTERPRISE_CONTENT_MANAGEMENT_BY_OPENTEXT, "SAP Extended Enterprise Content Management by OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_EXTENDED_WAREHOUSE_MANAGEMENT, "SAP Extended Warehouse Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FASHION_MANAGEMENT, "SAP Fashion Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FIELDGLASS_VENDOR_MANAGEMENT_SYSTEM, "SAP Fieldglass Vendor Management System"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FILE_LIFECYCLE_MANAGEMENT, "SAP File Lifecycle Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FINANCIAL_CLOSING_COCKPIT, "SAP Financial Closing cockpit"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FINANCIAL_CONSOLIDATION, "SAP Financial Consolidation"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FINANCIAL_INFORMATION_MANAGEMENT, "SAP Financial Information Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FINANCIAL_SERVICES_NETWORK, "SAP Financial Services Network"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FINANCIAL_SUPPLY_CHAIN_MANAGEMENT, "SAP Financial Supply Chain Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FIORI, "SAP Fiori"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FIORY, "SAP Fiory"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FLEXIBLE_SOLUTION_BILLING, "SAP Flexible Solution Billing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FORECASTING_AND_REPLENISHMENT_FOR_RETAIL, "SAP Forecasting and Replenishment for Retail"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FORTIFY_BY_HP, "SAP Fortify by HP"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_FUNDRAISING_MANAGEMENT, "SAP Fundraising Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_GATEWAY, "SAP Gateway"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_GLOBAL_BATCH_TRACEABILITY, "SAP Global Batch Traceability"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_GLOBAL_TRADE_SERVICES, "SAP Global Trade Services"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA, "SAP HANA"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_CLOUD_INTEGRATION, "SAP HANA Cloud Integration"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_CLOUD_PLATFORM, "SAP HANA Cloud Platform"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_CLOUD_PLATFORM_FOR_THE_INTERNET_OF_THINGS, "SAP HANA Cloud Platform for the Internet of Things"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_CLOUD_PORTAL, "SAP HANA Cloud Portal"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_ENTERPRISE_CLOUD, "SAP HANA Enterprise Cloud"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_ENTERPRISE_CLOUD_CONSULTING_SERVICES, "SAP HANA Enterprise Cloud Consulting Services"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_LIVE, "SAP HANA Live"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_VORA, "SAP HANA Vora"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HYBRIS_COMMERCE_SUITE, "SAP hybris Commerce Suite"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_HYBRIS_MARKETING, "SAP hybris Marketing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_IDENTITY_ANALYTICS, "SAP Identity Analytics"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_IDENTITY_MANAGEMENT, "SAP Identity Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INCENTIVE_ADMINISTRATION_BY_VISTEX, "SAP Incentive Administration by Vistex "));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INFOMAKER, "SAP InfoMaker"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INFORMATION_STEWARD, "SAP Information Steward"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INNOVATION_MANAGEMENT, "SAP Innovation Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INSTORE_PRODUCT_LOOKUP, "SAP In-Store Product Lookup"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INTEGRATED_BUSINESS_PLANNING, "SAP Integrated Business Planning"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INTERCOMPANY, "SAP Intercompany"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INTERCOMPANY_DATA_EXCHANGE, "SAP Intercompany Data Exchange"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INVENTORY_MANAGER, "SAP Inventory Manager"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_INVOICE_MANAGEMENT_BY_OPENTEXT, "SAP Invoice Management by OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_IQ, "SAP IQ"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_IT_INFRASTRUCTURE_MANAGEMENT, "SAP IT Infrastructure Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_JAM, "SAP Jam"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_JAM_COMMUNITIES, "SAP Jam Communities"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_KNOWLEDGE_ACCELERATION, "SAP Knowledge Acceleration"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_KNOWLEDGE_CENTRAL_BY_MINDTOUCH, "SAP Knowledge Central by MindTouch"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_LANDSCAPE_TRANSFORMATION, "SAP Landscape Transformation"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_LANDSCAPE_VIRTUALIZATION_MANAGEMENT, "SAP Landscape Virtualization Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_LEASE_ADMINISTRATION_BY_NAKISA, "SAP Lease Administration by Nakisa"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_LIQUIDITY_RISK_MANAGEMENT, "SAP Liquidity Risk Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_LOADRUNNER_BY_HP, "SAP LoadRunner by HP"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_LOANS_MANAGEMENT, "SAP Loans Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_LUMIRA, "SAP Lumira"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_LUMIRA_CLOUD, "SAP Lumira Cloud"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MANAGEMENT_OF_CHANGE, "SAP Management of Change"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MANAGER_INSIGHT, "SAP Manager Insight"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MANUFACTURING_EXECUTION, "SAP Manufacturing Execution"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MANUFACTURING_INTEGRATION_AND_INTELLIGENCE, "SAP Manufacturing Integration and Intelligence"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MASTER_DATA_GOVERNANCE, "SAP Master Data Governance"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MILITARY_DATA_EXCHANGE, "SAP Military Data Exchange"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MOBILE_APP_PROTECTION_BY_MOCANA, "SAP Mobile App Protection by Mocana"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MOBILE_ASSET_MANAGEMENT, "SAP Mobile Asset Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MOBILE_DEFENSE_SECURITY, "SAP Mobile Defense & Security"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MOBILE_DOCUMENTS, "SAP Mobile Documents"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MOBILE_PLATFORM, "SAP Mobile Platform"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MOBILE_SECURE, "SAP Mobile Secure"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MULTICHANNEL_FOUNDATION, "SAP Multichannel Foundation"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_MULTIRESOURCE_SCHEDULING, "SAP Multiresource Scheduling"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_NETWEAVER, "SAP NetWeaver"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_NETWEAVER_MASTER_DATA_MANAGEMENT, "SAP NetWeaver Master Data Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_NETWEAVER_MOBILE, "SAP NetWeaver Mobile"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_NETWORKED_LOGISTICS_HUB, "SAP Networked Logistics Hub"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_NOTES_MANAGEMENT, "SAP Notes Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_OPEN_SERVER, "SAP Open Server"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_OPEN_SWITCH, "SAP Open Switch"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_OPERATIONAL_PROCESS_INTELLIGENCE, "SAP Operational Process Intelligence"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_ORGANIZATIONAL_VISUALIZATION_BY_NAKISA, "SAP Organizational Visualization by Nakisa"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_OVERALL_EQUIPMENT_EFFECTIVENESS_MANAGEMENT, "SAP Overall Equipment Effectiveness Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PAYBACKS_AND_CHARGEBACKS_BY_VISTEX, "SAP Paybacks and Chargebacks by Vistex"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PAYMENT_APPROVALS, "SAP Payment Approvals"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PAYMENT_ENGINE, "SAP Payment Engine"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PLANT_CONNECTIVITY, "SAP Plant Connectivity"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_POINTOFSALE, "SAP Point-of-Sale"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_POLICY_MANAGEMENT, "SAP Policy Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PORTAL_SITE_MANAGEMENT_BY_OPENTEXT, "SAP Portal Site Management By OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PORTFOLIO_AND_PROJECT_MANAGEMENT, "SAP Portfolio and Project Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_POWERBUILDER, "SAP PowerBuilder"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_POWERDESIGNER, "SAP PowerDesigner"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PREDICTIVE_ANALYTICS, "SAP Predictive Analytics"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PREDICTIVE_MAINTENANCE_AND_SERVICE, "SAP Predictive Maintenance and Service"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PRICE_AND_MARGIN_MANAGEMENT_BY_VENDAVO, "SAP Price and Margin Management by Vendavo"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PRICING_AND_COSTING_FOR_UTILITIES, "SAP Pricing and Costing for Utilities"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PROCESS_CONTROL, "SAP Process Control"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PROCESS_OBJECT_BUILDER, "SAP Process Object Builder"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PRODUCT_LIFECYCLE_MANAGEMENT_FOR_INSURANCE, "SAP Product Lifecycle Management for Insurance"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PRODUCTION_AND_REVENUE_ACCOUNTING, "SAP Production and Revenue Accounting"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PROFITABILITY_AND_COST_MANAGEMENT, "SAP Profitability and Cost Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PROMOTION_MANAGEMENT_FOR_RETAIL, "SAP Promotion Management for Retail"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_PUBLIC_BUDGET_FORMULATION, "SAP Public Budget Formulation"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_QUALITY_CENTER_BY_HP, "SAP Quality Center by HP"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_R3, "SAP R/3"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_REAL_ESTATE_MANAGEMENT, "SAP Real Estate Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_REALTIME_OFFER_MANAGEMENT, "SAP Real-Time Offer Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_RECEIVABLES_MANAGER, "SAP Receivables Manager"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_REGULATION_MANAGEMENT_BY_GREENLIGHT, "SAP Regulation Management by Greenlight"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_REPLICATION_SERVER, "SAP Replication Server"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_RETAIL_EXECUTION, "SAP Retail Execution"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_REVENUE_ACCOUNTING_AND_REPORTING, "SAP Revenue Accounting and Reporting"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_RISK_MANAGEMENT, "SAP Risk Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_S4HANA, "SAP S/4HANA"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SALES_AND_OPERATIONS_PLANNING, "SAP Sales and Operations Planning"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SALES_COMPANION, "SAP Sales Companion"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SALES_MANAGER, "SAP Sales Manager"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SCOUTING, "SAP Scouting"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SCREEN_PERSONAS, "SAP Screen Personas"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SECONDARY_DISTRIBUTION_FOR_OIL_GAS, "SAP Secondary Distribution for Oil & Gas"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SERVICE_VIRTUALIZATION_BY_HP, "SAP Service Virtualization by HP"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SIMPLE_FINANCE, "SAP Simple Finance"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SINGLE_SIGNON, "SAP Single Sign-On"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOLUTION_MANAGER, "SAP Solution Manager"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOLUTIONS_FOR_COMMERCE, "SAP Solutions for Commerce"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOLUTIONS_FOR_FINANCE, "SAP Solutions for Finance"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOLUTIONS_FOR_HUMAN_RESOURCES, "SAP Solutions for Human Resources"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOLUTIONS_FOR_MARKETING, "SAP Solutions for Marketing"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOLUTIONS_FOR_SALES, "SAP Solutions for Sales"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOLUTIONS_FOR_SERVICE, "SAP Solutions for Service"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOLUTIONS_FOR_SOURCING_AND_PROCUREMENT, "SAP Solutions for Sourcing and Procurement"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SOURCING_AND_SAP_CONTRACT_LIFECYCLE_MANAGEMENT, "SAP Sourcing and SAP Contract Lifecycle Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SPEND_PERFORMANCE_MANAGEMENT, "SAP Spend Performance Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SQL_ANYWHERE, "SAP SQL Anywhere"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_STRATEGIC_ENTERPRISE_MANAGEMENT, "SAP Strategic Enterprise Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_STRATEGY_MANAGEMENT, "SAP Strategy Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SUPPLIER_LIFECYCLE_MANAGEMENT, "SAP Supplier Lifecycle Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SUPPLIER_RELATIONSHIP_MANAGEMENT, "SAP Supplier Relationship Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SUPPLY_CHAIN_INFO_CENTER, "SAP Supply Chain Info Center"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SUPPLY_CHAIN_MANAGEMENT, "SAP Supply Chain Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SUPPLY_NETWORK_COLLABORATION, "SAP Supply Network Collaboration"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_SUSTAINABILITY_PERFORMANCE_MANAGEMENT, "SAP Sustainability Performance Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TALENT_VISUALIZATION_BY_NAKISA, "SAP Talent Visualization by Nakisa"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TAX_CLASSIFICATION_AND_REPORTING_FOR_FATCA, "SAP Tax Classification and Reporting for FATCA"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TAX_DECLARATION_FRAMEWORK_FOR_BRAZIL, "SAP Tax Declaration Framework for Brazil"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TEST_ACCELERATION_AND_OPTIMIZATION, "SAP Test Acceleration and Optimization"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TEST_DATA_MIGRATION_SERVER, "SAP Test Data Migration Server"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TRADE_PROMOTION_OPTIMIZATION, "SAP Trade Promotion Optimization"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TRANSPORT_NOTIFICATION_AND_STATUS, "SAP Transport Notification and Status"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TRANSPORT_TENDERING, "SAP Transport Tendering"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TRANSPORTATION_MANAGEMENT, "SAP Transportation Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TRANSPORTATION_RESOURCE_PLANNING, "SAP Transportation Resource Planning"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_TRAVEL_RECEIPTS_MANAGEMENT_BY_OPENTEXT, "SAP Travel Receipts Management by OpenText"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_UNDERWRITING_FOR_INSURANCE, "SAP Underwriting for Insurance"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_USER_EXPERIENCE_MANAGEMENT, "SAP User Experience Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_UTILITY_CUSTOMER_ESERVICES, "SAP Utility Customer E-Services"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_VISUAL_BUSINESS, "SAP Visual Business"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_WEB_IDE, "SAP Web IDE"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_WORKFORCE_MANAGEMENT, "SAP Workforce Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_WORKFORCE_PERFORMANCE_BUILDER, "SAP Workforce Performance Builder"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_WORKFORCE_SCHEDULING_AND_OPTIMIZATION_BY_CLICKSOFTWARE, "SAP Workforce Scheduling and Optimization by ClickSoftware"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SAP_WORKING_CAPITAL_ANALYTICS, "SAP Working Capital Analytics"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SECURITY, "Security"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SELLING_THROUGH_CONTACT_CENTERS, "Selling Through Contact Centers"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SOFTWARE_LOGISTICS_TOOLSET, "Software logistics toolset"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SOLUTIONS_BY_LINE_OF_BUSINESS, "Solutions by Line of Business"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SUBSCRIPTION_BILLING_AND_REVENUE_MANAGEMENT, "Subscription Billing and Revenue Management"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SUCCESSFACTORS_EMPLOYEE_CENTRAL, "SuccessFactors Employee Central"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.SUCCESSFACTORS_HCM_SUITE, "SuccessFactors HCM Suite"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.TAXPAYER_ONLINE_SERVICES, "Taxpayer Online Services"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.TWOGO_BY_SAP, "TwoGo by SAP"));
					sapSoftwareSolutionRepository.save(new SapSoftwareSolution(SapSoftwareSolution.UI_ADDON, "UI add-on"));
				}

				if (expertiseLevelRepository.count() == 0) {
					log.info("Expertise level table is still empty. Prepopulating it");
					expertiseLevelRepository.save(new ExpertiseLevel(ExpertiseLevel.ADVANCE, "Advance"));
					expertiseLevelRepository.save(new ExpertiseLevel(ExpertiseLevel.EXPERT, "Expert"));
				}

				if (softSkillRepository.count() == 0) {
					log.info("Soft skills table is still empty. Prepopulating it");
					softSkillRepository.save(new SoftSkill(SoftSkill.DESIGN_THINKING, "Design Thinking"));
					softSkillRepository.save(new SoftSkill(SoftSkill.ASUG, "ASUG"));
					softSkillRepository.save(new SoftSkill(SoftSkill.INSIDETRACKS, "InsideTracks"));
					softSkillRepository.save(new SoftSkill(SoftSkill.INTERNET_OF_THINGS, "Internet of Things"));
					softSkillRepository.save(new SoftSkill(SoftSkill.LEAN_METHODOLOGY, "Lean Methodology"));
					softSkillRepository.save(new SoftSkill(SoftSkill.COMMUNICATION_SKILLS, "Communication skills"));
					softSkillRepository.save(new SoftSkill(SoftSkill.INTERPERSONAL_SKILLS, "Interpersonal skills"));
					softSkillRepository.save(new SoftSkill(SoftSkill.PROJECT_MANAGEMENT_SKILLS, "Project management skills"));
					softSkillRepository.save(new SoftSkill(SoftSkill.PROCESS_IMPROVEMENT_EXPERTISE, "Process improvement expertise"));
					softSkillRepository.save(new SoftSkill(SoftSkill.EMOTIONAL_INTELLIGENCE, "Emotional intelligence"));
					softSkillRepository.save(new SoftSkill(SoftSkill.CRITICAL_OBSERVATION_SKILLS, "Critical observation skills"));
					softSkillRepository.save(new SoftSkill(SoftSkill.CONFLICT_RESOLUTION, "Conflict Resolution"));
					softSkillRepository.save(new SoftSkill(SoftSkill.DECISIONMAKING, "Decision-Making"));
					softSkillRepository.save(new SoftSkill(SoftSkill.LEADERSHIP_SKILLS, "Leadership skills"));
					softSkillRepository.save(new SoftSkill(SoftSkill.CHANGE_MANAGEMENT, "Change Management"));
				}

				if (countryRepository.count() == 0) {
					log.info("Country table is still empty. Prepopulating it");
					countryRepository.save(new Country(Country.AD, "Andorra"));
					countryRepository.save(new Country(Country.AE, "United Arab Emirates"));
					countryRepository.save(new Country(Country.AF, "Afghanistan"));
					countryRepository.save(new Country(Country.AG, "Antigua and Barbuda"));
					countryRepository.save(new Country(Country.AI, "Anguilla"));
					countryRepository.save(new Country(Country.AL, "Albania"));
					countryRepository.save(new Country(Country.AM, "Armenia"));
					countryRepository.save(new Country(Country.AO, "Angola"));
					countryRepository.save(new Country(Country.AQ, "Antarctica"));
					countryRepository.save(new Country(Country.AR, "Argentina"));
					countryRepository.save(new Country(Country.AS, "American Samoa"));
					countryRepository.save(new Country(Country.AT, "Austria"));
					countryRepository.save(new Country(Country.AU, "Australia"));
					countryRepository.save(new Country(Country.AW, "Aruba"));
					countryRepository.save(new Country(Country.AX, "Åland Islands"));
					countryRepository.save(new Country(Country.AZ, "Azerbaijan"));
					countryRepository.save(new Country(Country.BA, "Bosnia and Herzegovina"));
					countryRepository.save(new Country(Country.BB, "Barbados"));
					countryRepository.save(new Country(Country.BD, "Bangladesh"));
					countryRepository.save(new Country(Country.BE, "Belgium"));
					countryRepository.save(new Country(Country.BF, "Burkina Faso"));
					countryRepository.save(new Country(Country.BG, "Bulgaria"));
					countryRepository.save(new Country(Country.BH, "Bahrain"));
					countryRepository.save(new Country(Country.BI, "Burundi"));
					countryRepository.save(new Country(Country.BJ, "Benin"));
					countryRepository.save(new Country(Country.BL, "Saint Barthélemy"));
					countryRepository.save(new Country(Country.BM, "Bermuda"));
					countryRepository.save(new Country(Country.BN, "Brunei Darussalam"));
					countryRepository.save(new Country(Country.BO, "Bolivia, Plurinational State of"));
					countryRepository.save(new Country(Country.BQ, "Bonaire, Sint Eustatius and Saba"));
					countryRepository.save(new Country(Country.BR, "Brazil"));
					countryRepository.save(new Country(Country.BS, "Bahamas"));
					countryRepository.save(new Country(Country.BT, "Bhutan"));
					countryRepository.save(new Country(Country.BV, "Bouvet Island"));
					countryRepository.save(new Country(Country.BW, "Botswana"));
					countryRepository.save(new Country(Country.BY, "Belarus"));
					countryRepository.save(new Country(Country.BZ, "Belize"));
					countryRepository.save(new Country(Country.CA, "Canada"));
					countryRepository.save(new Country(Country.CC, "Cocos (Keeling) Islands"));
					countryRepository.save(new Country(Country.CD, "Congo, the Democratic Republic of the"));
					countryRepository.save(new Country(Country.CF, "Central African Republic"));
					countryRepository.save(new Country(Country.CG, "Congo"));
					countryRepository.save(new Country(Country.CH, "Switzerland"));
					countryRepository.save(new Country(Country.CI, "Côte d'Ivoire"));
					countryRepository.save(new Country(Country.CK, "Cook Islands"));
					countryRepository.save(new Country(Country.CL, "Chile"));
					countryRepository.save(new Country(Country.CM, "Cameroon"));
					countryRepository.save(new Country(Country.CN, "China"));
					countryRepository.save(new Country(Country.CO, "Colombia"));
					countryRepository.save(new Country(Country.CR, "Costa Rica"));
					countryRepository.save(new Country(Country.CU, "Cuba"));
					countryRepository.save(new Country(Country.CV, "Cabo Verde"));
					countryRepository.save(new Country(Country.CW, "Curaçao"));
					countryRepository.save(new Country(Country.CX, "Christmas Island"));
					countryRepository.save(new Country(Country.CY, "Cyprus"));
					countryRepository.save(new Country(Country.CZ, "Czech Republic"));
					countryRepository.save(new Country(Country.DE, "Germany"));
					countryRepository.save(new Country(Country.DJ, "Djibouti"));
					countryRepository.save(new Country(Country.DK, "Denmark"));
					countryRepository.save(new Country(Country.DM, "Dominica"));
					countryRepository.save(new Country(Country.DO, "Dominican Republic"));
					countryRepository.save(new Country(Country.DZ, "Algeria"));
					countryRepository.save(new Country(Country.EC, "Ecuador"));
					countryRepository.save(new Country(Country.EE, "Estonia"));
					countryRepository.save(new Country(Country.EG, "Egypt"));
					countryRepository.save(new Country(Country.EH, "Western Sahara"));
					countryRepository.save(new Country(Country.ER, "Eritrea"));
					countryRepository.save(new Country(Country.ES, "Spain"));
					countryRepository.save(new Country(Country.ET, "Ethiopia"));
					countryRepository.save(new Country(Country.FI, "Finland"));
					countryRepository.save(new Country(Country.FJ, "Fiji"));
					countryRepository.save(new Country(Country.FK, "Falkland Islands (Malvinas)"));
					countryRepository.save(new Country(Country.FM, "Micronesia, Federated States of"));
					countryRepository.save(new Country(Country.FO, "Faroe Islands"));
					countryRepository.save(new Country(Country.FR, "France"));
					countryRepository.save(new Country(Country.GA, "Gabon"));
					countryRepository.save(new Country(Country.GB, "United Kingdom"));
					countryRepository.save(new Country(Country.GD, "Grenada"));
					countryRepository.save(new Country(Country.GE, "Georgia"));
					countryRepository.save(new Country(Country.GF, "French Guiana"));
					countryRepository.save(new Country(Country.GG, "Guernsey"));
					countryRepository.save(new Country(Country.GH, "Ghana"));
					countryRepository.save(new Country(Country.GI, "Gibraltar"));
					countryRepository.save(new Country(Country.GL, "Greenland"));
					countryRepository.save(new Country(Country.GM, "Gambia"));
					countryRepository.save(new Country(Country.GN, "Guinea"));
					countryRepository.save(new Country(Country.GP, "Guadeloupe"));
					countryRepository.save(new Country(Country.GQ, "Equatorial Guinea"));
					countryRepository.save(new Country(Country.GR, "Greece"));
					countryRepository.save(new Country(Country.GS, "South Georgia and the South Sandwich Islands"));
					countryRepository.save(new Country(Country.GT, "Guatemala"));
					countryRepository.save(new Country(Country.GU, "Guam"));
					countryRepository.save(new Country(Country.GW, "Guinea-Bissau"));
					countryRepository.save(new Country(Country.GY, "Guyana"));
					countryRepository.save(new Country(Country.HK, "Hong Kong"));
					countryRepository.save(new Country(Country.HM, "Heard Island and McDonald Islands"));
					countryRepository.save(new Country(Country.HN, "Honduras"));
					countryRepository.save(new Country(Country.HR, "Croatia"));
					countryRepository.save(new Country(Country.HT, "Haiti"));
					countryRepository.save(new Country(Country.HU, "Hungary"));
					countryRepository.save(new Country(Country.ID, "Indonesia"));
					countryRepository.save(new Country(Country.IE, "Ireland"));
					countryRepository.save(new Country(Country.IL, "Israel"));
					countryRepository.save(new Country(Country.IM, "Isle of Man"));
					countryRepository.save(new Country(Country.IN, "India"));
					countryRepository.save(new Country(Country.IO, "British Indian Ocean Territory"));
					countryRepository.save(new Country(Country.IQ, "Iraq"));
					countryRepository.save(new Country(Country.IR, "Iran, Islamic Republic of"));
					countryRepository.save(new Country(Country.IS, "Iceland"));
					countryRepository.save(new Country(Country.IT, "Italy"));
					countryRepository.save(new Country(Country.JE, "Jersey"));
					countryRepository.save(new Country(Country.JM, "Jamaica"));
					countryRepository.save(new Country(Country.JO, "Jordan"));
					countryRepository.save(new Country(Country.JP, "Japan"));
					countryRepository.save(new Country(Country.KE, "Kenya"));
					countryRepository.save(new Country(Country.KG, "Kyrgyzstan"));
					countryRepository.save(new Country(Country.KH, "Cambodia"));
					countryRepository.save(new Country(Country.KI, "Kiribati"));
					countryRepository.save(new Country(Country.KM, "Comoros"));
					countryRepository.save(new Country(Country.KN, "Saint Kitts and Nevis"));
					countryRepository.save(new Country(Country.KP, "Korea (the Democratic People's Republic of)"));
					countryRepository.save(new Country(Country.KR, "Korea (the Republic of)"));
					countryRepository.save(new Country(Country.KW, "Kuwait"));
					countryRepository.save(new Country(Country.KY, "Cayman Islands"));
					countryRepository.save(new Country(Country.KZ, "Kazakhstan"));
					countryRepository.save(new Country(Country.LA, "Lao People's Democratic Republic"));
					countryRepository.save(new Country(Country.LB, "Lebanon"));
					countryRepository.save(new Country(Country.LC, "Saint Lucia"));
					countryRepository.save(new Country(Country.LI, "Liechtenstein"));
					countryRepository.save(new Country(Country.LK, "Sri Lanka"));
					countryRepository.save(new Country(Country.LR, "Liberia"));
					countryRepository.save(new Country(Country.LS, "Lesotho"));
					countryRepository.save(new Country(Country.LT, "Lithuania"));
					countryRepository.save(new Country(Country.LU, "Luxembourg"));
					countryRepository.save(new Country(Country.LV, "Latvia"));
					countryRepository.save(new Country(Country.LY, "Libya"));
					countryRepository.save(new Country(Country.MA, "Morocco"));
					countryRepository.save(new Country(Country.MC, "Monaco"));
					countryRepository.save(new Country(Country.MD, "Moldova, Republic of"));
					countryRepository.save(new Country(Country.ME, "Montenegro"));
					countryRepository.save(new Country(Country.MF, "Saint Martin (French part)"));
					countryRepository.save(new Country(Country.MG, "Madagascar"));
					countryRepository.save(new Country(Country.MH, "Marshall Islands"));
					countryRepository.save(new Country(Country.MK, "Macedonia, the former Yugoslav Republic of"));
					countryRepository.save(new Country(Country.ML, "Mali"));
					countryRepository.save(new Country(Country.MM, "Myanmar"));
					countryRepository.save(new Country(Country.MN, "Mongolia"));
					countryRepository.save(new Country(Country.MO, "Macao"));
					countryRepository.save(new Country(Country.MP, "Northern Mariana Islands"));
					countryRepository.save(new Country(Country.MQ, "Martinique"));
					countryRepository.save(new Country(Country.MR, "Mauritania"));
					countryRepository.save(new Country(Country.MS, "Montserrat"));
					countryRepository.save(new Country(Country.MT, "Malta"));
					countryRepository.save(new Country(Country.MU, "Mauritius"));
					countryRepository.save(new Country(Country.MV, "Maldives"));
					countryRepository.save(new Country(Country.MW, "Malawi"));
					countryRepository.save(new Country(Country.MX, "Mexico"));
					countryRepository.save(new Country(Country.MY, "Malaysia"));
					countryRepository.save(new Country(Country.MZ, "Mozambique"));
					countryRepository.save(new Country(Country.NA, "Namibia"));
					countryRepository.save(new Country(Country.NC, "New Caledonia"));
					countryRepository.save(new Country(Country.NE, "Niger"));
					countryRepository.save(new Country(Country.NF, "Norfolk Island"));
					countryRepository.save(new Country(Country.NG, "Nigeria"));
					countryRepository.save(new Country(Country.NI, "Nicaragua"));
					countryRepository.save(new Country(Country.NL, "Netherlands[note 1]"));
					countryRepository.save(new Country(Country.NO, "Norway"));
					countryRepository.save(new Country(Country.NP, "Nepal"));
					countryRepository.save(new Country(Country.NR, "Nauru"));
					countryRepository.save(new Country(Country.NU, "Niue"));
					countryRepository.save(new Country(Country.NZ, "New Zealand"));
					countryRepository.save(new Country(Country.OM, "Oman"));
					countryRepository.save(new Country(Country.PA, "Panama"));
					countryRepository.save(new Country(Country.PE, "Peru"));
					countryRepository.save(new Country(Country.PF, "French Polynesia"));
					countryRepository.save(new Country(Country.PG, "Papua New Guinea"));
					countryRepository.save(new Country(Country.PH, "Philippines"));
					countryRepository.save(new Country(Country.PK, "Pakistan"));
					countryRepository.save(new Country(Country.PL, "Poland"));
					countryRepository.save(new Country(Country.PM, "Saint Pierre and Miquelon"));
					countryRepository.save(new Country(Country.PN, "Pitcairn"));
					countryRepository.save(new Country(Country.PR, "Puerto Rico"));
					countryRepository.save(new Country(Country.PS, "Palestine, State of"));
					countryRepository.save(new Country(Country.PT, "Portugal"));
					countryRepository.save(new Country(Country.PW, "Palau"));
					countryRepository.save(new Country(Country.PY, "Paraguay"));
					countryRepository.save(new Country(Country.QA, "Qatar"));
					countryRepository.save(new Country(Country.RE, "Réunion"));
					countryRepository.save(new Country(Country.RO, "Romania"));
					countryRepository.save(new Country(Country.RS, "Serbia"));
					countryRepository.save(new Country(Country.RU, "Russian Federation"));
					countryRepository.save(new Country(Country.RW, "Rwanda"));
					countryRepository.save(new Country(Country.SA, "Saudi Arabia"));
					countryRepository.save(new Country(Country.SB, "Solomon Islands"));
					countryRepository.save(new Country(Country.SC, "Seychelles"));
					countryRepository.save(new Country(Country.SD, "Sudan"));
					countryRepository.save(new Country(Country.SE, "Sweden"));
					countryRepository.save(new Country(Country.SG, "Singapore"));
					countryRepository.save(new Country(Country.SH, "Saint Helena, Ascension and Tristan da Cunha"));
					countryRepository.save(new Country(Country.SI, "Slovenia"));
					countryRepository.save(new Country(Country.SJ, "Svalbard and Jan Mayen"));
					countryRepository.save(new Country(Country.SK, "Slovakia"));
					countryRepository.save(new Country(Country.SL, "Sierra Leone"));
					countryRepository.save(new Country(Country.SM, "San Marino"));
					countryRepository.save(new Country(Country.SN, "Senegal"));
					countryRepository.save(new Country(Country.SO, "Somalia"));
					countryRepository.save(new Country(Country.SR, "Suriname"));
					countryRepository.save(new Country(Country.SS, "South Sudan"));
					countryRepository.save(new Country(Country.ST, "Sao Tome and Principe"));
					countryRepository.save(new Country(Country.SV, "El Salvador"));
					countryRepository.save(new Country(Country.SX, "Sint Maarten (Dutch part)"));
					countryRepository.save(new Country(Country.SY, "Syrian Arab Republic"));
					countryRepository.save(new Country(Country.SZ, "Swaziland"));
					countryRepository.save(new Country(Country.TC, "Turks and Caicos Islands"));
					countryRepository.save(new Country(Country.TD, "Chad"));
					countryRepository.save(new Country(Country.TF, "French Southern Territories"));
					countryRepository.save(new Country(Country.TG, "Togo"));
					countryRepository.save(new Country(Country.TH, "Thailand"));
					countryRepository.save(new Country(Country.TJ, "Tajikistan"));
					countryRepository.save(new Country(Country.TK, "Tokelau"));
					countryRepository.save(new Country(Country.TL, "Timor-Leste"));
					countryRepository.save(new Country(Country.TM, "Turkmenistan"));
					countryRepository.save(new Country(Country.TN, "Tunisia"));
					countryRepository.save(new Country(Country.TO, "Tonga"));
					countryRepository.save(new Country(Country.TR, "Turkey"));
					countryRepository.save(new Country(Country.TT, "Trinidad and Tobago"));
					countryRepository.save(new Country(Country.TV, "Tuvalu"));
					countryRepository.save(new Country(Country.TW, "Taiwan, Province of China [note 2]"));
					countryRepository.save(new Country(Country.TZ, "Tanzania, United Republic of"));
					countryRepository.save(new Country(Country.UA, "Ukraine"));
					countryRepository.save(new Country(Country.UG, "Uganda"));
					countryRepository.save(new Country(Country.UM, "United States Minor Outlying Islands"));
					countryRepository.save(new Country(Country.US, "United States"));
					countryRepository.save(new Country(Country.UY, "Uruguay"));
					countryRepository.save(new Country(Country.UZ, "Uzbekistan"));
					countryRepository.save(new Country(Country.VA, "Holy See (Vatican City State)"));
					countryRepository.save(new Country(Country.VC, "Saint Vincent and the Grenadines"));
					countryRepository.save(new Country(Country.VE, "Venezuela, Bolivarian Republic of"));
					countryRepository.save(new Country(Country.VG, "Virgin Islands, British"));
					countryRepository.save(new Country(Country.VI, "Virgin Islands, U.S."));
					countryRepository.save(new Country(Country.VN, "Viet Nam"));
					countryRepository.save(new Country(Country.VU, "Vanuatu"));
					countryRepository.save(new Country(Country.WF, "Wallis and Futuna"));
					countryRepository.save(new Country(Country.WS, "Samoa"));
					countryRepository.save(new Country(Country.YE, "Yemen"));
					countryRepository.save(new Country(Country.YT, "Mayotte"));
					countryRepository.save(new Country(Country.ZA, "South Africa"));
					countryRepository.save(new Country(Country.ZM, "Zambia"));
					countryRepository.save(new Country(Country.ZW, "Zimbabwe"));
				}

				if (regionRepository.count() == 0) {
					log.info("Region table is still empty. Prepopulating it");
					regionRepository.save(new Region(Region.APJ, "Asia Pacific and Japan (APJ)"));
					regionRepository.save(new Region(Region.EUR, "Europe"));
					regionRepository.save(new Region(Region.MEA, "Middle East and Africa"));
					regionRepository.save(new Region(Region.NA, "North America"));
					regionRepository.save(new Region(Region.SA, "South America"));
				}
				
				if (mentorRepository.count() == 0) {
					log.info("Mentors is still empty. Adding some sample records");
					mentorRepository.save(new Mentor(
							UUID.randomUUID().toString(), 
							"Jan Penninkhof",
							new MentorStatus(MentorStatus.ACTIVE),
							"SAP Consultant",
							"Phoqus B.V.",
							new RelationshipToSap(RelationshipToSap.PARTNER),
							new LineOfBusiness(LineOfBusiness.PLATFORM_AND_TECHNOLOGY),
							null,
							null,
							new Industry(Industry.HEALTHCARE),
							new Industry(Industry.HIGH_TECH),
							null,
							new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_CLOUD_PLATFORM),
							new ExpertiseLevel(ExpertiseLevel.EXPERT),
							new SapSoftwareSolution(SapSoftwareSolution.SAP_MOBILE_PLATFORM),
							new ExpertiseLevel(ExpertiseLevel.EXPERT),
							new SapSoftwareSolution(SapSoftwareSolution.SAP_FIORI),
							new ExpertiseLevel(ExpertiseLevel.EXPERT),
							new SoftSkill(SoftSkill.INSIDETRACKS),
							new SoftSkill(SoftSkill.DESIGN_THINKING),
							new SoftSkill(SoftSkill.INTERNET_OF_THINGS),
							null,
							null,
							null,
							"Lorem ipsum dolor sit amet, consectetur adipiscing elit.\nCras posuere nunc non efficitur feugiat.\nQuisque aliquam porttitor eros quis vestibulum.",
							"email1@jpenninkhof.com",
							"email2@jpenninkhof.com",
							1,
							"Janstreet 1", 
							"Lelystad", 
							null, 
							"4321AA", 
							new Country(Country.NL), 
							"+31987654321",
							new Region(Region.EUR),
							150,
							"@jpenninkhof"
						));
					mentorRepository.save(new Mentor(
							UUID.randomUUID().toString(), 
							"Robin van het Hof",
							new MentorStatus(MentorStatus.ACTIVE),
							"SAP NetWeaver Magician",
							"Qualiture",
							new RelationshipToSap(RelationshipToSap.FREELANCE),
							new LineOfBusiness(LineOfBusiness.PLATFORM_AND_TECHNOLOGY),
							new LineOfBusiness(LineOfBusiness.SUPPLY_CHAIN),
							new LineOfBusiness(LineOfBusiness.HUMAN_RESOURCES),
							new Industry(Industry.PROFESSIONAL_SERVICES),
							new Industry(Industry.OIL_AND_GAS),
							null,
							new SapSoftwareSolution(SapSoftwareSolution.UI_ADDON),
							new ExpertiseLevel(ExpertiseLevel.EXPERT),
							new SapSoftwareSolution(SapSoftwareSolution.SAP_HANA_CLOUD_PLATFORM),
							new ExpertiseLevel(ExpertiseLevel.EXPERT),
							new SapSoftwareSolution(SapSoftwareSolution.SAP_NETWEAVER),
							new ExpertiseLevel(ExpertiseLevel.EXPERT),
							new SoftSkill(SoftSkill.INTERPERSONAL_SKILLS),
							new SoftSkill(SoftSkill.EMOTIONAL_INTELLIGENCE),
							new SoftSkill(SoftSkill.DESIGN_THINKING),
							new SoftSkill(SoftSkill.LEAN_METHODOLOGY),
							new SoftSkill(SoftSkill.CONFLICT_RESOLUTION),
							new SoftSkill(SoftSkill.CRITICAL_OBSERVATION_SKILLS),
							"Donec tincidunt turpis magna, in consequat eros condimentum ut.\nCurabitur eleifend pharetra varius.",
							"email1@robin.com",
							"email2@robin.com",
							1,
							"Robinstreet 1", 
							"Zoetermeer", 
							null, 
							"1234AA", 
							new Country(Country.NL), 
							"+31123456789",
							new Region(Region.EUR),
							234,
							"@Qualiture"
						));
				}

	        }
	    };
	}

}