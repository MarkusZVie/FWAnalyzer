package at.ac.univie.FirewallLogAnayzer.Processing;

import at.ac.univie.FirewallLogAnayzer.Data.DoSData;
import at.ac.univie.FirewallLogAnayzer.Data.DoSDataList;
import at.ac.univie.FirewallLogAnayzer.Data.IpLocation;
import at.ac.univie.FirewallLogAnayzer.Data.LogRow;

import java.util.*;

/**
 * Created by josefweber on 11.05.17.
 */
public class AnalyzerDos implements IProcessingAnalyse {

    public AnalyzerDos(){}

    public void abc(){

    }

    @Override
    public void analyseDosAny(String protocol) {
        System.out.println(protocol);
        ArrayList<LogRow> fpl = StaticDos.filterProtocol(protocol);
        System.out.println(protocol + " has items: " + fpl.size());
        HashMap map = StaticDos.countIpDenies(fpl);
        StaticDos.manageall(map);
    }

    @Override
    public void analyseDosSyn() {
        String protocol = "TCP";
        System.out.println("Analyse TCP-SYN-DoS: " + protocol);

        // get all TCP Denies
        ArrayList<LogRow> fpl = StaticDos.filterProtocol(protocol);
        System.out.println(protocol + " has filtered items: " + fpl.size());
        //StaticDos.printFilterProtocol(fpl);

        // get Map <IP,DenyMessages>
        HashMap map = StaticDos.countIpDenies(fpl);
        // StaticDos.printHashmap(map);

        // calc Zeitabstände für alle DenyMessages jeder IP
        DoSDataList ddlPing = StaticDos.manageall(map);
        ddlPing.setName(protocol+"-Data");

    }

    @Override
    public void analyseDosLayer7() {
        String protocol = "http";
        System.out.println("Analyse Layer7-DoS: " + protocol);
    }

    @Override
    public void analyseDosPing() {
        String protocol = "icmp";
        System.out.println("Analyse ICMP-Flood-DoS: " + protocol);

        ArrayList<LogRow> fpl = StaticDos.filterProtocol(protocol);
        System.out.println(protocol + " has filtered items: " + fpl.size());


        // Diff zwischen allen Denies aller IPs gemischt!
        // StaticDos.calcTimeInterval(fpl);

        // Diff zwischen jeweils einer IP
        HashMap map = StaticDos.countIpDenies(fpl);
        DoSDataList ddlPing = StaticDos.manageall(map);
        ddlPing.setName(protocol+"-Data");

        //System.out.println("DDL Size: " + ddlPing.getDataEdited().size());
        //System.out.println("DDL Example: " + ddlPing.getDataEdited().get(3).getMessages().get(1).getProtocol());


        // SORT
        //ArrayList<DoSData> sortedList = sortMessagePerMinute(ddlPing, "asc");
        mostDeniedCountry(ddlPing, "abcdefg");

    }

    public ArrayList<DoSData> sortMessagePerMinute(DoSDataList processedData, String ascdesc){
        ArrayList<DoSData> dataraw = processedData.getDataEdited();
        System.out.println("Sort MPM: " + dataraw.size());

        if (ascdesc.equals("asc")){
            Collections.sort(dataraw, new Comparator<DoSData>() {
                @Override
                public int compare(DoSData o1, DoSData o2) {
                    return Double.compare(o2.getStd().getMessagePerMinute(), o1.getStd().getMessagePerMinute());
                }
            });
        }

        if (ascdesc.equals("desc")){
            Collections.sort(dataraw, new Comparator<DoSData>() {
                @Override
                public int compare(DoSData o1, DoSData o2) {
                    return Double.compare(o1.getStd().getMessagePerMinute(), o2.getStd().getMessagePerMinute());
                }
            });
        }

        for (int i = 0; i<dataraw.size();i++){
            System.out.println(i + " : " + dataraw.get(i).getStd().getMessagePerMinute()
                    + " ip: " +  dataraw.get(i).getMessages().get(0).getSrcIP() );
            /*
            int check = dataraw.get(i).getMessages().size();
            if (check > 1) {
                IpLocation iltemp = dataraw.get(i).getMessages().get(0).getLocation();
                if (iltemp == null) {
                    System.out.println("    No IpLocation: ");
                } else {
                    String check2 = iltemp.getCityName();
                    if (check2 == null){
                        System.out.println("    = null: ");
                    } else {
                        System.out.println("    country: " + check2);
                    }
                }
            }
            */
        }
        return dataraw;
    }

    // zählt alle Länder durch
    public void mostDeniedCountry(DoSDataList processedData, String ascdesc){
        ArrayList<DoSData> dataraw = processedData.getDataEdited();
        System.out.println("Sort Countries: " + dataraw.size());

        HashMap<String, ArrayList<DoSData>> countrymap = new HashMap<>();

        for (int i = 0; i<dataraw.size(); i++){

        }

        for (DoSData dd: dataraw) {
            int check = dd.getMessages().size();
            if (check > 0) {
                IpLocation iltemp = dd.getMessages().get(0).getLocation();
                if (iltemp == null) {
                    //System.out.println("    No IpLocation: ");
                } else {
                    String check2 = iltemp.getCityName();
                    if (check2 == null) {
                        //System.out.println("    = null: ");
                    } else {
                        //System.out.println("    country: " + check2);
                        if (!countrymap.containsKey(dd.getMessages().get(0).getLocation().getCountryName())) {
                            ArrayList<DoSData> ips = new ArrayList<DoSData>();
                            ips.add(dd);
                            countrymap.put(dd.getMessages().get(0).getLocation().getCountryName(), ips);
                        } else {
                            ArrayList<DoSData> r = countrymap.get(dd.getMessages().get(0).getLocation().getCountryName());
                            r.add(dd);
                            countrymap.put(dd.getMessages().get(0).getLocation().getCountryName(), r);
                        }

                    }
                }
            }
        }

        System.out.println("hashmap contains Countrys: " + countrymap.size());

        /*
        int tmpCount = 0;
        for (Map.Entry<String, ArrayList<DoSData>> entry : countrymap.entrySet()){

            System.out.println("#country: " + entry.getKey().toString() + " | ips: " + entry.getValue().size());
            ArrayList<DoSData> alr = entry.getValue();


            tmpCount = 0;
            for (DoSData c: alr){
                tmpCount = tmpCount + c.getMessages().size();
            }
            System.out.println();

        }
*/
    }

    public void sortStabw(){

    }

}
