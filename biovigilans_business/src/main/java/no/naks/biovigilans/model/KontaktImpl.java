package no.naks.biovigilans.model;

import java.sql.Types;

public class KontaktImpl extends AbstractKontakt implements Kontakt {

	public KontaktImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		utypes = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};

	}
	public void setParams(){
		Long id = getKontakt_Id();
	
		if (id == null){
			params = new Object[]{getNavn(),getTittel(),getEpost()};
		}else
			params = new Object[]{getNavn(),getTittel(),getEpost(),getKontakt_Id()};

	}
	
}
