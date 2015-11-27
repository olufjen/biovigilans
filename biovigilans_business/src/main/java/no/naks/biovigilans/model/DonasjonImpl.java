package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ibm.icu.util.Calendar;

public class DonasjonImpl extends AbstractDonasjon implements Donasjon {

	public DonasjonImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.INTEGER,Types.INTEGER};
		donasjonsFields = new HashMap();
		
	}

	public void setParams(){
		Long id = getDonasjonsId();
		if (id == null){
			params = new Object[]{getDonasjonssted(),getKomplisertvenepunksjon(),getTappetype(),getTappevarighet(),getMaltidfortapping(),getLokalisasjonvenepunksjon(),getDonasjonsdato(),getGiveId()};
		}else
			params = new Object[]{getDonasjonssted(),getKomplisertvenepunksjon(),getTappetype(),getTappevarighet(),getMaltidfortapping(),getLokalisasjonvenepunksjon(),getDonasjonsdato(),getGiveId(),getDonasjonsId()};
		
	}	

	/**
	 * setDonasjonsfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	/*public void setDonasjonsfieldMaps(String[]userFields){
		keys = userFields;
		for (int i = 0;i<keys.length ;i++){
			donasjonsFields.put(userFields[i],null);
		}
	}*/
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	/*public void saveField(String userField, String userValue) {
		if (donasjonsFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			donasjonsFields.put(userField,userValue);	
		}
		
	}*/

	public void saveToField(){
		if (getDonasjonsId() == null){
			setDonasjonssted(null);
			setKomplisertvenepunksjon(null);
			setTappetype(null);
			setTappevarighet(null);
			setMaltidfortapping(null);
			setLokalisasjonvenepunksjon(null);
			setDonasjonsdato(null);
		}
		if (getDonasjonsId() != null){
			String donasjonsted = new String(getDonasjonssted()); 
			String komplisert = new String(getKomplisertvenepunksjon());
			String tappetype = new String(getTappetype());
			String tappevarighet = new String (getTappevarighet());
			String maltid = new String (getMaltidfortapping());
			String lokalisasjon = new String(getLokalisasjonvenepunksjon());
			
			Calendar kalender = Calendar.getInstance();
			kalender.setTime(getDonasjonsdato());
			Date donDato = kalender.getTime();
			setDonasjonssted(null);
			setKomplisertvenepunksjon(null);
			setTappetype(null);
			setTappevarighet(null);
			setMaltidfortapping(null);
			setLokalisasjonvenepunksjon(null);
			setDonasjonsdato(null);
			if (getDonasjonssted() == null || getDonasjonssted().isEmpty())
				setDonasjonssted(donasjonsted);
			if (getKomplisertvenepunksjon() == null || getKomplisertvenepunksjon().isEmpty())
				setKomplisertvenepunksjon(komplisert);
			if (getTappetype() == null || getTappetype().isEmpty())
				setTappetype(tappetype);
			if (getTappevarighet() == null || getTappevarighet().isEmpty())
				setTappevarighet(tappevarighet);
			if (getMaltidfortapping() == null || getMaltidfortapping().isEmpty())
				setMaltidfortapping(maltid);
			if (getLokalisasjonvenepunksjon() == null || getLokalisasjonvenepunksjon().isEmpty())
				setLokalisasjonvenepunksjon(lokalisasjon);
			if (getDonasjonsdato() == null)
				setDonasjonsdato(donDato);
			
		}
	}
}
