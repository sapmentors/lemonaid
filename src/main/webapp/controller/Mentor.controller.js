sap.ui.define([
	"com/sap/mentors/lemonaid/controller/BaseController"
], function(Controller) {
	"use strict";

	return Controller.extend("com.sap.mentors.lemonaid.controller.Mentor", {
		onInit: function () {
			var oRouter = this.getRouter();
			oRouter.getRoute("Mentor").attachMatched(this._onRouteMatched, this);
		},
		_onRouteMatched : function (oEvent) {
			var oArgs, oView;
			oArgs = oEvent.getParameter("arguments");
			oView = this.getView();
			this.shirtNumber = oArgs.ShirtNumber;
			// Temporary stub code validation that shirt number is correct via using Router pattern
			oView.byId("MentorPage").setTitle(this.shirtNumber);
			/*
			TODO: Figure out intended JSON Model path.  Is the key ShirtNumber, or is it index based?  Or should it 
			be a new ODataModel to a different OData service with specific mentor details?
			oView.bindElement({
				path : "/ShirtNumber/" + this.shirtNumber or item index ???
			});
			*/
		}
	});

});