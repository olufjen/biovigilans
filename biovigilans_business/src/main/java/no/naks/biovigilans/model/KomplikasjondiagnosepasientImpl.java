package no.naks.biovigilans.model;

import java.sql.Types;

public class KomplikasjondiagnosepasientImpl extends
		AbstractKomplikasjondiagnosepasient implements
		Komplikasjondiagnosepasient {

	public KomplikasjondiagnosepasientImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
	}

	public void setParams(){
		Long id = getKomplikasjonsdiagnoseId() ;
		if (id == null){
			params = new Object[]{};
		}else
			params = new Object[]{getKomplikasjonsdiagnoseId()};
		
	}	
	
}
