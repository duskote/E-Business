package finki.ukim.mk.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import finki.ukim.mk.entities.base.BaseEntity;

@Entity
@Table(name = "twitter_users")
public class TwitterUser extends BaseEntity {
	private Long twitterId;

	private String twitterName;

	@Column(name = "twitter_id", nullable = false)
	public Long getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	@Column(name = "twitter_name")
	public String getTwitterName() {
		return twitterName;
	}

	public void setTwitterName(String twitterName) {
		this.twitterName = twitterName;
	}

}
