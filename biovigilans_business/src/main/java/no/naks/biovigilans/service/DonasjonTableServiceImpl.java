package no.naks.biovigilans.service;

import no.naks.biovigilans.dao.DonasjonDAO;
import no.naks.biovigilans.model.Donasjon;
import no.naks.rammeverk.kildelag.service.BusinessService;

public class DonasjonTableServiceImpl extends BusinessService implements DonasjonTableService {

	private DonasjonDAO  donasjonDAO;
	
	
	
	public DonasjonDAO getDonasjonDAO() {
		return donasjonDAO;
	}



	public void setDonasjonDAO(DonasjonDAO donasjonDAO) {
		this.donasjonDAO = donasjonDAO;
	}



	
	public void saveDonasjon(Donasjon donasjon) {
		donasjonDAO.saveDonasjonDAO(donasjon);
	}

	@Override
	public void setAlterativeSource(String key) {
		super.setAlterativeDBSource(key);
		if (getJdbctemplatesDAO() != null){
			donasjonDAO.setAlternativeSource(getJdbctemplatesDAO().getAlternativeSource());
		}
		
	}


}
