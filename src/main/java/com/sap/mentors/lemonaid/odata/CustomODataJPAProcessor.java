package com.sap.mentors.lemonaid.odata;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAProcessorDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomODataJPAProcessor extends ODataJPAProcessorDefault {

	private static final Logger log = LoggerFactory.getLogger(CustomODataJPAProcessor.class);

	public CustomODataJPAProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
	}

	private boolean isInRole(String roleName) {
		ODataContext ctx = ODataJPAContextImpl.getContextInThreadLocal();  
		HttpServletRequest request = (HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);  
    	return request.isUserInRole(roleName);
	}
	
	private boolean isMentor() {
    	return isInRole("Mentor");
	}

	private boolean isProjectMember() {
    	return isInRole("ProjectMember");
	}

	@Override
	public ODataResponse createEntity(final PostUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final String contentType) throws ODataException {
		if (!isMentor() && !isProjectMember()) {
			return ODataResponse.entity("Unauthorized").status(HttpStatusCodes.UNAUTHORIZED).contentHeader("text/html").build();
		}
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Object createdJpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
			oDataResponse = responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse updateEntity(final PutMergePatchUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final boolean merge, final String contentType) throws ODataException {
		if (!isMentor() && !isProjectMember()) {
			return ODataResponse.entity("Unauthorized").status(HttpStatusCodes.UNAUTHORIZED).contentHeader("text/html").build();
		}
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Object jpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntity);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse deleteEntity(final DeleteUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		if (!isMentor() && !isProjectMember()) {
			return ODataResponse.entity("Unauthorized").status(HttpStatusCodes.UNAUTHORIZED).contentHeader("text/html").build();
		}
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Object deletedObj = jpaProcessor.process(uriParserResultView, contentType);
			oDataResponse = responseBuilder.build(uriParserResultView, deletedObj);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse createEntityLink(final PostUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final String contentType) throws ODataException {
		if (!isMentor() && !isProjectMember()) {
			return ODataResponse.entity("Unauthorized").status(HttpStatusCodes.UNAUTHORIZED).contentHeader("text/html").build();
		}
		try {
			oDataJPAContext.setODataContext(getContext());
			jpaProcessor.process(uriParserResultView, content, requestContentType, contentType);
			return ODataResponse.newBuilder().build();
		} finally {
			close();
		}
	}

	@Override
	public ODataResponse updateEntityLink(final PutMergePatchUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final String contentType) throws ODataException {
		if (!isMentor() && !isProjectMember()) {
			return ODataResponse.entity("Unauthorized").status(HttpStatusCodes.UNAUTHORIZED).contentHeader("text/html").build();
		}
		try {
			oDataJPAContext.setODataContext(getContext());
			jpaProcessor.process(uriParserResultView, content, requestContentType, contentType);
			return ODataResponse.newBuilder().build();
		} finally {
			close();
		}
	}

	@Override
	public ODataResponse deleteEntityLink(final DeleteUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		try {
			oDataJPAContext.setODataContext(getContext());
			jpaProcessor.process(uriParserResultView, contentType);
			return ODataResponse.newBuilder().build();
		} finally {
			close();
		}
	}

}