package no.naks.biovigilans.model;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Kontaktperson for prosedyren. Benyttes ikke i Biovigilans. (Se Melder)
 * 
 */

public abstract class AbstractKontakt extends AbstractModel implements Kontakt{

	private Long kontakt_Id;
	/**
	 * Navn på kontaktperson
	 */
	private String navn;
	/**
	 * Yrkestittel
	 */
	private String tittel;
	/**
	 * Epost til kontaktperson
	 */
	private String epost;
	
	
	public Long getKontakt_Id() {
		return kontakt_Id;
	}
	public void setKontakt_Id(Long kontakt_Id) {
		this.kontakt_Id = kontakt_Id;
	}
	public String getTittel() {
		return tittel;
	}
	public void setTittel(String tittel) {
		this.tittel = tittel;
	}
	public String getNavn() {
		return navn;
	}
	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getEpost() {
		return epost;
	}
	public void setEpost(String epost) {
		this.epost = epost;
	}
	
}