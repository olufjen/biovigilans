package no.naks.biovigilans.model;

import java.util.Map;

public interface Utredning {
	public Long getUtredningId();
	public void setUtredningId(Long utredningId);
	public String getUtredningsklassifikasjon();
	public void setUtredningsklassifikasjon(String utredningsklassifikasjon);
	public String getUtredningbeskrivelse();
	public void setUtredningbeskrivelse(String utredningbeskrivelse);
	public String getBlodtypeserologisk();
	public void setBlodtypeserologisk(String blodtypeserologisk);
	public String getHemolyseparameter();
	public void setHemolyseparameter(String hemolyseparameter);
	public String getLga();
	public void setLga(String lga);
	public String getPosedyrking();
	public void setPosedyrking(String posedyrking);
	public String getPosedyrkingpositiv();
	public void setPosedyrkingpositiv(String posedyrkingpositiv);	
	
	public String[] getKeys();
	public void setKeys(String[] keys);
	public Map<String, String> getUtredningsFields();
	public void setUtredningsFields(Map<String, String> utredningsFields);
	public void setutredningfieldMaps(String[]userFields);
	public void addFields(String[]fields);
	public void saveField(String userField,String userValue);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	public void saveUtredning();
	public void distributeValues(String utredningValue);
	public Long getMeldeId();
	public void setMeldeId(Long meldeId);
	public void produceHemolyse(Hemolyse hemolyse);
	public Map<String, Hemolyse> getHemolyseAnalyser();

	public void setHemolyseAnalyser(Map<String, Hemolyse> hemolyseAnalyser);
}
