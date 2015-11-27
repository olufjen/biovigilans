package no.naks.biovigilans.model;

import java.util.Date;
import java.util.Map;

/**
 * Tiltak
 * Dette er grenssnittet fr alle typer tiltak for pasient
 * @author oluf
 *
 */
public interface Tiltak {

	public Date getTiltaksDato();
	public void setTiltaksDato(Date tiltaksDato);
	public Date getGjennomfortDato();
	public void setGjennomfortDato(Date gjennomfortDato);
	public String getBeskrivelse();
	public void setBeskrivelse(String beskrivelse);
	public Long getPasient_id();
	public void setPasient_id(Long pasient_id);
	public Long getTiltakid();
	public void setTiltakid(Long tiltakid);
		
	public String[] getKeys();
	public void setKeys(String[] keys);
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();		
	public Map<String, Forebyggendetiltak> getAlleforebyggendeTiltak();
	public void setAlleforebyggendeTiltak(
			Map<String, Forebyggendetiltak> alleforebyggendeTiltak);
	public Map<String, String> getTiltakFields();
	public void setTiltakFields(Map<String, String> tiltakFields);

	public String[] getTiltakKeys();
	public void setTiltakKeys(String[] tiltakKeys);
	public void setTiltakFieldMap(String[]userFields);
	public void saveField(String userField, String userValue);
	public void setParams();
}
