package finki.ukim.mk.services;

import org.apache.tapestry5.json.JSONObject;
import org.scribe.model.Token;


//Interface
public interface BaseOAuthService {
	String getAuthorizationURL();

	Token requestAccessToken(String temporaryToken, String verifier);

	JSONObject send(OAuthResource resource);

	Token newRequestToken();

	OAuthConfiguration getConfiguration();
}
