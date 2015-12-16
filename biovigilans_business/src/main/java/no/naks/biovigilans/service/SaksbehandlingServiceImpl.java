package no.naks.biovigilans.service;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.dao.SakDAO;
import no.naks.biovigilans.dao.SaksbehandlingDAO;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Regionstatistikk;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Vigilansmelding;

public class SaksbehandlingServiceImpl implements SaksbehandlingService {
	private SaksbehandlingDAO saksbehandlingDAO;
	private SakDAO sakDAO;
	
	
	public SakDAO getSakDAO() {
		return sakDAO;
	}

	public void setSakDAO(SakDAO sakDAO) {
		this.sakDAO = sakDAO;
	}

	public SaksbehandlingDAO getSaksbehandlingDAO() {
		return saksbehandlingDAO;
	}


	public void setSaksbehandlingDAO(SaksbehandlingDAO saksbehandlingDAO) {
		this.saksbehandlingDAO = saksbehandlingDAO;
	}

	public void setTimeperiodType(boolean timeperiodType) {
		saksbehandlingDAO.setTimeperiodType(timeperiodType);
	}
	public List collectMessages() {
		
		return saksbehandlingDAO.collectMessages();
	}
	public List collectMessages(String types){
		return saksbehandlingDAO.collectMessagesbytypes(types);
	}
	public List collectMessages(String start,String end){
		return saksbehandlingDAO.collectMessages(start, end);
	}	
	public List collectPasientMeldinger(List<Vigilansmelding>meldinger){
		
		return null;
	}
	public List collectGiverMeldinger(List<Vigilansmelding>meldinger){
		
		return null;
	}
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger){
		Map andreMeldinger = saksbehandlingDAO.collectAnnenMeldinger(meldinger);
		return andreMeldinger;
	}
	public Map<String,List> selectMeldinger(String meldingsNokkel){
		return saksbehandlingDAO.selectMeldinger(meldingsNokkel);
				
	}
	public void saveDiskusjon(Map<String,Diskusjon> diskusjonsMappe){
		sakDAO.saveDiskusjon(diskusjonsMappe);
	}
	public void saveDiskusjon(Diskusjon diskusjon){
		sakDAO.saveDiskusjon(diskusjon);
	}
	public void saveSak(Map<String,Sak> saksMappe){
		sakDAO.saveSak(saksMappe);
	}
	public Map<String,List> collectDiskusjoner(Long meldeId){
		return sakDAO.collectDiskusjoner(meldeId);
	}
	public List collectSaksbehandlere(){
		return sakDAO.collectSaksbehandlere();
	}
	public Melder collectmelder(Long melderId){
		return sakDAO.collectMelder(melderId);
	}
	public List collectMessagesMarks(String merknad){
		return saksbehandlingDAO.collectMessagesMarks(merknad);
	}
	public List<Regionstatistikk> collectRegionstatistikk(){
		return sakDAO.collectRegionstatistikk();
	}
}
