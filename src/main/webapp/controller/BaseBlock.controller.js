/* global sap */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "com/sap/mentors/lemonaid/util/formatters"
], function(BaseController, formatters) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.BaseBlock", {

		formatters: formatters

    });

});
