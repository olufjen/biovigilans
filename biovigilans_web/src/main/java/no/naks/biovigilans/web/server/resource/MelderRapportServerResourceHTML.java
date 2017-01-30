package no.naks.biovigilans.web.server.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.biovigilans.model.KomplikasjonsdiagnosegiverImpl;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Utredning;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.model.VigilansModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;

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

/**
 * Denne resource henter frem alle meldinger knyttet til en melder.
 * Den er knyttet til siden melder_rapport
 * @author olj
 *
 */
public class MelderRapportServerResourceHTML extends SessionServerResource {

	private String meldeKey = "meldinger";
	private String[] pasientSession;
	private String[] giverSession;
	private String[] annenSession; 
	private String[] aldergruppeGiver;
	private String meldeTxtId = "melding";
	private String utvalg = "";
	private String merknadValg = "";
	private String utvalgKey = "valgt";
	private String merknadlisteKey = "merknadvalgt";
	
	private String listeutvalgKey = "utvalg"; // Denne benyttes som session key dersom bruker har valgt å se på meldinger som har sammen meldingsnøkkel OLJ 26.1.17
	
	public String getMeldeTxtId() {
		return meldeTxtId;
	}

	public void setMeldeTxtId(String meldeTxtId) {
		this.meldeTxtId = meldeTxtId;
	}

	public String[] getAldergruppeGiver() {
		return aldergruppeGiver;
	}

	public void setAldergruppeGiver(String[] aldergruppeGiver) {
		this.aldergruppeGiver = aldergruppeGiver;
	}

	public String[] getPasientSession() {
		return pasientSession;
	}

	public void setPasientSession(String[] pasientSession) {
		this.pasientSession = pasientSession;
	}

	public String getMeldeKey() {
		return meldeKey;
	}

	public void setMeldeKey(String meldeKey) {
		this.meldeKey = meldeKey;
	}

	public String[] getGiverSession() {
		return giverSession;
	}

	public void setGiverSession(String[] giverSession) {
		this.giverSession = giverSession;
	}

	public String[] getAnnenSession() {
		return annenSession;
	}

	public void setAnnenSession(String[] annenSession) {
		this.annenSession = annenSession;
	}

	/**
	 * getHemovigilans
	 * Denne rutinen henter inn nødvendige session objekter og  
	 * henter frem siden for oppfølgingsmeldinger
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {
		
		 List<Vigilansmelding> meldinger = null;
	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	   	 String page = "/hemovigilans/melder_rapport.html";	   
	     Long melderId = null;
	 	Map<String, List> alleMeldinger = new HashMap<String,List>();
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	 	 SimpleScalar simpleDisplay = new SimpleScalar(displayPart);
	     Melder melder =  (Melder) sessionAdmin.getSessionObject(request, melderNokkel);
	     if (melder == null){
	        String meldingsText = "Vennligst velg oppfølgingsmelding, og logg inn med din epost adresse";
	        sessionAdmin.setSessionObject(request,meldingsText, meldeTxtId);
	        dataModel.put( meldeTxtId, meldingsText);
	    	 page = "../hemovigilans/startside.html";	
		     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
	                 "/hemovigilans");
		     LocalReference localUri = new LocalReference(reference);
		     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/startside.html"));

		        Representation pasientkomplikasjonFtl = clres2.get();

		        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
		                MediaType.TEXT_HTML);
		     redirectPermanent(page);
			 return templatemapRep;
	     }
	     melderId = melder.getMelderId();
	     
   
	     
	     if (melderId != null){
	    	 meldinger = hendelseWebService.collectMeldinger(melderId); // Henter alle vigilansmeldinger til bruker/melder
	    	 alleMeldinger = melderWebService.collectAnnenMeldinger(meldinger); // Henter alle meldingsdetaljer fra vigilansmeldingene
	    	 sorterMeldinger(alleMeldinger,meldinger); // Fjerner doble meldinger
	    	 sessionAdmin.setSessionObject(request,andreMeldingene, andreMeldingKey); //Oppfølgingsmeldinger settes i sesjon
	    	 sessionAdmin.setSessionObject(request,giverMeldingene, giverMeldingKey);
	    	 sessionAdmin.setSessionObject(request,pasientMeldingene, pasientMeldingKey);
	 	    List<Annenkomplikasjon> annenListe =(List) alleMeldinger.get(andreKey);
		    List<Pasientkomplikasjon> pasientListe = (List) alleMeldinger.get(pasientKey);
		    List<Giverkomplikasjon> giverListe = (List)  alleMeldinger.get(giverKey);
		    sessionAdmin.setSessionObject(request,annenListe, andreKey); // Aktuelle meldinger settes i sesjon
		    sessionAdmin.setSessionObject(request,pasientListe, pasientKey);
		    sessionAdmin.setSessionObject(request,giverListe, giverKey);
		    sessionAdmin.setSessionObject(request,alleMeldinger, allemeldingerMap);
		    sessionAdmin.setSessionObject(request,meldinger, vigilansmeldinger);
		    List<Diskusjon> diskusjoner = null;
    		meldinger = hentMeldingstyper(meldinger);
		    for (Vigilansmelding melding: meldinger){
//		    	melding.setMeldingstype("Ukjent"); Dette settes i hentmeldingstyper
/*
 * Lagt til for å vise kommentar til meldingen		    OLJ Desember 2016	
 */
		    	Long meldeId = melding.getMeldeid();
		    	Map<String,List> diskusjonene = saksbehandlingWebservice.collectDiskusjoner(meldeId);
		    	String mKey = String.valueOf(meldeId.longValue());
		    	diskusjoner = diskusjonene.get(mKey);
		    	for (Diskusjon diskusjon : diskusjoner){
		    		String tema = diskusjon.getTema();
		    		if (tema != null && !tema.isEmpty() && tema.startsWith("Vises;")){
		    			char sep = ';';
		    			tema = extractString(tema, sep, -1);
		    			melding.setMeldingTitle(tema+" "+melding.getMeldingTitle());
		    		}
		    	}
/*
 * 		    	
 */
/*	Fjernet fordi dette gjøres i hentmeldingstyper
 * 	    	for (Annenkomplikasjon annenKomplikasjon : annenListe){
		    		if (melding.getMeldeid().longValue() == annenKomplikasjon.getMeldeid().longValue()){
		    			melding.setMeldingstype("Annen hendelse");
		    		
		    		}
		    	}
		    	for (Pasientkomplikasjon pasientKomplikasjon : pasientListe){
		    		if (melding.getMeldeid().longValue() == pasientKomplikasjon.getMeldeid().longValue()){
		    			melding.setMeldingstype("Pasientkomplikasjon");
		    		
		    		}
		    	}
		    	for (Giverkomplikasjon giverKomplikasjon : giverListe){
		    		if (melding.getMeldeid().longValue() == giverKomplikasjon.getMeldeid().longValue()){
		    			melding.setMeldingstype("Giverkomplikasjon");
		    		
		    		}
		    	} Fjernet fordi dette gjøres i hentmeldingstyper*/
		    }
	    	 Vigilansmelding[] meldingene = new Vigilansmelding[meldinger.size()];
	    	 int index = 0;
	    	 for (Vigilansmelding lokalmelding : meldinger){
	    		 if (lokalmelding.getSjekklistesaksbehandling() == null || lokalmelding.getSjekklistesaksbehandling().isEmpty() )
	    			 lokalmelding.setSjekklistesaksbehandling("Levert");
	    		 meldingene[index] = lokalmelding;
	    		 index++;
	    	 }
	     }

	   
	     dataModel.put(meldeKey,meldinger);
	     dataModel.put(melderNokkel,melder);
		 dataModel.put(displayKey, simpleDisplay);
//	     meldingene = (Vigilansmelding) meldinger.toArray();
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	     LocalReference localUri = new LocalReference(reference);
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));

	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	}
	
	  /**
     * storeHemovigilans
     * Denne rutinen utføres når bruker har valgt en melding han/hun ønsker å gjøre en oppfølging på 
     * 
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	
 	    Map<String, Object> dataModel = new HashMap<String, Object>();
 	    Reference reference = new Reference(getReference(),"..").getTargetRef();
	    Request request = getRequest();
	    Melder melder =  (Melder) sessionAdmin.getSessionObject(request, melderNokkel);
	    /*
	     * Denne listen inneholder nå bare oppfølgingsmelding (dersom det finnes oppfølging)
	     * Dersom en meldingsnøkkel bare har en melding, så inneholder den denne.	     
	     */
	    List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(request,vigilansmeldinger);
        Map<String, List> alleMeldinger = (Map)sessionAdmin.getSessionObject(request,allemeldingerMap);

		Parameter sokMelding = form.getFirst("meldingsnokkelsok"); // Bruker etterspør en gitt melding
		Parameter oversikt = form.getFirst("oversikt"); // Bruker går tilbake til opprinnelig oversikt	
		displayPart = "block";
	 	 SimpleScalar simpleDisplay = new SimpleScalar(displayPart);
 	    String meldingsID = null;
 	     meldingsID = (String)sessionAdmin.getSessionObject(request, listeutvalgKey);
 	     if (meldingsID != null){
 	    	meldinger = (List)sessionAdmin.getSessionObject(request,meldingsID);
 	     }
 	   if (oversikt != null){ // Dersom bruker ønsker tilbake til oversikt
 		   	displayPart = "none";
 		 	 SimpleScalar simpleDisp = new SimpleScalar(displayPart);
 		 	 meldinger = (List)sessionAdmin.getSessionObject(request,vigilansmeldinger);
 		     andreMeldingene = (List)sessionAdmin.getSessionObject(request,andreMeldingKey);
 		     giverMeldingene = (List)sessionAdmin.getSessionObject(request,giverMeldingKey);
 		     pasientMeldingene = (List)sessionAdmin.getSessionObject(request,pasientMeldingKey);
 		     sessionAdmin.removesessionObject(request, listeutvalgKey);
 
 	/*
 	 * Disse listene inneholder alle meldingene som annenliste, pasientliste og giverliste	     
 	 */
 	         List<Annenkomplikasjon> annenListe =(List) sessionAdmin.getSessionObject(request, andreKey);
 			 List<Pasientkomplikasjon> pasientListe = (List) sessionAdmin.getSessionObject(request, pasientKey);
 			 List<Giverkomplikasjon> giverListe = (List)  sessionAdmin.getSessionObject(request,  giverKey);
 		     dataModel.put(meldeKey,meldinger);
 		     dataModel.put(melderNokkel,melder);
 			 dataModel.put(displayKey, simpleDisp);
 	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/melder_rapport.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;  	
 			 
 	   }
  		if (sokMelding != null){ // Søker etter en gitt melding
  			meldingsID = null;
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
 			}
 			
 			sessionAdmin.setSessionObject(request, meldingsID,listeutvalgKey);
 			sessionAdmin.setSessionObject(request, meldinger, meldingsID); // Lagrer meldingsutvalg i session med meldngsnøkkel som nøkkel
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleKey);
//			makeSequence(request, meldinger);// Sett sekvensnummer på oppfølging/reklassifisering
 		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  		    dataModel.put(meldeKey,meldinger);
  		     dataModel.put(melderNokkel,melder);
  			 dataModel.put(displayKey, simpleDisplay);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/melder_rapport.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;  			
  		}
	    meldingsNokkel = null;
	 	String page = "p";
	 	boolean toPDF = false;
//	 	melder =  (Melder) sessionAdmin.getSessionObject(request, melderNokkel); // Hentet tidligere OLJ 24.10.16
	 	String pdf = "";
	   	if (form != null){
	    		for (Parameter entry : form) {
	    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
	    					System.out.println(entry.getName() + "=" + entry.getValue());
	    					meldingsNokkel = entry.getValue();
	    			}
	    			if (entry.getName().equals("pdf")){
	    				page = "../hemovigilans/melder_rapport.html";
	    				toPDF = true;
	    		
	    			}
	    		}
	    }
	     LocalReference localUri = new LocalReference(reference);
	   
/*
 * Disse listene inneholder nå bare første melding (ved oppfølging)
 */
	     andreMeldingene = (List)sessionAdmin.getSessionObject(request,andreMeldingKey);
	     giverMeldingene = (List)sessionAdmin.getSessionObject(request,giverMeldingKey);
	     pasientMeldingene = (List)sessionAdmin.getSessionObject(request,pasientMeldingKey);
	     
//	     Map<String, List> alleMeldinger = (Map)sessionAdmin.getSessionObject(request,allemeldingerMap);

/*
 * Disse listene inneholder alle meldingene som annenliste, pasientliste og giverliste	     
 */
         List<Annenkomplikasjon> annenListe =(List) sessionAdmin.getSessionObject(request, andreKey);
		 List<Pasientkomplikasjon> pasientListe = (List) sessionAdmin.getSessionObject(request, pasientKey);
		 List<Giverkomplikasjon> giverListe = (List)  sessionAdmin.getSessionObject(request,  giverKey);
		 List<Komplikasjonsklassifikasjon> klassifikasjoner = null;
/*
 * For givere
 */
		 List<Donasjon>donasjoner = null;
		 List<Donasjon>newDonasjoner = null;
		 List<Giver> givere = null;
		 List<Komplikasjonsdiagnosegiver> komplikasjonsdiagnoser = null;
		 List<Giveroppfolging> giverOppfolginger = null;
/*
 * For transfusjoner		 
 */
		 List<Transfusjon> transfusjoner = null;
		 List<Transfusjon> nyetransfusjoner = null;
		 List<Pasient> pasienter = null;
		 List<Pasient> nyepasienter = null;
		 List<Sykdom> sykdommer = null;
		 List<Blodprodukt> blodprodukter = null;
		 List<Produktegenskap> produktegenskaper = null;
		 List<Symptomer> symptomer = null;
		 List<Symptomer> histsymptomer = null;
		 List<Utredning> utredninger = null;
		 List<Tiltak> tiltakene = null;
		 List<Forebyggendetiltak> forebyggendetiltakene = null;
		 
		 List<Vigilansmelding> meldingen = null;
    	 Vigilansmelding melding = null;
    	 Annenkomplikasjon annenKomplikasjon = null;
    	 Giverkomplikasjon giverKomplikasjon = null;
    	 Giverkomplikasjon orgGiverkomplikasjon = null;
    	 Pasientkomplikasjon pasientKomplikasjon = null;
    	 Pasientkomplikasjon orgPasientkomplikasjon = null;
    	 Transfusjon transfusjonen = null;
    	 Transfusjon nytransfusjon = null;
    	 Pasient pasient = null;
    	 Pasient nypasient = null;
    	 Sykdom sykdom = null;
    	 Tiltak tiltaket = null;
    	 Forebyggendetiltak forebyggendeTiltaket = null;
    	 Giver giver = null;
    	 String meldeidKey = "";
		 Long orgmeldeId = null;
		 Long orggiverMeldeid = null;
		 Long orgpasientMeldeid = null;
		 Long meldeId = null;
		 
		 if (meldingsNokkel != null){
			 
	
			 for (Vigilansmelding lokalmelding : meldinger){
				 if (lokalmelding.getMeldingsnokkel().equals(meldingsNokkel)){
					 melding = lokalmelding;
					 meldeId = melding.getMeldeid();
					 orgmeldeId = getmeldingsNokkelAndre(meldingsNokkel); //Hent meldingsid for første melding med samme meldngsnøkkel
					 orggiverMeldeid = getmeldingsNokkelGiver(meldingsNokkel);
					 orgpasientMeldeid = getmeldingsNokkelPasient(meldingsNokkel);
					 if (orgmeldeId == null)
						 orgmeldeId = melding.getMeldeid();
					 if ( orggiverMeldeid == null)
						 orggiverMeldeid = melding.getMeldeid();
					 if (orgpasientMeldeid == null)
						 orgpasientMeldeid = melding.getMeldeid();
					 break;
				 }
			 }
			 if (annenListe != null && !annenListe.isEmpty()){
				 for (Annenkomplikasjon annenKomp : annenListe){
					 if (annenKomp.getMeldeid().longValue() == meldeId.longValue()){
						 annenKomplikasjon = annenKomp;
						 annenKomplikasjon.setDatoforhendelse(melding.getDatoforhendelse());
						 annenKomplikasjon.setMeldingsdato(melding.getMeldingsdato());
						 break;
					 }
				 }				 
			 }
			 if (giverListe != null && !giverListe.isEmpty()){
				 for (Giverkomplikasjon giverKomp : giverListe){
					 if (giverKomp.getMeldeid().longValue() == meldeId.longValue()){
						 giverKomplikasjon = giverKomp;
						 giverKomplikasjon.setDatoforhendelse(melding.getDatoforhendelse());
						 giverKomplikasjon.setMeldingsdato(melding.getMeldingsdato());
						 break;
					 }
				 }
				 for (Giverkomplikasjon giverKomp : giverListe){
					 if (orggiverMeldeid != null && giverKomp.getMeldeid().longValue() == orggiverMeldeid.longValue()){
						 orgGiverkomplikasjon = giverKomp;
						 break;
					 }
				 }	
			 }
			 if (pasientListe != null && !pasientListe.isEmpty()){
				 for (Pasientkomplikasjon pasientKomp : pasientListe){
					 if (pasientKomp.getMeldeid().longValue() == meldeId.longValue()){
						 pasientKomplikasjon = pasientKomp;
						 break;
					 }
				 }
				 for (Pasientkomplikasjon pasientKomp : pasientListe){
					 if (orgpasientMeldeid != null && pasientKomp.getMeldeid().longValue() == orgpasientMeldeid.longValue()){
						 orgPasientkomplikasjon = pasientKomp;
						 break;
					 }
				 }	
			 }	
			
		 }
		 if (melding != null && melding.getSupplerendeopplysninger() == null){
			 melding.setSupplerendeopplysninger("Ikke angitt");
		 }
//	     dataModel.put(meldeKey,meldinger);
//	     dataModel.put(melderNokkel,melder);
	
		 if (!toPDF)
			 page =  "../hemovigilans/rapporter_andrehendelser.html";
		  if (annenKomplikasjon != null && melding != null){
			  meldeidKey = orgmeldeId.toString();	
			  setSessionParams(annenSession);
			     setAndreHendelser(); // Setter opp andreHendelser session objekter
				    // setTransfusjonsObjects(); 
			    klassifikasjoner = (List)alleMeldinger.get(klassifikasjonKey+meldeidKey);
			     if (klassifikasjoner != null){
			    	  sessionAdmin.setSessionObject(getRequest(), klassifikasjoner,klassifikasjonKey);
			     }
			     Vigilansmelding minmelding = (Vigilansmelding) annenKomplikasjon;
			     minmelding.setMeldingsnokkel(melding.getMeldingsnokkel());
			     minmelding.setMeldingsdato(melding.getMeldingsdato());
			     minmelding.setDatoforhendelse(melding.getDatoforhendelse());
			     minmelding.setDatooppdaget(melding.getDatooppdaget());
			     minmelding.setKladd(melding.getKladd());
				 annenModel.setFormNames(sessionParams);
				 annenModel.distributeTerms();
				 annenModel.setAnnenKomplikasjon(annenKomplikasjon); 
				 annenModel.setVigilansmelding(melding);
			   	 annenModel.setHendelseDato(melding.getMeldingsdato());
		    	 annenModel.setMeldingsNokkel(melding.getMeldingsnokkel());
		    	 klassifikasjoner = (List)sessionAdmin.getSessionObject(request, klassifikasjonKey);
		    	 if (klassifikasjoner == null){
		    			klassifikasjoner = new ArrayList<Komplikasjonsklassifikasjon>();
//		    			Komplikasjonsklassifikasjon komplikasjonsklassifikasjon =  new KomplikasjonsklassifikasjonImpl();
//		    			komplikasjonsklassifikasjon.setKlassifikasjon("ukjent");
//		    			komplikasjonsklassifikasjon.setKlassifikasjonsbeskrivelse("ukjent");
//		    			klassifikasjoner.add(komplikasjonsklassifikasjon);
		    	 }
		    	 annenModel.setKlassifikasjoner(klassifikasjoner);

		    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		     	 SimpleScalar simple = new SimpleScalar(displayPart);
		     	 dataModel.put(displayKey, simple);
		    	 dataModel.put(displaydateKey, hendelseDate);
				 dataModel.put(andreHendelseId, annenModel);
				 dataModel.put(annenHendelseId, annenKomplikasjon);
			     dataModel.put(klassifikasjonKey, klassifikasjoner);
			     sessionAdmin.setSessionObject(request, klassifikasjoner,klassifikasjonKey);
				 sessionAdmin.setSessionObject(request, annenModel,andreHendelseId);
				 if (!toPDF)
					 redirectPermanent(page);
		  }
		  if (giverKomplikasjon != null && melding != null){
			  if (!toPDF)
				  page =  "../hemovigilans/rapporter_giver.html";
			  meldeidKey = orggiverMeldeid.toString();
			  setSessionParams(giverSession);
			  setGiverhendelser();
			  setAldergruppe(aldergruppeGiver);
		      Vigilansmelding minmelding = (Vigilansmelding) giverKomplikasjon;
		      minmelding.setMeldingsnokkel(melding.getMeldingsnokkel());
		      minmelding.setMeldingsdato(melding.getMeldingsdato());
		      minmelding.setDatoforhendelse(melding.getDatoforhendelse());
		      minmelding.setDatooppdaget(melding.getDatooppdaget());
		      minmelding.setKladd(melding.getKladd());
		      minmelding.setSupplerendeopplysninger(melding.getSupplerendeopplysninger());
		      Long donasjonsId = giverKomplikasjon.getDonasjonid(); // OBS !! Donasjonen er kopi av tidligere donasjon dersom ingen nye donasjonsopplysninger
		      Long newdonasjonId = new Long(giverKomplikasjon.getDonasjonid()); // Peker til gml donasjon!!
		      if (orgGiverkomplikasjon != null){
		    	  newdonasjonId = orgGiverkomplikasjon.getDonasjonid();
		      }
		      int giverId = 0;
		      String donId = String.valueOf(donasjonsId.longValue());
		      String newdonId = newdonasjonId.toString();
		      Donasjon donasjonen = null;
		      Donasjon newDonasjon = null;
		      Komplikasjonsdiagnosegiver  komplikasjonsdiagnose = null;
		      Giveroppfolging giverOppfolging = null;
		      komplikasjonsdiagnoser = alleMeldinger.get(giverkomplikasjondiagnoseKey+meldeidKey);//giverkomplikasjondiagnoseKey
		      giverOppfolginger = alleMeldinger.get(giverOppfolgingKey+meldeidKey);
		      if (komplikasjonsdiagnoser != null && !komplikasjonsdiagnoser.isEmpty()){
		    	  komplikasjonsdiagnose = komplikasjonsdiagnoser.get(0);
		    	  if (komplikasjonsdiagnose.getBivirkningbeskrivelse() == null || komplikasjonsdiagnose.getBivirkningbeskrivelse().isEmpty()){
			    		 komplikasjonsdiagnose.setBivirkningbeskrivelse("Ikke angitt");
			    		 komplikasjonsdiagnose.setLokalskadearm("Ikke angitt");
		    	  }
		    	  
		      }
		      if (komplikasjonsdiagnoser == null){
		    	  komplikasjonsdiagnose = new KomplikasjonsdiagnosegiverImpl();
		    	  komplikasjonsdiagnose.setBivirkningbeskrivelse("Ikke angitt");
		    	  komplikasjonsdiagnose.setLokalskadearm("Ikke angitt");
		    	  komplikasjonsdiagnose.setLokalskadebeskrivelse("Ikke angitt");
		    	  komplikasjonsdiagnose.setSystemiskbivirkning("Ikke angitt");
		      }
		      if (giverOppfolginger != null && !giverOppfolginger.isEmpty()){
		    	  giverOppfolging = giverOppfolginger.get(0);
		      }
		      
		      newDonasjoner = (List)alleMeldinger.get(donasjonKey+newdonId);
		      donasjoner = (List) alleMeldinger.get(donasjonKey+donId);
		      if (donasjoner != null && !donasjoner.isEmpty()){
		    	  donasjonen = donasjoner.get(0);
		    	  giverId = donasjonen.getGiveId();
		      }
		      if (donasjoner == null && newDonasjoner != null && !newDonasjoner.isEmpty()){
		    	  donasjonen = newDonasjoner.get(0);
		      }
		      if (giverId != 0){
		    	  String gId = String.valueOf(giverId);
		    	  givere = (List)alleMeldinger.get(giverenKey+gId);
		    	  if (givere != null && !givere.isEmpty()){
		    		  giver = givere.get(0);
		    	  }
		      }
		      giverModel.setFormNames(sessionParams);
		      giverModel.setAldergruppe(aldergruppeGiver);
		      giverModel.setGiverKomplikasjon(giverKomplikasjon);
		      giverModel.setVigilansmelding(melding);
		      giverModel.setMeldingsNokkel(melding.getMeldingsnokkel());
		      giverModel.setGiver(giver);
		      giverModel.setDonasjonen(donasjonen);
		      giverModel.setGiveroppfolging(giverOppfolging);
//		      giverModel.setGiverKomplikasjon(giverKomplikasjon);
		      giverModel.setKomplikasjonsdiagnoseGiver(komplikasjonsdiagnose);
		      donasjon.setDonasjon(donasjonen);   
		      sessionAdmin.setSessionObject(request, giverModel,giverkomplikasjonId);	
		      sessionAdmin.setSessionObject(request,giver,giverenKey);
			  sessionAdmin.setSessionObject(request,donasjon,donasjonId);
			  sessionAdmin.setSessionObject(request, donasjonen, donasjonKey); 
			  sessionAdmin.setSessionObject(request,komplikasjonsdiagnose,giverkomplikasjondiagnoseKey);
			  sessionAdmin.setSessionObject(request,giverOppfolging,giverOppfolgingKey);
		      SimpleScalar hendelseDate = new SimpleScalar(datePart);
		 	 SimpleScalar simple = new SimpleScalar(displayPart);
		   	  dataModel.put(displaydateKey, hendelseDate);
		   	 dataModel.put(displayKey, simple);
		   	  dataModel.put(giverkomplikasjonId,giverModel);
		   	  dataModel.put(giverkomplikasjonKey, giverKomplikasjon);
		   	  dataModel.put(giverenKey,giver);
		   	  dataModel.put(donasjonKey, donasjon);
		   	  dataModel.put(giverkomplikasjonKey, komplikasjonsdiagnoser);
		   	  dataModel.put(giverOppfolgingKey,giverOppfolginger);
		      dataModel.put(giverkomplikasjonId, giverModel);
		      if (!toPDF)
		    	  redirectPermanent(page);
		      
		  }
		  if (pasientKomplikasjon != null && melding != null){
			  if (!toPDF)
				  page =  "../hemovigilans/rapporter_transfusjon.html";
			  meldeidKey = orgpasientMeldeid.toString();
			  setTransfusjon();
		      Vigilansmelding minmelding = (Vigilansmelding) pasientKomplikasjon;
		      minmelding.setMeldingsnokkel(melding.getMeldingsnokkel());
		      minmelding.setMeldingsdato(melding.getMeldingsdato());
		      minmelding.setDatoforhendelse(melding.getDatoforhendelse());
		      minmelding.setDatooppdaget(melding.getDatooppdaget());
		      minmelding.setKladd(melding.getKladd());
		      minmelding.setSupplerendeopplysninger(melding.getSupplerendeopplysninger());
/*
 * Dersom flere meldinger med samme nøkkel:
 * Bruk ny transfusjon for pasientopplysninger
 * Bruk gammel transfusjon for blodprodukter dersom ny transfusjon ikke har blodprodukter.
 * Bruk gammel transfusjon og pasient for tiltak, dersom ny pasient ikke har tiltak.		      
 */
		      Long transId = pasientKomplikasjon.getTransfusjonsId();	// OBS !! transfusjonen kan tilhøre en tidligere melding OLJ 22.06.15
		      Long newTransId = pasientKomplikasjon.getTransfusjonsId();
		      if (orgPasientkomplikasjon != null){						// Sjekk tidligere melding !!
		    	  transId = orgPasientkomplikasjon.getTransfusjonsId();
		      }
		      String tKey = String.valueOf(transId.longValue());
		      String nytKey = String.valueOf(newTransId.longValue());
		      transfusjoner = (List)alleMeldinger.get(transfusjonsKey+tKey); // Gammel transfusjon
		      Long pasientId = null;
		      Long nypasientId = null;
		      if (transfusjoner != null && !transfusjoner.isEmpty()){
		    	  transfusjonen = transfusjoner.get(0);
		    	  pasientId = transfusjonen.getPasient_Id(); // Gammel pasient
		      }
		      if (pasientId == null)
		    	  pasientId = new Long(0);
		      String pKey = String.valueOf(pasientId.longValue());
		      pasienter = (List)alleMeldinger.get(pasientenKey+pKey); // Gammel pasient
		      if (pasienter != null && !pasienter.isEmpty()){
		    	  pasient = pasienter.get(0);
		      }
/*
 * Sjekk av ny og gammel!!		      
 */
		      String nypKey = null;
		      if (newTransId.longValue() != transId.longValue()){
		    	  nyetransfusjoner = (List)alleMeldinger.get(transfusjonsKey+nytKey);
		    	  if (nyetransfusjoner != null && !nyetransfusjoner.isEmpty()){
		    		  nytransfusjon = nyetransfusjoner.get(0);
		    		  nypasientId = nytransfusjon.getPasient_Id();
		    	  }
		    	  if (nypasientId != null){
		    		  nypKey = String.valueOf(nypasientId.longValue());
		    		  nyepasienter = (List)alleMeldinger.get(pasientenKey+nypKey);
		    		  if (nyepasienter != null && !nyepasienter.isEmpty()){
		    			  nypasient = nyepasienter.get(0);
		    		  }
		    	  }
		      }
		      sykdommer = (List)alleMeldinger.get(sykdomKey+nypKey);
		      if (sykdommer != null && !sykdommer.isEmpty()){
		    	  sykdom = sykdommer.get(0);
		    	  if (sykdom.getSykdomnsnavn() == null || sykdom.getSykdomnsnavn().equals("")){
				      sykdommer = (List)alleMeldinger.get(sykdomKey+pKey); // Hent gamle sykdommer
		    	  }
		      }	
		      if (sykdommer == null)
		    	  sykdommer = (List)alleMeldinger.get(sykdomKey+pKey);
		      if (sykdommer != null && !sykdommer.isEmpty()){
		    	  sykdom = sykdommer.get(0);
			      result.setSykdom(sykdom);

		      }
		      blodprodukter = (List)alleMeldinger.get(blodproduktKey+nytKey);
		      if (blodprodukter != null && blodprodukter.isEmpty())
		    	  blodprodukter = (List)alleMeldinger.get(blodproduktKey+tKey);
			  if (blodprodukter != null && !blodprodukter.isEmpty()){
					for (Blodprodukt blodProdukt :blodprodukter){
						produktegenskaper = (List)alleMeldinger.get(produktegenskapKey+tKey);
					}						
			  }
			  if (produktegenskaper == null){
				  produktegenskaper = new ArrayList<Produktegenskap>();
			  }
			  utredninger = (List)alleMeldinger.get(utredningKey+meldeidKey);
			  symptomer = (List)alleMeldinger.get(symptomerKey+meldeidKey);
			  if (symptomer == null){
				  symptomer = new ArrayList<Symptomer>();
			  }
			  List<String> keys = meldingidOppfolging(meldinger, melding.getMeldingsnokkel());
			  if (keys != null && !keys.isEmpty()){
				  for (String key : keys){
					  if (!key.equals(meldeidKey))
						  histsymptomer = (List)alleMeldinger.get(symptomerKey+key);
				  }
				  if (symptomer != null && histsymptomer != null )
					  symptomer.addAll(histsymptomer);
			  }
		
			  klassifikasjoner = (List)alleMeldinger.get(klassifikasjonKey+meldeidKey);
			  
			  tiltakene = (List)alleMeldinger.get(tiltakKey+nypKey); // ny pasient
			  if (tiltakene == null)
				  tiltakene = (List)alleMeldinger.get(tiltakKey+pKey); // Gammel pasient
			  Long tiltakId = null;
			  if (tiltakene != null && !tiltakene.isEmpty()){
				  tiltaket = tiltakene.get(0);
				  result.setTiltak(tiltaket);
				  tiltakId = tiltaket.getTiltakid();
			  }
			  if (tiltakId == null)
				  tiltakId = new Long(0);
			  String sTiltakid = String.valueOf(tiltakId.longValue());
			  forebyggendetiltakene = (List)alleMeldinger.get(forebyggendetiltakKey+sTiltakid);
			  if (forebyggendetiltakene != null && !forebyggendetiltakene.isEmpty()){
				  forebyggendeTiltaket = forebyggendetiltakene.get(0);
				  result.setForebyggendeTiltak(forebyggendeTiltaket);
			  }
			  setSessionParams(pasientSession); // Setter opp riktige sessionparametre for pasient/transfusjon
		      transfusjon.setPlasmaEgenskaper(blodProdukt); // Setter plasma produkttyper
		    	 transfusjon.setFormNames(sessionParams);
			     transfusjon.distributeTerms();
			   	 transfusjon.setHendelseDato(melding.getMeldingsdato());
		    	 transfusjon.setMeldingsNokkel(melding.getMeldingsnokkel());
		    	 transfusjon.setVigilansmelding(melding);
		    	 transfusjon.setPasientKomplikasjon(pasientKomplikasjon);
		    	 transfusjon.setTransfusjon(transfusjonen);
				 result.setFormNames(sessionParams);
		    	 result.setPasient(pasient);
			    	 
				 result.distributeTerms();
			//	 result.setAldergruppe(aldergruppePasient); // Setter opp riktig aldersgruppe for pasienter
			//	 giverModel.setGiverKomplikasjon(giverKomplikasjon);
				 result.setVigilansmelding(melding);
				 result.setHendelseDato(melding.getMeldingsdato());
		    	 result.setMeldingsNokkel(melding.getMeldingsnokkel());
		    	 SimpleScalar simple = new SimpleScalar(displayPart);
		    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		       	 dataModel.put(displayKey, simple);
		    	 dataModel.put(displaydateKey, hendelseDate);
			//	 dataModel.put(andreHendelseId, annenModel);

			     dataModel.put(pasientkomplikasjonId, result);
			     dataModel.put(transfusjonId,transfusjon);
			     sessionAdmin.setSessionObject(request,pasient,pasientenKey);
			     sessionAdmin.setSessionObject(request,sykdommer,sykdomKey);
			     sessionAdmin.setSessionObject(request,transfusjonen,transfusjonsKey);
			     sessionAdmin.setSessionObject(request,blodprodukter,blodproduktKey);
			     sessionAdmin.setSessionObject(request,produktegenskaper,produktegenskapKey);
			     sessionAdmin.setSessionObject(request,symptomer,symptomerKey);
			     sessionAdmin.setSessionObject(request,klassifikasjoner,klassifikasjonKey);
			     sessionAdmin.setSessionObject(request,utredninger,utredningKey);
			     sessionAdmin.setSessionObject(request,tiltakene,tiltakKey);
			     sessionAdmin.setSessionObject(request,forebyggendetiltakene,forebyggendetiltakKey);
			     
				 sessionAdmin.setSessionObject(request, result,pasientkomplikasjonId);	    			  
				 sessionAdmin.setSessionObject(request, transfusjon,transfusjonId);
				 if (!toPDF)
					 redirectPermanent(page);
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
	     dataModel.put(meldeKey,meldinger);
	     dataModel.put(melderNokkel,melder);

		    ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));

	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
    }
}
