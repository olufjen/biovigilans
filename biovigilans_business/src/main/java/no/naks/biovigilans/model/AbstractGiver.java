package no.naks.biovigilans.model;

import java.util.HashMap;
import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Denne klassen representerer Giver eller  Donor
 * N�r en giver melder seg til ny avtale, vil eventuelle tidligere komplikasjoner bli vist.
 * 
 */

public abstract class AbstractGiver extends AbstractModel implements Giver{

	protected Long giverid;
	
	protected String kjonn;
	protected String alder;
	/**
	 * Vekt angitt i kilo
	 */
	private Long vekt;
	/**
	 * Om giver er giver for første gang
	 */
	private String givererfaring;
	/**
	 * Om giver har hatt komplikasjoner tidligere (Ja/Nei)
	 */
	private String tidligerekomlikasjonjanei;
	/**
	 * Dersom ja må det være en forklaring
	 */
	private String tidligerekomplikasjonforklaring;
	/**
	 * Aferese er en egen måte å tappe blod på.
	 * Er dette første gang.
	 */
	private String givererfaringaferese;
	
	protected String[]keys;
	protected Map<String,String> giverFields;
	
	
	
	
	public Long getGiverid(){
		return giverid;
	}
	public void setGiverid(Long giverid) {
		this.giverid = giverid;
	}
	public String getKjonn() {
		return kjonn;
	}
	public void setKjonn(String kjonn) {
		if(kjonn==null){
			Map<String,String> userEntries = getGiverFields();
			String field = "tab-kjonn";
			kjonn = userEntries.get(field);
			if (kjonn == null){
				kjonn = "";
			}
		}
		this.kjonn = kjonn;
	}
	public String getAlder() {
		
		return alder;
	}
	public void setAlder(String alder) {
		if(alder==null){
			Map<String,String> userEntries = getGiverFields();
			String field = "tab-alder";
			alder = userEntries.get(field);
			if (alder == null){
				alder = "";
			}
		}
			
		this.alder = alder;
	}
	public Long getVekt() {
		
		return vekt;
	}
	public void setVekt(Long vekt) {
		if(vekt== null){
			Map<String,String> userEntries = getGiverFields();
			String field = "givervektkg";
			String val = userEntries.get(field);
			if(val == null ||  val.isEmpty()){
				vekt = (long) 0;
				
			}else{
				vekt = Long.parseLong(val);
			}
		}
			
		this.vekt = vekt;
	}
	public String getGivererfaring() {
		return givererfaring;
	}
	public void setGivererfaring(String givererfaring) {
		if(givererfaring==null){
			Map<String,String> userEntries = getGiverFields();
			String field = "tab-givererfaring";
			givererfaring = userEntries.get(field);
			if (givererfaring == null){
				givererfaring ="";
			}	
		}
			
		this.givererfaring = givererfaring;
	}
	public String getTidligerekomlikasjonjanei() {
		
		return tidligerekomlikasjonjanei;
	}
	public void setTidligerekomlikasjonjanei(String tidligerekomlikasjonjanei) {
		if(tidligerekomlikasjonjanei==null){
			Map<String,String> userEntries = getGiverFields();
			String field = "tab-tidl-tapp";
			tidligerekomlikasjonjanei = userEntries.get(field);
			if (tidligerekomlikasjonjanei == null){
				tidligerekomlikasjonjanei = "";
			}
		}
			
		this.tidligerekomlikasjonjanei = tidligerekomlikasjonjanei;
	}
	public String getTidligerekomplikasjonforklaring() {
		return tidligerekomplikasjonforklaring;
	}
	public void setTidligerekomplikasjonforklaring(
			String tidligerekomplikasjonforklaring) {
		
		if(tidligerekomplikasjonforklaring==null){
			Map<String,String> userEntries = getGiverFields();
			String field = "tab-reaksjon-beskriv";
			tidligerekomplikasjonforklaring = userEntries.get(field);
			if (tidligerekomplikasjonforklaring == null){
				tidligerekomplikasjonforklaring = "";
			}
		}
		
		this.tidligerekomplikasjonforklaring = tidligerekomplikasjonforklaring;
	}
	public String getGivererfaringaferese() {
		return givererfaringaferese;
	}
	public void setGivererfaringaferese(String givererfaringaferese) {
		if(givererfaringaferese==null){
			Map<String,String> userEntries = getGiverFields();
			String field = "aferese-options";
			givererfaringaferese = userEntries.get(field);
			if (givererfaringaferese == null ){
				field = "fullblod";
				givererfaringaferese = userEntries.get(field);
				if (givererfaringaferese == null ){
					givererfaringaferese="";
				}
			}
		}
		
 		this.givererfaringaferese = givererfaringaferese;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	public Map<String, String> getGiverFields() {
		if(giverFields == null){
			giverFields = new HashMap<String, String>();
		}
		return giverFields;
	}
	public void setGiverFields(Map<String, String> giverFields) {
		this.giverFields = giverFields;
	}
	
}