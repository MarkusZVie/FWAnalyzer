package at.ac.univie.FirewallLogAnayzer.Processing;

import at.ac.univie.FirewallLogAnayzer.Data.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// Static Funktionen für DoS Analyse
public class StaticDos {

    // Filtere nachrichten nach Protokoll
    // 		icmp
    // 		TCP
    public static ArrayList<LogRow> filterProtocol(String protocol){
        ArrayList<LogRow> fpl = new ArrayList<LogRow>();
        for(LogRow lr: LogRows.getInstance().getLogRows()){
            if (lr.getProtocol()!= null && lr.getProtocol().equals(protocol)){
                fpl.add(lr);
            }
        }
        return fpl;
    }

    public static void printFilterProtocol(ArrayList<LogRow> fpl){
        for (LogRow lr: fpl){
            System.out.println(lr.getSrcIP());
        }
    }

    // Hashmap bestehend aus:
    //		IP = Key
    //		Arraylist = Value (beinhaltet alle denies der IP)
    // Alle IPs mit deren Denies
    public static HashMap<String, ArrayList<LogRow>> countIpDenies(ArrayList<LogRow> denies){
        HashMap<String, ArrayList<LogRow>> map = new HashMap<String, ArrayList<LogRow>>();

        for (LogRow lr: denies){
            if (!map.containsKey(lr.getSrcIP())){
                ArrayList<LogRow> rn = new ArrayList<LogRow>();
                rn.add(lr);
                map.put(lr.getSrcIP(), rn);
            } else {
                ArrayList<LogRow> r = map.get(lr.getSrcIP());
                r.add(lr);
                map.put(lr.getSrcIP(), r);
            }
        }

        System.out.println(">>> Counted IPs: " + map.size());
        return map;
    }

    public static void printHashmap(HashMap<String, ArrayList<LogRow>> map){
        for (Map.Entry<String, ArrayList<LogRow>> entry : map.entrySet()){
            System.out.println("#ip: " + entry.getKey().toString() + " | Array: " + entry.getValue().size());
            ArrayList<LogRow> alr = entry.getValue();

            for (LogRow lrr: alr){
                if (lrr.getLocation() != null) {
                    if (lrr.getLocation().getCountryName() != null) {
                        System.out.println(lrr.getLocation().getCountryName());
                    }
                }
            }

        }
    }

    // Für jede IP (mit deren Deny-LogRows)
    // nur wenn LogRow > 1 sonst kann keine Zeit verglichen werden
    public static DoSDataList manageall(HashMap<String, ArrayList<LogRow>> map){
        DoSDataList ddl = new DoSDataList();
        for (Map.Entry<String, ArrayList<LogRow>> entry : map.entrySet()){

            // für Jede IP und deren LogRows > 1
            //      -> Differences
            //      -> Standardabweichung
            //      = 1 Objekt DoS Data
            ArrayList<LogRow> alr = entry.getValue();
            if (alr.size() > 1){
                ArrayList<Integer> oneIpDifferences = calcTimeInterval(alr);
                StandardDeviation sData = new StandardDeviation(oneIpDifferences);

                System.out.println(sData.toString());
                System.out.println("---------------");

                DoSData dd = new DoSData(alr, sData);
                ddl.addDoSData(dd);
            }
        }
        return ddl;
    }


    // get Single IP from Hashmap DoSDataList
    public static DoSData getSingleIp(DoSDataList ddl, String ip){
        //System.out.println("Search for: " + ip);
        DoSData singleIp = null;
        for (int i = 0; i < ddl.getDataEdited().size() ; i++) {
            if (ddl.getDataEdited().get(i).getMessages().get(0).getSrcIP().equals(ip)){
                singleIp = ddl.getDataEdited().get(i);
            }
        }
        return singleIp;
    }


    // Vergleiche jeweils 2 Zeiten von LogRows und speichere den Wert in ArrayList
    // Alle die nur eine Zeit haben sind nicht relevant
    // Return Wert für Analyse -> Varianz
    public static ArrayList<Integer> calcTimeInterval(ArrayList<LogRow> denies){
        System.out.println("  calcTime() for ip: " + denies.get(0).getSrcIP() + " having " + denies.size() + " messages.");

        ArrayList<Integer> differences = new ArrayList<>();

        for (int i = 0; i<denies.size(); i++){
            if (i+1 < denies.size()) {
                Date dateA = denies.get(i).getDateTime();
                Date dateB = denies.get(i + 1).getDateTime();
                long diff = dateB.getTime() - dateA.getTime();
                long sec = TimeUnit.MILLISECONDS.toSeconds(diff);
                //System.out.println("# " + denies.get(i).getDateTime().toString() + " - " + denies.get(i).getSrcIP());
                //System.out.println("# " + denies.get(i + 1).getDateTime().toString() + " - " + denies.get(i + 1).getSrcIP());
                //System.out.println(i + " Diff in sec.: " + sec);
                Integer parseLong = (int) (long) sec;
                differences.add(parseLong);
            }
        }
        return differences;
    }

}
