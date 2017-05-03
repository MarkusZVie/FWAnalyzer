package at.ac.univie.FirewallLogAnayzer.Input;

import java.io.FileNotFoundException;

import at.ac.univie.FirewallLogAnayzer.Data.LogType;
import at.ac.univie.FirewallLogAnayzer.Exceptions.LogIdNotFoundException;

public interface IInputHandler {
	public void loadeFirewallLog(String logpath,LogType logtype) throws FileNotFoundException, LogIdNotFoundException;
}
