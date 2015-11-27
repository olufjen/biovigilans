package no.naks.biovigilans.model;

import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Vigilansmelding som annen komplikasjon
 */

public class AnnenkomplikasjonImpl extends AbstractVigilansmelding implements Vigilansmelding,Annenkomplikasjon{

	
	/**
	 * Klassifikasjon av hendelsen (beskrivelse)
	 */
	private String klassifikasjon;
	/**
	 * Klassifikasjonskode
	 */
	private String Klassifikasjonkode;
	/**
	 * Beskrivelse av hendelsen
	 */
	private String komplikasjonbeskrivelse;
	/**
	 * Hva slags hendelse er dette?
	 */
	private String komplikasjondefinisjon;
	/**
	 * Årsak til avviket
	 */
	private String avvikarsak;
	/**
	 * Under hvilken prosess skjedde hendelsen?
	 */
	private String hovedprosess;
	/**
	 * Gjennomførte eller planlagte tiltak
	 */
	private String tiltak;
	/**
	 * Kommentarer til hendelsen
	 */
	private String kommentar;
	/**
	 * Hvordan ble hendelsen oppdaget
	 */
	private String oppdaget;
	private String delkode;
	private String pasientopplysninger; // Pasientopplysninger ved feil blod transfundert OLJ 15.03.15
	/**
	 * check update the database table or insert
	 */
	private boolean isUpdat =false;
	
	/**
	 * Dato når komplikasjonen ble oppdaget for kvittering
	 */
	//private String datoforhendelseKvittering;

//	private Long meldeid; Meldeid finnes i AbstractVigelansmelding OLJ 16.01.15 !!
	private Map<String,String> annenKomplikasjonsFields;
	private String[] keys;
	
	
	public AnnenkomplikasjonImpl() {
		super();
		types = new int[]  {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		annenKomplikasjonsFields = new HashMap<String,String>();
	}
	/**
	 * Denne starten benyttes til å sttte initverdier til komplikasjonen
	 * @param init
	 */
	public AnnenkomplikasjonImpl(String init) {
		super();
		types = new int[]  {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		annenKomplikasjonsFields = new HashMap<String,String>();
		setInit();
	}	
	private void setInit(){
		setKlassifikasjon("");
		setKlassifikasjonkode("");
		setKomplikasjonbeskrivelse("");
		setKomplikasjondefinisjon("");
		setAvvikarsak("");
		setHovedprosess("");
		setDelkode("");
		setTiltak("");
		setKommentar("");
		setOppdaget("");
		setPasientopplysninger("");
	}
	public void setParams(){
		Long id = getMeldeid();
		if (id == null){
			params = new Object[]{getKlassifikasjon(),getKlassifikasjonkode(),getKomplikasjonbeskrivelse(),getKomplikasjondefinisjon(),getAvvikarsak(),getHovedprosess(),getTiltak(),getKommentar(),getOppdaget(),getDelkode(),getPasientopplysninger(),getMeldeid()};
		}else
			params = new Object[]{getKlassifikasjon(),getKlassifikasjonkode(),getKomplikasjonbeskrivelse(),getKomplikasjondefinisjon(),getAvvikarsak(),getHovedprosess(),getTiltak(),getKommentar(),getOppdaget(),getDelkode(),getPasientopplysninger(),getMeldeid()};
	}
	
	public String getKlassifikasjon() {
		return klassifikasjon;
	}
	public void setKlassifikasjon(String klassifikasjon) {
		if(klassifikasjon == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "tab-klassifikasjon";
			klassifikasjon = userEntries.get(field);
			if (klassifikasjon == null){
				klassifikasjon = "";
			}
			//klassifikasjon = (annenKomplikasjonsFields.get(keys[3])==null ? "" : annenKomplikasjonsFields.get(keys[3])) ;
			/*for(int i=9;i<16;i++){
				if(annenKomplikasjonsFields.get(keys[i])!=null){
					klassifikasjon = klassifikasjon +" ; " + annenKomplikasjonsFields.get(keys[i]);
					i=16;
				}
			}*/
		}
		this.klassifikasjon = klassifikasjon;
	}
	
	
	public String getPasientopplysninger() {
		return pasientopplysninger;
	}

	public void setPasientopplysninger(String pasientopplysninger) {
		if(pasientopplysninger == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "pas-kjonn";
			pasientopplysninger = userEntries.get(field);
			String alder = "pas-alder";
			String temp = userEntries.get(alder);
			pasientopplysninger = pasientopplysninger + ";" + temp;
			if(pasientopplysninger == null)
				pasientopplysninger = "";
		}
		this.pasientopplysninger = pasientopplysninger;
	}

	public boolean isUpdat() {
		return isUpdat;
	}
	public void setUpdat(boolean isUpdat) {
		this.isUpdat = isUpdat;
	}
	public String getDelkode() {
		return delkode;
	}
	
	public void setDelkode(String delkode) {
		if(delkode == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "tab-klassifikasjon-sub";
			delkode = userEntries.get(field);
			if (delkode == null || delkode.trim().equalsIgnoreCase("")){ // --- Select ---
				delkode = "";
			}
			//delkode = annenKomplikasjonsFields.get(keys[9]);
		}
		this.delkode = delkode;
	}
	/*public String getDatoforhendelseKvittering() {
		if(datoforhendelseKvittering == null){
			datoforhendelseKvittering = "";
		}
		return datoforhendelseKvittering;
	}
	public void setDatoforhendelseKvittering(String datoforhendelseKvittering) {
		this.datoforhendelseKvittering = datoforhendelseKvittering;
	}*/
	public String getKlassifikasjonkode() {
		return Klassifikasjonkode;
	}
	public void setKlassifikasjonkode(String klassifikasjonkode) {
		if(klassifikasjonkode==null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "planlagtetiltak";
			Klassifikasjonkode = userEntries.get(field);
			if (Klassifikasjonkode == null){
				Klassifikasjonkode = "";
			}
		}
		Klassifikasjonkode = klassifikasjonkode;
	}
	public String getKomplikasjonbeskrivelse() {
		return komplikasjonbeskrivelse;
	}
	public void setKomplikasjonbeskrivelse(String komplikasjonbeskrivelse) {
		if(komplikasjonbeskrivelse == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "beskrivelse";
			komplikasjonbeskrivelse = userEntries.get(field);
			if (komplikasjonbeskrivelse == null){
				komplikasjonbeskrivelse = "";
			}
			
			//komplikasjonbeskrivelse = (annenKomplikasjonsFields.get(keys[1])==null ? "" : annenKomplikasjonsFields.get(keys[1])) ;
		}
		this.komplikasjonbeskrivelse = komplikasjonbeskrivelse;
	}
	public String getKomplikasjondefinisjon() {
		return komplikasjondefinisjon;
	}
	public void setKomplikasjondefinisjon(String komplikasjondefinisjon) {
		if(komplikasjondefinisjon == null){
			
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "tab-hendelse";
			komplikasjondefinisjon = userEntries.get(field);
			if (komplikasjondefinisjon == null || komplikasjondefinisjon.trim().equalsIgnoreCase("")){
				komplikasjondefinisjon = "";
			}
			//komplikasjondefinisjon = (annenKomplikasjonsFields.get(keys[2])==null ? "" : annenKomplikasjonsFields.get(keys[2])) ;
		}
		this.komplikasjondefinisjon = komplikasjondefinisjon;
	}
	public String getAvvikarsak() {
		return avvikarsak;
	}
	public void setAvvikarsak(String avvikarsak) {
		if(avvikarsak == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "tab-kvalitetskrav";
			avvikarsak = userEntries.get(field);
			if (avvikarsak == null || avvikarsak.trim().equalsIgnoreCase("")){
				avvikarsak = "";
			}
			//avvikarsak = (annenKomplikasjonsFields.get(keys[4])==null ? "" : annenKomplikasjonsFields.get(keys[4])) ;
		}
		this.avvikarsak = avvikarsak;
	}
	public String getHovedprosess() {
		return hovedprosess;
	}
	public void setHovedprosess(String hovedprosess) {
		if(hovedprosess == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "tab-prosess";
			hovedprosess = userEntries.get(field);
			if (hovedprosess == null || hovedprosess.trim().equalsIgnoreCase("")){
				hovedprosess = "";
			}
		/*	hovedprosess = (annenKomplikasjonsFields.get(keys[5])==null ? "" : annenKomplikasjonsFields.get(keys[5])) ;
			if(hovedprosess.equalsIgnoreCase("")){
				hovedprosess=null;
			}*/
		}
		this.hovedprosess = hovedprosess;
	}
	public String getTiltak() {
		return tiltak;
	}
	public void setTiltak(String tiltak) {
		if(tiltak == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "planlagtetiltak";
			tiltak = userEntries.get(field);
			if (tiltak == null || tiltak.trim().equalsIgnoreCase("")){
				tiltak = "";
			}
			
			//tiltak = (annenKomplikasjonsFields.get(keys[7])==null ? "" : annenKomplikasjonsFields.get(keys[7])) ;
		}
		this.tiltak = tiltak;
	}
	public String getKommentar() {
		return kommentar;
	}
	public void setKommentar(String kommentar) {
		if(kommentar == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "hendelsekommentar";
			kommentar = userEntries.get(field);
			if (kommentar == null ){
				kommentar = "";
			}
			//kommentar = (annenKomplikasjonsFields.get(keys[8])==null ? "" : annenKomplikasjonsFields.get(keys[8])) ;
		}
		this.kommentar = kommentar;
	}
	public String getOppdaget() {
		return oppdaget;
	}
	public void setOppdaget(String oppdaget) {
		if(oppdaget == null){
			Map<String,String> userEntries = getAnnenKomplikasjonsFields();
			String field = "hendelseoppdaget";
			oppdaget = userEntries.get(field);
			if (oppdaget == null || oppdaget.trim().equalsIgnoreCase("")){
				oppdaget = "";
			}
			/*oppdaget = (annenKomplikasjonsFields.get(keys[6])==null ? "" : annenKomplikasjonsFields.get(keys[6])) ;
			if(oppdaget.equalsIgnoreCase("--- Select ---")){
				oppdaget=null;
			}*/
		}
		this.oppdaget = oppdaget;
	}
	public Map<String, String> getAnnenKomplikasjonsFields() {
		return annenKomplikasjonsFields;
	}
	public void setAnnenKomplikasjonsFields(
			Map<String, String> annenKomplikasjonsFields) {
		this.annenKomplikasjonsFields = annenKomplikasjonsFields;
		setVigilansFields(annenKomplikasjonsFields);
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	/*public void setKladd(String kladd) {
		if (kladd == null){
			int l = keys.length - 1;
			kladd = annenKomplikasjonsFields.get(keys[l]);
		}
		super.setKladd(kladd);
	}*/
	
	public void setannenKomplikasjonstypes(){
		types = new int[]  {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};

	}
	/**
	 * setAnnenkomplicationfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	public void setAnnenkomplicationfieldMaps(String[]userFields){
		keys = userFields;
		int size = keys.length;
		for (int i = 0; i<size;i++){
			annenKomplikasjonsFields.put(userFields[i],null);
		}
	
		
	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	public void saveField(String userField, String userValue) {
		if (annenKomplikasjonsFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			annenKomplikasjonsFields.put(userField,userValue);	
	
		}
		
	}

	public void saveToAnnenKomplikasjon(){
		if (getMeldingsnokkel() == null){
			setTiltak(null);
			setKomplikasjonbeskrivelse(null);
			setKlassifikasjonkode(null);
			setKlassifikasjon(null);
			setAvvikarsak(null);
			setHovedprosess(null);
			setOppdaget(null);
			setKomplikasjondefinisjon(null);
			setKommentar(null);
			setDelkode(null);
			setKladd(null);
			setPasientopplysninger(null);
		}
		if (getMeldingsnokkel() != null){
			setMeldeid(null);
			String tiltak = new String(getTiltak());
			String beskrivelse = new String(getKomplikasjonbeskrivelse());
			String kode = null;
			if (getKlassifikasjonkode() != null)
				kode = new String(getKlassifikasjonkode()); 
			String klassifikasjon = new String(getKlassifikasjon());
			String avvik = new String(getAvvikarsak());
			String prosess = new String(getHovedprosess());
			String oppdaget = new String(getOppdaget());
			String komplikasjon = new String(getKomplikasjondefinisjon());
			String kommentar = new String(getKommentar());
			String delkode = new String(getDelkode());
			String kladd = new String(getKladd());
			String pasient = new String(getPasientopplysninger());
			setTiltak(null);
			setKomplikasjonbeskrivelse(null);
			setKlassifikasjonkode(null);
			setKlassifikasjon(null);
			setAvvikarsak(null);
			setHovedprosess(null);
			setOppdaget(null);
			setKomplikasjondefinisjon(null);
			setKommentar(null);
			setDelkode(null);
			setKladd(null);
			setPasientopplysninger(null);			
			if (getTiltak() == null || getTiltak().isEmpty())
				setTiltak(tiltak);
			if (getKomplikasjonbeskrivelse() == null || getKomplikasjonbeskrivelse().isEmpty())
				setKomplikasjonbeskrivelse(beskrivelse);
			if (getKlassifikasjonkode() == null || getKlassifikasjonkode().isEmpty())
				setKlassifikasjonkode(kode);
			if (getKlassifikasjon() == null || getKlassifikasjon().isEmpty())
				setKlassifikasjon(klassifikasjon);
			if (getAvvikarsak() == null || getAvvikarsak().isEmpty())
				setAvvikarsak(avvik);
			if (getHovedprosess() == null || getHovedprosess().isEmpty())
				setHovedprosess(prosess);
			if (getOppdaget() == null || getOppdaget().isEmpty())
				setOppdaget(oppdaget);
			if (getKomplikasjondefinisjon() == null || getKomplikasjondefinisjon().isEmpty())
				setKomplikasjondefinisjon(komplikasjon);
			if (getKommentar() == null || getKommentar().isEmpty())
				setKommentar(kommentar);
			if (getDelkode() == null || getDelkode().isEmpty())
				setDelkode(delkode);
			if (getKladd() == null || getKladd().isEmpty())
				setKladd(kladd);
			if (getPasientopplysninger() == null || getPasientopplysninger().isEmpty() || getPasientopplysninger().contains("null") )
				setPasientopplysninger(pasient);
			
		}
	}
	
}

