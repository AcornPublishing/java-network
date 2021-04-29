package packt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAHashingExample {

    public static void main(String[] args) throws Exception {
        SHAHashingExample example = new SHAHashingExample();
        String message = "This is a simple text message";
        byte hashValue[] = example.getHashValue(message);
        example.displayHashValue(hashValue);
        // Encrypt and send
        // On the other side show how it is verified
    }

    public void displayHashValue(byte hashValue[]) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hashValue.length; i++) {
            // Do I have a different version elsewhere?
            builder.append(Integer.toString((hashValue[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println("Hash Value: " + builder.toString());
    }

    public byte[] getHashValue(String message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(message.getBytes());
//            System.out.println("Digest length: " + messageDigest.getDigestLength() * 8);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
