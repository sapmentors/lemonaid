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
        },

		/* =========================================================== */
		/* event handlers                                              */
		/* =========================================================== */
        
		onSideNavButtonPress : function() {
			var viewId = this.getView().getId();
			var toolPage = sap.ui.getCore().byId(viewId + "--toolPage");
			var sideExpanded = toolPage.getSideExpanded();
 			toolPage.setSideExpanded(!toolPage.getSideExpanded());
		}

	});
});