package com.sap.mentors.lemonaid.odata.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.GetMediaResourceUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.springframework.stereotype.Component;

import com.sap.mentors.lemonaid.entities.Attachment;
import com.sap.mentors.lemonaid.repository.AttachmentRepository;
import com.sap.mentors.lemonaid.repository.MentorRepository;

@Component
public class MediaProcessor {
	
	private MentorRepository mentorRepository = null;
	private AttachmentRepository attachmentRepository = null;
	
	private void initialize() {
		if (mentorRepository == null) {
			mentorRepository = (MentorRepository) SpringContextsUtil.getBean("mentorRepository");
		}
		if (attachmentRepository == null) {
			attachmentRepository = (AttachmentRepository) SpringContextsUtil.getBean("attachmentRepository");
		}
	}

	public ODataResponse getMediaEntity(final GetMediaResourceUriInfo uriInfo, final String contentType) throws ODataException {
		initialize();
		if (uriInfo.getTargetEntitySet().getEntityType().getName().equals("Attachment")) {
			for (KeyPredicate keyPredicate : uriInfo.getKeyPredicates()) {
				if (keyPredicate.getProperty().getName().equals("Id")) {
					Attachment attachment = attachmentRepository.findOne(keyPredicate.getLiteral());
					return ODataResponse
							.fromResponse(EntityProvider.writeBinary(attachment.getMimeType(), attachment.getData()))
							.header("Content-Disposition", "attachment ;filename=\"" + attachment.getFileName() + "\"")
							.build();
				}
			}
		}
		throw new ODataNotFoundException(ODataNotFoundException.COMMON);
	}

	public Object process(PostUriInfo uriParserResultView, InputStream content, String requestContentType) throws ODataException {
		initialize();
		ODataContext ctx = ODataJPAContextImpl.getContextInThreadLocal();
		HttpServletRequest request = (HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);  		
		if (uriParserResultView.getTargetEntitySet().getEntityType().getName().equals("Attachment")) {
			Attachment attachment = new Attachment();
			attachment.setId(UUID.randomUUID().toString());
			attachment.setMimeType(requestContentType);
			attachment.setFileName(request.getHeader("slug"));
			try {
				attachment.setData(IOUtils.toByteArray(content));
			} catch (IOException e) {
				throw new ODataException(e);
			}
			attachment.setFileSize(attachment.getData().length);
			attachment.setLastModified(Calendar.getInstance());
			for (KeyPredicate keyPredicate : uriParserResultView.getKeyPredicates()) {
				if (keyPredicate.getProperty().getName().equals("Id")) {
					attachment.setMentorId(mentorRepository.findOne(keyPredicate.getLiteral()));
				}
			}
			attachmentRepository.save(attachment);
			attachment.setData(null);
			return attachment;
		}
		return null;
	}

}
