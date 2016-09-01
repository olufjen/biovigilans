package no.naks.biovigilans.felles.control;

import no.naks.biovigilans.service.DonasjonTableService;
import no.naks.biovigilans.felles.model.DonasjonwebModel;

/**
 * Denne singleton tjenesten benyttes til å lagre informasjon om donasjon ved bruk av forretningslaget
 * @author olj
 *
 */
public class DonasjonWebServiceImpl implements DonasjonWebService {

	/**
	 * Forretningslaget singleton tjeneste
	 */
	private DonasjonTableService donasjonTableService;
	
	
	public DonasjonWebServiceImpl() {
		super();
	}


	public DonasjonTableService getDonasjonTableService() {
		return donasjonTableService;
	}


	public void setDonasjonTableService(DonasjonTableService donasjonTableService) {
		this.donasjonTableService = donasjonTableService;
	}


	public void saveDonasjon(DonasjonwebModel donasjonwebModel) {
	
		donasjonTableService.saveDonasjon(donasjonwebModel.getDonasjon());
	}
	/* (non-Javadoc)
	 * @see no.naks.biovigilans.felles.control.DonasjonWebService#setAlterativeSource(java.lang.String)
	 */
	public void setAlterativeSource(String key){
		donasjonTableService.setAlterativeSource(key);
	}
}
