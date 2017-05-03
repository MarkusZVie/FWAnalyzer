package at.ac.univie.FirewallLogAnayzer.Processing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import at.ac.univie.FirewallLogAnayzer.Exceptions.StringNotFoundException;

public class StaticFunctions {
	public static String readeFile(String filePath) throws FileNotFoundException {
		//http://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
		
		String logFileContent = "";
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    //add every line to logFileContent
		    logFileContent = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logFileContent;
		
	}
	
public static String searchTheNStringWithPreAndPostfix(String line, int i, String preFix, String postFix) throws StringNotFoundException{
		
		//The N Search is not Developed, only the first is searchable,
		//Every call of the first substring is called with int n of one-> what means the first appierence of that post&prefix
		if(line.indexOf(preFix)<0||line.indexOf(postFix)<0)
			throw new StringNotFoundException();
		
		int beginIndexPreFix =line.indexOf(preFix);
		int lengthOfPreFix = preFix.length();
		int endIndexPrefix = beginIndexPreFix+lengthOfPreFix;
		String lineFromEndPreFix = line.substring(endIndexPrefix);
		int numberOfCuttingChar = line.length()-lineFromEndPreFix.length();
		int beginIndexPostFix = lineFromEndPreFix.indexOf(postFix)+numberOfCuttingChar;
		String result = line.substring(endIndexPrefix, beginIndexPostFix);
		//System.out.print(beginIndexPreFix + " " + endIndexPrefix + " " + beginIndexPostFix + " " + result);
		//System.out.println();
		return result;
		
		
	}
}
