package packt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SymmetricKeyStoreCreation {

    private static KeyStore createKeyStore(String fileName, String password) {
        try {
            File file = new File(fileName);
            
            final KeyStore keyStore = KeyStore.getInstance("JCEKS");
            if (file.exists()) {
                keyStore.load(new FileInputStream(file), password.toCharArray());
            } else {
                keyStore.load(null, null);
                keyStore.store(new FileOutputStream(fileName), password.toCharArray());
            }
            return keyStore;
        } catch (KeyStoreException | IOException | 
                NoSuchAlgorithmException | CertificateException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            final String keyStoreFile = "secretkeystore.jks";
            KeyStore keyStore = createKeyStore(keyStoreFile, "keystorepassword");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecretKey secretKey = keyGenerator.generateKey();

            KeyStore.SecretKeyEntry keyStoreEntry
                    = new KeyStore.SecretKeyEntry(secretKey);
            KeyStore.PasswordProtection keyPassword
                    = new KeyStore.PasswordProtection("keypassword".toCharArray());
            keyStore.setEntry("secretKey", keyStoreEntry, keyPassword);
            keyStore.store(new FileOutputStream(keyStoreFile),
                    "keystorepassword".toCharArray());
            System.out.println(secretKey);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } catch (NoSuchAlgorithmException ex) {
//            ex.printStackTrace();
//        } catch (KeyStoreException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (CertificateException ex) {
//            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
