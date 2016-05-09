
package no.naks.biovigilans.dao;


import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;







import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;
import no.naks.rammeverk.kildelag.dao.Tablesupdate;
import no.naks.rammeverk.kildelag.model.LineKey;


/**
 * 
 * 
 *
 * @author ojn
 *
 */
public interface HendelsehemovigilansDAO {
	
	public String getUpdateVigilansmeldingSQL();
	public void setUpdateVigilansmeldingSQL(String updateVigilansmeldingSQL);
	
	public void updateVigilansMelding(Vigilansmelding melding);	
	public List<Vigilansmelding> collectMeldinger(Long melderId);
	public void updateVigilansstatus(Vigilansmelding melding);
	public List<Vigilansmelding> collectMeldingen(Long meldeId);
	public void setAlternativeSource(JdbcTemplate alternativeSource);
}
