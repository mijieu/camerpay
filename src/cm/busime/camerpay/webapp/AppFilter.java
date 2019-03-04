package cm.busime.camerpay.webapp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cm.busime.camerpay.utils.Path;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 * Filter that sets the character encoding to be used in parsing the incoming request, either unconditionally or only if the
 * client did not specify a character encoding.
 * 
 * @author tchayepa
 * @version 
 */
@WebFilter("/views/content/user/*")
public class AppFilter implements Filter {
	
	private static Logger log = Logger.getLogger(AppFilter.class.getName());

	/** The default character encoding to set for requests that pass through this filter. */
	protected String encoding = null;

	/**
	 * The filter configuration object we are associated with. If this value is null, this filter instance is not currently
	 * configured.
	 */
	protected FilterConfig filterConfig = null;

	/** Should a character encoding specified by the client be ignored? */
	protected boolean ignore = true;
	
	/**AJAX partial response*/
	private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	        + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

	@Override
	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		boolean resourceRequested = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
        boolean ajaxRequest = "partial/ajax".equals(req.getHeader("Faces-Request"));
		// Conditionally select and set the character encoding to be used
		if (ignore || (request.getCharacterEncoding() == null)) {
			if (this.encoding != null) {
				request.setCharacterEncoding(this.encoding);
			}
		}
		
		try {
			HttpSession ses = req.getSession();
			String reqUrl = req.getRequestURI();
			//log.log(Level.INFO, "reqUrl is " + reqUrl);
			if ((ses != null && ses.getAttribute("username") != null) || reqUrl.indexOf("login.") >= 0 || resourceRequested) {
				if (!resourceRequested) {
					//log.log(Level.INFO, "ressource is requested ");
					//prevent browser to cache restricted ressource. So browser back button won't show up them anymore
					resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	                resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	                resp.setDateHeader("Expires", 0); // Proxies.
				}
				chain.doFilter(request, response);
			}
	        /*else if (ajaxRequest) {
	        	log.log(Level.INFO, "AJAX request ");
	            resp.setContentType("text/xml");
	            resp.setCharacterEncoding("UTF-8");
	            resp.getWriter().printf(AJAX_REDIRECT_XML, Page.Login.path()); // So, return special XML response instructing JSF ajax to send a redirect.
	        }*/
	        else if (reqUrl.indexOf("/views/content/user/") >= 0){
	        	//log.log(Level.INFO, "req. a protected url ");
	            resp.sendRedirect(Path.Login.path()); //
	        }
	        else {
	        	//log.log(Level.INFO, "No risk just continue the request");
	        	chain.doFilter(request, response); // So, just perform standard synchronous redirect.
	        }
		}catch(Exception e) {
			log.log(Level.INFO, e.getMessage());
			((HttpServletResponse) response).sendRedirect(Path.LoginError.pathRedirected());
		}
	}

	@Override
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