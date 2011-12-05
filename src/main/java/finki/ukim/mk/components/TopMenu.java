package finki.ukim.mk.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;
import org.hibernate.Session;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import finki.ukim.mk.entities.TwitterUser;
import finki.ukim.mk.entities.User;
import finki.ukim.mk.pages.About;
import finki.ukim.mk.services.GenericService;
import finki.ukim.mk.services.UserService;

public class TopMenu {
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;

	@Property
	private String email, password;

	@Inject
	private UserService userService;

	@Inject
	private AlertManager alertManager;

	@Inject
	private GenericService<TwitterUser, Long> genericService;

	@Inject
	private Response response;

	@Inject
	private PeriodicExecutor periodicExecutor;

	public Link getAboutLink() {
		return pageRenderLinkSource.createPageRenderLink(About.class);
	}

	public User getUser() {
		return userService.getUser();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "loginForm")
	void handleLogin() {
		if (userService.authenticate(email, password))
			alertManager.alert(Duration.TRANSIENT, Severity.INFO,
					"Successfully logged in!");
	}

	void onLogout() {
		userService.logout();
	}

	private Twitter twitter = new TwitterFactory().getInstance();

	@Inject
	private ComponentResources resources;

	public void onAuthorize() throws TwitterException, Exception {
		RequestToken requestToken = twitter.getOAuthRequestToken();
		final String url = requestToken.getAuthorizationURL();
		response.sendRedirect(url);
		AccessToken accessToken = null;
		while (null == accessToken) {
			try {
				accessToken = twitter.getOAuthAccessToken();
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
					System.out.println("Unable to get the access token.");
				} else {
					te.printStackTrace();
				}
			}
		}
		resources.triggerEvent("accessTokenAvailable",
				new Object[] { accessToken }, null);

	}

	void onAccessTokenAvailable(AccessToken accessToken) {
		storeAccessToken(accessToken);
	}

	@Inject
	private Session session;

	@CommitAfter
	private void storeAccessToken(AccessToken accessToken) {
		TwitterUser twitterUser = new TwitterUser();
		twitterUser.setId(getUser().getId());
		twitterUser.setTwitterId(accessToken.getUserId());
		twitterUser.setToken(accessToken.getToken());
		twitterUser.setTokenSecret(accessToken.getTokenSecret());
		session.saveOrUpdate(twitterUser);

		User user = getUser();
		user.setTwitterUser(twitterUser);
		session.saveOrUpdate(user);
	}
}
