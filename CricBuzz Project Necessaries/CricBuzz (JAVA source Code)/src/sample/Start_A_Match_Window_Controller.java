package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Start_A_Match_Window_Controller {

    @FXML
    Button Back_Button;

    @FXML
    Button Start_Button;

    @FXML
    public TextField Match_Search_Textfield;
    @FXML
    public TableView<Browse_Match> All_Matches_Tableview;
    @FXML
    public TableColumn<Browse_Match, String> Match_Title_Col;
    @FXML
    public TableColumn<Browse_Match, String> Format_Col;
    @FXML

    public TableColumn<Browse_Match, String> Date_Col;
    @FXML

    public TableColumn<Browse_Match, String> Time_Col;

    @FXML
    public TableColumn<Browse_Match, String> Stadium_Col;
    @FXML
    public TableColumn<Browse_Match, String> Status_Col;
    @FXML
    public TableColumn<Browse_Match, String> Tournament_Title_Col;

    public ObservableList<Browse_Match> All_Matches_List = FXCollections.observableArrayList();


    public void initialize() {
        OracleConnect oc = null;
        String match_title = "";
        String format = "";
        String date = "";
        String time = "";
        String stadium = "";
        String status = "";
        String tournament_title = "";

        String date_and_time = "";


        try {
            //Retrieving ALL PLAYERS info from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT M.MATCH_TITLE,M.FORMAT,TO_CHAR(M.MATCH_DATE,'YYYY-MM-DD HH:MIAM') " +
                    "AS MATCH_DATE_AND_TIME,S.STADIUM_NAME,M.STATUS,T.TOURNAMENT_TITLE\n" +
                    "FROM MATCHES M JOIN STADIUMS S on (M.STADIUM_ID = S.STADIUM_ID) JOIN\n" +
                    "TOURNAMENTS T on (M.TOURNAMENT_ID = T.TOURNAMENT_ID) WHERE " +
                    "M.STATUS = 'UPCOMING' ORDER BY MATCH_DATE";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                match_title = rs.getString("MATCH_TITLE");
                format = rs.getString("FORMAT");
                date_and_time = rs.getString("MATCH_DATE_AND_TIME");
                stadium = rs.getString("STADIUM_NAME");
                status = rs.getString("STATUS");
                tournament_title = rs.getString("TOURNAMENT_TITLE");

                String[] match_date_and_Time = date_and_time.split(" ");
                date = match_date_and_Time[0];
                time = match_date_and_Time[1];

                All_Matches_List.add(new Browse_Match(match_title, format, date, time, stadium, status, tournament_title));


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


        Match_Title_Col.setCellValueFactory(new PropertyValueFactory<>("Match_Title"));
        Format_Col.setCellValueFactory(new PropertyValueFactory<>("Format"));
        Date_Col.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Time_Col.setCellValueFactory(new PropertyValueFactory<>("Time"));
        Stadium_Col.setCellValueFactory(new PropertyValueFactory<>("Stadium"));
        Status_Col.setCellValueFactory(new PropertyValueFactory<>("Status"));
        Tournament_Title_Col.setCellValueFactory(new PropertyValueFactory<>("Tournament_Title"));


        All_Matches_Tableview.setItems(All_Matches_List);

        FilteredList<Browse_Match> filteredData = new FilteredList<>(All_Matches_List, b -> true);

        Match_Search_Textfield.textProperty().addListener((observabe, oldValue, newValue) -> {
            filteredData.setPredicate(browse_match -> {

                //if the searched key is not found,then the table view will be empty
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null)
                    return true;

                String searched_keyword = newValue.toLowerCase();

                if (browse_match.getMatch_Title().toLowerCase().startsWith(searched_keyword))
                    return true;
                else
                    return false;
            });
        });

        //SortedList<Tour> sortedData = new SortedList<>(filteredData);
        //sortedData.comparatorProperty().bind(Tour_Search_list_tableview.comparatorProperty());
        All_Matches_Tableview.setItems(filteredData);


    }


    public void Start_Button_Clicked(ActionEvent actionEvent) {

        Browse_Match selected_Match = (Browse_Match) All_Matches_Tableview.getSelectionModel().getSelectedItem();

        if (selected_Match == null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Nothing is selected");
                alert.setContentText("Please Select a Match");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            return;
        }

        if (selected_Match.getStatus().equalsIgnoreCase("LIVE"))
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("The Match has already started");
                alert.setContentText("Please Select another Match");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            return;
        }

        selected_Match.setStatus("LIVE");

        //a match is selected

        String Date_And_Time = selected_Match.getDate()+" "+selected_Match.getTime();

        OracleConnect oc = null;
        int selected_Match_ID=0;
        int selected_Match_Tournament_ID=0;
        int Team1_ID=0;
        int Team2_ID=0;




        try {
            //Retrieving ALL PLAYERS info from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT MATCH_ID,TEAM1_ID,TEAM2_ID,TOURNAMENT_ID FROM MATCHES\n" +
                    "WHERE MATCH_TITLE = '"+selected_Match.getMatch_Title() +"' and MATCH_DATE = TO_DATE('"+Date_And_Time +"', 'YYYY-MM-DD HH:MIAM')";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {

                selected_Match_ID = rs.getInt("MATCH_ID");
                selected_Match_Tournament_ID = rs.getInt("TOURNAMENT_ID");
                Team1_ID = rs.getInt("TEAM1_ID");
                Team2_ID = rs.getInt("TEAM2_ID");

            }


            //Updating tournament status and Match status
            String update_tour_status ="UPDATE TOURNAMENTS SET STATUS = 'RUNNING' WHERE" +
                    " TOURNAMENT_ID = "+ selected_Match_Tournament_ID;
            oc.updateDB(update_tour_status);
           // System.out.println(update_tour_status);

            String update_match_status="UPDATE MATCHES SET STATUS = 'LIVE' WHERE " +
                    "MATCH_ID = "+selected_Match_ID;
             oc.updateDB(update_match_status);
            //System.out.println(update_match_status);





        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




      //  Populating PLAYING_XI table for this match

        List<Integer> Team1_players=new ArrayList<>();
        List<Integer> Team2_players=new ArrayList<>();


        try {

            oc = new OracleConnect();
            String query1 = "SELECT PLAYER_ID FROM PLAYERS WHERE TEAM_ID ="+Team1_ID;
            ResultSet rs1 = oc.searchDB(query1);
            while (rs1.next()) {
                Team1_players.add(rs1.getInt("PLAYER_ID"));
            }

            for (int i=0;i<11;i++)
            {
                String insert_query=String.format("INSERT INTO PLAYING_XI VALUES (%d,%d,%d,%s)",
                        selected_Match_ID ,Team1_ID,Team1_players.get(i),null);

                oc.updateDB(insert_query);
                //System.out.println(insert_query);

            }
            System.out.println();


            String query2 = "SELECT PLAYER_ID FROM PLAYERS WHERE TEAM_ID ="+Team2_ID;
            ResultSet rs2 = oc.searchDB(query2);
            while (rs2.next()) {
                Team2_players.add(rs2.getInt("PLAYER_ID"));
            }

            for (int i=0;i<11;i++)
            {
                String insert_query=String.format("INSERT INTO PLAYING_XI VALUES (%d,%d,%d,%s)",
                        selected_Match_ID ,Team2_ID,Team2_players.get(i),null);
                oc.updateDB(insert_query);
                //System.out.println(insert_query);
            }
            System.out.println();



            //populating MATCH_UMPIRES
            for (int i=3;i<=5;i++)
            {
                String insert_query=String.format("INSERT INTO MATCH_UMPIRES VALUES (%d,%d)",
                        selected_Match_ID ,i);
                oc.updateDB(insert_query);
                //System.out.println(insert_query);
            }
            System.out.println();


            //populating FINAL_SCORE

            String Final_Score_Initial = String.format("INSERT INTO FINAL_SCORES VALUES (%d,%d,%d,%f,%d,%d,%d,%d,%f,%d,%d,'%s',%d)",
                    selected_Match_ID,Team1_ID,0,0.0,0,0 ,Team2_ID,0,0.0,0,0,null,Team1_ID);
            oc.updateDB(Final_Score_Initial);
            //System.out.println(Final_Score_Initial);
            //System.out.println();


            //Populating BATTING CARD BY OPENNING TWO BATSMAN OF TEAM1_ID

            String Batsman1_Insert =  String.format("INSERT INTO BATTING_CARD VALUES (%d,%d,%d,%d,%d,%d,%d,%d,'%s')",
                    selected_Match_ID,Team1_ID,
                    1,Team1_players.get(0),0,0,0,0,"not out" );

            oc.updateDB(Batsman1_Insert);
            //System.out.println(Batsman1_Insert);

            String Batsman2_Insert =  String.format("INSERT INTO BATTING_CARD VALUES (%d,%d,%d,%d,%d,%d,%d,%d,'%s')",
                    selected_Match_ID,Team1_ID,
                    1,Team1_players.get(1),0,0,0,0,"not out");

            oc.updateDB(Batsman2_Insert);
            //System.out.println(Batsman2_Insert);




            //The selected Match Has started Successfully
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText("Match Start Sucessful");
                alert.setContentText("The Match has Started Successfully");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            } catch (Exception exc) {
                exc.printStackTrace();
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

    }

    public void Back_Button_Clicked(ActionEvent actionEvent) {
        if (Main.Admin_Running) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Admin_Window.fxml"));
                Parent root2 = loader.load();
                Admin_Window_Controller controller = loader.getController();
                controller.Admin_Label.setText("USERNAME: " + Main.Current_Admin_Name);
                Main.stage.setTitle("Admin Window");
                Main.stage.setScene(new Scene(root2, 809, 645));
                Main.stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (Main.Viewer_Running) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Viewer_Window.fxml"));
                Parent root2 = loader.load();
                Viewer_Window_Controller controller = loader.getController();
                Main.stage.setTitle("Viewer Window");
                Main.stage.setScene(new Scene(root2, 611, 628));
                Main.stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}




