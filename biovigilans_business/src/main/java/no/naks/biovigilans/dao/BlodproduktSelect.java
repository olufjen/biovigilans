package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.BlodproduktImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * Denne klassen gjør oppslag mot tabellen blodprodukter
 * @author olj
 *
 */
public class BlodproduktSelect extends AbstractSelect {

	public BlodproduktSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Blodprodukt blodProdukt = new BlodproduktImpl();
		blodProdukt.setBlodProduktId(new Long(rs.getLong(tableDefs[0])));
		String tappetype = rs.getString(tableDefs[1]);
		if (tappetype != null){
			blodProdukt.setTappetype(tappetype);
		}
		String produkt = rs.getString(tableDefs[2]);
		if (produkt != null){
			blodProdukt.setBlodprodukt(produkt);
		}
		String suspensjon = rs.getString(tableDefs[3]);
		if (suspensjon != null){
			blodProdukt.setSuspensjon(suspensjon);
		}	
		blodProdukt.setTransfusjonsId(new Long(rs.getLong(tableDefs[4])));
		return blodProdukt;
	}

}
