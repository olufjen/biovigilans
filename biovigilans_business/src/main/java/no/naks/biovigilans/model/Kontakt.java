package no.naks.biovigilans.model;

public interface Kontakt {

	public Long getKontakt_Id();
	public void setKontakt_Id(Long kontakt_Id);
	public String getTittel();
	public void setTittel(String tittel);
	public String getNavn();
	public void setNavn(String navn);

	public String getEpost();
	public void setEpost(String epost);
	public void setParams();
}