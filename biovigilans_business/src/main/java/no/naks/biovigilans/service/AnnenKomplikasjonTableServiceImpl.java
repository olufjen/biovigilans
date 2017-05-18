package no.naks.biovigilans.service;

import no.naks.biovigilans.dao.AnnenKomplikasjonDAO;
import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.rammeverk.kildelag.service.BusinessService;

/**
 * Denne singleton tjenesten benyttes til oppslag mot og lagring av meldinger  type andre hendelser
 * @author olj
 *
 */
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
	@Override
	public void setAlterativeSource(String key) {
		super.setAlterativeDBSource(key);
		if (getJdbctemplatesDAO() != null){
			annenKomplikasjonDAO.setAlternativeSource(getJdbctemplatesDAO().getAlternativeSource());
		}
		
	}
}
