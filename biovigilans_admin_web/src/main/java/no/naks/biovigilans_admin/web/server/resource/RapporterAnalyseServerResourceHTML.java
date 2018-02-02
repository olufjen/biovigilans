package no.naks.biovigilans_admin.web.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Saksbehandler;
/**
 * RapporterAnalyseServerResourceHTML
 * Denne klassen er ikke i bruk OLJ 07.04.17
 * @since 03.01.18
 * Revidert for vedlikehold av meldere og annen administrasjon OLJ 03.01.18
 * Denne klassen er Resource for vedlikeholdssidene
 * @author oluf

 * 
 */
public class RapporterAnalyseServerResourceHTML extends SaksbehandlingSessionServer  {
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	     Request request = getRequest();


	    

/*
 * En Hashmap benyttes dersom en html side henter data fra flere javaklasser.	
 * Hver javaklasse får en id (ex pasientkomplikasjonId) som er tilgjengelig for html
 *      
*/	     
	     Map<String, Object> dataModel = new HashMap<String, Object>();

	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/analyse_admin_main.html"));
	     
	        // Load the FreeMarker template
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/pasientkomplikasjon/nymeldingfagprosedyre.html").get();
	        Representation pasientkomplikasjonFtl = clres2.get();
	//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	        
//	        TemplateRepresentation  templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
//	                MediaType.TEXT_HTML);
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	 }
	   @Post
	    public Representation storeHemovigilans(Form form) {
	    	
	    	Request request = getRequest();
	    	if(form == null){
	    		invalidateSessionobjects();
	    	}
	    	List<Melder>  meldere = null;
	    	List<Saksbehandler> saksbehandlere = null;
	    	Map<String, Object> dataModel = new HashMap<String, Object>();
	    	String page = "/hemovigilans/analyse_admin_main.html";
	    	String functionValg = "";
	    	if (form != null){
	    		Parameter tilbake = form.getFirst("tilbake"); // Bruker returnerer til hovedside
	      		if (tilbake != null){
	     	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/analyse_admin_main.html"));

	        		// Load the FreeMarker template
	        		Representation pasientkomplikasjonFtl = clres2.get();

	        		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	        				MediaType.TEXT_HTML);
	        		String backPage = "../hemovigilans/hemovigilansadmin.html";
	        		  redirectPermanent(backPage);
	        		return templatemapRep;
	      		}
    	 		for (Parameter entry : form) {
        			if (entry.getValue() != null && !(entry.getValue().equals(""))){
        					System.out.println(entry.getName() + "=" + entry.getValue());
        					functionValg = entry.getValue();	
        			}

        		}
    	 		if (functionValg.equals("meld")){
    	 		    meldere = saksbehandlingWebservice.collectMeldere();
    	 		    adminWebService.encyptmeldere(meldere);
    	 		   
    	 		}
    	 		if (functionValg.equals("resettmeld")){
    	 		    meldere = saksbehandlingWebservice.collectMeldere();
    	 		    adminWebService.decyptmeldere(meldere);
    	 		   
    	 		}
       	 		if (functionValg.equals("saksbehandler")){
    	 		   saksbehandlere = saksbehandlingWebservice.collectSaksbehandlere();
    	 		   adminWebService.encyptsaksbehandler(saksbehandlere);
    	 		}
      	 		if (functionValg.equals("resettsaksbehandler")){
     	 		   saksbehandlere = saksbehandlingWebservice.collectSaksbehandlere();
     	 		   adminWebService.decyptsaksbehandlere(saksbehandlere);
     	 		}
	    	}
		     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/analyse_admin_main.html"));
		     
		        // Load the FreeMarker template
//		        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
//		        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/pasientkomplikasjon/nymeldingfagprosedyre.html").get();
		        Representation pasientkomplikasjonFtl = clres2.get();
		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
		        
//		        TemplateRepresentation  templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
//		                MediaType.TEXT_HTML);
		        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
		                MediaType.TEXT_HTML);
			 return templatemapRep;
	
	   }
}
