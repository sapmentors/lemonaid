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
					this.view.byId("pageContainer").to(this.getView().byId(key));
        	}
        },
        
		onHamburgerPress: function() {
			var viewId = this.view.getId();
			var toolPage = sap.ui.getCore().byId(viewId + "--toolPage");
 			toolPage.setSideExpanded(!toolPage.getSideExpanded());
		},
		
		onMenuSelect: function(event) {
            this.router.navTo(event.getParameter('item').getKey(), null, true);
		}

	});
});