/* global sap, jQuery */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel",
	"sap/ui/model/Filter",
	"sap/m/MessageToast"
], function(BaseController, JSONModel, Filter, MessageToast) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.MentorDetails", {

        onInit: function() {
        	this.view = this.getView();
        	this.component = this.getComponent();
        	this.model = this.component.getModel();
        	this.router = this.getRouter();
        	this.i18n = this.component.getModel("i18n").getResourceBundle();
        	this.ui = new JSONModel({
        		ServiceUrl : this.model.sServiceUrl,
				isEditMode : false
        	});
        	this.getView().setModel(this.ui, "ui");
            this.router.getRoute("Mentor").attachMatched(this.onRouteMatched, this);
        },

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
			//TODO: Implement!
			MessageToast.show("TODO: \"Save\" not implemented!");

			this.getView().getModel("ui").setProperty("/isEditMode", false);
		},

		/**
		 *
		 * @param {sap.ui.base.Event} oEvent - 'press' event of Cancel button
		 */
		onCancel: function(oEvent) {
			//TODO: Implement!
			MessageToast.show("TODO: \"Cancel\" not implemented!");

			this.getView().getModel("ui").setProperty("/isEditMode", false);
		},

		bindView: function() {
            this.view.bindElement({
                path: this.getModel().createKey("/Mentors", { Id: this.sMentorId }),
                parameters: {
                    expand: "MentorStatus,RelationshipToSap,SapExpertise1,SapExpertise2,SapExpertise3,SoftSkill1,SoftSkill2,SoftSkill3,SoftSkill4,SoftSkill5,SoftSkill6,Country,Region,Topic1,Topic2,Topic3"
                }
            });
            this.ui.setProperty("/UploadUrl", this.model.sServiceUrl + "/" + this.model.createKey("Mentors", {Id: this.sMentorId}) + "/Attachments");
        }

    });
});
