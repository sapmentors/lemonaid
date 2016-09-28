/* global sap, $, Promise */

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
    		    		$.each(data.results, function(idx, value) {
                            if (value.Name === "false") { value.Name = false; }
                            if (value.Name === "true") { value.Name = true; }
    		    			oModel.setProperty("/" + value.Id, value.Name);
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
                    if (config.getProperty("/IsProjectMember")) {
                        menu.push({
                            key: "ImportExport",
                            text: i18n.getText("importExport"),
                            icon: "sap-icon://download-from-cloud"
                        });
                        menu.push({
                            key: "Admin",
                            text: i18n.getText("admin"),
                            icon: "sap-icon://action-settings"
                        });
                    }
                    menu.push({
                        key: "Help",
                        text: i18n.getText("help"),
                        icon: "sap-icon://sys-help"
                    });
                    oModel.setProperty("/", menu);
                    resolve();
                });
            });
			return oModel;
		}

	};

});
