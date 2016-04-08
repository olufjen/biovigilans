package no.naks.biovigilans.service;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.dao.SakDAO;
import no.naks.biovigilans.dao.SaksbehandlingDAO;
import no.naks.biovigilans.dao.StatistikkDAO;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Regionstatistikk;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.service.BusinessService;

public class SaksbehandlingServiceImpl extends BusinessService implements SaksbehandlingService {
	private SaksbehandlingDAO saksbehandlingDAO;
	private SakDAO sakDAO;
	private StatistikkDAO statistikkDAO;

	
	public StatistikkDAO getStatistikkDAO() {
		return statistikkDAO;
	}

	public void setStatistikkDAO(StatistikkDAO statistikkDAO) {
		this.statistikkDAO = statistikkDAO;
	}

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
	public Map<String,List> selectMeldingetternokkel (String meldeid){
		return saksbehandlingDAO.selectMeldingetternokkel(meldeid);
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
	public List collectMessagesSaksbehandler(Long saksbehandlerid){
		return saksbehandlingDAO.collectMessagessaksbehandler(saksbehandlerid);
	}
	public List<Regionstatistikk> collectRegionstatistikk(){
		return sakDAO.collectRegionstatistikk();
	}
	public List<Regionstatistikk> collectForetakstatistikk(String reg){
		return statistikkDAO.collectRegionstatistikk(reg);
	}
	public List<Regionstatistikk> collectRegionstatistikk(String startperiod,String endperiod,String type){
		return sakDAO.collectRegionstatistikk(startperiod, endperiod, type);
	}
	public List<Regionstatistikk> collectForetakstatistikk(String startperiod,String endperiod,String type){
		return statistikkDAO.collectRegionstatistikk(startperiod, endperiod, type);
	}
	public List<Regionstatistikk> collectsykehusstatistikk(String startperiod,String endperiod,String type){
		return statistikkDAO.collectsykehusstatistikk(startperiod, endperiod, type);
	}
	public List collectMessagesanonyme(){
		return saksbehandlingDAO.collectMessagesanonyme();
	}
}
