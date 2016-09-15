package com.sap.mentors.lemonaid.odata;

import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMediaResourceUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.core.uri.expression.FilterParserImpl;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAProcessorDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.mentors.lemonaid.entities.Config;
import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.repository.MentorRepository;
import com.sap.mentors.lemonaid.rest.User;
import com.sap.security.um.service.UserManagementAccessor;
import com.sap.security.um.user.UserProvider;

public class ODataJPAProcessor extends ODataJPAProcessorDefault {

	private final MediaProcessor mediaProcessor = new MediaProcessor();
	private HttpServletRequest batchRequest = null;

	private static final Logger log = LoggerFactory.getLogger(ODataJPAProcessor.class);

	private MentorRepository mentorRepository = null;
	
	public ODataJPAProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
	}
	
	private HttpServletRequest getRequest() {
		ODataContext ctx = ODataJPAContextImpl.getContextInThreadLocal();  
		HttpServletRequest request = (HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);  
		return request == null ? batchRequest : request;
	}

	private boolean isInRole(String roleName) {
    	return getRequest().isUserInRole(roleName);
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
	
	private Mentor getCurrentMentor() {
		User user = getCurrentUser();
		if (user != null) {
			if (this.mentorRepository == null) this.mentorRepository = (MentorRepository) SpringContextsUtil.getBean("mentorRepository");
			List<Mentor> mentors = mentorRepository.findByEmail(user.getEmail());
			for (Mentor mentor : mentors) {
				return mentor;
			}
		}
		return null;
	}
	
	private User getCurrentUser() {
    	Principal userPrincipal = getRequest().getUserPrincipal();
		if (userPrincipal != null) {
            try {
			    // UserProvider provides access to the user storage
			    UserProvider users = UserManagementAccessor.getUserProvider();
	
			    // Read the currently logged in user from the user storage
			    com.sap.security.um.user.User user = users.getUser(userPrincipal.getName());
	
			    // Print the user name and email
			    return new User(
			    		userPrincipal.getName(),
			    		user.getAttribute("firstname"),
			    		user.getAttribute("lastname"),
			    		user.getAttribute("email"),
			    		user.getAttribute("firstname") + " " + user.getAttribute("lastname") + " (" + userPrincipal.getName() + ")"
			    	);
			} catch (Exception e) {
				log.error("Error: " + e.getMessage());
			}			
	        return new User(userPrincipal.getName());
		}
        return null;
	}
	
	private Object enrichEntity(EdmEntityType entityType, Object jpaEntity) throws ODataException {
		User user = getCurrentUser();
		if (entityType.getName().equals("Mentor") && jpaEntity != null) {
			Mentor mentor = (Mentor) jpaEntity;
			mentor.setMayEdit(
					( isProjectMember() ) ||
					( user != null && user.getName() != null && mentor.getUserId() != null && mentor.getUserId().toUpperCase().equals(user.getName().toUpperCase()) ) ||
					( user != null && user.getEmail() != null && mentor.getEmail1() != null && mentor.getEmail1().toUpperCase().equals(user.getEmail().toUpperCase()) ) || 
					( user != null && user.getEmail() != null && mentor.getEmail2() != null && mentor.getEmail2().toUpperCase().equals(user.getEmail().toUpperCase()) ) 
				);
			if (mentor.getUserId() == null && user != null && user.getName() != null && user.getName().length() > 0) {
				if (( user != null && user.getEmail() != null && mentor.getEmail1() != null && mentor.getEmail1().toUpperCase().equals(user.getEmail().toUpperCase()) ) || 
					( user != null && user.getEmail() != null && mentor.getEmail2() != null && mentor.getEmail2().toUpperCase().equals(user.getEmail().toUpperCase()) )) {
					mentor.setUserId(user.getName());
					oDataJPAContext.getEntityManager().persist(mentor);
				}
			}
			return mentor;
		}
		return jpaEntity;
	}
	
	private List<Object> enrichEntities(EdmEntityType entityType, List<Object> jpaEntities) throws ODataException {
		for (Object jpaEntity : jpaEntities) {
			jpaEntity = enrichEntity(entityType, jpaEntity);
		}
		return jpaEntities;
	}

	private UriInfoImpl augmentFilter(UriInfoImpl uriParserResultView) 
			throws ODataException{
		if (uriParserResultView.getTargetEntitySet().getEntityType().getName().equals("Mentor")) {
			// Search on FullName should be case insensitive
			if (uriParserResultView.getFilter() != null && uriParserResultView.getFilter().getExpressionString() != null && uriParserResultView.getFilter().getExpressionString().length() > 0) {
				uriParserResultView.setFilter(
					(new FilterParserImpl(uriParserResultView.getTargetEntitySet().getEntityType())).parseFilterString(
						uriParserResultView.getFilter().getExpressionString().replaceAll("substringof\\(('.+'),(FullName)\\)", "substringof\\(tolower\\($1\\),tolower\\($2\\)\\)")
					));
			}
			// If the user is not a mentor, only return mentors profiles that are marked as publicly visible
			if (!this.isMentor() && !this.isAlumnus() && !this.isProjectMember()) {  
				uriParserResultView.setFilter(
					(new FilterParserImpl(uriParserResultView.getTargetEntitySet().getEntityType())).parseFilterString(
						(uriParserResultView.getFilter() != null && uriParserResultView.getFilter().getExpressionString() != null && uriParserResultView.getFilter().getExpressionString().length() > 0 ?
							"(" + uriParserResultView.getFilter().getExpressionString() + ") and " : "") + 
						"PublicProfile eq true"));
			}
		}
		return uriParserResultView;
	}

	@Override
	public ODataResponse readEntityMedia(final GetMediaResourceUriInfo uriInfo, final String contentType)
			throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataResponse = mediaProcessor.getMediaEntity(uriInfo, contentType);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse readEntity(final GetEntityUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Object jpaEntity = jpaProcessor.process(uriParserResultView);
			jpaEntity = enrichEntity(uriParserResultView.getTargetEntitySet().getEntityType(), jpaEntity);
			if (jpaEntity instanceof Mentor) {
				if (!this.isMentor() && !this.isAlumnus() && !this.isProjectMember()) {
					if (!((Mentor) jpaEntity).isPublicProfile()) {
						throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
					}
				}
			}
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntity, contentType);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse readEntitySet(GetEntitySetUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		ODataResponse oDataResponse = null;
		augmentFilter((UriInfoImpl) uriParserResultView);
		try {
			oDataJPAContext.setODataContext(getContext());
			List<Object> jpaEntities = jpaProcessor.process(uriParserResultView);
			jpaEntities = enrichEntities(uriParserResultView.getTargetEntitySet().getEntityType(), jpaEntities);
			Mentor currentMentor = getCurrentMentor();
			if (uriParserResultView.getTargetEntitySet().getEntityType().getName().equals("Config")) {
				jpaEntities.add(new Config("IsMentor", Boolean.toString(isMentor())));
				jpaEntities.add(new Config("IsAlumnus", Boolean.toString(isAlumnus())));
				jpaEntities.add(new Config("IsProjectMember", Boolean.toString(isProjectMember())));
				if (currentMentor != null) jpaEntities.add(new Config("MentorId", currentMentor.getId()));
			}
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntities, contentType);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse countEntitySet(final GetEntitySetCountUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		ODataResponse oDataResponse = null;
		augmentFilter((UriInfoImpl) uriParserResultView);
		try {
			oDataJPAContext.setODataContext(getContext());
			long jpaEntityCount = jpaProcessor.process(uriParserResultView);
			oDataResponse = responseBuilder.build(jpaEntityCount);
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
				createdJpaEntity = enrichEntity(uriParserResultView.getTargetEntitySet().getEntityType(), createdJpaEntity);
				oDataResponse = responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);
			} else {
				oDataJPAContext.setODataContext(getContext());
				Object createdJpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
				createdJpaEntity = enrichEntity(uriParserResultView.getTargetEntitySet().getEntityType(), createdJpaEntity);
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
			jpaEntity = enrichEntity(uriParserResultView.getTargetEntitySet().getEntityType(), jpaEntity);
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
			deletedObj = enrichEntity(uriParserResultView.getTargetEntitySet().getEntityType(), deletedObj);
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