package no.naks.biovigilans.web.server.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;




import no.naks.biovigilans.felles.model.AnnenKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.DonasjonwebModel;
import no.naks.biovigilans.felles.model.GiverKomplikasjonwebModel;
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.model.TransfusjonWebModel;
import no.naks.biovigilans.felles.model.VigilansModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.Vigilansmelding;



import org.apache.commons.lang.WordUtils;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Disposition;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import freemarker.template.SimpleScalar;

/**
 * RapporterLeveranseServerResourceHTML
 * @author olj
 *
 * Denne klassen er knyttet til leveranse.html
 * Det er den endelige kvitteringssiden for en melding til Hemovigilans
 */
public class RapporterLeveranseServerResourceHTML extends SessionServerResource {

	private static final int ALIGN_RIGHT = 0;

	private static final String PARAM_ORDER_TYPE = null;

	private String meldingsId = "melding";

	private Date dato = null;
	private String datoStr = "";
	private String path = "";



	public String getDatoStr() {
		return datoStr;
	}


	public void setDatoStr(String datoStr) {
		this.datoStr = datoStr;
	}


	public String getMeldingsId() {
		return meldingsId;
	}


	public void setMeldingsId(String meldingsId) {
		this.meldingsId = meldingsId;
	}


	public Date getDato() {
		return dato;
	}


	public void setDato(Date dato) {
		this.dato = dato;
	}


	/**
	 * getHemovigilans
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en rapportert hendelse
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	   
	     String datoLevert = null;
/*
 * Hent alle session objekter
 */
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	     melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(getRequest(),melderId);
		 transfusjon = (TransfusjonWebModel) sessionAdmin.getSessionObject(getRequest(),transfusjonId);
    	 giverModel = (GiverKomplikasjonwebModel) sessionAdmin.getSessionObject(getRequest(),giverkomplikasjonId);
    	 annenModel = (AnnenKomplikasjonwebModel) sessionAdmin.getSessionObject(getRequest(),andreHendelseId);
    	 donasjon = (DonasjonwebModel) sessionAdmin.getSessionObject(getRequest(),donasjonId);
   
	     

	 
    	 String alvorGrad = "";
    	 if (transfusjon != null){
    		 alvorGrad = transfusjon.getPasientKomplikasjon().getAlvorlighetsgrad();
    		 Vigilansmelding vigilansmelding = (Vigilansmelding) transfusjon.getPasientKomplikasjon();
    		 try {
				transfusjon.setMeldingLevert(vigilansmelding.getMeldingsdato());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 transfusjon.setMeldingsNokkel(vigilansmelding.getMeldingsnokkel());
    		 meldingsNokkel = vigilansmelding.getMeldingsnokkel();
    		 datoLevert = transfusjon.getMeldLevert();
    		 dataModel.put(meldingsId, transfusjon);
    	 }
    	 if (giverModel != null){
    		 Vigilansmelding vigilansmelding = (Vigilansmelding) giverModel.getGiverKomplikasjon();
    		 alvorGrad = giverModel.getGiverKomplikasjon().getAlvorlighetsgrad();
				try {
					giverModel.setMeldingLevert(vigilansmelding.getMeldingsdato()); // OBS nullpointer !!
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
    		 giverModel.setMeldingsNokkel(vigilansmelding.getMeldingsnokkel());
    		 meldingsNokkel = vigilansmelding.getMeldingsnokkel();
    		 datoLevert = giverModel.getMeldLevert();
    		 dataModel.put(meldingsId, giverModel);
    	 }
    	 if (annenModel != null){
    		 Vigilansmelding vigilansmelding = (Vigilansmelding) annenModel.getAnnenKomplikasjon();
    		
    		 try {
				annenModel.setMeldingLevert(vigilansmelding.getMeldingsdato());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 annenModel.setMeldingsNokkel(vigilansmelding.getMeldingsnokkel());
    		 meldingsNokkel = vigilansmelding.getMeldingsnokkel();
    		 datoLevert = annenModel.getMeldLevert();
    		 dataModel.put(meldingsId, annenModel);
    	 }
    	 
         VigilansModel melding = checkMessageType();
         
    	 if (messageType.equals("none")){
    		 annenModel = new AnnenKomplikasjonwebModel();
    		 try {
				annenModel.setMeldingLevert(new Date());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 annenModel.setMeldingsNokkel("Ingen melding levert");
    		 meldingsNokkel = "Ingen melding levert";
    		 datoLevert = annenModel.getMeldLevert();
    		 dataModel.put(meldingsId, annenModel);
    	 }
    	    SimpleScalar simple = new SimpleScalar(meldingsNokkel);
    	    dataModel.put(nokkelId,simple);
    	    SimpleScalar datoSimple = new SimpleScalar(datoLevert);
    	    dataModel.put(datoId, datoSimple);
    	    String melderEpost = melderwebModel.getMelder().getMelderepost();
    	    if(melderEpost != null || !melderEpost.equals("")){
    	    	 emailWebService.setMailTo(melderEpost);
    	    	 emailWebService.sendEmail(meldingsNokkel); //Kommentert bort til stage !!
    	    }
    	   
    	    checkmessagetoAdmin(alvorGrad,meldingsNokkel);
   
    	    setlinkMap();
    	    
    	    
//    	 dato = melding.getVigilans().getDatoforhendelse();
    	    
    	   try {
    	    
				path = createPDF();
				sessionAdmin.setSessionObject(request,path,"path");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
    	// invalidateSessionobjects();
    	 ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/leveranse.html"));
	     Representation pasientkomplikasjonFtl = clres2.get();
	     TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
    	 return templatemapRep;
	}
	
	@Post
	 public Representation generatePDF(Form form) throws DocumentException, IOException {
		   Request request = getRequest();
		Representation representation = null;
		try {
			path = (String) sessionAdmin.getSessionObject(request, "path");
  			//path = createPDF();
		//	invalidateSessionobjects();
  		
  	    
		Response response = getResponse(); 
          
		 representation = new FileRepresentation(path, MediaType.APPLICATION_PDF); 
	     
	    Disposition disposition = representation.getDisposition(); 
	    disposition.setType(disposition.TYPE_ATTACHMENT); 
	    disposition.setFilename("leveranse" + ".pdf"); 
	     
	    response.setEntity(representation); 
		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
   	 return  representation;
		
	}
	

	
} 
