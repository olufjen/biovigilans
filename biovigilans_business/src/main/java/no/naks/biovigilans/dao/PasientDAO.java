package no.naks.biovigilans.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Tiltak;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;

/**
 * @author olj
 *
 */
public interface PasientDAO {

	public String getInsertPatientSQL();
	public void setInsertPatientSQL(String insertPatientSQL);
	public String[] getPatienttableDefs();
	public void setPatienttableDefs(String[] patienttableDefs);
	
	public String getUpdatePatientSQL();
	public void setUpdatePatientSQL(String updatePatientSQL);
	public void savePasient(Pasient pasient);
	public String getPasientPrimaryKey();
	public void setPasientPrimaryKey(String pasientPrimaryKey);
	public String[] getPasientprimarykeyTableDefs();
	public void setPasientprimarykeyTableDefs(String[] pasientprimarykeyTableDefs);
	
	public String getInsertsykdomSQL();
	public void setInsertsykdomSQL(String insertsykdomSQL);
	public String getUpdatesykdomSQL();
	public void setUpdatesykdomSQL(String updatesykdomSQL);
	public String[] getSykdomTableDefs();
	public void setSykdomTableDefs(String[] sykdomTableDefs);
	
	public String getInsertAntistoffSQL();
	public void setInsertAntistoffSQL(String insertAntistoffSQL);
	public String getUpdateAntistoffSQL();
	public void setUpdateAntistoffSQL(String updateAntistoffSQL);
	public String[] getAntistoffTableDefs();
	public void setAntistoffTableDefs(String[] antistoffTableDefs);
	public String getInsertForebyggendeSQL();
	public void setInsertForebyggendeSQL(String insertForebyggendeSQL);
	public String getInsertTiltakSQL();
	public void setInsertTiltakSQL(String insertTiltakSQL);
	public String getUpdateTiltakSQL();
	public void setUpdateTiltakSQL(String updateTiltakSQL);
	public String[] getTiltakTableDefs();
	public void setTiltakTableDefs(String[] tiltakTableDefs);
	public String getUpdateForebyggendeSQL();
	public void setUpdateForebyggendeSQL(String updateForebyggendeSQL);
	public String[] getForebyggendeTableDefs();
	public void setForebyggendeTableDefs(String[] forebyggendeTableDefs);
	public String getTiltakPrimarykey();
	public void setTiltakPrimarykey(String tiltakPrimarykey);
	
	public List<Tiltak> getTiltakene();
	public void setTiltakene(List<Tiltak> tiltakene);
	public List<Forebyggendetiltak> getForebyggendeListe();
	public void setForebyggendeListe(List<Forebyggendetiltak> forebyggendeListe);
	public void setAlternativeSource(JdbcTemplate alternativeSource);
}
