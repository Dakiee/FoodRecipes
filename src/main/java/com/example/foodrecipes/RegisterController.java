package com.example.foodrecipes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController{

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField tfUsername;
    @FXML
    private Label lblIsValid;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private Label lblRegistrationMessage;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    void signUpEvent(ActionEvent event) {
        if(tfUsername.getText().isEmpty() || setPasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()){
            lblRegistrationMessage.setStyle("-fx-text-fill: red");
            lblRegistrationMessage.setText("Please enter username and password");
        }
        else if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            lblIsValid.setText("");
            closeStage(event);
        }
        else{
            lblIsValid.setText("Password does not match");
        }
    }

    public void registerUser(){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String username = tfUsername.getText();
        String password = setPasswordField.getText();

        String insertFields = "INSERT INTO users(username, PASSWORD) VALUES ('";
        String insertValues = username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            lblRegistrationMessage.setText("Success modofaka");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
