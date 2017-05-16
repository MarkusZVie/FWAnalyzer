package at.ac.univie.FirewallLogAnayzer.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Init Test haha");

        setRootLayout();
        //initFileTab();
        changeScene("/dosGraphicsA.fxml");
    }

    public static BorderPane getRoot() {
        return rootLayout;
    }

    public static void changeScene(String scene) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(scene));
        AnchorPane ap = (AnchorPane) loader.load();

        BorderPane border = Main.getRoot();
        border.setCenter(ap);
    }

    /**
     * Root Layout Set
     */
    public void setRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/root.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show/Put first Scene
     */
    public void initFileTab() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/inputTab1.fxml"));
            AnchorPane ap = (AnchorPane) loader.load();

            // Set filetab View into the center of root layout.
            rootLayout.setCenter(ap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
