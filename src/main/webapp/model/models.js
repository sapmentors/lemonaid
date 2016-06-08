sap.ui.define([
	"sap/ui/model/json/JSONModel",
	"sap/ui/Device"
], function(JSONModel, Device) {
	"use strict";

	return {

		createDeviceModel: function() {
			var oModel = new JSONModel(Device);
			oModel.setDefaultBindingMode("OneWay");
			return oModel;
		},

		/**
		 * Create a model which holds additional info about mentor
		 * @return {object} JSON Model for Mentor details
		 */
		createMentorsModel: function() {
			var oModel = new JSONModel([]);
			oModel.setDefaultBindingMode("OneWay");
			return oModel;
		}

	};

});