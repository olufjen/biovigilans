package no.naks.biovigilans.web.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.Pasientkomplikasjon;
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
 * RapporterteMeldingerServerResourceHTML
 * Dette er resource for rapporterte meldinger.
 * Den aktiveres når bruker velger oppfølgingsmelding og har angitt en meldingsnøkkel.
 * En oppfølgingsmelding identifiseres med bruk av en meldingsnøkkel.
 * Den er knyttet til alle typer meldinger, Transfusjon, Giver eller andre meldinger
 * @author olj
 *
 */
public class RapporterteMeldingerServerResourceHTML extends
		SessionServerResource {

	private String delMelding = "delmelding";

	private String displayPart = "none";
	private String displayKey = "display";

	private String detaljer = "Vis ytterligere detaljer";
	private String detaljerId = "detaljer";
	private String[] aldergruppePasient;	// Dette er aldersgrupper for pasienter (transfusjonsskjema)
	private String[] pasientSessionParams; // Dette er session parametre for pasient/transfusjonsskjema 

	
	public String[] getPasientSessionParams() {
		return pasientSessionParams;
	}

	public void setPasientSessionParams(String[] pasientSessionParams) {
		this.pasientSessionParams = pasientSessionParams;
	}

	public String[] getAldergruppePasient() {
		return aldergruppePasient;
	}

	public void setAldergruppePasient(String[] aldergruppePasient) {
		this.aldergruppePasient = aldergruppePasient;
	}

	public String getDetaljerId() {
		return detaljerId;
	}

	public void setDetaljerId(String detaljerId) {
		this.detaljerId = detaljerId;
	}

	public String getDetaljer() {
		return detaljer;
	}

	public void setDetaljer(String detaljer) {
		this.detaljer = detaljer;
	}
	
	




	public String getDisplayPart() {
		return displayPart;
	}



	public void setDisplayPart(String displayPart) {
		this.displayPart = displayPart;
	}

	public String getDisplayKey() {
		return displayKey;
	}

	public void setDisplayKey(String displayKey) {
		this.displayKey = displayKey;
	}

	public String getDelMelding() {
		return delMelding;
	}

	public void setDelMelding(String delMelding) {
		this.delMelding = delMelding;
	}



	/**
	 * getHemovigilans
	 * Denne rutinen henter inn nødvendige session objekter og  
	 * henter frem siden for oppfølgingsmeldinger
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	     Vigilansmelding melding = (Vigilansmelding) sessionAdmin.getSessionObject(request, meldingsId);
	     
	    annenKomplikasjon = (Annenkomplikasjon)sessionAdmin.getSessionObject(request, andreKey);
	    pasientKomplikasjon = (Pasientkomplikasjon)sessionAdmin.getSessionObject(request, pasientKey);
	    giverKomplikasjon = (Giverkomplikasjon)sessionAdmin.getSessionObject(request,  giverKey);
	   
	    Donasjon donasjon = (Donasjon) sessionAdmin.getSessionObject(request,  donasjonKey);
	     Map<String, Object> dataModel = new HashMap<String, Object>();

	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/cellerogvev");
	     
	   	 String page = "/cellerogvev/rapportert_melding.html";
	     SimpleScalar simple = new SimpleScalar(displayPart);
		 SimpleScalar detaljButton = new SimpleScalar(detaljer);
		 dataModel.put(detaljerId,detaljButton);
	     LocalReference localUri = new LocalReference(reference);
	     dataModel.put(meldingsId, melding);
	     dataModel.put(displayKey, simple);
	     if (annenKomplikasjon != null){
	    	 dataModel.put(andreKey,annenKomplikasjon);
	     }
	     if (giverKomplikasjon != null){
	    	 Giver giver = (Giver)sessionAdmin.getSessionObject(request,  giverenKey);
	    	 dataModel.put(giverKey,giverKomplikasjon);
	    	 dataModel.put(giverenKey, giver);
	    	 page = "/cellerogvev/rapportert_giver.html";
	     }
	     if (pasientKomplikasjon != null){
	    	 dataModel.put(pasientKey,pasientKomplikasjon);
	    	 page = "/cellerogvev/rapportert_pasient.html";
	    	 
	     }
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));

	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	
	}
	  /**
     * storeHemovigilans
     * Denne rutinen tar imot meldingsnøkkel fra bruker og henter frem meldingsinformasjon basert på 
     * oppgitt meldingsnøkkel
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	
 	    Map<String, Object> dataModel = new HashMap<String, Object>();
 	    Reference reference = new Reference(getReference(),"..").getTargetRef();
	    Request request = getRequest();
	    Vigilansmelding melding = (Vigilansmelding) sessionAdmin.getSessionObject(request, meldingsId);
	    annenKomplikasjon = (Annenkomplikasjon)sessionAdmin.getSessionObject(request, andreKey);
	    pasientKomplikasjon = (Pasientkomplikasjon)sessionAdmin.getSessionObject(request, pasientKey);
	    giverKomplikasjon = (Giverkomplikasjon)sessionAdmin.getSessionObject(request,  giverKey);
	
	     LocalReference localUri = new LocalReference(reference);
	     dataModel.put(meldingsId, melding);
	     String infoButton = null;
	     String melder = null;
	   	 String page = "/cellerogvev/rapportert_melding.html";
	     for (Parameter entry : form) {
	    	 if (entry.getValue() != null && !(entry.getValue().equals(""))){
	    		 System.out.println(entry.getName() + "=" + entry.getValue());
	    		 if (entry.getName().equals("hentopplysninger")){
	    			 infoButton = entry.getValue();
	    			 if (infoButton.equals("Skjul detaljer")){
	    				 detaljer = "Vis ytterligere detaljer";
	    				 displayPart = "none";
	    			 }else{
	    				 detaljer = "Skjul detaljer";
	    				 displayPart = "block";
	    			 }
	    		 }
				if (entry.getName().equals("k-melder")){
						melder = entry.getValue();
						page =  "../cellerogvev/melder_rapport.html";
						redirectPermanent(page);
				}
	    		 if (entry.getName().equals("oppfolgingandre")){
	    			  page =  "../cellerogvev/rapporter_andrehendelser.html";
	  //  			  setAndreHendelser(); // Setter opp andreHendelser session objekter
	    				    // setTransfusjonsObjects(); 
	  //  			  annenModel.setFormNames(sessionParams);
	  //  		      sessionAdmin.setSessionObject(getRequest(), annenModel,andreHendelseId);
	    			     setAndreHendelser(); // Setter opp andreHendelser session objekter
	    				    // setTransfusjonsObjects(); 
	    				 annenModel.setFormNames(sessionParams);
	    				 annenModel.distributeTerms();
	    				 annenModel.setAnnenKomplikasjon(annenKomplikasjon); 
	    				 annenModel.setVigilansmelding(melding);
	    			   	 annenModel.setHendelseDato(melding.getMeldingsdato());
	    		    	 annenModel.setMeldingsNokkel(melding.getMeldingsnokkel());
	    		    	 List<Komplikasjonsklassifikasjon> klassifikasjoner = (List)sessionAdmin.getSessionObject(request, klassifikasjonKey);
	    		    	 annenModel.setKlassifikasjoner(klassifikasjoner);
	    		    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
	    			     SimpleScalar simple = new SimpleScalar(displayPart);
	    				 dataModel.put(displayKey, simple);
	    		    	 dataModel.put(displaydateKey, hendelseDate);
	    				 dataModel.put(andreHendelseId, annenModel);
	    				 dataModel.put(annenHendelseId, annenKomplikasjon);
	    			     dataModel.put(klassifikasjonKey, klassifikasjoner);
	    				 sessionAdmin.setSessionObject(getRequest(), annenModel,andreHendelseId);	    			  
   			 	
	    			  redirectPermanent(page);
	    		 }
	    		
	    		 if (entry.getName().equals("oppfolginggiver")){
	    			  page =  "../cellerogvev/rapporter_giver.html";
	    			  setGiverhendelser(); // Setter opp giverHendelser session objekter
	    				
	    				 giverModel.setFormNames(sessionParams);
	    			
	    				 giverModel.setGiverKomplikasjon(giverKomplikasjon);
	    				 giverModel.setVigilansmelding(melding);
	    			   	 giverModel.setHendelseDato(melding.getMeldingsdato());
	    		    	 giverModel.setMeldingsNokkel(melding.getMeldingsnokkel());
	    		    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
	    		    	 dataModel.put(displaydateKey, hendelseDate);
	//    				 dataModel.put(andreHendelseId, annenModel);
	    			     SimpleScalar simple = new SimpleScalar(displayPart);
	    				 dataModel.put(displayKey, simple);
	    		    	 dataModel.put(displaydateKey, hendelseDate);
	    			     dataModel.put(giverkomplikasjonId, giverModel);
	    				 sessionAdmin.setSessionObject(getRequest(), giverModel,giverkomplikasjonId);	    			  
	    				 sessionAdmin.setSessionObject(getRequest(),donasjon,donasjonId);
	    			  redirectPermanent(page);
	    		 }
	    		 if (entry.getName().equals("oppfolgingpasient")){
	    			  page =  "../cellerogvev/rapporter_transfusjon.html";
	    			  setTransfusjon(); // Setter opp pasientHendelser session objekter
	    			  setSessionParams(pasientSessionParams); // Setter opp riktige sessionparametre for pasient/transfusjon
	    		      transfusjon.setPlasmaEgenskaper(blodProdukt); // Setter plasma produkttyper
	    		    	 transfusjon.setFormNames(sessionParams);
	    			     transfusjon.distributeTerms();
	    			   	 transfusjon.setHendelseDato(melding.getMeldingsdato());
	    		    	 transfusjon.setMeldingsNokkel(melding.getMeldingsnokkel());
	    		    	 transfusjon.setVigilansmelding(melding);
	    		    	 transfusjon.setPasientKomplikasjon(pasientKomplikasjon);
	    				 result.setFormNames(sessionParams);
	    				 result.distributeTerms();
	    				 result.setAldergruppe(aldergruppePasient); // Setter opp riktig aldersgruppe for pasienter
	    			//	 giverModel.setGiverKomplikasjon(giverKomplikasjon);
	    				 result.setVigilansmelding(melding);
	    				 result.setHendelseDato(melding.getMeldingsdato());
	    		    	 result.setMeldingsNokkel(melding.getMeldingsnokkel());
	    		    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
	    		    	 dataModel.put(displaydateKey, hendelseDate);
	    			//	 dataModel.put(andreHendelseId, annenModel);

	    			     dataModel.put(pasientkomplikasjonId, result);
	    			     dataModel.put(transfusjonId,transfusjon);
	    				 sessionAdmin.setSessionObject(getRequest(), result,pasientkomplikasjonId);	    			  
	    				 sessionAdmin.setSessionObject(getRequest(), transfusjon,transfusjonId);
	    			  redirectPermanent(page);
	    		 }
	    		 
	    	 }

	     }
	  
	     if (annenKomplikasjon != null){
	    	 dataModel.put(andreKey,annenKomplikasjon);
//	    	 displayPart = "block";
//	    	 detaljer = "Skjul detaljer";
	   	     SimpleScalar simple = new SimpleScalar(displayPart);
			 SimpleScalar detaljButton = new SimpleScalar(detaljer);
			 dataModel.put(detaljerId,detaljButton);
	   	     dataModel.put(displayKey, simple);

			     
	     }
	     if (pasientKomplikasjon != null){
	    	 dataModel.put(pasientKey,pasientKomplikasjon);
//	    	 displayPart = "block";
//	    	 detaljer = "Skjul detaljer";
	   	     SimpleScalar simple = new SimpleScalar(displayPart);
			 SimpleScalar detaljButton = new SimpleScalar(detaljer);
			 dataModel.put(detaljerId,detaljButton);
	   	     dataModel.put(displayKey, simple);
	   	     page =  "/cellerogvev/rapportert_pasient.html";
	     }
	     if (giverKomplikasjon != null){
	    	 dataModel.put(giverKey,giverKomplikasjon);
//	    	 displayPart = "block";
//	    	 detaljer = "Skjul detaljer";
	   	     SimpleScalar simple = new SimpleScalar(displayPart);
			 SimpleScalar detaljButton = new SimpleScalar(detaljer);
			 dataModel.put(detaljerId,detaljButton);
	   	     dataModel.put(displayKey, simple);
	   	     page =  "/cellerogvev/rapportert_giver.html";
	     }
 	    ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,page));

	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
    }
}
