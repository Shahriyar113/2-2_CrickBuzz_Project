package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Schedule_A_Tour_Window_Controller {

    @FXML
    Button Confirm_Button;
    @FXML
    Button Back_Button;

    @FXML
    TextField Tournament_Title_Textfield;
    @FXML
    DatePicker Start_Date_DatePicker;
    @FXML
    DatePicker End_Date_DatePicker;
    @FXML
    ComboBox<String> Host_Country_ComboBox;
    @FXML
    TextField Totat_Matches_Textfield;



    public void initialize()
    {
        OracleConnect oc = null;

        ObservableList<String> CountryList = FXCollections.observableArrayList();

        String country="";
        try {
            //Retrieving Ranking info from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TEAM_NAME FROM TEAMS ORDER BY TEAM_NAME";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                country = rs.getString("TEAM_NAME");
                CountryList.add(country);


            }

            Host_Country_ComboBox.setItems(CountryList);

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void Back_Button_Clicked(ActionEvent actionEvent)
    {

        try {
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


    public void Confirm_Button_Clicked(ActionEvent actionEvent)
    {

        String Tournament_title_input = Tournament_Title_Textfield.getText();
        if(Totat_Matches_Textfield.getText() == null ||  Totat_Matches_Textfield.getText().equals(""))
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("TOURNAMENT TITLE can not be empty");
                alert.setContentText("Please insert A suitable TITLE ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }

            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        String System_date=formatter.format(date).toString();


        if(Start_Date_DatePicker.getValue() == null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Invalid date");
                alert.setContentText("Please select A suitable Start date ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        else if(End_Date_DatePicker.getValue()==null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Invalid date");
                alert.setContentText("Please select A suitable End date ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        //if start date is bigger than end date,then error
        else if(Start_Date_DatePicker.getValue().toString().compareToIgnoreCase
                (End_Date_DatePicker.getValue().toString()) > 0)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Invalid date");
                alert.setContentText("Please select A suitable Start and End date ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        //if start date is less than today

        else if(Start_Date_DatePicker.getValue().toString().compareToIgnoreCase
                (System_date) < 0)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Old date is not valid");
                alert.setContentText("Please choose a valid START DATE ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }

            return;
        }



        String Start_Date_Input = Start_Date_DatePicker.getValue().toString();
        String End_Date_Input = End_Date_DatePicker.getValue().toString();


        String Host_selected = Host_Country_ComboBox.getSelectionModel().getSelectedItem();

        if(Host_selected == null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("HOST is not selected");
                alert.setContentText("Please select A HOST Country ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }



        int Totat_Matches_input=0;

        try {
             Totat_Matches_input = Integer.parseInt(Totat_Matches_Textfield.getText());
        }catch (Exception e)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("INVALID INPUT FOR NUM OF MATCHES");
                alert.setContentText("Please input a valid number for NUM OF MATCHES field ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }

            Totat_Matches_Textfield.setText(null);
            return;


        }



        OracleConnect oc = null;

        try {
            oc = new OracleConnect();

            String Input_Start_Date = "TO_DATE("+"'"+Start_Date_Input+"'"+","+"'YYYY-MM-DD')";
            String Input_End_Date = "TO_DATE("+"'"+End_Date_Input+"'"+","+"'YYYY-MM-DD')";


            String insertQuery = String.format(
                    "INSERT INTO TOURNAMENTS (TOURNAMENT_ID,TOURNAMENT_TITLE,START_DATE," +
                            "END_DATE,HOST_COUNTRY,STATUS,NUM_OF_MATCHES) VALUES " +
                            "(TOURNAMENTS_ID_SEQ.NEXTVAL ,'%s',%s,%s,'%s','%s',%d)",
                    Tournament_title_input, Input_Start_Date,Input_End_Date,Host_selected,
                    "UPCOMING",Totat_Matches_input);


            oc.updateDB(insertQuery);

            //successfully inserted



            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("INSERTED");
                alert.setHeaderText("TOUR CREATED SUCCESSFULLY");
                alert.setContentText("Press OK To continue ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            Tournament_Title_Textfield.setText(null);
            Totat_Matches_Textfield.setText(null);




        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }










        }






}
