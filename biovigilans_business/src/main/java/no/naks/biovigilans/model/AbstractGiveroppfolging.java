package no.naks.biovigilans.model;

import java.util.HashMap;
import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Denne klassen representerer oppf�lging av Giver ved en Giverkomplikasjon.
 * 
 */

public abstract class AbstractGiveroppfolging extends AbstractModel implements Giveroppfolging{

	/**
	 * Denne klassifikasjonen kan ha flere ulike verdier:
	 * - Ingen videre oppf�lging
	 * - Avregistering
	 * - Sperring
	 * - annet??
	 */
	private String klassifikasjongiveroppfolging;
	/**
	 * Beskrivelse av oppf�lgingen (klartekst).
	 */
	private String giveroppfolgingbeskrivelse;
	/**
	 * Giver er sperret frem til en gitt dato
	 */
	private String avregistering;
	private String strakstiltak;
	private String videreoppfolging;
	private Long giveroppfolgingId;
	private Long meldeid;
	protected Map<String,String> giveroppfolgingFields;
	protected String[]keys;
	
	
	
	public Long getGiveroppfolgingId() {
		return giveroppfolgingId;
	}
	public void setGiveroppfolgingId(Long giveroppfolgingId) {
		this.giveroppfolgingId = giveroppfolgingId;
	}
	
	public Long getMeldeid() {
		return meldeid;
	}
	public void setMeldeid(Long meldeid) {
		this.meldeid = meldeid;
	}
	public String getKlassifikasjongiveroppfolging() {
		
		return klassifikasjongiveroppfolging;
	}
	public void setKlassifikasjongiveroppfolging(
			String klassifikasjongiveroppfolging) {
		if(klassifikasjongiveroppfolging == null){
			Map<String,String> userEntries = getGiveroppfolgingFields();
			String field = "tab-annenreaksjon";
			klassifikasjongiveroppfolging = userEntries.get(field);
			if (klassifikasjongiveroppfolging == null){
				klassifikasjongiveroppfolging = "";
			}
		}
		this.klassifikasjongiveroppfolging = klassifikasjongiveroppfolging;
	}
	public String getGiveroppfolgingbeskrivelse() {
		
		return giveroppfolgingbeskrivelse;
	}
	public void setGiveroppfolgingbeskrivelse(String giveroppfolgingbeskrivelse) {
		if(giveroppfolgingbeskrivelse == null){

			Map<String,String> userEntries = getGiveroppfolgingFields();
			String field1 = "tab-alvorlighetgrad";
			String field2="tab-forbedringstiltak";
			String alvorlighetgrad = userEntries.get(field1);
			String forbedringstiltak = userEntries.get(field2);
			if(alvorlighetgrad == null){
				giveroppfolgingbeskrivelse="";
			}else{
				giveroppfolgingbeskrivelse = alvorlighetgrad + "; ";
			}
			if(forbedringstiltak!=null){
				giveroppfolgingbeskrivelse = giveroppfolgingbeskrivelse + forbedringstiltak;
			}
		}
		this.giveroppfolgingbeskrivelse = giveroppfolgingbeskrivelse;
	}
	public String getAvregistering() {
		
		return avregistering;
	}
	public void setAvregistering(String avregistering) {
		if(avregistering == null){
			Map<String,String> userEntries = getGiveroppfolgingFields();
			String field1 = "tab-avreg";
			String field2 ="tab-onske";
			String avreg = userEntries.get(field1);
			String onske = userEntries.get(field2);
			if(avreg != null){
				avregistering = avreg;
			}else{
				avregistering = "";
			}
			
			if (onske != null && avregistering.trim().equalsIgnoreCase("avregistrert-Ja") ){
				avregistering = avregistering +"; " + onske;
			}
		}
		this.avregistering = avregistering;
	}
	
	public String getStrakstiltak() {
		return strakstiltak;
	}
	public void setStrakstiltak(String strakstiltak) {
		if(strakstiltak == null){
			Map<String,String> userEntries = getGiveroppfolgingFields();
			String field1 = "tab-strakstiltak";
			String field2="behandlingTxt";
			String field3 = "tab-behandling-sykehus";
			
			String strakst = userEntries.get(field1);
			String behandlingTxt = userEntries.get(field2);
			String behandlingSykehus = userEntries.get(field3);
			
			if(strakst == null || strakst.isEmpty()){
				strakst ="";
			}else{
				if(strakst.trim().equalsIgnoreCase("Behandlet av blodbankpersonale")){
					if(behandlingTxt != null && !behandlingTxt.isEmpty()){
						strakst = strakst +";"+behandlingTxt;
					}
				}
				if(strakst.trim().equalsIgnoreCase("Behandling på sykehus")){
					if(behandlingSykehus != null && !behandlingSykehus.isEmpty()){
						strakst = strakst +";"+behandlingSykehus;
						String innleggelsetxt = userEntries.get("innleggelsetxt");
						if(innleggelsetxt != null && behandlingSykehus.trim().equalsIgnoreCase("Innleggelse") ){
							strakst = strakst + ";" + innleggelsetxt;
						}
						
					}
				}
			}
			strakstiltak = strakst;
		}
		this.strakstiltak = strakstiltak;
	}
	public String getVidereoppfolging() {
		return videreoppfolging;
	}
	public void setVidereoppfolging(String videreoppfolging) {
		if(videreoppfolging == null){

			Map<String,String> userEntries = getGiveroppfolgingFields();
			String field1 = "tab-behandling";
			String field2="tab-annenreak";
			
			String behandling = userEntries.get(field1);
			String annenreak = userEntries.get(field2);
			
			if(behandling == null || behandling.isEmpty()){
				behandling ="";
			}else{
				if(annenreak != null && !annenreak.isEmpty()){
					behandling = behandling +";"+annenreak;
					
					String legeSpesifiser = userEntries.get("legeSpesifiser");
					String sykemeldinggruppe = userEntries.get("tab-sykemeldinggruppe");
					if(legeSpesifiser != null && ! legeSpesifiser.isEmpty()){
						behandling = behandling + ";" + legeSpesifiser;
					}
					if(sykemeldinggruppe != null && ! sykemeldinggruppe.trim().equalsIgnoreCase("")){
						behandling = behandling + ";" + sykemeldinggruppe;
					}
				}
				
			}
			videreoppfolging = behandling;
		}
		this.videreoppfolging = videreoppfolging;
	}
	public Map<String, String> getGiveroppfolgingFields() {
		if(giveroppfolgingFields == null){
			giveroppfolgingFields = new HashMap<String, String>() ;
		}
		return giveroppfolgingFields;
	}
	public void setGiveroppfolgingFields(Map<String, String> giveroppfolgingFields) {
		this.giveroppfolgingFields = giveroppfolgingFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	
	
}