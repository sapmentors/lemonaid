/* global sap */

sap.ui.define([
	"sap/ui/core/Control"
], function (Control) {
	"use strict";

	var Iframe = Control.extend("com.sap.mentors.lemonaid.controls.Iframe", {

		metadata : {
			properties : {
				width : { type : "sap.ui.core.CSSSize", group : "Appearance", defaultValue : "100%" },
				height : { type : "sap.ui.core.CSSSize", group : "Appearance", defaultValue : "100%" },
				url: { type: "string", group : "Appearance", defaultValue : null }
			}
		},

		renderer: function(rm, oControl) {
			rm.write("<div");
			rm.writeControlData(oControl);
			rm.writeClasses();
			rm.writeAttribute("style", "width:" + oControl.getWidth() + ";height:" + oControl.getHeight());
			rm.write(">");
			rm.write("<iframe");
			rm.writeAttribute("src", oControl.getUrl());
			rm.writeAttribute("style", "border:none;width:" + oControl.getWidth() + ";height:" + oControl.getHeight());
			rm.write("></iframe>");
			rm.write("</div>");
		}

	});

	return Iframe;

});
