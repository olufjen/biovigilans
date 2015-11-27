package no.naks.biovigilans.web.server.resource;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.felles.server.resource.SessionServerResource;


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

/**
 * @author olj
 *  Denne resursen er knyttet til siden rapporter_hendelse_main
 *  Med denne siden velger melder hva slags type melding dette er.
 */
public class RapporterMainServerResourceHtml extends SessionServerResource {

	public RapporterMainServerResourceHtml() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * getInnmelding
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en rapportert hendelse
	 * @return
	 */
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
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_hendelse_main.html"));
	     
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
    /**
     * storeHemovigilans
     * Denne rutinen tar imot alle ny informasjon fra bruker om den rapporterte hendelsen
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	TemplateRepresentation  templateRep = null;
    	
 
    	if (form != null){
    		Parameter logout = form.getFirst("avbryt4");
    		Parameter lukk = form.getFirst("lukk4");
    	     Map<String, Object> dataModel = new HashMap<String, Object>();

    		if (logout != null || lukk != null){
 
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemivigilans/Logout.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
	    				MediaType.TEXT_HTML);
    			return templateRep; // return a new page!!!
    		}
    			
 
    		for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals("")))
    					System.out.println(entry.getName() + "=" + entry.getValue());
    	
    		}

    	
    		Parameter lagre = form.getFirst("lagre4");
 
//    		System.out.println("Status = "+result.getStatus());
    		// Denne client resource forholder seg til src/main/resource katalogen !!!	
    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/hemovigilans.html"));
    		Representation pasientkomplikasjonFtl = clres2.get();
    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
    				MediaType.TEXT_HTML);
    	}
    	return templateRep;
      
    }

}
