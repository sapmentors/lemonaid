/* global sap, jQuery */

sap.ui.define([
	"sap/ui/model/odata/v2/ODataModel",
	"sap/ui/model/odata/ODataUtils",
	"com/sap/mentors/lemonaid/util/ODataUtils"
], function (BaseControl, ODataUtils, LemonaidODataUtils) {
	"use strict";

	var ODataModel = BaseControl.extend("com.sap.mentors.lemonaid.model.ODataModel", {

	});

    /* =========================================================== */
    /* Overrides                                                   */
    /* =========================================================== */

    /**
	 * process a 'TwoWay' change
	 *
	 * @param {string} sKey Key of the entity to change
	 * @param {object} oData The entry data
	 * @param {boolean} [sUpdateMethod] Sets MERGE/PUT method
	 * @returns {object} oRequest The request object
	 * @private
	 */
	ODataModel.prototype._processChange = function(sKey, oData, sUpdateMethod) {
		var oPayload, oEntityType, mParams, sMethod, sETag, sUrl, mHeaders, aUrlParams, oRequest, oUnModifiedEntry, that = this;

		// delete expand properties = navigation properties
		oEntityType = this.oMetadata._getEntityTypeByPath(sKey);

		//default to MERGE
		if (!sUpdateMethod) {
			sUpdateMethod = "MERGE";
		}

		// do a copy of the payload or the changes will be deleted in the model as well (reference)
		oPayload = jQuery.sap.extend(true, {}, this._getObject("/" + sKey, true), oData);

		if (oData.__metadata && oData.__metadata.created){
			sMethod = oData.__metadata.created.method ? oData.__metadata.created.method : "POST";
			sKey = oData.__metadata.created.key;
			mParams = oData.__metadata.created;
			if (oData.__metadata.created.functionImport){
				// update request url params with changed data from payload
				mParams.urlParameters = this._createFunctionImportParameters(oData.__metadata.created.key, sMethod, oPayload );
				// clear data
				oPayload = undefined;
			}
		} else if (sUpdateMethod === "MERGE") {
			sMethod = "MERGE";
			// get original unmodified entry for diff
			oUnModifiedEntry = this.oData[sKey];
		} else {
			sMethod = "PUT";
		}

		// remove metadata, navigation properties to reduce payload
		if (oPayload && oPayload.__metadata) {
			for (var n in oPayload.__metadata) {
				if (n !== "type" && n !== "uri" && n !== "etag" && n !== "content_type" && n !== "media_src") {
					delete oPayload.__metadata[n];
				}
			}
		}

		// delete nav props
		if (oPayload && oEntityType) {
			var aNavProps = this.oMetadata._getNavigationPropertyNames(oEntityType);
			jQuery.each(aNavProps, function(iIndex, sNavPropName) {
				delete oPayload[sNavPropName];
			});
		}

		if (sMethod === "MERGE" && oEntityType && oUnModifiedEntry) {
			jQuery.each(oPayload, function(sPropName, oPropValue) {
				if (sPropName !== "__metadata") {
					// remove unmodified properties and keep only modified properties for delta MERGE
					if (jQuery.sap.equal(oUnModifiedEntry[sPropName], oPropValue) && !that.isLaundering("/" + sKey + "/" + sPropName)) {
						delete oPayload[sPropName];
					}
				}
			});
			// check if we have unit properties which were changed and if yes sent the associated unit prop also.
			var sPath = "/" + sKey, sUnitNameProp;
			jQuery.each(oPayload, function(sPropName, oPropValue) {
				if (sPropName !== "__metadata") {
					sUnitNameProp = that.getProperty(sPath + "/" + sPropName + "/#@sap:unit");
					if (sUnitNameProp) {
						// set unit property only if it wasn't modified. Otherwise it should already exist on the payload.
						if (oPayload[sUnitNameProp] === undefined) {
							oPayload[sUnitNameProp] = oUnModifiedEntry[sUnitNameProp];
						}
					}
				}
			});
		}

		// remove any yet existing references which should already have been deleted
		oPayload = this._removeReferences(oPayload);

		// add navigation links
		oPayload = LemonaidODataUtils.addNavigationLinks(this, oPayload, oEntityType, sKey, mParams);	// <-- This is the portion that has been added (in the override)

		//get additional request info for created entries
		aUrlParams = mParams && mParams.urlParameters ? ODataUtils._createUrlParamsArray(mParams.urlParameters) : undefined;
		mHeaders = mParams && mParams.headers ? this._getHeaders(mParams.headers) : this._getHeaders();
		sETag = mParams && mParams.eTag ? mParams.eTag : this.getETag(oPayload);

		sUrl = this._createRequestUrl("/" + sKey, null, aUrlParams, this.bUseBatch);

		oRequest = this._createRequest(sUrl, sMethod, mHeaders, oPayload, sETag);

		if (this.bUseBatch) {
			oRequest.requestUri = oRequest.requestUri.replace(this.sServiceUrl + "/","");
		}

		return oRequest;
	};

	return ODataModel;

});
