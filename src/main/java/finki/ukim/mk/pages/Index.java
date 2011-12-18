package finki.ukim.mk.pages;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.collections.FastTreeMap;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import finki.ukim.mk.entities.Group;
import finki.ukim.mk.entities.TwitterUser;
import finki.ukim.mk.pages.base.TwitterBasePage;

/**
 * Start page of application twitter.
 */
public class Index extends TwitterBasePage {
	@Property
	private Status status;

	@Property
	private User twitterUser;

	@Property
	private Group group;

	@Persist
	private Group selectedGroup;

	private Twitter twitter = TwitterFactory.getSingleton();

	private SortedMap<Date, Status> dateToStatusesMap;

	@InjectComponent
	private Zone userDetailsZone, statusesZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private Session session;

	public Collection<Status> getStatuses() throws TwitterException {
		if (selectedGroup == null)
			return twitter.getHomeTimeline();

		dateToStatusesMap = new FastTreeMap(Collections.reverseOrder());

		for (TwitterUser tu : selectedGroup.getUsers()) {
			System.out.println(tu);
			for (Status s : twitter.getUserTimeline(tu.getTwitterId()))
				dateToStatusesMap.put(s.getCreatedAt(), s);
		}
		return dateToStatusesMap.values();

	}

	@OnEvent(value = "viewUserDetails")
	public void getUserDetails(Long id) throws TwitterException {
		this.twitterUser = twitter.showUser(id);
		ajaxResponseRenderer.addRender("userDetailsZone", userDetailsZone);
	}

	@SuppressWarnings("unchecked")
	public List<Group> getGroups() {
		return session.createCriteria(Group.class)
				.add(Restrictions.eq("user", getUser())).list();
	}

	@OnEvent(value = "selectGroup")
	boolean handleSelectGroup(Group group) {
		this.selectedGroup = group;
		ajaxResponseRenderer.addRender(statusesZone);
		return true; // return true to stop propagating
	}

	@OnEvent(value = "selectGroup")
	void handleSelectGroup() {
		System.err.println("called again");
		this.selectedGroup = null;
		ajaxResponseRenderer.addRender(statusesZone);
	}

	public String getActiveCssClass() {
		return group.equals(selectedGroup) ? "active" : null;
	}

	public String getAllCssClass() {
		return selectedGroup == null ? "active" : null;
	}
}
