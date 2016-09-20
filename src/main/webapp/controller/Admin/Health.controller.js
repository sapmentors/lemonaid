/* global sap, jQuery */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel"
], function(BaseController, JSONModel) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.Admin.Health", {

		/* =========================================================== */
		/* lifecycle methods                                           */
		/* =========================================================== */

		/**
		 * Called when the view controller is instantiated.
		 * @public
		 */
        onInit: function() {
			this.view      = this.getView();
			this.component = this.getComponent();
			this.router    = this.getRouter();
			this.i18n      = this.component.getModel("i18n").getResourceBundle();
			this.model     = new JSONModel();
			this.view.setModel(this.model);
            this._loadHealth();
        },

		/* =========================================================== */
		/* event handlers                                              */
		/* =========================================================== */
		onRefresh: function() {
			this._loadHealth();
		},
		
		/* =========================================================== */
		/* formatters                                                  */
		/* =========================================================== */

		formatIcon: function(status) {
			switch (status) {
				case "DOWN":
					return "sap-icon://status-error";
				case "UP":
					return "sap-icon://sys-enter-2";
				case "OUT_OF_SERVICE":
					return "sap-icon://status-inactive";
				default:
					return "sap-icon://status-in-process";
			}
		},

		formatIconColor: function(status) {
			switch (status) {
				case "DOWN":
					return "red";
				case "UP":
					return "green";
				case "OUT_OF_SERVICE":
					return "red";
				default:
					return "yellow";
			}
		},
	
		formatStatusText: function(status) {
			switch (status) {
				case "DOWN":
					return this.i18n.getText("HealthStatusDown");
				case "UP":
					return this.i18n.getText("HealthStatusUp");
				case "OUT_OF_SERVICE":
					return this.i18n.getText("HealthStatusOutOfService");
				default:
					return this.i18n.getText("HealthStatusWarning");
			}
		},

		/* =========================================================== */
		/* internal methods                                            */
		/* =========================================================== */

		_loadHealth: function(event) {
            jQuery.ajax("/admin/health", { dataType: "json" }).always(function(response) {
	        	var data = [];
	        	if (response.responseJSON) {
	        		response = response.responseJSON;
	        	}
	        	jQuery.each(response, function(key, value) {
	        		if (key === "status") {
		        		data.push({
		        			titleText: "Overall Health",
		        			status: {
		        				title: "Health status",
		        				status: value
		        			}
		        		});
	        		} else {
	        			var title = "";
	        			if (key === "db") { 
	        				title = "DB"; 
	        				value.result = value.hello + " profiles";
	        				delete value.hello;
	        			} else {
		        			title = key.replace(/([A-Z0-9])/g, " $1");
		        			title = title.charAt(0).toUpperCase() + title.slice(1);
	        			}
	        			if (key === "diskSpace") { 
	        				value.total = Math.round(value.total / 1024 / 1024) + " MB";
	        				value.free = Math.round(value.free / 1024 / 1024) + " MB";
	        				value.threshold = Math.round(value.threshold / 1024 / 1024) + " MB";
	        			}
	        			if (key ==="twitter") {
	        				if (value.status === "NotAuthenticated") {
	        					value.result = "Not authenticated";
	        				}
	        			}
		        		var entry = {
		        			titleText: title,
		        			status: {
		        				title: "Health status",
		        				status: value.status,
		        				details: []
		        			}
		        		};
	    				jQuery.each(value, function(label, text) {
	    					if (label !== "status") {
	    						label = label.charAt(0).toUpperCase() + label.slice(1);
		        				entry.status.details.push({
		        					label: label,
		        					text: text
		        				});
	    					}
	    				});
	        			data.push(entry);
	        		}
	        	}.bind(this));
				this.model.setProperty("/tiles", data);
			}.bind(this));
		}

    });

});