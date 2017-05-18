package no.naks.biovigilans.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import no.naks.biovigilans.dao.MelderDAO;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.service.BusinessService;

/**
 * Denne singleton tjenesten benyttes til oppslag mot og lagring av melders opplysninger
 * @author olj
 *
 */
public class MelderTableServiceImpl extends BusinessService implements MelderTableService {

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
	public List<Melder> collectMeldere(){
		return melderDAO.hentMeldere();
	}
	public List<Vigilansmelding> collectMeldersmeldinger(String melderIds){
		return melderDAO.meldersMeldinger(melderIds);
	}

	@Override
	public void setAlternativeSource(String key) {
		super.setAlterativeDBSource(key);
		if (getJdbctemplatesDAO() != null){
			melderDAO.setAlternativeSource(getJdbctemplatesDAO().getAlternativeSource());
		}

		
	}
}
