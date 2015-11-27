package no.naks.biovigilans.model;

import java.sql.Types;

public class PasientGruppeImpl extends AbstractPasientgruppe implements
		Pasientgruppe {

	public PasientGruppeImpl() {
		super();
		
		types = new int[] {Types.VARCHAR,Types.VARCHAR};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};

	}

	public void setParams(){
		Long id = getPasientgruppe_Id();
	
		if (id == null){
			params = new Object[]{getMeshindeks(),getPasientgruppe()};
		}else
			params = new Object[]{getMeshindeks(),getPasientgruppe(),getPasientgruppe_Id()};

	}	

}
