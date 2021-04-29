package packt;

import java.io.IOException;
import java.util.Scanner;
import org.kth.dks.JDHT;

public class JDHTExample {

    public static void main(String[] arg) {
        try {
            JDHT DHTExample = new JDHT();
            DHTExample.put("Java SE API", 
                    "http://docs.oracle.com/javase/8/docs/api/");
            System.out.println(((JDHT) DHTExample).getReference());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press Enter to terminate application: ");
            scanner.next();
            DHTExample.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
}
