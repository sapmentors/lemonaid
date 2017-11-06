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
                    var lines = strCSV.split(/\r?\n|\r/g);
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
                    var objects = {};
                    objects[rowIdx] = {};
                    jQuery.each(row, function (fieldName, field) {
                        fieldName = fieldName.replace(/\s+/g, "");
                        if (that._isValidField(fieldName)) {
                            objects[rowIdx][fieldName] = field;
                        }
                    });
                    delete objects[rowIdx].longitude;
                    delete objects[rowIdx].latitude;
                    delete objects[rowIdx].publicLongitude;
                    delete objects[rowIdx].publicLatitude;
                    //if (objects[rowIdx].ShirtNumber) { objects[rowIdx].ShirtNumber = that._parseInteger(objects[rowIdx].ShirtNumber); } else { delete objects[rowIdx].ShirtNumber; }
                    if (objects[rowIdx].HoursAvailable) {
                        objects[rowIdx].HoursAvailable = that._parseInteger(objects[rowIdx].HoursAvailable);
                    } else {
                        delete objects[rowIdx].HoursAvailable;
                    }
                    if (objects[rowIdx].InterestInMentorCommunicationStrategy) {
                        objects[rowIdx].InterestInMentorCommunicationStrategy = that._parseBoolean(objects[rowIdx].InterestInMentorCommunicationStrategy);
                    } else {
                        delete objects[rowIdx].InterestInMentorCommunicationStrategy;
                    }
                    if (objects[rowIdx].InterestInMentorManagementModel) {
                        objects[rowIdx].InterestInMentorManagementModel = that._parseBoolean(objects[rowIdx].InterestInMentorManagementModel);
                    } else {
                        delete objects[rowIdx].InterestInMentorManagementModel;
                    }
                    if (objects[rowIdx].InterestInMentorMix) {
                        objects[rowIdx].InterestInMentorMix = that._parseBoolean(objects[rowIdx].InterestInMentorMix);
                    } else {
                        delete objects[rowIdx].InterestInMentorMix;
                    }
                    if (objects[rowIdx].InterestInOtherIdeas) {
                        objects[rowIdx].InterestInOtherIdeas = that._parseBoolean(objects[rowIdx].InterestInOtherIdeas);
                    } else {
                        delete objects[rowIdx].InterestInOtherIdeas;
                    }
                    if (objects[rowIdx].TopicLeadInterest) {
                        objects[rowIdx].TopicLeadInterest = that._parseBoolean(objects[rowIdx].TopicLeadInterest);
                    } else {
                        delete objects[rowIdx].TopicLeadInterest;
                    }
                    if (objects[rowIdx].HoursAvailable) {
                        objects[rowIdx].HoursAvailable = that._parseInteger(objects[rowIdx].HoursAvailable);
                    } else {
                        delete objects[rowIdx].HoursAvailable;
                    }
                    //Convert Jamsband Stuff to boolean!
                    if(objects[rowIdx].JambandBarcelona != undefined){
                        objects[rowIdx].JambandBarcelona = that._parseBoolean(objects[rowIdx].JambandBarcelona);
                    }
                    if(objects[rowIdx].JambandLasVegas != undefined){
                        objects[rowIdx].JambandLasVegas = that._parseBoolean(objects[rowIdx].JambandLasVegas);
                    }
                    if(objects[rowIdx].JambandMusician != undefined){
                        objects[rowIdx].JambandMusician = that._parseBoolean(objects[rowIdx].JambandMusician);
                    }

                    //Convert the publicity settings to boolean
                    if(objects[rowIdx].PhonePublic){
                        objects[rowIdx].PhonePublic = that._parseBoolean(objects[rowIdx].PhonePublic);
                    }
                    if(objects[rowIdx].ZipPublic){
                        objects[rowIdx].ZipPublic = that._parseBoolean(objects[rowIdx].ZipPublic);
                    }
                    if(objects[rowIdx].CityPublic){
                        objects[rowIdx].CityPublic = that._parseBoolean(objects[rowIdx].CityPublic);
                    }
                    if(objects[rowIdx].CountryPublic){
                        objects[rowIdx].CountryPublic = that._parseBoolean(objects[rowIdx].CountryPublic);
                    }
                    if(objects[rowIdx].StatePublic){
                        objects[rowIdx].StatePublic = that._parseBoolean(objects[rowIdx].StatePublic);
                    }
                    if(objects[rowIdx].Address1Public){
                        objects[rowIdx].Address1Public = that._parseBoolean(objects[rowIdx].Address1Public);
                    }
                    if(objects[rowIdx].Address2Public){
                        objects[rowIdx].Address2Public = that._parseBoolean(objects[rowIdx].Address2Public);
                    }
                    if(objects[rowIdx].CompanyPublic){
                        objects[rowIdx].CompanyPublic = that._parseBoolean(objects[rowIdx].CompanyPublic);
                    }
                    if(objects[rowIdx].JobTitlePublic){
                        objects[rowIdx].JobTitlePublic = that._parseBoolean(objects[rowIdx].JobTitlePublic);
                    }
                    if(objects[rowIdx].Email1Public){
                        objects[rowIdx].Email1Public = that._parseBoolean(objects[rowIdx].Email1Public);
                    }
                    if(objects[rowIdx].Email2Public){
                        objects[rowIdx].Email2Public = that._parseBoolean(objects[rowIdx].Email2Public);
                    }
                    if(objects[rowIdx].SoftSkillsPublic){
                        objects[rowIdx].SoftSkillsPublic = that._parseBoolean(objects[rowIdx].SoftSKILLSPublic);
                    }

                    //Add objects[rowIdx]s of JoinColum Entities
                    if(objects[rowIdx].Language1Id){
                        objects[rowIdx]["Language1"] = that.model.oData["Languages('"+objects[rowIdx].Language1Id+"')"];
                    }
                    if(objects[rowIdx].Language2Id){
                        objects[rowIdx]["Language2"] = that.model.oData["Languages('"+objects[rowIdx].Language2Id+"')"];
                    }
                    if(objects[rowIdx].Language3Id){
                        objects[rowIdx]["Language3"] = that.model.oData["Languages('"+objects[rowIdx].Language3Id+"')"];
                    }
                    if(objects[rowIdx].RegionId){
                        objects[rowIdx]["Region"] = that.model.oData["Regions('"+objects[rowIdx].RegionId+"')"];
                    }
                    if(objects[rowIdx].RelationshipToSapId){
                        objects[rowIdx]["RelationshipToSap"] = that.model.oData["RelationshipsToSap('"+objects[rowIdx].RelationshipToSapId+"')"];
                    }
                    if(objects[rowIdx].StatusId){
                         objects[rowIdx]["MentorStatus"] = that.model.oData["MentorStatuses('"+objects[rowIdx].StatusId+"')"];
                    }
                    if(objects[rowIdx].CountryId){
                         objects[rowIdx]["Country"] = that.model.oData["Countries('"+objects[rowIdx].CountryId+"')"];
                    }
                    if(objects[rowIdx].Industry1Id){
                         objects[rowIdx]["Industry1"] = that.model.oData["Industries('"+objects[rowIdx].Industry1Id+"')"];
                    }
                    if(objects[rowIdx].Industry2Id){
                        objects[rowIdx]["Industry2"] = that.model.oData["Industries('"+objects[rowIdx].Industry2Id+"')"];
                    }
                    if(objects[rowIdx].Industry3Id){
                        objects[rowIdx]["Industry3"] = that.model.oData["Industries('"+objects[rowIdx].Industry3Id+"')"];
                    }
                    if(objects[rowIdx].ShirtMFId){
                        objects[rowIdx]["ShirtMF"] = that.model.oData["Genders('"+objects[rowIdx].shirtMF+"')"];
                    }
                    if(objects[rowIdx].ShirtSizeId){
                        objects[rowIdx]["ShirtSize"] = that.model.oData["Sizes('"+objects[rowIdx].ShirtSizeId+"')"];
                    }
                    if(objects[rowIdx].SapExpertise1Id){
                        objects[rowIdx]["SapExpertise1"] = that.model.oData["SapSoftwareSolutions('"+objects[rowIdx].SapExpertise1Id+"')"];
                    }
                    if(objects[rowIdx].SapExpertise2Id){
                        objects[rowIdx]["SapExpertise2"] = that.model.oData["SapSoftwareSolutions('"+objects[rowIdx].SapExpertise2Id+"')"];
                    }
                    if(objects[rowIdx].SapExpertise3Id){
                        objects[rowIdx]["SapExpertise3"] = that.model.oData["SapSoftwareSolutions('"+objects[rowIdx].SapExpertise3Id+"')"];
                    }
                    if(objects[rowIdx].SapExpertise1LevelId){
                        objects[rowIdx]["SapExpertise1Level"] = that.model.oData["ExpertiseLevels('"+objects[rowIdx].SapExpertise1LevelId+"')"];
                    }
                    if(objects[rowIdx].SapExpertise2LevelId){
                        objects[rowIdx]["SapExpertise2Level"] = that.model.oData["ExpertiseLevels('"+objects[rowIdx].SapExpertise2LevelId+"')"];
                    }
                    if(objects[rowIdx].SapExpertise3LevelId){
                        objects[rowIdx]["SapExpertise3Level"] = that.model.oData["ExpertiseLevels('"+objects[rowIdx].SapExpertise3LevelId+"')"];
                    }
                    if(objects[rowIdx].Topic1Id){
                         objects[rowIdx]["Topic1"] = that.model.oData["Topics('"+objects[rowIdx].Topic1Id+"')"];
                    }
                    if(objects[rowIdx].Topic2Id){
                        objects[rowIdx]["Topic2"] = that.model.oData["Topics('"+objects[rowIdx].Topic2Id+"')"];
                    }
                    if(objects[rowIdx].Topic3Id){
                        objects[rowIdx]["Topic3"] = that.model.oData["Topics('"+objects[rowIdx].Topic3Id+"')"];
                    }
                    if(objects[rowIdx].SoftSkill1Id){
                        objects[rowIdx]["SoftSkill1"] = that.model.oData["SoftSkills('"+objects[rowIdx].SoftSkill1Id+"')"];
                    }
                    if(objects[rowIdx].SoftSkill2Id){
                        objects[rowIdx]["SoftSkill2"] = that.model.oData["SoftSkills('"+objects[rowIdx].SoftSkill2Id+"')"];
                    }
                    if(objects[rowIdx].SoftSkill3Id){
                        objects[rowIdx]["SoftSkill3"] = that.model.oData["SoftSkills('"+objects[rowIdx].SoftSkill3Id+"')"];
                    }
                    if(objects[rowIdx].SoftSkill4Id){
                        objects[rowIdx]["SoftSkill4"] = that.model.oData["SoftSkills('"+objects[rowIdx].SoftSkill4Id+"')"];
                    }
                    if(objects[rowIdx].SoftSkill5Id){
                        objects[rowIdx]["SoftSkill5"] = that.model.oData["SoftSkills('"+objects[rowIdx].SoftSkill5Id+"')"];
                    }
                    if(objects[rowIdx].SoftSkill6Id){
                        objects[rowIdx]["SoftSkill6"] = that.model.oData["SoftSkills('"+objects[rowIdx].SoftSkill6Id+"')"];
                    }
                    requests.push(new Promise(function (resolve) {
                        if (row.__new) {
                            if (!objects[rowIdx].Id) {
                                objects[rowIdx].Id = that.guidGenerator.generateGuid();
                            }
                            for (var i in objects[rowIdx]) {
                                if(objects[rowIdx][i] == undefined){
                                    delete objects[rowIdx][i];
                                }else if (objects[rowIdx][i].length === 0) {
                                    delete objects[rowIdx][i];
                                }
                            }
                            that.model.create(
                                "/Mentors",
                                objects[rowIdx], {
                                    success: function (data) {
                                        newCount++;
                                        resolve();
                                    },
                                    error: function (error) {
                                        imp.errors.push({
                                            title: that.i18n.getText("importCreateErrorTitle"),
                                            message: that.i18n.getText("importCreateError", [
                                                objects[rowIdx].Id,
                                                objects[rowIdx].fullName,
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
                                    Id: objects[rowIdx].Id
                                }),
                                objects[rowIdx], {
                                    success: function (data) {
                                        updateCount++;
                                        resolve();
                                    },
                                    error: function (error) {
                                        imp.errors.push({
                                            title: that.i18n.getText("importUpdateErrorTitle"),
                                            message: that.i18n.getText("importUpdateError", [
                                                objects[rowIdx].Id,
                                                objects[rowIdx].fullName,
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
