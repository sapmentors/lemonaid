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

			// Register Google Maps
			var sPath = jQuery.sap.getResourcePath("openui5/googlemaps/loadScripts");
			jQuery.sap.registerResourcePath("google.maps", sPath);

			// initialize the error handler with the component
			this._oErrorHandler = new ErrorHandler(this);

			// set the device model
			this.setModel(models.createDeviceModel(), "device");

			// set the mentors model, needed for additional info
			this.setModel(models.createMentorsModel(), "mentors");

			//TODO - get real key this is W3Cs
       		gmapScriptsUtil.setApiKey('AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM');

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
		},

		/**
		 * This method can be called to determine whether the sapUiSizeCompact or sapUiSizeCozy
		 * design mode class should be set, which influences the size appearance of some controls.
		 * @public
		 * @return {string} css class, either 'sapUiSizeCompact' or 'sapUiSizeCozy' - or an empty string if no css class should be set
		 */
		getContentDensityClass : function() {
			if (this._sContentDensityClass === undefined) {
				// check whether FLP has already set the content density class; do nothing in this case
				if (jQuery(document.body).hasClass("sapUiSizeCozy") || jQuery(document.body).hasClass("sapUiSizeCompact")) {
					this._sContentDensityClass = "";
				// } else if (!Device.support.touch) { // apply "compact" mode if touch is not supported
				// 	this._sContentDensityClass = "sapUiSizeCompact";
				} else {
					// "cozy" in case of touch support; default for most sap.m controls, but needed for desktop-first controls like sap.ui.table.Table
					this._sContentDensityClass = "sapUiSizeCozy";
				}
			}
			return this._sContentDensityClass;
		}

	});

});