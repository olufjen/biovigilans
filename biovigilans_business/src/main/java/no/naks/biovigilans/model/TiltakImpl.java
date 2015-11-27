package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class TiltakImpl extends AbstractTiltak implements Tiltak {

	public TiltakImpl() {
		super();
		tiltakFields = new HashMap();
		alleforebyggendeTiltak = new HashMap();
		types = new int[] {Types.DATE,Types.DATE,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.DATE,Types.DATE,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
	}

	public void setParams() {
		Long id = getTiltakid();
		if (id == null){
			params = new Object[]{getTiltaksDato(),getGjennomfortDato(),getBeskrivelse(),getPasient_id()};
		}else
			params = new Object[]{getTiltaksDato(),getGjennomfortDato(),getBeskrivelse(),getTiltakid(),getTiltakid(),getPasient_id()};
	
		
	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	
	public void saveField(String userField, String userValue) {
		if (tiltakFields.containsKey(userField) && userValue != null && !userValue.equals("") ){
			tiltakFields.put(userField,userValue);	
			System.out.println("Tiltak field "+ userField+" value "+userValue);

		}	
	}
	




}
