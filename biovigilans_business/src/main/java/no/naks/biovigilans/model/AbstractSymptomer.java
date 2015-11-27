package no.naks.biovigilans.model;

import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;



/**
 * En PasientkomplikasjonImpl viser flere ulike symptomer.
 * 
 */

public abstract class AbstractSymptomer extends AbstractModel implements Symptomer{

	private Long symptomId;
	private Long meldeId; 
	/**
	 * Klassifikasjon av komplikasjon, hentes fra AbstractSykdom?
	 */
	private String symptomklassifikasjon;
	/**
	 * Her beskrives symptomene i klartekst
	 */
	private String symptombeskrivelse;
	private double tempFor;	// -- Temperatur angitt før temperaturstigning
	private double tempetter;	//  -- Temperatur angitt etter temperaturstigning
	private String tempFors = "Ikke relevant"; // Temperatur for display
	private String tempEtters = "Ikke relevant";
/**
 * Inneholder navn på input felt i skjermbildet	
 */
	protected Map<String,String> symptomerFields;
	
	protected String[]keys;
	

	public Long getMeldeId() {
		return meldeId;
	}
	public void setMeldeId(Long meldeId) {
		this.meldeId = meldeId;
	}
	public Long getSymptomId() {
		return symptomId;
	}
	public void setSymptomId(Long symptomId) {
		this.symptomId = symptomId;
	}
	public String getSymptomklassifikasjon() {
		return symptomklassifikasjon;
	}
	public void setSymptomklassifikasjon(String symptomklassifikasjon) {
		this.symptomklassifikasjon = symptomklassifikasjon;
	}
	public String getSymptombeskrivelse() {
		return symptombeskrivelse;
	}
	public void setSymptombeskrivelse(String symptombeskrivelse) {
		this.symptombeskrivelse = symptombeskrivelse;
	}
	

	public String getTempFors() {
		return tempFors;
	}
	public void setTempFors(String tempFors) {
		this.tempFors = tempFors;
	}
	public String getTempEtters() {
		return tempEtters;
	}
	public void setTempEtters(String tempEtters) {
		this.tempEtters = tempEtters;
	}
	public double getTempFor() {
		return tempFor;
	}
	public void setTempFor(double tempFor) {
		if (tempFor > 0)
			tempFors.valueOf(tempFor);
		this.tempFor = tempFor;
	}
	public double getTempetter() {
		return tempetter;
	}
	public void setTempetter(double tempetter) {
		if (tempetter > 0)
			tempEtters.valueOf(tempetter);
		this.tempetter = tempetter;
	}
	
	public Map<String, String> getSymptomerFields() {
		return symptomerFields;
	}
	public void setSymptomerFields(Map<String, String> symptomerFields) {
		this.symptomerFields = symptomerFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	
	
	
}