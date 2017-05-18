package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.ForebyggendetiltakImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot tabellen forebyggende tiltak
 * @author olj
 *
 */
public class ForebyggendetiltakSelect extends AbstractSelect {

	public ForebyggendetiltakSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Forebyggendetiltak tiltak = new ForebyggendetiltakImpl();
	
		tiltak.setForebyggendetiltakid(new Long(rs.getLong(tableDefs[0])));
		String valg = rs.getString(tableDefs[1]);
		if (valg != null){
			tiltak.setTiltakvalg(valg);
		}
		String beskrivelse = rs.getString(tableDefs[2]);
		if (beskrivelse != null){
			tiltak.setTiltakbeskrivelse(beskrivelse);
		}
		
		tiltak.setTiltakid(new Long(rs.getLong(tableDefs[3])));
		return tiltak;
	}

}
