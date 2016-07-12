package com.sap.mentors.lemonaid.external;

import org.springframework.stereotype.Component;

@Component
public class SCN {

	public String getProfilePhoto(final String scnUrl) {
		return scnUrl + "/avatar/144.png";
	}

}
