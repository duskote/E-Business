package finki.ukim.mk.components;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.scribe.model.Token;

import finki.ukim.mk.model.OAuthConstants;
import finki.ukim.mk.services.TwitterService;

@Events({ OAuthConstants.CONNECTION_ESTABLISHED,
		OAuthConstants.CONNECTION_FAILED })
public class TwitterConnect implements ClientElement {
	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	private String assignedClientId;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private ComponentResources resources;

	@Inject
	private TwitterService twitterService;

	void setupRender() {
		assignedClientId = javaScriptSupport.allocateClientId(clientId);
	}

	public String getClientId() {
		return assignedClientId;
	}

	URL onConnectToTwitter() throws MalformedURLException {
		return new URL(twitterService.getAuthorizationURL());
	}

	Object onAuthorize(
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier,
			@RequestParameter(value = "oauth_token", allowBlank = true) String oAuthToken,
			@RequestParameter(value = "denied", allowBlank = true) String denied) {

		if (verifier != null) {
			return accessGranted(oAuthToken, verifier);
		} else {
			return accessDenied(denied);
		}
	}

	private Object accessGranted(String oAuthToken, String verifier) {
		Token accessToken = twitterService.requestAccessToken(oAuthToken,
				verifier);

		CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();

		boolean handled = resources.triggerEvent(
				OAuthConstants.CONNECTION_ESTABLISHED,
				new Object[] { accessToken }, callback);

		if (handled) {
			return callback.getResult();
		}

		return null;
	}

	private Object accessDenied(String denied) {
		CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();

		boolean handled = resources.triggerEvent(
				OAuthConstants.CONNECTION_FAILED, new Object[] { denied },
				callback);

		if (handled) {
			return callback.getResult();
		}

		return null;
	}

}
