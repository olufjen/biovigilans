package no.naks.biovigilans.dao;

import java.util.Date;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.dao.AbstractAdmintablesDAO;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;
import no.naks.rammeverk.kildelag.dao.Tablesupdate;

/**
 * Denne DAO tjenesten lagrer meldinger av typen Annen hendelse
 * @author olj
 *
 */
public class AnnenkomplikasjonDAOImpl extends AbstractAdmintablesDAO implements AnnenKomplikasjonDAO {

	private String meldingHead = "";  // Meldingshode for hemovigilans = Hem, for Celler og vev = Cev, for Organer = Org (April 2016)

	private String insertAnnenKomplikasjonSQL;
	private String updateAnnenKomplikasjonSQL;
	private String annenKomplikasjonPrimaryKey ;
	private String insertMeldingSQL;
	private String updateMeldingSQL;
	private String meldingPrimaryKey;
	private String[]meldingprimarykeyTableDefs; 
	private String[] annenKomplikasjonprimarykeyTableDefs;
	private Tablesupdate tablesUpdate = null;
	
	
	
	
	public String getMeldingHead() {
		return meldingHead;
	}
	public void setMeldingHead(String meldingHead) {
		this.meldingHead = meldingHead;
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
	public String getUpdateAnnenKomplikasjonSQL() {
		return updateAnnenKomplikasjonSQL;
	}
	public void setUpdateAnnenKomplikasjonSQL(String updateAnnenKomplikasjonSQL) {
		this.updateAnnenKomplikasjonSQL = updateAnnenKomplikasjonSQL;
	}
	public String getInsertAnnenKomplikasjonSQL() {
		return insertAnnenKomplikasjonSQL;
	}
	public void setInsertAnnenKomplikasjonSQL(String insertAnnenKomplikasjonSQL) {
		this.insertAnnenKomplikasjonSQL = insertAnnenKomplikasjonSQL;
	}

	public String getAnnenKomplikasjonPrimaryKey() {
		return annenKomplikasjonPrimaryKey;
	}
	public void setAnnenKomplikasjonPrimaryKey(String annenKomplikasjonPrimaryKey) {
		this.annenKomplikasjonPrimaryKey = annenKomplikasjonPrimaryKey;
	}
	public String[] getAnnenKomplikasjonprimarykeyTableDefs() {
		return annenKomplikasjonprimarykeyTableDefs;
	}
	public void setAnnenKomplikasjonprimarykeyTableDefs(
			String[] annenKomplikasjonprimarykeyTableDefs) {
		this.annenKomplikasjonprimarykeyTableDefs = annenKomplikasjonprimarykeyTableDefs;
	}


	public void saveAnnenKomplikasjon(Annenkomplikasjon annenKomplikasjon){
		Vigilansmelding melding = (Vigilansmelding)annenKomplikasjon;
		melding.setMeldingHead(meldingHead);
		Date mDato = melding.getMeldingsdato(); // Meldingsdato = null ved rapportering
		melding.setMeldingsdato(mDato); // Setter meldingsdato til opprinnelig meldingsdato ved reklassifisering OLJ 23.01.17
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
		tablesUpdate = new TablesUpdateImpl(getDataSource(),meldeSQL,meldingTypes);
		tablesUpdate.insert(meldingParams);
		if (id == null){
			melding.setMeldeid(getPrimaryKey(meldingPrimaryKey,meldingprimarykeyTableDefs));
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
		id = melding.getMeldeid();
		tablesUpdate = null;

		annenKomplikasjon.setParams();
		annenKomplikasjon.setannenKomplikasjonstypes();
		int[] atypes = annenKomplikasjon.getTypes();
		Object[] aparams = annenKomplikasjon.getParams();
		boolean isUpdate = annenKomplikasjon.isUpdat();
		String sql = insertAnnenKomplikasjonSQL;
		if(isUpdate){
			sql = updateAnnenKomplikasjonSQL;
			atypes = annenKomplikasjon.getUtypes();
		}
		tablesUpdate = new TablesUpdateImpl(getDataSource(),sql,atypes);
		tablesUpdate.insert(aparams);
		
	}
}
