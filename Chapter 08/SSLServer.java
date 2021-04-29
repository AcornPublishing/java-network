package packt;

import com.sun.net.ssl.internal.ssl.Provider;
//import com.sun.security.sasl.Provider;

//import com.sun.org.apache.xerces.internal.impl.dtd.XMLContentSpec.Provider;
//import java.security.Provider;
//import com.sun.tracing.Provider;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Security;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

// See https://www.owasp.org/index.php/Using_the_Java_Secure_Socket_Extensions#What_is_JSSE_.3F
public class SSLServer {

    public static void main(String[] args) throws Exception {
        System.out.println("SSL Server Started");
        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");

        SSLServerSocketFactory sslServerSocketfactory = 
                (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = (SSLServerSocket) 
                sslServerSocketfactory.createServerSocket(5000);
        System.out.println("Waiting for a connection");
        SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
        System.out.println("Connection established");

        PrintWriter pw = new PrintWriter(sslSocket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(sslSocket.getInputStream()));

        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            pw.println(inputLine);
            if ("quit".equalsIgnoreCase(inputLine)) {
                break;
            }
            System.out.println("Receiving: " + inputLine);
        }
        pw.close();
        br.close();
        sslSocket.close();
        sslServerSocket.close();
    }

}
