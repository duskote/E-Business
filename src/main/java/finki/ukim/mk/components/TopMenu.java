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
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import finki.ukim.mk.entities.TwitterUser;
import finki.ukim.mk.entities.User;
import finki.ukim.mk.pages.About;
import finki.ukim.mk.pages.Groups;
import finki.ukim.mk.pages.Index;
import finki.ukim.mk.pages.Register;
import finki.ukim.mk.services.UserService;

public class TopMenu {

	private static final String CSS_ACTIVE_CLASS = "active";
	@Property
	private String email, password;

	private Twitter twitter = new TwitterFactory().getInstance();

	@Inject
	private PageRenderLinkSource pageRenderLinkSource;

	@Inject
	private UserService userService;

	@Inject
	private AlertManager alertManager;

	@Inject
	private Session session;

	@Inject
	private Response response;

	@Inject
	private ComponentResources resources;

	public Link getAboutLink() {
		return pageRenderLinkSource.createPageRenderLink(About.class);
	}

	public Link getGroupsLink() {
		return pageRenderLinkSource.createPageRenderLink(Groups.class);
	}

	public Link getHomeLink() {
		return pageRenderLinkSource.createPageRenderLink(Index.class);
	}

	public Link getRegisterLink() {
		return pageRenderLinkSource.createPageRenderLink(Register.class);
	}

	/* css class methods */

	public String getHomeCssClass() {
		return resources.getPage().getClass().equals(Index.class) ? CSS_ACTIVE_CLASS
				: null;
	}

	public String getAboutCssClass() {
		return resources.getPage().getClass().equals(About.class) ? CSS_ACTIVE_CLASS
				: null;
	}

	public String getGroupsCssClass() {
		return resources.getPage().getClass().equals(Groups.class) ? CSS_ACTIVE_CLASS
				: null;
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
		System.out.println(accessToken);
		storeAccessToken(accessToken);
	}

	@CommitAfter
	private void storeAccessToken(AccessToken accessToken) {

		TwitterUser twitterUser = (TwitterUser) session
				.createCriteria(TwitterUser.class)
				.add(Restrictions.eq("twitterId", accessToken.getUserId()))
				.uniqueResult();

		if (twitterUser == null) {
			twitterUser = new TwitterUser();
			twitterUser.setTwitterId(accessToken.getUserId());
			twitterUser.setTwitterName(accessToken.getScreenName());
			session.saveOrUpdate(twitterUser);
		}

		User user = getUser();
		user.setToken(accessToken.getToken());
		user.setTokenSecret(accessToken.getTokenSecret());
		user.setTwitterUser(twitterUser);
		session.saveOrUpdate(user);

		//
	}
}
