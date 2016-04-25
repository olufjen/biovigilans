package no.naks.biovigilans.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;

public interface KomplikasjonsdiagnosegiverDAO {
	public String getInsertKomplikasjonsdiagnosegiverSQL();
	public void setInsertKomplikasjonsdiagnosegiverSQL(
			String insertKomplikasjonsdiagnosegiverSQL) ;
	public String getUpdateKomplikasjonsdiagnosegiverSQL() ;
	public void setUpdateKomplikasjonsdiagnosegiverSQL(
			String updateKomplikasjonsdiagnosegiverSQL) ;
	public String getKomplikasjonsdiagnosegiverPrimaryKey() ;
	public void setKomplikasjonsdiagnosegiverPrimaryKey(
			String komplikasjonsdiagnosegiverPrimaryKey) ;
	public String[] getKomplikasjonsdiagnosegiverprimarykeyTableDefs() ;
	public void setKomplikasjonsdiagnosegiverprimarykeyTableDefs(
			String[] komplikasjonsdiagnosegiverprimarykeyTableDefs);
	
	public void saveKompDiagnosgiverDAO(Komplikasjonsdiagnosegiver Komplikasjonsdiagnosegiver );
	public void setAlternativeSource(JdbcTemplate alternativeSource);
}
