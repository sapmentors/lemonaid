package com.sap.mentors.lemonaid.external;

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
public class Gravatar {

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

	public String getUrlForHash(String hash) {
		return img_endpoint + hash;
	}

	public String getUrlForEmail(String email) {
		return getUrlForHash(hash(email));
	}
	
	public String getUrlOfUser() {
		return getUrlForHash(this.user);
	}
	
	public void setUser(String user) {
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

	public void setPassword(String password) {
		this.password = password;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
			
	Object callFunction(String method, Map<String, Object> parameters) {
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
			throw new RuntimeException(e);
		}
	}
	
	public boolean emailExists(String email) {
		return this.emailsExist(new ArrayList<String>(Arrays.asList(email))).get(email);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Boolean> emailsExist(List<String> emails) {
        ArrayList<String> hashes = new ArrayList<String>();
        for (String email : emails) {
        	hashes.add(hash(email));
        }
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("hashes", hashes);
        HashMap<String, Integer> result = (HashMap<String, Integer>) callFunction("grav.exists", map);
        HashMap<String, Boolean> retval = new HashMap<String, Boolean>();
        for (String email : emails) {
        	retval.put(email, result.get(hash(email)) > 0);
        }
        return retval;
	}

	public boolean hashExists(String hash) {
		return this.hashesExist(new ArrayList<String>(Arrays.asList(hash))).get(hash);
	}

	public HashMap<String, Boolean> hashesExist(List<String> hashes) {
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("hashes", hashes);
        @SuppressWarnings("unchecked")
		HashMap<String, Integer> result = (HashMap<String, Integer>) callFunction("grav.exists", map);
        HashMap<String, Boolean> retval = new HashMap<String, Boolean>();
        for (String hash : hashes) {
        	retval.put(hash, result.get(hash) > 0);
        }
        return retval;
	}
	
}