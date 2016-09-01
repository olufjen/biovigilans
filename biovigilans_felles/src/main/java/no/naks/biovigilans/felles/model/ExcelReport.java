package no.naks.biovigilans.felles.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.restlet.Request;

import no.naks.biovigilans.felles.control.SessionAdmin;
import no.naks.biovigilans.model.Annenkomplikasjon;
import no.naks.biovigilans.model.Giverkomplikasjon;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.Regionstatistikk;
import no.naks.biovigilans.model.Vigilansmelding;
/**
 * Denne klassen benyttes til å lage rapporter til excel
 * Den benyttes i saksbehandlingen
 * @author olj
 * @param <T>
 *
 */
public class ExcelReport<T> {
	private  List<Annenkomplikasjon> annenListe;
	private  List<Pasientkomplikasjon> pasientListe;
	private List<Giverkomplikasjon> giverListe;
	private List<Regionstatistikk> statistikk;
	private List<Regionstatistikk> foretakStatistikk;
	private List<Regionstatistikk> sykehusStatistikk;
	private List<List> allclassified;
	private Map<List<T>,String> classification;
	private Map<List<T>,String> pasientclassification;
	private Map<List<T>,String> giverclassification;
	private   XSSFCellStyle cellStyle;
	
	protected SessionAdmin sessionAdmin = null;
	
	public ExcelReport(		
			SessionAdmin sessionAdmin) {
		super();
		allclassified = new ArrayList<>();
//		classification = new HashMap<>();
		
		this.sessionAdmin = sessionAdmin;
	}
	
	public List<Regionstatistikk> getForetakStatistikk() {
		return foretakStatistikk;
	}

	public void setForetakStatistikk(List<Regionstatistikk> foretakStatistikk) {
		this.foretakStatistikk = foretakStatistikk;
	}

	public List<Regionstatistikk> getSykehusStatistikk() {
		return sykehusStatistikk;
	}

	public void setSykehusStatistikk(List<Regionstatistikk> sykehusStatistikk) {
		this.sykehusStatistikk = sykehusStatistikk;
	}

	public void setStatistikk(List<Regionstatistikk> statistikk){
		this.statistikk = statistikk;
	}
	/**
	 * createBook
	 * Denne rutinene lager regneark til rapporter
	 * @param meldinger Liste over meldinger som danner grunnlaget for rapportene
	 * @param annenKey
	 * @param giverKey
	 * @param pasientKey
	 * @param request
	 * @return
	 */
	public String createBook(List<Vigilansmelding> meldinger, String annenKey,String giverKey,String pasientKey,Request request) {
		annenListe = (List)sessionAdmin.getSessionObject(request,annenKey); 
		giverListe = (List)sessionAdmin.getSessionObject(request,giverKey); 
		pasientListe = (List)sessionAdmin.getSessionObject(request,pasientKey); 
		XSSFWorkbook workbook = new XSSFWorkbook();
		  String sheetName = "Alle meldinger";
		  String header1 = "Beskrivelse";
		  String header2 = "Meldingstype";
		  String[]headers = {header1,header2};
		  XSSFSheet sheet = createHeader(workbook, sheetName,headers);
		  cellStyle = workbook.createCellStyle();
			
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  cellStyle.setDataFormat(celldataFormat.getFormat("dd.mm.yyyy"));
	      int rc = 3;
	      int teller = 0;
	      int nTeller = 0;
	      for (Vigilansmelding melding : meldinger){
	    	   Row row = sheet.createRow(++rc);
	           int columnCount = 0;
	           Cell cell = row.createCell(++columnCount);
	           cell.setCellValue(melding.getDatoforhendelse());
	           cell.setCellStyle(cellStyle);
	           Cell celloppdaget = row.createCell(++columnCount);
	           celloppdaget.setCellValue(melding.getMeldingsdato());
	           celloppdaget.setCellStyle(cellStyle);
	           Cell idCell = row.createCell(++columnCount);
	           idCell.setCellValue(melding.getMeldingsnokkel());
	           Cell beskrivelse = row.createCell(++columnCount);
	           beskrivelse.setCellValue(melding.getSupplerendeopplysninger());
	           Cell meldtype = row.createCell(++columnCount);
	           meldtype.setCellValue(melding.getMeldingstype());
	           Cell meldstatus = row.createCell(++columnCount);
	           String status = melding.getSjekklistesaksbehandling();
	           if (status != null && !status.equals("Avvist"))
	        	   nTeller++;
	           meldstatus.setCellValue(melding.getSjekklistesaksbehandling());
 	          teller ++;
	      }
	      rc++;
	     
	      Row srow = sheet.createRow(++rc);
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger totalt");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	      Row ssrow = sheet.createRow(++rc);
	      Cell nsummer = ssrow.createCell(2);
	      nsummer.setCellValue("Antall ikke avviste meldinger");
	      Cell sumnCount = ssrow.createCell(3);
	      sumnCount.setCellValue(nTeller);
	      createAnnen(meldinger, workbook);
	      createGiver(meldinger, workbook);
	      createPasient(meldinger, workbook);
	      createStatistikk(workbook);
	 	File temp = null;
	 	File directory = new File("C:\\hemovigilans\\rapporter");
	 	boolean bdirs = false;
	 	String path = "";
	 	if (!directory.exists()){
	 		bdirs = directory.mkdirs();
	 		if (bdirs)
	 			System.out.println("Katalog laget");
	 	}
			try {
				temp = File.createTempFile("rapport", ".xlsx",directory);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.out.println("File not created "+e.getMessage());
			}
			
			if (temp != null){
				path = temp.getAbsolutePath();
			}
			if (path == null){
				path = "";
			}
			System.out.println("File created "+ path);
//	 	  String path = temp.getAbsolutePath();
	      try (FileOutputStream outputStream = new FileOutputStream(path)) {
	    	   workbook.write(outputStream);
	    	   outputStream.flush();
	    	   outputStream.close();
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	       try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return path;   
	     	
	}
	protected void createStatistikk(XSSFWorkbook workbook){
		  String sheetName = "Statistikk regioner og foretak";
		  String header1 = "";
		  String[] headers = {header1};
		  
		  XSSFSheet sheet = createHeader(workbook, sheetName,headers);
		  int rc = 3;
		  Row toprow = sheet.createRow(rc);
		  Cell regionTopp = toprow.createCell(1);
		  regionTopp.setCellValue("Fordelt på regioner");
		  regionTopp.setCellStyle(cellStyle);
		  int teller = 0;
		  for (Regionstatistikk region : statistikk){
			  Row row = sheet.createRow(++rc);
    		  int columnCount = 0;
    		  Cell regionNavn = row.createCell(++columnCount);
    		  regionNavn.setCellValue(region.getRegion());
    		  Cell regionAntall = row.createCell(++columnCount);
    		  regionAntall.setCellValue(region.getAntall());
    		  
		  }
		  rc++;
		  Row foretakrow = sheet.createRow(++rc);
		  Cell foretakTopp = foretakrow.createCell(1);
		  foretakTopp.setCellValue("Fordelt på foretak");
		  foretakTopp.setCellStyle(cellStyle);
		  for (Regionstatistikk region : foretakStatistikk){
			  String foretakNavn = region.getRegion();
			  if (foretakNavn == null || foretakNavn.equals(""))
				  foretakNavn = "Ukjent";
			  Row row = sheet.createRow(++rc);
    		  int columnCount = 0;
    		  Cell regionNavn = row.createCell(++columnCount);
    		  regionNavn.setCellValue(foretakNavn);
    		  Cell regionAntall = row.createCell(++columnCount);
    		  regionAntall.setCellValue(region.getAntall());
    		  
		  }
		  rc++;
		  Row sykehusrow = sheet.createRow(++rc);
		  Cell sykehusTopp = sykehusrow.createCell(1);
		  sykehusTopp.setCellValue("Fordelt på sykehus");
		  sykehusTopp.setCellStyle(cellStyle);
		  for (Regionstatistikk region : sykehusStatistikk){
			  String foretakNavn = region.getRegion();
			  if (foretakNavn == null || foretakNavn.equals(""))
				  foretakNavn = "Ukjent";
			  Row row = sheet.createRow(++rc);
    		  int columnCount = 0;
    		  Cell regionNavn = row.createCell(++columnCount);
    		  regionNavn.setCellValue(foretakNavn);
    		  Cell regionAntall = row.createCell(++columnCount);
    		  regionAntall.setCellValue(region.getAntall());
    		  
		  }

	}

	/**
	 * classify
	 * Denne funksjonen klassifiserere meldinger basert på lambda uttrykk classifyP
	 * @param lclassification
	 * @param meldingsListe
	 * @param meldinger
	 * @param key
	 * @param classifyP Predicate lambda uttrykk
	 */
	protected void classify(Map<List<T>,String> lclassification,List<T> meldingsListe,List<Vigilansmelding> meldinger, String key,Predicate<T> classifyP){
		List<T> result = new ArrayList<>();

		for ( T komp : meldingsListe){
			String flag = "";
			Vigilansmelding akomp = (Vigilansmelding)komp;
			for (Vigilansmelding melding:meldinger){
				if (melding.getMeldeid().equals(akomp.getMeldeid())){
					flag = melding.getSjekklistesaksbehandling();
					break;
				}
			}
			if (!flag.equals("Avvist")){
				if (classifyP.test(komp)){
					result.add(komp);

				}
			}

		}
		if (!result.isEmpty())
			lclassification.put( result,key);
	
	}
	protected void createAnnen(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Andre hendelser";
		  String header1 = "Klassifikasjon";
		  String header2 = "Delklassifikasjon";
		  String headers[] = {header1,header2};
//		  String code = "";
			Map<List<T>,String> aclassification = new HashMap();
		  XSSFSheet sheet = createHeader(workbook, sheetName,headers);
	      int rc = 3;
	      int teller = 0;
	      int nTeller = 0;
//	      Row toprow = sheet.createRow(rc-1);
	      for (Vigilansmelding melding : meldinger){
	    	  if (melding.getMeldingstype().equals("Annen hendelse")){
	    		  Annenkomplikasjon komplikasjonen = null;
	    		  if (annenListe != null && !annenListe.isEmpty() ){
	    			  for (Annenkomplikasjon komplikasjon : annenListe){
	    				  if (komplikasjon.getMeldeid().equals(melding.getMeldeid())){
	    					  komplikasjonen = komplikasjon;
	    					  if (!melding.getSjekklistesaksbehandling().equals("Avvist")){
	    						  String code = komplikasjonen.getDelkode();
	    						  Long aId = komplikasjonen.getMeldeid();
	    						  /*
	    						   * Finn hvilke andre meldinger som har samme klassifikasjon som denne meldingen.
	    						   * - Og som ikke er avvist!?
	    						   */

		    					  Predicate<Annenkomplikasjon> classifyP = (Annenkomplikasjon s) -> (s != null && code.equals(s.getDelkode()));
		    					  classify(aclassification,(List<T>) annenListe,meldinger,code,(Predicate<T>) classifyP);
	    					  }
	    					  break;
	    				  }
	    			  }
	    		  }
	    		  String klassifikasjon = "Ikke satt";
	    		  String delkode = "Ikke satt";
	    		  if (komplikasjonen != null){
	    			  klassifikasjon = komplikasjonen.getKlassifikasjon();
	    			  delkode = komplikasjonen.getDelkode();
	    		  }
	    		  Row row = sheet.createRow(++rc);
	    		  int columnCount = 0;
	    		  Cell cell = row.createCell(++columnCount);
	    		  cell.setCellValue(melding.getDatoforhendelse());
	    		  cell.setCellStyle(cellStyle);
	    		  Cell celloppdaget = row.createCell(++columnCount);
	    		  celloppdaget.setCellValue(melding.getMeldingsdato());
	    		  celloppdaget.setCellStyle(cellStyle);
	    		  Cell idCell = row.createCell(++columnCount);
	    		  idCell.setCellValue(melding.getMeldingsnokkel());
	    		  Cell beskrivelse = row.createCell(++columnCount);
	    		  beskrivelse.setCellValue(klassifikasjon);
	    		  Cell meldtype = row.createCell(++columnCount);
	    		  meldtype.setCellValue(delkode);
	    		  Cell meldstatus = row.createCell(++columnCount);
	    		  meldstatus.setCellValue(melding.getSjekklistesaksbehandling());
	              String status = melding.getSjekklistesaksbehandling();
		           if (status != null && !status.equals("Avvist"))
		        	   nTeller++;
	    		  teller ++; 
	    	  }
	
	      }
	      rc++;
	      Row srow = sheet.createRow(++rc);
	      srow.setRowStyle(cellStyle);
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	      Row ssrow = sheet.createRow(++rc);
	      ssrow.setRowStyle(cellStyle);
	      Cell nsummer = ssrow.createCell(2);
	      nsummer.setCellValue("Antall ikke avviste meldinger");
	      Cell sumnCount = ssrow.createCell(3);
	      sumnCount.setCellValue(nTeller);
	      Function<Annenkomplikasjon,String> classification = (Annenkomplikasjon s) -> s.getDelkode();
	      createclassificationAnnen(aclassification,"Statistikk klassifikasjoner andre hendelser",workbook,(Function<T, String>) classification);
	     
    }
	/**
	 * creatClassificationAnnen
	 * Denne rutinen samler statistikk over klassifikasjoner for en type meldingo
	 * @param lclassification En Hashmap av lister over klassifikasjoner
	 * @param pageName
	 * @param workbook
	 * @param f
	 */
	protected void createclassificationAnnen(Map<List<T>,String> lclassification,String pageName,XSSFWorkbook workbook, Function<T,String> f){
		  String sheetName = pageName;
		  String header1 = "Klassifikasjoner";
		  String header2 = "Antall";
		  String headers[] = {header1,header2};
//		  String code = "";
		  
		  XSSFSheet sheet = createHeader(workbook, sheetName,headers);
	      int rc = 3;
	      Row toprow = sheet.createRow(rc);
	      int columnct = 4;
	      Cell klassification = toprow.createCell(columnct);
	      Set<List<T>> set = lclassification.keySet();
	      Iterator itr = set.iterator();
	      while (itr.hasNext()){
	    	  Row nrow = sheet.createRow(++rc);
	    	  List<T> kompliste = (List<T>) itr.next();
	    	  T komp = kompliste.get(0);
	    	  Cell klassificationName = nrow.createCell(columnct);
	    	  klassificationName.setCellValue(f.apply(komp));
	    	  Cell classificationAntall = nrow.createCell(columnct+1);
	    	  classificationAntall.setCellValue(kompliste.size());
	      }

	}
	protected void createGiver(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Giverkomplikasjoner";
		  String header1 = "Alvorlighetsgrad";
		  String header2 = "Klinisk resultat";
		  String header3 = "Sted for komplikasjon";
		  String header4 = "Tid fra rapportering til reaksjon";
		  String header5 = "Varighet reaksjon";
		  String header6 = "Tilleggsopplysninger";
		  String header7 = "Status";
		  String[] headers = {header3,header1,header2,header4,header5,header6,header7};
		  XSSFSheet sheet = createHeader(workbook, sheetName,headers);
		  
	      int rc = 3;
	      int teller = 0;
	      int nTeller = 0;
	      for (Vigilansmelding melding : meldinger){
	    	  if (melding.getMeldingstype().equals("Giverkomplikasjon")){
	    		 Giverkomplikasjon komplikasjonen = null;
	    		  if (giverListe != null && !giverListe.isEmpty() ){
	    			  for (Giverkomplikasjon komplikasjon : giverListe){
	    				  if (komplikasjon.getMeldeid().equals(melding.getMeldeid())){
	    					  komplikasjonen = komplikasjon;
	    					  break;
	    				  }
	    			  }
	    		  }
	    		  String klassifikasjon = "Ikke satt";
	    		  String delkode = "Ikke satt";
	    		  if (komplikasjonen != null){
	    			  klassifikasjon = komplikasjonen.getAlvorlighetsgrad();
	    			  delkode = komplikasjonen.getKliniskresultat();
	    		  }
	    		  String sted = komplikasjonen.getStedforkomplikasjon();
	    		  String tidfra = komplikasjonen.getTidfratappingtilkompliasjon();
	    		  String varighet = komplikasjonen.getVarighetkomplikasjon();
	    		  String tillegg = komplikasjonen.getTilleggsopplysninger();
	    		  Row row = sheet.createRow(++rc);
	    		  int columnCount = 0;
	    		  Cell cell = row.createCell(++columnCount);
	    		  cell.setCellValue(melding.getDatoforhendelse());
	    		  cell.setCellStyle(cellStyle);
	    		  Cell celloppdaget = row.createCell(++columnCount);
	    		  celloppdaget.setCellValue(melding.getMeldingsdato());
	    		  celloppdaget.setCellStyle(cellStyle);
	    		  Cell idCell = row.createCell(++columnCount);
	    		  idCell.setCellValue(melding.getMeldingsnokkel());
	    		  Cell cellsted = row.createCell(++columnCount);
	    		  cellsted.setCellValue(sted);
	    		  Cell beskrivelse = row.createCell(++columnCount);
	    		  beskrivelse.setCellValue(klassifikasjon);
	    		  Cell meldtype = row.createCell(++columnCount);
	    		  meldtype.setCellValue(delkode);
	    		  Cell celltidfra = row.createCell(++columnCount);
	    		  celltidfra.setCellValue(tidfra);
	    		  Cell cellvarighet = row.createCell(++columnCount);
	    		  cellvarighet.setCellValue(varighet);
	    		  Cell celltillegg = row.createCell(++columnCount);
	    		  celltillegg.setCellValue(tillegg);
	    		  Cell meldstatus = row.createCell(++columnCount);
	              String status = melding.getSjekklistesaksbehandling();
		           if (status != null && !status.equals("Avvist"))
		        	   nTeller++;
	    		  meldstatus.setCellValue(melding.getSjekklistesaksbehandling());
	    		  teller ++; 
	    	  }
	
	      }
	      rc++;
	      Row srow = sheet.createRow(++rc);
	      srow.setRowStyle(cellStyle);
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	      Row ssrow = sheet.createRow(++rc);
	      ssrow.setRowStyle(cellStyle);
	      Cell nsummer = ssrow.createCell(2);
	      nsummer.setCellValue("Antall ikke avviste meldinger");
	      Cell sumnCount = ssrow.createCell(3);
	      sumnCount.setCellValue(nTeller);
	}	
	protected void createPasient(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Pasientkomplikasjoner";
		  String header1 = "Alvorghetsgrad";
		  String header2 = "Klassifikasjon";
		  String[] headers = {header1,header2};
			Map<List<T>,String> pclassification = new HashMap();
		  XSSFSheet sheet = createHeader(workbook, sheetName,headers);
	      int rc = 3;
	      int teller = 0;
	      int nTeller = 0;
	      for (Vigilansmelding melding : meldinger){
	    	  if (melding.getMeldingstype().equals("Pasientkomplikasjon")){
	    		  Pasientkomplikasjon komplikasjonen = null;
	    		  if (pasientListe != null && !pasientListe.isEmpty() ){
	    			  for (Pasientkomplikasjon komplikasjon : pasientListe){
	    				  if (komplikasjon.getMeldeid().equals(melding.getMeldeid())){
	    					  komplikasjonen = komplikasjon;
	    					  if (!melding.getSjekklistesaksbehandling().equals("Avvist")){
	    						  String lcode = komplikasjonen.getKlassifikasjon();
	    						  if (lcode == null)
	    							  lcode = "";
	    						  String code = lcode;
	    						  Long aId = komplikasjonen.getMeldeid();
	    						  /*
	    						   * Finn hvilke andre meldinger som har samme klassifikasjon som denne meldingen.
	    						   * - Og som ikke er avvist!?
	    						   */
	    						  Predicate<Pasientkomplikasjon> classifyP = (Pasientkomplikasjon s) -> (s != null && code.equals(s.getKlassifikasjon()));
		    					  classify(pclassification,(List<T>) pasientListe,meldinger,code,(Predicate<T>) classifyP);
	    					  }
	    					  break;
	    				  }
	    			  }
	    		  }
	    		  String klassifikasjon = "Ikke satt";
	    		  String delkode = "Ikke satt";
	    		  if (komplikasjonen != null){
	    			  klassifikasjon = komplikasjonen.getAlvorlighetsgrad();
	    			  delkode = komplikasjonen.getKlassifikasjon();
	    		  }	    		  
	    		  Row row = sheet.createRow(++rc);
	    		  int columnCount = 0;
	    		  Cell cell = row.createCell(++columnCount);
	    		  cell.setCellValue(melding.getDatoforhendelse());
	    		  cell.setCellStyle(cellStyle);
	    		  Cell celloppdaget = row.createCell(++columnCount);
	    		  celloppdaget.setCellValue(melding.getMeldingsdato());
	    		  celloppdaget.setCellStyle(cellStyle);
	    		  Cell idCell = row.createCell(++columnCount);
	    		  idCell.setCellValue(melding.getMeldingsnokkel());
	    		  Cell beskrivelse = row.createCell(++columnCount);
	    		  beskrivelse.setCellValue(klassifikasjon);
	    		  Cell meldtype = row.createCell(++columnCount);
	    		  meldtype.setCellValue(delkode);
	    		  Cell meldstatus = row.createCell(++columnCount);
	    		  meldstatus.setCellValue(melding.getSjekklistesaksbehandling());
	              String status = melding.getSjekklistesaksbehandling();
		           if (status != null && !status.equals("Avvist"))
		        	   nTeller++;
	    		  teller ++; 
	    	  }
	
	      }
	      rc++;
	      Row srow = sheet.createRow(++rc);
	      srow.setRowStyle(cellStyle);
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	      Row ssrow = sheet.createRow(++rc);
	      ssrow.setRowStyle(cellStyle);
	      Cell nsummer = ssrow.createCell(2);
	      nsummer.setCellValue("Antall ikke avviste meldinger");
	      Cell sumnCount = ssrow.createCell(3);
	      sumnCount.setCellValue(nTeller);
	      Function<Pasientkomplikasjon,String> classification = (Pasientkomplikasjon s) -> s.getKlassifikasjon();
	      createclassificationAnnen(pclassification,"Statistikk klassifikasjoner pasienthendelser",workbook,(Function<T, String>) classification);
	}
	protected XSSFSheet createHeader(XSSFWorkbook workbook,String sheetName, String[] headers){
	
	      XSSFSheet sheet = workbook.createSheet(sheetName);
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  cellStyle = workbook.createCellStyle();
		  cellStyle.setDataFormat(celldataFormat.getFormat("dd.mm.yyyy"));
		  Font headerFont = workbook.createFont();
		  headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		  cellStyle.setFont(headerFont);
		  
	      Row header = sheet.createRow(2);
	 
	      Cell hendelse = header.createCell(1);
	      if (!sheetName.startsWith("Statistikk")){
	    	  hendelse.setCellValue("Dato for hendelse");
	    	  hendelse.setCellStyle(cellStyle);
	    	  Cell meldt = header.createCell(2);
	  	      meldt.setCellValue("Dato meldt");
	  	      meldt.setCellStyle(cellStyle);
	  	      Cell nokkel = header.createCell(3);
	  	      nokkel.setCellValue("Meldingsnøkkel");
	  	      nokkel.setCellStyle(cellStyle);
	  	      Cell status = header.createCell(6);
		      status.setCellValue("Status");
		      status.setCellStyle(cellStyle);
	      }
	      int hctn = 4;
	     
	      for (String headertxt : headers ){
	    	  Cell opplysninger = header.createCell(hctn);
	    	  opplysninger.setCellValue(headertxt);
	    	  opplysninger.setCellStyle(cellStyle);
	    	  hctn++;
	    	  
	      }

	      header.setRowStyle(cellStyle);
	      return sheet;
	}
}
