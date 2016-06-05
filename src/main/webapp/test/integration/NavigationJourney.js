sap.ui.define([
    "sap/ui/test/opaQunit"
], function(opaTest) {
    "use strict";

    QUnit.module("Simple Navigation");

    opaTest("Should see the application loaded and show a table with all the Mentors", function(Given, When, Then) {
        // Arrangements
        Given.iStartMyApp();

        //Actions
        When.onTheMainPage.iLookAtTheScreen();

        // Assertions
        Then.onTheMainPage.iShouldSeeTheTableOfMentors().
        and.theTableShouldHaveAllEntries();
    });

    opaTest("Should navigate to the Detail page and show the selected Mentors information", function(Given, When, Then) {
        //Actions
        When.onTheMainPage.iPressOnTheFirstMentor();

        // Assertions
        Then.onTheMentorPage.iShouldSeeTheFirstMentorSelected();
    });


    opaTest("Should navigate back to Main List and show all Mentors", function(Given, When, Then) {
        //Actions
        When.onTheMentorPage.iPressTheBackButton();

        // Assertions
        Then.onTheMainPage.iShouldSeeTheTableOfMentors().
        and.theTableShouldHaveAllEntries().
        and.iTeardownMyUIComponent();
    });
});
