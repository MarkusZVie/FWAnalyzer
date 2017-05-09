package at.ac.univie.FirewallLogAnayzer.Input;

import java.io.File;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Parser {
	
	 

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

	

}
