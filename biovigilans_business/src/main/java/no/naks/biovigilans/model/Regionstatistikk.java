package no.naks.biovigilans.model;

/**
 * @author olj
 * Representerer klassen Regionstatistikk
 */
public interface Regionstatistikk {
	public void setAntall(Double antall);
	public Double getAntall();
	public String getRegion();
	public void setRegion(String region);
	
}
