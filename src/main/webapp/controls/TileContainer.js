/* global sap */

sap.ui.define([], function () {
	"use strict";

	var TileContainer = sap.ui.core.Control.extend("com.sap.mentors.lemonaid.controls.TileContainer", {
		metadata : {
			properties : {
				noData : {
					type : "string",
					defaultValue : "No data"
				},
				wrapContent : {
					type : "boolean",
					defaultValue : false
				}
			},
			defaultAggregation : "content",
			aggregations : {
				/**
				 * The tile controls inside this container
				 */
				content : {
					type : "sap.ui.core.Control",
					multiple : true,
					singularName : "content"
				}
			}
		},
	
		renderer : {
			render : function(oRm, oControl) {
				oRm.write("<div");
				oRm.writeControlData(oControl);
				oRm.addClass("cockpitTileContainer");
				oRm.writeClasses();
				oRm.write(">");
				this.renderHeader(oRm, oControl);
				this.renderContent(oRm, oControl);
				oRm.write("</div>");
			},
			renderHeader : function(oRm, oControl) {
				// split into header and content so that subclasses can override
			},
			renderContent : function(oRm, oControl) {
				var tiles = oControl.getAggregation("content");
				if (!tiles || tiles.length === 0) {
					this.renderNoData(oRm, oControl);
				} else {
					for (var i = 0; i < tiles.length; i++) {
						if (!tiles[i].getVisible()) {
							continue;
						}
						oRm.write("<div");
						if (oControl.getProperty("wrapContent")) {
							oRm.addClass("cockpitTileWrapContent");
						} else {
							oRm.addClass("cockpitTileWrapper");
						}
						oRm.addClass("sapUiSmallMarginEnd");
						oRm.addClass("sapUiSmallMarginBottom");
						oRm.writeClasses();
						oRm.write(">");
						oRm.renderControl(tiles[i]);
						oRm.write("</div>");
					}
				}
			},
			renderNoData : function(oRm, oControl) {
				if (!oControl._oNoDataLabel.getText()) {
					return;
				}
				oRm.write("<div");
				oRm.addClass("cockpitTileContainerNoDataWrapper");
				oRm.writeClasses();
				oRm.write(">");
				oRm.renderControl(oControl._oNoDataLabel);
				oRm.write("</div>");
			}
		}
	});
	
	TileContainer.prototype.init = function() {
		this._oNoDataLabel = new sap.m.Label().addStyleClass("cockpitTileContainerNoData");
	};
	
	TileContainer.prototype.setNoData = function(sNoData) {
		this.setProperty("noData", sNoData);
		this._oNoDataLabel.setText(sNoData);
	};
	
	TileContainer.prototype.setContent = function(content) {
		this.setAggregation("content", content);
	};
	
	TileContainer.prototype.exit = function() {
		this._oNoDataLabel.destroy();
		delete this._oNoDataLabel;
	};

	return TileContainer;

});