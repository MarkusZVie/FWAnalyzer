package at.ac.univie.FirewallLogAnayzer.Input;

import java.io.FileNotFoundException;

import at.ac.univie.FirewallLogAnayzer.Data.LogType;
import at.ac.univie.FirewallLogAnayzer.Exceptions.LogIdNotFoundException;
import at.ac.univie.FirewallLogAnayzer.Processing.StaticFunctions;


public class InputHandler implements IInputHandler{

	public void loadeFirewallLog(String logpath, LogType logType) throws FileNotFoundException, LogIdNotFoundException{
		String logFileContent = StaticFunctions.readeFile(logpath);
		switch (logType.getId()) {
		case 0:
			ParserCisco parser = new ParserCisco();
			parser.parse(logFileContent);
			break;

		default:
			throw new LogIdNotFoundException();
		}
		
	}

	

}
