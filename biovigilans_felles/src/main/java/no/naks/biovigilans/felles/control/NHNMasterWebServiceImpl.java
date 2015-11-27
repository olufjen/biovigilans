package no.naks.biovigilans.felles.control;

import java.util.List;

import javax.xml.bind.JAXBElement;

import no.naks.framework.web.control.DomainDictionary;
import no.naks.framework.web.control.MasterWebService;
import no.naks.framework.web.control.MasterWebServiceImpl;
import no.naks.nhn.service.NHNServiceClient;


/**
 * This class is the super class for all web service classes for this project
 * It contains a reference to the DomainDictionary.
 * 
 * @author ojn
 *
 */
/**
 * @author olj
 *
 */
public class NHNMasterWebServiceImpl extends MasterWebServiceImpl implements NHNMasterWebService,MasterWebService{
	
	
	
	protected String foretakKey;
	protected NHNServiceClient nhnClient; // Henter inn organisasjoner fra NHN adresseregister dersom nhnFlag er satt.
	protected boolean nhnFlag = false;
	protected JAXBElement<String> organisationName = null;
	
	public NHNMasterWebServiceImpl() {
		super();
	
	}
	/**
	 * clearquestionaireDictionary
	 * This routine is used when questionnaires and related tables need to be collected
	 * fresh from the db	
	 *
 	 */
	protected void clearquestionaireDictionary(){
		if (masterDictionary.getDictionaryTable(foretakKey) != null){
			
			masterDictionary.getDictionaryTable(foretakKey).clear();
		}	

	}



/*
	 * @return the foretakKey
 */
	public String getforetakKey() {
		return foretakKey;
	}

	/**
	 * @param foretakKey the foretakKey to set
	 */
	public void setforetakKey(String foretakKey) {
		this.foretakKey = foretakKey;
	}

	/**
	 * @return the answerKey
	 */

	public void collectMasterTables(){
		
		nhnClient.setNhnFlag(nhnFlag);
	//	List organisations = nhnClient.collectNHNParties();
		List organisations = nhnClient.getAllOrganisations();
		masterDictionary.addtoTableDictionary(organisations,foretakKey);
		organisationName = nhnClient.getOrganisationName();
		

	}




}