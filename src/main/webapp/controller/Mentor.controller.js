sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "openui5/googlemaps/MapUtils"
], function(BaseController, mapUtils) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.Mentor", {
        mapUtils: mapUtils,

        onInit: function() {
            this.getRouter().getRoute("Mentor").attachMatched(this._onRouteMatched, this);
        },

        _onRouteMatched: function(oEvent) {
            this.sMentorId = oEvent.getParameter("arguments").Id;
            this.getModel().metadataLoaded().then(this.bindView.bind(this));
        },

        bindView: function() {
            this.getView().bindElement({
                path: this.getModel().createKey("/Mentors", { Id: this.sMentorId }),
                parameters: {
                    expand: 'MentorStatus,RelationshipToSap,Country,Topic1,Topic2,Topic3'
                },
                events: {
                    change: this._onBindingChange.bind(this)
                }
            });
        },

        _onBindingChange: function() {
            this.setMentorLocation();
            // set avatar
            var sEmail = this.getView().getBindingContext().getProperty("Email1");
            this.oView.byId("header").setObjectImageURI(this.getAvatarURL(sEmail));
        },

        /**
         * set the location of the new binding
         */
        setMentorLocation: function() {
            var oMap = this.getView().byId("idMap");
            var oObject = this.getView().getBindingContext().getObject();
            var oMentorsModel = this.getModel("mentors");
            var aEntries = oMentorsModel.getData();

            // check for an existing entry
            var fnFilter = function(oEntry) {
                return (oEntry.Id === oObject.Id);
            };

            var oEntry = aEntries.filter(fnFilter)[0] || null;

            if (!oEntry) {
                this.searchForLocation(oObject, this.addMentorLocation.bind(this), this.setMentorLocation.bind(this));
            } else {
                var oContext = oMentorsModel.getContext("/" + aEntries.indexOf(oEntry));
                oMap.setBindingContext(oContext, "mentors");      
     
            }
        },

        /**
         * search for the location, if found add to the model and set the map
         * @param  {object} oObject     context data for entry
         * @param  {function} fnCallBack1 add the location to the model
         * @param  {function} fnCallBack2 set the location
         */
        searchForLocation: function(oObject, fnCallBack1, fnCallBack2) {
            var sAddress = oObject.City + " " + oObject.State;

            mapUtils.search({
                "address": sAddress
            }).then(fnCallBack1).then(fnCallBack2);
        },

        /**
         * read the first returned entry from the geocode call and set it to model
         * @param {array} aResults array of found locations
         * @param {string} sStatus  status of the call
         */
        addMentorLocation: function(aResults, sStatus) {
            if (sStatus === "OK") {
                var oMentorsModel = this.getModel("mentors");
                var aEntries = oMentorsModel.getData();
                var oObject = this.getView().getBindingContext().getObject();
                var oLocation = aResults[0];
                var oEntry = {
                    Id: oObject.Id,
                    lat: oLocation.geometry.location.lat(),
                    lng: oLocation.geometry.location.lng(),
                    info: oLocation.formatted_address
                };

                aEntries.push(oEntry);
                oMentorsModel.setData(aEntries);
            }
        }
    });
});

