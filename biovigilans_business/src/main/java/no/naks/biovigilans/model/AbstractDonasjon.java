package no.naks.biovigilans.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Denne klassen representerer en donasjon fra en giver.
 * @author olj
 */

public abstract class AbstractDonasjon extends AbstractModel implements Donasjon{

	private Long donasjonsId;
	/**
	 * M� beskrives
	 */
	private String donasjonssted;
	/**
	 * Ja nei, vet ikke
	 */
	private String komplisertvenepunksjon;
	/**
	 * Tappetype vil v�re en av f�lgende:
	 * Fullblods
	 */
	private String tappetype;
	/**
	 * Tappevariighet
	 */
	private String tappevarighet;
	/**
	 * M� beskrives
	 */
	private String lokalisasjonvenepunksjon;
	/**
	 * M�ltid innen 3 timer f�r tapping (Ja, nei Vet ikke)
	 */
	private String maltidfortapping;
	private Date donasjonsdato;
	private int giveId;
	
	protected Map<String,String> donasjonsFields;
	protected String[] keys;
	
	
	public Long getDonasjonsId() {
		return donasjonsId;
	}
	public void setDonasjonsId(Long donasjonsId) {
		this.donasjonsId = donasjonsId;
	}
	
	public String getDonasjonssted() {
		
		return donasjonssted;
	}
	public void setDonasjonssted(String donasjonssted) {
		if(donasjonssted == null){
			Map<String,String> userEntries = getDonasjonsFields();
			String field = "tab-sted";
			donasjonssted = userEntries.get(field);
			if (donasjonssted == null || donasjonssted.trim().equalsIgnoreCase("") ){ //"--- Select ---"
				donasjonssted = "";
			}
		}
		this.donasjonssted = donasjonssted;
	}
	public String getKomplisertvenepunksjon() {
		
		return komplisertvenepunksjon;
	}
	public void setKomplisertvenepunksjon(String komplisertvenepunksjon) {
		if(komplisertvenepunksjon==null){
			Map<String,String> userEntries = getDonasjonsFields();
			String field = "tab-vene";
			komplisertvenepunksjon = userEntries.get(field);
			if (komplisertvenepunksjon == null || komplisertvenepunksjon.isEmpty() ){
				komplisertvenepunksjon = "";
			}
		}
		this.komplisertvenepunksjon = komplisertvenepunksjon;
	}
	public String getTappetype() {
		
		return tappetype;
	}
	public void setTappetype(String tappetype) {
		if(tappetype==null){
			Map<String,String> userEntries = getDonasjonsFields();
			String field = "type-tapping";
			tappetype = userEntries.get(field);
			if (tappetype == null || tappetype.isEmpty() ){
				tappetype = "";
			}else{
				if(tappetype.trim().equalsIgnoreCase("aferese")){
					String typeAferese = userEntries.get("type-aferese");
					if(typeAferese != null ){
						tappetype = tappetype + ";" + typeAferese;
					}
				}
			}
		}
		this.tappetype = tappetype;
	}
	public String getTappevarighet() {
		return tappevarighet;
	}
	public void setTappevarighet(String tappevarighet) {
		if(tappevarighet == null){
			tappevarighet ="";
		}
		this.tappevarighet = tappevarighet;
	}
	public String getLokalisasjonvenepunksjon() {
		return lokalisasjonvenepunksjon;
	}
	public void setLokalisasjonvenepunksjon(String lokalisasjonvenepunksjon) {
		if(lokalisasjonvenepunksjon == null){
			lokalisasjonvenepunksjon = "";
		}
		this.lokalisasjonvenepunksjon = lokalisasjonvenepunksjon;
	}
	public String getMaltidfortapping() {
		return maltidfortapping;
	}
	public void setMaltidfortapping(String maltidfortapping) {
		if(maltidfortapping==null){
			Map<String,String> userEntries = getDonasjonsFields();
			String field = "tab-maltid";
			maltidfortapping = userEntries.get(field);
			if (maltidfortapping == null || maltidfortapping.isEmpty() ){
				maltidfortapping = "";
			}
		}
		this.maltidfortapping = maltidfortapping;
	}
	
	public Date getDonasjonsdato() {
		return donasjonsdato;
	}
	public void setDonasjonsdato(Date donasjonsdato) {
		if(donasjonsdato == null){
			Map<String,String> userEntries = getDonasjonsFields();
			String field = "dato-donasjon";
			String strDate = userEntries.get(field);
			if (strDate == null || strDate.isEmpty()){
				donasjonsdato = null;
			}else{
				DateFormat dateFormat = 
			            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				try {
					donasjonsdato =   dateFormat.parse(strDate);
				}catch (ParseException e) {
					System.out.println("date format problem: " + e.toString());
				}
			}
		}
		this.donasjonsdato = donasjonsdato;
	}
	public Map<String, String> getDonasjonsFields() {
		return donasjonsFields;
	}
	public void setDonasjonsFields(Map<String, String> donasjonsFields) {
		this.donasjonsFields = donasjonsFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	public int getGiveId() {
		return giveId;
	}
	public void setGiveId(int giveId) {
		this.giveId = giveId;
	}
	
	
}