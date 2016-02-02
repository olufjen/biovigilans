package no.naks.biovigilans.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameter;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.service.SaksbehandlingService;
import no.naks.rammeverk.kildelag.dao.AbstractAdmintablesDAO;

public class SaksbehandlingDAOImpl extends AbstractAdmintablesDAO implements
		SaksbehandlingDAO{

	private String[] vigilandsMeldingTableDefs;
	private String[] pasientMeldingTableDefs;
	private String[] giverMeldingTableDefs;
	private String[] annenMeldingTableDefs;
	
	private String selectvigilansMeldingSQL;
	private String selectvigilansMeldingidSQL;
	private String selectvigilansMeldingtypesSQL;
	private String selectvigilansMeldingnullSQL;
	private String selectvigilansMeldingtimeperiodSQL;
	private String selectvigilansMeldinghentperiodSQL;
	private String selectvigilansMeldingmerknaderSQL;
	private String selectvigilansMeldingikkeavvistSQL;
	private String selectpasientMeldingSQL;
	private String selectgiverMeldingSQL;
	private String selectannenMeldingSQL;
	private boolean timeperiodType = true; // Satt til true dersom forespurt tidsperiode er meldt
	// satt til false dersom forespurt tidsperiode er når meldingen skjedde.
	
	private String selectannenKomplikasjonSQL;
	private String[] annenkomplikasjonTableDefs;
	private String selectpasientKomplikasjonSQL;
	private String[] pasientkomplikasjonTableDefs;
	private String selectgiverKomplikasjonSQL;
	private String[] giverkomplikasjonTableDefs;
/*
 * Må settes verdier på OLJ 28.05.15	
 */
	private String annenkomplikasjonSQL;
	private String symptomerSQL;
	private String[] symptomerTableDefs;
	private String selectDonasjonSQL;
	private String[] donasjonTabledefs;
	private String selectgiverSQL;
	private String[] giverTableDefs;
	private String giveroppfolgingSQL;
	private String[] giveroppfolgingTableDefs;
	private String komplikasjonsdiagnosegiverSQL;
	private String[] komplikasjonsdiagnosegiverTableDefs;
	private String selecttransfusjonSQL;
	private String[] transfusjonTableDefs;
	private String selectPasientSQL;
	private String[] pasientTableDefs;
	private String selectSykdomSQL;
	private String[] sykdomTableDefs;
	private String komplikasjonSQL;
	private String[]komplikasjonTableDefs;
	
	private String utredningSQL;
	private String[] utredningTableDefs;
	private String blodProduktSQL;
	private String[] blodproduktTableDefs;
	private String produktegenskapSQL;
	private String[] produktegenskapTableDefs;
	private String tiltakSQL;
	private String[] tiltakTableDefs;
	private String forebyggendetiltakSQL;
	private String[] forebyggendetiltakTableDefs;
	
		
/*
 * END ==============================================	
 */
	private VigilansSelect vigilansSelect;
	private AnnenmeldingSelect annenmeldingSelect;
	private AnnenkomplikasjonSelect andremeldingSelect = null;
	private PasientkomplikasjonSelect pasientmeldingSelect = null;
	private GiverkomplikasjonSelect givermeldingSelect = null;
	private DonasjonSelect donasjonSelect = null;
	private GiverSelect giverSelect = null;
	private GiveroppfolgingSelect giveroppfolgingSelect = null;
	private KomplikasjonsdiagnoseGiverSelect giverkomplikasjonSelect = null;
	private TransfusjonSelect transfusjonSelect = null;
	private PasientSelect pasientSelect = null;
	private SykdomSelect sykdomSelect = null;
	private KomplikasjonklassifikasjonSelect komplikasjonklassifikasjonSelect = null;
	private UtredningSelect utredningSelect = null;
	private BlodproduktSelect blodproduktSelect = null;
	private ProduktegenskapSelect produktegenskapSelect = null;
	private SymptomerSelect symptomerSelect = null;
	private TiltakSelect tiltakSelect = null;
	private ForebyggendetiltakSelect forebyggendetiltakSelect = null;
	
	/*
	 * Nøkler for giverkomplikasjoner	
	 */
	private String donasjonKey = "donasjonen";
	private String giverenKey = "giver";
	private String giverOppfolgingKey = "giveroppfolging";
	private String giverkomplikasjondiagnoseKey = "giverkomplikasjondiagnose";
	
	
	private String pasientKey = "pasientKomp"; // Nøkkel dersom melding er av type pasientkomplikasjon
	private String giverKey = "giverkomp"; 	// Nøkkel dersom melding er at type giverkomplikasjon
	private String andreKey = "annenKomp";
	private String delMeldingKey = null;
	
	private String pasientenKey = "pasienten";
	private String transfusjonsKey = "transfusjon";
	private String sykdomKey = "sykdom";
	private String klassifikasjonKey = "komplikasjonklassifikasjon";
	private String utredningKey = "utredning";
	private String blodproduktKey = "blodprodukt";
	private String produktegenskapKey = "produktegenskap";
	private String symptomerKey  ="symptomer";
	private String tiltakKey = "tiltak";
	private String forebyggendetiltakKey = "forebyggende";
	

	private Map alleMeldinger = null;


	/* =====================000000000000000000*/
	
	
	public String[] getGiverTableDefs() {
		return giverTableDefs;
	}


	public String getSelectvigilansMeldingikkeavvistSQL() {
		return selectvigilansMeldingikkeavvistSQL;
	}


	public void setSelectvigilansMeldingikkeavvistSQL(
			String selectvigilansMeldingikkeavvistSQL) {
		this.selectvigilansMeldingikkeavvistSQL = selectvigilansMeldingikkeavvistSQL;
	}


	public String getSelectvigilansMeldingmerknaderSQL() {
		return selectvigilansMeldingmerknaderSQL;
	}


	public void setSelectvigilansMeldingmerknaderSQL(
			String selectvigilansMeldingmerknaderSQL) {
		this.selectvigilansMeldingmerknaderSQL = selectvigilansMeldingmerknaderSQL;
	}


	public boolean isTimeperiodType() {
		return timeperiodType;
	}


	public void setTimeperiodType(boolean timeperiodType) {
		this.timeperiodType = timeperiodType;
	}


	public String getSelectvigilansMeldingtimeperiodSQL() {
		return selectvigilansMeldingtimeperiodSQL;
	}


	public String getSelectvigilansMeldinghentperiodSQL() {
		return selectvigilansMeldinghentperiodSQL;
	}


	public void setSelectvigilansMeldinghentperiodSQL(
			String selectvigilansMeldinghentperiodSQL) {
		this.selectvigilansMeldinghentperiodSQL = selectvigilansMeldinghentperiodSQL;
	}


	public void setSelectvigilansMeldingtimeperiodSQL(
			String selectvigilansMeldingtimeperiodSQL) {
		this.selectvigilansMeldingtimeperiodSQL = selectvigilansMeldingtimeperiodSQL;
	}


	public String getSelectvigilansMeldingnullSQL() {
		return selectvigilansMeldingnullSQL;
	}


	public void setSelectvigilansMeldingnullSQL(String selectvigilansMeldingnullSQL) {
		this.selectvigilansMeldingnullSQL = selectvigilansMeldingnullSQL;
	}


	public String getSelectvigilansMeldingtypesSQL() {
		return selectvigilansMeldingtypesSQL;
	}


	public void setSelectvigilansMeldingtypesSQL(
			String selectvigilansMeldingtypesSQL) {
		this.selectvigilansMeldingtypesSQL = selectvigilansMeldingtypesSQL;
	}


	public String getSelectvigilansMeldingidSQL() {
		return selectvigilansMeldingidSQL;
	}


	public void setSelectvigilansMeldingidSQL(String selectvigilansMeldingidSQL) {
		this.selectvigilansMeldingidSQL = selectvigilansMeldingidSQL;
	}


	public void setGiverTableDefs(String[] giverTableDefs) {
		this.giverTableDefs = giverTableDefs;
	}


	public String getAnnenkomplikasjonSQL() {
		return annenkomplikasjonSQL;
	}


	public void setAnnenkomplikasjonSQL(String annenkomplikasjonSQL) {
		this.annenkomplikasjonSQL = annenkomplikasjonSQL;
	}


	public String getSymptomerSQL() {
		return symptomerSQL;
	}


	public void setSymptomerSQL(String symptomerSQL) {
		this.symptomerSQL = symptomerSQL;
	}


	public String[] getSymptomerTableDefs() {
		return symptomerTableDefs;
	}


	public void setSymptomerTableDefs(String[] symptomerTableDefs) {
		this.symptomerTableDefs = symptomerTableDefs;
	}


	public String getSelectDonasjonSQL() {
		return selectDonasjonSQL;
	}


	public void setSelectDonasjonSQL(String selectDonasjonSQL) {
		this.selectDonasjonSQL = selectDonasjonSQL;
	}


	public String[] getDonasjonTabledefs() {
		return donasjonTabledefs;
	}


	public void setDonasjonTabledefs(String[] donasjonTabledefs) {
		this.donasjonTabledefs = donasjonTabledefs;
	}


	public String getSelectgiverSQL() {
		return selectgiverSQL;
	}


	public void setSelectgiverSQL(String selectgiverSQL) {
		this.selectgiverSQL = selectgiverSQL;
	}


	public String getGiveroppfolgingSQL() {
		return giveroppfolgingSQL;
	}


	public void setGiveroppfolgingSQL(String giveroppfolgingSQL) {
		this.giveroppfolgingSQL = giveroppfolgingSQL;
	}


	public String[] getGiveroppfolgingTableDefs() {
		return giveroppfolgingTableDefs;
	}


	public void setGiveroppfolgingTableDefs(String[] giveroppfolgingTableDefs) {
		this.giveroppfolgingTableDefs = giveroppfolgingTableDefs;
	}


	public String getKomplikasjonsdiagnosegiverSQL() {
		return komplikasjonsdiagnosegiverSQL;
	}


	public void setKomplikasjonsdiagnosegiverSQL(
			String komplikasjonsdiagnosegiverSQL) {
		this.komplikasjonsdiagnosegiverSQL = komplikasjonsdiagnosegiverSQL;
	}


	public String[] getKomplikasjonsdiagnosegiverTableDefs() {
		return komplikasjonsdiagnosegiverTableDefs;
	}


	public void setKomplikasjonsdiagnosegiverTableDefs(
			String[] komplikasjonsdiagnosegiverTableDefs) {
		this.komplikasjonsdiagnosegiverTableDefs = komplikasjonsdiagnosegiverTableDefs;
	}


	public String getSelecttransfusjonSQL() {
		return selecttransfusjonSQL;
	}


	public void setSelecttransfusjonSQL(String selecttransfusjonSQL) {
		this.selecttransfusjonSQL = selecttransfusjonSQL;
	}


	public String[] getTransfusjonTableDefs() {
		return transfusjonTableDefs;
	}


	public void setTransfusjonTableDefs(String[] transfusjonTableDefs) {
		this.transfusjonTableDefs = transfusjonTableDefs;
	}


	public String getSelectPasientSQL() {
		return selectPasientSQL;
	}


	public void setSelectPasientSQL(String selectPasientSQL) {
		this.selectPasientSQL = selectPasientSQL;
	}


	public String[] getPasientTableDefs() {
		return pasientTableDefs;
	}


	public void setPasientTableDefs(String[] pasientTableDefs) {
		this.pasientTableDefs = pasientTableDefs;
	}


	public String getSelectSykdomSQL() {
		return selectSykdomSQL;
	}


	public void setSelectSykdomSQL(String selectSykdomSQL) {
		this.selectSykdomSQL = selectSykdomSQL;
	}


	public String[] getSykdomTableDefs() {
		return sykdomTableDefs;
	}


	public void setSykdomTableDefs(String[] sykdomTableDefs) {
		this.sykdomTableDefs = sykdomTableDefs;
	}


	public String getKomplikasjonSQL() {
		return komplikasjonSQL;
	}


	public void setKomplikasjonSQL(String komplikasjonSQL) {
		this.komplikasjonSQL = komplikasjonSQL;
	}


	public String[] getKomplikasjonTableDefs() {
		return komplikasjonTableDefs;
	}


	public void setKomplikasjonTableDefs(String[] komplikasjonTableDefs) {
		this.komplikasjonTableDefs = komplikasjonTableDefs;
	}


	public String getUtredningSQL() {
		return utredningSQL;
	}


	public void setUtredningSQL(String utredningSQL) {
		this.utredningSQL = utredningSQL;
	}


	public String[] getUtredningTableDefs() {
		return utredningTableDefs;
	}


	public void setUtredningTableDefs(String[] utredningTableDefs) {
		this.utredningTableDefs = utredningTableDefs;
	}


	public String getBlodProduktSQL() {
		return blodProduktSQL;
	}


	public void setBlodProduktSQL(String blodProduktSQL) {
		this.blodProduktSQL = blodProduktSQL;
	}


	public String[] getBlodproduktTableDefs() {
		return blodproduktTableDefs;
	}


	public void setBlodproduktTableDefs(String[] blodproduktTableDefs) {
		this.blodproduktTableDefs = blodproduktTableDefs;
	}


	public String getProduktegenskapSQL() {
		return produktegenskapSQL;
	}


	public void setProduktegenskapSQL(String produktegenskapSQL) {
		this.produktegenskapSQL = produktegenskapSQL;
	}


	public String[] getProduktegenskapTableDefs() {
		return produktegenskapTableDefs;
	}


	public void setProduktegenskapTableDefs(String[] produktegenskapTableDefs) {
		this.produktegenskapTableDefs = produktegenskapTableDefs;
	}


	public String getTiltakSQL() {
		return tiltakSQL;
	}


	public void setTiltakSQL(String tiltakSQL) {
		this.tiltakSQL = tiltakSQL;
	}


	public String[] getTiltakTableDefs() {
		return tiltakTableDefs;
	}


	public void setTiltakTableDefs(String[] tiltakTableDefs) {
		this.tiltakTableDefs = tiltakTableDefs;
	}


	public String getForebyggendetiltakSQL() {
		return forebyggendetiltakSQL;
	}


	public void setForebyggendetiltakSQL(String forebyggendetiltakSQL) {
		this.forebyggendetiltakSQL = forebyggendetiltakSQL;
	}


	public String[] getForebyggendetiltakTableDefs() {
		return forebyggendetiltakTableDefs;
	}


	public void setForebyggendetiltakTableDefs(String[] forebyggendetiltakTableDefs) {
		this.forebyggendetiltakTableDefs = forebyggendetiltakTableDefs;
	}

/*
 * =======================
 */
	public String getSelectannenKomplikasjonSQL() {
		return selectannenKomplikasjonSQL;
	}


	public void setSelectannenKomplikasjonSQL(String selectannenKomplikasjonSQL) {
		this.selectannenKomplikasjonSQL = selectannenKomplikasjonSQL;
	}


	public String[] getAnnenkomplikasjonTableDefs() {
		return annenkomplikasjonTableDefs;
	}


	public void setAnnenkomplikasjonTableDefs(String[] annenkomplikasjonTableDefs) {
		this.annenkomplikasjonTableDefs = annenkomplikasjonTableDefs;
	}


	public String getSelectpasientKomplikasjonSQL() {
		return selectpasientKomplikasjonSQL;
	}


	public void setSelectpasientKomplikasjonSQL(String selectpasientKomplikasjonSQL) {
		this.selectpasientKomplikasjonSQL = selectpasientKomplikasjonSQL;
	}


	public String[] getPasientkomplikasjonTableDefs() {
		return pasientkomplikasjonTableDefs;
	}


	public void setPasientkomplikasjonTableDefs(
			String[] pasientkomplikasjonTableDefs) {
		this.pasientkomplikasjonTableDefs = pasientkomplikasjonTableDefs;
	}


	public String getSelectgiverKomplikasjonSQL() {
		return selectgiverKomplikasjonSQL;
	}


	public void setSelectgiverKomplikasjonSQL(String selectgiverKomplikasjonSQL) {
		this.selectgiverKomplikasjonSQL = selectgiverKomplikasjonSQL;
	}


	public String[] getGiverkomplikasjonTableDefs() {
		return giverkomplikasjonTableDefs;
	}


	public void setGiverkomplikasjonTableDefs(String[] giverkomplikasjonTableDefs) {
		this.giverkomplikasjonTableDefs = giverkomplikasjonTableDefs;
	}


	public VigilansSelect getVigilansSelect() {
		return vigilansSelect;
	}


	public void setVigilansSelect(VigilansSelect vigilansSelect) {
		this.vigilansSelect = vigilansSelect;
	}


	public String[] getPasientMeldingTableDefs() {
		return pasientMeldingTableDefs;
	}


	public void setPasientMeldingTableDefs(String[] pasientMeldingTableDefs) {
		this.pasientMeldingTableDefs = pasientMeldingTableDefs;
	}


	public String[] getGiverMeldingTableDefs() {
		return giverMeldingTableDefs;
	}


	public void setGiverMeldingTableDefs(String[] giverMeldingTableDefs) {
		this.giverMeldingTableDefs = giverMeldingTableDefs;
	}


	public String[] getAnnenMeldingTableDefs() {
		return annenMeldingTableDefs;
	}


	public void setAnnenMeldingTableDefs(String[] annenMeldingTableDefs) {
		this.annenMeldingTableDefs = annenMeldingTableDefs;
	}


	public String getSelectpasientMeldingSQL() {
		return selectpasientMeldingSQL;
	}


	public void setSelectpasientMeldingSQL(String selectpasientMeldingSQL) {
		this.selectpasientMeldingSQL = selectpasientMeldingSQL;
	}


	public String getSelectgiverMeldingSQL() {
		return selectgiverMeldingSQL;
	}


	public void setSelectgiverMeldingSQL(String selectgiverMeldingSQL) {
		this.selectgiverMeldingSQL = selectgiverMeldingSQL;
	}


	public String getSelectannenMeldingSQL() {
		return selectannenMeldingSQL;
	}


	public void setSelectannenMeldingSQL(String selectannenMeldingSQL) {
		this.selectannenMeldingSQL = selectannenMeldingSQL;
	}


	public String[] getVigilandsMeldingTableDefs() {
		return vigilandsMeldingTableDefs;
	}


	public void setVigilandsMeldingTableDefs(String[] vigilandsMeldingTableDefs) {
		this.vigilandsMeldingTableDefs = vigilandsMeldingTableDefs;
	}


	public String getSelectvigilansMeldingSQL() {
		return selectvigilansMeldingSQL;
	}


	public void setSelectvigilansMeldingSQL(String selectvigilansMeldingSQL) {
		this.selectvigilansMeldingSQL = selectvigilansMeldingSQL;
	}


	@Override
	public List collectMessages() {
		vigilansSelect = new VigilansSelect(getDataSource(),selectvigilansMeldingSQL,vigilandsMeldingTableDefs);
		List meldinger = vigilansSelect.execute();
		return meldinger;
	}
	/**
	 * collectMessagesMarks
	 * Denne rutinen henter alle vigilansmeldingermeldinger med en gitt type merknader
	 * @param types
	 * @return List en liste over meldinger
	 */
	public List collectMessagesMarks(String merknad){
		List meldinger = null;
		vigilansSelect = new VigilansSelect(getDataSource(),selectvigilansMeldingmerknaderSQL,vigilandsMeldingTableDefs);
		int type = Types.VARCHAR;
		vigilansSelect.declareParameter(new SqlParameter(type));
		meldinger = vigilansSelect.execute(merknad);
		return meldinger;
	}
	/**
	 * collectMessagesbytypes
	 * Denne rutinen henter alle vigilansmeldingermeldinger med en gitt status
	 * @param types
	 * @return List en liste over meldinger
	 */
	public List collectMessagesbytypes(String types){
		List meldinger = null;
		if (types.equals("Levert")){
			vigilansSelect = new VigilansSelect(getDataSource(),selectvigilansMeldingnullSQL,vigilandsMeldingTableDefs);
			meldinger = vigilansSelect.execute();
		}
		String sql = selectvigilansMeldingtypesSQL;
		String params = types;
		if (types.equals("Alle unntatt avviste")){
			sql = selectvigilansMeldingikkeavvistSQL;
			params = "Avvist";
		}
		vigilansSelect = new VigilansSelect(getDataSource(),sql,vigilandsMeldingTableDefs);
		int type = Types.VARCHAR;
		vigilansSelect.declareParameter(new SqlParameter(type));
		List fleremeldinger = vigilansSelect.execute(params);
		if (meldinger == null){
			meldinger = fleremeldinger;
		}else
			meldinger.addAll(fleremeldinger);
		return meldinger;
	}
	/**
	 * collectMessages
	 * Denne rutinen henter alle vigilansmeldingermeldinger innenfor en gitt tidsperiode
	 * @param start startperiode
	 * @param end slutt tidsperiode
	 * @return List en liste over meldinger
	 */
	public List collectMessages(String start,String end){
		List meldinger = null;
		String sql = selectvigilansMeldingtimeperiodSQL;
		if (!timeperiodType)
			sql = selectvigilansMeldinghentperiodSQL;
		vigilansSelect = new VigilansSelect(getDataSource(),sql,vigilandsMeldingTableDefs);
		int stype = Types.DATE;
		int etype = Types.DATE;
		SqlParameter param = new SqlParameter(stype);
		SqlParameter param1 = new SqlParameter(etype);
		vigilansSelect.declareParameter(param);
		vigilansSelect.declareParameter(param1);
		meldinger = vigilansSelect.execute(start,end);
		return meldinger;
	}	
	public List collectPasientMeldinger(List<Vigilansmelding>meldinger){
		
		return null;
	}
	public List collectGiverMeldinger(List<Vigilansmelding>meldinger){
		
		return null;
	}
	/**
	 * collectAnnenMeldinger
	 * Denne rutinen henter all informasjon om alle meldinger gitt en liste over vigilansmeldinger
	 * @param meldinger En liste over vigilansmeldinger
	 * @return Map En samling informasjon om alle meldinger
	 */
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger){
		long p1 = 0;
		List andreMeldinger = new ArrayList<Annenkomplikasjon>();
		List pasientMeldinger = new ArrayList<Pasientkomplikasjon>();
		List giverMeldinger = new ArrayList<Giverkomplikasjon>();
		alleMeldinger = new HashMap<String,List>();
		annenmeldingSelect = new AnnenmeldingSelect(getDataSource(),selectannenMeldingSQL,annenMeldingTableDefs);
		int type = Types.INTEGER;
		annenmeldingSelect.declareParameter(new SqlParameter(type));
		for (Vigilansmelding melding : meldinger){
			Long id = melding.getMeldeid();
			p1 = id.longValue();
			List delMelding = velgMeldinger(id,type);
			if (delMeldingKey.equals(andreKey) && delMelding != null && !delMelding.isEmpty()){
				andreMeldinger.add(delMelding.get(0));
			}
			if (delMeldingKey.equals(pasientKey) && delMelding != null && !delMelding.isEmpty()){
				pasientMeldinger.add(delMelding.get(0));
			}	
			if (delMeldingKey.equals(giverKey) && delMelding != null && !delMelding.isEmpty()){
				giverMeldinger.add(delMelding.get(0));
			}		
		}
		alleMeldinger.put(andreKey, andreMeldinger);
		alleMeldinger.put(pasientKey, pasientMeldinger);
		alleMeldinger.put(giverKey, giverMeldinger);
		return alleMeldinger;
	}
	
	/**
	 * selectMeldinger
	 * Denne rutinen henter alle meldinger til en melder basert på en meldingsid
	 * @param meldingsNokkel
	 * @return
	 */
	public Map<String,List> selectMeldinger (String meldeid){
		Long meldId  = Long.valueOf(meldeid);
		int type = Types.INTEGER;
		alleMeldinger = new HashMap<String,List>();
		vigilansSelect = new VigilansSelect(getDataSource(),selectvigilansMeldingidSQL,vigilandsMeldingTableDefs);
		vigilansSelect.declareParameter(new SqlParameter(type));
		List meldinger = vigilansSelect.execute(meldId);
		alleMeldinger.put(meldeid, meldinger);
		if (!meldinger.isEmpty()){
			Vigilansmelding melding = (Vigilansmelding)meldinger.get(0);
			Long mId = melding.getMeldeid();
			int nType = Types.INTEGER;
			List delMelding = velgMeldinger(mId,nType);
			alleMeldinger.put(delMeldingKey, delMelding);
		}
		return alleMeldinger;
	}
	/**
	 * velgMeldinger
	 * Denne rutinen henter en delmelding til en vigilansmelding basert på meldingsid.
	 * En delmelding er enten av type annenkomplikasjon,pasientkomplikasjon eller giverkomplikasjon
	 * @param mId  meldingsid
	 * @param nType
	 * @return
	 */
	private List velgMeldinger(Long mId,int nType){
		andremeldingSelect = new AnnenkomplikasjonSelect(getDataSource(),selectannenKomplikasjonSQL, annenkomplikasjonTableDefs);
		andremeldingSelect.declareParameter(new SqlParameter(nType));
		List<Vigilansmelding> delMeldinger = andremeldingSelect.execute(mId);
		delMeldingKey = andreKey;
		if (!delMeldinger.isEmpty()){
			List klassifikasjoner = velgKomplikasjonklassifikasjon(mId, nType,annenkomplikasjonSQL);
			alleMeldinger.put(klassifikasjonKey,klassifikasjoner);
		}
		if (delMeldinger.isEmpty()){
			andremeldingSelect = null;
			delMeldingKey = pasientKey;
			pasientmeldingSelect = new PasientkomplikasjonSelect(getDataSource(),selectpasientKomplikasjonSQL,pasientkomplikasjonTableDefs);
			pasientmeldingSelect.declareParameter(new SqlParameter(nType));
			delMeldinger = pasientmeldingSelect.execute(mId);
			Pasientkomplikasjon pasientKomplikasjon  = null;
			if (delMeldinger != null && !delMeldinger.isEmpty()){
				pasientKomplikasjon = (Pasientkomplikasjon)delMeldinger.get(0);
				Long tId = pasientKomplikasjon.getTransfusjonsId();
				List transfusjoner = velgTransfusjon(tId, nType);
				alleMeldinger.put(transfusjonsKey, transfusjoner);
				Transfusjon transfusjon = null;
				if(transfusjoner != null && !transfusjoner.isEmpty()){
					transfusjon = (Transfusjon)transfusjoner.get(0);
					Long pId = transfusjon.getPasient_Id();
					List pasienter =  velgPasient(pId, nType);
					alleMeldinger.put(pasientenKey,pasienter);
					Pasient pasient = null;
					if(pasienter != null && !pasienter.isEmpty()){
						pasient = (Pasient)pasienter.get(0);
						List sykdommer = velgSykdom(pId, nType);
						alleMeldinger.put(sykdomKey,sykdommer);
						List tiltak = velgTiltak(pId, nType);
						if (tiltak != null && !tiltak.isEmpty()){
							alleMeldinger.put(tiltakKey, tiltak);
							Tiltak tiltaket = (Tiltak)tiltak.get(0);
							Long ftId = tiltaket.getTiltakid();
							List forebyggendeTiltak =  velgforebyggendeTiltak(ftId, nType);
							if (forebyggendeTiltak != null && !forebyggendeTiltak.isEmpty()){
								alleMeldinger.put(forebyggendetiltakKey, forebyggendeTiltak);
							}
							
						}
					}
					List<Blodprodukt> blodprodukter = velgblodProdukter(tId, nType);
					alleMeldinger.put(blodproduktKey, blodprodukter);
					List utredninger = velgUtredning(mId, nType);
					alleMeldinger.put(utredningKey, utredninger);
					List produktEgenskaper = new ArrayList<Produktegenskap>();
					if (blodprodukter != null && !blodprodukter.isEmpty()){
						for (Blodprodukt blodProdukt :blodprodukter){
							Long bId = blodProdukt.getBlodProduktId();
							List egenskaper = velgproduktEgenskap(bId, nType);
							produktEgenskaper.addAll(egenskaper);
						}						
					}
				
					alleMeldinger.put(produktegenskapKey, produktEgenskaper);
				}
				List klassifikasjoner = velgKomplikasjonklassifikasjon(mId, nType,komplikasjonSQL);
				alleMeldinger.put(klassifikasjonKey,klassifikasjoner);
				List symptomer = velgSymptomer(mId, nType);
				alleMeldinger.put(symptomerKey,symptomer);
				
			}
		}
		if (delMeldinger.isEmpty()){
			pasientmeldingSelect = null;
			delMeldingKey = giverKey;
			givermeldingSelect = new GiverkomplikasjonSelect(getDataSource(),selectgiverKomplikasjonSQL,giverkomplikasjonTableDefs);
			givermeldingSelect.declareParameter(new SqlParameter(nType));
			delMeldinger = givermeldingSelect.execute(mId);
			Giverkomplikasjon komplikasjon = null;
			if (delMeldinger != null && !delMeldinger.isEmpty()){
				komplikasjon = (Giverkomplikasjon) delMeldinger.get(0);
				Long dId = komplikasjon.getDonasjonid();
				List donasjoner = velgDonasjon(dId,nType);
				alleMeldinger.put(donasjonKey,donasjoner);
				Donasjon donasjon = null;
				if (donasjoner != null && !donasjoner.isEmpty()){
					donasjon = (Donasjon)donasjoner.get(0);
					int gId = donasjon.getGiveId();
					Long giverId = new Long(gId);
					List givere = velgGiver(giverId,nType);
					alleMeldinger.put(giverenKey,givere);
				}
				List giveroppfolginger = velgGiveroppfolging(mId, nType);
				List komplikasjonsdiagnoser = velgkomplikasjonsdiagnoseGiver(mId, nType);
				alleMeldinger.put(giverOppfolgingKey, giveroppfolginger);
				alleMeldinger.put(giverkomplikasjondiagnoseKey, komplikasjonsdiagnoser);
				
				
			}
			
		}
		return delMeldinger;
		
	}
	/**
	 * velgdonasjon
	 * Denne rutinen henter donasjoner til en gitt giverkomplikasjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgDonasjon(Long dId, int nType){
		donasjonSelect = new DonasjonSelect(getDataSource(),selectDonasjonSQL,donasjonTabledefs);
		donasjonSelect.declareParameter(new SqlParameter(nType));
		List donasjoner = donasjonSelect.execute(dId);
		return donasjoner;
	}
	/**
	 * velgGiver
	 * Denne rutinen henter giver til en gitt donasjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgGiver(Long dId, int nType){
		giverSelect = new GiverSelect(getDataSource(),selectgiverSQL,giverTableDefs);
		giverSelect.declareParameter(new SqlParameter(nType));
		List givere = giverSelect.execute(dId);
		return givere;
	}
	/**
	 * velgGiveroppfolging
	 * Denne rutinen henter giveroppfolging til en gitt giverkomplikasjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgGiveroppfolging(Long dId, int nType){
		giveroppfolgingSelect = new GiveroppfolgingSelect(getDataSource(),giveroppfolgingSQL,giveroppfolgingTableDefs);
		giveroppfolgingSelect.declareParameter(new SqlParameter(nType));
		List giveoppfolginger = giveroppfolgingSelect.execute(dId);
		return giveoppfolginger;
	}
	/**
	 * velgkomplikasjonsdiagnoseGiver
	 * Denne rutinen henter komplikasjonsdiagnoser til en gitt giverkomplikasjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgkomplikasjonsdiagnoseGiver(Long dId, int nType){
		giverkomplikasjonSelect = new KomplikasjonsdiagnoseGiverSelect(getDataSource(),komplikasjonsdiagnosegiverSQL,komplikasjonsdiagnosegiverTableDefs);
		giverkomplikasjonSelect.declareParameter(new SqlParameter(nType));
		List komplikasjonsdiagnoser = giverkomplikasjonSelect.execute(dId);
		return komplikasjonsdiagnoser;
	}
	/**
	 * velgTransfusjon
	 * Denne rutinen henter transfusjon til en gitt pasientkomplikasjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgTransfusjon(Long dId, int nType){
		transfusjonSelect = new TransfusjonSelect(getDataSource(),selecttransfusjonSQL,transfusjonTableDefs);
		transfusjonSelect.declareParameter(new SqlParameter(nType));
		List transfusjoner = transfusjonSelect.execute(dId);
		return transfusjoner;
	}
	/**
	 * velgPasient
	 * Denne rutinen henter pasient til en gitt transfusjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgPasient(Long dId, int nType){
		pasientSelect = new PasientSelect(getDataSource(),selectPasientSQL,pasientTableDefs);
		pasientSelect.declareParameter(new SqlParameter(nType));
		List pasienter = pasientSelect.execute(dId);
		return pasienter;
	}
	/**
	 * velgSykdom
	 * Denne rutinen henter sykdommer til en gitt pasient
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgSykdom(Long dId, int nType){
		sykdomSelect = new SykdomSelect(getDataSource(),selectSykdomSQL,sykdomTableDefs);
		sykdomSelect.declareParameter(new SqlParameter(nType));
		List sykdommer = sykdomSelect.execute(dId);
		return sykdommer;
	}
	/**
	 * velgUtredning
	 * Denne rutinen henter utredninger til en gitt pasientkomplikasjon
	 * Denne tabellen inneholder årsaksdetaljer knyttet til klasifikasjonen
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgUtredning(Long dId, int nType){
		utredningSelect = new UtredningSelect(getDataSource(),utredningSQL,utredningTableDefs);
		utredningSelect.declareParameter(new SqlParameter(nType));
		List utredninger = utredningSelect.execute(dId);
		return utredninger;
	}
	/**
	 * velgblodProdukter
	 * Denne rutinen henter blodprodukter til en gitt transfusjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgblodProdukter(Long dId, int nType){
		blodproduktSelect = new BlodproduktSelect(getDataSource(),blodProduktSQL,blodproduktTableDefs);
		blodproduktSelect.declareParameter(new SqlParameter(nType));
		List blodprodukter = blodproduktSelect.execute(dId);
		return blodprodukter;
	}
	/**
	 * velgproduktEgenskap
	 * Denne rutinen henter produktegenskaper til et gitt blodprodukt
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgproduktEgenskap(Long dId, int nType){
		produktegenskapSelect = new ProduktegenskapSelect(getDataSource(),produktegenskapSQL,produktegenskapTableDefs);
		produktegenskapSelect.declareParameter(new SqlParameter(nType));
		List egenskaper = produktegenskapSelect.execute(dId);
		return egenskaper;
	}		
	/**
	 * velgKomplikasjonklassifikasjon
	 * Denne rutinen henter klassifikasjoner til en gitt pasientkomplikasjon eller annen komplikasjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgKomplikasjonklassifikasjon(Long dId, int nType,String sql){
		komplikasjonklassifikasjonSelect = new KomplikasjonklassifikasjonSelect(getDataSource(),sql,komplikasjonTableDefs);
		komplikasjonklassifikasjonSelect.declareParameter(new SqlParameter(nType));
		List klassifikasjoner = komplikasjonklassifikasjonSelect.execute(dId);
		return klassifikasjoner;
	}
	/**
	 * velgSymptomer
	 * Denne rutinen henter symptomer til en gitt pasientkomplikasjon eller annen komplikasjon
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgSymptomer(Long dId, int nType){
		symptomerSelect = new SymptomerSelect(getDataSource(),symptomerSQL,symptomerTableDefs);
		symptomerSelect.declareParameter(new SqlParameter(nType));
		List symptomer = symptomerSelect.execute(dId);
		return symptomer;
	}
	/**
	 * velgTiltak
	 * Denne rutinen henter tiltak til en gitt pasientkomplikasjon 
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgTiltak(Long dId, int nType){
		tiltakSelect = new TiltakSelect(getDataSource(),tiltakSQL,tiltakTableDefs);
		tiltakSelect.declareParameter(new SqlParameter(nType));
		List tiltak = tiltakSelect.execute(dId);
		return tiltak;
	}
	/**
	 * velgforebyggendeTiltak
	 * Denne rutinen henter tiltak til en gitt pasientkomplikasjon 
	 * @param dId
	 * @param nType
	 * @return
	 */
	private List velgforebyggendeTiltak(Long dId, int nType){
		forebyggendetiltakSelect = new ForebyggendetiltakSelect(getDataSource(),forebyggendetiltakSQL,forebyggendetiltakTableDefs);
		forebyggendetiltakSelect.declareParameter(new SqlParameter(nType));
		List forebyggendetiltak = forebyggendetiltakSelect.execute(dId);
		return forebyggendetiltak;
	}		
	
}
