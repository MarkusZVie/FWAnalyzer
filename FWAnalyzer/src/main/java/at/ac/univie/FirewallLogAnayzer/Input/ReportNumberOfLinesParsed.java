package at.ac.univie.FirewallLogAnayzer.Input;

public class ReportNumberOfLinesParsed extends Thread{

	private ParserCisco parser;
	
	public ReportNumberOfLinesParsed(ParserCisco parser) {
		this.parser = parser;
	}

	@Override
	public void run() {
		try {
		Thread.sleep(5000);
		while(true){
			long readedRows = parser.getNumberOfRowsReaded();
			long numberToRead  = parser.getNumberToRead();
			double percentage = ((Double.parseDouble(readedRows+"")/(Double.parseDouble(numberToRead+"")))*100);
			double percentageRound = Math.round(percentage*100);
			percentageRound = percentageRound/100;
			System.out.println("Logrows read: " + readedRows + " of " + numberToRead + " => " + percentageRound + " %");
			
			Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
