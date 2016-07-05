package com.sap.mentors.lemonaid.odata;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.springframework.stereotype.Component;

import com.sap.mentors.lemonaid.entities.Attachment;
import com.sap.mentors.lemonaid.repository.AttachmentRepository;
import com.sap.mentors.lemonaid.repository.MentorRepository;

@Component
public class MediaProcessor {
	
	private MentorRepository mentorRepository;
	private AttachmentRepository attachmentRepository;
	
	private void initialize() {
		mentorRepository = (MentorRepository) SpringContextsUtil.getBean("mentorRepository");
		attachmentRepository = (AttachmentRepository) SpringContextsUtil.getBean("attachmentRepository");
	}

	public Object process(PostUriInfo uriParserResultView, InputStream content, String requestContentType) throws ODataException{
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
