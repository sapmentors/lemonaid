sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController"
], function(BaseController) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.Mentor", {

        onInit: function() {
            this.getRouter().getRoute("Mentor").attachMatched(this._onRouteMatched, this);
        },

        _onRouteMatched: function(oEvent) {
            this.sMentorId = oEvent.getParameter("arguments").Id;
            this.getModel().metadataLoaded().then(this.bindView.bind(this));
        },

        bindView: function() {
            this.getView().bindElement({
                path: this.getModel().createKey("/Mentors", { Id: this.sMentorId }),
                parameters: {
                    expand: 'MentorStatus,RelationshipToSap,Country,Topic1,Topic2,Topic3'
                },
                events: {
                    change: this._onBindingChange.bind(this)
                }
            });
        },

        _onBindingChange: function() {
            // set avatar
            var sEmail = this.getView().getBindingContext().getProperty("Email1");
            this.oView.byId("header").setObjectImageURI(this.getAvatarURL(sEmail));
        }

    });
});
