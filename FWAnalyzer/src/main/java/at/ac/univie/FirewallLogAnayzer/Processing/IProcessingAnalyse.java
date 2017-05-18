package at.ac.univie.FirewallLogAnayzer.Processing;

import at.ac.univie.FirewallLogAnayzer.Data.DoSData;
import at.ac.univie.FirewallLogAnayzer.Data.DoSDataList;

import java.util.ArrayList;
import java.util.HashMap;

public interface IProcessingAnalyse {

	// TCP - SYN -flood
	// icmp - flood
	// layer 7 - Application - flood
	public DoSDataList analyseDos(String dosProtocolType, int timeSlot);

	public void analyseDDoS();

	public ArrayList<DoSData> sortMessagePerMinute(DoSDataList processedData, String ascdesc);

	public HashMap<String, ArrayList<DoSData>> messagesOfCountry(DoSDataList processedData);

	public HashMap<String, Integer> sumMessagesPerCountry(HashMap<String, ArrayList<DoSData>> countrymap, String ascdesc);

	public DoSData getSingleIP(DoSDataList processedData, String ip);

	public ArrayList<DoSData> analyzeMpt(DoSDataList processedData, Double criticalValue);

}
