package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;

public class Tour_Search_List_Window_Controller {

    @FXML
    Button Back_Button;

    @FXML
    Button View_Details_Button;

    @FXML
    public TextField Tour_Search_Textfield;
    @FXML
    public TableView<Tour> Tour_Search_list_tableview;
    @FXML
    public TableColumn<Tour,String> Tour_Title_Col;
    @FXML
    public TableColumn<Tour,String> Start_Date_Col;
    @FXML

    public TableColumn<Tour,String> End_Date_Col;
    @FXML

    public TableColumn<Tour,Integer> Status_Col;

    @FXML
    public TableColumn<Tour,String> Host_Col;

    public ObservableList<Tour> Tournament_List = FXCollections.observableArrayList();

    public void initialize()
    {

        OracleConnect oc = null;
        String TornamentTitle="";
        String StartDate="";
        String EndDate="";
        String status="";
        String host="";

        try {
            //Retrieving ALL PLAYERS info from database
            oc = new OracleConnect(); //Connecting with oracle database
            String query = "SELECT TOURNAMENT_TITLE,TO_CHAR(START_DATE,'DD-MON-YYYY')" +
                    " AS START_DATE,TO_CHAR(END_DATE,'DD-MON-YYYY') AS END_DATE,STATUS,HOST_COUNTRY " +
                    "FROM TOURNAMENTS T ORDER BY T.START_DATE\n";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                TornamentTitle = rs.getString("TOURNAMENT_TITLE");
                StartDate = rs.getString("START_DATE");
                EndDate = rs.getString("END_DATE");
                status = rs.getString("STATUS");
                host = rs.getString("HOST_COUNTRY");

                Tournament_List.add(new Tour(TornamentTitle,StartDate,EndDate,status,host));


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



        Tour_Title_Col.setCellValueFactory(new PropertyValueFactory<>("Tournament_Title"));
        Start_Date_Col.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        End_Date_Col.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
        Status_Col.setCellValueFactory(new PropertyValueFactory<>("Status"));
        Host_Col.setCellValueFactory(new PropertyValueFactory<>("Host"));


        Tour_Search_list_tableview.setItems(Tournament_List);

        FilteredList<Tour> filteredData = new FilteredList<>(Tournament_List, b->true);

        Tour_Search_Textfield.textProperty().addListener((observabe,oldValue,newValue) ->{
            filteredData.setPredicate(tour -> {

                //if the searched key is not found,then the table view will be empty
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                    return true;

                String searched_keyword = newValue.toLowerCase();

                if(tour.getTournament_Title().toLowerCase().startsWith(searched_keyword))
                    return true;
                else
                    return false;
            });
        });

        //SortedList<Tour> sortedData = new SortedList<>(filteredData);
        //sortedData.comparatorProperty().bind(Tour_Search_list_tableview.comparatorProperty());
        Tour_Search_list_tableview.setItems(filteredData);


    }

    public void View_Details_Button_Clicked(ActionEvent actionEvent)
    {
        Tour selected_Tour = (Tour)Tour_Search_list_tableview.getSelectionModel().getSelectedItem();

        if(selected_Tour == null)
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Nothing is selected");
                alert.setContentText("Please Select a Tour");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;

        }

        //showing the Tour_Details_Window

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Tour_Details_Show_Window.fxml"));
            Parent root2 = loader.load();
            Tour_Details_Show_Window_Controller controller=loader.getController();
            controller.init(selected_Tour);
            Main.stage.setTitle("Admin Window");
            Main.stage.setScene(new Scene(root2, 886, 402));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }



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
