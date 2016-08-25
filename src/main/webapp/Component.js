sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/Device",
	"com/sap/mentors/lemonaid/model/models",
	"com/sap/mentors/lemonaid/controller/ErrorHandler",
	"com/sap/mentors/lemonaid/util/openui5_googlemaps/ScriptsUtil"
], function(UIComponent, Device, models, ErrorHandler, gmapScriptsUtil) {
	"use strict";

	return UIComponent.extend("com.sap.mentors.lemonaid.Component", {

		metadata: {
			manifest: "json"
		},

		/**
		 * The component is initialized by UI5 automatically during the startup of the app and calls the init method once.
		 * @public
		 * @override
		 */
		init: function() {

			// call the base component's init function
			UIComponent.prototype.init.apply(this, arguments);

			// Remove the splash screen
		    $('.loader-icon').removeClass('spinning-cog').addClass('shrinking-cog');
		    $('.loader-background').fadeOut();
		    $('.loader-text').fadeOut();
		    window.setTimeout(function() { $('#loader').remove() }, 400);

			// Register Google Maps
			var sPath = jQuery.sap.getResourcePath("openui5/googlemaps/loadScripts");
			jQuery.sap.registerResourcePath("google.maps", sPath);

			// initialize the error handler with the component
			this._oErrorHandler = new ErrorHandler(this);

			// configure the main model
			this.getModel().setSizeLimit(1000);
			this.metadata = {};
			this.getModel().attachMetadataLoaded(function(event) {
				this.metadata = event.getParameter("metadata");
			}, this);

			// set the device model
			this.setModel(models.createDeviceModel(), "device");

			// set the config model, needed for additional info
			this.setModel(models.createConfigModel(this.getModel()), "config");

            // set the menu model, contains the main menu
			this.setModel(models.createMenuModel(
                this.getModel("i18n").getResourceBundle(),
                this.getModel("config")
            ), "menu");

			//TODO - get real key this is W3Cs
       		gmapScriptsUtil.setApiKey('AIzaSyCaQHRFcYj9WY2yU_bfaf1pW5MWpszjWrM');

			// create the views based on the url/hash
			this.getRouter().initialize();

		},

		/**
		 * The component is destroyed by UI5 automatically.
		 * In this method, the ErrorHandler is destroyed.
		 * @public
		 * @override
		 */
		destroy : function () {
			this._oErrorHandler.destroy();
			// call the base component's destroy function
			UIComponent.prototype.destroy.apply(this, arguments);
		}

	});

});
