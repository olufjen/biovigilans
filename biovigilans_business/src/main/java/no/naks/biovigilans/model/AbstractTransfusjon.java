package no.naks.biovigilans.model;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import no.naks.rammeverk.kildelag.model.AbstractModel;



/**
 * Denne klassen representerer transfusjonene som er gjort ved en gitt pasientkomplikasjonen.
 * 
 */

public abstract class AbstractTransfusjon extends AbstractModel implements Transfusjon{

	private Long transfusjonsId;
	private Long pasient_Id;
	
	/**
	 * Dato for transfusjonen
	 */
	private DateTime transfusjondato; 	// Vi benytter Joda time i transformasjon
	private Date transfusionDate;   	// Vi benytter util.Date mot databasen !!!
	
	private String transDato;
	/**
	 * Klokkeslett for transfusjonen
	 */
	private String transfusjonsklokkeslett;
	private Time transklokke;
	/**
	 * En verdi for hastegrad ved transfusjonen. Den er en av følgende:
	 * - Planlagt
	 * - øyeblikkelig hjelp
	 * - Vet ikke
	 */
	private String hastegrad;
	private String feiltranfudert;
	private String tildigerKomplikasjon;
	/**
	 * Dette er grunnen til at pasienten f�r en transfusjon, for eksempel jernmangelanemi, 
	 * bl�dning fra tarm� De samme betraktninger som under 
	 * Sykdom av betydning for komplikasjonen (ovenfor). M� diskuteres.
	 */
	private String indikasjon;
	/**
	 * Totalt antall poser som er benyttet ved transfusjonen.
	 */
	private int antalenheter;
/**
 * En transfunsjon kan føre til flere pasientkomplikasjoner.
*/	
	protected Map<String,Pasientkomplikasjon> pasientKomplikasjoner;
/**
 * En transfunsjon benytter flere blodprodukter.
*/		
	protected Map<String,Blodprodukt> blodProdukter;
	
/**
* Inneholder navn på input felt i skjermbildet	
*/	
	protected Map<String,String> transfusjonsFields;
	
	protected String[]keys;

	public Long getTransfusjonsId() {
		return transfusjonsId;
	}

	public Long getPasient_Id() {
		return pasient_Id;
	}

	public void setPasient_Id(Long pasient_Id) {
		this.pasient_Id = pasient_Id;
	}

	public void setTransfusjonsId(Long transfusjonsId) {
		this.transfusjonsId = transfusjonsId;
	}

	public Date getTransfusionDate() {
		return transfusionDate;
	}

	public void setTransfusionDate(Date transfusionDate) {
		this.transfusionDate = transfusionDate;
	}

	public DateTime getTransfusjondato() {
		return transfusjondato;
	}

	public void setTransfusjondato(DateTime transfusjondato) {
		this.transfusjondato = transfusjondato;
	}

	public String getTransfusjonsklokkeslett() {
		return transfusjonsklokkeslett;
	}

	public String getTildigerKomplikasjon() {
		return tildigerKomplikasjon;
	}

	public void setTildigerKomplikasjon(String tildigKomplikasjon) {
	
		if (tildigKomplikasjon == null){
			String aProd = null;
			for (int i = 9;i<36;i++){
				if ( keys[i] != null)
					aProd = transfusjonsFields.get(keys[i]);
				if (aProd != null){
					tildigKomplikasjon = aProd;
					break;
				}
			}
		}	
		this.tildigerKomplikasjon = tildigKomplikasjon;
	}

	public void setTransfusjonsklokkeslett(String transfusjonsklokkeslett) {
		if (transfusjonsklokkeslett == null){
			String aProd = null;
			if ( keys[3] != null)
				aProd = transfusjonsFields.get(keys[3]);
				if (aProd != null){
					transfusjonsklokkeslett = aProd;
				
				}

		}
//		DateTimeFormatter formatter = DateTimeFormat.forPattern( "HH:mm:ss" ); // DD er forskjellig fra dd !! .withZone( timeZone ).withLocale( locale )
//		transklokke = formatter.parseDateTime(transfusjonsklokkeslett);
		transklokke = Time.valueOf(transfusjonsklokkeslett+":00");
		this.transfusjonsklokkeslett = transfusjonsklokkeslett;
	}

	public Time getTransklokke() {
		return transklokke;
	}

	public void setTransklokke(Time transklokke) {
		this.transklokke = transklokke;
	}

	public String getHastegrad() {
		return hastegrad;
	}

	public void setHastegrad(String hastegrad) {
		if (hastegrad == null){
			String aProd = null;
			for (int i=5;i<8;i++){
				String vt = keys[i];
				if ( vt != null)
						aProd = transfusjonsFields.get(keys[i]);
				if (aProd != null){
					hastegrad = aProd;
					break;
				}
			}
		}
		this.hastegrad = hastegrad;
	}

	public String getFeiltranfudert() {
		return feiltranfudert;
	}

	/* 
	 * Feltet benyttes til celler og vev autologt alogent bruk
	 */
	public void setFeiltranfudert(String feiltranfudert) {
		if (feiltranfudert == null){
			String aProd = null;
			for (int i=33;i<35;i++){
				String vt = keys[i];
				if ( vt != null)
						aProd = transfusjonsFields.get(keys[i]);
				if (aProd != null){
					feiltranfudert = aProd;
					break;
				}
			}
		}
		this.feiltranfudert = feiltranfudert;
	}

	public String getIndikasjon() {
		return indikasjon;
	}

	public void setIndikasjon(String indikasjon) {
		if (indikasjon == null){
			String aProd = null;
			if ( keys[8] != null)
				aProd = transfusjonsFields.get(keys[8]);
				if (aProd != null){
					indikasjon = aProd;
				
				}

		}
		this.indikasjon = indikasjon;
	}

	public int getAntalenheter() {
		return antalenheter;
	}

	public void setAntalenheter(int antalenheter) {
		this.antalenheter = antalenheter;
	}

	public String getTransDato() {
		return transDato;
	}

	public void setTransDato(String transDato) {
		if (transDato == null){
			String aProd = null;
			if ( keys[4] != null)
				aProd = transfusjonsFields.get(keys[4]);
				if (aProd != null){
					transDato = aProd;
				
				}

		}
		
			//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Locale locale = java.util.Locale.UK;
		DateTimeZone timeZone = DateTimeZone.forID( "Europe/Oslo" );
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "yyyy-MM-dd" ); // DD er forskjellig fra dd !! .withZone( timeZone ).withLocale( locale )
		if (transDato != null){
			transfusjondato = formatter.parseDateTime(transDato);
//			System.out.println("Dato formattert: "+ transfusjondato.toString(formatter));
			transfusionDate = transfusjondato.toDate();
		}

		this.transDato = transDato;
	}

	public Map<String, Pasientkomplikasjon> getPasientKomplikasjoner() {
		return pasientKomplikasjoner;
	}

	public void setPasientKomplikasjoner(
			Map<String, Pasientkomplikasjon> pasientKomplikasjoner) {
		this.pasientKomplikasjoner = pasientKomplikasjoner;
	}
	

	public Map<String, Blodprodukt> getBlodProdukter() {
		return blodProdukter;
	}

	public void setBlodProdukter(Map<String, Blodprodukt> blodProdukter) {
		this.blodProdukter = blodProdukter;
	}
	


	public Map<String, String> getTransfusjonsFields() {
		return transfusjonsFields;
	}

	public void setTransfusjonsFields(Map<String, String> transfusjonsFields) {
		this.transfusjonsFields = transfusjonsFields;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}


	
	
}