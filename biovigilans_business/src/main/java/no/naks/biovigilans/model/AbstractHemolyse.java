package no.naks.biovigilans.model;

import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Representerer Hemolyseparametre for en utredning
 * 
 */

public abstract class AbstractHemolyse extends AbstractModel implements Hemolyse{


	private String hemolyseParameter;
	private String hemolyseKode;
	private Long hemolyseid;
	private Long utredningid;
	
	protected Map<String,String> hemolyseFields;
	protected String[] keys;
	

	public String getHemolyseParameter() {
		return hemolyseParameter;
	}
	public void setHemolyseParameter(String hemolyseParameter) {
		this.hemolyseParameter = hemolyseParameter;
	}
	public String getHemolyseKode() {
		return hemolyseKode;
	}
	public void setHemolyseKode(String hemolyseKode) {
		this.hemolyseKode = hemolyseKode;
	}
	public Long getHemolyseid() {
		return hemolyseid;
	}
	public void setHemolyseid(Long hemolyseid) {
		this.hemolyseid = hemolyseid;
	}
	public Long getUtredningid() {
		return utredningid;
	}
	public void setUtredningid(Long utredningid) {
		this.utredningid = utredningid;
	}
	public Map<String, String> getHemolyseFields() {
		return hemolyseFields;
	}
	public void setHemolyseFields(Map<String, String> hemolyseFields) {
		this.hemolyseFields = hemolyseFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	
	
}