package no.naks.biovigilans.web.server.resource;

import java.util.HashMap;
import java.util.Map;

import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.DonasjonImpl;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.GiverImpl;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.GiverkomplikasjonImpl;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.GiveroppfolgingImpl;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.biovigilans.model.KomplikasjonsdiagnosegiverImpl;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.model.DonasjonwebModel;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.KomDiagnosegiverwebModel;
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.ibm.icu.util.Calendar;

import freemarker.template.SimpleScalar;

/**
 * @author olj
 *  Denne resursen er knyttet til siden for å rapportere giverhendelser 
 */
public class RapporterGiverServerResourceHtml extends SessionServerResource {

	
	public RapporterGiverServerResourceHtml() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * getInnmelding
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en rapportert hendelse
	 * @return
	 */
	/**
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {

		//invalidateSessionobjects();
	    // Reference reference = new Reference(getReference(),"..").getTargetRef();
	

/*
 * En Hashmap benyttes dersom en html side henter data fra flere javaklasser.	
 * Hver javaklasse får en id (ex pasientkomplikasjonId) som er tilgjengelig for html
 *      
*/	     
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	     Request request = getRequest();
	    /* LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	*/
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_giver.html"));
	    
//	     setTransfusjonsObjects(); // Setter opp alle session objekter
/*
 * Lager alltid nye session model objekter
 * Dersom meldingsnøkkel er angitt så vil session model objektene få modelobjekter fra databasen.	     
 */
	     setGiverhendelser(); // Setter opp giverHendelser session objekter
	     
	     Giver giver = giverModel.getGiver();
	     Donasjon donasjonen = giverModel.getDonasjonen();
	     Komplikasjonsdiagnosegiver komplikasjonsdiagnoseGiver = giverModel.getKomplikasjonsdiagnoseGiver();
		 Giveroppfolging giveroppfolging = giverModel.getGiveroppfolging();
		 giverKomplikasjon = (Giverkomplikasjon)giverModel.getGiverKomplikasjon();
	     if (giverModel.getVigilansmelding().getMeldingsnokkel() != null){
	    	 displayPart = "block";
	    	 datePart = "none";
	    	 Vigilansmelding melding = (Vigilansmelding)giverModel.getGiverKomplikasjon();
	    	 giverModel.setVigilansmelding(melding);
	    	 
	    	 if(giverKomplikasjon.getDatosymptomer() == null){
	    			Calendar kalender = Calendar.getInstance();
	    			int month = kalender.get(Calendar.MONTH);
	    			kalender.set(Calendar.MONTH, month+1);
	    			giverKomplikasjon.setDatosymptomer(kalender.getTime());
	    			giverKomplikasjon.setSymptomMelding("Fiktiv ikke angitt: ");
	    	 }
	    	 
	    	 giverModel.setHendelseDato(melding.getDatoforhendelse());
	    	 giverModel.setMeldingsNokkel(melding.getMeldingsnokkel());
	    	 donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(request, donasjonId);
	    	 giver = (Giver)sessionAdmin.getSessionObject(request,  giverenKey);
	    	 giverModel.setGiver(giver);
	    	 donasjonen = (Donasjon) sessionAdmin.getSessionObject(request ,  donasjonKey);
	    	 giveroppfolging = (Giveroppfolging) sessionAdmin.getSessionObject(request,giverOppfolgingKey);
	    	 giverModel.setGiveroppfolging(giveroppfolging);
	    	 giverModel.setDonasjonen(donasjonen);
	    	 donasjon.setDonasjon(donasjonen);
	    	 komplikasjonsdiagnoseGiver = (Komplikasjonsdiagnosegiver)sessionAdmin.getSessionObject(request,giverkomplikasjondiagnoseKey); //giverkomplikasjondiagnoseKey
	    	 if (komplikasjonsdiagnoseGiver.getBivirkningbeskrivelse() == null || komplikasjonsdiagnoseGiver.getBivirkningbeskrivelse().isEmpty())
	    		 komplikasjonsdiagnoseGiver.setBivirkningbeskrivelse("Ikke angitt");
	    	 komDiagnosegiver.setKomDiagnosegiver(komplikasjonsdiagnoseGiver);
	    	 giverModel.setKomplikasjonsdiagnoseGiver(komplikasjonsdiagnoseGiver);
	    	 
	     }
/*	   
 * Alle model objekter som benyttes i html må settes med initielle verdier dersom
 * dette er en ny melding
 *  Dette er flyttet til giverkomplikasjonswebmodel se funksjonen  setTransfusjonsObjects(); !!  
*/	    



    	 SimpleScalar simple = new SimpleScalar(displayPart);
    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);

    	 dataModel.put(giverkomplikasjonKey, giverKomplikasjon);
    	 dataModel.put(giverenKey, giver);
     	 dataModel.put(donasjonKey, donasjonen);
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(displaydateKey, hendelseDate);
	     dataModel.put(giverkomplikasjonId, giverModel);
	     dataModel.put(donasjonId, donasjon);
	     dataModel.put(komDiagnosegiverId, komDiagnosegiver);
	     
    	 dataModel.put(giverOppfolgingKey,giveroppfolging);
    	 dataModel.put(giverkomplikasjondiagnoseKey,komplikasjonsdiagnoseGiver);
	     
	     sessionAdmin.setSessionObject(getRequest(), giverModel,giverkomplikasjonId);
	     sessionAdmin.setSessionObject(getRequest(), donasjon,donasjonId);
	     sessionAdmin.setSessionObject(getRequest(), komDiagnosegiver, komDiagnosegiverId);
	//     sessionAdmin.setSessionObject(request,giverKvittering, kvitteringGiverId);
	     
	     // Load the FreeMarker template
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/pasientkomplikasjon/nymeldingfagprosedyre.html").get();
	        Representation givertkomplikasjonFtl = clres2.get();
	   
	//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	        
//	        TemplateRepresentation  templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, giverModel,
//	                MediaType.TEXT_HTML);
	        
	     /*
	        Directory directory = new Directory(getContext(), "file:///hemovigilans/img/");
	        Router router = new Router(getContext());
	        router.attach("/", directory);*/
	        
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(givertkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML ); 
		 return templatemapRep;
	 }
	

	
	
	public String[] getAldergruppe() {
		return aldergruppe;
	}

	public void setAldergruppe(String[] aldergruppe) {
		this.aldergruppe = aldergruppe;
	}

	public String[] getKjonnValg() {
		return kjonnValg;
	}

	public void setKjonnValg(String[] kjonnValg) {
		this.kjonnValg = kjonnValg;
	}
	public String[] getReaksjonengruppe() {
		return reaksjonengruppe;
	}

	public void setReaksjonengruppe(String[] reaksjonengruppe) {
		this.reaksjonengruppe = reaksjonengruppe;
	}
	
	public String[] getUtenforBlodbankengruppe() {
		return utenforBlodbankengruppe;
	}

	public void setUtenforBlodbankengruppe(String[] utenforBlodbankengruppe) {
		this.utenforBlodbankengruppe = utenforBlodbankengruppe;
	}
	
	
	
	public String[] getDonasjonsstedgruppe() {
		return donasjonsstedgruppe;
	}

	public void setDonasjonsstedgruppe(String[] donasjonsstedgruppe) {
		this.donasjonsstedgruppe = donasjonsstedgruppe;
	}

	/**
     * storeHemovigilans
     * Denne rutinen tar imot alle ny informasjon fra bruker om den rapporterte hendelsen
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	TemplateRepresentation  templateRep = null;
        Request request = getRequest();
    	if (form == null){
    		sessionAdmin.getSession(getRequest(),giverkomplikasjonId).invalidate();
    		sessionAdmin.getSession(getRequest(), donasjonId).invalidate();
    		sessionAdmin.getSession(getRequest(), komDiagnosegiverId).invalidate();
    	}
 
    	if (form != null){
    		giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(getRequest(),giverkomplikasjonId);
    		donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(getRequest(), donasjonId);
    		komDiagnosegiver = (KomDiagnosegiverwebModel) sessionAdmin.getSessionObject(getRequest(),komDiagnosegiverId );
    //		giverKvittering = (GiverKvitteringWebModel)sessionAdmin.getSessionObject(getRequest(),kvitteringGiverId);
   	     	melderwebModel = ( MelderwebModel)sessionAdmin.getSessionObject(getRequest(),melderId);
    		Parameter logout = form.getFirst("avbryt4");
    		Parameter lukk = form.getFirst("lukk4");
    	     Map<String, Object> dataModel = new HashMap<String, Object>();

    		if (logout != null || lukk != null){
    			sessionAdmin.getSession(request,giverkomplikasjonId).invalidate();
    			sessionAdmin.getSession(request, donasjonId).invalidate();
    			sessionAdmin.getSession(request, komDiagnosegiverId).invalidate();
    			sessionAdmin.getSession(request,kvitteringGiverId).invalidate();
    			
	    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemivigilans/Logout.html"));
	    		Representation pasientkomplikasjonFtl = clres2.get();
	    	/*	templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
	    				MediaType.TEXT_HTML);
	    	*/	
	    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, giverModel,
	    				MediaType.TEXT_HTML);
    			return templateRep; // return a new page!!!
    		}
    		
    		if (giverModel == null){
    			giverModel = new GiverKomplikasjonwebModel();
    			// giverModel.setFormNames(sessionParams);
    		}
 
    		if(donasjon==null){
    			donasjon = new DonasjonwebModel();
    			donasjon.setFormNames(sessionParams);
    		}
    		
    		if(komDiagnosegiver == null){
    	    	 komDiagnosegiver = new KomDiagnosegiverwebModel();
    	    	 komDiagnosegiver.setFormNames(sessionParams);
    	     }
    		
    	/*	if (giverKvittering == null){
    			giverKvittering = new GiverKvitteringWebModel();
    			giverKvittering.setFormNames(sessionParams);
		     }*/
    		
    		for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals("")))
    					System.out.println(entry.getName() + "=" + entry.getValue());
    			giverModel.setValues(entry);
    			donasjon.setValues(entry);
    			komDiagnosegiver.setValues(entry);
    			/*giverKvittering.setValues(entry);
    			if(entry.getName().equalsIgnoreCase("tab-annenreak")){
    				giverKvittering.annenreakList.add(entry.getValue()) ;
    			}*/
    		}
    		
 
    		dataModel.put(giverkomplikasjonId, giverModel);
    	//	dataModel.put(kvitteringGiverId, giverKvittering);
    		
    		Parameter lagre = form.getFirst("btnSendinn");
    		Parameter lagrex = form.getFirst("lagreskjema");
    		if(lagre!=null){
    			giverModel.saveValues();
       			giverModel.savekomplikasjonsValues();
    			giverWebService.saveGiver(giverModel);
    			giverWebService.saveVigilansmelding(giverModel);
    			
    			Long giverId=	giverModel.getGiver().getGiverid();
    			if(giverId != null){
    				donasjon.getDonasjon().setGiveId(giverId.intValue());
    			}
    		    donasjon.saveValues();
    		    donasjonWebService.saveDonasjon(donasjon);
    		    
    		    Long donasjonensId = donasjon.getDonasjon().getDonasjonsId();
    		    giverModel.getGiverKomplikasjon().setDonasjonid(donasjonensId);
    		    
    		    Long meldeId = giverModel.getVigilansmelding().getMeldeid();
    			giverModel.getGiverKomplikasjon().setMeldeid(meldeId);
 
    			giverWebService.saveGiverkomplikasjon(giverModel);
    			
    			komDiagnosegiver.getKomDiagnosegiver().setMeldeId(meldeId);
    			komDiagnosegiver.saveValues();
    			komDiagnosegiverWebService.saveKomDiagnosegiver(komDiagnosegiver);
    			
    			giverModel.getGiveroppfolging().setMeldeid(meldeId);
    			giverWebService.saveGiveroppfolging(giverModel);
    			giverModel.setLagret(true);
    			giverModel.setDonasjonen(donasjon.getDonasjon());
    			giverModel.setKomplikasjonsdiagnoseGiver(komDiagnosegiver.getKomDiagnosegiver());
    	   		sessionAdmin.setSessionObject(request, giverModel,giverkomplikasjonId);
        		sessionAdmin.setSessionObject(request, donasjon, donasjonId);
        		  sessionAdmin.setSessionObject(request,donasjon.getDonasjon(), donasjonKey);
        		sessionAdmin.setSessionObject(request, komDiagnosegiver, komDiagnosegiverId);
    			  sessionAdmin.setSessionObject(request,komDiagnosegiver.getKomDiagnosegiver(),giverkomplikasjondiagnoseKey);
    			  sessionAdmin.setSessionObject(request,giverModel.getGiveroppfolging(),giverOppfolgingKey);
        	    sessionAdmin.setSessionObject(request, melderwebModel,melderId);
    	//		giverKvittering.setReadonlyFlag("true");
    			
    			//lagre i vigiansmelding
    			//dataModel.put(melderId, melderwebModel);
    			//ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
	    		//Representation pasientkomplikasjonFtl = clres2.get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
	    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	    		//templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,MediaType.TEXT_HTML);
	    		redirectPermanent("../hemovigilans/rapporter_kontakt.html");
    		}else{
    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_giver.html"));
    			Representation pasientkomplikasjonFtl = clres2.get();
        		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
        				MediaType.TEXT_HTML);
			}
    		
    	}
    
    	return templateRep;
      
    }

}
