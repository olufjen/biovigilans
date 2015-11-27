package no.naks.biovigilans.model;

import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;

/**
 * Denne klassen representerer pasienten i pasientkomplikasjoner
 * @author olj
 *
 */
public abstract class AbstractPasient extends AbstractModel implements Pasient{

	protected Long pasient_Id;
	protected String kjonn = null;
	protected String aldersGruppe = null;
	protected String[] antiStoff = null;
	protected String inneliggendePoli = null;
	protected String avdeling = null;
	/**
	 * Inneholder navn på input felt i skjermbildet	
	 */
	protected Map<String,String> patientFields;
	
	protected String[]keys;
	
	public Long getPasient_Id() {
		return pasient_Id;
	}
	public void setPasient_Id(Long pasient_Id) {
		this.pasient_Id = pasient_Id;
	}
	public String getKjonn() {

		return kjonn;
	}
	public void setKjonn(String kjnn) {
		if (kjnn == null){
			for (int i=0;i<2;i++){
				kjnn = patientFields.get(keys[i]);
				if (kjnn != null)
					break;
			}
		}
		this.kjonn = kjnn;
	}
	public String getAldersGruppe() {
		return aldersGruppe;
	}
	public void setAldersGruppe(String aldrsGruppe) {
		if (aldrsGruppe == null)
			aldrsGruppe = patientFields.get(keys[2]);
	
		this.aldersGruppe = aldrsGruppe;
	}
	public String[] getAntiStoff() {
		return antiStoff;
	}
	public void setAntiStoff(String antStoff[]) {
		int index = 0;
		if (antStoff == null){
		String aStoff = null;
			for (int i=3;i<7;i++){
				aStoff = patientFields.get(keys[i]);
				if (aStoff != null){
					this.antiStoff[index] = aStoff;
					index++;
					aStoff = null;
				}
			}
		
		}
		else{
			this.antiStoff = antStoff;
		}

	}
	public String getInneliggendePoli() {
		return inneliggendePoli;
	}
	public void setInneliggendePoli(String innliggendePoli) {
		if (innliggendePoli == null){
			
			for (int i=7;i<10;i++){
				innliggendePoli = patientFields.get(keys[i]);
				if (innliggendePoli != null)
					break;
			}
		}
		this.inneliggendePoli = innliggendePoli;
	}
	public String getAvdeling() {
		return avdeling;
	}
	public void setAvdeling(String avdling) {
		if (avdling == null){
			avdling = patientFields.get(keys[10]);
		}
		this.avdeling = avdling;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	public Map<String, String> getPatientFields() {
		return patientFields;
	}
	public void setPatientFields(Map<String, String> patientFields) {
		this.patientFields = patientFields;
	}
	
	
}
