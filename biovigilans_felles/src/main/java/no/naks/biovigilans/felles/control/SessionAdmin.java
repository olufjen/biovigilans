package no.naks.biovigilans.felles.control;

import javax.servlet.http.HttpSession;

import org.restlet.Request;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Dette en grensesnittdefinisjon for SessionAdmin
 * Klassen håndterer alle session objekter for Restlet resurser
 * Lagt til jdbcTemplates for flere databaser
 * @author olj
 *
 */
public interface SessionAdmin {
	
	public Object getSessionObject(Request request,String idKey);
	public void setSessionObject(Request request,Object o,String idKey);
	public HttpSession getSession(Request request,String idKey);
	public String[] getSessionParams();
	public void setSessionParams(String[] sessionParams);
	public void removesessionObject(Request request,String idKey);
	public String getChosenDB();

	public void setChosenDB(String chosenDB);

	public JdbcTemplate getChosenTemplate();

	public void setChosenTemplate(JdbcTemplate chosenTemplate);

	public JdbcTemplate getHemovigilansjdbcTemplate();

	public void setHemovigilansjdbcTemplate(JdbcTemplate hemovigilansjdbcTemplate);

	public JdbcTemplate getCellerogvevjdbcTemplate();

	public void setCellerogvevjdbcTemplate(JdbcTemplate cellerogvevjdbcTemplate);

}
