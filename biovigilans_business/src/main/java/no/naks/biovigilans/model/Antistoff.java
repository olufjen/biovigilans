package no.naks.biovigilans.model;

import java.util.Map;

/**
 * Representerer antistoff for pasienten
 * @author olj
 *
 */
public interface Antistoff {

	public String getAntistoffbeskrivelse();
	public void setAntistoffbeskrivelse(String antistoffbeskrivelse);
	public String getAntistoffKode();
	public void setAntistoffKode(String antistoffKode);
	public Long getAntistoffId();
	public void setAntistoffId(Long antistoffId);
	public Long getPasient_Id();
	public void setPasient_Id(Long pasient_Id);
	public Map<String, String> getAntistoffFields();
	public void setAntistoffFields(Map<String, String> antistoffFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void setantistofffieldMaps(String[]userFields);
	public void saveField(String userField, String userValue);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
}
