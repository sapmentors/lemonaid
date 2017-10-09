/* global sap, jQuery, Papa, Promise */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageBox",
    "sap/m/MessageToast",
    "sap/m/BusyDialog",
    "sap/ui/core/util/Export",
    "sap/ui/core/util/ExportColumn",
    "sap/ui/core/util/ExportTypeCSV",
    "com/sap/mentors/lemonaid/util/GuidGenerator",
    "com/sap/mentors/lemonaid/util/papaparse"
], function (BaseController, JSONModel, MessageBox, MessageToast, BusyDialog, Export, ExportColumn, ExportTypeCSV, GuidGenerator) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.ImportExport", {

        busyDialog: new BusyDialog(),
        guidGenerator: GuidGenerator,

        /* =========================================================== */
        /* lifecycle methods										   */
        /* =========================================================== */

        /**
         * Called when the view controller is instantiated.
         * @public
         */
        onInit: function () {
            var that = this;
            this.view = this.getView();
            this.component = this.getComponent();
            this.model = this.component.getModel();
            this.router = this.getRouter();
            this.i18n = this.component.getModel("i18n").getResourceBundle();
            this.ui = new JSONModel();
            this.setModel(this.ui, "ui");
            this.metadata = this.component.metadata;
            this.model.metadataLoaded().then(function () {
                that.metadata = that.component.metadata;
                that._buildFieldGroups("Model.Mentor");
                that.ui.refresh();
            });
        },

        /* =========================================================== */
        /* event handlers											  */
        /* =========================================================== */
        onSelectAll: function (event) {
            this._toggleFieldGroup(event.getSource().getBindingContext("ui").sPath, true);
        },

        onSelectNone: function (event) {
            this._toggleFieldGroup(event.getSource().getBindingContext("ui").sPath, false);
        },

        onDownload: function (event) {
            var exp = new Export({
                exportType: new ExportTypeCSV({
                    separatorChar: ";"
                }),
                models: this.model,
                rows: {
                    path: "/Mentors"
                }
            });
            jQuery.each(this.ui.getProperty("/FieldGroups"), function (FieldGroupIdx, fieldGroup) {
                var e = exp;
                jQuery.each(fieldGroup.Fields, function (fieldIdx, field) {
                    if (field.Value) {
                        e.addColumn(new ExportColumn({
                            name: field.Name,
                            template: {
                                content: "{" + field.Id + "}"
                            }
                        }));
                    }
                });
            });
            exp.saveFile("mentors").catch(function (oError) {
                MessageBox.error("Error while downloading export file\n\n" + oError);
            }).then(function () {
                exp.destroy();
            });
        },

        onTypeMismatch: function (event) {
            MessageBox.error(this.i18n.getText("importOnlyCsv"));
        },

        onUploadChange: function (event) {
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
                reader.onload = function (evn) {
                    var strCSV = evn.target.result; //string in CSV
                    var lines = strCSV.split("\n");
                    lines[0] = lines[0].replace(/ /g, "");
                    strCSV = lines.join("\n");
                    var imp = Papa.parse(strCSV, {
                        header: true
                    });
                    jQuery.each(imp.errors, function (idx, row) {
                        row.title = that.i18n.getText("importReportTitle", [row.type.replace(/([A-Z0-9])/g, " $1").trim(), row.row + 2]);
                        row.priority = "High";
                        errorCount++;
                    });
                    if (imp.errors && imp.errors.length > 0) {
                        imp.errors.push({
                            title: that.i18n.getText("importReportErrorsTitle"),
                            message: that.i18n.getText("importReportErrors", [imp.data.length, imp.errors.length, 0]),
                            priority: "High"
                        });
                        errorCount++;
                    } else {
                        jQuery.each(imp.meta.fields, function (idx, field) {
                            if (!that._isValidField(field.replace(/\s+/g, ""))) {
                                // RvhHof var not used? var type = "ErrorIdentifyingField";
                                imp.errors.push({
                                    title: that.i18n.getText("importErrorIdentifyingFieldTitle", field),
                                    message: that.i18n.getText("importErrorIdentifyingField", field),
                                    priority: "Medium"
                                });
                                warningCount++;
                            }
                        });
                        var checks = [];
                        jQuery.each(imp.data, function (idx, row) {
                            checks.push(new Promise(function (resolve) {
                                row.__skip = false;
                                if (!row.Id) {
                                    row.__new = true;
                                    newCount++;
                                    resolve();
                                } else {
                                    that.model.read(that.model.createKey("/Mentors", {
                                        Id: row.Id
                                    }), {
                                        success: function (data) {
                                            row.__new = false;
                                            resolve();
                                        },
                                        error: function (error) {
                                            if (error.statusCode === "404") {
                                                row.__new = true;
                                                newCount++;
                                                resolve();
                                            } else {
                                                imp.errors.push({
                                                    type: "ErrorIdentifyingMentor",
                                                    title: that.i18n.getText("importReportTitle", [row.type.replace(/([A-Z0-9])/g, " $1").trim(), idx]),
                                                    message: that.i18n.getText("importErrorIdentifyingMentor", [row.Id]),
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
                        Promise.all(checks).then(function () {
                            if (errorCount + warningCount > 0) {
                                imp.errors.push({
                                    title: that.i18n.getText("importReportErrorsTitle"),
                                    message: that.i18n.getText("importReportErrors", [imp.data.length, errorCount, warningCount]) +
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

        onUpload: function (event) {
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
            jQuery.each(imp.data, function (rowIdx, row) {
                if (!row.__skip) {
                    var object = {};
                    jQuery.each(row, function (fieldName, field) {
                        fieldName = fieldName.replace(/\s+/g, "");
                        if (that._isValidField(fieldName)) {
                            object[fieldName] = field;
                        }
                    });
                    delete object.longitude;
                    delete object.latitude;
                    delete object.publicLongitude;
                    delete object.publicLatitude;
                    //if (object.ShirtNumber) { object.ShirtNumber = that._parseInteger(object.ShirtNumber); } else { delete object.ShirtNumber; }
                    if (object.HoursAvailable) {
                        object.HoursAvailable = that._parseInteger(object.HoursAvailable);
                    } else {
                        delete object.HoursAvailable;
                    }
                    if (object.InterestInMentorCommunicationStrategy) {
                        object.InterestInMentorCommunicationStrategy = that._parseBoolean(object.InterestInMentorCommunicationStrategy);
                    } else {
                        delete object.InterestInMentorCommunicationStrategy;
                    }
                    if (object.InterestInMentorManagementModel) {
                        object.InterestInMentorManagementModel = that._parseBoolean(object.InterestInMentorManagementModel);
                    } else {
                        delete object.InterestInMentorManagementModel;
                    }
                    if (object.InterestInMentorMix) {
                        object.InterestInMentorMix = that._parseBoolean(object.InterestInMentorMix);
                    } else {
                        delete object.InterestInMentorMix;
                    }
                    if (object.InterestInOtherIdeas) {
                        object.InterestInOtherIdeas = that._parseBoolean(object.InterestInOtherIdeas);
                    } else {
                        delete object.InterestInOtherIdeas;
                    }
                    if (object.TopicLeadInterest) {
                        object.TopicLeadInterest = that._parseBoolean(object.TopicLeadInterest);
                    } else {
                        delete object.TopicLeadInterest;
                    }
                    if (object.HoursAvailable) {
                        object.HoursAvailable = that._parseInteger(object.HoursAvailable);
                    } else {
                        delete object.HoursAvailable;
                    }
                    //Convert Jamsband Stuff to boolean!
                    if(object.JambandBarcelona != undefined){
                        object.JambandBarcelona = that._parseBoolean(object.JambandBarcelona);
                    }
                    if(object.JambandLasVegas != undefined){
                        object.JambandLasVegas = that._parseBoolean(object.JambandLasVegas);
                    }
                    if(object.JambandMusician != undefined){
                        object.JambandMusician = that._parseBoolean(object.JambandMusician);
                    }

                    //Convert the publicity settings to boolean
                    if(object.PhonePublic){
                        object.PhonePublic = that._parseBoolean(object.PhonePublic);
                    }
                    if(object.ZipPublic){
                        object.ZipPublic = that._parseBoolean(object.ZipPublic);
                    }
                    if(object.CityPublic){
                        object.CityPublic = that._parseBoolean(object.CityPublic);
                    }
                    if(object.CountryPublic){
                        object.CountryPublic = that._parseBoolean(object.CountryPublic);
                    }
                    if(object.StatePublic){
                        object.StatePublic = that._parseBoolean(object.StatePublic);
                    }
                    if(object.Address1Public){
                        object.Address1Public = that._parseBoolean(object.Address1Public);
                    }
                    if(object.Address2Public){
                        object.Address2Public = that._parseBoolean(object.Address2Public);
                    }
                    if(object.CompanyPublic){
                        object.CompanyPublic = that._parseBoolean(object.CompanyPublic);
                    }
                    if(object.JobTitlePublic){
                        object.JobTitlePublic = that._parseBoolean(object.JobTitlePublic);
                    }
                    if(object.Email1Public){
                        object.Email1Public = that._parseBoolean(object.Email1Public);
                    }
                    if(object.Email2Public){
                        object.Email2Public = that._parseBoolean(object.Email2Public);
                    }
                    if(object.SoftSkillsPublic){
                        object.SoftSkillsPublic = that._parseBoolean(object.SoftSKILLSPublic);
                    }

                    //Add Objects of JoinColum Entities
                    if(object.Language1Id){
                        object["Language1"] = that.model.oData["Languages('"+object.Language1Id+"')"];
                    }
                    if(object.Language2Id){
                        object["Language2"] = that.model.oData["Languages('"+object.Language2Id+"')"];
                    }
                    if(object.Language3Id){
                        object["Language3"] = that.model.oData["Languages('"+object.Language3Id+"')"];
                    }
                    if(object.RegionId){
                        object["Region"] = that.model.oData["Regions('"+object.RegionId+"')"];
                    }
                    if(object.RelationshipToSapId){
                        object["RelationshipToSap"] = that.model.oData["RelationshipsToSap('"+object.RelationshipToSapId+"')"];
                    }
                    if(object.StatusId){
                         object["MentorStatus"] = that.model.oData["MentorStatuses('"+object.StatusId+"')"];
                    }
                    if(object.CountryId){
                         object["Country"] = that.model.oData["Countries('"+object.CountryId+"')"];
                    }
                    if(object.Industry1Id){
                         object["Industry1"] = that.model.oData["Industries('"+object.Industry1Id+"')"];
                    }
                    if(object.Industry2Id){
                        object["Industry2"] = that.model.oData["Industries('"+object.Industry2Id+"')"];
                    }
                    if(object.Industry3Id){
                        object["Industry3"] = that.model.oData["Industries('"+object.Industry3Id+"')"];
                    }
                    if(object.ShirtMFId){
                        object["ShirtMF"] = that.model.oData["Genders('"+object.shirtMF+"')"];
                    }
                    if(object.ShirtSizeId){
                        object["ShirtSize"] = that.model.oData["Sizes('"+object.ShirtSizeId+"')"];
                    }
                    if(object.SapExpertise1Id){
                        object["SapExpertise1"] = that.model.oData["SapSoftwareSolutions('"+object.SapExpertise1Id+"')"];
                    }
                    if(object.SapExpertise2Id){
                        object["SapExpertise2"] = that.model.oData["SapSoftwareSolutions('"+object.SapExpertise2Id+"')"];
                    }
                    if(object.SapExpertise3Id){
                        object["SapExpertise3"] = that.model.oData["SapSoftwareSolutions('"+object.SapExpertise3Id+"')"];
                    }
                    if(object.SapExpertise1LevelId){
                        object["SapExpertise1Level"] = that.model.oData["ExpertiseLevels('"+object.SapExpertise1LevelId+"')"];
                    }
                    if(object.SapExpertise2LevelId){
                        object["SapExpertise2Level"] = that.model.oData["ExpertiseLevels('"+object.SapExpertise2LevelId+"')"];
                    }
                    if(object.SapExpertise3LevelId){
                        object["SapExpertise3Level"] = that.model.oData["ExpertiseLevels('"+object.SapExpertise3LevelId+"')"];
                    }
                    if(object.Topic1Id){
                         object["Topic1"] = that.model.oData["Topics('"+object.Topic1Id+"')"];
                    }
                    if(object.Topic2Id){
                        object["Topic2"] = that.model.oData["Topics('"+object.Topic2Id+"')"];
                    }
                    if(object.Topic3Id){
                        object["Topic3"] = that.model.oData["Topics('"+object.Topic3Id+"')"];
                    }
                    if(object.SoftSkill1Id){
                        object["SoftSkill1"] = that.model.oData["SoftSkills('"+object.SoftSkill1Id+"')"];
                    }
                    if(object.SoftSkill2Id){
                        object["SoftSkill2"] = that.model.oData["SoftSkills('"+object.SoftSkill2Id+"')"];
                    }
                    if(object.SoftSkill3Id){
                        object["SoftSkill3"] = that.model.oData["SoftSkills('"+object.SoftSkill3Id+"')"];
                    }
                    if(object.SoftSkill4Id){
                        object["SoftSkill4"] = that.model.oData["SoftSkills('"+object.SoftSkill4Id+"')"];
                    }
                    if(object.SoftSkill5Id){
                        object["SoftSkill5"] = that.model.oData["SoftSkills('"+object.SoftSkill5Id+"')"];
                    }
                    if(object.SoftSkill6Id){
                        object["SoftSkill6"] = that.model.oData["SoftSkills('"+object.SoftSkill6Id+"')"];
                    }
                    requests.push(new Promise(function (resolve) {
                        if (row.__new) {
                            if (!object.Id) {
                                object.Id = that.guidGenerator.generateGuid();
                            }
                            for (var i in object) {
                                if(object[i] == undefined){
                                    delete object[i];
                                }else if (object[i].length === 0) {
                                    delete object[i];
                                }
                            }
                            that.model.create(
                                "/Mentors",
                                object, {
                                    success: function (data) {
                                        newCount++;
                                        resolve();
                                    },
                                    error: function (error) {
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
                                that.model.createKey("/Mentors", {
                                    Id: object.Id
                                }),
                                object, {
                                    success: function (data) {
                                        updateCount++;
                                        resolve();
                                    },
                                    error: function (error) {
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
                    Promise.all(requests).then(function () {
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

        _toggleFieldGroup: function (path, state) {
            jQuery.each(this.ui.getProperty(path + "/Fields"), function (idx, field) {
                field.Value = state;
            });
            this.ui.refresh();
        },

        _buildFieldGroups: function (entityType) {
            var fieldGroups = [];
            var fieldGroup;
            this.ui.setProperty("/FieldGroups", fieldGroups);
            jQuery.each(this.component.metadata.oMetadata.dataServices.schema, function (schemaIdx, schema) {
                jQuery.each(schema.annotations, function (annodationsIdx, annotations) {
                    if (annotations.target === "Model.Mentor") {
                        jQuery.each(annotations.annotation, function (annotationIdx, annotation) {
                            if (annotation.term === "UI.FieldGroup") {
                                jQuery.each(annotation.extensions, function (extensionIdx, extension) {
                                    if (extension.name === "Qualifier") {
                                        fieldGroup = {
                                            Id: extension.value,
                                            Name: extension.value.replace(/([A-Z0-9])/g, " $1").trim(),
                                            Fields: []
                                        };
                                        fieldGroups.push(fieldGroup);
                                    }
                                });
                                if (annotation.record.type === "UI.FieldGroupType") {
                                    jQuery.each(annotation.record.propertyValue, function (propertyValueIdx, propertyValue) {
                                        if (propertyValue.property === "Data") {
                                            jQuery.each(propertyValue.collection.record, function (recordIdx, record) {
                                                if (record.type === "UI.DataField") {
                                                    jQuery.each(record.propertyValue, function (propValueIdx, propValue) {
                                                        if (propValue.property === "Value") {
                                                            fieldGroup.Fields.push({
                                                                Id: propValue.path,
                                                                Name: propValue.path.replace(/([A-Z0-9])/g, " $1").trim(),
                                                                Value: propValue.path === "Id"
                                                            });
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

        _isValidField: function (fieldName) {
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
        },

        _parseBoolean: function (value) {
            if (value) {
                if (value.toUpperCase() === "YES" ||
                    value.toUpperCase() === "TRUE" ||
                    value.toUpperCase() === "Y" ||
                    value.toUpperCase() === "T" ||
                    value === "1" ||
                    value === true ||
                    value === 1) {
                    return true;
                }
            }
            return false;
        },

        _parseInteger: function (value) {
            if (typeof value === "undefined") {
                return;
            }
            if (typeof value === "number") {
                return value;
            }
            var res = value.split(" ");
            for (var i = 0; i < res.length; i++) {
                var iValue = parseInt(res[i]);
                if (!isNaN(iValue)) {
                    return iValue;
                }
            }
            return 0;
        }

    });

});
