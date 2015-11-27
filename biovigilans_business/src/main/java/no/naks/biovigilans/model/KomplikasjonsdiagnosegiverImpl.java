package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;

import org.apache.commons.httpclient.methods.GetMethod;

public class KomplikasjonsdiagnosegiverImpl extends
		AbstractKomplikasjonsdiagnosegiver implements Komplikasjonsdiagnosegiver {

	public KomplikasjonsdiagnosegiverImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		komplikasjonGiverFields = new HashMap<String,String>();
		
	}

	public void setParams(){
		Long id = getKomlikasjonsdiagnoseId();
		if (id == null){
			params = new Object[]{getLokalskadearm(),getSystemiskbivirkning(),getAnnenreaksjon(),getLokalskadebeskrivelse(),getBivirkningbeskrivelse(),getAnnenreaksjonbeskrivelse(),getMeldeId()};
		}else
			params = new Object[]{getLokalskadearm(),getSystemiskbivirkning(),getAnnenreaksjon(),getLokalskadebeskrivelse(),getBivirkningbeskrivelse(),getAnnenreaksjonbeskrivelse(),getMeldeId(),getKomlikasjonsdiagnoseId()};
	}	

	/**
	 * setKomplikasjonsdiagnoseMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	public void setKomplikasjonsdiagnoseMaps(String[]userFields){
		keys = userFields;
	    int size = userFields.length;
		for (int i = 0;i<size;i++){
			komplikasjonGiverFields.put(userFields[i],null);
		}
	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	public void saveField(String userField, String userValue) {
		if (komplikasjonGiverFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			komplikasjonGiverFields.put(userField,userValue);	
	
		}
	}
	
	public void saveToField(){
		if (getKomlikasjonsdiagnoseId() == null){
			setLokalskadearm(null);
			setLokalskadebeskrivelse(null);
			setSystemiskbivirkning(null);
			setBivirkningbeskrivelse(null);
		}

		if (getKomlikasjonsdiagnoseId() != null){
			String lokalskade = new String(getLokalskadearm());
			String lokalbeskrivelse = new String(getLokalskadebeskrivelse());
			String systemisk = new String(getSystemiskbivirkning());
			String bivirkning = new String(getBivirkningbeskrivelse());
			setLokalskadearm(null);
			setLokalskadebeskrivelse(null);
			setSystemiskbivirkning(null);
			setBivirkningbeskrivelse(null);
			if (getLokalskadearm() == null || getLokalskadearm().isEmpty())
				setLokalskadearm(lokalskade);
			if (getLokalskadebeskrivelse() == null || getLokalskadebeskrivelse().isEmpty())
				setLokalskadebeskrivelse(lokalbeskrivelse);
			if (getSystemiskbivirkning() == null || getSystemiskbivirkning().isEmpty())
				setSystemiskbivirkning(systemisk);
			if (getBivirkningbeskrivelse() == null || getBivirkningbeskrivelse().isEmpty())
				setBivirkningbeskrivelse(bivirkning);
			
		}
//		setAnnenreaksjon(null); Dette er fjernet fra skjermbildet !!
//		setAnnenreaksjonbeskrivelse(null);
	}
}
