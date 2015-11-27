package no.naks.biovigilans.service;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.dao.SakDAO;
import no.naks.biovigilans.dao.SaksbehandlingDAO;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Vigilansmelding;

public interface SaksbehandlingService {
	
	public SaksbehandlingDAO getSaksbehandlingDAO();


	public void setSaksbehandlingDAO(SaksbehandlingDAO saksbehandlingDAO);
	public void setTimeperiodType(boolean timeperiodType);
	public List collectMessages();
	public List collectMessages(String types);
	public List collectPasientMeldinger(List<Vigilansmelding>meldinger);
	public List collectGiverMeldinger(List<Vigilansmelding>meldinger);
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger);
	public Map<String,List> selectMeldinger(String meldingsNokkel);
	public List collectMessages(String start,String end);	
	public SakDAO getSakDAO();
	public void setSakDAO(SakDAO sakDAO);
	public void saveDiskusjon(Map<String,Diskusjon> diskusjonsMappe);
	public void saveDiskusjon(Diskusjon diskusjon);
	public void saveSak(Map<String,Sak> saksMappe);
	public Map<String,List> collectDiskusjoner(Long meldeId);
	public List collectSaksbehandlere();
	public Melder collectmelder(Long melderId);
	public List collectMessagesMarks(String merknad);
}
