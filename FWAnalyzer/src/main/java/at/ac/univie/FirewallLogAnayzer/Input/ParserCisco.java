package at.ac.univie.FirewallLogAnayzer.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.ac.univie.FirewallLogAnayzer.Data.CiscoAsaCodeSingelton;
import at.ac.univie.FirewallLogAnayzer.Exceptions.StringNotFoundException;
import at.ac.univie.FirewallLogAnayzer.Processing.StaticFunctions;

public class ParserCisco extends Parser{

	public void parse(String logFileContent) {
		//read LogFile row by row
		BufferedReader reader = new BufferedReader(new StringReader(logFileContent));
		try {
			String line = reader.readLine();
		    while (line != null) {
		    	analyseRow(line);
		        line = reader.readLine();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void analyseRow(String line){
		Date dateTime = searchForDateTime(line);
		String rowType ="";
		String prorityCodeAndAsaCode="";
		try {
			rowType = StaticFunctions.searchTheNStringWithPreAndPostfix(line,1,"\tLocal4.","\t");
			prorityCodeAndAsaCode = StaticFunctions.searchTheNStringWithPreAndPostfix(line,1,"\t%ASA-",": ");
		} catch (StringNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String destIp = searchTheNIpInRow(line,1);
		
		String prorityCode = prorityCodeAndAsaCode.substring(0, 1);
		String asaCodeString = prorityCodeAndAsaCode.substring(2);
		int asaCode = Integer.parseInt(asaCodeString);
		ArrayList<String> asaCodeDescription = CiscoAsaCodeSingelton.getInstance().getBackgroundInfoAsaCode(asaCode);
		ArrayList<String> asaSplitDescLine = ArraysplitLineBySimmilarities (line,asaCodeDescription,asaCode);
		
		String tempSrcIP = findeSrcIp(asaSplitDescLine);
		String srcIP =null;
		if(tempSrcIP != null){
			srcIP = searchTheNIpInRow(tempSrcIP,1);
		}
		
		System.out.println(asaCodeDescription.get(0));
		System.out.println(line);
		for(int i=0; i<asaSplitDescLine.size();i++){
			System.out.println(asaSplitDescLine.get(i++)+ " ");
			System.out.println("--->"+asaSplitDescLine.get(i));
			
		}
		
		String protocol = findProtocol(asaSplitDescLine);
		if(protocol == null){
			/*
			System.out.println("------------");
			System.out.println(asaCodeDescription.get(0));
			System.out.println(line);
			*/
		}
		
		/*
		// See if there is really no IP
		if(srcIP==null){
			System.out.println("----------------------");
			System.out.println(asaCodeDescription.get(0));
			System.out.println(line);
			System.out.println("......................");
			for(int i=0; i<asaSplitDescLine.size();i++){
				System.out.println(asaSplitDescLine.get(i++)+ " ");
				System.out.println("--->"+asaSplitDescLine.get(i));
				
			}
			System.out.println("llllll " + srcIP);
		}
		*/
		
		
	}

	
	

	


	private String findProtocol(ArrayList<String> asaSplitDescLine) {
		for(int i=0; i<asaSplitDescLine.size();i=i+2){
			String description = asaSplitDescLine.get(i);
			if(description.contains("protocol")||
					description.contains("{TCP|UDP}")){
				return asaSplitDescLine.get(i+1).trim();
				
			}
		}
		return null;
	}

	private String findeSrcIp(ArrayList<String> asaSplitDescLine) {
		for(int i=0; i<asaSplitDescLine.size();i=i+2){
			String description = asaSplitDescLine.get(i);
			if(description.trim().equals("undef")){
				String line= asaSplitDescLine.get(i+1);
				if(line.contains("IP =")){
					int beginIndex= line.indexOf("IP =")+"IP =".length();
					int endIndex = line.indexOf(',', beginIndex);
					if(endIndex<0){
						return line.substring(beginIndex);
					}else{
						return line.substring(beginIndex, endIndex);
					}
				}
			}else{
				if(description.trim().contains("source_IP")||
						description.trim().contains("source_address")||
						description.trim().contains("IP_address")||
						description.trim().contains("peer_address")||
						description.trim().contains("peerIP")){
					return asaSplitDescLine.get(i+1);
				}
			}
		}
		return null;
	}

	private ArrayList<String> ArraysplitLineBySimmilarities(String line, ArrayList<String> asaCodeDescription,int asaCode) {
		
		String trimedDescription = " "+ asaCodeDescription.get(0).substring(asaCodeDescription.get(0).indexOf(asaCode+": ")+(asaCode+": ").length())+ " ";
		trimedDescription= trimedDescription.replaceAll("\\s+", " "); //Delete Double Spaces
		String trimedLine = " "+ line.substring(line.indexOf("-"+asaCode+": ")+("-"+asaCode+": ").length())+ " ";
		
		
		ArrayList<String> splittedLine = new ArrayList<>();
		ArrayList<String> foundWords = new ArrayList<>(); //when some unice words are double
		
		boolean checker=true;
		int beginCoubleIndex=0; //where starts to search the next double
		boolean isForstFoundWord = true;
		while(checker){		
			String[] oWord1 = findTheNextValidWordGroupThatAppiersInLine(trimedDescription, trimedLine, beginCoubleIndex,0);
			
		
			
			while(isAreadyInList(foundWords, oWord1[0])){
				beginCoubleIndex=Integer.parseInt(oWord1[1])+oWord1[0].length();
				oWord1 = findTheNextValidWordGroupThatAppiersInLine(trimedDescription, trimedLine, beginCoubleIndex,0);
			}
			if(!isAreadyInList(foundWords, oWord1[0])){
				foundWords.add(oWord1[0]);
			}else{
				oWord1=null;
			}
			
			
			
			if(oWord1!=null){
				
				String[] oWord2 = findTheNextValidWordGroupThatAppiersInLine(trimedDescription, trimedLine, Integer.parseInt(oWord1[1])+oWord1[0].length(),Integer.parseInt(oWord1[2]));
				if(Integer.parseInt(oWord1[1])==0){
					if(Integer.parseInt(oWord1[2])>0){
						splittedLine.add("undef");
						splittedLine.add(trimedLine.substring(0, Integer.parseInt(oWord1[2])));
					}
				}
				
				if(oWord2!=null){	
										
					if(oWord1[0].equals(oWord2[0])){//in case of double word
						 oWord2 = findTheNextValidWordGroupThatAppiersInLine(trimedDescription, trimedLine, Integer.parseInt(oWord2[1])+oWord1[0].length(),Integer.parseInt(oWord1[2]));
					}
					//descripion artifact
					int beginIndex = Integer.parseInt(oWord1[1])+oWord1[0].length()+1;
					int endIndex = Integer.parseInt(oWord2[1]);
					
					//if firstWord check if there is something before the word
					if(isForstFoundWord && Integer.parseInt(oWord1[1])!=0){
						splittedLine.add(trimedDescription.substring(0, Integer.parseInt(oWord1[1])).trim());//add pre description				
					}
					
					
					
					//save the Indexes for later add
					int saveBeginIndex = beginIndex;
					int saveEndIndex = endIndex;
					
					
					
					//update Searchindex for next double
					beginCoubleIndex = endIndex;
					
					
					//line artifact
					beginIndex = Integer.parseInt(oWord1[2])+oWord1[0].length()+1;
					endIndex = Integer.parseInt(oWord2[2]);
				
					if(isForstFoundWord && Integer.parseInt(oWord1[1])!=0){
						splittedLine.add(trimedLine.substring(0, Integer.parseInt(oWord1[2])).trim());//add pre description				
					}
					isForstFoundWord = false;
					
					
					//add orginal lines
					splittedLine.add(trimedDescription.substring(saveBeginIndex, saveEndIndex).trim()); //decription add, because eventual pre add
					splittedLine.add(trimedLine.substring(beginIndex, endIndex).trim());				//line artifact add
					
					
					
				}else{
										
					//last part of the line
					int beginIndex = Integer.parseInt(oWord1[1])+oWord1[0].length()+1;
					splittedLine.add(trimedDescription.substring(beginIndex).trim());
					
					
					//line artifact
					beginIndex = Integer.parseInt(oWord1[2])+oWord1[0].length()+1;
					splittedLine.add(trimedLine.substring(beginIndex).trim());
					checker = false;
	
				}
			}else {
				System.err.println("Does Not Happen");
			}
		}
		//System.out.println(splittedLine.toString());
		
		//System.out.println(oWord2[0]);
		
		
		
		

		

		return splittedLine;
	}
	
	private boolean isAreadyInList(ArrayList<String> foundWords, String word) {
		for(String s: foundWords){
			if(s.equals(word)){
				return true;
			}
		}
		return false;
	}

	private String[] findTheNextValidWordGroupThatAppiersInLine (String trimedDescription,String trimedLine, int beginSearchIndexDesc, int beginSearchIndexLine){
		int count=1;
		boolean checker = true;
		String[] saveLastValid = null;
		boolean nextWord= false;
		if(beginSearchIndexLine<0){
			beginSearchIndexLine=0;
		}
		while(checker){
			String[] nWord = findNextWord(trimedDescription,beginSearchIndexDesc,count++);
			if(nWord!=null){
				
				//in generell word found
				if(trimedLine.indexOf(" "+nWord[0].trim()+" ")<beginSearchIndexLine){
					//this word (word combi) appiers not in the line
					count=1;
					//take the next word
					beginSearchIndexDesc=Integer.parseInt(nWord[1])+nWord[0].length();
					if(saveLastValid!=null){
						checker=false;
					}
				}else{
					//is valid and appiers in line
					nWord[2]=trimedLine.indexOf(" "+nWord[0].trim()+" ")+"";
					saveLastValid = nWord;
				}
			}else{
				//word absolut not found
				checker = false;
			}
		}
		if(saveLastValid!=null){
			return saveLastValid;
		}
		return null;
	}
	
	private String[] findNextWord(String s, int searchBeginIndex,int n) {
		//Delivers an String[2], String[0]= FoundWord, String[1]=inizialIndex, over more than one word
		String[] word = new String[3];
		int markerPosition = searchBeginIndex;
		for(int i=1; i<n; i++){
			if(findNextWord(s, markerPosition)!=null){
				markerPosition = Integer.parseInt(findNextWord(s, markerPosition)[1]);
				markerPosition++;
			}
		}
		if(s.indexOf(' ', searchBeginIndex)<0 || s.indexOf(' ',s.indexOf(' ', markerPosition)+1) <0)
			return null;
		word[0]=s.substring(s.indexOf(' ', searchBeginIndex)+1, s.indexOf(' ',s.indexOf(' ', markerPosition)+1));
		word[1]=s.indexOf(' ', searchBeginIndex)+"";
		return word;
	}
	
	private String[] findNextWord(String s, int searchBeginIndex) {
		//Delivers an String[2], String[0]= FoundWord, String[1]=inizialIndex
		if(s.indexOf(' ', searchBeginIndex)<0 || s.indexOf(' ',s.indexOf(' ', searchBeginIndex)+1) <0)
			return null;
		String[] word = new String[2];
		word[0]=s.substring(s.indexOf(' ', searchBeginIndex)+1, s.indexOf(' ',s.indexOf(' ', searchBeginIndex)+1));
		word[1]=s.indexOf(' ', searchBeginIndex)+"";
		return word;
	}

	private Date searchForDateTime(String line) {
		String datePattern = "[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime = null;
		try {
			dateTime= searchDateTime(line,datePattern,sdf);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateTime;
		
	}

	
}
