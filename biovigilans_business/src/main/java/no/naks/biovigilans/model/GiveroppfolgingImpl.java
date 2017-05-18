package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;

/**
 * Implementasjonen av AbstractGiveroppfolging
 * @author olj
 *
 */
public class GiveroppfolgingImpl extends AbstractGiveroppfolging implements Giveroppfolging {

	public GiveroppfolgingImpl(){
		super();
		types= new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.INTEGER};
		utypes= new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.INTEGER, Types.INTEGER};
		giveroppfolgingFields = new HashMap<String,String>();
	}
	
	public void setParams() {
	
		Long id = getGiveroppfolgingId();
		if(id == null){
			params = new Object[]{getKlassifikasjongiveroppfolging(), getGiveroppfolgingbeskrivelse(), getAvregistering(),getStrakstiltak(),getVidereoppfolging(),getMeldeid() };
		}else{
			params = new Object[]{getKlassifikasjongiveroppfolging(), getGiveroppfolgingbeskrivelse(), getAvregistering(),getStrakstiltak(),getVidereoppfolging(),getMeldeid(), getGiveroppfolgingId() };

		}
	}
	
	/**
	 * setGiveroppfolgingfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	
/*	public void setGiveroppfolgingfieldMaps(String[]userFields){
		keys = userFields;
		int size = keys.length;
		for(int i=0; i<size;i++){
			giveroppfolgingFields.put(userFields[i],null);
		}
	}*/
	
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	/*public void saveField(String userField, String userValue) {
		if (giveroppfolgingFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			giveroppfolgingFields.put(userField,userValue);	
	
		}
		
	}*/
	
	public void saveToField(){
		
		if (getGiveroppfolgingId() == null){
			setKlassifikasjongiveroppfolging(null);
			setGiveroppfolgingbeskrivelse(null);
			setAvregistering(null);
			setStrakstiltak(null);
			setVidereoppfolging(null);
		}
		if (getGiveroppfolgingId() != null){
			String klasse = new String(getKlassifikasjongiveroppfolging());
			String beskrivelse = new String(getGiveroppfolgingbeskrivelse());
			String avregistrering = new String(getAvregistering());
			String strakstiltak = new String(getStrakstiltak());
			String videre = new String(getVidereoppfolging());
			setKlassifikasjongiveroppfolging(null);
			setGiveroppfolgingbeskrivelse(null);
			setAvregistering(null);
			setStrakstiltak(null);
			setVidereoppfolging(null);
			if (getKlassifikasjongiveroppfolging() == null || getKlassifikasjongiveroppfolging().isEmpty())
					setKlassifikasjongiveroppfolging(klasse);
			if (getGiveroppfolgingbeskrivelse() == null || getGiveroppfolgingbeskrivelse().isEmpty())
				setGiveroppfolgingbeskrivelse(beskrivelse);
			if (getAvregistering() == null || getAvregistering().isEmpty())
				setAvregistering(avregistrering);
			if ( getStrakstiltak() == null || getStrakstiltak().isEmpty())
				setStrakstiltak(strakstiltak);
			if (getVidereoppfolging() == null || getVidereoppfolging().isEmpty())
				setVidereoppfolging(videre);
			
			
			
		}
	}

}
