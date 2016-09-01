package no.naks.biovigilans.felles.control;

import no.naks.biovigilans.service.GiverTableService;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.framework.web.control.MasterWebServiceImpl;

/**
 * Denne singleton tjenesten benyttes til å lagre givermeldinger og giveropplysninger ved bruk av forretningslagets tjenester
 * @author olj
 *
 */
public class GiverWebServiceImpl extends MasterWebServiceImpl implements GiverWebService {

	/**
	 * Forretningslaget singleton tjeneste
	 */
	private GiverTableService giverTableService;
	
	public GiverWebServiceImpl(){
		super();
	}
	
	public GiverTableService getGiverTableService() {
		return giverTableService;
	}

	public void setGiverTableService(GiverTableService giverTableService) {
		this.giverTableService = giverTableService;
	}

	public void saveGiver(GiverKomplikasjonwebModel giverModel) {
		giverTableService.saveGiver(giverModel.getGiver());
		
	}
	public void saveVigilansmelding(GiverKomplikasjonwebModel giverModel) {
		giverTableService.saveVigilansmelding(giverModel.getVigilansmelding());
		
	}
	public void saveGiverkomplikasjon(GiverKomplikasjonwebModel giverModel){
		giverTableService.saveGiverKomplikasjon(giverModel.getGiverKomplikasjon());
	}
	
	public void saveGiveroppfolging(GiverKomplikasjonwebModel giverModel){
		giverTableService.saveGiveroppfolging(giverModel.getGiveroppfolging()); 
	}
}
