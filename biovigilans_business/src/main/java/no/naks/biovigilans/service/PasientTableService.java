package no.naks.biovigilans.service;

import java.util.List;

import no.naks.biovigilans.dao.PasientDAO;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Utredning;

public interface PasientTableService {

	public PasientDAO getPasientDAO();

	public void setPasientDAO(PasientDAO pasientDAO);
	
	public void savePasient(Pasient pasient);
	public void saveTransfusjon(Transfusjon transfusjon);
	public List<Tiltak> hentTiltakene();
	public List<Forebyggendetiltak> hentforebyggendeListe();
	public List<Produktegenskap> hentproduktEgenskaper();
	public List<Symptomer> hentsymptomer();
	public List<Utredning> hentUtredninger();
	
}
