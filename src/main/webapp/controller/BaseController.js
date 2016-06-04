sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/core/routing/History",
    "sap/ui/core/UIComponent",
    "../util/avatarHelper"
], function(Controller, History, UIComponent, avatarHelper) {
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
         * get Componet
         * @return {[type]} [description]
         */
        getComponent: function() {
            return this.getOwnerComponent();
        },

        /**
         * get the model with name from the component
         * @param  {string} sName the name of the model
         * @return {object}       named model
         */
        getModel: function(sName) {
            return this.getView().getModel(sName) || this.getOwnerComponent().getModel(sName);
        },

        /**
         * get the resource model
         * @return {[type]} [description]
         */
        getResourceBundle: function() {
            return this.getModel("i18n").getResourceBundle();
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
        },

        /**
         * get Avatar URL for Email address
         * @param  {string} sEmail Email Address
         * @return {string}        URL of Avatar
         */
        getAvatarURL: function(sEmail) {
            return avatarHelper.getAvatarURL(sEmail);
        }

    });

});
