package com.example.foodrecipes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecipeApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(570);

        primaryStage.setTitle("FoodRecipes");
        primaryStage.setScene(new Scene(root, 800, 570));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}