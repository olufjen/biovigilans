package no.naks.biovigilans.service;

import no.naks.biovigilans.dao.KomplikasjonsdiagnosegiverDAO;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.rammeverk.kildelag.service.BusinessService;

/**
 * Denne singleton tjenesten benyttes til oppslag mot og lagring av diagnoser for giver ved en giverhendelse
 * @author olj
 *
 */
public class KomDiagnosegiverTableServiceImpl extends BusinessService implements
		KomDiagnosegiverTableService {

	private KomplikasjonsdiagnosegiverDAO komplikasjonsdiagnosegiverDAO;

	
	public KomplikasjonsdiagnosegiverDAO getKomplikasjonsdiagnosegiverDAO() {
		return komplikasjonsdiagnosegiverDAO;
	}
	public void setKomplikasjonsdiagnosegiverDAO(
			KomplikasjonsdiagnosegiverDAO komplikasjonsdiagnosegiverDAO) {
		this.komplikasjonsdiagnosegiverDAO = komplikasjonsdiagnosegiverDAO;
	}



	public void saveKomDiagnosgiver(Komplikasjonsdiagnosegiver komDiagnosegiver){
		komplikasjonsdiagnosegiverDAO.saveKompDiagnosgiverDAO(komDiagnosegiver);
	}
	@Override
	public void setAlterativeSource(String key) {
		super.setAlterativeDBSource(key);
		if (getJdbctemplatesDAO() != null){
		komplikasjonsdiagnosegiverDAO.setAlternativeSource(getJdbctemplatesDAO().getAlternativeSource());
		}
		
	}
}
