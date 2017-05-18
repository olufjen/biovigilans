package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.AnnenkomplikasjonImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot tabellen andrehendelser
 * @author olj
 *
 */
public class AnnenmeldingSelect extends AbstractSelect {

	public AnnenmeldingSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		if (rs != null){
			Annenkomplikasjon melding = new AnnenkomplikasjonImpl();
			melding.setMeldeid(new Long(rs.getLong(tableDefs[0])));
			String klassifikasjon = rs.getString(tableDefs[1]);
			if (klassifikasjon != null){
				melding.setKlassifikasjon(klassifikasjon);
			}
			String klassifikasjonKode = rs.getString(tableDefs[2]);
			if (klassifikasjonKode != null)
				melding.setKlassifikasjonkode(klassifikasjonKode);
			String delkode = rs.getString(tableDefs[3]);
			if (delkode != null)
				melding.setDelkode(delkode);
			String komplikasjon = rs.getString(tableDefs[4]);
			if (komplikasjon != null)
				melding.setKomplikasjonbeskrivelse(komplikasjon);
			String komplikasjonDef = rs.getString(tableDefs[5]);
			if (komplikasjonDef != null)
				melding.setKomplikasjondefinisjon(komplikasjonDef);
			String avvikarsak = rs.getString(tableDefs[6]);
			if (avvikarsak != null)
				melding.setAvvikarsak(avvikarsak);
			String hovedprosess = rs.getString(tableDefs[7]);
			if (hovedprosess != null)
				melding.setHovedprosess(hovedprosess);
			String tiltak = rs.getString(tableDefs[8]);
			if (tiltak != null)
				melding.setTiltak(tiltak);
			String kommentar = rs.getString(tableDefs[9]);
			if (kommentar != null)
				melding.setKommentar(kommentar);
			String oppdaget = rs.getString(tableDefs[10]);
			if (oppdaget != null)
				melding.setOppdaget(oppdaget);
			String pasientopplysninger =  rs.getString(tableDefs[11]);
			if (pasientopplysninger != null)
				melding.setPasientopplysninger(pasientopplysninger);
			return melding;
		}
		return null;
	}

}
