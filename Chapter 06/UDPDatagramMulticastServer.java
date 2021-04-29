package packt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPDatagramMulticastServer {

    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> networkInterfaces;
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
                displayNetworkInterfaceInformation(networkInterface);
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        try {
            System.setProperty("java.net.preferIPv6Stack", "true");
            DatagramChannel channel = DatagramChannel.open();
            NetworkInterface networkInterface = NetworkInterface.getByName("eth1");
            channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);
            InetSocketAddress group = new InetSocketAddress("FF01:0:0:0:0:0:0:FC", 9003);

            String message = "The message";
            ByteBuffer buffer = ByteBuffer.allocate(message.length());
            buffer.put(message.getBytes());

            while (true) {
//                System.out.println(buffer);
                channel.send(buffer, group);
                System.out.println("Sent the multicast message: " + message);
                buffer.clear();
//                System.out.println(buffer);

                buffer.mark();
                System.out.print("Sent: [");
                StringBuilder msg = new StringBuilder();
                while (buffer.hasRemaining()) {
                    msg.append((char) buffer.get());
                }
                System.out.println(msg + "]");
                buffer.reset();

                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // Adapted from: https://docs.oracle.com/javase/tutorial/networking/nifs/listing.html
    static void displayNetworkInterfaceInformation(NetworkInterface networkInterface) {
        try {
            System.out.printf("Display name: %s\n", networkInterface.getDisplayName());
            System.out.printf("Name: %s\n", networkInterface.getName());
            System.out.printf("Supports Multicast: %s\n", networkInterface.supportsMulticast());
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                System.out.printf("InetAddress: %s\n", inetAddress);
            }
            System.out.println();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

    }
}
