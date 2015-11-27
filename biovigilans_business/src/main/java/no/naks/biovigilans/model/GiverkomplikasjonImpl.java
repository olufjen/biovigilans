package no.naks.biovigilans.model;


import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.ibm.icu.util.Calendar;


/**
 * En givekomplikasjon er en komplikasjon som oppst�r hos giver etter at hun har gitt blod eller andre organer
 * 
 */

public class GiverkomplikasjonImpl extends AbstractVigilansmelding implements Vigilansmelding,Giverkomplikasjon{

	/**
	 * Hvor skjedde komplikasjonen (venterom, benk)
	 */
	private String stedforkomplikasjon;
	private String tidfratappingtilkompliasjon;
	/**
	 * Nedtrekksvelg
	 */
	private String behandlingssted;
	/**
	 * Tilleggsopplysninger knyttet til denne oppf�lgingen.
	 */
	private String tilleggsopplysninger;
	private String alvorlighetsgrad;
	private String kliniskresultat;
	private Date datosymptomer;
//	private Long meldeId; Meldeid finnes i AbstractVigelansmelding !! Olj 16.01.15
	private Long donasjonid;
	/**
	 * Varghet kan v�re fra fra minutter til m�neder
	 */
	private String varighetkomplikasjon;
	private String symptomMelding = ""; // Benyttet dersom dato for symptomer er fiktiv
	private Map<String,String> komplikasjonsFields;
	private String[] keys;

	
	public GiverkomplikasjonImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.INTEGER, Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.INTEGER, Types.INTEGER};
		komplikasjonsFields = new HashMap<String, String>();
	}
	
	public void setParams(){
		Long id = getMeldeid();
		if (id == null){
			params = new Object[]{getStedforkomplikasjon(),getBehandlingssted(),getTidfratappingtilkompliasjon(),getTilleggsopplysninger(),getAlvorlighetsgrad(),getKliniskresultat(),getVarighetkomplikasjon(),getDatosymptomer(),getMeldeid(), getDonasjonid()};
		}else
			params = new Object[]{getStedforkomplikasjon(),getBehandlingssted(),getTidfratappingtilkompliasjon(),getTilleggsopplysninger(),getAlvorlighetsgrad(),getKliniskresultat(),getVarighetkomplikasjon(),getDatosymptomer(),getMeldeid(), getDonasjonid()};
	}
	public void setGiverkompTypes(){
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.INTEGER, Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.INTEGER, Types.INTEGER};

	}
	/**
	 * setGiverkomplicationfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	public void setGiverkomplicationfieldMaps(String[]userFields){
		keys = userFields;
		int size = keys.length;
		for (int i = 0;i<size;i++){
		komplikasjonsFields.put(userFields[i],null);
		}
	}
	
	public void saveToGiverkomplikasjon(){
		if (getMeldingsnokkel() == null){
			setStedforkomplikasjon(null);
			setBehandlingssted(null);
			setTidfratappingtilkompliasjon(null);
			setTilleggsopplysninger(null);
			setAlvorlighetsgrad(null);
			setKliniskresultat(null);
			setVarighetkomplikasjon(null);
			setDatosymptomer(null);
		}
		if (getMeldingsnokkel() != null){
			setMeldeid(null);
			String sted = new String(getStedforkomplikasjon());
			String behandling = new String(getBehandlingssted());
			String tid = new String(getTidfratappingtilkompliasjon());
			String tillegg = new String(getTilleggsopplysninger());
			String alvor = new String(getAlvorlighetsgrad());
			String klinisk = new String(getKliniskresultat());
			String varighet = new String(getVarighetkomplikasjon());
			Calendar kalender = Calendar.getInstance();
			Date donDato = null;
			if (getDatosymptomer() != null){
				kalender.setTime(getDatosymptomer());
				donDato = kalender.getTime();
			}
			
			setStedforkomplikasjon(null);
			setBehandlingssted(null);
			setTidfratappingtilkompliasjon(null);
			setTilleggsopplysninger(null);
			setAlvorlighetsgrad(null);
			setKliniskresultat(null);
			setVarighetkomplikasjon(null);
			setDatosymptomer(null);
			if (getStedforkomplikasjon() == null || getStedforkomplikasjon().isEmpty())
				setStedforkomplikasjon(sted);
			if (getBehandlingssted() == null || getBehandlingssted().isEmpty())
				setBehandlingssted(behandling);
			if (getTidfratappingtilkompliasjon() == null || getTidfratappingtilkompliasjon().isEmpty())
				setTidfratappingtilkompliasjon(tid);
			if (getTilleggsopplysninger() == null || getTilleggsopplysninger().isEmpty() || getTilleggsopplysninger().equals(";") )
				setTilleggsopplysninger(tillegg);
			if (getAlvorlighetsgrad() == null || getAlvorlighetsgrad().isEmpty())
				setAlvorlighetsgrad(alvor);
			if (getKliniskresultat() == null || getKliniskresultat().isEmpty())
				setKliniskresultat(klinisk);
			if (getVarighetkomplikasjon() == null || getVarighetkomplikasjon().isEmpty())
				setVarighetkomplikasjon(varighet);
			if (donDato != null)
				setDatosymptomer(donDato);
			
		}
	}
	
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	public void saveField(String userField, String userValue) {
		if (komplikasjonsFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			komplikasjonsFields.put(userField,userValue);	
	
		}
		
	}
	

	public String getSymptomMelding() {
		return symptomMelding;
	}

	public void setSymptomMelding(String symptomMelding) {
		this.symptomMelding = symptomMelding;
	}

	public String getStedforkomplikasjon() {
		return stedforkomplikasjon;
	}

	public void setStedforkomplikasjon(String stedforkomplikasjon) {
		if(stedforkomplikasjon == null){
			Map<String,String> userEntries = getKomplikasjonsFields();
			String field = "tab-hvor";
			stedforkomplikasjon = userEntries.get(field);
			if (stedforkomplikasjon == null){
				stedforkomplikasjon = "";
			}
		}
		this.stedforkomplikasjon = stedforkomplikasjon;
	}

	public String getTidfratappingtilkompliasjon() {
		
		return tidfratappingtilkompliasjon;
	}

	public void setTidfratappingtilkompliasjon(String tidfratappingtilkompliasjon) {
		if(tidfratappingtilkompliasjon==null){
			Map<String,String> userEntries = getKomplikasjonsFields();
			String field = "tab-tapp-reak";
			tidfratappingtilkompliasjon = userEntries.get(field);
			if (tidfratappingtilkompliasjon == null){
				tidfratappingtilkompliasjon = "";
			}		}
		this.tidfratappingtilkompliasjon = tidfratappingtilkompliasjon;
	}

	public String getBehandlingssted() {
		return behandlingssted;
	}

	public void setBehandlingssted(String behandlingssted) {
		if(behandlingssted == null){
			behandlingssted="";
		}
		this.behandlingssted = behandlingssted;
	}

	public String getTilleggsopplysninger() {
		return tilleggsopplysninger;
	}

	public void setTilleggsopplysninger(String tilleggsopplysninger) {
		if(tilleggsopplysninger == null){
			Map<String,String> userEntries = getKomplikasjonsFields();
			String field = "tab-forbedringstiltak";
			String forbedrings = userEntries.get(field);
			String avreg = userEntries.get("tab-avreg");
			if (forbedrings == null || forbedrings.isEmpty()){
				forbedrings = "";
			}
			if(avreg == null || avreg.isEmpty()){
				avreg="";
			}
			tilleggsopplysninger = avreg + ";" + forbedrings ;
			
		}
		this.tilleggsopplysninger = tilleggsopplysninger;
	}

	public String getAlvorlighetsgrad() {
	
		return alvorlighetsgrad;
	}

	public void setAlvorlighetsgrad(String alvorlighetsgrad) {
		if(alvorlighetsgrad==null){
			Map<String,String> userEntries = getKomplikasjonsFields();
			String field = "alvor-mang";
			alvorlighetsgrad = userEntries.get(field);
			if (alvorlighetsgrad == null || alvorlighetsgrad.isEmpty()){
				alvorlighetsgrad = "";
			}
			
		}
		this.alvorlighetsgrad = alvorlighetsgrad;
	}

	public String getKliniskresultat() {
		
		return kliniskresultat;
	}

	public void setKliniskresultat(String kliniskresultat) {
		if(kliniskresultat == null){
			Map<String,String> userEntries = getKomplikasjonsFields();
			String field = "tab-klinisk";
			kliniskresultat = userEntries.get(field);
			if (kliniskresultat == null){
				kliniskresultat = "";
			}
		}
		this.kliniskresultat = kliniskresultat;
	}

	
	
	public Date getDatosymptomer() {
		
		return datosymptomer;
	}

	public void setDatosymptomer(Date datosymptomer) {
		if(datosymptomer == null){
			Map<String,String> userEntries = getKomplikasjonsFields();
			String field = "dato-hendelse";
			String strDate = userEntries.get(field);
			if (strDate == null || strDate.isEmpty()){
				datosymptomer = getDonasjonoverforing(); // Dersom dato for symptomer ikke er angitt, settes den til dato for donasjonen
			}else{
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				try {
					datosymptomer =   dateFormat.parse(strDate);
				}catch (ParseException e) {
					System.out.println("date format problem: " + e.toString());
				}
			}
		}
		this.datosymptomer = datosymptomer;
	}

	public String getVarighetkomplikasjon() {
		return varighetkomplikasjon;
	}

	public void setVarighetkomplikasjon(String varighetkomplikasjon) {
		if(varighetkomplikasjon==null){
			Map<String,String> userEntries = getKomplikasjonsFields();
			String field = "tab-varighet";
			varighetkomplikasjon = userEntries.get(field);
			if (varighetkomplikasjon == null){
				varighetkomplikasjon = "";
			}
		}
		this.varighetkomplikasjon = varighetkomplikasjon;
	}

	public Map<String, String> getKomplikasjonsFields() {
		return komplikasjonsFields;
	}
	public void setKomplikasjonsFields(Map<String, String> komplikasjonsFields) {
		this.komplikasjonsFields = komplikasjonsFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}



	public Long getDonasjonid() {
		return donasjonid;
	}

	public void setDonasjonid(Long donasjonid) {
		this.donasjonid = donasjonid;
	}
	

}