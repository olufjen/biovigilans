package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.PasientkomplikasjonImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot tabellen pasientkomplikasjon
 * @author olj
 *
 */
public class PasientkomplikasjonSelect extends AbstractSelect {

	public PasientkomplikasjonSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Pasientkomplikasjon melding = new PasientkomplikasjonImpl();
		melding.setMeldeid(new Long(rs.getLong(tableDefs[0])));
		melding.setTransfusjonsId(new Long(rs.getLong(tableDefs[1])));
		String klassifikasjon = rs.getString(tableDefs[2]);
		if (klassifikasjon != null)
			melding.setKlassifikasjon(klassifikasjon);
		String tidFra = rs.getString(tableDefs[3]);
		if (tidFra != null){
			int tidsfra = Integer.parseInt(tidFra);
			melding.setTidfrapabegynttrasfusjontilkomplikasjon(tidsfra);
		}
		String alvorlighetsgrad = rs.getString(tableDefs[4]);
		if (alvorlighetsgrad != null)
			melding.setAlvorlighetsgrad(alvorlighetsgrad);
		String kliniskresultat =  rs.getString(tableDefs[5]);
		if (kliniskresultat != null)
			melding.setKliniskresultat(kliniskresultat);
		String arsaksammenheng = rs.getString(tableDefs[6]);
		if (arsaksammenheng != null)
			melding.setArsakssammenheng(arsaksammenheng);
		return melding;
	}

}
