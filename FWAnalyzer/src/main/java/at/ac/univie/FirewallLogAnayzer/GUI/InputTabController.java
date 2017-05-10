package at.ac.univie.FirewallLogAnayzer.GUI;


import at.ac.univie.FirewallLogAnayzer.Data.LogTypeSingelton;
import at.ac.univie.FirewallLogAnayzer.Exceptions.LogIdNotFoundException;
import at.ac.univie.FirewallLogAnayzer.Input.IInputHandler;
import at.ac.univie.FirewallLogAnayzer.Input.InputHandler;
import at.ac.univie.FirewallLogAnayzer.Processing.TemporairProcessing;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InputTabController {

    // Falscher IMPORT !!
    @FXML
    private TextField pathfield;
    @FXML
    private Button btn1;
    @FXML
    private Button parsebtn;
    @FXML
    private Label validlabell;

    @FXML
    private Label parsestatus;

    @FXML
    private Button changebtn;

    private String path;

    public InputTabController(){}

    @FXML
    public void initialize() {
        System.out.println("init InputTabController");
        parsebtn.setVisible(false);
        changebtn.setVisible(false);
    }

    public void presschangebtn() throws IOException {
        System.out.println("Changing Scene Example");

        Main.changeScene("/analyzeTab1.fxml");
    }

    @FXML
    public void startchoose(){

        path = pathfield.getText();
        IInputHandler ih = new InputHandler();

      //  InputInterface di = new Parser(path);

        // by typing
        if (pathfield.getText().length() != 0){
            validlabell.setText("File was chosen");
            validlabell.setTextFill(Color.DARKGREEN);

        } else {
        // by filemanger
            File chosen = chooseFile();
            if (chosen != null) {
                pathfield.setText(chosen.toString());
                validlabell.setText("File was chosen");
                validlabell.setTextFill(Color.DARKGREEN);
            }

        }

        File f = new File(pathfield.getText());

        if (f.exists() && !f.isDirectory()){
            path = pathfield.getText();
            parsebtn.setVisible(true);

        } else {
            validlabell.setText("File not found");
            validlabell.setTextFill(Color.DARKRED);
            parsebtn.setVisible(false);
            changebtn.setVisible(false);
        }

    }

    @FXML
    public void startparse(){

        IInputHandler ih = new InputHandler();
        try {
            ih.loadeFirewallLog(path, LogTypeSingelton.getInstance().getSupportedLogTypeList().get(0));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (LogIdNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Test Parsing
        TemporairProcessing.doSomething();

        parsestatus.setText("Parsing Success!");
        // enable next view if parse success
        changebtn.setVisible(true);
    }

    public File chooseFile(){
        // get Scene from current page
        Stage stage = (Stage) btn1.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        // open Diaog from Scene (btn1)
        File file = fileChooser.showOpenDialog(stage);
        System.out.println(file);

        btn1.setText("Choose File");
        return file;
    }
}
