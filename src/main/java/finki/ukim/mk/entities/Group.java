package finki.ukim.mk.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import finki.ukim.mk.entities.base.BaseEntity;

@Entity
@Table(name = "groups")
public class Group extends BaseEntity {

	private String name, description;

	private User user;

	private List<TwitterUser> users;

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	@OneToMany
	public List<TwitterUser> getUsers() {
		return users;
	}

	public void setUsers(List<TwitterUser> users) {
		this.users = users;
	}
}
