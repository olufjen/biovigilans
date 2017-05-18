package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.ProduktegenskapImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot tabellen produktegenskap (blod)
 * @author olj
 *
 */
public class ProduktegenskapSelect extends AbstractSelect {

	public ProduktegenskapSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Produktegenskap produktEgenskap = new ProduktegenskapImpl();
		produktEgenskap.setProduktegenskapId(new Long(rs.getLong(tableDefs[0])));
		String kode = rs.getString(tableDefs[1]);
		if (kode != null){
			produktEgenskap.setEgenskapKode(kode);
		}
		String beskrivelse =  rs.getString(tableDefs[2]);
		if (beskrivelse != null){
			produktEgenskap.setEgenskapBeskrivelse(beskrivelse);
		}
		produktEgenskap.setBlodProduktId(rs.getLong(tableDefs[3]));
		return produktEgenskap;
	}

}
