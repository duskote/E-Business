package finki.ukim.mk.services;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

public interface OAuthResource {

	Verb getMethod();

	void initialize(OAuthRequest request);

	String getURL();

	Token getAccessToken();

	void process(Response response);

}