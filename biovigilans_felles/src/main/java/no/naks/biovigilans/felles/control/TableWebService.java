
package no.naks.biovigilans.felles.control;

import javax.xml.bind.JAXBElement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import no.naks.nhn.service.MelderService;
import no.naks.nhn.service.NHNServiceClient;
import no.naks.semweb.control.HiveService;
import no.naks.services.nhn.client.Organization;


/**
 * Dette grensesnitt implementeres av TableWebServiceImpl som
 * gjør tjenester for TableMaintenanceBean
 * @author ojn
 * TODO: remove?
 */
@RequestMapping("/") 
public interface TableWebService extends NHNMasterWebService  {

	public NHNServiceClient getNhnClient();
	@RequestMapping(method = RequestMethod.GET) 
	public void setNhnClient(NHNServiceClient nhnClient);

	public void collectMasterTables();
	public String getforetakKey();
	public MelderService getMeldingService();

	public JAXBElement<String> getOrganisationName();

	public void setOrganisationName(JAXBElement<String> organisationName);
	public HiveService getHiveService();
	public void setHiveService(HiveService hiveService);

	/**
	 * @param foretakKey the foretakKey to set
	 */
	public void setforetakKey(String foretakKey);
	public boolean isNhnFlag();

	public void setNhnFlag(boolean nhnFlag);
}
