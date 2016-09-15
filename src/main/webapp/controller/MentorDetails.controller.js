/* global sap, console */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel",
	"sap/ui/model/Filter",
	"sap/m/MessageToast"
], function(BaseController, JSONModel, Filter, MessageToast) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.MentorDetails", {

        /* =========================================================== */
        /* lifecycle methods                                           */
        /* =========================================================== */

        /**
         * Called when the master list controller is instantiated. It sets up the event handling for the master/detail communication and other lifecycle tasks.
         * @public
         */
        onInit: function() {
			this.view      = this.getView();
			this.component = this.getComponent();
			this.model     = this.component.getModel();
			this.router    = this.getRouter();
			this.i18n      = this.component.getModel("i18n").getResourceBundle();
			this.ui        = new JSONModel({
        		ServiceUrl : this.model.sServiceUrl,
				isEditMode : false
        	});
        	this.getView().setModel(this.ui, "ui");
            this.router.getRoute("Mentor").attachMatched(this.onRouteMatched, this);
        },

        /* =========================================================== */
        /* event handlers                                              */
        /* =========================================================== */

        onRouteMatched: function(oEvent) {
            this.sMentorId = oEvent.getParameter("arguments").Id;
            this.model.metadataLoaded().then(this.bindView.bind(this));
        },

		/**
		 *
		 * @param {sap.ui.base.Event} oEvent - 'press' event of Edit button
		 */
		onEdit: function(oEvent) {
			this.getView().getModel("ui").setProperty("/isEditMode", true);
		},

		/**
		 *
		 * @param {sap.ui.base.Event} oEvent - 'press' event of Save button
		 */
		onSave: function(oEvent) {
			this.model.submitChanges({
				success: function(oData) {
					console.log("OK", oData);
					MessageToast.show("Profile is saved successfully");
					this.getView().getModel("ui").setProperty("/isEditMode", false);
				}.bind(this),
				error: function(oError) {
					MessageToast.show("ERROR: Profile cannot be saved!");
					console.log("ERROR", oError);
				}
			});
		},

		/**
		 *
		 * @param {sap.ui.base.Event} oEvent - 'press' event of Cancel button
		 */
		onCancel: function(oEvent) {
			this.model.resetChanges();
			this.getView().getModel("ui").setProperty("/isEditMode", false);
		},

		bindView: function() {
            this.view.bindElement({
                path: this.getModel().createKey("/Mentors", { Id: this.sMentorId }),
                parameters: {
                    expand: "MentorStatus,RelationshipToSap,Industry1,Industry2,Industry3,ShirtMF,ShirtSize,SapExpertise1,SapExpertise1Level,SapExpertise2,SapExpertise2Level,SapExpertise3,SapExpertise3Level,SoftSkill1,SoftSkill2,SoftSkill3,SoftSkill4,SoftSkill5,SoftSkill6,Country,Region,Topic1,Topic2,Topic3"
                }
            });
            this.ui.setProperty("/UploadUrl", this.model.sServiceUrl + "/" + this.model.createKey("Mentors", {Id: this.sMentorId}) + "/Attachments");
        },

        /* =========================================================== */
        /* begin: internal methods                                     */
        /* =========================================================== */
        

    });
});
