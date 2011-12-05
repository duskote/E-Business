package finki.ukim.mk.services;

import org.scribe.model.Token;

public interface TwitterService extends BaseOAuthService {

	void tweet(Token accessToken, String text);

	void verify(Token accessToken);

}
