package no.naks.biovigilans.web.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import freemarker.template.SimpleScalar;

/**
 * @author olj
 *  Denne resursen er knyttet til startsiden for Hemovigilans. 
 *  Her velger bruker om det er en ny melding eller en oppfølgingsmelding
 *  Ved oppfølgingsmeldinger hentes all informasjon om alle meldinger til pålogget melder.
 *  Meldingene fra databasen og settes i sessionadmin
 *  Velger bruker ny melding hentes siden rapporter_hendelse_main.html direkte
 */
public class RapporterStartServerResourceHTML extends SessionServerResource {

	
	private String delMelding = "delmelding";
	private String meldeTxtId = "melding";
	private String passordCheck = "none";
	private String displayPassord = "passord";

	public String getDisplayPassord() {
		return displayPassord;
	}

	public void setDisplayPassord(String displayPassord) {
		this.displayPassord = displayPassord;
	}

	public String getPassordCheck() {
		return passordCheck;
	}

	public void setPassordCheck(String passordCheck) {
		this.passordCheck = passordCheck;
	}

	public String getDelMelding() {
		return delMelding;
	}

	public void setDelMelding(String delMelding) {
		this.delMelding = delMelding;
	}


	public String getMeldeTxtId() {
		return meldeTxtId;
	}

	public void setMeldeTxtId(String meldeTxtId) {
		this.meldeTxtId = meldeTxtId;
	}

	public String getAndreKey() {
		return andreKey;
	}

	public void setAndreKey(String andreKey) {
		this.andreKey = andreKey;
	}

	public String getPasientKey() {
		return pasientKey;
	}

	public void setPasientKey(String pasientKey) {
		this.pasientKey = pasientKey;
	}

	public String getGiverKey() {
		return giverKey;
	}

	public void setGiverKey(String giverKey) {
		this.giverKey = giverKey;
	}

	private void setMeldingsValues(Vigilansmelding lokalMelding,Vigilansmelding melding){
			lokalMelding.setMeldingsnokkel(melding.getMeldingsnokkel());
			lokalMelding.setDatoforhendelse(melding.getDatoforhendelse());
	//		lokalMelding.setMeldeid(melding.getMeldeid());
			lokalMelding.setMelderId(melding.getMelderId());
			lokalMelding.setDatooppdaget(melding.getDatooppdaget());
			lokalMelding.setDonasjonoverforing(melding.getDonasjonoverforing());
			lokalMelding.setSjekklistesaksbehandling(melding.getSjekklistesaksbehandling());
			lokalMelding.setSupplerendeopplysninger(melding.getSupplerendeopplysninger());
			lokalMelding.setMeldingsdato(melding.getMeldingsdato());
			lokalMelding.setKladd(melding.getKladd());
			lokalMelding.setGodkjent(melding.getGodkjent());
			lokalMelding.setMeldeid(null);
	}
	/**
	 * getHemovigilans
	 * Denne rutinen starter med startside.html
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en meldingsnøkkel til en oppfølgingsmelding
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	     String meldingsText = (String)sessionAdmin.getSessionObject(request, meldeTxtId);
	     if (meldingsText == null)
	    	 meldingsText = " ";
	     SimpleScalar simple = new SimpleScalar(meldingsText);
		 dataModel.put(meldeTxtId,simple );
//		 SimpleScalar pwd = new SimpleScalar(passordCheck);
//		 dataModel.put(displayPassord,pwd);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/startside.html"));

	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	
	}
	
    /**
     * storeHemovigilans
     * Denne rutinen rutinen kjøres dersom epost og passord er gitt fra bruker.
     * Den tar imot epost og passord og henter frem riktig meldingsinformasjon fra
     * databasen basert på melders id
     * @param form
     * @return
     */
    /**
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	TemplateRepresentation  templateRep = null;
 	    Map<String, Object> dataModel = new HashMap<String, Object>();
 	    String meldingsText = "Melders epost/passord finnes ikke, prøv igjen";
	    dataModel.put( meldeTxtId, meldingsText);
	    Request request = getRequest();
	    
/*	    Map<String,List> alleMeldinger = new HashMap<String,List>();
 	    List<Vigilansmelding> meldinger = null;
 //	    List delMeldinger = null;
 	    List<Vigilansmelding> andreMeldinger = null;
 	    List<Vigilansmelding> pasientMeldinger = null;
 	    List<Vigilansmelding> giverMeldinger = null;*/
 	    
    	if(form == null){
    		invalidateSessionobjects();
    	}
/*
 * Verdier angitt av bruker    	
 */
    	String melderEpost = null;
    	String melderPassord = null;
    	String meldingsNokkel = null;
/*
 * Verdier fra database
 */
		String name ="";
		String passord = "";

    	Long melderid = null; 
    	Parameter nyttPassord = form.getFirst("nyttpassord");
        String page = "../cellerogvev/melder_rapport.html"; 
    	for (Parameter entry : form) {
			if (entry.getValue() != null && !(entry.getValue().equals(""))){
					System.out.println(entry.getName() + "=" + entry.getValue());
					if (entry.getName().equals("k-melderepost")){
						melderEpost = entry.getValue();
					}
					if (entry.getName().equals("k-melderpassord")){
						melderPassord = entry.getValue();
					}

			}
			
    	}
		Parameter formValue = form.getFirst("formValue"); // Bruker oppgir epost og passord
	
		if (formValue != null && melderEpost != null){
			List<Map<String, Object>> rows = melderWebService.selectMelder(melderEpost);

			if(rows.size() > 0){
				for(Map row:rows){
					melderid = Long.parseLong(row.get("melderid").toString());
	
					if (row.get("meldernavn") != null)
						name = row.get("meldernavn").toString();
					if (row.get("melderpassord") != null)
						passord = row.get("melderpassord").toString();
					if (melderPassord != null && melderPassord.equals(passord)){
						Melder melder = new MelderImpl();
						melder.setMelderId(melderid);
						melder.setMeldernavn(name);
						melder.setMelderPassord(passord);
						sessionAdmin.setSessionObject(request, melder, melderNokkel); 
						break;
					}
				}
			}
			if (melderPassord != null && melderPassord.equals(passord)){
	     		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/startside.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
	    				MediaType.TEXT_HTML);	
				redirectPermanent(page);
				return templateRep;
			}else{ // Feil passord går til startside.
	     		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/startside.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
	    				MediaType.TEXT_HTML);	
	    		return templateRep;
	    	}
		}
		if (nyttPassord != null){
     		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/startside.html"));
    		Representation pasientkomplikasjonFtl = clres2.get();
    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
    				MediaType.TEXT_HTML);
			page = "../cellerogvev/passord.html"; 
			redirectPermanent(page);
			return templateRep;
		}
    


    	return templateRep;
    }
}
