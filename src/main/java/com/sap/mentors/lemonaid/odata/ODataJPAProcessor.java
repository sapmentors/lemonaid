package com.sap.mentors.lemonaid.odata;

import static com.sap.mentors.lemonaid.odata.authorization.ODataAuthorization.Operation.CREATE;
import static com.sap.mentors.lemonaid.odata.authorization.ODataAuthorization.Operation.DELETE;
import static com.sap.mentors.lemonaid.odata.authorization.ODataAuthorization.Operation.READ;
import static com.sap.mentors.lemonaid.odata.authorization.ODataAuthorization.Operation.UPDATE;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.UriInfo;
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
import com.sap.mentors.lemonaid.odata.authorization.ODataAuthorization;
import com.sap.mentors.lemonaid.odata.util.MediaProcessor;
import com.sap.mentors.lemonaid.odata.util.SpringContextsUtil;

public class ODataJPAProcessor extends ODataJPAProcessorDefault {

	private final MediaProcessor mediaProcessor = new MediaProcessor();
	private ODataAuthorization authorization = null;

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ODataJPAProcessor.class);

	public ODataJPAProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
		this.authorization = (ODataAuthorization) SpringContextsUtil.getBean("ODataAuthorization");
	}

	private Object enrichEntity(Object uriParserResultView, Object jpaEntity) throws ODataException {
		UriInfo uriInfo = (UriInfo) uriParserResultView;
		if ("Mentors".equals(uriInfo.getTargetEntitySet().getName()) && jpaEntity != null) {
			Mentor mentor = (Mentor) jpaEntity;
			mentor.setMayEdit(authorization.mayEdit(mentor));
			return mentor;
		}
		return jpaEntity;
	}

	private List<Object> enrichEntities(Object uriParserResultView, List<Object> jpaEntities) throws ODataException {
		UriInfo uriInfo = (UriInfo) uriParserResultView;
		for (Object jpaEntity : jpaEntities) {
			jpaEntity = enrichEntity(uriInfo, jpaEntity);
		}
		String entityTypeName = uriInfo.getTargetEntitySet().getEntityType().getName();
		if (entityTypeName.equals("Config")) {
			Mentor currentMentor = authorization.getCurrentMentor();
			if (currentMentor != null) jpaEntities.add(new Config("MentorId", currentMentor.getId()));
			jpaEntities.add(new Config("IsMentor", Boolean.toString(authorization.isMentor())));
			jpaEntities.add(new Config("IsProjectMember", Boolean.toString(authorization.isProjectMember())));
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
			if (!authorization.isMentor() && !authorization.isProjectMember()) {
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
	public ODataResponse readEntityMedia(final GetMediaResourceUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		authorization.check(READ, uriParserResultView);
		ODataResponse oDataResponse = null;
		try {
			oDataResponse = mediaProcessor.getMediaEntity(uriParserResultView, contentType);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse readEntity(final GetEntityUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		authorization.check(READ, uriParserResultView);
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Object jpaEntity = jpaProcessor.process(uriParserResultView);
			jpaEntity = enrichEntity(uriParserResultView, jpaEntity);
			if (jpaEntity instanceof Mentor) {
				if (!authorization.isMentor() && !authorization.isProjectMember()) {
					if (!((Mentor) jpaEntity).isPublicProfile()) {
						throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
                    }
                    //Set non public values to null!
                    if ((!((Mentor) jpaEntity).getCompanyPublic())) {
						((Mentor) jpaEntity).setCompany(null);
                    }
                    if (!((Mentor) jpaEntity).getJobTitlePublic()) {
						((Mentor) jpaEntity).setJobTitle(null);
                    }
                    if (!((Mentor) jpaEntity).getAddress1Public()) {
						((Mentor) jpaEntity).setAddress1(null);
                    }
                    if (!((Mentor) jpaEntity).getAddress2Public()) {
						((Mentor) jpaEntity).setAddress2(null);
                    }
                    if (!((Mentor) jpaEntity).getCityPublic()) {
						((Mentor) jpaEntity).setCity(null);
                    }
                    if (!((Mentor) jpaEntity).getZipPublic()) {
						((Mentor) jpaEntity).setZip(null);
                    }
                    if (!((Mentor) jpaEntity).getStatePublic()) {
						((Mentor) jpaEntity).setState(null);
                    }
                     if (!((Mentor) jpaEntity).getCountryPublic()) {
                       ((Mentor) jpaEntity).setCountryId(null);
                    }
                    if (!((Mentor) jpaEntity).getPhonePublic()) {
						((Mentor) jpaEntity).setPhone(null);
                    }
                    if (!((Mentor) jpaEntity).getEmail1Public()) {
						((Mentor) jpaEntity).setEmail1(null);
                    }
                    if (!((Mentor) jpaEntity).getEmail2Public()) {
						((Mentor) jpaEntity).setEmail2(null);
                    }
                    if (!((Mentor) jpaEntity).getSoftSkillsPublic()) {
                        ((Mentor) jpaEntity).setSoftSkill1Id(null);
                        ((Mentor) jpaEntity).setSoftSkill2Id(null);
                        ((Mentor) jpaEntity).setSoftSkill3Id(null);
                        ((Mentor) jpaEntity).setSoftSkill4Id(null);
                        ((Mentor) jpaEntity).setSoftSkill5Id(null);
                        ((Mentor) jpaEntity).setSoftSkill6Id(null);
                    }
                    if(!((Mentor) jpaEntity).getAttachmentsPublic()) {
						((Mentor) jpaEntity).setAttachments(null);
                    }
				}else{
                        ((Mentor) jpaEntity).setPublicLongitude(((Mentor) jpaEntity).getLongitude());
                        ((Mentor) jpaEntity).setPublicLatitude(((Mentor) jpaEntity).getLatitude());
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
		authorization.check(READ, uriParserResultView);
		ODataResponse oDataResponse = null;
		augmentFilter((UriInfoImpl) uriParserResultView);
		try {
			oDataJPAContext.setODataContext(getContext());
			List<Object> jpaEntities = jpaProcessor.process(uriParserResultView);
            jpaEntities = enrichEntities(uriParserResultView, jpaEntities);
            for(Object jpaEntity : jpaEntities){
                if (jpaEntity instanceof Mentor) {
				    if (!authorization.isMentor() && !authorization.isProjectMember()) {

                    }else{
                        ((Mentor) jpaEntity).setPublicLongitude(((Mentor) jpaEntity).getLongitude());
                        ((Mentor) jpaEntity).setPublicLatitude(((Mentor) jpaEntity).getLatitude());
                    }

                }
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
		authorization.check(READ, uriParserResultView);
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
		authorization.check(CREATE, uriParserResultView);
		ODataResponse oDataResponse = null;
		try {
			if (uriParserResultView.getTargetEntitySet().getEntityType().hasStream()) {
				Object createdJpaEntity = mediaProcessor.process(uriParserResultView, content, requestContentType);
				createdJpaEntity = enrichEntity(uriParserResultView, createdJpaEntity);
				oDataResponse = responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);
			} else {
				oDataJPAContext.setODataContext(getContext());
				Object createdJpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
				createdJpaEntity = enrichEntity(uriParserResultView, createdJpaEntity);
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
		authorization.check(UPDATE, uriParserResultView);
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Object jpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
			jpaEntity = enrichEntity(uriParserResultView, jpaEntity);
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntity);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse deleteEntity(final DeleteUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		authorization.check(DELETE, uriParserResultView);
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Object deletedObj = jpaProcessor.process(uriParserResultView, contentType);
			deletedObj = enrichEntity(uriParserResultView, deletedObj);
			oDataResponse = responseBuilder.build(uriParserResultView, deletedObj);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse createEntityLink(final PostUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final String contentType) throws ODataException {
		authorization.check(CREATE, uriParserResultView);
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
		authorization.check(UPDATE, uriParserResultView);
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
		authorization.check(DELETE, uriParserResultView);
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
		authorization.setContext((HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT));
		return super.executeBatch(handler, contentType, content);
	}

}
