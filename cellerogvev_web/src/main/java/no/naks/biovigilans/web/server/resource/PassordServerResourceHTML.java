package no.naks.biovigilans.web.server.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
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
 * Denne resursen sørger for at bruker får tilsendt sitt passord, dersom bruker har glemt dette.
 * @since 17.10.18:
 * Genererer et engangspassord som sendes til bruker
 * @since 20.05.19 Tipasset brukerscenario 2 beskrevet i Jira Meld-80
 * Denne resrures er en del omarbeidet for denne tilpasningen.
 * Gammel versjon finnes på google drive C:\Users\olj\Google Drive\jobb\hdir\biovigilans\jira\kildekode
 * @author olj
 *
 */
public class PassordServerResourceHTML extends SessionServerResource {
	private String meldeTxtId = "melding";
	private String genPWId = "passwordID";
	private String changeId ="change"; // Flagg for å endre passord
	private String buttonTxtId = "buttonTxt";
	private String emailID = "email";
	private String meldernavnID = "Mnavn";
	private String melderepostID = "Mepost";
	private String engangPWID = "engang"; //Denne til bruker for å angi engangspassord
	/**
	 * getHemovigilans
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en epost adresse fra en bruker som ikke husker epost/passord
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	     Map<String, Object> dataModel = new HashMap<String, Object>();

	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/cellerogvev");
	    
	     LocalReference localUri = new LocalReference(reference);

	     String page = "/cellerogvev/passord.html";
	     String result = (String)sessionAdmin.getSessionObject(request,genPWId); //Sjekker om passord allerede er sendt
	 	 Melder melder = (Melder)sessionAdmin.getSessionObject(request,melderNokkel);
	     String name = "x";
	     String email = "";
	
	 	 String engangs = "none";
		
	     if (result != null && melder != null){
		     name = melder.getMeldernavn();
			 email = melder.getMelderepost();
	    	 page = "/cellerogvev/tilsendtpassord.html";
	    	 engangs = "block";
	     }
	 	 String meldingsText = "";
	 	 String pwFlag = "none";
	 	 String buttonTxt = "Hent passord";
	 	 SimpleScalar eepost = new SimpleScalar(email);
	 	 SimpleScalar mNavn = new SimpleScalar(name);

	 	 SimpleScalar simple = new SimpleScalar(meldingsText);
	 	 SimpleScalar changePW = new SimpleScalar(pwFlag);
	 	 SimpleScalar hentPW = new SimpleScalar(buttonTxt);
	 	 SimpleScalar epost = new SimpleScalar(email);
		 SimpleScalar engangPage = new SimpleScalar(engangs);
		 dataModel.put(melderepostID, eepost);
	     dataModel.put(meldernavnID,mNavn);
	 	 dataModel.put(buttonTxtId, hentPW);
		 dataModel.put(meldeTxtId,simple);
		 dataModel.put(changeId, changePW);
	     dataModel.put(engangPWID,engangPage);
		 dataModel.put(emailID, epost);
	     
	     
	     
	     
	     
	     
	     setMelderObject();
//	     setTransfusjonsObjects();
    	 melderwebModel.setFormNames(sessionParams);
 //   	 setMelderparams();
    	 melderwebModel.distributeTerms();
    	 
	     dataModel.put(melderId, melderwebModel);
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));
	     melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);

	     sessionAdmin.setSessionObject(getRequest(), melderwebModel,melderId);
	        Representation pasientkomplikasjonFtl = clres2.get();
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	}
    /**
     * storeHemovigilans
     * 
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	TemplateRepresentation  templateRep = null;
 	    Map<String, Object> dataModel = new HashMap<String, Object>();
 	    String meldingsText = "";
 	    String result = "";
 	    String email = "";
  	   String engangs = "none";
	    Request request = getRequest();
	    result = (String)sessionAdmin.getSessionObject(request,genPWId);
	    melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);
	    Melder kontaktMelder = (Melder)sessionAdmin.getSessionObject(request,melderNokkel);
/*	    Map<String,List> alleMeldinger = new HashMap<String,List>();
 	    List<Vigilansmelding> meldinger = null;
 //	    List delMeldinger = null;
 	    List<Vigilansmelding> andreMeldinger = null;
 	    List<Vigilansmelding> pasientMeldinger = null;
 	    List<Vigilansmelding> giverMeldinger = null;*/
		SimpleScalar simple = new SimpleScalar(meldingsText);
	 	 String pwFlag = "block";
	 	 String buttonTxt = "Hent passord på nytt";
	 	 SimpleScalar hentPW = new SimpleScalar(buttonTxt);
	 	 dataModel.put(buttonTxtId, hentPW);
	 	 SimpleScalar changePW = new SimpleScalar(pwFlag);
//	 	 SimpleScalar eepost = new SimpleScalar(email);
		 dataModel.put( meldeTxtId,simple);
		 dataModel.put(changeId, changePW);
//		 dataModel.put(melderepostID, eepost);

    	if(form == null){
    		invalidateSessionobjects();
    	}
/*
 * Verdier angitt av bruker    	
 */
    	String melderEpost = null;
//    	String melderPassord = null; Bruker oppgir ikke passord
    	String meldingsNokkel = null;
/*
 * Verdier fra database
 */
		String name ="";
		String passord = "";
		String epost = "";
    	Long melderid = null; 
    	
    	for (Parameter entry : form) {
			if (entry.getValue() != null && !(entry.getValue().equals(""))){
					System.out.println(entry.getName() + "=" + entry.getValue());
					if (entry.getName().equals("k-epost")){
						melderEpost = entry.getValue();
					}
			
			}
			
    	}

    	email = melderEpost;
    	if (email == null){
    		pwFlag = "none";
    		meldingsText = "Epost adresse må oppgis!!";
    		changePW = null;
    		changePW = new SimpleScalar(pwFlag);
    	}
		Parameter formValue = form.getFirst("passord"); // Bruker oppgir epostadresse kommer fra siden passord.html
		Parameter changePassword = form.getFirst("tilsendtpassord"); // Bruker sender inn tilsendt passord
		Parameter savePassord = form.getFirst("lagrenyttpassord"); // Bruker har angitt nytt passord
		Parameter tilOversikt = form.getFirst("meldoversikt"); // Bruker ønsker å gå til meldingsoversikt (fra endrepassordok)
		Parameter passordPanytt = form.getFirst("passordpanytt"); // Bruker ønsker å få tilsendt generert passord på nytt
		Parameter tilkontaktSkjema = form.getFirst("kontaktskjema"); // Bruker har byttet passord og skal til kontaktskjema
		boolean bStrenght = true;
		String genPW = "";
		if (tilkontaktSkjema != null){ // Bruker har byttet passord og kommer fra kontaktskjema
			String page = "../cellerogvev/rapporter_kontakt.html";
//			Melder melder = melderwebModel.getMelder();
//			sessionAdmin.setSessionObject(request,melder,melderNokkel); // Må settes for bruk i meldeoversikt.
//			origpasswd = null;
//			sessionAdmin.setSessionObject(getRequest(),origpasswd,origpasswdID);
		     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/rapporter_kontakt.html"));
		     Representation pasientkomplikasjonFtl = clres2.get();
		        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
		                MediaType.TEXT_HTML);
				redirectPermanent(page);
			 return templatemapRep;		
		}
		if (passordPanytt != null){ // Bruker ønsker å få tilsendt generert passord på nytt
			Melder melder = melderwebModel.getMelder();
			melderid = melder.getMelderId();
			if (melderid != null && melder != null ){ // Sender epost til bruker
				emailWebService.setSubject("Passord");
				
/*
 * Decrypt passord før sending OLJ 26.01.18				
 */
				passord = adminWebService.decryptMelderPassword(melder);
			 	result = RandomStringUtils.randomAlphabetic(16);
			 	sessionAdmin.setSessionObject(request,result,genPWId);
     	    	emailWebService.setEmailText("Ditt engangspassord er: "+result+ " Du må nå oppgi dette passordet og velge Bekreft tilsendt passord.");
    	    	 emailWebService.setMailTo(melder.getMelderepost());
    	    	 emailWebService.sendEmail("");
				meldingsText = "Melding med et generert passord er sendt til oppgitt adresse";
			}
		}
		if (tilOversikt != null){// Bruker ønsker å gå til meldingsoversikt (fra endrepassordok)
			String page = "../cellerogvev/melder_rapport.html";
			Melder melder = melderwebModel.getMelder();
			sessionAdmin.setSessionObject(request,melder,melderNokkel); // Må settes for bruk i meldeoversikt.
//			origpasswd = null;
//			sessionAdmin.setSessionObject(getRequest(),origpasswd,origpasswdID);
		     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/melder_rapport.html"));
		     Representation pasientkomplikasjonFtl = clres2.get();
		        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
		                MediaType.TEXT_HTML);
				redirectPermanent(page);
			 return templatemapRep;		
		}
		if (savePassord != null){// Bruker har angitt nytt passord
			String newPW = "x";
			String newPW2 = "y";
			Melder melder = melderwebModel.getMelder();
			if (melder != null){
				name = melder.getMeldernavn();
				email = melder.getMelderepost();
			}
			for (Parameter entry : form) {
				if (entry.getValue() != null && !(entry.getValue().equals(""))){
					//	System.out.println(entry.getName() + "=" + entry.getValue()+" "+result);
						if (entry.getName().equals("k-newpwd")){
							newPW = entry.getValue();
						}
						if (entry.getName().equals("k-bekreftpwd")){
							newPW2 = entry.getValue();
						}
				}
	    	}
			if (newPW.equals(newPW2)){
				melder.setMelderPassord(newPW);
				bStrenght = adminWebService.checkStrenghtPassword(melder);
				if (bStrenght){
				    List<Melder> meldere = new ArrayList<Melder>();
				    meldere.add(melder);
				    adminWebService.encyptmeldere(meldere); //Lagrer nytt passord
				    melder.setPwStrength(bStrenght);
					String page = "../cellerogvev/endrepassordok.html";
					if (kontaktMelder != null){
						page = "../cellerogvev/endrepassordokfrakontakt.html";
						name = kontaktMelder.getMeldernavn();
						email = kontaktMelder.getMelderepost();
					}
					simple = new SimpleScalar(meldingsText);
				 	 SimpleScalar eepost = new SimpleScalar(email);
					 engangs = "block";
					 SimpleScalar engangPage = new SimpleScalar(engangs);
					 dataModel.put( meldeTxtId,simple);
					 dataModel.put(changeId, changePW);
					 dataModel.put(melderepostID, eepost);
				     dataModel.put(meldernavnID,name);
				     dataModel.put(engangPWID,engangPage);
//				     dataModel.put(melderepostID,meldEpost);
					//Feil passord går til startside.
			 		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));
					Representation pasientkomplikasjonFtl = clres2.get();
					templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
							MediaType.TEXT_HTML);
					return templateRep;
				}
				if (!bStrenght){
					String page = "../cellerogvev/endrepassordvglemtpassord.html";
					pwFlag = "none";
					String feilmelding = "Det nye passordet tilfredstiller ikke krav satt. Vennligst prøv igjen";
					 melderwebModel.setChangePasswd("pw");
					 sessionAdmin.setSessionObject(request,melderwebModel,melderId);
				 	 SimpleScalar eepost = new SimpleScalar(email);
				 	 SimpleScalar mNavn = new SimpleScalar(name);
					 SimpleScalar feil = new SimpleScalar(feilmelding);
					 dataModel.put(melderepostID, eepost);
				     dataModel.put(meldernavnID,mNavn);
				     dataModel.put(meldeTxtId, feil);
	/*			 	 SimpleScalar changePW = new SimpleScalar(pwFlag);
				 	 dataModel.put(changeId, changePW);*/
				     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));
				     Representation pasientkomplikasjonFtl = clres2.get();
				        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
				                MediaType.TEXT_HTML);

					 return templatemapRep;	
				}
			}
			if (!newPW.equals(newPW2)){
				String page = "../cellerogvev/endrepassordvglemtpassord.html";
				pwFlag = "none";
				String feilmelding = "Passordene er ikke like og ble ikke lagret. Vennligst skriv passordet på nytt";
				 melderwebModel.setChangePasswd("pw");
				 sessionAdmin.setSessionObject(request,melderwebModel,melderId);
			 	 SimpleScalar eepost = new SimpleScalar(email);
			 	 SimpleScalar mNavn = new SimpleScalar(name);
				 SimpleScalar feil = new SimpleScalar(feilmelding);
				 dataModel.put(melderepostID, eepost);
			     dataModel.put(meldernavnID,mNavn);
			     dataModel.put(meldeTxtId, feil);
/*			 	 SimpleScalar changePW = new SimpleScalar(pwFlag);
			 	 dataModel.put(changeId, changePW);*/
			     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));
			     Representation pasientkomplikasjonFtl = clres2.get();
			        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
			                MediaType.TEXT_HTML);

				 return templatemapRep;	
			}
		}
		if (changePassword != null ){
			Melder melder = melderwebModel.getMelder();
			if (melder == null || melder.getMeldernavn() == null){
				melder = (Melder)sessionAdmin.getSessionObject(request,melderNokkel);
				melderwebModel.setMelder(melder);
			}
			if (melder != null){
				name = melder.getMeldernavn();
				email = melder.getMelderepost();
			}
			for (Parameter entry : form) {
				if (entry.getValue() != null && !(entry.getValue().equals(""))){
					//	System.out.println(entry.getName() + "=" + entry.getValue()+" "+result);
						if (entry.getName().equals("k-genpassword")){
							genPW = entry.getValue();
						}
				}
	    	}
			if (result != null && result.equals(genPW)){
				String page = "../cellerogvev/endrepassordvglemtpassord.html";
				pwFlag = "none";
				 melderwebModel.setChangePasswd("pw");
				 sessionAdmin.setSessionObject(request,melderwebModel,melderId);
			 	 SimpleScalar eepost = new SimpleScalar(email);
			 	 SimpleScalar mNavn = new SimpleScalar(name);
				 dataModel.put(melderepostID, eepost);
			     dataModel.put(meldernavnID,mNavn);
/*			 	 SimpleScalar changePW = new SimpleScalar(pwFlag);
			 	 dataModel.put(changeId, changePW);*/
			     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));
			     Representation pasientkomplikasjonFtl = clres2.get();
			        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
			                MediaType.TEXT_HTML);

				 return templatemapRep;	
			}

		}    	
    	
    	

//	    String page = "../cellerogvev/melder_rapport.html"; 
		if (formValue != null && melderEpost != null){
			List<Melder> rows = melderWebService.selectMelder(melderEpost);
			Melder melder = new MelderImpl();
//			Melder melder = null;
			if(rows != null && rows.size() > 0){
				for(Melder rowmelder :rows){
					melderid = rowmelder.getMelderId();
	
/*					if (row.get("meldernavn") != null)
						name = row.get("meldernavn").toString();
					if (row.get("melderpassord") != null)
						passord = row.get("melderpassord").toString();*/
					name = rowmelder.getMeldernavn();
					passord = rowmelder.getMelderPassord();
					epost = rowmelder.getMelderepost();
					email = rowmelder.getMelderepost();

/*
 * Decrypting password OLJ 10.01.18					
 */
					passord = adminWebService.decryptMelderPassword(passord);
					if (passord != null){
//						Melder melder = new MelderImpl();
						melder.setMelderId(melderid);
						melder.setMeldernavn(name);
						melder.setMelderPassord(passord);
						melder.setMelderepost(epost);
//						sessionAdmin.setSessionObject(request, melder, melderNokkel); Benyttes kun fra kontaktskjema !!! OLJ 27.05.19
						melderwebModel.setMelder(melder);
						break;
					}
				}
				

			}
			if (melderid != null && melder != null ){
				emailWebService.setSubject("Passord");
				passord = adminWebService.decryptMelderPassword(melder); // Tilpasset encrypted passord OLJ 25.01.18
     	    	emailWebService.setEmailText("Ditt passord er: "+passord); // Tilpasset encrypted passord OLJ 25.01.18
    	    	 emailWebService.setMailTo(melder.getMelderepost());
    	    	 emailWebService.sendEmail("");
				meldingsText = "Melding med passord er sendt til oppgitt adresse";
			}
	
	    
		}
	 dataModel.put( meldeTxtId,simple);
	
		//Feil passord går til startside.
 		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/passord.html"));
		Representation pasientkomplikasjonFtl = clres2.get();
		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
				MediaType.TEXT_HTML);
		return templateRep;
    }
    
}
