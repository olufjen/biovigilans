package no.naks.biovigilans.felles.control;

import java.util.List;

import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Saksbehandler;

public interface AdminWebService {

	public void encyptmeldere(List<Melder> meldere);
	public void decyptmeldere(List<Melder>  meldere);
	public String decryptMelderPassword(Melder melder);
	public String decryptMelderPassword(String pw);
	public MelderWebService getMelderWebService();
	public void encyptsaksbehandler(List<Saksbehandler>  saksbehandlere);
	public void decyptsaksbehandlere(List<Saksbehandler>  saksbehandlere);
	public String decryptsaksbehandlerPassword(String pw);
	public void encryptMelderpassord(Melder melder);
	public void setMelderWebService(MelderWebService melderWebService);
	public void setAlterativeSource(String key);
}