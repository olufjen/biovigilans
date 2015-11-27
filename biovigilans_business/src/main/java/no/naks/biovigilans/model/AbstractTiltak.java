package no.naks.biovigilans.model;

import java.util.Date;
import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;

/**
 * Tiltak rettet mot pasient
 * @author olj
 *
 */
public class AbstractTiltak extends AbstractModel {
	
	private Date tiltaksDato;
	private Date gjennomfortDato;
	private String beskrivelse;
	private Long pasient_id;
	private Long tiltakid;
	

	protected String[] keys;
	protected String[] tiltakKeys;
	
	protected Map<String,String> forebyggendeTiltakFields;
	protected Map<String,String> tiltakFields;

	protected Map<String,Forebyggendetiltak> alleforebyggendeTiltak;

	
	public Date getTiltaksDato() {
		return tiltaksDato;
	}
	public void setTiltaksDato(Date tiltaksDato) {
		this.tiltaksDato = tiltaksDato;
	}
	public Date getGjennomfortDato() {
		return gjennomfortDato;
	}
	public void setGjennomfortDato(Date gjennomfortDato) {
		this.gjennomfortDato = gjennomfortDato;
	}
	public String getBeskrivelse() {
		return beskrivelse;
	}
	public void setBeskrivelse(String beskrvelse) {
			this.beskrivelse = beskrvelse;
	}
	public Long getPasient_id() {
		return pasient_id;
	}
	public void setPasient_id(Long pasient_id) {
		this.pasient_id = pasient_id;
	}
	public Long getTiltakid() {
		return tiltakid;
	}
	public void setTiltakid(Long tiltakid) {
		this.tiltakid = tiltakid;
	}

	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	public Map<String, Forebyggendetiltak> getAlleforebyggendeTiltak() {
		return alleforebyggendeTiltak;
	}
	public void setAlleforebyggendeTiltak(
			Map<String, Forebyggendetiltak> alleforebyggendeTiltak) {
		this.alleforebyggendeTiltak = alleforebyggendeTiltak;
	}
	public Map<String, String> getTiltakFields() {
		return tiltakFields;
	}
	public void setTiltakFields(Map<String, String> tiltakFields) {
		this.tiltakFields = tiltakFields;
	}

	public String[] getTiltakKeys() {
		return tiltakKeys;
	}
	public void setTiltakKeys(String[] tiltakKeys) {
		this.tiltakKeys = tiltakKeys;
	}
	public void setTiltakFieldMap(String[]userFields){
		tiltakKeys = userFields;
		for (int i = 0; i<1;i++){
			tiltakFields.put(userFields[i],null);
			
		}
	}

}
