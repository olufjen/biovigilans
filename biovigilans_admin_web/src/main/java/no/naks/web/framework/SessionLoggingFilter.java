package no.naks.web.framework;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This filter inspects the request and tries to extract some useful
 * information about the user interaction in order to log it in a
 * queue in the session, which will be dumped to logfile upon a system 
 * crash. 
 *
 */
public class SessionLoggingFilter implements Filter {

    private static Log logger = LogFactory.getLog(SessionLoggingFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        long timeStart = new Date().getTime();
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession();

            SessionLog sessionLog = SessionLog.getSessionLog(session);

            SessionLogElement sessionLogElement = new SessionLogElement();
            sessionLogElement.setSessionId(session.getId());
            sessionLogElement.setTime(new Date());
            sessionLogElement.setRequest(httpRequest); // only on debug?
            sessionLogElement.setDescription(formatRequest(httpRequest));
            sessionLog.log(sessionLogElement);
        } catch (Exception e) {
            logger.warn("Error during session logging: " + e.getMessage(), e);
        }
        long timeEnd = new Date().getTime();
        System.err.println("SESSION LOGGING TIME SPENT " + (timeEnd - timeStart) + " ms");
        long restTimeStart = new Date().getTime();
        filterChain.doFilter(request, response);
        long restTimeEnd = new Date().getTime();
        System.err.println("REST OF FILTERCHAIN SPENT " + (restTimeEnd - restTimeStart) + " ms");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private String formatRequest(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();

        sb.append("REQUEST identified by session id " + request.getRequestedSessionId() + ".  \n");
        sb.append("REQUEST HEADERS:\n");

        Enumeration headerEnum = request.getHeaderNames();

        // print enumeration of request attributes (except viewstate)
        while (headerEnum != null && headerEnum.hasMoreElements()) {

            String headerName = (String) headerEnum.nextElement();
            Object header = null;

            if (headerName != null) {

                header = request.getHeader(headerName);
                sb.append("Header name: '" + headerName + "' Value: '");

                if (header != null) {
                    sb.append(header + "'\n");
                } else {
                    sb.append("null'\n");
                }

            } else {
                logger.warn("Header with null name value found in request.");
            }
        } // headers

        sb.append("REQUEST ATTRIBUTES:\n");
        Enumeration attrEnum = request.getAttributeNames();

        // print enumeration of request attributes (except viewstate)
        while (attrEnum != null && attrEnum.hasMoreElements()) {

            String attribName = (String) attrEnum.nextElement();
            Object attrib = null;

            if (attribName != null && !ViewStateHandler.VIEW_STATE_KEY.equals(attribName)) {

                attrib = request.getAttribute(attribName);
                sb.append("Attribute name: '" + attribName + "' Value(String): '");

                if (attrib != null) {
                    sb.append(attrib.toString() + "'\n");
                } else {
                    sb.append("null'\n");
                }

            } else if (!"ViewState".equals(attribName)) {
                logger.warn("Attribute with null attrib name found in request.");
            } else {
                // ViewState: ignore.
            }
        } // attribute values

        sb.append("REQUEST PARAMETERS (omitting ViewState):\n");

        Enumeration parameterEnum = request.getParameterNames();

        // print enumeration of request attributes (except viewstate)
        while (parameterEnum != null && parameterEnum.hasMoreElements()) {

            String parameterName = (String) parameterEnum.nextElement();
            String parameter = null;

            if (parameterName != null && !ViewStateHandler.VIEW_STATE_KEY.equals(parameterName)) {

                parameter = request.getParameter(parameterName);
                sb.append("Parameter name: '" + parameterName + "' Value: '");

                if (parameter != null) {
                    sb.append(parameter + "'\n");
                } else {
                    sb.append("null'\n");
                }
            } else if (!"javax.faces.ViewState".equals(parameterName)) {
                logger.warn("Parameter with null name found in request.");
            } else {
                // ViewState: ignore
            }
        } // headers

        sb.append("OTHER REQUEST DATA:\n");

        sb.append("AuthType: " + request.getAuthType() + "\n");
        sb.append("CharacterEncoding: " + request.getCharacterEncoding() + "\n");
        sb.append("ContentLength: " + request.getContentLength() + "\n");
        sb.append("ContentType: " + request.getContentType() + "\n");
        sb.append("ContextPath: " + request.getContextPath() + "\n");
        sb.append("QueryString: " + request.getQueryString() + "\n");
        sb.append("RequestURI: " + request.getRequestURI() + "\n");
        sb.append("RequestURL: " + request.getRequestURL() + "\n");

        return sb.toString();
    }
}
