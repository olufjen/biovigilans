package no.naks.biovigilans.felles.control;

import java.util.List;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Utredning;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.service.PasientTableService;
import no.naks.biovigilans.felles.model.PasientKomplikasjonWebModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.framework.web.control.MasterWebService;

public interface HendelseWebService extends MasterWebService {

	public void saveHendelse(PasientKomplikasjonWebModel innMelding);
	public void saveTransfusjon(TransfusjonWebModel transfusjon,PasientKomplikasjonWebModel innMelding);
	public PasientTableService getPasientService();


	public void setPasientService(PasientTableService pasientService);
	public void saveVigilansMelder(Vigilansmelding melding);
	public List<Vigilansmelding> collectMeldinger(Long meldeid);
	public void updateVigilansmelding(Vigilansmelding melding);
	public List<Vigilansmelding> collectMeldingen(Long meldeId);
	public Diskusjon collectDiskusjon(Long diskId);
	public List<Produktegenskap> hentEgenskaper();
	public List<Tiltak> hentTiltakene();
	public List<Forebyggendetiltak> hentForebyggende();
	public List<Symptomer> hentSymptomer();
	public List<Utredning> hentUtredningene();
	
}
