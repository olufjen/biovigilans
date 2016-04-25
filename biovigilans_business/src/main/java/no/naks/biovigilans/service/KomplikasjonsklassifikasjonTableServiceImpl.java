package no.naks.biovigilans.service;

import java.util.List;

import no.naks.biovigilans.dao.KomplikasjonsklassifikasjonDAO;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.rammeverk.kildelag.service.BusinessService;

public class KomplikasjonsklassifikasjonTableServiceImpl extends BusinessService implements
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
	@Override
	public void setAlterativeSource(String key) {
		super.setAlterativeDBSource(key);
		if (getJdbctemplatesDAO() != null){
			komplikasjonsklassifikasjonDAO.setAlternativeSource(getJdbctemplatesDAO().getAlternativeSource());
		}
		
	}
}
