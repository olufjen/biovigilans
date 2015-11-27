package no.naks.biovigilans.dao;

import no.naks.biovigilans.model.Annenkomplikasjon;

public interface AnnenKomplikasjonDAO {
	
	public String getInsertAnnenKomplikasjonSQL();
	public void setInsertAnnenKomplikasjonSQL(String insertGiverSQL);
	public String getAnnenKomplikasjonPrimaryKey() ;
	public void setAnnenKomplikasjonPrimaryKey(String giverPrimaryKey);
	public String[] getAnnenKomplikasjonprimarykeyTableDefs();
	public void setAnnenKomplikasjonprimarykeyTableDefs(String[] giverprimarykeyTableDefs);
	public void saveAnnenKomplikasjon(Annenkomplikasjon annenKomplikasjon);

	public String getInsertMeldingSQL();
	public void setInsertMeldingSQL(String insertMeldingSQL);
	public String getUpdateMeldingSQL();
	public void setUpdateMeldingSQL(String updateMeldingSQL);
	public String getMeldingPrimaryKey();
	public void setMeldingPrimaryKey(String meldingPrimaryKey);
	public String[] getMeldingprimarykeyTableDefs();
	public void setMeldingprimarykeyTableDefs(String[] meldingprimarykeyTableDefs);
}
