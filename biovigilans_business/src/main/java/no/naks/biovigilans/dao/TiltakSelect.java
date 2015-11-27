package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.TiltakImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * TiltakSelect
 * Denne klassen henter inn tiltak som er gjennomført til en pasientmelding
 * @author olj
 *
 */
public class TiltakSelect extends AbstractSelect {

	public TiltakSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Tiltak tiltak = new TiltakImpl();
		tiltak.setTiltakid(new Long(rs.getLong(tableDefs[0])));
		Date tiltakDato = rs.getDate(tableDefs[1]);
		if (tiltakDato != null){
			tiltak.setTiltaksDato(tiltakDato);
		}
		Date gjennomfortDato = rs.getDate(tableDefs[2]);
		if (gjennomfortDato != null){
			tiltak.setGjennomfortDato(gjennomfortDato);
		}
		String beskrivelse = rs.getString(tableDefs[3]);
		if (beskrivelse != null){
			tiltak.setBeskrivelse(beskrivelse);
		}
		tiltak.setPasient_id(new Long(rs.getLong(tableDefs[4])));
		return tiltak;
	}

}
