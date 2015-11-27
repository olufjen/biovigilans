package no.naks.biovigilans.model;

import java.sql.Types;
import java.util.Map;

public interface Pasientkomplikasjon {

	
	public Long getMeldeid();
	public void setMeldeid(Long meldeid);
	
	public Long getMelderId();
	public void setMelderId(Long melderId);
	public Long getTransfusjonsId();

	public void setTransfusjonsId(Long transfusjonsId);
	public String getKlassifikasjon();
	public void setKlassifikasjon(String klassifikasjon);
	public int getTidfrapabegynttrasfusjontilkomplikasjon();
	public void setTidfrapabegynttrasfusjontilkomplikasjon(
			int tidfrapabegynttrasfusjontilkomplikasjon);
	public String getAlvorlighetsgrad();
	public void setAlvorlighetsgrad(String alvorlighetsgrad);
	public String getKliniskresultat();
	public void setKliniskresultat(String kliniskresultat);
	public String getArsakssammenheng();
	public void setArsakssammenheng(String arsakssammenheng);
	public Map<String, Symptomer> getSymptomer();
	public void savetoPasientkomplikasjon();
	public void setSymptomer(Map<String, Symptomer> symptomer);

	public Map<String, Komplikasjonsklassifikasjon> getKomplikasjonsKlassifikasjoner();

	public void setKomplikasjonsKlassifikasjoner(
			Map<String, Komplikasjonsklassifikasjon> komplikasjonsKlassifikasjoner);

	public Map<String, String> getKomplikasjonsFields();
	
	public Map<String, Utredning> getUtredninger();

	public void setUtredninger(Map<String, Utredning> utredninger);
	public void setKomplikasjonsFields(Map<String, String> komplikasjonsFields);
	public void setPatientkomplicationfieldMaps(String[]userFields);
	public void saveField(String userField,String userValue);
	public void setKladd(String kladd);
	public String[] getKeys();

	public void setKeys(String[] keys);
	public void produceSymptoms(Symptomer symptomer);
	public void produceClassification(Komplikasjonsklassifikasjon klassifikasjon);
	public Utredning getUtredning();

	public void setUtredning(Utredning utredning);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
	public void setKomplikasjonstypes();
	
}
