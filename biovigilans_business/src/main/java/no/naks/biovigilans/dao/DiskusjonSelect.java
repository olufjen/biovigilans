package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.DiskusjonImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot tabellen diskusjon
 * @author olj
 *
 */
public class DiskusjonSelect extends AbstractSelect {

	
	public DiskusjonSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Diskusjon diskusjon = new DiskusjonImpl();
		diskusjon.setDiskusjonid(new Long(rs.getLong(tableDefs[0])));
		Date datoSak = rs.getDate(tableDefs[1]);
		diskusjon.setDatoforkommentar(datoSak);
		String sakkommentar = rs.getString(tableDefs[2]);
		if (sakkommentar != null)
			diskusjon.setKommentar(sakkommentar);
		String tema = rs.getString(tableDefs[3]);
		if (tema != null)
			diskusjon.setTema(tema);
		diskusjon.setMeldeid(new Long(rs.getLong(tableDefs[4])));
		return diskusjon;
	}

}
