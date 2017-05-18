package no.naks.biovigilans.model;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Dette er implementasjonen av klassen Diskusjon
 * @author olj
 */
public class DiskusjonImpl extends AbstractDiskusjon implements Diskusjon {

	public DiskusjonImpl() {
		super();
		types = new int[] {Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		 SimpleDateFormat dateFormat = 
		            new SimpleDateFormat("yyyy/MM/dd");
	//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		try {
			String strDate = dateFormat.format(date);
			setDatoforkommentar(dateFormat.parse(strDate));
		} catch (ParseException e) {
			System.out.println("date format problem: " + e.toString());
		}
	}

	public void setParams(){
//		Long id = getDiskusjonid();
		Long id = null; // diskusjoner er alltid nye OLJ 22.06.16
		if (id == null){
			params = new Object[]{getDatoforkommentar(),getKommentar(),getTema(),getMeldeid()};
		}else
			params = new Object[]{getDatoforkommentar(),getKommentar(),getTema(),getMeldeid(),getDiskusjonid()};
		
	}	
	public void savetoDiskusjon(){
		setKommentar(null);
		setTema(null);
	}
}
