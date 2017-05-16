package at.ac.univie.FirewallLogAnayzer.Processing;

import at.ac.univie.FirewallLogAnayzer.Data.DoSData;
import at.ac.univie.FirewallLogAnayzer.Data.DoSDataList;
import at.ac.univie.FirewallLogAnayzer.Data.IpLocation;
import at.ac.univie.FirewallLogAnayzer.Data.LogRow;

import java.util.*;

public class AnalyzerDos implements IProcessingAnalyse {

    public AnalyzerDos(){}

    @Override
    public DoSDataList analyseDos(String dosProtocolType) {
        System.out.println("analyseDos(): Analyse TCP-SYN-DoS: " + dosProtocolType);

        // get Protocol dedicated LogRows
        ArrayList<LogRow> fpl = StaticDos.filterProtocol(dosProtocolType);
        System.out.println("analyseDos(): " + dosProtocolType + " has filtered items: " + fpl.size());
        //StaticDos.printFilterProtocol(fpl);


        // Diff zwischen allen Denies aller IPs gemischt/nicht sortiert nach Prodokoll
        //  -> relevant für DDoS?
        // StaticDos.calcTimeInterval(fpl);

        // get Map <IP, List<LogRows>>
        HashMap map = StaticDos.countIpDenies(fpl);
        // StaticDos.printHashmap(map);

        // calc Zeitabstände für alle DenyMessages jeder IP
        //  -> Diff zwischen jeweils einer IP
        DoSDataList ddlist = StaticDos.manageall(map);
        ddlist.setName(dosProtocolType+"-Data");

        return ddlist;
    }

    @Override
    public void analyseDDoS() {

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

    // Hashmap mit Country und Array mit allen DoSData -> return countrymap zurückgeben
    // Hashmap mit Country und Counts -> return countryCount
    public HashMap<String, ArrayList<DoSData>> messagesOfCountry(DoSDataList processedData){
        ArrayList<DoSData> dataraw = processedData.getDataEdited();
        System.out.println("Sort Countries: " + dataraw.size());

        // Hashmap -> Key = Country, Value = Alle IPs mit gleichem Country
        HashMap<String, ArrayList<DoSData>> countrymap = new HashMap<>();

        int failedCountries = 0;
        ArrayList<DoSData> unassigned = new ArrayList<DoSData>();

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
                        int ff = dd.getMessages().size();
                        failedCountries = failedCountries + ff;
                        unassigned.add(dd);
                        //failedCountries++;

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
            countrymap.put("unassignedMessages", unassigned);
        }
        System.out.println("hashmap contains Countrys: " + countrymap.size() + " || no country found of Messages: " + failedCountries + " LL" + unassigned.size());
        return countrymap;
    }

    // zählen aller Messages pro Country, aus der countrymap
    // Hashmap NUR aus -> Country + MessageCount-aller IPs mit Country
    public HashMap<String, Integer> sumMessagesPerCountry(HashMap<String, ArrayList<DoSData>> countrymap, String ascdesc){
        // zählen aller Messages pro Country, aus der countrymap
        HashMap<String,Integer> countryCount = new HashMap<String,Integer>();
        int tmpCount = 0;
        for (Map.Entry<String, ArrayList<DoSData>> entry : countrymap.entrySet()){
            ArrayList<DoSData> alr = entry.getValue();
            tmpCount = 0;
            for (DoSData c: alr){
                tmpCount = tmpCount + c.getMessages().size();
            }
            countryCount.put(entry.getKey().toString(), tmpCount);
            System.out.println("#country: " + entry.getKey().toString() + " | ips: " + entry.getValue().size() + " | having count messages: " + tmpCount);
        }
        return countryCount;
    }


    public void sortStabw(){

    }

    // get Single IP from DoSList -> Check for null
    public DoSData getSingleIP(DoSDataList processedData, String ip){
        DoSData getthis = StaticDos.getSingleIp(processedData, ip);
        return getthis;
    }

}
