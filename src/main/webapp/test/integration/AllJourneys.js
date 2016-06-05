jQuery.sap.require("sap.ui.qunit.qunit-css");
jQuery.sap.require("sap.ui.thirdparty.qunit");
jQuery.sap.require("sap.ui.qunit.qunit-junit");
jQuery.sap.require("sap.ui.qunit.qunit-coverage");
jQuery.sap.require("sap.ui.qunit.QUnitUtils");
jQuery.sap.require("sap.ui.thirdparty.sinon");
jQuery.sap.require("sap.ui.thirdparty.sinon-qunit");
QUnit.config.autostart = false;


sap.ui.require([
    "sap/ui/test/Opa5",
    "test/integration/pages/Common",
    "test/integration/pages/App",
    "test/integration/pages/Main",
    "test/integration/pages/Mentor"
], function(Opa5, Common) {
    "use strict";

    Opa5.extendConfig({
        arrangements: new Common(),
        viewNamespace: "com.sap.mentors.lemonaid.view."
    });

    sap.ui.require([
        "test/integration/NavigationJourney"
    ], function() {
        //TODO - coverage not working in latest version
        /* filter only custom code for coverage results */
        // if (window.blanket) {
        //     var fnOriginalReport = window.blanket.report;
        //     window.blanket.report = function() {
        //         var oResults = window._$blanket,
        //             oFiltered = {},
        //             oRetValue;
        //         for (var sFile in oResults) {
        //             if (sFile.substr(0, 15) === "com/sap/mentors") {
        //                 oFiltered[sFile] = oResults[sFile];
        //             }
        //         }
        //         window._$blanket = oFiltered;
        //         oRetValue = fnOriginalReport.apply(this, arguments);
        //         window._$blanket = oResults;
        //         return oRetValue;
        //     };
        // }


        QUnit.start();
    });
});
