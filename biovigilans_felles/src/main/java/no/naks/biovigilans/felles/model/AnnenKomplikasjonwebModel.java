package no.naks.biovigilans.felles.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sun.xml.bind.v2.schemagen.episode.Klass;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.AnnenkomplikasjonImpl;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.KomplikasjonsklassifikasjonImpl;
import no.naks.biovigilans.model.Vigilansmelding;

/**
 * Denne web modellklassen representerer felter for skjermbildet til andre hendelser.
 * @author olj
 *
 */
public class AnnenKomplikasjonwebModel extends VigilansModel {
	
	private Annenkomplikasjon annenKomplikasjon;
	private Vigilansmelding vigilansmelding;
	private Komplikasjonsklassifikasjon komplikasjonsklassifikasjon;
	protected String[] alvorligHendelse; 
	protected String[] hovedprosesslist;
	protected String[] feilelleravvik;
	protected String[] hendelsenoppdaget;
	protected List<Komplikasjonsklassifikasjon> klassifikasjoner;
	protected List<Vigilansmelding> tidligereVigilans; // Inneholder en liste over tidligere meldinger med samme meldingsnummer
	protected List<Annenkomplikasjon> tidligereAnnenkomp; // Inneholder en liste over tidligere meldinger med samme meldingsnummer
	protected List<Komplikasjonsklassifikasjon> tidligereKlassifikasjoner; // Inneholder en liste over tidligere klassifikasjoner
	List<String> hvagikkgaltList ;
	private String[] aldergruppe;	// Inneholder aldersgrupper tilgjengelig for brukervalg


	public AnnenKomplikasjonwebModel(){
		super();
		tidligereVigilans = new ArrayList<Vigilansmelding>();
		tidligereAnnenkomp = new ArrayList<Annenkomplikasjon>();
		tidligereKlassifikasjoner = new ArrayList<Komplikasjonsklassifikasjon>();
		//hvagikkgaltList = new ArrayList<String>();
	}

	/**
	 * createModelobjects
	 * Denne rutinen lager database modellobjekter.
	 * Den kalles dersom disse objektene ikke finnes fra  før (som i en oppfølgingsmelding)
	 */
	public void createModelobjects(){
		annenKomplikasjon = new AnnenkomplikasjonImpl();
		komplikasjonsklassifikasjon =  new KomplikasjonsklassifikasjonImpl();
		vigilansmelding = (Vigilansmelding) annenKomplikasjon;
		annenKomplikasjon.setHovedprosess("ukjent");
		annenKomplikasjon.setKomplikasjonbeskrivelse("ukjent");
		annenKomplikasjon.setAvvikarsak("ukjent");
		annenKomplikasjon.setDelkode("ukjent");
		annenKomplikasjon.setKlassifikasjon("ukjent");
		annenKomplikasjon.setKlassifikasjonkode("ukjent");
		annenKomplikasjon.setKommentar("ukjent");
		annenKomplikasjon.setKomplikasjondefinisjon("ukjent");
		annenKomplikasjon.setPasientopplysninger("ukjent");
		annenKomplikasjon.setOppdaget("ukjent");
		annenKomplikasjon.setTiltak("ukjent");
		vigilansmelding.setKladd("ukjent");
		komplikasjonsklassifikasjon.setKlassifikasjon("ukjent");
		komplikasjonsklassifikasjon.setKlassifikasjonsbeskrivelse("ukjent");
		klassifikasjoner = new ArrayList<Komplikasjonsklassifikasjon>();
		klassifikasjoner.add(komplikasjonsklassifikasjon);
	}


	public List<Komplikasjonsklassifikasjon> getTidligereKlassifikasjoner() {
		return tidligereKlassifikasjoner;
	}

	public void setTidligereKlassifikasjoner(
			List<Komplikasjonsklassifikasjon> tidligereKlassifikasjoner) {
		this.tidligereKlassifikasjoner = tidligereKlassifikasjoner;
	}

	public List<Vigilansmelding> getTidligereVigilans() {
		return tidligereVigilans;
	}

	public void setTidligereVigilans(List<Vigilansmelding> tidligereVigilans) {
		this.tidligereVigilans = tidligereVigilans;
	}

	public List<Annenkomplikasjon> getTidligereAnnenkomp() {
		return tidligereAnnenkomp;
	}

	public void setTidligereAnnenkomp(List<Annenkomplikasjon> tidligereAnnenkomp) {
		this.tidligereAnnenkomp = tidligereAnnenkomp;
	}

	public List<Komplikasjonsklassifikasjon> getKlassifikasjoner() {
		return klassifikasjoner;
	}

	public void setKlassifikasjoner(
			List<Komplikasjonsklassifikasjon> klassifikasjoner) {
		this.klassifikasjoner = klassifikasjoner;
	}

	public String[] getAldergruppe() {
		return aldergruppe;
	}




	public void setAldergruppe(String[] aldergruppe) {
		this.aldergruppe = aldergruppe;
	}




	public Vigilansmelding getVigilansmelding() {
		return vigilansmelding;
	}

	public void setVigilansmelding(Vigilansmelding vigilansmelding) {
		this.vigilansmelding = vigilansmelding;
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
	public String[] getAlvorligHendelse() {
		return alvorligHendelse;
	}

	public void setAlvorligHendelse(String[] alvorligHendelse) {
		this.alvorligHendelse = alvorligHendelse;
	}
	
	public Annenkomplikasjon getAnnenKomplikasjon() {
		return annenKomplikasjon;
	}

	public void setAnnenKomplikasjon(Annenkomplikasjon annenKomplikasjon) {
		this.annenKomplikasjon = annenKomplikasjon;
	}
	
	
	
	public Komplikasjonsklassifikasjon getKomplikasjonsklassifikasjon() {
		return komplikasjonsklassifikasjon;
	}




	public void setKomplikasjonsklassifikasjon(
			Komplikasjonsklassifikasjon komplikasjonsklassifikasjon) {
		this.komplikasjonsklassifikasjon = komplikasjonsklassifikasjon;
	}

	public List<String> getHvagikkgaltList() {
		return hvagikkgaltList;
	}

	public void setHvagikkgaltList(List<String> hvagikkgaltList) {
		this.hvagikkgaltList = hvagikkgaltList;
	}

	public void distributeTerms(){
		String[] formFields = getFormNames();
		String annenFields[] = {formFields[0],formFields[1],formFields[2],formFields[3],formFields[4],formFields[5],formFields[6],formFields[7],formFields[8],formFields[9],
				formFields[11],formFields[12]};
		annenKomplikasjon.setAnnenkomplicationfieldMaps(annenFields); 
		
		String komplikasjonFields[] = {formFields[10]};
		komplikasjonsklassifikasjon.setkomplikasjonklassifikasjonFieldsMaps(komplikasjonFields);

	}

	public void saveValues(){
		String[] formFields = getFormNames();
		Map<String,String> userEntries = getFormMap(); // formMap inneholder verdier angitt av bruker
		annenKomplikasjon.setAnnenKomplikasjonsFields(userEntries);
		
		/*for(String field: formFields ){
			String userEntry = userEntries.get(field);
			annenKomplikasjon.saveField(field, userEntry);
			komplikasjonsklassifikasjon.saveField(field, userEntry);
			
		}*/
		
		annenKomplikasjon.saveToAnnenKomplikasjon();
	//	komplikasjonsklassifikasjon.savetoKomplikasjonklassifikasjon();
		
	}
	
}
