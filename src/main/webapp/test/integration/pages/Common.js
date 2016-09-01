/* global sap */

sap.ui.define([
        "sap/ui/test/Opa5",
        "com/sap/mentors/lemonaid/localService/mockserver"
    ],
    function(Opa5, mockserver) {
        "use strict";
        var sComponent = "com.sap.mentors.lemonaid";

        // start the mockserver
        mockserver.init();

        return Opa5.extend("test.integration.pages.Common", {
            constructor: function(oConfig) {
                Opa5.apply(this, arguments);

                this._oConfig = oConfig;
            },

            /**
             * i start my app
             * @param  {[type]} oOptions [description]
             */
            iStartMyApp: function(oOptions) {
                this.iStartMyUIComponent({
                    componentConfig: {
                        name: sComponent
                    },
                    hash: ""
                });
            },

            /**
             * [iLookAtTheScreen description]
             * @return {[type]} [description]
             */
            iLookAtTheScreen: function() {
                return this;
            },


            /**
             * [getEntitySet description]
             * @param  {string} sEntitySet name of entity set
             * @return {array}           values in entityset
             */
            getEntitySet: function(sEntitySet) {
                return mockserver.getMockServer().getEntitySetData(sEntitySet);
            }


        });
    });
