package no.naks.biovigilans.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import no.naks.biovigilans.model.Donasjon;

public interface DonasjonDAO {

	public String getInsertDonasjonSQL();
	public String getUpdateDonasjonSQL();
	public void setUpdateDonasjonSQL(String updateDonasjonSQL) ;
	public void setInsertDonasjonSQL(String insertDonasjonSQL);
	public String getDonasjonPrimaryKey() ;
	public void setDonasjonPrimaryKey(String donasjonPrimaryKey) ;
	public String[] getDonasjonprimarykeyTableDefs() ;
	public void setDonasjonprimarykeyTableDefs(String[] donasjonprimarykeyTableDefs) ;
	public void saveDonasjonDAO(Donasjon donasjon);
	public void setAlternativeSource(JdbcTemplate alternativeSource);

}
