package packt;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPMulticastClient {

    public UDPMulticastClient() {
        System.out.println("UDP Multicast Time Client Started");
        try {
            MulticastSocket multicastSocket = new MulticastSocket(9877);
            InetAddress inetAddress = InetAddress.getByName("228.5.6.7");
            multicastSocket.joinGroup(inetAddress);
            
            byte[] data = new byte[256];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            
            while (true) {
                multicastSocket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Message from: " + packet.getAddress() 
                        + " Message: [" + message + "]");        
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("UDP Multicast Time Client Terminated");
    }
    
    public static void main(String[] args) {
        new UDPMulticastClient();
    }
}

