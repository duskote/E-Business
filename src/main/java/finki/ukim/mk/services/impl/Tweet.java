package finki.ukim.mk.services.impl;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

public class Tweet extends AbstractOAuthResource {

	private String tweet;

	public Tweet(Token token, String tweet) {
		super(token, Verb.POST, "statuses/update.json");

		this.tweet = tweet;
	}

	@Override
	public void initialize(OAuthRequest request) {
		request.addBodyParameter("status", tweet);
		request.addBodyParameter("wrap_links", "true");
	}

	public void process(Response response) {
		// TODO Auto-generated method stub

	}
}