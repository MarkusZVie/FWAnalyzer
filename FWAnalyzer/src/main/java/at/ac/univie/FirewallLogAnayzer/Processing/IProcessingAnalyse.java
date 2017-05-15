package at.ac.univie.FirewallLogAnayzer.Processing;

public interface IProcessingAnalyse {

	public void analyseDosAny(String protocol);
	public void analyseDosSyn();
	public void analyseDosLayer7();
	public void analyseDosPing();

}
