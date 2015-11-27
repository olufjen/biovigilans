package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.HashMap;

public class ProduktegenskapImpl extends AbstractProduktegenskap implements Produktegenskap {
	private String egenskapType = ""; // Er av typen erytrocytt, trombocytt eller plasma
	private String[] egenskapTyper;   // Plasma kan være en av flere
	public ProduktegenskapImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		produktegenskapFields = new HashMap();
	
		
	}
	public ProduktegenskapImpl(String egenskaptype) {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		produktegenskapFields = new HashMap();
		this.egenskapType = egenskaptype;
		
	}
	public ProduktegenskapImpl(String[] egenskaptyper) {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		produktegenskapFields = new HashMap();
		this.egenskapTyper = egenskaptyper;
		
	}		
	public void setParams(){
		Long id = getProduktegenskapId();
		if (id == null){
			params = new Object[]{getEgenskapKode(),getEgenskapBeskrivelse(),getBlodProduktId()};
		}else
			params = new Object[]{getEgenskapKode(),getEgenskapBeskrivelse(),getProduktegenskapId(),getBlodProduktId()};
		
	}
	public void setEgenskaperfieldMaps(String[]egenskapFields){
		keys = egenskapFields;
		int z = egenskapFields.length;
		
		for (int i = 0; i<z;i++){
			produktegenskapFields.put(egenskapFields[i],null);
					
		}
	}
	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	
	public void saveField(String userField, String userValue) {
		if (produktegenskapFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			produktegenskapFields.put(userField,userValue);	

		}	
		
	}
	public void distributeValues(String egenskap){
		setEgenskapBeskrivelse(egenskap);
		setEgenskapKode(egenskap);


	}
	
	public String[] getEgenskapTyper() {
		return egenskapTyper;
	}
	public void setEgenskapTyper(String[] egenskapTyper) {
		this.egenskapTyper = egenskapTyper;
	}
	public String getEgenskapType() {
		return egenskapType;
	}
	public void setEgenskapType(String egenskapType) {
		this.egenskapType = egenskapType;
	}
	
}
