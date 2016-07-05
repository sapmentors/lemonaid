package com.sap.mentors.lemonaid.odata;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAProcessorDefault;

import com.sap.mentors.lemonaid.entities.Config;

public class ODataJPAProcessor extends ODataJPAProcessorDefault {

	private final MediaProcessor mediaProcessor = new MediaProcessor();
	private HttpServletRequest batchRequest = null;

	public ODataJPAProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
	}

	private boolean isInRole(String roleName) {
		ODataContext ctx = ODataJPAContextImpl.getContextInThreadLocal();  
		HttpServletRequest request = (HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);  
    	return request == null ? batchRequest.isUserInRole(roleName) : request.isUserInRole(roleName);
	}
	
	private boolean isMentor() {
    	return isInRole("Mentor");
	}

	private boolean isAlumnus() {
    	return isInRole("Alumnus");
	}

	private boolean isProjectMember() {
    	return isInRole("ProjectMember");
	}

	@Override
	public ODataResponse readEntitySet(final GetEntitySetUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			List<Object> jpaEntities = jpaProcessor.process(uriParserResultView);
			if (uriParserResultView.getTargetEntitySet().getEntityType().getName().equals("Config")) {
				jpaEntities.add(new Config("IsMentor", Boolean.toString(isMentor())));
				jpaEntities.add(new Config("IsAlumnus", Boolean.toString(isAlumnus())));
				jpaEntities.add(new Config("IsProjectMember", Boolean.toString(isProjectMember())));
			}
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntities, contentType);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse createEntity(final PostUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final String contentType) throws ODataException {
		if (!isMentor() && !isAlumnus() && !isProjectMember()) {
			return ODataResponse.entity("Unauthorized").status(HttpStatusCodes.UNAUTHORIZED).contentHeader("text/html").build();
		}
		ODataResponse oDataResponse = null;
		try {
			if (uriParserResultView.getTargetEntitySet().getEntityType().hasStream()) {
				Object createdJpaEntity = mediaProcessor.process(uriParserResultView, content, requestContentType);
				oDataResponse = responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);
			} else {
				oDataJPAContext.setODataContext(getContext());
				Object createdJpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
				oDataResponse = responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);
			}
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse updateEntity(final PutMergePatchUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final boolean merge, final String contentType) throws ODataException {
		if (!isMentor() && !isAlumnus() && !isProjectMember()) {
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
		if (!isMentor() && !isAlumnus() && !isProjectMember()) {
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
		if (!isMentor() && !isAlumnus() && !isProjectMember()) {
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
		if (!isMentor() && !isAlumnus() && !isProjectMember()) {
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
		if (!isMentor() && !isAlumnus() && !isProjectMember()) {
			return ODataResponse.entity("Unauthorized").status(HttpStatusCodes.UNAUTHORIZED).contentHeader("text/html").build();
		}
		try {
			oDataJPAContext.setODataContext(getContext());
			jpaProcessor.process(uriParserResultView, contentType);
			return ODataResponse.newBuilder().build();
		} finally {
			close();
		}
	}

	@Override
	public ODataResponse executeBatch(BatchHandler handler, String contentType, InputStream content)
			throws ODataException {
		ODataContext ctx = ODataJPAContextImpl.getContextInThreadLocal();  
		this.batchRequest = (HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);  
		return super.executeBatch(handler, contentType, content);
	}

	
	
}