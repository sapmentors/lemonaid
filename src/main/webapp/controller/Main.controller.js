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
			this.getRouter().navTo("Mentor", {
				Id : event.getSource().getBindingContext().getProperty("Id")
			});
		}

	});

});