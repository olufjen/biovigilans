package no.naks.biovigilans.felles.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;

/**
 * Denne web modellklassen representerer en melder i kontaktskjema
 * @author olj
 *
 */
public class MelderwebModel extends VigilansModel {
	
	/**
	 * Melders database modell klasse
	 */
	private Melder melder;
	
	private String meldernavn ="" ;
	private String melderepost ="";
	private String meldertlf ="";
	private String helseregion ="";
	private String helseforetak ="";
	private String sykehus="";
	private String anonymEpost = "";
	
	private String melderRegion = "";
	private String melderSykehus = "";
	private String[] helseRegioner;
	private List<String>helseRegionene; // Nedtrekk for helseregioner
	private String[] hfeneNord;
	private List<String> helseforetakNord; // Nedtrekk for helseforetak Nord
	private String[]hfeneMidt;
	private List<String> helseforetakMidt; //Nedtrekk for helseforetak Midtnorge
	private String[]hfeneVest;
	private List<String> helseforetakVest; //Nedtrekk for helseforetak helse Vest
	private String[]hfeneSor;
	private List<String> helseforetakSorost; //Nedtrekk for helseforetak sørøst
	
	private String[]sykehusHFfinnmark;
	private String[]sykehusHFNord;
	private String[]sykehusHFnordland;
	private String[]sykehusHFHelgeland;
	private String[]sykehusHFNtrond;
	private String[]sykehusHFOlav;
	private String[]sykehusHFMRoms;
	private String[]sykehusHFForde;
	private String[]sykehusHFbergen;
	private String[]sykehusHFFonna;
	private String[]sykehusHFstav;
	private String[]sykehusHFVpriv;
	private String[]sykehusHFsorland;
	private String[]sykehusHFtele;
	private String[]sykehusHFvestf;
	private String[]sykehusHFvviken;
	private String[]sykehusHFostf;
	private String[]sykehusHFoslo;
	private String[]sykehusHFahus;
	private String[]sykehusHFinnl;
	private String[]sykehusHFOpriv;
	
	private List<String> hfFinnmarksykehus;
	private List<String> hfNordsykehus;
	private List<String> hfNordlandsykehus;
	private List<String> hfHelgelandsykehus;
	private List<String> hfNtrondsykehus;
	private List<String> hfOlavsykehus;
	private List<String> hfMRomssykehus;
	private List<String> hfFordesykehus;
	private List<String> hfBergensykehus;
	private List<String> hfFonnasykehus;
	private List<String> hfStavsykehus;
	private List<String> hfVprivsykehus;
	private List<String> hfSortlandsykehus;
	private List<String> hfTelesykehus;
	private List<String> hfVestfsykehus;
	private List<String> hfVvikensykehus;
	private List<String> hfOstfsykehus;
	private List<String> hfOslosykehus;
	private List<String> hfAhussykehus;
	private List<String> hfInnlsykehus;
	private List<String> hfOprivsykehus;
	
	
	
	private Map<String,List> regioner;				// Inneholder HF for en gitt region
	private Map<String,List> sykehusene;			// Inneholder liste over sykehus til et gitt HF
	private List<String> valgtRegion;				// Inneholder Liste over HF i Valgt region
	private List<String> valgtHFsykehus;			// Inneholder liste over sykehus i valgt HF
	private List<Melder> melderInfo = null;
	private String melderPassord;
	private String nyPassord; // Nytt passord for eksistrende melder
	
	private String checkPassword = "";
	private String chekPwKey = "checkpassword";
	
	private String checkemail = "";
	private String chekemKey = "checkemail";
	private String changePasswd =""; // Dette får en annen verdi når melder ønsker å endre passord fra meldingsoversikt OLJ 31.10.18
	
	public MelderwebModel(){
		super();
		melder = new MelderImpl();
		helseRegionene = new ArrayList<String>();
		helseforetakNord = new ArrayList<String>();
		helseforetakMidt = new ArrayList<String>();
		helseforetakVest = new ArrayList<String>();
		helseforetakSorost = new ArrayList<String>();
		valgtRegion = new ArrayList<String>();
		valgtHFsykehus = new ArrayList<String>();
		regioner = new HashMap<String,List>(); 
		sykehusene = new HashMap<String,List>();
	}

	
	public String getChangePasswd() {
		return changePasswd;
	}


	public void setChangePasswd(String changePasswd) {
		this.changePasswd = changePasswd;
	}


	public String getNyPassord() {
		return nyPassord;
	}


	public void setNyPassord(String nyPassord) {
		this.nyPassord = nyPassord;
	}


	public String getCheckemail() {
		return checkemail;
	}


	public void setCheckemail(String checkemail) {
		this.checkemail = checkemail;
	}


	public String getChekemKey() {
		return chekemKey;
	}


	public void setChekemKey(String chekemKey) {
		this.chekemKey = chekemKey;
	}


	public String getCheckPassword() {
		return checkPassword;
	}


	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}


	public String getChekPwKey() {
		return chekPwKey;
	}


	public void setChekPwKey(String chekPwKey) {
		this.chekPwKey = chekPwKey;
	}


	public void setMelderPassord(String melderPassord) {
		this.melderPassord = melderPassord;
	}


	public String[] getSykehusHFfinnmark() {
		return sykehusHFfinnmark;
	}


	public void setSykehusHFfinnmark(String[] sykehusHFfinnmark) {
		this.sykehusHFfinnmark = sykehusHFfinnmark;
		if (this.hfFinnmarksykehus == null)
			this.hfFinnmarksykehus = new ArrayList<String>();
		for (String hus : sykehusHFfinnmark){
			this.hfFinnmarksykehus.add(hus);
		}
		sykehusene.put(helseforetakNord.get(0), this.hfFinnmarksykehus);
		
	}


	public String[] getSykehusHFNord() {
		return sykehusHFNord;
	}


	public void setSykehusHFNord(String[] sykehusHFNord) {
		this.sykehusHFNord = sykehusHFNord;
		if (this.hfNordsykehus == null)
			this.hfNordsykehus = new ArrayList<String>();
		for (String hus : sykehusHFNord){
			this.hfNordsykehus.add(hus);
		}
		sykehusene.put(helseforetakNord.get(1), this.hfNordsykehus);
	}


	public String[] getSykehusHFnordland() {
		return sykehusHFnordland;
	}


	public void setSykehusHFnordland(String[] sykehusHFnordland) {
		this.sykehusHFnordland = sykehusHFnordland;
		if (this.hfNordlandsykehus == null)
			this.hfNordlandsykehus = new ArrayList<String>();
		for (String hus : sykehusHFnordland){
			this.hfNordlandsykehus.add(hus);
		}
		sykehusene.put(helseforetakNord.get(2), this.hfNordlandsykehus);
		
	}


	public String[] getSykehusHFHelgeland() {
		return sykehusHFHelgeland;
	}


	public void setSykehusHFHelgeland(String[] sykehusHFHelgeland) {
		this.sykehusHFHelgeland = sykehusHFHelgeland;
		if (this.hfHelgelandsykehus == null)
			this.hfHelgelandsykehus = new ArrayList<String>();
		for (String hus : sykehusHFHelgeland){
			this.hfHelgelandsykehus.add(hus);
		}
		sykehusene.put(helseforetakNord.get(3), this.hfHelgelandsykehus);
	}


	public String[] getSykehusHFNtrond() {
		return sykehusHFNtrond;
	}


	public void setSykehusHFNtrond(String[] sykehusHFNtrond) {
		this.sykehusHFNtrond = sykehusHFNtrond;
		if (this.hfNtrondsykehus == null)
			this.hfNtrondsykehus = new ArrayList<String>();
		for (String hus : sykehusHFNtrond){
			this.hfNtrondsykehus.add(hus);
		}
		sykehusene.put(helseforetakMidt.get(0), this.hfNtrondsykehus);
	}


	public String[] getSykehusHFOlav() {
		return sykehusHFOlav;
	}


	public void setSykehusHFOlav(String[] sykehusHFOlav) {
		this.sykehusHFOlav = sykehusHFOlav;
		if (this.hfOlavsykehus == null)
			this.hfOlavsykehus = new ArrayList<String>();
		for (String hus :sykehusHFOlav){
			this.hfOlavsykehus.add(hus);
		}
		sykehusene.put(helseforetakMidt.get(1), this.hfOlavsykehus);
	}


	public String[] getSykehusHFMRoms() {
		return sykehusHFMRoms;
	}


	public void setSykehusHFMRoms(String[] sykehusHFMRoms) {
		this.sykehusHFMRoms = sykehusHFMRoms;
		if (this.hfMRomssykehus == null)
			this.hfMRomssykehus = new ArrayList<String>();
		for (String hus :sykehusHFMRoms){
			this.hfMRomssykehus.add(hus);
		}
		sykehusene.put(helseforetakMidt.get(2), this.hfMRomssykehus);
	}


	public String[] getSykehusHFForde() {
		return sykehusHFForde;
	}


	public void setSykehusHFForde(String[] sykehusHFForde) {
		this.sykehusHFForde = sykehusHFForde;
		if (this.hfFordesykehus == null)
			this.hfFordesykehus = new ArrayList<String>();
		for (String hus :sykehusHFForde){
			this.hfFordesykehus.add(hus);
		}
		sykehusene.put(helseforetakVest.get(0), this.hfFordesykehus);
	}


	public String[] getSykehusHFbergen() {
		return sykehusHFbergen;
	}


	public void setSykehusHFbergen(String[] sykehusHFbergen) {
		this.sykehusHFbergen = sykehusHFbergen;
		if (this.hfBergensykehus == null)
			this.hfBergensykehus = new ArrayList<String>();
		for (String hus :sykehusHFbergen){
			this.hfBergensykehus.add(hus);
		}
		sykehusene.put(helseforetakVest.get(1), this.hfBergensykehus);
	}


	public String[] getSykehusHFFonna() {
		return sykehusHFFonna;
	}


	public void setSykehusHFFonna(String[] sykehusHFFonna) {
		this.sykehusHFFonna = sykehusHFFonna;
		if (this.hfFonnasykehus == null)
			this.hfFonnasykehus = new ArrayList<String>();
		for (String hus :sykehusHFFonna){
			this.hfFonnasykehus.add(hus);
		}
		sykehusene.put(helseforetakVest.get(2), this.hfFonnasykehus);
	}


	public String[] getSykehusHFstav() {
		return sykehusHFstav;
	}


	public void setSykehusHFstav(String[] sykehusHFstav) {
		this.sykehusHFstav = sykehusHFstav;
		if (this.hfStavsykehus == null)
			this.hfStavsykehus = new ArrayList<String>();
		for (String hus :sykehusHFstav){
			this.hfStavsykehus.add(hus);
		}
		sykehusene.put(helseforetakVest.get(3), this.hfStavsykehus);
	}


	public String[] getSykehusHFVpriv() {
		return sykehusHFVpriv;
	}


	public void setSykehusHFVpriv(String[] sykehusHFVpriv) {
		this.sykehusHFVpriv = sykehusHFVpriv;
		if (this.hfVprivsykehus == null)
			this.hfVprivsykehus = new ArrayList<String>();
		for (String hus :sykehusHFVpriv){
			this.hfVprivsykehus.add(hus);
		}
		sykehusene.put(helseforetakVest.get(4), this.hfVprivsykehus);
	}


	public String[] getSykehusHFsorland() {
		return sykehusHFsorland;
	}


	public void setSykehusHFsorland(String[] sykehusHFsorland) {
		this.sykehusHFsorland = sykehusHFsorland;
		if (this.hfSortlandsykehus == null)
			this.hfSortlandsykehus = new ArrayList<String>();
		for (String hus :sykehusHFsorland){
			this.hfSortlandsykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(0), this.hfSortlandsykehus);
	}


	public String[] getSykehusHFtele() {
		return sykehusHFtele;
	}


	public void setSykehusHFtele(String[] sykehusHFtele) {
		this.sykehusHFtele = sykehusHFtele;
		if (this.hfTelesykehus == null)
			this.hfTelesykehus = new ArrayList<String>();
		for (String hus :sykehusHFtele){
			this.hfTelesykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(1), this.hfTelesykehus);
	}


	public String[] getSykehusHFvestf() {
		return sykehusHFvestf;
	}


	public void setSykehusHFvestf(String[] sykehusHFvestf) {
		this.sykehusHFvestf = sykehusHFvestf;
		if (this.hfVestfsykehus == null)
			this.hfVestfsykehus = new ArrayList<String>();
		for (String hus :sykehusHFvestf){
			this.hfVestfsykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(2), this.hfVestfsykehus);
	}


	public String[] getSykehusHFvviken() {
		return sykehusHFvviken;
	}


	public void setSykehusHFvviken(String[] sykehusHFvviken) {
		this.sykehusHFvviken = sykehusHFvviken;
		if (this.hfVvikensykehus == null)
			this.hfVvikensykehus = new ArrayList<String>();
		for (String hus :sykehusHFvviken){
			this.hfVvikensykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(3), this.hfVvikensykehus);
	}


	public String[] getSykehusHFostf() {
		return sykehusHFostf;
	}


	public void setSykehusHFostf(String[] sykehusHFostf) {
		this.sykehusHFostf = sykehusHFostf;
		if (this.hfOstfsykehus == null)
			this.hfOstfsykehus = new ArrayList<String>();
		for (String hus :sykehusHFostf){
			this.hfOstfsykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(4), this.hfOstfsykehus);
	}


	public String[] getSykehusHFoslo() {
		return sykehusHFoslo;
	}


	public void setSykehusHFoslo(String[] sykehusHFoslo) {
		this.sykehusHFoslo = sykehusHFoslo;
		if (this.hfOslosykehus == null)
			this.hfOslosykehus = new ArrayList<String>();
		for (String hus :sykehusHFoslo){
			this.hfOslosykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(5), this.hfOslosykehus);
	}


	public String[] getSykehusHFahus() {
		return sykehusHFahus;
	}


	public void setSykehusHFahus(String[] sykehusHFahus) {
		this.sykehusHFahus = sykehusHFahus;
		if (this.hfAhussykehus == null)
			this.hfAhussykehus = new ArrayList<String>();
		for (String hus :sykehusHFahus){
			this.hfAhussykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(6), this.hfAhussykehus);
	}


	public String[] getSykehusHFinnl() {
		return sykehusHFinnl;
	}


	public void setSykehusHFinnl(String[] sykehusHFinnl) {
		this.sykehusHFinnl = sykehusHFinnl;
		if (this.hfInnlsykehus == null)
			this.hfInnlsykehus = new ArrayList<String>();
		for (String hus :sykehusHFinnl){
			this.hfInnlsykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(7), this.hfInnlsykehus);
	}


	public String[] getSykehusHFOpriv() {
		return sykehusHFOpriv;
	}


	public void setSykehusHFOpriv(String[] sykehusHFOpriv) {
		this.sykehusHFOpriv = sykehusHFOpriv;
		if (this.hfOprivsykehus == null)
			this.hfOprivsykehus = new ArrayList<String>();
		for (String hus :sykehusHFOpriv){
			this.hfOprivsykehus.add(hus);
		}
		sykehusene.put(helseforetakSorost.get(8), this.hfOprivsykehus);
	}


	public String[] getHelseRegioner() {
		return helseRegioner;
	}


	public void setHelseRegioner(String[] helseRegioner) {
		this.helseRegioner = helseRegioner;
		for (String reg : helseRegioner){
			this.helseRegionene.add(reg);
		
		}
	}


	public List<String> getValgtHFsykehus() {
		return valgtHFsykehus;
	}


	public void setValgtHFsykehus(List<String> valgtHFsykehus) {
		this.valgtHFsykehus = valgtHFsykehus;
	}


	public List<String> getHelseRegionene() {
		return helseRegionene;
	}


	public void setHelseRegionene(List<String> helseRegionene) {
		this.helseRegionene = helseRegionene;
	}


	public String[] getHfeneNord() {
		return hfeneNord;
	}


	public void setHfeneNord(String[] hfeneNord) {
		this.hfeneNord = hfeneNord;
		for (String hf : hfeneNord){
			this.helseforetakNord.add(hf);
		}
		regioner.put(this.helseRegionene.get(0), this.helseforetakNord);
//		valgtRegion = this.helseforetakNord;
	}


	public List<String> getHelseforetakNord() {
		return helseforetakNord;
	}


	public void setHelseforetakNord(List<String> helseforetakNord) {
		this.helseforetakNord = helseforetakNord;
	}


	public String[] getHfeneMidt() {
		return hfeneMidt;
	}


	public void setHfeneMidt(String[] hfeneMidt) {
		this.hfeneMidt = hfeneMidt;
		for (String hf : hfeneMidt){
			this.helseforetakMidt.add(hf);
		}
		regioner.put(this.helseRegionene.get(1), this.helseforetakMidt);
	}


	public List<String> getHelseforetakMidt() {
		return helseforetakMidt;
	}


	public void setHelseforetakMidt(List<String> helseforetakMidt) {
		this.helseforetakMidt = helseforetakMidt;
	}


	public String[] getHfeneVest() {
		return hfeneVest;
	}


	public void setHfeneVest(String[] hfeneVest) {
		this.hfeneVest = hfeneVest;
		for (String hf : hfeneVest){
			this.helseforetakVest.add(hf);
		}
		regioner.put(this.helseRegionene.get(2), this.helseforetakVest);
	}


	public List<String> getHelseforetakVest() {
		return helseforetakVest;
	}


	public void setHelseforetakVest(List<String> helseforetakVest) {
		this.helseforetakVest = helseforetakVest;
	}


	public String[] getHfeneSor() {
		return hfeneSor;
	}


	public void setHfeneSor(String[] hfeneSor) {
		this.hfeneSor = hfeneSor;
		for (String hf : hfeneSor){
			this.helseforetakSorost.add(hf);
		}
		regioner.put(this.helseRegionene.get(3), this.helseforetakSorost);
	}


	public List<String> getHelseforetakSorost() {
		return helseforetakSorost;
	}


	public void setHelseforetakSorost(List<String> helseforetakSorost) {
		this.helseforetakSorost = helseforetakSorost;
	}


	public String getMeldernavn() {
		if (melder != null && melder.getMeldernavn() != null && !melder.getMeldernavn().isEmpty()){
			meldernavn = melder.getMeldernavn();
		}
		return meldernavn;
	}
	public String getMelderPassord() {
		if (melderPassord == null || melderPassord.isEmpty())
			melderPassord = "";
		if (melder != null && melder.getMelderPassord() != null && !melder.getMelderPassord().isEmpty()){
			melderPassord = melder.getMelderPassord();
		}
		
		return melderPassord;
	}

	public void setMeldernavn(String meldernavn) {
		this.meldernavn = meldernavn;
	}

	public String getMelderepost() {
		Map<String,String> userEntries = getFormMap();
		String field = "k-epost";
		String mPassord = "k-passord";
		melderepost = userEntries.get(field);
		melderPassord = userEntries.get(mPassord);
		if (melderPassord == null)
			melderPassord = "";
		if (melderepost == null && anonymEpost.equals("")){
			melderepost = "";
		}else if (!anonymEpost.equals("")) {
			melderepost = anonymEpost;
		}
		return melderepost;
	}
	public String getAnonymEpost(){
		return anonymEpost;
	}
	

	public void setAnonymEpost(String anonymEpost) {
		this.anonymEpost = anonymEpost;
	}

	public void setMelderepost(String melderepost) {
		this.melderepost = melderepost;
	}

	public String getMeldertlf() {
		return meldertlf;
	}

	public void setMeldertlf(String meldertlf) {
		this.meldertlf = meldertlf;
	}

	public String getHelseregion() {
		return helseregion;
	}

	public void setHelseregion(String helseregion) {
		this.helseregion = helseregion;
	}

	public String getHelseforetak() {
		return helseforetak;
	}

	public void setHelseforetak(String helseforetak) {
		this.helseforetak = helseforetak;
	}

	public String getSykehus() {
		return sykehus;
	}

	public void setSykehus(String sykehus) {
		this.sykehus = sykehus;
	}

	public Melder getMelder() {
		return melder;
	}

	public void setMelder(Melder melder) {
		this.melder = melder;
	}

	public List getValgtRegion() {
		return valgtRegion;
	}


	public void setValgtRegion(List valgtRegion) {
		this.valgtRegion = valgtRegion;
	}


	public void distributeTerms(){
		String[] formFields = getFormNames();
		String melderFields[] = {formFields[0],formFields[1],formFields[2],formFields[3],formFields[4],formFields[5],formFields[6]};
		melder.setMelderfieldMaps(melderFields);
	}
	
	public void saveValues(){
		String[] formFields = getFormNames();
		Map<String,String> userEntries = getFormMap(); // formMap inneholder verdier angitt av bruker
		String field = "k-passordsjekk";
		checkPassword = userEntries.get(field);
		String emeilField = "k-epostsjekk";
		checkemail = userEntries.get(emeilField);
		String nypwd = "k-passord";
		nyPassord = userEntries.get(nypwd);
		melder.setMelderFields(userEntries);
		
		/*for(String field: formFields ){
			String userEntry = userEntries.get(field);
			melder.saveField(field, userEntry);
		}
		*/
		melder.saveToMelder();
		melderPassord = melder.getMelderPassord();
/*
 * 
 */
		
		// For å kunne vise om kontaktskjema er forhåndsutfylt Må sjekkes !!!
/*		
		this.setMeldernavn(melder.getMeldernavn());
		this.setMeldertlf(melder.getMeldertlf());
		this.setHelseregion(melder.getHelseregion());
		this.setHelseforetak(melder.getHelseforetak());
		this.setSykehus(melder.getSykehus());
*/		
		
	}
	public void saveAnonym(){
		melder.setMelderepost(melderepost);
		melder.setMeldernavn("nn");
		melder.setMeldertlf("11");
		melder.setHelseforetak("nn");
		melder.setHelseregion("nn");
		melder.setSykehus("nn");
		melder.setMelderPassord("nn");
	}
	/**
	 * kontaktValues
	 * Denne rutinen henter frem melders opplysninger om region og sykehus etc
	 * @since 01.02.2019 Endret parameter fra Map rows til List<Melder> rows
	 * @param rows
	 * @return
	 */
	public boolean kontaktValues(List<Melder>  rows){
			melderInfo = rows;
			int nop = rows.size();
			String gittPassord =(String) getFormMap().get("k-passord");
/*
 * OLJ 07.11.18: Dersom melder kommer fra nytt passord funksjonen, så må man benytte melders nye passord			
 */
			if (gittPassord == null || gittPassord.isEmpty()){
				gittPassord = melder.getMelderPassord();
			}
			nyPassord = (String) getFormMap().get("k-passord");
			boolean found = false;
			for(Melder row:rows){
//				String pwd = "";
				String pwd = melder.getMelderPassord(); // Bruker decryptert passord OLJ 22.01..18
//				if (row.get("melderpassord") != null)
//					pwd = row.get("melderpassord").toString(); Bruker decryptert passord OLJ 22.01..18
				this.setMelderPassord(pwd);
				Long id = row.getMelderId();
				melder.setMelderId(id);
				melder.setMelderepost(this.getMelderepost());
				melder.setMelderPassord(pwd);
				if (pwd != null && pwd.equals(gittPassord)){
					found = true;
				
					String name ="";
					if (row.getMeldernavn() != null)
						name = row.getMeldernavn();
					String tlf = "";
					this.setMeldernavn(name);
					if (row.getMeldertlf()!= null)
						tlf = row.getMeldertlf();
					this.setMeldertlf(tlf);
					String helseregion = "";
					if (row.getHelseregion() != null)
						helseregion = row.getHelseregion();
					this.setHelseregion(helseregion);
					String helseforetak = "";
					if (row.getHelseforetak() != null)
						helseforetak = row.getHelseforetak();
					this.setHelseforetak(helseforetak);
					String sykehus = "";
					if (row.getSykehus()!= null)
						sykehus = row.getSykehus();
					this.setSykehus(sykehus);
					break;
				}
					
			}
			return found;
	}
	public void emptyMelder(){
		this.setMeldernavn("");
		this.setMeldertlf("");
		melder.setMelderId(null);
		this.setHelseregion("");
		this.setHelseforetak("");
		this.setSykehus("");
		this.setMelderepost("");
	}
	/**
	 * setValgtregion
	 * Denne rutinen kjøres når bruker har valgt en helseregion. Da lages det en korrekt liste av HF til regionen
	 */
	public void setValgtregion(){
	//	setKontaktinfo();
		Map<String,String> userEntries = getFormMap();
		String field = "k-helseregion";
		String mfield = "k-passordsjekk";
		String emeilField = "k-epostsjekk";
		melderRegion = userEntries.get(field);
		checkPassword = userEntries.get(mfield);
		checkemail = userEntries.get(emeilField);
		valgtRegion = null;
		valgtRegion = regioner.get(melderRegion);
		helseregion = melderRegion;  
	}
	
	/**
	 * setValgtsykehus
	 * Denne rutinen kjøres når bruker har valgt et HF og skal ha liste over tilgjengelige sykehus for dette HF 
	 */
	public void setValgtsykehus(){
	//	setKontaktinfo();
		Map<String,String> userEntries = getFormMap();
		String field = "k-helseforetak";
		melderSykehus = userEntries.get(field);
		valgtHFsykehus = null;
		valgtHFsykehus = sykehusene.get(melderSykehus);
		helseforetak = melderSykehus;
	}
	/**
	 * @since 01.02.2019 Endret parameter fra Map rows til List<Melder> rows
	 */
	private void setKontaktinfo(){
		if (melderInfo != null){
			for(Melder row:melderInfo){
				Long id = row.getMelderId();
				melder.setMelderId(id);
				String name = melder.getMeldernavn();
				if (name == null)
					name = "";
				if (row.getMeldernavn() != null)
					name = row.getMeldernavn();
				this.setMeldernavn(name);
				String tlf = melder.getMeldertlf();
				if (tlf == null)
					tlf = "";
				if (row.getMeldertlf()!= null)
					tlf = row.getMeldertlf();
				this.setMeldertlf(tlf);
				String pwd = "";
				if (row.getMelderPassord()!= null)
					pwd = row.getMelderPassord();
				this.setMelderPassord(pwd);
				String helseregion = melder.getHelseregion();
				if (helseregion == null)
					helseregion = "";
				if (row.getHelseregion()!= null)
					helseregion = row.getHelseregion();
				this.setHelseregion(helseregion);
				String helseforetak = melder.getHelseforetak();
				if (helseforetak == null)
					helseforetak = "";
				if (row.getHelseforetak() != null)
					helseforetak = row.getHelseforetak();
				this.setHelseforetak(helseforetak);
				String sykehus = melder.getSykehus();
				if (sykehus == null)
					sykehus = "";
				if (row.getSykehus() != null)
					sykehus = row.getSykehus();
				this.setSykehus(sykehus);
				

			}
		}
		if (melderInfo == null){
			if (melder.getMeldernavn() != null)
				this.setMeldernavn(melder.getMeldernavn());
			if (melder.getMeldertlf() != null)
				this.setMeldertlf(melder.getMeldertlf());
			if (melder.getMelderPassord() != null)
				this.setMelderPassord(melder.getMelderPassord());
			if (melder.getHelseregion() != null)
				this.setHelseregion(melder.getHelseregion());
			if (melder.getHelseforetak() != null)
				this.setHelseforetak(melder.getHelseforetak());
			if (melder.getSykehus() != null)
				this.setSykehus(melder.getSykehus());
		}
	}
}
