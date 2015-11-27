package no.naks.biovigilans.model;

import java.util.Date;
import java.util.Map;

public interface Donasjon {
	
	
	public Long getDonasjonsId();
	public void setDonasjonsId(Long donasjonsId);
	public String getDonasjonssted();
	public void setDonasjonssted(String donasjonssted);
	public Date getDonasjonsdato();
	public void setDonasjonsdato(Date donasjonsdato);
	public String getKomplisertvenepunksjon();
	public void setKomplisertvenepunksjon(String komplisertvenepunksjon);
	public String getTappetype();
	public void setTappetype(String tappetype);
	public String getTappevarighet();
	public void setTappevarighet(String tappevarighet);
	public String getLokalisasjonvenepunksjon();
	public void setLokalisasjonvenepunksjon(String lokalisasjonvenepunksjon);
	public String getMaltidfortapping();
	public void setMaltidfortapping(String maltidfortapping);
	public Map<String, String> getDonasjonsFields();
	public void setDonasjonsFields(Map<String, String> donasjonsFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	//public void setDonasjonsfieldMaps(String[]userFields);
	//public void saveField(String userField, String userValue);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	public int getGiveId();
	public void setGiveId(int giveId);
	public void saveToField();
	
}
