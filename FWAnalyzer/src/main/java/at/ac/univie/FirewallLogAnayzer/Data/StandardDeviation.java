package at.ac.univie.FirewallLogAnayzer.Data;


import java.util.ArrayList;

public class StandardDeviation {

    private ArrayList<Integer> differences;
    private double varianz;
    private double stabw;
    private double mittelwert;
    private double messagesPerTime;

    public StandardDeviation(ArrayList<Integer> differences){
        this.differences = differences;
        varianz = varianz();
        stabw = getStandartabweichung(varianz);
        messagesPerTime = getMessagePerMinute();
    }

    public double varianz(){

        mittelwert = getMittelwert();
        double varianzGrundgesamtmenge = 0;

        for (int i = 0; i<differences.size(); i++){
            double tmp = Math.pow(differences.get(i)-mittelwert,2);
            varianzGrundgesamtmenge = varianzGrundgesamtmenge + tmp;
        }

        varianzGrundgesamtmenge = runden(varianzGrundgesamtmenge/differences.size());
        stabw = getStandartabweichung(varianzGrundgesamtmenge);

        return varianzGrundgesamtmenge;
    }

    public double getMittelwert(){
        double mittelwert = 0;
        for (int i = 0; i<differences.size();i++){
            double x =  differences.get(i);
            mittelwert = x + mittelwert;
        }
        return runden(mittelwert/differences.size());
    }

    public double getStandartabweichung(double varianz){
        return  runden(Math.sqrt(varianz));
    }

    public double runden(Double x){
        return Math.round(x * 100) / 100.00;
    }

    public double getMessagePerMinute(){
        double messages = differences.size()+1;
        double time = 0;
        for (int i = 0; i<messages-1; i++){
            time = time + differences.get(i);
        }
        time = time/60;
        //System.out.println("Messages per time: " + messages/time);
        return messages/time;
    }

    public String toString(){
        return "Mittelwert    : " + mittelwert + "\nVarianz       : " + varianz + "\nSt. Abweichung: " + stabw + "\nDifferences   : " + differences.size();
        /*
        for (int i = 0; i<differences.size(); i++){
            System.out.print(i + "-" + differences.get(i));
        }
        */
    }
}
