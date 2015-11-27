package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;

public class SykdomImpl extends AbstractSykdom implements Sykdom {

	public SykdomImpl() {
		super();
		types = new int[] {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		utypes = new int[] {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		sykdomFields = new HashMap();
	}

	public void setParams(){
		Long id = getSykdomId();
		if (id == null){
			params = new Object[]{getPasient_Id(),getSykdomnsnavn(),getSymptomer(),getDiagnosekode()};
		}else
			params = new Object[]{getPasient_Id(),getSykdomnsnavn(),getSymptomer(),getDiagnosekode(),getSykdomId()};
		
	}
	
	/**
	 * setsykdomfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	public void setsykdomfieldMaps(String[]userFields){

		keys = userFields;
		
		sykdomFields.put(userFields[0],getSykdomnsnavn());

		
	//	sykdomFields.put(userFields[7],getInneliggendePoli() );
	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	public void saveField(String userField,String userValue){
		if (sykdomFields.containsKey(userField) && userValue != null){
			sykdomFields.put(userField,userValue);	
	
		}
	}	
	/**
	 * saveSykdom
	 * Denne rutinene lagrer feltverdier til riktig databasefelt
	 */
	public void saveSykdom(){
		setSykdomnsnavn(null);
		setSymptomer(null);
		setDiagnosekode(null);
	}



	
}
