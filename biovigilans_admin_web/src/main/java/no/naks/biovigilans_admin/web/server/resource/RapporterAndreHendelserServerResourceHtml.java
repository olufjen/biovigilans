package no.naks.biovigilans_admin.web.server.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.AnnenkomplikasjonImpl;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.model.AnnenKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.model.SakModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;

import org.apache.commons.lang.time.FastDateFormat;
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
 * @author olj
 *  Denne resursen er knyttet til siden for å rapportere andre hendelser
 */
public class RapporterAndreHendelserServerResourceHtml extends SaksbehandlingSessionServer{

	private String flaggShow = "none";
	private String flaggKey = "saksbehandling";
	private String tilsakbehandler = "tilsaksbehandler";
	private String sakbehandlerShow = "none";
//    private Annenkomplikasjon orgAnnenkomplikasjon = null;
    private String orgKompKey = "orgkomp";
//	private SaksbehandlingWebService saksbehandlingWebservice;
	

	public RapporterAndreHendelserServerResourceHtml (){
		super();
	}
	


	public String getFlaggKey() {
		return flaggKey;
	}



	public void setFlaggKey(String flaggKey) {
		this.flaggKey = flaggKey;
	}



	public String getFlaggShow() {
		return flaggShow;
	}



	public void setFlaggShow(String flaggShow) {
		this.flaggShow = flaggShow;
	}



	public String getBehandlingsFlaggKey() {
		return behandlingsFlaggKey;
	}



	public void setBehandlingsFlaggKey(String behandlingsFlaggKey) {
		this.behandlingsFlaggKey = behandlingsFlaggKey;
	}





	private void setDiplayvalues(Map<String, Object> dataModel,Melder melder){
		Request request = getRequest();
		Komplikasjonsklassifikasjon klassifikasjon = annenModel.getKomplikasjonsklassifikasjon();
		List klassifikasjoner = annenModel.getKlassifikasjoner();
		klassifikasjon.setKlassifikasjonList(hvagikkgaltList);
		 annenKomplikasjon = annenModel.getAnnenKomplikasjon();
		 displayPart = "block";
    	 datePart = "none";
    	 /*
    	  * For å vise info fra tidligere meldinger	    	 
    	  */
    	 displayorgInfo = (String)sessionAdmin.getSessionObject(request, displayorgInfoKey);
    	 if (displayorgInfo == null || displayorgInfo.isEmpty())
    		 displayorgInfo = "none";    	 
    	 SimpleScalar flaggs = new SimpleScalar(flaggShow);
	   	 SimpleScalar simple = new SimpleScalar(displayPart);
    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
    	 SimpleScalar tilsakbehandel = new SimpleScalar(sakbehandlerShow);
     	 SimpleScalar iconImportant = new SimpleScalar(imagesrcImportant);
     	 SimpleScalar iconQuestion = new SimpleScalar(imagesrcQuestion);
		 SimpleScalar orgInfo = new SimpleScalar(displayorgInfo);
    	 dataModel.put(imageImportantkey,iconImportant);
    	 dataModel.put(imageQuestionkey,iconQuestion);
      	 dataModel.put(displayorgInfoKey, orgInfo);
    	 dataModel.put(tilsakbehandler, tilsakbehandel);
    	 dataModel.put(flaggKey,flaggs);
    	 dataModel.put(statusflagKey,statusflag);
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(displaydateKey, hendelseDate);
	     dataModel.put(andreHendelseId, annenModel);
	     dataModel.put(annenHendelseId, annenKomplikasjon);
	     dataModel.put(klassifikasjonKey, klassifikasjoner);
//	     dataModel.put(annenHendelseOrg, orgAnnenkomplikasjon);
	  	 dataModel.put(tilmelderKey, melder);
	     sessionAdmin.setSessionObject(getRequest(), annenModel,andreHendelseId);
	}

	public String[] getStatusflag() {
		return statusflag;
	}



	public void setStatusflag(String[] statusflag) {
		this.statusflag = statusflag;
	}



	public String getStatusflagKey() {
		return statusflagKey;
	}



	public void setStatusflagKey(String statusflagKey) {
		this.statusflagKey = statusflagKey;
	}
	/**
	 * statusChange
	 * Denne rutinen kalles når bruker har valgt å endre status på meldingen
	 * @param form
	 * @param request
	 * @param dataModel
	 * @param melder
	 * @param diskusjoner
	 * @return
	 */
	private TemplateRepresentation statusChange(Form form,Request request,Map<String, Object> dataModel,Melder melder,List<Diskusjon>diskusjoner,ClientResource clres2){
		 //      			Vigilansmelding melding = (Vigilansmelding) annenModel.getAnnenKomplikasjon();
		Vigilansmelding melding = (Vigilansmelding) annenModel.getAnnenKomplikasjon();
		Date datoforhendelse =  melding.getDatoforhendelse();
	 	Long meldeId = melding.getMeldeid();
		melding.setDatoforhendelse(datoforhendelse);
		String statusCode = "";
	     SakModel sakModel = (SakModel)sessionAdmin.getSessionObject(request,  sakModelKey);
		for (Parameter entry : form) {
			if (entry.getName().equals("nystatus") && entry.getValue() != null && !(entry.getValue().equals(""))){
				statusCode = entry.getValue();
				System.out.println(entry.getName() + "=" + entry.getValue());
		
			}
		}
		if (!statusCode.equals("")){
			if (statusCode.equals(statusflag[0])) // Dersom status settes til Levert, så skal det settes til null i db
				statusCode = null;
			annenModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
			hendelseWebService.updateVigilansmelding(annenModel.getVigilansmelding());
			if (statusCode == null){
				statusCode = statusflag[0];
				annenModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
			}
			
			sakModel.setLoginSaksbehandler(login.getSaksbehandler());
			sakModel.lagSak(meldeId, statusCode);
			saksbehandlingWebservice.saveDiskusjon(sakModel.getDiskusjonsMappe());
			sakModel.setSakdiskusjon();
			saksbehandlingWebservice.saveSak(sakModel.getSaksMappe());
			
			List<Diskusjon> nyediskusjoner = sakModel.lagDiskusjonsliste();
			if (diskusjoner != null)
				diskusjoner.addAll(nyediskusjoner);
   			sakModel.setDiskusjonsMappe(null);
			sakModel.setSaksMappe(null);
		}
		setDiplayvalues(dataModel,melder);
	 	dataModel.put(behandlingsFlaggKey, diskusjoner);
		clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,andrehendelserskjema));
		 
		Representation andreHendelser = clres2.get();
//		invalidateSessionobjects();
		TemplateRepresentation templateRep = new TemplateRepresentation(andreHendelser, dataModel,
				MediaType.TEXT_HTML);
		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
	}


	/**
	 * getInnmelding
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en rapportert hendelse
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {

	//	invalidateSessionobjects();
	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	     Request request = getRequest();
	     setDBSource(request);
	     String db =  sessionAdmin.getChosenDB(request);
	     if (db != null && db.equals("cellerogvev"))
	    	 andrehendelserskjema = cellereogvevandrehendelser;
	     if (db != null && db.equals("organer"))
	    	 andrehendelserskjema = organerandrehendelser;
	 	hvagikkgaltList.clear(); // Lagt til 31.10.16 OLJ

	     SakModel sakModel = (SakModel)sessionAdmin.getSessionObject(request,  sakModelKey);
	     if (sakModel == null){
	    	 sakModel = new SakModel();
	    	 sessionAdmin.setSessionObject(request, sakModel, sakModelKey);
	     }
	     sakModel.setMerknader(merknader);
	     sakModel.setStatusflag(statusflag);
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
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,andrehendelserskjema));
	     setAndreHendelser(); // Setter opp andreHendelser session objekter
	    // setTransfusjonsObjects(); 
	     annenModel.setFormNames(sessionParams);
	     annenModel.distributeTerms();
	     andreMeldingene = (List<Vigilansmelding>)sessionAdmin.getSessionObject(request, andreMeldingKey);
	     Annenkomplikasjon annenKomplikasjon = null;
	     Map alleMeldinger = (HashMap)sessionAdmin.getSessionObject(request,allemeldingerMap);
	     List klassifikasjoner = annenModel.getKlassifikasjoner();
	     
	     List<Diskusjon> diskusjoner = null;
	     List<Sak> saker = null;
//	     List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
		 Melder melder = null;
	     
		 List<Annenkomplikasjon> annenListe =(List) sessionAdmin.getSessionObject(request,reportAndreKey);
	     if (annenModel.getVigilansmelding().getMeldingsnokkel() != null){
	    	 displayPart = "block";
	    	 datePart = "none";
	    	 Vigilansmelding melding = (Vigilansmelding)annenModel.getAnnenKomplikasjon();
	    	 annenKomplikasjon = annenModel.getAnnenKomplikasjon();
	    	 makeSequence(request, melding);
	    	 annenModel.getVigilansmelding().setSekvensNr(melding.getSekvensNr());
//	    	 orgAnnenkomplikasjon = annenKomplikasjon; //Dersom det ikke finnes oppfølginger
	    	 annenModel.setHendelseDato(melding.getDatoforhendelse());
	    	 annenModel.setMeldingsNokkel(melding.getMeldingsnokkel());
//	    	 klassifikasjoner = (List)sessionAdmin.getSessionObject(request, klassifikasjonKey); Klassifikasjonene finnes allerede i annenModel
//			 annenModel inneholder enten nye klassifikasjoner eller de fra orginal melding	    	 
//==  Hent alle diskusjoner og merknader   =======	    	 
	    	 Long meldeId = melding.getMeldeid();
	
	    	 melder = hentMelder(annenModel.getVigilansmelding());

	     	 
	   	     String mmKey = melding.getMeldingsnokkel(); //
    	     Long orgmeldeId = getmeldingsNokkelsak(mmKey); //Hent meldingsid for første melding med samme meldingsnøkkel
    	     // orgMeldeid er null dersom det ikke finnes andre meldinger med samme nøkkel.
       		 String mnKey = String.valueOf(meldeId.longValue());
	    	 Map<String,List> diskusjonene = saksbehandlingWebservice.collectDiskusjoner(meldeId);
	    	 Map<String,List> orgMapdiskusjoner = null;
	    	 String omKey = "";
	    	 List<Diskusjon> orgdiskusjoner = null;	    	 
	    	 boolean sameKey = false;
	    	 annenModel.getTidligereVigilans().clear();
	    	 annenModel.getTidligereAnnenkomp().clear();
	    	 annenModel.getTidligereKlassifikasjoner().clear();
	    	 if (orgmeldeId != null){
	    		 orgMapdiskusjoner = saksbehandlingWebservice.collectDiskusjoner(orgmeldeId); // Henter diskusjoner fra tidligere meldinger med samme nøkkel
	    		 omKey = String.valueOf(orgmeldeId.longValue());
	    		 orgdiskusjoner =  orgMapdiskusjoner.get(omKey);
	    		 sameKey = mnKey.equals(omKey);
	    	 }
/*
 * For å vise historikk	    	 
 */
//			 List<Komplikasjonsklassifikasjon> orgKlassifikasjoner =  (List)alleMeldinger.get(klassifikasjonKey);	    	 
	    	 Vigilansmelding orgVigilansmelding = null;

	    	 for (Vigilansmelding nestemelding: andreMeldingene){
	    		 if (orgmeldeId != null && nestemelding.getMeldingsnokkel().equals(mmKey) && nestemelding.getMeldeid().longValue() != meldeId.longValue() ){
	    			 orgVigilansmelding = nestemelding;
	    			 Long orgMeldId = nestemelding.getMeldeid();
	    			 String oMKey = String.valueOf(orgMeldId.longValue());
	    			 Map<String,List> histMeldingsdetaljer = null;
					 histMeldingsdetaljer = (Map<String,List>)saksbehandlingWebservice.selectMeldinger(oMKey); // Hent historikk
					 if (histMeldingsdetaljer != null){
						 List<Komplikasjonsklassifikasjon> orgKlassifikasjoner =  (List)histMeldingsdetaljer.get(klassifikasjonKey);
						 annenModel.getTidligereKlassifikasjoner().addAll(orgKlassifikasjoner);
					 }
	    			 annenModel.getTidligereVigilans().add(orgVigilansmelding);
	    		 }
	    	 }
	    	 List<Vigilansmelding> vigilans =  annenModel.getTidligereVigilans();
	    	 for (Vigilansmelding orgmelding : vigilans){
		    	 for (Annenkomplikasjon komplikasjon : annenListe){
		    		 if (orgmeldeId != null && komplikasjon.getMeldeid().longValue() == orgmelding.getMeldeid().longValue() ){
		    			 Annenkomplikasjon orgAnnenkomplikasjon = komplikasjon;
		    			 displayorgInfo = "block";
	    				 sessionAdmin.setSessionObject(request, displayorgInfo,displayorgInfoKey );
		    			 annenModel.getTidligereAnnenkomp().add(orgAnnenkomplikasjon);
//		    			 annenModel.getTidligereVigilans().add(orgVigilansmelding);
//		    			 break;
		    		 }
		    	 }		    		 
	    	 }
    		 
	    	 String mKey = String.valueOf(meldeId.longValue());
	    	 diskusjoner = diskusjonene.get(mKey);

	    	 if (diskusjoner == null)
	    		 diskusjoner = new ArrayList<Diskusjon>();
	    	 if (orgdiskusjoner != null && !sameKey)
	    		 diskusjoner.addAll(orgdiskusjoner);
	    	 if (!diskusjoner.isEmpty()){
	    		 checkDiskusjoner(diskusjoner); // Sjekker om diskusjonene inneholder melding til melder
	    	 }
	      	 if (orgMapdiskusjoner != null)
	      		 diskusjonene.putAll(orgMapdiskusjoner);
	    	 sessionAdmin.setSessionObject(request, diskusjonene, diskusjonsKey);
/*
 * Hent saker til diskusjonene 	    	 
 */
	    	 setsaksbehandlerTildiskusjon(request, diskusjoner, saker);
	    	 

	    	 
	     }
	     if (annenKomplikasjon == null){
	    	 annenKomplikasjon = annenModel.getAnnenKomplikasjon();
	  
	     }

	     
    	 SimpleScalar simple = new SimpleScalar(displayPart);
    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		 SimpleScalar orgInfo = new SimpleScalar(displayorgInfo);
    	 SimpleScalar tilMelder = new SimpleScalar(tilMelderPart);
    	 SimpleScalar iconImportant = new SimpleScalar(imagesrcImportant);
    	 
      	 SimpleScalar iconQuestion = new SimpleScalar(imagesrcQuestion);

    	 dataModel.put(imageImportantkey,iconImportant);
    	 dataModel.put(imageQuestionkey,iconQuestion);    	 
    	 
    	 dataModel.put(imageImportantkey,iconImportant);
    	 dataModel.put(tilMelding,tilMelder);
    	 dataModel.put(displayorgInfoKey, orgInfo);
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(displaydateKey, hendelseDate);
    	 dataModel.put(statusflagKey,statusflag);
	     dataModel.put(andreHendelseId, annenModel);
	     dataModel.put(annenHendelseId, annenKomplikasjon);
//	     dataModel.put(annenHendelseOrg, orgAnnenkomplikasjon);
	     dataModel.put(klassifikasjonKey, klassifikasjoner);
	 	dataModel.put(behandlingsFlaggKey, diskusjoner);
	 	
	 	dataModel.put(tilmelderKey, melder);
	 	
	     sessionAdmin.setSessionObject(request, annenModel,andreHendelseId);
//	     sessionAdmin.setSessionObject(request, orgAnnenkomplikasjon, orgKompKey);
	     
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
    	Request request = getRequest();
/*    	if(form == null){
    		invalidateSessionobjects();
    	}*/
    	
    	hvagikkgaltList.clear(); // Lagt til 31.10.16 OLJ
	     login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
	     sakbehandlerShow = "none";
	     setDBSource(request);
	     String db =  sessionAdmin.getChosenDB(request);
	     if (db != null && db.equals("cellerogvev"))
	    	 andrehendelserskjema = cellereogvevandrehendelser;
	     if (db != null && db.equals("organer"))
	    	 andrehendelserskjema = organerandrehendelser;
	     
    	if (form != null){
    		Map<String, Object> dataModel = new HashMap<String, Object>();

    	     SakModel sakModel = (SakModel)sessionAdmin.getSessionObject(request,  sakModelKey);
    	     if (sakModel == null){
    	    	 sakModel = new SakModel();
    	    	 sessionAdmin.setSessionObject(request, sakModel, sakModelKey);
    	     }
    	     sakModel.setFlaggNames(flaggNames);
    	     Map<String,List> diskusjonene = (Map<String,List>)sessionAdmin.getSessionObject(request, diskusjonsKey);
//====    	     
  		  annenModel = (AnnenKomplikasjonwebModel)sessionAdmin.getSessionObject(request, andreHendelseId);
//  		  orgAnnenkomplikasjon = (Annenkomplikasjon)sessionAdmin.getSessionObject(request, orgKompKey);
  		  if(annenModel == null){
  			  annenModel = new AnnenKomplikasjonwebModel();
  			  annenModel.setFormNames(sessionParams);
  		  }
  		  
  		    Vigilansmelding melding = (Vigilansmelding) annenModel.getAnnenKomplikasjon();
  		    Long melderId = annenModel.getVigilansmelding().getMelderId();
  		    melding.setMelderId(melderId);
  		    Long gmlMeldeid = sakModel.getGmlMeldeid();
   		  	Long meldeId = melding.getMeldeid();
  		  	String mKey = String.valueOf(meldeId.longValue());
		    if (gmlMeldeid != null){
  		    	mKey = String.valueOf(gmlMeldeid.longValue());
  		    }
  		    List<Diskusjon>diskusjoner = diskusjonene.get(mKey);
  		   SimpleScalar tilMelder = new SimpleScalar(tilMelderPart);
  	    	dataModel.put(tilMelding,tilMelder);
  		 	dataModel.put(behandlingsFlaggKey, diskusjoner);
  		 	Melder melder = null;
    		for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals("")))
    					System.out.println(entry.getName() + "=" + entry.getValue());
    			if(entry.getName().equalsIgnoreCase("tab-hvagikkgalt")){
    				hvagikkgaltList.add(entry.getValue());
    			}else{
    				annenModel.setValues(entry);
    			}
    		}
    		
    		sessionAdmin.setSessionObject(request, annenModel,andreHendelseId);
    		dataModel.put(andreHendelseId,annenModel);
    		ClientResource clres2  ;
/*
 * =====================================================================
 */
	    	melder = hentMelder(annenModel.getVigilansmelding());
    		Parameter lagre = form.getFirst("btnSendinn");
    		Parameter lagreFlagg = form.getFirst("btnlagreflagg");
    		Parameter statusReklassifisering = form.getFirst("btnstatusendring");
    		Parameter statusChange = form.getFirst("btnstatuschange");
    		Parameter avslutt = form.getFirst("btnavslutt");
    		Parameter sendTilmelder = form.getFirst("btnsend");
    		
    		if (sendTilmelder != null){ // Ønsker å sende melding til melder
    			setDiplayvalues(dataModel,melder);
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,andrehendelserskjema));
    			meldingsType = "annen";
    			 sessionAdmin.setSessionObject(request,meldingsType, meldingstypeKey);
    			Representation andreHendelser = clres2.get();
 //       		invalidateSessionobjects();
        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);
        		String page = "../hemovigilans/meldingtilmelder.html";
        		redirectPermanent(page);
        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
    		}
    		
    		if (avslutt != null){ // Bruker avslutter
    			LoginModel newLogin = new LoginModel();
    			newLogin.setSaksbehandler(login.getSaksbehandler());
    			newLogin.setPassord(login.getSaksbehandler().getBehandlerpassord());
    			newLogin.setEpostAdresse(login.getSaksbehandler().getBehandlerepost());
    			List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(request, meldingsId); // Midlertidig
//    			meldinger = null;
//    			sessionAdmin.setSessionObject(request, meldinger, meldingsId); 
    			List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
//    			invalidateSessionobjects();
//    			sessionAdmin.getSession(request,diskusjonsKey).invalidate();
//    			sessionAdmin.getSession(request,sakModelKey).invalidate();
/*
 * Fjerner saksgangen og diskusjonene fra session
*/
    			sessionAdmin.removesessionObject(request, displayorgInfoKey);
    		 	sessionAdmin.removesessionObject(request, diskusjonsKey);
    		 	sessionAdmin.removesessionObject(request, sakModelKey);
       		 	sessionAdmin.removesessionObject(request, klassifikasjonKey);
    			sessionAdmin.setSessionObject(request, newLogin, loginKey);
    			 sessionAdmin.setSessionObject(request, saksbehandlere, behandlereKey);
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));
   			 
    			Representation andreHendelser = clres2.get();
        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);
        		redirectPermanent("../hemovigilans/saksbehandling.html");
        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
    		}
    		if (statusChange != null){  // Bruker har valgt å endre saksstatus
 //      			Vigilansmelding melding = (Vigilansmelding) annenModel.getAnnenKomplikasjon();
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,andrehendelserskjema));
    			
    			templateRep = statusChange(form, request, dataModel, melder, diskusjoner, clres2);
/*    		
 * Dette utføres nå i egen subrutine  se over. OLJ 28.10.16	
 * 
    			Date datoforhendelse =  melding.getDatoforhendelse();
    			melding.setDatoforhendelse(datoforhendelse);
    			String statusCode = "";
    	
    			for (Parameter entry : form) {
        			if (entry.getName().equals("nystatus") && entry.getValue() != null && !(entry.getValue().equals(""))){
        				statusCode = entry.getValue();
        				System.out.println(entry.getName() + "=" + entry.getValue());
        		
        			}
    			}
    			if (!statusCode.equals("")){
    				if (statusCode.equals(statusflag[0])) // Dersom status settes til Levert, så skal det settes til null i db
    					statusCode = null;
    				annenModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
    				hendelseWebService.updateVigilansmelding(annenModel.getVigilansmelding());
    				if (statusCode == null){
    					statusCode = statusflag[0];
    					annenModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
    				}
    				
    				sakModel.setLoginSaksbehandler(login.getSaksbehandler());
    				sakModel.lagSak(meldeId, statusCode);
        			saksbehandlingWebservice.saveDiskusjon(sakModel.getDiskusjonsMappe());
        			sakModel.setSakdiskusjon();
        			saksbehandlingWebservice.saveSak(sakModel.getSaksMappe());
        			
        			List<Diskusjon> nyediskusjoner = sakModel.lagDiskusjonsliste();
        			if (diskusjoner != null)
        				diskusjoner.addAll(nyediskusjoner);
           			sakModel.setDiskusjonsMappe(null);
        			sakModel.setSaksMappe(null);
    			}
    			setDiplayvalues(dataModel,melder);
    		 	dataModel.put(behandlingsFlaggKey, diskusjoner);
    		
    			 
    			Representation andreHendelser = clres2.get();
//        		invalidateSessionobjects();
        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);
*/         				
        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15

    			}
    		//Parameter ikkegodkjet = form.getFirst("ikkegodkjent");
    		//Parameter godkjet = form.getFirst("godkjent");
    		if (lagreFlagg != null){ // Bruker har valgt å sette saksmerknader
       		//	Vigilansmelding melding = (Vigilansmelding) annenModel.getAnnenKomplikasjon();
    			Date datoforhendelse =  melding.getDatoforhendelse();
    			melding.setDatoforhendelse(datoforhendelse);
    			sakModel.setLoginSaksbehandler(login.getSaksbehandler());
    	
/*
 * Hent flaggverdier valgt fra bruker 
 */
    			
    			sakModel.setFlaggNames(flaggNames);
    			sakModel.getFormMap().clear();
    			boolean avvist = false;
    			for (Parameter entry : form) {
        			if (entry.getValue() != null && !(entry.getValue().equals(""))){
        					System.out.println("Saksmerknader "+entry.getName() + "=" + entry.getValue());
        					if (entry.getName().equals("nystatus")){ // Bruker også valgt å endre status til avvist
        						avvist = true;
        					}
        					if (entry.getName().equals("kommentartilmeldingtxt")&& avvist){
        						String txtVal = entry.getValue(); // Betyr at kommentar skal vises til Melder
        						txtVal = "Vises;"+txtVal;
        						entry.setValue(txtVal);
        					}
        					sakModel.setValues(entry);
        			}
    			}

    			sakModel.saveSaker(melding.getMeldeid());// Setter også saksbehandler navn til merknad
    			saksbehandlingWebservice.saveDiskusjon(sakModel.getDiskusjonsMappe());
    			sakModel.setSakdiskusjon();
    			saksbehandlingWebservice.saveSak(sakModel.getSaksMappe());
    			
    			List<Diskusjon> nyediskusjoner = sakModel.lagDiskusjonsliste();
    			if (diskusjoner != null)
    				diskusjoner.addAll(nyediskusjoner);
    			flaggShow = "block";
    		
    			if (sakModel.isTilDialog()){
    				String mailTxt = sakModel.getHemovigilansDiskusjon().getKommentar() + " Meldingsnøkkel: " + annenModel.getVigilansmelding().getMeldingsnokkel()+ " Type Andre hendelser.";
        			dialogHemivigilans(request, sakModel.getDiskId(),mailTxt,annenModel.getVigilansmelding());
        			sakbehandlerShow = "block";
        			
    			}
    			if (sakModel.isReklassifikasjon()){
     				annenModel.getVigilansmelding().setSjekklistesaksbehandling(statusflag[8]); //Sett gammel melding til Erstattet OLJ 01.10.16
     				hendelseWebService.updateVigilansmelding(annenModel.getVigilansmelding());
    				sakModel.setGmlMeldeid(meldeId);
    				saveannenReclassifikasjon();
    			}
/*
 * En rutine for å sende epost til Helsedirektoratet dersom saksbehandler har valgt "Melde til Helsedirektoratet"
 * olj 03.02.16    			
 */
    			String mailText = (String)sakModel.getFormMap().get("meldingtilhelsedir")+ " Meldingsnummer: "+ melding.getMeldingsnokkel();
    			tilHelsedirektoratet(request, mailText, sakModel.getFormMap());
    			sakModel.setDiskusjonsMappe(null);
    			sakModel.setSaksMappe(null);
    			setDiplayvalues(dataModel,melder);
    		 	dataModel.put(behandlingsFlaggKey, diskusjoner); // Lagt til 26.10.26 OLJ 
    			tilMelderPart = "none";
    			if (sakModel.isTilMelder())
    				tilMelderPart = "block";
    			tilMelder = null;
    		    tilMelder = new SimpleScalar(tilMelderPart);
    	    	dataModel.put(tilMelding,tilMelder); 		

    			dataModel.put(behandlingsFlaggKey, diskusjoner);
    			String newStatus = sakModel.setsakStatus(nyediskusjoner);
    			if (newStatus != null){
    				annenModel.getVigilansmelding().setSjekklistesaksbehandling(newStatus);
    				hendelseWebService.updateVigilansmelding(annenModel.getVigilansmelding());
    			}
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,andrehendelserskjema));
    			 
    			
    			if (avvist)
    				templateRep = statusChange(form, request, dataModel, melder, diskusjoner, clres2);
    			else{
    				Representation andreHendelser = clres2.get();
    				templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);
    			}
//        		invalidateSessionobjects();    			
        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
    		}
    		if(lagre != null){ // Bruker har valgt å godkjenne meldingen
    			
    			//giverModel.getVigilansmelding().saveToVigilansmelding();
    			String strDate = form.getValues("hendelsen-date");
    			
  //  			Vigilansmelding melding = (Vigilansmelding) annenModel.getAnnenKomplikasjon();
    			Date datoforhendelse =  melding.getDatoforhendelse();
    			if(strDate != null && melding.getDatoforhendelse() == null){
					try {
						DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
						datoforhendelse = dateFormat.parse(strDate);
					} catch (ParseException e) {
						System.out.println("date format problem: " + e.toString());
	
					}
    			}

    			melding.setDatoforhendelse(datoforhendelse);
    		  	
    			annenModel.getVigilansmelding().setSjekklistesaksbehandling(statusflag[1]);
    			hendelseWebService.updateVigilansmelding(annenModel.getVigilansmelding());
    			setDiplayvalues(dataModel,melder);
    			
 /*  Erstattet med setDisplayvalues: 			Komplikasjonsklassifikasjon klassifikasjon = annenModel.getKomplikasjonsklassifikasjon();
    			List klassifikasjoner = annenModel.getKlassifikasjoner();
    			klassifikasjon.setKlassifikasjonList(hvagikkgaltList);
    			 annenKomplikasjon = annenModel.getAnnenKomplikasjon();
    			 displayPart = "block";
    	    	 datePart = "none";
    		   	 SimpleScalar simple = new SimpleScalar(displayPart);
    	    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
    	    	 dataModel.put(statusflagKey,statusflag);
    	    	 dataModel.put(displayKey, simple);
    	    	 dataModel.put(displaydateKey, hendelseDate);
    		     dataModel.put(andreHendelseId, annenModel);
    		     dataModel.put(annenHendelseId, annenKomplikasjon);
    		     dataModel.put(klassifikasjonKey, klassifikasjoner);
    		     sessionAdmin.setSessionObject(getRequest(), annenModel,andreHendelseId);*/
    		     
    		//	dataModel.put(melderId, melderwebModel);
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,andrehendelserskjema));
 
    			Representation andreHendelser = clres2.get();
 //       		invalidateSessionobjects();
        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);
        		
    		}else{
	    		//invalidateSessionobjects();
	    		clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));
	    		Representation andreHendelser = clres2.get();
	       		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
	    				MediaType.TEXT_HTML);
	    		
    		}
	    }
/*
 * ==============================================================================================================    	
 */
    	return templateRep;
      
    }


}
