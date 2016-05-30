package no.naks.biovigilans_admin.web.server.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.Donasjon;
import no.naks.biovigilans.model.Forebyggendetiltak;
import no.naks.biovigilans.model.Giver;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Giveroppfolging;
import no.naks.biovigilans.model.Komplikasjonsdiagnosegiver;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.KomplikasjonsklassifikasjonImpl;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.Regionstatistikk;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.Utredning;
import no.naks.biovigilans.model.Vigilansmelding;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Disposition;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import freemarker.template.SimpleScalar;
import no.naks.biovigilans.felles.model.ExcelReport;
import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.felles.model.PasientKomplikasjonWebModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;
public class RapporteringServerResourceHTML extends SaksbehandlingSessionServer {


	private String utvalg = "";
	private String merknadValg = "";
	private String utvalgKey = "valgt";
	private String merknadKey = "merknader";
	private String merknadlisteKey = "merknadvalgt";


	@Get
	public Representation getHemovigilans() {


	    Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	    Request request = getRequest();


// ============	    
	    List<Vigilansmelding> meldinger = hentMeldingene(null);
	     Map<String, Object> dataModel = new HashMap<String, Object>();
		 sessionAdmin.setSessionObject(request, meldinger, meldingsId);
	 	 sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
	 	 SimpleScalar simpleDisplay = new SimpleScalar(displayPart);
	 	 SimpleScalar simple = new SimpleScalar(utvalg);
	 	 SimpleScalar merk = new SimpleScalar(merknadValg);
	 	 dataModel.put(merknadlisteKey, merk);
	 	 dataModel.put(utvalgKey, simple);

		 dataModel.put(displayKey, simpleDisplay);
	     dataModel.put(meldeKey,meldinger);
 		 dataModel.put(statusflagKey,statusflag);
 		 dataModel.put(merknadKey, merknader);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapportering.html"));
	     
	        // Load the FreeMarker template
	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	 }
	
	  /**
     * storeHemovigilans
     * Denne rutinen tar imot meldingsnøkkel fra bruker og henter frem meidngsinformasjon basert på 
     * oppgitt meldingsnøkkel
     * Bruker kan også velge å endre listeutvalg
     * @param form
     * @return
     */
    /**
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        Reference reference = new Reference(getReference(),"..").getTargetRef();
        Request request = getRequest();
  	    List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(getRequest(), meldingsId);
  	    dobleMeldingene = (List)sessionAdmin.getSessionObject(request,dobleMeldingKey);
  	    login = (LoginModel) sessionAdmin.getSessionObject(request, loginKey);
  	    dataModel.put(meldeKey,meldinger);
  	    dataModel.put(statusflagKey,statusflag);
		 dataModel.put(merknadKey, merknader);
  		 SimpleScalar simpleDisplay = new SimpleScalar(displayPart);
  		 dataModel.put(displayKey, simpleDisplay);
  	    meldingsNokkel = null;
  	    String meldingsID = null;
  	    String utvalget = null;
  		Parameter formValue = form.getFirst("utvalg"); // Bruker ønsker å begrense utvalget
  		Parameter formMerknad = form.getFirst("merknad"); // Bruker ønsker å begrense utvalget i hht merknader
  		Parameter datoMeldt = form.getFirst("datomeldt"); 
  		Parameter datoHendt = form.getFirst("datohendt");
  		Parameter datoHSort   = form.getFirst("sorteringdatohendt");
		Parameter datoMSort   = form.getFirst("sorteringdatomeldt"); 
		Parameter excelDoc = form.getFirst("excel");
		
  		String meldtUtvalgetstart = null;
  		String meldtUtvalgetslutt = null;
  		boolean toPDF = false;
  		String page = "";
  		String path = "";
  		if (excelDoc != null){ // Rapporter utvalg til excel
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    			}
    			if (entry.getValue() != null){
    				System.out.println(entry.getName()); 
    			}
  			}
  			List<Regionstatistikk> statistikk = null;
  			List<Regionstatistikk> sykehusStatistikk = null;
  			List<Regionstatistikk> foretakStatistikk = null;
  			String startPeriod =(String) sessionAdmin.getSessionObject(request, startPeriodKey);
  			String endPeriod = (String) sessionAdmin.getSessionObject(request, endPeriodKey);
  			String type = (String)sessionAdmin.getSessionObject(request, "hent");
  			if (startPeriod == null){
  	  			statistikk = saksbehandlingWebservice.collectRegionstatistikk();
  	  			sykehusStatistikk = saksbehandlingWebservice.collectForetakstatistikk("");
  	  			foretakStatistikk = saksbehandlingWebservice.collectForetakstatistikk("foretak");
  			}
  			if (startPeriod != null){
  				String typeset = "meldt";
  				if (type != null)
  					typeset = "";
  				statistikk = saksbehandlingWebservice.collectRegionstatistikk(startPeriod, endPeriod, typeset);
  				foretakStatistikk = saksbehandlingWebservice.collectForetakstatistikk(startPeriod, endPeriod, typeset);
  				sykehusStatistikk = saksbehandlingWebservice.collectsykehusstatistikk(startPeriod, endPeriod, typeset);
  			}
  			 ExcelReport excel = new ExcelReport(sessionAdmin);
  			 excel.setStatistikk(statistikk);
  			 excel.setForetakStatistikk(foretakStatistikk);
  			 excel.setSykehusStatistikk(sykehusStatistikk);
  			 path = excel.createBook(meldinger,reportAndreKey,reportGiverKey,reportPasientKey,request);
 		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
 		 	 dataModel.put(merknadlisteKey, merk);
  			
 		 	SimpleScalar simple = new SimpleScalar(utvalg);
 		 	dataModel.put(utvalgKey, simple);
 			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
 		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
 		    dataModel.put(meldeKey,meldinger);
 			Response response = getResponse(); 
	         // String path = "C:\\hemovigilans\\rapporter";
			 FileRepresentation representation = new FileRepresentation(path, MediaType.APPLICATION_EXCEL); 
		     
		    Disposition disposition = representation.getDisposition(); 
		    disposition.setType(disposition.TYPE_ATTACHMENT); 
		    disposition.setFilename("leveranse" + ".xlsx"); 
		     
		    response.setEntity(representation); 
		    return  representation;
 
 		}
  		
  		
 		if (datoHendt != null){ // Begrense utvalget til en periode av dato hendt
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					if (entry.getName().equals("fra-dato") )
    						meldtUtvalgetstart = entry.getValue();
    					if (entry.getName().equals("til-dato") )
    						meldtUtvalgetslutt = entry.getValue();
   
    			}
  
    		}
 			saksbehandlingWebservice.setTimeperiodType(false); // Flagg for dato hendt til false
  			meldinger = hentMeldingene(meldtUtvalgetstart, meldtUtvalgetslutt);
   		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
  		 	sessionAdmin.setSessionObject(request, meldtUtvalgetstart, startPeriodKey);
  		 	sessionAdmin.setSessionObject(request, meldtUtvalgetslutt, endPeriodKey);
  		 	sessionAdmin.setSessionObject(request, "hent", "hent");
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapportering.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  		}
  		
  		if (datoMeldt != null){ // Begrense utvalget til en periode av dato meldt
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					if (entry.getName().equals("fra-dato") )
    						meldtUtvalgetstart = entry.getValue();
    					if (entry.getName().equals("til-dato") )
    						meldtUtvalgetslutt = entry.getValue();
    			}
  
    		}
  			saksbehandlingWebservice.setTimeperiodType(true);// Flagg for dato medt til true
  			meldinger = hentMeldingene(meldtUtvalgetstart, meldtUtvalgetslutt);
   		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
		 	sessionAdmin.setSessionObject(request, meldtUtvalgetstart, startPeriodKey);
  		 	sessionAdmin.setSessionObject(request, meldtUtvalgetslutt, endPeriodKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapportering.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  		}
  		if (formValue != null){ // Begrense utvalget etter status
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					if (entry.getName().equals("nystatus") )
    						utvalget = entry.getValue();
    			}
    		}
  			meldinger = hentMeldingene(utvalget);
  			utvalg = utvalget;
  		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapportering.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  			
  		}
  		if (formMerknad != null){ // Begrense utvalget etter saksmerknader
  			for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					if (entry.getName().equals("merkn") )
    						utvalget = entry.getValue();
    			}
    		}
  			meldinger = hentMeldingMerknader(utvalget);
  			merknadValg = utvalget;
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
   			
  		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapportering.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  			
  		}
/*
 * Sorteringer  		
 */
  		if (datoHSort != null){ // Sortering dato hendt
  			
  			 sortermeldingerHendelse(meldinger);
  			 
//  			 meldinger.sort((vm1, vm2)->vm2.getDatoforhendelse().compareTo(vm1.getDatoforhendelse()));
  		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
  		 	 dataModel.put(merknadlisteKey, merk);
   			
  		 	SimpleScalar simple = new SimpleScalar(utvalg);
  		 	dataModel.put(utvalgKey, simple);
  			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
  		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
  		    dataModel.put(meldeKey,meldinger);
  	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapportering.html"));

    		// Load the FreeMarker template
    		Representation pasientkomplikasjonFtl = clres2.get();

    		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
    				MediaType.TEXT_HTML);
    		return templatemapRep;
  		}
  		if (datoMSort != null){ // Sortering dato meldt
  			
 			 sortermeldingerMeldt(meldinger);
 			 
// 			 meldinger.sort((vm1, vm2)->vm2.getDatoforhendelse().compareTo(vm1.getDatoforhendelse()));
 		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
 		 	 dataModel.put(merknadlisteKey, merk);
  			
 		 	SimpleScalar simple = new SimpleScalar(utvalg);
 		 	dataModel.put(utvalgKey, simple);
 			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
 		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
 		    dataModel.put(meldeKey,meldinger);
 	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapportering.html"));

   		// Load the FreeMarker template
   		Representation pasientkomplikasjonFtl = clres2.get();

   		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
   				MediaType.TEXT_HTML);
   		return templatemapRep;
 		}  		
  		
    	if (form != null){
    		for (Parameter entry : form) {
    			if (entry.getValue() != null && !(entry.getValue().equals(""))){
    					System.out.println(entry.getName() + "=" + entry.getValue());
    					meldingsID = entry.getValue();
     					if (entry.getName().equals("excel")){
    	    				page = "../hemovigilans/rapportering.html";
    	    				toPDF = true;
    	    				Response response = getResponse(); 
    	    		         // String path = "C:\\hemovigilans\\rapporter";
    	    				 FileRepresentation representation = new FileRepresentation(path, MediaType.APPLICATION_EXCEL); 
    	    			     
    	    			    Disposition disposition = representation.getDisposition(); 
    	    			    disposition.setType(disposition.TYPE_ATTACHMENT); 
    	    			    disposition.setFilename("leveranse" + ".xls"); 
    	    			     
    	    			    response.setEntity(representation); 
    	    			    return  representation;
   /* 	        			LoginModel newLogin = new LoginModel();
    	        			newLogin.setSaksbehandler(login.getSaksbehandler());
    	        			newLogin.setPassord(login.getSaksbehandler().getBehandlerpassord());
    	        			newLogin.setEpostAdresse(login.getSaksbehandler().getBehandlerepost());
    	        			List<Saksbehandler> saksbehandlere = (List<Saksbehandler>) sessionAdmin.getSessionObject(request,behandlereKey);
    	    				invalidateSessionobjects();
    	    				sessionAdmin.setSessionObject(request, newLogin, loginKey);
    	       			    sessionAdmin.setSessionObject(request, saksbehandlere, behandlereKey);
    */
    	    			}
    			}
    		}
  
    	}
    	Map<String,List> meldingDetaljene = null;
    	Map<String,List> histMeldingsdetaljer = null; // Dette inneholder historiske meldingsdetaljer når flere meldinger har samme nøkkel
    	String histmeldingsNokkel = null;
/*
 * Henter meldingsdetaljer til en valgt melding    	
 */
   	
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
              "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
//Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/rapportering.html"));
	     
	        // Load the FreeMarker template
	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
    
    }

}
