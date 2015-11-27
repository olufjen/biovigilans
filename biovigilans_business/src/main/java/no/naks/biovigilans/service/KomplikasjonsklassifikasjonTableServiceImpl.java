package no.naks.biovigilans.service;

import java.util.List;

import no.naks.biovigilans.dao.KomplikasjonsklassifikasjonDAO;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;

public class KomplikasjonsklassifikasjonTableServiceImpl implements
		KomplikasjonsklassifikasjonTableService {

	KomplikasjonsklassifikasjonDAO komplikasjonsklassifikasjonDAO ;

	public KomplikasjonsklassifikasjonDAO getKomplikasjonsklassifikasjonDAO() {
		return komplikasjonsklassifikasjonDAO;
	}

	public void setKomplikasjonsklassifikasjonDAO(
			KomplikasjonsklassifikasjonDAO komplikasjonsklassifikasjonDAO) {
		this.komplikasjonsklassifikasjonDAO = komplikasjonsklassifikasjonDAO;
	}
	
	public void saveKomplikasjonsklassifikasjon(Komplikasjonsklassifikasjon komplikasjonsklassifikasjon){
		komplikasjonsklassifikasjonDAO.saveKomplikasjonsklassifikasjon(komplikasjonsklassifikasjon);
	}
	public List<Komplikasjonsklassifikasjon> hentKlassifikasjoner(){
		return komplikasjonsklassifikasjonDAO.getKomplikasjonsklassifikasjoner();
	}
}
