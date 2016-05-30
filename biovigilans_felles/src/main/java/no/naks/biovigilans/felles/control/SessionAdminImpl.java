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
	private String dbKey = "key";
	private String jdbccellerKey = "jdbccellerkey";
	private String jdbchemoKey = "jdbchemokey";
	private JdbcTemplate chosenTemplate = null;
	
	public SessionAdminImpl() {
		super();
		  System.out.println("SessionAdmin felles started  - inneholder jdbcTemplates (saksbehandling)");
	}

	/* getChosenDB
	 * Denne rutinen returnerer nøkkel til hvilken database som er valgt
	 */
	public String getChosenDB(Request req) {
		String idKey = dbKey+"hemovigilans";
		String chosenDB = (String)getSessionObject(req, idKey);
		if (chosenDB == null){
			idKey = dbKey+"cellerogvev";
			chosenDB = (String)getSessionObject(req, idKey);
		}
			
		return chosenDB;
	}

	/* setChosenDB
	 * Denne rutinen setter opp riktig jdbcTemplate etter hvilken database bruker velger
	 */
	public void setChosenDB(Request req, String chosenDB) {
		String idKey = dbKey+chosenDB;
		setSessionObject(req, chosenDB, idKey);
		if (chosenDB != null && !chosenDB.equals("hemovigilans")){
			setSessionObject(req, cellerogvevjdbcTemplate, jdbccellerKey);
		}
		if (chosenDB != null && chosenDB.equals("hemovigilans")){
			setSessionObject(req, hemovigilansjdbcTemplate,jdbchemoKey );

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
