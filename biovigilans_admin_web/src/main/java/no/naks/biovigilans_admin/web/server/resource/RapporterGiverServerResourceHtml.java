package no.naks.biovigilans_admin.web.server.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.DonasjonImpl;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.GiverImpl;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.GiverkomplikasjonImpl;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.GiveroppfolgingImpl;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.biovigilans.model.KomplikasjonsdiagnosegiverImpl;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.model.DonasjonwebModel;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.KomDiagnosegiverwebModel;
import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.model.SakModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.ibm.icu.util.Calendar;

import freemarker.template.SimpleScalar;

/**
 * @author olj
 *  Denne resursen er knyttet til siden for å rapportere giverhendelser 
 */
public class RapporterGiverServerResourceHtml extends SaksbehandlingSessionServer {
	
	private String statusflagKey = "statusflag";

	
	public RapporterGiverServerResourceHtml() {
		super();
		// TODO Auto-generated constructor stub
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
		Vigilansmelding	melding = (Vigilansmelding) giverModel.getGiverKomplikasjon();
		Date datoforhendelse =  melding.getDatoforhendelse();
		melding.setDatoforhendelse(datoforhendelse);
		String statusCode = "";
		Long meldeId = melding.getMeldeid();
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
			giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
			hendelseWebService.updateVigilansmelding(giverModel.getVigilansmelding());
				if (statusCode == null){
				statusCode = statusflag[0];
				giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
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
	
//   			setDiplayvalues(dataModel);
		clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,giverhendelsesskjema));
		 
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
	/**
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {

		//invalidateSessionobjects();
	    // Reference reference = new Reference(getReference(),"..").getTargetRef();
	

/*
 * En Hashmap benyttes dersom en html side henter data fra flere javaklasser.	
 * Hver javaklasse får en id (ex pasientkomplikasjonId) som er tilgjengelig for html
 *      
*/	     
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	
	     Request request = getRequest();
	     setDBSource(request);
	     String db =  sessionAdmin.getChosenDB(request);
	     if (db != null && !db.equals("hemovigilans"))
	    	 giverhendelsesskjema = cellerogvevgiverhendelser;
	    /* LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	*/
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,giverhendelsesskjema));
	    
//	     setTransfusjonsObjects(); // Setter opp alle session objekter
/*
 * Lager alltid nye session model objekter
 * Dersom meldingsnøkkel er angitt så vil session model objektene få modelobjekter fra databasen.	     
 */
	     SakModel sakModel = (SakModel)sessionAdmin.getSessionObject(request,  sakModelKey);
	     if (sakModel == null){
	    	 sakModel = new SakModel();
	    	 sessionAdmin.setSessionObject(request, sakModel, sakModelKey);
	     }
	     sakModel.setMerknader(merknader);
	     sakModel.setStatusflag(statusflag);
	     
	     setGiverhendelser(); // Setter opp giverHendelser session objekter
	     List diskusjoner = null;
	     List<Sak> saker = null;
	     
	     Giver giver = giverModel.getGiver();
	     Donasjon donasjonen = giverModel.getDonasjonen();
	     Komplikasjonsdiagnosegiver komplikasjonsdiagnoseGiver = giverModel.getKomplikasjonsdiagnoseGiver();
		 Giveroppfolging giveroppfolging = giverModel.getGiveroppfolging();
		 giverKomplikasjon = (Giverkomplikasjon)giverModel.getGiverKomplikasjon();
         giverMeldingene = (List<Vigilansmelding>)sessionAdmin.getSessionObject(request,giverMeldingKey);
		 List<Giverkomplikasjon> giverListe =(List) sessionAdmin.getSessionObject(request,reportGiverKey);
		 Melder melder = null;
	     if (giverModel.getVigilansmelding().getMeldingsnokkel() != null){
	    	 displayPart = "block";
	    	 datePart = "block";
	    	 Vigilansmelding melding = (Vigilansmelding)giverModel.getGiverKomplikasjon();
	    	 makeSequence(request, melding);
	    	 setGivermelding();
	    	 giverModel.setVigilansmelding(melding); // Fjernet kommentert ut. Denne linjen var kommentert ut OLJ 07.10.15
	    	//==  Hent alle diskusjoner og merknader   =======	    	 
	    	 Long meldeId = melding.getMeldeid();
		    	
	    	 String mKey = melding.getMeldingsnokkel();
	   
	   	     Long orgmeldeId = getmeldingsNokkelsak(mKey); //Hent meldingsid for første melding med samme meldingsnøkkel
	   		 String mnKey = String.valueOf(meldeId.longValue());
	    	 Map<String,List> diskusjonene = saksbehandlingWebservice.collectDiskusjoner(meldeId);
	     	 diskusjoner = diskusjonene.get(mKey);
	    	 String omKey = "";
	    	 Map<String,List> orgMapdiskusjoner = null;
	    	 List<Diskusjon> orgdiskusjoner = null;
	    	 boolean sameKey = false;
	    	 if (orgmeldeId != null){
	    		 orgMapdiskusjoner = saksbehandlingWebservice.collectDiskusjoner(orgmeldeId); // Henter diskusjoner fra tidligere meldinger med samme nøkkel
	    		 omKey = String.valueOf(orgmeldeId.longValue());
	    		 orgdiskusjoner =  orgMapdiskusjoner.get(omKey);
	    		 sameKey = mnKey.equals(omKey);
	    	 }	  
	    	 /*
	    	  * For å vise historikk	    	 
	    	  */
	    	 Vigilansmelding orgVigilansmelding = null;
	    	 giverModel.getTidligereVigilans().clear();
	    	 giverModel.getTidligereGiverkomp().clear();
	    	 giverModel.getTidligereDonasjoner().clear();
	    	 giverModel.getTidligereGivere().clear();
	    	 giverModel.getTidligereKomplikasjonsdiagnoser().clear();
	    	 giverModel.getTidligereOppfolging().clear();
	    	 
	    	 for (Vigilansmelding nestemelding: giverMeldingene){
	    		 if (orgmeldeId != null && nestemelding.getMeldingsnokkel().equals(mKey) && nestemelding.getMeldeid().longValue() != meldeId.longValue() ){
	    			 orgVigilansmelding = nestemelding;
	    			 Long orgMeldId = nestemelding.getMeldeid();
	    			 String oMKey = String.valueOf(orgMeldId.longValue());
	    			 Map<String,List> histMeldingsdetaljer = null;
					 histMeldingsdetaljer = (Map<String,List>)saksbehandlingWebservice.selectMeldinger(oMKey); // Hent historikk
					 if (histMeldingsdetaljer != null){
				   			List givere = histMeldingsdetaljer.get(giverenKey);
			    			List donasjoner = histMeldingsdetaljer.get(donasjonKey);
			    			List giveroppfolginger = histMeldingsdetaljer.get(giverOppfolgingKey);
			    			List komplikasjonsdiagnosergiver = histMeldingsdetaljer.get(giverkomplikasjondiagnoseKey);
			    			giverModel.getTidligereDonasjoner().addAll(donasjoner);
			    			giverModel.getTidligereGivere().addAll(givere);
			    			giverModel.getTidligereKomplikasjonsdiagnoser().addAll(komplikasjonsdiagnosergiver);
			    			giverModel.getTidligereOppfolging().addAll(giveroppfolginger);
			    			
					 }
	    			 giverModel.getTidligereVigilans().add(orgVigilansmelding);
	    		 }
	    	 }
	    	 List<Vigilansmelding> vigilans =  giverModel.getTidligereVigilans();
	    	 for (Vigilansmelding orgmelding : vigilans){
	    		 for (Giverkomplikasjon komplikasjon : giverListe){
	    			 if (orgmeldeId != null && komplikasjon.getMeldeid().longValue() == orgmelding.getMeldeid().longValue() ){
	    				 Giverkomplikasjon orgGiverkomplikasjon = komplikasjon;
	    				 displayorgInfo = "block";
	    				 sessionAdmin.setSessionObject(request, displayorgInfo,displayorgInfoKey );
	    				 giverModel.getTidligereGiverkomp().add(orgGiverkomplikasjon);
	    			 }
	    		 }		    		 
	    	 }	    	 

	    	 
	    	 String mmKey = String.valueOf(meldeId.longValue());
	    	 diskusjoner = diskusjonene.get(mmKey);	    	 
	    	 if (diskusjoner == null)
	    		 diskusjoner = new ArrayList<Diskusjon>();
	    	 if (orgdiskusjoner != null)
	    		 diskusjoner.addAll(orgdiskusjoner);
	    	 if (!diskusjoner.isEmpty()){
	    		 checkDiskusjoner(diskusjoner);
	    	 }
	    	 if (orgMapdiskusjoner != null && !sameKey)
	    		 diskusjonene.putAll(orgMapdiskusjoner);
	    	 
	    	 sessionAdmin.setSessionObject(request, diskusjonene, diskusjonsKey);
	    	 melder = hentMelder(giverModel.getVigilansmelding());
 /*
 * Hent saker til diskusjonene 	    	 
 */
 	    	 setsaksbehandlerTildiskusjon(request, diskusjoner, saker);
	    	 if(giverKomplikasjon.getDatosymptomer() == null){
	    			Calendar kalender = Calendar.getInstance();
	    			int month = kalender.get(Calendar.MONTH);
	    			kalender.set(Calendar.MONTH, month+1);
	    			giverKomplikasjon.setDatosymptomer(kalender.getTime());
	    			giverKomplikasjon.setSymptomMelding("Fiktiv ikke angitt: ");
	    	 }
	    	 
	    	 giverModel.setHendelseDato(melding.getDatoforhendelse());
	    	 giverModel.setMeldingsNokkel(melding.getMeldingsnokkel());
	    	 donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(request, donasjonId);
	    	 giver = (Giver)sessionAdmin.getSessionObject(request,  giverenKey);
	    	 giverModel.setGiver(giver);
	    	 donasjonen = (Donasjon) sessionAdmin.getSessionObject(request,  donasjonKey);
	    	 giveroppfolging = (Giveroppfolging) sessionAdmin.getSessionObject(request,giverOppfolgingKey);
	    	 giverModel.setGiveroppfolging(giveroppfolging);
	    	 giverModel.setDonasjonen(donasjonen);
	    	 donasjon.setDonasjon(donasjonen);
	    	 komplikasjonsdiagnoseGiver = (Komplikasjonsdiagnosegiver)sessionAdmin.getSessionObject(request,giverkomplikasjondiagnoseKey);
	    	 if (komplikasjonsdiagnoseGiver.getBivirkningbeskrivelse() == null || komplikasjonsdiagnoseGiver.getBivirkningbeskrivelse().isEmpty())
	    		 komplikasjonsdiagnoseGiver.setBivirkningbeskrivelse("Ikke angitt");
	    	 komDiagnosegiver.setKomDiagnosegiver(komplikasjonsdiagnoseGiver);
	    	 giverModel.setKomplikasjonsdiagnoseGiver(komplikasjonsdiagnoseGiver);
	    	 
	     }
/*	   
 * Alle model objekter som benyttes i html må settes med initielle verdier dersom
 * dette er en ny melding
 *  Dette er flyttet til giverkomplikasjonswebmodel se funksjonen  setTransfusjonsObjects(); !!  
*/	    
		 melder = hentMelder(giverModel.getVigilansmelding());
	  	 SimpleScalar tilMelder = new SimpleScalar(tilMelderPart);
    	 dataModel.put(tilMelding,tilMelder);
    	 

    	 SimpleScalar simple = new SimpleScalar(displayPart);
    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		 SimpleScalar orgInfo = new SimpleScalar(displayorgInfo);
    	 SimpleScalar iconImportant = new SimpleScalar(imagesrcImportant);
    	 dataModel.put(imageImportantkey,iconImportant);
    	 dataModel.put(tilMelding,tilMelder);
    	 dataModel.put(displayorgInfoKey, orgInfo);    	 
    	 
 	 	 dataModel.put(behandlingsFlaggKey, diskusjoner);
    	 dataModel.put(statusflagKey,statusflag);
    	 dataModel.put(giverkomplikasjonKey, giverKomplikasjon);
    	 dataModel.put(giverenKey, giver);
     	 dataModel.put(donasjonKey, donasjonen);
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(displaydateKey, hendelseDate);
	     dataModel.put(giverkomplikasjonId, giverModel);
	     dataModel.put(donasjonId, donasjon);
	     dataModel.put(komDiagnosegiverId, komDiagnosegiver);
	 	dataModel.put(tilmelderKey, melder);
    	 dataModel.put(giverOppfolgingKey,giveroppfolging);
    	 dataModel.put(giverkomplikasjondiagnoseKey,komplikasjonsdiagnoseGiver);
	     
	     sessionAdmin.setSessionObject(request, giverModel,giverkomplikasjonId);
	     sessionAdmin.setSessionObject(request, donasjon,donasjonId);
	     sessionAdmin.setSessionObject(request, komDiagnosegiver, komDiagnosegiverId);
        Representation givertkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(givertkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML ); 
		 return templatemapRep;
	 }
	

	
	
	public String[] getAldergruppe() {
		return aldergruppe;
	}

	public void setAldergruppe(String[] aldergruppe) {
		this.aldergruppe = aldergruppe;
	}

	public String[] getKjonnValg() {
		return kjonnValg;
	}

	public void setKjonnValg(String[] kjonnValg) {
		this.kjonnValg = kjonnValg;
	}
	public String[] getReaksjonengruppe() {
		return reaksjonengruppe;
	}

	public void setReaksjonengruppe(String[] reaksjonengruppe) {
		this.reaksjonengruppe = reaksjonengruppe;
	}
	
	public String[] getUtenforBlodbankengruppe() {
		return utenforBlodbankengruppe;
	}

	public void setUtenforBlodbankengruppe(String[] utenforBlodbankengruppe) {
		this.utenforBlodbankengruppe = utenforBlodbankengruppe;
	}
	
	
	
	public String[] getDonasjonsstedgruppe() {
		return donasjonsstedgruppe;
	}

	public void setDonasjonsstedgruppe(String[] donasjonsstedgruppe) {
		this.donasjonsstedgruppe = donasjonsstedgruppe;
	}

	/**
     * storeHemovigilans
     * Denne rutinen tar imot alle ny informasjon fra bruker om den rapporterte hendelsen
     * @param form
     * @return
     */
    /**
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	TemplateRepresentation  templateRep = null;
    	Request request = getRequest();
    	setDBSource(request);
	     String db =  sessionAdmin.getChosenDB(request);
	     if (db != null && !db.equals("hemovigilans"))
	    	 giverhendelsesskjema = cellerogvevgiverhendelser;
    	if (form == null){
    		sessionAdmin.getSession(request,giverkomplikasjonId).invalidate();
    		sessionAdmin.getSession(request, donasjonId).invalidate();
    		sessionAdmin.getSession(request, komDiagnosegiverId).invalidate();
    	}
    	   login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
    	if (form != null){
    		
    		giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(request,giverkomplikasjonId);
    		donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(request, donasjonId);
    		komDiagnosegiver = (KomDiagnosegiverwebModel) sessionAdmin.getSessionObject(request,komDiagnosegiverId );
    //		giverKvittering = (GiverKvitteringWebModel)sessionAdmin.getSessionObject(request,kvitteringGiverId);
   	     	melderwebModel = ( MelderwebModel)sessionAdmin.getSessionObject(request,melderId);
   	        Melder melder = null;
    		Parameter logout = form.getFirst("avbryt4");
    		Parameter lukk = form.getFirst("lukk4");
    	     Map<String, Object> dataModel = new HashMap<String, Object>();
    	     
    	     SakModel sakModel = (SakModel)sessionAdmin.getSessionObject(request,  sakModelKey);
    	     if (sakModel == null){
    	    	 sakModel = new SakModel();
    	    	 sessionAdmin.setSessionObject(request, sakModel, sakModelKey);
    	     }
    		sakModel.setFlaggNames(flaggNames);
    	     Map<String,List> diskusjonene = (Map<String,List>)sessionAdmin.getSessionObject(request, diskusjonsKey);
    	     
    		if (logout != null || lukk != null){
    			sessionAdmin.getSession(request,giverkomplikasjonId).invalidate();
    			sessionAdmin.getSession(request, donasjonId).invalidate();
    			sessionAdmin.getSession(request, komDiagnosegiverId).invalidate();
    			sessionAdmin.getSession(request,kvitteringGiverId).invalidate();
    			
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemivigilans/Logout.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    	/*	templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
	    				MediaType.TEXT_HTML);
	    	*/	
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, giverModel,
	    				MediaType.TEXT_HTML);
    			return templateRep; // return a new page!!!
    		}
    		
    		if (giverModel == null){
    			giverModel = new GiverKomplikasjonwebModel();
    			// giverModel.setFormNames(sessionParams);
    		}
 
    		if(donasjon==null){
    			donasjon = new DonasjonwebModel();
    			donasjon.setFormNames(sessionParams);
    		}
    		
    		if(komDiagnosegiver == null){
    	    	 komDiagnosegiver = new KomDiagnosegiverwebModel();
    	    	 komDiagnosegiver.setFormNames(sessionParams);
    	     }
    		
    	/*	if (giverKvittering == null){
    			giverKvittering = new GiverKvitteringWebModel();
    			giverKvittering.setFormNames(sessionParams);
		     }*/
    		
   		for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals("")))
    					System.out.println(entry.getName() + "=" + entry.getValue());
    			giverModel.setValues(entry);
    			donasjon.setValues(entry);
    			komDiagnosegiver.setValues(entry);
/*    			giverKvittering.setValues(entry);
    			if(entry.getName().equalsIgnoreCase("tab-annenreak")){
    				giverKvittering.annenreakList.add(entry.getValue()) ;
    			}*/
    		}

    		
    		Giver giver = (Giver)sessionAdmin.getSessionObject(request,  giverenKey);
    		Donasjon donasjonen = (Donasjon) sessionAdmin.getSessionObject(request,  donasjonKey);
	    	Giveroppfolging giveroppfolging = (Giveroppfolging) sessionAdmin.getSessionObject(request,giverOppfolgingKey);
	    	Komplikasjonsdiagnosegiver komplikasjonsdiagnoseGiver = (Komplikasjonsdiagnosegiver)sessionAdmin.getSessionObject(request,giverkomplikasjondiagnoseKey);
	    	Vigilansmelding melding = (Vigilansmelding)giverModel.getGiverKomplikasjon();
	   	    melder = hentMelder(giverModel.getVigilansmelding());
	   	    
	   	    
/*	  	     String mmKey = melding.getMeldingsnokkel(); //
    	     Long orgmeldeId = getmeldingsNokkelsak(mmKey); //Hent meldingsid for første melding med samme meldingsnøkkel
    		 Map<String,List> orgMapdiskusjoner = null;
	    	 String omKey = "";
	    	 List orgdiskusjoner = null;
	    	 if (orgmeldeId != null){
	    		 orgMapdiskusjoner = saksbehandlingWebservice.collectDiskusjoner(orgmeldeId); // Henter diskusjoner fra tidligere meldinger med samme nøkkel
	    		 omKey = String.valueOf(orgmeldeId.longValue());
	    		 orgdiskusjoner =  orgMapdiskusjoner.get(omKey);
	    	 }
	    	 
	    	Long meldeId = melding.getMeldeid();
  		  	String mKey = String.valueOf(meldeId.longValue());
  		    List<Diskusjon>diskusjoner = diskusjonene.get(mKey);
  	    	 if (diskusjoner == null)
	    		 diskusjoner = new ArrayList<Diskusjon>(); 
  
  	    	 if (orgdiskusjoner != null)
	    		 diskusjoner.addAll(orgdiskusjoner);
  	     	 if (!diskusjoner.isEmpty()){
	    		 checkDiskusjoner(diskusjoner); // Sjekker om diskusjonene inneholder medling til melder
	    	 }*/
		    Long gmlMeldeid = sakModel.getGmlMeldeid();
	   	 	Long meldeId = melding.getMeldeid();
  		  	String mKey = String.valueOf(meldeId.longValue());  
  		    if (gmlMeldeid != null){
  		    	mKey = String.valueOf(gmlMeldeid.longValue());
  		    }
  		    List<Diskusjon>diskusjoner = diskusjonene.get(mKey); 
 		 	dataModel.put(behandlingsFlaggKey, diskusjoner); 	    	 
	    	giverKomplikasjon = (Giverkomplikasjon)giverModel.getGiverKomplikasjon();
    		sessionAdmin.setSessionObject(request, giverModel,giverkomplikasjonId);
    		sessionAdmin.setSessionObject(request, donasjon, donasjonId);
    		sessionAdmin.setSessionObject(request, komDiagnosegiver, komDiagnosegiverId);
    	    sessionAdmin.setSessionObject(request, melderwebModel,melderId);
    		SimpleScalar tilMelder = new SimpleScalar(tilMelderPart);
     	    dataModel.put(tilMelding,tilMelder);
    	     displayPart = "block";
	    	 datePart = "none";
/*
 * For å vise info fra tidligere meldinger	    	 
 */
	    	 displayorgInfo = (String)sessionAdmin.getSessionObject(request, displayorgInfoKey);
	    	 if (displayorgInfo == null || displayorgInfo.isEmpty())
	    		 displayorgInfo = "none";
    	   	 SimpleScalar simple = new SimpleScalar(displayPart);
        	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
    		 SimpleScalar orgInfo = new SimpleScalar(displayorgInfo);
        	 SimpleScalar iconImportant = new SimpleScalar(imagesrcImportant);
        	 dataModel.put(imageImportantkey,iconImportant);
  
        	 dataModel.put(displayorgInfoKey, orgInfo);    	 
        	 dataModel.put(statusflagKey,statusflag);
        	 dataModel.put(giverkomplikasjonKey, giverKomplikasjon);
        	 dataModel.put(giverenKey, giver);
         	 dataModel.put(donasjonKey, donasjonen);
        	 dataModel.put(displayKey, simple);
        	 dataModel.put(displaydateKey, hendelseDate);
    	     dataModel.put(giverkomplikasjonId, giverModel);
    	     dataModel.put(donasjonId, donasjon);
    	     dataModel.put(komDiagnosegiverId, komDiagnosegiver);
    	     
        	 dataModel.put(giverOppfolgingKey,giveroppfolging);
        	 dataModel.put(giverkomplikasjondiagnoseKey,komplikasjonsdiagnoseGiver);
    		dataModel.put(giverkomplikasjonId, giverModel);
    		 dataModel.put(tilmelderKey, melder);
    	//	dataModel.put(kvitteringGiverId, giverKvittering);
    		
    		Parameter lagre = form.getFirst("btnSendinn");
       		Parameter lagreFlagg = form.getFirst("btnlagreflagg");
    		Parameter statusChange = form.getFirst("btnstatuschange");
    		Parameter avslutt = form.getFirst("btnavslutt");
    		Parameter sendTilmelder = form.getFirst("btnsend");
    		
    		if (sendTilmelder != null){
       			meldingsType = "giver";
       			sessionAdmin.setSessionObject(request,meldingsType, meldingstypeKey);
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,giverhendelsesskjema));
    			 
    			Representation giverHendelser = clres2.get();
 //       		invalidateSessionobjects();
        		templateRep = new TemplateRepresentation(giverHendelser, dataModel,
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
   			    List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
/*
 * Fjerner saksgangen og diskusjonene fra session
 */
   	   			sessionAdmin.removesessionObject(request, displayorgInfoKey);
    		 	sessionAdmin.removesessionObject(request, diskusjonsKey);
    		 	sessionAdmin.removesessionObject(request, sakModelKey);
    			sessionAdmin.setSessionObject(request, newLogin, loginKey);
   			    sessionAdmin.setSessionObject(request, saksbehandlere, behandlereKey);
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));
   			 
    			Representation giverHendelser = clres2.get();
//        		invalidateSessionobjects();
        		templateRep = new TemplateRepresentation(giverHendelser, dataModel,
        				MediaType.TEXT_HTML);
        		redirectPermanent("../hemovigilans/saksbehandling.html");
        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
    		}
    		if (statusChange != null){// Bruker har valgt å endre saksstatus
       			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,giverhendelsesskjema));
       			templateRep = statusChange(form, request, dataModel, melder, diskusjoner, clres2);
    			
    			
/*  Dette er nå flyttet til egen rutine - se over
 *      			melding = (Vigilansmelding) giverModel.getGiverKomplikasjon();
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
    				giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
    				hendelseWebService.updateVigilansmelding(giverModel.getVigilansmelding());
       				if (statusCode == null){
    					statusCode = statusflag[0];
    					giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
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
    		
 //   			setDiplayvalues(dataModel);
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,giverhendelsesskjema));
    			 
    			Representation andreHendelser = clres2.get();
//        		invalidateSessionobjects();
        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);*/
        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
    		}
    		//Parameter ikkegodkjet = form.getFirst("ikkegodkjent");
    		//Parameter godkjet = form.getFirst("godkjent");
    		if (lagreFlagg != null){
       			melding = (Vigilansmelding) giverModel.getGiverKomplikasjon();
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
        					System.out.println(entry.getName() + "=" + entry.getValue());
        					if (entry.getName().equals("nystatus")){ // Bruker har også valgt å endre status til avvist
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
    			sakModel.saveSaker(melding.getMeldeid()); // Setter også saksbehandler navn til merknad
    			saksbehandlingWebservice.saveDiskusjon(sakModel.getDiskusjonsMappe());
    			sakModel.setSakdiskusjon();
    			saksbehandlingWebservice.saveSak(sakModel.getSaksMappe());
    			List nyediskusjoner = sakModel.lagDiskusjonsliste();
    			if (diskusjoner != null)
    				diskusjoner.addAll(nyediskusjoner);
      			
      			if (sakModel.isTilDialog()){
    				String mailTxt = sakModel.getHemovigilansDiskusjon().getKommentar() + " Meldingsnøkkel: " + giverModel.getVigilansmelding().getMeldingsnokkel()+ " Type Giverhendelse.";
        			dialogHemivigilans(request, sakModel.getDiskId(),mailTxt,giverModel.getVigilansmelding());
    			}
      			if (sakModel.isReklassifikasjon()){
     				giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusflag[8]); //Sett gammel melding til Erstattet OLJ 01.10.16
     				hendelseWebService.updateVigilansmelding(giverModel.getVigilansmelding());
      				sakModel.setGmlMeldeid(meldeId);
      				giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusflag[0]); //OBS !!?? Nødvendig her OLJ 04.10.16
      				savegiverReclassifikasjon();
//      			    giverMeldingene = (List<Vigilansmelding>)sessionAdmin.getSessionObject(request,giverMeldingKey);
//      			    giverMeldingene.add(giverModel.getVigilansmelding());
    			}
/*
 * En rutine for å sende epost til Helsedirektoratet dersom saksbehandler har valgt "Melde til Helsedirektoratet"
 * olj 03.02.16    			
 */
    			String mailText = (String)sakModel.getFormMap().get("meldingtilhelsedir")+ " Meldingsnummer: "+ melding.getMeldingsnokkel();
    			tilHelsedirektoratet(request, mailText, sakModel.getFormMap());      			
       			sakModel.setDiskusjonsMappe(null);
    			sakModel.setSaksMappe(null);
    			tilMelderPart = "none";
    			if (sakModel.isTilMelder())
    				tilMelderPart = "block";
    			tilMelder = null;
    		    tilMelder = new SimpleScalar(tilMelderPart);
    	    	dataModel.put(tilMelding,tilMelder); 		

    			dataModel.put(behandlingsFlaggKey, diskusjoner);
    			String newStatus = sakModel.setsakStatus(nyediskusjoner);
    			if (newStatus != null){
    				giverModel.getVigilansmelding().setSjekklistesaksbehandling(newStatus);
    				hendelseWebService.updateVigilansmelding(giverModel.getVigilansmelding());
    			}
//    			setDiplayvalues(dataModel);
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,giverhendelsesskjema));
    			if (avvist)
    				templateRep = statusChange(form, request, dataModel, melder, diskusjoner, clres2);
    			else{
    				Representation andreHendelser = clres2.get();
    				templateRep = new TemplateRepresentation(andreHendelser, dataModel,
        				MediaType.TEXT_HTML);
    			}    			 
//    			Representation andreHendelser = clres2.get();
//        		invalidateSessionobjects();
//        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
//        				MediaType.TEXT_HTML);
        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
    		}    		
    		if(lagre!=null){
    	
    	
    			giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusflag[1]);
    			hendelseWebService.updateVigilansmelding(giverModel.getVigilansmelding());
   
 
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,giverhendelsesskjema));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,MediaType.TEXT_HTML);
	    		
    		}else{
    			/*
    			 * Dette må gjøres om (OLJ 06.07.15
    			 */
    				
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,giverhendelsesskjema));
    			Representation pasientkomplikasjonFtl = clres2.get();
        		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
        				MediaType.TEXT_HTML);
			}
    		
    	}
    
    	return templateRep;
      
    }

}
