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
            oModel._loaded = new Promise(function(resolve) {
    		    odataModel.read("/Configuration", {
    		    	success: function(data) {
    		    		jQuery.each(data.results, function(idx, value) {
    		    			oModel.setProperty("/" + value.Id, value.Name)
    		    		});
                        resolve();
    		    	}
    		    });
            });
			return oModel;
		},

        createMenuModel: function(i18n, config) {
            var menu = [{
                    key: "Mentors",
                    text: i18n.getText("mentors"),
                    icon: "sap-icon://person-placeholder"
                },{
                    key: "Events",
                    text: i18n.getText("events"),
                    icon: "sap-icon://calendar"
                },{
                    key: "Content",
                    text: i18n.getText("content"),
                    icon: "sap-icon://documents"
                },{
                    key: "SocialMedia",
                    text: i18n.getText("socialMedia"),
                    icon: "sap-icon://discussion"
                }];
			var oModel = new JSONModel(menu);
			oModel.setDefaultBindingMode("OneWay");
            oModel._loaded = new Promise(function(resolve) {
                config._loaded.then(function() {
                    if (config.getProperty("IsProjectMember")) {
                        menu.push({
                            key: "ImportExport",
                            text: i18n.getText("importExport"),
                            icon: "sap-icon://download-from-cloud"
                        });
                    }
                    oModel.setProperty("/", menu);
                    resolve();
                });
            });
			return oModel;
		}

	};

});
