package no.naks.web.framework;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*
 * The POST-REDIRECT-GET pattern for enabling bookmarks and the back button.
 * JSF allows you to specify a redirect element in a navigation case,
 * but what if you want to automatically redirect every request?
 * This PostRedirectGetPhaseListener does just that.
 * Very simply, it intercepts all POST requests just before the render phase and
 * redirects the browser to the view via a GET request.
 * This way, you donøt have to put redirect elements in every navigation case
 * in faces-config.xml. OTOH, the phase listener does this every time,
 * which you may not want, either. Like using a redirect in a navigation case,
 * you incur the overhead of storing and restoring the view twice for each action;
 * however, the phase listener approach does ensure that the browser never saves a
 * POST request in its history.
 * A few notes about the code. As someone noted earlier in the dicussion,
 * you will lose any values in the Request scope between the redirect and the get.
 * This includes the Faces message queue. Iøve handled this below by saving and
 * restoring all Faces messages in the session between the redirect and the get,
 * so <h:message> will still work. If you wish, you can modify this code to save
 * and restore the entire request scope in session.
 * To use the PostRedirectGetListener, just declare as usual in faces-config:
 * Caveat emptor: I have tested this a little, but only for my narrow applications.
 * YMMV. As usual, this code comes with no warranty whatsoever.
 */

public class NoCachePhaseListener implements PhaseListener {

	private static final Log log = LogFactory
	.getLog(NoCachePhaseListener.class);

//	 a name under which to save messages between the redirect and the
//	 subsequent
//	 get
	private static final String sessionToken = "MULTI_PAGE_MESSAGES_SUPPORT";
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
		}
	*/	

	public PhaseId getPhaseId() {
	        return PhaseId.RENDER_RESPONSE;
	    }


	public void afterPhase(PhaseEvent event) {
//		 Save messages in session so theyøll be avaiable on the
//		 subsequent GET request
		/*
		if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES
		|| event.getPhaseId() == PhaseId.PROCESS_VALIDATIONS
		|| event.getPhaseId() == PhaseId.INVOKE_APPLICATION) {
		FacesContext facesContext = event.getFacesContext();
		saveMessages(facesContext);
		}
		*/
	}

	public void beforePhase(PhaseEvent phaseEvent) {
        FacesContext facesContext = phaseEvent.getFacesContext();
  
        
        //Implement POST-REDIRECT-GET pattern
  //      FacesContext facesContext = phaseEvent.getFacesContext();
        /*
        UIViewRoot viewRoot = facesContext.getViewRoot();
        HttpServletRequest req = (HttpServletRequest) facesContext
        .getExternalContext().getRequest();
        if ("POST".equals(req.getMethod())) {
        String nextViewID = facesContext.getViewRoot().getViewId();
        String nextViewURL = facesContext.getApplication().getViewHandler()
        .getActionURL(facesContext, nextViewID);
        log.debug("Redirecting to" + nextViewURL);
        try {
        phaseEvent.getFacesContext().getExternalContext().redirect(
        nextViewURL);
        } catch (IOException e) {
//         TODO Auto-generated catch block
        e.printStackTrace();
        }
        } else {
//         Move saved messages from session back to request queue
        restoreMessages(facesContext);
      
        * JSF normally clears input component values in the UpdateModel
        * phase. However, this phase does not run for a GET request, so we
        * must do it ourselves. Otherwise, the view will retain values from
        * the first time it was loaded.
     
        resetComponentValues(viewRoot.getChildren());
        */
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
        response.addHeader("Expires","Mon, 1 Jan 2006 05:00:00 GMT");//in the past
   //     }
    }
	
	/**
	* Remove the messages that are not associated with any particular component
	* from the userøs session and add them to the faces context.
	*
	* @return the number of removed messages.
	*/
	private int restoreMessages(FacesContext facesContext) {
//	 remove messages from the session
	int numRestoredMessages = 0;
	Map sessionMap = facesContext.getExternalContext().getSessionMap();
	Map allMessages = (Map) sessionMap.remove(sessionToken);
	if (allMessages == null) {
	return 0;
	}

//	 Move messages from session back to facesContext
	for (Iterator i = allMessages.entrySet().iterator(); i.hasNext();) {
	Entry e = (Entry) i.next();
	String clientId = (String) e.getKey();
	List clientMessages = (List) e.getValue();
	for (Iterator j = clientMessages.iterator(); j.hasNext();) {
	facesContext.addMessage(clientId, (FacesMessage) j.next());
	numRestoredMessages++;
	}
	}
	return numRestoredMessages;
	}

	/**
	* Remove the messages that are not associated with any particular component
	* from the faces context and store them to the userøs session.
	*
	* @return the number of removed messages.
	*/
	private int saveMessages(FacesContext facesContext) {
//	 Remove messages from the context
//	 Save as a map of lists so we can continue to messages with components
	Map sessionMap = facesContext.getExternalContext().getSessionMap();
	int numMessages = 0;

	if (!sessionMap.containsKey(sessionToken)) {
	sessionMap.put(sessionToken, new HashMap());
	}
	Map allMessages = (Map) sessionMap.get(sessionToken);

	for (Iterator i = facesContext.getClientIdsWithMessages(); i.hasNext();) {
	String clientId = (String) i.next();
//	 For each component (client ID), retrieve the messages to a list
	List messages = new ArrayList();
	for (Iterator j = facesContext.getMessages(clientId); j.hasNext();) {
	messages.add(j.next());
	j.remove();
	numMessages++;
	}

	List clientMessages = (List) allMessages.get(clientId);
	if (clientMessages != null)
//	 There are already messages for this component
	{
	clientMessages.addAll(messages);
	} else
//	 Not yet messages for this component
	{
	allMessages.put(clientId, messages);
	}
	}
	return numMessages;
	}

	/**
	* Resets UIInput component values From http://
	* forum.java.sun.com/thread.jspa?threadID=495087&messageID=3704164
	*/
	private void resetComponentValues(List childList) {
	for (int i = 0; i < childList.size(); i++) {
	UIComponent component = (UIComponent) childList.get(i);
	if (component instanceof UIInput) {
	UIInput input = (UIInput) component;
	input.setSubmittedValue(null);
	}
	resetComponentValues(component.getChildren());
	}
	}
}
