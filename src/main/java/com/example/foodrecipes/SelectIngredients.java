package com.example.foodrecipes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class SelectIngredients {

    @FXML
    private FlowPane allIngredients;
    private ArrayList<Node> selected = new ArrayList<>();
    @FXML
    private Button done;

    @FXML
    void onDoneClicked(ActionEvent event) {

    }

    @FXML
    void onExitDone(MouseEvent event) {

    }

    @FXML
    void onHoverDone(MouseEvent event) {

    }
    public EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Node hbox = (Node)e.getSource();
            if (selected.contains(hbox)) {
                //System.out.println(e.getSource());
                hbox.setStyle("-fx-border:1px;-fx-border-color:lightgray;-fx-border-radius:10px;" +
                        "-fx-background-color:white;");
                selected.remove(hbox);
            } else {
                hbox.setStyle("-fx-border:1px;-fx-border-color:rgb(0, 153, 51);-fx-border-radius:10px;" +
                        "-fx-background-color:white;");
                selected.add(hbox);
            }


            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };

}
