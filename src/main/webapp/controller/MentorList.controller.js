/* global sap, $ */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "com/sap/mentors/lemonaid/util/formatters",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/json/JSONModel"
], function(BaseController, formatters, Filter, FilterOperator, JSONModel) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.MentorList", {

		formatters: formatters,

		/* =========================================================== */
		/* lifecycle methods                                           */
		/* =========================================================== */

		filters: {
				active: 	new sap.ui.model.Filter("MentorStatus/Id", "EQ", "active"),
				alumni: 	new sap.ui.model.Filter("MentorStatus/Id", "EQ", "alumni"),
				program: 	new sap.ui.model.Filter("MentorStatus/Id", "EQ", "program"),
				bandVegas:	new sap.ui.model.Filter("JambandLasVegas", "EQ", true),
				bandBcn:	new sap.ui.model.Filter("JambandBarcelona", "EQ", true),
				notpublic:	new sap.ui.model.Filter("PublicProfile", "EQ", false)
			},
		searchFilter: null, quickFilter: null,
		expertiseFilters: [],

		/**
		 * Called when the view controller is instantiated.
		 * @public
		 */
        onInit: function() {
        	this.table = this.byId("mentorsTable");
        	this.map = this.byId("map");
            this.ui = new JSONModel({ tableBusyDelay: 0, count: 0 });
            this.model = this.getComponent().getModel();
            this.view = this.getView();
            this.router = this.getRouter();
            this.view.setModel(this.ui, "ui");
            this.router.getRoute("Mentors").attachMatched(this.onRouteMatched, this);

			// Make sure, busy indication is showing immediately so there is no
			// break after the busy indication for loading the view's meta data is
			// ended (see promise 'oWhenMetadataIsLoaded' in AppController)
			if (this.table) {
				var that = this,
					originalBusyDelay = this.table.getBusyIndicatorDelay();
				this.table.attachEventOnce("updateFinished", function(){
					// Restore original busy indicator delay for worklist's table
					that.ui.setProperty("/tableBusyDelay", originalBusyDelay);
				});
			}
        },

        onSearchPressed: function(event) {
            var search = event.getParameters().query;

			/**
			 * Helper function to return a list of terms that include
			 * special-char equivalents.
			 * e.g. "Koehntopp" -> ["Koehntopp", "Köhntopp"]
			 */
			function expandedSearchTerms(term) {
    
    			//Feel free to add more Umlauts and their replacements
		        var specialChars = [
		        	["oe","ö"],
		        	["ae","ä"],
		        	["ue","ü"],
		        	["OE","Ö"],
		        	["AE","Ä"],
		        	["UE","Ü"],
		        	["Oe","Ö"],
		        	["Ae","Ä"],
		        	["Ue","Ü"],
		        	["ss","ß"]
		        ];

				/**
				 * Produces a list of at least one item, the original search
				 * term. Additionally may contain further items where special 
				 * chars have been substituted.
				 */
				return [term].concat(specialChars.reduce(function(a, x) {
					if (term.includes(x[0])) {
						a.push(term.replace(x[0], x[1]));
					}
					return a;
				}, []));
			}

			// Keeping this "multi term" comma separation in, even though
			// searching for multiple keywords breaks on the backend currently
			var keywords = search.split(",").reduce(function(a, x) {
				return a.concat(expandedSearchTerms(x));
			}, []);
			
			/**
			 * Build the search filter, which may have multiple terms and properties
			 * to match. Reduced to just FullName or ShirtNumber as that's all that's
			 * really appropriate.
			 */
			this.searchFilter = new Filter(
				keywords.map(function(x) {
					
					// Match shirt number if the term is digits, otherwise full name
					var path = x.match(/^\d+$/) ? "ShirtNumber" : "FullName";
					
					return new Filter({
						path : path,
						operator : FilterOperator.Contains,
						value1 : x
					});
				})
			); 
			
			/**
			 * Now we have the search filter, go ahead and apply it
			 */
			this._applyFilters();
        },

		/* =========================================================== */
		/* event handlers                                              */
		/* =========================================================== */

		/**
		 * Triggered by the table's 'updateFinished' event: after new table
		 * data is available, this handler method updates the table counter.
		 * This should only happen if the update was successful, which is
		 * why this handler is attached to 'updateFinished' and not to the
		 * table's list binding's 'dataReceived' method.
		 * @param {sap.ui.base.Event} oEvent the update finished event
		 * @public
		 */
		onUpdateFinished : function(event) {
			var that = this;
			var count = event.getParameter("total");
			this.ui.setProperty("/count", count);
			if (count && event.getSource().getBinding("items").isLengthFinal()) {
				$.each(this.filters, function (name, filters) {
					that.model.read("/Mentors/$count", {
						filters: [ filters ],
						success: function (oData) {
							that.ui.setProperty("/" + name, oData);
						}
					});
				});
			}
		},

        onMentorDetailPress: function(event) {
            this.getRouter().navTo("Mentor", {
                Id: event.getSource().getBindingContext().getProperty("Id")
            });
        },

        onAddNewMentor: function () {
            this.getRouter().navTo("MentorAddition");
        },

		/**
		 * Event handler when a filter tab gets pressed
		 * @param {sap.ui.base.Event} oEvent the filter tab event
		 * @public
		 */
		onQuickFilter: function(oEvent) {
			var key = oEvent.getParameter("key");
			this.quickFilter = key === "all" ? null : this.filters[key];
			this._applyFilters();
		},

		onRouteMatched: function() {
		},

		/**
         * Event handler for Map Marker click
         * @param  {sap.ui.base.Event} oEvent for click event
         * @public
         */
        onMarkerClick: function(oEvent) {
            if (this.activeMarker) {
                if (this.activeMarker.isOpen) {
                    this.activeMarker.infoWindowClose();
                    this.activeMarker.isOpen = false;
                } else {
                    this.activeMarker.isOpen = true; //same marker reopen
                }
            }

            if (this.activeMarker !== oEvent.getSource()) {
                this.activeMarker = oEvent.getSource();
                this.activeMarker.isOpen = true;
            }
        },
        
        onFilterPress: function(oEvent) {
			if (!this._expertiseFilter) {
				this._expertiseFilter = sap.ui.xmlfragment(
					"com.sap.mentors.lemonaid.view.MentorList.ExpertiseFilter",
					this
				);
				this.getView().addDependent(this._expertiseFilter);
				this.getView().getModel().read("/SapSoftwareSolutions", {
					urlParameters: {
						skip: 0,
						top: 1000
					},
					success: jQuery.proxy(function(data) {
						var oAppModel = this.getModel("app");
						oAppModel.setSizeLimit(1000);
						oAppModel.setProperty(
							"/SapSoftwareSolutions",
							data.results.map(function(x) {x.selected = false; return x; })
						);
						this._expertiseFilter.open();
					}, this)
				});
			} else {
				this._expertiseFilter.open();
			}
        },
        
        // Handlers for the View Settings Dialog (filter)
        onVsdConfirm: function(oEvent) {
        	var filterKeys = Object.keys(oEvent.getParameter("filterKeys"));
        	this.expertiseFilters = filterKeys.reduce(jQuery.proxy(function(a, x) {
        		return a.concat(this._buildExpertiseFilter(x));
        	}, this), []);
        	this.getView().getModel("app").setProperty("/expertiseFilterSet", this.expertiseFilters.length > 0);
        	this._applyFilters();
        },

		/* =========================================================== */
		/* internal methods                                            */
		/* =========================================================== */

		_applyFilters: function() {
			var aFilter = [];
			if (this.searchFilter)	{ aFilter.push(this.searchFilter); }
			if (this.quickFilter)	{ aFilter.push(this.quickFilter); }
			if (this.expertiseFilters.length > 0) { aFilter.push(new Filter(this.expertiseFilters)); }
			var filter = aFilter.length === 0 ? null : new Filter(aFilter, true);
            this.table.getBinding("items").filter(filter);
            this.map.getBinding("markers").filter(filter);
		},
		
		_buildExpertiseFilter: function(expertiseId) {
			return ["SapExpertise1Id", "SapExpertise2Id", "SapExpertise3Id"].map(function(property) {
				return new Filter({
					path: property,
					operator: "EQ",
					value1: expertiseId
				});
			});
		}

    });

});