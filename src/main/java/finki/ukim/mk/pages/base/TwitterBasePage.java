package finki.ukim.mk.pages.base;

import org.apache.tapestry5.ioc.annotations.Inject;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import finki.ukim.mk.entities.TwitterUser;
import finki.ukim.mk.entities.User;
import finki.ukim.mk.services.UserService;

public class TwitterBasePage {
	@Inject
	private UserService userService;

	private Twitter twitter = TwitterFactory.getSingleton();

	public User getUser() {
		return userService.getUser();
	}

	public TwitterUser getTwitterUser() {
		if (getUser() != null)
			return getUser().getTwitterUser();
		return null;
	}

	void setupRender() {
		if (getTwitterUser() != null)
			twitter.setOAuthAccessToken(new AccessToken(getUser().getToken(),
					getUser().getTokenSecret()));
	}
}
