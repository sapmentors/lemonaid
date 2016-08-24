sap.ui.define([
	"com/sap/mentors/lemonaid/controller/BaseController",
	"sap/ui/model/json/JSONModel",
	"sap/m/MessageBox",
	"sap/m/MessageToast",
	"sap/m/BusyDialog",
	"sap/ui/core/util/Export",
	"sap/ui/core/util/ExportColumn",
	"sap/ui/core/util/ExportTypeCSV",
	"com/sap/mentors/lemonaid/util/papaparse"
], function(BaseController, JSONModel, MessageBox, MessageToast, BusyDialog, Export, ExportColumn, ExportTypeCSV) {
	"use strict";

	return BaseController.extend("com.sap.mentors.lemonaid.controller.ImportExport", {

		busyDialog: new BusyDialog(),

		/* =========================================================== */
		/* lifecycle methods										   */
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
			this.metadata = this.component.metadata;
			this.model.metadataLoaded().then(function() {
				that.metadata = that.component.metadata;
				that._buildFieldGroups("Model.Mentor");
				that.ui.refresh();
			});
		},

		/* =========================================================== */
		/* event handlers											  */
		/* =========================================================== */
		onSelectAll: function(event) {
			this._toggleFieldGroup(event.getSource().getBindingContext("ui").sPath, true);
		},

		onSelectNone: function(event) {
			this._toggleFieldGroup(event.getSource().getBindingContext("ui").sPath, false);
		},

		onDownload: function(event) {
			var exp = new Export({
				exportType: new ExportTypeCSV( { separatorChar : ";" } ),
				models: this.model,
				rows : { path : "/Mentors" }
			});
			jQuery.each(this.ui.getProperty("/FieldGroups"), function(FieldGroupIdx, fieldGroup) {
				var e = exp;
				jQuery.each(fieldGroup.Fields, function(fieldIdx, field) {
					if (field.Value) {
						e.addColumn(new ExportColumn({
								name : field.Name,
								template : { content : "{" + field.Id + "}" }
							}));
					}
				});
			});
			exp.saveFile("mentors").catch(function(oError) {
				MessageBox.error("Error while downloading export file\n\n" + oError);
			}).then(function() {
				exp.destroy();
			});
		},

		onTypeMismatch: function(event) {
			MessageBox.error(this.i18n.getText("importOnlyCsv"));
		},

		onUploadChange: function(event) {
			var that = this,
				errorCount = 0,
				warningCount = 0,
				newCount = 0,
				file = event.getParameter("files") && event.getParameter("files")[0];
			this.view.byId("btnUpload").setEnabled(false);
			that.ui.setProperty("/errors", []);
			this.busyDialog.setTitle(this.i18n.getText("importScanningTitle"));
			this.busyDialog.setText(this.i18n.getText("importScanningText"));
			this.busyDialog.open();
			if (file && window.FileReader) {
				var reader = new FileReader();
				reader.onload = function(evn) {
					var strCSV = evn.target.result; //string in CSV
					var lines = strCSV.split('\n');
					lines[0] = lines[0].replace(/ /g,'');
					strCSV = lines.join('\n');
					var imp = Papa.parse(strCSV, { header: true });
					jQuery.each(imp.errors, function(idx, row) {
						row.title = that.i18n.getText("importReportTitle", [ row.type.replace(/([A-Z0-9])/g, ' $1').trim(), row.row + 2 ]);
						row.priority = "High";
						errorCount++;
					});
					if (imp.errors && imp.errors.length > 0) {
						imp.errors.push({
							title: that.i18n.getText("importReportErrorsTitle"),
							message: that.i18n.getText("importReportErrors", [ imp.data.length, imp.errors.length, 0 ]),
							priority: "High"
						});
						errorCount++;
					} else {
						jQuery.each(imp.meta.fields, function(idx, field) {
							if (!that._isValidField(field.replace(/\s+/g, ''))) {
								var type = "ErrorIdentifyingField";
								imp.errors.push({
									title: that.i18n.getText("importErrorIdentifyingFieldTitle", field),
									message: that.i18n.getText("importErrorIdentifyingField", field),
									priority: "Medium"
								});
								warningCount++;
							}
						});
						var checks = [];
						jQuery.each(imp.data, function(idx, row) {
							checks.push(new Promise(function(resolve) {
								row.__skip = false;
								if (!row.Id) {
									row.__new = true;
									newCount++;
									resolve();
								} else {
									that.model.read(that.model.createKey("/Mentors", { Id: row.Id }), {
										success: function(data) {
											row.__new = false;
											resolve();
										},
										error: function(error) {
											if (error.statusCode === "404") {
												row.__new = true;
												newCount++;
												resolve();
											} else {
												imp.errors.push({
													type: "ErrorIdentifyingMentor",
													title: that.i18n.getText("importReportTitle", [ row.type.replace(/([A-Z0-9])/g, ' $1').trim(), idx ]),
													message: that.i18n.getText("importErrorIdentifyingMentor", [ row.Id ]),
													priority: "High"
												});
												errorCount++;
												resolve();
											}
										}
									});
								}
							}));
						});
						Promise.all(checks).then(function() {
							if (errorCount + warningCount > 0) {
								imp.errors.push({
									title: that.i18n.getText("importReportErrorsTitle"),
									message: that.i18n.getText("importReportErrors", [ imp.data.length, errorcount, warningCount ]) +
											 (warningCount > 0 ? " " + that.i18n.getText("importReportErrorsCanContinue") : ""),
									priority: errorCount > 0 ? "High" : "Medium"
								});
							} else {
								imp.errors.push({
									title: that.i18n.getText("importReportResultTitle"),
									picture: jQuery.sap.getModulePath("com.sap.mentors.lemonaid.images") + "/heart.png",
									message: that.i18n.getText("importReportResult", [
										imp.data.length,
										imp.meta.fields.length,
										newCount,
										imp.data.length - newCount
									])
								});
							}
							that.view.byId("btnUpload").setEnabled(errorCount === 0);
							that.ui.setProperty("/import", imp);
						});
					}
					that.ui.setProperty("/import", imp);
				};
				reader.readAsText(file);
			}
			this.busyDialog.close();
		},

        onUpload: function(event) {
            var that = this,
			    imp = this.ui.getProperty("/import"),
                errorCount = 0,
                updateCount = 0,
                newCount = 0,
                requests = [];
            imp.errors = [];
            that.view.byId("btnUpload").setEnabled(false);
            this.model.detachRequestFailed(
                this.component._oErrorHandler._requestFailedHandler,
                this.component._oErrorHandler);
            jQuery.each(imp.data, function(rowIdx, row) {
            	if (!row.__skip) {
					var object = {};
					jQuery.each(row, function(fieldName, field) {
						fieldName = fieldName.replace(/\s+/g, '');
						if (that._isValidField(fieldName)) {
							object[fieldName] = field;
						}
					});
                    requests.push(new Promise(function(resolve) {
    					if (row.__new) {
                            that.model.create(
                                "/Mentors",
                                object,
                                {
                                    success: function(data) {
                                        newCount++;
                                        resolve();
                                    },
                                    error: function(error) {
                                        imp.errors.push({
        									title: that.i18n.getText("importCreateErrorTitle"),
        									message: that.i18n.getText("importCreateError", [
        										object.Id,
        										object.fullName,
                                                error.responseText
        									])
        								});
                                        errorCount++;
                                        resolve();
                                    }
                                }
                            );
                        } else {
                            that.model.update(
                                that.model.createKey("/Mentors", { Id: object.Id }),
                                object,
                                {
                                    success: function(data) {
                                        updateCount++;
                                        resolve();
                                    },
                                    error: function(error) {
                                        imp.errors.push({
        									title: that.i18n.getText("importUpdateErrorTitle"),
        									message: that.i18n.getText("importUpdateError", [
        										object.Id,
        										object.fullName,
                                                error.responseText
        									])
        								});
                                        errorCount++;
                                        resolve();
                                    }
                                }
                            );
                        }
                    }));
                    Promise.all(requests).then(function() {
                        if (errorCount === 0) {
                            imp.errors.push({
                                title: that.i18n.getText("importSuccessTitle"),
                                picture: jQuery.sap.getModulePath("com.sap.mentors.lemonaid.images") + "/heart.png",
                                message: that.i18n.getText("importSuccess", [
                                    imp.data.length,
                                    newCount,
                                    updateCount
                                ])
                            });
                        }
                        that.model.attachRequestFailed(
                            that.component._oErrorHandler._requestFailedHandler,
                            that.component._oErrorHandler);
                        that.ui.setProperty("/import", imp);
                    });
            	}
            });
		},

		/* =========================================================== */
		/* internal methods											*/
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
										fieldGroup = { Id: extension.value, Name: extension.value.replace(/([A-Z0-9])/g, ' $1').trim(), Fields: [] };
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
															fieldGroup.Fields.push( { Id: propValue.path, Name: propValue.path.replace(/([A-Z0-9])/g, ' $1').trim(), Value: propValue.path === "Id" } );
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
		},

		_isValidField: function(fieldName) {
			for (var i = 0; i < this.metadata.getServiceMetadata().dataServices.schema.length; i++) {
				var schema = this.metadata.getServiceMetadata().dataServices.schema[i];
				for (var j = 0; j < schema.entityType.length; j++) {
					var entityType = schema.entityType[j];
					if (entityType.name === "Mentor") {
						for (var k = 0; k < entityType.property.length; k++) {
							var property = entityType.property[k];
							if (property.name === fieldName) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}

	});

});
