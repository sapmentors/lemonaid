# Architecture

### Foundation

After asking for feedback from the various mentors involved in the development of the application, it has been suggested to use HCP, CRM/C4C or Successfactors as a foundation for the application. In SAP Jam the pro's and con's have been gathered, which have been discussed in a phone conference. Looking at features only, a custom solutions and a solution based on CRM ended up equally high. Eventually factors as availability of license and dependencies with additional system components have led to the decision to build a custom solution. The team has found sponsorship from SAP's executives to sponsor a HANA Cloud Platform account to go ahead with the solution.

### SAP Hana Cloud Platform

The SAP HANA Cloud Platform is used as a foundation for the SAP Mentors Lemonaid application:

* As we noticed that the Java skillset of the involved back-end developers was better than the current HANA XS skillset, a decision was made to develop the back-end logic in Java. In the future this might change, and a move to HANA XS should be anticipated. Hence, the amount of back-end logic should be kept to a minimum.
* The front-end logic will be developed using SAP UI5 (or Open UI5)
* HANA Cloud Platform persistence layer will be leveraged to store mentors and master data. To optimize access times, it would be preferable to store data in a HANA schema.