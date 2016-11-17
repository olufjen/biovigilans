package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.KomplikasjonsklassifikasjonImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

public class KomplikasjonklassifikasjonSelect extends AbstractSelect {

	public KomplikasjonklassifikasjonSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Komplikasjonsklassifikasjon klassifikasjon = new KomplikasjonsklassifikasjonImpl();
		klassifikasjon.setKlassifikasjonsid(new Long(rs.getLong(tableDefs[0])));
		String klasse = rs.getString(tableDefs[1]);
		if (klasse != null){
			klassifikasjon.setKlassifikasjon(klasse);
		}
		String klasseBeskr = rs.getString(tableDefs[2]);
		if (klasseBeskr != null){
			klassifikasjon.setKlassifikasjonsbeskrivelse(klasseBeskr);
		}
		Long meldeidpas = new Long(rs.getLong(tableDefs[3]));
		if (meldeidpas != null){
			klassifikasjon.setMeldeidpasient(meldeidpas);
		}
		Long meldeidannen = new Long(rs.getLong(tableDefs[4]));
		if (meldeidannen != null){
			klassifikasjon.setMeldeidannen(meldeidannen);
		}
		return klassifikasjon;
	}

}
