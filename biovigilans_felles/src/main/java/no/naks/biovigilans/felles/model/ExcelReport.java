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
	protected SessionAdmin sessionAdmin = null;
	
	public ExcelReport(		
			SessionAdmin sessionAdmin) {
		super();

		this.sessionAdmin = sessionAdmin;
	}
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
		  cellStyle.setDataFormat(celldataFormat.getFormat("mm.dd.yyyy"));
	      int rc = 3;
	      int teller = 0;
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
	           meldstatus.setCellValue(melding.getSjekklistesaksbehandling());
 	          teller ++;
	      }
	      rc++;
	      Row srow = sheet.createRow(++rc);
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	      createAnnen(meldinger, workbook);
	      createGiver(meldinger, workbook);
	      createPasient(meldinger, workbook);
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
	protected void createAnnen(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Andre hendelser";
		  String header1 = "Klassifikasjon";
		  String header2 = "Delklassifikasjon";
		  XSSFSheet sheet = createHeader(workbook, sheetName,header1,header2);
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  XSSFCellStyle cellStyle = workbook.createCellStyle();
		  cellStyle.setDataFormat(celldataFormat.getFormat("mm.dd.yyyy"));
	      int rc = 3;
	      int teller = 0;
	     
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
	    		  teller ++; 
	    	  }
	
	      }
	      rc++;
	      Row srow = sheet.createRow(++rc);
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	}
	protected void createGiver(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Giverkomplikasjoner";
		  String header1 = "Alvorlighetsgrad";
		  String header2 = "Klinisk resultat";
		  XSSFSheet sheet = createHeader(workbook, sheetName,header1,header2);
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  XSSFCellStyle cellStyle = workbook.createCellStyle();
		  cellStyle.setDataFormat(celldataFormat.getFormat("mm.dd.yyyy"));
	      int rc = 3;
	      int teller = 0;
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
	}	
	protected void createPasient(List<Vigilansmelding> meldinger,XSSFWorkbook workbook){
		  String sheetName = "Pasientkomplikasjoner";
		  String header1 = "Klassifikasjon";
		  String header2 = "Alvorghetsgrad";
		  XSSFSheet sheet = createHeader(workbook, sheetName,header1,header2);
		  XSSFDataFormat celldataFormat =  workbook.createDataFormat();
		  XSSFCellStyle cellStyle = workbook.createCellStyle();
		  cellStyle.setDataFormat(celldataFormat.getFormat("mm.dd.yyyy"));
	      int rc = 3;
	      int teller = 0;
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
	    		  teller ++; 
	    	  }
	
	      }
	      rc++;
	      Row srow = sheet.createRow(++rc);
	      Cell summer = srow.createCell(2);
	      summer.setCellValue("Antall meldinger");
	      Cell sumCount = srow.createCell(3);
	      sumCount.setCellValue(teller);
	}
	protected XSSFSheet createHeader(XSSFWorkbook workbook,String sheetName, String header1,String header2){
	
	      XSSFSheet sheet = workbook.createSheet(sheetName);
	      Row header = sheet.createRow(2);
	      Cell hendelse = header.createCell(1);
	      hendelse.setCellValue("Dato for hendelse");
	      Cell meldt = header.createCell(2);
	      meldt.setCellValue("Dato meldt");
	      Cell nokkel = header.createCell(3);
	      nokkel.setCellValue("Meldingsnøkkel");
	      Cell opplysninger = header.createCell(4);
	      opplysninger.setCellValue(header1);
	      Cell type = header.createCell(5);
	      type.setCellValue(header2);
	      Cell status = header.createCell(6);
	      status.setCellValue("Status");
	      return sheet;
	}
}
