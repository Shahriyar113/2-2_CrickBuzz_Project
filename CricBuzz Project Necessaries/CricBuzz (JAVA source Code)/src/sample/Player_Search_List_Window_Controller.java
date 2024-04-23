package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.NoSuchElementException;

public class Player_Search_List_Window_Controller {

    @FXML
    Button Back_Button;

    @FXML
    Button Browse_Button;

    @FXML
    public TextField Player_Search_Textfield;
    @FXML
    public TableView<Player> Player_Search_list_tableview;
    @FXML
    public TableColumn<Player,String> Player_Name_Col;
    @FXML

    public TableColumn<Player,String> Country_Col;
    @FXML

    public TableColumn<Player,Integer> Age_Col;


    public ObservableList<Player> All_players_list = FXCollections.observableArrayList();


    public void initialize()
    {
        OracleConnect oc = null;
        String Player_Name="";
        String Country="";
        int Age=0;

        try {
            //Retrieving ALL PLAYERS info from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT PLAYER_NAME,COUNTRY,AGE FROM PLAYERS ORDER BY PLAYER_NAME";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Player_Name = rs.getString("PLAYER_NAME");
                Country = rs.getString("COUNTRY");
                Age = rs.getInt("AGE");

                All_players_list.add(new Player(Player_Name,Country,Age));

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


        Player_Name_Col.setCellValueFactory(new PropertyValueFactory<>("Player_Name"));
        Country_Col.setCellValueFactory(new PropertyValueFactory<>("Country"));
        Age_Col.setCellValueFactory(new PropertyValueFactory<>("Age"));

        Player_Search_list_tableview.setItems(All_players_list);

        FilteredList<Player> filteredData = new FilteredList<>(All_players_list, b->true);

        Player_Search_Textfield.textProperty().addListener((observabe,oldValue,newValue) ->{
            filteredData.setPredicate(player -> {

                //if the searched key is not found,then the table view will be empty
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                    return true;

                String searched_keyword = newValue.toLowerCase();

                if(player.getPlayer_Name().toLowerCase().startsWith(searched_keyword))
                    return true;
                else
                    return false;
            });
        });

        //SortedList<Player> sortedData = new SortedList<>(filteredData);
        //sortedData.comparatorProperty().bind(Player_Search_list_tableview.comparatorProperty());
        Player_Search_list_tableview.setItems(filteredData);


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


    public void Browse_Button_Clicked(ActionEvent actionEvent)
    {
        Player player = (Player)Player_Search_list_tableview.getSelectionModel().getSelectedItem();

        if(player==null)
        {
            try {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("Selection Error");
                alert1.setContentText("Please Select A Player");

                alert1.showAndWait();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }

        }

        else
        {
            OracleConnect oc = null;
            String Player_Name = "";
            String Country = "";
            String ROLE = "";
            int Age = 0;
            int Match_Played = 0;

            try {
                //Retrieving INDIVIDUAL PLAYER info from database
                oc = new OracleConnect(); //Connecting with oracle database

                String query = "SELECT PLAYER_NAME,COUNTRY,ROLE,AGE,MATCH_PLAYED" +
                        " FROM PLAYERS WHERE PLAYER_NAME = " + "'" + player.getPlayer_Name() + "'";
                ResultSet rs = oc.searchDB(query);
                while (rs.next()) {
                    Player_Name = rs.getString("PLAYER_NAME");
                    Country = rs.getString("COUNTRY");
                    ROLE = rs.getString("ROLE");
                    Age = rs.getInt("AGE");
                    Match_Played = rs.getInt("MATCH_PLAYED");

                }

                //Showing the data on the screen

                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("Player_Info_Show_Window.fxml"));
                    Parent root2 = loader.load();
                    Player_Info_Show_Window_Controller controller = loader.getController();

                    //setting the label
                    controller.Current_Player_Name = Player_Name;

                    controller.Player_Name_Label.setText(Player_Name);
                    controller.Country_Label.setText(Country);
                    controller.Role_Label.setText(ROLE);
                    controller.Age_Label.setText(Integer.toString(Age));
                    controller.Match_Played_Label.setText(Integer.toString(Match_Played));


                    Main.stage.setTitle("Player Info");
                    Main.stage.setScene(new Scene(root2, 686, 499));
                    Main.stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
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

    }




}
