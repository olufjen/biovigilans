package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.AnnenkomplikasjonImpl;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

public class AnnenkomplikasjonSelect extends AbstractSelect {

	public AnnenkomplikasjonSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Annenkomplikasjon melding = new AnnenkomplikasjonImpl();
		melding.setMeldeid(new Long(rs.getLong(tableDefs[0])));
		String klassifikasjon = rs.getString(tableDefs[1]);
		if (klassifikasjon != null)
			melding.setKlassifikasjon(klassifikasjon);
		String kode =  rs.getString(tableDefs[2]);
		if (kode != null)
			melding.setKlassifikasjonkode(kode);
		String delkode = rs.getString(tableDefs[3]);
		if (delkode != null)
			melding.setDelkode(delkode);
		String komplikasjonbeskrivelse =  rs.getString(tableDefs[4]);
		if (komplikasjonbeskrivelse != null)
			melding.setKomplikasjonbeskrivelse(komplikasjonbeskrivelse);
		String komplikasjondefinisjon =  rs.getString(tableDefs[5]);
		if (komplikasjondefinisjon != null)
			melding.setKomplikasjondefinisjon(komplikasjondefinisjon);
		String avvikarsak =  rs.getString(tableDefs[6]);
		if (avvikarsak != null)
			melding.setAvvikarsak(avvikarsak);
		String hovedprosess =  rs.getString(tableDefs[7]);
		if (hovedprosess != null)
			melding.setHovedprosess(hovedprosess);
		String tiltak =  rs.getString(tableDefs[8]);
		if (tiltak != null)
			melding.setTiltak(tiltak);
		String kommentar =  rs.getString(tableDefs[9]);
		if (kommentar != null)
			melding.setKommentar(kommentar);
		String oppdaget =  rs.getString(tableDefs[10]);
		if (oppdaget != null)
			melding.setOppdaget(oppdaget);
		String pasientopplysninger =  rs.getString(tableDefs[11]);
		if (pasientopplysninger != null)
			melding.setPasientopplysninger(pasientopplysninger);
		return melding;
	}

}
