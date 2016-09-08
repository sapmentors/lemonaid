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
				// },
				// events: {
				//     generateImage: { }
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
			oRm.write("<div class=\"MentorShirtText\"></div>");
			oRm.write("<div class=\"MentorShirtNumber\">" + oControl.getNumber() + "</div>");
			oRm.write("<span class=\"hidden-resizer MentorShirtText\" style=\"visibility: hidden\">" + oControl.getText() + "</span>");
			oRm.write("</div>");

			oRm.write("</div>");
		},

		onAfterRendering: function () {
			var size;
			var desiredWidth = 135;
			var resizer = $(".hidden-resizer.MentorShirtText");

			while(resizer.width() > desiredWidth) {
				size = parseInt(resizer.css("font-size"), 10);
				resizer.css("font-size", size - 1);
			}

			$("div.MentorShirtText").css("font-size", size).html(resizer.html());
		}
	});

}, true);
