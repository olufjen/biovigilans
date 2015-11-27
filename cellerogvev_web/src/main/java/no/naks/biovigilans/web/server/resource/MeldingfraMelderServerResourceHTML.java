package no.naks.biovigilans.web.server.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.felles.control.SaksbehandlingWebService;
import no.naks.biovigilans.felles.model.AnnenKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.model.MeldingModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.AnnenkomplikasjonImpl;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.DiskusjonImpl;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.Vigilansmelding;

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

public class MeldingfraMelderServerResourceHTML extends
		SessionServerResource {
	protected SaksbehandlingWebService saksbehandlingWebservice;
	private Vigilansmelding melding;
	private String  meldingsType;
	
	private String meldingstypeKey = "meldingstype";
	protected String tilmelderKey = "melderen";
	protected String diskusjonsKey = "diskusjon";
	protected String sakModelKey = "sakModel";
	protected String statusflagKey = "statusflag";
	private String paramMessageKey = "meldingsparam";
	private String sendKey = "send";
	private String sendPart = "block";
	private String buttonPart = "Send Melding";
	private String buttonKey = "sendButton";
			
	public MeldingfraMelderServerResourceHTML() {
		super();
		
	}
	
	public SaksbehandlingWebService getSaksbehandlingWebservice() {
		return saksbehandlingWebservice;
	}

	public void setSaksbehandlingWebservice(
			SaksbehandlingWebService saksbehandlingWebservice) {
		this.saksbehandlingWebservice = saksbehandlingWebservice;
	}

	private void findMessageType(Request request){
		 meldingsType =	(String) sessionAdmin.getSessionObject(request,meldingstypeKey);
		 if (meldingsType != null){
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
		 
	}
	
	@Get
	public Representation getHemovigilans() {

	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	     Request request = getRequest();
	     Reference reqRef = request.getResourceRef();
	     Form form = request.getResourceRef().getQueryAsForm();
	     String meldingParam = null;
	     String diskparam = null;
	     Long meldeId = null;
	     Long diskId = null;
	     List<Vigilansmelding> meldinger = null;
	     melding = null;
	     Diskusjon diskusjon = null;
	    
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	     for (Parameter parameter : form) {
	         System.out.print("parameter " + parameter.getName());
	         System.out.println(": " + parameter.getValue());
	         if (parameter.getName() != null && parameter.getName().equals("meldeid")){
	        	 meldingParam =  parameter.getValue();
	         }
	         if (parameter.getName() != null && parameter.getName().equals("diskid")){
	        	 diskparam =  parameter.getValue();
	         }	         
	     }
	     String paramMessage = "?meldeid="+meldingParam+"&diskid="+diskparam;
	     sessionAdmin.setSessionObject(request,paramMessage, paramMessageKey);
	     if (meldingParam != null){
	    	 meldeId = Long.valueOf(meldingParam);
	    	 meldinger = hendelseWebService.collectMeldingen(meldeId);
	    	 if (meldinger != null && !meldinger.isEmpty()){
	    		 melding = meldinger.get(0);
	    		 sessionAdmin.setSessionObject(request, melding, meldingtilMelderKey);
	    	 }
	     }
	     if (diskparam != null){
	    	 diskId = Long.valueOf(diskparam);
	    	 diskusjon = hendelseWebService.collectDiskusjon(diskId);
//	    	 String komm = "Vennlingst stryk teksten og skriv ditt svar: " + diskusjon.getKommentar();
//	    	 diskusjon.setKommentar(komm);
	     }
	     if (melding == null){
	    	 melding = new AnnenkomplikasjonImpl("");
	    	 melding.setMeldingsnokkel("Ikke angitt");
	    	 melding.setDatoforhendelse(new Date());
	    	 melding.setDatooppdaget(new Date());
	    	 melding.setMeldingsdato(new Date());
	    	 sessionAdmin.setSessionObject(request, melding, meldingtilMelderKey);
	    	 sendPart = "none";
	     }
	     if (diskusjon == null){
	    	 diskusjon = new DiskusjonImpl();
	    	 diskusjon.setTema("Ingen melding"); 
	    	 diskusjon.setKommentar("");
	     }
		 displayPart = "none";
    	 datePart = "none";
    	 
    	 findMessageType(request);
/*    	 Melder melder = saksbehandlingWebservice.collectmelder(melding.getMelderId());
    	 sessionAdmin.setSessionObject(request, melder, tilmelderKey);
    	 sessionAdmin.setSessionObject(request, melding, meldingtilMelderKey);
    	 */
    	 meldingsDiskusjon = (MeldingModel) sessionAdmin.getSessionObject(request,meldingdiskusjonKey); 
    	 if (meldingsDiskusjon == null){
    		 meldingsDiskusjon = new MeldingModel();
    		 sessionAdmin.setSessionObject(request,meldingsDiskusjon, meldingdiskusjonKey);
    	 }
    	  meldingsDiskusjon.setFormNames( getSessionParams());
    	  meldingsDiskusjon.setDiskusjon(diskusjon);
    	  
	   	 SimpleScalar simple = new SimpleScalar(displayPart);
    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
    	 SimpleScalar sendMessage = new SimpleScalar(sendPart);
    	 SimpleScalar sendButton = new SimpleScalar(buttonPart);
    	 dataModel.put(meldingtilMelderKey, melding);
    	 dataModel.put(diskusjonsKey,diskusjon);
    	 dataModel.put(sendKey, sendMessage);
    	 dataModel.put(buttonKey, sendButton);
/*    	 dataModel.put(statusflagKey,statusflag);
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(tilmelderKey, melder);
    	 dataModel.put(meldingtilMelderKey, melding);
    	 */
    	 
//Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/meldingframelder.html"));
	     
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
	    	if(form == null){
	    		invalidateSessionobjects();
	    	}
	    	
	    	Map<String, Object> dataModel = new HashMap<String, Object>();
	   	 meldingsDiskusjon = (MeldingModel) sessionAdmin.getSessionObject(request,meldingdiskusjonKey); 
	   	 melding = (Vigilansmelding)sessionAdmin.getSessionObject(request,meldingtilMelderKey); 
    	 if (meldingsDiskusjon == null){
    		 meldingsDiskusjon = new MeldingModel();
    		 sessionAdmin.setSessionObject(request,meldingsDiskusjon, meldingdiskusjonKey);
    	 }
    	   meldingsDiskusjon.setFormNames( getSessionParams());
    	   meldingsDiskusjon.setDiskusjon(null);
    	   SimpleScalar sendMessage = new SimpleScalar(sendPart);
	    	if (form != null){
	    		Parameter sendMelding = form.getFirst("btnsendmelding");
	    		Parameter avslutt = form.getFirst("btnavslutt");
	    		if (sendMelding != null){
		     		for (Parameter entry : form) {
	        			if (entry.getValue() != null && !(entry.getValue().equals(""))){
	        					System.out.println(entry.getName() + "=" + entry.getValue());
	        					meldingsDiskusjon.setValues(entry);
	        			}

	        		}
		     		meldingsDiskusjon.saveDiskusjon();
		     		meldingsDiskusjon.getDiskusjon().setMeldeid(melding.getMeldeid());
		     		saksbehandlingWebservice.saveDiskusjon(meldingsDiskusjon.getDiskusjon());
		     		melding.setSjekklistesaksbehandling("Tilleggsopplysninger mottatt");
		     		hendelseWebService.updateVigilansmelding(melding);
		    		 displayPart = "none";
		        	 datePart = "none";
		        	 Melder melder = (Melder) sessionAdmin.getSessionObject(request, tilmelderKey);
//		        	 Vigilansmelding melding = (Vigilansmelding) sessionAdmin.getSessionObject(request, meldingtilMelderKey);
		    	   	 SimpleScalar simple = new SimpleScalar(displayPart);
		        	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		        	 String paramMessage = (String)sessionAdmin.getSessionObject(request, paramMessageKey);	
//		        	 dataModel.put(statusflagKey,statusflag);
		        	 dataModel.put(displayKey, simple);
		          	 dataModel.put(meldingtilMelderKey, melding);
		        	 dataModel.put(diskusjonsKey,meldingsDiskusjon.getDiskusjon());
		        	 dataModel.put(sendKey, sendMessage);
		        	 buttonPart = "Melding levert";
		        	 SimpleScalar sendButton = new SimpleScalar(buttonPart);
		        	 dataModel.put(buttonKey, sendButton);
//		        	 dataModel.put(tilmelderKey, melder);
//		        	 dataModel.put(meldingtilMelderKey, melding);
		 	    	ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/meldingframelder.html"+paramMessage));
					 
					Representation meldingmelderHendelser = clres2.get();
//		       		invalidateSessionobjects();
		    		templateRep = new TemplateRepresentation(meldingmelderHendelser, dataModel,
		    				MediaType.TEXT_HTML);	
		    		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
	    		}
	    		if (avslutt != null){
	    			invalidateSessionobjects();
	    			sessionAdmin.getSession(request,diskusjonsKey).invalidate();
	    			sessionAdmin.getSession(request,sakModelKey).invalidate();
	    		   	 SimpleScalar simple = new SimpleScalar(displayPart);
		        	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		     
//		        	 dataModel.put(statusflagKey,statusflag);
		        	 
	    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/meldingframelder.html"));
	   			 
	    			Representation meldingHendelser = clres2.get();
//	        		invalidateSessionobjects();
	        		templateRep = new TemplateRepresentation(meldingHendelser, dataModel,
	        				MediaType.TEXT_HTML);
	        		redirectPermanent("../hemovigilans/");
	        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
	    		}
	    	}
	    	ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/meldingframelder.html"));
			 
			Representation meldingmelderHendelser = clres2.get();
//       		invalidateSessionobjects();
    		templateRep = new TemplateRepresentation(meldingmelderHendelser, dataModel,
    				MediaType.TEXT_HTML);
    	
	    	return templateRep;
	  }

}
