package no.naks.biovigilans.model;
import java.util.Date;
import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;



/**
 * Inneholder diskusjonen knyttet til en melding, og som pågår mellom melder og Mottaker/vurderer
 * En diskusjon opprettes når saksbehandler setter et eller flere flagg til en melding, eller fortsetter en dialog med melder
 */

public abstract class AbstractDiskusjon extends AbstractModel {

	private Date datoforkommentar;
	private String kommentar;
	private String tema;
	private Long diskusjonid;
	private Long meldeid;
	private String saksbehandler; // Saksbehandler for denne kommentaren
	
	private Map<String,String> diskusjonsFields;

	
	public Map<String, String> getDiskusjonsFields() {
		return diskusjonsFields;
	}
	public void setDiskusjonsFields(Map<String, String> diskusjonsFields) {
		this.diskusjonsFields = diskusjonsFields;
	}
	public Date getDatoforkommentar() {
		return datoforkommentar;
	}
	public void setDatoforkommentar(Date datoforkommentar) {
		this.datoforkommentar = datoforkommentar;
	}
	public String getKommentar() {
		return kommentar;
	}
	public void setKommentar(String kommentar) {
		if (kommentar == null){
			Map<String,String> userEntries = getDiskusjonsFields();
			String field = "melding";
			kommentar = userEntries.get(field);
			if (kommentar == null)
				kommentar = "";
		}
		this.kommentar = kommentar;
	}
	public Long getDiskusjonid() {
		return diskusjonid;
	}
	public void setDiskusjonid(Long diskusjonid) {
		this.diskusjonid = diskusjonid;
	}
	public Long getMeldeid() {
		return meldeid;
	}
	public void setMeldeid(Long meldeid) {
		this.meldeid = meldeid;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		if (tema == null){
			Map<String,String> userEntries = getDiskusjonsFields();
			String field = "tema";
			tema = userEntries.get(field);
			if (tema == null)
				tema = "";
		}
		this.tema = tema;
	}
	public String getSaksbehandler() {
		return saksbehandler;
	}
	public void setSaksbehandler(String saksbehandler) {
		this.saksbehandler = saksbehandler;
	}
	
	
}