package com.example.foodrecipes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class RecipeApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(570);
        primaryStage.getIcons()
                .add(new Image(Objects.requireNonNull(getClass().getResource("img/favicon.png")).openStream()));

        primaryStage.setTitle("FoodRecipes");
        primaryStage.setScene(new Scene(root, 800, 570));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}