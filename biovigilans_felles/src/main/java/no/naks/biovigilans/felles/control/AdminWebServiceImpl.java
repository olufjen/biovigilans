package no.naks.biovigilans.felles.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Saksbehandler;
import no.naks.biovigilans.service.SaksbehandlingService;

import com.itextpdf.text.pdf.codec.Base64;

/**
 * AdminWebServiceImpl
 * Denne tjenesten benyttes til administrativt vedlikehold
 * Den benyttes blant annet til kryptering og dekryptering av melderes passord
 * @author olj
 *
 */
public class AdminWebServiceImpl implements AdminWebService {
	
	private MelderWebService melderWebService;
	private SaksbehandlingService saksbehandlingService;
	private byte[] salt;
	private String password = "pss";
	
	public AdminWebServiceImpl() {
		super();
		salt = new String("12345678").getBytes();
		System.out.println("adminwebservice admin created");
		// TODO Auto-generated constructor stub
	}
	
	public SaksbehandlingService getSaksbehandlingService() {
		return saksbehandlingService;
	}

	public void setSaksbehandlingService(SaksbehandlingService saksbehandlingService) {
		this.saksbehandlingService = saksbehandlingService;
	}

	public MelderWebService getMelderWebService() {
		return melderWebService;
	}

	public void setMelderWebService(MelderWebService melderWebService) {
		this.melderWebService = melderWebService;
	}
	/* encyptsaksbehandler
	 * Denne rutinen krypterer saksbehandlers passord
	 * @see no.naks.biovigilans.felles.control.AdminWebService#encyptsaksbehandler(java.util.List)
	 */
	public void encyptsaksbehandler(List<Saksbehandler>  saksbehandlere){
//	      salt = new String("12345678").getBytes();
//	      String password = "pss"; // Nøkkelpassord
	      for (Saksbehandler saksbehandler : saksbehandlere ){
	    	  String navn = saksbehandler.getBehandernavn();
	    	  String orgPassword = saksbehandler.getBehandlerpassord();	        // Decreasing this speeds down startup time and can be useful during testing, but it also makes it easier for brute force attackers
		        int iterationCount = 40000;
		        // Other values give me java.security.InvalidKeyException: Illegal key size or default parameters
		        int keyLength = 128;
		        SecretKeySpec key = null;
				try {
					key = createSecretKey(password.toCharArray(),
					        salt, iterationCount, keyLength);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//		        String originalPassword = "secret";
//		        System.out.println("Original password: " + orgPassword);
		        String encryptedPassword = "";
				try {
					encryptedPassword = encrypt(orgPassword, key);
				} catch (UnsupportedEncodingException | GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//		        System.out.println("Encrypted password: " + encryptedPassword);
		        String decryptedPassword = "";
		        saksbehandler.setBehandlerpassord(encryptedPassword);

				try {
					decryptedPassword = decrypt(encryptedPassword, key);
				} catch (GeneralSecurityException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println("Decrypted password: " + decryptedPassword);	    	  
	    	  
	      }
	      System.out.println("Oppdaterer db " );	  
	      saksbehandlingService.updateSaksbehandlerPW(saksbehandlere);
	}
	/**
	 * decyptsaksbehandlere
	 * Denne rutinen decryterer alle passord for alle saksbehandlere
	 * @param meldere
	 */
	public void decyptsaksbehandlere(List<Saksbehandler>  saksbehandlere){
		 for (Saksbehandler saksbehandler : saksbehandlere ){
			 String orgPassword = saksbehandler.getBehandlerpassord();
			 long mid = saksbehandler.getSakbehandlerid().longValue();
			 String encryptedPassword = orgPassword;
			 String decryptedPassword = "";
			 int iterationCount = 40000;
			 // Other values give me java.security.InvalidKeyException: Illegal key size or default parameters
			 int keyLength = 128;
			 SecretKeySpec key = null;
			 try {
				 key = createSecretKey(password.toCharArray(),
						 salt, iterationCount, keyLength);
			 } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
//			 if (mid == 1){ // Midlertidig !!!
				 try {
					 decryptedPassword = decrypt(encryptedPassword, key);
				 } catch (GeneralSecurityException | IOException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
				 saksbehandler.setBehandlerpassord(decryptedPassword);
//			 }
		     saksbehandlingService.updateSaksbehandlerPW(saksbehandlere);
//			 System.out.println("Decrypted password: " + decryptedPassword);	    	  
		 }
	}	
	/**
	 * decyptmeldere
	 * Denne rutinen decryterer alle assord for alle meldere
	 * @param meldere
	 */
	public void decyptmeldere(List<Melder>  meldere){
		 for (Melder melder : meldere ){
			 String orgPassword = melder.getMelderPassord();
			 long mid = melder.getMelderId().longValue();
			 String encryptedPassword = orgPassword;
			 String decryptedPassword = "";
			 int iterationCount = 40000;
			 // Other values give me java.security.InvalidKeyException: Illegal key size or default parameters
			 int keyLength = 128;
			 SecretKeySpec key = null;
			 try {
				 key = createSecretKey(password.toCharArray(),
						 salt, iterationCount, keyLength);
			 } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
//			 if (mid == 1){ // Midlertidig !!!
				 try {
					 decryptedPassword = decrypt(encryptedPassword, key);
				 } catch (GeneralSecurityException | IOException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
				 melder.setMelderPassord(decryptedPassword);
//			 }

			 melderWebService.updateMelderPW(meldere);
//			 System.out.println("Decrypted password: " + decryptedPassword);	    	  
		 }
	}
	/* encyptmeldere
	 * Denne metoden kryterer alle melderes passord og lagrer disse til databasen.
	 * @see no.naks.biovigilans.felles.control.AdminWebService#encyptmeldere(java.util.List)
	 */
	@Override
	public void encyptmeldere(List<Melder>  meldere){
//	      salt = new String("12345678").getBytes();
//	      String password = "pss"; // Nøkkelpassord
	      for (Melder melder : meldere ){
	    	  String navn = melder.getMeldernavn();
	    	  String orgPassword = melder.getMelderPassord();
		        // Decreasing this speeds down startup time and can be useful during testing, but it also makes it easier for brute force attackers
		        int iterationCount = 40000;
		        // Other values give me java.security.InvalidKeyException: Illegal key size or default parameters
		        int keyLength = 128;
		        SecretKeySpec key = null;
				try {
					key = createSecretKey(password.toCharArray(),
					        salt, iterationCount, keyLength);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//		        String originalPassword = "secret";
//		        System.out.println("Original password: " + orgPassword);
		        String encryptedPassword = "";
				try {
					encryptedPassword = encrypt(orgPassword, key);
				} catch (UnsupportedEncodingException | GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//		        System.out.println("Encrypted password: " + encryptedPassword);
		        String decryptedPassword = "";
		        melder.setMelderPassord(encryptedPassword);

				try {
					decryptedPassword = decrypt(encryptedPassword, key);
				} catch (GeneralSecurityException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//		        System.out.println("Decrypted password: " + decryptedPassword);	    	  
	    	  
	      }
	      melderWebService.updateMelderPW(meldere);
	}
	/**
	 * encryptMelderpassord
	 * Denne rutinen krypterer passord for nye meldere
	 * @param melder
	 */
	public void encryptMelderpassord(Melder melder){
  	  String orgPassword = melder.getMelderPassord();
  	if (melder.getMelderId() == null){
 // Decreasing this speeds down startup time and can be useful during testing, but it also makes it easier for brute force attackers
        int iterationCount = 40000;
        // Other values give me java.security.InvalidKeyException: Illegal key size or default parameters
        int keyLength = 128;
        SecretKeySpec key = null;
  		try {
  			key = createSecretKey(password.toCharArray(),
  			        salt, iterationCount, keyLength);
  		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}

//        String originalPassword = "secret";
//        System.out.println("Original password: " + orgPassword);
        String encryptedPassword = "";
  		try {
  			encryptedPassword = encrypt(orgPassword, key);
  		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
//        System.out.println("Encrypted password: " + encryptedPassword);
//        String decryptedPassword = "";
        melder.setMelderPassord(encryptedPassword);
  	}
 
	}
	/**
	 * decryptMelderPassword
	 * Denne rutinen dekrypterer melders passord.
	 * Rutinen kalles når melder gjør en pålogging: Lager oppfølgingsmelding eller når melder ønsker oppslag i sine meldinger.
	 * @param melder
	 * @return
	 */
	public String decryptMelderPassword(Melder melder){
		 String decryptedPassword = "";
		 String encryptedPassword = melder.getMelderPassord();
//	       System.out.println("Encrypted password: " + encryptedPassword);
         int keyLength = 128;
         SecretKeySpec key = null;
         int iterationCount = 40000;
			try {
				key = createSecretKey(password.toCharArray(),
				        salt, iterationCount, keyLength);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		try {
			decryptedPassword = decrypt(encryptedPassword, key);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println("Decrypted password: " + decryptedPassword);	    	
		return decryptedPassword;
	}
	/**
	 * decryptsaksbehandlerPassword
	 * Denne rutinen dekrypterer saksbehandler passord.
	 * Rutinen kalles når saksbehandlerrealm starter opp
	 * @param pw encrypted password
	 * @return
	 */
	public String decryptsaksbehandlerPassword(String pw){
		 String decryptedPassword = "";
//        System.out.println("Encrypted password: " + pw);
//		 String encryptedPassword = melder.getMelderPassord();
         int keyLength = 128;
         SecretKeySpec key = null;
         int iterationCount = 40000;
			try {
				key = createSecretKey(password.toCharArray(),
				        salt, iterationCount, keyLength);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		try {
			decryptedPassword = decrypt(pw, key);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 //       System.out.println("Decrypted password: " + decryptedPassword);	    	
		return decryptedPassword;
	}
	/**
	 * decryptMelderPassword
	 * Denne rutinen dekrypterer melders passord.
	 * Rutinen kalles når melder gjør en pålogging: Lager oppfølgingsmelding eller når melder ønsker oppslag i sine meldinger.
	 * @param pw encrypted password
	 * @return
	 */
	public String decryptMelderPassword(String pw){
		 String decryptedPassword = "";
//        System.out.println("Encrypted password: " + pw);
//		 String encryptedPassword = melder.getMelderPassord();
         int keyLength = 128;
         SecretKeySpec key = null;
         int iterationCount = 40000;
			try {
				key = createSecretKey(password.toCharArray(),
				        salt, iterationCount, keyLength);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		try {
			decryptedPassword = decrypt(pw, key);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 //       System.out.println("Decrypted password: " + decryptedPassword);	    	
		return decryptedPassword;
	}
	public void setAlterativeSource(String key){
		saksbehandlingService.setAlterativeSource(key);
//		melderWebService.setAlterativeSource(key);
	}
    private SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    private String encrypt(String property, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
     
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(property.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private String base64Encode(byte[] bytes) {
    	int source = 0;
		return Base64.encodeBytes(bytes,source);
//        return Base64.encodeToString(bytes);
    }

    private String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException {
    	if (string.contains(":")){
            String iv = string.split(":")[0];
            String property = string.split(":")[1];
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
            return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");  		
    	}
    	return string;

    }

    private byte[] base64Decode(String property) throws IOException {
    	
    	return Base64.decode(property);
    	
//        return Base64.getDecoder().decode(property);
    }
}
