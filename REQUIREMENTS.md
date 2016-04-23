# Requirements

### Functionality

* List/Attributes could change over time (managed by the SAP Project Team)
* Each mentor should have a mentor profile similar to the current SCN profile
* It should be possible to add links to twitter, SCN, LinkIn profile pages
* It should be possible to make custom selections from the database to create e.g. mailing lists and export these (to e.g. mailchimp)
* It should be possible to add attachments and pictures (pdf/doc/jpg) to profiles
* An audit trail should be produced, to give insight in when what was change by whom.
* It should be possible to import data into the database from e.g. excel 
* It should be possible to manage data of and make a distinction between SAP Mentors and SAP Mentor Alumni
* It should be possible to manage data of and make a distinction between SAP employee mentors, SAP mentors at partners, SAP mentors at end-users or self-employed SAP mentors)

### Volume

* It should be possible to manage data of all mentors and mentor alumni. Estimated number of profiles is 500.

### Security

* Authentication should leverage an IDP, preferrably linked to the SAP/SCN ID service
* It should be possible to add all mentors and alumni as users of the applications
* It should be possible to grant specific access rights to particular users, identified as part of the project team
* Every mentors has insight in their own profile and is able to manage it (read/write)
* Every mentor should have insight in the profile of other mentors, but should not be able to change it (read)
* Members of the SAP project team should have access to all mentor data (read/write/create)

### Scalability

* It should be possible to extend the application in the future with addional features
* In the future the application could potential also share (smaller portions of) mentors data with the larger SAP community, based on mentors permissions