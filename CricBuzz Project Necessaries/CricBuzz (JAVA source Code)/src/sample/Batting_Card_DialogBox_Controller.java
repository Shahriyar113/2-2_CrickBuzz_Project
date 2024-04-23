package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;

public class Batting_Card_DialogBox_Controller {

    @FXML
    Label Team_Name_Label;

    @FXML
    TableView<Batsman> Batting_Card_Tableview;

    @FXML
    TableColumn<Batsman,String> Batsman_Col;
    @FXML
    TableColumn<Batsman,String> Status_Col;
    @FXML
    TableColumn<Batsman,Integer> Fours_Col;
    @FXML
    TableColumn<Batsman,Integer> Sixes_Col;
    @FXML
    TableColumn<Batsman,Integer> Run_Col;
    @FXML
    TableColumn<Batsman,Integer> Bowl_Faced_Col;

    @FXML
    Label Extra_Label;
    @FXML
    Label Over_Label;
    @FXML
    Label Score_Label;

    ObservableList<Batsman> Batsman_List =FXCollections.observableArrayList();



    public void init(int Match_ID,int Team_ID,int Innings_No)
    {

        OracleConnect oc=null;

        String batsman_Name="";
        String status="";
        int fours = 0;
        int sixes = 0;
        int Run = 0;
        int bowl_faced=0;


        try {

            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT P.PLAYER_NAME,B.STATUS,B.FOURS,B.SIXES,B.SCORED_RUN,B.BOWL_FACED\n" +
                    "FROM BATTING_CARD B JOIN PLAYERS P on (B.BATSMAN_ID = P.PLAYER_ID) \n" +
                    "WHERE B.MATCH_ID = "+Match_ID +" and B.TEAM_ID = "+ Team_ID+" and B.INNINGS = "+Innings_No;
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                batsman_Name = rs.getString("PLAYER_NAME");
                status = rs.getString("STATUS");
                fours = rs.getInt("FOURS");
                sixes = rs.getInt("SIXES");
                Run = rs.getInt("SCORED_RUN");
                bowl_faced = rs.getInt("BOWL_FACED");

                Batsman_List.add(new Batsman(batsman_Name,status,fours,sixes,Run,bowl_faced));
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

        Batsman_Col.setCellValueFactory(new PropertyValueFactory<>("Batsman_Name"));
        Status_Col.setCellValueFactory(new PropertyValueFactory<>("Status"));
        Fours_Col.setCellValueFactory(new PropertyValueFactory<>("Fours"));
        Sixes_Col.setCellValueFactory(new PropertyValueFactory<>("Sixes"));
        Run_Col.setCellValueFactory(new PropertyValueFactory<>("Run_Scored"));
        Bowl_Faced_Col.setCellValueFactory(new PropertyValueFactory<>("Bowl_Faced"));

        Batting_Card_Tableview.setItems(Batsman_List);


    }








}
