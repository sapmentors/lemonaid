sap.ui.define([
	"com/sap/mentors/lemonaid/util/avatarHelper"
	] , function (avatarHelper) {
		"use strict";
		return {

			formatMapInfo: function(name, number, email, status) {
				return '<table><tr><td valign="top">' +
				       '<img class="placeIcon" src="images/pin-' + (status === 'active' ? 'mentor' : 'alumni') + '.png"/></td>' +
				       '<td><b>' + name + '</b><br/>' + number + '</td>' +
				       '</tr><tr><td colspan="2" align="center"><img src="' + avatarHelper.getAvatarURL(email) + '"/></td>' +
				       '</tr></table>';
			}

		};
	}
);