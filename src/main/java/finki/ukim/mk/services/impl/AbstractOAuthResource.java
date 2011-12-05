package finki.ukim.mk.services.impl;

import org.apache.tapestry5.services.Response;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;

import finki.ukim.mk.services.OAuthResource;

public abstract class AbstractOAuthResource implements OAuthResource {

	private Verb method;

	private String resource;

	private Token accessToken;

	public AbstractOAuthResource(Token accessToken, Verb method, String resource) {
		this.accessToken = accessToken;
		this.method = method;
		this.resource = resource;
	}

	public Verb getMethod() {
		return method;
	}

	public String getURL() {
		return resource;
	}

	public Token getAccessToken() {
		return accessToken;
	}

	public void initialize(OAuthRequest request) {

	}

	public void process(Response response) {
	}

}
