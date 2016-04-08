package no.naks.biovigilans.service;

import no.naks.biovigilans.dao.KomplikasjonsdiagnosegiverDAO;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.rammeverk.kildelag.service.BusinessService;

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
}
