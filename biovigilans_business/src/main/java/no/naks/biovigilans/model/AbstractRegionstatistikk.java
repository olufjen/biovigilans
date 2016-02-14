<<<<<<< HEAD
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
=======
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
>>>>>>> 844f43c43746d2bd69a4b702ffbb85e8783d832b
