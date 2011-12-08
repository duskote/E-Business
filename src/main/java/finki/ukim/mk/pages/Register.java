package finki.ukim.mk.pages;

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
import org.hibernate.Session;

import finki.ukim.mk.entities.User;
import finki.ukim.mk.services.UserService;

public class Register {

	@Property
	private String email, password;

	@Inject
	private PageRenderLinkSource pageRenderLinkSource;

	@Inject
	private Session session;

	@Inject
	private UserService userService;

	@Inject
	private AlertManager alertManager;

	@CommitAfter
	@OnEvent(value = EventConstants.SUCCESS, component = "createUserForm")
	Link handleCreateUser() {
		User user = new User();
		user.setEmail(email);
		user.setPasswordHash(password);

		session.save(user);

		userService.authenticate(email, password);
		alertManager
				.alert(Duration.UNTIL_DISMISSED,
						Severity.INFO,
						"Success!\nNow authenticate your twitter profile by clicking the authorize button.");

		return pageRenderLinkSource.createPageRenderLink(Index.class);
	}
}
