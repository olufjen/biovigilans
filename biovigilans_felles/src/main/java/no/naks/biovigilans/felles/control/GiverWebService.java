package no.naks.biovigilans.felles.control;

import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.framework.web.control.MasterWebService;

public interface GiverWebService extends MasterWebService {

		public void saveGiver(GiverKomplikasjonwebModel giverModel);
		public void saveVigilansmelding(GiverKomplikasjonwebModel giverModel);
		public void saveGiverkomplikasjon(GiverKomplikasjonwebModel giverModel);
		public void saveGiveroppfolging(GiverKomplikasjonwebModel giverModel) ;
}
