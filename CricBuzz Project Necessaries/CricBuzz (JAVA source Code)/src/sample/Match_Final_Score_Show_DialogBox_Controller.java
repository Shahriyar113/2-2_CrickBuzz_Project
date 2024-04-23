package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.util.Optional;

public class Match_Final_Score_Show_DialogBox_Controller {

    @FXML
    Label Tour_Name_Label;
    @FXML
    Label Match_No_Label;
    @FXML
    Label Team1_Name_Label;
    @FXML
    Label Team1_Score_Label;
    @FXML
    Label Team1_Over_Label;
    @FXML
    Label Team1_Extras_Label;
    @FXML
    Label Team2_Name_Label;
    @FXML
    Label Team2_Score_Label;
    @FXML
    Label Team2_Over_Label;
    @FXML
    Label Team2_Extras_Label;
    @FXML
    Label Result_Label;

    @FXML
    Button First_Inn_Batting_Card_Button;
    @FXML
    Button First_Inn_Bowling_Card_Button;
    @FXML
    Button Second_Inn_Batting_Card_Button;
    @FXML
    Button Second_Inn_Bowling_Card_Button;

    int Current_Match_ID;
    int Current_Team1_ID;
    int Current_Team2_ID;



    //For my Application
    //1st innings batting = Team1
    //1st innings bowling = Team2
    //2nd innings batting = Team2;
    //2nd innings bowling = Team1


    public void First_Inn_Batting_Card_Button_Clicked(ActionEvent actionEvent)
    {

        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(Main.stage);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Batting_Card_DialogBox.fxml"));
            DialogPane root = loader.load();
            Batting_Card_DialogBox_Controller controller = loader.getController();
            controller.init(Current_Match_ID,Current_Team1_ID,1);

            controller.Team_Name_Label.setText(Team1_Name_Label.getText());
            controller.Extra_Label.setText(Team1_Extras_Label.getText());
            controller.Over_Label.setText(Team1_Over_Label.getText());
            controller.Score_Label.setText("SCORE:  "+Team1_Score_Label.getText());


            dialog.setDialogPane(root);
            dialog.setTitle("Batting_Card");

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get() == ButtonType.CLOSE) {

                dialog.close();

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    public void First_Inn_Bowling_Card_Button_Clicked(ActionEvent actionEvent)
    {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(Main.stage);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Bowling_Card_DialogBox.fxml"));
            DialogPane root = loader.load();
            Bowling_Card_DialogBox_Controller controller = loader.getController();
            controller.init(Current_Match_ID,Current_Team2_ID,1);

            controller.Team_Name_Label.setText(Team2_Name_Label.getText());

            controller.Extra_Label.setText(Team1_Extras_Label.getText());
            controller.Over_Label.setText(Team1_Over_Label.getText());
            controller.Score_Label.setText("SCORE:  "+Team1_Score_Label.getText());


            dialog.setDialogPane(root);
            dialog.setTitle("Bowling_Card");

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get() == ButtonType.CLOSE) {

                dialog.close();

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }





    }

    public void Second_Inn_Batting_Card_Button_Clicked(ActionEvent actionEvent)
    {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(Main.stage);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Batting_Card_DialogBox.fxml"));
            DialogPane root = loader.load();
            Batting_Card_DialogBox_Controller controller = loader.getController();
            controller.init(Current_Match_ID,Current_Team2_ID,2);

            controller.Team_Name_Label.setText(Team2_Name_Label.getText());
            controller.Extra_Label.setText(Team2_Extras_Label.getText());
            controller.Over_Label.setText(Team2_Over_Label.getText());
            controller.Score_Label.setText("SCORE:  "+Team2_Score_Label.getText());


            dialog.setDialogPane(root);
            dialog.setTitle("Batting_Card");

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get() == ButtonType.CLOSE) {

                dialog.close();

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    public void Second_Inn_Bowling_Card_Button_Clicked(ActionEvent actionEvent)
    {

        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(Main.stage);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Bowling_Card_DialogBox.fxml"));
            DialogPane root = loader.load();
            Bowling_Card_DialogBox_Controller controller = loader.getController();
            controller.init(Current_Match_ID,Current_Team1_ID,2);

            controller.Team_Name_Label.setText(Team1_Name_Label.getText());

            controller.Extra_Label.setText(Team2_Extras_Label.getText());
            controller.Over_Label.setText(Team2_Over_Label.getText());
            controller.Score_Label.setText("SCORE:  "+Team2_Score_Label.getText());


            dialog.setDialogPane(root);
            dialog.setTitle("Bowling_Card");

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
