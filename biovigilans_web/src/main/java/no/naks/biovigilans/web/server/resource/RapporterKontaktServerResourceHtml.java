package no.naks.biovigilans.web.server.resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.felles.control.EmailWebService;
import no.naks.biovigilans.felles.control.EmailWebServiceImpl;
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.model.PasientKomplikasjonWebModel;
import no.naks.biovigilans.felles.model.TransfusjonKvitteringWebModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.AnnenKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.DonasjonwebModel;
import no.naks.biovigilans.felles.xml.Letter;
import no.naks.biovigilans.felles.xml.MainTerm;

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

/**
 *  Denne resursen er knyttet til kontaktsiden. Her rapporterer man kontaktinformasjon
 * @author olj
 * 
 */
public class RapporterKontaktServerResourceHtml extends SessionServerResource {
	/**
	 * Epost adresse som blir benyttet når bruker velger å melde anonymt
	 */
	private String anonymEpost = "hemovigilans@helsedir.no";
	/**
	 * Inneholder navn på alle helseregioner
	 */
	private String[] helseRegioner;
	private String[] hfeneNord;
	/**
	 * Inneholder navn på alle foretakene i en helseregion
	 */
	private String[]hfeneMidt;
	private String[]hfeneVest;
	private String[]hfeneSor;

	private String[]sykehusHFfinnmark;
	private String[]sykehusHFNord;
	private String[]sykehusHFnordland;
	private String[]sykehusHFHelgeland;
	private String[]sykehusHFNtrond;
	private String[]sykehusHFOlav;
	private String[]sykehusHFMRoms;
	private String[]sykehusHFForde;
	private String[]sykehusHFbergen;
	private String[]sykehusHFFonna;
	private String[]sykehusHFstav;
	private String[]sykehusHFVpriv;
	private String[]sykehusHFsorland;
	private String[]sykehusHFtele;
	private String[]sykehusHFvestf;
	private String[]sykehusHFvviken;
	private String[]sykehusHFostf;
	private String[]sykehusHFoslo;
	/**
	 * Inneholder navn på alle sykehus i et foretak
	 */
	private String[]sykehusHFahus;
	private String[]sykehusHFinnl;
	private String[]sykehusHFOpriv;
	/**
	 * Benyttes dersom melder er funnet, men bruker har oppgitt feil passord 
	 */
	private String foundKey = "funnet"; //Benyttes dersom melder er funnet, men bruker har oppgitt feil passord
	private String extraPassordKey = "extra";
	private String enableLagre = "lagre"; // Benyttes til å enable lagre knapp på kontaktskjema
	private String genPWId = "passwordID"; // Session ID for generert passord
	
	public RapporterKontaktServerResourceHtml() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	
	public String getFoundKey() {
		return foundKey;
	}




	public void setFoundKey(String foundKey) {
		this.foundKey = foundKey;
	}




	public String[] getSykehusHFfinnmark() {
		return sykehusHFfinnmark;
	}




	public void setSykehusHFfinnmark(String[] sykehusHFfinnmark) {
		this.sykehusHFfinnmark = sykehusHFfinnmark;
	}




	public String[] getSykehusHFNord() {
		return sykehusHFNord;
	}




	public void setSykehusHFNord(String[] sykehusHFNord) {
		this.sykehusHFNord = sykehusHFNord;
	}




	public String[] getSykehusHFnordland() {
		return sykehusHFnordland;
	}




	public void setSykehusHFnordland(String[] sykehusHFnordland) {
		this.sykehusHFnordland = sykehusHFnordland;
	}




	public String[] getSykehusHFHelgeland() {
		return sykehusHFHelgeland;
	}




	public void setSykehusHFHelgeland(String[] sykehusHFHelgeland) {
		this.sykehusHFHelgeland = sykehusHFHelgeland;
	}




	public String[] getSykehusHFNtrond() {
		return sykehusHFNtrond;
	}




	public void setSykehusHFNtrond(String[] sykehusHFNtrond) {
		this.sykehusHFNtrond = sykehusHFNtrond;
	}




	public String[] getSykehusHFOlav() {
		return sykehusHFOlav;
	}




	public void setSykehusHFOlav(String[] sykehusHFOlav) {
		this.sykehusHFOlav = sykehusHFOlav;
	}




	public String[] getSykehusHFMRoms() {
		return sykehusHFMRoms;
	}




	public void setSykehusHFMRoms(String[] sykehusHFMRoms) {
		this.sykehusHFMRoms = sykehusHFMRoms;
	}




	public String[] getSykehusHFForde() {
		return sykehusHFForde;
	}




	public void setSykehusHFForde(String[] sykehusHFForde) {
		this.sykehusHFForde = sykehusHFForde;
	}




	public String[] getSykehusHFbergen() {
		return sykehusHFbergen;
	}




	public void setSykehusHFbergen(String[] sykehusHFbergen) {
		this.sykehusHFbergen = sykehusHFbergen;
	}




	public String[] getSykehusHFFonna() {
		return sykehusHFFonna;
	}




	public void setSykehusHFFonna(String[] sykehusHFFonna) {
		this.sykehusHFFonna = sykehusHFFonna;
	}




	public String[] getSykehusHFstav() {
		return sykehusHFstav;
	}




	public void setSykehusHFstav(String[] sykehusHFstav) {
		this.sykehusHFstav = sykehusHFstav;
	}




	public String[] getSykehusHFVpriv() {
		return sykehusHFVpriv;
	}




	public void setSykehusHFVpriv(String[] sykehusHFVpriv) {
		this.sykehusHFVpriv = sykehusHFVpriv;
	}




	public String[] getSykehusHFsorland() {
		return sykehusHFsorland;
	}




	public void setSykehusHFsorland(String[] sykehusHFsorland) {
		this.sykehusHFsorland = sykehusHFsorland;
	}




	public String[] getSykehusHFtele() {
		return sykehusHFtele;
	}




	public void setSykehusHFtele(String[] sykehusHFtele) {
		this.sykehusHFtele = sykehusHFtele;
	}




	public String[] getSykehusHFvestf() {
		return sykehusHFvestf;
	}




	public void setSykehusHFvestf(String[] sykehusHFvestf) {
		this.sykehusHFvestf = sykehusHFvestf;
	}




	public String[] getSykehusHFvviken() {
		return sykehusHFvviken;
	}




	public void setSykehusHFvviken(String[] sykehusHFvviken) {
		this.sykehusHFvviken = sykehusHFvviken;
	}




	public String[] getSykehusHFostf() {
		return sykehusHFostf;
	}




	public void setSykehusHFostf(String[] sykehusHFostf) {
		this.sykehusHFostf = sykehusHFostf;
	}




	public String[] getSykehusHFoslo() {
		return sykehusHFoslo;
	}




	public void setSykehusHFoslo(String[] sykehusHFoslo) {
		this.sykehusHFoslo = sykehusHFoslo;
	}




	public String[] getSykehusHFahus() {
		return sykehusHFahus;
	}




	public void setSykehusHFahus(String[] sykehusHFahus) {
		this.sykehusHFahus = sykehusHFahus;
	}




	public String[] getSykehusHFinnl() {
		return sykehusHFinnl;
	}




	public void setSykehusHFinnl(String[] sykehusHFinnl) {
		this.sykehusHFinnl = sykehusHFinnl;
	}




	public String[] getSykehusHFOpriv() {
		return sykehusHFOpriv;
	}




	public void setSykehusHFOpriv(String[] sykehusHFOpriv) {
		this.sykehusHFOpriv = sykehusHFOpriv;
	}




	public String[] getHelseRegioner() {
		return helseRegioner;
	}


	public void setHelseRegioner(String[] helseRegioner) {
		this.helseRegioner = helseRegioner;
	}


	public String[] getHfeneNord() {
		return hfeneNord;
	}


	public void setHfeneNord(String[] hfeneNord) {
		this.hfeneNord = hfeneNord;
	}


	public String[] getHfeneMidt() {
		return hfeneMidt;
	}


	public void setHfeneMidt(String[] hfeneMidt) {
		this.hfeneMidt = hfeneMidt;
	}


	public String[] getHfeneVest() {
		return hfeneVest;
	}


	public void setHfeneVest(String[] hfeneVest) {
		this.hfeneVest = hfeneVest;
	}


	public String[] getHfeneSor() {
		return hfeneSor;
	}


	public void setHfeneSor(String[] hfeneSor) {
		this.hfeneSor = hfeneSor;
	}


	/**
	 * getHemovigilans
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * kontaktinformasjon
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();


	     setTransfusjonsObjects();
    	 melderwebModel.setFormNames(sessionParams);
    	 setMelderparams();
    	 melderwebModel.distributeTerms();
     	 String result = ""; // Inneholder generert passord, dersom bruker har endret sitt passord, og kommer tilbake for å lagre skjema

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
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	     melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);
	     
			transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(request,transfusjonId);
    		giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(request,giverkomplikasjonId);
    		annenModel = (AnnenKomplikasjonwebModel) sessionAdmin.getSessionObject(request,andreHendelseId);
    		donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(request,donasjonId);
    		String buttonValue = "disable";
/*
 * Sjekker om melder kommer fra "endre passord" funksjonen  OLJ 07.11.18
*/
    		result = (String) sessionAdmin.getSessionObject(request, genPWId);
    		boolean found = false;
    		String encryptedPasswd = melderwebModel.getMelder().getMelderPassord();
    		if (result != null && !result.isEmpty()){
    			 Melder melder = (Melder)sessionAdmin.getSessionObject(request,melderNokkel);
    			String epost = melder.getMelderepost();

    			String passord = adminWebService.decryptMelderPassword(melder);
    			melder.setMelderPassord(passord);
    			List<Melder> rows = melderWebService.selectMelder(epost); // Korrekt kall 
//    			List<Map<String, Object>> rows = melderWebService.selectMelder(epost);
    			found = melderwebModel.kontaktValues( rows); // Found er true dersom riktig oppgitt passord og melder finnes fra før
    			buttonValue = "enable";
    			melder.setMelderPassord(encryptedPasswd);
    			dataModel.put(melderId, melderwebModel);
    		}    		
/*
 * En Hashmap benyttes dersom en html side henter data fra flere javaklasser.	
 * Hver javaklasse får en id (ex pasientkomplikasjonId) som er tilgjengelig for html
 *      
*/	     
       	 displayPart = "none";
       	 String displayFound = "none";
     	 String extrapwd = "none";
       	 SimpleScalar melderFound = new SimpleScalar(displayFound);
      	 SimpleScalar simple = new SimpleScalar(displayPart);
       	 SimpleScalar extraPassord = new SimpleScalar(extrapwd);
     
		SimpleScalar lagreButton = new SimpleScalar(buttonValue);
		dataModel.put(enableLagre, lagreButton);
     	 dataModel.put(displayKey, simple);
     	 dataModel.put(foundKey,  melderFound);
      	 dataModel.put(extraPassordKey,extraPassord);
	     dataModel.put(pasientkomplikasjonId, result);
	     dataModel.put(transfusjonId,transfusjon);
	     dataModel.put(kvitteringsId,kvittering);
     
//=======================	     

	     dataModel.put(melderId, melderwebModel);
	     sessionAdmin.setSessionObject(request, melderwebModel,melderId);
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
     * Denne rutinen tar imot alle ny informasjon fra brukers kontaktinformasjon
     * @param form
     * @return
     */
    /**
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	  Request request = getRequest();
    	TemplateRepresentation  templateRep = null;
        String meldingsNokkel = null;
	     String datoLevert = null;
//	   	 displayPart = "none";
//      	 SimpleScalar simple = new SimpleScalar(displayPart);
    	if(form == null){
    		invalidateSessionobjects();
    	}
    	GiverKomplikasjonwebModel model = null;
 
    	boolean found = false;
    	String displayFound = "none";
    	String extrapwd = "none";
    	String result = "";
    	if (form != null){
    		melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);
    		transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(request,transfusjonId);
    		giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(request,giverkomplikasjonId);
    		annenModel = (AnnenKomplikasjonwebModel) sessionAdmin.getSessionObject(request,andreHendelseId);
    		donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(request,donasjonId);
    		Parameter logout = form.getFirst("avbrytkontakt");
    		Parameter lukk = form.getFirst("lukkkontakt");
  
    	     Map<String, Object> dataModel = new HashMap<String, Object>();
    	     String nokkel = "";
    	     if (annenModel != null)
    	    	 nokkel = annenModel.getMeldingsNokkel();
    	     if (giverModel != null)
    	    	 nokkel = giverModel.getMeldingsNokkel();
    	     if (transfusjon != null)
    	    	 nokkel = transfusjon.getMeldingsNokkel();
    		if (logout != null || lukk != null){
    			invalidateSessionobjects();
    			
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemivigilans/Logout.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
	    				MediaType.TEXT_HTML);
    			return templateRep; // return a new page!!!
    		}
    			
    		 if(melderwebModel == null){
    	    	 melderwebModel = new MelderwebModel();
    	    	 melderwebModel.setFormNames(sessionParams);
    	    	 setMelderparams();
    	    	 melderwebModel.distributeTerms();
    	     }
 
    		for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals("")))
    					System.out.println(entry.getName() + "=" + entry.getValue());
    			melderwebModel.setValues(entry);
    		}

    		sessionAdmin.setSessionObject(request, melderwebModel, melderId);
    		dataModel.put(melderId, melderwebModel);
    		
    		Parameter formValue = form.getFirst("formValue"); 		// Bruker har oppgitt epost og password
    		Parameter lagre = form.getFirst("lagrekontakt"); 		// Bruker ønsker å lagre informasjonen
    		Parameter lagreAnonymt = form.getFirst("lagreanonymt"); // Bruker ønsker å melde anonymt
    		Parameter valgtRegion = form.getFirst("regionValue");   //Bruker har valgt region
    		Parameter valgtForetak = form.getFirst("foretakValue"); //Bruker har valgt HF
    		Parameter nyttPassord = form.getFirst("nyttpassord");   // Bruker har glemt passord
    		Parameter glemPassord = form.getFirst("glemtpass");   // Bruker har glemt passord: Bruker ny funksjon (22.10.18) for å få tilsendt engangspassord
    		Parameter provNy = form.getFirst("provnytt");
    		Parameter hentPassord = form.getFirst("hentpassord"); // Sender passord til bruker OLJ 22.10.18: Dette endres til engangspassord
    		Parameter nymelder = form.getFirst("nymelder");			// Bruker ønsker å registrere ny melder til en eksisterende epost adresse
    		String genPW = "";
 
    		if(lagre != null){								// Lagre kontaktskjema
    			melderwebModel.saveValues();
/*
 * Encrypt passord for ny melder OLJ 23.01.18    			
 */
    			adminWebService.encryptMelderpassord(melderwebModel.getMelder());
    			melderWebService.saveMelder(melderwebModel);
    			SaveSkjema();
    			sessionAdmin.setSessionObject(request, melderwebModel, melderId);
        		dataModel.put(melderId, melderwebModel);
        		String page = getPage();
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));
	    		Representation pasientkomplikasjonFtl = clres2.get();
/*
 * Invalidate session objects kun dersom et meldeskjema er fylt ut	 
 * Dette er nå irrelevant Dette skjer i leveranseskjema olj 23.02.15   		
 
	    		if (checkSavedModel()){
	    			invalidateSessionobjects();
	    		}
*/	    		
//	    		sessionAdmin.getSession(getRequest(),melderId).invalidate();
/*
 */
/*
 * Disse verdiene må settes opp for å unngå feilemelding i kvitteringsskjema !!	    		
 */
	     	    SimpleScalar simple = new SimpleScalar(meldingsNokkel);
	    	    dataModel.put(nokkelId,simple);
	    	    SimpleScalar datoSimple = new SimpleScalar(datoLevert);
	    	    dataModel.put(datoId, datoSimple);
/*	    FLYTTET TIL Leveranse !!! OLJ 13.03.15		
	    	    String melderEpost = melderwebModel.getMelder().getMelderepost();
	    	    if(melderEpost != null || !melderEpost.equals("")){
	    	    	 emailWebService.setMailTo(melderEpost);
	    	    	 emailWebService.sendEmail(nokkel);
	    	    }
	    	   
*/	    	    
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
	    				MediaType.TEXT_HTML);
	    		redirectPermanent("../hemovigilans/leveranse.html");
	    		return templateRep;
    		}else if(glemPassord != null){// Bruker har glemt passord: Bruker ny funksjon (22.10.18) for å få tilsendt engangspassord
    			emailWebService.setSubject("Passord");
     			String melder_epost = (String) melderwebModel.getFormMap().get("k-epost");
     			if (melder_epost == null){
    	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
    	    		Representation pasientkomplikasjonFtl = clres2.get();
    	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
    	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
    	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
    			    				MediaType.TEXT_HTML);
    	    		return templateRep;
     			}
     			List<Melder> rows = melderWebService.selectMelder(melder_epost);
//				List<Map<String, Object>> rows = melderWebService.selectMelder(melder_epost);
				String name = "";
				Long melderid = null;
				if(rows != null && rows.size() > 0){
/*
* Tilpasset kryptering OLJ 22.01.18						
*/
					String passord = "";
					
//					Long melderid = null;
			   		String epost = "";
					for(Melder rowmelder :rows){
						melderid = rowmelder.getMelderId();
						name = rowmelder.getMeldernavn();
						passord = rowmelder.getMelderPassord();
						epost = rowmelder.getMelderepost();
						melderwebModel.setMelder(rowmelder);//Added 30.05.19
					}
/*					for(Map row:rows){
						melderid = Long.parseLong(row.get("melderid").toString());
						if (row.get("meldernavn") != null)
							name = row.get("meldernavn").toString();
						if (row.get("melderpassord") != null)
							passord = row.get("melderpassord").toString();
//						row.put(arg0, arg1)
					}*/

				}
    			Melder melder = melderwebModel.getMelder();
/*				melder.setMeldernavn(name); Dette er unødvendig etter endring over (724) OLJ 30.05.19
				melder.setMelderId(melderid);
    			if (melder_epost != null && (melder.getMelderepost() == null || melder.getMelderepost().isEmpty())){
    				melder.setMelderepost(melder_epost);
    			}*/
    			sessionAdmin.setSessionObject(request,melder,melderNokkel);
    			result = RandomStringUtils.randomAlphabetic(16);
    			sessionAdmin.setSessionObject(request,result,genPWId);
    			emailWebService.setEmailText("Ditt engangspassord er: "+result+ " Du må nå oppgi dette passordet og deretter endre ditt passord.");
    			emailWebService.setMailTo(melder.getMelderepost());
    			emailWebService.sendEmail("");
/*
 * Legge til funksjon her for å lagre melding: 
 *     			melderWebService.saveMelder(melderwebModel);
    			SaveSkjema();
    			sessionAdmin.setSessionObject(request, melderwebModel, melderId);
        		dataModel.put(melderId, melderwebModel);
 */
    			String page = "../hemovigilans/tilsendtpassord.html";
   		     	ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));
   		     	Representation pasientkomplikasjonFtl = clres2.get();
   		        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
   		                MediaType.TEXT_HTML);
   				redirectPermanent(page);
   			 return templatemapRep;	
    		}else if(hentPassord != null){
    			Melder melder = melderwebModel.getMelder();
    			Long melderid = melder.getMelderId();
    			String meldingsText = "";
    			if (melderid != null && melder != null ){
    				emailWebService.setSubject("Passord");
         	    	emailWebService.setEmailText("Ditt passord er: "+melder.getMelderPassord()); // Dersom flere kontakter med samme epost !!!
        	    	 emailWebService.setMailTo(melder.getMelderepost());
        	    	 emailWebService.sendEmail("");
    				meldingsText = "Melding med passord er sendt til oppgitt adresse";
    			}
    			displayFound = "none";
    	     	String buttonValue = "disable";
				SimpleScalar lagreButton = new SimpleScalar(buttonValue);
				dataModel.put(enableLagre, lagreButton);
		      	 SimpleScalar simpledisp = new SimpleScalar(displayPart);
		     	SimpleScalar melderFound = new SimpleScalar(displayFound);
		    	SimpleScalar extraPassord = new SimpleScalar(extrapwd);
		      	 dataModel.put(extraPassordKey,extraPassord);
			   	 dataModel.put(displayKey, simpledisp);
			   	 dataModel.put(foundKey,  melderFound);
				dataModel.put(melderId, melderwebModel);
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
			    				MediaType.TEXT_HTML);   
	    		return templateRep;
    		}else if(provNy != null){ // Bruker oppgitt feil passord og prøver på nytt
    			melderwebModel.emptyMelder();
    			String buttonValue = "disable";
				SimpleScalar lagreButton = new SimpleScalar(buttonValue);
				dataModel.put(enableLagre, lagreButton);
    			displayFound = "none";
		      	 SimpleScalar simpledisp = new SimpleScalar(displayPart);
		     	SimpleScalar melderFound = new SimpleScalar(displayFound);
		    	SimpleScalar extraPassord = new SimpleScalar(extrapwd);
		      	 dataModel.put(extraPassordKey,extraPassord);
			   	 dataModel.put(displayKey, simpledisp);
			   	 dataModel.put(foundKey,  melderFound);
				dataModel.put(melderId, melderwebModel);
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
			    				MediaType.TEXT_HTML);
	    		return templateRep;
	    		
    		}else if(nymelder != null){ // Bruker ønsker å registrere ny melder til en eksisterende epost adresse
    			//melderwebModel.saveValues();
    			String pwd = melderwebModel.getNyPassord();
    			melderwebModel.emptyMelder();
    			melderwebModel.getMelder().setMelderPassord(pwd);
    			displayFound = "none";
    			extrapwd = "block";
    			String buttonValue = "disable";
				SimpleScalar lagreButton = new SimpleScalar(buttonValue);
				dataModel.put(enableLagre, lagreButton);
		      	 SimpleScalar simpledisp = new SimpleScalar(displayPart);
		     	SimpleScalar melderFound = new SimpleScalar(displayFound);
		    	SimpleScalar extraPassord = new SimpleScalar(extrapwd);
		      	 dataModel.put(extraPassordKey,extraPassord);
			   	 dataModel.put(displayKey, simpledisp);
			   	 dataModel.put(foundKey,  melderFound);
				dataModel.put(melderId, melderwebModel);
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
			    				MediaType.TEXT_HTML);
	    		return templateRep;
    		}else if(formValue != null){					// Sjekk epostadresse og passord oppgitt
				String epost = melderwebModel.getMelderepost();
		     	String buttonValue = "disable";
				boolean newMelder = true;
				if(!epost.equalsIgnoreCase("")){
					List<Melder> rows = melderWebService.selectMelder(epost);
//					List<Map<String, Object>> rows = melderWebService.selectMelder(epost);
					if(rows != null && rows.size() > 0){
/*
 * Tilpasset kryptering OLJ 22.01.18						
 */
						String passord = "";
						String name = "";
						Long melderid = null;
						for(Melder row:rows){
							melderid = row.getMelderId();
							if (row.getMeldernavn() != null)
								name = row.getMeldernavn();
							if (row.getMelderPassord() != null)
								passord = row.getMelderPassord();
//							row.put(arg0, arg1)
						}
						String encryptedPW = new String(passord);
						Melder melder = melderwebModel.getMelder();
						melder.setMelderId(melderid);
						melder.setMeldernavn(name);
						melder.setMelderPassord(passord);
						melder.setMelderepost(epost);
// Sjekker styrken på passord og setter flagg OLJ Mars 2018						
						boolean pwstrength = adminWebService.checkStrenghtPassword(melder);
						melder.setPwStrength(pwstrength);
						passord = adminWebService.decryptMelderPassword(melder);
						melder.setMelderPassord(passord);
						
/*
 * Tilpasset kryptering END						
 */
						found = melderwebModel.kontaktValues( rows); // Found er true dersom riktig oppgitt passord og melder finnes fra før
						if (found){
							melderwebModel.saveValues();
							melder.setMelderPassord(encryptedPW); // Tilpasset kryptering END	
						}
							
						newMelder = false;
						buttonValue = "enable";
			    	}
				
				}
				if (newMelder)
					extrapwd = "block";
				// Denne client resource forholder seg til src/main/resource katalogen !!!	
//			   	 displayPart = "block";
//			   	 simple = null;
//		      	simple = new SimpleScalar(displayPart);
//		      	 dataModel.put(displayKey, simple);
				if (!found && !newMelder){
					displayFound = "block";
				}
			   	 displayPart = "block";
		      	 SimpleScalar simpledisp = new SimpleScalar(displayPart);
		     	SimpleScalar melderFound = new SimpleScalar(displayFound);
		     	SimpleScalar extraPassord = new SimpleScalar(extrapwd);
		
				SimpleScalar lagreButton = new SimpleScalar(buttonValue);
				dataModel.put(enableLagre, lagreButton);
			   	 dataModel.put(displayKey, simpledisp);
			   	 dataModel.put(foundKey,  melderFound);
			   	 dataModel.put(extraPassordKey,extraPassord);
				dataModel.put(melderId, melderwebModel);
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
			    				MediaType.TEXT_HTML);
	    		return templateRep;
    		}else if(valgtRegion != null){   // Bruker har valgt en helseregion
    			melderwebModel.setValgtregion();
    			melderwebModel.saveValues();
			   	 displayPart = "block";
			  	String buttonValue = "disable";
			 	SimpleScalar lagreButton = new SimpleScalar(buttonValue);
				dataModel.put(enableLagre, lagreButton);
				
		      	 SimpleScalar simpledisp = new SimpleScalar(displayPart);
			   	SimpleScalar extraPassord = new SimpleScalar(extrapwd);
			   	 dataModel.put(extraPassordKey,extraPassord);
			  	 SimpleScalar melderFound = new SimpleScalar(displayFound);
			 	 dataModel.put(foundKey,  melderFound);
			   	 dataModel.put(displayKey, simpledisp);
			   	dataModel.put(melderId, melderwebModel);
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
			    				MediaType.TEXT_HTML);    
	    		return templateRep;
    	
       		}else if(valgtForetak != null){   // Bruker har valgt et HF
       			melderwebModel.setValgtsykehus();
       			melderwebModel.saveValues();
			   	 displayPart = "block";
				String buttonValue = "disable";
				SimpleScalar lagreButton = new SimpleScalar(buttonValue);
				dataModel.put(enableLagre, lagreButton);
		      	SimpleScalar simpledisp = new SimpleScalar(displayPart);
			    SimpleScalar extraPassord = new SimpleScalar(extrapwd);
			    dataModel.put(extraPassordKey,extraPassord);
			   	dataModel.put(displayKey, simpledisp);
				SimpleScalar melderFound = new SimpleScalar(displayFound);
		 	 	dataModel.put(foundKey,  melderFound);
			   	dataModel.put(melderId, melderwebModel);
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
			    				MediaType.TEXT_HTML); 
	    		return templateRep;
    	
    	 	}else if(lagreAnonymt != null){					// Bruker velger å lagre skjema anonymt.
    	 		boolean ikkeAnonym = checkMelder();
    	 		if (ikkeAnonym){ // Bruker har brukt returtasten eller har et forhåndsutfylt epost felt!!
    	 			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
    	    		Representation pasientkomplikasjonFtl = clres2.get();
    	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
    	    				MediaType.TEXT_HTML);
    	     		return templateRep;
    	    		
    	 		}
    			melderwebModel.setMelderepost(anonymEpost);
    			melderwebModel.setAnonymEpost(anonymEpost);
    			String epost = melderwebModel.getMelderepost();
    			if(!epost.equalsIgnoreCase("")){
					List<Melder> rows = melderWebService.selectMelder(epost);
					if(rows != null && rows.size() > 0){
						melderwebModel.kontaktValues( rows);
						melderwebModel.saveAnonym();
//						melderwebModel.saveValues();
						
						//sessionAdmin.getSession(getRequest(),melderId).invalidate();
			    	}else{
						melderwebModel.setMelderepost(anonymEpost);
						melderwebModel.saveAnonym();
					}
			    		
				}else{
					melderwebModel.setMelderepost(anonymEpost);
					melderwebModel.saveAnonym();
				}
    			melderWebService.saveMelder(melderwebModel);
				SaveSkjema();
    			sessionAdmin.setSessionObject(request, melderwebModel, melderId);
    		   	 displayPart = "block";
 				String buttonValue = "disable";
 				SimpleScalar lagreButton = new SimpleScalar(buttonValue);
 				dataModel.put(enableLagre, lagreButton);
 		      	SimpleScalar simpledisp = new SimpleScalar(displayPart);
 			    SimpleScalar extraPassord = new SimpleScalar(extrapwd);
 			    dataModel.put(extraPassordKey,extraPassord);
 			   	dataModel.put(displayKey, simpledisp);
 				SimpleScalar melderFound = new SimpleScalar(displayFound);
 		 	 	dataModel.put(foundKey,  melderFound);
 			   	dataModel.put(melderId, melderwebModel);
    		   	 dataModel.put(displayKey, simpledisp);
        		String page = getPage(); //Hent neste side 
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
/*
 * Invalidate session objects kun dersom et meldeskjema er fylt ut	
 * For å Ta melding ut til PDF i kvitteringsskjema så kan vi ikke nullstille session !! OLJ 12.03.15    		

	    		if (checkSavedModel()){
	    			invalidateSessionobjects();
	    		}
	    		
 */	  
	     	    SimpleScalar simple = new SimpleScalar(meldingsNokkel);
	    	    dataModel.put(nokkelId,simple);
	    	    SimpleScalar datoSimple = new SimpleScalar(datoLevert);
	    	    dataModel.put(datoId, datoSimple);
	    	    	    	    
//	    		sessionAdmin.getSession(getRequest(),melderId).invalidate();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
	    				MediaType.TEXT_HTML);
	    		redirectPermanent("../hemovigilans/leveranse.html");
	    		return templateRep;
    	 	}else if (nyttPassord != null){
    	 		redirectPermanent("../hemovigilans/passord.html");
    		}else{

    			sessionAdmin.setSessionObject(request, melderwebModel, melderId);
        		dataModel.put(melderId, melderwebModel);
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
    			invalidateSessionobjects();
    			Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
			    				MediaType.TEXT_HTML);
    		}
    	}
 
    	
    	return templateRep;
      
    }
    /**
     * setMelderparams
     * Denne rutinen setter opp alle helseregioner og HF etc for kontaktperson
     */
    private void setMelderparams(){
     	 melderwebModel.setHelseRegioner(helseRegioner);
    	 melderwebModel.setHfeneNord(hfeneNord);
    	 melderwebModel.setHfeneMidt(hfeneMidt);
    	 melderwebModel.setHfeneVest(hfeneVest);
    	 melderwebModel.setHfeneSor(hfeneSor);
    	 melderwebModel.setSykehusHFahus(sykehusHFahus);
    	 melderwebModel.setSykehusHFbergen(sykehusHFbergen);
    	 melderwebModel.setSykehusHFfinnmark(sykehusHFfinnmark);
    	 melderwebModel.setSykehusHFFonna(sykehusHFFonna);
    	 melderwebModel.setSykehusHFForde(sykehusHFForde);
    	 melderwebModel.setSykehusHFHelgeland(sykehusHFHelgeland);
    	 melderwebModel.setSykehusHFinnl(sykehusHFinnl);
    	 melderwebModel.setSykehusHFMRoms(sykehusHFMRoms);
    	 melderwebModel.setSykehusHFNord(sykehusHFNord);
    	 melderwebModel.setSykehusHFnordland(sykehusHFnordland);
    	 melderwebModel.setSykehusHFNtrond(sykehusHFNtrond);
    	 melderwebModel.setSykehusHFOlav(sykehusHFOlav);
    	 melderwebModel.setSykehusHFOpriv(sykehusHFOpriv);
    	 melderwebModel.setSykehusHFoslo(sykehusHFoslo);
    	 melderwebModel.setSykehusHFostf(sykehusHFostf);
    	 melderwebModel.setSykehusHFsorland(sykehusHFsorland);
    	 melderwebModel.setSykehusHFstav(sykehusHFstav);
    	 melderwebModel.setSykehusHFtele(sykehusHFtele);
    	 melderwebModel.setSykehusHFvestf(sykehusHFvestf);
    	 melderwebModel.setSykehusHFVpriv(sykehusHFVpriv);
    	 melderwebModel.setSykehusHFvviken(sykehusHFvviken);
    }
    /**
     * checkMelder
     * Denne rutinen sjekker om melderinformasjon er fylt ut ved en anonym melding.
     * @return
     */
    private boolean checkMelder(){
    	String lokalnavn = melderwebModel.getMeldernavn();
    	String lokalepost = melderwebModel.getMelderepost();
    	if (lokalepost != null && !lokalepost.isEmpty()){
    		melderwebModel.setMeldernavn("");
    		melderwebModel.setMelderepost("");
 //   		return true; Dette er kommentert bort olj 16.03.16
    	}
    	return false;
    }
}
