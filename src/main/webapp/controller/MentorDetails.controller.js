sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel"
], function(BaseController, JSONModel) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.MentorDetails", {

        onInit: function() {
        	this.view = this.getView();
        	this.component = this.getComponent();
        	this.model = this.component.getModel();
        	this.router = this.getRouter();
        	this.i18n = this.component.getModel("i18n").getResourceBundle();
        	this.ui = new JSONModel({
        		ServiceUrl: this.model.sServiceUrl,
        	});
        	this.getView().setModel(this.ui, "ui")
            this.router.getRoute("Mentor").attachMatched(this.onRouteMatched, this);
        },

        onRouteMatched: function(oEvent) {
            this.sMentorId = oEvent.getParameter("arguments").Id;
            this.model.metadataLoaded().then(this.bindView.bind(this));
        },

        bindView: function() {
            this.view.bindElement({
                path: this.getModel().createKey("/Mentors", { Id: this.sMentorId }),
                parameters: {
                    expand: 'MentorStatus,RelationshipToSap,Country,Region,Topic1,Topic2,Topic3'
                }
            });
            this.ui.setProperty("/UploadUrl", this.model.sServiceUrl + "/" + this.model.createKey("Mentors", {Id: this.sMentorId}) + "/Attachments");
        }

    });
});