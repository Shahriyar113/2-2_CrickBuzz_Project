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

public class Create_Account_Window_Controller {

    @FXML
    private TextField Create_User_Textfield;

    @FXML
    private PasswordField Create_Password_Textfield;

    @FXML
    private PasswordField Confirm_Password_Textfield;

    @FXML
    private Button Reset_Button;

    @FXML
    private Button Back_button;
    @FXML
    private Button Create_button;


    public void initialize()
    {
        Create_User_Textfield.setText(null);
        Create_Password_Textfield.setText(null);
        Confirm_Password_Textfield.setText(null);

    }


    @FXML
    void ResetAction(ActionEvent event) {
        Create_User_Textfield.setText(null);
        Create_Password_Textfield.setText(null);
        Confirm_Password_Textfield.setText(null);

    }


    public void Back_button_pressed(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Admin_Login.fxml"));
            Parent root2 = loader.load();
            Admin_Login_Controller controller=loader.getController();
            Main.stage.setTitle("Admin Login Page");
            Main.stage.setScene(new Scene(root2, 549, 250));
            Main.stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }



    }



    public void Create_button_pressed(ActionEvent actionEvent)
    {
        //First ei check kore nicchi j kono textfield jeno empty na hoy
        if(Create_User_Textfield.getText()==null || Create_User_Textfield.getText().equals(""))
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Creating Account Status");
                alert.setHeaderText("Creating Account Failed");
                alert.setContentText("Please Enter a Valid Username");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    ;
                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }


        if(Create_Password_Textfield.getText()==null || Create_Password_Textfield.getText().equals(""))
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Creating Account Status");
                alert.setHeaderText("Creating Account Failed");
                alert.setContentText("Please Enter a Valid Password");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    ;
                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }


        if(Confirm_Password_Textfield.getText()==null || Confirm_Password_Textfield.getText().equals(""))
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Creating Account Status");
                alert.setHeaderText("Creating Account Failed");
                alert.setContentText("Please confirm your Password");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    ;
                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
            return;
        }


        //if created password and confirmed password don't match

        if(!Create_Password_Textfield.getText().equals(Confirm_Password_Textfield.getText()))
        {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Creating Account Status");
                alert.setHeaderText("Creating Account Failed");
                alert.setContentText("Wrong Password Input.Try Again");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    ;
                }
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }

            //clearing both the password field
            Create_Password_Textfield.setText(null);
            Confirm_Password_Textfield.setText(null);

            return;

        }

        //ai prjnto asha mane shobkisu ok


        OracleConnect oc = null;
        try {
            //inserting New admin into databae
            oc = new OracleConnect();
            //Checking if the input username already exists or not

            String query = String.format("select * from ADMIN where NAME = '%s'", Create_User_Textfield.getText() );
            ResultSet rs = oc.searchDB(query);

            if (rs.next()) {
                //System.out.println("Username already exisits");
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Creating Account Status");
                    alert.setHeaderText("Username Already Exists");
                    alert.setContentText("Try A Unique Username");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        ;
                    }
                }catch (Exception exc)
                {
                    exc.printStackTrace();
                }

                //clearing the usertext field
                Create_User_Textfield.setText(null);


            } else {
                String new_username=Create_User_Textfield.getText();
                String new_password=Create_Password_Textfield.getText();

                String insertQuery = String.format(
                        "INSERT INTO ADMIN VALUES (ADMIN_ID_SEQ.NEXTVAL ,'%s','%s',%d)",new_username ,new_password,-1);
                oc.updateDB(insertQuery);


                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Creating Account Status");
                    alert.setHeaderText("Created Account Successfully");
                    alert.setContentText("Now You are a new Admin!");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        ;
                    }
                }catch (Exception exc)
                {
                    exc.printStackTrace();
                }

                //clearing all the text field
                Create_User_Textfield.setText(null);
                Create_Password_Textfield.setText(null);
                Confirm_Password_Textfield.setText(null);


            }
        } catch (Exception e) {
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
