/* global sap, jQuery */

sap.ui.define([
    "sap/ui/core/util/MockServer",
    "./MockRequests"
], function(MockServer, MockRequests) {
    "use strict";
    var oMockServer;

    return {
        /**
         * Initializes the mock server. You can configure the delay with the URL parameter "serverDelay"
         * The local mock data in this folder is returned instead of the real data for testing.
         *
         * @public
         */
        init: function() {
            var oUriParameters = jQuery.sap.getUriParameters();

            oMockServer = new MockServer({
                rootUri: "/odata.svc/"
            });

            // configure mock server with a delay of 1s
            MockServer.config({
                autoRespond: true,
                autoRespondAfter: (oUriParameters.get("serverDelay") || 0)
            });

            var sPath = jQuery.sap.getModulePath("com.sap.mentors.lemonaid.localService");
            // load local mock data
            oMockServer.simulate(sPath + "/metadata.xml", {
                sMockdataBaseUrl: sPath + "/mockdata"
            });


            var oRequests = new MockRequests(oMockServer);
            var aRequests = oMockServer.getRequests();
            oMockServer.setRequests(aRequests.concat(oRequests.getRequests()));
            oMockServer.start();

            jQuery.sap.log.info("Running the app with mock data");
        },

        /**
         * @public returns the mockserver of the app, should be used in integration tests
         * @returns {sap.ui.core.util.MockServer} the mockserver instance
         */
        getMockServer: function() {
            return oMockServer;
        }
    };
});
