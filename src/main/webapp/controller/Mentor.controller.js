sap.ui.define([
	"com/sap/mentors/lemonaid/controller/BaseController",
	"com/sap/mentors/lemonaid/util/crypto-js/core",
	"com/sap/mentors/lemonaid/util/crypto-js/md5"
], function(Controller) {
	"use strict";

	return Controller.extend("com.sap.mentors.lemonaid.controller.Mentor", {
		
		onInit: function () {
			this.oView = this.getView();
			this.oComponent = sap.ui.component(sap.ui.core.Component.getOwnerIdFor(this.oView));
			this.oModel = this.oComponent.getModel();
			this.oView.setModel(this.oModel);
			this.oRouter = this.getRouter();
			this.oRouter.getRoute("Mentor").attachMatched(this._onRouteMatched, this);
		},
		
		_onRouteMatched : function (oEvent) {
			var	sId = oEvent.getParameter("arguments").Id,
				that = this;
			this.oModel.metadataLoaded().then(function() {
				that.oView.bindElement({
					path: that.oModel.createKey("/Mentors", { Id: sId }),
					parameters: {
						expand: 'MentorStatus,RelationshipToSap,Country,Topic1,Topic2,Topic3'
					},
					events : {
						change: that._onBindingChange.bind(that)
					}
				});
			});
		},
		
		_onBindingChange : function () {
			var sEmail = this.oView.getBindingContext().getProperty("Email1");
			this.oView.byId("header").setObjectImageURI(
				sEmail ?
					"https://www.gravatar.com/avatar/" + 
						CryptoJS.MD5(sEmail).toString() +
						"?s=144&d=http%3A%2F%2Fscn.sap.com%2Fcommunity%2Fimage%2F2422%2F1.png" :
					"images/logo.png");
		}

	});
});