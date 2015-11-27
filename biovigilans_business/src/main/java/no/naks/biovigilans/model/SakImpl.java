package no.naks.biovigilans.model;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author olj
 * Dette er implementasjonen av klassen Sak
 */
public class SakImpl extends AbstractSak implements Sak {

	public SakImpl() {
		super();
		types = new int[] {Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		utypes = new int[]{Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.INTEGER};
		 SimpleDateFormat dateFormat = 
		            new SimpleDateFormat("yyyy/MM/dd");
	//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		try {
			String strDate = dateFormat.format(date);
			setDatoSak(dateFormat.parse(strDate));
		} catch (ParseException e) {
			System.out.println("date format problem: " + e.toString());
		}
	}
	public void setParams(){
		Long id = getSaksid();
		
		if (id == null){
			params = new Object[]{getDatoSak(),getSakskommentar(),getSaksopplysninger(),getSakstatus(),getDiskusjonid(),getSakbehandlerId()};
		}else
			params = new Object[]{getDatoSak(),getSakskommentar(),getSaksopplysninger(),getSakstatus(),getDiskusjonid(),getSaksid()};
		
	}	
}
