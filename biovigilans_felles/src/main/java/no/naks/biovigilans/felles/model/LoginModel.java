package no.naks.biovigilans.felles.model;

import java.util.Map;

import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.SaksbehandlerImpl;

public class LoginModel extends VigilansModel {

	private String epostAdresse;
	private String passord;
	private Saksbehandler saksbehandler;
	
	public LoginModel() {
		super();
		saksbehandler = new SaksbehandlerImpl();
		
	}
	public String getEpostAdresse() {
		return epostAdresse;
	}
	public void setEpostAdresse(String epostAdresse) {
		this.epostAdresse = epostAdresse;
	}
	public String getPassord() {
		return passord;
	}
	public void setPassord(String passord) {
		this.passord = passord;
	}
	/**
	 * saveLogin
	 * Denne rutinen lagrer saksbehandlers epost og passord til en instans av saksbehandler
	 * @since 05.04.16
	 * Den lagrer også valg av database (meldeordning). Default er Hemovigilans
	 */
	public void saveLogin(){
		String[] formFields = getFormNames();
		Map<String,String> userEntries = getFormMap(); // formMap inneholder verdier angitt av bruker
		saksbehandler.setBehandlerFields(userEntries);
		saksbehandler.savetoSaksbehandler();
	}
	public Saksbehandler getSaksbehandler() {
		return saksbehandler;
	}
	/**
	 * setSaksbehandler
	 * Rutinen setter aktuell login saksbehandler
	 * saksbehandler har også informasjon om valgt database
	 * @param saksbehandler
	 */
	public void setSaksbehandler(Saksbehandler saksbehandler) {
		String db = this.saksbehandler.getDbChoice();
		if (db == null || db.equals(""))
			db = saksbehandler.getDbChoice();
		saksbehandler.setDbChoice(db);
		this.saksbehandler = saksbehandler;
	}
	
}
