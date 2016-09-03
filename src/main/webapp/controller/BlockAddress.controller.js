/* global sap */

sap.ui.define([
	"com/sap/mentors/lemonaid/controller/BaseController",
	"com/sap/mentors/lemonaid/util/formatters"
], function(BaseController, formatters) {
	"use strict";

	return BaseController.extend("com.sap.mentors.lemonaid.controller.BlockAddress", {

		formatters: formatters,

		onDisplayDialogSelectCountry : function(oEvent) {
			this.oParentBlock.fireDisplayDialogSelectCountry(oEvent.getParameters());
		},

		onCloseDialogSelectCountry : function(oEvent) {
			this.oParentBlock.fireCloseDialogSelectCountry(oEvent.getParameters());
		},

		onSearchCountry : function(oEvent) {
			this.oParentBlock.fireSearchCountry(oEvent.getParameters());
		}

	});

});
