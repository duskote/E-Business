package finki.ukim.mk.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Layout component for pages of application twitter.
 */
@Import(stylesheet = { "context:css/bootstrap.min.css",
		"context:css/layout.css" }, library = { "context:js/jquery-1.7.min.js",
		"context:js/bootstrap-dropdown.js", "context:js/bootstrap-twipsy.js" })
public class Layout {

	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "false")
	private boolean full;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String sidebarTitle;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private Block sidebar;

	@Inject
	private ComponentResources resources;
}
