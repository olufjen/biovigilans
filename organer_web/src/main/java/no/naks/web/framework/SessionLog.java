package no.naks.web.framework;
 
import java.io.Serializable;
import java.util.ArrayDeque;

import javax.servlet.http.HttpSession;
 

/**
 * SessionLog holds a queue of strings that is continonusly updated on each request
 * so that a dump of a reasonable long interaction log can be performed when severe errors
 * are logged.
 *
 */

public class SessionLog  implements Serializable {

	private static final long serialVersionUID = -662713080018127823L;
	private static final int LOG_CAPACITY = 20;
	public static final String SESSION_LOG_KEY = "SessionLog.session.log";
	
	ArrayDeque<SessionLogElement> queue = new ArrayDeque<SessionLogElement>();
	
	/**
	 * Put an element on the log queue and remove the oldest if capacity is exceeded.
	 * 
	 * @param sessionLogElement
	 */
	public void log(SessionLogElement sessionLogElement) {
		if (queue.size() >= LOG_CAPACITY) {
			queue.pop();
		}
		queue.push(sessionLogElement);
	}
	
	/**
	 * Get the queue holding all log elements.
	 * @return
	 */
	public ArrayDeque<SessionLogElement> getLog()
	{
		return queue;
	}
	
	public static SessionLog getSessionLog(HttpSession session)
	{
		SessionLog sessionLog = (SessionLog) session.getAttribute(SESSION_LOG_KEY);
		
		if (sessionLog == null) {
			sessionLog = new SessionLog();
			session.setAttribute(SESSION_LOG_KEY, sessionLog);
		}
		return sessionLog;
	}
	
}
