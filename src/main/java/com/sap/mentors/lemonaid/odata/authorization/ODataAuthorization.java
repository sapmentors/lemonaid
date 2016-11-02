package com.sap.mentors.lemonaid.odata.authorization;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.mentors.lemonaid.entities.Mentor;
import com.sap.mentors.lemonaid.repository.MentorRepository;
import com.sap.mentors.lemonaid.rest.User;
import com.sap.security.um.service.UserManagementAccessor;
import com.sap.security.um.user.UserProvider;

@Component
public class ODataAuthorization {

	private HttpServletRequest request = null;
	private static final Logger log = LoggerFactory.getLogger(ODataAuthorization.class);
	@Autowired private MentorRepository mentorRepository;
	
	public static enum Operation {
		CREATE, READ, UPDATE, DELETE
	}

	private static ThreadLocal<Map<String, Object>> threadLocalData = new ThreadLocal<Map<String, Object>>();
	static {
		threadLocalData.set(new HashMap<String, Object>());
	}
	
	public void setContext(HttpServletRequest request) {
		this.request = request; 
	}

	private HttpServletRequest getRequest() {
		ODataContext ctx = ODataJPAContextImpl.getContextInThreadLocal();  
		HttpServletRequest request = (HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);  
		return request == null ? this.request : request;
	}

	public boolean isMentor() {
		User user = getCurrentUser();
		if (user == null) return false;
		String email = user.getEmail();
    	if (email == null) return false;
    	List<Mentor> mentors = mentorRepository.findByEmail(email);
    	if (mentors == null || mentors.isEmpty()) return false;
    	return true;
	}

	public boolean isProjectMember() {
    	return isInRole("ProjectMember");
	}

	public boolean isInRole(String roleName) {
    	return getRequest().isUserInRole(roleName);
	}

	public Principal getUserPrincipal() {
		return getRequest().getUserPrincipal();
	}

	public Mentor getCurrentMentor() {
		User user = getCurrentUser();
		if (user != null) {
			List<Mentor> mentors = mentorRepository.findByEmail(user.getEmail());
			for (Mentor mentor : mentors) {
				return mentor;
			}
		}
		return null;
	}
	
	public User getCurrentUser() {
    	Principal userPrincipal = getUserPrincipal();
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

	public static ThreadLocal<Map<String, Object>> getThreadLocalData() {
		return threadLocalData;
	}
	
	public boolean mayEdit(Mentor mentor) {
		User currentUser = getCurrentUser();
		if (currentUser == null) return false;
		if (isProjectMember()) return true;
		if (currentUser.getEmail() != null) {
			if (mentor.getEmail1() != null && currentUser.getEmail().toUpperCase().equals(mentor.getEmail1().toUpperCase())) return true; 
			if (mentor.getEmail2() != null && currentUser.getEmail().toUpperCase().equals(mentor.getEmail2().toUpperCase())) return true;
		}
		return false; 
	
	}
	
	private boolean isOwnProfile(Object uriParserResultView) throws EdmException {
		UriInfo uriInfo = (UriInfo) uriParserResultView;
		if ("Mentors".equals(uriInfo.getTargetEntitySet().getName())) {
			for (KeyPredicate keyPredicate : uriInfo.getKeyPredicates()) {
				if ("Id".equals(keyPredicate.getProperty().getName()) && keyPredicate.getLiteral().equals(getCurrentMentor().getId())) {
					return true;
				}
			}
		}
		return false;
	}

	private void storeUsernameInTreadlocal() {
		User user = getCurrentUser();
		if (threadLocalData.get() == null) threadLocalData.set(new HashMap<String, Object>());
		if (user != null) threadLocalData.get().put("UserName", user.getName());
	}

	public void check(Operation operation, Object uriParserResultView) throws ODataUnauthorizedException, EdmException {

		// Store the current user name in thread local
		storeUsernameInTreadlocal();
		
		switch (operation) {
			case READ: 
				break;
			case CREATE: 
				if (!isProjectMember()) {
					throw new ODataUnauthorizedException();
				}
				break;
			case UPDATE: 
				if (!isProjectMember() && !isOwnProfile(uriParserResultView)) {
					throw new ODataUnauthorizedException();
				}
				break;
			case DELETE: 
				if (!isProjectMember()) {
					throw new ODataUnauthorizedException();
				}
				break;
			default:
				throw new ODataUnauthorizedException();
		}
		
	}
	
}