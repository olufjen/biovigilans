package no.naks.biovigilans.model;

import java.util.Map;

public interface Forebyggendetiltak {

	public String getTiltakvalg();
	public void setTiltakvalg(String tiltakvalg);
	public String getTiltakbeskrivelse();
	public void setTiltakbeskrivelse(String tiltakbeskrivelse);
	public Long getForebyggendetiltakid();
	public void setForebyggendetiltakid(Long forebyggendetiltakid);
	public Long getTiltakid();
	public void setTiltakid(Long tiltakid);
	public Map<String, String> getForebyggendeTiltakFields();
	public void setForebyggendeTiltakFields(
			Map<String, String> forebyggendeTiltakFields);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();	
	public void setforebyggendefieldMaps(String[]userFields);

	public void saveField(String userField, String userValue);
}
