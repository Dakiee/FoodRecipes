package com.example.foodrecipes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Button btnSignin;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    private Label wrongLogin;

    public void initialize(){

    }
    @FXML
    void btnSign(ActionEvent event) {
        if(!tfUsername.getText().isBlank() && !tfPassword.getText().isBlank()) {
            validateLogin();
        } else {
            wrongLogin.setText("Please enter username and password");
        }
    }
    void validateLogin(){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE username =  '" + tfUsername.getText() + "' AND password = '" + tfPassword.getText() + "'";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    wrongLogin.setText("yes");
                }else{
                    wrongLogin.setText("nope");
                }
            }

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountForm(MouseEvent mouseEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 800, 570);
            Stage stage = new Stage();
            stage.setMinWidth(600);
            stage.setMinHeight(600);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
