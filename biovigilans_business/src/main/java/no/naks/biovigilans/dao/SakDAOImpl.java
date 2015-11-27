package no.naks.biovigilans.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameter;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.rammeverk.kildelag.dao.AbstractAdmintablesDAO;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;
import no.naks.rammeverk.kildelag.dao.Tablesupdate;

/**
 * @author olj
 *  SakDAOImpl
 *  Denne klassen håndterer oppslag/lagring til tabellene Sak og Diskusjon
 *  Den henter også saksbehandlere
 */
public class SakDAOImpl extends AbstractAdmintablesDAO implements SakDAO{
	private String insertDiskusjonSQL;
	private String updateDiskusjonSQL;
	private String insertSakSQL;
	private String updateSakSQL;
	private String sakPrimaryKey;
	private String selectDiskusjonSQL;
	private String selectenDiskusjonSQL;
	private String selectSakSQL;
	private String saksbehandlerSQL;
	private String selectmelderSQL;
	private String[] melderTableDefs;
	private String[] saksbehandlerTableDefs;
	private String[] diskusjonTableDefs;
	private String[] sakTableDefs;
	private String[] sakprimarykeyTableDefs;
	private String diskusjonPrimaryKey;
	private String[] diskusjonprimarykeyTableDefs;
	private String[] flaggNames;

	private Tablesupdate tablesUpdate = null;
	/**
	 * saveDiskusjon
	 * Denne funksjonen lagrer en diskusjon til tabellen diskusjon
	 */	
	public void saveDiskusjon(Diskusjon diskusjon){
		if (diskusjon != null){
			diskusjon.setParams();
			int[] types= diskusjon.getTypes();
			Object[] params = diskusjon.getParams();
			String sql=insertDiskusjonSQL;
			Long id = diskusjon.getDiskusjonid();
			if(id!=null){
				sql = updateDiskusjonSQL;
				types = diskusjon.getUtypes();
			}
			
			tablesUpdate = new TablesUpdateImpl(getDataSource(), sql, types);
			tablesUpdate.insert(params);
			
			if(id==null){
				diskusjon.setDiskusjonid(getPrimaryKey(diskusjonPrimaryKey,diskusjonprimarykeyTableDefs));
			}
		}
		
	}
	/**
	 * saveDiskusjon
	 * Denne funksjonen lagrer diskusjoner til tabellen diskusjon
	 */	
	public void saveDiskusjon(Map<String,Diskusjon> diskusjonsMappe){
		
		for (int i = 0;i< flaggNames.length;i++){
			String flagg = flaggNames[i];
			Diskusjon diskusjon = diskusjonsMappe.get(flagg);
			if (diskusjon != null){
				diskusjon.setParams();
				int[] types= diskusjon.getTypes();
				Object[] params = diskusjon.getParams();
				String sql=insertDiskusjonSQL;
				Long id = diskusjon.getDiskusjonid();
				if(id!=null){
					sql = updateDiskusjonSQL;
					types = diskusjon.getUtypes();
				}
				
				tablesUpdate = new TablesUpdateImpl(getDataSource(), sql, types);
				tablesUpdate.insert(params);
				
				if(id==null){
					diskusjon.setDiskusjonid(getPrimaryKey(diskusjonPrimaryKey,diskusjonprimarykeyTableDefs));
				}					
			}
		
		}

	}
	/**
	 * saveSak
	 * Denne funksjonen lagrer saker til tabellen sak
	 */
	public void saveSak(Map<String,Sak> saksMappe){
		for (int i = 0;i< flaggNames.length;i++){
			String flagg = flaggNames[i];
			Sak sak = saksMappe.get(flagg);
			if (sak !=null){
				sak.setParams();
				int[] types= sak.getTypes();
				Object[] params = sak.getParams();
				String sql=insertSakSQL;
				Long id = sak.getSaksid();
				if(id!=null){
					sql = updateSakSQL;
					types = sak.getUtypes();
				}
				
				tablesUpdate = new TablesUpdateImpl(getDataSource(), sql, types);
				tablesUpdate.insert(params);
				
				if(id==null){
					sak.setSaksid(getPrimaryKey(sakPrimaryKey,sakprimarykeyTableDefs));
				}	
			}
		}
	}
	/**
	 * collectDiskusjoner
	 * Denne rutinen henter alle saksmerknader til en sak
	 * @param meldeId Meldingsid
	 * @return
	 */
	public Map<String,List> collectDiskusjoner(Long meldeId){
		Map alleMerknader = new HashMap<String,List>();
		String mId = String.valueOf(meldeId.longValue());
		DiskusjonSelect diskusjonSelect = new DiskusjonSelect(getDataSource(),selectDiskusjonSQL,diskusjonTableDefs);
		int type = Types.INTEGER;
		diskusjonSelect.declareParameter(new SqlParameter(type));
		List<Diskusjon> diskusjoner = diskusjonSelect.execute(meldeId);
		alleMerknader.put(mId, diskusjoner);
		if (diskusjoner != null && !diskusjoner.isEmpty()){
			SakSelect sakSelect = new SakSelect(getDataSource(),selectSakSQL,sakTableDefs);
			int saktype = Types.INTEGER;
			sakSelect.declareParameter(new SqlParameter(saktype));
			for (Diskusjon diskusjon :diskusjoner){
				Long dId = diskusjon.getDiskusjonid();
				String diskId = "s"+String.valueOf(dId.longValue());
				List<Sak> saker = sakSelect.execute(dId);
				alleMerknader.put(diskId, saker);
			}
		}
		
		return alleMerknader;
	}
	/**
	 * collectDiskusjon
	 * Denne rutinen henteren gitt merknad til en sak
	 * @param meldeId Meldingsid
	 * @return
	 */
	public Diskusjon collectDiskusjon(Long diskId){
		String mId = String.valueOf(diskId.longValue());
		Diskusjon diskusjon = null;
		DiskusjonSelect diskusjonSelect = new DiskusjonSelect(getDataSource(),selectenDiskusjonSQL,diskusjonTableDefs);
		int type = Types.INTEGER;
		diskusjonSelect.declareParameter(new SqlParameter(type));
		List<Diskusjon> diskusjoner = diskusjonSelect.execute(diskId);
		if (diskusjoner != null && !diskusjoner.isEmpty()){
			diskusjon = diskusjoner.get(0);
		}

		
		return diskusjon;
	}	
	public Melder collectMelder(Long melderId){
		MelderSelect melderSelect = new MelderSelect(getDataSource(),selectmelderSQL,melderTableDefs);
		int meldertype = Types.INTEGER;
		melderSelect.declareParameter(new SqlParameter(meldertype));
		List<Melder> meldere = melderSelect.execute(melderId);
		Melder melder = meldere.get(0);
		return melder;
	}
	
	public List collectSaksbehandlere(){
		List saksbehandlere = new ArrayList<Saksbehandler>();
		SaksbehandlerSelect behandlerSelect = new SaksbehandlerSelect(getDataSource(),saksbehandlerSQL,saksbehandlerTableDefs);
		saksbehandlere = behandlerSelect.execute();
		return saksbehandlere;
	}
	
	public String getSelectenDiskusjonSQL() {
		return selectenDiskusjonSQL;
	}
	public void setSelectenDiskusjonSQL(String selectenDiskusjonSQL) {
		this.selectenDiskusjonSQL = selectenDiskusjonSQL;
	}
	public String getSelectmelderSQL() {
		return selectmelderSQL;
	}
	public void setSelectmelderSQL(String selectmelderSQL) {
		this.selectmelderSQL = selectmelderSQL;
	}
	public String[] getMelderTableDefs() {
		return melderTableDefs;
	}
	public void setMelderTableDefs(String[] melderTableDefs) {
		this.melderTableDefs = melderTableDefs;
	}
	public String getSelectDiskusjonSQL() {
		return selectDiskusjonSQL;
	}
	public void setSelectDiskusjonSQL(String selectDiskusjonSQL) {
		this.selectDiskusjonSQL = selectDiskusjonSQL;
	}
	public String getSelectSakSQL() {
		return selectSakSQL;
	}
	public void setSelectSakSQL(String selectSakSQL) {
		this.selectSakSQL = selectSakSQL;
	}
	public String[] getDiskusjonTableDefs() {
		return diskusjonTableDefs;
	}
	public void setDiskusjonTableDefs(String[] diskusjonTableDefs) {
		this.diskusjonTableDefs = diskusjonTableDefs;
	}
	public String[] getSakTableDefs() {
		return sakTableDefs;
	}
	public void setSakTableDefs(String[] sakTableDefs) {
		this.sakTableDefs = sakTableDefs;
	}
	
	public String getInsertSakSQL() {
		return insertSakSQL;
	}
	public void setInsertSakSQL(String insertSakSQL) {
		this.insertSakSQL = insertSakSQL;
	}
	public String getUpdateSakSQL() {
		return updateSakSQL;
	}
	public void setUpdateSakSQL(String updateSakSQL) {
		this.updateSakSQL = updateSakSQL;
	}
	public String getSakPrimaryKey() {
		return sakPrimaryKey;
	}
	public void setSakPrimaryKey(String sakPrimaryKey) {
		this.sakPrimaryKey = sakPrimaryKey;
	}
	public String[] getSakprimarykeyTableDefs() {
		return sakprimarykeyTableDefs;
	}
	public void setSakprimarykeyTableDefs(String[] sakprimarykeyTableDefs) {
		this.sakprimarykeyTableDefs = sakprimarykeyTableDefs;
	}
	public String[] getFlaggNames() {
		return flaggNames;
	}

	public void setFlaggNames(String[] flaggNames) {
		this.flaggNames = flaggNames;
	}

	public String getInsertDiskusjonSQL() {
		return insertDiskusjonSQL;
	}

	public void setInsertDiskusjonSQL(String insertDiskusjonSQL) {
		this.insertDiskusjonSQL = insertDiskusjonSQL;
	}

	public String getUpdateDiskusjonSQL() {
		return updateDiskusjonSQL;
	}

	public void setUpdateDiskusjonSQL(String updateDiskusjonSQL) {
		this.updateDiskusjonSQL = updateDiskusjonSQL;
	}

	public String getDiskusjonPrimaryKey() {
		return diskusjonPrimaryKey;
	}

	public void setDiskusjonPrimaryKey(String diskusjonPrimaryKey) {
		this.diskusjonPrimaryKey = diskusjonPrimaryKey;
	}

	public String[] getDiskusjonprimarykeyTableDefs() {
		return diskusjonprimarykeyTableDefs;
	}

	public void setDiskusjonprimarykeyTableDefs(
			String[] diskusjonprimarykeyTableDefs) {
		this.diskusjonprimarykeyTableDefs = diskusjonprimarykeyTableDefs;
	}
	public String getSaksbehandlerSQL() {
		return saksbehandlerSQL;
	}
	public void setSaksbehandlerSQL(String saksbehandlerSQL) {
		this.saksbehandlerSQL = saksbehandlerSQL;
	}
	public String[] getSaksbehandlerTableDefs() {
		return saksbehandlerTableDefs;
	}
	public void setSaksbehandlerTableDefs(String[] saksbehandlerTableDefs) {
		this.saksbehandlerTableDefs = saksbehandlerTableDefs;
	}
	
}
