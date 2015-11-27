package no.naks.biovigilans_admin.web.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

import org.restlet.data.Form;
import org.restlet.data.Parameter;

import edu.unc.ils.mrc.hive2.api.HiveConcept;

/**
 * @author olj
 * For Restlet er dette representation klassen for Innmelding
 * 
 */
public class Innmelding  implements Serializable {
	
	private ArrayList<HiveConcept> hivePersonConcepts; //Hovegruppe for personer (pasientgrupper)
	private ArrayList<HiveConcept> hivehelseConcepts; //Hovedgruppe for helsepersonell
	private ArrayList<HiveConcept> pasientgrupper; 	// Undergrupper for pasienter
	private ArrayList<HiveConcept> personell;		// Undergrupper for helsepersonell
	
	private HiveConcept conceptHelse;
	private HiveConcept conceptPerson;
	
	private QName qNameperson;
	private QName qNamehelse;
	
	private Map formMap; // Inneholder brukers input verdier fra skjermbildet
	private String[] formNames; // Inneholder navn på input felt i skjermbildet
	
	private String tittel;
	private String begrunnelse;
	private Date oppstartDato;
	private Date ferdigDato;
	private String accountRef;
	private String oppstartSDato;
	private String ferdigSDato;
	private String undef;
	private String status = "";
	private String internledernavn = "";
	private String internledertittel = "";
	private String internlederepost = "";
	private String internlederinstistusjon = "";
	
	private ArrayList<String>pasientgruppe;
	private ArrayList<String>pasienterValgt;
	private ArrayList<String>helsepersonell;
	private ArrayList<String>helsepersonellValgt;
	
	private String buttonPasientvalgt = null;
	private String buttonPersonellvalgt = null;
	private String buttonPasientfjern = null;
	private String buttonPersonellfjern = null;

	
	public Innmelding() {
		super();
		qNameperson = new QName("http://www.nlm.nih.gov/mesh/D009272#concept","");
		qNamehelse = new QName("http://www.nlm.nih.gov/mesh/D006282#concept","");
		
		tittel = "tittel";
		begrunnelse = "begrunnelse";
		oppstartDato = Calendar.getInstance().getTime();        
		ferdigDato = Calendar.getInstance().getTime();        
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		oppstartSDato = df.format(oppstartDato);
		ferdigSDato = df.format(ferdigDato)	;

		pasientgruppe = new ArrayList<String>();

		pasienterValgt = new ArrayList<String>();
		helsepersonell = new ArrayList<String>();
		helsepersonellValgt = new ArrayList<String>();
	
		formMap = new HashMap<String,String>();
		
	}

	/**
	 * buildPasientgruppe
	 * Denne rutinen bygge opp grupper av personer (pasienter til å vises som nedtrekk
	 * 
	 */
	private void buildPasientgruppe(){
		for (HiveConcept personConcept : hivePersonConcepts){
			pasientgruppe.add(personConcept.getPrefLabel());
		}
		for (HiveConcept personConcept : pasientgrupper){
			pasientgruppe.add(personConcept.getPrefLabel());
		}
	}
	/**
	 * buldHelsegruppe
	 * Denne rutinen bygger opp grupper av helsepersonell til å vises som nedtrekk 
	 */
	private void buildHelsegruppe(){
		for (HiveConcept helseConcept : hivehelseConcepts){
			helsepersonell.add(helseConcept.getPrefLabel());
		}
		for (HiveConcept helseConcept : personell){
			helsepersonell.add(helseConcept.getPrefLabel());
		}
	}
	/**
	 * setValues
	 * Denne rutinen setter ale verdier mottatt fra bruker.
	 * Verdier må lagres avhengig av hvilke knapper bruker har valgt
	 * @param entry
	 */
	public void setValues(Parameter entry){
		String name = entry.getName();
		String value = entry.getValue();
		boolean finnes = formMap.containsKey(name);
		formMap.put(name, value);
	
	}
	
	public void buildGroups(){
		if (pasientgruppe.isEmpty())
			buildPasientgruppe();
		if (helsepersonell.isEmpty())
			buildHelsegruppe();
	}

	public HiveConcept getConceptHelse() {
		return conceptHelse;
	}
	public void setConceptHelse(HiveConcept conceptHelse) {
		this.conceptHelse = conceptHelse;
	}
	public HiveConcept getConceptPerson() {
		return conceptPerson;
	}
	public void setConceptPerson(HiveConcept conceptPerson) {
		this.conceptPerson = conceptPerson;
	}
	public ArrayList<HiveConcept> getPasientgrupper() {
		if (pasientgrupper == null)
			pasientgrupper = new ArrayList<HiveConcept>();
		return pasientgrupper;
	}
	public void setPasientgrupper(ArrayList<HiveConcept> pasientgrupper) {
		this.pasientgrupper = pasientgrupper;
	
	}
	public ArrayList<HiveConcept> getPersonell() {
		if (personell == null)
			personell = new ArrayList<HiveConcept>();
		return personell;
	}
	public void setPersonell(ArrayList<HiveConcept> personell) {
		this.personell = personell;
	
	}	
	public ArrayList<HiveConcept> getHivePersonConcepts() {
		return hivePersonConcepts;
	}
	public void setHivePersonConcepts(ArrayList<HiveConcept> hivePersonConcepts) {
		this.hivePersonConcepts = hivePersonConcepts;
	
	}
	public ArrayList<HiveConcept> getHivehelseConcepts() {
		return hivehelseConcepts;
	}
	public void setHivehelseConcepts(ArrayList<HiveConcept> hivehelseConcepts) {
		this.hivehelseConcepts = hivehelseConcepts;
	}
	public QName getqNameperson() {
		return qNameperson;
	}
	public void setqNameperson(QName qNameperson) {
		this.qNameperson = qNameperson;
	}
	public QName getqNamehelse() {
		return qNamehelse;
	}
	public void setqNamehelse(QName qNamehelse) {
		this.qNamehelse = qNamehelse;
	}
	public String getInternledernavn() {
		String leder = formNames[6];
		String ledernavn = (String)formMap.get(leder);
		if (ledernavn == null)
			ledernavn = "";
		 internledernavn = ledernavn;
		return internledernavn;
	}
	public void setInternledernavn(String internledernavn) {
		this.internledernavn = internledernavn;
	}
	public String getInternledertittel() {
		String leder = formNames[7];
		String ledertittel = (String)formMap.get(leder);
		if (ledertittel == null)
			ledertittel = "";
		 internledertittel = ledertittel;
		return internledertittel;
	}
	public void setInternledertittel(String internledertittel) {
		this.internledertittel = internledertittel;
	}
	public String getInternlederepost() {
		String leder = formNames[8];
		String lederepost = (String)formMap.get(leder);
		if (lederepost == null)
			lederepost = "";
		 internlederepost = lederepost;
		return internlederepost;
	}
	public void setInternlederepost(String internlederepost) {
		this.internlederepost = internlederepost;
	}
	public String getInternlederinstistusjon() {
		return internlederinstistusjon;
	}
	public void setInternlederinstistusjon(String internlederinstistusjon) {
		this.internlederinstistusjon = internlederinstistusjon;
	}
	public ArrayList<String> getPasienterValgt() {
		if (formNames == null || formNames.length <= 2)
			return pasienterValgt;
		String name = formNames[2];
		String pasient = (String)formMap.get(name);
		if (pasient == null)
			pasient = "";
		pasienterValgt.add(pasient);
		return pasienterValgt;
	}
	public void setPasienterValgt(ArrayList<String> pasienterValgt) {
		this.pasienterValgt = pasienterValgt;
	}
	public ArrayList<String> getHelsepersonell() {
		return helsepersonell;
	}
	public void setHelsepersonell(ArrayList<String> helsepersonell) {
		this.helsepersonell = helsepersonell;
	}
	public ArrayList<String> getHelsepersonellValgt() {
		if (formNames == null || formNames.length <= 3)
			return helsepersonellValgt;
		String name = formNames[3];
		String person = (String)formMap.get(name);
		if (person == null)
			person = "";
		helsepersonellValgt.add(person);
		return helsepersonellValgt;
	}
	public void setHelsepersonellValgt(ArrayList<String> helsepersonellValgt) {
		this.helsepersonellValgt = helsepersonellValgt;
	}
	public ArrayList<String> getPasientgruppe() {
		return pasientgruppe;
	}
	public void setPasientgruppe(ArrayList<String> pasientgruppe) {
		this.pasientgruppe = pasientgruppe;
	}
	
	public String[] getFormNames() {
		return formNames;
	}

	public void setFormNames(String[] formNames) {
		int size = formNames.length;
		if (this.formNames == null)
			this.formNames = new String[size];
		int i = 0;
		for (String name : formNames){
			this.formNames[i] = name;
			i++;
		}
	}

	public String getUndef() {
		return undef;
	}
	public void setUndef(String undef) {
		this.undef = undef;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOppstartSDato() {
		String nameDato = formNames[4];
		String sDato = (String)formMap.get(nameDato);
		if (sDato == null)
			sDato = "";
		oppstartSDato = sDato;
		
		try {
			oppstartDato = new SimpleDateFormat("YYYY-MM-DD", Locale.getDefault()).parse(sDato);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return oppstartSDato;
	}

	public void setOppstartSDato(String oppstartSDato) {
		this.oppstartSDato = oppstartSDato;
	}

	public String getFerdigSDato() {
		String nameDato = formNames[5];
		String sDato = (String)formMap.get(nameDato);
		if (sDato == null)
			sDato = "";
		ferdigSDato = sDato;
		try {
			ferdigDato = new SimpleDateFormat("YYYY-MM-DD", Locale.getDefault()).parse(sDato);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ferdigSDato;
	}

	public void setFerdigSDato(String ferdigSDato) {
		this.ferdigSDato = ferdigSDato;
	}

	public String getAccountRef() {
		return accountRef;
	}

	public void setAccountRef(String accountRef) {
		this.accountRef = accountRef;
	}

	public String getTittel() {
		String name = formNames[0];
		tittel = (String)formMap.get(name);
		if (tittel == null)
			tittel = "";
		return tittel;
	}
	public void setTittel(String tittel) {
		this.tittel = tittel;
			
	}
	public String getBegrunnelse() {
		String name = formNames[1];
		begrunnelse = (String)formMap.get(name);
		if (begrunnelse == null)
			begrunnelse = "";
		return begrunnelse;
	}
	public void setBegrunnelse(String begrunnelse) {
		this.begrunnelse = begrunnelse;
	}
	public Date getOppstartDato() {
		if (oppstartDato == null && oppstartSDato != null && !oppstartSDato.equals("")){
			try {
				ferdigDato = new SimpleDateFormat("YYYY-MM-DD", Locale.getDefault()).parse(oppstartSDato);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return oppstartDato;
	}
	public void setOppstartDato(Date oppstartDato) {
		this.oppstartDato = oppstartDato;
	}
	public Date getFerdigDato() {
		if (ferdigDato == null && ferdigSDato != null && !ferdigSDato.equals("")){
			try {
				ferdigDato = new SimpleDateFormat("YYYY-MM-DD", Locale.getDefault()).parse(ferdigSDato);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ferdigDato;
	}
	public void setFerdigDato(Date ferdigDato) {
		this.ferdigDato = ferdigDato;
	}
	
}
