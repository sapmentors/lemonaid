/* global sap */

sap.ui.define(["jquery.sap.global"],

	function(jQuery) {
	"use strict";

	var TwoColumnTileRenderer = {};

	TwoColumnTileRenderer.render = function(rm, oControl) {

		rm.write("<div"); // outermost
		rm.addClass("twoColumnTileLayout");
		rm.writeControlData(oControl);
		rm.writeClasses();
		rm.write(">");
	
		if (oControl.getTitle()) {
			rm.write("<div");
			rm.addStyle("height", oControl.getTitleHeight() + "px");
			rm.writeStyles();
			rm.write(">");
			rm.renderControl(oControl.getTitle().addStyleClass("tileTitleContent"));
			rm.write("</div>");
		}
	
		rm.write("<div"); // content area wrapper
		rm.addClass("sapUiSmallMarginTop");
		rm.writeClasses();
		rm.addStyle("height", oControl.getContentHeight());
		rm.writeStyles();
		rm.write(">");
	
		rm.write("<div"); // First column wrapper
		rm.addClass("first");
		// default width is 50%, it is introduced to effectively consume available space
		rm.addStyle("width", oControl.getFirstWidth());
		rm.writeClasses();
		rm.addStyle("height", "100%");
		rm.writeStyles();
		rm.write(">");
		if (oControl.getFirst()) {
			rm.renderControl(oControl.getFirst());
		}
		rm.write("</div>"); // First column wrapper
	
		rm.write("<div"); // Second column wrapper
		rm.addClass("second");
		// default width is 50%, it is introduced to effectively consume available space
		rm.addStyle("width", oControl.getSecondWidth());
		rm.addClass("verticalTileSeparator");
		rm.writeClasses();
		rm.addStyle("height", "100%");
		rm.writeStyles();
		rm.write(">");
		if (oControl.getSecond()) {
			rm.renderControl(oControl.getSecond());
		}
		rm.write("</div>"); // Second column wrapper
	
		rm.write("</div>"); // content area wrapper
	
		rm.write("</div>"); // outermost
	
	};

	return TwoColumnTileRenderer;

}, /* bExport= */ true);