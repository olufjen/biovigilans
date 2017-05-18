package no.naks.biovigilans.dao;

import java.sql.Types;

import no.naks.biovigilans.model.Giver;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;
import no.naks.rammeverk.kildelag.dao.Tablesupdate;

import org.springframework.jdbc.core.SqlParameter;

import no.naks.biovigilans.model.Antistoff;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.dao.AbstractAdmintablesDAO;


/**
 * Denne DAO tjenesten lagrer meldinger av typen Giverhendelse
 * Den lagrer også informasjon om Giver og giveroppfølgng
 * @author olj
 *
 */
public class GiverDAOImpl extends AbstractAdmintablesDAO implements GiverDAO {

	private String meldingHead = "";  // Meldingshode for hemovigilans = Hem, for Celler og vev = Cev, for Organer = Org (April 2016)
	
	private String insertGiverSQL;
	private String updateGiverSQL;
	private Tablesupdate tablesUpdate = null;
	private String giverPrimaryKey;
	private String[] giverprimarykeyTableDefs;
	
	private String insertMeldingSQL;
	private String updateMeldingSQL;
	private String meldingPrimaryKey;
	private String[] meldingprimarykeyTableDefs; 
	
	private String insertGiverkomplikasjonSQL;
	private String giverkomplikasjonPrimaryKey;
	private String[] giverkomplikasjonprimarykeyTableDefs;
	
	private String insertGiveroppfolgingSQL;
	private String giveroppfolgingPrimaryKey;
	private String[] giveroppfolgingprimarykeyTableDefs;
	
	
	public String getMeldingHead() {
		return meldingHead;
	}
	public void setMeldingHead(String meldingHead) {
		this.meldingHead = meldingHead;
	}
	public String getInsertGiverSQL() {
		return insertGiverSQL;
	}
	public void setInsertGiverSQL(String insertGiverSQL) {
		this.insertGiverSQL = insertGiverSQL;
	}

	public String getUpdateGiverSQL() {
		return updateGiverSQL;
	}
	public void setUpdateGiverSQL(String updateGiverSQL) {
		this.updateGiverSQL = updateGiverSQL;
	}
	
	public String getGiverPrimaryKey() {
		return giverPrimaryKey;
	}
	public void setGiverPrimaryKey(String giverPrimaryKey) {
		this.giverPrimaryKey = giverPrimaryKey;
	}
	public String[] getGiverprimarykeyTableDefs() {
		return giverprimarykeyTableDefs;
	}
	public void setGiverprimarykeyTableDefs(String[] giverprimarykeyTableDefs) {
		this.giverprimarykeyTableDefs = giverprimarykeyTableDefs;
	}
	
	
	public String getInsertMeldingSQL() {
		return insertMeldingSQL;
	}
	public void setInsertMeldingSQL(String insertMeldingSQL) {
		this.insertMeldingSQL = insertMeldingSQL;
	}
	public String getUpdateMeldingSQL() {
		return updateMeldingSQL;
	}
	public void setUpdateMeldingSQL(String updateMeldingSQL) {
		this.updateMeldingSQL = updateMeldingSQL;
	}
	public String getMeldingPrimaryKey() {
		return meldingPrimaryKey;
	}
	public void setMeldingPrimaryKey(String meldingPrimaryKey) {
		this.meldingPrimaryKey = meldingPrimaryKey;
	}
	public String[] getMeldingprimarykeyTableDefs() {
		return meldingprimarykeyTableDefs;
	}
	public void setMeldingprimarykeyTableDefs(String[] meldingprimarykeyTableDefs) {
		this.meldingprimarykeyTableDefs = meldingprimarykeyTableDefs;
	}
	
	public String getInsertGiverkomplikasjonSQL() {
		return insertGiverkomplikasjonSQL;
	}
	public void setInsertGiverkomplikasjonSQL(String insertGiverkomplikasjonSQL) {
		this.insertGiverkomplikasjonSQL = insertGiverkomplikasjonSQL;
	}
	public String getGiverkomplikasjonPrimaryKey() {
		return giverkomplikasjonPrimaryKey;
	}
	public void setGiverkomplikasjonPrimaryKey(String giverkomplikasjonPrimaryKey) {
		this.giverkomplikasjonPrimaryKey = giverkomplikasjonPrimaryKey;
	}
	public String[] getGiverkomplikasjonprimarykeyTableDefs() {
		return giverkomplikasjonprimarykeyTableDefs;
	}
	public void setGiverkomplikasjonprimarykeyTableDefs(
			String[] giverkomplikasjonprimarykeyTableDefs) {
		this.giverkomplikasjonprimarykeyTableDefs = giverkomplikasjonprimarykeyTableDefs;
	}
	
	
	public String getInsertGiveroppfolgingSQL() {
		return insertGiveroppfolgingSQL;
	}
	public void setInsertGiveroppfolgingSQL(String insertGiveroppfolgingSQL) {
		this.insertGiveroppfolgingSQL = insertGiveroppfolgingSQL;
	}
	public String getGiveroppfolgingPrimaryKey() {
		return giveroppfolgingPrimaryKey;
	}
	public void setGiveroppfolgingPrimaryKey(String giveroppfolgingPrimaryKey) {
		this.giveroppfolgingPrimaryKey = giveroppfolgingPrimaryKey;
	}
	public String[] getGiveroppfolgingprimarykeyTableDefs() {
		return giveroppfolgingprimarykeyTableDefs;
	}
	public void setGiveroppfolgingprimarykeyTableDefs(
			String[] giveroppfolgingprimarykeyTableDefs) {
		this.giveroppfolgingprimarykeyTableDefs = giveroppfolgingprimarykeyTableDefs;
	}
	
	public void saveGiver(Giver giver) {
	
		giver.setParams();
		
		int[] types = giver.getTypes();
//		Object[] params = giver.getParams();
		String sql = insertGiverSQL;
		Long id = giver.getGiverid();
		if(id!=null){
			sql=insertGiverSQL;
			giver.setGiverid(null);
			giver.setParams();
			id = null;
			//types = giver.getUtypes();
		}
		Object[] params = giver.getParams();
		tablesUpdate = new TablesUpdateImpl(getDataSource(),sql,types);
		tablesUpdate.insert(params);
		if (id == null){
			giver.setGiverid(getPrimaryKey(giverPrimaryKey, giverprimarykeyTableDefs));
		}
		
	}
	
	public void saveVigilansmelding(Vigilansmelding melding){
		melding.setMeldingHead(meldingHead);
		melding.setMeldingParams();
		melding.setMeldingTypes();
		int[]meldingTypes = melding.getTypes();
		Object[]meldingParams = melding.getParams();
		Long id = melding.getMeldeid();
		String meldeSQL = insertMeldingSQL;
		if (id != null){
			meldeSQL = updateMeldingSQL;
			meldingTypes = melding.getUtypes();
		}
	//	id = melding.getMeldeid();
		tablesUpdate = new TablesUpdateImpl(getDataSource(),meldeSQL,meldingTypes);
		tablesUpdate.insert(meldingParams);
		
		if(id==null){
			melding.setMeldeid(getPrimaryKey(meldingPrimaryKey, meldingprimarykeyTableDefs));
			/** update table vigilansmelding with meldingnokkel   */
			String nokkel = melding.getMeldingsnokkel();
			if (nokkel == null)						// For å håndtere oppfølgingsmeldinger
				melding.setMeldingsnokkel(null);
			melding.setMeldingParams();
			melding.setMeldingTypes();
			meldeSQL = updateMeldingSQL;
			meldingTypes = melding.getUtypes();
			meldingParams= melding.getParams();
			tablesUpdate = new TablesUpdateImpl(getDataSource(),meldeSQL,meldingTypes);
			tablesUpdate.insert(meldingParams);
		}
			
	}
	
	public void saveGiverkomplikasjon(Giverkomplikasjon giverKomplikasjon){
	
		giverKomplikasjon.setParams();
		giverKomplikasjon.setGiverkompTypes();
		int[] types = giverKomplikasjon.getTypes();
		Object[] params = giverKomplikasjon.getParams();
		String sql = insertGiverkomplikasjonSQL;
		tablesUpdate = new TablesUpdateImpl(getDataSource(),sql,types);
		tablesUpdate.insert(params);
		
	}

	public void saveGiveroppfolging(Giveroppfolging giveroppfolging){
		giveroppfolging.setParams();
		int[] types = giveroppfolging.getTypes();
		String sql = insertGiveroppfolgingSQL;
		Long id = giveroppfolging.getGiveroppfolgingId();
		if(id!=null){
			sql = insertGiveroppfolgingSQL;
			giveroppfolging.setGiveroppfolgingId(null);
			giveroppfolging.setParams();
			id = null;
		}
		Object[] params = giveroppfolging.getParams();
		tablesUpdate = new TablesUpdateImpl(getDataSource(),sql,types);
		tablesUpdate.insert(params);
	}
}
