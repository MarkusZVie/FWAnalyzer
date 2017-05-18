package at.ac.univie.FirewallLogAnayzer.GUI;

import at.ac.univie.FirewallLogAnayzer.Data.DoSData;
import at.ac.univie.FirewallLogAnayzer.Data.DoSDataList;
import at.ac.univie.FirewallLogAnayzer.Data.LogTypeSingelton;
import at.ac.univie.FirewallLogAnayzer.Exceptions.LogIdNotFoundException;
import at.ac.univie.FirewallLogAnayzer.Input.IInputHandler;
import at.ac.univie.FirewallLogAnayzer.Input.InputHandler;
import at.ac.univie.FirewallLogAnayzer.Processing.AnalyzerDos;
import at.ac.univie.FirewallLogAnayzer.Processing.IProcessingAnalyse;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import com.sun.org.apache.xml.internal.dtm.*;
import com.sun.org.apache.xpath.internal.operations.Number;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by josefweber on 16.05.17.
 */
public class DoSControllerA {

    private HashMap<String, ArrayList<DoSData>> countrymap;
    private LineChart<String,Number> lineChart = null;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    private BarChart<String,Number> bc = null;
    private CategoryAxis xxAxis;
    private NumberAxis yyAxis;

    @FXML Label mainLabel;
    @FXML private AnchorPane mainAP;
    @FXML private AnchorPane apCenter;
    @FXML private Button backtochartBtn;
    @FXML private AnchorPane apCenterZoom;

    @FXML
    public void initialize() {
        System.out.println("init DoSController-A");
        HashMap<String, Integer> cc = tmpCallMainCode();
        initpiechart(cc);
        apCenterZoom.setVisible(false);
        backtochartBtn.setVisible(false);
    }

    public void backtochart(){
        apCenterZoom.setVisible(false);
        apCenter.setVisible(true);
        backtochartBtn.setVisible(false);
        //lineChart = null;
        //apCenterZoom.getChildren().remove(lineChart);
        if (!lineChart.getData().isEmpty()){
            System.out.println("Remove Series");
            lineChart.getData().remove((lineChart.getData().size()-1));
        }
    }

    public void initpiechart(HashMap<String, Integer> cc){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : cc.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(),entry.getValue()));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("country pie chart of messages");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.RIGHT);

        chart.setCursor(Cursor.CROSSHAIR);
        chart.setLabelsVisible(true);
        chart.setLegendVisible(true);

        final Label caption = new Label();
        caption.setTextFill(Color.GRAY);
        caption.setStyle("-fx-font: 16 arial;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(data.getName() + " " + data.getPieValue());
                }
            });
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    zoomCountry(data.getName());
                }
            });
        }

        apCenter.getChildren().addAll(chart,caption);

    }

    public void initLineChart(ArrayList<DoSData> countrydata){
        if (lineChart == null) {
            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            xAxis.setLabel("Time");
        } else {
            lineChart.getData().removeAll(lineChart.getData());
            lineChart.getData().remove(xAxis);
            lineChart.getData().remove(yAxis);
        }
        lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle("Stock Monitoring");

        for (DoSData dd: countrydata){
            XYChart.Series series1 = new XYChart.Series();
            series1.setName(dd.getMessages().get(0).getSrcIP());

            for (int i = 0; i < dd.getStd().getDifferences().size(); i++) {
                String sTmp = Objects.toString(dd.getMessages().get(i).getDateTime(), null);
                series1.getData().add(new XYChart.Data(sTmp, dd.getStd().getDifferences().get(i)));
            }
            lineChart.getData().add(series1);
        }
        apCenterZoom.getChildren().add(lineChart);
        /*
        Legende ausblenden
        -> Hover an!
        1280-720 px
        Zoom funktion?
        ticks?
        zuerst IPs anzeigen und dann einzelne XY Charts machen?
         */
    }

    public void initBarChart(){

          String austria = "Austria";
          String brazil = "Brazil";
          String france = "France";
          String italy = "Italy";
          String usa = "USA";
        xxAxis = new CategoryAxis();
        yyAxis = new NumberAxis();
        bc = new BarChart(xxAxis,yyAxis);


        bc.setTitle("Country Summary");
            xxAxis.setLabel("Country");
            yyAxis.setLabel("Value");

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("2003");
            series1.getData().add(new XYChart.Data(austria, 25601.34));
            series1.getData().add(new XYChart.Data(brazil, 20148.82));
            series1.getData().add(new XYChart.Data(france, 10000));
            series1.getData().add(new XYChart.Data(italy, 35407.15));
            series1.getData().add(new XYChart.Data(usa, 12000));

            XYChart.Series series2 = new XYChart.Series();
            series2.setName("2004");
            series2.getData().add(new XYChart.Data(austria, 57401.85));
            series2.getData().add(new XYChart.Data(brazil, 41941.19));
            series2.getData().add(new XYChart.Data(france, 45263.37));
            series2.getData().add(new XYChart.Data(italy, 117320.16));
            series2.getData().add(new XYChart.Data(usa, 14845.27));

            XYChart.Series series3 = new XYChart.Series();
            series3.setName("2005");
            series3.getData().add(new XYChart.Data(austria, 45000.65));
            series3.getData().add(new XYChart.Data(brazil, 44835.76));
            series3.getData().add(new XYChart.Data(france, 18722.18));
            series3.getData().add(new XYChart.Data(italy, 17557.31));
            series3.getData().add(new XYChart.Data(usa, 92633.68));


            bc.getData().addAll(series1, series2, series3);
        apCenterZoom.getChildren().add(bc);
    }


    public void zoomCountry(String country){
        System.out.println(country + " clicked .. show Info");
        ArrayList<DoSData> countryData = countrymap.get(country);
        System.out.println("IP's: " + countryData.size());

        apCenter.setVisible(false);
        apCenterZoom.setVisible(true);
        backtochartBtn.setVisible(true);
        initLineChart(countryData);
        //initBarChart();
    }

    public HashMap<String, Integer> tmpCallMainCode(){
        IInputHandler inputHandler = new InputHandler();
        // /Users/josefweber/Desktop/SyslogCatchAll-2017-03-14.txt
        // C:\Users\Lezard\Desktop\SyslogCatchAll-2017-03-14.txt
        try {
            inputHandler.loadeFirewallLog("/Users/josefweber/Desktop/SyslogCatchAll-2017-03-14.txt", LogTypeSingelton.getInstance().getSupportedLogTypeList().get(0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (LogIdNotFoundException e) {
            e.printStackTrace();
        }
        // DOS
        IProcessingAnalyse da = new AnalyzerDos();
        DoSDataList ddl = da.analyseDos("icmp", 60);
        // Sort mpm
        da.sortMessagePerMinute(ddl, "asc");
        // Sort country to Controller-Objekt
        countrymap = da.messagesOfCountry(ddl);
        HashMap<String, Integer> countryCount = da.sumMessagesPerCountry(countrymap, "asc");
        return countryCount;
    }

}
