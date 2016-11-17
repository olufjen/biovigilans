package no.naks.biovigilans.model;

import java.util.Date;
import java.util.Map;

public interface Annenkomplikasjon extends Vigilansmelding {
	
	
	public String getKlassifikasjon();
	public void setKlassifikasjon(String klassifikasjon);
	public String getKlassifikasjonkode();
	public void setKlassifikasjonkode(String klassifikasjonkode);
	public String getKomplikasjonbeskrivelse();
	public void setKomplikasjonbeskrivelse(String komplikasjonbeskrivelse);
	public String getKomplikasjondefinisjon();
	public void setKomplikasjondefinisjon(String komplikasjondefinisjon);
	public String getAvvikarsak();
	public void setAvvikarsak(String avvikarsak);
	public String getHovedprosess();
	public void setHovedprosess(String hovedprosess);
	public String getTiltak();
	public void setTiltak(String tiltak);
	public void setDelkode(String delkode);
	public String getDelkode();
	public String getKommentar();
	public void setKommentar(String kommentar);
	public String getOppdaget();
	public void setOppdaget(String oppdaget);
	public Long getMeldeid();
	public void setMeldeid(Long meldeid);
	public Long getMelderId();
	public void setMelderId(Long melderId);
	public Map<String, String> getAnnenKomplikasjonsFields();
	public void setAnnenKomplikasjonsFields(
			Map<String, String> annenKomplikasjonsFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void setAnnenkomplicationfieldMaps(String[]userFields);
	public void setParams();
	public int[] getTypes();
	public void setannenKomplikasjonstypes();
	public Object[] getParams();
	public int[] getUtypes();	
	public void saveField(String userField, String userValue);
	public void saveToAnnenKomplikasjon();
	
	public String getPasientopplysninger();

	public void setPasientopplysninger(String pasientopplysninger);
	public java.lang.String getSjekklistesaksbehandling();
	public void setSjekklistesaksbehandling(
			java.lang.String sjekklistesaksbehandling);

	//public String getDatoforhendelseKvittering() ;
	//public void setDatoforhendelseKvittering(String datoforhendelseKvittering);

	public boolean isUpdat();
	public void setUpdat(boolean isUpdat);
	public Date getDatoforhendelse();
	public void setDatoforhendelse(Date datoforhendelse);
	public Date getMeldingsdato();
	public void setMeldingsdato(Date meldingsdato);
}
