package no.naks.web.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Session filter that checks for session availability. If a session is not present, 
 * redirects silently to the velkommen-page, where from the user will be able to
 * navigate further with a new session.
 * </p>
 * <p>
 * Note: This functionality will be superseded when proper login and user handling
 * is implemented. The general idea is just to avioid showing stacktrace when a
 * session has timed out
 * </p>

 *
 */

public class SessionTimeoutFilter implements Filter {
	
	private static Log logger = LogFactory.getLog(SessionTimeoutFilter.class);

	private final static String CONFIG_REDIRECT_PAGE = "expiry-page";
	private final static String DEFAULT_REDIRECT_PAGE = "velkommen.faces";
	private final static String CONFIG_AVOID_URLS = "avoid-urls";
	private final static String DEFAULT_AVOID_URLS = "velkommen.faces,sessionexpired.faces";

	private String redirectPage = DEFAULT_REDIRECT_PAGE;
	private String avoidUrls = DEFAULT_AVOID_URLS;

	private ArrayList<String> avoidUrlList;
	
	@Override
	public void destroy() 
	{
	}

	/**
	 * <p>Check for session availability and redirect when session is missing.</p> 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getServletPath();
		boolean allowedRequest = false;
		
		for(String avoidUrl : avoidUrlList) {
			if(url.contains(avoidUrl)) {
				allowedRequest = true;
				break;
			}
		}

		if (!allowedRequest) {
			HttpSession session = req.getSession(false);
			if (null == session) {
				res.sendRedirect(redirectPage);
			}
		}

		chain.doFilter(req, res);
	}

	/**
	 * <p>Will retrieve a welcome-page from filterconfig, or fall back to
	 * the hardcoded page defined in this class.</p>
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException 
	{
		if (filterConfig.getInitParameter(CONFIG_REDIRECT_PAGE) != null) {
			redirectPage = filterConfig.getInitParameter(CONFIG_REDIRECT_PAGE);
			logger.info(CONFIG_REDIRECT_PAGE + " param found: " + redirectPage);	
		}
		else {
			logger.info(CONFIG_REDIRECT_PAGE + " param not found, using default " + redirectPage);
		}
		
		if (filterConfig.getInitParameter(CONFIG_AVOID_URLS) != null) {
			avoidUrls = filterConfig.getInitParameter(CONFIG_AVOID_URLS);
			logger.info(CONFIG_AVOID_URLS + " param found: " + avoidUrls);	
		}
		else {
			logger.info(CONFIG_AVOID_URLS + " param not found, using default " + avoidUrls);
		}		
		
		logger.info("Session expiry redirection to " + redirectPage + " initiated.");
		
		StringTokenizer token = new StringTokenizer(avoidUrls, ",");
		avoidUrlList = new ArrayList<String>();

		while (token.hasMoreTokens()) {
			avoidUrlList.add(token.nextToken());
		}
	}
}
