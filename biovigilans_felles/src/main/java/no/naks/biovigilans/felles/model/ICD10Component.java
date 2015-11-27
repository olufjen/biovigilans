package no.naks.biovigilans.felles.model;


import java.io.Serializable;
import java.util.List;


import no.naks.biovigilans.felles.xml.Term;
import no.naks.biovigilans.felles.xml.Title;

/**
 * Denne klassen representerer ICD10 strukturerer
 * @author olj
 *
 */
public class ICD10Component {


	private Title title;
	

	private Term term;
	
	private String strCode;
	private String strTitle;
	private String strSee;
	private String strseeAlso;
	private String strseeCat;
	private String strsubCat;
	private String strTerm;
	


	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
		List<Serializable> names = title.getContent(); 
	//	this.strTitle = title.getvalue();
	}

	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	//	this.strTerm = term.
	}

	
	public String getStrCode() {
		return strCode;
	}
	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public String getStrSee() {
		return strSee;
	}
	public void setStrSee(String strSee) {
		this.strSee = strSee;
	}
	public String getStrseeAlso() {
		return strseeAlso;
	}
	public void setStrseeAlso(String strseeAlso) {
		this.strseeAlso = strseeAlso;
	}
	public String getStrseeCat() {
		return strseeCat;
	}
	public void setStrseeCat(String strseeCat) {
		this.strseeCat = strseeCat;
	}
	public String getStrsubCat() {
		return strsubCat;
	}
	public void setStrsubCat(String strsubCat) {
		this.strsubCat = strsubCat;
	}
	public String getStrTerm() {
		return strTerm;
	}
	public void setStrTerm(String strTerm) {
		this.strTerm = strTerm;
	}

	
	
}
