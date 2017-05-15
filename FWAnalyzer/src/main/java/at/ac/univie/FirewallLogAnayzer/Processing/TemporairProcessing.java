package at.ac.univie.FirewallLogAnayzer.Processing;

import at.ac.univie.FirewallLogAnayzer.Data.IpLocation;
import at.ac.univie.FirewallLogAnayzer.Data.LogRow;
import at.ac.univie.FirewallLogAnayzer.Data.LogRows;
import at.ac.univie.FirewallLogAnayzer.Data.StandardDeviation;

import java.util.ArrayList;

public class TemporairProcessing {
	public static void doSomething(){
		for(LogRow lr:LogRows.getInstance().getLogRows()){
			System.out.println(lr.toString());
		}
	}

	public static void testPortScan(){
		StaticFunctions.doPortScan("192.168.88.44",9000);
	}

	public static void testStandardAbweichung(){
		ArrayList al = new ArrayList<Integer>();
		al.add(17);al.add(15);al.add(23);al.add(7);al.add(9);al.add(13);
		//al.add(5);al.add(5);al.add(8);al.add(12);al.add(15);al.add(18);

		StandardDeviation sData = new StandardDeviation(al);
	}

}
