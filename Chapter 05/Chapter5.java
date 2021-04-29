package packt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Chapter5 {
    
    public static void main(String args[]) {
        shaExamples();
    }

    private static void shaExamples() {
        String message = "String to be hashed";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(message.getBytes());
            byte[] digest = messageDigest.digest();

            StringBuffer buffer = new StringBuffer();
            for (byte element : digest) {
                buffer.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format : " + buffer.toString());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

}
