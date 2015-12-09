package no.naks.biovigilans.web.server.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import no.naks.biovigilans.felles.control.SessionAdmin;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.felles.control.TableWebService;
import no.naks.biovigilans.felles.model.PasientKomplikasjonWebModel;



import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.data.Parameter;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import edu.unc.ils.mrc.hive2.api.HiveConcept;

/**
 * Resurser blir instansiert for hver kall fra klient
 * 
 * @author olj
 * Denne resursen er knyttet til hovedsiden for celler og vev
 * 
 */
public class HemovigilansServerResourceHtml extends SessionServerResource {
		

		
	 public HemovigilansServerResourceHtml() {
			super();
			// TODO Auto-generated constructor stub
		}
	

	public SessionAdmin getSessionAdmin() {
		return sessionAdmin;
	}

	public void setSessionAdmin(SessionAdmin sessionAdmin) {
		this.sessionAdmin = sessionAdmin;
	//	this.sessionParams = this.sessionAdmin.getSessionParams();
	}

	public TableWebService getTablewebservice() {
		return tablewebservice;
	}

	public void setTablewebservice(TableWebService tablewebservice) {
		this.tablewebservice = tablewebservice;
	}

	public String[] getSessionParams() {
		return sessionParams;
	}

	public void setSessionParams(String[] sessionParams) {
		this.sessionParams = sessionParams;
	}

	/**
	 * getpasientkomplikasjon
	 * Denne rutinen henter inn pasientkomplikasjonsresurser til å håndtere forespørsel om ny prosedyre og setter opp nettsiden for å ta i mot
	 * opplysninger om ny prosedyre.
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	     Request request = getRequest();
	     
	     invalidateSessionobjects();

/*
 * En Hashmap benyttes dersom en html side henter data fra flere javaklasser.	
 *      

	     
 */	    
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
//	     sessionAdmin.setSessionObject(getRequest(), result,"pasientkomplikasjon");
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/cellerogvev.html"));
	     
	        // Load the FreeMarker template
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/pasientkomplikasjon/nymeldingfagprosedyre.html").get();
	        Representation pasientkomplikasjonFtl = clres2.get();
	//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	        TemplateRepresentation  templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
	                MediaType.TEXT_HTML);
		 return templateRep;
	 }
	    @Put
	    public Representation store(Form form) {
	    	
	        for (Parameter entry : form) {
	            System.out.println(entry.getName() + "=" + entry.getValue());
	            result.setValues(entry);
	            
	        }
//	        System.out.println("Status = "+result.getStatus());
	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    	//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    	        TemplateRepresentation  templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
	    	                MediaType.TEXT_HTML);
	    	return templateRep;
	      
	    }
	    /**
	     * storepasientkomplikasjon
	     * Denne rutinen tar imot alle ny informasjon fra bruker om ny fagprosedyre.
	     * A form must have form elements (Parameters), and they must have names
	     * Otherwise it returns null
	     * @param form
	     * @return
	     */
	    @Post
	    public Representation storeHemovigilans(Form form) {
	    	TemplateRepresentation  templateRep = null;
	    	if (form == null){
	    		sessionAdmin.getSession(getRequest(),"pasientkomplikasjon").invalidate();
	    	}
	    	if (form != null){
	    		result = (PasientKomplikasjonWebModel) sessionAdmin.getSessionObject(getRequest(),"pasientkomplikasjon");
	    		Parameter logout = form.getFirst("logout");
	    		if (logout != null){
	    			sessionAdmin.getSession(getRequest(),"pasientkomplikasjon").invalidate();
		    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemivigilans/Logout.html"));
		    		Representation pasientkomplikasjonFtl = clres2.get();
		    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
		    				MediaType.TEXT_HTML);
	    			return templateRep; // return a new page!!!
	    		}
	    			
	    		if (result == null){
	    			result = new PasientKomplikasjonWebModel();
	    			 result.setFormNames(sessionParams);
	    		}
	    		for (Parameter entry : form) {
	    			System.out.println(entry.getName() + "=" + entry.getValue());
	    			result.setValues(entry);

	    		}
	    		sessionAdmin.setSessionObject(getRequest(), result,"pasientkomplikasjon");
	    		Parameter lagre = form.getFirst("lagrekjema");
	    		if (lagre != null){
	    			hendelseWebService.saveHendelse(result);
	    		}
//	    		System.out.println("Status = "+result.getStatus());
	    		// Denne client resource forholder seg til src/main/resource katalogen !!!	
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/cellerogvev.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
	    				MediaType.TEXT_HTML);
	    	}
	    	return templateRep;
	      
	    }
}
