package no.naks.biovigilans.model;

import no.naks.rammeverk.kildelag.model.AbstractModel;


/**
 * Pasientgrupper som denne prosedyren er relevant for. (3)
 * 
 */

public abstract class AbstractPasientgruppe extends AbstractModel implements Pasientgruppe{

	private Long pasientgruppe_Id;
	/**
	 * MesH term
	 */
	private java.lang.String pasientgruppe;
	/**
	 * Entydig MeSH identifikator
	 */
	private java.lang.String meshindeks;
	
	
	
	public Long getPasientgruppe_Id() {
		return pasientgruppe_Id;
	}
	public void setPasientgruppe_Id(Long pasientgruppe_Id) {
		this.pasientgruppe_Id = pasientgruppe_Id;
	}
	public java.lang.String getPasientgruppe() {
		return pasientgruppe;
	}
	public void setPasientgruppe(java.lang.String pasientgruppe) {
		this.pasientgruppe = pasientgruppe;
	}
	public java.lang.String getMeshindeks() {
		return meshindeks;
	}
	public void setMeshindeks(java.lang.String meshindeks) {
		this.meshindeks = meshindeks;
	}
	
}