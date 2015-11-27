package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.GiverImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * GiverSelect
 * Denne klassen gjør oppslag mot givere
 * @author olj
 *
 */
public class GiverSelect extends AbstractSelect {

	public GiverSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Giver giver = new GiverImpl();
		giver.setGiverid(new Long(rs.getLong(tableDefs[0])));
		String kjonn = rs.getString(tableDefs[1]);
		if (kjonn != null){
			giver.setKjonn(kjonn);
		}
		String alder = rs.getString(tableDefs[2]);
		if (alder != null){
			giver.setAlder(alder);
		}
		giver.setVekt(new Long(rs.getLong(tableDefs[3])));
		String erfaring = rs.getString(tableDefs[4]);
		if (erfaring != null){
			giver.setGivererfaring(erfaring);
		}
		String komplikasjon = rs.getString(tableDefs[5]);
		if (komplikasjon != null){
			giver.setTidligerekomlikasjonjanei(komplikasjon);
		}	
		String komperfaring = rs.getString(tableDefs[6]);
		if (komperfaring != null){
			giver.setTidligerekomplikasjonforklaring(komperfaring);
		}
		String erfaringafarese = rs.getString(tableDefs[7]);
		if (erfaringafarese != null){
			giver.setGivererfaringaferese(erfaringafarese);
		}
		return giver;
	}

}
