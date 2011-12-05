package finki.ukim.mk.services.impl;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import finki.ukim.mk.services.BaseOAuthService;
import finki.ukim.mk.services.OAuthConfiguration;
import finki.ukim.mk.services.OAuthResource;

//Implementation
public class BaseOAuthServiceImpl implements BaseOAuthService {

	private OAuthService service;

	private String apiPrefix;

	private OAuthConfiguration configuration;

	public BaseOAuthServiceImpl(OAuthConfiguration configuration,
			Class<? extends Api> provider, String apiPrefix) {
		ServiceBuilder builder = new ServiceBuilder().provider(provider)
				.apiKey(configuration.getApiKey())
				.apiSecret(configuration.getApiSecret())
				.callback(configuration.getCallbackURL());

		if (configuration.getScope() != null) {
			builder.scope(configuration.getScope());
		}

		service = builder.build();

		this.apiPrefix = apiPrefix;
		this.configuration = configuration;
	}

	public String getAuthorizationURL() {
		return service.getAuthorizationUrl(newRequestToken());
	}

	public Token newRequestToken() {
		return service.getRequestToken();
	}

	public Token requestAccessToken(String oAuthToken, String verifier) {

		Token accessToken = service.getAccessToken(new Token(oAuthToken,
				configuration.getApiSecret()), new Verifier(verifier));

		return accessToken;
	}

	public JSONObject send(OAuthResource resource) {
		OAuthRequest request = new OAuthRequest(resource.getMethod(), apiPrefix
				+ resource.getURL());

		resource.initialize(request);
		service.signRequest(resource.getAccessToken(), request);

		Response response = request.send();
		checkResponse(response);

		resource.process(response);

		return new JSONObject(response.getBody());
	}

	private void checkResponse(Response response) {
		if (response.getCode() != HttpServletResponse.SC_OK) {
			throw new OAuthException("Failure sending request");
		}
	}

	public OAuthService getOAuthService() {
		return service;
	}

	public OAuthConfiguration getConfiguration() {
		return configuration;
	}
}