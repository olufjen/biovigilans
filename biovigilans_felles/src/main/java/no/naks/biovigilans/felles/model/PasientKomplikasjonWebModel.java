package no.naks.biovigilans.felles.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Antistoff;
import no.naks.biovigilans.model.AntistoffImpl;
import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.ForebyggendetiltakImpl;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.PasientImpl;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.SykdomImpl;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.TiltakImpl;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Utredning;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.xml.Letter;
import no.naks.biovigilans.felles.xml.MainTerm;
import no.naks.biovigilans.felles.xml.Term;
import no.naks.biovigilans.felles.xml.Title;
import no.naks.biovigilans.felles.xml.no.KodeNivaa1;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa2;

import javax.xml.bind.JAXBElement;

import org.restlet.data.Parameter;

/**
 *
 * Denne klassen representerer en pasientkomplikasjon ved en transfusjon 
 * Klassen er også kilde til nedtrekksvalg for htmlsidene.
 * Disse valgene kan hentes fra andre kilder
 * ID for Websider: pasientkomplikasjonId = "pasientkomplikasjon"
 * 
 * @author olj
 */
public class PasientKomplikasjonWebModel extends VigilansModel{


	
	private Pasient pasient;
	private Sykdom sykdom;
	private Sykdom transfusjonKomplikasjon;
	private Sykdom annenSykdom;
	private Antistoff antistoff;
	private Forebyggendetiltak forebyggendeTiltak;
	private Tiltak tiltak;
	private Vigilansmelding vigilansmelding;
	
	/**
	 * Inneholder navn på avdelinger tilgjengelig for brukerrvalg
	 */
	private String[] avdelinger; 	// Inneholder navn på avdelinger tilgjengelig for brukerrvalg
	/**
	 * Inneholder aldersgrupper tilgjengelig for brukervalg
	 */
	private String[] aldergruppe;	// Inneholder aldersgrupper tilgjengelig for brukervalg
	/**
	 * Inneholder definisjon av kjønn for mann/kvinne
	 */
	private String[] kjonnValg; 	// Inneholder definisjon av kjønn for mann/kvinne
	private String mann; 			//definisjon kjønn mann
	private String kvinne;			//definisjon kjønn kvinne 
	private String antiStoffjanei;
	private String [] antiStofflabel;
	private String [] antiStoffid;
	private String sykdomSymptom;
	private String transfusjon;
	/**
	 * Viser hvilket kjønn som er valgt 		
	 */
	private String kjonnValgt;		//Viser hvilket kjønn som er valgt 		
	
	private String alder = "-";
	
	private List<TematiskGruppeNivaa2> nivaa2;
	private List<KodeNivaa1> koder;
	private List<MainTerm> terms;
	private List<MainTerm> icd10Elements;
	private List<Title> titles;

	private List<String> icd10Codes;
	/**
	 * ICD10 hovedtermer
	 */
	private List<String> mainTerms;  // ICD10 hovedtermer
	
	private List<ICD10Component> components;
	private Map<String,Object > mapComponent;
	
	/**
	 * Nedtrekk Blodprodukt når man velger blodplasma
	 */
	private List<String>blodProdukt; // Nedtrekk Blodprodukt når man velger blodplasma
	/**
	 * Nedtrekk Hemolyseparametre når hemelyseparametre er positive
	 */
	private List<String>hemolyseParametre; // Nedtrekk Hemolyseparametre når hemelyseparametre er positive
	
	protected List<Vigilansmelding> tidligereVigilans; // Inneholder en liste over tidligere meldinger med samme meldingsnummer
	protected List<Pasientkomplikasjon> tidligerePasientkomp; // Inneholder en liste over tidligere meldinger med samme meldingsnummer
	protected List<Transfusjon> tidligereTransfusjoner; // Inneholder en liste over tidligere transfusjoner med samme meldingsnummer

	protected List<Komplikasjonsklassifikasjon> tidligereKlassifikasjoner; // Inneholder en liste over tidligere klassifikasjoner

	protected List<Blodprodukt>tidligereBlodprodukter;	// Inneholder en liste over tidligere blodprodukter
	protected List<Produktegenskap>tidligereProduktegenskaper;	// Inneholder en liste over tidligere produktegenskaper
	protected List<Pasient>tidligerePasienter;	// Inneholder en liste over tidligere pasientopplysninger
	protected List<Sykdom>tidligereSykdommer;	// Inneholder en liste over tidligere sykdomsopplysninger
	protected List<Utredning>tidligereUtredninger;	// Inneholder en liste over tidligere utredninger
	protected List<Tiltak>tidligereTiltak;	// Inneholder en liste over tidligere tiltak
	protected List<Forebyggendetiltak>tidligereforebyggendeTiltak;	// Inneholder en liste over tidligere forebyggende tiltak
	protected List<Symptomer>tidligereSymptomer;
	
	public PasientKomplikasjonWebModel() {
		super();
		tidligereVigilans = new ArrayList<Vigilansmelding>();
		tidligerePasientkomp = new ArrayList<Pasientkomplikasjon>();
		tidligereTransfusjoner = new ArrayList<Transfusjon>();
		tidligereKlassifikasjoner = new ArrayList<Komplikasjonsklassifikasjon>();
		tidligereBlodprodukter = new ArrayList<Blodprodukt> ();
		tidligereProduktegenskaper = new ArrayList<Produktegenskap>();
		tidligerePasienter = new ArrayList<Pasient>();
		tidligereSykdommer = new ArrayList<Sykdom>();
		tidligereUtredninger = new ArrayList<Utredning>();
		tidligereTiltak = new ArrayList<Tiltak>();
		tidligereforebyggendeTiltak = new ArrayList<Forebyggendetiltak>();
		tidligereSymptomer = new ArrayList<Symptomer>();
		
		sykdomSymptom = "symptom";
		transfusjon = "transfusjon";
	
		icd10Elements = new ArrayList<MainTerm>();
		icd10Codes = new ArrayList<String>();
		mainTerms = new ArrayList<String>();
		titles = new ArrayList<Title>();
	
		components = new ArrayList<ICD10Component>();
		mapComponent = new HashMap<String, Object>();
		blodProdukt = new ArrayList<String>();
		hemolyseParametre = new ArrayList<String>();
		lagret = false;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * createModelobjects
	 * Denne rutinen lager database modellobjekter.
	 * Den kalles dersom disse objektene ikke finnes fra  før (som i en oppfølgingsmelding)
	 */
	public void createModelobjects(){
		pasient = new PasientImpl();
		sykdom = new SykdomImpl();
		annenSykdom = new SykdomImpl();
		antistoff = new AntistoffImpl();
		forebyggendeTiltak = new ForebyggendetiltakImpl();
		tiltak = new TiltakImpl();
		forebyggendeTiltak = new ForebyggendetiltakImpl();
		//sykdom.setSymptomer(sykdomSymptom);
		transfusjonKomplikasjon = new SykdomImpl();
		//transfusjonKomplikasjon.setSymptomer(transfusjon);
		
	}
	
	public List<Symptomer> getTidligereSymptomer() {
		return tidligereSymptomer;
	}

	public void setTidligereSymptomer(List<Symptomer> tidligereSymptomer) {
		this.tidligereSymptomer = tidligereSymptomer;
	}

	public List<Transfusjon> getTidligereTransfusjoner() {
		return tidligereTransfusjoner;
	}

	public void setTidligereTransfusjoner(List<Transfusjon> tidligereTransfusjoner) {
		this.tidligereTransfusjoner = tidligereTransfusjoner;
	}

	public List<Komplikasjonsklassifikasjon> getTidligereKlassifikasjoner() {
		return tidligereKlassifikasjoner;
	}

	public void setTidligereKlassifikasjoner(
			List<Komplikasjonsklassifikasjon> tidligereKlassifikasjoner) {
		this.tidligereKlassifikasjoner = tidligereKlassifikasjoner;
	}

	public List<Blodprodukt> getTidligereBlodprodukter() {
		return tidligereBlodprodukter;
	}

	public void setTidligereBlodprodukter(List<Blodprodukt> tidligereBlodprodukter) {
		this.tidligereBlodprodukter = tidligereBlodprodukter;
	}

	public List<Produktegenskap> getTidligereProduktegenskaper() {
		return tidligereProduktegenskaper;
	}

	public void setTidligereProduktegenskaper(
			List<Produktegenskap> tidligereProduktegenskaper) {
		this.tidligereProduktegenskaper = tidligereProduktegenskaper;
	}

	public List<Pasient> getTidligerePasienter() {
		return tidligerePasienter;
	}

	public void setTidligerePasienter(List<Pasient> tidligerePasienter) {
		this.tidligerePasienter = tidligerePasienter;
	}

	public List<Sykdom> getTidligereSykdommer() {
		return tidligereSykdommer;
	}

	public void setTidligereSykdommer(List<Sykdom> tidligereSykdommer) {
		this.tidligereSykdommer = tidligereSykdommer;
	}

	public List<Utredning> getTidligereUtredninger() {
		return tidligereUtredninger;
	}

	public void setTidligereUtredninger(List<Utredning> tidligereUtredninger) {
		this.tidligereUtredninger = tidligereUtredninger;
	}

	public List<Tiltak> getTidligereTiltak() {
		return tidligereTiltak;
	}

	public void setTidligereTiltak(List<Tiltak> tidligereTiltak) {
		this.tidligereTiltak = tidligereTiltak;
	}

	public List<Forebyggendetiltak> getTidligereforebyggendeTiltak() {
		return tidligereforebyggendeTiltak;
	}

	public void setTidligereforebyggendeTiltak(
			List<Forebyggendetiltak> tidligereforebyggendeTiltak) {
		this.tidligereforebyggendeTiltak = tidligereforebyggendeTiltak;
	}

	public List<Vigilansmelding> getTidligereVigilans() {
		return tidligereVigilans;
	}

	public void setTidligereVigilans(List<Vigilansmelding> tidligereVigilans) {
		this.tidligereVigilans = tidligereVigilans;
	}

	public List<Pasientkomplikasjon> getTidligerePasientkomp() {
		return tidligerePasientkomp;
	}

	public void setTidligerePasientkomp(
			List<Pasientkomplikasjon> tidligerePasientkomp) {
		this.tidligerePasientkomp = tidligerePasientkomp;
	}

	public Sykdom getAnnenSykdom() {
		return annenSykdom;
	}

	public void setAnnenSykdom(Sykdom annenSykdom) {
		this.annenSykdom = annenSykdom;
	}

	public Forebyggendetiltak getForebyggendeTiltak() {
		return forebyggendeTiltak;
	}

	public void setForebyggendeTiltak(Forebyggendetiltak forebyggendeTiltak) {
		this.forebyggendeTiltak = forebyggendeTiltak;
	}

	public Tiltak getTiltak() {
		return tiltak;
	}

	public void setTiltak(Tiltak tiltak) {
		this.tiltak = tiltak;
	}

	public Vigilansmelding getVigilansmelding() {
		return vigilansmelding;
	}

	public void setVigilansmelding(Vigilansmelding vigilansmelding) {
		this.vigilansmelding = vigilansmelding;
	}

	public String getAlder() {
		Map<String,String> userEntries = getFormMap();
		String field = "pas-alder";
		alder = userEntries.get(field);
		return alder;
	}


	public void setAlder(String alder) {
		this.alder = alder;
	}


	public String[] getAvdelinger() {
		return avdelinger;
	}


	public void setAvdelinger(String[] avdelinger) {
		this.avdelinger = avdelinger;
	}


	public List<String> getHemolyseParametre() {
		return hemolyseParametre;
	}


	public void setHemolyseParametre(List<String> hemolyseParametre) {
		this.hemolyseParametre = hemolyseParametre;
	}


	public String getMann() {
		return mann;
	}


	public void setMann(String mann) {
		this.mann = mann;
	}


	public String getKvinne() {
		return kvinne;
	}


	public void setKvinne(String kvinne) {
		this.kvinne = kvinne;
	}


	public Pasient getPasient() {
		return pasient;
	}


	public void setPasient(Pasient pasient) {
		this.pasient = pasient;
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
		kvinne = kjonnValg[0];
		mann = kjonnValg[1];
	}



	public Sykdom getSykdom() {
		return sykdom;
	}


	public void setSykdom(Sykdom sykdom) {
		this.sykdom = sykdom;
	}


	public Sykdom getTransfusjonKomplikasjon() {
		return transfusjonKomplikasjon;
	}


	public void setTransfusjonKomplikasjon(Sykdom transfusjonKomplikasjon) {
		this.transfusjonKomplikasjon = transfusjonKomplikasjon;
	}


	public String getSykdomSymptom() {
		return sykdomSymptom;
	}


	public void setSykdomSymptom(String sykdomSymptom) {
		this.sykdomSymptom = sykdomSymptom;
	}


	public String getAntiStoffjanei() {
		return antiStoffjanei;
	}
	public void setAntiStoffjanei(String antiStoffjanei) {
		this.antiStoffjanei = antiStoffjanei;
	}
	public String[] getAntiStofflabel() {
		return antiStofflabel;
	}
	public void setAntiStofflabel(String[] antiStofflabel) {
		this.antiStofflabel = antiStofflabel;
	}
	public String[] getAntiStoffid() {
		return antiStoffid;
	}
	public void setAntiStoffid(String[] antiStoffid) {
		this.antiStoffid = antiStoffid;
	}

	public String getTransfusjon() {
		return transfusjon;
	}
	public void setTransfusjon(String transfusjon) {
		this.transfusjon = transfusjon;
	}

	public List<MainTerm> getTerms() {
		return terms;
	}
	
	public List getIcd10Elements() {
		return icd10Elements;
	}
	public void setIcd10Elements(List icd10Elements) {
		this.icd10Elements = icd10Elements;
	}
	public List<Title> getTitles() {
		return titles;
	}
	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}

	public List<String> getIcd10Codes() {
		return icd10Codes;
	}
	public void setIcd10Codes(List<String> icd10Codes) {
		this.icd10Codes = icd10Codes;
	}
	public List<String> getMainTerms() {
		return mainTerms;
	}
	public void setMainTerms(List<String> mainTerms) {
		this.mainTerms = mainTerms;
	}
	
	public List<ICD10Component> getComponents() {
		return components;
	}
	public void setComponents(List<ICD10Component> components) {
		this.components = components;
	}
	

	public List<String> getBlodProdukt() {
		return blodProdukt;
	}


	public void setBlodProdukt(List<String> blodProdukt) {
		this.blodProdukt = blodProdukt;
	}


	public Map<String, Object> getMapComponent() {
		return mapComponent;
	}
	public void setMapComponent(Map<String, Object> mapComponent) {
		this.mapComponent = mapComponent;
	}
	
	/**
	 * setnoTerms
	 * Denne rutinen setter opp norske ICD10 koder
	 * @param terms
	 */
	public void setnoTerms( List<KodeNivaa1> koder) {
		this.koder = koder;
	//	 icd10Elements.addAll(nivaa2);
		 String kode = "";
		 String tittel = "";
	     for (KodeNivaa1 term : koder){
	    	kode = term.getKode();
	    	tittel = term.getTittel();
	        mainTerms.add(tittel+";"+kode);
	     }
	     

	}	
	/**
	 * setTerms
	 * Denne rutinen setter opp ICD10 koder
	 * @param terms
	 */
	public void setTerms(List<MainTerm> terms) {
		this.terms = terms;
		 icd10Elements.addAll(terms);
	     for (MainTerm term : terms){
	    	 Title localTitle = term.getTitle();
	    	 List<Serializable>titler = localTitle.getContent();
	    	 String first = (String)titler.get(0);
	    	 String second = "";
	    	 JAXBElement otherElement = null;
	    	 if (titler.size()>=2)
	    		 otherElement = (JAXBElement)titler.get(1);
	    	if (otherElement != null)
	    		second = (String) otherElement.getValue();
//	    	 System.out.println("Titler "+first+" "+second);
	    	 String code = term.getCode();
	    	 if (code != null)
	    		 mainTerms.add(first+second+ " "+code);
	     }
	     

	}
	/**
	 * setblodProducts
	 * Denne rutnen setter opp nedtrekk for blodprodukter
	 * @param blodProdukt - En rekke strengvariable som inneholder blodprodukt
	 * 
	 */
	public void setblodProducts(String[] blodProdukt){
		
		for (String produkt : blodProdukt){
			this.blodProdukt.add(produkt);
		}
	}

	/**
	 * setHemolyseparams
	 * Denne rutnen setter opp nedtrekk for hemolyseparametre
	 * @param hemoparams - En rekke strengvariable som inneholder hemolyseparametre
	 * 
	 */
	public void setHemolyseparams(String[] hemoparams){
		
		for (String hemo : hemoparams){
			this.hemolyseParametre.add(hemo);
		
		}
	}	
	/**
	 * distributeTerms
	 * Denne rutinen setter opp hvilke skjermbildefelt som hører til hvilke modelobjekter (Pasient, Sykdom etc)
	 * Disse feltene har en gitt rekkefølge i tables.properties !!
	 * 
	 */
	public void distributeTerms(){
		String[] formFields = getFormNames();
		String patientFields[] = {formFields[0],formFields[1],formFields[2],formFields[5],formFields[6],formFields[7],formFields[8],formFields[18],formFields[19],formFields[20],formFields[146]};
		String sykdomFields[] = {formFields[9]};
		String transFields[] = {formFields[13]};
		String annenSykdomFields[] = {formFields[13]};
		String antistoffFields[] = {formFields[5],formFields[6],formFields[7],formFields[8],formFields[114],formFields[118],formFields[121],formFields[143],formFields[144],formFields[145]};//formFields[114],formFields[117],formFields[120] lagt til for bruk i organer OLJ 31.01.17
		String tiltakFields[] = {formFields[202],formFields[207]}; // formFields[203],formFields[204],formFields[205],formFields[206] (Ja/nei felt 
		String forebyggendeTiltakFields[] = {formFields[195],
				formFields[196],formFields[197],formFields[198],formFields[199],formFields[200],formFields[201],formFields[202]}; //formFields[192],formFields[193],formFields[194] (Ja/nei felt)
		
		pasient.setPatientfieldMaps(patientFields);
		sykdom.setsykdomfieldMaps(sykdomFields);
		transfusjonKomplikasjon.setsykdomfieldMaps(transFields);
		annenSykdom.setsykdomfieldMaps(annenSykdomFields);
		antistoff.setantistofffieldMaps(antistoffFields);
		forebyggendeTiltak.setforebyggendefieldMaps(forebyggendeTiltakFields);
		tiltak.setTiltakFieldMap(tiltakFields);
	}
	/**
	 * saveValues
	 * Denne rutinen lagrer feltverdier for transfusjons(pasient)komplikasjoner som er angitt av bruker 
	 */
	public void saveValues() {
		String[] formFields = getFormNames(); // Inneholder navn på input felt i skjermbildet
		Map<String,String> userEntries = getFormMap(); // formMap inneholder verdier angitt av bruker
		for (String field : formFields){
			String userEntry = userEntries.get(field);
			pasient.saveField(field, userEntries.get(field));
			sykdom.saveField(field, userEntries.get(field));
			transfusjonKomplikasjon.saveField(field, userEntries.get(field));
			annenSykdom.saveField(field,userEntries.get(field));
			antistoff.saveField(field,userEntries.get(field));
			forebyggendeTiltak.saveField(field,userEntries.get(field));
			tiltak.saveField(field,userEntries.get(field));
		}
		pasient.savetoPatient();
		sykdom.saveSykdom();
		transfusjonKomplikasjon.saveSykdom();
		annenSykdom.saveSykdom();
		pasient.produceAntistoffer(antistoff);
		pasient.produceTiltak(tiltak, forebyggendeTiltak);
		pasient.getSykdommer().put(sykdom.getDiagnosekode(),sykdom);
		pasient.getSykdommer().put(annenSykdom.getDiagnosekode(),annenSykdom);
		kjonnValgt = pasient.getKjonn();
//		pasient.getSykdommer().put(transfusjonKomplikasjon.getDiagnosekode(),transfusjonKomplikasjon);
		
	}
	
	
}
