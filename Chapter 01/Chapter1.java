package packt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chapter1 {

    public static void main(String[] args) {
        inetAddressExamples();
//        nioExamples();
//        socketExamples();
//        urlConnectionExample();
    }

    private static void displayInetAddressInformation(InetAddress address) {
        System.out.println("---InetAddress Information---");
        System.out.println(address);
        System.out.println("CanonicalHostName: " + address.getCanonicalHostName());
        System.out.println("HostAddress: " + address.getHostAddress());
        System.out.println("HostName: " + address.getHostName());
        System.out.println("---------");
    }

    private static void inetAddressExamples() {
        try {
            InetAddress address = InetAddress.getByName("www.packtpub.com");
            System.out.println(address);
            displayInetAddressInformation(address);
            address = InetAddress.getByName("83.166.169.231");
            System.out.println(address);
            address = InetAddress.getLocalHost();
            System.out.println(address);

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    private static void nioExamples() {
        try {
            InetAddress address = InetAddress.getByName("packtpub.com");
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(address, 80));
            while (!socketChannel.finishConnect()) {
                // wait, or do something else...
            }
            System.out.println(socketChannel);
            System.out.println(socketChannel.isConnected());
            System.out.println(socketChannel.isBlocking());

            ByteBuffer buffer;
            buffer = ByteBuffer.allocate(64);
            System.out.println("buffer: " + buffer);
            int bytesRead = socketChannel.read(buffer);
            System.out.println("bytesRead: " + bytesRead);
            if (bytesRead > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println("--" + buffer.get());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void socketExamples() {
        try {
            InetAddress address = InetAddress.getByName("google.com");
            Socket socket = new Socket(address, 80);
            System.out.println(socket.isConnected());
            InputStream input;
            input = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            while (!br.ready()) {
            }
            String line = br.readLine();
            System.out.println("First - " + line);
            while ((line = br.readLine()) != null) {
                System.out.println("Each - " + line);
            }
            System.out.println("Done");
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void urlConnectionExample() {
        try {
            URL url = new URL("http://www.google.com");
//            URLConnection urlConnection = url.openConnection();
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            br.close();

            // Channel
            
            System.out.println("Channel Example");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            ReadableByteChannel channel = Channels.newChannel(inputStream);
            ByteBuffer buffer = ByteBuffer.allocate(64);
            String line = null;
            while (channel.read(buffer) > 0) {
                System.out.println("---" + new String(buffer.array()));
                buffer.clear();
            }
            channel.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
