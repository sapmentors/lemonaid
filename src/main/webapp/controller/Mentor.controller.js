sap.ui.define([
	"com/sap/mentors/lemonaid/controller/BaseController"
], function(Controller) {
	"use strict";

	return Controller.extend("com.sap.mentors.lemonaid.controller.Mentor", {
		
		onInit: function () {
			this.oView = this.getView();
			this.oComponent = sap.ui.component(sap.ui.core.Component.getOwnerIdFor(this.oView));
			this.oModel = this.oComponent.getModel();
			this.oView.setModel(this.oModel);
			this.oRouter = this.getRouter();
			this.oRouter.getRoute("Mentor").attachMatched(this.onRouteMatched, this);
		},
		
		onRouteMatched : function (oEvent) {
			var oModel = this.oModel, 
				oView = this.oView,
				sId = oEvent.getParameter("arguments").Id;
			oModel.metadataLoaded().then(function() {
				oView.bindElement({
					path: oModel.createKey("/Mentors", { Id: sId })
				});
			});
		}

	});
});