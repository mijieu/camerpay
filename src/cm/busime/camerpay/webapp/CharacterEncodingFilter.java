package cm.busime.camerpay.webapp;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 * Filter that sets the character encoding to be used in parsing the incoming request, either unconditionally or only if the
 * client did not specify a character encoding.
 * 
 * @author tchayepa
 * @version 
 */
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

	/** The default character encoding to set for requests that pass through this filter. */
	protected String encoding = null;

	/**
	 * The filter configuration object we are associated with. If this value is null, this filter instance is not currently
	 * configured.
	 */
	protected FilterConfig filterConfig = null;

	/** Should a character encoding specified by the client be ignored? */
	protected boolean ignore = true;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
		// Conditionally select and set the character encoding to be used
		if (ignore || (request.getCharacterEncoding() == null)) {
			if (this.encoding != null) {
				request.setCharacterEncoding(this.encoding);
			}
		}

		// Pass control on to the next filter
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");

		String value = filterConfig.getInitParameter("ignore");
		if (value == null) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("true")) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("yes")) {
			this.ignore = true;
		} else {
			this.ignore = false;
		}
	}

}