package packt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class AsymmetricEchoServer {

    public static void main(String[] args) {
        System.out.println("Simple Echo Server");
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Waiting for connection.....");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client");

            try (DataInputStream in = new DataInputStream(
                    clientSocket.getInputStream());
                    PrintWriter out = new PrintWriter(
                            clientSocket.getOutputStream(), true);) {
                byte[] inputLine = new byte[171];

                PrivateKey privateKey = AsymmetricKeyUtility.getPrivateKey();

                while (true) {
                    int length = in.read(inputLine);
                    String buffer = AsymmetricKeyUtility.decrypt(privateKey, inputLine);
                    System.out.println("Client request: " + buffer);

                    if ("quit".equalsIgnoreCase(buffer)) {
                        break;
                    }

                    // not encrypted
                    out.println(buffer);
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
