package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;
    static boolean Admin_Running=false;
    static boolean Viewer_Running=false;

    static String Current_Admin_Name="";
    static int Current_Admin_id=0;


    @Override
    public void start(Stage primaryStage) throws Exception{
        stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Before_Login.fxml"));
        primaryStage.setTitle("Intro Page");
        primaryStage.setScene(new Scene(root, 800, 485));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
