package com.example.foodrecipes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class testApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(testApp.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 540);
        stage.setTitle("Tree Table View!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}