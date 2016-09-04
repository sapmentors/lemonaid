/* global sap */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "com/sap/mentors/lemonaid/util/formatters",
    "sap/ui/model/json/JSONModel",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator"
], function(BaseController, formatters, JSONModel, Filter, FilterOperator) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.EventList", {
    	
    	formatters: formatters,

		/* =========================================================== */
		/* lifecycle methods                                           */
		/* =========================================================== */

		/**
		 * Called when the view controller is instantiated.
		 * @public
		 */
        onInit: function() {
            this.ui = new JSONModel();
            this.model = this.getComponent().getModel();
            this.view = this.getView();
        	this.component = this.getComponent();
            this.router = this.getRouter();
            this.view.setModel(this.ui, "ui");
            this.router.getRoute("Events").attachMatched(this.onRouteMatched, this);
        	this.list = this.byId("eventList");
        	this.details = this.byId("eventDetails");
            this.currentId = null;
            this.whenListLoaded = new Promise(function (resolve) {
				this._resolveListLoaded = resolve;
			}.bind(this));
        },

		/* =========================================================== */
		/* event handlers                                              */
		/* =========================================================== */

		onRouteMatched: function(event) {
			var Id = this.currentId = event.getParameter("arguments").Id;
			this.whenListLoaded.then(function() {
				if (this.currentId) {
					jQuery.each(this.list.getItems(), function(i, item) {
						if (item.getBindingContext().getObject().Id === Id) {
							this._selectItem(item);
						}
					}.bind(this));
				}
			}.bind(this));
		},

		onUpdateFinished: function(event) {
			if (!this.currentId) {
				var firstEvent = this.list.getItems()[0];
				if (firstEvent) {
		            this.getRouter().navTo("Events", {
		                Id: firstEvent.getBindingContext().getProperty("Id")
		            });
				}
			}
			this._resolveListLoaded();
		},

		onSearch: function(event) {
            var search = event.getParameters().query;
            var filter = new Filter([
		            new Filter("Name", FilterOperator.Contains, search),
		            new Filter("Location", FilterOperator.Contains, search)
            	]);
            this.list.getBinding("items").filter(filter);
		},
		
		onSelectionChange: function(event) {
			var item = event.getParameter("listItem") || event.getSource();
            this.getRouter().navTo("Events", {
                Id: item.getBindingContext().getProperty("Id")
            });
		},

		/* =========================================================== */
		/* internal methods                                            */
		/* =========================================================== */

		_selectItem: function(item) {
			this.list.setSelectedItem(item);
			this.details.bindElement(item.getBindingContext().getPath());
		}

    });

});