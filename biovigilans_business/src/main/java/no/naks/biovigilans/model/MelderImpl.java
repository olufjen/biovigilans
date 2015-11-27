package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class MelderImpl extends AbstractMelder implements Melder {

	private Map<String,Vigilansmelding> meldinger;
	
	public MelderImpl(){
		super();
		types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR};
		utypes = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		melderFields = new HashMap<String,String>();
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
