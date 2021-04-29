package packt;

import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class UDPEchoServer {

    public static void main(String[] args) {
        int port = 9000;
        System.out.println("UDP Echo Server Started");
        try (DatagramChannel channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();){
            
            SocketAddress address = new InetSocketAddress(port);
            socket.bind(address);
            ByteBuffer buffer = ByteBuffer.allocateDirect(65507);
            while (true) {
                SocketAddress client = channel.receive(buffer);
                buffer.flip();

                // Mark and then reset so that it will send properly
                buffer.mark();
                System.out.print("Received: [");
                StringBuilder message = new StringBuilder();
                while (buffer.hasRemaining()) {
                    message.append((char) buffer.get());
                }
                System.out.println(message + "]");
                buffer.reset();

                channel.send(buffer, client);
                System.out.println("Sent: [" + message + "]");
                buffer.clear();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("UDP Echo Server Terminated");
    }
}
