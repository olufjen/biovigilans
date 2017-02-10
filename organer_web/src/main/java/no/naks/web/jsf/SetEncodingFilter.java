package no.naks.web.jsf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetEncodingFilter implements Filter {
	private static final String defaultEncoding = "UTF-8";
	private String encoding;
    private FilterConfig filterConfig;

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig fc) throws ServletException {
        this.filterConfig = fc;
        this.encoding = filterConfig.getInitParameter("encoding");
        if (this.encoding == null || "".equals(this.encoding)) {
        	this.encoding = SetEncodingFilter.defaultEncoding;
        }
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    	// Respect the client-specified character encoding
    	// (see HTTP specification section 3.4.1)
    	if(null == req.getCharacterEncoding()) {
    		req.setCharacterEncoding(this.encoding);
    	}
        chain.doFilter(req, resp);
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    } 
}