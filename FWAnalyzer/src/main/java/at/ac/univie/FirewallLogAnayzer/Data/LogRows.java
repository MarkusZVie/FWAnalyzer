package at.ac.univie.FirewallLogAnayzer.Data;

import java.util.ArrayList;

public class LogRows {
	private ArrayList<LogRow> logRows;
	private static LogRows instance =null;
	private LogRows(){
		logRows = new ArrayList<>();
	}
	public static LogRows getInstance(){
		if(instance == null){
			instance = new LogRows();
		}
		return instance;
	}
	public ArrayList<LogRow> getLogRows() {
		return logRows;
	}
	
	public void addLogRow(LogRow logRow){
		logRows.add(logRow);
	}
	
}
