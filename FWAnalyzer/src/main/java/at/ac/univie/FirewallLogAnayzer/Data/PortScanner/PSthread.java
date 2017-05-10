package at.ac.univie.FirewallLogAnayzer.Data.PortScanner;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

public class PSthread implements Callable<Integer> {

    private int port;
    private String host;

    public PSthread(String host, int port) {
        this.port = port;
        this.host = host;
    }

    @Override
    public Integer call() throws Exception {
        try {
            Socket target = new Socket(host, port);

            if (!target.isClosed()){
                //System.out.println("isClosed");
                System.err.println("Host: " + host + " open Port: " + port);
                return port;
            }
            target.close();
        } catch (UnknownHostException ex) {
            System.out.println("Unkown Host " + host);
        } catch (IOException ex) {
        }
        return null;
    }
}
