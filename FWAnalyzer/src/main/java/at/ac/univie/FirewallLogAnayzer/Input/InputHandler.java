package at.ac.univie.FirewallLogAnayzer.Input;

import java.io.FileNotFoundException;

import at.ac.univie.FirewallLogAnayzer.Data.LogType;
import at.ac.univie.FirewallLogAnayzer.Exceptions.LogIdNotFoundException;
import at.ac.univie.FirewallLogAnayzer.Processing.StaticFunctions;


public class InputHandler implements IInputHandler{

	
	
	
	



	public void loadeFirewallLog(String logpath, LogType logType) throws FileNotFoundException, LogIdNotFoundException{
		
		StaticFunctions.cleanFile("Files\\errorLog.txt");
		
		String[] logFileContentAndNumberOfRows = StaticFunctions.readeFile(logpath);
		String logFileContent = logFileContentAndNumberOfRows[0];
		int numberOfRows = Integer.parseInt(logFileContentAndNumberOfRows[1]);
		
		switch (logType.getId()) {
		case 0:
			ParserCisco parser = new ParserCisco(numberOfRows);
			ReportNumberOfLinesParsed reporter = new ReportNumberOfLinesParsed(parser);
			reporter.start();
			parser.parse(logFileContent);
			reporter.stop();
			break;

		default:
			throw new LogIdNotFoundException();
		}
		
	}

	

}
