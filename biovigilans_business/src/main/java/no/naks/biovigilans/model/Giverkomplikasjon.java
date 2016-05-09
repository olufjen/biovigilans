package no.naks.biovigilans.model;

import java.util.Date;
import java.util.Map;

public interface Giverkomplikasjon {
	
	public String getStedforkomplikasjon();

	public void setStedforkomplikasjon(String stedforkomplikasjon);

	public String getTidfratappingtilkompliasjon();

	public void setTidfratappingtilkompliasjon(String tidfratappingtilkompliasjon);

	public String getBehandlingssted();

	public void setBehandlingssted(String behandlingssted);

	public String getTilleggsopplysninger();

	public void setTilleggsopplysninger(String tilleggsopplysninger);

	public String getAlvorlighetsgrad();

	public void setAlvorlighetsgrad(String alvorlighetsgrad);

	public String getKliniskresultat();

	public void setKliniskresultat(String kliniskresultat);

	public String getVarighetkomplikasjon();

	public void setVarighetkomplikasjon(String varighetkomplikasjon);

	public String getSymptomMelding();

	public void setSymptomMelding(String symptomMelding);
	public Map<String, String> getKomplikasjonsFields();
	public void setKomplikasjonsFields(Map<String, String> komplikasjonsFields);
	public String[] getKeys();
	public void setKeys(String[] keys);
	public void setGiverkomplicationfieldMaps(String[]userFields);
	public void saveField(String userField, String userValue); 
	public void setParams();
	public void setGiverkompTypes();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	
	public void saveToGiverkomplikasjon();

	public Long getMelderId();
	public void setMelderId(Long melderId);
	public Long getMeldeid();
	public void setMeldeid(Long meldeid);
	public Long getDonasjonid();
	public void setDonasjonid(Long donasjonid);
	public Date getDatosymptomer();
	public void setDatosymptomer(Date datosymptomer);
	public java.lang.String getSjekklistesaksbehandling();
	public void setSjekklistesaksbehandling(
			java.lang.String sjekklistesaksbehandling);
	public Date getDatoforhendelse();
	public void setDatoforhendelse(Date datoforhendelse);
	public Date getDatooppdaget();
	public void setDatooppdaget(Date datooppdaget);
	public Date getMeldingsdato();
	public void setMeldingsdato(Date meldingsdato);
	
}
