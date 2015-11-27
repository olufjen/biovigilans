package no.naks.biovigilans.service;

import no.naks.biovigilans.dao.DonasjonDAO;
import no.naks.biovigilans.model.Donasjon;

public class DonasjonTableServiceImpl implements DonasjonTableService {

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




}
