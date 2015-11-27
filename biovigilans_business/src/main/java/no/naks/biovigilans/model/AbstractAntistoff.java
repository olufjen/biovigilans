package no.naks.biovigilans.model;

import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Viser type antistoff for pasienten.
 * 
 */

public abstract class AbstractAntistoff extends AbstractModel implements Antistoff{

	private String antistoffbeskrivelse;
	private String antistoffKode;
	private Long antistoffId;
	private Long pasient_Id;
	
	protected Map<String,String> antistoffFields;
	protected String[] keys;
	
	public String getAntistoffbeskrivelse() {
		return antistoffbeskrivelse;
	}
	public void setAntistoffbeskrivelse(String antistoffbeskrivelse) {
		this.antistoffbeskrivelse = antistoffbeskrivelse;
	}
	public String getAntistoffKode() {
		return antistoffKode;
	}
	public void setAntistoffKode(String antistoffKode) {
		this.antistoffKode = antistoffKode;
	}

	public Long getPasient_Id() {
		return pasient_Id;
	}
	public void setPasient_Id(Long pasient_Id) {
		this.pasient_Id = pasient_Id;
	}
	public Long getAntistoffId() {
		return antistoffId;
	}
	public void setAntistoffId(Long antistoffId) {
		this.antistoffId = antistoffId;
	}
	public Map<String, String> getAntistoffFields() {
		return antistoffFields;
	}
	public void setAntistoffFields(Map<String, String> antistoffFields) {
		this.antistoffFields = antistoffFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	
	
}