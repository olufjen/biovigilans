package no.naks.biovigilans.felles.control;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Regionstatistikk;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.service.MelderTableService;
import no.naks.biovigilans.service.SaksbehandlingService;

public interface SaksbehandlingWebService {

	public List collectMessages();
	public SaksbehandlingService getSaksbehandlingService();
	public void setSaksbehandlingService(SaksbehandlingService saksbehandlingService);	
	public Map<String,List> selectMeldinger(String meldingsNokkel);
	public MelderTableService getMelderTableService();
	public void setMelderTableService(MelderTableService melderTableService);
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger);
	public List collectMessages(String types);
	public List collectMessages(String start,String end);	
	public void setTimeperiodType(boolean timeperiodType);
	public void saveDiskusjon(Map<String,Diskusjon> diskusjonsMappe);
	public void saveDiskusjon(Diskusjon diskusjon);
	public void saveSak(Map<String,Sak> saksMappe);
	public Map<String,List> collectDiskusjoner(Long meldeId);
	public List collectSaksbehandlere();
	public Melder collectmelder(Long melderId);
	public List collectMessagesMarks(String merknader);
	public List<Regionstatistikk> collectRegionstatistikk();
	public List<Regionstatistikk> collectForetakstatistikk(String reg);
	public List<Regionstatistikk> collectRegionstatistikk(String startperiod,String endperiod,String type);
	public List<Regionstatistikk> collectForetakstatistikk(String startperiod,String endperiod,String type);
	public List<Regionstatistikk> collectsykehusstatistikk(String startperiod,String endperiod,String type);
}
