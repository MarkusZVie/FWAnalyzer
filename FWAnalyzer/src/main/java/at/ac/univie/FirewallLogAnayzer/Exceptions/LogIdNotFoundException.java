package at.ac.univie.FirewallLogAnayzer.Exceptions;

public class LogIdNotFoundException extends Exception {
	public LogIdNotFoundException() { super(); }
	public LogIdNotFoundException(String message) { super(message); }
	public LogIdNotFoundException(String message, Throwable cause) { super(message, cause); }
	public LogIdNotFoundException(Throwable cause) { super(cause); }
}
