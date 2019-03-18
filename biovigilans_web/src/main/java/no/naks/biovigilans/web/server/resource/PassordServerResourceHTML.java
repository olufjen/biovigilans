package no.naks.biovigilans.web.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
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
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;

/**
 * Denne resursen sørger for at bruker får tilsendt sitt passord, dersom bruker har glemt dette.
 * @since 17.10.18:
 * Genererer et engangspassord som sendes til bruker
 * @author olj
 *
 */
public class PassordServerResourceHTML extends SessionServerResource {
	private String meldeTxtId = "melding";
	private String changeId ="change"; // Flagg for å endre passord
	private String buttonTxtId = "buttonTxt";
	private String genPWId = "passwordID";
	private String email = "";
	private String emailID = "email";

	/**
	 * getHemovigilans
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en epost adresse fra en bruker som ikke husker epost/passord
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	 	 String meldingsText = "";
	 	 String pwFlag = "none";
	 	 String buttonTxt = "Hent passord";
	 	 SimpleScalar simple = new SimpleScalar(meldingsText);
	 	 SimpleScalar changePW = new SimpleScalar(pwFlag);
	 	 SimpleScalar hentPW = new SimpleScalar(buttonTxt);
	 	 SimpleScalar epost = new SimpleScalar(email);
	 	 dataModel.put(buttonTxtId, hentPW);
		 dataModel.put( meldeTxtId,simple);
		 dataModel.put(changeId, changePW);
		 dataModel.put(emailID, epost);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
	     setMelderObject();
//	     setTransfusjonsObjects();
    	 melderwebModel.setFormNames(sessionParams);
 //   	 setMelderparams();
    	 melderwebModel.distributeTerms();
    	 
	     dataModel.put(melderId, melderwebModel);
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/passord.html"));
	     melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);

	     sessionAdmin.setSessionObject(request, melderwebModel,melderId);
	        Representation pasientkomplikasjonFtl = clres2.get();
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	}
    /**
     * storeHemovigilans
     * Denne rutinen sørger for å sende passord til oppgitt epost adresse, eller gir melding om at epost adresse ikke finnes
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	TemplateRepresentation  templateRep = null;
 	    Map<String, Object> dataModel = new HashMap<String, Object>();
 	    String meldingsText = "Melders epost finnes ikke, prøv igjen";
 	    String result = "";
	    Request request = getRequest();
	    result = (String)sessionAdmin.getSessionObject(request,genPWId);
	    melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);
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
 //   	String melderPassord = null; Bruker oppgir ikke passord
    	String meldingsNokkel = null;
/*
 * Verdier fra database
 */
		String name ="";
		String passord = "";
		String epost = "";
    	Long melderid = null; 
    	
    	for (Parameter entry : form) {
			if (entry.getValue() != null && !(entry.getValue().equals(""))){
					System.out.println(entry.getName() + "=" + entry.getValue());
					if (entry.getName().equals("k-epost")){
						melderEpost = entry.getValue();
					}
			
			}
			
    	}
    	email = melderEpost;
		Parameter formValue = form.getFirst("passord"); // Bruker oppgir epostadresse
		Parameter changePassword = form.getFirst("changepassord"); // Bruker oppgir å bytte passord
		boolean bStrenght = true;
		String genPW = "";
		if (changePassword != null ){
			for (Parameter entry : form) {
				if (entry.getValue() != null && !(entry.getValue().equals(""))){
					//	System.out.println(entry.getName() + "=" + entry.getValue()+" "+result);
						if (entry.getName().equals("k-genpassword")){
							genPW = entry.getValue();
						}
				
				}
				
	    	}
			if (result != null && result.equals(genPW)){
				String page = "../hemovigilans/changepassord.html";
				 String pwFlag = "none";
				 melderwebModel.setChangePasswd("pw");
				 sessionAdmin.setSessionObject(request,melderwebModel,melderId);
			 	 SimpleScalar changePW = new SimpleScalar(pwFlag);
			 	 dataModel.put(changeId, changePW);
			     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/passord.html"));
			     Representation pasientkomplikasjonFtl = clres2.get();
			        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
			                MediaType.TEXT_HTML);
					redirectPermanent(page);
				 return templatemapRep;	
			}

		}
//	    String page = "../hemovigilans/melder_rapport.html"; 
		if (formValue != null && melderEpost != null){
			List<Melder> rows = melderWebService.selectMelder(melderEpost);
//			List<Map<String, Object>> rows = melderWebService.selectMelder(melderEpost);
			Melder melder = new MelderImpl();
//			Melder melder = null;
			if(rows != null && rows.size() > 0){
				for(Melder rowmelder :rows){
					melderid = rowmelder.getMelderId();
	
/*					if (row.get("meldernavn") != null)
						name = row.get("meldernavn").toString();
					if (row.get("melderpassord") != null)
						passord = row.get("melderpassord").toString();*/
					name = rowmelder.getMeldernavn();
					passord = rowmelder.getMelderPassord();
					epost = rowmelder.getMelderepost();

/*
 * OLJ April 2018
 * Hente alle opplysninger om melder fra DB!?					
 */
/*					if (row.get("melderepost") != null)
						epost = row.get("melderepost").toString();		*/	
/*
 * Decrypting password OLJ 10.01.18					
 */
					passord = adminWebService.decryptMelderPassword(passord);
					if (passord != null){
//						Melder melder = new MelderImpl();
						melder.setMelderId(melderid);
						melder.setMeldernavn(name);
						melder.setMelderPassord(passord);
						melder.setMelderepost(epost);
						sessionAdmin.setSessionObject(request, melder, melderNokkel); 
						break;
					}
				}
				
				
/*				for(Map row:rows){
					melderid = Long.parseLong(row.get("melderid").toString());
	
					if (row.get("meldernavn") != null)
						name = row.get("meldernavn").toString();
					if (row.get("melderpassord") != null)
						passord = row.get("melderpassord").toString();
					if (row.get("melderepost") != null)
						epost = row.get("melderepost").toString();
						melder = new MelderImpl();
						melder.setMelderId(melderid);
						melder.setMeldernavn(name);
						melder.setMelderPassord(passord);
						melder.setMelderepost(epost);
						sessionAdmin.setSessionObject(request, melder, melderNokkel); 
						break;
					
				}*/
			}
			if (melderid != null && melder != null ){
				emailWebService.setSubject("Passord");
/*
 * Decrypt passord før sending OLJ 26.01.18				
 */
				passord = adminWebService.decryptMelderPassword(melder);
			 	result = RandomStringUtils.randomAlphabetic(16);
			 	sessionAdmin.setSessionObject(request,result,genPWId);
     	    	emailWebService.setEmailText("Ditt engangspassord er: "+result+ " Du må nå oppgi dette passordet og velge Bytte passord.");
    	    	 emailWebService.setMailTo(melder.getMelderepost());
    	    	 emailWebService.sendEmail("");
				meldingsText = "Melding med et generert passord er sendt til oppgitt adresse";
			}
			bStrenght = adminWebService.checkStrenghtPassword(melder);
	    
		}
		if (bStrenght){
			SimpleScalar simple = new SimpleScalar(meldingsText);
		 	 String pwFlag = "block";
		 	 String buttonTxt = "Hent passord på nytt";
		 	 SimpleScalar hentPW = new SimpleScalar(buttonTxt);
		 	 dataModel.put(buttonTxtId, hentPW);
		 	 SimpleScalar changePW = new SimpleScalar(pwFlag);
		 	 SimpleScalar eepost = new SimpleScalar(email);
			 dataModel.put( meldeTxtId,simple);
			 dataModel.put(changeId, changePW);
			 dataModel.put(emailID, eepost);
			//Feil passord går til startside.
	 		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/passord.html"));
			Representation pasientkomplikasjonFtl = clres2.get();
			templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
					MediaType.TEXT_HTML);
			return templateRep;
		} else {
			String page = "../hemovigilans/changepassord.html";
			melderwebModel.setChangePasswd("pw");
			sessionAdmin.setSessionObject(request,melderwebModel,melderId);
		     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/passord.html"));
		     Representation pasientkomplikasjonFtl = clres2.get();
		        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
		                MediaType.TEXT_HTML);
				redirectPermanent(page);
			 return templatemapRep;	
		}

    }
}
