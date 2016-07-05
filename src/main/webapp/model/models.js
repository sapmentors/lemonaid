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
		
		createConfigModel: function(odataModel) {
			var oModel = new JSONModel();
			oModel.setDefaultBindingMode("OneWay");
		    odataModel.read("/Configuration", { 
		    	success: function(data) { 
		    		jQuery.each(data.results, function(idx, value) {
		    			oModel.setProperty("/" + value.Id, value.Name)
		    		});
		    	}
		    });
			return oModel;
		}

	};

});