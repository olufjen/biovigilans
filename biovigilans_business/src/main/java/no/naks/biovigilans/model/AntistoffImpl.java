package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;

/**
 * Konkret modellklasse for antistoff til pasienten 
 * @author olj
 * 
 */
public class AntistoffImpl extends AbstractAntistoff implements Antistoff {

	public AntistoffImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		antistoffFields = new HashMap();
	}
	public void setParams(){
		Long id = getAntistoffId();
		if (id == null){
			params = new Object[]{getAntistoffKode(),getAntistoffbeskrivelse(),getPasient_Id()};
		}else
			params = new Object[]{getAntistoffKode(),getAntistoffbeskrivelse(),getPasient_Id(),getAntistoffId()};
		
	}
	/**
	 * setantistofffieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 * @since 31.01.17 Bruker antistoff tabellen i organer, og utvidet antall felter til 10 OLJ
	 */
	public void setantistofffieldMaps(String[]userFields){

		keys = userFields;
		int ll = userFields.length;
		for (int i = 0; i<ll;i++){
			antistoffFields.put(userFields[i],null);
		}
	
		


	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	
	public void saveField(String userField, String userValue) {
		if (antistoffFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			antistoffFields.put(userField,userValue);	

		}	
	}



	
}
