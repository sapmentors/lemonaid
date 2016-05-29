package com.sap.mentors.lemonaid.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

public class JPAEdmExtension implements org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension {

	public static final String MAPPING_MODEL = "odata-mapping.xml";
	public static final String SAP_NAMESPACE = "http://www.sap.com/Protocols/SAPData";
	public static final String SAP_PREFIX = "sap";
	public static final String LABEL = "label";
	
	@Override
	public void extendWithOperation(JPAEdmSchemaView view) {
	}

	@Override
	public void extendJPAEdmSchema(JPAEdmSchemaView view) {
		ResourceBundle i18n = ODataContextUtil.getResourceBundle("i18n");
		final Schema edmSchema = view.getEdmSchema();
		
		for (EntityType entityType : edmSchema.getEntityTypes()) {
			
			// Add language specific labels to the properties
			for (Property property : entityType.getProperties()) {
				String label = null;
				if (i18n != null) { try { label = i18n.getString(entityType.getName() + "." + property.getName()); } catch (Exception e) {} }
				List<AnnotationAttribute> annotationAttributeList = new ArrayList<AnnotationAttribute>();
				if (label != null) {
					annotationAttributeList.add(new AnnotationAttribute()
							.setNamespace(SAP_NAMESPACE)
							.setPrefix(SAP_PREFIX)
							.setName(LABEL).setText(label));
				}
				property.setAnnotationAttributes(annotationAttributeList); 
			}
			
			if (entityType.getName().equals("Mentor")) {
				entityType.getProperties().add(new SimpleProperty().setName("CountryId").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("LineOfBusiness1Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("LineOfBusiness2Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("LineOfBusiness3Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("Industry1Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("Industry2Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("Industry3Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SapExpertise1Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SapExpertise1LevelId").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SapExpertise2Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SapExpertise2LevelId").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SapExpertise3Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SapExpertise3LevelId").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SoftSkill1Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SoftSkill2Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SoftSkill3Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SoftSkill4Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SoftSkill5Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("SoftSkill6Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("RegionId").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("ShirtSizeId").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("ShirtMFId").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("TopicLeadRegionId").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("Topic1Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("Topic2Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("Topic3Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("Topic4Id").setType(EdmSimpleTypeKind.String));
				entityType.getProperties().add(new SimpleProperty().setName("TopicInterestId").setType(EdmSimpleTypeKind.String));
			}
		}
	}

	@Override
	public InputStream getJPAEdmMappingModelStream() {
		return JPAEdmExtension.class.getClassLoader().getResourceAsStream(MAPPING_MODEL);
	}

}