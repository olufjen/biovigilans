package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementasjonen av AbstractMelder
 * @since 08.01.18
 * Endring og lagring av kryptert passord OLJ
 * @author olj
 *
 */
public class MelderImpl extends AbstractMelder implements Melder {

	private Map<String,Vigilansmelding> meldinger;
	private int[] pwtypes;
	private Object[] pwParams;
	public MelderImpl(){
		super();
		pwtypes = new int[]{Types.VARCHAR,Types.INTEGER};
		types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR};
		utypes = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		pwParams = new Object[]{getMelderPassord(),getMelderId()};
		melderFields = new HashMap<String,String>();
	}
	
	public int[] getPwtypes() {
		return pwtypes;
	}

	public void setPwtypes(int[] pwtypes) {
		this.pwtypes = pwtypes;
	}

	public Object[] getPwParams() {
		return pwParams;
	}

	public void setPwParams(Object[] pwParams) {
		this.pwParams = pwParams;
	}

	public void setParams(){
		Long id = getMelderId();
		if(id==null){
			params = new Object[]{getHelseregion(),getHelseforetak(),getSykehus(),getMeldernavn(),getMelderepost(),getMeldertlf(),getMelderPassord()};
		}else{
			params = new Object[]{getHelseregion(),getHelseforetak(),getSykehus(),getMeldernavn(),getMelderepost(),getMeldertlf(),getMelderPassord(),getMelderId()};
		}
	}
	
	/**
	 * setMelderfieldMaps
	 * Denne rutinen setter opp hvilke kontaktfelter som hører til hvilke databasefelter
	 * @param userFields En liste over kontaktfelter
	 */
	public void setMelderfieldMaps(String[]userFields){
		keys = userFields;
		int size =userFields.length;
		for (int i = 0;i<size;i++){
			melderFields.put(userFields[i],null);
		}
	}
	
	
	public Map<String, Vigilansmelding> getMeldinger() {
		return meldinger;
	}

	public void setMeldinger(Map<String, Vigilansmelding> meldinger) {
		this.meldinger = meldinger;
	}

	/**
	 * saveField
	 * Denne rutinen lagrer kontaktfelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	public void saveField(String userField, String userValue) {
		if (melderFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			melderFields.put(userField,userValue);	
	
		}
	}
	
	public void saveToMelder(){
		setHelseregion(null);
		setHelseforetak(null);
		setSykehus(null);
		setMeldernavn(null);
		setMelderepost(null);
		setMeldertlf(null);
		setMelderPassord(null);
	}
}
