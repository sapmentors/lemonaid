package com.sap.mentors.lemonaid.external;

import org.springframework.stereotype.Component;

@Component
public class ScnImage {

	public String getProfilePhoto(final String scnUrl) {
		if (scnUrl.contains("people.sap.com")) {
			return scnUrl.replace("people.sap.com", "people.sap.com/avatar");
		} else {
			return scnUrl + "/avatar/144.png";
		}
	}

}
