sap.ui.define([
] , function () {
	"use strict";
	return {

    	generateSapGuid: function() {
    		return this._generate4Chars().toUpperCase() + 
    		    this._generate4Chars().toUpperCase() +
    		    this._generate4Chars().toUpperCase() +
    		    this._generate4Chars().toUpperCase() +
    		    this._generate4Chars().toUpperCase() +
    		    this._generate4Chars().toUpperCase() + 
    		    this._generate4Chars().toUpperCase() + 
    		    this._generate4Chars().toUpperCase();
    	},

    	generateGuid: function() {
    		return this._generate4Chars() + 
    		    this._generate4Chars() + "-" + 
    		    this._generate4Chars() + "-" + 
    		    this._generate4Chars() + "-" + 
    		    this._generate4Chars() + "-" + 
    		    this._generate4Chars() + 
    		    this._generate4Chars() + 
    		    this._generate4Chars();
    	},

    	_generate4Chars: function() {
    		return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
    	}

    };

});