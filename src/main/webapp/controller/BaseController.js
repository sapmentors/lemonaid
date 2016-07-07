sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/core/routing/History",
    "sap/ui/core/UIComponent",
], function(Controller, History, UIComponent) {
    "use strict";

    return Controller.extend("com.sap.mentors.lemonaid.controller.BaseController", {
        /**
         * get the apps router
         * @return {object} app router
         */
        getRouter: function() {
            return UIComponent.getRouterFor(this);
        },

        /**
         * get the apps event bys
         * @return {object} app event bus
         */
		getEventBus: function(){
			return this.getOwnerComponent().getEventBus();
		},

        /**
         * get Componet
         * @return {[type]} [description]
         */
        getComponent: function() {
            return this.getOwnerComponent() ? this.getOwnerComponent() : this.getParent().getOwnerComponent();
        },

		/**
		 * Convenience method for getting the view model by name.
		 * @public
		 * @param {string} [sName] the model name
		 * @returns {sap.ui.model.Model} the model instance
		 */
		getModel: function(sName) {
			return this.getView().getModel(sName);
		},

		/**
		 * Convenience method for setting the view model.
		 * @public
		 * @param {sap.ui.model.Model} oModel the model instance
		 * @param {string} sName the model name
		 * @returns {sap.ui.mvc.View} the view instance
		 */
		setModel: function(oModel, sName) {
			return this.getView().setModel(oModel, sName);
		},

		/**
		 * Getter for the resource bundle.
		 * @public
		 * @returns {sap.ui.model.resource.ResourceModel} the resourceModel of the component
		 */
		getResourceBundle: function() {
			return this.getOwnerComponent().getModel("i18n").getResourceBundle();
		},

        /**
         * get the message manager - MM  is a unified error handler, it parses both client
         * and OData errors and needed if using $batch / two way binding -> many errors one call
         * @return {object} MessageManager
         */
        getMessageManager: function() {
            if (!this._oMessageManager) {
                this._oMessageManager = sap.ui.getCore().getMessageManager();
            }
            return this._oMessageManager;
        },

        /**
         * Navigate back
         * @param  {object} oEvent object
         */
        onNavBack: function(oEvent) {
            var oHistory, sPreviousHash;

            oHistory = History.getInstance();
            sPreviousHash = oHistory.getPreviousHash();

            if (sPreviousHash !== undefined) {
                window.history.go(-1);
            } else {
                this.getRouter().navTo("Main", {}, true /*no history*/ );
            }
        }

    });

});