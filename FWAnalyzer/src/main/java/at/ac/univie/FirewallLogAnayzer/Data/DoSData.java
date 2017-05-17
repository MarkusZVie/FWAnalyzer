package at.ac.univie.FirewallLogAnayzer.Data;



import com.oracle.tools.packager.Log;

import java.util.ArrayList;
import java.util.Date;

public class DoSData {
    private ArrayList<LogRow> messages;
    private StandardDeviation std;
    private MessagePerTime mpt;
    private ArrayList<Integer> mptList;

    public DoSData(ArrayList<LogRow> messages, StandardDeviation std){
        this.messages = messages;
        this.std = std;
    }


    public void setMpt(int min){
        MessagePerTime mpt = new MessagePerTime();
        mptList = mpt.calcMsgPerSlot(min, messages);
    }

    public ArrayList<Integer> getMptList(){
        return mptList;
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
