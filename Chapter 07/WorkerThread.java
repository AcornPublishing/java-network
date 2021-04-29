package packt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import static java.lang.System.out;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerThread implements Runnable {

    private static final ConcurrentHashMap<String, Float> map;
    private final Socket clientSocket;

    static {
        map = new ConcurrentHashMap<>();
        map.put("Axle", 238.50f);
        map.put("Gear", 45.55f);
        map.put("Wheel", 86.30f);
        map.put("Rotor", 8.50f);
    }

    public WorkerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Worker Thread Started");
        try (BufferedReader bis = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
                PrintStream out = new PrintStream(
                        clientSocket.getOutputStream())) {

            String partName = bis.readLine();
            float price = map.get(partName); 

            // Used to support WorkerCallable approach
//            float price = 0.0f;
//            try {
//                price = new WorkerCallable(partName).call();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }

            // Used to support Callable approach
//            float price = 0.0f;
//            ThreadPoolExecutor executor
//                    = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//            Future<Float> future1 = executor.submit(new Callable<Float>() {
//                @Override
//                public Float call() {
//                    // Compute first part
//                    return 1.0f;
//                }
//            });
//            Future<Float> future2 = executor.submit(new Callable<Float>() {
//                @Override
//                public Float call() {
//                    // Compute second part
//                    return 2.0f;
//                }
//            });
//
//            try {
//                Float firstPart = future1.get();
//                Float secondPart = future2.get();
//                price = firstPart + secondPart;
//            } catch (InterruptedException | ExecutionException ex) {
//                ex.printStackTrace();
//            }

            out.println(price);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            System.out.println("Request for " + partName
                    + " and returned a price of "
                    + nf.format(price));

//            while(true) {
//                String partName = bis.readLine();
//                if("quit".equalsIgnoreCase(partName)) {
//                    break;
//                }
//                float price = map.get(partName);
//                out.println(price);
//                NumberFormat nf = NumberFormat.getCurrencyInstance();
//                System.out.println("Request for " + partName
//                        + " and returned a price of "
//                        + nf.format(price));
//            } 
            clientSocket.close();
            System.out.println("Client Connection Terminated");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println(
                "Worker Thread Terminated");
    }
}
