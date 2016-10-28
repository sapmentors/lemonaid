/* global sap */

sap.ui.define([], function () {
	"use strict";

	var TwoColumnTileLayout = sap.ui.core.Control.extend("com.sap.mentors.lemonaid.controls.TwoColumnTileLayout", {
	
		metadata : {
			library : "cockpit.shared",
			properties : {
				contentHeight : {
					type : "sap.ui.core.CSSSize",
					defaultValue : "auto"
				},
				/* title is a fixflex which requires a parent with fixed height, so we use int for pixels */
				titleHeight : {
					type : "int",
					defaultValue : "32"
				},
				firstWidth : {
					type : "sap.ui.core.CSSSize",
					defaultValue : "50%"
				},
				secondWidth : {
					type : "sap.ui.core.CSSSize",
					defaultValue : "50%"
				}
			},
			aggregations : {
				title : {
					type : "sap.ui.layout.FixFlex",
					multiple : false
				},
				first : {
					type : "sap.ui.core.Control",
					multiple : false
				},
				second : {
					type : "sap.ui.core.Control",
					multiple : false
				}
			}
		}
	});

	return TwoColumnTileLayout;

});