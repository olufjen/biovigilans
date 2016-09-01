package no.naks.biovigilans.felles.control;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.service.AnnenKomplikasjonTableService;
import no.naks.biovigilans.felles.model.AnnenKomplikasjonwebModel;

/**
 * Denne singleton tjenesten benyttes til å lagre andre typer hendelser ved bruk av forretningslaget
 * @author olj
 *
 */
public class AnnenKomplikasjonWebServiceImpl implements
		AnnenKomplikasjonWebService {
	
	/**
	 * Forretningslaget singleton tjeneste
	 */
	AnnenKomplikasjonTableService annenKomplikasjonTableService;

	public AnnenKomplikasjonTableService getAnnenKomplikasjonTableService() {
		return annenKomplikasjonTableService;
	}

	public void setAnnenKomplikasjonTableService(
			AnnenKomplikasjonTableService annenKomplikasjonTableService) {
		this.annenKomplikasjonTableService = annenKomplikasjonTableService;
	}
	
	
	public void saveAnnenKomplikasjon(AnnenKomplikasjonwebModel annenKomplikasjonWebModel){
		annenKomplikasjonTableService.saveAnnenKomplikasjon(annenKomplikasjonWebModel.getAnnenKomplikasjon());
	}
	public void setAlterativeSource(String key){
		annenKomplikasjonTableService.setAlterativeSource(key);
	}
}
