package no.naks.biovigilans_admin.web.server.resource;

import java.util.HashMap;
import java.util.Map;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * LogoutServerResourceHTML
 * Denne klassen håndterer hendelsen Bruker logger seg ut.
 * @author olj
 *
 */
public class LogoutServerResourceHTML extends SaksbehandlingSessionServer {

	
	
	public LogoutServerResourceHTML() {
		super();
		
	}

	@Get
	public Representation getHemovigilans() {
		

	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	     Request request = getRequest();
	     Map<String, Object> dataModel = new HashMap<String, Object>();


	 	
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/hemovigilansadmin.html"));
	        Representation loginFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(loginFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;

	}
    @Post
    public Representation storeHemovigilans(Form form) {
    	Request request = getRequest();
    	invalidateSessionobjects();
    	Map<String, Object> dataModel = new HashMap<String, Object>();
    	String page = "/hemovigilans/login.html";
	    ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/login.html"));
	 	        Representation loginFtl = clres2.get();
	 	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(loginFtl,dataModel,
	 	                MediaType.TEXT_HTML);
 	    	  redirectPermanent(page);

 	     	return templatemapRep;   	

    }
}
