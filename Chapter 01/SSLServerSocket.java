package packt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServerSocket {

    public static void main(String[] args) {
        try {
            SSLServerSocketFactory ssf = 
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket serverSocket = ssf.createServerSocket(8000);
            System.out.println("SSLServerSocket Started");
            try (Socket socket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(
                            socket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()))) {
                System.out.println("Client socket created");
                String line = null;
                while (((line = br.readLine()) != null)) {
                    System.out.println(line);
                    out.println(line);
                }
                br.close();
                System.out.println("SSLServerSocket Terminated");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
