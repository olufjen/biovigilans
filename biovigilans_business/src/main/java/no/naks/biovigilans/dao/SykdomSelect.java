package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.SykdomImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * SykdomSelect
 * Denne klassen gjør oppslag til sykdom tabellen
 * @author olj
 *
 */
public class SykdomSelect extends AbstractSelect {

	public SykdomSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Sykdom sykdom = new SykdomImpl();
		sykdom.setSykdomId(new Long(rs.getLong(tableDefs[0])));
		String sykdomNavn = rs.getString(tableDefs[1]);
		if (sykdomNavn != null){
			sykdom.setSykdomnsnavn(sykdomNavn);
		}
		String symptomer = rs.getString(tableDefs[2]);
		if (symptomer != null){
			sykdom.setSymptomer(symptomer);
		}	
		String diagnose = rs.getString(tableDefs[3]);
		if (diagnose != null){
			sykdom.setDiagnosekode(diagnose);
		}
		sykdom.setPasient_Id(new Long(rs.getLong(tableDefs[4])));
		return sykdom;
	}

}
