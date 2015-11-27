package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.biovigilans.model.KomplikasjonsdiagnosegiverImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * KomplikasjonsdiagnoseGiverSelect
 * Denne klassen gjør oppslag mot komplikasjonsdiagnoser givere
 * @author olj
 *
 */
public class KomplikasjonsdiagnoseGiverSelect extends AbstractSelect {

	public KomplikasjonsdiagnoseGiverSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Komplikasjonsdiagnosegiver komplikasjonsdiagnose = new KomplikasjonsdiagnosegiverImpl();
		komplikasjonsdiagnose.setKomlikasjonsdiagnoseId(new Long(rs.getLong(tableDefs[0])));
		String lokalskade = rs.getString(tableDefs[1]);
		if (lokalskade != null){
			komplikasjonsdiagnose.setLokalskadearm(lokalskade);
		}
		String systemisk = rs.getString(tableDefs[2]);
		if (systemisk != null){
			komplikasjonsdiagnose.setSystemiskbivirkning(systemisk);
		}	
		String annen = rs.getString(tableDefs[3]);
		if (annen != null){
			komplikasjonsdiagnose.setAnnenreaksjon(annen);
		}
		String lokalskadebesk = rs.getString(tableDefs[4]);
		if (lokalskadebesk != null){
			komplikasjonsdiagnose.setLokalskadebeskrivelse(lokalskadebesk);
		}
		String bivirkning = rs.getString(tableDefs[5]);
		if (bivirkning != null){
			komplikasjonsdiagnose.setBivirkningbeskrivelse(bivirkning);
		}
		String annenbesk = rs.getString(tableDefs[6]);
		if (annenbesk != null){
			komplikasjonsdiagnose.setAnnenreaksjonbeskrivelse(annenbesk);
		}
		String kommentar = rs.getString(tableDefs[7]);
		if (kommentar != null){
			komplikasjonsdiagnose.setKommentar(kommentar);
		}
		komplikasjonsdiagnose.setMeldeId(new Long(rs.getLong(tableDefs[8])));
		return komplikasjonsdiagnose;
	}

}
