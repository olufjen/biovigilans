package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Forebyggende tiltak mot pasient
 * @author olj
 *
 */
public class ForebyggendetiltakImpl extends AbstractTiltak implements Tiltak,Forebyggendetiltak {
	
	private String tiltakvalg;
	private String tiltakbeskrivelse;
	private Long forebyggendetiltakid;

	
	public ForebyggendetiltakImpl() {
		super();
		forebyggendeTiltakFields = new HashMap();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		
	}
	public String getTiltakvalg() {
		return tiltakvalg;
	}
	public void setTiltakvalg(String tiltakvalg) {
		this.tiltakvalg = tiltakvalg;
	}
	public String getTiltakbeskrivelse() {
		return tiltakbeskrivelse;
	}
	public void setTiltakbeskrivelse(String tiltakbeskrivelse) {
		this.tiltakbeskrivelse = tiltakbeskrivelse;
	}
	public Long getForebyggendetiltakid() {
		return forebyggendetiltakid;
	}
	public void setForebyggendetiltakid(Long forebyggendetiltakid) {
		this.forebyggendetiltakid = forebyggendetiltakid;
	}
	public Map<String, String> getForebyggendeTiltakFields() {
		return forebyggendeTiltakFields;
	}
	public void setForebyggendeTiltakFields(
			Map<String, String> forebyggendeTiltakFields) {
		this.forebyggendeTiltakFields = forebyggendeTiltakFields;
	}
	

	@Override
	public void setParams() {
		Long id = getForebyggendetiltakid();
		if (id == null){
			params = new Object[]{getTiltakvalg(),getTiltakbeskrivelse(),getTiltakid()};
		}else
			params = new Object[]{getTiltakvalg(),getTiltakbeskrivelse(),getTiltakid(),getForebyggendetiltakid()};
	
		
	}
	
	/**
	 * setforebyggendefieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	public void setforebyggendefieldMaps(String[]userFields){

		keys = userFields;
		
		for (int i = 0; i<8;i++){
			forebyggendeTiltakFields.put(userFields[i],null);
			
		}
	
		


	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	
	public void saveField(String userField, String userValue) {
		if (forebyggendeTiltakFields.containsKey(userField) && userValue != null && !userValue.equals("") ){
			forebyggendeTiltakFields.put(userField,userValue);	
			System.out.println("Forebyggende field "+ userField+" value "+userValue);
		}	
	}
	

}
