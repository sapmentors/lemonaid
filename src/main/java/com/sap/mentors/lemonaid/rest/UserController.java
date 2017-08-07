package com.sap.mentors.lemonaid.rest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.mentors.lemonaid.client.TwitterClient;
import com.sap.security.um.service.UserManagementAccessor;
import com.sap.security.um.user.UserProvider;

@RestController
@RequestMapping("/userapi/currentUser")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.GET)
    User getUser(HttpServletRequest request, HttpServletResponse response) {
        Principal userPrincipal = request.getUserPrincipal();
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", 0);
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
        return new User();
    }

}
