package finki.ukim.mk.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import finki.ukim.mk.entities.base.BaseEntity;

@Entity
@Table(name = "group_twitter_users")
public class GroupTwitterUser extends BaseEntity {
	private TwitterUser user;
	private Group group;

	@JoinColumn(name = "twitter_user_id", nullable = false)
	@ManyToOne
	public TwitterUser getUser() {
		return user;
	}

	public void setUser(TwitterUser user) {
		this.user = user;
	}

	@JoinColumn(name = "group_id", nullable = false)
	@ManyToOne
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
