package finki.ukim.mk.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import finki.ukim.mk.entities.base.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	private String email, passwordHash;

	private TwitterUser twitterUser;

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password_hash", nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@OneToOne
	public TwitterUser getTwitterUser() {
		return twitterUser;
	}

	public void setTwitterUser(TwitterUser twitterUser) {
		this.twitterUser = twitterUser;
	}

}