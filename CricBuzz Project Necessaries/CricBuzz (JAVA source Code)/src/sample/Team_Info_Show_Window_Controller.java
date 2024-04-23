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

public class Team_Info_Show_Window_Controller {

    @FXML
    public Label Team_Name_Label;
    @FXML
    public Label Head_Coach_Label;
    @FXML
    public Label Board_President_Label;
    @FXML
    public Label ODI_Rank_Label;
    @FXML
    public Label T20_Rank_Label;
    @FXML
    public Label Test_Rank_Label;

    @FXML
    public Button Back_Button;
    @FXML
    public Button Team_Stats_Button;

    public String current_selected_team;



    public void Back_Button_Clicked(ActionEvent actionEvent)
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



    public void Team_Stats_Button_Clicked(ActionEvent actionEvent)
    {
        OracleConnect oc = null;
        int ODI_Match_Played = 0;
        int ODI_Won = 0;
        int ODI_Lost = 0;
        int ODI_Drawn = 0;

        int T20_Match_Played = 0;
        int T20_Won = 0;
        int T20_Lost = 0;
        int T20_Drawn = 0;

        int Test_Match_Played = 0;
        int Test_Won = 0;
        int Test_Lost = 0;
        int Test_Drawn = 0;

        try {
            //Retrieving all The statistics of the "current_selected_team" from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TOTAL_ODI_PLAYED,TOTAL_ODI_WON,TOTAL_ODI_LOST,TOTAL_ODI_DRAWN," +
                    "TOTAL_T20_PLAYED,TOTAL_T20_WON,TOTAL_T20_LOST,TOTAL_T20_DRAWN," +
                    "TOTAL_TEST_PLAYED,TOTAL_TEST_WON,TOTAL_TEST_LOST,TOTAL_TEST_DRAWN" +
                    " FROM TEAMS WHERE TEAM_NAME = "+"'"+current_selected_team+"'";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {

               ODI_Match_Played = rs.getInt("TOTAL_ODI_PLAYED");
               ODI_Won = rs.getInt("TOTAL_ODI_WON");
               ODI_Lost = rs.getInt("TOTAL_ODI_LOST");
               ODI_Drawn = rs.getInt("TOTAL_ODI_DRAWN");

               T20_Match_Played = rs.getInt("TOTAL_T20_PLAYED");
               T20_Won = rs.getInt("TOTAL_T20_WON");
               T20_Lost = rs.getInt("TOTAL_T20_LOST");
               T20_Drawn = rs.getInt("TOTAL_T20_DRAWN");

               Test_Match_Played = rs.getInt("TOTAL_TEST_PLAYED");
               Test_Won = rs.getInt("TOTAL_TEST_WON");
               Test_Lost = rs.getInt("TOTAL_TEST_LOST");
               Test_Drawn = rs.getInt("TOTAL_TEST_DRAWN");




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
            loader.setLocation(getClass().getResource("Team_Stats_DialogBox.fxml"));
            DialogPane root = loader.load();
            Team_Stats_DialogBox_Controller controller = loader.getController();

            //putting all stats values from Database

            controller.ODI_Match_Played_Label.setText(Integer.toString(ODI_Match_Played));
            controller.ODI_Won_Label.setText(Integer.toString(ODI_Won));
            controller.ODI_Lost_Label.setText(Integer.toString(ODI_Lost));
            controller.ODI_Drawn_Label.setText(Integer.toString(ODI_Drawn));

            controller.T20_Match_Played_Label.setText(Integer.toString(T20_Match_Played));
            controller.T20_Won_Label.setText(Integer.toString(T20_Won));
            controller.T20_Lost_Label.setText(Integer.toString(T20_Lost));
            controller.T20_Drawn_Label.setText(Integer.toString(T20_Drawn));

            controller.Test_Match_Played_Label.setText(Integer.toString(Test_Match_Played));
            controller.Test_Won_Label.setText(Integer.toString(Test_Won));
            controller.Test_Lost_Label.setText(Integer.toString(Test_Lost));
            controller.Test_Drawn_Label.setText(Integer.toString(Test_Drawn));


            dialog.setDialogPane(root);
            dialog.setTitle("Team Statistics");

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
