package packt;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

public class UDPDatagramMulticastClient {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv6Stack", "true");
        NetworkInterface networkInterface = NetworkInterface.getByName("eth1");
        DatagramChannel channel = DatagramChannel.open()
                .bind(new InetSocketAddress(9003))
                .setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);

        InetAddress group = InetAddress.getByName("FF01:0:0:0:0:0:0:FC");
        MembershipKey key = channel.join(group, networkInterface);

        System.out.println("Joined Multicast Group: " + key);
        System.out.println("Waiting for a  message...");
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.receive(buffer);
//        buffer.flip();
//        
//        int limits = buffer.limit();
//        byte bytes[] = new byte[limits];
//        buffer.get(bytes, 0, limits);

        buffer.flip();
        System.out.print("Received: [");
        StringBuilder message = new StringBuilder();
        while (buffer.hasRemaining()) {
            message.append((char) buffer.get());
        }
        System.out.println(message + "]");
        
        key.drop();
    }
}
