package packt;

import java.io.IOException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SSLSocketServer {

    private SSLServerSocket serverSocket;
    
    public static void main(String[] args) {
        new SSLSocketServer(4444);
    }

    public SSLSocketServer(int port) {
        try {
            System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
//            System.setProperty("javax.net.ssl.trustStoreType","JCEKS");
            
            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket = (SSLServerSocket) factory.createServerSocket(port);
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                // Client is connected
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                System.out.println("User connected successfully?");
            } // try
            catch (IOException e) {
                e.printStackTrace();
            } // catch
        } // while
    } // run
} // class SSLSocketServer
