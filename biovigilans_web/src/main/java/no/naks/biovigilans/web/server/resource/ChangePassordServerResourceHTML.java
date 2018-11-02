package no.naks.biovigilans.web.server.resource;

import java.util.ArrayList;
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

import freemarker.template.SimpleScalar;
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;

/**
 * Denne resursen sørger for at bruker får anledning til å endre sitt passord.
 * @author olj
 *
 */
public class ChangePassordServerResourceHTML extends SessionServerResource {
	private String meldeTxtId = "melding"; // Peker til meldingstekst til bruker ved valgt av oppfølgingsmelding/meldingsoversikt og bruker oppgir feil passord
	private String meldernavnID = "Mnavn";
	private String melderepostID = "Mepost";
	private String genPWId = "passwordID"; // Session ID for generert passord
	private String engangPWID = "engang"; //Benes til bruker for å angi engangspassord
	/**
	 * getHemovigilans
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en epost adresse fra en bruker som ønsker å endre sitt passord
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	 	 String meldingsText = "";
	  	 displayPart = "none";
	  	 String displayOrd = "none";
	  	 String engangDisplay = "block";
	  	 String startPagekey = "start";
	     String genPasswd = (String) sessionAdmin.getSessionObject(request,genPWId);
	     melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);
	     Melder melder = (Melder) sessionAdmin.getSessionObject(request,melderNokkel);
	     String chPW = melderwebModel.getChangePasswd();
	     if (chPW != null && !chPW.isEmpty()){
		  	 displayOrd = "block";
		  	 engangDisplay = "none";
	     }
		 SimpleScalar startPage = new SimpleScalar(displayOrd);
		 SimpleScalar tilOversikt = new SimpleScalar(displayPart);
	 	 SimpleScalar simple = new SimpleScalar(meldingsText);
	 	 SimpleScalar engangPage = new SimpleScalar(engangDisplay);
	 	 dataModel.put(engangPWID, engangPage);
		 dataModel.put( meldeTxtId,simple);
		 dataModel.put( displayKey,tilOversikt);
		 dataModel.put( startPagekey,startPage);
	     String melderNavn = "";
	     if (melder.getMeldernavn()!= null){
	    	 melderNavn = melder.getMeldernavn();
	     };
	     String melderEpost = melder.getMelderepost();
	     SimpleScalar meldNavn = new SimpleScalar(melderNavn);
	     SimpleScalar meldEpost = new SimpleScalar(melderEpost);
	     dataModel.put(meldernavnID,meldNavn);
	     dataModel.put(melderepostID,meldEpost);
	     
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
	     setMelderObject();
//	     setTransfusjonsObjects();
    	 melderwebModel.setFormNames(sessionParams);
 //   	 setMelderparams();
    	 melderwebModel.distributeTerms();
    	 
	     dataModel.put(melderId, melderwebModel);
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/changepassord.html"));
	     melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);

	     melderwebModel.setMelder(melder);
	     sessionAdmin.setSessionObject(getRequest(), melderwebModel,melderId);
	        Representation pasientkomplikasjonFtl = clres2.get();
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	}
    /**
     * storeHemovigilans
     * Denne rutinen sørger for å sende passord til oppgitt epost adresse, eller gir melding om at epost adresse ikke finnes
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	TemplateRepresentation  templateRep = null;
 	    Map<String, Object> dataModel = new HashMap<String, Object>();
 	    String meldingsText = "Passordet har ikke nok styrke, vennligst prøv igjen";

	    Request request = getRequest();
	    melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);
	    Melder melder = (Melder) sessionAdmin.getSessionObject(request,melderNokkel);
	    String genPasswd = (String) sessionAdmin.getSessionObject(request,genPWId);
	    List<Melder> meldere = new ArrayList<Melder>();
	    meldere.add(melder);
/*	    Map<String,List> alleMeldinger = new HashMap<String,List>();
 	    List<Vigilansmelding> meldinger = null;
 //	    List delMeldinger = null;
 	    List<Vigilansmelding> andreMeldinger = null;
 	    List<Vigilansmelding> pasientMeldinger = null;
 	    List<Vigilansmelding> giverMeldinger = null;*/
 	    
    	if(form == null){
    		invalidateSessionobjects();
    	}
/*
 * Verdier angitt av bruker    	
 */
    	String melderEpost = null;
    	String melderPassord = null;
    	String meldingsNokkel = null;
    	String passordGjentatt = null;
/*
 * Verdier fra database
 */
		String name ="";
		String passord = "";
		String epost = "";
    	Long melderid = null; 
    	String userGenpasswd = "";
    	for (Parameter entry : form) {
			if (entry.getValue() != null && !(entry.getValue().equals(""))){
//					System.out.println(entry.getName() + "=" + entry.getValue());
					if (entry.getName().equals("k-epost")){
						melderPassord = entry.getValue();
					}
					if (entry.getName().equals("k-gjentaepost")){
						passordGjentatt = entry.getValue();
					}
					if (entry.getName().equals("k-genpassword")){
						userGenpasswd = entry.getValue();
					}					
					
			}
			
    	}
		Parameter formValue = form.getFirst("passord"); // Bruker har endret passord
		Parameter meldOversikt = form.getFirst("meldoversikt"); // Bruker ønsker å gå til sin meldingsoversikt
//	    String page = "../hemovigilans/melder_rapport.html"; 
		if (formValue != null && userGenpasswd != null){ // Bruker kommer fra kontaktskjema og oppgir tilsendt generert passord
			if (genPasswd.equals(userGenpasswd)){
			  	 String displayOrd = "block";
			  	 String startPagekey = "start";
			  	String engangDisplay = "none";
			  	 meldingsText = "Engangpassord riktig! Vennligst lag et nytt passord etter gjeldende regler";
				 SimpleScalar startPage = new SimpleScalar(displayOrd);
				 String melderNavn = melder.getMeldernavn();
				 melderEpost = melder.getMelderepost();
			 	 SimpleScalar engangPage = new SimpleScalar(engangDisplay);
			 	 dataModel.put(engangPWID, engangPage);
			     SimpleScalar meldNavn = new SimpleScalar(melderNavn);
			     SimpleScalar meldEpost = new SimpleScalar(melderEpost);
				 dataModel.put( startPagekey,startPage);
			     dataModel.put(meldernavnID,meldNavn);
			     dataModel.put(melderepostID,meldEpost);
				 SimpleScalar simple = new SimpleScalar(meldingsText);
				 dataModel.put( meldeTxtId,simple);
			     dataModel.put(melderId, melderwebModel);
			     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/changepassord.html"));
			     Representation pasientkomplikasjonFtl = clres2.get();
			        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
			                MediaType.TEXT_HTML);
				 return templatemapRep;
			}else if (!genPasswd.equals(userGenpasswd)&& melderPassord == null){ // Engangspassord feil
			  	 String displayOrd = "none";
			  	 String engangDisplay = "block";
			  	 String startPagekey = "start";
			  	 meldingsText = "Engangpassord feil! Vennligst prøv igjen";
				 SimpleScalar startPage = new SimpleScalar(displayOrd);
				 String melderNavn = melder.getMeldernavn();
				 melderEpost = melder.getMelderepost();
			 	 SimpleScalar engangPage = new SimpleScalar(engangDisplay);
			 	 dataModel.put(engangPWID, engangPage);
			     SimpleScalar meldNavn = new SimpleScalar(melderNavn);
			     SimpleScalar meldEpost = new SimpleScalar(melderEpost);
				 dataModel.put( startPagekey,startPage);
			     dataModel.put(meldernavnID,meldNavn);
			     dataModel.put(melderepostID,meldEpost);
				 SimpleScalar simple = new SimpleScalar(meldingsText);
				 dataModel.put( meldeTxtId,simple);
			     dataModel.put(melderId, melderwebModel);
			     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/changepassord.html"));
			     Representation pasientkomplikasjonFtl = clres2.get();
			        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
			                MediaType.TEXT_HTML);
				 return templatemapRep;
			}
		}
		if (formValue != null && melderPassord != null && passordGjentatt != null){
			boolean bStrenght = false;
			if (melderPassord.equals(passordGjentatt) && melderPassord.length() >= 8){
				melder.setMelderPassord(melderPassord);
				bStrenght = adminWebService.checkStrenghtPassword(melder);
			}
			if (!bStrenght){
			  	 String displayOrd = "block";
			  	 String startPagekey = "start";
			 	String engangDisplay = "none";
			 	 SimpleScalar engangPage = new SimpleScalar(engangDisplay);
			 	 dataModel.put(engangPWID, engangPage);
				 SimpleScalar startPage = new SimpleScalar(displayOrd);
				 String melderNavn = melder.getMeldernavn();
				 melderEpost = melder.getMelderepost();
			     SimpleScalar meldNavn = new SimpleScalar(melderNavn);
			     SimpleScalar meldEpost = new SimpleScalar(melderEpost);
				 dataModel.put( startPagekey,startPage);
			     dataModel.put(meldernavnID,meldNavn);
			     dataModel.put(melderepostID,meldEpost);
				 SimpleScalar simple = new SimpleScalar(meldingsText);
				 dataModel.put( meldeTxtId,simple);
			     dataModel.put(melderId, melderwebModel);
			     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/changepassord.html"));
			     Representation pasientkomplikasjonFtl = clres2.get();
			        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
			                MediaType.TEXT_HTML);
				 return templatemapRep;
			}
			if (bStrenght){
				meldingsText = "Passordet er godkjent og endret";
			  	 displayPart = "block";
			  	 String displayOrd = "none";
			  	 String startPagekey = "start";
				 String engangDisplay = "none";
				 SimpleScalar engangPage = new SimpleScalar(engangDisplay);
				 dataModel.put(engangPWID, engangPage);
				 SimpleScalar startPage = new SimpleScalar(displayOrd);
				 SimpleScalar tilOversikt = new SimpleScalar(displayPart);
				 dataModel.put( displayKey,tilOversikt);
				 String melderNavn = melder.getMeldernavn();
				 melderEpost = melder.getMelderepost();
			     SimpleScalar meldNavn = new SimpleScalar(melderNavn);
			     SimpleScalar meldEpost = new SimpleScalar(melderEpost);
				 dataModel.put( startPagekey,startPage);
			     dataModel.put(meldernavnID,meldNavn);
			     dataModel.put(melderepostID,meldEpost);
				 SimpleScalar simple = new SimpleScalar(meldingsText);
				 dataModel.put( meldeTxtId,simple);
			     dataModel.put(melderId, melderwebModel);
			     adminWebService.encyptmeldere(meldere);
			     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/changepassord.html"));
			     Representation pasientkomplikasjonFtl = clres2.get();
			        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
			                MediaType.TEXT_HTML);
				 return templatemapRep;				
			}
	    
		}
		if(meldOversikt != null) {
			String page = "../hemovigilans/melder_rapport.html";
		     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/changepassord.html"));
		     Representation pasientkomplikasjonFtl = clres2.get();
		        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
		                MediaType.TEXT_HTML);
				redirectPermanent(page);
			 return templatemapRep;		
		}
	 SimpleScalar simple = new SimpleScalar(meldingsText);
	 dataModel.put( meldeTxtId,simple);
	
		//Feil passord går til startside.
 		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/passord.html"));
		Representation pasientkomplikasjonFtl = clres2.get();
		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
				MediaType.TEXT_HTML);
		return templateRep;
    }
    
}
