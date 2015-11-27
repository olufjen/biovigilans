package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class GiverImpl extends AbstractGiver implements Giver {

	public GiverImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		giverFields = new HashMap<String,String>();
	}

	public void setParams(){
		Long id = getGiverid();
		
		if (id == null){
			params = new Object[]{getKjonn(),getAlder(),getGivererfaring(),getTidligerekomlikasjonjanei(),getTidligerekomplikasjonforklaring(),getGivererfaringaferese(),getVekt()};
			
		}else
			params = new Object[]{getKjonn(),getAlder(),getGivererfaring(),getTidligerekomlikasjonjanei(),getTidligerekomplikasjonforklaring(),getGivererfaringaferese(),getVekt(),getGiverid()};
		
	}

	/**
	 * setGiverfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	/*public void setGiverfieldMaps(String[]userFields){
		keys = userFields;
		//int size =userFields.length;
		for (int i = 0;i<7;i++){
			giverFields.put(userFields[i],null);
		}

		
	}*/
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	/*public void saveField(String userField, String userValue) {
		if (giverFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			giverFields.put(userField,userValue);	
	
		}
		
	}*/
	
	public void saveToGiver(){
		if (getGiverid() == null){
			setKjonn(null);
			setAlder(null);
			setVekt(null);
			setGivererfaring(null);
			setTidligerekomlikasjonjanei(null);
			setTidligerekomplikasjonforklaring(null);
			setGivererfaringaferese(null);
		}
		if (getGiverid() != null){
			String tkjonn = new String(getKjonn());
			String tAlder = new String(getAlder());
			String tgivererfaring = new String(getGivererfaring());
			String tidligerekompjanei = new String(getTidligerekomlikasjonjanei());
			String tidligerekompforkl = new String(getTidligerekomplikasjonforklaring());
			String givererfaring = new String(getGivererfaringaferese());
			Long tvekt = new Long(getVekt());
			setKjonn(null);
			setAlder(null);
			setVekt(null);
			setGivererfaring(null);
			setTidligerekomlikasjonjanei(null);
			setTidligerekomplikasjonforklaring(null);
			setGivererfaringaferese(null);
			if (getKjonn() == null || getKjonn().isEmpty())
				setKjonn(tkjonn);
			if (getAlder() == null || getAlder().isEmpty())
				setAlder(tAlder);
			if (getGivererfaring() == null || getGivererfaring().isEmpty())
				setGivererfaring(tgivererfaring);
			if (getTidligerekomlikasjonjanei() == null || getTidligerekomlikasjonjanei().isEmpty())
				setTidligerekomlikasjonjanei(tidligerekompjanei);
			if (getTidligerekomplikasjonforklaring() == null || getTidligerekomplikasjonforklaring().isEmpty())
				setTidligerekomplikasjonforklaring(tidligerekompforkl);
			if (getGivererfaringaferese() == null || getGivererfaringaferese().isEmpty())
				setGivererfaringaferese(givererfaring);
			if (getVekt() == null || getVekt().longValue() == 0 )
				setVekt(tvekt);
		}
		
	}
	
}
