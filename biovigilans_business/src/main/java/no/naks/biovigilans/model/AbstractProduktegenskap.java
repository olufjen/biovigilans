package no.naks.biovigilans.model;

import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Representerer produktegenskaper for blodprodukt
 * 
 */

public abstract class AbstractProduktegenskap extends AbstractModel implements Produktegenskap{


	private String egenskapBeskrivelse;
	private String egenskapKode;
	private Long produktegenskapId;
	private Long blodProduktId;
	
	protected Map<String,String> produktegenskapFields;
	protected String[] keys;
	
	
	public String getEgenskapBeskrivelse() {
		return egenskapBeskrivelse;
	}
	public void setEgenskapBeskrivelse(String egenskapBeskrivelse) {
		this.egenskapBeskrivelse = egenskapBeskrivelse;
	}
	public String getEgenskapKode() {
		return egenskapKode;
	}
	public void setEgenskapKode(String egenskapKode) {
		this.egenskapKode = egenskapKode;
	}
	public Long getProduktegenskapId() {
		return produktegenskapId;
	}
	public void setProduktegenskapId(Long produktegenskapId) {
		this.produktegenskapId = produktegenskapId;
	}
	public Long getBlodProduktId() {
		return blodProduktId;
	}
	public void setBlodProduktId(Long blodProduktId) {
		this.blodProduktId = blodProduktId;
	}
	public Map<String, String> getProduktegenskapFields() {
		return produktegenskapFields;
	}
	public void setProduktegenskapFields(Map<String, String> produktegenskapFields) {
		this.produktegenskapFields = produktegenskapFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	

}