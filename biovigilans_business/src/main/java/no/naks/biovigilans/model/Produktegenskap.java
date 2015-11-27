package no.naks.biovigilans.model;

import java.util.Map;

/**
 * Grensesnitt for Blodprodukt produktegenskaper
 * @author olj
 *
 */
public interface Produktegenskap {

	
	
	public String getEgenskapBeskrivelse();
	public void setEgenskapBeskrivelse(String egenskapBeskrivelse);
	public String getEgenskapKode();
	public void setEgenskapKode(String egenskapKode);
	public Long getProduktegenskapId();
	public void setProduktegenskapId(Long produktegenskapId);
	public Long getBlodProduktId();
	public void setBlodProduktId(Long blodProduktId);
	public Map<String, String> getProduktegenskapFields();
	public void setProduktegenskapFields(Map<String, String> produktegenskapFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void setEgenskaperfieldMaps(String[]egenskapFields);
	public void saveField(String userField, String userValue);
	public void distributeValues(String egenskap);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	public String getEgenskapType();
	public void setEgenskapType(String egenskapType);
	public String[] getEgenskapTyper();
	public void setEgenskapTyper(String[] egenskapTyper);
}
