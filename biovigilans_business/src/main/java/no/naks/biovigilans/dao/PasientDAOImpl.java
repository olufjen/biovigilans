package no.naks.biovigilans.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.SqlParameter;

import no.naks.biovigilans.model.Antistoff;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.Tiltak;
import no.naks.rammeverk.kildelag.dao.AbstractAdmintablesDAO;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;
import no.naks.rammeverk.kildelag.dao.Tablesupdate;

/**
 * Denne klassen lagrer pasientopplysninger og relaterte tabeller til databasen
 *  og henter pasientopplysninger fra databasen
 * 
 * @author olj
 *
 */
public class PasientDAOImpl extends AbstractAdmintablesDAO implements
		PasientDAO {

	private Tablesupdate tablesUpdate = null;
	private String insertPatientSQL;
	private String updatePatientSQL;
	private String pasientPrimaryKey;
	
	private String[] patienttableDefs;
	private String[] pasientprimarykeyTableDefs;
	
	private String insertsykdomSQL;
	private String updatesykdomSQL;
	private String[] sykdomTableDefs;
	private String insertAntistoffSQL;
	private String updateAntistoffSQL;
	private String[] antistoffTableDefs;
	
	private String insertTiltakSQL;
	private String updateTiltakSQL;
	private String[] tiltakTableDefs;
	private String insertForebyggendeSQL;
	private String updateForebyggendeSQL;
	private String[] forebyggendeTableDefs;
	private String tiltakPrimarykey;
	
	private List<Tiltak> tiltakene = null;
	private List<Forebyggendetiltak> forebyggendeListe = null;
	
	
	
	public List<Tiltak> getTiltakene() {
		return tiltakene;
	}
	public void setTiltakene(List<Tiltak> tiltakene) {
		this.tiltakene = tiltakene;
	}
	public List<Forebyggendetiltak> getForebyggendeListe() {
		return forebyggendeListe;
	}
	public void setForebyggendeListe(List<Forebyggendetiltak> forebyggendeListe) {
		this.forebyggendeListe = forebyggendeListe;
	}
	public String getTiltakPrimarykey() {
		return tiltakPrimarykey;
	}
	public void setTiltakPrimarykey(String tiltakPrimarykey) {
		this.tiltakPrimarykey = tiltakPrimarykey;
	}
	public String getInsertForebyggendeSQL() {
		return insertForebyggendeSQL;
	}
	public void setInsertForebyggendeSQL(String insertForebyggendeSQL) {
		this.insertForebyggendeSQL = insertForebyggendeSQL;
	}
	public String getInsertTiltakSQL() {
		return insertTiltakSQL;
	}
	public void setInsertTiltakSQL(String insertTiltakSQL) {
		this.insertTiltakSQL = insertTiltakSQL;
	}
	public String getUpdateTiltakSQL() {
		return updateTiltakSQL;
	}
	public void setUpdateTiltakSQL(String updateTiltakSQL) {
		this.updateTiltakSQL = updateTiltakSQL;
	}
	public String[] getTiltakTableDefs() {
		return tiltakTableDefs;
	}
	public void setTiltakTableDefs(String[] tiltakTableDefs) {
		this.tiltakTableDefs = tiltakTableDefs;
	}
	public String getUpdateForebyggendeSQL() {
		return updateForebyggendeSQL;
	}
	public void setUpdateForebyggendeSQL(String updateForebyggendeSQL) {
		this.updateForebyggendeSQL = updateForebyggendeSQL;
	}
	public String[] getForebyggendeTableDefs() {
		return forebyggendeTableDefs;
	}
	public void setForebyggendeTableDefs(String[] forebyggendeTableDefs) {
		this.forebyggendeTableDefs = forebyggendeTableDefs;
	}
	public String getInsertPatientSQL() {
		return insertPatientSQL;
	}
	public void setInsertPatientSQL(String insertPatientSQL) {
		this.insertPatientSQL = insertPatientSQL;
	}
	public String[] getPatienttableDefs() {
		return patienttableDefs;
	}
	public void setPatienttableDefs(String[] patienttableDefs) {
		this.patienttableDefs = patienttableDefs;
	}
	
	public String getUpdatePatientSQL() {
		return updatePatientSQL;
	}
	public void setUpdatePatientSQL(String updatePatientSQL) {
		this.updatePatientSQL = updatePatientSQL;
	}
	
	public String getInsertAntistoffSQL() {
		return insertAntistoffSQL;
	}
	public void setInsertAntistoffSQL(String insertAntistoffSQL) {
		this.insertAntistoffSQL = insertAntistoffSQL;
	}
	public String getUpdateAntistoffSQL() {
		return updateAntistoffSQL;
	}
	public void setUpdateAntistoffSQL(String updateAntistoffSQL) {
		this.updateAntistoffSQL = updateAntistoffSQL;
	}
	public String[] getAntistoffTableDefs() {
		return antistoffTableDefs;
	}
	public void setAntistoffTableDefs(String[] antistoffTableDefs) {
		this.antistoffTableDefs = antistoffTableDefs;
	}
	public String getPasientPrimaryKey() {
		return pasientPrimaryKey;
	}
	public void setPasientPrimaryKey(String pasientPrimaryKey) {
		this.pasientPrimaryKey = pasientPrimaryKey;
	}
	public String[] getPasientprimarykeyTableDefs() {
		return pasientprimarykeyTableDefs;
	}
	public void setPasientprimarykeyTableDefs(String[] pasientprimarykeyTableDefs) {
		this.pasientprimarykeyTableDefs = pasientprimarykeyTableDefs;
	}

	
	public String getInsertsykdomSQL() {
		return insertsykdomSQL;
	}
	public void setInsertsykdomSQL(String insertsykdomSQL) {
		this.insertsykdomSQL = insertsykdomSQL;
	}
	public String getUpdatesykdomSQL() {
		return updatesykdomSQL;
	}
	public void setUpdatesykdomSQL(String updatesykdomSQL) {
		this.updatesykdomSQL = updatesykdomSQL;
	}
	public String[] getSykdomTableDefs() {
		return sykdomTableDefs;
	}
	public void setSykdomTableDefs(String[] sykdomTableDefs) {
		this.sykdomTableDefs = sykdomTableDefs;
	}
	/* savePasient
	 * Denne rutinen lagrer informasjon om pasient
	 * @see no.naks.biovigilans.dao.PasientDAO#savePasient(no.naks.biovigilans.model.Pasient)
	 */
	public void savePasient(Pasient pasient){
		
		pasient.setParams();
		int[] types = pasient.getTypes();
		String sql = insertPatientSQL;
		Long id = pasient.getPasient_Id();
		if (id != null){
			sql = insertPatientSQL;
			pasient.setPasient_Id(null);
			pasient.setParams();
			id = null;
			//types = pasient.getUtypes();
		}
		Object[] params = pasient.getParams();
		tablesUpdate = new TablesUpdateImpl(getDataSource(),sql,types);
		tablesUpdate.insert(params);
		if (id == null){
			pasient.setPasient_Id(getPrimaryKey(pasientPrimaryKey,pasientprimarykeyTableDefs));
		}
//		tablesUpdate = null;
		
		Iterator iterator = pasient.getSykdommer().keySet().iterator();
		while (iterator.hasNext()){
			String key =(String) iterator.next();
			Sykdom sykdom = (Sykdom)pasient.getSykdommer().get(key);
			Long sId = sykdom.getSykdomId();
			if (sId != null){
				sykdom.setSykdomId(null);
				sId = null;
			}
			sykdom.setPasient_Id(pasient.getPasient_Id());
			sykdom.setParams();
			int[] sTypes = sykdom.getTypes();
			Object[] sParams = sykdom.getParams();
			String ssSQL = insertsykdomSQL;

			TablesUpdateImpl sykdomtablesUpdate = new TablesUpdateImpl(getDataSource(),ssSQL,sTypes);
			sykdomtablesUpdate.insert(sParams);
			sykdomtablesUpdate= null;
		}
		Iterator antiIterator = pasient.getAntistoffer().keySet().iterator();
		while (antiIterator.hasNext()){
			String key =(String) antiIterator.next();
			Antistoff antistoff = (Antistoff)pasient.getAntistoffer().get(key);
			antistoff.setPasient_Id(pasient.getPasient_Id());
			antistoff.setParams();
			int[] aTypes = antistoff.getTypes();
			Object[] aParams = antistoff.getParams();
			Long aId = antistoff.getAntistoffId();
			String aSQL = insertAntistoffSQL;
			if (aId != null){
				aSQL = updateAntistoffSQL;
				aTypes = antistoff.getUtypes();
			}
			TablesUpdateImpl antistoffTablesUpdate = new TablesUpdateImpl(getDataSource(), aSQL,aTypes);
			antistoffTablesUpdate.insert(aParams);
			antistoffTablesUpdate = null;
		}
		saveTiltak(pasient);
	}
	private void saveTiltak(Pasient pasient){
		Iterator tiltakIterator = pasient.getAlleTiltak().keySet().iterator();
		tiltakene = new ArrayList();
		forebyggendeListe = new ArrayList();
		while (tiltakIterator.hasNext()){
			String key = (String) tiltakIterator.next();
			Tiltak tiltak = (Tiltak)pasient.getAlleTiltak().get(key);
			tiltak.setPasient_id(pasient.getPasient_Id());
			tiltakene.add(tiltak);
			tiltak.setParams();
			int[] tTypes = tiltak.getTypes();
			Object[] tParams = tiltak.getParams();
			Long tId = tiltak.getTiltakid();
			String tSQL = insertTiltakSQL;
			if (tId != null){
				tSQL = updateTiltakSQL;
				tTypes =tiltak.getUtypes();
			}
			TablesUpdateImpl tiltakTableUpdate = new TablesUpdateImpl(getDataSource(), tSQL,tTypes);
			tiltakTableUpdate.insert(tParams);
			if (tId == null){
				tiltak.setTiltakid(getPrimaryKey(tiltakPrimarykey,pasientprimarykeyTableDefs));
			}
			tiltakTableUpdate = null;
			Iterator alleForebyggende = tiltak.getAlleforebyggendeTiltak().keySet().iterator();
			while (alleForebyggende.hasNext()){
				String fKey = (String) alleForebyggende.next();
				Forebyggendetiltak forebyggende = (Forebyggendetiltak) tiltak.getAlleforebyggendeTiltak().get(fKey);
				forebyggende.setTiltakid(tiltak.getTiltakid());
				forebyggendeListe.add(forebyggende);
				forebyggende.setParams();
				int[] forTypes = forebyggende.getTypes();
				Object[] forParams = forebyggende.getParams();
				Long fId = forebyggende.getForebyggendetiltakid();
				String fSQL = insertForebyggendeSQL;
				if (tId != null){
					fSQL = updateForebyggendeSQL;
					forTypes =forebyggende.getUtypes();
				}
				TablesUpdateImpl forebyggendeTableUpdate = new TablesUpdateImpl(getDataSource(), fSQL,forTypes);
				forebyggendeTableUpdate.insert(forParams);
			}
			
		}
	}
}
