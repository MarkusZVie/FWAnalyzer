package at.ac.univie.FirewallLogAnayzer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import at.ac.univie.FirewallLogAnayzer.Data.DoSData;
import at.ac.univie.FirewallLogAnayzer.Data.DoSDataList;
import at.ac.univie.FirewallLogAnayzer.Data.LogTypeSingelton;
import at.ac.univie.FirewallLogAnayzer.Exceptions.LogIdNotFoundException;
import at.ac.univie.FirewallLogAnayzer.Input.IInputHandler;
import at.ac.univie.FirewallLogAnayzer.Input.InputHandler;
import at.ac.univie.FirewallLogAnayzer.Processing.AnalyzerDos;
import at.ac.univie.FirewallLogAnayzer.Processing.IProcessingAnalyse;
import at.ac.univie.FirewallLogAnayzer.Processing.StaticFunctions;
import at.ac.univie.FirewallLogAnayzer.Processing.TemporairProcessing;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

       IInputHandler inputHandler = new InputHandler();
        // /Users/josefweber/Desktop/SyslogCatchAll-2017-03-14.txt
        // C:\Users\Lezard\Desktop\SyslogCatchAll-2017-03-14.txt
        try {
		inputHandler.loadeFirewallLog("/Users/josefweber/Desktop/SyslogCatchAll-2017-03-14.txt", LogTypeSingelton.getInstance().getSupportedLogTypeList().get(0));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (LogIdNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	System.out.println("beep");
	//test method
        //TemporairProcessing.doSomething();
        //TemporairProcessing.testPortScan();


        // DOS
        IProcessingAnalyse da = new AnalyzerDos();
        DoSDataList ddl = da.analyseDos("icmp", 60);

        // Sort mpm
        //da.sortMessagePerMinute(ddl, "asc");

        // Sort country
        //HashMap<String, ArrayList<DoSData>> countrymap = da.messagesOfCountry(ddl);
        //HashMap<String, Integer> countryCount = da.sumMessagesPerCountry(countrymap, "asc");

        // Get single DosData
        /*
        DoSData ddsingle = da.getSingleIP(ddl, "187.182.134.133");
        if (ddsingle == null){
            System.out.println("null");
        } else {
            System.out.println("Example value: " + ddsingle.getMessages().get(0).getProtocol());
        }
        */

        DoSData td1 = ddl.getDataEdited().get(5);
        System.out.println(td1.getMptList().toString() + " ip=" + td1.getMessages().get(0).getSrcIP());


        ArrayList<DoSData> crits = da.analyzeMpt(ddl,10.0);
        System.out.println(crits.size());

        // Test Message Per Time

        for (int i = 0; i < crits.size(); i++) {

            System.out.println(crits.get(i).getMessages().get(0).getSrcIP());
        }





        /*
        DoSData ddTest = ddl.getDataEdited().get(8);
        ddTest.calcMsgPerSlot(20);
        System.out.println(ddTest.getMessages().size());
        System.out.println("----");
        */


    }


}
