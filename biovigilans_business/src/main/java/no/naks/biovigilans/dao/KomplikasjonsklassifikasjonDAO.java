package no.naks.biovigilans.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;

public interface KomplikasjonsklassifikasjonDAO {

	public String getInsertKomplikasjonsklassifikasjonSQL();
	public void setInsertKomplikasjonsklassifikasjonSQL(
			String insertKomplikasjonsklassifikasjonSQL) ;
	public String[] getKomplikasjonsklassifikasjonprimarykeyTableDefs() ;
	public void setKomplikasjonsklassifikasjonprimarykeyTableDefs(
			String[] komplikasjonsklassifikasjonprimarykeyTableDefs) ;
	public String getKomplikasjonsklassifikasjonPrimaryKey() ;
	public void setKomplikasjonsklassifikasjonPrimaryKey(
			String komplikasjonsklassifikasjonPrimaryKey);
	public String getDeleteKomplikasjonsklassifikasjonSQL() ;
	public void setDeleteKomplikasjonsklassifikasjonSQL(
			String deleteKomplikasjonsklassifikasjonSQL) ;
	public void saveKomplikasjonsklassifikasjon(Komplikasjonsklassifikasjon komplikasjonsklassifikasjon);
	public List<Komplikasjonsklassifikasjon> getKomplikasjonsklassifikasjoner();
	public void setKomplikasjonsklassifikasjoner(
			List<Komplikasjonsklassifikasjon> komplikasjonsklassifikasjoner);
	public void setAlternativeSource(JdbcTemplate alternativeSource);
}
