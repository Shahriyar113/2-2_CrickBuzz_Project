package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Viewer_Window_Controller {

    @FXML
    Button Back_Button;
    @FXML
    Button Ranking_Button;
    @FXML
    Button Browse_A_Tour_Button;
    @FXML
    Button Browse_A_Team_Button;
    @FXML
    Button Browse_A_Player_Button;
    @FXML
    Button Browse_Matches_Button;



    public void Back_Button_Pressed(ActionEvent actionEvent)
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
            //System.out.println(selected);
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



    public void Browse_A_Tour_Button_Clicked(ActionEvent actionEvent)
    {
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

    public void Browse_Matches_Button_Clicked(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Browse_Matches_Window.fxml"));
            Parent root2 = loader.load();
            Browse_Matches_Window_Controller controller=loader.getController();
            Main.stage.setTitle("Browse Matches");
            Main.stage.setScene(new Scene(root2, 904, 633));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }











}
