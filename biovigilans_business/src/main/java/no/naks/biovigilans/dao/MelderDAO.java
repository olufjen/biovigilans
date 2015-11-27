package no.naks.biovigilans.dao;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.rammeverk.kildelag.dao.Tablesupdate;

	public interface MelderDAO {
		
	public void setInsertMelderSQL(String insertMelderSQL) ;
	public String getMelderPrimaryKey() ;
	public void setMelderPrimaryKey(String melderPrimaryKey) ;
	public String[] getMelderprimarykeyTableDefs();
	public void setMelderprimarykeyTableDefs(String[] melderprimarykeyTableDefs) ;
	public Tablesupdate getTablesUpdate() ;
	public void setTablesUpdate(Tablesupdate tablesUpdate) ;
	public void saveMelder(Melder melder);
	public String getUpdateMelderSQL() ;
	public void setUpdateMelderSQL(String updateMelderSQL);
	public List selectMelder(String epost);
	public Map<String,List> selectMeldinger (String meldingsNokkel);
	public String[] getVigilandsMeldingTableDefs();
	public void setVigilandsMeldingTableDefs(String[] vigilandsMeldingTableDefs);
	public String getSelectvigilansMeldingSQL();
	public void setSelectvigilansMeldingSQL(String selectvigilansMeldingSQL);
	public String getSelectannenKomplikasjonSQL();
	public void setSelectannenKomplikasjonSQL(String selectannenKomplikasjonSQL);
	public String[] getAnnenkomplikasjonTableDefs();
	public void setAnnenkomplikasjonTableDefs(String[] annenkomplikasjonTableDefs);
	public String getSelectpasientKomplikasjonSQL();
	public void setSelectpasientKomplikasjonSQL(String selectpasientKomplikasjonSQL);
	public String[] getPasientkomplikasjonTableDefs();
	public void setPasientkomplikasjonTableDefs(
			String[] pasientkomplikasjonTableDefs);
	public String getSelectgiverKomplikasjonSQL();
	public void setSelectgiverKomplikasjonSQL(String selectgiverKomplikasjonSQL);
	public String[] getGiverkomplikasjonTableDefs();
	public void setGiverkomplikasjonTableDefs(String[] giverkomplikasjonTableDefs);
	public String getSelectDonasjonSQL();
	public void setSelectDonasjonSQL(String selectDonasjonSQL);
	public String[] getDonasjonTabledefs();
	public void setDonasjonTabledefs(String[] donasjonTabledefs);
	public String getSelectgiverSQL();
	public void setSelectgiverSQL(String selectgiverSQL);
	public String[] getGiverTableDefs();
	public void setGiverTableDefs(String[] giverTableDefs);
	public String getGiveroppfolgingSQL();
	public void setGiveroppfolgingSQL(String giveroppfolgingSQL);
	public String[] getGiveroppfolgingTableDefs();
	public void setGiveroppfolgingTableDefs(String[] giveroppfolgingTableDefs);
	public String getKomplikasjonsdiagnosegiverSQL();
	public void setKomplikasjonsdiagnosegiverSQL(
			String komplikasjonsdiagnosegiverSQL);
	public String[] getKomplikasjonsdiagnosegiverTableDefs();
	public void setKomplikasjonsdiagnosegiverTableDefs(
			String[] komplikasjonsdiagnosegiverTableDefs);
	public String getSelecttransfusjonSQL();
	public void setSelecttransfusjonSQL(String selecttransfusjonSQL);
	public String[] getTransfusjonTableDefs();
	public void setTransfusjonTableDefs(String[] transfusjonTableDefs);
	public String getSelectPasientSQL();
	public void setSelectPasientSQL(String selectPasientSQL);
	public String[] getPasientTableDefs();
	public void setPasientTableDefs(String[] pasientTableDefs);
	public String getSelectSykdomSQL();
	public void setSelectSykdomSQL(String selectSykdomSQL);
	public String[] getSykdomTableDefs();
	public void setSykdomTableDefs(String[] sykdomTableDefs);
	public String getKomplikasjonSQL();
	public void setKomplikasjonSQL(String komplikasjonSQL);
	public String[] getKomplikasjonTableDefs();
	public void setKomplikasjonTableDefs(String[] komplikasjonTableDefs);
	public String getAnnenkomplikasjonSQL();
	public void setAnnenkomplikasjonSQL(String annenkomplikasjonSQL);
	public String getUtredningSQL();
	public void setUtredningSQL(String utredningSQL);
	public String[] getUtredningTableDefs();
	public void setUtredningTableDefs(String[] utredningTableDefs);
	public String getBlodProduktSQL();
	public void setBlodProduktSQL(String blodProduktSQL);
	public String[] getBlodproduktTableDefs();
	public void setBlodproduktTableDefs(String[] blodproduktTableDefs);
	public String getProduktegenskapSQL();
	public void setProduktegenskapSQL(String produktegenskapSQL);
	public String[] getProduktegenskapTableDefs();
	public void setProduktegenskapTableDefs(String[] produktegenskapTableDefs);
	public String getSymptomerSQL();
	public void setSymptomerSQL(String symptomerSQL);
	public String[] getSymptomerTableDefs();
	public void setSymptomerTableDefs(String[] symptomerTableDefs);
	public String getTiltakSQL();
	public void setTiltakSQL(String tiltakSQL);
	public String[] getTiltakTableDefs();
	public void setTiltakTableDefs(String[] tiltakTableDefs);
	public String getForebyggendetiltakSQL();
	public void setForebyggendetiltakSQL(String forebyggendetiltakSQL);
	public String[] getForebyggendetiltakTableDefs();
	public void setForebyggendetiltakTableDefs(String[] forebyggendetiltakTableDefs);
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger);
	
}
