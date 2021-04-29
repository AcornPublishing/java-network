package packt;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class ServerSocketChannelTimeServer {

    public static void main(String[] args) {
        System.out.println("Time Server started");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(5000));
            while (true) {
                System.out.println("Waiting for request ...");
                SocketChannel socketChannel
                        = serverSocketChannel.accept();

                if (socketChannel != null) {
                    String dateAndTimeMessage = "Date: " + 
                            new Date(System.currentTimeMillis());

                    ByteBuffer buf = ByteBuffer.allocate(64);
                    // If buffer is not large enough: BufferOverflowException
                    buf.put(dateAndTimeMessage.getBytes());
                    buf.flip();
                    while (buf.hasRemaining()) {
                        socketChannel.write(buf);
                    }
                    System.out.println("Sent: " + dateAndTimeMessage);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
