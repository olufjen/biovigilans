package no.naks.biovigilans.felles.control;

import no.naks.biovigilans.felles.model.AnnenKomplikasjonwebModel;

public interface AnnenKomplikasjonWebService {

	public void saveAnnenKomplikasjon(AnnenKomplikasjonwebModel annenKomplikasjonWebModel);
	public void setAlterativeSource(String key);
}
