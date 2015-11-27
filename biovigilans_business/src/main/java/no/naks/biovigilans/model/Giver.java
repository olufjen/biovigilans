package no.naks.biovigilans.model;

import java.util.Map;

public interface Giver {

	
	public Long getGiverid();
	public void setGiverid(Long giverid);
	public String getKjonn();
	public void setKjonn(String kjonn);
	public String getAlder();
	public void setAlder(String alder);
	public Long getVekt();
	public void setVekt(Long vekt);
	public String getGivererfaring();
	public void setGivererfaring(String givererfaring);
	public String getTidligerekomlikasjonjanei();
	public void setTidligerekomlikasjonjanei(String tidligerekomlikasjonjanei);
	public String getTidligerekomplikasjonforklaring();
	public void setTidligerekomplikasjonforklaring(
			String tidligerekomplikasjonforklaring);
	public String getGivererfaringaferese();
	public void setGivererfaringaferese(String givererfaringaferese);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public Map<String, String> getGiverFields();
	public void setGiverFields(Map<String, String> giverFields);
	//public void setGiverfieldMaps(String[]userFields);
	//public void saveField(String userField, String userValue);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	public void saveToGiver();
	
	
}
