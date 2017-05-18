package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Regionstatistikk;
import no.naks.biovigilans.model.RegionstatistikkImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot ulike tabeller som grunnlag for statistikk
 * @author olj
 *
 */
public class StatistikkSelect extends AbstractSelect {

	public StatistikkSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Regionstatistikk statistikk = new RegionstatistikkImpl();
		String region = rs.getString(tableDefs[0]);
		if (region != null)
			statistikk.setRegion(region);
		statistikk.setAntall(rs.getDouble(tableDefs[1]));
		return statistikk;
	}

}

