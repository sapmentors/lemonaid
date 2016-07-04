sap.ui.define([
	] , function () {
		"use strict";
		return {

			formatMapInfo: function(name, number, photo, status) {
				return '<table><tr><td valign="top">' +
				       '<img class="placeIcon" src="images/pin-' + (status === 'active' ? 'mentor' : 'alumni') + '.png"/></td>' +
				       '<td><b>' + name + '</b><br/>' + number + '</td>' +
				       '</tr><tr><td colspan="2" align="center"><img src="' + photo + '"/></td>' +
				       '</tr></table>';
			}

		};
	}
);