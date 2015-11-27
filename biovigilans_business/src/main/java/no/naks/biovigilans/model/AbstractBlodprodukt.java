package no.naks.biovigilans.model;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import no.naks.rammeverk.kildelag.model.AbstractModel;



/**
 * Denne klassen beskriver blodprodukt som er benyttet i overføringen.
 * 
 */

public abstract class AbstractBlodprodukt extends AbstractModel implements Blodprodukt {

	private Long blodProduktId;
	private Long transfusjonsId;
	/**
	 * Hvilken blodtype som er benyttet
	 */
	private String blodtype = null;
	/**
	 * Alder angis som en tappedato
	 */
	private Date alderProdukt;
	private DateTime produktdato; 	// Vi benytter Joda time i transformasjon
	private String aldersProdukt;
	
	/**
	 * Må beskrives
	 */
	private String tappetype = null;
	/**
	 * Må beskrives
	 */
	private String blodprodukt = null;
	/**
	 * Må beskrives
	 */
	private String produktetsegenskap = null;
	
	/**
	 * Antall poser som er benyttet ved transfusjonen.
	 */
	private int antallEnheter = -2;
	private int antallenheterpakke = 0;
	private int antallTromb; // -- Antall trombocyttkonsentrater
	private int antallPlasma; //-- Antall plasamaenheter
	
	private String suspensjon; // Suspensjonsmedium (tappetype aferese)
	
/**
* Inneholder navn på input felt i skjermbildet	
*/
	protected Map<String,String> blodproduktFields; // Inneholder blodproduktene
	protected  Map<String,String> antallFields; // Inneholder antall poser
	protected  Map<String,String> egenskaperFields; // Inneholder egenskaper
	protected  Map<String,String> tappetypeFields; // Inneholder tappetype
	
	protected String[]keys;	

	protected int namePos = 0;
	protected String antallKey = "";
	
	public String getSuspensjon() {
		return suspensjon;
	}
	public void setSuspensjon(String suspnsjon) {
		if (suspnsjon == null){
			String sp = null;
			for (int i = 9;i<12;i++){
				sp = blodproduktFields.get(keys[i]);
				if (sp != null){
					suspnsjon = sp;
					break;
				}
			}
		}
		this.suspensjon = suspnsjon;
	}
	
	
	public int getAntallTromb() {
		return antallTromb;
	}
	public void setAntallTromb(int antalTromb) {
		if (antalTromb == -1){
			String aProd = null;
			aProd = antallFields.get(keys[26]); // OBS !!
			if (aProd != null){
				antalTromb = Integer.parseInt(aProd);
			
			}
		}
		this.antallTromb = antalTromb;
	}
	public int getAntallPlasma() {
		return antallPlasma;
	}
	public void setAntallPlasma(int antalPlasma) {
		if (antalPlasma == -1){
			String aProd = null;
			aProd = antallFields.get(keys[27]); // OBS !!
			if (aProd != null){
				antalPlasma = Integer.parseInt(aProd);
			
			}
		}
		this.antallPlasma = antalPlasma;
	}
	public int getAntallEnheter() {
		return antallEnheter;
	}
	public void setAntallEnheter(int antallEnheter) {
		if (antallEnheter == -1){
			String aProd = null;
			
				aProd = antallFields.get(antallKey);
				if (aProd != null){
					antallEnheter = Integer.parseInt(aProd);
				
				}
		
		}
		this.antallEnheter = antallEnheter;
	}

	public int getAntallenheterpakke() {
		return antallenheterpakke;
	}
	public void setAntallenheterpakke(int antallenhetrpakke) {
		if (antallenhetrpakke == -1){
			String aProd = null;
			aProd = antallFields.get(keys[25]); // OBS !!
			if (aProd != null){
				antallenhetrpakke = Integer.parseInt(aProd);
			
			}
		}
			
		this.antallenheterpakke = antallenhetrpakke;
	}
	public Long getBlodProduktId() {
		return blodProduktId;
	}
	public void setBlodProduktId(Long blodProduktId) {
		this.blodProduktId = blodProduktId;
	}
	public Long getTransfusjonsId() {
		return transfusjonsId;
	}
	public void setTransfusjonsId(Long transfusjonsId) {
		this.transfusjonsId = transfusjonsId;
	}
	public String getBlodtype() {
		return blodtype;
	}
	public void setBlodtype(String blodtype) {
		this.blodtype = blodtype;
	}

	public String getTappetype() {
		return tappetype;
	}
	public void setTappetype(String tapptype) {
		if (tapptype == null){
			String ttype = null;
			for (int i = 6;i<9;i++){
				ttype = blodproduktFields.get(keys[i]);
				if (ttype != null){
					tapptype = ttype;
					break;
				}
			}
		}
			
		this.tappetype = tapptype;
	}
	public String getBlodprodukt() {
		return blodprodukt;
	}
	public void setBlodprodukt(String blodprodukt) {
		if (blodprodukt == null){
			String aProd = null;
			if (namePos > 0){
				aProd = blodproduktFields.get(keys[namePos]);
				if (aProd != null)
					blodprodukt = aProd;
			}
		}
			
		
		this.blodprodukt = blodprodukt;
	}
	public String getProduktetsegenskap() {
		return produktetsegenskap;
	}
	public void setProduktetsegenskap(String produktetsegenskap) {
		if (produktetsegenskap == null){
			String aProd = null;
			for (int i=11;i<=17;i++){
				aProd = egenskaperFields.get(keys[i]);
				if (aProd != null){
					produktetsegenskap = aProd;
					break;
				}
			}
		}
		
		this.produktetsegenskap = produktetsegenskap;
	}
	
	
	public Date getAlderProdukt() {
		return alderProdukt;
	}
	public void setAlderProdukt(Date alderProdukt) {
		this.alderProdukt = alderProdukt;
	}
	public String getAldersProdukt() {
		return aldersProdukt;
	}
	public void setAldersProdukt(String alderssProdukt) {
		if (alderssProdukt == null){
			String aProd = null;
	
				aProd = egenskaperFields.get(keys[4]); // OBS !!
				if (aProd != null){
					alderssProdukt = aProd;
				
				}

		}
		
			//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Locale locale = java.util.Locale.UK;
		DateTimeZone timeZone = DateTimeZone.forID( "Europe/Oslo" );
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "yyyy-MM-dd" ); // DD er forskjellig fra dd !! .withZone( timeZone ).withLocale( locale )
		produktdato = formatter.parseDateTime(alderssProdukt);
//		System.out.println("Dato formattert: "+ transfusjondato.toString(formatter));
		alderProdukt = produktdato.toDate();
		
		
		this.aldersProdukt = alderssProdukt;
	}
	
	public Map<String, String> getBlodproduktFields() {
		return blodproduktFields;
	}
	public void setBlodproduktFields(Map<String, String> blodproduktFields) {
		this.blodproduktFields = blodproduktFields;
	}
	
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	public DateTime getProduktdato() {
		return produktdato;
	}
	public void setProduktdato(DateTime produktdato) {
		this.produktdato = produktdato;
	}
	public Map<String, String> getAntallFields() {
		return antallFields;
	}
	public void setAntallFields(Map<String, String> antallFields) {
		this.antallFields = antallFields;
	}
	public Map<String, String> getEgenskaperFields() {
		return egenskaperFields;
	}
	public void setEgenskaperFields(Map<String, String> egenskaperFields) {
		this.egenskaperFields = egenskaperFields;
	}
	public Map<String, String> getTappetypeFields() {
		return tappetypeFields;
	}
	public void setTappetypeFields(Map<String, String> tappetypeFields) {
		this.tappetypeFields = tappetypeFields;
	}
	public int getNamePos() {
		return namePos;
	}
	public void setNamePos(int namePos) {
		this.namePos = namePos;
	}

	
}