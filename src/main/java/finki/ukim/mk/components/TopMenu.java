package finki.ukim.mk.components;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import finki.ukim.mk.pages.About;

public class TopMenu {
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;

	public Link getAboutLink() {
		return pageRenderLinkSource.createPageRenderLink(About.class);
	}
}
