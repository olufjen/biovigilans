package no.naks.biovigilans.service;

import no.naks.biovigilans.dao.AnnenKomplikasjonDAO;
import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.rammeverk.kildelag.service.BusinessService;

public class AnnenKomplikasjonTableServiceImpl extends BusinessService implements
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
