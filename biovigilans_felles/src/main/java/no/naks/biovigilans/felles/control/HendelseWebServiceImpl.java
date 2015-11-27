package no.naks.biovigilans.felles.control;

import java.util.List;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.PasientGruppeImpl;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Utredning;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.service.HendelseTablesService;
import no.naks.biovigilans.service.PasientTableService;
import no.naks.biovigilans.felles.model.PasientKomplikasjonWebModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.framework.web.control.MasterWebServiceImpl;

/**
 * HendelsesWebService
 * Denne tjenesten sørger for lagring av pasienthendelser
 * Den benyttes også til å hente ut alle meldinger knyttet til en gitt melder
 * @author olj
 *
 */
public class HendelseWebServiceImpl extends MasterWebServiceImpl implements
		HendelseWebService{

	private PasientTableService pasientService;
	private HendelseTablesService hendelseTablesService;
	
	public HendelseWebServiceImpl() {
		super();
		 System.out.println("Hendelsewebservice created");
	}

	
	public HendelseTablesService getHendelseTablesService() {
		return hendelseTablesService;
	}


	public void setHendelseTablesService(HendelseTablesService hendelseTablesService) {
		this.hendelseTablesService = hendelseTablesService;
	}


	public PasientTableService getPasientService() {
		return pasientService;
	}


	public void setPasientService(PasientTableService pasientService) {
		this.pasientService = pasientService;
	}



	public void saveHendelse(PasientKomplikasjonWebModel innMelding) {

		pasientService.savePasient(innMelding.getPasient());
		
	}


	
	public void saveTransfusjon(TransfusjonWebModel transfusjon,PasientKomplikasjonWebModel innMelding) {
		transfusjon.setPasient(innMelding.getPasient());
		transfusjon.getTransfusjon().setPasient_Id(innMelding.getPasient().getPasient_Id());
		pasientService.saveTransfusjon(transfusjon.getTransfusjon());
		
	}
	public List<Produktegenskap> hentEgenskaper(){
		return pasientService.hentproduktEgenskaper();
	}
	public List<Tiltak> hentTiltakene(){
		return pasientService.hentTiltakene();
	}
	public List<Forebyggendetiltak> hentForebyggende(){
		return pasientService.hentforebyggendeListe();
	}
	public List<Symptomer> hentSymptomer(){
		return pasientService.hentsymptomer();
	}
	public List<Utredning> hentUtredningene(){
		return pasientService.hentUtredninger();
	}
	/* 
	 * saveVigilansMelder
	 * Denne rutinen oppdaterer Vigilansmelding med melderid
	 */
	public void saveVigilansMelder(Vigilansmelding melding){
		hendelseTablesService.saveVigilansmelding(melding);
	}

	public List<Vigilansmelding> collectMeldinger(Long meldeid){
		
		return hendelseTablesService.collectMeldinger(meldeid);
	}
	public List<Vigilansmelding> collectMeldingen(Long meldeId){
		return hendelseTablesService.collectMeldingen(meldeId);
		
	}
	public Diskusjon collectDiskusjon(Long diskId){
		return hendelseTablesService.collectDiskusjon(diskId);
	}
	/* 
	 * updateVigilansmelding
	 * Denne rutinen oppdaterer vigilansmelding med status fra saksbehandler (sjekklistesaksbehandler)
	 */
	public void updateVigilansmelding(Vigilansmelding melding){
		hendelseTablesService.updateVigilansmelding(melding);
	}
}
