package at.ac.univie.FirewallLogAnayzer.Data;

import java.util.ArrayList;

public class LogTypeSingelton {
	private ArrayList<LogType> supportedLogTypeList;
	private static LogTypeSingelton instance =null;
	private LogTypeSingelton(){
		//fill in all supported LogTypes here
		supportedLogTypeList = new ArrayList<LogType>();
		LogType logType1 = new LogType("cisco", 0);
		supportedLogTypeList.add(logType1);
	}
	public static LogTypeSingelton getInstance(){
		if(instance == null){
			instance = new LogTypeSingelton();
		}
		return instance;
	}
	public ArrayList<LogType> getSupportedLogTypeList() {
		return supportedLogTypeList;
	}
	
}
