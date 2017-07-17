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

				var afilters = [];
				var outerFilters = [];
				var searchTerms = search.split(" "); //words separated by space are considered as separate search terms.
				for (var k = 0; k < searchTerms.length; k++) {
					afilters.push(new Filter("FullName", FilterOperator.Contains, searchTerms[k]));
					afilters.push(new Filter("ShirtNumber", FilterOperator.Contains, searchTerms[k]));
					afilters.push(new Filter("RelationshipToSap/Name", FilterOperator.Contains, searchTerms[k]));
					afilters.push(new Filter("MentorStatus/Name", FilterOperator.Contains, searchTerms[k]));
					outerFilters.push(new Filter(afilters));
					afilters = [];
				}

            this.searchFilter = search ? new Filter(outerFilters) : null;
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


		/* =========================================================== */
		/* internal methods                                            */
		/* =========================================================== */

		_applyFilters: function() {
			var aFilter = [];
			if (this.searchFilter)	{ aFilter.push(this.searchFilter); }
			if (this.quickFilter)	{ aFilter.push(this.quickFilter); }
			var filter = aFilter.length === 0 ? null : new Filter(aFilter, true);
            this.table.getBinding("items").filter(filter);
            this.map.getBinding("markers").filter(filter);
		}

    });

});
