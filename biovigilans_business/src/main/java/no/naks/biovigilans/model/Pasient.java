package no.naks.biovigilans.model;

import java.util.Map;

public interface Pasient {
	public Long getPasient_Id();
	public void setPasient_Id(Long pasient_Id);
	public String getKjonn();
	public void setKjonn(String kjonn);
	public String getAldersGruppe();
	public void setAldersGruppe(String aldersGruppe);
	public String[] getAntiStoff();
	public void setAntiStoff(String antiStoff[]);
	public String getInneliggendePoli();
	public void setInneliggendePoli(String inneliggendePoli);
	public String getAvdeling();
	public void setAvdeling(String avdeling);
	public Map<String, String> getPatientFields();

	public void setPatientFields(Map<String, String> patientFields);

	/**
	 * setPatientfieldMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	public void setPatientfieldMaps(String[]userFields);
	/**
	 * saveField
	 * Denne rutinen lagrer skrembildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	public void saveField(String userField,String userValue);
	public void savetoPatient();
	public String[] getKeys();
	public void setKeys(String[] keys);
	public Map<String, Antistoff> getAntistoffer();

	public void setAntistoffer(Map<String, Antistoff> antistoffer);

	public Map<String, Sykdom> getSykdommer();

	public void setSykdommer(Map<String, Sykdom> sykdommer);
	

	public Map<String, Tiltak> getAlleTiltak();

	public void setAlleTiltak(Map<String, Tiltak> alleTiltak);

	public void produceSykdommer(Sykdom sykdom);
	public void produceAntistoffer(Antistoff antistoff);
	public void produceTiltak(Tiltak tiltak, Forebyggendetiltak forebyggendeTiltak);
	public Map<String, Transfusjon> getTransfusjoner();

	public void setTransfusjoner(Map<String, Transfusjon> transfusjoner);
	
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
}
