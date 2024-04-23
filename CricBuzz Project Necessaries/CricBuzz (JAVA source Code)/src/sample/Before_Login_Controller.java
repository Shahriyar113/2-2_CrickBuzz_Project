package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import static java.lang.System.exit;


public class Before_Login_Controller {

    @FXML
    private Button Admin_button;
    @FXML
    private Button viewer_button;
    @FXML
    private Button Exit_button;


    public void initialize()
    {
         ;
    }


    public Button getManufacturer_button() {
        return Admin_button;
    }

    public Button getViewer_button() {
        return viewer_button;
    }

    public void Admin_button_pressed(ActionEvent actionEvent)  {

        Main.Admin_Running=true;
        Main.Viewer_Running=false;



        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Admin_Login.fxml"));
            Parent root2 = loader.load();
            Admin_Login_Controller controller=loader.getController();
            Main.stage.setTitle("Admin Login Page");
            Main.stage.setScene(new Scene(root2, 549, 250));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }



    }

    public void viewer_button_pressed(ActionEvent actionEvent) {

        Main.Admin_Running=false;
        Main.Viewer_Running=true;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Viewer_Window.fxml"));
            Parent root2 = loader.load();
            Viewer_Window_Controller controller=loader.getController();
            Main.stage.setTitle("Viewer Window");
            Main.stage.setScene(new Scene(root2, 611, 628));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }



    }

    public void Exit_button_pressed(ActionEvent actionEvent) {

        exit(0);
    }


}
