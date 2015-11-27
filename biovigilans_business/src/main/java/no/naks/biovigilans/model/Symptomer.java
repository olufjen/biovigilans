package no.naks.biovigilans.model;

import java.util.Map;

public interface Symptomer {

	public Long getSymptomId();
	public void setSymptomId(Long symptomId);

	public Long getMeldeId();
	public void setMeldeId(Long meldeId);
	public String getSymptomklassifikasjon();
	public void setSymptomklassifikasjon(String symptomklassifikasjon);
	public String getSymptombeskrivelse();
	public void setSymptombeskrivelse(String symptombeskrivelse);

	public double getTempFor();
	public void setTempFor(double tempFor);
	public double getTempetter();
	public void setTempetter(double tempetter);
	public String getTempFors();
	public void setTempFors(String tempFors);
	public String getTempEtters();
	public void setTempEtters(String tempEtters);		

	public Map<String, String> getSymptomerFields();
	public void setSymptomerFields(Map<String, String> symptomerFields);
	public String[] getKeys();
	public void distributeValues(String symptom);
	public void distributeTemperature(String temp);
	public void setKeys(String[] keys);
	public void setsymptomerfieldMaps(String[]userFields);
	public void saveField(String userField,String userValue);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
}
