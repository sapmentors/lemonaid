sap.ui.define([
	"com/sap/mentors/lemonaid/controller/BaseController",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator"
], function(Controller, Filter, FilterOperator) {
	"use strict";

	return Controller.extend("com.sap.mentors.lemonaid.controller.Main", {

		onInit: function() {
			this.ui = new sap.ui.model.json.JSONModel({count: 0});
			this.getView().setModel(this.ui, "ui");
		},
		
		onSearchPressed: function(event) {
			var search = event.getParameters().query;
			this.getView().byId("table").getBinding("items").filter(
				[ new Filter([
					new Filter("FullName", FilterOperator.Contains, search)
				], false)]);
		},

		onUpdateFinished: function(event) {
			this.ui.setProperty("/count", event.getParameter("total"));	
		},
		
		onMentorDetailPress : function(event){
			/*
			 * Ideally, we'd use Shirt Number as unique identifier to pass to Router, rather 
			 * than array index.  Will need to know how we can expose additional OData services
			 * possibly, or maybe just use a Filter in the main model similar to text search.
			 */
			var oItem = event.getSource();
			var oContext = oItem.getBindingContext();
			var shirtNumber = oContext.getProperty("ShirtNumber");
			// Routing pattern is set up in manifest.json and expects a Shirt Number for now.
			this.getRouter().navTo("Mentor",{ShirtNumber : shirtNumber});
		}

	});

});