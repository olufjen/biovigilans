package no.naks.biovigilans.service;

import no.naks.biovigilans.dao.GiverDAO;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.Vigilansmelding;

public class GiverTableServiceImpl implements GiverTableService {

	private GiverDAO giverDAO;
		
		
	public GiverDAO getGiverDAO() {
		return giverDAO;
	}
	public void setGiverDAO(GiverDAO giverDAO) {
		this.giverDAO = giverDAO;
	}



	public void saveGiver(Giver giver) {
	
		giverDAO.saveGiver(giver);

	}

	public void saveVigilansmelding(Vigilansmelding vigilansmelding){
		
		giverDAO.saveVigilansmelding(vigilansmelding);
		
	}
	
	public void saveGiverKomplikasjon(Giverkomplikasjon giverKomplikasjon){
		giverDAO.saveGiverkomplikasjon(giverKomplikasjon);
	}
	
	public void saveGiveroppfolging(Giveroppfolging giveroppfolging){
		giverDAO.saveGiveroppfolging(giveroppfolging);
	}
}
