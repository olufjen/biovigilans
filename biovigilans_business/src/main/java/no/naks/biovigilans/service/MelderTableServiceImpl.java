package no.naks.biovigilans.service;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.dao.MelderDAO;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Vigilansmelding;

public class MelderTableServiceImpl implements MelderTableService {

	private MelderDAO melderDAO;
	
	
	public MelderDAO getMelderDAO() {
		return melderDAO;
	}
	public void setMelderDAO(MelderDAO melderDAO) {
		this.melderDAO = melderDAO;
	}



	public void saveMelder(Melder melder) {
		
		melderDAO.saveMelder(melder);

	}
	
	public List selectMelder(String epost){
		return melderDAO.selectMelder(epost);
	}
	public Map<String,List> selectMeldinger(String meldingsNokkel){
		return melderDAO.selectMeldinger(meldingsNokkel);
	}
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger){
		return melderDAO.collectAnnenMeldinger(meldinger);
	}
}
