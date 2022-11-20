package com.example.foodrecipes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private Button add;

    @FXML
    void addIngredient(ActionEvent event) throws IOException {
        ArrayList<Node> selected = onOpenDialog();
        System.out.println(selected);

    }
    @FXML
    private ArrayList<Node> onOpenDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addIngredients.fxml"));
        Parent parent = fxmlLoader.load();
        SelectIngredients dialogController = fxmlLoader.getController();
        // dialogController.setAppMainObservableList(tvObservableList);


        dialogController.FillIngredients();

        Scene scene = new Scene(parent, 700, 570);
        Stage stage = new Stage();
        stage.setMinWidth(600);
        stage.setMinHeight(600);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        //return parent.getChildrenUnmodifiable().toString();

        EventHandler handler = dialogController.getEventHandler();
        //

        ArrayList<Node> temporary = new ArrayList<>(dialogController.getSelected());
        for (Node ing : temporary) {
            ing.removeEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        }
        return temporary;
    }
}
