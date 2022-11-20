package com.example.foodrecipes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Button btnSignin;

    @FXML
    private TextField tfPassword;

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

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username =  '" + tfUsername.getText() + "' AND password = '" + tfPassword.getText() + "'";
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

        }
    }

}
