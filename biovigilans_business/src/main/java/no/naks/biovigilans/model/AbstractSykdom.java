package no.naks.biovigilans.model;

import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;

/**
 * AbstractSykdom beskriver sykdom til pasient som er av betydning for komplikasjonen
 * 
 */

public abstract class AbstractSykdom extends AbstractModel implements Sykdom{

	private Long sykdomId;
	/**
	 * Navn på sykdom
	 */
	private String sykdomnsnavn;
	/**
	 * Beskriver symptomer ved denne sykdommen
	 */
	private String symptomer;
	/**
	 * ICD10 diagnosekode
	 */
	private String diagnosekode;
	/**
	 * Inneholder navn på input felt i skjermbildet	
	 */	
	private long pasient_Id;
	
	protected Map<String,String> sykdomFields;
	
	protected String[]keys;
	

	
	public Long getSykdomId() {
		return sykdomId;
	}


	public void setSykdomId(Long sykdomId) {
		this.sykdomId = sykdomId;
	}


	public long getPasient_Id() {
		return pasient_Id;
	}


	public void setPasient_Id(long pasient_Id) {
		this.pasient_Id = pasient_Id;
	}



	public String getSykdomnsnavn() {
		return sykdomnsnavn;
	}
	public void setSykdomnsnavn(String sykdmnsnavn) {
		if (sykdmnsnavn == null){
			sykdmnsnavn = sykdomFields.get(keys[0]);
		}
		this.sykdomnsnavn = sykdmnsnavn;
	}
	public String getSymptomer() {
		return symptomer;
	}
	public void setSymptomer(String symptmer) {
		if (symptmer == null){
			symptmer = sykdomFields.get(keys[0]);
		}
		this.symptomer = symptmer;
	}
	public String getDiagnosekode() {
		return diagnosekode;
	}
	public void setDiagnosekode(String diagnoskode) {
		if (diagnoskode == null){
			diagnoskode = sykdomFields.get(keys[0]);
		}
		this.diagnosekode = diagnoskode;
	}
	public Map<String, String> getSykdomFields() {
		return sykdomFields;
	}
	public void setSykdomFields(Map<String, String> sykdomFields) {
		this.sykdomFields = sykdomFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	
	
}