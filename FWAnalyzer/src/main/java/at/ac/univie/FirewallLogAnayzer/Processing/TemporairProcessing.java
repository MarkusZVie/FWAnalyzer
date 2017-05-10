package at.ac.univie.FirewallLogAnayzer.Processing;

import at.ac.univie.FirewallLogAnayzer.Data.IpLocation;
import at.ac.univie.FirewallLogAnayzer.Data.LogRow;
import at.ac.univie.FirewallLogAnayzer.Data.LogRows;

public class TemporairProcessing {
	public static void doSomething(){
		for(LogRow lr:LogRows.getInstance().getLogRows()){
			System.out.println(lr.toString());
		}
	}

	public static void testPortScan(){
		StaticFunctions.doPortScan("192.168.88.1233",9000);
	}

}
