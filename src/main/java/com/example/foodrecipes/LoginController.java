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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class LoginController {

    static Boolean test = false;
    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    private Label wrongLogin;

    @FXML
    void btnSign(ActionEvent event) throws InterruptedException {
        if (!tfUsername.getText().isBlank() && !tfPassword.getText().isBlank()) {
            validateLogin(tfUsername.getText(), tfPassword.getText());
        } else {
            wrongLogin.setText("Please enter username and password");
        }
    }

    void validateLogin(String username, String password) {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE username =  '" + username + "' AND password = '"
                + password + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    wrongLogin.setText("Sign in Successful");
                    wrongLogin.setStyle("-fx-text-fill: green");
                    test = true;
                } else {
                    wrongLogin.setText("Wrong Name or Password!!");
                    wrongLogin.setStyle("-fx-text-fill: red");
                    test = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

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
