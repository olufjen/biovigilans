package no.naks.biovigilans.model;

import java.util.Map;

public interface Komplikasjonsdiagnosegiver {

	
	public Long getKomlikasjonsdiagnoseId();
	public void setKomlikasjonsdiagnoseId(Long komlikasjonsdiagnoseId);
	public String getLokalskadearm();
	public void setLokalskadearm(String lokalskadearm);
	public String getSystemiskbivirkning();
	public void setSystemiskbivirkning(String systemiskbivirkning);
	public String getAnnenreaksjon();
	public void setAnnenreaksjon(String annenreaksjon);
	public String getLokalskadebeskrivelse();
	public void setLokalskadebeskrivelse(String lokalskadebeskrivelse);
	public String getBivirkningbeskrivelse();
	public void setBivirkningbeskrivelse(String bivirkningbeskrivelse);
	public String getAnnenreaksjonbeskrivelse();
	public void setAnnenreaksjonbeskrivelse(String annenreaksjonbeskrivelse);
	public String getKommentar();
	public void setKommentar(String kommentar);
	public Long getMeldeId() ;
	public void setMeldeId(Long meldeId);
	public Map<String, String> getKomplikasjonGiverFields();
	public void setKomplikasjonGiverFields(
			Map<String, String> komplikasjonGiverFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void setKomplikasjonsdiagnoseMaps(String[]userFields);
	public void saveField(String userField, String userValue);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	public void saveToField();
	
}
