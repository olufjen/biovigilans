package no.naks.biovigilans_admin.web.server.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.KomplikasjonsklassifikasjonImpl;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Utredning;
import no.naks.biovigilans.model.Vigilansmelding;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Disposition;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import freemarker.template.SimpleScalar;
import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.model.PasientKomplikasjonWebModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;
/**
 * SaksbehandlingServerResourceHTML
 * Denne resursklassen henter frem oversikt over alle mottatte meldinger og gir bruker anledning til å saksbehandle meldingene
 * Klassen sørger også for å velge riktig database for den meldeordningen brukeren har valgt.
 * @author olj
 *
 */
public class SaksbehandlingServerResourceHTML extends SaksbehandlingSessionServer {


	private String utvalg = "";
	private String merknadValg = "";
	private String utvalgKey = "valgt";
	private String merknadKey = "merknader";
	private String merknadlisteKey = "merknadvalgt";


	@Get
	public Representation getHemovigilans() {


	    Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	    Request request = getRequest();
/*
 * Setter opp knytning til valgt database, dersom forskjellig fra hemovigilans
 * Tjenesteklassene setter opp databasekilde til sine DAO klasser
 * Dette gjøres kun her !!	    
 */
		 List<Saksbehandler> saksbehandlere = (List)sessionAdmin.getSessionObject(request,behandlereKey);
	     login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
		String db = login.getSaksbehandler().getDbChoice();
		sessionAdmin.setChosenDB(request,db);
		setDBSource(request);
/*		if (sessionAdmin.getChosenTemplate() != null){
			saksbehandlingWebservice.setAlterativeSource(db);
			annenKomplikasjonWebService.setAlterativeSource(db);
			donasjonWebService.setAlterativeSource(db);
			komplikasjonsklassifikasjonWebService.setAlterativeSource(db);
			komDiagnosegiverWebService.setAlterativeSource(db);
			hendelseWebService.setAlterativeSource(db);
//			melderWebService.setAlterativeSource(db); MeldertableService er i saksbehandlerWebservice !!
			setAlternativeSource(sessionAdmin.getChosenTemplate());

		}
	*/
/*
 * Ferdig alternativeSource		
 */
	    List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(request, meldingsId); // For å vise tidligere valgt liste
	    List<Vigilansmelding> meldingermerknader = (List)sessionAdmin.getSessionObject(request,meldingMerknad); // For å vise meldinger som er under behandling/opplysninger etterspurt OLJ 11.01.17
	    if (meldingermerknader == null){
	    	meldingermerknader = hentmeldingerMerknader();
      	    sessionAdmin.setSessionObject(request, meldingermerknader, meldingMerknad);
	    }
	    if (meldinger == null){
	    	meldinger = hentMeldingene(statusflag[0]);
	    	sortermeldingerMeldt(meldinger); // Sorter listen etter dato meldt
	    	makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering
	    }
	    setsaksbehandlerMerknader(meldinger,meldingermerknader);
	    Map<String, Object> dataModel = new HashMap<String, Object>();
		 sessionAdmin.setSessionObject(request, meldinger, meldingsId);
//	 	 sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey); // Er allerede satt ved dobleKey
	 	 SimpleScalar simpleDisplay = new SimpleScalar(displayPart);
	 	 SimpleScalar simple = new SimpleScalar(utvalg);
	 	 SimpleScalar merk = new SimpleScalar(merknadValg);
	 	 dataModel.put(merknadlisteKey, merk);
	 	 dataModel.put(utvalgKey, simple);

		 dataModel.put(displayKey, simpleDisplay);
	     dataModel.put(meldeKey,meldinger);
 		 dataModel.put(statusflagKey,statusflag);
 		 dataModel.put(merknadKey, merknader);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));
	     
	        // Load the FreeMarker template
	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	 }
	
	  /**
     * storeHemovigilans
     * Denne rutinen tar imot meldingsnøkkel fra bruker og henter frem meldingsinformasjon basert på 
     * oppgitt meldingsnøkkel
     * Bruker kan også velge å endre listeutvalg, eller søke frem en valgt meldingsnøkkel.
     * @param form
     * @return
     */
     @Post
    public Representation storeHemovigilans(Form form) {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        Reference reference = new Reference(getReference(),"..").getTargetRef();
        Request request = getRequest();
        setDBSource(request);
//	     String db =  sessionAdmin.getChosenDB(request);
  	    List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(request, meldingsId);
	    List<Vigilansmelding> meldingermerknader = (List)sessionAdmin.getSessionObject(request,meldingMerknad); // For å vise meldinger som er under behandling/opplysninger etterspurt OLJ 11.01.17
	    if (meldingermerknader == null){
	    	meldingermerknader = hentmeldingerMerknader();
      	    sessionAdmin.setSessionObject(request, meldingermerknader, meldingMerknad);
	    }
  	    dobleMeldingene = (List)sessionAdmin.getSessionObject(request,dobleKey);
  	    login = (LoginModel) sessionAdmin.getSessionObject(request, loginKey);
  	    dataModel.put(meldeKey,meldinger);
  	    dataModel.put(statusflagKey,statusflag);
		 dataModel.put(merknadKey, merknader);
  		 SimpleScalar simpleDisplay = new SimpleScalar(displayPart);
  		 dataModel.put(displayKey, simpleDisplay);
  	    meldingsNokkel = null;
  	    String meldingsID = null;
  	    String utvalget = null;
  		Parameter formValue = form.getFirst("utvalg"); // Bruker ønsker å begrense utvalget
  		Parameter formMerknad = form.getFirst("merknad"); // Bruker ønsker å begrense utvalget i hht merknader
  		Parameter datoMeldt = form.getFirst("datomeldt"); 
  		Parameter datoHendt = form.getFirst("datohendt");
  		Parameter datoHSort   = form.getFirst("sorteringdatohendt");
		Parameter datoMSort   = form.getFirst("sorteringdatomeldt"); 
		Parameter statusSort   = form.getFirst("btnsorteringstatus");
		Parameter mtypeSort   = form.getFirst("btnsorteringmtype");
		Parameter formMinesaker  = form.getFirst("minesaker"); // Bruker etterspør sine saker
		Parameter sokMelding = form.getFirst("meldingsnokkelsok"); // Bruker etterspør en gitt melding
		Parameter formAnonymesaker  = form.getFirst("anonymesaker"); // Bruker etterspør anonyme meldinger
		Parameter tilbake = form.getFirst("tilbake"); // Bruker returnerer til hovedside
  		String meldtUtvalgetstart = null;
  		String meldtUtvalgetslutt = null;
  		boolean toPDF = false;
  		String page = "";
  		if (tilbake != null){
 	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		String backPage = "../hemovigilans/hemovigilansadmin.html";
    		  redirectPermanent(backPage);
    		return templatemapRep;
  		}
  		if (formAnonymesaker != null){ // Henter anonyme meldinger
  			Long saksbehandlerid = login.getSaksbehandler().getSakbehandlerid();
  			meldinger = hentanonymeMeldinger();
//	    	sortermeldingerMeldt(meldinger); // Sorter listen etter dato meldt
	    	makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering
 			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    setsaksbehandlerMerknader(meldinger,meldingermerknader);
 		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  		}
  		if (sokMelding != null){ // Søker etter en gitt melding
  			meldingsID = null;
 			Long saksbehandlerid = login.getSaksbehandler().getSakbehandlerid();
 			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    				
     					if (entry.getName().equals("meldingsnokkel")){
     						meldingsID = entry.getValue();

    	    			}
    			}
    		}
  
    
 			Map<String,List> meldingDetaljene = null;
 			if (meldingsID != null){
 	    		 meldingDetaljene = (Map<String,List>)saksbehandlingWebservice.selectMeldingetternokkel(meldingsID);
 	    	}
 			if (meldingDetaljene != null){
 	    		meldinger = (List) meldingDetaljene.get(meldingsID);
 	    		meldinger = hentMeldingstyper(meldinger);
/*
 * Dersom listen av meldinger nå inneholder meldingstypen Ukjent Sjekk:
 */
 	    		int ct = 0;
 	    		int ix = meldinger.size();
 	    		String rType[] = new String[ix];
 	    		String type = "";
 	    		int[] pt = new int[ix];
 	    		for (Vigilansmelding nestemelding: meldinger){
 	    			rType[ct]= nestemelding.getMeldingstype();
 	    			pt[ct] = ct;
	    			if (rType[ct] != null && !rType[ct].equals("Ukjent")){
 	    				pt[ct] = 0;
 	    				type = rType[ct];
 	    			}
 	    			ct++;
 	    		}
 	    		for (int i : pt){
 	    			if (i != 0)
 	    				meldinger.get(i).setMeldingstype(type);
 	    		}
// =================== 	    		
 			}
 			makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering. Dette blir feil dersom listen over doble ikke er oppdatert
 			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    setsaksbehandlerMerknader(meldinger,meldingermerknader);
 		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;  			
  		}
  		if (formMinesaker != null){ // Søker frem saksbehandlers saker
  			Long saksbehandlerid = login.getSaksbehandler().getSakbehandlerid();
  			meldinger = hentMineMeldinger(saksbehandlerid);
//	    	sortermeldingerMeldt(meldinger); // Sorter listen etter dato meldt
	    	makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering
 			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    setsaksbehandlerMerknader(meldinger,meldingermerknader);
 		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  		}
 		if (datoHendt != null){ // Begrense utvalget til en periode av dato Hendt
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					if (entry.getName().equals("fra-dato") )
    						meldtUtvalgetstart = entry.getValue();
    					if (entry.getName().equals("til-dato") )
    						meldtUtvalgetslutt = entry.getValue();
   
    			}
  
    		}
 			saksbehandlingWebservice.setTimeperiodType(false); // Flagg for dato hendt til false
  			meldinger = hentMeldingene(meldtUtvalgetstart, meldtUtvalgetslutt);
//	    	sortermeldingerMeldt(meldinger); // Sorter listen etter dato meldt
	    	makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering
   		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  		}
  		
  		if (datoMeldt != null){ // Begrense utvalget til en periode av dato meldt
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					if (entry.getName().equals("fra-dato") )
    						meldtUtvalgetstart = entry.getValue();
    					if (entry.getName().equals("til-dato") )
    						meldtUtvalgetslutt = entry.getValue();
    			}
  
    		}
  			saksbehandlingWebservice.setTimeperiodType(true);// Flagg for dato meldt til true
  			meldinger = hentMeldingene(meldtUtvalgetstart, meldtUtvalgetslutt);
//	    	sortermeldingerMeldt(meldinger); // Sorter listen etter dato meldt
	    	makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering
		    setsaksbehandlerMerknader(meldinger,meldingermerknader);
   		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  		}
  		if (formValue != null){ // Begrense utvalget etter status
  			String utvalget2 = "";
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					if (entry.getName().equals("nystatus") )
    						utvalget = entry.getValue();
    					if (utvalget != null && utvalget.equals("Melde Helsedirektoratet")){
    						utvalget2 = "Meldt Helsedirektoratet";
    					}
    			}
    		}
 /*
  * Nullstiller meldingsdetaljer OJN 12.12.16 		
  * Dette sørger for at alle lister blir oppdatert	

  			String db = login.getSaksbehandler().getDbChoice();
  			sessionAdmin.getSession(request,reportAndreKey).invalidate();
 			sessionAdmin.getSession(request,reportPasientKey).invalidate();
 			sessionAdmin.getSession(request,reportGiverKey).invalidate();
 			sessionAdmin.setChosenDB(request,db);
  			setDBSource(request);
 Dette fjerne alle session objekter !!!!		  */  			

/*
 * Nullstiller meldingsdetaljer OJN 12.12.16 		
 * Dette sørger for at alle lister blir oppdatert	
 * Dette kan flyttes til egen rutine, og også brukes når man begrenser utvalget på annen måte? OJN 15.12.16
 */   			
	  		List<Annenkomplikasjon> annenListe =(List) sessionAdmin.getSessionObject(request,reportAndreKey);
		    List<Pasientkomplikasjon> pasientListe = (List)	sessionAdmin.getSessionObject(request,reportPasientKey);	
		    List<Giverkomplikasjon> giverListe = (List)	sessionAdmin.getSessionObject(request,reportGiverKey);
		    annenListe = null;pasientListe = null;giverListe = null;
		    sessionAdmin.setSessionObject(request, annenListe, reportAndreKey);
		    sessionAdmin.setSessionObject(request, pasientListe, reportPasientKey);
		    sessionAdmin.setSessionObject(request, giverListe, reportGiverKey);
		    
  			meldinger = hentMeldingene(utvalget);
 			if (!utvalget2.equals("")){
  				List<Vigilansmelding> fleremeldinger = hentMeldingene(utvalget2);
  				meldinger.addAll(fleremeldinger);
  			}
//	    	sortermeldingerMeldt(meldinger); // Sorter listen etter dato meldt
	    	makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering
		    setsaksbehandlerMerknader(meldinger,meldingermerknader);
  			utvalg = utvalget;
  		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  			
  		}
  	
  		if (formMerknad != null){ // Begrense utvalget etter saksmerknader
  			String utvalget2 = "";
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					if (entry.getName().equals("merkn") )
    						utvalget = entry.getValue();
    					if (utvalget != null && utvalget.equals("Meldt Helsedirektoratet")){
    						utvalget2 = "Melde til Helsedirektoratet";
    					}
    			}
    		}
  			meldinger = hentMeldingMerknader(utvalget);
  			if (!utvalget2.equals("")){
  				List<Vigilansmelding> fleremeldinger = hentMeldingMerknader(utvalget2);
  				meldinger.addAll(fleremeldinger);
  			}
//	    	sortermeldingerMeldt(meldinger); // Sorter listen etter dato meldt
	    	makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering
		    setsaksbehandlerMerknader(meldinger,meldingermerknader);
  			merknadValg = utvalget;
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
   			
  		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  			
  		}
/*
 * Sorteringer  		
 */
  		if (mtypeSort != null){ // Sortering etter meldingstype
  			sorterMeldingermeldingstype(meldinger);
		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
   			
  		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep; 			
  		}
  		if (statusSort != null){ // Sortering etter status
  			sorterMeldingerstatus(meldinger);
		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
   			
  		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep; 			
  		}
  		if (datoHSort != null){ // Sortering dato hendt
  			
  			 sortermeldingerHendelse(meldinger);
  			 
//  			 meldinger.sort((vm1, vm2)->vm2.getDatoforhendelse().compareTo(vm1.getDatoforhendelse()));
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
   			
  		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  		}
  		if (datoMSort != null){ // Sortering dato meldt
  			
 			 sortermeldingerMeldt(meldinger);
 			 
// 			 meldinger.sort((vm1, vm2)->vm2.getDatoforhendelse().compareTo(vm1.getDatoforhendelse()));
 		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
 		 	 dataModel.put(merknadlisteKey, merk);
  			
 		 	SimpleScalar simple = new SimpleScalar(utvalg);
 		 	dataModel.put(utvalgKey, simple);
 			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
 		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
 		    dataModel.put(meldeKey,meldinger);
 	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

   		// Load the FreeMarker template
   		Representation pasientkomplikasjonFtl = clres2.get();

   		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
   				MediaType.TEXT_HTML);
   		return templatemapRep;
 		}  		
  		
    	if (form != null){
    		for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					meldingsID = entry.getValue();
     					if (entry.getName().equals("pdf")){
    	    				page = "../hemovigilans/saksbehandling.html";
    	    				toPDF = true;

    	    			}
    			}
    		}
  
    	}
    	char separator = ',';
    	String mxId = extractString(meldingsID, separator, 0);
    	String mx2Id = extractString(meldingsID, separator,-1);
    	if (mxId != null && mx2Id != null)
    		meldingsID = mxId+mx2Id;
    	Map<String,List> meldingDetaljene = null;
    	Map<String,List> histMeldingsdetaljer = null; // Dette inneholder historiske meldingsdetaljer når flere meldinger har samme nøkkel
    	String histmeldingsNokkel = null;
/*
 * Henter meldingsdetaljer til en valgt melding    	
 */
    	if (meldingsID != null){
    		 meldingDetaljene = (Map<String,List>)saksbehandlingWebservice.selectMeldinger(meldingsID);
    	}
    	 List<Annenkomplikasjon> annenListe = null;
    	 List<Pasientkomplikasjon> pasientListe = null;
    	 List<Giverkomplikasjon> giverListe = null;
    	 List<Vigilansmelding> meldingen = null;
    	 List<Transfusjon> transfusjoner = null;
    	 List<Pasient> pasienter = null;
       	 List<Sykdom> sykdommer = null;
    	 List<Blodprodukt> blodprodukter = null;
    	 List<Produktegenskap> egenskaper = null;
    	 List<Symptomer> symptomer = null;
    	 List<Komplikasjonsklassifikasjon> klassifikasjoner = null;
    	 List<Utredning> utredninger = null;
    	 List<Tiltak> tiltak = null;
    	 List<Forebyggendetiltak> forebyggendeTiltak = null;
    	 
/*
 * Historikk:    	 
 */
       	 List<Annenkomplikasjon> histannenListe = null;
    	 List<Pasientkomplikasjon> histpasientListe = null;
    	 List<Giverkomplikasjon> histgiverListe = null;
    	 List<Transfusjon> histtransfusjoner = null;
    	 List<Pasient> histpasienter = null;
       	 List<Sykdom> histsykdommer = null;
    	 List<Blodprodukt> histblodprodukter = null;
    	 List<Produktegenskap> histegenskaper = null;
    	 List<Symptomer> histsymptomer = null;
    	 List<Komplikasjonsklassifikasjon> histklassifikasjoner = null;
    	 List<Utredning> histutredninger = null;
    	 List<Tiltak> histtiltak = null;
    	 List<Forebyggendetiltak> histforebyggendeTiltak = null;
    	 Pasientkomplikasjon histpasientKomplikasjon = null;
    	 Transfusjon histtransfusjonen = null;
    	 Pasient histpasient = null;
    	 Giver histgiver = null;
/*
 *     	 
 */
    	 Tiltak tiltaket = null;
    	 Forebyggendetiltak forebyggendeTiltaket = null;
    	 Vigilansmelding melding = null;
    	 Annenkomplikasjon annenKomplikasjon = null;
    	 Giverkomplikasjon giverKomplikasjon = null;
    	 Pasientkomplikasjon pasientKomplikasjon = null;
    	 Transfusjon transfusjonen = null;
    	 Pasient pasient = null;
    	 Giver giver = null;
		 Long orgmeldeId = null;
		 Long meldeId = null;
    	if (meldingDetaljene != null){
    		meldingen = (List) meldingDetaljene.get(meldingsID);
    	    annenListe = (List) meldingDetaljene.get(andreKey);
    	    pasientListe = (List) meldingDetaljene.get(pasientKey);
    	    giverListe = (List)  meldingDetaljene.get(giverKey);
    	    if (meldingen != null){
    	    	melding = meldingen.get(0);
    	    	meldingsNokkel = melding.getMeldingsnokkel();
    	    	if (melding.getSjekklistesaksbehandling() == null || melding.getSjekklistesaksbehandling().isEmpty()){
    	    		melding.setSjekklistesaksbehandling("Levert");
    	    	}
    	    	String mKey = melding.getMeldingsnokkel();
    	    	orgmeldeId = getmeldingsNokkelsak(mKey); //Hent meldingsid for første melding med samme meldingsnøkkel
				if (orgmeldeId == null)
					 orgmeldeId = melding.getMeldeid();
				Long mId = melding.getMeldeid();
				String orgMkey = orgmeldeId.toString();
				if (mId.longValue() != orgmeldeId.longValue()){
					histMeldingsdetaljer = (Map<String,List>)saksbehandlingWebservice.selectMeldinger(orgMkey); // Hent historikk
					Vigilansmelding histmelding = (Vigilansmelding) histMeldingsdetaljer.get(orgMkey).get(0);
					histmelding.setMeldingsnokkel(histmelding.getMeldingsnokkel());
					melding.setFormatNokkel(histmelding.getFormatNokkel());
				    histannenListe = (List) histMeldingsdetaljer.get(andreKey);
		    	    histpasientListe = (List) histMeldingsdetaljer.get(pasientKey);
		    	    histgiverListe = (List)  histMeldingsdetaljer.get(giverKey);
				}
    	    }
   		 if (melding != null && melding.getSupplerendeopplysninger() == null){
			 melding.setSupplerendeopplysninger("Ikke angitt");
		 }
    	    if (annenListe != null){
    	    	annenKomplikasjon = annenListe.get(0);
    	    }
    	    if (pasientListe != null){
    	    	pasientKomplikasjon = pasientListe.get(0);
    	    	transfusjoner = (List)meldingDetaljene.get(transfusjonsKey);
    	    	transfusjonen = transfusjoner.get(0);
    	    	pasienter = (List)meldingDetaljene.get(pasientenKey);
    	    	pasient = pasienter.get(0);
    	      	sykdommer = (List)meldingDetaljene.get(sykdomKey);
    	    	blodprodukter = (List)meldingDetaljene.get(blodproduktKey);
    	    	egenskaper = meldingDetaljene.get(produktegenskapKey);
    	    	symptomer = meldingDetaljene.get(symptomerKey);
//    	    	klassifikasjoner = (List)meldingDetaljene.get(klassifikasjonKey); // Lagt til 26.05.16 OLJ
    	    	utredninger = meldingDetaljene.get(utredningKey);
    	    	tiltak = meldingDetaljene.get(tiltakKey);
    	    	forebyggendeTiltak = meldingDetaljene.get(forebyggendetiltakKey);
 
    	    }
    	    if (histannenListe != null){
    	    	histklassifikasjoner = (List)histMeldingsdetaljer.get(klassifikasjonKey);
    	    }
    	    if (histpasientListe != null){
    	    	histpasientKomplikasjon = histpasientListe.get(0);
    	    	histklassifikasjoner = (List)histMeldingsdetaljer.get(klassifikasjonKey);
    	    	histtransfusjoner = (List)histMeldingsdetaljer.get(transfusjonsKey);
    	    	histtransfusjonen = histtransfusjoner.get(0);
    	    	histpasienter = (List)histMeldingsdetaljer.get(pasientenKey);
    	    	histpasient = histpasienter.get(0);
    	      	histsykdommer = (List)histMeldingsdetaljer.get(sykdomKey);
    	    	histblodprodukter = (List)histMeldingsdetaljer.get(blodproduktKey);
    	    	histegenskaper = histMeldingsdetaljer.get(produktegenskapKey);
    	    	histsymptomer = histMeldingsdetaljer.get(symptomerKey);
    	    	histutredninger = histMeldingsdetaljer.get(utredningKey);
    	    	histtiltak = histMeldingsdetaljer.get(tiltakKey);
    	    	histforebyggendeTiltak = histMeldingsdetaljer.get(forebyggendetiltakKey);
    	    }
    	    Sykdom sykdom = null;
    	    if (sykdommer != null && !sykdommer.isEmpty() && sykdommer.size() == 1)
    	    	sykdom = sykdommer.get(0);
    	    if (sykdom != null && sykdom.getSykdomnsnavn().equals("") && histsykdommer != null){
    	    	sykdommer = histsykdommer;
    	    } //Kommentert 14.11.16 OLJ 
    	    if (sykdom == null && histsykdommer != null && sykdommer != null){
    	    	sykdommer.addAll(histsykdommer);
    	    } //Kommentert 14.11.16 OLJ
       	    if (blodprodukter != null && histblodprodukter != null)
    	    	blodprodukter.addAll(histblodprodukter);
    	    if (blodprodukter == null || blodprodukter.isEmpty() && histblodprodukter != null){
    	    	blodprodukter = histblodprodukter;
    	    } //Kommentert 14.11.16 OLJ 
    	    if (egenskaper != null && histegenskaper != null)
    	    	egenskaper.addAll(histegenskaper);
    	    if (egenskaper == null || egenskaper.isEmpty() && histegenskaper != null){
    	    	egenskaper = histegenskaper;
    	    } //Kommentert 14.11.16 OLJ
       	    if (symptomer != null && histsymptomer != null)
    	    	symptomer.addAll(histsymptomer);
    	       	
    	    if (symptomer == null || symptomer.isEmpty() && histsymptomer != null){
    	    	symptomer = histsymptomer;
    	    } //Kommentert 14.11.16 OLJ 
    	    if (utredninger != null && histutredninger != null)
    	    	utredninger.addAll(histutredninger);
    	    if (utredninger == null || utredninger.isEmpty() && histutredninger != null){
    	    	utredninger = histutredninger;
    	    } //Kommentert 14.11.16 OLJ 
/*
 * Klassifikasjoner lagt til 26.05.16    	    
 */
 /*   	    if (klassifikasjoner != null && histklassifikasjoner != null)
    	    	klassifikasjoner.addAll(histklassifikasjoner);
    	    if (klassifikasjoner == null || klassifikasjoner.isEmpty() && histklassifikasjoner != null)
    	    	klassifikasjoner = histklassifikasjoner;
Disse hentes i RapporterAnnenServerResource og RapporterPasientserverResource OLJ 11.11.16
    	    */
    	    if (giverListe != null ){
    	    	giverKomplikasjon = giverListe.get(0);
 
 //   			setMeldingsValues(lokalMelding, melding);
    	  	  setSessionParams(giverSession);
			  setGiverhendelser();
			  setAldergruppe(aldergruppeGiver);
			  Giveroppfolging giveroppfolging = null;
			  Komplikasjonsdiagnosegiver komplikasjonsdiagnoseGiver = null;
    			giver = null;
    			Donasjon donasjonen = null;
    			List givere = meldingDetaljene.get(giverenKey);
    			List donasjoner = meldingDetaljene.get(donasjonKey);
    			List giveroppfolginger = meldingDetaljene.get(giverOppfolgingKey);
    			List komplikasjonsdiagnosergiver = meldingDetaljene.get(giverkomplikasjondiagnoseKey);
    			
    			if (givere != null && !givere.isEmpty()){
    				giver = (Giver)givere.get(0);
    				sessionAdmin.setSessionObject(request, giver,giverenKey);
    			}
    			if (donasjoner != null && !donasjoner.isEmpty()){
    				donasjonen = (Donasjon)donasjoner.get(0);
    				sessionAdmin.setSessionObject(request, donasjonen,donasjonKey);
    			}
    			if (giveroppfolginger != null && !giveroppfolginger.isEmpty()){
    				giveroppfolging = (Giveroppfolging)giveroppfolginger.get(0);
    				sessionAdmin.setSessionObject(request,giveroppfolging, giverOppfolgingKey);
    			}
    			if (komplikasjonsdiagnosergiver != null && !komplikasjonsdiagnosergiver.isEmpty()){
    				komplikasjonsdiagnoseGiver = (Komplikasjonsdiagnosegiver)komplikasjonsdiagnosergiver.get(0);
    				sessionAdmin.setSessionObject(request, komplikasjonsdiagnoseGiver,giverkomplikasjondiagnoseKey);
    			}
    			sessionAdmin.setSessionObject(request, giverKomplikasjon,giverKey);
    			giverModel.setDonasjonen(donasjonen);
    			giverModel.setGiver(giver);
    			giverModel.setGiveroppfolging(giveroppfolging);
    			giverModel.setKomplikasjonsdiagnoseGiver(komplikasjonsdiagnoseGiver);
    			sessionAdmin.setSessionObject(request, giverModel,giverkomplikasjonId);
    	    }
    	    
    	}
		 page =  "../hemovigilans/rapporter_andrehendelser.html";

		  if (annenKomplikasjon != null && melding != null){
			     setAndreHendelser(); // Setter opp andreHendelser session objekter
				    // setTransfusjonsObjects(); 
			    klassifikasjoner = (List)meldingDetaljene.get(klassifikasjonKey);
/*				if (klassifikasjoner == null || klassifikasjoner.isEmpty()){
	    			klassifikasjoner = histklassifikasjoner;
	    		}  OLJ 12.11.16  Henter historikk i rapporterAndreServer Resource*/
			     if (klassifikasjoner != null){
			    	  sessionAdmin.setSessionObject(request, klassifikasjoner,klassifikasjonKey);
			     }
			     Vigilansmelding minmelding = (Vigilansmelding) annenKomplikasjon;
			     minmelding.setMeldingsdato(melding.getMeldingsdato());
			     minmelding.setDatoforhendelse(melding.getDatoforhendelse());
			     minmelding.setDatooppdaget(melding.getDatooppdaget());
			     minmelding.setMeldingsnokkel(melding.getMeldingsnokkel());
			     minmelding.setKladd(melding.getKladd());
				 annenModel.setFormNames(sessionParams);
				 annenModel.distributeTerms();
				 annenModel.setAnnenKomplikasjon(annenKomplikasjon); 
				 annenModel.setVigilansmelding(melding);
			   	 annenModel.setHendelseDato(melding.getMeldingsdato());
		    	 annenModel.setMeldingsNokkel(melding.getMeldingsnokkel());
		    	 klassifikasjoner = (List)sessionAdmin.getSessionObject(getRequest(), klassifikasjonKey);
		    	 if (klassifikasjoner == null){
		    			klassifikasjoner = new ArrayList<Komplikasjonsklassifikasjon>();
		    			sessionAdmin.setSessionObject(request, klassifikasjoner,klassifikasjonKey);
//		    			Komplikasjonsklassifikasjon komplikasjonsklassifikasjon =  new KomplikasjonsklassifikasjonImpl();
//		    			komplikasjonsklassifikasjon.setKlassifikasjon("ukjent");
//		    			komplikasjonsklassifikasjon.setKlassifikasjonsbeskrivelse("ukjent");
//		    			klassifikasjoner.add(komplikasjonsklassifikasjon);
		    	 }
		    	 annenModel.setKlassifikasjoner(klassifikasjoner);

		    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		    	 SimpleScalar simple = new SimpleScalar(utvalg);
			 	 SimpleScalar merk = new SimpleScalar(merknadValg);
			 	 dataModel.put(merknadlisteKey, merk);
		     	 dataModel.put(utvalgKey, simple);
		    	 dataModel.put(displaydateKey, hendelseDate);
				 dataModel.put(andreHendelseId, annenModel);
				 dataModel.put(annenHendelseId, annenKomplikasjon);
			     dataModel.put(klassifikasjonKey, klassifikasjoner);
			     sessionAdmin.setSessionObject(request, klassifikasjoner,klassifikasjonKey);
				 sessionAdmin.setSessionObject(request, annenModel,andreHendelseId);	

	    	}
	    	if(pasientKomplikasjon != null && melding != null){
	    		page =  "../hemovigilans/rapporter_transfusjon.html";
	    		setTransfusjon();
	    		setSessionParams( getPasientSession());
	    		klassifikasjoner = (List)meldingDetaljene.get(klassifikasjonKey);
/*	    		if (klassifikasjoner == null || klassifikasjoner.isEmpty()){
	    			klassifikasjoner = histklassifikasjoner;
	    		} Kommentert bort 11.11.16 OLJ Historikk hentes i HendelseServerResource*/
	    		sessionAdmin.setSessionObject(getRequest(), klassifikasjoner,klassifikasjonKey);
				if (tiltak != null && !tiltak.isEmpty()){
					  tiltaket = tiltak.get(0);
					  result.setTiltak(tiltaket);
					  
				}
/*				if (histtiltak != null && !histtiltak.isEmpty()){
					  tiltak = histtiltak;
					  tiltaket = histtiltak.get(0);
					  result.setTiltak(tiltaket);
					  
				} Kommentert bort 11.11.16 OLJ Historikk hentes i HendelseServerResource*/
				
				if (forebyggendeTiltak != null && !forebyggendeTiltak.isEmpty()){
					  forebyggendeTiltaket = forebyggendeTiltak.get(0);
					  result.setForebyggendeTiltak(forebyggendeTiltaket);
				  }
/*				if (histforebyggendeTiltak != null && !histforebyggendeTiltak.isEmpty()){
					  forebyggendeTiltak = histforebyggendeTiltak;
					  forebyggendeTiltaket = histforebyggendeTiltak.get(0);
					  result.setForebyggendeTiltak(forebyggendeTiltaket);
				  } Kommentert bort 11.11.16 OLJ Historikk hentes i HendelseServerResource*/
								
	   	    	sessionAdmin.setSessionObject(request,transfusjonen, transfusjonsKey);
    	    	sessionAdmin.setSessionObject(request,pasientKomplikasjon, pasientKey);
    	    	sessionAdmin.setSessionObject(request,pasient, pasientenKey);
    	    	sessionAdmin.setSessionObject(request,sykdommer,sykdomKey);
    	    	sessionAdmin.setSessionObject(request,blodprodukter,blodproduktKey);
    	    	sessionAdmin.setSessionObject(request,egenskaper,produktegenskapKey);
    	    	sessionAdmin.setSessionObject(request,symptomer,symptomerKey);
    	    	sessionAdmin.setSessionObject(request,utredninger,utredningKey);
    	    	sessionAdmin.setSessionObject(request,tiltak,tiltakKey);
    	    	sessionAdmin.setSessionObject(request,forebyggendeTiltak,forebyggendetiltakKey);
   			 	result.setFormNames(sessionParams);
   			 	result.setAldergruppe(aldergruppe);
   			 	result.setKjonnValg(kjonnValg);
   			 	result.setblodProducts(blodProdukt);
   			 	result.setHemolyseparams(hemolyseParametre);
   			 	result.setAvdelinger(avdelinger);
 //  			 	result.setnoTerms(koder);
   			 	result.distributeTerms();
   			 	result.setVigilansmelding(melding);
   			 	result.setPasient(pasient);
   			 	result.setMeldingsNokkel(melding.getMeldingsnokkel());
   	
   			 	transfusjon.setVigilansmelding(melding);
   			 	transfusjon.setMeldingsNokkel(melding.getMeldingsnokkel());
   			 	transfusjon.setFormNames(sessionParams);
   			 	transfusjon.setPlasmaEgenskaper(blodProdukt); // Setter plasma produkttyper
   			 	transfusjon.setPasientKomplikasjon(pasientKomplikasjon);
   			 	transfusjon.setTransfusjon(transfusjonen);
	   	     	Vigilansmelding minmelding = (Vigilansmelding) pasientKomplikasjon;
	   	     	minmelding.setMeldingsdato(melding.getMeldingsdato());
	   	     	minmelding.setDatoforhendelse(melding.getDatoforhendelse());
	   	     	minmelding.setDatooppdaget(melding.getDatooppdaget());
	   	     	minmelding.setMeldingsnokkel(melding.getMeldingsnokkel());
	   	     	minmelding.setKladd(melding.getKladd());
	    	    dataModel.put(pasientkomplikasjonId, result);
	    	    dataModel.put(transfusjonId,transfusjon);
	    	    sessionAdmin.setSessionObject(request, result,pasientkomplikasjonId);
	       	    sessionAdmin.setSessionObject(request, transfusjon,transfusjonId);
	    	}
		     if (giverKomplikasjon != null && melding != null){
		    	 setGiverhendelser();
		    	 setSessionParams(getGiverSession()); 
//		    	 Giver giver = (Giver)sessionAdmin.getSessionObject(request,  giverenKey);
		    	 if (melding.getSupplerendeopplysninger() == null || melding.getSupplerendeopplysninger().isEmpty())
		    		 melding.setSupplerendeopplysninger("ikke angitt");
		    	 giverModel.setGiver(giver);
		    	 giverModel.setGiverKomplikasjon(giverKomplikasjon);
		    	 giverModel.setFormNames(sessionParams);
		    	 giverModel.setMeldingsNokkel(melding.getMeldingsnokkel());
		    	 giverModel.setVigilansmelding(melding);
		    	 giverModel.setDonasjonsstedgruppe(donasjonsstedgruppe);
		    	 giverModel.setReaksjonengruppe(reaksjonengruppe);
		    	 giverModel.setUtenforBlodbankengruppe(utenforBlodbankengruppe);
    			Vigilansmelding lokalMelding = (Vigilansmelding) giverKomplikasjon;
      			lokalMelding.setMeldingsdato(melding.getMeldingsdato());
     			lokalMelding.setDatoforhendelse(melding.getDatoforhendelse());
     			lokalMelding.setDatooppdaget(melding.getDatooppdaget());
    			lokalMelding.setMeldingsnokkel(melding.getMeldingsnokkel());
     			lokalMelding.setKladd(melding.getKladd());
//     			sessionAdmin.setSessionObject(request, lokalMelding, meldingsId);  // OBS Feil ID!!
     			 sessionAdmin.setSessionObject(request, giverModel,giverkomplikasjonId);	    			  
				 sessionAdmin.setSessionObject(request,donasjon,donasjonId);
		    	 dataModel.put(giverenKey, giver);
		    	 dataModel.put(giverKey,giverKomplikasjon);
		    	 page = "../hemovigilans/rapporter_giver.html";
		     }	 
			  if (toPDF){
				  setlinkMap();
				  String path = "";
					Representation representation = null;
			   	   try {
			    	    
						path = createPDF();
						
						sessionAdmin.setSessionObject(request,path,"path");
						Response response = getResponse(); 
					
						representation = new FileRepresentation(path, MediaType.APPLICATION_PDF); 
					     
					    Disposition disposition = representation.getDisposition(); 
					    disposition.setType(disposition.TYPE_ATTACHMENT); 
					    disposition.setFilename("leveranse" + ".pdf"); 
					     
					    response.setEntity(representation); 
					   
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   	 return representation;
			  }
			  if (!toPDF)
				  redirectPermanent(page);  
		 

    	
    	
    	
    	
    	
    	
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
              "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
//Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));
	     
	        // Load the FreeMarker template
	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
    
    }

}
