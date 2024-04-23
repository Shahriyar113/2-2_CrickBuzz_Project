package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;


public class Ranking_Window_Controller {

    @FXML
    Button Back_Button;
    @FXML
    Label Ranking_Label ;
    @FXML
    TableView Ranking_TableView;

    //public ObservableList<Ranking> Rank_list;


    public void load_the_Ranking_table(ObservableList<Ranking> Rank_list)
    {
        TableColumn<Ranking, Integer> Rank_Col = new TableColumn<>("RANK");
        Rank_Col.setMinWidth(100);
        Rank_Col.setCellValueFactory(new PropertyValueFactory<>("Rank"));


        TableColumn<Ranking, String> Team_name_Col = new TableColumn<>("TEAM");
        Team_name_Col.setMinWidth(100);
        Team_name_Col.setCellValueFactory(new PropertyValueFactory<>("Team_name"));

        Rank_Col.setStyle("-fx-alignment: CENTER");
        Team_name_Col.setStyle("-fx-alignment: CENTER");

        Ranking_TableView.getColumns().addAll(Rank_Col,Team_name_Col);


        //Rank_list= FXCollections.observableArrayList();
        Ranking_TableView.setItems(Rank_list);



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







}
