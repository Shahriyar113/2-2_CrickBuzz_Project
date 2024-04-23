package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class Tour_Details_Show_Window_Controller {

    @FXML
    Button Back_Button;
    @FXML
    Button See_Result_Button;
    @FXML
    TableView<Match> Tournament_Match_List_Tableview;
    @FXML
    TableColumn<Match,String> Match_No_Col;
    @FXML
    TableColumn<Match,String> Match_Date_Col;
    @FXML
    TableColumn<Match,String> Stadium_Col;
    @FXML
    TableColumn<Match,String> Status_Col;
    @FXML
    Label Tour_Name_Label;

    Tour Selected_Tour;

    public int current_tournament_ID;

    public ObservableList<Match> Tournament_Match_List = FXCollections.observableArrayList();


    public void init(Tour tour)
    {
        Selected_Tour=tour;
        Tour_Name_Label.setText(tour.getTournament_Title());


        //Collecting the tournament ID

        int selected_tournament_ID=0;

        OracleConnect oc=null;
        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TOURNAMENT_ID FROM TOURNAMENTS WHERE TOURNAMENT_TITLE = "+"'"+tour.getTournament_Title()+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                selected_tournament_ID = rs.getInt("TOURNAMENT_ID");
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

        current_tournament_ID =selected_tournament_ID;




        //finding the matches for this "selected_tournament_ID"
        String Match_no="";
        String Date="";
        String Stadium="";
        String status = "";


        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT M.FORMAT AS FORMAT,TO_CHAR(M.MATCH_DATE,'DD-MON-YYYY') AS MATCH_DATE , S.STADIUM_NAME" +
                    " AS STADIUM,M.STATUS AS STATUS FROM MATCHES M JOIN STADIUMS S on(M.STADIUM_ID = S.STADIUM_ID)" +
                    " WHERE TOURNAMENT_ID = "+"'"+selected_tournament_ID+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next())
            {
                Match_no = rs.getString("FORMAT");
                Date = rs.getString("MATCH_DATE");
                Stadium =rs.getString("STADIUM");
                status = rs.getString("STATUS");

                Tournament_Match_List.add(new Match(Match_no,Date,Stadium,status));


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


        Match_No_Col.setCellValueFactory(new PropertyValueFactory<>("Match_No"));
        Match_Date_Col.setCellValueFactory(new PropertyValueFactory<>("Stadium_Name"));
        Stadium_Col.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Status_Col.setCellValueFactory(new PropertyValueFactory<>("Status"));

        Tournament_Match_List_Tableview.setItems(Tournament_Match_List);


    }



    public void See_Result_Button_Clicked(ActionEvent actionEvent)
    {
        Match selected_Match = (Match)Tournament_Match_List_Tableview.getSelectionModel().getSelectedItem();

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

        //If the match is recent or LIVE then the result will be shown

        //collected Selected_Match_ID
        OracleConnect oc=null;
        int Selected_Match_ID =0;
        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT MATCH_ID FROM MATCHES WHERE " +
                    "TOURNAMENT_ID = "+current_tournament_ID +" and " +
                    "FORMAT = "+"'"+selected_Match.getMatch_No()+"'";
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

            controller.Tour_Name_Label.setText(Tour_Name_Label.getText());
            controller.Match_No_Label.setText(selected_Match.getMatch_No());

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



    public void Back_Button_Clicked(ActionEvent actionEvent)
    {
        if(Main.Admin_Running)
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



        if(Main.Viewer_Running) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Tour_Search_List_Window.fxml"));
                Parent root2 = loader.load();
                Tour_Search_List_Window_Controller controller=loader.getController();
                Main.stage.setTitle("Browse A Tour");
                Main.stage.setScene(new Scene(root2, 904, 633));
                Main.stage.show();

            }catch (IOException e)
            {
                e.printStackTrace();
            }




        }


    }


}
