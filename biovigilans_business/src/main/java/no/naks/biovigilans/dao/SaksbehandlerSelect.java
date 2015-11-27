package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.SaksbehandlerImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

public class SaksbehandlerSelect extends AbstractSelect {

	
	public SaksbehandlerSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Saksbehandler saksbehandler = new SaksbehandlerImpl();
		saksbehandler.setSakbehandlerid(new Long(rs.getLong(tableDefs[0])));
		String saknavn = rs.getString(tableDefs[1]);
		if (saknavn != null)
			saksbehandler.setBehandernavn(saknavn);
		String sakepost = rs.getString(tableDefs[2]);
		if (sakepost != null)
			saksbehandler.setBehandlerepost(sakepost);
		String saktlf = rs.getString(tableDefs[3]);
		if (saktlf != null)
			saksbehandler.setBehandlertlf(saktlf);
		String sakpwd = rs.getString(tableDefs[4]);
		if (sakpwd != null)
			saksbehandler.setBehandlerpassord(sakpwd);
		
		return saksbehandler;
	}

}
