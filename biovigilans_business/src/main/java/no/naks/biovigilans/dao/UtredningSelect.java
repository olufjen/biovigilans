package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Utredning;
import no.naks.biovigilans.model.UtredningImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

public class UtredningSelect extends AbstractSelect {

	public UtredningSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Utredning utredning = new UtredningImpl();
		utredning.setUtredningId(new Long(rs.getLong(tableDefs[0])));
		String klassifikasjon = rs.getString(tableDefs[1]);
		if (klassifikasjon != null){
			utredning.setUtredningsklassifikasjon(klassifikasjon);
		}
		String beskrivelse = rs.getString(tableDefs[2]);
		if (beskrivelse != null){
			utredning.setUtredningbeskrivelse(beskrivelse); 
		}	
		utredning.setMeldeId(new Long(rs.getLong(tableDefs[3])));
		return utredning;
	}

}
