package at.ac.univie.FirewallLogAnayzer.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import at.ac.univie.FirewallLogAnayzer.Exceptions.StringNotFoundException;
import at.ac.univie.FirewallLogAnayzer.Processing.StaticFunctions;


public class CiscoAsaCodeSingelton {
	private String ciscoAsaContextPage;
	private HashMap<Integer,ArrayList<String>> savedAsaCodeDescriptions = new HashMap<Integer,ArrayList<String>>(); 
	private static final String ciscoURL = "http://www.cisco.com/en/US/docs/security/asa/asa80/system/message/logmsgs.html";
	private static CiscoAsaCodeSingelton instance =null;
	private CiscoAsaCodeSingelton(){
		ciscoAsaContextPage = "";
		
		try { //try to find it Online, if an error appiers loade file
			ciscoAsaContextPage = loadeCiscoPageOnline();
		} catch (IOException e) {
			try {
				//   Files\ciscoAsaCodeBackgroundInfos.htm
				//   /Users/josefweber/Documents/Intelliji\ Projekte/Bakk_Analyzer/FWAnalyzer/Files/ciscoAsaCodeBackgroundInfos.htm
				ciscoAsaContextPage = StaticFunctions.readeFile("Files\\ciscoAsaCodeBackgroundInfos.htm")[0];
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
	}
	
	
	private String loadeCiscoPageOnline() throws MalformedURLException, IOException {
		Scanner scanner = new Scanner(new URL(ciscoURL).openStream());
		StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()){
        	sb.append(scanner.nextLine());
        }
        scanner.close();
		return sb.toString();
	}
	
	
	public static CiscoAsaCodeSingelton getInstance(){
		if(instance == null){
			instance = new CiscoAsaCodeSingelton();
		}
		return instance;
	}


	public ArrayList<String> getBackgroundInfoAsaCode(int asaCode) {
		if(savedAsaCodeDescriptions.containsKey(asaCode)){
			return savedAsaCodeDescriptions.get(asaCode);
		}
		else{
			
			String rawDescriptionInformation="";
			ArrayList<String> asaCodeDescription;
			try {
				rawDescriptionInformation = StaticFunctions.searchTheNStringWithPreAndPostfix(ciscoAsaContextPage, 1, "<h3 class=\"p_H_Head2\">"+asaCode, "<h3 class=\"p_H_Head2\">");
				asaCodeDescription = cutOutOnlyTextBetweenTags(rawDescriptionInformation);
			} catch (StringNotFoundException e) {
				asaCodeDescription = new ArrayList<>();
				asaCodeDescription.add("not Found");
				asaCodeDescription.add("not Found");
				asaCodeDescription.add("not Found");
			}
			if(CiscoAsaCodeExceptions.getInstance().isAsaCodeAnException(asaCode)){
				savedAsaCodeDescriptions.put(asaCode, CiscoAsaCodeExceptions.getInstance().getExceptionalDescriptionText(asaCode,asaCodeDescription));
			}else{
				savedAsaCodeDescriptions.put(asaCode, asaCodeDescription);
			}
			
			
			
			return asaCodeDescription;
		}
	}


	private ArrayList<String> cutOutOnlyTextBetweenTags(String rawDescriptionInformation) {
		ArrayList<String> asaCodeDescription = new ArrayList<>();
		String asaCodeDescriptionLine ="";
		int count = 0;
		while(rawDescriptionInformation.indexOf('>', count)!=rawDescriptionInformation.lastIndexOf('>')){
			int o = rawDescriptionInformation.indexOf('>', count);
			if(rawDescriptionInformation.charAt(o+1)!='<'){
				asaCodeDescriptionLine=asaCodeDescriptionLine+" "+(rawDescriptionInformation.substring(o+1, rawDescriptionInformation.indexOf('<', o+1)));
				
			}
			count = o+1;
		}
		
		
		//There are 3 Typse of logs: PIX,ASA and PIX/ASA and we need to find that as the first marker
		int firstMarker=0;
		if(asaCodeDescriptionLine.indexOf("%PIX|ASA-")>0){
			firstMarker = asaCodeDescriptionLine.indexOf("%PIX|ASA-");
		}else{
			if(asaCodeDescriptionLine.indexOf("%ASA-")>0){
				firstMarker = asaCodeDescriptionLine.indexOf("%ASA-");
			}
			if(asaCodeDescriptionLine.indexOf("%PIX-")>0){
				firstMarker = asaCodeDescriptionLine.indexOf("%PIX-");
			}
		}
		if(firstMarker<=0){
			//It should never come to this code
			System.err.println("PIX,ASA and PIX/ASA Not FOunt");
		}
		String errorMessage="";
		if(asaCodeDescriptionLine.indexOf("The following is an example")>=0){
			errorMessage = asaCodeDescriptionLine.substring(firstMarker, asaCodeDescriptionLine.indexOf("The following is an example"));
		}else{
			errorMessage = asaCodeDescriptionLine.substring(firstMarker, asaCodeDescriptionLine.indexOf("Explanation"));
		}
		
		
		String explanation = asaCodeDescriptionLine.substring(asaCodeDescriptionLine.indexOf("Explanation&#160;&#160;&#160;")+29, asaCodeDescriptionLine.indexOf("Recommended Action"));
		String recommendedAction = asaCodeDescriptionLine.substring(asaCodeDescriptionLine.indexOf("Recommended Action&#160;&#160;&#160;")+37,asaCodeDescriptionLine.length());
		asaCodeDescription.add(errorMessage.trim());
		asaCodeDescription.add(explanation.trim());
		asaCodeDescription.add(recommendedAction.trim());
		
		
		
		return asaCodeDescription;
	}


	public HashMap<Integer, ArrayList<String>> getSavedAsaCodeDescriptions() {
		return savedAsaCodeDescriptions;
	}
	
	public String printSavedAsaCodeDescriptions(){
		StringBuilder sb = new StringBuilder();
		for (HashMap.Entry<Integer, ArrayList<String>> entry : savedAsaCodeDescriptions.entrySet()){
			sb.append(entry.getKey());
			sb.append(System.lineSeparator());
			
			sb.append("Error Message:       " + entry.getValue().get(0));
			sb.append(System.lineSeparator());
			sb.append("Explanation:         " + entry.getValue().get(1));
			sb.append(System.lineSeparator());
			sb.append("Recommended Action:  " + entry.getValue().get(2));
			sb.append(System.lineSeparator());
        }
		
		return sb.toString();
	}
	
	
}
