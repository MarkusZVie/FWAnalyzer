package at.ac.univie.FirewallLogAnayzer.Data;

import java.util.ArrayList;
import java.util.Date;

import at.ac.univie.FirewallLogAnayzer.Processing.StaticFunctions;

public class LogRow {
	private String srcIP;
	private String srcPort;
	private String destIP;
	private String destPort;
	private String protocol;
	private String logLine;
	private String descriptionLogLine;
	private String explanation;
	private String recommendedAction;
	private String fwIP;
	private int logLineCode;
	private ArrayList<String> additonalInformation;
	private int priority;
	private Date dateTime; 
	private String internalExternal;
	private String warningNotice;
	private IpLocation location;

	

	
	public LogRow(String srcIP, String srcPort, String destIP, String destPort, String protocol, String logLine,
			String descriptionLogLine, String explanation, String recommendedAction, String fwIP, int logLineCode,
			ArrayList<String> additonalInformation, int priority, Date dateTime, String internalExternal,
			String warningNotice, IpLocation ipLocation) {
		super();
		this.srcIP = srcIP;
		this.srcPort = srcPort;
		this.destIP = destIP;
		this.destPort = destPort;
		this.protocol = protocol;
		this.logLine = logLine;
		this.descriptionLogLine = descriptionLogLine;
		this.explanation = explanation;
		this.recommendedAction = recommendedAction;
		this.fwIP = fwIP;
		this.logLineCode = logLineCode;
		this.additonalInformation = additonalInformation;
		this.priority = priority;
		this.dateTime = dateTime;
		this.internalExternal = internalExternal;
		this.warningNotice = warningNotice;
		this.location = ipLocation;
	}
	
	public String getToStringHeadline(){
		return "srcIP \t srcPort \t destIP \t destPort \t protocol \t DateTime \t priority \t Type \t CityName \t CountryName";
	}
	
	@Override
	public String toString() {
		String nullWord = "[null]";
		String srcIP = this.srcIP;
		if(srcIP==null){
			srcIP=nullWord;
		}
		String srcPort = this.srcPort;
		if(srcPort==null){
			srcPort=nullWord;
		}
		String destIP = this.destIP;
		if(destIP==null){
			destIP=nullWord;
		}
		String destPort = this.destPort;
		if(destPort==null){
			destPort=nullWord;
		}
		String protocol = this.protocol;
		if(protocol==null){
			protocol=nullWord;
		}
		String dateTime = StaticFunctions.getSimpleDateFormat().format(this.dateTime);
		if(dateTime==null){
			dateTime=nullWord;
		}
		String priority = this.priority+"";
		if(priority==null){
			priority=nullWord;
		}
		String warningNotice = this.warningNotice;
		if(warningNotice==null){
			warningNotice=nullWord;
		}
		String cityName;
		String countryName;
		if(location==null){
			cityName = nullWord;
			countryName = nullWord;
		}else{
			cityName = location.getCityName();
			if(cityName==null){
				cityName=nullWord;
			}
			countryName = location.getCountryName();
			if(countryName==null){
				countryName=nullWord;
			}
		}
		
		
		return srcIP + "\t" + srcPort + "\t" + destIP + "\t" + destPort + "\t" + protocol + "\t" + dateTime + "\t" + priority + "\t" + warningNotice + "\t" + cityName + "\t" + countryName;
	}
	
	
	
	public String getWarningNotice() {
		return warningNotice;
	}
	
	public int getPriority() {
		return priority;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public String getInternalExternal() {
		return internalExternal;
	}

	public IpLocation getLocation() {
		return location;
	}

	public int getLogLineCode() {
		return logLineCode;
	}
	
	public String getSrcIP() {
		return srcIP;
	}

	public String getSrcPort() {
		return srcPort;
	}

	public String getDestIP() {
		return destIP;
	}

	public String getDestPort() {
		return destPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getLogLine() {
		return logLine;
	}

	public String getDescriptionLogLine() {
		return descriptionLogLine;
	}

	public String getExplanation() {
		return explanation;
	}

	public String getRecommendedAction() {
		return recommendedAction;
	}

	public String getFwIP() {
		return fwIP;
	}

	public ArrayList<String> getAdditonalInformation() {
		return additonalInformation;
	}
	
	
	
}
