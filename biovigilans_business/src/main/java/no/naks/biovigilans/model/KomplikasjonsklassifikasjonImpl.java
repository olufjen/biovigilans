package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;

public class KomplikasjonsklassifikasjonImpl extends
		AbstractKomplikasjonsklassifikasjon implements Komplikasjonsklassifikasjon {

	public KomplikasjonsklassifikasjonImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.INTEGER};
		komplikasjonklassifikasjonFields = new HashMap<String,String>();
	}

	public void setParams(){
		Long id = getKlassifikasjonsid();
		if (id == null){
			params = new Object[]{getKlassifikasjon(),getKlassifikasjonsbeskrivelse(),getMeldeidpasient(),getMeldeidannen()};
		}else
			params = new Object[]{getKlassifikasjon(),getKlassifikasjonsbeskrivelse(),getMeldeidpasient(),getMeldeidannen(),getKlassifikasjonsid()};
		
	}

	/**
	 * setkomplikasjonklassifikasjonFieldsMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter 
	 */
	public void setkomplikasjonklassifikasjonFieldsMaps(String[]userFields){
		keys = userFields;
		for (int i = 0; i<userFields.length;i++){
			komplikasjonklassifikasjonFields.put(userFields[i],null);
		}
	
		
	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	public void saveField(String userField, String userValue) {
		if (komplikasjonklassifikasjonFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			komplikasjonklassifikasjonFields.put(userField,userValue);	
			getKlassifikasjonList().add(userValue);
		}	
	}
	/**
	 * savetoField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	public void savetoField(String userField, String userValue) {
		if (komplikasjonklassifikasjonFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			komplikasjonklassifikasjonFields.put(userField,userValue);	
			
		}	
	}	
	
	public void savetoKomplikasjonklassifikasjon(){
		
		setKlassifikasjon(null);
		setKlassifikasjonsbeskrivelse(null);
		getKlassifikasjonList().add(getKlassifikasjon());
	}

}
