package no.naks.biovigilans_admin.web.control;

import java.util.ArrayList;
import java.util.List;

import no.naks.biovigilans.felles.control.AdminWebService;
import no.naks.biovigilans.felles.control.SessionAdmin;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.felles.control.SaksbehandlingWebService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.restlet.Request;

/**
 * SaksbehandlingRealm
 * Denne klassen henter brukerinformasjon basert på et login skjermbilde og sjekker mot informasjon i db
 * Den benytter Apache shiro rammeverk til autentisering
 * @author olj
 *
 */
public class SaksbehandlingRealm extends AuthorizingRealm {

	private SimpleCredentialsMatcher matcher;
	private SaksbehandlingWebService saksbehandlingWebservice;
	private SessionAdmin sessionAdmin;
	private List<Saksbehandler> saksbehandlere = null;
	private Saksbehandler loginSaksbehandler = null;
	private List<String> credentials;
	private List<String> principals;
	private List<SimpleAccount> accounts;
	private String accountKey = "accountKey";
	private Request request;
	private AdminWebService adminWebService;

	public SaksbehandlingRealm() {
		super();
		matcher = new SimpleCredentialsMatcher();
		setCredentialsMatcher(matcher);
		credentials = new ArrayList<String>();
		principals = new ArrayList<String>();
		accounts = new ArrayList<SimpleAccount>();
		 System.out.println("Saksbehandling realm created");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(			
			AuthenticationToken userToken) throws AuthenticationException {
		System.out.println("Saksbehandling realm authentication info check");
		SimpleAccount endAccount = null;
		boolean result = false;
		boolean brukerogpassord = false;
		sessionAdmin.setSessionObject(request, endAccount, accountKey);
		for (SimpleAccount account : accounts){
			result = matcher.doCredentialsMatch(userToken, account);
			if (result){
				endAccount = account;
				sessionAdmin.setSessionObject(request, endAccount, accountKey);
				break;
			}
		}
		if (result){
			for (Saksbehandler saksbehandler : saksbehandlere){
				 String credentials = (String)endAccount.getCredentials();
				 String principal = (String)userToken.getPrincipal();
  				if (saksbehandler.getBehandlerepost().equalsIgnoreCase(principal) && saksbehandler.getBehandlerpassord().equals(credentials)){
					loginSaksbehandler = saksbehandler;
					brukerogpassord = true;
					break;
				}

			}
		}
		if (!brukerogpassord)
			return null;
		return endAccount;
	}
	
	public AdminWebService getAdminWebService() {
		return adminWebService;
	}

	public void setAdminWebService(AdminWebService adminWebService) {
		this.adminWebService = adminWebService;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}

	public SaksbehandlingWebService getSaksbehandlingWebservice() {
		return saksbehandlingWebservice;
	}

	public void setSaksbehandlingWebservice(
			SaksbehandlingWebService saksbehandlingWebservice) {
		this.saksbehandlingWebservice = saksbehandlingWebservice;
		saksbehandlere = this.saksbehandlingWebservice.collectSaksbehandlere();
		for (Saksbehandler saksbehandler: saksbehandlere){
//			String decryptPW = "oluf";
			String decryptPW = adminWebService.decryptsaksbehandlerPassword(saksbehandler.getBehandlerpassord());
			principals.add(saksbehandler.getBehandlerepost());
			credentials.add(decryptPW);
			SimpleAccount account = new SimpleAccount(saksbehandler.getBehandlerepost(),decryptPW,"dbrealm");
		
			accounts.add(account);
		}
		
	}

	public SessionAdmin getSessionAdmin() {
		return sessionAdmin;
	}

	public void setSessionAdmin(SessionAdmin sessionAdmin) {
		this.sessionAdmin = sessionAdmin;
	}

	public List<Saksbehandler> getSaksbehandlere() {
		return saksbehandlere;
	}

	public void setSaksbehandlere(List<Saksbehandler> saksbehandlere) {
		this.saksbehandlere = saksbehandlere;
	}

	public Saksbehandler getLoginSaksbehandler() {
		return loginSaksbehandler;
	}

	public void setLoginSaksbehandler(Saksbehandler loginSaksbehandler) {
		this.loginSaksbehandler = loginSaksbehandler;
	}
	public UsernamePasswordToken createToken(){
		UsernamePasswordToken token = new UsernamePasswordToken(loginSaksbehandler.getBehandlerepost(),loginSaksbehandler.getBehandlerpassord());
		
		return token;
	}

}
