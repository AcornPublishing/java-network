package packt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SymmetricEchoServer {

    public static String decrypt(String encryptedText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedBytes = decoder.decode(encryptedText);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedText = new String(decryptedBytes);
            return decryptedText;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | 
                InvalidKeyException | IllegalBlockSizeException | 
                BadPaddingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static SecretKey getSecretKey() {
        SecretKey keyFound = null;
        try {
            File file = new File("secretkeystore.jks");
            final KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(new FileInputStream(file),
                    "keystorepassword".toCharArray());
            KeyStore.PasswordProtection keyPassword
                    = new KeyStore.PasswordProtection("keypassword".toCharArray());
            KeyStore.Entry entry = keyStore.getEntry("secretKey", keyPassword);
            keyFound = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
        } catch (KeyStoreException | IOException | 
                NoSuchAlgorithmException | CertificateException ex) {
            ex.printStackTrace();
        } catch (UnrecoverableEntryException ex) {
            ex.printStackTrace();;
        } 
        return keyFound;
    }

    public static void main(String[] args) {
        System.out.println("Simple Echo Server");
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Waiting for connection.....");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client");

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(
                            clientSocket.getOutputStream(), true)) {
                String inputLine;
                while ((inputLine = br.readLine()) != null) {

                    String decryptedText = decrypt(inputLine, getSecretKey());
                    System.out.println("Client request: " + decryptedText);
                    out.println(inputLine);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Simple Echo Server Terminating");
    }
}
