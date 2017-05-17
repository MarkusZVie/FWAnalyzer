package at.ac.univie.FirewallLogAnayzer.Input;

public class ReportNumberOfLinesParsed extends Thread{

	private ParserCisco parser;
	
	public ReportNumberOfLinesParsed(ParserCisco parser) {
		this.parser = parser;
	}

	@Override
	public void run() {
		while(true){
			System.out.println(parser.getNumberOfRowsReaded());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
