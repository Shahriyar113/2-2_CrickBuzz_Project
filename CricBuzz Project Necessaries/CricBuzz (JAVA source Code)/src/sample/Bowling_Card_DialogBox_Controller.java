package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;

public class Bowling_Card_DialogBox_Controller {


    @FXML
    Label Team_Name_Label;

    @FXML
    TableView<Bowler> Bowling_Card_Tableview;

    @FXML
    TableColumn<Bowler,String> Bowler_Col;
    @FXML
    TableColumn<Bowler,Double> Over_Bowled_Col;
    @FXML
    TableColumn<Bowler,Integer> Maiden_Col;
    @FXML
    TableColumn<Bowler,Integer> Wicket_Taken_Col;
    @FXML
    TableColumn<Bowler,Integer> Run_Given_Col;
    @FXML
    TableColumn<Bowler,Double> Economy_Col;

    @FXML
    Label Extra_Label;
    @FXML
    Label Over_Label;
    @FXML
    Label Score_Label;

    ObservableList<Bowler> Bowler_List = FXCollections.observableArrayList();



    public void init(int Match_ID,int Team_ID,int Innings_No)
    {
        OracleConnect oc=null;

        String bowler_Name="";
        double over_bowled=0;
        int maiden = 0;
        int wicket_taken = 0;
        int Run_given = 0;
        double economy =0.0;


        try {

            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT P.PLAYER_NAME,B.OVER_BOWLED,B.MAIDEN,B.WICKETS,B.RUN_GIVEN," +
                    "ROUND(B.RUN_GIVEN/B.OVER_BOWLED, 2) AS ECONOMY\n" +
                    "FROM BOWLING_CARD B JOIN PLAYERS P on (B.BOWLER_ID = P.PLAYER_ID) \n" +
                    "WHERE B.MATCH_ID = "+Match_ID +"and B.TEAM_ID = "+Team_ID +"and B.INNINGS = "+Innings_No;
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                bowler_Name = rs.getString("PLAYER_NAME");
                over_bowled = rs.getDouble("OVER_BOWLED");
                maiden = rs.getInt("MAIDEN");
                wicket_taken = rs.getInt("WICKETS");
                Run_given = rs.getInt("RUN_GIVEN");
                economy = rs.getDouble("ECONOMY");

                Bowler_List.add(new Bowler(bowler_Name,over_bowled,maiden,wicket_taken,Run_given,economy) );
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



        Bowler_Col.setCellValueFactory(new PropertyValueFactory<>("Bowler_Name"));
        Over_Bowled_Col.setCellValueFactory(new PropertyValueFactory<>("Over_Bowled"));
        Maiden_Col.setCellValueFactory(new PropertyValueFactory<>("Maiden"));
        Wicket_Taken_Col.setCellValueFactory(new PropertyValueFactory<>("Wicket_Taken"));
        Run_Given_Col.setCellValueFactory(new PropertyValueFactory<>("Run_Given"));
        Economy_Col.setCellValueFactory(new PropertyValueFactory<>("Economy"));


        Bowling_Card_Tableview.setItems(Bowler_List);


    }


}
