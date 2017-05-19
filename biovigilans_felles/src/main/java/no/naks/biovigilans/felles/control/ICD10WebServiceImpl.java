package no.naks.biovigilans.felles.control;
import java.io.File;  
import java.util.ArrayList;  
import java.util.List;

import javax.xml.bind.JAXBContext;  
import javax.xml.bind.JAXBException;  
import javax.xml.bind.Unmarshaller; 

import no.naks.biovigilans.felles.xml.ICD10CMIndex;
import no.naks.biovigilans.felles.xml.Letter;
import no.naks.biovigilans.felles.xml.MainTerm;
import no.naks.biovigilans.felles.xml.no.ICD10;
import no.naks.biovigilans.felles.xml.no.KodeNivaa1;
import no.naks.biovigilans.felles.xml.no.KodeNivaa2;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa1;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa2;


/**
 * Denne klassen leser xml definisjoner av ICD10 og gjør disse tilgjengelig for søk og oppslag
 * @author olj
 * 
 */
public class ICD10WebServiceImpl implements ICD10WebService {
/*
 * Engelsk ICD10
 */
	private Unmarshaller jaxbUnmarshaller ;
	private String xmlFillocation;
	private File xmlFile;
	private List<MainTerm> terms;
	private List<Letter> letters; 
	private ICD10CMIndex icd10Index;
/*
 * Norsk ICD10
 */
	private ICD10 noICD10;
	private List<TematiskGruppeNivaa1> nivaa1;
	private List<TematiskGruppeNivaa2> nivaa2;
	private List<KodeNivaa1> kodeNivaa1;
	private List<KodeNivaa2> kodeNivaa2;
	
	public  ICD10WebServiceImpl() {
		super();
		nivaa1 = new ArrayList<TematiskGruppeNivaa1>();
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(ICD10.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		try {
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 System.out.println("ICD10 norsk utgave felles webservice created");
	}		

	public ICD10WebServiceImpl(String eng) {
		super();
		terms = new ArrayList<MainTerm>();
		
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(ICD10CMIndex.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		try {
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 System.out.println("ICD10 felles engelsk webservice created");
	}

	public ICD10 getNoICD10() {
		return noICD10;
	}

	public void setNoICD10(ICD10 noICD10) {
		this.noICD10 = noICD10;
	}

	public List<TematiskGruppeNivaa1> getNivaa1() {
		return nivaa1;
	}

	public void setNivaa1(List<TematiskGruppeNivaa1> nivaa1) {
		this.nivaa1 = nivaa1;
	}

	public List<TematiskGruppeNivaa2> getNivaa2() {
		return nivaa2;
	}

	public void setNivaa2(List<TematiskGruppeNivaa2> nivaa2) {
		this.nivaa2 = nivaa2;
	}

	public List<KodeNivaa1> getKodeNivaa1() {
		return kodeNivaa1;
	}

	public void setKodeNivaa1(List<KodeNivaa1> kodeNivaa1) {
		this.kodeNivaa1 = kodeNivaa1;
	}

	public List<KodeNivaa2> getKodeNivaa2() {
		return kodeNivaa2;
	}

	public void setKodeNivaa2(List<KodeNivaa2> kodeNivaa2) {
		this.kodeNivaa2 = kodeNivaa2;
	}

	public Unmarshaller getJaxbUnmarshaller() {
		return jaxbUnmarshaller;
	}

	public void setJaxbUnmarshaller(Unmarshaller jaxbUnmarshaller) {
		this.jaxbUnmarshaller = jaxbUnmarshaller;
	}

	public String getXmlFillocation() {
		return xmlFillocation;
	}

	public void setXmlFillocation(String xmlFillocation) {
		this.xmlFillocation = xmlFillocation;
	}
	

	public File getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	public List<MainTerm> getTerms() {
		return terms;
	}

	public void setTerms(List<MainTerm> terms) {
		this.terms = terms;
	}
	

	public List<Letter> getLetters() {
		return letters;
	}

	public void setLetters(List<Letter> letters) {
		this.letters = letters;
	}

	@Override
	public void readXml() {
		 xmlFile = new File(xmlFillocation);  
		try {
			noICD10 = (ICD10) jaxbUnmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	letters = icd10Index.getLetter();
		if (noICD10 != null)
			nivaa1 = noICD10.getTematiskGruppeNivaa1();
	}

/*	
	@Override
	public void readXml() {
		 xmlFile = new File(xmlFillocation);  
		try {
			icd10Index = (ICD10CMIndex) jaxbUnmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		letters = icd10Index.getLetter();
		
	}
*/
	@Override
	public ICD10CMIndex getIcd10Index() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIcd10Index(ICD10CMIndex icd10Index) {
		// TODO Auto-generated method stub
		
	}
	
	
}
