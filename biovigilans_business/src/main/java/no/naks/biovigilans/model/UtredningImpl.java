package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtredningImpl extends AbstractUtredning implements Utredning {

	private  Map<String,Hemolyse> hemolyseAnalyser;
	
	public UtredningImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		utredningsFields = new HashMap();
		keyList = new ArrayList<String>();
		hemolyseAnalyser = new HashMap();
	}
	
	public void setParams(){
		Long id = getUtredningId();
		if (id == null){
			params = new Object[]{getUtredningsklassifikasjon(),getUtredningbeskrivelse(),getBlodtypeserologisk(),getHemolyseparameter(),getLga(),getPosedyrking(),getPosedyrkingpositiv(),getMeldeId()};
		}else
			params = new Object[]{getUtredningsklassifikasjon(),getUtredningbeskrivelse(),getBlodtypeserologisk(),getHemolyseparameter(),getLga(),getPosedyrking(),getPosedyrkingpositiv(),getMeldeId(),getUtredningId()};
		
	}	
	/**
	 * setutredningfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	public void setutredningfieldMaps(String[]userFields){
		keys = userFields;
		int le = keys.length;
		for (int i = 0;i<le;i++){
			utredningsFields.put(userFields[i],null);
			keyList.add(userFields[i]);
		}
		
	}
	/**
	 * addFields
	 * Denne rutinen legger til flere skjermbildefelter gitt fra nedtrekkslister
	 * @param fields
	 */
	public void addFields(String[]fields){
		int lx = fields.length;
		for (int i = 0;i<lx;i++ ){
			utredningsFields.put(fields[i], null);
			keyList.add(fields[i]);
		}
		keys = new String[keyList.size()];
		keyList.toArray(keys);
	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */

	public void saveField(String userField, String userValue) {
		if (utredningsFields.containsKey(userField) && userValue != null){
			utredningsFields.put(userField,userValue);	
	
		}
		
		
	}
	
	/**
	 * saveUtredning
	 * Denne rutinen lagrer alle brukerverdier til riktig felt
	 * 
	 */
	public void saveUtredning(){
		setBlodtypeserologisk(null);
		setHemolyseparameter(null);
		setLga(null);
		setPosedyrking(null);
		setPosedyrkingpositiv(null);
		setUtredningbeskrivelse(null);
		setUtredningsklassifikasjon(null);
		
	}

	/**
	 * produceHemolyse
	 * Denne rutinen lager korrekt antall Hemlolysparametre etter hvor mange bruker har oppgitt
	 * @param hemolyse
	 */
	public void produceHemolyse(Hemolyse hemolyse) {
		Hemolyse lokalHemolyse = null;
		boolean noTemp = false;
		for (String hemoParam : hemolyse.getHemolyseFields().values() ){
			if (hemoParam != null && !hemoParam.equals("")){
				lokalHemolyse = new HemolyseImpl();
				lokalHemolyse.distributeValues(hemoParam);
				hemolyseAnalyser.put(hemoParam, lokalHemolyse);
			}
		}
	}
	public Map<String, Hemolyse> getHemolyseAnalyser() {
		return hemolyseAnalyser;
	}

	public void setHemolyseAnalyser(Map<String, Hemolyse> hemolyseAnalyser) {
		this.hemolyseAnalyser = hemolyseAnalyser;
	}
	public void distributeValues(String utredningValue){
		setUtredningbeskrivelse(utredningValue);
		setUtredningsklassifikasjon(utredningValue);
		
	}
}
