package no.naks.biovigilans.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Utredning;

public interface TransfusjonDAO {

	public void saveTransfusjon(Transfusjon transfusjon);
	public String getInsertblodProduktSQL();
	public void setInsertblodProduktSQL(String insertblodProduktSQL);
	public String getUpdateblodProduktSQL();
	public String getInsertTransfusjonSQL();
	public void setInsertTransfusjonSQL(String insertTransfusjonSQL);
	public String getUpdateTransfusjonSQL();
	public void setUpdateTransfusjonSQL(String updateTransfusjonSQL);
	public String getTransfusjonPrimaryKey();
	public void setTransfusjonPrimaryKey(String transfusjonPrimaryKey);
	public String[] getTransfusjonprimarykeyTableDefs();
	public void setTransfusjonprimarykeyTableDefs(
			String[] transfusjonprimarykeyTableDefs);
	public String getInsertPasientkomplikasjonSQL();
	public void setInsertPasientkomplikasjonSQL(String insertPasientkomplikasjonSQL);
	public String getUpdatePasientkomplikasjonSQL();
	public void setUpdatePasientkomplikasjonSQL(String updatePasientkomplikasjonSQL);
	public String getInsertMeldingSQL();
	public void setInsertMeldingSQL(String insertMeldingSQL);
	public String getUpdateMeldingSQL();
	public void setUpdateMeldingSQL(String updateMeldingSQL);
	public String getMeldingPrimaryKey();
	public void setMeldingPrimaryKey(String meldingPrimaryKey);
	public String[] getMeldingprimarykeyTableDefs();
	public void setMeldingprimarykeyTableDefs(String[] meldingprimarykeyTableDefs);
	public String getInsertSymptomerSQL();
	public void setInsertSymptomerSQL(String insertSymptomerSQL);
	public String getSymptomerPrimaryKey();
	public void setSymptomerPrimaryKey(String symptomerPrimaryKey);
	public String[] getSymptomerprimarykeyTableDefs();
	public void setSymptomerprimarykeyTableDefs(
			String[] symptomerprimarykeyTableDefs);
	public String getInsertUtredningSQL();
	public void setInsertUtredningSQL(String insertUtredningSQL);
	public String getInsertHemolyseSQL();
	public void setInsertHemolyseSQL(String insertHemolyseSQL);
	public String getUtredningPrimaryKey();
	public void setUtredningPrimaryKey(String utredningPrimaryKey);
	public String[] getUtredningprimarykeyTabledefs();
	public void setUtredningprimarykeyTabledefs(
			String[] utredningprimarykeyTabledefs);
	public String getInsertproduktEgenskapSQL();
	public void setInsertproduktEgenskapSQL(String insertproduktEgenskapSQL);
	public String getBlodproduktPrimarykey();
	public void setBlodproduktPrimarykey(String blodproduktPrimarykey);
	public String[] getBlodproduktPrimarykeyTableDefs();
	public void setBlodproduktPrimarykeyTableDefs(
			String[] blodproduktPrimarykeyTableDefs);
	public List<Produktegenskap> getProduktegenskaper();
	public void setProduktegenskaper(List<Produktegenskap> produktegenskaper);	
	public List<Symptomer> getSymptomListe();
	public void setSymptomListe(List<Symptomer> symptomListe);
	public List<Utredning> getUtredninger(); 
	public void setUtredninger(List<Utredning> utredninger);
	public String getMeldingHead();
	public void setMeldingHead(String meldingHead);
	public void setAlternativeSource(JdbcTemplate alternativeSource);

}
