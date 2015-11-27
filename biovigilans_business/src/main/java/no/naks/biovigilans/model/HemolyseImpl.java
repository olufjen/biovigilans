package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;

public class HemolyseImpl extends AbstractHemolyse implements Hemolyse {

	public HemolyseImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		hemolyseFields = new HashMap();
		
	}
	public void setParams(){
		Long id = getHemolyseid();
		if (id == null){
			params = new Object[]{getHemolyseKode(),getHemolyseParameter(),getUtredningid()};
		}else
			params = new Object[]{getHemolyseKode(),getHemolyseParameter(),getHemolyseid(),getUtredningid()};
		
	}
	public void setHemlysefieldMaps(String[]hemoFields){
		keys = hemoFields;
		int z = hemoFields.length;
		
		for (int i = 0; i<z;i++){
			hemolyseFields.put(hemoFields[i], null);
			
		}
	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	
	public void saveField(String userField, String userValue) {
		if (hemolyseFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			hemolyseFields.put(userField,userValue);	

		}	
		
	}
	public void distributeValues(String hemolyse){
		setHemolyseKode(hemolyse);
		setHemolyseParameter(hemolyse);

	}
}
