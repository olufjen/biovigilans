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
 * Denne klassen benyttes til oppslag mot tabellen vigilansmelding
 * @author olj
 *
 */
public class VigilansSelect extends AbstractSelect {

	
	
	public VigilansSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Vigilansmelding melding = new AbstractVigilansmelding();
		melding.setMeldeid(new Long(rs.getLong(tableDefs[0])));
		Date datoHendelse = rs.getDate(tableDefs[1]);
		melding.setDatoforhendelse(datoHendelse);
		Date datoOppdaget =  rs.getDate(tableDefs[2]);
		melding.setDatooppdaget(datoOppdaget);
		Date donasjon = rs.getDate(tableDefs[3]);
		melding.setDonasjonoverforing(donasjon);
		String sjekkliste = rs.getString(tableDefs[4]);
		if (sjekkliste != null)
			melding.setSjekklistesaksbehandling(sjekkliste);
		String supplerende = rs.getString(tableDefs[5]);
		if (supplerende != null)
			melding.setSupplerendeopplysninger(supplerende);
		Date meldingsDato =  rs.getDate(tableDefs[6]);
		melding.setMeldingsdato(meldingsDato);
		String nokkel = rs.getString(tableDefs[7]); 
		melding.setMeldingsnokkel(nokkel);
		melding.setMelderId(new Long(rs.getLong(tableDefs[8])));
		String kladd = rs.getString(tableDefs[9]);
		if (kladd != null)
			melding.setKladd(kladd);
		String godkjent = rs.getString(tableDefs[10]);
		if (godkjent != null)
			melding.setGodkjent(godkjent);
		return melding;
	}

}
