package com.sap.mentors.lemonaid.odata.authorization;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataHttpException;

/**
 * Exceptions of this class will result in a HTTP status 403 forbidden
 * 
 */
public class ODataUnauthorizedException extends ODataHttpException {

	private static final long serialVersionUID = 1L;

	public static final MessageReference COMMON = createMessageReference(ODataUnauthorizedException.class, "COMMON");

	public ODataUnauthorizedException() {
		super(COMMON, HttpStatusCodes.UNAUTHORIZED);
	}

	public ODataUnauthorizedException(final MessageReference context) {
		super(context, HttpStatusCodes.UNAUTHORIZED);
	}

	public ODataUnauthorizedException(final MessageReference context, final Throwable cause) {
		super(context, cause, HttpStatusCodes.UNAUTHORIZED);
	}

	public ODataUnauthorizedException(final MessageReference context, final String errorCode) {
		super(context, HttpStatusCodes.UNAUTHORIZED, errorCode);
	}

	public ODataUnauthorizedException(final MessageReference context, final Throwable cause, final String errorCode) {
		super(context, cause, HttpStatusCodes.UNAUTHORIZED, errorCode);
	}
}