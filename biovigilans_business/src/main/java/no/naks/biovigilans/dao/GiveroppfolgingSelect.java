package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.GiveroppfolgingImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * GiveroppfolgingSelect
 * Denne klassen gjør oppslag mot giveroppfolginger
 * @author olj
 *
 */
public class GiveroppfolgingSelect extends AbstractSelect {

	public GiveroppfolgingSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Giveroppfolging giverOppfolging = new GiveroppfolgingImpl();
		giverOppfolging.setGiveroppfolgingId(new Long(rs.getLong(tableDefs[0])));
		String klassifikasjon = rs.getString(tableDefs[1]);
		if (klassifikasjon != null){
			giverOppfolging.setKlassifikasjongiveroppfolging(klassifikasjon);
		}
		String beskrivelse = rs.getString(tableDefs[2]);
		if (beskrivelse != null){
			giverOppfolging.setGiveroppfolgingbeskrivelse(beskrivelse);
		}	
		String avregistrering = rs.getString(tableDefs[3]);
		if (avregistrering != null){
			giverOppfolging.setAvregistering(avregistrering);
		}	
		String strakstiltak = rs.getString(tableDefs[4]);
		if (strakstiltak != null){
			giverOppfolging.setStrakstiltak(strakstiltak);
		}
		String videreoppfolging = rs.getString(tableDefs[5]);
		if (videreoppfolging != null){
			giverOppfolging.setVidereoppfolging(videreoppfolging);
		}
		giverOppfolging.setMeldeid(new Long(rs.getLong(tableDefs[6])));
		return giverOppfolging;
	}

}
