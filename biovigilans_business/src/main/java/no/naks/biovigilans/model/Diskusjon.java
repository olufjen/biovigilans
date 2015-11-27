package no.naks.biovigilans.model;

import java.util.Date;
import java.util.Map;

public interface Diskusjon {
	public Date getDatoforkommentar();
	public void setDatoforkommentar(Date datoforkommentar);
	public java.lang.String getKommentar();
	public String getTema();
	public void setTema(String tema);
	public void setKommentar(java.lang.String kommentar);
	public Long getDiskusjonid();
	public void setDiskusjonid(Long diskusjonid);
	public Long getMeldeid();
	public void setMeldeid(Long meldeid);
	public void setParams();
	public Object[] getParams();
	public int[] getTypes();
	public int[] getUtypes();
	public Map<String, String> getDiskusjonsFields();
	public void setDiskusjonsFields(Map<String, String> diskusjonsFields);
	public void savetoDiskusjon();
	public String getSaksbehandler();
	public void setSaksbehandler(String saksbehandler);
}
