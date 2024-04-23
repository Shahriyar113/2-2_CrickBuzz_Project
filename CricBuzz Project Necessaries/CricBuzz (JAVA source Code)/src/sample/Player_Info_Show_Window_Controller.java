package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;

public class Player_Info_Show_Window_Controller {

    @FXML
    public Label Player_Name_Label;
    @FXML
    public Label Country_Label;
    @FXML
    public Label Age_Label;
    @FXML
    public Label Role_Label;
    @FXML
    public Label Match_Played_Label;

    @FXML
    public Button Batting_Stats_Button;
    @FXML
    public Button Bowling_Stats_Button;

    public String Current_Player_Name;


    public void Batting_Stats_Button_Clicked(ActionEvent event)
    {

        if(Role_Label.getText().equals("BATSMAN") || Role_Label.getText().equals("ALL ROUNDER"))
        {

            OracleConnect oc = null;
            String Batting_Style="";
            int Batting_Rank=0;
            int Hundreds=0;
            int Fifties=0;
            int Best=0;

            try {
                //Retrieving PLAYER's BOWLING STATS info from database
                oc = new OracleConnect(); //Connecting with oracle database
                String query = "SELECT BATTING_STYLE,BATTING_RANK,HUNDREDS,FIFTIES,BEST " +
                        "FROM BATSMANS WHERE PLAYER_NAME = "+"'"+Current_Player_Name+"'";
                ResultSet rs = oc.searchDB(query);
                while (rs.next()) {
                    Batting_Style = rs.getString("BATTING_STYLE");
                    Batting_Rank = rs.getInt("BATTING_RANK");
                    Hundreds = rs.getInt("HUNDREDS");
                    Fifties=rs.getInt("FIFTIES");
                    Best=rs.getInt("BEST");

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

            //Showing Batting States dialog pane


            try {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(Main.stage);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Batting_Stats_DialogBox.fxml"));
                DialogPane root = loader.load();
                Batting_Stats_DialogBox_Controller controller = loader.getController();

                //putting all stats values from Database
                controller.Batting_Style_Label.setText(Batting_Style);
                controller.Batting_Rank_Label.setText(Integer.toString(Batting_Rank));
                controller.Total_Hundreds_Label.setText(Integer.toString(Hundreds));
                controller.Total_Fifties_Label.setText(Integer.toString(Fifties));
                controller.Best_Score_Label.setText(Integer.toString(Best));

                dialog.setDialogPane(root);
                dialog.setTitle("Batting Statistics");

                Optional<ButtonType> result = dialog.showAndWait();

                if (result.get() == ButtonType.CLOSE) {

                    dialog.close();

                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }



        }

        // if BOWLER
        else
        {
            try {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(Main.stage);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Batting_Stats_DialogBox.fxml"));
                DialogPane root = loader.load();
                Batting_Stats_DialogBox_Controller controller = loader.getController();


                dialog.setDialogPane(root);
                dialog.setTitle("Batting Statistics");

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

    public void Bowling_Stats_Button_Clicked(ActionEvent event)
    {
        if(Role_Label.getText().equals("BOWLER") || Role_Label.getText().equals("ALL ROUNDER"))
        {

            OracleConnect oc = null;
            String Bowling_Style="";
            int Bowling_Rank=0;
            double Economy=0.0;
            int Wickets=0;
            String Best_Figure="";

            try {
                //Retrieving PLAYERS BATTING STATS info from database
                oc = new OracleConnect(); //Connecting with oracle database
                String query = "SELECT BOWLING_STYLE,BOWLING_RANK,ECONOMY,WICKETS,BEST_FIGURE" +
                        " FROM BOWLERS WHERE PLAYER_NAME = "+"'"+Current_Player_Name+"'";
                ResultSet rs = oc.searchDB(query);
                while (rs.next()) {
                    Bowling_Style = rs.getString("BOWLING_STYLE");
                    Bowling_Rank = rs.getInt("BOWLING_RANK");
                    Economy = rs.getDouble("ECONOMY");
                    Wickets=rs.getInt("WICKETS");
                    Best_Figure=rs.getString("BEST_FIGURE");

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

            //Showing Batting States dialog pane


            try {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(Main.stage);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Bowling_Stats_DialogBox.fxml"));
                DialogPane root = loader.load();
                Bowling_Stats_DialogBox_Controller controller = loader.getController();

                //putting all stats values from Database
                controller.Bowling_Style_Label.setText(Bowling_Style);
                controller.Bowling_Rank_Label.setText(Integer.toString(Bowling_Rank));
                controller.Economy_Label.setText(Double.toString(Economy));
                controller.Total_Wickets_Label.setText(Integer.toString(Wickets));
                controller.Best_Figure_Label.setText(Best_Figure);

                dialog.setDialogPane(root);
                dialog.setTitle("Bowling Statistics");

                Optional<ButtonType> result = dialog.showAndWait();

                if (result.get() == ButtonType.CLOSE) {

                    dialog.close();

                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }



        }

        // if BATSMAN
        else
        {
            try {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(Main.stage);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Bowling_Stats_DialogBox.fxml"));
                DialogPane root = loader.load();
                Bowling_Stats_DialogBox_Controller controller = loader.getController();


                dialog.setDialogPane(root);
                dialog.setTitle("Bowling Statistics");

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

    public void Back_Button_Clicked(ActionEvent event)
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










}
