package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.TransfusjonImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

/**
 * TransfusjonSelect
 * Denne klassen gjør oppslag mot transfusjonstabellen
 * @author olj
 *
 */
public class TransfusjonSelect extends AbstractSelect {

	public TransfusjonSelect(DataSource dataSource, String sql,
			String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Transfusjon transfusjon = new TransfusjonImpl();
		transfusjon.setTransfusjonsId(new Long(rs.getLong(tableDefs[0])));
		Date transDato = rs.getDate(tableDefs[1]);
		if (transDato != null){
			transfusjon.setTransfusionDate(transDato);
		}
		String hastegrad = rs.getString(tableDefs[2]);
		if (hastegrad != null){
			transfusjon.setHastegrad(hastegrad);
		}
		String feiltrans = rs.getString(tableDefs[3]);
		if (feiltrans != null){
			transfusjon.setFeiltranfudert(feiltrans);
		}	
		String indikasjon = rs.getString(tableDefs[4]);
		if (indikasjon != null){
			transfusjon.setIndikasjon(indikasjon);
		}	
		String tidligerkompl = rs.getString(tableDefs[5]);
		if (tidligerkompl != null){
			transfusjon.setTildigerKomplikasjon(tidligerkompl);
		}
		transfusjon.setPasient_Id(new Long(rs.getLong(tableDefs[6])));
		return transfusjon;
	}

}
