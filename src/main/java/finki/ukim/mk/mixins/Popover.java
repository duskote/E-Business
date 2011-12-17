package finki.ukim.mk.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * Popover extends twipsy, so twipsy has to be imported first.
 * 
 * @author dusko
 * 
 */
@Import(library = { Popover.TWIPSY_JS, Popover.POPOVER_JS })
@MixinAfter
public class Popover {
	public static final String TWIPSY_JS = "context:js/bootstrap-twipsy.js";
	public static final String POPOVER_JS = "context:js/bootstrap-popover.js";

	/**
	 * allowed values: above | below | left | right
	 */
	@Parameter(value = "right", defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String placement;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String content;

	@Inject
	private JavaScriptSupport jss;

	void beginRender(MarkupWriter writer) {
		writer.getElement().attributes("rel", "popover", "data-placement",
				placement, "data-content", content, "data-original-title",
				title);
	}

	void setupRender() {
		jss.addScript("jQuery('[rel=popover]').popover({ offset : 10, html : true});");
	}
}
