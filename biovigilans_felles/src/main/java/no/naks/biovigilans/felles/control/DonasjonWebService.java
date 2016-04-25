package no.naks.biovigilans.felles.control;

import no.naks.biovigilans.felles.model.DonasjonwebModel;

public interface DonasjonWebService {

	public void saveDonasjon(DonasjonwebModel donasjonwebModel);
	public void setAlterativeSource(String key);
}
