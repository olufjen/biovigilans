package no.naks.biovigilans.dao;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Vigilansmelding;

public interface SaksbehandlingDAO {

	public List collectMessages();
	
	public String[] getVigilandsMeldingTableDefs();


	public void setVigilandsMeldingTableDefs(String[] vigilandsMeldingTableDefs);


	public String getSelectvigilansMeldingSQL();


	public void setSelectvigilansMeldingSQL(String selectvigilansMeldingSQL);
	public List collectPasientMeldinger(List<Vigilansmelding>meldinger);
	public List collectGiverMeldinger(List<Vigilansmelding>meldinger);
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger);
	
	public String[] getPasientMeldingTableDefs();


	public void setPasientMeldingTableDefs(String[] pasientMeldingTableDefs);


	public String[] getGiverMeldingTableDefs();


	public void setGiverMeldingTableDefs(String[] giverMeldingTableDefs);


	public String[] getAnnenMeldingTableDefs();


	public void setAnnenMeldingTableDefs(String[] annenMeldingTableDefs);


	public String getSelectpasientMeldingSQL();


	public void setSelectpasientMeldingSQL(String selectpasientMeldingSQL);


	public String getSelectgiverMeldingSQL();


	public void setSelectgiverMeldingSQL(String selectgiverMeldingSQL);


	public String getSelectannenMeldingSQL();


	public void setSelectannenMeldingSQL(String selectannenMeldingSQL);

	
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


	public VigilansSelect getVigilansSelect();


	public void setVigilansSelect(VigilansSelect vigilansSelect);

	public String getAnnenkomplikasjonSQL();


	public void setAnnenkomplikasjonSQL(String annenkomplikasjonSQL);


	public String getSymptomerSQL();


	public void setSymptomerSQL(String symptomerSQL);


	public String[] getSymptomerTableDefs();


	public void setSymptomerTableDefs(String[] symptomerTableDefs);


	public String getSelectDonasjonSQL();


	public void setSelectDonasjonSQL(String selectDonasjonSQL);


	public String[] getDonasjonTabledefs();


	public void setDonasjonTabledefs(String[] donasjonTabledefs);


	public String getSelectgiverSQL();


	public void setSelectgiverSQL(String selectgiverSQL);


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


	public String getTiltakSQL();


	public void setTiltakSQL(String tiltakSQL);


	public String[] getTiltakTableDefs();


	public void setTiltakTableDefs(String[] tiltakTableDefs);


	public String getForebyggendetiltakSQL();


	public void setForebyggendetiltakSQL(String forebyggendetiltakSQL);


	public String[] getForebyggendetiltakTableDefs();


	public void setForebyggendetiltakTableDefs(String[] forebyggendetiltakTableDefs);
	public String[] getGiverTableDefs();


	public void setGiverTableDefs(String[] giverTableDefs);

	public Map<String,List> selectMeldinger (String meldeid);
	public String getSelectvigilansMeldingidSQL();

	public List collectMessagessaksbehandler(Long behandlerid);
	public void setSelectvigilansMeldingidSQL(String selectvigilansMeldingidSQL);
	public List collectMessagesbytypes(String types);
	public List collectMessages(String start,String end);	
	public String getSelectvigilansMeldingtypesSQL();


	public void setSelectvigilansMeldingtypesSQL(
			String selectvigilansMeldingtypesSQL);
	public String getSelectvigilansMeldingnullSQL();
	public String getSelectvigilansMeldingtimeperiodSQL();

	public String getSelectvigilansMeldinghentperiodSQL();


	public boolean isTimeperiodType();


	public void setTimeperiodType(boolean timeperiodType);

	public void setSelectvigilansMeldinghentperiodSQL(
			String selectvigilansMeldinghentperiodSQL);
	public void setSelectvigilansMeldingtimeperiodSQL(
			String selectvigilansMeldingtimeperiodSQL);

	public void setSelectvigilansMeldingnullSQL(String selectvigilansMeldingnullSQL);
	public String getSelectvigilansMeldingmerknaderSQL();


	public void setSelectvigilansMeldingmerknaderSQL(
			String selectvigilansMeldingmerknaderSQL);
	public List collectMessagesMarks(String merknad);
	public String getSelectvigilansMeldingikkeavvistSQL();


	public void setSelectvigilansMeldingikkeavvistSQL(
			String selectvigilansMeldingikkeavvistSQL);

	public String getSelectvigilansMeldingsaksbehandlerSQL();


	public void setSelectvigilansMeldingsaksbehandlerSQL(
			String selectvigilansMeldingsaksbehandlerSQL);

	public String getSelectvigilansMeldingnokkelSQL();


	public void setSelectvigilansMeldingnokkelSQL(
			String selectvigilansMeldingnokkelSQL);
	public String getSelectvigilansMeldinganonymSQL();


	public void setSelectvigilansMeldinganonymSQL(
			String selectvigilansMeldinganonymSQL);
	public List collectMessagesanonyme();

	public Map<String,List> selectMeldingetternokkel (String meldeid);
}
