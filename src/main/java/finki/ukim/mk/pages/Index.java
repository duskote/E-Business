package finki.ukim.mk.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import finki.ukim.mk.entities.base.BaseEntity;
import finki.ukim.mk.services.GenericService;

/**
 * Start page of application twitter.
 */
public class Index {
	@Property
	private Status status;
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	// @InjectComponent
	// private Zone userDetailsZone, statusesZone;

	@Property
	private User user;

	@Property
	private String searchTerm;

	@Inject
	private Response response;

	@Inject
	private GenericService<BaseEntity, Long> genericService;

	@Inject
	private PageRenderLinkSource pageRenderLinkSource;

	public void onUpdateStatus() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		// Status status = twitter.updateStatus("Twitter4j implementation");
		// System.out.println("Successfully updated the status to ["
		// + status.getText() + "].");

		List<Status> statuses = twitter.getHomeTimeline();

		for (Status status : statuses) {
			System.out.println(status.getUser().getName() + ":"
					+ status.getText());
		}
	}

	public List<Status> getStatuses() throws TwitterException {
		return TwitterFactory.getSingleton().getHomeTimeline();
	}

	// @OnEvent(value = "viewUserDetails")
	// public void getUserDetails(Long id) throws TwitterException {
	// Twitter twitter = TwitterFactory.getSingleton();
	// // twitter.get
	// this.user = twitter.showUser(id);
	// ajaxResponseRenderer.addRender("userDetailsZone", userDetailsZone);
	// }

	// @OnEvent(value = EventConstants.SUCCESS, component = "searchForm")
	// void getResults() throws TwitterException {
	//
	// Twitter twitter = TwitterFactory.getSingleton();
	// Query query = new Query(searchTerm);
	// QueryResult queryResult = twitter.search(query);
	//
	// // TODO carry on here
	// queryResult.getTweets();
	//
	// ajaxResponseRenderer.addRender("statusesZone", statusesZone);
	//
	// }
}
