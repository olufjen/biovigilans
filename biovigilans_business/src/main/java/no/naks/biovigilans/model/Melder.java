package no.naks.biovigilans.model;

import java.util.Map;

public interface Melder {

	
	public Long getMelderId();
	public void setMelderId(Long melderId);
	public String getMeldernavn();
	public void setMeldernavn(String meldernavn);
	public String getMelderepost();
	public void setMelderepost(String melderepost);
	public String getMeldertlf();
	public void setMeldertlf(String meldertlf);
	public String getHelseregion();
	public void setHelseregion(String helseregion) ;
	public String getHelseforetak();
	public void setHelseforetak(String helseforetak) ;
	public String getSykehus() ;
	public void setSykehus(String sykehus) ;
	public Map<String, String> getMelderFields();
	public void setMelderFields(Map<String, String> melderFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void setParams();
	public int[] getTypes();
	public int[] getUtypes();
	public Object[] getParams();
	public void setMelderfieldMaps(String[]userFields);
	public void saveField(String userField, String userValue);
	public void saveToMelder();
	public Map<String, Vigilansmelding> getMeldinger();
	public void setMeldinger(Map<String, Vigilansmelding> meldinger);
	public String getMelderPassord();
	public void setMelderPassord(String melderPassord);
	
}
