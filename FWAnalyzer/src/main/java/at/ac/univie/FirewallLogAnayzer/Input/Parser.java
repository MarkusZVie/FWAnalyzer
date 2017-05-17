package at.ac.univie.FirewallLogAnayzer.Input;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Parser {
	
	protected long numberOfRowsReaded;
	protected long numberToRead;
	

	public synchronized long getNumberToRead() {
		return numberToRead;
	}

	public synchronized long getNumberOfRowsReaded() {
		return numberOfRowsReaded;
	}

	public Parser() {
		numberOfRowsReaded = 0;
	}

	protected Date searchDateTime(String line, String datePattern, SimpleDateFormat sdf) throws ParseException {
		Pattern p = Pattern.compile(datePattern);
		Matcher m = p.matcher(line);
		if (m.find()) {
			return sdf.parse(m.group(0));
		}
		
		return null;
	}
	
	protected String searchTheNIpInRow(String line,int n){
		String ipPattern = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
		Pattern p = Pattern.compile(ipPattern);
		Matcher m = p.matcher(line);
		int count = 1;
		while (m.find()) {
			if(count++==n){
				return m.group(0);
			}
		}
		return null;
	}
	
	protected void writeErrorLogInErrorLogTxt(String log){
		//http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
		
        
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Files\\errorLog.txt", true)))) {
		    out.println(log);
		}catch (IOException e) {
		    System.err.println(e);
		}
           
       
           
           
     
	}

	

}
