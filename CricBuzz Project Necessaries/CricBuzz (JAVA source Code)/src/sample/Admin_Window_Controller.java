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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Admin_Window_Controller {

    @FXML
    Label Admin_Label;
    @FXML
    Button Log_Out_Button;
    @FXML
    Button Ranking_Button;
    @FXML
    Button Browse_A_Team_Button;
    @FXML
    Button Browse_A_Player_Button;
    @FXML
    Button Create_A_Tour_Button;
    @FXML
    Button Schedule_A_Tour_Button;
    @FXML
    Button Browse_Matches_Button;
    @FXML
    Button Start_A_Match_Button;
    @FXML
    Button Notification_Button;

    int Notified_Match_ID = 0;
    String Notified_Match_Format;
    String Notified_Match_Tournament_Title;


    public void initialize()
    {
        OracleConnect oc=null;

        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT MATCH_ID FROM ADMIN WHERE ID = "+Main.Current_Admin_id;

            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Notified_Match_ID = rs.getInt("MATCH_ID");
            }

            if(Notified_Match_ID == -1)
            {
                return;
            }

            String Notified_Match_Status ="";
            String query2 = "SELECT T.TOURNAMENT_TITLE,M.FORMAT,M.STATUS" +
                    " FROM MATCHES M JOIN TOURNAMENTS T on(M.TOURNAMENT_ID=T.TOURNAMENT_ID)" +
                    " WHERE MATCH_ID = "+Notified_Match_ID;

            ResultSet rs2 = oc.searchDB(query2);
            while (rs2.next()) {
                Notified_Match_Tournament_Title=rs2.getString("TOURNAMENT_TITLE");
                Notified_Match_Format = rs2.getString("FORMAT");
                Notified_Match_Status = rs2.getString("STATUS");
            }

            if(Notified_Match_Status.equalsIgnoreCase("LIVE"))
            {
                Notification_Button.setDisable(false);
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
    }




    public void Log_Out_Button_Clicked(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Before_Login.fxml"));
            Parent root2 = loader.load();
            Before_Login_Controller controller=loader.getController();
            Main.stage.setTitle("Before Login Page");
            Main.stage.setScene(new Scene(root2, 800, 485));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public void Ranking_Button_Clicked(ActionEvent actionEvent)
    {
        String [] Rank_Format_choices = {"ODI RANKING", "T20 RANKING", "TEST RANKING"};
        List<String> dialogData;

        dialogData = Arrays.asList(Rank_Format_choices);

        Dialog Ranking_dialog = new ChoiceDialog(dialogData.get(0), dialogData);
        Ranking_dialog.setHeaderText("SELECT A FORMAT");
        Ranking_dialog.setTitle("Ranking");

        Optional<String> result = Ranking_dialog.showAndWait();
        String selected = "cancelled";

        if (result.isPresent()) {
            selected = result.get();
        }

        if(selected.equals("ODI RANKING"))
        {
            ObservableList<Ranking> Rank_list = FXCollections.observableArrayList();

            OracleConnect oc = null;
            int Rank=0;
            String Team_name=null;
            try {
                //Retrieving Ranking info from database
                oc = new OracleConnect(); //Connecting with oracle database
                String query = "SELECT ODI_RANK,TEAM_NAME FROM TEAMS ORDER BY ODI_RANK";
                ResultSet rs = oc.searchDB(query);
                while (rs.next()) {
                    Rank = rs.getInt("ODI_RANK");
                    Team_name = rs.getString("TEAM_NAME");

                    Rank_list.add(new Ranking(Rank,Team_name));

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
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Ranking_Window.fxml"));
                Parent root2 = loader.load();
                Ranking_Window_Controller controller=loader.getController();
                controller.Ranking_Label.setText("ODI RANKING");
                controller.load_the_Ranking_table(Rank_list);
                Main.stage.setTitle("ODI Ranking");
                Main.stage.setScene(new Scene(root2, 322, 549));
                Main.stage.show();

            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(selected.equals("T20 RANKING"))
        {

            ObservableList<Ranking> Rank_list = FXCollections.observableArrayList();

            OracleConnect oc = null;
            int Rank=0;
            String Team_name=null;
            try {
                //Retrieving Ranking info from database
                oc = new OracleConnect(); //Connecting with oracle database
                String query = "SELECT T20_RANK,TEAM_NAME FROM TEAMS ORDER BY T20_RANK";
                ResultSet rs = oc.searchDB(query);
                while (rs.next()) {
                    Rank = rs.getInt("T20_RANK");
                    Team_name = rs.getString("TEAM_NAME");

                    Rank_list.add(new Ranking(Rank,Team_name));

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
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Ranking_Window.fxml"));
                Parent root2 = loader.load();
                Ranking_Window_Controller controller=loader.getController();
                controller.Ranking_Label.setText("T20 RANKING");
                controller.load_the_Ranking_table(Rank_list);
                Main.stage.setTitle("T20 Ranking");
                Main.stage.setScene(new Scene(root2, 322, 549));
                Main.stage.show();

            }catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        else if(selected.equals("TEST RANKING"))
        {

            ObservableList<Ranking> Rank_list = FXCollections.observableArrayList();

            OracleConnect oc = null;
            int Rank=0;
            String Team_name=null;
            try {
                //Retrieving Ranking info from database
                oc = new OracleConnect(); //Connecting with oracle database
                String query = "SELECT TEST_RANK,TEAM_NAME FROM TEAMS ORDER BY TEST_RANK";
                ResultSet rs = oc.searchDB(query);
                while (rs.next()) {
                    Rank = rs.getInt("TEST_RANK");
                    Team_name = rs.getString("TEAM_NAME");

                    Rank_list.add(new Ranking(Rank,Team_name));

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
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Ranking_Window.fxml"));
                Parent root2 = loader.load();
                Ranking_Window_Controller controller=loader.getController();
                controller.Ranking_Label.setText("TEST RANKING");
                controller.load_the_Ranking_table(Rank_list);
                Main.stage.setTitle("Test Ranking");
                Main.stage.setScene(new Scene(root2, 322, 549));
                Main.stage.show();

            }catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        else {
            System.out.println(selected);
        }

    }


    public void Browse_A_Team_Button_Clicked(ActionEvent actionEvent)
    {

        List<String> Choice_Team_list=new ArrayList<String>();

        OracleConnect oc = null;
        String Team_name=null;
        try {
            //Retrieving ALL the team name info from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TEAM_NAME FROM TEAMS ORDER BY TEAM_NAME";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Team_name = rs.getString("TEAM_NAME");

                Choice_Team_list.add(Team_name);

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


        Dialog Ranking_dialog = new ChoiceDialog(Choice_Team_list.get(0), Choice_Team_list);
        Ranking_dialog.setHeaderText("SELECT A TEAM");
        Ranking_dialog.setTitle("TEAM SELECTION");

        Optional<String> result = Ranking_dialog.showAndWait();
        String selected_team = "cancelled";

        //if "CANCEL" button clicked then result.isPresent() returns "false"
        //if "OK" if "CANCEL" button clicked then result.isPresent() returns "trur" and result.get()
        //returns the "selected choice"
        if (result.isPresent()) {
            selected_team = result.get();
        }

        if(selected_team.equals("cancelled"))
        {
            return;
        }



        //"selected_team" is storing the current selected team

        oc = null;
        Team_name=null;
        String Head_Coach_name="";
        String Board_President_name="";
        int ODI_Rank=0;
        int T20_Rank=0;
        int Test_Rank=0;
        try {
            //Retrieving ALL the info of "selected team" info from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TEAM_NAME,HEAD_COACH,BOARD_PRESIDENT,ODI_RANK,T20_RANK,TEST_RANK" +
                    " FROM TEAMS WHERE TEAM_NAME = "+"'"+selected_team+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Team_name = rs.getString("TEAM_NAME");
                Head_Coach_name = rs.getString("HEAD_COACH");
                Board_President_name = rs.getString("BOARD_PRESIDENT");
                ODI_Rank = rs.getInt("ODI_RANK");
                T20_Rank = rs.getInt("T20_RANK");
                Test_Rank = rs.getInt("TEST_RANK");

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

        //After selecting a team "Team Info" will be shown
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Team_Info_Show_Window.fxml"));
            Parent root2 = loader.load();
            Team_Info_Show_Window_Controller controller=loader.getController();

            controller.current_selected_team = Team_name;

            controller.Team_Name_Label.setText(Team_name);
            controller.Head_Coach_Label.setText(Head_Coach_name);
            controller.Board_President_Label.setText(Board_President_name);
            controller.ODI_Rank_Label.setText(Integer.toString(ODI_Rank));
            controller.T20_Rank_Label.setText(Integer.toString(T20_Rank));
            controller.Test_Rank_Label.setText(Integer.toString(Test_Rank));

            Main.stage.setTitle("Team Info");
            Main.stage.setScene(new Scene(root2, 686, 499));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    public void Browse_A_Player_Button_Clicked(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Player_Search_List_Window.fxml"));
            Parent root2 = loader.load();
            Player_Search_List_Window_Controller controller=loader.getController();
            Main.stage.setTitle("Browse A Player");
            Main.stage.setScene(new Scene(root2, 680, 633));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void Create_A_Tour_Button_Clicked(ActionEvent actionEvent) //creating = scheduling
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Schedule_A_Tour_Window.fxml"));
            Parent root2 = loader.load();
            Schedule_A_Tour_Window_Controller controller=loader.getController();
            Main.stage.setTitle("Schedule A Tour");
            Main.stage.setScene(new Scene(root2, 801, 547));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void Schedule_A_Tour_Button_Clicked(ActionEvent actionEvent) //Scheduling = updating
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


    public void Browse_Matches_Button_Clicked(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Browse_Matches_Window_Admin.fxml"));
            Parent root2 = loader.load();
            Browse_Matches_Window_Admin_Controller controller=loader.getController();
            Main.stage.setTitle("Browse Matches");
            Main.stage.setScene(new Scene(root2, 904, 633));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Start_A_Match_Button_Clicked(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Start_A_Match_Window.fxml"));
            Parent root2 = loader.load();
            Start_A_Match_Window_Controller controller=loader.getController();
            Main.stage.setTitle("Start A Match");
            Main.stage.setScene(new Scene(root2, 904, 633));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Notification_Button_Clicked(ActionEvent actionEvent)
    {

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


        OracleConnect oc = null;
        try {
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT T1.TEAM_ID AS TEAM1_ID,T1.TEAM_NAME AS TEAM1_NAME,F.TEAM1_SCORE,F.TEAM1_WICKETS," +
                    "F.TEAM1_OVER_PLAYED,F.TEAM1_EXTRAS,T2.TEAM_ID AS TEAM2_ID,T2.TEAM_NAME AS TEAM2_NAME," +
                    "F.TEAM2_SCORE,F.TEAM2_WICKETS,F.TEAM2_OVER_PLAYED,F.TEAM2_EXTRAS,F.RESULT\n" +
                    "FROM FINAL_SCORES F JOIN TEAMS T1 on(F.TEAM1_ID = T1.TEAM_ID) JOIN " +
                    "TEAMS T2 on(F.TEAM2_ID = T2.TEAM_ID)\n" +
                    "WHERE F.MATCH_ID = "+Notified_Match_ID;
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


            controller.Current_Match_ID=Notified_Match_ID;
            controller.Current_Team1_ID=Team1_ID;
            controller.Current_Team2_ID=Team2_ID;

            controller.Tour_Name_Label.setText(Notified_Match_Tournament_Title);
            controller.Match_No_Label.setText(Notified_Match_Format);

            controller.Team1_Name_Label.setText(Team1_Name+":");
            controller.Team1_Score_Label.setText(Integer.toString(Team1_Score)+"/"+Integer.toString(Team1_Wickets));
            controller.Team1_Over_Label.setText("OVER: "+Double.toString(Team1_OverPlayed));
            controller.Team1_Extras_Label.setText("EXTRAS: "+Integer.toString(Team1_Extras));

            controller.Team2_Name_Label.setText(Team2_Name+":");
            controller.Team2_Score_Label.setText(Integer.toString(Team2_Score)+"/"+Integer.toString(Team2_Wickets));
            controller.Team2_Over_Label.setText("OVER: "+Double.toString(Team2_OverPlayed));
            controller.Team2_Extras_Label.setText("EXTRAS: "+Integer.toString(Team2_Extras));


            if(Match_Result!=null)
            {
                if(!Match_Result.equalsIgnoreCase("null"))
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
