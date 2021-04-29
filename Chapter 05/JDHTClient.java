package packt;

import java.io.IOException;
import org.kth.dks.JDHT;
import org.kth.dks.dks_exceptions.DKSIdentifierAlreadyTaken;
import org.kth.dks.dks_exceptions.DKSRefNoResponse;
import org.kth.dks.dks_exceptions.DKSTooManyRestartJoins;

public class JDHTClient {
    
    public static void main(String[] args) {
        try {
            JDHT myDHT = new JDHT(5550, "dksref://192.168.1.9:4440" 
                    + "/0/2179157225/0/1952355557247862269");
            String value = (String) myDHT.get("Java SE API");
            System.out.println(value);
            myDHT.close();
        } catch (IOException | DKSTooManyRestartJoins | 
                DKSIdentifierAlreadyTaken | DKSRefNoResponse ex) {
            ex.printStackTrace();
        } 
    }
}
