package packt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Scanner;
import javax.crypto.Cipher;

public class AsymmetricEchoClient {

    public static void main(String args[]) {
        System.out.println("Simple Echo Client");

        try (Socket clientSocket
                = new Socket(InetAddress.getLocalHost(), 6000);
                DataOutputStream out = new DataOutputStream(
                        clientSocket.getOutputStream());
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                DataInputStream in = new DataInputStream(
                        clientSocket.getInputStream())) {
            System.out.println("Connected to server");
            Scanner scanner = new Scanner(System.in);

            PublicKey publicKey = AsymmetricKeyUtility.getPublicKey();
            while (true) {
                System.out.print("Enter text: ");
                String inputLine = scanner.nextLine();

                byte[] encodedData = AsymmetricKeyUtility.encrypt(publicKey, inputLine);
                System.out.println(encodedData);
                
                out.write(encodedData);
                if ("quit".equalsIgnoreCase(inputLine)) {
                    break;
                }

                // not encrypted
                String message = br.readLine();
                System.out.println("Server response: " + message);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
