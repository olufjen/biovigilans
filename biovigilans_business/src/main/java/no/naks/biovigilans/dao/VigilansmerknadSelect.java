package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.AbstractVigilansmelding;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * VegilansSelect
 * Denne klassen benyttes til oppslag mot tabellen vigilansmelding samt tabellene diskusjon og sak
 * Resultatet av oppslaget viser saksbehandler for meldingen
 * @author olj
 *
 */
public class VigilansmerknadSelect extends AbstractSelect {

	
	
	public VigilansmerknadSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Vigilansmelding melding = new AbstractVigilansmelding();
		melding.setMeldeid(new Long(rs.getLong(tableDefs[0])));
		String nokkel = rs.getString(tableDefs[1]); 
		Date datoHendelse = rs.getDate(tableDefs[4]);
		melding.setMeldingsdato(datoHendelse);
		melding.setMeldingsnokkel(nokkel);
		String sjekkliste = rs.getString(tableDefs[2]);
		if (sjekkliste != null)
			melding.setSjekklistesaksbehandling(sjekkliste);
		String saksbehandler = rs.getString(tableDefs[3]);
		if (saksbehandler != null)
			melding.setSaksBehandler(saksbehandler);
		return melding;
	}

}
