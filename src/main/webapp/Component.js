sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/Device",
	"com/sap/mentors/lemonaid/model/models",
	"openui5/googlemaps/ScriptsUtil"
], function(UIComponent, Device, models, gmapScriptsUtil) {
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

			// set the device model
			this.setModel(models.createDeviceModel(), "device");

			// set the mentors model, needed for additional info
			this.setModel(models.createMentorsModel(), "mentors");

			//TODO - get real key this is W3Cs
       		gmapScriptsUtil.setApiKey('AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM');


			// create the views based on the url/hash
			this.getRouter().initialize();



		}
	});

});

