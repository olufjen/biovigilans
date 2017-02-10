package no.naks.web.errorhandling;

import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import no.naks.web.framework.SessionLog;
import no.naks.web.framework.SessionLogElement;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Is called by myFaces instead of the default myFaces error handler, via
 * context-param param-name org.apache.myfaces.ERROR_HANDLER param-value
 * no.naks.web.errorhandling.ErrorHandler
 */

public class ErrorHandler {

	private static Log logger = LogFactory.getLog(ErrorHandler.class);
	private static Log severeLogger = LogFactory.getLog(ErrorHandler.class);

	private static final String ERROR_PREFIX = " >>>>>>>>>>>>>>>> E R R O R <<<<<<<<<<<<<<<<";
	private static final String DEFAULT_ERROR_PAGE = "/innhold/error.jsp"; // DEFAULT
	public static final String ERROR_PAGE_PARAM = "no.naks.web.errorhandling.ERROR_PAGE";

	public ErrorHandler() {
		
	}

	/**
	 * Hook method for facelets error handling. Any class that wants to be
	 * registered as error handler must implement this.
	 * 
	 * @param fc
	 * @param ex
	 */
	public void handleException(FacesContext fc, Exception ex) {
		handle(fc, ex);
	}

	/**
	 * Hook method for facelets error handling. Any class that wants to be
	 * registered as error handler must implement this.
	 * 
	 * @param fc
	 * @param ex
	 */
	public void handleExceptionList(FacesContext fc, List exceptionList) {
		if (fc != null && exceptionList != null) {
			for (Object e : exceptionList) {
				try {
					handle(fc, (Exception) e);
				} catch (ClassCastException cce) {
					logger.warn("Can't handle exception in list due to ClassCastException, "
									+ e.getClass().getName()
									+ " is not an instance of Exception. " + cce.getMessage());
				}
			}
		} else {
			logger.warn("Couldn't handle exceptionList due to missing (null) FacesContext or exception list.");
		}
	}

	/**
	 * Hook method for facelets error handling. Any class that wants to be
	 * registered as error handler must implement this.
	 * 
	 * @param fc
	 * @param ex
	 */
	public void handleThrowable(FacesContext fc, Throwable ex) {
		handle(fc, ex);
	}

	/**
	 * Handler method, logs the stacktrace recursively and redirects to error
	 * page. Also dumps the session interaction log.
	 * 
	 * @param fc
	 * @param ex
	 */
	private void handle(FacesContext fc, Throwable ex) {

		if (fc != null && ex != null) {
			StringBuffer message = new StringBuffer();
	
			message.append(ERROR_PREFIX);
			recursivelyBuildStackTrace(ex, message);
			buildSessionLog(fc, message);
			severeLogger.fatal(message.toString());
	
			try {
				fc.getExternalContext().redirect(getErrorPageRedirect(fc));
			} catch (IOException ioe) {
				logger.warn("Couldn't redirect to error page.");
			}
			
		} else {
			logger.warn("Couldn't handle due to missing FacesContext or Throwable.");
		}
	}

	private void buildSessionLog(FacesContext fc, StringBuffer sb) {
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		if (fc != null && sb != null && session != null) {
			SessionLog sessionLog = SessionLog.getSessionLog(session);
			if (sessionLog != null && sessionLog.getLog() != null) {
				for (int i = 0; i < sessionLog.getLog().size(); i++) {
					SessionLogElement sessionLogElement = sessionLog.getLog().pop();
					sb.append("\n**** REQUEST FOR SESSION " 
							+ sessionLogElement.getSessionId() 
							+ " AT TIMESTAMP " + sessionLogElement.getTime() + " ****\n");
					sb.append(sessionLogElement.getDescription()); 
				}
			} else {
				logger.warn("Couldn't write log due to missing session log in session.");
			}
		} else {
			logger.warn("Couldn't write log due to missing StringBuffer, FacesContext or session.");
		}
	}

	/**
	 * Follows the stacktrace recursively and appends it to the stringbuffer.
	 * 
	 * @param ex
	 * @param sb
	 */
	private void recursivelyBuildStackTrace(Throwable ex, StringBuffer sb) {

		if (ex != null && sb != null) {
			sb.append("\n\tThrowable class: \n\t\t");
			sb.append(ex.getClass());
			sb.append("\n\tMessage: \n\t\t");
			sb.append(ex.getMessage());
			sb.append("\n\tStacktrace: \n\t\t");
			formatStackTrace(ex.getStackTrace(), sb);
	
			if (ex.getCause() != null)
				recursivelyBuildStackTrace(ex.getCause(), sb);
		} else {
			logger.warn("Couldn't build stack trace due to missing Throwable or StringBuffe.");
		}
	}

	/**
	 * Formats an arary of stacktrace elements.
	 * 
	 * @param elements
	 * @param sb
	 */
	private void formatStackTrace(StackTraceElement[] elements, StringBuffer sb) {
		if (elements != null && sb != null) {
			for (StackTraceElement element : elements) {
				sb.append(element.toString());
				sb.append("\n\t\t");
			}
		} else {
			logger.warn("Couldn't format stack trace due to missing stack trace elements or StringBuffer.");
		}
	}

	/**
	 * Create a redirect string from context path and the relative redirect
	 * specified in ERROR_PAGE_PARAM no.naks.web.errorhandling.ERROR_PAGE
	 * context parameter.
	 * 
	 * @param fc
	 * @return the redirect String for the error page to display
	 */
	private String getErrorPageRedirect(FacesContext fc) {

		String errorPage = null;
		String prefix = null;

		if (fc != null) {
			errorPage = fc.getExternalContext().getInitParameter(ERROR_PAGE_PARAM);
	
			if (errorPage == null)
				errorPage = DEFAULT_ERROR_PAGE;
			
			prefix = fc.getExternalContext().getRequestContextPath();
			logger.info("Error redirect: " + prefix + errorPage);
		} else {
			logger.warn("Couldn't get error page redirect due to FacesContext was null.");
		}
		
		return prefix + errorPage;
	}
}
