package no.naks.biovigilans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.DonasjonImpl;
import no.naks.rammeverk.kildelag.dao.AbstractSelect;

public class DonasjonSelect extends AbstractSelect {

	public DonasjonSelect(DataSource dataSource, String sql, String[] tableDefs) {
		super(dataSource, sql, tableDefs);
		
	}

	@Override
	protected Object mapRow(ResultSet rs, int index) throws SQLException {
		Donasjon donasjon = new DonasjonImpl();
		donasjon.setDonasjonsId(new Long(rs.getLong(tableDefs[0])));
		String sted = rs.getString(tableDefs[1]);
		if (sted != null){
			donasjon.setDonasjonssted(sted);
		}
		String punksjon = rs.getString(tableDefs[2]);
		if (punksjon != null){
			donasjon.setKomplisertvenepunksjon(punksjon);
		}
		String tappetype = rs.getString(tableDefs[3]);
		if (tappetype != null){
			donasjon.setTappetype(tappetype);
		}
		String tappevarighet = rs.getString(tableDefs[4]);
		if (tappevarighet != null){
			donasjon.setTappevarighet(tappevarighet);
		}
		String lokalpunksjon = rs.getString(tableDefs[5]);
		if (lokalpunksjon != null){
			donasjon.setLokalisasjonvenepunksjon(lokalpunksjon);
		}
		String maltid = rs.getString(tableDefs[6]);
		if (maltid != null){
			donasjon.setMaltidfortapping(maltid);
		}
		donasjon.setGiveId(rs.getInt(tableDefs[7]));
		Date donasjondato =  rs.getDate(tableDefs[8]);
		if (donasjondato != null){
			donasjon.setDonasjonsdato(donasjondato);
		}
		return donasjon;
	}

}
