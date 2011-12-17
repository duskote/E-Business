package finki.ukim.mk.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.tapestry5.beaneditor.DataType;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import finki.ukim.mk.entities.base.BaseEntity;

@Entity
@Table(name = "groups")
public class Group extends BaseEntity {

	private String name, description;

	private User user;

	private List<GroupTwitterUser> twitterUsers;

	@Validate("required")
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DataType("longtext")
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Transient
	public List<TwitterUser> getUsers() {
		List<TwitterUser> ret = CollectionFactory.newList();
		for (GroupTwitterUser gtu : getTwitterUsers()) {
			ret.add(gtu.getUser());
		}
		return ret;
	}

	@OneToMany(mappedBy = "group")
	public List<GroupTwitterUser> getTwitterUsers() {
		return twitterUsers;
	}

	public void setTwitterUsers(List<GroupTwitterUser> twitterUsers) {
		this.twitterUsers = twitterUsers;
	}
}
