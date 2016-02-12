package no.naks.biovigilans.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.SqlParameter;

import no.naks.biovigilans.model.Regionstatistikk;
import no.naks.rammeverk.kildelag.dao.AbstractAdmintablesDAO;

/**
 * Denne klassen benyttes til å produsere ulike typer statistikk
 * (unntatt statistikk for regioner, som finnes i SakDAO)
 * 
 * @author olj
 *
 */
public class StatistikkDAOImpl extends AbstractAdmintablesDAO implements StatistikkDAO {
	private String statistikkregionSQL;
	private String statistikksykehusSQL;
	private String[] statistikkTableDefs;
	private String statistikkregionperiodeSQL;
	private String statistikksykehusperiodeSQL;
	private String statistikkregionhentperiodeSQL;
	private String statistikksykehushentperiodeSQL;

	
	public String getStatistikkregionperiodeSQL() {
		return statistikkregionperiodeSQL;
	}
	public void setStatistikkregionperiodeSQL(String statistikkregionperiodeSQL) {
		this.statistikkregionperiodeSQL = statistikkregionperiodeSQL;
	}
	public String getStatistikksykehusperiodeSQL() {
		return statistikksykehusperiodeSQL;
	}
	public void setStatistikksykehusperiodeSQL(String statistikksykehusperiodeSQL) {
		this.statistikksykehusperiodeSQL = statistikksykehusperiodeSQL;
	}
	public String getStatistikkregionhentperiodeSQL() {
		return statistikkregionhentperiodeSQL;
	}
	public void setStatistikkregionhentperiodeSQL(
			String statistikkregionhentperiodeSQL) {
		this.statistikkregionhentperiodeSQL = statistikkregionhentperiodeSQL;
	}
	public String getStatistikksykehushentperiodeSQL() {
		return statistikksykehushentperiodeSQL;
	}
	public void setStatistikksykehushentperiodeSQL(
			String statistikksykehushentperiodeSQL) {
		this.statistikksykehushentperiodeSQL = statistikksykehushentperiodeSQL;
	}
	public String getStatistikksykehusSQL() {
		return statistikksykehusSQL;
	}
	public void setStatistikksykehusSQL(String statistikksykehusSQL) {
		this.statistikksykehusSQL = statistikksykehusSQL;
	}
	public String getStatistikkregionSQL() {
		return statistikkregionSQL;
	}
	public void setStatistikkregionSQL(String statistikkregionSQL) {
		this.statistikkregionSQL = statistikkregionSQL;
	}
	public String[] getStatistikkTableDefs() {
		return statistikkTableDefs;
	}
	public void setStatistikkTableDefs(String[] statistikkTableDefs) {
		this.statistikkTableDefs = statistikkTableDefs;
	} 
	/**
	 * collectRegionstatistikk
	 * Denne rutinen henter statistikk pr foretak eller pr sykehus
	 * @param reg angir sykehus eller foretak
	 * @return
	 */
	public List<Regionstatistikk> collectRegionstatistikk(String reg){
		String sql = statistikksykehusSQL;
		List statistikk = new ArrayList<Regionstatistikk>();
		if (reg.equals("foretak"))
			sql = statistikkregionSQL;
		StatistikkSelect regionSelect = new StatistikkSelect(getDataSource(),sql,statistikkTableDefs);
		statistikk = regionSelect.execute();
		return statistikk;
	}
	public List<Regionstatistikk> collectRegionstatistikk(String startperiod,String endperiod,String type){
		List statistikk = new ArrayList<Regionstatistikk>();
		int stype = Types.DATE;
		int etype = Types.DATE;
		SqlParameter param = new SqlParameter(stype);
		SqlParameter param1 = new SqlParameter(etype);
		String sql = statistikkregionhentperiodeSQL;
		if (type.equals("meldt"))
			sql = statistikkregionperiodeSQL;
		StatistikkSelect regionSelect = new StatistikkSelect(getDataSource(),sql,statistikkTableDefs);
		regionSelect.declareParameter(param);
		regionSelect.declareParameter(param1);
		statistikk = regionSelect.execute(startperiod,endperiod);
		return statistikk;
	}
	public List<Regionstatistikk> collectsykehusstatistikk(String startperiod,String endperiod,String type){
		List statistikk = new ArrayList<Regionstatistikk>();
		int stype = Types.DATE;
		int etype = Types.DATE;
		SqlParameter param = new SqlParameter(stype);
		SqlParameter param1 = new SqlParameter(etype);
		String sql = statistikksykehushentperiodeSQL;
		if (type.equals("meldt"))
			sql = statistikksykehusperiodeSQL;
		StatistikkSelect regionSelect = new StatistikkSelect(getDataSource(),sql,statistikkTableDefs);
		regionSelect.declareParameter(param);
		regionSelect.declareParameter(param1);
		statistikk = regionSelect.execute(startperiod,endperiod);
		return statistikk;
	}
}
