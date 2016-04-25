package no.naks.biovigilans.felles.control;

import java.util.List;

import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;

public interface KomplikasjonsklassifikasjonWebService {

	public void saveKomplikasjonsklassifikasjon(Komplikasjonsklassifikasjon komplikasjonsklassifikasjon);
	public List<Komplikasjonsklassifikasjon> hentKomplikasjonsklassifikasjonene();
	public void setAlterativeSource(String key);		
}
