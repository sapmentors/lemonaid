/* global sap */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "com/sap/mentors/lemonaid/util/showdown"
], function(BaseController, JSONModel) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.Help", {

		/* =========================================================== */
		/* lifecycle methods                                           */
		/* =========================================================== */

    	urls: {
    		faq: "https://raw.githubusercontent.com/wiki/sapmentors/lemonaid/FAQ.md",
    		about: "https://raw.githubusercontent.com/wiki/sapmentors/lemonaid/About.md"
    	},
    	
		/**
		 * Called when the view controller is instantiated.
		 * @public
		 */
        onInit: function() {
			this.view      = this.getView();
			this.component = this.getComponent();
			this.router    = this.getRouter();
			this.i18n      = this.component.getModel("i18n").getResourceBundle();
            this.router.getRoute("Help").attachMatched(this.onRouteMatched, this);
        	this.model = new JSONModel();
        	this.view.setModel(this.model);
        	this._loadModel();
        },

		/* =========================================================== */
		/* event handlers                                              */
		/* =========================================================== */
		onRouteMatched: function(event) {
			this.router.navTo("Help-Faq", null, true);
		},
		
		onTabSelect: function(event) {
			this.router.navTo(event.getParameter("key"));
		},

		/* =========================================================== */
		/* internal methods                                            */
		/* =========================================================== */
		_loadModel: function() {
			var converter = new showdown.Converter();
			jQuery.each(this.urls, function(id, url) {
				this.model.setProperty("/" + id, "");
				jQuery.ajax(url).done(function(content) { 
					this.model.setProperty("/" + id, converter.makeHtml(content));
				}.bind(this));
			}.bind(this));
		}

    });

});