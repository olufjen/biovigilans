package no.naks.biovigilans.model;

import java.sql.Time;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;



/**
 * En AbstractVigilansmelding er en melding om uønskede hendelser eller komplikasjoner ved blodoverføringer, celler og vev, eller organsdonasjoner. 
 * En AbstractVigilansmelding er enten en Giverkomplikasjon eller en PasientkomplikasjonImpl.
 * 
 */

public class AbstractVigilansmelding extends AbstractModel implements Vigilansmelding{

	/**
	 * Id til meldingen
	 */
	private Long meldeid;

	/**
	 * Fremmednøkkel til melder
	 */
	private Long melderId;
	
	/**
	 * Dato for hendelsen
	 */
	protected String[]keys;
	
	private Date datoforhendelse;
	/**
	 * Klokkeslett for hendelsen
	 */
	private Time klokkesletthendelse;
	/**
	 * Dato når komplikasjonen ble oppdaget
	 */
	private Date datooppdaget;
	/**
	 * Tidspunkt for donasjon eller overføring
	 */
	private Date donasjonoverforing;
	/**
	 * Denne sjekklisten omfatter følgende definisjoner:
	 * Skal meldes videre til HDIR
	 * Skal diskuteres på neste møte
	 * Egnet som eksempler i rapport
	 * Egnet som oppgave på seminar
	 * Trenger ytterligere opplysninger
	 * Trenger å følges opp
	 * Ferdig behandlet
	 */
	private String sjekklistesaksbehandling;
	/**
	 * Eventuelle supplerende opplysninger ved meldingen.
	 */
	private String supplerendeopplysninger;
	/**
	 * Dato meldingen er mottatt
	 */
	private Date meldingsdato;

	private String meldingsnokkel;
	private String formatNokkel; // Formattert meldingsnøkkel 
	private String kladd; // Om denne meldingen er en kladd
	private String godkjent; // Om denne meldingen er godkjent
	private String meldingstype = "";
	
	protected String[]vigilansKeys;	
	protected Map<String,String>vigilansFields;

	
	public String getFormatNokkel() {
		return formatNokkel;
	}
	public void setFormatNokkel(String formatNokkel) {
		this.formatNokkel = formatNokkel;
	}
	public String getMeldingstype() {
		return meldingstype;
	}
	public void setMeldingstype(String meldingstype) {
		this.meldingstype = meldingstype;
	}
	public Long getMeldeid() {
		return meldeid;
	}
	public void setMeldeid(Long meldeid) {
		this.meldeid = meldeid;
	}
	
	public Long getMelderId() {
		return melderId;
	}
	public void setMelderId(Long melderId) {
		this.melderId = melderId;
	}

	public String getKladd() {
/*		
		Map<String,String> userEntries = getVigilansFields();
		String field = "p-ytterligereopp";
		kladd = userEntries.get(field);
		if (kladd == null ||  kladd.isEmpty()){
			kladd = "";
		}
*/		
		return kladd;
	}
	public void setKladd(String kladd) {
		if (kladd == null){
			Map<String,String> userEntries = getVigilansFields();
			String field = "p-ytterligereopp";
			String lkladd = userEntries.get(field);
			kladd = lkladd;
			if (kladd == null ||  kladd.isEmpty()){
				kladd = "";
			}
			
		}
	
		this.kladd = kladd; // p-ytterligereopp
	}
	public String getGodkjent() {
		return godkjent;
	}
	public void setGodkjent(String godkjent) {
		this.godkjent = godkjent;
	}
	
	public Date getDatoforhendelse() {
		return datoforhendelse;
	}
	
	public void setDatoforhendelse(Date datoforhendelse) {
		if(datoforhendelse==null){
			DateFormat dateFormat = 
			            new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			try {
				String strDate = dateFormat.format(date);
				datoforhendelse =   dateFormat.parse(strDate);
			} catch (ParseException e) {
				System.out.println("date format problem: " + e.toString());
			}
		}
		this.datoforhendelse = datoforhendelse;
	}
	
	public Time getKlokkesletthendelse() {
		
		return klokkesletthendelse;
	}
	public void setKlokkesletthendelse(Time klokkesletthendelse) {
		if(klokkesletthendelse==null){
			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
			Date date = new Date();
			try {
				Time time = Time.valueOf(dateFormat.format(date));
				System.out.print(time);
				klokkesletthendelse=time;
				
			} catch (Exception e) {
				System.out.println("date format problem: " + e.toString());
			}
		}
		this.klokkesletthendelse = klokkesletthendelse;
	}
	public Date getDatooppdaget() {
		
		return datooppdaget;
	}
	public void setDatooppdaget(Date datooppdaget) {
		if(datooppdaget==null){
			DateFormat dateFormat = 
			            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date = new Date();
			try {
				String strDate = dateFormat.format(date);
				datooppdaget =   dateFormat.parse(strDate);
			} catch (ParseException e) {
				System.out.println("date format problem: " + e.toString());
			}
		}
		this.datooppdaget = datooppdaget;
	}
	public Date getDonasjonoverforing() {
		
		return donasjonoverforing;
	}
	public void setDonasjonoverforing(Date donasjonoverforing) {
		if(donasjonoverforing == null){
			Map<String,String> userEntries = getVigilansFields();
			String field = "dato-donasjon";
			String strDate = userEntries.get(field);
			if (strDate == null || strDate.isEmpty()){
				donasjonoverforing = null;
			}else{
				DateFormat dateFormat = 
			            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				try {
				donasjonoverforing =   dateFormat.parse(strDate);
				}catch (ParseException e) {
					System.out.println("date format problem: " + e.toString());
				}
			}
		}
		this.donasjonoverforing = donasjonoverforing;
	}
	public String getSjekklistesaksbehandling() {
		return sjekklistesaksbehandling;
	}
	public void setSjekklistesaksbehandling(
			String sjekklistesaksbehandling) {
		this.sjekklistesaksbehandling = sjekklistesaksbehandling;
	}
	public String getSupplerendeopplysninger() {
		return supplerendeopplysninger;
	}
	public void setSupplerendeopplysninger(String supplerendeopplysninger) {
		if (supplerendeopplysninger == null){
			Map<String,String> userEntries = getVigilansFields();
			String field = "beskrivelsetransfusjon";
			String ytterligere = userEntries.get(field);
			supplerendeopplysninger = ytterligere;
			if (supplerendeopplysninger == null ||  supplerendeopplysninger.isEmpty()){
				supplerendeopplysninger = "";
			}
			
		}
	
		this.supplerendeopplysninger = supplerendeopplysninger;
	}
		
	public Map<String, String> getVigilansFields() {
		if(vigilansFields == null){
			vigilansFields= new HashMap<String, String>();
		}
		return vigilansFields;
	}
	public void setVigilansFields(Map<String, String> vigilansFields) {
		this.vigilansFields = vigilansFields;
	}
	
	public String getMeldingsnokkel() {
		return meldingsnokkel;
	}
	public void setMeldingsnokkel(String meldingsnokkel) {
		 Calendar cal = Calendar.getInstance();
		cal.setTime(getMeldingsdato());
		int day =  cal.get(Calendar.DAY_OF_MONTH) ;
		int month= cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		String date= Integer.toString(day);
		date = date + Integer.toString(month);
		date = date + Integer.toString(year);
		String fNokkel = "";
		if(meldingsnokkel == null){
	
			meldingsnokkel = "Hem" + getMeldeid() + date ;
			
		}

		fNokkel = "Hem"+ " "+ getMeldeid() + " " + String.valueOf(day) + " " + String.valueOf(month)+ String.valueOf(year);
		this.meldingsnokkel = meldingsnokkel;
		this.formatNokkel = fNokkel;
	}

	public Date getMeldingsdato() {
		
		return meldingsdato;
	}
	
	public void setMeldingsdato(Date meldingsdato) {
		if(meldingsdato==null){
			 SimpleDateFormat dateFormat = 
			            new SimpleDateFormat("yyyy/MM/dd");
		//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			try {
				String strDate = dateFormat.format(date);
				meldingsdato =   dateFormat.parse(strDate);
			} catch (ParseException e) {
				System.out.println("date format problem: " + e.toString());
			}
		}
		this.meldingsdato = meldingsdato;
	}
	
	public void setMeldingTypes(){
		types = new int[] {Types.DATE,Types.TIME,Types.DATE,Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.VARCHAR};
		utypes = new int[] {Types.DATE,Types.TIME,Types.DATE,Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
	}
	public void setMeldingParams(){
		Long id = getMeldeid();
		if (id == null){
			params = new Object[]{getDatoforhendelse(),getKlokkesletthendelse(),getDatooppdaget(),getDonasjonoverforing(),getSjekklistesaksbehandling(),getSupplerendeopplysninger(),getMeldingsdato(),getKladd()};
		}else
			params = new Object[]{getDatoforhendelse(),getKlokkesletthendelse(),getDatooppdaget(),getDonasjonoverforing(),getSjekklistesaksbehandling(),getSupplerendeopplysninger(),getMeldingsdato(),getMeldingsnokkel(),getKladd(),getMeldeid()};
	}
	
	/**
	 * setMelderTypes
	 * Rutine for oppsett av types til oppdatering av melderId fremmednøkkel og godkjentflagg og flagg for kladd
	 */
	public void setMelderTypes(){
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
	}
	/**
	 * setMelderParams
	 * Rutine for oppsett av parametre til oppdatering av melderid fremmednøkkel
	 */
	public void setMelderParams(){
		params =  new Object[] {getGodkjent(),getKladd(),getMelderId(),getMeldeid()};
	}
	/**
	 * setsakbehandlerTypes
	 * Oppsett av types for oppdatering av saksbehandler status
	 */
	public void setsakbehandlerTypes(){
		types = new int[] {Types.VARCHAR,Types.INTEGER};
	}
	/**
	 * setsaksbehandlerParams
	 * Oppsett av parametre for oppdatering av saksbehandler status
	 */
	public void setsaksbehandlerParams(){
		params =  new Object[] {getSjekklistesaksbehandling(),getMeldeid()};
	}
	/**
	 * setVigilansmeldingfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	/*public void setVigilansmeldingfieldMaps(String[]userFields){
		keys = userFields;
		int size =userFields.length;
		for (int i = 0;i<size;i++){
			vigilansFields.put(userFields[i],null);
		}
	}*/
	
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	/*public void saveField(String userField, String userValue) {
		if (vigilansFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			vigilansFields.put(userField,userValue);	
	
		}
		
	}
*/
	public void saveToVigilansmelding(){
		
		if (getMeldingsnokkel() == null || getMeldingsnokkel().isEmpty() ){
			setKlokkesletthendelse(null);
			setDatooppdaget(null);
			setDonasjonoverforing(null);
			setDatoforhendelse(donasjonoverforing); // OBS OLJ 24.02.15
			setMeldingsdato(null);
			setKladd(null);
			setSupplerendeopplysninger(null);
		}
		if (getMeldingsnokkel() != null){
			setKlokkesletthendelse(null);
			setDatooppdaget(null);
			setDonasjonoverforing(null);
			setDatoforhendelse(	getDatoforhendelse()); 
			setMeldingsdato(null);
			setKladd(null);
			String supplerende = null;
			if (getSupplerendeopplysninger() != null)
				supplerende = new String(getSupplerendeopplysninger());
			setSupplerendeopplysninger(null);
			if (getSupplerendeopplysninger() == null || getSupplerendeopplysninger().isEmpty())
				setSupplerendeopplysninger(supplerende);
			
		}

	}

	
}