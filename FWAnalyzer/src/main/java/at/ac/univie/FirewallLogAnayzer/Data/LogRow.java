package at.ac.univie.FirewallLogAnayzer.Data;

import java.util.ArrayList;
import java.util.Date;

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
	private String location;
	private String warningNotice;

	

	
	public LogRow(String srcIP, String srcPort, String destIP, String destPort, String protocol, String logLine,
			String descriptionLogLine, String explanation, String recommendedAction, String fwIP, int logLineCode,
			ArrayList<String> additonalInformation, int priority, Date dateTime, String internalExternal,
			String location,String warningNotice) {
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
		this.location = location;
		this.warningNotice = warningNotice;
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

	public String getLocation() {
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
