package finki.ukim.mk.services.impl;

import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;

import finki.ukim.mk.services.OAuthConfiguration;
import finki.ukim.mk.services.TwitterService;

public class TwitterServiceImpl extends BaseOAuthServiceImpl implements
		TwitterService {

	public TwitterServiceImpl(OAuthConfiguration configuration, String apiPrefix) {
		super(configuration, TwitterApi.class, apiPrefix);
	}

	public void tweet(Token accessToken, String text) {
		send(new Tweet(accessToken, text));
	}

	public void verify(Token accessToken) {
		// send(new TwitterVerifyCredentials(accessToken));
	}

}
