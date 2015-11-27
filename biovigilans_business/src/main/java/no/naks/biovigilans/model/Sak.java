package no.naks.biovigilans.model;

import java.util.Date;

public interface Sak {
	public void setDatoSak(Date datoSak);
	public String getSaksopplysninger();
	public void setSaksopplysninger(String saksopplysninger);
	public String getSakskommentar();
	public void setSakskommentar(String sakskommentar);
	public String getSakstatus();
	public void setSakstatus(String sakstatus);
	public Long getDiskusjonid();
	public void setDiskusjonid(Long diskusjonid);
	public Long getSakbehandlerId();
	public void setSakbehandlerId(Long sakbehandlerId);
	public Long getSaksid();
	public void setSaksid(Long saksid);
	public void setParams();
	public Object[] getParams();
	public int[] getTypes();
	public int[] getUtypes();
	
}
