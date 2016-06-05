sap.ui.define([
        "sap/ui/test/Opa5",
        "test/integration/pages/Common",
    ],
    function(Opa5, Common) {
        "use strict";
        var sViewName = "App";


        Opa5.createPageObjects({
            onTheAppPage: {
                baseClass: Common,
                actions: {},
                assertions: {}
            }
        });
    });
