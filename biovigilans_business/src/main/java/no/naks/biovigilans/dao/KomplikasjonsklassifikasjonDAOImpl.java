package no.naks.biovigilans.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.sparql.engine.http.Params;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.KomplikasjonsklassifikasjonImpl;
import no.naks.rammeverk.kildelag.dao.AbstractAdmintablesDAO;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;
import no.naks.rammeverk.kildelag.dao.Tablesupdate;

public class KomplikasjonsklassifikasjonDAOImpl extends AbstractAdmintablesDAO implements KomplikasjonsklassifikasjonDAO {
	
	private String insertKomplikasjonsklassifikasjonSQL;
	private String[] komplikasjonsklassifikasjonprimarykeyTableDefs;
	private String komplikasjonsklassifikasjonPrimaryKey;
	private String deleteKomplikasjonsklassifikasjonSQL;
	private Tablesupdate tablesUpdate = null;
	
	private List<Komplikasjonsklassifikasjon> komplikasjonsklassifikasjoner = null;
	
	
	public List<Komplikasjonsklassifikasjon> getKomplikasjonsklassifikasjoner() {
		return komplikasjonsklassifikasjoner;
	}
	public void setKomplikasjonsklassifikasjoner(
			List<Komplikasjonsklassifikasjon> komplikasjonsklassifikasjoner) {
		this.komplikasjonsklassifikasjoner = komplikasjonsklassifikasjoner;
	}
	public String getInsertKomplikasjonsklassifikasjonSQL() {
		return insertKomplikasjonsklassifikasjonSQL;
	}
	public void setInsertKomplikasjonsklassifikasjonSQL(
			String insertKomplikasjonsklassifikasjonSQL) {
		this.insertKomplikasjonsklassifikasjonSQL = insertKomplikasjonsklassifikasjonSQL;
	}
	public String[] getKomplikasjonsklassifikasjonprimarykeyTableDefs() {
		return komplikasjonsklassifikasjonprimarykeyTableDefs;
	}
	public void setKomplikasjonsklassifikasjonprimarykeyTableDefs(
			String[] komplikasjonsklassifikasjonprimarykeyTableDefs) {
		this.komplikasjonsklassifikasjonprimarykeyTableDefs = komplikasjonsklassifikasjonprimarykeyTableDefs;
	}
	public String getKomplikasjonsklassifikasjonPrimaryKey() {
		return komplikasjonsklassifikasjonPrimaryKey;
	}
	public void setKomplikasjonsklassifikasjonPrimaryKey(
			String komplikasjonsklassifikasjonPrimaryKey) {
		this.komplikasjonsklassifikasjonPrimaryKey = komplikasjonsklassifikasjonPrimaryKey;
	}
	
	public String getDeleteKomplikasjonsklassifikasjonSQL() {
		return deleteKomplikasjonsklassifikasjonSQL;
	}
	public void setDeleteKomplikasjonsklassifikasjonSQL(
			String deleteKomplikasjonsklassifikasjonSQL) {
		this.deleteKomplikasjonsklassifikasjonSQL = deleteKomplikasjonsklassifikasjonSQL;
	}
	public void saveKomplikasjonsklassifikasjon(Komplikasjonsklassifikasjon komplikasjonsklassifikasjon){
		String sql = "";
/*	Fjernet - OLJ 19.06.15	
		String sql =deleteKomplikasjonsklassifikasjonSQL;
		Long meldeAnnenId = komplikasjonsklassifikasjon.getMeldeidannen();
		int[] deleteType = new int[]{Types.INTEGER};
		tablesUpdate = new TablesUpdateImpl(getDataSource(),sql,deleteType);
		Object[] deleteParams = new Object[]{meldeAnnenId};
		tablesUpdate.insert(deleteParams);
	*/	
		List<String> klassifikasjonList =  komplikasjonsklassifikasjon.getKlassifikasjonList();
		komplikasjonsklassifikasjoner = new ArrayList();
		
		for(String klassifikasjon:klassifikasjonList){
			komplikasjonsklassifikasjon.setKlassifikasjon(klassifikasjon);
			komplikasjonsklassifikasjon.setKlassifikasjonsbeskrivelse(klassifikasjon);
			Komplikasjonsklassifikasjon komplikasjon = new KomplikasjonsklassifikasjonImpl();
			komplikasjon.setKlassifikasjon(klassifikasjon);
			komplikasjon.setKlassifikasjonsbeskrivelse(klassifikasjon);
			komplikasjonsklassifikasjoner.add(komplikasjon);
			komplikasjonsklassifikasjon.setParams();
			int[] types = komplikasjonsklassifikasjon.getTypes();
			Object[] params = komplikasjonsklassifikasjon.getParams();
			sql = insertKomplikasjonsklassifikasjonSQL;
			tablesUpdate = new TablesUpdateImpl(getDataSource(),sql,types);
			tablesUpdate.insert(params);
		}	
	}
	
	

}
