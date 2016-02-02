package no.naks.biovigilans.felles.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.DiskusjonImpl;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.SakImpl;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.SaksbehandlerImpl;

/**
 * Denne klasses benyttes til å sende meldinger til melder.
 * Disse meldingene blir lagret som en del av diskusjonen til en sak (melding)
 * 
 * @author olj
 *
 */
public class MeldingModel extends VigilansModel {
	private Diskusjon diskusjon;
	private List<Saksbehandler> saksbehandlere = null;
	private Saksbehandler saksbehandler = null;
	
	public MeldingModel() {
		super();
		
	}

	public Saksbehandler getSaksbehandler() {
		return saksbehandler;
	}

	public void setSaksbehandler(Saksbehandler saksbehandler) {
		this.saksbehandler = saksbehandler;
	}

	public List<Saksbehandler> getSaksbehandlere() {
		return saksbehandlere;
	}

	public void setSaksbehandlere(List<Saksbehandler> saksbehandlere) {
		this.saksbehandlere = saksbehandlere;
	}

	public Diskusjon getDiskusjon() {
		return diskusjon;
	}

	public void setDiskusjon(Diskusjon diskusjon) {
		this.diskusjon = diskusjon;
	}

	/**
	 * saveDiskusjon
	 * Denne rutinen lager en ny post i tabellen diskusjon basert på diskusjon mellom saksbehandlere
	 * Den finner også saksbehandler som gjør kommentaren.
	 * eller som del av dialog i hemovigilansgruppen
	 */
	public void saveDiskusjon(){
		String[] formFields = getFormNames();
		Map<String,String> userEntries = getFormMap(); // formMap inneholder verdier angitt av bruker
		if (diskusjon == null)
			diskusjon = new DiskusjonImpl();
		diskusjon.setDiskusjonsFields(userEntries);
		diskusjon.savetoDiskusjon();
		if (diskusjon.getTema() == null || diskusjon.getTema().equals(""))
			diskusjon.setTema("Svar;Fra melder");
		String behandlerKeyv = "tab-sakbeh";
		String behandlerValgt = userEntries.get(behandlerKeyv);
		if (behandlerValgt != null && !behandlerValgt.isEmpty()){
			for (Saksbehandler saksbehandler : saksbehandlere){
				if (saksbehandler.getBehandernavn().equals(behandlerValgt)){
					this.saksbehandler = saksbehandler;
					break;
				}
			}
		}
		if (saksbehandler != null)
			diskusjon.setSaksbehandler(saksbehandler.getBehandernavn());
		else{
			saksbehandler = new SaksbehandlerImpl();
			saksbehandler.setSakbehandlerid(new Long(1));
			diskusjon.setSaksbehandler("ukjent");
		}
	}
	
	/**
	 * saveSak
	 * Denne rutinen lager en ny sak som plasseres i en saksmappe som kan lagres i databasen.
	 * Rutinen kalles når det opprettes dialog mellom saksbehandlere
	 * @param behandlerId
	 * @param flaggName Nøkkel til saksmappe
	 * @return HashMap saksmappe
	 */
	public Map<String,Sak> saveSak(String flaggName,Long behandlerId){
		if (diskusjon != null){
			HashMap<String,Sak> saksMappe = new HashMap<String,Sak>(); 
			Sak sak = new SakImpl();
			sak.setDiskusjonid(diskusjon.getDiskusjonid());
			sak.setSakbehandlerId(behandlerId);
			sak.setSakskommentar("Diskusjon");
			sak.setSakstatus("Diskusjon");
			sak.setSaksopplysninger(diskusjon.getKommentar());
			saksMappe.put(flaggName, sak);
			return saksMappe;
		}
		return null;
	}

	
}	
