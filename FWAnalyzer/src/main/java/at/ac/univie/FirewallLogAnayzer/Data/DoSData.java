package at.ac.univie.FirewallLogAnayzer.Data;



import java.util.ArrayList;

public class DoSData {
    private ArrayList<LogRow> messages;
    private StandardDeviation std;

    public DoSData(ArrayList<LogRow> messages, StandardDeviation std){
        this.messages = messages;
        this.std = std;
    }

    public void setMessages(ArrayList<LogRow> map) {
        this.messages = map;
    }

    public void setStd(StandardDeviation std) {
        this.std = std;
    }

    public ArrayList<LogRow>  getMessages() {
        return messages;
    }

    public StandardDeviation getStd() {
        return std;
    }
}
