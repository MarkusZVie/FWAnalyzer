package at.ac.univie.FirewallLogAnayzer.Input;



/**
 * Created by josefweber on 13.04.17.
 */
public interface InputInterface {

    public boolean parsefile();
    public boolean checkFile(String filepath);
    public void analyzeTest();

}
