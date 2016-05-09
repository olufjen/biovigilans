package no.naks.biovigilans.service;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.SqlParameter;

import no.naks.biovigilans.dao.HendelsehemovigilansDAO;
import no.naks.biovigilans.dao.SakDAO;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;
import no.naks.rammeverk.kildelag.service.BusinessService;


/**
 * Denne klassen er en implementasjon av klassen HendelseTablesService og 
 * håndterer lagring/oppdatering av Vigilansmeldinger
 * Følgende tabeller blir berørt i db:
 * 		
 **/
public class HendelseTablesServiceImpl extends BusinessService implements HendelseTablesService {
	
	private HendelsehemovigilansDAO hendelsehemovigilansDAO;
	private SakDAO sakDAO;

	public SakDAO getSakDAO() {
		return sakDAO;
	}

	public void setSakDAO(SakDAO sakDAO) {
		this.sakDAO = sakDAO;
	}

	public HendelsehemovigilansDAO getHendelsehemovigilansDAO() {
		return hendelsehemovigilansDAO;
	}

	public void setHendelsehemovigilansDAO(
			HendelsehemovigilansDAO hendelsehemovigilansDAO) {
		this.hendelsehemovigilansDAO = hendelsehemovigilansDAO;
	}

	public void saveVigilansmelding(Vigilansmelding melding){
		hendelsehemovigilansDAO.updateVigilansMelding(melding);
	}

	public List<Vigilansmelding> collectMeldinger(Long melderId){
		return hendelsehemovigilansDAO.collectMeldinger(melderId);
		
	}
	public List<Vigilansmelding> collectMeldingen(Long meldeId){
		return hendelsehemovigilansDAO.collectMeldingen(meldeId);
		
	}
	
	public void updateVigilansmelding(Vigilansmelding melding){
		hendelsehemovigilansDAO.updateVigilansstatus(melding);
	}
	public Diskusjon collectDiskusjon(Long diskId){
		return sakDAO.collectDiskusjon(diskId);
	}
	public void setAlterativeSource(String key) {
		super.setAlterativeDBSource(key);
		if (getJdbctemplatesDAO() != null){
		sakDAO.setAlternativeSource(getJdbctemplatesDAO().getAlternativeSource());
		hendelsehemovigilansDAO.setAlternativeSource(getJdbctemplatesDAO().getAlternativeSource());
		}
		
	}
}