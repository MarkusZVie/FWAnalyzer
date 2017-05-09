package at.ac.univie.FirewallLogAnayzer.GUI;


import java.io.File;
import java.io.IOException;

import at.ac.univie.FirewallLogAnayzer.Input.IInputHandler;
import at.ac.univie.FirewallLogAnayzer.Input.InputHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by josefweber on 10.04.17.
 */
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
        IInputHandler x = new InputHandler();
        
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

        /*
        if (di.checkFile(pathfield.getText())){
            path = pathfield.getText();
            parsebtn.setVisible(true);
            changebtn.setVisible(true);
        } else {
            validlabell.setText("File not found");
            validlabell.setTextFill(Color.DARKRED);
            parsebtn.setVisible(false);
            changebtn.setVisible(false);
        }
*/
    }

    @FXML
    public void startparse(){
        //path = pathfield.getText();
        //InputInterface di = new Parser(path);

    	/*
        if (di.parsefile()){
            di.analyzeTest();
        } else {
            System.out.println("File could not be parsed.");
            validlabell.setText("File Error");
            validlabell.setTextFill(Color.DARKRED);
        }
        */

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
