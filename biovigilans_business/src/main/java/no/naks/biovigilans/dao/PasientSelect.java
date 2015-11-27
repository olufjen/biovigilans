package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.PasientImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * PasientSelect
 * Denne klassen hente pasientdata fra pasienttabellen
 * @author olj
 *
 */
public class PasientSelect extends AbstractSelect {

	public PasientSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Pasient pasient = new PasientImpl();
		pasient.setPasient_Id(new Long(rs.getLong(tableDefs[0])));
		String kjonn = rs.getString(tableDefs[1]);
		if (kjonn != null){
			pasient.setKjonn(kjonn);
		}
		String aldersgruppe = rs.getString(tableDefs[2]);
		if (aldersgruppe != null){
			pasient.setAldersGruppe(aldersgruppe);
		}	
		String inneliggende  = rs.getString(tableDefs[3]);
		if (inneliggende != null){
			pasient.setInneliggendePoli(inneliggende);;
		}
		String avdeling = rs.getString(tableDefs[4]);
		if (avdeling != null){
			pasient.setAvdeling(avdeling);
		}
		return pasient;
	}

}
