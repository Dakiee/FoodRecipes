package com.example.foodrecipes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
/**
 * @author : Dakie
 * @version : 1.0.0
 * @date : 2022:11:20
 * @improvement : Login and register
 *
 * @version 1.0.1
 * @date : 2022:11:24
 * @improvement : Login Field fixed
 */
public class LoginController {

    static Users user;
    static Favorites favorites;
    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    private Label wrongLogin;

    @FXML
    void btnSign(ActionEvent event) {
        if (!tfUsername.getText().isBlank() && !tfPassword.getText().isBlank()) {
            validateLogin(tfUsername.getText(), tfPassword.getText());
        } else {
            wrongLogin.setText("Please enter username and password");
        }
    }
//    Enehuu method ni garaas orj irj bui username bolon password iig database dotroos shalgan garj ireh utguudad tohiroh uildluudiig hiine
    void validateLogin(String username, String password) {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT * FROM users WHERE username =  '" + username + "' AND PASSWORD = '"
                + password + "'";
        try {
            Users temp = new Users();
            temp.setUserName("");
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                temp = new Users((Integer) queryResult.getObject(1),
                                (String) queryResult.getObject(2),
                                (String) queryResult.getObject(3),
                                (Integer) queryResult.getObject(4));

            }
            if (temp.getUserName().matches(username)) {
                wrongLogin.setText("Sign in Successful");
                wrongLogin.setStyle("-fx-text-fill: green");
                System.out.println(username + " successfully logged in");
                user = temp;

                changeLoginButton();
                getFavObject();
            } else {
                wrongLogin.setText("Wrong Name or Password!!");
                wrongLogin.setStyle("-fx-text-fill: red");
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void changeLoginButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        RecipeController rc = fxmlLoader.getController();
        rc.initialize();
    }

    private void getFavObject() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT * FROM favorites WHERE fav_Id = " + user.getFavId() + ";";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                Favorites temp = new Favorites();
                temp.setId((Integer) queryResult.getObject(1));
                temp.setDate(queryResult.getObject(2).toString());
                temp.setRecipe((String) queryResult.getObject(3));
                favorites = temp;
            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
    /*
    *
    * Hervee hereglegch burtgelgui bol signup dovchiig darsanaar enehuu method deer irj register stage iig duudah bolno
     */
    public void createAccountForm(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 800, 545);
            Stage newStage = new Stage();
            newStage.getIcons()
                    .add(new Image(Objects.requireNonNull(getClass().getResource("img/favicon.png")).openStream()));
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(scene);
            newStage.showAndWait();
        } catch (Exception e) {
            System.out.println("FXML Unshihad aldaa garlaa");
            e.printStackTrace();
            e.getCause();
        }
    }
}
