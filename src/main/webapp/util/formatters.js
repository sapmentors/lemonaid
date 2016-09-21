sap.ui.define([
	] , function () {
		"use strict";
		return {
			
			formatEventLogo: function(source) {
				var baseUrl = jQuery.sap.getModulePath("com.sap.mentors.lemonaid");
				switch(source) {
				    case "SITREG": return baseUrl + "/images/insidetrack.png";
				    default: return baseUrl + "/images/sap.png";
				}
			},

			formatMapInfo: function(name, number, photo, status) {
				return '<table><tr><td valign="top">' +
				       '<img class="placeIcon" src="images/pin-' + (status === 'active' ? 'mentor' : 'alumni') + '.png"/></td>' +
				       '<td><b>' + name + '</b><br/>' + number + '</td>' +
				       '</tr><tr><td colspan="2" align="center"><img src="' + photo + '" width="144" height="144"/></td>' +
				       '</tr></table>';
			},

			formatAddress: function(name, address1, address2, city, state, zip, country, locale) {
				if (typeof name === "undefined" || name === null) { name = ""; }
				if (typeof address1 === "undefined" || address1 === null) { address1 = ""; }
				if (typeof address2 === "undefined" || address2 === null) { address2 = ""; }
				if (typeof city === "undefined" || city === null) { city = ""; }
				if (typeof state === "undefined" || state === null) { state = ""; }
				if (typeof zip === "undefined" || zip === null) { zip = ""; }
				if (typeof country === "undefined" || country === null) { country = ""; }
				if (typeof locale === "undefined" || locale === null || typeof this.formatters.addressFormatters[locale] === "undefined") {
					locale = ""; // Use default locale if it isn't recognized
				}
				return jQuery.grep( 
					this.formatters.addressFormatters[locale]({
						name: name,
						address1: address1,
						address2: address2,
						city: city,
						state: state,
						zip: zip,
						country: country,
						locale: locale
					}), function(n) {
						return !!n;
					}).join("\n");
			},
			
			commaSeparated: function() {
				return $.grep(Array.prototype.slice.call(arguments), Boolean).join(", ");
			},
			
			addressFormatters: {
				NL: function(a) {
						return [
						        a.name,
						        a.address1,
						        a.address2,
						        a.zip + "  " + a.city,
						        a.country
						   ];
					},
				
				US: function(a) {
						return [
						        a.name,
						        a.address1,
						        a.address2,
						        a.city + (a.city ? ", " : "") + a.state + " " + a.zip,
						        a.country
						   ];
					},
					
				AU: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
						        a.city + (a.city ? ", " : "") + a.state + " " + a.zip,
								a.country
						   ];
					},

				BR: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city + "  " + a.state,
								a.country  
						   ];
					},

				BG: function(a) {
						return [
								a.country,
								a.state,
								a.zip + "  " + a.city,
								a.address1,
								a.address2,
								a.name
						   ];
					},

				CA: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
						        a.city + (a.city ? ", " : "") + a.state + " " + a.zip,
								a.country
						   ];
					},

				CN: function(a) {
						return [
								a.country,
								a.state + "  " + a.city,
								a.address1,
								a.address2,
								a.name
						   ];
					},

				HR: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				RS: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				SI: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				CZ: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				DK: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.locale + "  " + a.zip + "  " + a.city,
								a.country
						   ];
					},

				FI: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.country
						   ];
					},

				FR: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.country
						   ];
					},

				DE: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.locale + "  " + a.zip + "  " + a.city,
								a.country
						   ];
					},
							
				GR: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.country
						   ];
					},
	
				HU: function(a) {
						return [
								a.name,
								a.city,
								a.address1,
								a.address2,
								a.zip,
								a.state,
								a.country
						   ];
					},

				IT: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.locale + "  " + a.zip,
								a.city + "  " + a.state,
								a.country
						   ];
					},

				JP: function(a) {
						return [
								a.country,
								a.zip + "  " + a.state + "  " + a.city,
								a.address1,
								a.address2,
								a.name
						   ];
					},

				KR: function(a) {
						return [
								a.country,
								a.zip,
								a.state,
								a.city,
								a.address1,
								a.address2,
								a.name
						   ];
					},

				AR: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				BO: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				CL: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				CO: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				EC: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				FK: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				GF: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				GY: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				PY: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				PE: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				SR: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				UY: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				VE: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				MY: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.country
						   ];
					},

				SG: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.country + "  " + a.zip
						   ];
					},

				NO: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "   " + a.city,
								a.country
						   ];
					},

				PL: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				PT: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.country
						   ];
					},

				RO: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.state,
								a.country
						   ];
					},

				RU: function(a) {
						return [
								a.country,
								a.zip,
								a.state,
								a.city,
								a.address1,
								a.address2,
								a.name
						   ];
					},

				ES: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.country
						   ];
					},

				SE: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "   " + a.city,
								a.country
						   ];
					},

				CH: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + "  " + a.city,
								a.country
						   ];
					},

				TR: function(a) {
						return [
								a.name,
								a.address1,
								a.address2,
								a.zip + (a.zip ? ", " : "") + a.city,
								a.country
						   ];
					},
				"": function(a) {
						return [
						        a.name,
						        a.address1,
						        a.address2,
						        a.city + (a.city ? ", " : "") + a.state + " " + a.zip,
						        a.country
						   ];
				}
			}

		};
	}
);