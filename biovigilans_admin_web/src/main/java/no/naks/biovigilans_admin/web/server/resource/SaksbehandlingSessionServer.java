package no.naks.biovigilans_admin.web.server.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.restlet.Request;

import no.naks.biovigilans.felles.model.MeldingModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;
/**
 * SaksbehandlingSessionServer
 * Denne klassen er superklassen til sakbehandling.
 * Den muliggjør saksbehandling av tre typer meldinger:
 * Pasientmeldinger (transfusjoner)
 * Givermeldinger
 * Andre meldinger
 * @author olj
 *
 */
public class SaksbehandlingSessionServer extends SessionServerResource {
	protected String[] pasientSession;
	protected String[] giverSession;
	protected String[] aldergruppeGiver;
	protected String[] merknader; // Inneholder tilgjengelige merknader som kan resulterer i at saksstatus endres
	protected String meldeKey = "meldinger";
	protected String[] statusflag;
	protected String statusflagKey = "statusflag";
	protected String sakModelKey = "sakModel";
	protected String behandlingsFlaggKey = "flaggliste";
	protected String diskusjonsKey = "diskusjoner";
	protected String behandlereKey = "behandlere"; // Nøkkel til liste over saksbehandlere
	protected String sakloginKey = "loggedin"; // Nøkkel til saksbehandler som er logget på
	protected String accountKey = "accountKey";
	protected String tilMelding = "tilmelder"; // Nøkkel for å sende til melder
	protected String tilMelderPart = "none";
	protected String meldingsType = ""; // Meldingstypen saksbehandler jobber med
	protected String meldingstypeKey = "meldingstype";
	protected String tilmelderKey = "melderen";	//Til bruk i skjermbildet til å sende melding til melder
	protected String tilmelderSendKey = "nymelding"; 	// Nøkkel til meldingen som skal sendes
	
	protected String tilMeldermsg;			//Standard melding til meldere
	protected String tilMeldertillegg;
	protected String tilsakbehandlereMsg;	//Standard melding til saksbehandlere 
	protected String[] flaggNames; // Inneholder navn på input felt i skjermbildet. Bruker velger ett eller flere saksflagg
/*
 * Benyttet til excel rapporter
 */
	protected String reportAndreKey = "reportandremeldinger"; // Sesjonsnøkkel for rapporter andre meldinger
	protected String reportGiverKey = "reportgivermeldinger"; // Sesjonsnøkkel for rapporter giver meldinger	
	protected String reportPasientKey = "reportpasientmeldinger"; // Sesjonsnøkkel for rapporter pasient meldinger
	
	protected String startPeriodKey = "startperiod"; //Session nøkler for start og slutt på angitt periode
	protected String endPeriodKey = "endperiod";
/*
 * 	Pekere til valgte skjermbilder etter valg av meldeordning
 */
	protected String hemovigilansandrehendelser = "/hemovigilans/rapporter_andrehendelser.html";
	protected String cellereogvevandrehendelser = "/cellerogvev/rapporter_andrehendelser.html";
	protected String hemovigilanstransfusjon = "/hemovigilans/rapporter_transfusjon.html";
	protected String hemovigilansgiverhendelser = "/hemovigilans/rapporter_giver.html";
	protected String cellerogvevtransfusjonhendelse = "/cellerogvev/rapporter_transfusjon.html";
	protected String cellerogvevgiverhendelser = "/cellerogvev/rapporter_giver.html";
	protected String andrehendelserskjema = "/hemovigilans/rapporter_andrehendelser.html";
	protected String giverhendelsesskjema = "/hemovigilans/rapporter_giver.html";
	protected String transfusjonhendelseskjema = "/hemovigilans/rapporter_transfusjon.html";
	
	public String[] getFlaggNames() {
		return flaggNames;
	}

	public void setFlaggNames(String[] flaggNames) {
		this.flaggNames = flaggNames;
	}

	public String getTilsakbehandlereMsg() {
		return tilsakbehandlereMsg;
	}

	public void setTilsakbehandlereMsg(String tilsakbehandlereMsg) {
		this.tilsakbehandlereMsg = tilsakbehandlereMsg;
	}

	public String getTilMeldermsg() {
		return tilMeldermsg;
	}

	public void setTilMeldermsg(String tilMeldermsg) {
		this.tilMeldermsg = tilMeldermsg;
	}

	public String getTilmelderSendKey() {
		return tilmelderSendKey;
	}

	public void setTilmelderSendKey(String tilmelderSendKey) {
		this.tilmelderSendKey = tilmelderSendKey;
	}

	public String getTilMeldertillegg() {
		return tilMeldertillegg;
	}

	public void setTilMeldertillegg(String tilMeldertillegg) {
		this.tilMeldertillegg = tilMeldertillegg;
	}

	public String[] getAldergruppeGiver() {
		return aldergruppeGiver;
	}

	public void setAldergruppeGiver(String[] aldergruppeGiver) {
		this.aldergruppeGiver = aldergruppeGiver;
	}

	public String getMeldingtilMelderKey() {
		return meldingtilMelderKey;
	}

	public void setMeldingtilMelderKey(String meldingtilMelderKey) {
		this.meldingtilMelderKey = meldingtilMelderKey;
	}

	public String getTilmelderKey() {
		return tilmelderKey;
	}

	public void setTilmelderKey(String tilmelderKey) {
		this.tilmelderKey = tilmelderKey;
	}

	public String getMeldingstypeKey() {
		return meldingstypeKey;
	}

	public void setMeldingstypeKey(String meldingstypeKey) {
		this.meldingstypeKey = meldingstypeKey;
	}

	public String getMeldingsType() {
		return meldingsType;
	}

	public void setMeldingsType(String meldingsType) {
		this.meldingsType = meldingsType;
	}

	public String getTilMelding() {
		return tilMelding;
	}

	public void setTilMelding(String tilMelding) {
		this.tilMelding = tilMelding;
	}

	public String getTilMelderPart() {
		return tilMelderPart;
	}

	public void setTilMelderPart(String tilMelderPart) {
		this.tilMelderPart = tilMelderPart;
	}

	public String[] getMerknader() {
		return merknader;
	}

	public void setMerknader(String[] merknader) {
		this.merknader = merknader;
	}

	public String getDiskusjonsKey() {
		return diskusjonsKey;
	}

	public void setDiskusjonsKey(String diskusjonsKey) {
		this.diskusjonsKey = diskusjonsKey;
	}

	public String getBehandlingsFlaggKey() {
		return behandlingsFlaggKey;
	}

	public void setBehandlingsFlaggKey(String behandlingsFlaggKey) {
		this.behandlingsFlaggKey = behandlingsFlaggKey;
	}

	public String getMeldeKey() {
		return meldeKey;
	}

	public void setMeldeKey(String meldeKey) {
		this.meldeKey = meldeKey;
	}

	public String getSakModelKey() {
		return sakModelKey;
	}

	public void setSakModelKey(String sakModelKey) {
		this.sakModelKey = sakModelKey;
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
	public String[] getPasientSession() {
		return pasientSession;
	}
	public void setPasientSession(String[] pasientSession) {
		this.pasientSession = pasientSession;
	}
	public String[] getGiverSession() {
		return giverSession;
	}
	public void setGiverSession(String[] giverSession) {
		this.giverSession = giverSession;
	}
	
	/**
	 * setDBSource
	 * Denne rutinen setter aktuell databasekilde som bruker har valgt
	 * Dette gjøres pr. request
	 */
	protected void setDBSource(Request request){
		String db =  sessionAdmin.getChosenDB(request);
		saksbehandlingWebservice.setAlterativeSource(db);
		annenKomplikasjonWebService.setAlterativeSource(db);
		donasjonWebService.setAlterativeSource(db);
		komplikasjonsklassifikasjonWebService.setAlterativeSource(db);
		komDiagnosegiverWebService.setAlterativeSource(db);
		hendelseWebService.setAlterativeSource(db);
//		melderWebService.setAlterativeSource(db); MeldertableService er i saksbehandlerWebservice !!
		setAlternativeSource(sessionAdmin.getChosenTemplate());

	}
	/**
	 * dialogHemovigilans
	 * Denne rutinen sjekker om det skal sendes epost til saksbehandlerne i Hemovigilansgruppen
	 * Det skjer når merknadene "Starte dialog i hemovigilangruppen".
	 * Rutinen henter også inn melder til meldingen
	 */
	protected void dialogHemivigilans(Request request,String diskId,String mailText,Vigilansmelding melding){
		List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
		String mId = String.valueOf(melding.getMeldeid().longValue());
		if (saksbehandlere != null && diskId != null){
			for (Saksbehandler saksbehandler : saksbehandlere){
				emailWebService.setSubject(merknader[1]);
	 	    	emailWebService.setEmailText(mailText);
		    	 emailWebService.setMailTo(saksbehandler.getBehandlerepost());
		    	 String msg = String.format(tilsakbehandlereMsg+mId+"&diskid="+diskId);
		    	 String name = saksbehandler.getBehandernavn();
		    	 if (!name.equals("Helsedirektoratet"))
		    		 emailWebService.sendEmail(msg); //Kommentert bort til stage !!
				
			}

		}
		Melder melder = hentMelder(melding);
	 	sessionAdmin.setSessionObject(request, melder, tilmelderKey);
	}
	/**
	 * tilHelsedirektoratet
	 * Denne rutinen sender melding til Helsedirektoratet dersom merknad for dette er satt
	 * Rutinen sender også meldingen til saksbehander
	 * @param request
	 * @param mailText
	 * @param sakMap
	 */
	protected void tilHelsedirektoratet(Request request,String mailText,Map<String,String> sakMap){
		List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
		String loginNavn = login.getSaksbehandler().getBehandernavn();

		if (mailText == null || mailText.equals(""))
			mailText = " - Ingen kommentar fra saksbehandler";
		String flaggMerknad = sakMap.get(flaggNames[2]);
		String msg = String.format("%nDenne saken er meldt til Helsedirektoratet%n");
		if (flaggMerknad != null && saksbehandlere != null){
			for (Saksbehandler saksbehandler : saksbehandlere){
				emailWebService.setMailTo(saksbehandler.getBehandlerepost());
				emailWebService.setSubject(merknader[2]);
	 	    	emailWebService.setEmailText(mailText);
	 	    	 String name = saksbehandler.getBehandernavn();
		    	 if (name.equals("Helsedirektoratet") || name.equals(loginNavn))
		    		 emailWebService.sendEmail(msg); 
			}
		}
	}
	/**
	 * checkdiskusjoner
	 * Denne rutinene sjekker om diskusjonen inneholder melding til melder.
	 * Dersom det er tilfellet vises knapp for sende melding til melder
	 * @param diskusjoner
	 */
	protected void checkDiskusjoner(List<Diskusjon> diskusjoner){
		for (Diskusjon diskusjon : diskusjoner){
			if (diskusjon.getKommentar().equals("Starte dialog med melder")){
				tilMelderPart = "block";
			}
		}
	}
	/**
	 * hentMeldingstyper
	 * Denne rutinen setter meldingstype til meldingsuttvalget
	 * Verdien settes i feltet meldingstype
	 * @param meldinger
	 * @return
	 */
	protected List<Vigilansmelding> hentMeldingstyper(List<Vigilansmelding> meldinger){
		/*
		 * Finne meldinstyper:	    
		 */
		  		Request request = getRequest();
			    Map andreMeldinger = saksbehandlingWebservice.collectAnnenMeldinger(meldinger);
			    List<Annenkomplikasjon> annenListe =(List) andreMeldinger.get(andreKey);
			    List<Pasientkomplikasjon> pasientListe = (List) andreMeldinger.get(pasientKey);
			    List<Giverkomplikasjon> giverListe = (List)  andreMeldinger.get(giverKey);
			    sessionAdmin.setSessionObject(request, annenListe, reportAndreKey);
			    sessionAdmin.setSessionObject(request, pasientListe, reportPasientKey);
			    sessionAdmin.setSessionObject(request, giverListe, reportGiverKey);
			    
			    for (Vigilansmelding melding: meldinger){
			    	melding.setMeldingstype("Ukjent");
			    	if (melding.getSjekklistesaksbehandling() == null || melding.getSjekklistesaksbehandling().isEmpty()){
			    		melding.setSjekklistesaksbehandling("Levert");
			    	}
			    	for (Annenkomplikasjon annenKomplikasjon : annenListe){
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
			    	}
			    }	    
			    return meldinger;
	}
	/**
	 * hentMeldingene
	 * Denne rutinen henter meldinger basert på en tidperiode gitt som datoer
	 * @param start Start dato for perioden
	 * @param end Sluttdato for perioden
	 * @return
	 */
	public List<Vigilansmelding> hentMeldingene(String start,String end){
		 List<Vigilansmelding> meldinger = null;
		 meldinger = saksbehandlingWebservice.collectMessages(start, end);
		 sorterMeldinger(meldinger);
		 for (Vigilansmelding melding: meldinger){
		    	if (melding.getSjekklistesaksbehandling() == null){
		    		melding.setSjekklistesaksbehandling("Levert");
		    	}
		 }
		return hentMeldingstyper(meldinger);
	}
	/**
	 * hentanonymeMeldinger
	 * Denne rutinen henter meldinger som er meldt anonymt eller som mangler melderid
	 * @return Liste over saksbehandlers meldinger
	 */
	public List<Vigilansmelding> hentanonymeMeldinger(){
		 List<Vigilansmelding> meldinger = null;
		 meldinger = saksbehandlingWebservice.collectanonymemeldinger();
		    for (Vigilansmelding melding: meldinger){
		    	if (melding.getSjekklistesaksbehandling() == null){
		    		melding.setSjekklistesaksbehandling("Levert");
		    	}
		    }
	    
		 return hentMeldingstyper(meldinger);

	}
	/**
	 * hentMineMeldinger
	 * Denne rutinen henter meldinger til saksbehandler
	 * @param Long saksbehandlerid id til pålogget saksbehandler
	 * @return Liste over saksbehandlers meldinger
	 */
	public List<Vigilansmelding> hentMineMeldinger(Long saksbehandlerid){
		 List<Vigilansmelding> meldinger = null;
		 meldinger = saksbehandlingWebservice.collectMessagesSaksbehandler(saksbehandlerid);
		    for (Vigilansmelding melding: meldinger){
		    	if (melding.getSjekklistesaksbehandling() == null){
		    		melding.setSjekklistesaksbehandling("Levert");
		    	}
		    }
	    
		 return hentMeldingstyper(meldinger);

	}
	/**
	 * hentMeldingene
	 * Denne rutinen henter meldinger basert på definert utvalg
	 * @param status Utvalg status for meldingene eller null
	 * @return
	 */
	public List<Vigilansmelding> hentMeldingene(String status){
		 List<Vigilansmelding> meldinger = null;

		if (status != null && !status.equals(statusflag[6])){
			meldinger = saksbehandlingWebservice.collectMessages(status); 
		}else
			meldinger = saksbehandlingWebservice.collectMessages(); // Henter alle meldinger
	    sorterMeldinger(meldinger);
	    for (Vigilansmelding melding: meldinger){
	    	if (melding.getSjekklistesaksbehandling() == null){
	    		melding.setSjekklistesaksbehandling("Levert");
	    	}
	    }
    
	    return hentMeldingstyper(meldinger);
	}
	/**
	 * hentmeldingMerknader
	 * Denne rutinen henter meldinger basert på en valgt merknad
	 * @param merknad
	 * @return
	 */
	public List <Vigilansmelding> hentMeldingMerknader(String merknad){
		List<Vigilansmelding> meldinger = null;
		meldinger = saksbehandlingWebservice.collectMessagesMarks(merknad);
		return hentMeldingstyper(meldinger);
	}
	/**
	 * hentMelder
	 * Denne rutinen henter melder til en aktuell melding
	 * @param melding
	 * @return
	 */
	public Melder hentMelder(Vigilansmelding melding){
	 Melder melder = null;    
   	 if (melding.getMelderId() != null && melding.getMelderId().longValue() != 0)
			 melder = saksbehandlingWebservice.collectmelder(melding.getMelderId());
     if (melder == null){
    	 melder = new MelderImpl();
    	 melder.setMeldernavn("ukjent");
    	 melder.setMeldertlf("ukjent");
		 melder.setMelderepost("ukjent");
		 melder.setSykehus("ukjent");
		 melder.setHelseforetak("ukjent");
		 melder.setHelseregion("ukjent");
		 
     }
		return melder;
	}
	
	/**
	 * setsaksbehandlerTildiskusjon
	 * Denne rutinen setter saksbehandlernavn til en diskusjon 
	 * @param request
	 * @param diskusjoner
	 * @param saker
	 */
	public void setsaksbehandlerTildiskusjon(Request request, List<Diskusjon> diskusjoner, List<Sak> saker){
	 Map<String,List> diskusjonene = (Map<String, List>) sessionAdmin.getSessionObject(request, diskusjonsKey);
	 List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
   	 for (Diskusjon diskusjon : diskusjoner){
		 Long dId = diskusjon.getDiskusjonid();
		 String diskId = "s" + String.valueOf(dId.longValue());
		 if (saker == null)
			 saker = diskusjonene.get(diskId);
		 else{
			 if (diskusjonene.get(diskId) != null)
				 saker.addAll(diskusjonene.get(diskId));
		 }
	 }
		 

	 if (saker != null){
		 for (Sak sak :saker){
			 Long sakbehId = sak.getSakbehandlerId();
			 if (saksbehandlere != null){
				 for (Saksbehandler saksbehandler : saksbehandlere){
					 if (saksbehandler.getSakbehandlerid().longValue() == sakbehId.longValue()){
						 // Riktig saksbehandler settes til riktig diskusjon
						 Long dId = sak.getDiskusjonid();
						 for (Diskusjon diskusjon : diskusjoner){
							 if (diskusjon.getDiskusjonid().longValue() == dId.longValue()){
								 diskusjon.setSaksbehandler(saksbehandler.getBehandernavn());
							 }
							 if (diskusjon.getSaksbehandler() == null){
								 String tema = diskusjon.getTema();
								 char sep = ';';
								 tema = extractString(tema, sep, -1);
								 if (tema == null || tema.equals(""))
								 	tema = "Ikke satt";
								 diskusjon.setSaksbehandler(tema);
							 }
						 }
					 }
				 }
			 }else{
				 Long dId = sak.getDiskusjonid();
				 for (Diskusjon diskusjon : diskusjoner){
					 diskusjon.setSaksbehandler("Ikke satt");
				 }
			 }
				 
		 }
	 }else{
		 for (Diskusjon diskusjon : diskusjoner){
			 diskusjon.setSaksbehandler("Ikke satt - ingen sak!");
		 }
	 }
	}
	/**
	 * saveannenReclassifikasjon
	 * Denne rutinen lagrer reklassifikasjon av en vigilansmelding type annen
	 */
	protected void saveannenReclassifikasjon(){
		
		annenModel.saveValues();
	
		annenKomplikasjonWebService.saveAnnenKomplikasjon(annenModel);
		Long meldeId = annenModel.getVigilansmelding().getMeldeid();
		annenModel.getAnnenKomplikasjon().setUpdat(true);
		annenModel.setLagret(true);
		Komplikasjonsklassifikasjon klassifikasjon = annenModel.getKomplikasjonsklassifikasjon();
		klassifikasjon.setMeldeidannen(meldeId);
		klassifikasjon.setKlassifikasjonList(hvagikkgaltList);
		komplikasjonsklassifikasjonWebService.saveKomplikasjonsklassifikasjon(klassifikasjon);
		Vigilansmelding melding = (Vigilansmelding)annenModel.getAnnenKomplikasjon();
		melding.setGodkjent("Ja");
		//melding.setKladd("");
		hendelseWebService.saveVigilansMelder(melding);
	}
	/**
	 * savegiverReclassifikasjon
	 * Denne rutinen lagrer reklassifikasjon av en vigilansmelding type giver
	 */
	protected void savegiverReclassifikasjon(){
		giverModel.saveValues();
		giverModel.savekomplikasjonsValues();
		giverWebService.saveGiver(giverModel);
		giverWebService.saveVigilansmelding(giverModel);
		
		Long giverId=	giverModel.getGiver().getGiverid();
		if(giverId != null){
			donasjon.getDonasjon().setGiveId(giverId.intValue());
		}
	    donasjon.saveValues();
	    donasjonWebService.saveDonasjon(donasjon);
	    
	    Long donasjonensId = donasjon.getDonasjon().getDonasjonsId();
	    giverModel.getGiverKomplikasjon().setDonasjonid(donasjonensId);
	    
	    Long meldeId = giverModel.getVigilansmelding().getMeldeid();
		giverModel.getGiverKomplikasjon().setMeldeid(meldeId);

		giverWebService.saveGiverkomplikasjon(giverModel);
		
		komDiagnosegiver.getKomDiagnosegiver().setMeldeId(meldeId);
		komDiagnosegiver.saveValues();
		komDiagnosegiverWebService.saveKomDiagnosegiver(komDiagnosegiver);
		
		giverModel.getGiveroppfolging().setMeldeid(meldeId);
		giverWebService.saveGiveroppfolging(giverModel);
		giverModel.setLagret(true);
		giverModel.setDonasjonen(donasjon.getDonasjon());
		giverModel.setKomplikasjonsdiagnoseGiver(komDiagnosegiver.getKomDiagnosegiver());
		Vigilansmelding melding = (Vigilansmelding)giverModel.getGiverKomplikasjon();
		melding.setGodkjent("Ja");
		//melding.setKladd("");
		hendelseWebService.saveVigilansMelder(melding);		

	}	
	/**
	 * savetransfusjonReclassifikasjon
	 * Denne rutinen lagrer reklassifikasjon av en vigilansmelding type transfusjon
	 */
	protected void savetransfusjonReclassifikasjon(){	
		result.saveValues(); //Pasient og sykdommer
		
		transfusjon.saveValues(); // Transfusjon  og pasientkomplikasjoner
		result.getPasient().getTransfusjoner().put(transfusjon.getTransfusjon().getTransDato(), transfusjon.getTransfusjon());
		Vigilansmelding melding = (Vigilansmelding)transfusjon.getPasientKomplikasjon();
		melding.setDatoforhendelse(transfusjon.getTransfusjon().getTransfusionDate());
		hendelseWebService.saveHendelse(result);
		hendelseWebService.saveTransfusjon(transfusjon,result);
		result.setLagret(true);
		List<Sykdom> sykdommer = new ArrayList();
		sykdommer.add(result.getSykdom());
		sykdommer.add(result.getAnnenSykdom());
		transfusjon.setLagret(true);
		transfusjon.getKomplikasjonsklassifikasjon().setMeldeidpasient(transfusjon.getPasientKomplikasjon().getMeldeid());
		komplikasjonsklassifikasjonWebService.saveKomplikasjonsklassifikasjon(transfusjon.getKomplikasjonsklassifikasjon());
		Long melderKey = transfusjon.getVigilansmelding().getMelderId();
		if (melderKey != null){
			transfusjon.getPasientKomplikasjon().setMelderId(melderKey);
			melding = (Vigilansmelding)transfusjon.getPasientKomplikasjon();
			hendelseWebService.saveVigilansMelder(melding);
		}
	}
	
	/**
	 * setGivermelding
	 * Denne rutinen overfører informasjon fra vigilansmelding til giverkomplikasjon
	 */
	protected void setGivermelding(){
		 Vigilansmelding melding = (Vigilansmelding)giverModel.getGiverKomplikasjon();
		 melding.setSjekklistesaksbehandling( giverModel.getVigilansmelding().getSjekklistesaksbehandling());
		 melding.setSupplerendeopplysninger(giverModel.getVigilansmelding().getSupplerendeopplysninger());
		 melding.setMelderId(giverModel.getVigilansmelding().getMelderId());
		
	}
}
