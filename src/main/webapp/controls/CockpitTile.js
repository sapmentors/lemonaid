jQuery.sap.declare("cockpit.shared.ui.CockpitTileRenderer");

cockpit.shared.ui.CockpitTileRenderer = {
	render : function(rm, oControl) {

		rm.write("<div tabindex=\"0\""); // root
		rm.writeControlData(oControl);
		if (oControl.getBack()) {
			rm.addClass("flipcard");
		} else {
			rm.addClass("cockpitTileBorder");
			rm.addClass("cockpitTileBackground");
			rm.addStyle("padding", oControl.getPadding());
		}

		rm.addStyle("height", oControl.getHeight());
		rm.addStyle("width", oControl.getWidth());

		rm.writeClasses();
		rm.writeStyles();

		if (oControl.getGroupLabel()) {
			// ARIA
			var bAccessible = sap.ui.getCore().getConfiguration().getAccessibility();
			if (bAccessible) {
				rm.writeAccessibilityState(oControl, {
					role : "group",
					label : oControl.getGroupLabel()
				});
			}
		}
		rm.write(">"); // close root

		// we want to flip the whole tile if we have a back side
		if (oControl.getBack()) {
			rm.write("<div id=\"" + oControl.getId() + "-flipDiv\""); // flip content
			if (oControl.getFlipped()) {
				rm.addClass("flipped");
			}
			if (oControl.getBack()) {
				rm.addClass("flipContent");
			}
			rm.writeClasses();
			rm.write(">"); // close flip content
		}

		if (oControl.getBack()) {
			this._renderTwoSided(rm, oControl);
		} else {
			this._renderOneSided(rm, oControl);
		}

		if (oControl.getBack()) {
			rm.write("</div>"); // flip content end
		}

		rm.write("</div>"); // root end
	},

	_renderOneSided : function(rm, oControl) {
		rm.renderControl(oControl.getContent());
	},

	_renderTwoSided : function(rm, oControl) {
		rm.write("<div"); // front
		rm.addClass("flipFrontSide");
		rm.addClass("cockpitTileBorder");
		rm.addClass("cockpitTileBackground");
		rm.writeClasses();
		rm.addStyle("padding", oControl.getPadding());
		rm.writeStyles();
		rm.write(">");
		rm.renderControl(oControl.getContent());
		rm.write("</div>");

		rm.write("<div"); // back
		rm.addClass("flipBackSide");
		rm.addClass("cockpitTileBorder");
		rm.addClass("cockpitTileBackground");
		rm.writeClasses();
		rm.addStyle("padding", oControl.getPadding());
		rm.writeStyles();
		rm.write(">");
		rm.renderControl(oControl.getBack());
		rm.write("</div>");
	}
};
