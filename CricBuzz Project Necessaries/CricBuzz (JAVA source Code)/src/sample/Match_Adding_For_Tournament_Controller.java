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
import java.sql.SQLDataException;
import java.util.Optional;

public class Match_Adding_For_Tournament_Controller {

    @FXML
    Label Tour_Name_Label;
    @FXML
    ComboBox<String> Host_Team_ComboBox;
    @FXML
    ComboBox<String> Opponent_Team_ComboBox;
    @FXML
    ComboBox<String> AM_PM_ComboBox;
    @FXML
    DatePicker Match_Date_DatePicker;
    @FXML
    TextField Match_Time_TextField;
    @FXML
    ComboBox<String> Select_Staduim_ComboBox;
    @FXML
    Button Confirm_Button;
    @FXML
    Button Back_Button;

    public Tour selected_Tour;

    public String Host_Team;
    public  String Opponent_Team;
    public String Stadium_Name;
    public String Match_Date;
    public String Match_Time;
    public String Time_format;

    public int selected_Tournament_id;
    public int team1_ID;
    public int team2_ID;
    public int stadium_ID;







    public void init(Tour tour)
    {
        selected_Tour=tour;

        Tour_Name_Label.setText(tour.getTournament_Title());
        Host_Team = tour.getHost();

        Host_Team_ComboBox.getSelectionModel().select(tour.getHost());
        Host_Team_ComboBox.setDisable(true);



        ObservableList<String> Opponent_Team_List = FXCollections.observableArrayList();
        ObservableList<String> Stadium_List = FXCollections.observableArrayList();
        ObservableList<String> AM_PM_List = FXCollections.observableArrayList();

        AM_PM_List.add("AM");
        AM_PM_List.add("PM");

        AM_PM_ComboBox.setItems(AM_PM_List);
        AM_PM_ComboBox.getSelectionModel().select("AM");




        OracleConnect oc = null;

        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TEAM_NAME FROM TEAMS WHERE TEAM_NAME <> "+"'"+ Host_Team +"'"+ " ORDER BY TEAM_NAME";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Opponent_Team_List.add(rs.getString("TEAM_NAME"));
            }

            Opponent_Team_ComboBox.setItems(Opponent_Team_List);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        //stadium comboBox populating

        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT STADIUM_NAME FROM STADIUMS WHERE COUNTRY = "+"'"+Host_Team+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Stadium_List.add(rs.getString("STADIUM_NAME"));
            }

            Select_Staduim_ComboBox.setItems(Stadium_List);
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
            loader.setLocation(getClass().getResource("Tour_Updating_Window.fxml"));
            Parent root2 = loader.load();
            Tour_Updating_Window_Controller controller=loader.getController();
            Main.stage.setTitle("Tour Scheduling");
            Main.stage.setScene(new Scene(root2, 904, 633));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void Confirm_Button_Clicked(ActionEvent actionEvent)
    {


        Opponent_Team = Opponent_Team_ComboBox.getSelectionModel().getSelectedItem();

        if(Opponent_Team == null || Opponent_Team == "")
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Opponent Team Selection Error");
                alert.setContentText("Please select the OPPONENT TEAM ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        Stadium_Name= Select_Staduim_ComboBox.getSelectionModel().getSelectedItem();

        if(Stadium_Name == null || Stadium_Name == "")
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Staduim Selection Error");
                alert.setContentText("Please select a STADUIM ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }


        //validation for match date

        if(Match_Date_DatePicker.getValue() == null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Invalid date");
                alert.setContentText("Please select A suitable Match Date ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        Match_Date = Match_Date_DatePicker.getValue().toString();



        Match_Time = Match_Time_TextField.getText();

        if(Match_Time == null || Match_Time == "")
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Invalid Time");
                alert.setContentText("Please enter A Match Time ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        Time_format = AM_PM_ComboBox.getSelectionModel().getSelectedItem();

        if(Time_format == null || Time_format == "")
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INVALID INPUT");
                alert.setHeaderText("Invalid Time Format");
                alert.setContentText("Please Select the Time Format ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }


        //inserting the match to database

        OracleConnect oc = null;

        //collecting the tournament_ID
        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TOURNAMENT_ID FROM TOURNAMENTS WHERE TOURNAMENT_TITLE = "+"'"+selected_Tour.getTournament_Title()+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                selected_Tournament_id = rs.getInt("TOURNAMENT_ID");
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //collecting the team1_ID

        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TEAM_ID FROM TEAMS WHERE TEAM_NAME = "+"'"+Host_Team+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                team1_ID = rs.getInt("TEAM_ID");
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //collecting the team2_ID

        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TEAM_ID FROM TEAMS WHERE TEAM_NAME = "+"'"+Opponent_Team+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                team2_ID = rs.getInt("TEAM_ID");
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //collecting the stadium_ID

        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT STADIUM_ID FROM STADIUMS WHERE" +
                    " STADIUM_NAME = "+"'"+Stadium_Name+"' and COUNTRY = "+"'"+Host_Team+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                stadium_ID = rs.getInt("STADIUM_ID");
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //finding the inserted match no of that tournament of this tournament

        int Match_no = 0;
        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT COUNT(MATCH_ID) AS MATCH_NO FROM MATCHES WHERE TOURNAMENT_ID = "+selected_Tournament_id ;

            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Match_no = rs.getInt("MATCH_NO");
            }

            Match_no = Match_no + 1;

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String format = " ";

        if(Match_no == 1)
        {
            format = 1 + "st T20I";
        }
        else if(Match_no == 2)
        {
            format = 2 + "nd T20I";
        }

        else if(Match_no == 3)
        {
            format = 3 + "rd T20I";
        }
        else
            format = Match_no + "th T20I";






        String Match_Date_And_Time = Match_Date +" "+Match_Time+" "+Time_format;
        String Match_Title = Host_Team + " VS " +Opponent_Team;
        String Match_date_And_Time_input = "TO_DATE("+"'"+Match_Date_And_Time+"'"+","+"'YYYY-MM-DD HH:MI AM')";


//        System.out.println(Match_Title);
//        System.out.println("Team1: "+team1_ID);
//        System.out.println("Team2: "+team2_ID);
//        System.out.println("Stadium: "+stadium_ID);
//        System.out.println("Match_Date: "+Match_Date_And_Time);
//        System.out.println("Tour ID: "+selected_Tournament_id);
//        System.out.println("Format: "+format);

        //Now insert the match finally

        try {
            oc = new OracleConnect();


            String insertQuery = String.format("INSERT INTO MATCHES VALUES" +
                            " (MATCH_ID_SEQ.NEXTVAL,'%s',%d,%d,%d,'%s','%s',%s,%d)",
                    Match_Title,team1_ID,team2_ID,stadium_ID,"UPCOMING",format,
                    Match_date_And_Time_input,selected_Tournament_id);

            oc.updateDB(insertQuery);

            //successfully inserted

            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("INSERTED");
                alert.setHeaderText("A MATCH CREATED SUCCESSFULLY");
                alert.setContentText("Press OK To continue ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }

            Match_Time_TextField.setText(null);

        }catch (Exception e) {

            if(e instanceof SQLDataException)
            {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Invalid Time Input");
                    alert.setContentText("Please Insert a valid Time ");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {

                    }
                }catch (Exception exc)
                {
                    exc.printStackTrace();
                }

                Match_Time_TextField.setText(null);
            }
            else
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
