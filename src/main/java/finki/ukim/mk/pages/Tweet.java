package finki.ukim.mk.pages;

import java.util.Date;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.scribe.model.Token;

import finki.ukim.mk.services.TwitterService;

public class Tweet {

	@Inject
	private TwitterService twitterService;

	@SuppressWarnings("unused")
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String message;

	void onConnectionEstablishedFromTwitterConnect(Token accessToken) {

		twitterService.tweet(accessToken, "Hello from Tapestry at "
				+ new Date());

		message = "Tweeted";
	}

	void onConnectionFailed(String denied) {
		message = "Failed";
	}

}