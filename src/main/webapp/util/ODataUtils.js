sap.ui.define([] , function () {
	"use strict";

	var ODataUtils = {

		addNavigationLinks: function(oModel, oPayload, oEntityType) {
			var result = {};oModel, 
			jQuery.each(oPayload, function(sPropName, oPropValue) {
				result[sPropName] = oPropValue;
				if (sPropName !== "__metadata") {
					var association = ODataUtils.getAssociation(oModel, oEntityType, sPropName);
					if (association) {
						if (!oPropValue) result[sPropName] = oPropValue = null;
						var principal = association.referentialConstraint.principal,
							// dependent = association.referentialConstraint.dependent,
							associationSet = null;
						if (principal) {
							var key = {};
							associationSet = ODataUtils.getAssociationSet(oModel, association);
							key[principal.propertyRef[0].name] = oPropValue;
							var uri = oModel.createKey(
									ODataUtils.getPrincipalEntitySetName(associationSet, oEntityType.name, principal.role),
									key
								);
							var navProperty = ODataUtils.getNavigationPropertyOfAssociationsetInEntity(associationSet, oEntityType);							
							result[navProperty.name] = oPropValue ? { __deferred: { uri: uri } } : null;
						}
					}
				}
			}.bind(this));
			return result;
		},

		getAssociation: function(oModel, oEntityTypeDependent, sPropName) {
			var retval = null;
			jQuery.each(oModel.oMetadata.oMetadata.dataServices.schema, function(schemaIdx, schema) {
				jQuery.each(schema.association, function(associationIdx, association) {
					if (association.referentialConstraint.dependent.role === oEntityTypeDependent.name &&
						association.referentialConstraint.dependent.propertyRef[0].name === sPropName) {
						retval = association;
						retval.namespace = schema.namespace;
						return false;
					}
				}.bind(this));
			}.bind(this));
			return retval;
		},

		getAssociationSet: function(oModel, association) {
			var retval = null;
			jQuery.each(oModel.oMetadata.oMetadata.dataServices.schema, function(schemaIdx, schema) {
				jQuery.each(schema.entityContainer, function(entityContainerIdx, entityContainer) {
					jQuery.each(entityContainer.associationSet, function(associationSetIdx, associationSet) {
						if (associationSet.association === association.namespace + "." + association.name) {
							retval = associationSet;
							return false;
						}
					}.bind(this));
				}.bind(this));
			}.bind(this));
			return retval;
		},

		getPrincipalEntitySetName: function(associationSet, sDependentRoleName, sPrincipalRoleName) {
			var retval = null;
			if (associationSet.end[0].role === sDependentRoleName && associationSet.end[1].role === sPrincipalRoleName) {
				retval = associationSet.end[1].entitySet;
			} else if (associationSet.end[1].role === sDependentRoleName && associationSet.end[0].role === sPrincipalRoleName) {
				retval = associationSet.end[0].entitySet;
			}
			return retval;
		},

		getNavigationPropertyOfAssociationsetInEntity: function(associationSet, oEntityType) {
			var retval = null;
			jQuery.each(oEntityType.navigationProperty, function(i, navigationProperty) {
				if (navigationProperty.relationship === associationSet.association) {
					retval = navigationProperty;
					return false;
				}
			});
			return retval;
		}

	};
	
	return ODataUtils;
		
});