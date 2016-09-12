/* global sap, $ */

sap.ui.define([], function () {
	"use strict";

	return sap.ui.core.Control.extend("com.sap.mentors.lemonaid.controls.MentorShirt", {

		// the control API:
		metadata: {
			properties: { // setter and getter are created behind the scenes, incl. data binding and type validation
				gender : { type: "string", defaultValue: "" },
				text   : { type: "string", defaultValue: "" },
				number : { type: "string", defaultValue: 0 }
			}
		},

		init: function () {
		},

		// the part creating the HTML:
		renderer: function (oRm, oControl) { // static function, so use  "oControl" instance instead of "this" in the renderer function
			oRm.write("<div");
			oRm.writeControlData(oControl);
			oRm.addClass("mentorShirtContainer");
			oRm.writeClasses();

			oRm.write(">");

			oRm.write("<div class=\"mentorShirtContainer\">");
			oRm.write("<div class=\"mentorShirtText mentorShirtTypeface\"></div>");
			oRm.write("<div class=\"mentorShirtNumber mentorShirtTypeface\">" + oControl.getNumber() + "</div>");
			oRm.write("<span class=\"hidden-resizer mentorShirtText mentorShirtTypeface\" style=\"visibility: hidden\">" + oControl.getText() + "</span>");
			oRm.write("</div>");

			oRm.write("</div>");
		},

		onAfterRendering: function () {
			var size;
			var desired_width = 135;
			var resizer = $(".hidden-resizer.mentorShirtText");

			this.setGender(this.getGender());

			while(resizer.width() > desired_width) {
				size = parseInt(resizer.css("font-size"), 10);
				resizer.css("font-size", size - 1);
			}

			$("div.mentorShirtText").css("font-size", size).html(resizer.html());
		},

		setGender: function(sGender) {
			var container = $(".mentorShirtContainer");
			if (sGender === "F") {
				container.removeClass("male").addClass("female");
			}
			else {
				container.removeClass("female").addClass("male");
			}
			this.setProperty("gender", sGender);
		}
	});

}, true);
