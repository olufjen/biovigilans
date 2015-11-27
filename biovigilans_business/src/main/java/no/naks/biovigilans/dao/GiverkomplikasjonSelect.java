package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.GiverkomplikasjonImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * GiverkomplikasjonSelect
 * Denne klassen gjør oppslag mot giverkomplikasjoner
 * @author olj
 *
 */
public class GiverkomplikasjonSelect extends AbstractSelect {

	public GiverkomplikasjonSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Giverkomplikasjon melding = new GiverkomplikasjonImpl();
		melding.setMeldeid(new Long(rs.getLong(tableDefs[0])));
		melding.setDonasjonid(new Long(rs.getLong(tableDefs[1])));
		String stedforkomplikasjon =  rs.getString(tableDefs[2]);
		if (stedforkomplikasjon != null)
			melding.setStedforkomplikasjon(stedforkomplikasjon);
		String tidfraRapp =  rs.getString(tableDefs[3]);
		if (tidfraRapp != null)
			melding.setTidfratappingtilkompliasjon(tidfraRapp);
		String behandlingssted =  rs.getString(tableDefs[4]);
		if (behandlingssted != null)
			melding.setBehandlingssted(behandlingssted);
		String tilleggsopplysninger =  rs.getString(tableDefs[5]);
		if (tilleggsopplysninger != null )
			melding.setTilleggsopplysninger(tilleggsopplysninger);
		String alvorlighetsgrad =  rs.getString(tableDefs[6]);
		if (alvorlighetsgrad != null)
			melding.setAlvorlighetsgrad(alvorlighetsgrad);
		String kliniskresultat =  rs.getString(tableDefs[7]);
		if (kliniskresultat != null)
			melding.setKliniskresultat(kliniskresultat);
		String varighetkomplikasjon =  rs.getString(tableDefs[8]);
		if (varighetkomplikasjon != null)
			melding.setVarighetkomplikasjon(varighetkomplikasjon);
		Date datosymptomer = rs.getDate(tableDefs[9]);
		if (datosymptomer != null)
			melding.setDatosymptomer(datosymptomer);
		return melding;
	}

}
