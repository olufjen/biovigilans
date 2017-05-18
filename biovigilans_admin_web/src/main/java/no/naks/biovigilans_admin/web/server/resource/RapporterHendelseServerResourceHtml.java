package no.naks.biovigilans_admin.web.server.resource;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;

import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.felles.control.SessionAdmin;
import no.naks.biovigilans.felles.control.TableWebService;
import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.model.PasientKomplikasjonWebModel;
import no.naks.biovigilans.felles.model.SakModel;
import no.naks.biovigilans.felles.model.TransfusjonKvitteringWebModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.biovigilans.felles.xml.Letter;
import no.naks.biovigilans.felles.xml.MainTerm;
import no.naks.biovigilans.felles.xml.no.KodeNivaa1;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa1;
import no.naks.biovigilans.felles.xml.no.TematiskGruppeNivaa2;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.data.Parameter;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import freemarker.template.SimpleScalar;

//import edu.unc.ils.mrc.hive2.api.HiveConcept;

/**
 * Denne resursen håndterer all saksbehandling av transfusjonskomplikasjoner Biovigilans
 * Resurser blir instansiert for hver kall fra klient
 * 
 * @author olj
 * 
 * 
 */
public class RapporterHendelseServerResourceHtml extends SaksbehandlingSessionServer{

	 public RapporterHendelseServerResourceHtml() {
			super();
			// TODO Auto-generated constructor stub
		}

	 








	public String[] getAldergruppe() {
		return aldergruppe;
	}

	public String[] getAvdelinger() {
		return avdelinger;
	}

	public void setAvdelinger(String[] avdelinger) {
		this.avdelinger = avdelinger;
	}

	public String[] getBlodProdukt() {
		return blodProdukt;
	}

	public void setBlodProdukt(String[] blodProdukt) {
		this.blodProdukt = blodProdukt;
	}

	public String[] getHemolyseParametre() {
		return hemolyseParametre;
	}

	public void setHemolyseParametre(String[] hemolyseParametre) {
		this.hemolyseParametre = hemolyseParametre;
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

	public SessionAdmin getSessionAdmin() {
		return sessionAdmin;
	}

	public void setSessionAdmin(SessionAdmin sessionAdmin) {
		this.sessionAdmin = sessionAdmin;
	//	this.sessionParams = this.sessionAdmin.getSessionParams();
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
	/**
	 * statusChange
	 * Denne rutinen kalles når bruker har valgt å endre status på meldingen
	 * @param form
	 * @param request
	 * @param dataModel
	 * @param melder
	 * @param diskusjoner
	 * @return
	 */
	private TemplateRepresentation statusChange(Form form,Request request,Map<String, Object> dataModel,Melder melder,List<Diskusjon>diskusjoner,ClientResource clres2){
			Vigilansmelding melding = (Vigilansmelding) transfusjon.getPasientKomplikasjon();
		Date datoforhendelse =  melding.getDatoforhendelse();
		melding.setDatoforhendelse(datoforhendelse);
		String statusCode = "";
		Long meldeId = melding.getMeldeid();
	    SakModel sakModel = (SakModel)sessionAdmin.getSessionObject(request,  sakModelKey);
		for (Parameter entry : form) {
			if (entry.getName().equals("nystatus") && entry.getValue() != null && !(entry.getValue().equals(""))){
				statusCode = entry.getValue();
				System.out.println(entry.getName() + "=" + entry.getValue());
		
			}
		}
		if (!statusCode.equals("")){
			if (statusCode.equals(statusflag[0])) // Dersom status settes til Levert, så skal det settes til null i db
				statusCode = null;
			transfusjon.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
			hendelseWebService.updateVigilansmelding(transfusjon.getVigilansmelding());
			if (statusCode == null){
				statusCode = statusflag[0];
				giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
			}
				sakModel.setLoginSaksbehandler(login.getSaksbehandler());
			sakModel.lagSak(meldeId, statusCode);
			saksbehandlingWebservice.saveDiskusjon(sakModel.getDiskusjonsMappe());
			sakModel.setSakdiskusjon();
			saksbehandlingWebservice.saveSak(sakModel.getSaksMappe());
			
			List<Diskusjon> nyediskusjoner = sakModel.lagDiskusjonsliste();
			if (diskusjoner != null)
				diskusjoner.addAll(nyediskusjoner);
   			sakModel.setDiskusjonsMappe(null);
			sakModel.setSaksMappe(null);
		}
	
//   			setDiplayvalues(dataModel);
		clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,transfusjonhendelseskjema));
		 
		Representation andreHendelser = clres2.get();
		TemplateRepresentation templateRep = new TemplateRepresentation(andreHendelser, dataModel,
				MediaType.TEXT_HTML);
		return templateRep;
	}
	/**
	 * getInnmelding
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en rapportert hendelse av typen transfusjoner
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	     Request request = getRequest();
	     setDBSource(request);
	     String db =  sessionAdmin.getChosenDB(request);
	     if (db != null && db.equals("cellerogvev"))
	    	 transfusjonhendelseskjema = cellerogvevtransfusjonhendelse;
	     if (db != null && db.equals("organer"))
	    	 transfusjonhendelseskjema = organertransfusjonhendelse;
	     
	     FileInputStream adrFile = null;
	     SakModel sakModel = (SakModel)sessionAdmin.getSessionObject(request,  sakModelKey);
	     if (sakModel == null){
	    	 sakModel = new SakModel();
	    	 sessionAdmin.setSessionObject(request, sakModel, sakModelKey);
	     }
	     sakModel.setMerknader(merknader);
	     sakModel.setStatusflag(statusflag);
	     
	     setTransfusjonsObjects(); // Setter opp alle session objekter. Hentes fra sesjonen eller lager nye !!
	     
	     List diskusjoner = null;
	     List<Sak> saker = null;
	     
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	     Pasient pasient = result.getPasient();
	     Sykdom sykdom = result.getSykdom();
	     Pasientkomplikasjon pasientKomplikasjon = transfusjon.getPasientKomplikasjon();
	     List<Pasientkomplikasjon> pasientListe = (List)	sessionAdmin.getSessionObject(request,reportPasientKey);	
		 pasientMeldingene = (List<Vigilansmelding>)sessionAdmin.getSessionObject(request, pasientMeldingKey);

	     if (pasientKomplikasjon.getKlassifikasjon() == null || pasientKomplikasjon.getKlassifikasjon().isEmpty() ){
	    	 pasientKomplikasjon.setKlassifikasjon("Ikke angitt");
	     }
	     Transfusjon pasientTransfusjon = transfusjon.getTransfusjon();
    	 List sykdommer = (List)sessionAdmin.getSessionObject(request,sykdomKey);
    	 List<Blodprodukt> blodprodukter = (List)sessionAdmin.getSessionObject(request,blodproduktKey);
    	 List egenskaper = (List)sessionAdmin.getSessionObject(request,produktegenskapKey);
    	 List symptomer = (List)sessionAdmin.getSessionObject(request,symptomerKey);
    	 List klassifikasjoner = (List)sessionAdmin.getSessionObject(request,klassifikasjonKey);
    	 List utredninger = (List)sessionAdmin.getSessionObject(request,utredningKey);
    	 List tiltak = (List)sessionAdmin.getSessionObject(request,tiltakKey);
    	 List forebyggendeTiltak = (List)sessionAdmin.getSessionObject(request,forebyggendetiltakKey);

    	 if (tiltak == null){
    		 setalleTiltak();
    		 tiltak = (List)sessionAdmin.getSessionObject(request,tiltakKey);
        	 forebyggendeTiltak = (List)sessionAdmin.getSessionObject(request,forebyggendetiltakKey);
    	 }
		 Melder melder = null;
	     if (transfusjon.getVigilansmelding().getMeldingsnokkel() != null){
	    	 displayPart = "block";
	    	 datePart = "none";
	    	 Vigilansmelding melding = (Vigilansmelding)transfusjon.getPasientKomplikasjon();
	    	 makeSequence(request, melding);
	    	 transfusjon.getVigilansmelding().setSekvensNr(melding.getSekvensNr());
	    	 transfusjon.setHendelseDato(melding.getDatoforhendelse());
	    	 result.setHendelseDato(melding.getDatoforhendelse());
	    	 transfusjon.setMeldingsNokkel(melding.getMeldingsnokkel());
	    	 pasient = (Pasient)sessionAdmin.getSessionObject(request,pasientenKey);
	    	 pasientTransfusjon = (Transfusjon)sessionAdmin.getSessionObject(request,transfusjonsKey);
		    	//==  Hent alle diskusjoner og merknader   =======	    	 
	    	 Long meldeId = melding.getMeldeid();
	    	 Map<String,List> diskusjonene = saksbehandlingWebservice.collectDiskusjoner(meldeId);
	  
//	    	 String mKey = String.valueOf(meldeId.longValue());
	    	 String mKey = melding.getMeldingsnokkel();
	   	     Long orgmeldeId = getmeldingsNokkelsak(mKey); //Hent meldingsid for første melding med samme meldingsnøkkel
	    	 String mnKey = String.valueOf(meldeId.longValue());
	    	 diskusjoner = diskusjonene.get(mnKey);
	    	 Map<String,List> orgMapdiskusjoner = null;
	    	 String omKey = "";
	    	 List<Diskusjon> orgdiskusjoner = null;
	    	 boolean sameKey = false;
	    	 if (orgmeldeId != null){
	    		 orgMapdiskusjoner = saksbehandlingWebservice.collectDiskusjoner(orgmeldeId); // Henter diskusjoner fra tidligere meldinger med samme nøkkel
	    		 omKey = String.valueOf(orgmeldeId.longValue());
	    		 orgdiskusjoner =  orgMapdiskusjoner.get(omKey);
	    		 sameKey = mnKey.equals(omKey);
	    	 }	
/*
 * For å vise historikk	    	 
*/
	    	 Vigilansmelding orgVigilansmelding = null;
	    	 result.getTidligereVigilans().clear();
	    	 result.getTidligerePasientkomp().clear();
	    	 transfusjon.getTidligereVigilans().clear();
	    	 transfusjon.getTidligerePasientkomp().clear();
	    	 
	    	 transfusjon.getTidligereBlodprodukter().clear();
	    	 transfusjon.getTidligereKlassifikasjoner().clear();
	    	 transfusjon.getTidligerePasienter().clear();
	    	 transfusjon.getTidligereProduktegenskaper().clear();
	    	 transfusjon.getTidligereSykdommer().clear();
	    	 transfusjon.getTidligereTransfusjoner().clear();
	    	 transfusjon.getTidligereUtredninger().clear();
	    	 transfusjon.getTidligereforebyggendeTiltak().clear();
	    	 transfusjon.getTidligereTiltak().clear();
	    	 transfusjon.getTidligereSymptomer().clear();
	    	 result.getTidligereBlodprodukter().clear();
	    	 result.getTidligereKlassifikasjoner().clear();
	    	 result.getTidligerePasienter().clear();
	    	 result.getTidligereProduktegenskaper().clear();
	    	 result.getTidligereSykdommer().clear();
	    	 result.getTidligereTransfusjoner().clear();
	    	 result.getTidligereUtredninger().clear();
	    	 result.getTidligereforebyggendeTiltak().clear();
	    	 result.getTidligereTiltak().clear();
	    	 result.getTidligereSymptomer().clear();
/*
 * Henter historikk	    	 
 */
	    	 for (Vigilansmelding nestemelding: pasientMeldingene){
	    		 if (orgmeldeId != null && nestemelding.getMeldingsnokkel().equals(mKey) && nestemelding.getMeldeid().longValue() != meldeId.longValue() ){
	    			 orgVigilansmelding = nestemelding;
	    			 Long orgMeldId = nestemelding.getMeldeid();
	    			 String oMKey = String.valueOf(orgMeldId.longValue());
	    			 Map<String,List> histMeldingsdetaljer = null;
					 histMeldingsdetaljer = (Map<String,List>)saksbehandlingWebservice.selectMeldinger(oMKey); // Hent historikk
					 if (histMeldingsdetaljer != null ){
				   			List pasienter = histMeldingsdetaljer.get(pasientenKey);
				   			List transfusjoner = histMeldingsdetaljer.get(transfusjonsKey);
				   			List histsykdommer = histMeldingsdetaljer.get(sykdomKey);
				   			List histtiltak = histMeldingsdetaljer.get(tiltakKey);
				   			List histforebyggendeTiltak = histMeldingsdetaljer.get(forebyggendetiltakKey);
				   			List histsymptomer = histMeldingsdetaljer.get(symptomerKey);
				   			List histutredning = histMeldingsdetaljer.get(utredningKey);
				   			List histblodprodukter = histMeldingsdetaljer.get(blodproduktKey);
				   			List histproduktegenskaper = histMeldingsdetaljer.get(produktegenskapKey);
							String klasskey = klassifikasjonKey; // + oMKey; Kommentert bort 09.03.17 OLJ 
							List histklassifikasjoner = histMeldingsdetaljer.get(klasskey);
				   			if (histblodprodukter != null)
				   				result.getTidligereBlodprodukter().addAll(histblodprodukter);
				   			if (histforebyggendeTiltak != null)
				   				result.getTidligereforebyggendeTiltak().addAll(histforebyggendeTiltak);
				   			if (pasienter != null)
				   				result.getTidligerePasienter().addAll(pasienter);
				   			if (histproduktegenskaper != null)
				   				result.getTidligereProduktegenskaper().addAll(histproduktegenskaper);
				   			if (histsykdommer != null)
				   				result.getTidligereSykdommer().addAll(histsykdommer);
				   			if (histtiltak != null)
				   				result.getTidligereTiltak().addAll(histtiltak);
				   			if (transfusjoner != null)
				   				result.getTidligereTransfusjoner().addAll(transfusjoner);
				   			if (histutredning != null)
				   				result.getTidligereUtredninger().addAll(histutredning);
				   			if (histklassifikasjoner != null)
				   				result.getTidligereKlassifikasjoner().addAll(histklassifikasjoner);
				   			if (histsymptomer != null)
				   				result.getTidligereSymptomer().addAll(histsymptomer);
				   			if (histblodprodukter != null)
				   				transfusjon.getTidligereBlodprodukter().addAll(histblodprodukter);
				   			if (histforebyggendeTiltak != null)
				   				transfusjon.getTidligereforebyggendeTiltak().addAll(forebyggendeTiltak);
				   			if (pasienter != null)
				   				transfusjon.getTidligerePasienter().addAll(pasienter);
				   			if (histproduktegenskaper != null)
				   				transfusjon.getTidligereProduktegenskaper().addAll(histproduktegenskaper);
				   			if (histsykdommer != null)
				   				transfusjon.getTidligereSykdommer().addAll(histsykdommer);
				   			if (histtiltak != null)
				   				transfusjon.getTidligereTiltak().addAll(histtiltak);
				   			if (transfusjoner != null)
				   				transfusjon.getTidligereTransfusjoner().addAll(transfusjoner);
				   			if (histutredning != null)
				   				transfusjon.getTidligereUtredninger().addAll(histutredning);
				   			if (histklassifikasjoner != null)
				   				transfusjon.getTidligereKlassifikasjoner().addAll(histklassifikasjoner);
				   			if (histsymptomer != null)
				   				transfusjon.getTidligereSymptomer();
					 }
	    			 result.getTidligereVigilans().add(orgVigilansmelding);
	    			 transfusjon.getTidligereVigilans().add(orgVigilansmelding);
	    		 }
	    	 }
	    	 List<Vigilansmelding> vigilans =  result.getTidligereVigilans();
	    	 for (Vigilansmelding orgmelding : vigilans){
	    		 for (Pasientkomplikasjon komplikasjon : pasientListe){
	    			 if (orgmeldeId != null && komplikasjon.getMeldeid().longValue() == orgmelding.getMeldeid().longValue() ){
	    				 Pasientkomplikasjon orgPasientkomplikasjon = komplikasjon;
	    				 displayorgInfo = "block";
	    				 sessionAdmin.setSessionObject(request, displayorgInfo,displayorgInfoKey );
	    				 result.getTidligerePasientkomp().add(orgPasientkomplikasjon);
	    				 transfusjon.getTidligerePasientkomp().add(orgPasientkomplikasjon);
	    			 }
	    		 }		    		 
	    	 }	    	    	 
	    	 
	    	 
	    	 
	    	 melder = hentMelder(transfusjon.getVigilansmelding());
	    	 
	    	 if (diskusjoner == null)
	    		 diskusjoner = new ArrayList<Diskusjon>();
	    	 if (orgdiskusjoner != null && !sameKey)
	    		 diskusjoner.addAll(orgdiskusjoner);
	    	 if (!diskusjoner.isEmpty()){
	    		 checkDiskusjoner(diskusjoner);
	    	 }	    	 
	    	 if (orgMapdiskusjoner != null)
	    		 diskusjonene.putAll(orgMapdiskusjoner);
	    	 sessionAdmin.setSessionObject(request, diskusjonene, diskusjonsKey);
/*
 * Hent saker til diskusjonene 	    	 
 */
	    	 setsaksbehandlerTildiskusjon(request, diskusjoner, saker);
	    	 
	    	 if (pasientTransfusjon.getTildigerKomplikasjon() == null){
	    		 pasientTransfusjon.setTildigerKomplikasjon("Nei");
	    	 }
	    	 if (pasientTransfusjon.getIndikasjon() == null || pasientTransfusjon.getIndikasjon().isEmpty()){
	    		 pasientTransfusjon.setIndikasjon("Ikke angitt");
	    	 }
	    	 transfusjon.setTransfusjon(pasientTransfusjon);
	    	 result.setPasient(pasient);
	     }
	     for (Blodprodukt blodprodukt : blodprodukter){
	    	 if (blodprodukt.getTappetype() ==null){
	    		 blodprodukt.setTappetype("ukjent");
	    	 }
	     }
	  	 SimpleScalar tilMelder = new SimpleScalar(tilMelderPart);
    	 dataModel.put(tilMelding,tilMelder);
    	 
    	 SimpleScalar simple = new SimpleScalar(displayPart);
    	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
		 SimpleScalar orgInfo = new SimpleScalar(displayorgInfo);
    	 SimpleScalar iconImportant = new SimpleScalar(imagesrcImportant);
    	 dataModel.put(imageImportantkey,iconImportant);
     	 dataModel.put(displayorgInfoKey, orgInfo);    	 
 	 	 dataModel.put(behandlingsFlaggKey, diskusjoner);
    	 dataModel.put(statusflagKey,statusflag);
      	 dataModel.put(sykdomKey, sykdommer);
      	 dataModel.put(blodproduktKey,blodprodukter);
     	 dataModel.put(produktegenskapKey,egenskaper);
    	 dataModel.put(symptomerKey,symptomer);
     	 dataModel.put(klassifikasjonKey, klassifikasjoner);
    	 dataModel.put(displayKey, simple);
    	 dataModel.put(displaydateKey, hendelseDate);
    	 dataModel.put(pasientenKey, pasient);
    	 dataModel.put(transfusjonsKey, pasientTransfusjon);
    	 dataModel.put(pasientKey,pasientKomplikasjon);
    	 dataModel.put(utredningKey,utredninger);
    	 dataModel.put(tiltakKey,tiltak);
    	 dataModel.put(forebyggendetiltakKey,forebyggendeTiltak);
 	 	 dataModel.put(tilmelderKey, melder);
    	 result.setFormNames(sessionParams);
    	 transfusjon.setFormNames(sessionParams);
    	 transfusjon.setPlasmaEgenskaper(blodProdukt); // Setter plasma produkttyper
	     if (kvittering == null){
	    	 kvittering = new TransfusjonKvitteringWebModel(sessionParams);
	  //  	 kvittering.setFormNames(sessionParams);
	     }
	
		 result.distributeTerms();
	     String ref = reference.toString();
	     result.setAccountRef(ref);
	     transfusjon.setAccountRef(ref);
	     transfusjon.distributeTerms();
	 
/*
 * En Hashmap benyttes dersom en html side henter data fra flere javaklasser.	
 * Hver javaklasse får en id (ex pasientkomplikasjonId) som er tilgjengelig for html
 *      
*/	     
	
	     dataModel.put(pasientkomplikasjonId, result);
	     dataModel.put(transfusjonId,transfusjon);
	     dataModel.put(kvitteringsId,kvittering);
	     dataModel.put(melderId, melderwebModel);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	     sessionAdmin.setSessionObject(getRequest(), result,pasientkomplikasjonId);
	     sessionAdmin.setSessionObject(getRequest(), transfusjon,transfusjonId);
	     sessionAdmin.setSessionObject(request,kvittering, kvitteringsId);
	     sessionAdmin.setSessionObject(getRequest(), melderwebModel,melderId);
	     List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(getRequest(), meldingsId); // Midlertdig
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,transfusjonhendelseskjema));
	     
	        // Load the FreeMarker template
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
//	        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/pasientkomplikasjon/nymeldingfagprosedyre.html").get();
	        Representation pasientkomplikasjonFtl = clres2.get();
	//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
	        
//	        TemplateRepresentation  templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
//	                MediaType.TEXT_HTML);
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	 }

	    /**
	     * storeHemovigilans
	     * Denne rutinen tar imot alle ny informasjon fra bruker om den rapporterte hendelsen
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
	    	 Request request = getRequest();
	    	 displayPart = "block";
	    	 setDBSource(request);
		     String db =  sessionAdmin.getChosenDB(request);
		     if (db != null && !db.equals("hemovigilans"))
		    	 transfusjonhendelseskjema = cellerogvevtransfusjonhendelse;
	    	if (form == null){
	    		invalidateSessionobjects();
	      	}
	    	   login = (LoginModel)sessionAdmin.getSessionObject(request,loginKey);
	    	   Melder melder = null;
	    	if (form != null){
	    		Representation webTransfusjon = form.getWebRepresentation();
	      	     SakModel sakModel = (SakModel)sessionAdmin.getSessionObject(request,  sakModelKey);
	    	     if (sakModel == null){
	    	    	 sakModel = new SakModel();
	    	    	 sessionAdmin.setSessionObject(request, sakModel, sakModelKey);
	    	     }
	    		sakModel.setFlaggNames(flaggNames);
	    	     List<Diskusjon>diskusjoner = null;
	    	     Map<String,List> diskusjonene = (Map<String,List>)sessionAdmin.getSessionObject(request, diskusjonsKey);
		    	 Map<String, Object> dataModel = new HashMap<String, Object>();
		    	 
		  	   SimpleScalar tilMelder = new SimpleScalar(tilMelderPart);
	  	    	dataModel.put(tilMelding,tilMelder);
	    	 	 SimpleScalar simple = new SimpleScalar(displayPart);
	        	 SimpleScalar hendelseDate = new SimpleScalar(datePart);
	    		result = (PasientKomplikasjonWebModel) sessionAdmin.getSessionObject(getRequest(),pasientkomplikasjonId);
	    		transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(getRequest(),transfusjonId);
	    	    kvittering = (TransfusjonKvitteringWebModel)sessionAdmin.getSessionObject(getRequest(),kvitteringsId);
	    	     melderwebModel = ( MelderwebModel)sessionAdmin.getSessionObject(getRequest(),melderId);
	    	     Pasient pasient = result.getPasient();
	    	     Sykdom sykdom = result.getSykdom();
	    	     Pasientkomplikasjon pasientKomplikasjon = transfusjon.getPasientKomplikasjon();
	    	     Transfusjon pasientTransfusjon = transfusjon.getTransfusjon();
	    		Parameter logout = form.getFirst("avbryt4");
	    		Parameter lukk = form.getFirst("lukk4");
	    		if (logout != null || lukk != null){
	    			invalidateSessionobjects();
		    		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemivigilans/Logout.html"));
		    		Representation pasientkomplikasjonFtl = clres2.get();
		    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, result,
		    				MediaType.TEXT_HTML);
	    			return templateRep; // return a new page!!!
	    		}
	    		List<MainTerm> terms = new ArrayList();	
	    		if (result == null){
	    		     icd10WebService.readXml();
	    		     
	    		     List<TematiskGruppeNivaa1> nivaa1 = icd10WebService.getNivaa1();
	    		     List<TematiskGruppeNivaa2> nivaa2 = new ArrayList();
	    		     List<KodeNivaa1> koder = new ArrayList();
	    		     for (TematiskGruppeNivaa1 nivaa : nivaa1){
	    		    	 nivaa2.addAll(nivaa.getTematiskGruppeNivaa2()) ;
	    		     }
	    		     for (TematiskGruppeNivaa2 niva : nivaa2){
	    		    	 koder.addAll(niva.getKodeNivaa1());
	    		     }	     
	    		     	    		     
/*
 * Engelsk icd10	    		     
 */
	    		     List<Letter> letters = icd10WebService.getLetters();
	    		     for (Letter letter : letters){
	    		    	 terms.addAll(letter.getMainTerm());
	    		     }
	    			result = new PasientKomplikasjonWebModel();
	    			 result.setFormNames(sessionParams);
	    	    	 result.setAldergruppe(aldergruppe);
	    	    	 result.setKjonnValg(kjonnValg);
	    	    	 result.setblodProducts(blodProdukt);
	    	    	 result.setHemolyseparams(hemolyseParametre);
	    	    	 result.setAvdelinger(avdelinger);
	    		     result.setnoTerms(koder);
	    		//     result.setTerms(terms);
	    			 result.distributeTerms();
	    		   
	    	
	    		}
	   	     if (transfusjon == null){
		    	 transfusjon = new TransfusjonWebModel();
		    	 transfusjon.setFormNames(sessionParams);
		    	 transfusjon.setPlasmaEgenskaper(blodProdukt); // Setter plasma produkttyper
	   	     }
	   	     if (kvittering == null){
		    	 kvittering = new TransfusjonKvitteringWebModel(sessionParams);
		    	 //kvittering.setFormNames(sessionParams);
		     }
	   	     if (melderwebModel == null){
	   	    	 melderwebModel = new MelderwebModel();
	   	    	 melderwebModel.setFormNames(sessionParams);
	   	     }
	    		for (Parameter entry : form) {
	    			if (entry.getValue() != null && !(entry.getValue().equals("")))
	    					System.out.println(entry.getName() + "=" + entry.getValue());
	    			result.setValues(entry);
	    			transfusjon.setValues(entry);
	    			kvittering.setValues(entry);
	    			melderwebModel.setValues(entry);

	    		}
	    	 	 List sykdommer = (List)sessionAdmin.getSessionObject(request,sykdomKey);
	        	 List<Blodprodukt> blodprodukter = (List)sessionAdmin.getSessionObject(request,blodproduktKey);
	        	 List egenskaper = (List)sessionAdmin.getSessionObject(request,produktegenskapKey);
	        	 List symptomer = (List)sessionAdmin.getSessionObject(request,symptomerKey);
	        	 List klassifikasjoner = (List)sessionAdmin.getSessionObject(request,klassifikasjonKey);
	        	 List utredninger = (List)sessionAdmin.getSessionObject(request,utredningKey);
	        	 List tiltak = (List)sessionAdmin.getSessionObject(request,tiltakKey);
	        	 List forebyggendeTiltak = (List)sessionAdmin.getSessionObject(request,forebyggendetiltakKey);
	        	 if (tiltak == null){
	        		 setalleTiltak();
	        		 tiltak = (List)sessionAdmin.getSessionObject(request,tiltakKey);
	            	 forebyggendeTiltak = (List)sessionAdmin.getSessionObject(request,forebyggendetiltakKey);
	        	 }
	        	 Long meldeId = null;
	        	 Long gmlMeldeid = null;
	    	     if (transfusjon.getVigilansmelding().getMeldingsnokkel() != null){
	    	    	 displayPart = "block";
	    	    	 datePart = "none";
	    	    	 Vigilansmelding melding = (Vigilansmelding)transfusjon.getPasientKomplikasjon();
	    	    	 transfusjon.setHendelseDato(melding.getDatoforhendelse());
	    	    	 result.setHendelseDato(melding.getDatoforhendelse());
	    	    	 transfusjon.setMeldingsNokkel(melding.getMeldingsnokkel());
	    	    	 pasient = (Pasient)sessionAdmin.getSessionObject(request,pasientenKey);
	    	    	 pasientTransfusjon = (Transfusjon)sessionAdmin.getSessionObject(request,transfusjonsKey);
	    	    	
	    	    	 if (pasientTransfusjon.getTildigerKomplikasjon() == null){
	    	    		 pasientTransfusjon.setTildigerKomplikasjon("Nei");
	    	    	 }
	    	    	 transfusjon.setTransfusjon(pasientTransfusjon);
	    	    	 result.setPasient(pasient);
	    	    	 gmlMeldeid = sakModel.getGmlMeldeid();
	    		     meldeId = melding.getMeldeid();
	    	  		 String mKey = String.valueOf(meldeId.longValue());
	    			 if (gmlMeldeid != null){
	    	  		    	mKey = String.valueOf(gmlMeldeid.longValue());
	    	  		 }
	    	  		 diskusjoner = diskusjonene.get(mKey);
	    	 	 	dataModel.put(behandlingsFlaggKey, diskusjoner);
	    	  		 	
	    	     }
	    	     for (Blodprodukt blodprodukt : blodprodukter){
	    	    	 if (blodprodukt.getTappetype() ==null){
	    	    		 blodprodukt.setTappetype("ukjent");
	    	    	 }
	    	     }
	        	 melder = hentMelder(transfusjon.getVigilansmelding());
	    		sessionAdmin.setSessionObject(getRequest(), result,pasientkomplikasjonId);
	    	    sessionAdmin.setSessionObject(getRequest(), transfusjon,transfusjonId);
	    	    sessionAdmin.setSessionObject(getRequest(), melderwebModel,melderId);

	    	    /*
	    	     * For å vise info fra tidligere meldinger	    	 
	    	     */
	    	    displayorgInfo = (String)sessionAdmin.getSessionObject(request, displayorgInfoKey);
	    	    if (displayorgInfo == null || displayorgInfo.isEmpty())
	    	    	displayorgInfo = "none";   	  	
	    	    dataModel.put(displayKey, simple);
	    	    SimpleScalar orgInfo = new SimpleScalar(displayorgInfo);
	    	    SimpleScalar iconImportant = new SimpleScalar(imagesrcImportant);
	    	    dataModel.put(imageImportantkey,iconImportant);
	    	    dataModel.put(displayorgInfoKey, orgInfo);    	 
	            dataModel.put(displaydateKey, hendelseDate);
	       	 	dataModel.put(statusflagKey,statusflag);
	    	    dataModel.put(pasientkomplikasjonId, result);
	    	    dataModel.put(transfusjonId,transfusjon);
	    	    dataModel.put(kvitteringsId,kvittering);
	    	    dataModel.put(melderId, melderwebModel);
	         	 dataModel.put(sykdomKey, sykdommer);
	          	 dataModel.put(blodproduktKey,blodprodukter);
	         	 dataModel.put(produktegenskapKey,egenskaper);
	        	 dataModel.put(symptomerKey,symptomer);
	         	 dataModel.put(klassifikasjonKey, klassifikasjoner);
	        	 dataModel.put(pasientenKey, pasient);
	        	 dataModel.put(transfusjonsKey, pasientTransfusjon);
	        	 dataModel.put(pasientKey,pasientKomplikasjon);
	        	 dataModel.put(utredningKey,utredninger);
	        	 dataModel.put(tiltakKey,tiltak);
	        	 dataModel.put(forebyggendetiltakKey,forebyggendeTiltak);
	        	 dataModel.put(tilmelderKey, melder);
	        	 result.setFormNames(sessionParams);
	        	 transfusjon.setFormNames(sessionParams);
	        	 transfusjon.setPlasmaEgenskaper(blodProdukt); // Setter plasma produkttyper
    
	    		Parameter lagre = form.getFirst("btnSendinn");
	       		Parameter lagreFlagg = form.getFirst("btnlagreflagg"); // Lagrer saksmerknader
	    		Parameter statusChange = form.getFirst("btnstatuschange");
	      		Parameter avslutt = form.getFirst("btnavslutt");
	    		Parameter sendTilmelder = form.getFirst("btnsend");
	    		if (sendTilmelder != null){
	       			meldingsType = "transfusjon";
	    			 sessionAdmin.setSessionObject(request,meldingsType, meldingstypeKey);
	    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,transfusjonhendelseskjema));
	    			 
	    			Representation transfusjonHendelser = clres2.get();
	 //       		invalidateSessionobjects();
	        		templateRep = new TemplateRepresentation(transfusjonHendelser, dataModel,
	        				MediaType.TEXT_HTML);
	        		String page = "../hemovigilans/meldingtilmelder.html";
	        		redirectPermanent(page);
	        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
	    		}
	    		if (avslutt != null){ // Bruker avslutter
	    			LoginModel newLogin = new LoginModel();
	    			newLogin.setSaksbehandler(login.getSaksbehandler());
	    			newLogin.setPassord(login.getSaksbehandler().getBehandlerpassord());
	    			newLogin.setEpostAdresse(login.getSaksbehandler().getBehandlerepost());
	    		     List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(request, meldingsId); // Midlertdig
	    			 List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
	    			 /*
	    			  * Fjerner saksgangen og diskusjonene fra session
	    			  */
	    	   		sessionAdmin.removesessionObject(request, displayorgInfoKey);
	    		 	sessionAdmin.removesessionObject(request, diskusjonsKey);
	    		 	sessionAdmin.removesessionObject(request, sakModelKey);
	    			 sessionAdmin.setSessionObject(request, newLogin, loginKey);
	    			 sessionAdmin.setSessionObject(request, saksbehandlere, behandlereKey);
	    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));
	   			 
	    			Representation giverHendelser = clres2.get();
	        		templateRep = new TemplateRepresentation(giverHendelser, dataModel,
	        				MediaType.TEXT_HTML);
	        		redirectPermanent("../hemovigilans/saksbehandling.html");
	        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
	    		}
	    		if (statusChange != null){// Bruker har valgt å endre saksstatus
	    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,transfusjonhendelseskjema));
	       			templateRep = statusChange(form, request, dataModel, melder, diskusjoner, clres2);
	    			
/* Dette er nå flyttet til egen rutine - se over
 * 	       			Vigilansmelding melding = (Vigilansmelding) transfusjon.getPasientKomplikasjon();
	    			Date datoforhendelse =  melding.getDatoforhendelse();
	    			melding.setDatoforhendelse(datoforhendelse);
	    			String statusCode = "";
	    			for (Parameter entry : form) {
	        			if (entry.getName().equals("nystatus") && entry.getValue() != null && !(entry.getValue().equals(""))){
	        				statusCode = entry.getValue();
	        				System.out.println(entry.getName() + "=" + entry.getValue());
	        		
	        			}
	    			}
	    			if (!statusCode.equals("")){
	    				if (statusCode.equals(statusflag[0])) // Dersom status settes til Levert, så skal det settes til null i db
	    					statusCode = null;
	    				transfusjon.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
	        			hendelseWebService.updateVigilansmelding(transfusjon.getVigilansmelding());
	        			if (statusCode == null){
	    					statusCode = statusflag[0];
	    					giverModel.getVigilansmelding().setSjekklistesaksbehandling(statusCode);
	    				}
	       				sakModel.setLoginSaksbehandler(login.getSaksbehandler());
	    				sakModel.lagSak(meldeId, statusCode);
	        			saksbehandlingWebservice.saveDiskusjon(sakModel.getDiskusjonsMappe());
	        			sakModel.setSakdiskusjon();
	        			saksbehandlingWebservice.saveSak(sakModel.getSaksMappe());
	        			
	        			List<Diskusjon> nyediskusjoner = sakModel.lagDiskusjonsliste();
	        			if (diskusjoner != null)
	        				diskusjoner.addAll(nyediskusjoner);
	           			sakModel.setDiskusjonsMappe(null);
	        			sakModel.setSaksMappe(null);
	    			}
	    		
	 //   			setDiplayvalues(dataModel);
	    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,transfusjonhendelseskjema));
	    			 
	    			Representation andreHendelser = clres2.get();
//	        		invalidateSessionobjects();
	        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
	        				MediaType.TEXT_HTML);*/
	        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
	    		}
	    		//Parameter ikkegodkjet = form.getFirst("ikkegodkjent");
	    		//Parameter godkjet = form.getFirst("godkjent");
	    		if (lagreFlagg != null){  // Lagrer saksmerknader
	       			Vigilansmelding melding = (Vigilansmelding) transfusjon.getPasientKomplikasjon();
	    			Date datoforhendelse =  melding.getDatoforhendelse();
	    			if (login.getSaksbehandler() != null && login.getSaksbehandler().getSakbehandlerid() == null){
	    				System.out.println("Saksbehandler: ikke satt !!");
	    				login.getSaksbehandler().setSakbehandlerid(new Long(1));
	    				login.getSaksbehandler().setBehandlerepost("none");
	    				
	    			}
	    			String sakbId = String.valueOf(login.getSaksbehandler().getSakbehandlerid().longValue());
	    			System.out.println("Saksbehandler: "+login.getSaksbehandler().getBehandlerepost()+ " id "+sakbId);
	    			melding.setDatoforhendelse(datoforhendelse);
	    			sakModel.setLoginSaksbehandler(login.getSaksbehandler());

	/*
	 * Hent flaggverdier valgt fra bruker
	 */
	    			sakModel.setFlaggNames(flaggNames);
	    			sakModel.getFormMap().clear();
	    			boolean avvist = false;
	    			for (Parameter entry : form) {
	        			if (entry.getValue() != null && !(entry.getValue().equals(""))){
	        					System.out.println(entry.getName() + "=" + entry.getValue());
	        					if (entry.getName().equals("nystatus")){ // Bruker har også valgt å endre status til avvist
	        						avvist = true;
	        					}
	           					if (entry.getName().equals("kommentartilmeldingtxt")&& avvist){
	        						String txtVal = entry.getValue(); // Betyr at kommentar skal vises til Melder
	        						txtVal = "Vises;"+txtVal;
	        						entry.setValue(txtVal);
	           					}
	        					sakModel.setValues(entry);
	        			}
	    			}
	    			sakModel.saveSaker(melding.getMeldeid()); // Setter også saksbehandler navn til merknad
	    			saksbehandlingWebservice.saveDiskusjon(sakModel.getDiskusjonsMappe());
	    			sakModel.setSakdiskusjon();
	    			saksbehandlingWebservice.saveSak(sakModel.getSaksMappe());
	    			List nyediskusjoner = sakModel.lagDiskusjonsliste();
	    			if (diskusjoner != null)
	    				diskusjoner.addAll(nyediskusjoner);
	    			String newStatus = sakModel.setsakStatus(nyediskusjoner);
	    			if (newStatus != null){
	    				transfusjon.getVigilansmelding().setSjekklistesaksbehandling(newStatus);
	    				hendelseWebService.updateVigilansmelding(transfusjon.getVigilansmelding());
	    			}
	      			if (sakModel.isTilDialog()){
	    				String mailTxt = sakModel.getHemovigilansDiskusjon().getKommentar() + " Meldingsnøkkel: " + transfusjon.getVigilansmelding().getMeldingsnokkel()+ " Type Transfusjonshendelse.";
	        			dialogHemivigilans(request, sakModel.getDiskId(),mailTxt,transfusjon.getVigilansmelding());
	    			}
	     			if (sakModel.isReklassifikasjon()){
	     				transfusjon.getVigilansmelding().setSjekklistesaksbehandling(statusflag[8]); //Sett gammel melding til Erstattet OLJ 01.10.16
	     				hendelseWebService.updateVigilansmelding(transfusjon.getVigilansmelding());
	     				sakModel.setGmlMeldeid(meldeId);
	      				savetransfusjonReclassifikasjon();
//	      				melding = (Vigilansmelding)transfusjon.getPasientKomplikasjon();
//	      				melding.setMeldingstype("Pasientkomplikasjon");

	    			}  
/*
* En rutine for å sende epost til Helsedirektoratet dersom saksbehandler har valgt "Melde til Helsedirektoratet"
* olj 03.02.16    			
*/
	    			String mailText = (String)sakModel.getFormMap().get("meldingtilhelsedir")+ " Meldingsnummer: "+ melding.getMeldingsnokkel();
	    			tilHelsedirektoratet(request, mailText, sakModel.getFormMap());	     			
	       			sakModel.setDiskusjonsMappe(null);
	    			sakModel.setSaksMappe(null);
	    			tilMelderPart = "none";
	    			if (sakModel.isTilMelder())
	    				tilMelderPart = "block";
	    			tilMelder = null;
	    		    tilMelder = new SimpleScalar(tilMelderPart);
	    	    	dataModel.put(tilMelding,tilMelder); 		

	    			dataModel.put(behandlingsFlaggKey, diskusjoner);
	    			
/*	    			LoginModel newLogin = new LoginModel();
	    			newLogin.setSaksbehandler(login.getSaksbehandler());
	    			newLogin.setPassord(login.getSaksbehandler().getBehandlerpassord());
	    			newLogin.setEpostAdresse(login.getSaksbehandler().getBehandlerepost());
	    		    sessionAdmin.setSessionObject(request, newLogin, loginKey);
*/
//	    			setDiplayvalues(dataModel);
	    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,transfusjonhendelseskjema));
	    			if (avvist)
	    				templateRep = statusChange(form, request, dataModel, melder, diskusjoner, clres2);
	    			else{
	    				Representation andreHendelser = clres2.get();
	    				templateRep = new TemplateRepresentation(andreHendelser, dataModel,
	        				MediaType.TEXT_HTML);
	    			}	    			 
//	    			Representation andreHendelser = clres2.get();
//	        		invalidateSessionobjects();
//	        		templateRep = new TemplateRepresentation(andreHendelser, dataModel,
//	        				MediaType.TEXT_HTML);
	        		return templateRep;  // Hvorfor er denne nødvendig? OLJ 28.07.15
	    		}   	    		
	    		if (lagre != null){
	    			result.getVigilansmelding().setSjekklistesaksbehandling(statusflag[1]);
	    			hendelseWebService.updateVigilansmelding(result.getVigilansmelding());
	    			ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,transfusjonhendelseskjema));
		    		Representation pasientkomplikasjonFtl = clres2.get();
		    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
		    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
		    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
				    				MediaType.TEXT_HTML);
	    		}else{
	    		
/*
 * Dette må gjøres om (OLJ 06.07.15
 */
					ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapporter_kontakt.html"));
		    		Representation pasientkomplikasjonFtl = clres2.get();
		    		//        Representation pasientkomplikasjonFtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+ "/html/nymeldingfagprosedyre.html").get();
		    		//        Representation pasientkomplikasjonFtl = new ClientResource("http:///no/naks/server/resource"+"/pasientkomplikasjon.ftl").get();
		    		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
				    				MediaType.TEXT_HTML);
	    		}
	    		
	    
	    	}
	    	return templateRep;
	      
	    }
}
