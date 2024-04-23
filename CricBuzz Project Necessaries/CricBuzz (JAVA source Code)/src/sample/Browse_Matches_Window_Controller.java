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
import java.util.Optional;

public class Browse_Matches_Window_Controller {


    @FXML
    Button Back_Button;

    @FXML
    Button See_Result_Button;

    @FXML
    public TextField Match_Search_Textfield;
    @FXML
    public TableView<Browse_Match> All_Matches_Tableview;
    @FXML
    public TableColumn<Browse_Match,String> Match_Title_Col;
    @FXML
    public TableColumn<Browse_Match,String> Format_Col;
    @FXML

    public TableColumn<Browse_Match,String> Date_Col;
    @FXML

    public TableColumn<Browse_Match,String> Time_Col;

    @FXML
    public TableColumn<Browse_Match,String> Stadium_Col;
    @FXML
    public TableColumn<Browse_Match,String> Status_Col;
    @FXML
    public TableColumn<Browse_Match,String> Tournament_Title_Col;

    public ObservableList<Browse_Match> All_Matches_List = FXCollections.observableArrayList();


    public void initialize()
    {

        OracleConnect oc = null;
        String match_title="";
        String format="";
        String date="";
        String time="";
        String stadium="";
        String status="";
        String tournament_title="";

        String date_and_time="";



        try {
            //Retrieving ALL PLAYERS info from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT M.MATCH_TITLE,M.FORMAT,TO_CHAR(M.MATCH_DATE,'YYYY-MM-DD HH:MIAM') " +
                    "AS MATCH_DATE_AND_TIME,S.STADIUM_NAME,M.STATUS,T.TOURNAMENT_TITLE\n" +
                    "FROM MATCHES M JOIN STADIUMS S on (M.STADIUM_ID = S.STADIUM_ID) JOIN\n" +
                    "TOURNAMENTS T on (M.TOURNAMENT_ID = T.TOURNAMENT_ID) ORDER BY MATCH_DATE";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                match_title = rs.getString("MATCH_TITLE");
                format = rs.getString("FORMAT");
                date_and_time = rs.getString("MATCH_DATE_AND_TIME");
                stadium = rs.getString("STADIUM_NAME");
                status = rs.getString("STATUS");
                tournament_title=rs.getString("TOURNAMENT_TITLE");

                String[] match_date_and_Time = date_and_time.split(" ");
                date = match_date_and_Time[0];
                time=match_date_and_Time[1];

                All_Matches_List.add(new Browse_Match(match_title,format,date,time,stadium,status,tournament_title));


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




        Match_Title_Col.setCellValueFactory(new PropertyValueFactory<>("Match_Title"));
        Format_Col.setCellValueFactory(new PropertyValueFactory<>("Format"));
        Date_Col.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Time_Col.setCellValueFactory(new PropertyValueFactory<>("Time"));
        Stadium_Col.setCellValueFactory(new PropertyValueFactory<>("Stadium"));
        Status_Col.setCellValueFactory(new PropertyValueFactory<>("Status"));
        Tournament_Title_Col.setCellValueFactory(new PropertyValueFactory<>("Tournament_Title"));



        All_Matches_Tableview.setItems(All_Matches_List);

        FilteredList<Browse_Match> filteredData = new FilteredList<>(All_Matches_List, b->true);

        Match_Search_Textfield.textProperty().addListener((observabe,oldValue,newValue) ->{
            filteredData.setPredicate(browse_match -> {

                //if the searched key is not found,then the table view will be empty
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                    return true;

                String searched_keyword = newValue.toLowerCase();

                if(browse_match.getMatch_Title().toLowerCase().startsWith(searched_keyword))
                    return true;
                else
                    return false;
            });
        });

        //SortedList<Tour> sortedData = new SortedList<>(filteredData);
        //sortedData.comparatorProperty().bind(Tour_Search_list_tableview.comparatorProperty());
        All_Matches_Tableview.setItems(filteredData);



    }




    public void Back_Button_Pressed(ActionEvent actionEvent)
    {

        if(Main.Admin_Running)
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



        if(Main.Viewer_Running) {
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

    }

    public void See_Result_Button_Clicked(ActionEvent actionEvent)
    {

        Browse_Match selected_Match = (Browse_Match) All_Matches_Tableview.getSelectionModel().getSelectedItem();

        if(selected_Match == null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Nothing is selected");
                alert.setContentText("Please Select a Match");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }



        if(selected_Match.getStatus().equalsIgnoreCase("Upcoming"))
        {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText("The match is Upcoming");
                alert.setContentText("The Match has not start yeat");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        //if LIVE OR RECENT match is selected

        OracleConnect oc=null;
        int Selected_Match_ID =0;
        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String date_and_time = selected_Match.getDate() + " " + selected_Match.getTime();

            String query = "SELECT MATCH_ID\n" +
                    "FROM MATCHES\n" +
                    "WHERE MATCH_TITLE ='"+selected_Match.getMatch_Title()+"' and" +
                    " MATCH_DATE = TO_DATE('"+date_and_time+"','YYYY-MM-DD HH:MIAM') ";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Selected_Match_ID = rs.getInt("MATCH_ID");
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

        //Collecting the result for this "selected_Match_ID"
        int Team1_ID=0;
        String Team1_Name ="";
        int Team1_Score = 0;
        int Team1_Wickets = 0;
        double Team1_OverPlayed = 0;
        int Team1_Extras = 0;

        int Team2_ID=0;
        String Team2_Name ="";
        int Team2_Score = 0;
        int Team2_Wickets = 0;
        double Team2_OverPlayed = 0;
        int Team2_Extras = 0;
        String Match_Result="";


        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT T1.TEAM_ID AS TEAM1_ID,T1.TEAM_NAME AS TEAM1_NAME,F.TEAM1_SCORE,F.TEAM1_WICKETS," +
                    "F.TEAM1_OVER_PLAYED,F.TEAM1_EXTRAS,T2.TEAM_ID AS TEAM2_ID,T2.TEAM_NAME AS TEAM2_NAME," +
                    "F.TEAM2_SCORE,F.TEAM2_WICKETS,F.TEAM2_OVER_PLAYED,F.TEAM2_EXTRAS,F.RESULT\n" +
                    "FROM FINAL_SCORES F JOIN TEAMS T1 on(F.TEAM1_ID = T1.TEAM_ID) JOIN " +
                    "TEAMS T2 on(F.TEAM2_ID = T2.TEAM_ID)\n" +
                    "WHERE F.MATCH_ID = "+Selected_Match_ID;
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Team1_ID = rs.getInt("TEAM1_ID");
                Team1_Name = rs.getString("TEAM1_NAME");
                Team1_Score=rs.getInt("TEAM1_SCORE");
                Team1_Wickets = rs.getInt("TEAM1_WICKETS");
                Team1_OverPlayed = rs.getDouble("TEAM1_OVER_PLAYED");
                Team1_Extras = rs.getInt("TEAM1_EXTRAS");

                Team2_ID = rs.getInt("TEAM2_ID");
                Team2_Name = rs.getString("TEAM2_NAME");
                Team2_Score=rs.getInt("TEAM2_SCORE");
                Team2_Wickets = rs.getInt("TEAM2_WICKETS");
                Team2_OverPlayed = rs.getDouble("TEAM2_OVER_PLAYED");
                Team2_Extras = rs.getInt("TEAM2_EXTRAS");

                Match_Result=rs.getString("RESULT");

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



        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(Main.stage);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Match_Final_Score_Show_DialogBox.fxml"));
            DialogPane root = loader.load();
            Match_Final_Score_Show_DialogBox_Controller controller = loader.getController();


            controller.Current_Match_ID=Selected_Match_ID;
            controller.Current_Team1_ID=Team1_ID;
            controller.Current_Team2_ID=Team2_ID;

            controller.Tour_Name_Label.setText(selected_Match.getTournament_Title());
            controller.Match_No_Label.setText(selected_Match.getFormat());

            controller.Team1_Name_Label.setText(Team1_Name+":");
            controller.Team1_Score_Label.setText(Integer.toString(Team1_Score)+"/"+Integer.toString(Team1_Wickets));
            controller.Team1_Over_Label.setText("OVER: "+Double.toString(Team1_OverPlayed));
            controller.Team1_Extras_Label.setText("EXTRAS: "+Integer.toString(Team1_Extras));

            controller.Team2_Name_Label.setText(Team2_Name+":");
            controller.Team2_Score_Label.setText(Integer.toString(Team2_Score)+"/"+Integer.toString(Team2_Wickets));
            controller.Team2_Over_Label.setText("OVER: "+Double.toString(Team2_OverPlayed));
            controller.Team2_Extras_Label.setText("EXTRAS: "+Integer.toString(Team2_Extras));

            if(Match_Result!=null) {
                if (!Match_Result.equalsIgnoreCase("null"))
                    controller.Result_Label.setText(Match_Result);
            }

            dialog.setDialogPane(root);
            dialog.setTitle("Match_Result");

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get() == ButtonType.CLOSE) {

                dialog.close();

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }



}
