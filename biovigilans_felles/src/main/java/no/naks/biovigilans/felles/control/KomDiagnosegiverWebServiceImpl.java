package no.naks.biovigilans.felles.control;

import no.naks.biovigilans.service.KomDiagnosegiverTableService;
import no.naks.biovigilans.felles.model.KomDiagnosegiverwebModel;

public class KomDiagnosegiverWebServiceImpl implements
		KomDiagnosegiverWebService {
	
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

}
