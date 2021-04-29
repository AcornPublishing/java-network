package packt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class Chapter3 {

    public static void main(String[] args) {
//        creatingBuffers();
//        getWebPage();
//        bulkTransferExample();
    }

    private static void creatingBuffers() {
        String contents = "Book";
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put(contents.getBytes());
        // Views
        ByteBuffer duplicateBuffer = buffer.duplicate();
        duplicateBuffer.put(0,(byte)0x4c); // 'L'
        System.out.println("buffer: " + buffer.get(0));
        System.out.println("duplicateBuffer: " + duplicateBuffer.get(0));
        System.out.println();

        // Creating a read-only buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println("Read-only: " + readOnlyBuffer.isReadOnly());
    }

    private static void displayBuffer(IntBuffer buffer) {
        int arr[] = new int[buffer.position()];
        buffer.rewind();
        buffer.get(arr);
        for (int element : arr) {
            System.out.print(element + " ");
        }
        System.out.println();
        for (int i = 0; i < buffer.position(); i++) {
            System.out.print(buffer.get(i) + " ");
        }
        System.out.println();
    }

    private static void bulkTransferExample() {
        int[] arr = {12, 51, 79, 54};
        IntBuffer buffer = IntBuffer.allocate(6);
        System.out.println(buffer);

        buffer.put(arr);
        System.out.println(buffer);
        displayBuffer(buffer);

        int length = buffer.remaining();
        buffer.put(arr, 0, length);
        System.out.println(buffer);
        displayBuffer(buffer);
    }
}
