sap.ui.define([
    "./crypto-js/core",
    "./crypto-js/md5"
], function() {
    "use strict";
    /**
     * revealing module which encapsulates the getting of an avatar, may make it easier to mock / offline
     */

    var sDefaultImgSrc = "images/logo.png";
    var sBaseURL = "//www.gravatar.com/avatar/";
    var sLemonaideURL = "http://scn.sap.com/community/image/2422/1.png";

    /**
     * get URL for the Mentors Avator
     * @param  {string} sEmail Email Address
     * @return {string}        URL to avatar
     */
    function fnGetAvatarURL(sEmail) {
        var sSrc = sDefaultImgSrc;
        if (sEmail) {
            sSrc = sBaseURL + window.CryptoJS.MD5(sEmail).toString() + "?s=144&d=" + sLemonaideURL;
        }
        return sSrc;

    }


            searchForLocation: function(oObject, fnCallBack) {
            mapUtils.search({
                "address": oObject.City + " " + oObject.State
            }).done(this.addMentorLocation.bind(this)).done(fnCallBack);
        },

       var  fnAddMentorLocation(aResults, sStatus) {
            if (sStatus === "OK") {
                var oMentorsModel = this.getModel("mentors");
                var aEntries = oMentorsModel.getData().locations;
                var oObject = this.getView().getBindingContext().getObject();
                var oLocation = aResults[0];
                var oEntry = {
                    Id: oObject.Id,
                    lat: oLocation.geometry.location.lat(),
                    lng: oLocation.geometry.location.lng(),
                    info: oLocation.formatted_address
                };

                aEntries.push(oEntry);
                oMentorsModel.setData({
                    locations: aEntries
                });
            }
        }

    return {
        getAvatarURL: fnGetAvatarURL
    };
});
