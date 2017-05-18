package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot tabellen melder
 * @author olj
 *
 */
public class MelderSelect extends AbstractSelect {

	public MelderSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Melder melder = new MelderImpl();
		melder.setMelderId(new Long(rs.getLong(tableDefs[0])));
		String meldernavn =  rs.getString(tableDefs[1]);
		if (meldernavn != null)
			melder.setMeldernavn(meldernavn);
		String melderepost =  rs.getString(tableDefs[2]);
		if (melderepost != null)
			melder.setMelderepost(melderepost);
		String meldertlf =  rs.getString(tableDefs[3]);
		if (meldertlf != null)
			melder.setMeldertlf(meldertlf);
		String melderpassord =  rs.getString(tableDefs[4]);
		if (melderpassord != null)
			melder.setMelderPassord(melderpassord);
		String helseregion =  rs.getString(tableDefs[5]);
		if (helseregion != null)
			melder.setHelseregion(helseregion);
		String helseforetak =  rs.getString(tableDefs[6]);
		if (helseforetak != null)
			melder.setHelseforetak(helseforetak);
		String sykehus =  rs.getString(tableDefs[7]);
		if (sykehus != null)
			melder.setSykehus(sykehus);
		
		return melder;
	}

}
