package no.naks.biovigilans.felles.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.restlet.Request;
import org.restlet.ext.servlet.ServletUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Denne klassen administerer session objekter for Restlet Resurser
 * @author olj
 *
 */
public class SessionAdminImpl implements SessionAdmin {
	private String[]sessionParams;
	private JdbcTemplate hemovigilansjdbcTemplate; //  @since 30.03.2016 OLJ
	private JdbcTemplate cellerogvevjdbcTemplate; //  @since 30.03.2016 OLJ
	private String chosenDB = "";
	private JdbcTemplate chosenTemplate = null;
	
	public SessionAdminImpl() {
		super();
		  System.out.println("SessionAdmin felles started  - inneholder jdbcTemplates (saksbehandling)");
	}

	public String getChosenDB() {
		return chosenDB;
	}

	/* setChosenDB
	 * Denne rutinen setter opp riktig jdbcTemplate etter hvilken database bruker velger
	 */
	public void setChosenDB(String chosenDB) {
		this.chosenDB = chosenDB;
		if (chosenDB != null && !chosenDB.equals("hemovigilans")){
			chosenTemplate = cellerogvevjdbcTemplate;
		}
	}

	public JdbcTemplate getChosenTemplate() {
		return chosenTemplate;
	}

	public void setChosenTemplate(JdbcTemplate chosenTemplate) {
		this.chosenTemplate = chosenTemplate;
	}

	public JdbcTemplate getHemovigilansjdbcTemplate() {
		return hemovigilansjdbcTemplate;
	}

	public void setHemovigilansjdbcTemplate(JdbcTemplate hemovigilansjdbcTemplate) {
		this.hemovigilansjdbcTemplate = hemovigilansjdbcTemplate;
	}

	public JdbcTemplate getCellerogvevjdbcTemplate() {
		return cellerogvevjdbcTemplate;
	}

	public void setCellerogvevjdbcTemplate(JdbcTemplate cellerogvevjdbcTemplate) {
		this.cellerogvevjdbcTemplate = cellerogvevjdbcTemplate;
	}

	@Override
	public Object getSessionObject(Request request,String idKey) {
	     HttpServletRequest req = ServletUtils.getRequest(request);
	     HttpSession session = req.getSession();
	     Object result = session.getAttribute(idKey);
		return result;
	}

	@Override
	public void setSessionObject(Request request, Object o, String idKey) {
		  HttpServletRequest req = ServletUtils.getRequest(request);
		  HttpSession session = req.getSession();
		  session.setAttribute(idKey, o);

	}

	@Override
	public HttpSession getSession(Request request, String idKey) {
		HttpServletRequest req = ServletUtils.getRequest(request);
		HttpSession session = req.getSession();
		return session;
	}

	public String[] getSessionParams() {
		return sessionParams;
	}

	public void setSessionParams(String[] sessionParams) {
		this.sessionParams = sessionParams;
	}
	public void removesessionObject(Request request,String idKey){
		  HttpServletRequest req = ServletUtils.getRequest(request);
		  HttpSession session = req.getSession();
		  session.removeAttribute(idKey);
	}

}
