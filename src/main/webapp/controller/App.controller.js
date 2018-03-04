/* global sap */

sap.ui.define([
		"com/sap/mentors/lemonaid/controller/BaseController",
		"sap/ui/model/json/JSONModel"
	], function (BaseController, JSONModel) {
		"use strict";

		return BaseController.extend("com.sap.mentors.lemonaid.controller.App", {

			onInit: function () {
				var oViewModel,
					fnSetAppNotBusy,
					iOriginalBusyDelay = this.getView().getBusyIndicatorDelay(),
					me = this;

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
				
				this.getOwnerComponent().getModel().attachRequestSent(function() {
					sap.ui.core.BusyIndicator.show();
				});
				this.getOwnerComponent().getModel().attachRequestCompleted(function() {
					sap.ui.core.BusyIndicator.hide();
				});

			}

		});

	}
);
