package at.ac.univie.FirewallLogAnayzer.Data;

import java.util.ArrayList;

public class DoSDataList {

    private ArrayList<DoSData> dataEdited;
    String name;

    public DoSDataList(){
        dataEdited = new ArrayList<DoSData>();
    }
    public void addDoSData(DoSData singleData){
        dataEdited.add(singleData);
    }

    public ArrayList<DoSData> getDataEdited() {
        return dataEdited;
    }

    public void setDataEdited(ArrayList<DoSData> dataEdited) {
        this.dataEdited = dataEdited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
