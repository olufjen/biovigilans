package no.naks.biovigilans.model;

import java.util.Map;

public interface Giveroppfolging {

	public Long getGiveroppfolgingId();
	public void setGiveroppfolgingId(Long giveroppfolgingId);
	public Long getMeldeid();
	public void setMeldeid(Long meldeid);
	public String getKlassifikasjongiveroppfolging();
	public void setKlassifikasjongiveroppfolging(
			String klassifikasjongiveroppfolging);
	public String getGiveroppfolgingbeskrivelse();
	public void setGiveroppfolgingbeskrivelse(String giveroppfolgingbeskrivelse);
	public String getAvregistering();
	public void setAvregistering(String avregistering);
	public Map<String, String> getGiveroppfolgingFields();
	public void setGiveroppfolgingFields(Map<String, String> giveroppfolgingFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	//public void setGiveroppfolgingfieldMaps(String[]userFields);
	//public void saveField(String userField, String userValue);
	public void saveToField();
	public String getStrakstiltak();
	public void setStrakstiltak(String strakstiltak) ;
	public String getVidereoppfolging();
	public void setVidereoppfolging(String videreoppfolging) ;
}
