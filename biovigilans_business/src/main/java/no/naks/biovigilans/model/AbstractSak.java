package no.naks.biovigilans.model;

import java.util.Date;

import no.naks.rammeverk.kildelag.model.AbstractModel;

/**
 * @author olj
 * Denne klassen representerer sakstabellen i db.
 * En sak opprettes når saksbehandler setter et eller flere flagg til en melding, eller fortsetter en dialog med melder
 */
public abstract class AbstractSak extends AbstractModel implements Sak{

	private Date datoSak;
	private String saksopplysninger;
	private String sakskommentar;
	private String sakstatus;
	private Long diskusjonid;
	private Long saksid;
	private Long sakbehandlerId;
	
	public Long getSakbehandlerId() {
		return sakbehandlerId;
	}
	public void setSakbehandlerId(Long sakbehandlerId) {
		this.sakbehandlerId = sakbehandlerId;
	}
	public Date getDatoSak() {
		return datoSak;
	}
	public void setDatoSak(Date datoSak) {
		this.datoSak = datoSak;
	}
	public String getSaksopplysninger() {
		return saksopplysninger;
	}
	public void setSaksopplysninger(String saksopplysninger) {
		this.saksopplysninger = saksopplysninger;
	}
	public String getSakskommentar() {
		return sakskommentar;
	}
	public void setSakskommentar(String sakskommentar) {
		this.sakskommentar = sakskommentar;
	}
	public String getSakstatus() {
		return sakstatus;
	}
	public void setSakstatus(String sakstatus) {
		this.sakstatus = sakstatus;
	}
	public Long getDiskusjonid() {
		return diskusjonid;
	}
	public void setDiskusjonid(Long diskusjonid) {
		this.diskusjonid = diskusjonid;
	}
	public Long getSaksid() {
		return saksid;
	}
	public void setSaksid(Long saksid) {
		this.saksid = saksid;
	}
	
	
}
