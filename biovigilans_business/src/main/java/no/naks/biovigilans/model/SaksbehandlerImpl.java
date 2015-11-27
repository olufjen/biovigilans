package no.naks.biovigilans.model;

import java.sql.Types;

public class SaksbehandlerImpl extends AbstractSaksbehandler implements Saksbehandler {

	public SaksbehandlerImpl() {
		super();
		types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		utypes = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
	}
	public void setParams(){
		Long id = getSakbehandlerid();
		
		if (id == null){
			params = new Object[]{getBehandernavn(),getBehandlerepost(),getBehandlertlf(),getBehandlerpassord()};
		}else
			params = new Object[]{getBehandernavn(),getBehandlerepost(),getBehandlertlf(),getBehandlerpassord(),getSakbehandlerid()};
		
	}	
	public void savetoSaksbehandler(){
		setBehandlerepost(null);
		setBehandlerpassord(null);
	}
}
