package finki.ukim.mk.pages.base;

import org.apache.tapestry5.ioc.annotations.Inject;

import finki.ukim.mk.entities.TwitterUser;
import finki.ukim.mk.entities.User;
import finki.ukim.mk.services.UserService;

public class TwitterBasePage {
	@Inject
	private UserService userService;

	public User getUser() {
		return userService.getUser();
	}

	public TwitterUser getTwitterUser() {
		if (getUser() != null)
			return getUser().getTwitterUser();
		return null;
	}
}
