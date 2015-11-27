package no.naks.biovigilans.service;

import java.util.List;

import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;

public interface KomplikasjonsklassifikasjonTableService {

	public void saveKomplikasjonsklassifikasjon(Komplikasjonsklassifikasjon komplikasjonsklassifikasjon);
	public List<Komplikasjonsklassifikasjon> hentKlassifikasjoner();
}
