package no.naks.biovigilans.felles.control;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.model.Vigilansmelding;

public interface MelderWebService {

	public void saveMelder(MelderwebModel melderwebModel);
	public List selectMelder(String epost);
	public Map<String,List> selectMeldinger(String meldingsNokkel);
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger);
	public void setAlterativeSource(String key);
}
