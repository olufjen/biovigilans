package no.naks.biovigilans.felles.server.resource;

import no.naks.biovigilans.felles.control.AdminWebService;
import no.naks.biovigilans.felles.control.AnnenKomplikasjonWebService;
import no.naks.biovigilans.felles.control.DonasjonWebService;
import no.naks.biovigilans.felles.control.GiverWebService;
import no.naks.biovigilans.felles.control.ICD10WebService;
import no.naks.biovigilans.felles.control.HendelseWebService;
import no.naks.biovigilans.felles.control.KomDiagnosegiverWebService;
import no.naks.biovigilans.felles.control.KomplikasjonsklassifikasjonWebService;
import no.naks.biovigilans.felles.control.MelderWebService;
import no.naks.biovigilans.felles.control.SessionAdmin;
import no.naks.biovigilans.felles.control.TableWebService;

import org.restlet.resource.ServerResource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * ProsedyreServerResource
 * Dette er superklassen til Sessionserver Resources
 * Slike resurser håndterer alle hendelser fra html web sider.
 * Den inneholder referanser til alle singleton tjenester som er tilgjengelig for resursene.
 * Den er også superklassen som arver fra Restlet rammeverket
 * @author olj
 *
 */
public class ProsedyreServerResource extends ServerResource {

	/**
	 * Inneholder alle Web model objekter for sesjonen
	 */
	protected SessionAdmin sessionAdmin = null;
	/**
	 * Resursens singleton tjenester: Disse er definert i applikasjonens Spring xml definisjoner
	 */
	protected AnnenKomplikasjonWebService annenKomplikasjonWebService;
	protected TableWebService tablewebservice;
	protected HendelseWebService hendelseWebService;
	protected ICD10WebService icd10WebService;
	protected GiverWebService giverWebService; 
	protected DonasjonWebService donasjonWebService;
	protected MelderWebService melderWebService;
	protected KomDiagnosegiverWebService komDiagnosegiverWebService;
	protected AdminWebService adminWebService;

	protected KomplikasjonsklassifikasjonWebService komplikasjonsklassifikasjonWebService;
	/**
	 *  Er kun satt dersom saksbehandler velger annen database enn hemovigilans
	 */
	private JdbcTemplate alternativeSource = null; // Er kun satt dersom saksbehandler velger annen database enn hemovigilans
	
	
	/**
	 * Resursen parameteroppsett for HTML-siden den betjener
	 */
	protected String[]sessionParams;
	
	
	
	
	public JdbcTemplate getAlternativeSource() {
		return alternativeSource;
	}
	public void setAlternativeSource(JdbcTemplate alternativeSource) {
		this.alternativeSource = alternativeSource;
	}
	public KomplikasjonsklassifikasjonWebService getKomplikasjonsklassifikasjonWebService() {
		return komplikasjonsklassifikasjonWebService;
	}
	public void setKomplikasjonsklassifikasjonWebService(
			KomplikasjonsklassifikasjonWebService komplikasjonsklassifikasjonWebService) {
		this.komplikasjonsklassifikasjonWebService = komplikasjonsklassifikasjonWebService;
	}
	public AnnenKomplikasjonWebService getAnnenKomplikasjonWebService() {
		return annenKomplikasjonWebService;
	}
	public void setAnnenKomplikasjonWebService(
			AnnenKomplikasjonWebService annenKomplikasjonWebService) {
		this.annenKomplikasjonWebService = annenKomplikasjonWebService;
	}
	public SessionAdmin getSessionAdmin() {
		return sessionAdmin;
	}
	public void setSessionAdmin(SessionAdmin sessionAdmin) {
		this.sessionAdmin = sessionAdmin;
	}
	public TableWebService getTablewebservice() {
		return tablewebservice;
	}
	public void setTablewebservice(TableWebService tablewebservice) {
		this.tablewebservice = tablewebservice;
	}
	public String[] getSessionParams() {
		return sessionParams;
	}
	public void setSessionParams(String[] sessionParams) {
		this.sessionParams = sessionParams;
	}
	public HendelseWebService getInnmeldingWebService() {
		return hendelseWebService;
	}
	public void setInnmeldingWebService(HendelseWebService hendelseWebService) {
		this.hendelseWebService = hendelseWebService;
	}
	public ICD10WebService getIcd10WebService() {
		return icd10WebService;
	}
	public void setIcd10WebService(ICD10WebService icd10WebService) {
		this.icd10WebService = icd10WebService;
	}
	public GiverWebService getGiverWebService() {
		return giverWebService;
	}
	public void setGiverWebService(GiverWebService giverWebService) {
		this.giverWebService = giverWebService;
	}
	public DonasjonWebService getDonasjonWebService() {
		return donasjonWebService;
	}
	public void setDonasjonWebService(DonasjonWebService donasjonWebService) {
		this.donasjonWebService = donasjonWebService;
	}
	public MelderWebService getMelderWebService() {
		return melderWebService;
	}
	public void setMelderWebService(MelderWebService melderWebService) {
		this.melderWebService = melderWebService;
	}
	public KomDiagnosegiverWebService getKomDiagnosegiverWebService() {
		return komDiagnosegiverWebService;
	}
	public void setKomDiagnosegiverWebService(
			KomDiagnosegiverWebService komDiagnosegiverWebService) {
		this.komDiagnosegiverWebService = komDiagnosegiverWebService;
	}
	public AdminWebService getAdminWebService() {
		return adminWebService;
	}
	public void setAdminWebService(AdminWebService adminWebService) {
		this.adminWebService = adminWebService;
	}
	
	

}
