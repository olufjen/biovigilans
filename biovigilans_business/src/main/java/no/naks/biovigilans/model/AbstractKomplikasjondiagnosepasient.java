package no.naks.biovigilans.model;

import no.naks.rammeverk.kildelag.model.AbstractModel;



/**
 * På bakrunn av alle utredningene kommer man frem til flere diagnoser.
 * 
 */

public abstract class AbstractKomplikasjondiagnosepasient extends AbstractModel implements Komplikasjondiagnosepasient {

	private Long komplikasjonsdiagnoseId;
	/**
	 * Klassifikasjon av komplikasjonsdiagnose, hentes fra AbstractSykdom?
	 */
	private String diagnoseklassifikasjon;
	/**
	 * Beskrivelse av diagnosen.
	 */
	private String diagnosekommentar;
	
	public String getDiagnoseklassifikasjon() {
		return diagnoseklassifikasjon;
	}
	public void setDiagnoseklassifikasjon(String diagnoseklassifikasjon) {
		this.diagnoseklassifikasjon = diagnoseklassifikasjon;
	}
	public String getDiagnosekommentar() {
		return diagnosekommentar;
	}
	public void setDiagnosekommentar(String diagnosekommentar) {
		this.diagnosekommentar = diagnosekommentar;
	}
	public Long getKomplikasjonsdiagnoseId() {
		return komplikasjonsdiagnoseId;
	}
	public void setKomplikasjonsdiagnoseId(Long komplikasjonsdiagnoseId) {
		this.komplikasjonsdiagnoseId = komplikasjonsdiagnoseId;
	}
	
	
	
}