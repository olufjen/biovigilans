package no.naks.biovigilans.felles.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
 * @author olj
 *
 */
public class ExcelReport {
	private  List<Annenkomplikasjon> annenListe;
	private  List<Pasientkomplikasjon> pasientListe;
	private List<Giverkomplikasjon> giverListe;
	private List<Regionstatistikk> statistikk;
	protected SessionAdmin sessionAdmin = null;
	
	public ExcelReport(		
			SessionAdmin sessionAdmin) {
		super();

		this.sessionAdmin = sessionAdmin;
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
		  XSSFSheet sheet = createHeader(workbook, sheetName,header1,header2);
		  XSSFCellStyle cellStyle = workbook.createCellStyle();
			
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
	           celloppdaget.setCellValue(melding.getDatooppdaget());
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
			String path = "";
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
		  String sheetName = "Statistikk";
		  String header1 = "";
		  String header2 = "";
		  XSSFSheet sheet = createHeader(workbook, sheetName,header1,header2);
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  XSSFCellStyle cellStyle = workbook.createCellStyle();
		  cellStyle.setDataFormat(celldataFormat.getFormat("dd.mm.yyyy"));
		  int rc = 3;
		  int teller = 0;
		  for (Regionstatistikk region : statistikk){
			  Row row = sheet.createRow(++rc);
    		  int columnCount = 0;
    		  Cell regionNavn = row.createCell(++columnCount);
    		  regionNavn.setCellValue(region.getRegion());
    		  Cell regionAntall = row.createCell(++columnCount);
    		  regionAntall.setCellValue(region.getAntall());
    		  
		  }
	}
	protected void createAnnen(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Andre hendelser";
		  String header1 = "Klassifikasjon";
		  String header2 = "Delklassifikasjon";
		  XSSFSheet sheet = createHeader(workbook, sheetName,header1,header2);
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  XSSFCellStyle cellStyle = workbook.createCellStyle();
		  cellStyle.setDataFormat(celldataFormat.getFormat("dd.mm.yyyy"));
	      int rc = 3;
	      int teller = 0;
	      int nTeller = 0;
	      for (Vigilansmelding melding : meldinger){
	    	  if (melding.getMeldingstype().equals("Annen hendelse")){
	    		  Annenkomplikasjon komplikasjonen = null;
	    		  if (annenListe != null && !annenListe.isEmpty() ){
	    			  for (Annenkomplikasjon komplikasjon : annenListe){
	    				  if (komplikasjon.getMeldeid().equals(melding.getMeldeid())){
	    					  komplikasjonen = komplikasjon;
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
	    		  celloppdaget.setCellValue(melding.getDatooppdaget());
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
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	      Row ssrow = sheet.createRow(++rc);
	      Cell nsummer = ssrow.createCell(2);
	      nsummer.setCellValue("Antall ikke avviste meldinger");
	      Cell sumnCount = ssrow.createCell(3);
	      sumnCount.setCellValue(nTeller);
	}
	protected void createGiver(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Giverkomplikasjoner";
		  String header1 = "Alvorlighetsgrad";
		  String header2 = "Klinisk resultat";
		  XSSFSheet sheet = createHeader(workbook, sheetName,header1,header2);
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  XSSFCellStyle cellStyle = workbook.createCellStyle();
		  cellStyle.setDataFormat(celldataFormat.getFormat("dd.mm.yyyy"));
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
	    		  Row row = sheet.createRow(++rc);
	    		  int columnCount = 0;
	    		  Cell cell = row.createCell(++columnCount);
	    		  cell.setCellValue(melding.getDatoforhendelse());
	    		  cell.setCellStyle(cellStyle);
	    		  Cell celloppdaget = row.createCell(++columnCount);
	    		  celloppdaget.setCellValue(melding.getDatooppdaget());
	    		  celloppdaget.setCellStyle(cellStyle);
	    		  Cell idCell = row.createCell(++columnCount);
	    		  idCell.setCellValue(melding.getMeldingsnokkel());
	    		  Cell beskrivelse = row.createCell(++columnCount);
	    		  beskrivelse.setCellValue(klassifikasjon);
	    		  Cell meldtype = row.createCell(++columnCount);
	    		  meldtype.setCellValue(delkode);
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
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	      Row ssrow = sheet.createRow(++rc);
	      Cell nsummer = ssrow.createCell(2);
	      nsummer.setCellValue("Antall ikke avviste meldinger");
	      Cell sumnCount = ssrow.createCell(3);
	      sumnCount.setCellValue(nTeller);
	}	
	protected void createPasient(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Pasientkomplikasjoner";
		  String header1 = "Alvorghetsgrad";
		  String header2 = "Klassifikasjon";
		  XSSFSheet sheet = createHeader(workbook, sheetName,header1,header2);
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  XSSFCellStyle cellStyle = workbook.createCellStyle();
		  cellStyle.setDataFormat(celldataFormat.getFormat("dd.mm.yyyy"));
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
	    		  celloppdaget.setCellValue(melding.getDatooppdaget());
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
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	      Row ssrow = sheet.createRow(++rc);
	      Cell nsummer = ssrow.createCell(2);
	      nsummer.setCellValue("Antall ikke avviste meldinger");
	      Cell sumnCount = ssrow.createCell(3);
	      sumnCount.setCellValue(nTeller);
	}
	protected XSSFSheet createHeader(XSSFWorkbook workbook,String sheetName, String header1,String header2){
	
	      XSSFSheet sheet = workbook.createSheet(sheetName);
	      Row header = sheet.createRow(2);
	      Cell hendelse = header.createCell(1);
	      if (!sheetName.equals("Statistikk")){
	    	  hendelse.setCellValue("Dato for hendelse");
	    	  Cell meldt = header.createCell(2);
	  	      meldt.setCellValue("Dato meldt");
	  	      Cell nokkel = header.createCell(3);
	  	      nokkel.setCellValue("Meldingsnøkkel");
	  	      Cell status = header.createCell(6);
		      status.setCellValue("Status");
	      }
	  
	      Cell opplysninger = header.createCell(4);
	      opplysninger.setCellValue(header1);
	      Cell type = header.createCell(5);
	      type.setCellValue(header2);
	
	      return sheet;
	}
}
