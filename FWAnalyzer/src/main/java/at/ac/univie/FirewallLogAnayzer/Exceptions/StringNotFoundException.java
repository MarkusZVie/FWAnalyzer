package at.ac.univie.FirewallLogAnayzer.Exceptions;

public class StringNotFoundException extends Exception {
	public StringNotFoundException() { super(); }
	public StringNotFoundException(String message) { super(message); }
	public StringNotFoundException(String message, Throwable cause) { super(message, cause); }
	public StringNotFoundException(Throwable cause) { super(cause); }
}
