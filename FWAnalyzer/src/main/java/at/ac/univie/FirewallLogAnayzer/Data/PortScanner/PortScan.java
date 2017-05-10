package at.ac.univie.FirewallLogAnayzer.Data.PortScanner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PortScan {

    private String host;
    private int portrange;

    public PortScan(String host, int portrange){
        this.host = host;
        this.portrange = portrange;
    }

    public ArrayList getopenPorts(){

        ArrayList ports = new ArrayList();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println("scanning " + portrange + " ports..");

        for (int i = 0; i < portrange; i++) {
            PSthread curr = new PSthread(host, i);
            Future<Integer> f = executor.submit(curr);

            try {
                if (f.get() != null){
                    ports.add(f.get());
                    // get blockiert, bis das Ergebnis zur Verfügung steht!
                }
            } catch(InterruptedException | ExecutionException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }

        }
        executor.shutdown();
        return ports;
    }
}
