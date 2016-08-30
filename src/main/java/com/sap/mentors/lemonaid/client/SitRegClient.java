package com.sap.mentors.lemonaid.client;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;

import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.springframework.stereotype.Component;

@Component
public class SitRegClient extends GenericODataClient {

	public static final String SERVICE_URL = "https://sapmentorsa5a504e08.hana.ondemand.com/com/sap/sapmentors/sitreg/odatapublic/service.xsodata";

	public ODataFeed readFeed(String entitySetName)
			throws IOException, ODataException {
		return readFeed(SERVICE_URL, APPLICATION_JSON, entitySetName);
	}

}
