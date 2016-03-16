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
		Komplikasjonsklassifikasjon klassifikasjon = annenModel.getKomplikasjonsklassifikasjon();
		List klassifikasjoner = annenModel.getKlassifikasjoner();
		klassifikasjon.setKlassifikasjonList(hvagikkgaltList);
		 annenKomplikasjon = annenModel.getAnnenKomplikasjon();
		 displayPart = "block";
    	 datePart = "none";
    	 SimpleScalar flaggs = new SimpleScalar(flaggShow);
	   	 SimpleScalar simple = new SimpleScalar(displayPart);
    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
    	 SimpleScalar tilsakbehandel = new SimpleScalar(sakbehandlerShow);
    	 dataModel.put(tilsakbehandler, tilsakbehandel);
    	 dataModel.put(flaggKey,flaggs);
    	 dataModel.put(statusflagKey,statusflag);
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(displaydateKey, hendelseDate);
	     dataModel.put(andreHendelseId, annenModel);
	     dataModel.put(annenHendelseId, annenKomplikasjon);
	     dataModel.put(klassifikasjonKey, klassifikasjoner);
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
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_andrehendelser.html"));
	     setAndreHendelser(); // Setter opp andreHendelser session objekter
	    // setTransfusjonsObjects(); 
	     annenModel.setFormNames(sessionParams);
	     annenModel.distributeTerms();

	     Annenkomplikasjon annenKomplikasjon = null;
	     List klassifikasjoner = annenModel.getKlassifikasjoner();
	     
	     List<Diskusjon> diskusjoner = null;
	     List<Sak> saker = null;
//	     List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
		 Melder melder = null;
	     if (annenModel.getVigilansmelding().getMeldingsnokkel() != null){
	    	 displayPart = "block";
	    	 datePart = "none";
	    	 Vigilansmelding melding = (Vigilansmelding)annenModel.getAnnenKomplikasjon();
	    	 annenKomplikasjon = annenModel.getAnnenKomplikasjon();
	    	 annenModel.setHendelseDato(melding.getDatoforhendelse());
	    	 annenModel.setMeldingsNokkel(melding.getMeldingsnokkel());
	    	 klassifikasjoner = (List)sessionAdmin.getSessionObject(request, klassifikasjonKey);
//==  Hent alle diskusjoner og merknader   =======	    	 
	    	 Long meldeId = melding.getMeldeid();
	
	    	 melder = hentMelder(annenModel.getVigilansmelding());

	     	 
	   	     String mmKey = melding.getMeldingsnokkel(); //
    	     Long orgmeldeId = getmeldingsNokkelsak(mmKey); //Hent meldingsid for første melding med samme meldingsnøkkel
	    	 Map<String,List> diskusjonene = saksbehandlingWebservice.collectDiskusjoner(meldeId);
	    	 Map<String,List> orgMapdiskusjoner = null;
	    	 String omKey = "";
	    	 List<Diskusjon> orgdiskusjoner = null;
	    	 if (orgmeldeId != null){
	    		 orgMapdiskusjoner = saksbehandlingWebservice.collectDiskusjoner(orgmeldeId); // Henter diskusjoner fra tidligere meldinger med samme nøkkel
	    		 omKey = String.valueOf(orgmeldeId.longValue());
	    		 orgdiskusjoner =  orgMapdiskusjoner.get(omKey);
	    	 }

	    	 String mKey = String.valueOf(meldeId.longValue());
	    	 diskusjoner = diskusjonene.get(mKey);
	    	 if (diskusjoner == null)
	    		 diskusjoner = new ArrayList<Diskusjon>();
	    	 if (orgdiskusjoner != null)
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
    	 
    	 SimpleScalar tilMelder = new SimpleScalar(tilMelderPart);
    	 dataModel.put(tilMelding,tilMelder);
    	 
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(displaydateKey, hendelseDate);
    	 dataModel.put(statusflagKey,statusflag);
	     dataModel.put(andreHendelseId, annenModel);
	     dataModel.put(annenHendelseId, annenKomplikasjon);
	     dataModel.put(klassifikasjonKey, klassifikasjoner);
	 	dataModel.put(behandlingsFlaggKey, diskusjoner);
	 	
	 	dataModel.put(tilmelderKey, melder);
	 	
	     sessionAdmin.setSessionObject(getRequest(), annenModel,andreHendelseId);
	     
	     
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
    	
    	
	     login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
	     sakbehandlerShow = "none";
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
    		Parameter statusChange = form.getFirst("btnstatuschange");
    		Parameter avslutt = form.getFirst("btnavslutt");
    		
    		Parameter sendTilmelder = form.getFirst("btnsend");
    		if (sendTilmelder != null){ // Ønsker å sende melding til melder
    			setDiplayvalues(dataModel,melder);
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_andrehendelser.html"));
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
    		 	sessionAdmin.removesessionObject(request, diskusjonsKey);
    		 	sessionAdmin.removesessionObject(request, sakModelKey);
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
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_andrehendelser.html"));
    			 
    			Representation andreHendelser = clres2.get();
//        		invalidateSessionobjects();
        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);
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
    			for (Parameter entry : form) {
        			if (entry.getValue() != null && !(entry.getValue().equals(""))){
        					System.out.println("Saksmerknader "+entry.getName() + "=" + entry.getValue());
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
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_andrehendelser.html"));
    			 
    			Representation andreHendelser = clres2.get();
//        		invalidateSessionobjects();
        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);
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
    			clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_andrehendelser.html"));
 
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
