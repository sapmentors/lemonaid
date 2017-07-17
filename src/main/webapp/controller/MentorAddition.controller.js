/* global sap */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "sap/ui/model/Filter",
    "sap/m/MessageToast",
    "com/sap/mentors/lemonaid/util/GuidGenerator",
], function (BaseController, JSONModel, Filter, MessageToast, GuidGenerator) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.MentorAddition", {

        guidGenerator: GuidGenerator,

        /* =========================================================== */
        /* lifecycle methods                                           */
        /* =========================================================== */

        /**
         * Called when the master list controller is instantiated. It sets up the event handling for the master/detail communication and other lifecycle tasks.
         * @public
         */
        onInit: function () {
            var oEventBus = sap.ui.getCore().getEventBus();
            oEventBus.subscribe("MyChannelAddition", "notifyMentorAdditionHandler", this.handleBlockCallback, this);

            this.view = this.getView();
            this.component = this.getComponent();
            this.model = this.component.getModel();
            this.router = this.getRouter();
            this.i18n = this.component.getModel("i18n").getResourceBundle();
            this.config = this.component.getModel("config");
            this.ui = new JSONModel({
                isEditMode: true,
            });
            this.view.setModel(this.ui, "ui");
            this.router.getRoute("MentorAddition").attachMatched(this.onRouteMatched, this);

            // Remove sections/blocks that are not meant for a general audience
            this.config._loaded.then(function () {
                if (!this.config.getProperty("/IsProjectMember") && !this.config.getProperty("/IsMentor")) {
                    this.byId("ObjectPageLayout").removeSection(this.view.getId() + "--Media");
                    this.byId("PersonalInfo").removeBlock(this.view.getId() + "--BlockAddress");
                }
            }.bind(this));
            this.handleCounter = 0;
            this.handleCounteraccessed = false;
            this.objectToUpload = {};

        },

        /* =========================================================== */
        /* event handlers                                              */
        /* =========================================================== */

        onRouteMatched: function (oEvent) {
            this.model.metadataLoaded().then(this.bindView.bind(this));
        },

        /**
         *
         * @param {sap.ui.base.Event} oEvent - 'press' event of Edit button
         */
        onEdit: function (oEvent) {
            this.ui.setProperty("/isEditMode", true);
        },

        /**
         *
         * @param {sap.ui.base.Event} oEvent - 'press' event of Save button
         */
        onSave: function (oEvent) {
            var oEventBus = sap.ui.getCore().getEventBus();
            oEventBus.publish("BlockChannel", "readBlockContent");
        },

        /**
         *
         * @param {sap.ui.base.Event} oEvent - 'press' event of Cancel button
         */
        onCancel: function (oEvent) {
            this.model.resetChanges();
            this.ui.setProperty("/isEditMode", false);
        },

        bindView: function () {
            var oEventBus = sap.ui.getCore().getEventBus();
            oEventBus.publish("BlockChannel", "removeDataBinding");

        },


        handleBlockCallback: function (channel, event, data) {
            var oView = this.getView();
            var object = data.data;
            if (object != null) {
                var checkAccess = false;
                //Handle Stuff based on View calling it

                while (checkAccess == false) {
                    if (this.accessHandleCounter("write") != null) {
                        checkAccess = true;
                    }
                }
                if (object.viewName.includes("BasicData")) {
                    this.objectToUpload["Language1"] = this.model.oData["Languages('"+object.language1+"')"];
                    this.objectToUpload["Language1Id"] = object.language1;
                    this.objectToUpload["Language2"] = this.model.oData["Languages('"+object.language2+"')"];
                    this.objectToUpload["Language2Id"] = object.language2;
                    this.objectToUpload["Language3"] = this.model.oData["Languages('"+object.language3+"')"];
                    this.objectToUpload["Language3Id"] = object.language3;
                    this.objectToUpload["PublicProfile"] = object.public;
                    this.objectToUpload["Region"] = this.model.oData["Regions('"+object.region+"')"];
                    this.objectToUpload["RegionId"] = object.region;
                    this.objectToUpload["RelationshipToSap"] = this.model.oData["RelationshipsToSap('"+object.relationshipToSap+"')"];
                    this.objectToUpload["RelationshipToSapId"] = object.relationshipToSap;
                    this.objectToUpload["MentorStatus"] = this.model.oData["MentorStatuses('"+object.status+"')"];
                    this.objectToUpload["StatusId"] = object.status;
                } else if (object.viewName.includes("Address")) {
                    this.objectToUpload["Address1"] = object.address1;
                    this.objectToUpload["Address2"] = object.address2;
                    this.objectToUpload["City"] = object.city;
                    this.objectToUpload["Company"] = object.company;
                    this.objectToUpload["Country"] = this.model.oData["Countries('"+object.country+"')"];
                    this.objectToUpload["CountryId"] = object.country;
                    this.objectToUpload["FullName"] = object.fullName;
                    this.objectToUpload["JobTitle"] = object.jobTitle;
                    this.objectToUpload["Phone"] = object.phone;
                    this.objectToUpload["State"] = object.state;
                    this.objectToUpload["Zip"] = object.zip;
                } else if (object.viewName.includes("Bio")) {
                    this.objectToUpload["Bio"] = object.bio;
                    this.objectToUpload["Industry1"] = this.model.oData["Industries('"+object.industry1+"')"];
                    this.objectToUpload["Industry1Id"] = object.industry1;
                    this.objectToUpload["Industry2"] = this.model.oData["Industries('"+object.industry2+"')"];
                    this.objectToUpload["Industry2Id"] = object.industry2;
                    this.objectToUpload["Industry3"] = this.model.oData["Industries('"+object.industry3+"')"];
                    this.objectToUpload["Industry3Id"] = object.industry3;
                    this.objectToUpload["MentorSince"] = object.mentorSince;
                } else if (object.viewName.includes("Media")) {
                    this.objectToUpload["Email1"] = object.email1;
                    this.objectToUpload["Email2"] = object.email2;
                    this.objectToUpload["FacebookUrl"] = object.facebook;
                    this.objectToUpload["LinkedInUrl"] = object.linkedIn;
                    this.objectToUpload["ScnUrl"] = object.scnUrl;
                    this.objectToUpload["SlackId"] = object.slackUrl;
                    this.objectToUpload["TwitterId"] = object.twitterUrl;
                    this.objectToUpload["XingUrl"] = object.xing;
                } else if (object.viewName.includes("Shirt")) {
                    this.objectToUpload["ShirtMF"] = this.model.oData["Genders('"+object.shirtMF+"')"];
                    this.objectToUpload["ShirtMFId"] = object.shirtMF;
                    this.objectToUpload["ShirtNumber"] = object.shirtNumber;
                    this.objectToUpload["ShirtSize"] = this.model.oData["Sizes('"+object.shirtSize+"')"];
                    this.objectToUpload["ShirtSizeId"] = object.shirtSize;
                    this.objectToUpload["ShirtText"] = object.shirtText;
                } else if (object.viewName.includes("Expertise")) {
                    this.objectToUpload["SapExpertise1"] = this.model.oData["SapSoftwareSolutions('"+object.expertise1+"')"];
                    this.objectToUpload["SapExpertise1Id"] = object.expertise1;
                    this.objectToUpload["SapExpertise2"] = this.model.oData["SapSoftwareSolutions('"+object.expertise2+"')"];
                    this.objectToUpload["SapExpertise2Id"] = object.expertise2;
                    this.objectToUpload["SapExpertise3"] = this.model.oData["SapSoftwareSolutions('"+object.expertise3+"')"];
                    this.objectToUpload["SapExpertise3Id"] = object.expertise3;
                    this.objectToUpload["SapExpertise1Level"] = this.model.oData["ExpertiseLevels('"+object.expertiseLevel1+"')"];
                    this.objectToUpload["SapExpertise1LevelId"] = object.expertiseLevel1;
                    this.objectToUpload["SapExpertise2Level"] = this.model.oData["ExpertiseLevels('"+object.expertiseLevel2+"')"];
                    this.objectToUpload["SapExpertise2LevelId"] = object.expertiseLevel2;
                    this.objectToUpload["SapExpertise3Level"] = this.model.oData["ExpertiseLevels('"+object.expertiseLevel3+"')"];
                    this.objectToUpload["SapExpertise3LevelId"] = object.expertiseLevel3;
                } else if (object.viewName.includes("Topics")) {
                    this.objectToUpload["Topic1"] = this.model.oData["Topics('"+object.topic1+"')"];
                    this.objectToUpload["Topic1Id"] = object.topic1;
                    this.objectToUpload["Topic2"] = this.model.oData["Topics('"+object.topic2+"')"];
                    this.objectToUpload["Topic2Id"] = object.topic2;
                    this.objectToUpload["Topic3"] = this.model.oData["Topics('"+object.topic3+"')"];
                    this.objectToUpload["Topic3Id"] = object.topic3;
                    this.objectToUpload["Topic4"] = this.model.oData["Topics('"+object.topic4+"')"];
                    this.objectToUpload["Topic4Id"] = object.topic4;
                } else if (object.viewName.includes("SoftSkills")) {
                    this.objectToUpload["SoftSkill1"] = this.model.oData["SoftSkills('"+object.softSkill1+"')"];
                    this.objectToUpload["SoftSkill1Id"] = object.softSkill1;
                    this.objectToUpload["SoftSkill2"] = this.model.oData["SoftSkills('"+object.softSkill2+"')"];
                    this.objectToUpload["SoftSkill2Id"] = object.softSkill2;
                    this.objectToUpload["SoftSkill3"] = this.model.oData["SoftSkills('"+object.softSkill3+"')"];
                    this.objectToUpload["SoftSkill3Id"] = object.softSkill3;
                    this.objectToUpload["SoftSkill4"] = this.model.oData["SoftSkills('"+object.softSkill4+"')"];
                    this.objectToUpload["SoftSkill4Id"] = object.softSkill4;
                    this.objectToUpload["SoftSkill5"] = this.model.oData["SoftSkills('"+object.softSkill5+"')"];
                    this.objectToUpload["SoftSkill5Id"] = object.softSkill5;
                    this.objectToUpload["SoftSkill6"] = this.model.oData["SoftSkills('"+object.softSkill6+"')"];
                    this.objectToUpload["SoftSkill6Id"] = object.softSkill6;
                } else if (object.viewName.includes("JamBand")) {
                    this.objectToUpload["JambandBarcelona"] = object.jamBandBarcelona;
                    this.objectToUpload["JambandLasVegas"] = object.jamBandLasVegas;
                    this.objectToUpload["JambandInstrument"] = object.jamInstruments;
                    this.objectToUpload["JambandMusician"] = object.jamMusician;
                }

                checkAccess = false;
                var checkSum = false
                while (checkAccess == false) {
                    checkSum = this.accessHandleCounter("read")
                    if (checkSum != null) {
                        if (checkSum == true) {
                            var that = this;
                            //TEST UPLOAD
                            var requests = [];
                            requests.push(new Promise(function (resolve) {
                                var mentorId;
                                if (!that.objectToUpload.Id) {
                                    that.objectToUpload.Id = that.guidGenerator.generateGuid();
                                }
                                mentorId = that.objectToUpload.Id;
                                for (var i in that.objectToUpload) {
                                    if (that.objectToUpload[i]==undefined  ) {
                                        delete that.objectToUpload[i];
                                    }else if(that.objectToUpload[i].length === 0){delete that.objectToUpload[i];}
                                }
                                that.model.create(
                                    "/Mentors",
                                    that.objectToUpload, {
                                        success: function (data) {
                                            MessageToast.show(that.i18n.getText("profileSavedSuccesfully"));
                                            resolve();
                                            var createdId = that.objectToUpload.Id;
                                            that.model.oData["Mentors('" + createdId + "')"] = that.objectToUpload
                                            that.model.submitChanges({
                                                success: function (oData) {
                                                    sap.m.MessageToast.show(this.i18n.getText("profileSavedSuccesfully"));
                                                }.bind(this),
                                                error: function (oError) {
                                                    sap.m.MessageToast.show(this.i18n.getText("profileSavedError"));
                                                }.bind(this)
                                            });
                                            that.getRouter().navTo("Mentor", {
                                                Id: mentorId
                                            });
                                            that.objectToUpload = {};
                                            that.accessHandleCounter("zero");

                                        },
                                        error: function (error) {
                                            MessageToast.show(that.i18n.getText("profileSavedError"));
                                            that.objectToUpload = {};
                                            that.accessHandleCounter("zero");
                                            resolve();
                                        }
                                    }
                                );
                            }));
                            this.objectToUpload = {};
                        }
                        checkAccess = true;
                    }
                }
            }

        },

        accessHandleCounter: function (mode) {
            if (!this.handleCounteraccessed) {
                this.handleCounteraccessed = true;
                switch (mode) {
                    case "read":
                        if (this.handleCounter >= 9) {
                            this.handleCounteraccessed = false;

                            return true;
                        } else {
                            this.handleCounteraccessed = false;
                            return false;
                        }
                    case "write":
                        this.handleCounter++;
                        this.handleCounteraccessed = false;
                        return true;
                    case "zero":
                        this.handleCounter = 0;
                    default:
                        this.handleCounteraccessed = false;
                        return false;
                }
            }
            return null;
        },

        /* =========================================================== */
        /* begin: internal methods                                     */
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
