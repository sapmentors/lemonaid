sap.ui.define([
		"com/sap/mentors/lemonaid/controller/BaseController",
		"sap/ui/model/json/JSONModel"
	], function (BaseController, JSONModel) {
		"use strict";

		return BaseController.extend("com.sap.mentors.lemonaid.controller.App", {

			onInit: function () {
				var oViewModel,
					fnSetAppNotBusy,
					iOriginalBusyDelay = this.getView().getBusyIndicatorDelay();

				oViewModel = new JSONModel({
					busy : true,
					delay : 0
				});
				this.setModel(oViewModel, "appView");

				fnSetAppNotBusy = function() {
					oViewModel.setProperty("/busy", false);
					oViewModel.setProperty("/delay", iOriginalBusyDelay);
				};

				this.getOwnerComponent().getModel().metadataLoaded().
					then(fnSetAppNotBusy);

				// apply content density mode to root view
				this.getView().addStyleClass(this.getOwnerComponent().getContentDensityClass());
			},

			onAfterRendering: function() {
				// Remove the spinner
			    $('.loader-icon').removeClass('spinning-cog').addClass('shrinking-cog');
			    $('.loader-background').fadeOut(); 
			    $('.loader-text').fadeOut();
			}

		});

	}
);