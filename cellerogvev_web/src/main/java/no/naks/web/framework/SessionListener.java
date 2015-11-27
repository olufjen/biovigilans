package no.naks.web.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	
	private static Log logger = LogFactory.getLog(SessionListener.class);

	private int sessionCount = 0;


	public SessionListener() 
	{
		logger.info("Session listener constructed.");
		logger.info("Total sessions: " + sessionCount);
	}
	
	public void sessionCreated(HttpSessionEvent event) {

		synchronized (this) {
			sessionCount++;
		}
		
		logger.info("Session created: " + event.getSession().getId());
		logger.info("Total sessions: " + sessionCount);
	}

	public void sessionDestroyed(HttpSessionEvent event) {

		synchronized (this) {
			sessionCount--;
		}
		
		logger.info("Session destroyed: " + event.getSession().getId());
		logger.info("Total sessions: " + sessionCount);

		/*
		HttpServletRequest request=(HttpServletRequest)event.getSession().getAttribute("request");
		HttpServletResponse response=(HttpServletResponse)event.getSession().getAttribute("response");

		RequestDispatcher rd = event.getSession().getServletContext()
				.getRequestDispatcher("/sessionexpired.jsp");

		System.out.println("Dispatching to error page due to dead session.");
		
		try {
			rd.forward(request, response);
			System.out.println("Dispatched to error page due to dead session successfully.");
		} catch (ServletException e) {
			System.out.println("Redirect failed: " +  e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			System.out.println("Redirect failed: " +  e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		*/
	}

}