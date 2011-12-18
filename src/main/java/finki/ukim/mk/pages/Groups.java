package finki.ukim.mk.pages;

import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import finki.ukim.mk.entities.Group;
import finki.ukim.mk.entities.GroupTwitterUser;
import finki.ukim.mk.entities.TwitterUser;
import finki.ukim.mk.pages.base.TwitterBasePage;

public class Groups extends TwitterBasePage {

	@Inject
	private Session session;

	@Property
	private Group group, newGroup, selectedGroup;

	@Property
	private User twitterUser;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone groupZone;

	public List<Group> getGroups() {
		@SuppressWarnings("unchecked")
		List<Group> list = session.createCriteria(Group.class)
				.add(Restrictions.eq("user", getUser())).list();

		return list;
	}

	@CommitAfter
	@OnEvent(value = EventConstants.SUCCESS, component = "groupForm")
	void handleSuccess() {
		newGroup.setUser(getUser());
		session.saveOrUpdate(newGroup);
	}

	@Cached
	public List<User> getTwitterUsers() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		return twitter.lookupUsers(twitter.getFriendsIDs(-1l).getIDs());
	}

	@CommitAfter
	@OnEvent(value = "addToGroup")
	void handleAddToGroup(Group group, Long id, String twitterName,
			boolean selected) {
		// first, create the user if it doesn't exists already
		this.selectedGroup = group;

		TwitterUser twitterUser = (TwitterUser) session
				.createCriteria(TwitterUser.class)
				.add(Restrictions.eq("twitterId", id)).uniqueResult();

		if (twitterUser == null) {
			twitterUser = new TwitterUser();
			twitterUser.setTwitterId(id);
			twitterUser.setTwitterName(twitterName);
			session.saveOrUpdate(twitterUser);
		}

		if (selected) {

			GroupTwitterUser gtu = (GroupTwitterUser) session
					.createCriteria(GroupTwitterUser.class)
					.add(Restrictions.eq("group", group))
					.add(Restrictions.eq("user", twitterUser)).uniqueResult();
			session.delete(gtu);
		} else {
			GroupTwitterUser gtu = new GroupTwitterUser();
			gtu.setGroup(group);
			gtu.setUser(twitterUser);
			session.save(gtu);
		}

		ajaxResponseRenderer.addRender(groupZone);
	}

	public boolean isInGroup() {
		for (TwitterUser user : selectedGroup.getUsers())
			if (user.getTwitterId() == twitterUser.getId())
				return true;
		return false;
	}

	public String getSelectedClass() {
		if (isInGroup())
			return "selected";
		return "";
	}

	public String getGroupCssClass() {
		return group.equals(selectedGroup) ? "active" : null;
	}

	@OnEvent(value = "selectGroup")
	void handleSelectGroup(Group group) {
		this.selectedGroup = group;
		ajaxResponseRenderer.addRender(groupZone);
	}

	@CommitAfter
	@OnEvent(value = "deleteGroup")
	void handleDeleteGroup(Group group) {
		for (GroupTwitterUser gtu : group.getTwitterUsers()) {
			session.delete(gtu);
		}
		session.delete(group);
	}
}
