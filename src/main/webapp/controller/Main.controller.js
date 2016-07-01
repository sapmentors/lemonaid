sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/json/JSONModel"
], function(BaseController, Filter, FilterOperator, JSONModel) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.Main", {

		/* =========================================================== */
		/* lifecycle methods                                           */
		/* =========================================================== */

		/**
		 * Called when the view controller is instantiated.
		 * @public
		 */
        onInit: function() {
        	this.view = this.getView();
        	this.router = this.getRouter();
        	this.component = this.getComponent();
        	this.toolPage = this.view.byId("toolPage");
        	this.device = this.component.getModel("device");
        	this.router.attachRoutePatternMatched(this.onRoutePatternMatched, this);
        },

		/* =========================================================== */
		/* event handlers                                              */
		/* =========================================================== */
        
        onRoutePatternMatched: function(event) {
        	var key = event.getParameter("name");
        	switch(key) {
        		case "Main":
        			this.router.navTo("Mentors", null, true);
        			break;
        		default:
        			var navList = this.view.byId("NavigationList");
        			var items = navList.getItems();
        			for (var i = 0; i < items.length; i++) {
        				if (items[i].getKey() === key) {
        					navList.setSelectedItem(items[i]);
        				}
        			}
					this.view.byId("pageContainer").to(this.getView().byId(key));
        	}
        },
        
		onHamburgerPress: function() {
 			this.toolPage.setSideExpanded(!this.toolPage.getSideExpanded());
		},
		
		onMenuSelect: function(event) {
            this.router.navTo(event.getParameter('item').getKey(), null, true);
            if (this.device.getProperty("/system/phone")) {
 				this.toolPage.setSideExpanded(false);
            }
		},
		
		onPressLogin: function() {
			window.location = "login.html";
		}

	});
});