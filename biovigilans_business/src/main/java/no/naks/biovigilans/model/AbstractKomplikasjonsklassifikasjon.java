package no.naks.biovigilans.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Denne klassen representerer en klassifikasjon av pasientkomplikasjonen. En pasientkomplikasjon kan ha flere klassifikasjoner.
 * 
 */

public abstract class AbstractKomplikasjonsklassifikasjon extends AbstractModel implements Komplikasjonsklassifikasjon{

	/**
	 * Klassifikasjon av komplikasjon, hentes fra Sykdom?
	 */
	private String klassifikasjon ;
	private List<String> klassifikasjonList = new ArrayList<String>();
	/**
	 * Beskrivelse av klassifikasjonen til komplikasjonen
	 */
	private String klassifikasjonsbeskrivelse;
	
	private Long klassifikasjonsid;
	private Long meldeidannen;
	private Long meldeidpasient;
	
	protected Map<String,String> komplikasjonklassifikasjonFields;
	protected String[] keys;
	
	
	
	public String getKlassifikasjon() {
		return klassifikasjon;
	}
	public void setKlassifikasjon(String klassifikasjon) {
		this.klassifikasjon = klassifikasjon;
	}
	public List<String> getKlassifikasjonList() {
		return klassifikasjonList;
	}
	public void setKlassifikasjonList(List<String> klassifikasjonList) {
		this.klassifikasjonList = klassifikasjonList;
	}
	public String getKlassifikasjonsbeskrivelse() {
		return klassifikasjonsbeskrivelse;
	}
	public void setKlassifikasjonsbeskrivelse(String klassfikasjonsbeskrivelse) {
		/*if (klassfikasjonsbeskrivelse == null){
			String aProd = null;
			for (int i=0;i<=21;i++){
				aProd = komplikasjonklassifikasjonFields.get(keys[i]);
				if (aProd != null){
					klassfikasjonsbeskrivelse = aProd;
					break;
				}
			}
		}*/
		this.klassifikasjonsbeskrivelse = klassfikasjonsbeskrivelse;
	}
	public Long getKlassifikasjonsid() {
		return klassifikasjonsid;
	}
	public void setKlassifikasjonsid(Long klassifikasjonsid) {
		this.klassifikasjonsid = klassifikasjonsid;
	}
	
	
	public Long getMeldeidannen() {
		return meldeidannen;
	}
	public void setMeldeidannen(Long meldeidannen) {
		this.meldeidannen = meldeidannen;
	}
	
	public Long getMeldeidpasient() {
		return meldeidpasient;
	}
	public void setMeldeidpasient(Long meldeidpasient) {
		this.meldeidpasient = meldeidpasient;
	}
	public Map<String,String> getKomplikasjonklassifikasjonFields() {
		if(komplikasjonklassifikasjonFields == null){
			komplikasjonklassifikasjonFields = new HashMap<String, String>();
		}
		return komplikasjonklassifikasjonFields;
	}

	public void setKomplikasjonklassifikasjonFields(
			Map<String,String> komplikasjonklassifikasjonFields) {
		this.komplikasjonklassifikasjonFields = komplikasjonklassifikasjonFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	
	
}