package finki.ukim.mk.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import finki.ukim.mk.entities.base.BaseEntity;

@Entity
@Table(name = "twitter_users")
public class TwitterUser extends BaseEntity {
	private Long twitterId;
	private String token, tokenSecret;

	@Column(name = "twitter_id", nullable = false)
	public Long getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	@Column(name = "token", nullable = false)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "token_secret", nullable = false)
	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
}
