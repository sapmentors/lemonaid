/* global sap */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "sap/ui/model/Filter",
    'sap/m/Button',
    'sap/m/Dialog',
    "sap/m/BusyDialog",
    "sap/m/MessageToast"
], function (BaseController, JSONModel, Filter, MessageToast, Dialog, Button,BusyDialog) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.MentorDetails", {

        busyDialog: new sap.m.BusyDialog(),

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
            this.busyDialog.setTitle(this.i18n.getText("processChangesTitle"));
            this.busyDialog.setCustomIcon("data:image/svg+xml,%3Csvg%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20viewBox%3D%270%200%20289.13333%20289.13333%27%20height%3D%27289.133%27%20width%3D%27289.133%27%3E%3Cpath%20d%3D%27M289.128%20144.57c0%2079.84-64.724%20144.563-144.564%20144.563C64.724%20289.133%200%20224.41%200%20144.57%200%2064.727%2064.723.004%20144.564.004c79.84%200%20144.564%2064.723%20144.564%20144.564%27%20fill%3D%27%23eda216%27%2F%3E%3Cpath%20d%3D%27M111.09%20164.93l49.723-130.914c2.222-5.88%209.016-8.613%2014.69-5.897l35.892%2017.04c5.824%202.77%208.045%209.92%204.834%2015.5l-69.675%20120.957c-2.186%203.77-6.907%205.246-10.846%203.38l-20.457-9.707c-3.883-1.867-5.693-6.346-4.162-10.36M126.9%20235.222c-.11%2014.298-11.796%2025.814-26.093%2025.702-14.278-.112-25.814-11.796-25.703-26.095.094-14.297%2011.797-25.813%2026.095-25.7%2014.295.092%2025.812%2011.795%2025.7%2026.092%27%20fill%3D%27%23fff%27%2F%3E%3C%2Fsvg%3E");
            this.busyDialog.setCustomIconRotationSpeed(1000);
            this.busyDialog.setCustomIconWidth("48px");
            this.busyDialog.setCustomIconHeight("48px");
            this.busyDialog.open();

            var mentor =this.model.getPendingChanges()["Mentors('" + this.sMentorId + "')"];
            if(mentor != undefined){
                var name = mentor.FullName;
                var email =mentor.Email1;
                if((name== undefined || name.trim().length>0) && (email== undefined || email.trim().length>0)){
                    this.model.submitChanges({
                        success: function (oData) {
                            this.busyDialog.close();
                            sap.m.MessageToast.show(this.i18n.getText("profileSavedSuccesfully"));
                            this.ui.setProperty("/isEditMode", false);
                            this.model.resetChanges();
                        }.bind(this),
                        error: function (oError) {
                            this.busyDialog.close();
                            sap.m.MessageToast.show(this.i18n.getText("profileSavedError"));
                        }.bind(this)
                    });
                }else {
                    this.busyDialog.close();
                    sap.m.MessageToast.show(this.i18n.getText("requiredFieldError"));
                }
            }else{
                this.busyDialog.close();
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
