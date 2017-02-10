package no.naks.web.framework;

import java.io.Serializable;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 * Holding the information to be logged on each request.
 */

public class SessionLogElement implements Serializable {

	private static final long serialVersionUID = -954703736203125778L;
	
	private String description = null;
	private HttpServletRequest request = null;
	private Date time = null;
	private String sessionId = null;
	
	public void setSessionId(String id) {
		this.sessionId = id;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
}
