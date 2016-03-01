package no.naks.biovigilans.felles.server.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang.WordUtils;
import org.restlet.Request;
import org.restlet.data.Reference;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;
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
import no.naks.biovigilans.felles.control.EmailWebService;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;
import no.naks.biovigilans.felles.model.AnnenKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.DonasjonwebModel;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.GiverKvitteringWebModel;
import no.naks.biovigilans.felles.model.KomDiagnosegiverwebModel;
import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.model.MeldingModel;
import no.naks.biovigilans.felles.model.PasientKomplikasjonWebModel;
import no.naks.biovigilans.felles.model.TransfusjonKvitteringWebModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.biovigilans.felles.model.VigilansModel;
import no.naks.biovigilans.felles.xml.Letter;
import no.naks.biovigilans.felles.xml.MainTerm;
import no.naks.biovigilans.felles.xml.no.KodeNivaa1;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa1;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa2;

/**
 * SessionServerResource
 * Denne klassen inneholder alle Webmodel objekter for en session.
 * Den er felles superklasse for alle ResourceHtml klassene
 * @author olj
 *
 */
public class SessionServerResource extends ProsedyreServerResource {

/*
 * Session objekter 
 */
	protected PasientKomplikasjonWebModel result = null;
	protected TransfusjonWebModel transfusjon = null;
	protected TransfusjonKvitteringWebModel kvittering = null;
	protected String andreKey = "annenKomp"; 		// Nøkkel dersom melding er av type annenkomplikasjon
	protected String pasientKey = "pasientKomp"; // Nøkkel dersom melding er av type pasientkomplikasjon
	protected String giverKey = "giverkomp"; 	// Nøkkel dersom melding er at type giverkomplikasjon
	protected String meldingsId = "meldingliste";  // Nøkkel til en Vigilansmelding (OBS i adm til en liste vigilansmeldinger) OBS: Endret fra meldinger !!
	protected String vigilansmeldinger = "vigilansmeldinger"; // Nøkkel til en liste vigilansmeldinger
	protected String allemeldingerMap = "allemeldinger"; // Nøkkel til en Map som inneholder alle meldingsdetaljer		
	protected String[] avdelinger;
	protected String[] aldergruppe;
	protected String[] kjonnValg; 
	protected String[] blodProdukt; // Plasma blodprodukter for nedtrekk - plasma produkttyper
	protected String[] hemolyseParametre;
	protected String pasientkomplikasjonId = "pasientkomplikasjon"; 	// Benyttes som nøkkel til HTML-sider pasientkomplikasjon
	protected String transfusjonId = "transfusjonwebID";					// Benyttes som nøkkel til HTML-sider pasientkomplikasjon
	protected String kvitteringsId = "kvittering";					// Benyttes som nøkkel for kvitteringssiden
	protected String kvitteringGiverId = "giverKvittering";
	protected String giverkomplikasjonId="giverkomplikasjon"; 		// Benyttes som nøkkel for giverwebmodel
	protected String donasjonId ="donasjon";						// Benyttes som nøkkel for donasjonwebmodel
	protected String komDiagnosegiverId = "komDiagnosegiver";
	protected String vigilansmeldingId="vigilansmelding";
	protected String messageType = "none";
	protected String giverenKey="giver";
	protected String donasjonKey = "donasjonen";
	protected String giverkomplikasjonKey = "giverkomplikasjonen"; 	// Benyttes som nøkkel for giverkomplikasjon
	protected String giverOppfolgingKey = "giveroppfolging";
	protected String giverkomplikasjondiagnoseKey = "giverkomplikasjondiagnose";
	protected String symptomerKey  ="symptomer";
	protected String tiltakKey = "tiltak";
	protected String forebyggendetiltakKey = "forebyggende";
/*
 * Nøkler for pasientkomplikasjoner	
 */
	protected String pasientenKey = "pasienten";
	protected String transfusjonsKey = "transfusjon";
	protected String sykdomKey = "sykdom";
	protected String klassifikasjonKey = "komplikasjonklassifikasjon";
	protected String utredningKey = "utredning";
	protected String blodproduktKey = "blodprodukt";
	protected String produktegenskapKey = "produktegenskap";		
/*
 * Til bruk for oppfølgingsmeldinger	
 */
	protected String displayKey = "display";						
	protected String displayPart = "none";
	protected String displaydateKey = "displaydate";
	protected String datePart = "block";
	protected String dobleKey = "doblemeldinger";

	/*
	 * Variabler som definerer hva som er alvorlige hendelser
	 * OLJ 29.02.16	
	 */
	protected String alvorligAnnenhendelse;
	protected String[] alvorligGiverhendelse;
	protected String[] alvorligGivervarighet;
	protected String[] alvorligHendelsegiver; //Behandlet på sykehus, Videre behandling oppfølging JA
	protected String[] alvorligHendelsegivergrad;
	protected String[] alvorligHendelsegiverutfall;

	protected List<String> hvagikkgaltList = new ArrayList<String>();
/*
 * Session objekter for giver	
 */
	protected GiverKomplikasjonwebModel giverModel = null;
	protected DonasjonwebModel donasjon = null;
	protected KomDiagnosegiverwebModel komDiagnosegiver = null;
//	protected GiverKvitteringWebModel giverKvittering = null;

	protected String[] reaksjonengruppe;
	protected String[] utenforBlodbankengruppe;
	protected String[] donasjonsstedgruppe;


	protected String[] systemiskgruppe;
	protected String[] skadeiarmen;
	protected String[] sykemeldinggruppe;
	protected String[] varighetSkadegruppe;
	
	//Rapporter AndreHendelse
	protected AnnenKomplikasjonwebModel annenModel =  null;
	protected String andreHendelseId ="andreHendelse";
	protected String annenHendelseId ="annenHendelse";
	protected String[] alvorligHendelse; 
	protected String[] hovedprosesslist;
	protected String[] feilelleravvik;
	protected String[] hendelsenoppdaget;
/*
 * Session objekter for kontakt	
 */
	protected MelderwebModel melderwebModel;
	protected String melderId = "melder"; // Nøkkel for melderwebModel
	protected String melderNokkel = "melderPrimar"; // Nøkkel for melder fra db
	
	protected String nokkelId = "nokkel"; // Til bruk leveranseside
	protected String datoId = "dato";		//Til bruk leveranseside
	
/*
 * Disse objektene inneholder tidligere rapporterte meldinger
 * Benyttes når bruker har angitt oppfølgingsmelding	
 */
	protected Annenkomplikasjon annenKomplikasjon = null;
    protected Pasientkomplikasjon pasientKomplikasjon = null;
    protected Giverkomplikasjon giverKomplikasjon = null;

    protected EmailWebService emailWebService;
    
	protected List<Vigilansmelding>andreMeldingene = null; // Inneholder oppfølgingsmeldinger som er fjernet fra liste (andre hendelser)
	protected String andreMeldingKey = "andreMeldingrem"; //Session key
	protected List<Vigilansmelding>giverMeldingene = null; // Inneholder oppfølgingsmeldinger som er fjernet fra liste (givermeldinger)
	protected String giverMeldingKey = "giverMeldingrem"; //Session key
	protected List<Vigilansmelding>pasientMeldingene = null; // Inneholder oppfølgingsmeldinger som er fjernet fra liste (pasienthendelser)
	protected String pasientMeldingKey = "pasientMeldingrem"; //Session key
    protected List<Vigilansmelding> dobleMeldingene = null;
    protected String dobleMeldingKey = "meldingrem"; // BRUKES IKKE!! OLJ 11.09.15
/*
 * Login objekter
 */
    protected LoginModel login = null;
    protected String loginKey = "login";
/*
 * Meldingsdiskusjoner    
 */
    protected MeldingModel meldingsDiskusjon;
    protected String meldingdiskusjonKey = "meldingdiskusjon";
    protected String meldingtilMelderKey = "melding"; //Til bruk i skjermbildet til å sende melding til melder
    
	protected  LinkedHashMap<String,String> linkHashmap;
	protected Vigilansmelding pdfMelding; // Melding brukt til PDF utskrift
	
	protected SaksbehandlingWebService saksbehandlingWebservice;
    
	protected String meldingsNokkel = null;   // Meldingsnøkkel benyttet for utskrift til pdf
	
	private String[] alvorGrad = {"Grad 2 Alvorlig","Grad 3 Livstruende","Grad 4 Dødsfall"};
	
	public String getMeldingsNokkel() {
		return meldingsNokkel;
	}

	public void setMeldingsNokkel(String meldingsNokkel) {
		this.meldingsNokkel = meldingsNokkel;
	}

	public EmailWebService getEmailWebService() {
		return emailWebService;
	}

	public void setEmailWebService(EmailWebService emailWebService) {
		this.emailWebService = emailWebService;
	}

	public SaksbehandlingWebService getSaksbehandlingWebservice() {
		return saksbehandlingWebservice;
	}

	public void setSaksbehandlingWebservice(
			SaksbehandlingWebService saksbehandlingWebservice) {
		this.saksbehandlingWebservice = saksbehandlingWebservice;
	}
	

	public LoginModel getLogin() {
		return login;
	}

	public void setLogin(LoginModel login) {
		this.login = login;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getDisplayKey() {
		return displayKey;
	}

	public void setDisplayKey(String displayKey) {
		this.displayKey = displayKey;
	}

	public String getDisplayPart() {
		return displayPart;
	}

	public void setDisplayPart(String displayPart) {
		this.displayPart = displayPart;
	}

	public String getDisplaydateKey() {
		return displaydateKey;
	}

	public void setDisplaydateKey(String displaydateKey) {
		this.displaydateKey = displaydateKey;
	}

	public String getDatePart() {
		return datePart;
	}

	public void setDatePart(String datePart) {
		this.datePart = datePart;
	}

	public String getMeldingsId() {
		return meldingsId;
	}

	public void setMeldingsId(String meldingsId) {
		this.meldingsId = meldingsId;
	}
	public String getPasientKey() {
		return pasientKey;
	}
	public void setPasientKey(String pasientKey) {
		this.pasientKey = pasientKey;
	}



	public String getGiverKey() {
		return giverKey;
	}



	public void setGiverKey(String giverKey) {
		this.giverKey = giverKey;
	}

	public String getAndreKey() {
		return andreKey;
	}



	public void setAndreKey(String andreKey) {
		this.andreKey = andreKey;
	}

	
	public String[] getVarighetSkadegruppe() {
		return varighetSkadegruppe;
	}
	public void setVarighetSkadegruppe(String[] varighetSkadegruppe) {
		this.varighetSkadegruppe = varighetSkadegruppe;
	}
	public String[] getSykemeldinggruppe() {
		return sykemeldinggruppe;
	}
	public void setSykemeldinggruppe(String[] sykemeldinggruppe) {
		this.sykemeldinggruppe = sykemeldinggruppe;
	}
	public String[] getSystemiskgruppe() {
		return systemiskgruppe;
	}
	public void setSystemiskgruppe(String[] systemiskgruppe) {
		this.systemiskgruppe = systemiskgruppe;
	}
	public String[] getSkadeiarmen() {
		return skadeiarmen;
	}
	public void setSkadeiarmen(String[] skadeiarmen) {
		this.skadeiarmen = skadeiarmen;
	}
	public String[] getHendelsenoppdaget() {
		return hendelsenoppdaget;
	}
	public void setHendelsenoppdaget(String[] hendelsenoppdaget) {
		this.hendelsenoppdaget = hendelsenoppdaget;
	}
	public String[] getFeilelleravvik() {
		return feilelleravvik;
	}
	public void setFeilelleravvik(String[] feilelleravvik) {
		this.feilelleravvik = feilelleravvik;
	}
	public String[] getHovedprosesslist() {
		return hovedprosesslist;
	}
	public void setHovedprosesslist(String[] hovedprosesslist) {
		this.hovedprosesslist = hovedprosesslist;
	}
	public AnnenKomplikasjonwebModel getAnnenModel() {
		return annenModel;
	}
	public void setAnnenModel(AnnenKomplikasjonwebModel annenModel) {
		this.annenModel = annenModel;
	}
	public String getAndreHendelseId() {
		return andreHendelseId;
	}
	public void setAndreHendelseId(String andreHendelseId) {
		this.andreHendelseId = andreHendelseId;
	}
	public String[] getAlvorligHendelse() {
		return alvorligHendelse;
	}
	public void setAlvorligHendelse(String[] alvorligHendelse) {
		this.alvorligHendelse = alvorligHendelse;
	}
	public PasientKomplikasjonWebModel getResult() {
		return result;
	}
	public void setResult(PasientKomplikasjonWebModel result) {
		this.result = result;
	}
	public TransfusjonWebModel getTransfusjon() {
		return transfusjon;
	}
	public void setTransfusjon(TransfusjonWebModel transfusjon) {
		this.transfusjon = transfusjon;
	}
	public TransfusjonKvitteringWebModel getKvittering() {
		return kvittering;
	}
	public void setKvittering(TransfusjonKvitteringWebModel kvittering) {
		this.kvittering = kvittering;
	}
	public String[] getAvdelinger() {
		return avdelinger;
	}
	public void setAvdelinger(String[] avdelinger) {
		this.avdelinger = avdelinger;
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
	public String[] getBlodProdukt() {
		return blodProdukt;
	}
	public void setBlodProdukt(String[] blodProdukt) {
		this.blodProdukt = blodProdukt;
	}
	public String[] getHemolyseParametre() {
		return hemolyseParametre;
	}
	public void setHemolyseParametre(String[] hemolyseParametre) {
		this.hemolyseParametre = hemolyseParametre;
	}
	public String getPasientkomplikasjonId() {
		return pasientkomplikasjonId;
	}
	public void setPasientkomplikasjonId(String pasientkomplikasjonId) {
		this.pasientkomplikasjonId = pasientkomplikasjonId;
	}
	public String getTransfusjonId() {
		return transfusjonId;
	}
	public void setTransfusjonId(String transfusjonId) {
		this.transfusjonId = transfusjonId;
	}
	public String getKvitteringsId() {
		return kvitteringsId;
	}
	public void setKvitteringsId(String kvitteringsId) {
		this.kvitteringsId = kvitteringsId;
	}
	public GiverKomplikasjonwebModel getGiverModel() {
		return giverModel;
	}
	public void setGiverModel(GiverKomplikasjonwebModel giverModel) {
		this.giverModel = giverModel;
	}
	public DonasjonwebModel getDonasjon() {
		return donasjon;
	}
	public void setDonasjon(DonasjonwebModel donasjon) {
		this.donasjon = donasjon;
	}
	public KomDiagnosegiverwebModel getKomDiagnosegiver() {
		return komDiagnosegiver;
	}
	public void setKomDiagnosegiver(KomDiagnosegiverwebModel komDiagnosegiver) {
		this.komDiagnosegiver = komDiagnosegiver;
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
	public String getGiverkomplikasjonId() {
		return giverkomplikasjonId;
	}
	public void setGiverkomplikasjonId(String giverkomplikasjonId) {
		this.giverkomplikasjonId = giverkomplikasjonId;
	}
	public String getDonasjonId() {
		return donasjonId;
	}
	public void setDonasjonId(String donasjonId) {
		this.donasjonId = donasjonId;
	}
	public String getKomDiagnosegiverId() {
		return komDiagnosegiverId;
	}
	public void setKomDiagnosegiverId(String komDiagnosegiverId) {
		this.komDiagnosegiverId = komDiagnosegiverId;
	}
	public String getVigilansmeldingId() {
		return vigilansmeldingId;
	}
	public void setVigilansmeldingId(String vigilansmeldingId) {
		this.vigilansmeldingId = vigilansmeldingId;
	}
	/*public GiverKvitteringWebModel getGiverKvittering() {
		return giverKvittering;
	}
	public void setGiverKvittering(GiverKvitteringWebModel giverKvittering) {
		this.giverKvittering = giverKvittering;
	}*/
	public MelderwebModel getMelderwebModel() {
		return melderwebModel;
	}
	public void setMelderwebModel(MelderwebModel melderwebModel) {
		this.melderwebModel = melderwebModel;
	}
	public String getMelderId() {
		return melderId;
	}
	public void setMelderId(String melderId) {
		this.melderId = melderId;
	}
	
	public List<String> getHvagikkgaltList() {
		return hvagikkgaltList;
	}
	public void setHvagikkgaltList(List<String> hvagikkgaltList) {
		this.hvagikkgaltList = hvagikkgaltList;
	}
	
	
	/**
	 * invalidateSessionobjects
	 * Denne rutinen fjerner alle session objekter
	 */
	public void invalidateSessionobjects(){
		sessionAdmin.getSession(getRequest(),pasientkomplikasjonId).invalidate();
		sessionAdmin.getSession(getRequest(),transfusjonId).invalidate();
		sessionAdmin.getSession(getRequest(),melderId).invalidate();
		sessionAdmin.getSession(getRequest(),giverkomplikasjonId).invalidate();
		sessionAdmin.getSession(getRequest(), kvitteringGiverId).invalidate();
		sessionAdmin.getSession(getRequest(), donasjonId).invalidate();
		sessionAdmin.getSession(getRequest(), komDiagnosegiverId).invalidate();
		sessionAdmin.getSession(getRequest(), andreHendelseId).invalidate();
		sessionAdmin.getSession(getRequest(), vigilansmeldingId).invalidate();
	}
	/**
	 * setalleTiltak
	 * Denne rutinen setter opp alle initielle tiltak
	 * dersom de ikke finnes fra før.
	 * Den kalles fra setTransfusjonsObjects() og RapporterHendelsesserverresourceHTML
	 */
	protected void setalleTiltak(){
		Request request = getRequest();
    	result.getTiltak().setBeskrivelse("Ikke angitt");
    	result.getForebyggendeTiltak().setTiltakbeskrivelse("Ikke angitt");
    	result.getForebyggendeTiltak().setTiltakvalg("Ikke angitt");
    	result.getForebyggendeTiltak().setTiltakbeskrivelse("Ikke angitt");
    	List<Tiltak> tiltak = new ArrayList<Tiltak>();
    	List<Forebyggendetiltak> forebyggende = new ArrayList<Forebyggendetiltak>();
    	tiltak.add(result.getTiltak());
    	forebyggende.add(result.getForebyggendeTiltak());
    	sessionAdmin.setSessionObject(request, tiltak,tiltakKey);
    	sessionAdmin.setSessionObject(request, forebyggende,forebyggendetiltakKey);
	}
	/**
	 * setMelderObject
	 * Denne rutinen setter opp et meldeWebmodel objekt for sesjonen
	 */
	public void setMelderObject(){
	     if ( melderwebModel == null){
	    	 melderwebModel = new MelderwebModel();
	
	     }
	     sessionAdmin.setSessionObject(getRequest(), melderwebModel,melderId);
	   
	}
	/**
	 * setTransfusjonsObjects
	 * Denne rutinene setter opp alle session objekter som er nødvendig for å fylle ut 
	 * Rutnen henter først transfusjon og pasient web model objektene fra session (om de finnes der: oppføglingsmedinger)
	 * 
	 * et hendelsesskjema
	 */
	public void setTransfusjonsObjects(){
		
/*
 * Transfusjonsession		
 */
	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	     icd10WebService.readXml();
	     
	     List<TematiskGruppeNivaa1> nivaa1 = icd10WebService.getNivaa1();
	     List<TematiskGruppeNivaa2> nivaa2 = new ArrayList();
	     List<KodeNivaa1> koder = new ArrayList();
	     for (TematiskGruppeNivaa1 nivaa : nivaa1){
	    	 nivaa2.addAll(nivaa.getTematiskGruppeNivaa2()) ;
	     }
	     for (TematiskGruppeNivaa2 niva : nivaa2){
	    	 koder.addAll(niva.getKodeNivaa1());
	     }
/*	      Engelsk ICD10
	     List<Letter> letters = icd10WebService.getLetters();
	     List<MainTerm> terms = new ArrayList();
	     for (Letter letter : letters){
	    	 terms.addAll(letter.getMainTerm());
	     }
*/	     
	     result = (PasientKomplikasjonWebModel) sessionAdmin.getSessionObject(request,pasientkomplikasjonId);
	     transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(request,transfusjonId);
	     kvittering = (TransfusjonKvitteringWebModel)sessionAdmin.getSessionObject(request,kvitteringsId);
	 	melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(getRequest(),melderId);
	     if (result == null){
	    	 result = new PasientKomplikasjonWebModel();
	    	 result.createModelobjects();
	    	 result.setAldergruppe(aldergruppe);
	    	 result.setKjonnValg(kjonnValg);
	    	 result.setblodProducts(blodProdukt);
	    	 result.setHemolyseparams(hemolyseParametre);
	    	 result.setAvdelinger(avdelinger);
	    	 result.setHendelseDato(new Date());
	    	 result.getPasient().setKjonn("ukjent");
	    	 result.getPasient().setAldersGruppe("ukjent");
	    	 result.getPasient().setInneliggendePoli("ukjent");
	    	 result.getPasient().setAvdeling("ukjent");
	    	 result.getSykdom().setSykdomnsnavn("ukjent");
	    	setalleTiltak();
	    	
	     }
//	     result.setTerms(terms);
	     result.setnoTerms(koder);
	     if (transfusjon == null){
	    	 transfusjon = new TransfusjonWebModel();
	    	 transfusjon.createModelobjects();
	    	 transfusjon.setHendelseDato(new Date());

	    	 transfusjon.getPasientKomplikasjon().setKlassifikasjon("ukjent");
	    	 transfusjon.getPasientKomplikasjon().setAlvorlighetsgrad("ukjent");
	    	 transfusjon.getPasientKomplikasjon().setKliniskresultat("ukjent");
	    	 transfusjon.getPasientKomplikasjon().setArsakssammenheng("ukjent");

	    	 transfusjon.getTransfusjon().setTildigerKomplikasjon("ukjent");
	    	 transfusjon.getTransfusjon().setTransfusionDate(new Date());
	    	 transfusjon.getTransfusjon().setHastegrad("ukjent");
	    	 transfusjon.getTransfusjon().setIndikasjon("ukjent");
	    	 transfusjon.getBlodProdukt().setBlodprodukt("ukjent");
	    	 transfusjon.getBlodProdukt().setTappetype("ukjent");
	    	 transfusjon.getProduktEgenskap().setEgenskapBeskrivelse("ukjent");
	    	 transfusjon.getProduktEgenskap().setEgenskapKode("ukjent");
	    	 List<Blodprodukt> blodprodukter = new ArrayList<Blodprodukt>();
	    	 blodprodukter.add( transfusjon.getBlodProdukt());
	    	 sessionAdmin.setSessionObject(request, blodprodukter,blodproduktKey);
	    	 List<Produktegenskap> egenskaper = new ArrayList<Produktegenskap>();
	    	 egenskaper.add(transfusjon.getProduktEgenskap());
	    	 sessionAdmin.setSessionObject(request, egenskaper,produktegenskapKey);
	    	 List<Sykdom> sykdommer = new ArrayList<Sykdom>();
	    	 sykdommer.add(result.getSykdom());
	    	 sessionAdmin.setSessionObject(request, sykdommer, sykdomKey);
	    	 transfusjon.getSymptomer().setSymptombeskrivelse("ukjent");
//	    	 transfusjon.getSymptomer().setTempFors("ikke relevant");
//	    	 transfusjon.getSymptomer().setTempEtters("ikke relevant");
	    	 List<Symptomer> symptomer = new ArrayList<Symptomer>();
	    	 symptomer.add( transfusjon.getSymptomer());
	    	 sessionAdmin.setSessionObject(request, symptomer,symptomerKey);
	    	 transfusjon.getKomplikasjonsklassifikasjon().setKlassifikasjonsbeskrivelse("ukjent");
	    	 transfusjon.getKomplikasjonsklassifikasjon().setKlassifikasjon("ukjent");
	    	 transfusjon.getUtredning().setUtredningbeskrivelse("ukjent");
	    	 List<Utredning> utredninger = new ArrayList<Utredning>();
	    	 utredninger.add( transfusjon.getUtredning());
	    	 sessionAdmin.setSessionObject(request, utredninger,utredningKey);

	    	 List<Komplikasjonsklassifikasjon> klassifikasjoner = new ArrayList<Komplikasjonsklassifikasjon>();
	    	 klassifikasjoner.add( transfusjon.getKomplikasjonsklassifikasjon());
	    	 sessionAdmin.setSessionObject(request, klassifikasjoner,klassifikasjonKey);
	  //  	 transfusjon.setHemolyseParametre(hemolyseParametre);
	     }

	     if ( melderwebModel == null){
	    	 melderwebModel = new MelderwebModel();
	
	     }
	     sessionAdmin.setSessionObject(getRequest(), melderwebModel,melderId);
	   
/*
 * Giver session 	     
 */
	     giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(request,giverkomplikasjonId);
		 donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(request, donasjonId);  
		 komDiagnosegiver =(KomDiagnosegiverwebModel) sessionAdmin.getSessionObject(request,komDiagnosegiverId );
	//	 giverKvittering = (GiverKvitteringWebModel)sessionAdmin.getSessionObject(request,kvitteringGiverId);
	     if(giverModel==null){
	    	 giverModel = new GiverKomplikasjonwebModel();
	    	 giverModel.createModelobjects();
	    	 giverModel.setAldergruppe(aldergruppe);
	    	 giverModel.setReaksjonengruppe(reaksjonengruppe);
	    	 giverModel.setUtenforBlodbankengruppe(utenforBlodbankengruppe);  
	    	 giverModel.setDonasjonsstedgruppe(donasjonsstedgruppe);
	    	 giverModel.setSkadeiarmen(skadeiarmen);
	    	 giverModel.setSystemiskgruppe(systemiskgruppe);
	    	 giverModel.setSykemeldinggruppe(sykemeldinggruppe);
	    	 giverModel.setVarighetSkadegruppe(varighetSkadegruppe);
	    	 giverModel.setHendelseDato(new Date());
	     }
	     if(donasjon==null){
	    	 donasjon = new DonasjonwebModel();
	    	 donasjon.createModelobjects();
	     }
	     if(komDiagnosegiver == null){
	    	 komDiagnosegiver = new KomDiagnosegiverwebModel();
	    	 komDiagnosegiver.createModelobjects();
	     }
	     
	   /*  if (giverKvittering == null){
	    	 giverKvittering = new GiverKvitteringWebModel();
	    
	     }*/
	
	}

    /*
     * Andre Hendelse session
     */
   	
	public void setAndreHendelser(){
		 Request request = getRequest();
		 annenModel = (AnnenKomplikasjonwebModel)sessionAdmin.getSessionObject(request, andreHendelseId);
	     if(annenModel == null){
//	    	 annenModel.setKjonnValg(kjonnValg);
	    	 annenModel = new AnnenKomplikasjonwebModel();
	    	 annenModel.createModelobjects();
	    	 annenModel.setAldergruppe(aldergruppe);
	    	 annenModel.setAlvorligHendelse(alvorligHendelse);
	    	 annenModel.setHovedprosesslist(hovedprosesslist);
	    	 annenModel.setFeilelleravvik(feilelleravvik);
	    	 annenModel.setHendelsenoppdaget(hendelsenoppdaget);
	    	 annenModel.setHvagikkgaltList(hvagikkgaltList);
	    	 annenModel.setMeldingsNokkel("xx");
	    	 annenModel.setHendelseDato(new Date());
	    	 
	     }
	}
	public void setGiverhendelser(){
		 Request request = getRequest();
	     giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(request,giverkomplikasjonId);
		 donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(request, donasjonId);  
		 komDiagnosegiver =(KomDiagnosegiverwebModel) sessionAdmin.getSessionObject(request,komDiagnosegiverId );
	//	 giverKvittering = (GiverKvitteringWebModel)sessionAdmin.getSessionObject(request,kvitteringGiverId);
	     if(giverModel==null){
	    	 giverModel = new GiverKomplikasjonwebModel();
	    	 giverModel.createModelobjects();
	    	 giverModel.setAldergruppe(aldergruppe);
	    	 giverModel.setReaksjonengruppe(reaksjonengruppe);
	    	 giverModel.setUtenforBlodbankengruppe(utenforBlodbankengruppe);  
	    	 giverModel.setDonasjonsstedgruppe(donasjonsstedgruppe);
	    	 giverModel.setSkadeiarmen(skadeiarmen);
	    	 giverModel.setSystemiskgruppe(systemiskgruppe);
	    	 giverModel.setSykemeldinggruppe(sykemeldinggruppe);
	    	 giverModel.setVarighetSkadegruppe(varighetSkadegruppe);
	    	 giverModel.setHendelseDato(new Date());
	     }
	     if(donasjon==null){
	    	 donasjon = new DonasjonwebModel();
	    	 donasjon.createModelobjects();
	     }
	     if(komDiagnosegiver == null){
	    	 komDiagnosegiver = new KomDiagnosegiverwebModel();
	    	 komDiagnosegiver.createModelobjects();
	     }
	}
	/**
	 * setTransfusjon
	 * Denne rutinen setter opp riktige transfusjon og pasient web model objekter
	 * Den kalles fra RapporterteMeldingerServerResource dersom tidligere melding er en transfusjnsmelding
	 *  
	 */
	public void setTransfusjon(){
		 Request request = getRequest();
	     result = (PasientKomplikasjonWebModel) sessionAdmin.getSessionObject(request,pasientkomplikasjonId);
	     transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(request,transfusjonId);
	     if (result == null){
	    	 result = new PasientKomplikasjonWebModel();
	    	 sessionAdmin.setSessionObject(request, result,pasientkomplikasjonId);
	    	 result.createModelobjects();
	       	 result.setAldergruppe(aldergruppe);
	    	 result.setKjonnValg(kjonnValg);
	    	 result.setblodProducts(blodProdukt);
	    	 result.setHemolyseparams(hemolyseParametre);
	    	 result.setAvdelinger(avdelinger);
	    	 result.setHendelseDato(new Date());
	     }
	     if (transfusjon == null){
	    	 transfusjon = new TransfusjonWebModel();
	    	 sessionAdmin.setSessionObject(request, transfusjon,transfusjonId);
	    	 transfusjon.createModelobjects();
	    	 Pasientkomplikasjon pasientkomplikasjon = transfusjon.getPasientKomplikasjon();
	    	 Transfusjon transfusjonen = transfusjon.getTransfusjon();
//	     	 Pasientkomplikasjon pasientkomplikasjon = (Pasientkomplikasjon) sessionAdmin.getSessionObject(request,pasientKey);
//	     	 Transfusjon transfusjonen = (Transfusjon) sessionAdmin.getSessionObject(request, transfusjonsKey);
	     	if (pasientkomplikasjon.getAlvorlighetsgrad() != null)
	     		transfusjon.getPasientKomplikasjon().setAlvorlighetsgrad(pasientkomplikasjon.getAlvorlighetsgrad());
	     	if (pasientkomplikasjon.getKliniskresultat() != null)
	     		transfusjon.getPasientKomplikasjon().setKliniskresultat(pasientkomplikasjon.getKliniskresultat());
	     	if (pasientkomplikasjon.getArsakssammenheng() != null)
	     		transfusjon.getPasientKomplikasjon().setArsakssammenheng(pasientkomplikasjon.getArsakssammenheng());
	     	if (transfusjonen.getHastegrad() != null)
	     		transfusjon.getTransfusjon().setHastegrad(transfusjonen.getHastegrad());
	     	if (transfusjonen.getIndikasjon() != null)
	     		transfusjon.getTransfusjon().setIndikasjon(transfusjonen.getIndikasjon());
	     	if (transfusjonen.getTildigerKomplikasjon() != null)
	     		transfusjon.getTransfusjon().setTildigerKomplikasjon(transfusjonen.getTildigerKomplikasjon());
	     }
	     icd10WebService.readXml();
//	     Pasient pasient = (Pasient)sessionAdmin.getSessionObject(request,pasientenKey);
//	     result.setPasient(pasient);
	     List<TematiskGruppeNivaa1> nivaa1 = icd10WebService.getNivaa1();
	     List<TematiskGruppeNivaa2> nivaa2 = new ArrayList();
	     List<KodeNivaa1> koder = new ArrayList();
	     for (TematiskGruppeNivaa1 nivaa : nivaa1){
	    	 nivaa2.addAll(nivaa.getTematiskGruppeNivaa2()) ;
	     }
	     for (TematiskGruppeNivaa2 niva : nivaa2){
	    	 koder.addAll(niva.getKodeNivaa1());
	     }	     
	     
/*
 * Engelsk icd10	     
 */
	     List<Letter> letters = icd10WebService.getLetters();
	     List<MainTerm> terms = new ArrayList();
	     if (letters != null && !letters.isEmpty()){
		     for (Letter letter : letters){
		    	 terms.addAll(letter.getMainTerm());
		     }	    	 
	     }

	     if (result == null){
	    	 result = new PasientKomplikasjonWebModel();
	
//	    	 result.setAldergruppe(aldergruppe);
	    	 result.setKjonnValg(kjonnValg);
	    	 result.setblodProducts(blodProdukt);
	    	 result.setHemolyseparams(hemolyseParametre);
	    	 result.setAvdelinger(avdelinger);
	    	 result.setHendelseDato(new Date());
	    
	     }
	     result.setnoTerms(koder);
//	     result.setTerms(terms);
	     if (transfusjon == null){
	    	 transfusjon = new TransfusjonWebModel();
	    	 transfusjon.setHendelseDato(new Date());
	     }
	}
	/**
	 * setlinkMap
	 * Denne rutinen setter opp linkhashmap for modelobjekter til bruk i PDF rapporter
	 * Den kalles når bruker ønsker en PDF rapport fra en levert melding
	 */
	protected void setlinkMap(){
		 Request request = getRequest();
		 LinkedHashMap<String, String> formLinkHashmap = null;
		  transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(request,transfusjonId);
		  annenModel = (AnnenKomplikasjonwebModel)sessionAdmin.getSessionObject(request, andreHendelseId);
		  giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(request,giverkomplikasjonId);
		  Pasient pasient = (Pasient) sessionAdmin.getSessionObject(request,pasientenKey);
		  String transKey = "";
		  if (transfusjon != null)
			  transKey = transfusjon.getVigilansmelding().getMeldingsnokkel();
		  String giverMKey = "";
		  if (giverModel != null)
			  giverMKey = giverModel.getVigilansmelding().getMeldingsnokkel();
		  String annenMeldingKey = "";
		  if(annenModel != null)
			  annenMeldingKey = annenModel.getVigilansmelding().getMeldingsnokkel();
		  
		  messageType = "none";
		  if (transfusjon != null && meldingsNokkel != null && meldingsNokkel.equals(transKey)){
			  formLinkHashmap = transfusjon.getFormLinkHashmap();
			  Pasientkomplikasjon pasientkomplikasjon = transfusjon.getPasientKomplikasjon();
			  Transfusjon transfusjonen = transfusjon.getTransfusjon();
			  SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			  Date pDate = transfusjon.getVigilansmelding().getDatoforhendelse();
			  String prDate = "";
			  if (pDate != null)
				  prDate = df.format(pDate);
			  formLinkHashmap.put("Dato for hendelsen", prDate);
			  String kjonn = pasient.getKjonn();
			  if (kjonn == null)
				  kjonn = "";
			  formLinkHashmap.put("Kjønn", kjonn);
			  String alder = pasient.getAldersGruppe();
			  if (alder == null)
				  alder = "";
			  formLinkHashmap.put("Alder", alder);
			  List <Sykdom> sykdommer = (List)sessionAdmin.getSessionObject(request,sykdomKey);
			  String key = "Sykdommer oppgitt";
			  int ct = 0;
			  if (sykdommer != null && !sykdommer.isEmpty()){
				  for (Sykdom sykdom : sykdommer){
					  String diagnose = sykdom.getDiagnosekode();
					  if (diagnose == null)
						  diagnose = "";
					  formLinkHashmap.put(key, diagnose);
					  key = "Annen sykdom " + String.valueOf(ct);
					  ct++;
				  }			  
			  }
		
			  String tidligereKomp = transfusjonen.getTildigerKomplikasjon();
			  if (tidligereKomp == null)
				  tidligereKomp = "Nei";
			  formLinkHashmap.put("Tidligere oppgitt transfusjonskomplikasjoner", tidligereKomp);
			  String hastegrad = transfusjonen.getHastegrad();
			  String innePoli = pasient.getInneliggendePoli();
			  if (innePoli == null)
				  innePoli = "";
			  formLinkHashmap.put("Inneliggene/Poliklinisk", innePoli);
			  if (hastegrad == null)
				  hastegrad = "";
			  formLinkHashmap.put("Hastegrad", hastegrad);
			  String avd = pasient.getAvdeling();
			  if (avd == null)
				  avd = "";
			  formLinkHashmap.put("Avdeling", avd);
			  Date transDato = transfusjonen.getTransfusionDate();
			  String tDato = "";
			  if (transDato != null)
				  tDato = df.format(transDato);
			  formLinkHashmap.put("Transfusjonsdato",tDato);
			  String indikasjon = transfusjonen.getIndikasjon();
			  if (indikasjon == null)
				  indikasjon = "";
			  formLinkHashmap.put("Indikasjon for transfusjonen",indikasjon);
			  List<Blodprodukt> blodprodukter = (List)sessionAdmin.getSessionObject(request,blodproduktKey);
			  int ctb = 0;
			  if (blodprodukter != null && !blodprodukter.isEmpty() ){
				  for (Blodprodukt blodprodukt : blodprodukter){
					  String produktnavn = blodprodukt.getBlodprodukt();
					  String blodkey = "Blodprodukt " + String.valueOf(ctb);
					  String tappekey = "Tappetype " + String.valueOf(ctb);
					  if (produktnavn == null)
						  produktnavn = "Ikke oppgitt";
					  formLinkHashmap.put(blodkey, produktnavn);
					  String tappetype = blodprodukt.getTappetype();
					  if (tappetype == null)
						  tappetype = "";
					  formLinkHashmap.put(tappekey+String.valueOf(ctb),tappetype);
					  ctb++;
				  }
			  }
			  List<Produktegenskap> produktegenskaper = (List)sessionAdmin.getSessionObject(request,produktegenskapKey);
			  if (produktegenskaper != null && !produktegenskaper.isEmpty()){
				  int ctp = 0;
				  for (Produktegenskap produktegenskap : produktegenskaper){
					  String egenskapKey = "Produktegenskap " + String.valueOf(ctp);
					  String egenskap = produktegenskap.getEgenskapBeskrivelse();
					  if (egenskap == null)
						  egenskap = "";
					  formLinkHashmap.put(egenskapKey+String.valueOf(ctp),egenskap);
					  ctp++;
				  }
			  }
			  List<Symptomer> symptomer =  (List)sessionAdmin.getSessionObject(request,symptomerKey);
			  if (symptomer != null && !symptomer.isEmpty()){
				  int ctp = 0;
				  for (Symptomer symptom : symptomer){
					  String egenskapKey = "Symptomer " + String.valueOf(ctp);
					  String egenskap = symptom.getSymptombeskrivelse();
					  if (egenskap == null)
						  egenskap = "";
					  formLinkHashmap.put(egenskapKey+String.valueOf(ctp),egenskap);
					  ctp++;
				  }
			  }
			  String hovedegenskap = pasientkomplikasjon.getKlassifikasjon();
			  List<Utredning> utredninger = (List)sessionAdmin.getSessionObject(request,utredningKey);
			  if (hovedegenskap != null){
				  	String hegenskapKey = "Mistenkte årsaker ";
				  	formLinkHashmap.put(hegenskapKey,hovedegenskap);  
			  }
			  if (utredninger != null && !utredninger.isEmpty()){
				  int ctp = 0;
				  for (Utredning utredning : utredninger){
					  String egenskapKey = "Mistenkte årsaker " + String.valueOf(ctp);
					  String egenskap = utredning.getUtredningbeskrivelse();
					  if (egenskap == null)
						  egenskap = "";
					  formLinkHashmap.put(egenskapKey+String.valueOf(ctp),egenskap);
					  ctp++;
				  }
			  }	
			  String alvor = pasientkomplikasjon.getAlvorlighetsgrad();
			  if (alvor == null)
				  alvor = "";
			  formLinkHashmap.put("Alvorlighetsgrad",alvor);
			  String utfall = pasientkomplikasjon.getKliniskresultat();
			  if (utfall == null)
				  utfall = "";
			  formLinkHashmap.put("Endelig utfall av skaden",utfall);
			  String arsak = pasientkomplikasjon.getArsakssammenheng();
			  if (arsak == null)
				  arsak = "";
			  formLinkHashmap.put("Årsaksammenheng",arsak);
			  String beskrivelse = transfusjon.getVigilansmelding().getSupplerendeopplysninger();
			  if (beskrivelse == null)
				  beskrivelse = "";
			  formLinkHashmap.put("Beskrivelse av hendelsen",beskrivelse);
			  List<Komplikasjonsklassifikasjon> klassifikasjoner = (List)sessionAdmin.getSessionObject(request,klassifikasjonKey);
			  if (klassifikasjoner != null && !klassifikasjoner.isEmpty()){
				  int ctp = 0;
				  for (Komplikasjonsklassifikasjon klassifikasjon :klassifikasjoner){
					  String egenskapKey = "Hvorfor skjedde " + String.valueOf(ctp);
					  String egenskap = klassifikasjon.getKlassifikasjonsbeskrivelse();
					  if (egenskap == null)
						  egenskap = "";
					  formLinkHashmap.put(egenskapKey+String.valueOf(ctp),egenskap);
					  ctp++;
				  }
			  }		
			  List<Tiltak> tiltakene = (List)sessionAdmin.getSessionObject(request,tiltakKey);
			  if (tiltakene != null && !tiltakene.isEmpty()){
				  int ctp = 0;
				  for (Tiltak tiltak :tiltakene){
					  String egenskapKey = "Tiltak " + String.valueOf(ctp);
					  String egenskap = tiltak.getBeskrivelse();
					  if (egenskap == null)
						  egenskap = "";
					  formLinkHashmap.put(egenskapKey+String.valueOf(ctp),egenskap);
					  ctp++;
				  }
			  }	
			  List<Forebyggendetiltak> forebyggtiltakene = (List)sessionAdmin.getSessionObject(request,forebyggendetiltakKey);
			  if (forebyggtiltakene != null && !forebyggtiltakene.isEmpty()){
				  int ctp = 0;
				  for (Forebyggendetiltak tiltak :forebyggtiltakene){
					  String egenskapKey = "Tiltak " + String.valueOf(ctp);
					  String egenskap = tiltak.getTiltakbeskrivelse();
					  if (egenskap == null)
						  egenskap = "";
					  formLinkHashmap.put(egenskapKey+String.valueOf(ctp),egenskap);
					  ctp++;
				  }
			  }
			  String kladd = transfusjon.getVigilansmelding().getKladd();
			  if (kladd == null)
				  kladd = "";
			  formLinkHashmap.put("Ytterligere opplysninger",kladd);
			  String godkjent = transfusjon.getVigilansmelding().getGodkjent();
			  if (godkjent == null)
				  godkjent = "";
			  formLinkHashmap.put("Godkjent",godkjent);			  
		  }
		  if (giverModel != null && meldingsNokkel != null && meldingsNokkel.equals(giverMKey)){
			  formLinkHashmap = giverModel.getFormLinkHashmap();
			  Giverkomplikasjon giverkomplikasjon = giverModel.getGiverKomplikasjon();
			  Giver giver = giverModel.getGiver();
			  Donasjon donasjon = giverModel.getDonasjonen();
			  Komplikasjonsdiagnosegiver komplikasjonsdiagnose = giverModel.getKomplikasjonsdiagnoseGiver();
			  Giveroppfolging giveroppfolging = giverModel.getGiveroppfolging();
			  SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			  Date hDate = giverModel.getVigilansmelding().getDatoforhendelse();

			  String rDate = "";
			  if (hDate != null)
				  rDate = df.format(hDate);
			  formLinkHashmap.put("Dato for hendelsen", rDate);
			  String kjonn = giver.getKjonn();
			  if (kjonn == null)
				  kjonn = "";
			  formLinkHashmap.put("Kjønn", kjonn);
			  String alder = giver.getAlder();
			  if (alder == null)
				  alder = "";
			  formLinkHashmap.put("Alder", alder);
			  Long v = giver.getVekt();
			  String vekt = "";
			  if (v != null)
				  vekt = String.valueOf(v.longValue());
			  formLinkHashmap.put("Vekt", vekt);
			  String erfaring = giver.getGivererfaring();
			  if (erfaring == null)
				  erfaring = "";
			  formLinkHashmap.put("Givererfaring", erfaring);
			  String sted = donasjon.getDonasjonssted();
			  if (sted == null)
				  sted = "";
			  formLinkHashmap.put("Sted for tapping",sted);
			  String type = donasjon.getTappetype();
			  if (type == null)
				  type = "";
			  formLinkHashmap.put("Type tapping",type);
			  String gerfaring = giver.getGivererfaringaferese();
			  if (gerfaring == null)
				  gerfaring = "";
			  formLinkHashmap.put("Første gang giver", gerfaring);
			  String maltid = donasjon.getMaltidfortapping();
			  if (maltid == null)
				  maltid = "";
			  formLinkHashmap.put("Måltid før tapping", maltid);
			  String kompljanei = donasjon.getKomplisertvenepunksjon();
			  if (kompljanei == null)
				  kompljanei = "";
			  formLinkHashmap.put("Komplisert venepunksjon", kompljanei);
			  String stedkomp = giverkomplikasjon.getStedforkomplikasjon();
			  if (stedkomp == null)
				  stedkomp = "";
			  formLinkHashmap.put("Hvor skjedde reaksjonen",stedkomp);
			  Date sDate = giverkomplikasjon.getDatosymptomer();
			  String srDate = "";
			  if (sDate != null)
				  srDate = df.format(sDate);
			  formLinkHashmap.put("Dato for reaksjon", srDate);
			  String tidfratap = giverkomplikasjon.getTidfratappingtilkompliasjon();
			  if (tidfratap == null)
				  tidfratap = "";
			  formLinkHashmap.put("Tid fra tapping til reaksjon", tidfratap);
			  String lokal = komplikasjonsdiagnose.getLokalskadearm();
			  if (lokal == null)
				  lokal = "";
			  formLinkHashmap.put("Type komplikasjon", lokal);
			  String systemisk = komplikasjonsdiagnose.getLokalskadebeskrivelse();
			  if (systemisk == null)
				  systemisk = "";
			  formLinkHashmap.put("Systemisk skade", systemisk);
			  String lokalskade = komplikasjonsdiagnose.getSystemiskbivirkning();
			  if (lokalskade == null)
				  lokalskade = "";
			  formLinkHashmap.put("Type skade", lokalskade);
			  String annen = komplikasjonsdiagnose.getAnnenreaksjon();
			  if (annen == null)
				  annen = "";
			  formLinkHashmap.put("Systemisk", annen);
	
			  String venepunksjon = donasjon.getLokalisasjonvenepunksjon();
			  if (venepunksjon != null)
				  formLinkHashmap.put("Venepunksjon", venepunksjon);
			  String varighet = giverkomplikasjon.getVarighetkomplikasjon();
			  if (varighet == null)
				  varighet = "";
			  formLinkHashmap.put("Varighet reaksjon",varighet);
			  String tiltak = giveroppfolging.getStrakstiltak();
			  if (tiltak == null)
				  tiltak = "";
			  formLinkHashmap.put("Strakstiltak", tiltak);
			  String oppfolging = giveroppfolging.getVidereoppfolging();
			  if (oppfolging == null)
				  oppfolging = "";
			  formLinkHashmap.put("Videre oppfolging", oppfolging);
			  String grad = giverkomplikasjon.getAlvorlighetsgrad();
			  if (grad == null)
				  grad = "";
			  formLinkHashmap.put("Alvorlighetsgrad", grad);
			  String klinisk = giverkomplikasjon.getKliniskresultat();
			  if (klinisk == null)
				  klinisk = "";
			  formLinkHashmap.put("Endelig utfall av skaden", klinisk);
			  String hendelse = giverModel.getVigilansmelding().getSupplerendeopplysninger();
			  if (hendelse == null)
				  hendelse = "";
			  formLinkHashmap.put("Beskrivelse av hendelsen", hendelse);
			  String oppfbeskrivelse = giveroppfolging.getGiveroppfolgingbeskrivelse();
			  if (oppfbeskrivelse == null)
				  oppfbeskrivelse = "";
			  formLinkHashmap.put("Forbedringstiltak", oppfbeskrivelse);
			  String avreg = giveroppfolging.getAvregistering();
			  if (avreg == null)
				  avreg = "";
			  formLinkHashmap.put("Avregistrering",avreg);
			  String ytteropp = giverModel.getVigilansmelding().getKladd();
			  if (ytteropp == null)
				  ytteropp = "";
			  formLinkHashmap.put("Ytterligere opplysninger",ytteropp);
			  String godkjent = giverModel.getVigilansmelding().getGodkjent();
			  if (godkjent == null)
				  godkjent = "";
			  formLinkHashmap.put("Godkjent",godkjent);
		  }
		  if (annenModel != null && meldingsNokkel != null && meldingsNokkel.equals(annenMeldingKey)){
			  formLinkHashmap = annenModel.getFormLinkHashmap();
			  Annenkomplikasjon annenKomplikasjon = annenModel.getAnnenKomplikasjon();
			  SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			  Date hDate = annenModel.getVigilansmelding().getDatoforhendelse();
			  String rDate = "";
			  if (hDate != null)
				  rDate = df.format(hDate);
			  formLinkHashmap.put("Dato for hendelsen", rDate);
			  String beskrivelse = annenModel.getVigilansmelding().getSupplerendeopplysninger();
			  if (beskrivelse == null || beskrivelse.equals("Ikke angitt")){
				  beskrivelse = annenKomplikasjon.getKomplikasjonbeskrivelse();
				  if (beskrivelse == null)
					  beskrivelse = "";
			  }else{
				  beskrivelse = beskrivelse + annenKomplikasjon.getKomplikasjonbeskrivelse();
			  }
			
			  formLinkHashmap.put("Beskrivelse", beskrivelse);
			  String hovedprosess = annenKomplikasjon.getHovedprosess();
			  if (hovedprosess == null)
				  hovedprosess = "";
			  formLinkHashmap.put("Hovedprosess", hovedprosess);
			  String avvikarsak = annenKomplikasjon.getAvvikarsak();
			  if (avvikarsak == null)
				  avvikarsak = "";
			  formLinkHashmap.put("Primærårsak", avvikarsak);
			  String klasse = annenKomplikasjon.getKlassifikasjon();
			  if (klasse == null)
				  klasse = "";
			  formLinkHashmap.put("Klassifikasjoner", klasse);
			  String delkode = annenKomplikasjon.getDelkode();
			  if (delkode == null)
				  delkode = "";
			  formLinkHashmap.put("Delkode", delkode);
			  String pasientopp = annenKomplikasjon.getPasientopplysninger();
			  if (pasientopp != null)
				  formLinkHashmap.put("Pasientopplysninger", pasientopp);
			  String def = annenKomplikasjon.getKomplikasjondefinisjon();
			  List<Komplikasjonsklassifikasjon> klassifikasjoner = (List)sessionAdmin.getSessionObject(request,klassifikasjonKey);
			  if (klassifikasjoner != null && !klassifikasjoner.isEmpty()){
				  int ctp = 0;
				  for (Komplikasjonsklassifikasjon klassifikasjon :klassifikasjoner){
					  String egenskapKey = "Hva gikk galt " + String.valueOf(ctp);
					  String egenskap = klassifikasjon.getKlassifikasjonsbeskrivelse();
					  if (egenskap == null)
						  egenskap = "";
					  if (!egenskap.isEmpty())
						  formLinkHashmap.put(egenskapKey+String.valueOf(ctp),egenskap);
					  ctp++;
				  }
			  }	
			  if (def == null)
				  def = "";
			  formLinkHashmap.put("Definisjon av hendelsen",def);
			  String oppdaget = annenKomplikasjon.getOppdaget();
			  if (oppdaget == null)
				  oppdaget = "";
			  formLinkHashmap.put("Hvordan ble hendelsen oppdaget",oppdaget);
			  String tiltak = annenKomplikasjon.getTiltak();
			  if (tiltak == null)
				  tiltak = "";
			  formLinkHashmap.put("Tiltak",tiltak);
			  String komm = annenKomplikasjon.getKommentar();
			  if (komm == null)
				  komm = "";
			  formLinkHashmap.put("Kommentarer",komm);
			  String opplys = annenModel.getVigilansmelding().getKladd();
			  if (opplys == null)
				  opplys = "";
			  formLinkHashmap.put("Ytterligere opplysninger følger",opplys);
			  String godkjent = annenModel.getVigilansmelding().getGodkjent();
			  if (godkjent == null)
				  godkjent = "";
			  formLinkHashmap.put("Godkjent",godkjent);
		  }		
	}

	/**
	 * checkMessageType
	 * Denne rutinen sjekker type melding som er sendt inn
	 * Brukes av leveransesiden for å vise riktig informasjon
	 * @return
	 */
	public VigilansModel checkMessageType(){
		  Request request = getRequest();
		  VigilansModel melding = null;
		  transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(request,transfusjonId);
		  annenModel = (AnnenKomplikasjonwebModel)sessionAdmin.getSessionObject(request, andreHendelseId);
		  giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(request,giverkomplikasjonId);
		  messageType = "none";
		  String transKey = "";
		  if (transfusjon != null)
			  transKey = transfusjon.getVigilansmelding().getMeldingsnokkel();
		  String giverMKey = "";
		  if (giverModel != null)
			  giverMKey = giverModel.getVigilansmelding().getMeldingsnokkel();
		  String annenMeldingKey = "";
		  if(annenModel != null)
			  annenMeldingKey = annenModel.getVigilansmelding().getMeldingsnokkel();
		  
		  if (transfusjon != null && meldingsNokkel != null && meldingsNokkel.equals(transKey)){
			  messageType = "transfusjon";
			  melding = (VigilansModel)transfusjon;
			  pdfMelding = transfusjon.getVigilansmelding();
		  }
		  if (giverModel != null && meldingsNokkel != null && meldingsNokkel.equals(giverMKey)){
			  messageType = "giver";
			  melding = (VigilansModel)giverModel;
			  pdfMelding = giverModel.getVigilansmelding();
		  }
		  if (annenModel != null && meldingsNokkel != null && meldingsNokkel.equals(annenMeldingKey)){
			  messageType = "annen";
			  melding = (VigilansModel)annenModel;
			  pdfMelding = annenModel.getVigilansmelding();
		  }
		return melding;
	}
	public String getPage(){
		String page = "/hemovigilans/leveranse.html";
		return page;
	}
	/**
	 * checkSavedModel
	 * Denne rutinen sjekker om et skjema er lagret
	 * @return true dersom et skjema er lagret.
	 */
	public boolean checkSavedModel(){
		if (giverModel != null){
			return giverModel.isLagret();
		}
		if (result != null){
			return result.isLagret();
		}
		if (transfusjon != null){
			return transfusjon.isLagret();
		}
		return false;
	}
	    /**
     * Save Skjema
     * DEnne rutinen sørger for å lagre melderid til vigilansmelding
     * 
     */
   protected void SaveSkjema(){
		Long melderKey = melderwebModel.getMelder().getMelderId();
		if (melderKey != null && transfusjon != null){
			if (transfusjon.isLagret()){
				transfusjon.getPasientKomplikasjon().setMelderId(melderKey);
				Vigilansmelding melding = (Vigilansmelding)transfusjon.getPasientKomplikasjon();
				melding.setGodkjent("Ja");
				//melding.setKladd("");
				hendelseWebService.saveVigilansMelder(melding);
			}
		}
			if (melderKey != null && giverModel != null){
			if (giverModel.isLagret()){
				giverModel.getGiverKomplikasjon().setMelderId(melderKey);
				Vigilansmelding melding = (Vigilansmelding)giverModel.getGiverKomplikasjon();
				melding.setGodkjent("Ja");
				//melding.setKladd("");
				hendelseWebService.saveVigilansMelder(melding);
			}
		}
		if (melderKey != null && annenModel != null){
			if (annenModel.isLagret()){
				annenModel.getAnnenKomplikasjon().setMelderId(melderKey); 
				Vigilansmelding melding = (Vigilansmelding)annenModel.getAnnenKomplikasjon();
				melding.setGodkjent("Ja");
				//melding.setKladd("");
				hendelseWebService.saveVigilansMelder(melding);
			}
		}  
    }
 /**
 * sorterMeldinger
 * Denne rutinen sørger for at et uttrekk av meldinger blir sortert.
 * Det innebærer at oppfølgingsmeldinger blir fjernet, og en melding blir igjen som representerer alle 
 * meldingene med samme meldingsnøkkel.
 * @param alleMeldinger,meldinger
 */
protected void sorterMeldinger(Map<String,List>alleMeldinger,List<Vigilansmelding>meldinger){
	    List<Annenkomplikasjon> annenListe =(List) alleMeldinger.get(andreKey);
	    List<Pasientkomplikasjon> pasientListe = (List) alleMeldinger.get(pasientKey);
	    List<Giverkomplikasjon> giverListe = (List)  alleMeldinger.get(giverKey);
		List<Komplikasjonsklassifikasjon> klassifikasjoner = null;
		andreMeldingene = new ArrayList<Vigilansmelding>();
		giverMeldingene = new ArrayList<Vigilansmelding>();
		pasientMeldingene = new ArrayList<Vigilansmelding>();
		for (Vigilansmelding melding: meldinger){
			 
			 for (Vigilansmelding nestemelding: meldinger){
		    		if (melding.getMeldingsnokkel().equals(nestemelding.getMeldingsnokkel()) && melding.getMeldeid().longValue() < nestemelding.getMeldeid().longValue()){
		    			
		    	    	for (Annenkomplikasjon nesteKomplikasjon : annenListe){
		    	    		if (nesteKomplikasjon.getMeldeid().longValue() == melding.getMeldeid().longValue()){
		    	    			andreMeldingene.add(melding);		
		    	    		}
		    	    	}
		    	    	for (Giverkomplikasjon nesteGiverkomplikasjon : giverListe){
		    	    		if (nesteGiverkomplikasjon.getMeldeid().longValue() == melding.getMeldeid().longValue()){
		    	    			giverMeldingene.add(melding);		
		    	    		}
		    	    	}
		    	    	for (Pasientkomplikasjon nestePasientkomplikasjon : pasientListe){
		    	    		if (nestePasientkomplikasjon.getMeldeid().longValue() == melding.getMeldeid().longValue()){
		    	    			pasientMeldingene.add(melding);		
		    	    		}
		    	    	}
		    		}				 
			 }
		 }
		 meldinger.removeAll(andreMeldingene);
		 meldinger.removeAll(giverMeldingene);
		 meldinger.removeAll(pasientMeldingene);

	  
   }

	/**
	 * sortermeldingerHendelse
	 * Denne rutinen sorterer meldingene etter hendelsedato
	 * Siste registrerte meldinger kommer først i listen.
	 * Dersom listen allerede er sortert slik, så blir sorteringen reversert.
	 * @param meldinger
	 */
	protected void sortermeldingerHendelse(List<Vigilansmelding>meldinger){
		List<Vigilansmelding> localMeldinger = new ArrayList();
		localMeldinger.addAll(meldinger);
		meldinger.sort((vm1, vm2)->vm2.getDatoforhendelse().compareTo(vm1.getDatoforhendelse()));
		if (meldinger.equals(localMeldinger)){
			meldinger.sort((vm1, vm2)->vm1.getDatoforhendelse().compareTo(vm2.getDatoforhendelse()));
		}
	}
	/**
	 * sortermeldingerMeldt
	 * Denne rutinen sorterer meldingene etter dato meldt
	 * Siste registrerte meldinger kommer først i listen.
	 * Dersom listen allerede er sortert slik, så blir sorteringen reversert.
	 * @param meldinger
	 */
	protected void sortermeldingerMeldt(List<Vigilansmelding>meldinger){
		List<Vigilansmelding> localMeldinger = new ArrayList();
		localMeldinger.addAll(meldinger);
		meldinger.sort((vm1, vm2)->vm2.getDatooppdaget().compareTo(vm1.getDatooppdaget()));
		if (meldinger.equals(localMeldinger)){
			meldinger.sort((vm1, vm2)->vm1.getDatooppdaget().compareTo(vm2.getDatooppdaget()));
		}
	}	
/**
* sorterMeldinger
* Denne rutinen sørger for at et uttrekk av meldinger blir sortert.
* Det innebærer at oppfølgingsmeldinger blir fjernet, og en melding blir igjen som representerer alle 
* meldingene med samme meldingsnøkkel.
* @param meldinger Liste over vigilansmeldinger
*/
protected void sorterMeldinger(List<Vigilansmelding>meldinger){
		Request request = getRequest();
		dobleMeldingene = new ArrayList<Vigilansmelding>();

		for (Vigilansmelding melding: meldinger){
			 
			 for (Vigilansmelding nestemelding: meldinger){
		    		if (melding.getMeldingsnokkel().equals(nestemelding.getMeldingsnokkel()) && melding.getMeldeid().longValue() < nestemelding.getMeldeid().longValue()){
		    				dobleMeldingene.add(melding);
		    		}				 
			 }
		 }
		 meldinger.removeAll(dobleMeldingene);

		 sessionAdmin.setSessionObject(request,dobleMeldingene, dobleKey);
	  
  }


	/**
	 * getmeldingsNokkelAndre
	 * Denne rutinen returnerer meldeid fra første melding med samme meldingsnøkkel andre meldinger
	 * @param nokkel
	 * @return
	 */
	protected Long getmeldingsNokkelAndre(String nokkel){
		if (andreMeldingene != null && !andreMeldingene.isEmpty()){
			for (Vigilansmelding melding : andreMeldingene){
				if (melding.getMeldingsnokkel().equals(nokkel)){
					return melding.getMeldeid();
				}
			}			
		}
	
		return null;
	}
	/**
	 * getmeldingsNokkelGiver
	 * Denne rutinen returnerer meldeid fra første melding med samme meldingsnøkkel givermeldinger
	 * @param nokkel
	 * @return
	 */
	protected Long getmeldingsNokkelGiver(String nokkel){
		if (giverMeldingene != null && !giverMeldingene.isEmpty()){
			for (Vigilansmelding melding : giverMeldingene){
				if (melding.getMeldingsnokkel().equals(nokkel)){
					return melding.getMeldeid();
				}
			}			
		}
	
		return null;
	}
	/**
	 * getmeldingsNokkelPasient
	 * Denne rutinen returnerer meldeid fra første melding med samme meldingsnøkkel pasientmeldinger
	 * @param nokkel
	 * @return
	 */
	protected Long getmeldingsNokkelPasient(String nokkel){
		if (pasientMeldingene != null && !pasientMeldingene.isEmpty()){
			for (Vigilansmelding melding : pasientMeldingene){
				if (melding.getMeldingsnokkel().equals(nokkel)){
					return melding.getMeldeid();
				}
			}			
		}
	
		return null;
	}
	/**
	 * getmeldingsNokkelsak
	 * Denne rutinen returnerer meldeid fra første melding med samme meldingsnøkkel andre meldinger
	 * @param nokkel
	 * @return
	 */

	protected Long getmeldingsNokkelsak(String nokkel){
		Request request = getRequest();
		List<Vigilansmelding> dobleMeldingene = (List<Vigilansmelding>)sessionAdmin.getSessionObject(request, dobleKey);

		if (dobleMeldingene != null && !dobleMeldingene.isEmpty()){
			for (Vigilansmelding melding : dobleMeldingene){
				if (melding.getMeldingsnokkel().equals(nokkel)){
					return melding.getMeldeid();
				}
			}			
		}
	
		return null;
	}
	
	/**
	 * meldingidOppfolging
	 * Denne rutinen returnerer en liste av nøkler for oppfølgingsmeldinger
	 * @param meldinger
	 * @param nokkel
	 * @return
	 */
	protected List<String> meldingidOppfolging(List<Vigilansmelding> meldinger,String nokkel){
		List keys = new ArrayList();
		for (Vigilansmelding melding : meldinger){
			Long mId = melding.getMeldeid();
			String mKey = melding.getMeldingsnokkel();
			if (mKey.equals(nokkel)){
				String nkey = mId.toString();
				keys.add(nkey);
			}
		}
		
		
		return keys;
	}
	
	protected String createPDF() throws Exception{
		  
		VigilansModel melding = checkMessageType();
		 Map<String, Object> dataModel = new HashMap<String, Object>();
	     melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(getRequest(),melderId);
		 transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(getRequest(),transfusjonId);
    	 giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(getRequest(),giverkomplikasjonId);
    	 annenModel = (AnnenKomplikasjonwebModel) sessionAdmin.getSessionObject(getRequest(),andreHendelseId);
    	 donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(getRequest(),donasjonId);
    	 Long melderId = pdfMelding.getMelderId();
    	 Melder melder = null;
    	 if (melderwebModel != null){
    		 melder = melderwebModel.getMelder();
    		 melderId = melder.getMelderId();
    	 }
    	
    	if (melder == null && melderId != null && melderId.longValue() != 0) 
    		melder = saksbehandlingWebservice.collectmelder(melderId);
    	else if( melder == null){
    		melder = new MelderImpl();
    		melder.setMeldernavn("ukjent");
    		melder.setMelderepost("ukjent");
    		melder.setMeldertlf("ukjent");
    		melder.setHelseforetak("ukjent");
    		melder.setHelseregion("ukjent");
    		melder.setSykehus("ukjent");
    	}
    		
       	 /** create temporary pdf file **/
 		File temp = File.createTempFile("leveranse", ".pdf"); 
 	    String path = temp.getAbsolutePath();
     	
 		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

 		// Listing 2. Creation of PdfWriter object
 		PdfWriter writer = PdfWriter.getInstance(document,
 				new FileOutputStream(path));
 		
 		document.open();
 		
 		PdfPTable table =  new PdfPTable(2);
 		
 		if(messageType.equals("annen")){
 			//Map<String, String> map = new LinkedHashMap<String,String>();
    		// set the text ordering 
 		//	linkHashmap = setAnnenOrdering(annenModel.getFormMap());
 			linkHashmap = annenModel.getFormLinkHashmap();
 			setMelderinfo(melder);
    		 String nokkel = annenModel.getMeldingsNokkel();
    		 List<String> hvagikkgaltList = annenModel.getHvagikkgaltList();
    		 String hvagikkgaltString ="";
    		 for(String hvagikkgalt: hvagikkgaltList){
    			 if(hvagikkgaltString ==""){
    				 hvagikkgaltString = hvagikkgalt;
    			 }else{
    				 hvagikkgaltString = hvagikkgaltString + ", " + hvagikkgalt;
    			 }
    			 
    		 }
    		 
    		 linkHashmap.put("Hva gikk galt", hvagikkgaltString);
    		 
    		 String heading ="Rapporter Andre Hendelser";
     		 table = createTable(heading,nokkel);
    		
        }
    	 
    	 if(messageType.equals("transfusjon") ){
        	//	map = new LinkedHashMap<String,String>(transfusjon.getFormMap()) ;
    		// linkHashmap = setTransfusjonOrdering(transfusjon.getFormMap()) ;
    		 Map map = transfusjon.getFormMap();
    		 linkHashmap = transfusjon.getFormLinkHashmap();
 			setMelderinfo(melder);
        	 String nokkel = transfusjon.getMeldingsNokkel();
        	String heading ="Rapporter Transfusjon Hemovigilans";
        	
			table = createTable(heading,nokkel);
	    }
    	 if (messageType.equals("giver")){
    		// linkHashmap = new LinkedHashMap<String,String>(giverModel.getFormMap());
    		 linkHashmap = giverModel.getFormLinkHashmap();
 			setMelderinfo(melder);
    		 String nokkel = giverModel.getMeldingsNokkel();
    		 String heading ="Rapporter Giver Hemovigilans";
 			 table = createTable(heading,nokkel);
 	    }
    	 document.add(table);
    	 document.close();
    	 return path;
		
	}
	
	private void setMelderinfo(Melder melder){
		linkHashmap.put("MELDER", "Detaljer om melder");
		linkHashmap.put("Melders navn", melder.getMeldernavn());
		linkHashmap.put("Melders epost", melder.getMelderepost());
		linkHashmap.put("Melders telefon", melder.getMeldertlf());
		linkHashmap.put("Melders region", melder.getHelseregion());
		linkHashmap.put("Melders forertak", melder.getHelseforetak());
		linkHashmap.put("Melders sykehus", melder.getSykehus());
	}
	private PdfPTable createTable(String heading, String nokkel){
		
		if(nokkel == null){
			nokkel ="";
		}
	
      //specify column widths
        float[] columnWidths = {2f, 3f};
     // a table with two columns
        PdfPTable table = new PdfPTable(columnWidths);
     // set table width a percentage of the page width
        table.setWidthPercentage(80f);
        
     // creation of paragraph object
		Paragraph heading1 = new Paragraph();
		
			
		heading1 = getHeading1(heading);
		PdfPCell headingCell = new PdfPCell();
		
		headingCell.setColspan(2);
		headingCell.addElement(heading1);
		table.addCell(headingCell);
		PdfPCell cellh1 = new PdfPCell();
		PdfPCell cellh2 = new PdfPCell();
	   if(!nokkel.isEmpty()){
		   	String nokkelHeading ="Meldingsnøkkel";
	        cellh1.addElement(getHeading2(nokkelHeading));
			table.addCell(cellh1);
			cellh2.addElement(getText(nokkel));
			table.addCell(cellh2);
	   }	     
        Iterator iterator = linkHashmap.entrySet().iterator();
    	
		while (iterator.hasNext()) {
			PdfPCell cell1 = new PdfPCell();
			PdfPCell cell2 = new PdfPCell();
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			String headingTxt = mapEntry.getKey().toString();
		//	headingTxt = swapText(headingTxt);
			/*if(headingTxt.contains("tab-")){
				String[] textParts = headingTxt.split("-");
				headingTxt = textParts[1];
			} olj 02.09.15 */
			if(headingTxt.contains("p-")){
				String[] textParts = headingTxt.split("-");
				headingTxt = textParts[1];
			}
			headingTxt = WordUtils.capitalize(headingTxt);
			String txt = mapEntry.getValue().toString();
			if(txt.equalsIgnoreCase("M")){
				txt ="Mann";
			}else if(txt.equalsIgnoreCase("K")){
				txt="Kvinne";
			}
			headingTxt = swapText(headingTxt);
			if(!txt.isEmpty()){
				
				cell1.addElement(getHeading2(headingTxt));
				table.addCell(cell1);
				
				cell2.addElement(getText(txt));
				table.addCell(cell2);
				
			}
		}

      
        
		return table;
	}
	

	private String swapText(String txt){
		if(txt.equalsIgnoreCase("Hendelsen-date")){
			txt ="Dato for hendelsen";
		}else if(txt.equalsIgnoreCase("BtnSendinn")){
			txt ="";
		}else if(txt.equalsIgnoreCase("tab-klassifikasjon-sub")){
			txt = "Del Klassifikasjon";
		}else if(txt.equalsIgnoreCase("Pas-kjonn")){
			txt="Kjonn";
		}else if(txt.equalsIgnoreCase("Pas-alder")){
			txt="Alder";
		}else if(txt.equalsIgnoreCase("Ytterligereopp")){
			txt="Ytterligere opplyninger følger";
		}else if(txt.equalsIgnoreCase("Beskrivelsetransfusjon")){
			txt="Beskrivelse av transfusjon";
		}else if(txt.equalsIgnoreCase("tidligeretransfusjon")){
			txt="Tidligere oppgitt transfusjonskomplikasjoner";
		}else if(txt.startsWith("egenskaper")){
			txt="Produktegenskaper";
		}else if(txt.equalsIgnoreCase("antistoff")){
			txt="Sykdommer oppgit";
		}else if(txt.equalsIgnoreCase("ytterligereopp")){
			txt="Ytterligere opplysninger følger";
		}else if(txt.equalsIgnoreCase("transfusjon")){
			txt="Hastegrad";
		}else if(txt.equalsIgnoreCase("transfusjon-date")){
			txt="Transfusjondato";
		}else if(txt.equalsIgnoreCase("klinisk-res")){
			txt="Endelig utfall av skaden";
		}else if(txt.equalsIgnoreCase("temperaturetterstigning")){
			txt="Temperatur før";
		}else if(txt.equalsIgnoreCase("Forebygg")){
			txt="Tiltak";
		}else if(txt.equalsIgnoreCase("Feilblod")){
			txt ="";
		}else if(txt.equalsIgnoreCase("blod")){
			txt="Blodprodukt";
		}else if(txt.equalsIgnoreCase("komp")){
			txt="Anafylaktisk";
		}else if(txt.equalsIgnoreCase("Tapp")){
			txt="Tid til reaksjon";
		}else if(txt.equalsIgnoreCase("Alvor-mang ")){
			txt="Alvorlighetsgrad";
		}else if(txt.equalsIgnoreCase("Arm")){
			txt="Type Komplikasjon";
		}else if(txt.equalsIgnoreCase("Prosess")){
			txt = "Hovedprosess";
		}else if(txt.equalsIgnoreCase("Kvalitetskrav")){
			txt = "Primærårsak";
		}else if(txt.equalsIgnoreCase("tab-klassifikasjon")){
			txt = "Klassifikasjon av feilen/avviket";
		}
		
		return txt;
	}
	
	private Paragraph getHeading1(String txt){
		Paragraph p = new Paragraph();
		Chunk chunk = new Chunk(txt);
        chunk.setUnderline(0.2f, -2f);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        chunk.setFont(font);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        p.add(chunk);
        
        return p;
	}
	private Paragraph getHeading2(String txt){
		Paragraph p = new Paragraph();
        Chunk chunk = new Chunk(txt,  
        		new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
        p.setAlignment(Element.ALIGN_LEFT);
        p.add(chunk);
        return p;
	}
	private Paragraph getText(String txt){
		Paragraph p = new Paragraph();
        Chunk chunk = new Chunk(txt,  
        		new Font(Font.FontFamily.TIMES_ROMAN, 10));
        p.setAlignment(Element.ALIGN_LEFT);
        p.add(chunk);
        
        return p;
	}	
	
	public  String extract(String s,Function <String,String> f){
		return f.apply(s);
	}
	/**
	 * extractString
	 * This routine extracts a substring form a string  using the Function interface
	 * It finds the last index of a string using separator
	 * @param line The original string
	 * @param separator The separator
	 * @param startindex The startindex
	 * @return the substring
	 */
	public String extractString(String line,char separator,int startindex){
		int index = line.lastIndexOf(separator);
		if (index == -1)
			return null;
		
		Function<String,String> f = (String s) -> line.substring(startindex,index);
		Function<String,String> ef = (String s) -> line.substring(index+1);
		if (startindex == -1){
			return extract(line,ef);
		}else
			return extract(line,f);

	}
	/**
	 * sjekkannenalvorligMelding
	 * Denne rutinen sjekker meldingens alvorlighetsgrad, og sender epost til saksbehandlere
	 * dersom meldingen er definert som alvorlig.
	 * 
	 */
	public void sjekkannenalvorligMelding(Annenkomplikasjon annenKomplikasjon, String meldingsNokkel,String klassifikasjon){
		String meldingsKlassifikasjon = annenKomplikasjon.getKlassifikasjon();
		if (meldingsKlassifikasjon != null && meldingsKlassifikasjon.equals(klassifikasjon)){
			sendMeldingtilsaksbehandlere("TEST: Alvorlig melding type Andre hendelser", "Det er mottatt en alvorlig melding meldingsNøkkel: "+meldingsNokkel);
		}
	}
	/**
	 * sjekkgiveralvorligMelding
	 * Denne rutinen sjekker om det er mottatt en alvorlig givermelding, og sender melding til saksbehandlere om det
	 * @param giverKomplikasjon
	 * @param komplikasjonsDiagnose
	 * @param giverOppfolging
	 * @param meldingsNokkel
	 */
	public void sjekkgiveralvorligMelding(Giverkomplikasjon giverKomplikasjon, Komplikasjonsdiagnosegiver komplikasjonsDiagnose, Giveroppfolging giverOppfolging, String meldingsNokkel){
		boolean alvorlig = false;
		String arsak = "";
		for (String alvorlighet : alvorligHendelsegivergrad){
			if (giverKomplikasjon.getAlvorlighetsgrad() != null && alvorlighet.equals(giverKomplikasjon.getAlvorlighetsgrad())){
				alvorlig = true;
				arsak = giverKomplikasjon.getAlvorlighetsgrad();
				break;
			}	
		}
		if (!alvorlig){
			for (String alvorlighet : alvorligGivervarighet){
				if (giverKomplikasjon.getVarighetkomplikasjon() != null && alvorlighet.equals(giverKomplikasjon.getVarighetkomplikasjon())){
					alvorlig = true;
					arsak = giverKomplikasjon.getVarighetkomplikasjon();
					break;
				}
			}
		}
		String typeKompl = komplikasjonsDiagnose.getSystemiskbivirkning();
		char sep = ';';
		String systemiskKompl = extractString(typeKompl, sep, -1);
		if (!alvorlig && systemiskKompl != null){
			for (String alvorlighet : alvorligGiverhendelse){
				if (systemiskKompl.equals(alvorlighet)){
					alvorlig = true;
					arsak = systemiskKompl;
					break;
				}
			}
		}
		if (!alvorlig){
			for (String alvorlighet : alvorligHendelsegiverutfall){
				if (giverKomplikasjon.getKliniskresultat() != null && alvorlighet.equals(giverKomplikasjon.getKliniskresultat())){
					alvorlig = true;
					arsak = giverKomplikasjon.getKliniskresultat();
					break;
				}
			}
		}
		String oppfolgJa = giverOppfolging.getVidereoppfolging();
		String straksTiltak = giverOppfolging.getStrakstiltak();
		String oppfJa = extractString(oppfolgJa, sep, 0);
		String straxTiltak = extractString(straksTiltak, sep, 0);
		if (!alvorlig){
			for (String alvorlighet : alvorligHendelsegiver){
				if (straxTiltak != null && straxTiltak.equals(alvorlighet)){
					alvorlig = true;
					arsak = straxTiltak;
					break;
				}
			}
		}
		if (!alvorlig){
			for (String alvorlighet : alvorligHendelsegiver){
				if (oppfJa != null && oppfJa.equals(alvorlighet)){
					alvorlig = true;
					arsak = oppfJa;
					break;
				}
			}
		}	
		if (alvorlig){
			sendMeldingtilsaksbehandlere("TEST: Alvorlig melding type Giverhendelse", "Det er mottatt en alvorlig givermelding årsak: "+arsak+" Meldingsnøkkel "+meldingsNokkel);

		}
	}
	/**
	 * sendMeldingtilsaksbehandlere
	 * Denne rutinen sender meldinger til saksbehandlere
	 * Den benyttes dersom meldingen som er mottatt er klassfisert som alvorlig
	 * @param subject
	 * @param text
	 */
	/**
	 * @param subject
	 * @param text
	 */
	protected void sendMeldingtilsaksbehandlere(String subject,String text){
		List<Saksbehandler> saksbehandlere = null;
		saksbehandlere = this.saksbehandlingWebservice.collectSaksbehandlere();
		for (Saksbehandler saksbehandler : saksbehandlere){
			emailWebService.setSubject(subject);
			emailWebService.setEmailText(text);
			emailWebService.setMailTo(saksbehandler.getBehandlerepost());
			//	    	 String msg = String.format(tilsakbehandlereMsg+mId+"&diskid="+diskId);
			String name = saksbehandler.getBehandernavn();
			if (!name.equals("Helsedirektoratet"))
				emailWebService.sendEmail(""); //Kommentert bort til stage !!

		}
	}
}
