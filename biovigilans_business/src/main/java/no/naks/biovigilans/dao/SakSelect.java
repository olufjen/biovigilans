package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.SakImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

public class SakSelect extends AbstractSelect {

	public SakSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Sak sak = new SakImpl();
		sak.setSaksid(new Long(rs.getLong(tableDefs[0])));
		Date datoSak = rs.getDate(tableDefs[1]);
		sak.setDatoSak(datoSak);
		String sakopplysning = rs.getString(tableDefs[2]);
		if (sakopplysning != null)
			sak.setSaksopplysninger(sakopplysning);
		String sakkommentar = rs.getString(tableDefs[3]);
		if (sakkommentar != null)
			sak.setSakskommentar(sakkommentar);
		String sakstatus = rs.getString(tableDefs[4]);
		if (sakstatus != null)
			sak.setSakstatus(sakstatus);
		sak.setSakbehandlerId(new Long(rs.getLong(tableDefs[6])));
		sak.setDiskusjonid(new Long(rs.getLong(tableDefs[5])));
		return sak;
	}

}
