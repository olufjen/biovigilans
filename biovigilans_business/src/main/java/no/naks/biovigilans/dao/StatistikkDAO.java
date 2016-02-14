<<<<<<< HEAD
package no.naks.biovigilans.dao;

import java.util.List;

import no.naks.biovigilans.model.Regionstatistikk;

/**
 * Dette grensesnittet benyttes til å produsere ulike typer statistikk
 * (unntatt statistikk for regioner, som finnes i SakDAO)
 * 
 * @author olj
 *
 */
public interface StatistikkDAO {
	public String getStatistikkregionSQL();
	public void setStatistikkregionSQL(String statistikkregionSQL);
	public String[] getStatistikkTableDefs();
	public void setStatistikkTableDefs(String[] statistikkTableDefs); 
	
	public String getStatistikksykehusSQL();
	public void setStatistikksykehusSQL(String statistikksykehusSQL);
	public List<Regionstatistikk> collectRegionstatistikk(String reg);
	public String getStatistikkregionperiodeSQL();
	public void setStatistikkregionperiodeSQL(String statistikkregionperiodeSQL);
	public String getStatistikksykehusperiodeSQL();
	public void setStatistikksykehusperiodeSQL(String statistikksykehusperiodeSQL);
	public String getStatistikkregionhentperiodeSQL();
	public void setStatistikkregionhentperiodeSQL(
			String statistikkregionhentperiodeSQL);
	public String getStatistikksykehushentperiodeSQL();
	public void setStatistikksykehushentperiodeSQL(
			String statistikksykehushentperiodeSQL);
	public List<Regionstatistikk> collectRegionstatistikk(String startperiod,String endperiod,String type);
	public List<Regionstatistikk> collectsykehusstatistikk(String startperiod,String endperiod,String type);

}
=======
package no.naks.biovigilans.dao;

import java.util.List;

import no.naks.biovigilans.model.Regionstatistikk;

/**
 * Dette grensesnittet benyttes til å produsere ulike typer statistikk
 * (unntatt statistikk for regioner, som finnes i SakDAO)
 * 
 * @author olj
 *
 */
public interface StatistikkDAO {
	public String getStatistikkregionSQL();
	public void setStatistikkregionSQL(String statistikkregionSQL);
	public String[] getStatistikkTableDefs();
	public void setStatistikkTableDefs(String[] statistikkTableDefs); 
	
	public String getStatistikksykehusSQL();
	public void setStatistikksykehusSQL(String statistikksykehusSQL);
	public List<Regionstatistikk> collectRegionstatistikk(String reg);
	public String getStatistikkregionperiodeSQL();
	public void setStatistikkregionperiodeSQL(String statistikkregionperiodeSQL);
	public String getStatistikksykehusperiodeSQL();
	public void setStatistikksykehusperiodeSQL(String statistikksykehusperiodeSQL);
	public String getStatistikkregionhentperiodeSQL();
	public void setStatistikkregionhentperiodeSQL(
			String statistikkregionhentperiodeSQL);
	public String getStatistikksykehushentperiodeSQL();
	public void setStatistikksykehushentperiodeSQL(
			String statistikksykehushentperiodeSQL);
	public List<Regionstatistikk> collectRegionstatistikk(String startperiod,String endperiod,String type);
	public List<Regionstatistikk> collectsykehusstatistikk(String startperiod,String endperiod,String type);

}
>>>>>>> 844f43c43746d2bd69a4b702ffbb85e8783d832b
