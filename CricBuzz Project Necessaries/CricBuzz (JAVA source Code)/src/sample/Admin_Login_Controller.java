package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import sample.Main;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Optional;


public class Admin_Login_Controller {



    @FXML
     TextField userText;

    @FXML
     PasswordField passwordText;

    @FXML
     Button resetButton;

    @FXML
     Button loginButton;
    @FXML
     Button back_button;
    @FXML
     Button create_an_account_button;




    //functionalities of enable and disable login button
    public void initialize()
    {
        userText.setText(null);
        passwordText.setText(null);
    }



    public void loginAction(ActionEvent event) {

        //First ei check kore nicchi j kono textfield jeno empty na hoy
        if(userText.getText()==null || userText.getText().equals("") )
        {
            try {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Status");
                alert.setHeaderText("Login Failed");
                alert.setContentText("Please Enter a valid Username");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        if(passwordText.getText()==null || passwordText.getText().equals(""))
        {
            try {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Status");
                alert.setHeaderText("Login Failed");
                alert.setContentText("Please Enter a valid Password");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    ;
                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }




        Boolean login_flag=false ; //true if login successful
        OracleConnect oc = null;
        String User_Name=null;
        String Password=null;
        int Current_Admin_ID =0;
        try {
            //Retrieving data from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "select * from ADMIN";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {


                User_Name=rs.getString("NAME");
                Password=rs.getString("PASSWORD");
                Current_Admin_ID = rs.getInt("ID");


                if(userText.getText().equals(User_Name) && passwordText.getText().equals(Password))
                {
                    login_flag=true; //login successful
                    try {

                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Login Status");
                        alert.setHeaderText("Login Successful");
                        alert.setContentText("Press OK to visit CRICBUZZ!");
                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.get() == ButtonType.OK) {
                            try {

                                Main.Current_Admin_Name = userText.getText();
                                Main.Current_Admin_id = Current_Admin_ID;

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("Admin_Window.fxml"));
                                Parent root2 = loader.load();
                                Admin_Window_Controller controller=loader.getController();

                                controller.Admin_Label.setText("USERNAME: "+Main.Current_Admin_Name);
                                Main.stage.setTitle("Admin Window");
                                Main.stage.setScene(new Scene(root2, 809, 645));
                                Main.stage.show();

                            }catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }catch (Exception exc)
                    {
                        exc.printStackTrace();
                    }

                }


            }

            if(login_flag==false)
            {
                //Login failed
                try {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Status");
                    alert.setHeaderText("Login Failed");
                    alert.setContentText("Incorrect Username or Password");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        ;
                    }
                }catch (Exception exc)
                {
                    exc.printStackTrace();
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //after try to login the textfield of user and password will be cleared
//        userText.setText(null);
//        passwordText.setText(null);



    }



    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    public void back_button_pressed(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Before_Login.fxml"));
            Parent root2 = loader.load();
            //Before_Login_Controller controller=loader.getController();
            Main.stage.setTitle("Intro Page");
            Main.stage.setScene(new Scene(root2, 800, 485));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }



    }

    public void create_an_account_button_pressed(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Create_Account_Window.fxml"));
            Parent root2 = loader.load();
            Create_Account_Window_Controller controller=loader.getController();
            Main.stage.setTitle("Create An Account");
            Main.stage.setScene(new Scene(root2, 551, 317));

            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }



    }

//    void setMain(Main main) {
//        this.main = main;
//    }

}
