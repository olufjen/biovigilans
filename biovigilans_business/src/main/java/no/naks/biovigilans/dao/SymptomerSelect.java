package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.SymptomerImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot tabellen symptomer
 * @author olj
 *
 */
public class SymptomerSelect extends AbstractSelect {

	public SymptomerSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Symptomer symptomer = new SymptomerImpl();
		symptomer.setSymptomId(new Long(rs.getLong(tableDefs[0])));
		String klasse = rs.getString(tableDefs[1]);
		if (klasse != null){
			symptomer.setSymptomklassifikasjon(klasse);
		}
		String beskrivelse = rs.getString(tableDefs[2]);
		if (beskrivelse != null){
			symptomer.setSymptombeskrivelse(beskrivelse);
		}
		Double tempfor = rs.getDouble(tableDefs[3]);
		if (tempfor != null){
			symptomer.setTempFor(tempfor.doubleValue());
			String tempFors = String.valueOf(tempfor.doubleValue());
			symptomer.setTempFors(tempFors);
		}
		Double tempetter = rs.getDouble(tableDefs[4]);
		if (tempetter != null){
			symptomer.setTempetter(tempetter.doubleValue());
			String tempEtters = String.valueOf(tempetter.doubleValue());
			symptomer.setTempEtters(tempEtters);
		}
		symptomer.setMeldeId(new Long(rs.getLong(tableDefs[5])));
		return symptomer;
	}

}
