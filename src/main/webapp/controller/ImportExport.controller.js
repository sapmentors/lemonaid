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
		
		onUploadChange: function(event) {
			var that = this,
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
						row.title = that.i18n.getText("importReportTitle", [ row.type.replace(/([A-Z0-9])/g, ' $1').trim(), row.row ]);
						row.priority = "High";
					});
					if (imp.errors && imp.errors.length > 0) {
						imp.errors.push({
						title: that.i18n.getText("importReportErrorsTitle"),
							message: that.i18n.getText("importReportErrors", [ imp.data.length, imp.errors.length ]),
							priority: "High"
						});
					} else {
						var newCount = 0;
						jQuery.each(imp.data, function(idx, row) {
							row.__skip = false;
							if (!row.Id) {
								row.__new = true;
								newCount++;
							} else {
								that.model.read(that.model.createKey("/Mentors", { Id: row.Id }), {
									success: function(data) {
										row.__new = false;
									},
									error: function(error) {
										if (error.statusCode === "404") {
											row.__new = true;
											newCount++;
										} else {
											imp.errors.push({
												type: "ErrorIdentifyingMentor",
												title: that.i18n.getText("importReportTitle", [ row.type.replace(/([A-Z0-9])/g, ' $1').trim(), idx ]),
												message: that.i18n.getText("importErrorIdentifyingMentor", [ row.Id ]),
												priority: "High"
											});
										}
									}
								});
							}
						});
						if (imp.errors && imp.errors.length > 0) {
							imp.errors.push({
								title: that.i18n.getText("importReportErrorsTitle"),
								message: that.i18n.getText("importReportErrors", [ imp.data.length, imp.errors.length ]),
								priority: "High"
							});
						} else {
							that.view.byId("btnUpload").setEnabled(true);
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
					}
					that.ui.setProperty("/errors", imp.errors);
					that.ui.setProperty("/import", imp.data.length + " lines");
				};
				reader.readAsText(file);
			}
			this.busyDialog.close();
		},
		
		onTypeMismatch: function(event) {
			MessageBox.error(this.i18n.getText("importOnlyCsv"));
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
		}

	});

});