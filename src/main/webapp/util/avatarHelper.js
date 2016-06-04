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

    return {
        getAvatarURL: fnGetAvatarURL
    };
});
