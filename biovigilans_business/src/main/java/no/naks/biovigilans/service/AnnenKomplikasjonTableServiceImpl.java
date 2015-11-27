package no.naks.biovigilans.service;

import no.naks.biovigilans.dao.AnnenKomplikasjonDAO;
import no.naks.biovigilans.model.Annenkomplikasjon;

public class AnnenKomplikasjonTableServiceImpl implements
		AnnenKomplikasjonTableService {

	AnnenKomplikasjonDAO annenKomplikasjonDAO;
	
	
	
	public AnnenKomplikasjonDAO getAnnenKomplikasjonDAO() {
		return annenKomplikasjonDAO;
	}



	public void setAnnenKomplikasjonDAO(AnnenKomplikasjonDAO annenKomplikasjonDAO) {
		this.annenKomplikasjonDAO = annenKomplikasjonDAO;
	}



	public void saveAnnenKomplikasjon(Annenkomplikasjon annenKomplikasjon){
		annenKomplikasjonDAO.saveAnnenKomplikasjon(annenKomplikasjon);
	}
}
