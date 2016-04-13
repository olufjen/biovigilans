package no.naks.biovigilans_admin.web.server.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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

import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans_admin.web.control.SaksbehandlingRealm;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;

public class LoginServerResourceHTML extends SaksbehandlingSessionServer {
	private DefaultWebSecurityManager securityManager;
	private SaksbehandlingRealm saksbehandlingRealm = null;
	
	public LoginServerResourceHTML() {
		super();
		
	}
	
	public DefaultWebSecurityManager getSecurityManager() {
		return securityManager;
	}

	public void setSecurityManager(DefaultWebSecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	     Request request = getRequest();


	    
	     ArrayList<Realm> realms = (ArrayList)securityManager.getRealms();
	     saksbehandlingRealm = (SaksbehandlingRealm)realms.get(0);
	     List<Saksbehandler> saksbehandlere = saksbehandlingRealm.getSaksbehandlere();
	     sessionAdmin.setSessionObject(request, saksbehandlere, behandlereKey);
	     login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
	     if (login == null){
	    	 login = new LoginModel();
    	     sessionAdmin.setSessionObject(request, login, loginKey);
	     }
	    
	     login.setFormNames( getSessionParams());
/*	  
 * En Hashmap benyttes dersom en html side henter data fra flere javaklasser.	
 * Hver javaklasse får en id (ex pasientkomplikasjonId) som er tilgjengelig for html
 *      
*/	     
	     Map<String, Object> dataModel = new HashMap<String, Object>();


	
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/login.html"));
	     
	        // Load the FreeMarker template
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/pasientkomplikasjon/nymeldingfagprosedyre.html").get();
	        Representation loginFtl = clres2.get();
	//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	        
//	        TemplateRepresentation  templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
//	                MediaType.TEXT_HTML);
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(loginFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	 }
    @Post
    public Representation storeHemovigilans(Form form) {
    	
    	Request request = getRequest();
    	if(form == null){
    		invalidateSessionobjects();
    	}
    	
    	Map<String, Object> dataModel = new HashMap<String, Object>();
    	String page = "/hemovigilans/login.html";
    	if (form != null){
    	
    		 List<Saksbehandler> saksbehandlere = (List)sessionAdmin.getSessionObject(request,behandlereKey);
    	     login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
    	     if (login == null){
    	    	 login = new LoginModel();
        	     sessionAdmin.setSessionObject(request, login, loginKey);
        	     login.setFormNames( getSessionParams());
    	     }   		 
    	     ArrayList<Realm> realms = (ArrayList)securityManager.getRealms();
    	     saksbehandlingRealm = (SaksbehandlingRealm)realms.get(0);
    	     saksbehandlingRealm.setRequest(request);
    	 		for (Parameter entry : form) {
        			if (entry.getValue() != null && !(entry.getValue().equals(""))){
        					System.out.println(entry.getName() + "=" + entry.getValue());
        					login.setValues(entry);
        			}

        		}
    	 	login.saveLogin();
    	 	saksbehandlingRealm.setLoginSaksbehandler(login.getSaksbehandler());
    	    SecurityUtils.setSecurityManager(securityManager);
    	    Subject currentUser = SecurityUtils.getSubject();
    	    UsernamePasswordToken token = saksbehandlingRealm.createToken();
    	    boolean allowed = true;
    	    try {
    	        currentUser.login(token);
    	    } catch ( UnknownAccountException uae ) { 
    	    	System.out.println("Unknown account"+uae.getMessage());
    	    	allowed = false;
    	    } catch ( IncorrectCredentialsException ice ) { 
    	    	System.out.println("no credential "+ice.getMessage());
    	    	allowed = false;
    	    } catch ( LockedAccountException lae ) { 
    	    	System.out.println("locked user "+lae.getMessage());
    	    	allowed = false;
    	    } catch ( ExcessiveAttemptsException eae ) { 
    	    	 System.out.println("excessive attempts "+eae.getMessage());
    	    	 allowed = false;
      	    } catch ( AuthenticationException ae ) {
    	    	 System.out.println("authentication failure "+ae.getMessage());
    	    	 allowed = false;
    	    }
    	    if (allowed){
    	    	  System.out.println("Bruker godkjent");
/*
 * saksbahandler har også valgt database    	    	  
 */
    	    	  login.setSaksbehandler(saksbehandlingRealm.getLoginSaksbehandler());
    	    	  
    	    	  page = "../hemovigilans/";
 	    	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/login.html"));
 	 	        Representation loginFtl = clres2.get();
 	 	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(loginFtl,dataModel,
 	 	                MediaType.TEXT_HTML);
    	    	  redirectPermanent(page);

    	     	return templatemapRep;
    	    }
    	  
    	}
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));
	     
	        // Load the FreeMarker template
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/pasientkomplikasjon/nymeldingfagprosedyre.html").get();
	        Representation loginFtl = clres2.get();
	//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	        
//	        TemplateRepresentation  templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
//	                MediaType.TEXT_HTML);
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(loginFtl,dataModel,
	                MediaType.TEXT_HTML);
    	return templatemapRep;
    }
	
}
