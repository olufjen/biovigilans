package no.naks.biovigilans.model;

import java.util.List;
import java.util.Map;

public interface Komplikasjonsklassifikasjon {

	public String getKlassifikasjon();
	public void setKlassifikasjon(String klassifikasjon);
	public List<String> getKlassifikasjonList();
	public void setKlassifikasjonList(List<String> klassifikasjonList) ;
	//public String getKlassifikasjon();
	//public void setKlassifikasjon(String klassifikasjon);
	public String getKlassifikasjonsbeskrivelse();
	public void setKlassifikasjonsbeskrivelse(String klassifikasjonsbeskrivelse);
	public Long getKlassifikasjonsid();
	public void setKlassifikasjonsid(Long klassifikasjonsid);
	
	public Long getMeldeidannen();
	public void setMeldeidannen(Long meldeid);	
	public Long getMeldeidpasient();
	public void setMeldeidpasient(Long meldeidpasient);	
	public Map<String,String> getKomplikasjonklassifikasjonFields();
	public void setKomplikasjonklassifikasjonFields(
			Map<String,String> komplikasjonklassifikasjonFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void setkomplikasjonklassifikasjonFieldsMaps(String[]userFields);
	public void saveField(String userField, String userValue);
	public void savetoField(String userField, String userValue);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	public void savetoKomplikasjonklassifikasjon();
	
}
