package finki.ukim.mk.services;

public class OAuthConfiguration {

	private String apiKey;

	private String apiSecret;

	private String callbackURL;

	private String scope;

	public OAuthConfiguration(String apiKey, String apiSecret,
			String callbackURL, String scope) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.callbackURL = callbackURL;
		this.scope = scope;
	}

	public OAuthConfiguration(String apiKey, String apiSecret,
			String callbackURL) {
		this(apiKey, apiSecret, callbackURL, null);
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public String getCallbackURL() {
		return callbackURL;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}