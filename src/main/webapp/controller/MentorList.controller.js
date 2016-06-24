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

		/**
		 * Called when the view controller is instantiated.
		 * @public
		 */
        onInit: function() {
        	this.table = this.byId("table");
        	this.map = this.byId("map");
            this.ui = new JSONModel({ tableBusyDelay: 0, count: 0, mentors: 0, alumni: 0 });
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
            var filter = new Filter("FullName", FilterOperator.Contains, search); 
            this.table.getBinding("items").filter(filter);
            this.map.getBinding("markers").filter(filter);
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
			if (count && this.table.getBinding("items").isLengthFinal()) {
				jQuery.each(['active', 'alumni'], function (idx, key) {
					that.model.read("/Mentors/$count", {
						filters: [ new sap.ui.model.Filter("MentorStatus/Id", "EQ", key) ],
						success: function (oData) {
							that.ui.setProperty("/" + key, oData);
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

		/**
		 * Event handler when a filter tab gets pressed
		 * @param {sap.ui.base.Event} oEvent the filter tab event
		 * @public
		 */
		onQuickFilter: function(oEvent) {
			var filter = 
				oEvent.getParameter("key") === "all" ? 
					[] :
					new sap.ui.model.Filter("MentorStatus/Id", "EQ", oEvent.getParameter("key"));
			this.table.getBinding("items").filter(filter);
			this.map.getBinding("markers").filter(filter);
		},
		
		onRouteMatched: function() {
			var that = this;
			jQuery.sap.delayedCall(500, this, function() {
				that.view.byId("searchField").focus();
			});
		}

		/* =========================================================== */
		/* internal methods                                            */
		/* =========================================================== */

    });

});