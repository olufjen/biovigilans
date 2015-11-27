package no.naks.biovigilans.model;

import java.util.Map;

public interface Hemolyse {


	public String getHemolyseParameter();
	public void setHemolyseParameter(String hemolyseParameter);
	public String getHemolyseKode();
	public void setHemolyseKode(String hemolyseKode);
	public Long getHemolyseid();
	public void setHemolyseid(Long hemolyseid);
	public Long getUtredningid();
	public void setUtredningid(Long utredningid);
	public Map<String, String> getHemolyseFields();
	public void setHemolyseFields(Map<String, String> hemolyseFields);
	public void setHemlysefieldMaps(String[]hemoFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void saveField(String userField, String userValue);
	public void distributeValues(String hemolyse);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
}
