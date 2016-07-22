sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
	"sap/ui/model/json/JSONModel"
], function(BaseController, JSONModel) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.ImportExport", {

		/* =========================================================== */
		/* lifecycle methods                                           */
		/* =========================================================== */

		/**
		 * Called when the view controller is instantiated.
		 * @public
		 */
        onInit: function() {
        	var that = this;
        	this.view = this.getView();
        	this.component = this.getComponent();
        	this.model = this.component.getModel();
        	this.router = this.getRouter();
        	this.i18n = this.component.getModel("i18n").getResourceBundle();
        	this.ui = new JSONModel();
        	this.setModel(this.ui, "ui");
        	this.model.metadataLoaded().then(function() {
        		that.metadata = that.component.metadata;
        		that._buildFieldGroups("Model.Mentor");
        		that.ui.refresh();
        	});
        },

		/* =========================================================== */
		/* event handlers                                              */
		/* =========================================================== */
		onSelectAll: function(event) {
			this._toggleFieldGroup(event.getSource().getBindingContext("ui").sPath, true);
		},
		
		onSelectNone: function(event) {
			this._toggleFieldGroup(event.getSource().getBindingContext("ui").sPath, false);
		},

		/* =========================================================== */
		/* internal methods                                            */
		/* =========================================================== */
		
		_toggleFieldGroup: function(path, state) {
			jQuery.each(this.ui.getProperty(path + "/Fields"), function(idx, field) {
				field.Value = state;	
			});
			this.ui.refresh();
		},
		
		_buildFieldGroups: function(entityType) {
			var fieldGroups = [];
			var fieldGroup;
			this.ui.setProperty("/FieldGroups", fieldGroups);
			jQuery.each(this.component.metadata.oMetadata.dataServices.schema, function(schemaIdx, schema) {
				jQuery.each(schema.annotations, function(annodationsIdx, annotations) {
					if (annotations.target === "Model.Mentor") {
						jQuery.each(annotations.annotation, function(annotationIdx, annotation) {
							if (annotation.term === "UI.FieldGroup") {
								jQuery.each(annotation.extensions, function(extensionIdx, extension) {
									if (extension.name === "Qualifier") {
										fieldGroup = { Id: extension.value, Name: extension.value.replace(/([A-Z0-9])/g, ' $1'), Fields: [] };
										fieldGroups.push(fieldGroup);
									}
								});
								if (annotation.record.type === "UI.FieldGroupType") {
									jQuery.each(annotation.record.propertyValue, function(propertyValueIdx, propertyValue) {
										if (propertyValue.property === "Data") {
											jQuery.each(propertyValue.collection.record, function(recordIdx, record) {
												if (record.type === "UI.DataField") {
													jQuery.each(record.propertyValue, function(propValueIdx, propValue) {
														if (propValue.property === "Value") {
															fieldGroup.Fields.push( { Id: propValue.path, Name: propValue.path.replace(/([A-Z0-9])/g, ' $1'), Value: propValue.path === "Id" } );
														}
													});
												}
											});
										}
									});
								}
							}
						});
					}
				});
			});
		}

    });

});