/* global sap */

sap.ui.define([
        "sap/ui/test/Opa5",
        "test/integration/pages/Common",
        "sap/ui/test/matchers/BindingPath",
        "sap/ui/test/matchers/PropertyStrictEquals",
        "sap/ui/test/actions/Press"
    ],
    function(Opa5, Common, BindingPath, PropertyStrictEquals, Press) {
        "use strict";
        var sViewName = "Mentor";

        Opa5.createPageObjects({
            onTheMentorPage: {
                baseClass: Common,
                actions: {
                    iPressTheBackButton: function() {
                        return this.waitFor({
                            controlType: "sap.m.Button",
                            viewName: sViewName,
                            matchers: new PropertyStrictEquals({ name: "type", value: "Back" }),
                            actions: new Press(),
                            errorMessage: "Did not find the nav button on Mentor Detail page"
                        });
                    }
                },
                assertions: {
                    iShouldSeeTheFirstMentorSelected: function() {
                        return this.waitFor({
                            id: "ObjectPageLayout",
                            viewName: sViewName,
                            matchers: function(oObjectPageLayout) {
                                // the path to the First mentor is stored in the shared context
                                var sPath = this.getContext().sFirstMentorPath;
                                return new BindingPath({ path: sPath });
                            }.bind(this),
                            success: function() {
                                this.getContext().sFirstMentorPath = undefined;
                                Opa5.assert.ok(true, "Selected Mentor bound");
                            },
                            errorMessage: "Selected Mentor not bound"
                        });
                    }
                }
            }
        });
    });
