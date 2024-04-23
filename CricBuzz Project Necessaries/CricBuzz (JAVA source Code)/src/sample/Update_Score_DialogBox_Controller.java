package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Update_Score_DialogBox_Controller {

    @FXML
    Label Team_Name_Label;
    @FXML
    ComboBox<String> Select_Batsman_ComboBox;
    @FXML
    ComboBox<String> Select_Bowler_ComboBox;
    @FXML
    ComboBox<String> Wicket_ComboBox;
    @FXML
    ComboBox<Integer> Run_Increment_ComboBox;
    @FXML
    ComboBox<Integer> Bowl_Increment_ComboBox;
    @FXML
    ComboBox<Integer> Extra_Increment_ComboBox;


    @FXML
    Button Update_Button;

    int Selected_Match_id;
    int Team1_id;
    String Team1_name;
    String Team2_name;
    int Team2_id;
    int Current_Batting_Team_ID;
    int Current_Bowling_Team_ID;
    String Current_Batting_Team_name;
    int Current_Innings;

    int selected_Batsman_id;
    int selected_Bowler_id ;

    ObservableList<String> Selected_Batsman_List = FXCollections.observableArrayList();
    ObservableList<String> Selected_Bolwer_List = FXCollections.observableArrayList();
    ObservableList<String> Wicket_comboList = FXCollections.observableArrayList();

    ObservableList<Integer> Run_Increment_List = FXCollections.observableArrayList();
    ObservableList<Integer> Bowl_Increment_List = FXCollections.observableArrayList();
    ObservableList<Integer> Extra_Increment_List = FXCollections.observableArrayList();




    public void init(int match_id)
    {
        Selected_Match_id =match_id;

        OracleConnect oc=null;

        try {
            oc = new OracleConnect(); //Connecting with oracle database


            String query = "SELECT FS.TEAM1_ID,T1.TEAM_NAME AS TEAM1_NAME" +
                    ",T2.TEAM_NAME AS TEAM2_NAME,FS.TEAM2_ID,FS.CURR_BATTING_TEAM_ID," +
                    "T3.TEAM_NAME AS CURR_BATTING_TEAM \n" +
                    "FROM FINAL_SCORES FS JOIN TEAMS T1 on (FS.TEAM1_ID = T1.TEAM_ID) " +
                    "JOIN TEAMS T2 on (FS.TEAM2_ID = T2.TEAM_ID)JOIN TEAMS T3 on" +
                    " (FS.CURR_BATTING_TEAM_ID = T3.TEAM_ID)\n" +
                    "WHERE MATCH_ID = "+Selected_Match_id;
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Team1_id = rs.getInt("TEAM1_ID");
                Team2_id = rs.getInt("TEAM2_ID");
                Team1_name =rs.getString("TEAM1_NAME");
                Team2_name =rs.getString("TEAM2_NAME");
                Current_Batting_Team_ID = rs.getInt("CURR_BATTING_TEAM_ID");
                Current_Batting_Team_name = rs.getString("CURR_BATTING_TEAM");
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


        if(Team1_id == Current_Batting_Team_ID)
        {
            Current_Innings = 1;
            Current_Bowling_Team_ID = Team2_id;
        }

        if(Team2_id == Current_Batting_Team_ID)
        {
            Current_Innings = 2;
            Current_Bowling_Team_ID = Team1_id;
        }




        Team_Name_Label.setText(Current_Batting_Team_name+" BATTING");

        Run_Increment_List.add(0);
        Run_Increment_List.add(1);
        Run_Increment_List.add(2);
        Run_Increment_List.add(3);
        Run_Increment_List.add(4);
        Run_Increment_List.add(5);
        Run_Increment_List.add(6);

        Run_Increment_ComboBox.setItems(Run_Increment_List);
        Run_Increment_ComboBox.getSelectionModel().select(0);


        Bowl_Increment_List.add(0);
        Bowl_Increment_List.add(1);

        Bowl_Increment_ComboBox.setItems(Bowl_Increment_List);
        Bowl_Increment_ComboBox.getSelectionModel().select(0);

        Extra_Increment_List.add(0);
        Extra_Increment_List.add(1);
        Extra_Increment_List.add(2);
        Extra_Increment_List.add(3);
        Extra_Increment_List.add(4);
        Extra_Increment_List.add(5);

        Extra_Increment_ComboBox.setItems(Extra_Increment_List);
        Extra_Increment_ComboBox.getSelectionModel().select(0);

        Wicket_comboList.add("NO WICKET");
        Wicket_comboList.add("WICKET");

        Wicket_ComboBox.setItems(Wicket_comboList);
        Wicket_ComboBox.getSelectionModel().select(0);




        //Finding Current Batting Pair

        try {
            oc = new OracleConnect(); //Connecting with oracle database


            String query = "SELECT P.PLAYER_NAME\n" +
                    "FROM BATTING_CARD B JOIN PLAYERS P on (B.BATSMAN_ID = P.PLAYER_ID)\n" +
                    "WHERE B.MATCH_ID = "+Selected_Match_id +" and" +
                    " B.TEAM_ID ="+Current_Batting_Team_ID +" and" +
                    " B.INNINGS = "+ Current_Innings +" and B.STATUS = 'not out'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Selected_Batsman_List.add(rs.getString("PLAYER_NAME"));
            }

            String query2 = "SELECT P.PLAYER_NAME\n" +
                    "FROM PLAYING_XI PXI JOIN PLAYERS P on (PXI.PLAYER_ID = P.PLAYER_ID) \n" +
                    "WHERE PXI.MATCH_ID = "+Selected_Match_id+" and " +
                    "PXI.TEAM_ID = "+Current_Bowling_Team_ID +" and " +
                    "(P.ROLE = 'BOWLER' or  P.ROLE = 'ALL ROUNDER')";
            ResultSet rs2 = oc.searchDB(query2);
            while (rs2.next()) {
                Selected_Bolwer_List.add(rs2.getString("PLAYER_NAME"));
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

        Select_Batsman_ComboBox.setItems(Selected_Batsman_List);
        Select_Bowler_ComboBox.setItems(Selected_Bolwer_List);


    }

    public void Update_Button_Clicked(ActionEvent actionEvent)
    {
        String Selected_Batsman = Select_Batsman_ComboBox.getSelectionModel().getSelectedItem();
        if(Selected_Batsman == null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Batsman Selection Error");
                alert.setContentText("Please Select a Batsman");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        String Selected_Bowler = Select_Bowler_ComboBox.getSelectionModel().getSelectedItem();
        if(Selected_Bowler == null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Bowler Selection Error");
                alert.setContentText("Please Select a Bowler");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }

        int Run_increment = Run_Increment_ComboBox.getSelectionModel().getSelectedItem();
        int Bowl_increment = Bowl_Increment_ComboBox.getSelectionModel().getSelectedItem();
        int Extra_increment = Extra_Increment_ComboBox.getSelectionModel().getSelectedItem();


        //Finding selected batsman id and bowler id

        OracleConnect oc=null;


        try {
            oc = new OracleConnect(); //Connecting with oracle database


            String query = "SELECT PLAYER_ID FROM PLAYERS WHERE PLAYER_NAME = '"+Selected_Batsman+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {

                selected_Batsman_id = rs.getInt("PLAYER_ID");
            }

            String query2 = "SELECT PLAYER_ID FROM PLAYERS WHERE PLAYER_NAME = '"+Selected_Bowler+"'";
            ResultSet rs2 = oc.searchDB(query2);
            while (rs2.next()) {

                selected_Bowler_id = rs2.getInt("PLAYER_ID");

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

        //Updating Score to database



        //Batting Card Update
        try {
            oc = new OracleConnect(); //Connecting with oracle database


            String Run_Update = "UPDATE BATTING_CARD SET SCORED_RUN = SCORED_RUN + "+Run_increment+" WHERE" +
                    " MATCH_ID = "+Selected_Match_id+" and TEAM_ID = "+Current_Batting_Team_ID+" and " +
                    "INNINGS = "+Current_Innings+" and BATSMAN_ID = "+selected_Batsman_id;

            oc.updateDB(Run_Update);
            //System.out.println(Run_Update);

            String Bowl_Faced_Update = "UPDATE BATTING_CARD SET BOWL_FACED = BOWL_FACED + "+Bowl_increment+" WHERE" +
                    " MATCH_ID = "+Selected_Match_id+" and TEAM_ID = "+Current_Batting_Team_ID+" and " +
                    "INNINGS = "+Current_Innings+" and BATSMAN_ID = "+selected_Batsman_id;

            oc.updateDB(Bowl_Faced_Update);
            //System.out.println(Bowl_Faced_Update);

            String Four_Update="";
            String Six_Update="";

            if(Run_increment == 4)
            {
                Four_Update = "UPDATE BATTING_CARD SET FOURS = FOURS + 1 WHERE" +
                        " MATCH_ID = "+Selected_Match_id+" and TEAM_ID = "+Current_Batting_Team_ID+" and " +
                        "INNINGS = "+Current_Innings+" and BATSMAN_ID = "+selected_Batsman_id;

                oc.updateDB(Four_Update);
                //System.out.println(Four_Update);
            }

            if(Run_increment == 6)
            {
                Six_Update = "UPDATE BATTING_CARD SET SIXES = SIXES + 1 WHERE" +
                        " MATCH_ID = "+Selected_Match_id+" and TEAM_ID = "+Current_Batting_Team_ID+" and " +
                        "INNINGS = "+Current_Innings+" and BATSMAN_ID = "+selected_Batsman_id;
                oc.updateDB(Six_Update);
                //System.out.println(Six_Update);
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


        //Bowling Card Update

        try {
            oc = new OracleConnect(); //Connecting with oracle database

            //at first checking if the bowler is in the database or not
            //if not then insert the bowler
            int Count =-1;

            String query = "SELECT COUNT(BOWLER_ID) AS" +
                    " COUNT FROM BOWLING_CARD WHERE " +
                    "MATCH_ID ="+Selected_Match_id+" and BOWLER_ID ="+selected_Bowler_id;
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Count = rs.getInt("COUNT");
            }

            if(Count == 0)
            {
                //insert the bowler to database

                String Inserting_New_Bolwer_Into_Batting_Card =
                        String.format("INSERT INTO BOWLING_CARD VALUES (%d,%d,%d,%d,%d,%d,%f,%d)",
                                Selected_Match_id,Current_Bowling_Team_ID,
                                Current_Innings,selected_Bowler_id,0,0,0.0,0);

                oc.updateDB(Inserting_New_Bolwer_Into_Batting_Card);
                //System.out.println(Inserting_New_Bolwer_Into_Batting_Card);


            }




            String Run_Given_Update =  "UPDATE BOWLING_CARD SET RUN_GIVEN = RUN_GIVEN + "+Run_increment+" WHERE" +
                    " MATCH_ID = "+Selected_Match_id+" and TEAM_ID = "+Current_Bowling_Team_ID+" and " +
                    "INNINGS = "+Current_Innings+" and BOWLER_ID = "+selected_Bowler_id;
            oc.updateDB(Run_Given_Update);
            //System.out.println(Run_Given_Update);


            String Over_Bowled_Update ="UPDATE BOWLING_CARD SET OVER_BOWLED = OVER_BOWLED + "+Bowl_increment/10.0+" WHERE" +
                    " MATCH_ID = "+Selected_Match_id+" and TEAM_ID = "+Current_Bowling_Team_ID+" and " +
                    "INNINGS = "+Current_Innings+" and BOWLER_ID = "+selected_Bowler_id;

            oc.updateDB(Over_Bowled_Update);
            //System.out.println(Over_Bowled_Update);




        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //Final Score Card Update

        try {
            oc = new OracleConnect(); //Connecting with oracle database

            String Total_Score_Update ="";
            String Total_Over_Update ="";
            String Total_Extra_Update="";
            String Result_Update=" ";

            if(Current_Innings == 1)
            {
                Total_Score_Update = "UPDATE FINAL_SCORES SET TEAM1_SCORE = TEAM1_SCORE + "+(Run_increment+Extra_increment)+" WHERE " +
                        "MATCH_ID = "+Selected_Match_id;

                oc.updateDB(Total_Score_Update);
                //System.out.println(Total_Score_Update);



                Total_Over_Update = "UPDATE FINAL_SCORES SET TEAM1_OVER_PLAYED = TEAM1_OVER_PLAYED" +
                        " + "+Bowl_increment/10.0+" WHERE " +
                        "MATCH_ID = "+Selected_Match_id;

                oc.updateDB(Total_Over_Update);
                //System.out.println(Total_Over_Update);

                Total_Extra_Update = "UPDATE FINAL_SCORES SET TEAM1_EXTRAS = TEAM1_EXTRAS" +
                        " + "+Extra_increment+" WHERE " +
                        "MATCH_ID = "+Selected_Match_id;

                oc.updateDB(Total_Extra_Update);
                //System.out.println(Total_Extra_Update);

                Result_Update = "UPDATE FINAL_SCORES SET RESULT = NULL WHERE MATCH_ID = "+Selected_Match_id;

                oc.updateDB(Result_Update);
                //System.out.println(Result_Update);



            }

            if(Current_Innings == 2)
            {
                Total_Score_Update = "UPDATE FINAL_SCORES SET TEAM2_SCORE = TEAM2_SCORE + "+(Run_increment+Extra_increment)+" WHERE " +
                        "MATCH_ID = "+Selected_Match_id;

                oc.updateDB(Total_Score_Update);
                //System.out.println(Total_Score_Update);


                Total_Over_Update = "UPDATE FINAL_SCORES SET TEAM2_OVER_PLAYED = TEAM2_OVER_PLAYED" +
                        " + "+Bowl_increment/10.0+" WHERE " +
                        "MATCH_ID = "+Selected_Match_id;

                oc.updateDB(Total_Over_Update);
                //System.out.println(Total_Over_Update);


                Total_Extra_Update = "UPDATE FINAL_SCORES SET TEAM2_EXTRAS = TEAM2_EXTRAS" +
                        " + "+Extra_increment+" WHERE " +
                        "MATCH_ID = "+Selected_Match_id;

                oc.updateDB(Total_Extra_Update);
                //System.out.println(Total_Extra_Update);


                String Result ="";

                String query = "SELECT RESULT FROM FINAL_SCORES WHERE MATCH_ID = "+Selected_Match_id;
                ResultSet rs = oc.searchDB(query);
                while (rs.next()) {
                    Result = rs.getString("RESULT");
                }

                String[] Results = Result.split(" ");
                int Run_Remaining = Integer.parseInt(Results[2]);
                int Ball_Remaining = Integer.parseInt(Results[7]);

                String result = Team2_name +" NEEDS "+(Run_Remaining-Run_increment-Extra_increment)+" RUNS TO WIN IN "
                        +(Ball_Remaining-Bowl_increment)+" BALLS";

                Result_Update = "UPDATE FINAL_SCORES SET RESULT = '"+result +"' WHERE " +
                        "MATCH_ID = "+Selected_Match_id;

                oc.updateDB(Result_Update);
                //System.out.println(Result_Update);



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


        //if wicket is fallen
        if(Wicket_ComboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("WICKET"))
        {

            System.out.println("WICKET FALLLS");
            try {
                oc = new OracleConnect(); //Connecting with oracle database

                String Update_batting_status ="UPDATE BATTING_CARD SET STATUS ='out' WHERE " +
                        "MATCH_ID ="+Selected_Match_id+" and BATSMAN_ID ="+selected_Batsman_id;

                oc.updateDB(Update_batting_status);
                //System.out.println(Update_batting_status);

                //Finding Next Batsman


                int Next_Batsman_ID = 0;
                String Next_Batsman_Name="";

                String query = "SELECT E.PLAYER_ID,P.PLAYER_NAME FROM ((SELECT PLAYER_ID FROM" +
                        " PLAYING_XI WHERE MATCH_ID = "+Selected_Match_id+" and" +
                        " TEAM_ID = "+Current_Batting_Team_ID+") MINUS" +
                        " (SELECT BATSMAN_ID FROM BATTING_CARD WHERE " +
                        "MATCH_ID = "+Selected_Match_id+" and " +
                        "TEAM_ID = "+Current_Batting_Team_ID+")) E JOIN PLAYERS " +
                        "P on (E.PLAYER_ID = P.PLAYER_ID) ";
                ResultSet rs = oc.searchDB(query);
                while (rs.next()) {
                    Next_Batsman_ID = rs.getInt("PLAYER_ID");
                    Next_Batsman_Name = rs.getString("PLAYER_NAME");
                    break;
                }


                String Inserting_New_Batsman_Into_Batting_Card =
                        String.format("INSERT INTO BATTING_CARD VALUES (%d,%d,%d,%d,%d,%d,%d,%d,'%s')",
                                Selected_Match_id,Current_Batting_Team_ID,
                                Current_Innings,Next_Batsman_ID,0,0,0,0,"not out" );

                oc.updateDB(Inserting_New_Batsman_Into_Batting_Card);
                //System.out.println(Inserting_New_Batsman_Into_Batting_Card);


                Selected_Batsman_List.remove(Select_Batsman_ComboBox.getSelectionModel().getSelectedIndex());
                Selected_Batsman_List.add(Next_Batsman_Name);
                Select_Batsman_ComboBox.setItems(Selected_Batsman_List);
                Select_Batsman_ComboBox.getSelectionModel().select(1);



                String Bowler_Wicket_Update = "UPDATE BOWLING_CARD" +
                        " SET WICKETS = WICKETS + 1 WHERE" +
                        " MATCH_ID = "+Selected_Match_id+" and BOWLER_ID = "+selected_Bowler_id;

                oc.updateDB(Bowler_Wicket_Update);
                //System.out.println(Bowler_Wicket_Update);


                String Final_Score_Wicket_Update="";

                if(Current_Innings == 1)
                {
                    Final_Score_Wicket_Update = "UPDATE FINAL_SCORES SET " +
                            "TEAM1_WICKETS = TEAM1_WICKETS + 1 WHERE MATCH_ID = "+Selected_Match_id;

                }
                if(Current_Innings == 2)
                {
                    Final_Score_Wicket_Update = "UPDATE FINAL_SCORES SET " +
                            "TEAM2_WICKETS = TEAM2_WICKETS + 1 WHERE MATCH_ID = "+Selected_Match_id;

                }

                oc.updateDB(Final_Score_Wicket_Update);
                //System.out.println(Final_Score_Wicket_Update);


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





        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Score Updated Successfully");
            alert.setContentText("Press OK to continue");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

            }
        }catch (Exception exc)
        {
            exc.printStackTrace();
        }







    }















}
