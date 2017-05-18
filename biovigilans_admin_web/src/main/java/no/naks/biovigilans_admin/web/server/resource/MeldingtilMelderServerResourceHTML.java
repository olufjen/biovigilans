package no.naks.biovigilans_admin.web.server.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.felles.model.AnnenKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.model.MeldingModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans_admin.web.control.SaksbehandlingRealm;

import org.apache.shiro.realm.Realm;
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

import freemarker.template.SimpleScalar;

/**
 * MeldingtilMelderServerResourceHTML
 * Denne Resursklassen håndterer brukstilfellet Dialog melder.
 * Dersom saksbehandler har behov for ytterligere opplysinger om en Biovigilanbsmelding, så kan saksbehandler sende en melding til melder og be ham/henne svare med supplerende informasjon.
 * @author olj
 *
 */
public class MeldingtilMelderServerResourceHTML extends
		SaksbehandlingSessionServer {
	private Vigilansmelding melding;
	
	public MeldingtilMelderServerResourceHTML() {
		super();
		
	}
	private void findMessageType(Request request){
		 meldingsType =	(String) sessionAdmin.getSessionObject(request,meldingstypeKey);
		 if (meldingsType.equals("annen")){
			 annenModel = (AnnenKomplikasjonwebModel)sessionAdmin.getSessionObject(request, andreHendelseId);
			 melding = annenModel.getVigilansmelding();
		 }
		 if (meldingsType.equals("giver")){
			 giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(request,giverkomplikasjonId);
			 melding = (Vigilansmelding)giverModel.getVigilansmelding();
		
		 }
		 if (meldingsType.equals("transfusjon")){
			 transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(getRequest(),transfusjonId);
			 melding = (Vigilansmelding)transfusjon.getVigilansmelding();
		
		 }				 
	}
	
	@Get
	public Representation getHemovigilans() {

	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	     Request request = getRequest();
    
	     Map<String, Object> dataModel = new HashMap<String, Object>();

		 displayPart = "none";
    	 datePart = "none";
    	 
    	 findMessageType(request);
    	 Melder melder = null;
    	 if (melding.getMelderId() != null && melding.getMelderId().longValue() != 0)
    			 melder = saksbehandlingWebservice.collectmelder(melding.getMelderId());
	     login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
	     String db =  sessionAdmin.getChosenDB(request);
	     setMeldertext(db);
    	 sessionAdmin.setSessionObject(request, melder, tilmelderKey);
    	 sessionAdmin.setSessionObject(request, melding, meldingtilMelderKey);
    	 meldingsDiskusjon = (MeldingModel) sessionAdmin.getSessionObject(request,meldingdiskusjonKey); 
    	 if (meldingsDiskusjon == null){
    		 meldingsDiskusjon = new MeldingModel();
    		 sessionAdmin.setSessionObject(request,meldingsDiskusjon, meldingdiskusjonKey);
    	 }
    	  meldingsDiskusjon.setFormNames( getSessionParams());
	   	 SimpleScalar simple = new SimpleScalar(displayPart);
    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
    	 if (melder == null){
    		 melder = new MelderImpl();
    		 melder.setMelderepost("Melder ikke funnet!! ");
    	 }
    
    	 dataModel.put(statusflagKey,statusflag);
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(tilmelderKey, melder);
    	 dataModel.put(meldingtilMelderKey, melding);
    	 
//Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/meldingtilmelder.html"));
	     
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
		   findMessageType(request);
		   TemplateRepresentation  templateRep = null;
		   login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
		   Map<String, Object> dataModel = new HashMap<String, Object>();
		   meldingsDiskusjon = (MeldingModel) sessionAdmin.getSessionObject(request,meldingdiskusjonKey); 
		   if (meldingsDiskusjon == null){
			   meldingsDiskusjon = new MeldingModel();
			   sessionAdmin.setSessionObject(request,meldingsDiskusjon, meldingdiskusjonKey);
		   }
		   String db =  sessionAdmin.getChosenDB(request);
		   setMeldertext(db);
		   meldingsDiskusjon.setFormNames( getSessionParams());
		   if (form != null){
	    		Parameter sendMelding = form.getFirst("btnsendmelding");
	    		Parameter avslutt = form.getFirst("btnavslutt");
	    		if (sendMelding != null){ // Bruker velger å sende melding
		     		for (Parameter entry : form) {
	        			if (entry.getValue() != null && !(entry.getValue().equals(""))){
	        					System.out.println(entry.getName() + "=" + entry.getValue());
	        					meldingsDiskusjon.setValues(entry);
	        			}

	        		}
		     		String saksNavn = login.getSaksbehandler().getBehandernavn();
		     		meldingsDiskusjon.saveDiskusjon();
		     		String sakTema = meldingsDiskusjon.getDiskusjon().getTema();
		     		sakTema = sakTema + ";"+saksNavn;
		     		meldingsDiskusjon.getDiskusjon().setTema(sakTema);
		     		meldingsDiskusjon.getDiskusjon().setMeldeid(melding.getMeldeid());
		     		saksbehandlingWebservice.saveDiskusjon(meldingsDiskusjon.getDiskusjon());
		     		displayPart = "none";
		     		datePart = "none";
		     		String meldingsId = String.valueOf(melding.getMeldeid().longValue());
		     		String diskId = String.valueOf(meldingsDiskusjon.getDiskusjon().getDiskusjonid().longValue());
		     		Melder melder = (Melder) sessionAdmin.getSessionObject(request, tilmelderKey);
		     		Vigilansmelding melding = (Vigilansmelding) sessionAdmin.getSessionObject(request, meldingtilMelderKey);
		     		SimpleScalar simple = new SimpleScalar(displayPart);
		     		SimpleScalar hendelseDate = new SimpleScalar(datePart);
		     		System.out.println("Sender melding til melder - sjekker epost adresse");
		     	    if(melder.getMelderepost() != null || !melder.getMelderepost().equals("")){
		     	    	System.out.println("Sender melding til melder: " +melder.getMelderepost()+ " Om "+meldingsDiskusjon.getDiskusjon().getTema());
		     	    	emailWebService.setSubject(meldingsDiskusjon.getDiskusjon().getTema());
		     	    	emailWebService.setEmailText("Detaljer finner du i vedlagte lenke.");
		    	    	 emailWebService.setMailTo(melder.getMelderepost());
		    	    	 String msg = String.format(tilMeldermsg+meldingsId+"&diskid="+diskId
		    	    			 +tilMeldertillegg);
		    	    	 emailWebService.sendEmail(msg); //Kommentert bort til stage !!
		    	    }	     
		        	 dataModel.put(statusflagKey,statusflag);
		        	 dataModel.put(displayKey, simple);
		        	 dataModel.put(tilmelderKey, melder);
		        	 dataModel.put(meldingtilMelderKey, melding);
		 	    	ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/meldingtilmelder.html"));
					 
					Representation meldingmelderHendelser = clres2.get();
//		       		invalidateSessionobjects();
		    		templateRep = new TemplateRepresentation(meldingmelderHendelser, dataModel,
		    				MediaType.TEXT_HTML);		        	 
	    		}
	    		if (avslutt != null){
	    			LoginModel newLogin = new LoginModel();
	    			newLogin.setSaksbehandler(login.getSaksbehandler());
	    			newLogin.setPassord(login.getSaksbehandler().getBehandlerpassord());
	    			newLogin.setEpostAdresse(login.getSaksbehandler().getBehandlerepost());
	    			
/*	    			sessionAdmin.getSession(request,diskusjonsKey).invalidate();
	    			sessionAdmin.getSession(request,sakModelKey).invalidate();
*/	
	    		 	sessionAdmin.removesessionObject(request, diskusjonsKey);
	    		 	sessionAdmin.removesessionObject(request, sakModelKey);
	    			sessionAdmin.setSessionObject(request, newLogin, loginKey);
	    		   	 SimpleScalar simple = new SimpleScalar(displayPart);
		        	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		     
		        	 dataModel.put(statusflagKey,statusflag);
		        	 
	    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));
	   			 
	    			Representation meldingHendelser = clres2.get();
	        		templateRep = new TemplateRepresentation(meldingHendelser, dataModel,
	        				MediaType.TEXT_HTML);
	        		redirectPermanent("../hemovigilans/saksbehandling.html");
	        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
	    		}
	    	}
	    	ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/meldingtilmelder.html"));
			 
			Representation meldingmelderHendelser = clres2.get();
//       		invalidateSessionobjects();
    		templateRep = new TemplateRepresentation(meldingmelderHendelser, dataModel,
    				MediaType.TEXT_HTML);
    	
	    	return templateRep;
	  }

}
