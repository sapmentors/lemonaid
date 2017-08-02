package com.sap.mentors.lemonaid.odata;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.persistence.JoinColumn;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

import com.sap.mentors.lemonaid.annotations.SAP;
import com.sap.mentors.lemonaid.odata.authorization.ODataAuthorization;
import com.sap.mentors.lemonaid.odata.util.ODataContextUtil;
import com.sap.mentors.lemonaid.odata.util.SpringContextsUtil;

public class JPAEdmExtension implements org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension {

	public static final String MAPPING_MODEL = "odata-mapping.xml";
	public static final String SAP_NAMESPACE = "http://www.sap.com/Protocols/SAPData";
	public static final String SAP_PREFIX = "sap";
	public static final String LABEL = "label";

	public static final String PUBLIC_PROPERTIES =
			"Id|FullName|PublicProfile|ShirtNumber|PhotoUrl|"
			+ "StatusId|RelationshipToSap|RegionId|CountryId|"
			+ "Bio|"
            + "JambandLasVegas|JambandBarcelona|JambandMusician|JambandInstrument|"
            //The ones that are evaluated for each mentor
            +"JobTitle|"
            +"Company|"
            +"Address1|"
            +"Address2|"
            +"City|"
            +"Zip|"
            +"State|"
            +"Phone|"
            +"Email1|"
            +"Email2|"
            +"SoftSkill1Id|SoftSkill2Id|SoftSkill3Id|SoftSkill4Id|SoftSkill5Id|SoftSkill6Id|"
            +"SapExpertise1Id|SapExpertise1LevelId|SapExpertise2Id|SapExpertise2LevelId|SapExpertise3Id|SapExpertise3LevelId|"
			+ "PublicLongitude|PublicLatitude"; //Longitude|Latitude|
	public static final String PUBLIC_NAVPROPERTIES =
            "MentorStatus|RelationshipToSap|Region|Country|"
             +"SoftSkill1|SoftSkill2|SoftSkill3|SoftSkill4|SoftSkill5|SoftSkill6|"
             +"SapExpertise1|SapExpertise1Level|SapExpertise2|SapExpertise2Level|SapExpertise3|SapExpertise3Level|"
            +"Attachments";
	private ODataAuthorization authorization = null;

	public JPAEdmExtension() {
		this.authorization = (ODataAuthorization) SpringContextsUtil.getBean("ODataAuthorization");
	}

	@Override
	public void extendWithOperation(JPAEdmSchemaView view) {
	}

	@Override
	public void extendJPAEdmSchema(JPAEdmSchemaView view) {
		ResourceBundle i18n = ODataContextUtil.getResourceBundle("i18n");
		final Schema edmSchema = view.getEdmSchema();

		for (EntityType entityType : edmSchema.getEntityTypes()) {

			// Add property annotations
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
				annotationAttributeList.addAll(getSapPropertyAnnotations(entityType, property));
				property.setAnnotationAttributes(annotationAttributeList);
			}

			if (entityType.getName().equals("Mentor")) {

				if (authorization.isMentor() || authorization.isProjectMember()) {

					// Add transient properties
					JPAEdmMappingImpl mapping = new JPAEdmMappingImpl();
					mapping.setJPAType(boolean.class);
					entityType.getProperties().add(new SimpleProperty().setName("MayEdit").setType(EdmSimpleTypeKind.Boolean).setMapping(mapping));

				} else {

					// Remove properties that are not meant for the public
					List<Property> publicProperties = new LinkedList<Property>();
					for (Property property : entityType.getProperties()) {
						if (property.getName().matches(PUBLIC_PROPERTIES)) {
							publicProperties.add(property);
						}
					}
					entityType.setProperties(publicProperties);

					List<NavigationProperty> publicNavigationProperties = new LinkedList<NavigationProperty>();
					for (NavigationProperty navigationProperty : entityType.getNavigationProperties()) {
						if (navigationProperty.getName().matches(PUBLIC_NAVPROPERTIES)) {
							publicNavigationProperties.add(navigationProperty);
						}
					}
					entityType.setNavigationProperties(publicNavigationProperties);
				}

			}

			// Turn entity 'Attachments' into a media entity (with a stream) and hide the data property
			if (entityType.getName().equals("Attachment")) {
				entityType.setHasStream(true);
				for (int i = entityType.getProperties().size() - 1; i >= 0; i--) {
					Property property = entityType.getProperties().get(i);
					if (property.getName().equals("Data")) {
						entityType.getProperties().remove(property);
					}
				}
			}

		}

		// Add smart annotations
		addSmartAnnotations(edmSchema);

	}

	@SuppressWarnings("unchecked")
	private Collection<AnnotationAttribute> getSapPropertyAnnotations(EntityType entityType, Property property) {
		List<AnnotationAttribute> result = new ArrayList<AnnotationAttribute>();
		for (Field field : ((JPAEdmMappingImpl)entityType.getMapping()).getJPAType().getDeclaredFields()) {
			if (field.getName().equals(((JPAEdmMappingImpl) property.getMapping()).getInternalName())) {
				if (field.getAnnotation(SAP.class) != null) {
					InvocationHandler handler = Proxy.getInvocationHandler(field.getAnnotation(SAP.class));
					Field f = null;
					try {
				        f = handler.getClass().getDeclaredField("memberValues");
				    } catch (NoSuchFieldException | SecurityException e) {
				        continue;
				    }
					f.setAccessible(true);
					Map<String, Object> memberValues = null;
				    try {
				        memberValues = (Map<String, Object>) f.get(handler);
				    } catch (IllegalArgumentException | IllegalAccessException e) {
				    	continue;
				    }
				    for (Entry<String, Object> memberValue : memberValues.entrySet()) {
				    	if (!"FieldGroup".equals(memberValue.getKey())) {
							result.add(new AnnotationAttribute()
									.setNamespace(SAP_NAMESPACE)
									.setPrefix(SAP_PREFIX)
									.setName(memberValue.getKey())
									.setText(String.valueOf(memberValue.getValue())));
				    	}
					}
				}
			}
		}
		return result;
	}

	@SuppressWarnings({ "serial" })
	private void addSmartAnnotations(final Schema edmSchema) {
		List<AnnotationElement> schemaAnnotations = edmSchema.getAnnotationElements();
		if (schemaAnnotations == null) {
			schemaAnnotations = new ArrayList<AnnotationElement>();
			edmSchema.setAnnotationElements(schemaAnnotations);
			for (final EntityContainer container : edmSchema.getEntityContainers()) {
				for (final EntitySet entitySet : container.getEntitySets()) {
					EntityType entityType = getEntityType(edmSchema, entitySet.getEntityType());
					final LinkedHashMap<String, ArrayList<String>> fieldGroups = new LinkedHashMap<String, ArrayList<String>>();
					for (Field field : ((JPAEdmMappingImpl)entityType.getMapping()).getJPAType().getDeclaredFields()) {
						String propertyName = null;
						for (Property property : entityType.getProperties()) {
							if (field.getName().equals(((JPAEdmMappingImpl) property.getMapping()).getInternalName())) {
								propertyName = property.getName();
							}
						}
						if (propertyName == null && entityType.getNavigationProperties() != null) {
							for (NavigationProperty property : entityType.getNavigationProperties()) {
								if (field.getName().equals(((JPAEdmMappingImpl) property.getMapping()).getInternalName())) {
									propertyName = (String) getAnnotationMemberValue(field, JoinColumn.class, "name");
									if (propertyName != null && propertyName.length() > 0) {
										propertyName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
									} else {
										propertyName = property.getName();
									}
								}
							}
						}
						if (propertyName != null) {
							String fieldGroupName = (String) getAnnotationMemberValue(field, SAP.class, "fieldGroup");
							if (fieldGroupName != null) {
					    		ArrayList<String> fieldGroup = fieldGroups.get(fieldGroupName);
					    		if (fieldGroup == null) {
					    			fieldGroup = new ArrayList<String>();
					    		}
					    		fieldGroup.add(propertyName);
					    		fieldGroups.put((String) fieldGroupName, fieldGroup);
							}
						}
					}
					if (!fieldGroups.isEmpty()) {
						schemaAnnotations.add(new AnnotationElement()
							.setName("Annotations")
							.setAttributes(
								new ArrayList<AnnotationAttribute>() {{
									add(new AnnotationAttribute()
										.setName("Target")
										.setNamespace("http://docs.oasis-open.org/odata/ns/edm")
										.setText(entitySet.getEntityType().toString()));
							}})
							.setChildElements(new ArrayList<AnnotationElement>() {{
								for (final Entry<String, ArrayList<String>> fieldGroup : fieldGroups.entrySet()) {
									add(new AnnotationElement()
										.setName("Annotation")
										.setAttributes(
											new ArrayList<AnnotationAttribute>() {{
												add(new AnnotationAttribute()
														.setName("Term")
														.setText("UI.FieldGroup"));
												add(new AnnotationAttribute()
														.setName("Qualifier")
														.setText(fieldGroup.getKey()));
									}})
									.setChildElements(new ArrayList<AnnotationElement>() {{
										add(new AnnotationElement()
											.setName("Record")
											.setAttributes(
												new ArrayList<AnnotationAttribute>() {{
													add(new AnnotationAttribute()
													.setName("Type")
													.setText("UI.FieldGroupType"));
										}})
										.setChildElements(new ArrayList<AnnotationElement>() {{
											add(new AnnotationElement()
												.setName("PropertyValue")
												.setAttributes(
													new ArrayList<AnnotationAttribute>() {{
														add(new AnnotationAttribute()
															.setName("Property")
															.setText("Data"));
											}})
											.setChildElements(new ArrayList<AnnotationElement>() {{
												add(new AnnotationElement()
													.setName("Collection")
													.setChildElements(new ArrayList<AnnotationElement>() {{
														for (final String field : fieldGroup.getValue()) {
															add(new AnnotationElement()
																.setName("Record")
																.setAttributes(
																	new ArrayList<AnnotationAttribute>() {{
																		add(new AnnotationAttribute()
																			.setName("Type")
																			.setText("UI.DataField"));
																}})
																.setChildElements(new ArrayList<AnnotationElement>() {{
																		add(new AnnotationElement()
																			.setName("PropertyValue")
																			.setAttributes(
																				new ArrayList<AnnotationAttribute>() {{
																					add(new AnnotationAttribute()
																						.setName("Property")
																						.setText("Value"));
																					add(new AnnotationAttribute()
																						.setName("Path")
																						.setText(field));
																		}}));
															}}));
														}
												}}));
											}}));
										}}));
									}})
							);}}})
						);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Object getAnnotationMemberValue(Field field, Class<? extends Annotation> annoClass, String memberKey) {
		if (field.getAnnotation(annoClass) != null) {
			InvocationHandler handler = Proxy.getInvocationHandler(field.getAnnotation(annoClass));
			Field f = null;
			try {
		        f = handler.getClass().getDeclaredField("memberValues");
		    } catch (NoSuchFieldException | SecurityException e) {
		        return null;
		    }
			f.setAccessible(true);
			Map<String, Object> memberValues = null;
		    try {
		        memberValues = (Map<String, Object>) f.get(handler);
		    } catch (IllegalArgumentException | IllegalAccessException e) {
		    	return null;
		    }
		    return memberValues.get(memberKey);
		}
		return null;
	}

	private EntityType getEntityType(Schema edmSchema, FullQualifiedName entityType) {
		for (EntityType e : edmSchema.getEntityTypes()) {
			if (entityType.equals(new FullQualifiedName(edmSchema.getNamespace(), e.getName()))) {
				return e;
			}
		}
		return null;
	}

	@Override
	public InputStream getJPAEdmMappingModelStream() {
		return JPAEdmExtension.class.getClassLoader().getResourceAsStream(MAPPING_MODEL);
	}

}
