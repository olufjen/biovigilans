package no.naks.biovigilans.service;

import no.naks.biovigilans.model.Annenkomplikasjon;


public interface AnnenKomplikasjonTableService {
	public void saveAnnenKomplikasjon(Annenkomplikasjon annenKomplikasjon);
	public void setAlterativeSource(String key);
	
}
