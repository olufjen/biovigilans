package no.naks.biovigilans.felles.control;

import java.util.List;

import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.service.KomplikasjonsklassifikasjonTableService;

/**
 * Denne singleton tjenesten benyttes til å lagre klassifikasjon av komplikasjonsdiagnoser for pasient
 * ved bruk av forretninglagets tjenester
 * @author olj
 *
 */
public class KomplikasjonsklassifikasjonWebServiceImpl implements KomplikasjonsklassifikasjonWebService {

	/**
	 * Forretningslagets lagringstjeneste
	 */
	private KomplikasjonsklassifikasjonTableService komplikasjonsklassifikasjonTableService;

	public KomplikasjonsklassifikasjonTableService getKomplikasjonsklassifikasjonTableService() {
		return komplikasjonsklassifikasjonTableService;
	}

	public void setKomplikasjonsklassifikasjonTableService(
			KomplikasjonsklassifikasjonTableService komplikasjonsklassifikasjonTableService) {
		this.komplikasjonsklassifikasjonTableService = komplikasjonsklassifikasjonTableService;
	}
	
	public void saveKomplikasjonsklassifikasjon(Komplikasjonsklassifikasjon komplikasjonsklassifikasjon){
		komplikasjonsklassifikasjonTableService.saveKomplikasjonsklassifikasjon(komplikasjonsklassifikasjon);
	}
	public List<Komplikasjonsklassifikasjon> hentKomplikasjonsklassifikasjonene(){
		return komplikasjonsklassifikasjonTableService.hentKlassifikasjoner();
	}
	public void setAlterativeSource(String key){
		komplikasjonsklassifikasjonTableService.setAlterativeSource(key);
	}
}
