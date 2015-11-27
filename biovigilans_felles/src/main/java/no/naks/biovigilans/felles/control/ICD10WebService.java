package no.naks.biovigilans.felles.control;

import java.io.File;
import java.util.List;

import javax.xml.bind.Unmarshaller;

import no.naks.biovigilans.felles.xml.ICD10CMIndex;
import no.naks.biovigilans.felles.xml.Letter;
import no.naks.biovigilans.felles.xml.MainTerm;
import no.naks.biovigilans.felles.xml.no.ICD10;
import no.naks.biovigilans.felles.xml.no.KodeNivaa1;
import no.naks.biovigilans.felles.xml.no.KodeNivaa2;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa1;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa2;

public interface ICD10WebService {
	public Unmarshaller getJaxbUnmarshaller();

	public void setJaxbUnmarshaller(Unmarshaller jaxbUnmarshaller);
	public void readXml();
	public String getXmlFillocation();

	public void setXmlFillocation(String xmlFillocation);
	

	public File getXmlFile();

	public void setXmlFile(File xmlFile);

	public List<MainTerm> getTerms();

	public void setTerms(List<MainTerm> terms);

	public List<Letter> getLetters();

	public void setLetters(List<Letter> letters);


	public ICD10CMIndex getIcd10Index();

	public void setIcd10Index(ICD10CMIndex icd10Index);


	public ICD10 getNoICD10();

	public void setNoICD10(ICD10 noICD10);

	public List<TematiskGruppeNivaa1> getNivaa1();

	public void setNivaa1(List<TematiskGruppeNivaa1> nivaa1);

	public List<TematiskGruppeNivaa2> getNivaa2();

	public void setNivaa2(List<TematiskGruppeNivaa2> nivaa2);

	public List<KodeNivaa1> getKodeNivaa1();

	public void setKodeNivaa1(List<KodeNivaa1> kodeNivaa1);

	public List<KodeNivaa2> getKodeNivaa2();

	public void setKodeNivaa2(List<KodeNivaa2> kodeNivaa2);

}
