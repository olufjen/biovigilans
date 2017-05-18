package no.naks.biovigilans.dao;

import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.rammeverk.kildelag.dao.AbstractAdmintablesDAO;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;
import no.naks.rammeverk.kildelag.dao.Tablesupdate;

/**
 * Denne DAO tjenesten lagrer informasjon om komplikasjonsdiagnose for givere
 * @author olj
 *
 */
public class KomplikasjonsdiagnosegiverDAOImpl extends AbstractAdmintablesDAO implements
		KomplikasjonsdiagnosegiverDAO {
	
	
	public KomplikasjonsdiagnosegiverDAOImpl(){
		super();
	}
	
	private String insertKomplikasjonsdiagnosegiverSQL;
	private String updateKomplikasjonsdiagnosegiverSQL;
	private String komplikasjonsdiagnosegiverPrimaryKey;
	private String[] komplikasjonsdiagnosegiverprimarykeyTableDefs;
	private Tablesupdate tablesUpdate = null;
	
	public String getInsertKomplikasjonsdiagnosegiverSQL() {
		return insertKomplikasjonsdiagnosegiverSQL;
	}
	public void setInsertKomplikasjonsdiagnosegiverSQL(
			String insertKomplikasjonsdiagnosegiverSQL) {
		this.insertKomplikasjonsdiagnosegiverSQL = insertKomplikasjonsdiagnosegiverSQL;
	}
	public String getUpdateKomplikasjonsdiagnosegiverSQL() {
		return updateKomplikasjonsdiagnosegiverSQL;
	}
	public void setUpdateKomplikasjonsdiagnosegiverSQL(
			String updateKomplikasjonsdiagnosegiverSQL) {
		this.updateKomplikasjonsdiagnosegiverSQL = updateKomplikasjonsdiagnosegiverSQL;
	}
	public String getKomplikasjonsdiagnosegiverPrimaryKey() {
		return komplikasjonsdiagnosegiverPrimaryKey;
	}
	public void setKomplikasjonsdiagnosegiverPrimaryKey(
			String komplikasjonsdiagnosegiverPrimaryKey) {
		this.komplikasjonsdiagnosegiverPrimaryKey = komplikasjonsdiagnosegiverPrimaryKey;
	}
	public String[] getKomplikasjonsdiagnosegiverprimarykeyTableDefs() {
		return komplikasjonsdiagnosegiverprimarykeyTableDefs;
	}
	public void setKomplikasjonsdiagnosegiverprimarykeyTableDefs(
			String[] komplikasjonsdiagnosegiverprimarykeyTableDefs) {
		this.komplikasjonsdiagnosegiverprimarykeyTableDefs = komplikasjonsdiagnosegiverprimarykeyTableDefs;
	}
	

	public void saveKompDiagnosgiverDAO(Komplikasjonsdiagnosegiver komplikasjonsdiagnosegiver ) {
		komplikasjonsdiagnosegiver.setParams();
		int[] types= komplikasjonsdiagnosegiver.getTypes();
		
		String sql=insertKomplikasjonsdiagnosegiverSQL;
		Long id = komplikasjonsdiagnosegiver.getKomlikasjonsdiagnoseId();
		if(id!=null){
			sql = insertKomplikasjonsdiagnosegiverSQL;
			komplikasjonsdiagnosegiver.setKomlikasjonsdiagnoseId(null);
			komplikasjonsdiagnosegiver.setParams();
			id = null;
		}
		Object[] params = komplikasjonsdiagnosegiver.getParams();
		tablesUpdate = new TablesUpdateImpl(getDataSource(), sql, types);
		tablesUpdate.insert(params);
		
		if(id==null){
			komplikasjonsdiagnosegiver.setKomlikasjonsdiagnoseId(getPrimaryKey(komplikasjonsdiagnosegiverPrimaryKey,komplikasjonsdiagnosegiverprimarykeyTableDefs));
		}
		
	}
	
	
}
