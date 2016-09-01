package no.naks.biovigilans.felles.control;

import no.naks.biovigilans.service.KomDiagnosegiverTableService;
import no.naks.biovigilans.felles.model.KomDiagnosegiverwebModel;

/**
 * Denne singleton tjenesten benyttes til å lagre diagnoser for giver ved bruk av forretningslaget tjenester
 * @author olj
 *
 */
public class KomDiagnosegiverWebServiceImpl implements
		KomDiagnosegiverWebService {
	
	/**
	 * Forretningslagets tjeneste for lagring
	 */
	private KomDiagnosegiverTableService komDiagnosegiverTableService;

	public KomDiagnosegiverTableService getKomDiagnosegiverTableService() {
		return komDiagnosegiverTableService;
	}

	public void setKomDiagnosegiverTableService(
			KomDiagnosegiverTableService komDiagnosegiverTableService) {
		this.komDiagnosegiverTableService = komDiagnosegiverTableService;
	}
	
	public void saveKomDiagnosegiver(KomDiagnosegiverwebModel komDiagnosegiverwebModel){
		komDiagnosegiverTableService.saveKomDiagnosgiver(komDiagnosegiverwebModel.getKomDiagnosegiver());
	}
	public void setAlterativeSource(String key){
		komDiagnosegiverTableService.setAlterativeSource(key);
	}
}
