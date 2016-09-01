/* global sap */

sap.ui.define([
        "sap/ui/test/Opa5",
        "test/integration/pages/Common",
        "sap/ui/test/matchers/AggregationLengthEquals",
        "sap/ui/test/actions/Press",
        "sap/ui/test/matchers/BindingPath",
        "sap/ui/test/actions/EnterText",
        "sap/ui/test/matchers/PropertyStrictEquals"
    ],
    function(Opa5, Common, AggregationLengthEquals, Press, BindingPath, EnterText, PropertyStrictEquals) {
        "use strict";
        var sViewName = "Main";
        var sTableId = "table";
        var sSearchFieldId = "searchField";
        var sFirstMentorPath = "/Mentors('S0004623911')";

        Opa5.createPageObjects({
            onTheMainPage: {
                baseClass: Common,
                actions: {
                    iPressOnTheFirstMentor: function() {
                        return this.waitFor({
                            controlType: "sap.m.ColumnListItem",
                            viewName: sViewName,
                            matchers: new BindingPath({ path: sFirstMentorPath }),
                            success: function() {
                                this.getContext().sFirstMentorPath = sFirstMentorPath;
                            },
                            actions: new Press(),
                            errorMessage: "Cannot find the Mentor list"
                        });

                    },

                    iEnterRonaldInTheSearchField: function() {
                        return this.waitFor({
                            id: sSearchFieldId,
                            viewName: sViewName,
                            actions: [new EnterText({ text: "Ronald" }), new Press()],
                            errorMessage: "Failed to find search field in Main view."
                        });
                    }
                },
                assertions: {
                    iShouldSeeTheTableOfMentors: function() {
                        return this.waitFor({
                            id: sTableId,
                            viewName: sViewName,
                            success: function(oTable) {
                                Opa5.assert.ok(oTable, "Found the object Table");
                            },
                            errorMessage: "Can't see the Mentors table."
                        });
                    },

                    theTableShouldHaveAllEntries: function() {
                        var iExpectedNumberOfItems;
                        var iAllEntries = this.getEntitySet("Mentors").length || 0;

                        return this.waitFor({
                            id: sTableId,
                            viewName: sViewName,
                            matchers: function(oTable) {
                                // If there are less items in the list than the growingThreshold, only check for this number.
                                iExpectedNumberOfItems = Math.min(oTable.getGrowingThreshold(), iAllEntries);
                                return new AggregationLengthEquals({ name: "items", length: iExpectedNumberOfItems }).isMatching(oTable);
                            },
                            success: function(oTable) {
                                Opa5.assert.strictEqual(oTable.getItems().length, iExpectedNumberOfItems, "The growing Table has " + iExpectedNumberOfItems + " items");
                            },
                            errorMessage: "Table does not have all entries."
                        });
                    },

                    iShouldSeeOnlyRonaldMcDonaldInTheTable: function() {
                        this.waitFor({
                            id: sTableId,
                            viewName: sViewName,
                            matchers: new AggregationLengthEquals({ name: "items", length: 1 }),
                            success: function(oTable) {
                                Opa5.assert.strictEqual(oTable.getItems().length, 1, "The table has only 1 entry");
                            },
                            errorMessage: "Table does not have all entries."
                        });

                        return this.waitFor({
                            controlType: "sap.m.ObjectIdentifier",
                            viewName: sViewName,
                            matchers: new PropertyStrictEquals({ name: "title", value: "Ronald McDonald" }),
                            success: function(objectIdentifier) {
                                Opa5.assert.ok(objectIdentifier, "Found Ronald McDonald");
                            },
                            errorMessage: "Table does not have all entries."
                        });
                    }

                }
            }
        });
    });
