/* global sap */

// In mock mode, the mock server intercepts HTTP calls and provides fake output to the
// client without involving a backend system. But special backend logic, such as that
// performed by function imports, is not automatically known to the mock server. To handle
// such cases, the app needs to define specific mock requests that simulate the backend
// logic using standard HTTP requests (that are again interpreted by the mock server) as
// shown below.

// Please note:
// The usage of synchronous calls is only allowed in this context because the requests are
// handled by a latency-free client-side mock server. In production coding, asynchronous
// calls are mandatory.

sap.ui.define(["sap/ui/base/Object"], function(Object) {
    "use strict";

    return Object.extend("com.sap.mentors.lemonaid.localServic.MockRequests", {
        constructor: function(oMockServer) {
            this._oMockServer = oMockServer;
            this._oMockServer.attachBefore("GET", this.onGetMentorsBefore.bind(this), "Mentors");
            this._oMockServer.attachAfter("GET", this.onGetMentorsAfter.bind(this), "Mentors");
        },

        //TODO - mock https://www.gravatar.com/avatar/ requests, set up a default image || real image saved locally

        /**
         * [getRequests description]
         * @return {[type]} [description]
         */
        getRequests: function() {
            return [ /**this.mockMentorsExpandedRequest() , this.mockGravitarRquest() */ ];
        },

        /**
         * [mockMentorsExpandedRequest description]
         * @return {[type]} [description]
         */
        mockMentorsExpandedRequest: function() {
            return {
                method: "GET",
                path: new RegExp("Mentors?(.*)"),
                response: this.getMentorsExpanded.bind(this)
            };
        },

        /**
         * [mockGravitarRquest description]

         * @return {[type]} [description]
         */
        mockGravitarRquest: function() {
            return {
                method: "GET",
                path: new RegExp("https://www.gravatar.com/avatar/?(.*)"),
                response: this.getMentorsAvatar.bind(this)
            };
        },

        /**
         * [getMentorsExpanded description]
         * @param  {[type]} oEvent [description]
         */
        getMentorsExpanded: function(oEvent) {
            return;
        },

        /**
         * [getMentorsAvatar description]
         * @param  {[type]} oEvent [description]
         */
        getMentorsAvatar: function(oEvent) {
            return;
        },

        /**
         * [onGetMentorsBefore description]
         * @param  {[type]} oEvent [description]
         */
        onGetMentorsBefore: function(oEvent) {
            return;
        },

        /**
         * [onGetMentorsAfter description]
         * @param  {[type]} oEvent [description]
         */
        onGetMentorsAfter: function(oEvent) {
            return;
        },


        /**
         * [getUri description]
         * @param  {[type]} sEntitySetName [description]
         * @param  {[type]} oEntity        [description]
         * @return {[type]}                [description]
         */
        getUri: function(sEntitySetName, oEntity) {
            return this._srvUrl + sEntitySetName + "(" + this._oMockServer._createKeysString(this._oMockServer._mEntitySets[sEntitySetName],
                    oEntity) +
                ")";
        },

        /**
         * [addEntityToMockData description]
         * @param {[type]} sEntitySetName [description]
         * @param {[type]} oEntity        [description]
         */
        addEntityToMockData: function(sEntitySetName, oEntity) {
            this._oMockServer._oMockdata[sEntitySetName] = this._oMockServer._oMockdata[sEntitySetName].concat([oEntity]);
        },

        /**
         * [initNewEntity description]
         * @param  {[type]} oXhr        [description]
         * @param  {[type]} sEntityName [description]
         * @param  {[type]} oKeys       [description]
         * @return {[type]}             [description]
         */
        initNewEntity: function(oXhr, sEntityName, oKeys) {
            var oEntity = JSON.parse(oXhr.requestBody);
            if (oEntity) {
                oKeys = oKeys || {};
                this._oMockServer._completeKey(this._oMockServer._mEntitySets[sEntityName], oKeys, oEntity);
                this._oMockServer._enhanceWithMetadata(this._oMockServer._mEntitySets[sEntityName], [oEntity]);
                return oEntity;
            }
            return null;
        },

        /**
         * [_getRequestBody description]
         * @param  {[type]} oXhr [description]
         * @return {[type]}      [description]
         */
        _getRequestBody: function(oXhr) {
            // returns the request body as a Json object
            var oXhrModel = new sap.ui.model.json.JSONModel();
            oXhrModel.setJSON(oXhr.requestBody);
            return oXhrModel.getData();
        }

    });
});
