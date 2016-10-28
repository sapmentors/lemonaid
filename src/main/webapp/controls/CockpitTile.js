/* global sap */

sap.ui.define([], function () {
	"use strict";

	var CockpitTile = sap.ui.core.Control.extend("com.sap.mentors.lemonaid.controls.CockpitTile", {

		metadata : {
			properties : {
				width : {
					type : "sap.ui.core.CSSSize",
					defaultValue : "auto"
				},
				height : {
					type : "sap.ui.core.CSSSize",
					defaultValue : "auto"
				},
				padding : {
					type : "sap.ui.core.CSSSize",
					defaultValue : "1rem"
				},
				groupLabel : {
					type : "string"
				},
				flipped : {
					type : "boolean",
					defaultValue : false
				}
			},
			aggregations : {
				back : {
					type : "sap.ui.core.Control",
					multiple : false
				},
				content : {
					type : "sap.ui.core.Control",
					multiple : false
				}
			},
			events : {
				press : {
					enablePreventDefault : true
				}
			},
			defaultAggregation : "content"
		},
	
		constructor : function() {
			sap.ui.core.Control.prototype.constructor.apply(this, arguments);
		},
	
		onclick : function() {
			this.firePress();
		}
	});
	
	CockpitTile.prototype.setFlipped = function(flipped) {
		var flipValue = !!flipped;
		this.setProperty("flipped", flipValue, true);
		this.$("flipDiv").toggleClass("flipped", flipValue);
	};
	
	return CockpitTile;

});