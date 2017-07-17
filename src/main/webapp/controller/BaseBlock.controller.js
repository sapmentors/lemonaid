/* global sap */

sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "com/sap/mentors/lemonaid/util/formatters"
], function (BaseController, formatters) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.BaseBlock", {

        formatters: formatters,
        onInit: function () {
            var oEventBus = sap.ui.getCore().getEventBus();
            oEventBus.subscribe("BlockChannel", "readBlockContent", this.handleReadBlockContent, this);
            oEventBus.subscribe("BlockChannel", "removeDataBinding", this.handleRemoveDataBinding, this);
        },

        handleReadBlockContent: function (channel, event, data) {
            var oEventBus = sap.ui.getCore().getEventBus();
            var oView = this.getView();
            var controlsArray = oView.getContent()[0]._aElements;
            var oData = [];
            var oTest = {}
            if (oView.sId.includes("creation")) {
                oTest["viewName"] = oView.getId()
                var notFoundLableCounter = 0;
                for (var i in controlsArray) {
                    var controlsId = controlsArray[i].sId;
                    if (controlsId.includes("input")) {
                        oData.push(controlsArray[i].getValue());
                        oTest[controlsId.split("-")[6]] = controlsArray[i].getValue();

                    } else if (controlsId.includes("select")) {
                        oData.push(controlsArray[i].getSelectedKey());
                        oTest[controlsId.split("-")[6]] = controlsArray[i].getSelectedKey();

                    } else if (controlsId.includes("switch")) {
                        oData.push(controlsArray[i].getState());
                        oTest[controlsId.split("-")[6]] = controlsArray[i].getState();
                    }
                }
            } else {
                oTest = null;
            }
            oEventBus.publish("MyChannelAddition", "notifyMentorAdditionHandler", {
                data: oTest
            });

        },

        handleRemoveDataBinding: function (channel, event, data) {
            var oView = this.getView();
            if (oView.sId.includes("creation")) {
                var controlsArray = oView.getContent()[0]._aElements;
                for (var i in controlsArray) {
                    var controlsId = controlsArray[i].sId;
                    if (controlsId.includes("input")) {
                        controlsArray[i].unbindAggregation("value", true)
                    } else if (controlsId.includes("select")) {
                        controlsArray[i].unbindAggregation("selectedKey", true)
                    } else if (controlsId.includes("switch")) {
                        controlsArray[i].unbindAggregation("state", true)
                    }
                }
            }
        },


    });

});
