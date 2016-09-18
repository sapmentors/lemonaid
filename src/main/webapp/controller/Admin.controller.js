/* global sap */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel"
], function(BaseController, JSONModel) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.Admin", {

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
            this.router.getRoute("Admin").attachMatched(this.onRouteMatched, this);
        },

		/* =========================================================== */
		/* event handlers                                              */
		/* =========================================================== */
		onRouteMatched: function(event) {
			this.router.navTo("Admin-Health", null, true);
		}

		/* =========================================================== */
		/* internal methods                                            */
		/* =========================================================== */

    });

});