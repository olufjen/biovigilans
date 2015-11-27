package no.naks.biovigilans.model;

import java.util.Map;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * AbstractSaksbehandler
 * Denne klassen representerer saksbehandlerne i biovigilans
 *  
 * @author olj
 *
 */
public abstract class AbstractSaksbehandler extends AbstractModel {
	
	private Long sakbehandlerid;
	private String behandernavn;
	private String behandlerepost;
	private String behandlertlf;
	private String behandlerpassord;
	
	protected Map<String,String> behandlerFields;
	
	public Map<String, String> getBehandlerFields() {
		return behandlerFields;
	}
	public void setBehandlerFields(Map<String, String> behandlerFields) {
		this.behandlerFields = behandlerFields;
	}
	public Long getSakbehandlerid() {
		return sakbehandlerid;
	}
	public void setSakbehandlerid(Long sakbehandlerid) {
		this.sakbehandlerid = sakbehandlerid;
	}
	public String getBehandernavn() {
		return behandernavn;
	}
	public void setBehandernavn(String behandernavn) {
		this.behandernavn = behandernavn;
	}
	public String getBehandlerepost() {
		return behandlerepost;
	}
	public void setBehandlerepost(String behandlerepost) {
		if (behandlerepost == null){
			Map<String,String> userEntries = getBehandlerFields();
			String field = "user";
			behandlerepost =  userEntries.get(field);
			if (behandlerepost == null)
				behandlerepost = "";
		}
		this.behandlerepost = behandlerepost;
	}
	public String getBehandlertlf() {
		return behandlertlf;
	}
	public void setBehandlertlf(String behandlertlf) {
		this.behandlertlf = behandlertlf;
	}
	public String getBehandlerpassord() {
		return behandlerpassord;
	}
	public void setBehandlerpassord(String behandlerpassord) {
		if (behandlerpassord == null){
			Map<String,String> userEntries = getBehandlerFields();
			String field = "pass";
			behandlerpassord =  userEntries.get(field);
			if (behandlerpassord == null)
				behandlerpassord = "";
		}
		this.behandlerpassord = behandlerpassord;
	}
	
	
}
