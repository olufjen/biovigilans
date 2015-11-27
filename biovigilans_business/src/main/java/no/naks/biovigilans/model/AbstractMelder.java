package no.naks.biovigilans.model;

import java.util.HashMap;
import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * AbstractMelder til Biovigilans har tilknytning til og jobber i en helseinstitusjon.
 * Hun melder om u�nskede hendelser eller komplikasjoner ved givning eller mottak av blod, celler og vev, eller organer. 
 * AbstractMelder har ogs� rollen som Oppf�lger.
 * 
 */

public abstract class AbstractMelder extends AbstractModel implements Melder{

	private Long melderId;
	private String meldernavn;
	private String melderepost;
	private String meldertlf;
	private String helseregion;
	private String helseforetak;
	private String sykehus;
	private String melderPassord;
	
	protected Map<String,String>melderFields;
	protected String[]keys;
	
	
	public Long getMelderId() {
		return melderId;
	}
	public void setMelderId(Long melderId) {
		this.melderId = melderId;
	}
	public String getMeldernavn() {
		return meldernavn;
	}
	
	public String getMelderPassord() {
		return melderPassord;
	}
	public void setMelderPassord(String melderPassord) {
		if (melderPassord == null){
			Map<String,String> userEntries = getMelderFields();
			String field = "k-passord";
			melderPassord = userEntries.get(field);
			if (melderPassord == null){
				melderPassord = "";
			}
		}
		this.melderPassord = melderPassord;
	}
	public void setMeldernavn(String meldernavn) {
		if(meldernavn ==  null){
			Map<String,String> userEntries = getMelderFields();
			String field = "k-navn";
			meldernavn = userEntries.get(field);
			if (meldernavn == null){
				meldernavn = "";
			}
		}
		this.meldernavn = meldernavn;
	}
	public String getMelderepost() {
		return melderepost;
	}
	public void setMelderepost(String melderepost) {
		if(melderepost == null){
			Map<String,String> userEntries = getMelderFields();
			String field = "k-epost";
			melderepost = userEntries.get(field);
			if (melderepost == null){
				melderepost = "";
			}
		}
		this.melderepost = melderepost;
	}
	public String getMeldertlf() {
		return meldertlf;
	}
	public void setMeldertlf(String meldertlf) {
		if(meldertlf == null){
			Map<String,String> userEntries = getMelderFields();
			String field = "k-tlf";
			meldertlf = userEntries.get(field);
			if (meldertlf == null){
				meldertlf = "";
			}
		}
		this.meldertlf = meldertlf;
	}
	public String getHelseregion() {
		return helseregion;
	}
	public void setHelseregion(String helseregion) {
		if(helseregion == null){
			Map<String,String> userEntries = getMelderFields();
			String field = "k-helseregion";
			helseregion = userEntries.get(field);
			if (helseregion == null){
				helseregion = "";
			}
		}
		this.helseregion = helseregion;
	}
	public String getHelseforetak() {
		return helseforetak;
	}
	public void setHelseforetak(String helseforetak) {
		if(helseforetak == null) {
			Map<String,String> userEntries = getMelderFields();
			String field = "k-helseforetak";
			helseforetak = userEntries.get(field);
			if (helseforetak == null){
				helseforetak = "";
			}
		}
		this.helseforetak = helseforetak;
	}
	public String getSykehus() {
		return sykehus;
	}
	public void setSykehus(String sykehus) {
		if(sykehus == null){
			Map<String,String> userEntries = getMelderFields();
			String field = "k-sykehus";
			sykehus = userEntries.get(field);
			if (sykehus == null){
				sykehus = "";
			}
		}
		this.sykehus = sykehus;
	}
	public Map<String, String> getMelderFields() {
		if(melderFields == null){
			melderFields = new HashMap<String,String>();
		}
		return melderFields;
	}
	public void setMelderFields(Map<String, String> melderFields) {
		this.melderFields = melderFields;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	
}