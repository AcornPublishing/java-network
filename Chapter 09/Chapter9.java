package packt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channel;

public class Chapter9 {

    public static void main(String[] args) {
        endianExample();
    }

    private static void endianExample() {
        System.out.println(ByteOrder.nativeOrder());

        // Allocate a big endian byte buffer
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        System.out.println(buffer.order());
        buffer.order(ByteOrder.LITTLE_ENDIAN);        
        System.out.println(buffer.order());

        
        buffer.order(ByteOrder.LITTLE_ENDIAN);        
        ByteBuffer slice = buffer.slice();      
        System.out.println(buffer.order());
        
        int number = 0x01234567;
        
    }
}
