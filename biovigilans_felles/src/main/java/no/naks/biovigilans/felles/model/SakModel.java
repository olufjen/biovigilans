package no.naks.biovigilans.felles.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.DiskusjonImpl;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.SakImpl;
import no.naks.biovigilans.model.Saksbehandler;

/**
 * @author olj
 *  SakModel
 *  Denne web model klassen benyttes til saksgang i administrasjonen
 *  Den benyttes for lagring av all informasjon til tabellene sak og diskusjon
 */
public class SakModel extends VigilansModel {

	private Sak sak;
	private Diskusjon hemovigilansDiskusjon; // Settes når diskusjonen er av type diskusjon i Hemovigilansgruppen
	private String[] flaggNames; // Inneholder navn på input felt i skjermbildet. Bruker velger ett eller flere saksflagg
	private String[] merknader; // Inneholder merknader som resulterer i at saksstatus endres
	private String[] statusflag;
	private Map<String,Sak> saksMappe = null;
	private Map<String,Diskusjon> diskusjonsMappe = null;
	private Saksbehandler loginSaksbehandler;
	private boolean tilMelder = false; // Flagg er satt dersom man skal sende melding til melder
	private String tilDiskusjon =  ""; // Har verdi dersom merknad til diskusjon i hemovigilansgruppen er satt
	private String diskId;				// Satt dersom merknad til diskusjon i hemovigilansgruppen er satt
	private String sakId;				// Satt dersom merknad til diskusjon i hemovigilansgruppen er satt
	private boolean tilDialog = false; // Satt true når merknad til diskusjon i hemovilansgruppen er satt
	private boolean reklassifikasjon = false; // Satt true dersom bruker ønsker å reklassifisere 
	private Long gmlMeldeid = null;   	//Har verdi dersom det gjøres en reklassifisering
	
	public SakModel() {
		super();
		
	}
	
	public Long getGmlMeldeid() {
		return gmlMeldeid;
	}

	public void setGmlMeldeid(Long gmlMeldeid) {
		this.gmlMeldeid = gmlMeldeid;
	}

	public boolean isReklassifikasjon() {
		boolean treklass = reklassifikasjon;
		reklassifikasjon = false;
		return treklass;
	}

	public void setReklassifikasjon(boolean reklassifikasjon) {
		this.reklassifikasjon = reklassifikasjon;
	}

	public boolean isTilDialog() {
		return tilDialog;
	}

	public void setTilDialog(boolean tilDialog) {
		this.tilDialog = tilDialog;
	}

	public Diskusjon getHemovigilansDiskusjon() {
		return hemovigilansDiskusjon;
	}

	public void setHemovigilansDiskusjon(Diskusjon hemovigilansDiskusjon) {
		this.hemovigilansDiskusjon = hemovigilansDiskusjon;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public String getSakId() {
		return sakId;
	}

	public void setSakId(String sakId) {
		this.sakId = sakId;
	}

	public String getTilDiskusjon() {
		return tilDiskusjon;
	}

	public void setTilDiskusjon(String tilDiskusjon) {
		this.tilDiskusjon = tilDiskusjon;
	}

	public boolean isTilMelder() {
		return tilMelder;
	}

	public void setTilMelder(boolean tilMelder) {
		this.tilMelder = tilMelder;
	}

	public Saksbehandler getLoginSaksbehandler() {
		return loginSaksbehandler;
	}

	public void setLoginSaksbehandler(Saksbehandler loginSaksbehandler) {
		this.loginSaksbehandler = loginSaksbehandler;
	}

	public String[] getMerknader() {
		return merknader;
	}

	public void setMerknader(String[] merknader) {
		this.merknader = merknader;
	}

	public String[] getStatusflag() {
		return statusflag;
	}

	public void setStatusflag(String[] statusflag) {
		this.statusflag = statusflag;
	}

	public Map<String, Sak> getSaksMappe() {
		return saksMappe;
	}

	public void setSaksMappe(Map<String, Sak> saksMappe) {
		this.saksMappe = saksMappe;
	}

	public Map<String, Diskusjon> getDiskusjonsMappe() {
		return diskusjonsMappe;
	}

	public void setDiskusjonsMappe(Map<String, Diskusjon> diskusjonsMappe) {
		this.diskusjonsMappe = diskusjonsMappe;
	}

	public Sak getSak() {
		return sak;
	}
	public void setSak(Sak sak) {
		this.sak = sak;
	}

	public String[] getFlaggNames() {
		return flaggNames;
	}
	public void setFlaggNames(String[] flaggNames) {
		this.flaggNames = flaggNames;
	}
	/**
	 * saveSaker
	 * Denne funksjonen oppretter en mappe med diskusjoner og en mappe med saker 
	 * etter hvilke flagg saksbehandler har satt.
	 * Den kalles fra rapporterSrverResource når saksbehandler har valgt å sette saksmerknader
	 * @param meldeId Fremmednøkkel til melding
	 */
	public void saveSaker(Long meldeId){
		Map<String,String> saksMap = getFormMap();
		if (saksMappe == null)
			saksMappe = new HashMap<String,Sak>(); 
		if (diskusjonsMappe == null)
			diskusjonsMappe = new HashMap<String,Diskusjon>();
		for (int i = 0;i< flaggNames.length;i++){
			String flagg = saksMap.get(flaggNames[i]);
			if (flagg != null && !flagg.isEmpty()){
				if (i == 0)
					tilMelder = true;
				Diskusjon diskusjon = new DiskusjonImpl();
				sak = new SakImpl();
				diskusjon.setKommentar(flagg);
				diskusjon.setTema("Sak opprettet");
				String name = "Ikke kjent";
				if (loginSaksbehandler != null && loginSaksbehandler.getBehandernavn() != null)
					name = loginSaksbehandler.getBehandernavn();
				else 
					name = "Ikke kjent";
				diskusjon.setSaksbehandler(name);
				diskusjon.setMeldeid(meldeId);
				sak.setSakskommentar(flagg);
				sak.setSaksopplysninger(flagg);
				sak.setSakstatus(flagg);
				diskusjonsMappe.put(flaggNames[i],diskusjon);
				saksMappe.put(flaggNames[i],sak);
		
			}			
		}
		
	}
	/**
	 * setSakdiskusjon
	 * Denne funksjonen setter fremmednøkkel til sak etter at riktig antall poster for diskusjoner er opprettet 
	 * Den kalles fra rapporterSrverResource når saksbehandler har valgt å sette saksmerknader
	 */
	public void setSakdiskusjon(){
		Map<String,String> saksMap = getFormMap();
		tilDialog = false;
		for (int i = 0;i< flaggNames.length;i++){
			String flagg = flaggNames[i];
			String flaggMerknad = saksMap.get(flaggNames[i]);
			Diskusjon diskusjon = diskusjonsMappe.get(flagg);
			if (diskusjon != null){
				for (int isak = 0;isak< flaggNames.length;isak++){
					String sakFlagg = flaggNames[isak];
					Sak sak = saksMappe.get(sakFlagg);
					if (sak != null && sak.getSakstatus().equals(diskusjon.getKommentar())){
						sak.setDiskusjonid(diskusjon.getDiskusjonid());
						long ll = 1;
						Long bId = new Long(ll);
						if (loginSaksbehandler != null && loginSaksbehandler.getSakbehandlerid() != null){
							bId = loginSaksbehandler.getSakbehandlerid();
							System.out.println("Login saksbehandler: "+loginSaksbehandler.getBehandernavn());
						}else {
//							long ll = 1;
//							bId = new Long(ll);
							System.out.println("Login saksbehandler mangler !!");
						}
						sak.setSakbehandlerId(bId);
						break;
					}
				}
				if (flaggMerknad != null && flaggMerknad.equals(merknader[1])){
					tilDiskusjon = merknader[1];
					 tilDialog = true;
					diskId = String .valueOf(diskusjon.getDiskusjonid().longValue());
//					sakId = String.valueOf(sak.getSaksid().longValue());
					hemovigilansDiskusjon = diskusjon;
				}
				if (flaggMerknad != null && flaggMerknad.equals(merknader[6])){
					reklassifikasjon = true; // Settes true dersom bruker ønsker reklassifisering
				}
			}
		}
	}
	/**
	 * lagDiskusjonsliste
	 * Denne funksjonen lager en diskusjonsliste for visning til saksbehandling en en sak (melding)
	 * @return
	 */
	public List lagDiskusjonsliste(){
		Map<String,String> saksMap = getFormMap();
		List diskusjonsliste = new ArrayList<Diskusjon>();
		for (int i = 0;i< flaggNames.length;i++){
			String flagg = flaggNames[i];
			if (flagg != null && !flagg.isEmpty()){
				Diskusjon diskusjon = diskusjonsMappe.get(flagg);
				if (diskusjon != null)
					diskusjonsliste.add(diskusjon);
			}
		}
		return diskusjonsliste;
	}
	/**
	 * setsakStatus
	 * Denne funksjonen setter sakstatus på en sak (melding) på bakgrunn av merknader som settes på meldingen.
	 * @param merknad
	 * @return
	 */
	public String setsakStatus(List<Diskusjon>diskusjoner){
		int i = 0;
		String status = null;
		for (Diskusjon diskusjon:diskusjoner){
			String merknad = diskusjon.getKommentar();
			for (String merknaden:merknader){
				if (merknad.equals(merknaden)){
					if (i == 0)
						status = statusflag[5];
					if (i >= 1 && i < 3)
						status = statusflag[2];
				}
				i++;
				if (status != null)
					break;
			}			
		}

		return status;
	}
	/**
	 * lagSak
	 * Denne rutinen oppretter en diskusjon/sak når en bruker endrer status på en melding
	 * @param meldeId Fremmednøkkel til melding
	 * @param status Hva status er endret til.
	 */
	public void lagSak(Long meldeid,String status){
		String msg = "Endret status ";
		if (saksMappe == null)
			saksMappe = new HashMap<String,Sak>(); 
		if (diskusjonsMappe == null)
			diskusjonsMappe = new HashMap<String,Diskusjon>();
		Diskusjon diskusjon = new DiskusjonImpl();
		sak = new SakImpl();
		diskusjon.setMeldeid(meldeid);
		diskusjon.setTema("Sak opprettet");
		String name = "Ikke kjent";
		if (loginSaksbehandler != null )
			name = loginSaksbehandler.getBehandernavn();
		diskusjon.setSaksbehandler(name);
		diskusjon.setTema(msg);
		diskusjon.setKommentar(msg+status);
		sak.setSakskommentar(msg);
		sak.setSaksopplysninger(msg);
		sak.setSakstatus(msg+status);
		diskusjonsMappe.put(flaggNames[7],diskusjon);
		saksMappe.put(flaggNames[7],sak);
		
	}
}
