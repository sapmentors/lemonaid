/* global sap */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "sap/ui/model/Filter",
    'sap/m/Button',
    'sap/m/Dialog',
    "sap/m/MessageToast"
], function (BaseController, JSONModel, Filter, MessageToast, Dialog, Button) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.MentorDetails", {

        /* =========================================================== */
        /* lifecycle methods                                           */
        /* =========================================================== */

        /**
         * Called when the master list controller is instantiated. It sets up the event handling for the master/detail communication and other lifecycle tasks.
         * @public
         */
        onInit: function () {

            this.view = this.getView();
            this.component = this.getComponent();
            this.model = this.component.getModel();
            this.router = this.getRouter();
            this.i18n = this.component.getModel("i18n").getResourceBundle();
            this.config = this.component.getModel("config");
            this.config.setProperty("/IsProjectMember", (this.config.getProperty("/IsProjectMember") == true));
            this.ui = new JSONModel({
                ServiceUrl: this.model.sServiceUrl,
                isEditMode: false
            });
            this.view.setModel(this.ui, "ui");
            this.router.getRoute("Mentor").attachMatched(this.onRouteMatched, this);

            // Remove sections/blocks that are not meant for a general audience
            this.config._loaded.then(function () {
                if (!this.config.getProperty("/IsProjectMember") && !this.config.getProperty("/IsMentor")) {
                //    this.byId("ObjectPageLayout").removeSection(this.view.getId() + "--Media");
                //    this.byId("PersonalInfo").removeBlock(this.view.getId() + "--BlockAddress");
                }
            }.bind(this));

        },

        /* =========================================================== */
        /* event handlers                                              */
        /* =========================================================== */

        onRouteMatched: function (oEvent) {
            this.sMentorId = oEvent.getParameter("arguments").Id;
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
            var mentor =this.model.getPendingChanges()["Mentors('" + this.sMentorId + "')"];
            if(mentor != undefined){
                var name = mentor.FullName;
                var email =mentor.Email1;
                if((name== undefined || name.trim().length>0) && (email== undefined || email.trim().length>0)){
                    this.model.submitChanges({
                        success: function (oData) {
                            sap.m.MessageToast.show(this.i18n.getText("profileSavedSuccesfully"));
                            this.ui.setProperty("/isEditMode", false);
                        }.bind(this),
                        error: function (oError) {
                            sap.m.MessageToast.show(this.i18n.getText("profileSavedError"));
                        }.bind(this)
                    });
                }else {
                    sap.m.MessageToast.show(this.i18n.getText("requiredFieldError"));
                }
            }
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

            this.view.bindElement({
                path: this.getModel().createKey("/Mentors", {
                    Id: this.sMentorId
                }),
                parameters: {
                    expand: this.component.metadata._getEntityTypeByName("Mentor").navigationProperty.map(function (navigationProperty) {
                        return navigationProperty.name;
                    }).join() // Expand all navigation properties
                }
            });
            this.ui.setProperty("/UploadUrl", this.model.sServiceUrl + "/" + this.model.createKey("Mentors", {
                Id: this.sMentorId
            }) + "/Attachments");
        },

        onDelete: function () {
            var that = this;
            var dialog = new Dialog({
                title: 'Delete Profil',
                type: 'Message',
                content: new sap.m.Text({
                    text: that.i18n.getText("profileDeletionQuestion")
                }),
                endButton: new sap.m.Button({
                    text: 'Delete',
                    type: 'Reject',
                    press: function () {
                        that.model.remove(
                            "/Mentors('" + that.sMentorId + "')",
                            //that.model.oData["Mentors('"+that.sMentorId+"')"],
                            {
                                success: function (data) {
                                    sap.m.MessageToast.show(that.i18n.getText("profileDeleted"));
                                    dialog.close();
                                    that.getRouter().navTo("Mentors");

                                },
                                error: function (error) {
                                    sap.m.MessageToast.show(that.i18n.getText("profileDeletedError"));
                                    dialog.close();

                                }
                            }
                        );

                    }

                }),
                beginButton: new sap.m.Button({
                    text: 'Cancel',
                    press: function () {
                        dialog.close();
                    }
                }),
                afterClose: function () {
                    dialog.destroy();
                }
            });

            dialog.open();
        }

        /* =========================================================== */
        /* begin: internal methods                                     */
        /* =========================================================== */


    });
});
