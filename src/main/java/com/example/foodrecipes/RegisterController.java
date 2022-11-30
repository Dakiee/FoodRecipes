package com.example.foodrecipes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     void signUpEvent(ActionEvent event) throws InterruptedException {
        if(tfUsername.getText().isEmpty() || setPasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()){
            lblRegistrationMessage.setStyle("-fx-text-fill: red");
            lblRegistrationMessage.setText("Please enter username and password");
        }
        else if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            lblIsValid.setText("");
//            closeStage(event);
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

        if(!isPasswordValid(password)){
            Date date = new Date();
            String selectFavId = "SELECT favId FROM favorites WHERE dates = '" + date + "';";
            String addFavorites = "INSERT INTO `favorites`( `dates`, `recipeIds`) VALUES ('"+ date +"',' ')";
            int favId = -1;
            try{
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(addFavorites);
                ResultSet queryResult = statement.executeQuery(selectFavId);
                while (queryResult.next()) {
                    favId = queryResult.getInt(1);
                }
                String insertFields = "INSERT INTO users(username, PASSWORD,favId) VALUES ('";
                String insertValues = username + "','" + password + "','" + favId +"');";
                String insertToRegister = insertFields + insertValues;
                statement.executeUpdate(insertToRegister);
                lblRegistrationMessage.setText("Register Successful");
            }catch(Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }else{
            lblRegistrationMessage.setText("Password Not Valid");
        }
    }

    boolean isPasswordValid(String password){
        String regex = "^(?=.*[0-9])\"\n" +
                " + \"(?=.*[a-z])(?=.*[A-Z])\"\n" +
                " + \"(?=.*[@#$%^&+=])\"\n" +
                "  + \"(?=\\\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
