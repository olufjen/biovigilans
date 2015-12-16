package no.naks.biovigilans.model;

import no.naks.rammeverk.kildelag.model.AbstractModel;

/**
 * @author olj
 * Regionstatistikk
 * Inneholder antall ikke avviste meldinger pr region
 */
public abstract class AbstractRegionstatistikk extends AbstractModel implements Regionstatistikk{

	private Double antall;
	private String region;
	public Double getAntall() {
		return antall;
	}
	public void setAntall(Double antall) {
		this.antall = antall;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
