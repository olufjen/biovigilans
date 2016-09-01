package no.naks.biovigilans.felles.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import no.naks.biovigilans.model.Vigilansmelding;

import org.restlet.data.Parameter;

/**
 * Denne klassen er superklassen til alle web modelklasser som representerer et skjermbilde (et brukergrensesnitt)
 * @author olj
 *
 *
 */
public class VigilansModel {

	protected boolean lagret = false; // Satt true dersom session objects er lagret
	/**
	 * Inneholder brukers input verdier fra skjermbildet
	 */
	private Map<String,String> formMap; // Inneholder brukers input verdier fra skjermbildet
	
	/**
	 * Inneholder verdier for generate PDF file.
	 */
	private LinkedHashMap<String, String> formLinkHashmap ; // Inneholder verdier for generate PDF file.
	
	/**
	 * Inneholder navn på input felt i skjermbildet
	 */
	private String[] formNames; // Inneholder navn på input felt i skjermbildet
	private String accountRef;
	private Vigilansmelding vigilans;
	private Date meldingLevert;
	private String meldingsNokkel = "";
	private String meldLevert;
	/**
	 * Dato settes dersom dette er en oppfølgingsmelding
	 */
	protected Date hendelseDato = null; // Dato settes dersom dette er en oppfølgingsmelding
	
	public VigilansModel() {
		super();
		formMap = new HashMap<String,String>();
		formLinkHashmap = new LinkedHashMap<String,String>();
		// TODO Auto-generated constructor stub
	}


	public boolean isLagret() {
		return lagret;
	}

	public void setLagret(boolean lagret) {
		this.lagret = lagret;
	}

	public Date getMeldingLevert() {
		return meldingLevert;
	}


	public void setMeldingLevert(Date meldingLevert) throws ParseException {
		this.meldingLevert = meldingLevert;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (meldingLevert != null){
			try {
				meldLevert = dateFormat.format(meldingLevert);
				Date datoLevert =   dateFormat.parse(meldLevert);
			} catch (ParseException e) {
				System.out.println("date format problem: " + e.toString());
			}			
		}

	}


	public Date getHendelseDato() {
		return hendelseDato;
	}


	public void setHendelseDato(Date hendelseDato) {
		this.hendelseDato = hendelseDato;
	}

	public String getMeldingsNokkel() {
		return meldingsNokkel;
	}


	public void setMeldingsNokkel(String meldingsNokkel) {
		this.meldingsNokkel = meldingsNokkel;
	}


	public String getMeldLevert() {
		return meldLevert;
	}


	public void setMeldLevert(String meldLevert) {
		this.meldLevert = meldLevert;
	}


	/**
	 * setValues
	 * Denne rutinen setter alle verdier mottatt fra bruker.
	 * Verdier må lagres avhengig av hvilke knapper bruker har valgt
	 * @param entry
	 */
	public void setValues(Parameter entry){
		String name = entry.getName();
		String value = entry.getValue();
		/*
		boolean finnes = formMap.containsKey(name);
		if(finnes){
			String val =  formMap.get(name);
			value = val + "-" + value; 
		}*/
		formMap.put(name, value);
//		formLinkHashmap.put(name, value);
	}


	public Map getFormMap() {
		return formMap;
	}


	public void setFormMap(Map formMap) {
		this.formMap = formMap;
	}


	public LinkedHashMap<String, String> getFormLinkHashmap() {
		return formLinkHashmap;
	}


	public String[] getFormNames() {
		return formNames;
	}


	public void setFormNames(String[] formNames) {
		this.formNames = formNames;
	}


	public String getAccountRef() {
		return accountRef;
	}


	public void setAccountRef(String accountRef) {
		this.accountRef = accountRef;
	}


	public Vigilansmelding getVigilans() {
		return vigilans;
	}


	public void setVigilans(Vigilansmelding vigilans) {
		this.vigilans = vigilans;
	}
	
	/*public void distributeTerms(){
		String[] formFields = getFormNames();
		String donasjonFields[] = {formFields[6],formFields[7],formFields[8],formFields[9],formFields[10],formFields[11],formFields[12],formFields[25]};
		//vigilans.set .setDonasjonsfieldMaps(donasjonFields);
	}
	*/
	
}
