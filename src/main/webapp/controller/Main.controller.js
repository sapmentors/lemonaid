sap.ui.define([
	"com/sap/mentors/lemonaid/controller/BaseController",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	"com/sap/mentors/lemonaid/util/crypto-js/core",
	"com/sap/mentors/lemonaid/util/crypto-js/md5"
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
		},
		
		gravatar : function(email){
			var src = "images/logo.png";
			if(email) {
				src = "https://www.gravatar.com/avatar/" + 
					CryptoJS.MD5(email).toString() +
					"?s=144&d=http%3A%2F%2Fscn.sap.com%2Fcommunity%2Fimage%2F2422%2F1.png"
			}
			return src;
		}
	});

});