package no.naks.biovigilans.service;

import java.util.List;

import no.naks.biovigilans.dao.PasientDAO;
import no.naks.biovigilans.dao.TransfusjonDAO;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Pasientgruppe;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Utredning;
import no.naks.rammeverk.kildelag.service.BusinessService;

public class PasientTableServiceImpl extends BusinessService implements PasientTableService {

	private PasientDAO pasientDAO;
	private TransfusjonDAO transfusjonDAO;

	public PasientDAO getPasientDAO() {
		return pasientDAO;
	}

	public void setPasientDAO(PasientDAO pasientDAO) {
		this.pasientDAO = pasientDAO;
	}

	
	public TransfusjonDAO getTransfusjonDAO() {
		return transfusjonDAO;
	}

	public void setTransfusjonDAO(TransfusjonDAO transfusjonDAO) {
		this.transfusjonDAO = transfusjonDAO;
	}

	public void savePasient(Pasient pasient){
		pasientDAO.savePasient(pasient);
	}

	
	public void saveTransfusjon(Transfusjon transfusjon) {
		transfusjonDAO.saveTransfusjon(transfusjon);
		
	}
	public List<Tiltak> hentTiltakene(){
		return pasientDAO.getTiltakene();
	}
	public List<Forebyggendetiltak> hentforebyggendeListe(){
		return pasientDAO.getForebyggendeListe();
	}
	public List<Produktegenskap> hentproduktEgenskaper(){
		return transfusjonDAO.getProduktegenskaper();
	}
	public List<Symptomer> hentsymptomer(){
		return transfusjonDAO.getSymptomListe();
	}
	public List<Utredning> hentUtredninger(){
		return transfusjonDAO.getUtredninger();
	}
}
