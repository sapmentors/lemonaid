package com.sap.mentors.lemonaid.external;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="gravatar")
@Component
public class GravatarImage {

	private final String img_endpoint = "https://www.gravatar.com/avatar/";
	private final String api_endpoint = "https://secure.gravatar.com/xmlrpc";
	private URL endpoint = null;
	private String user = null;
	private String apiKey = null;
	private String password = null;

	private final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	private final XmlRpcClient client = new XmlRpcClient();

	private String hash(String s) {
		return DigestUtils.md5Hex(s.toLowerCase().trim());
	}

	public String getUrlForHash(final String hash) {
		return img_endpoint + hash;
	}

	public String getUrlForEmail(final String email) {
		return getUrlForHash(hash(email));
	}
	
	public String getUrlOfUser() {
		return getUrlForHash(this.user);
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void setUser(final String user) {
		this.user = user;
		try {
			this.endpoint = new URL(this.api_endpoint + "?user=" + user);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while creating URL for Gravatar calls");
		}
        this.config.setServerURL(endpoint);
		this.client.setConfig(config);
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(final String apiKey) {
		this.apiKey = apiKey;
	}
			
	Object callFunction(final String method, final Map<String, Object> parameters) throws IOException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.putAll(parameters);
		if (this.apiKey != null) {
			map.put("apikey", this.apiKey);
		} else if (this.password != null) {
			map.put("password", this.password);
		} else {
			throw new RuntimeException("User or API key not set yet");
		}
        try {
			return client.execute(method, new Object [] { map });
		} catch (XmlRpcException e) {
			throw new IOException(e);
		}
	}
	
	public boolean emailExists(final String email) throws IOException {
		return this.emailsExist(new ArrayList<String>(Arrays.asList(email))).get(email);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Boolean> emailsExist(final List<String> emails) throws IOException {
        ArrayList<String> hashes = new ArrayList<String>();
        for (String email : emails) {
        		hashes.add(hash(email));
        }
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("hashes", hashes);
        HashMap<String, Boolean> retval = new HashMap<String, Boolean>();
        if (hashes.isEmpty()) return retval;
        HashMap<String, Integer> result = (HashMap<String, Integer>) callFunction("grav.exists", map);
        for (String email : emails) {
        		retval.put(email, result.get(hash(email)) > 0);
        }
        return retval;
	}

	public boolean hashExists(final String hash) throws IOException {
		return this.hashesExist(new ArrayList<String>(Arrays.asList(hash))).get(hash);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Boolean> hashesExist(final List<String> hashes) throws IOException {
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("hashes", hashes);
        HashMap<String, Boolean> retval = new HashMap<String, Boolean>();
        if (hashes.isEmpty()) return retval;
		HashMap<String, Integer> result = (HashMap<String, Integer>) callFunction("grav.exists", map);
        for (String hash : hashes) {
        	retval.put(hash, result.get(hash) > 0);
        }
        return retval;
	}
	
}