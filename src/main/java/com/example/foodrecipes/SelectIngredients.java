package com.example.foodrecipes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectIngredients {

    @FXML
    private FlowPane allIngredients;
    private ArrayList<Node> selected = new ArrayList<>();
    @FXML
    private Button done;

    @FXML
    void onDoneClicked(ActionEvent event) {
        if (done.getText().contentEquals("Done")) {
            closeStage(event);
        }
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onExitDone(MouseEvent event) {
        if (done.getText().contentEquals("Done")) {
            done.setStyle("-fx-background-color: #3ACECE;-fx-border-radius:25px;");

        }
    }

    @FXML
    void onHoverDone(MouseEvent event) {
        if (done.getText().contentEquals("Done")) {
            done.setStyle("-fx-background-color: #0062cc;-fx-border-radius:25px;");
        }

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
                hbox.setStyle("-fx-border:1px;-fx-border-color: #E58412;-fx-border-radius:10px;" +
                        "-fx-background-color:white;");
                selected.add(hbox);
            }


            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };
    public EventHandler getEventHandler(){
        return eventHandler;
    }
    @FXML
    public void FillIngredients() throws FileNotFoundException {
        ArrayList<String> ingredients;
        //        ingredients.add("BBQ sause, bacon, basil, beans, beef, berry, broth, brussels sprouts, buckwheat, butter, cabbage, capers, carrot, cheese, chicken, chickpeas, chocolate, cocoa, couscous, cucumber, dill, dough, eggs, fish, flour, garlic, ginger, gnocchi, ham, kale, lemon, lentils, milk, mustard, noodles, nuts, olives, onion, parsley, parsnips, pasta, pastry, peanut, peas, pepper, potatoes, quinoa, rice, salmon, sardine, sausage, soy sauce, starch, sugar, toast, tomato, tuna, yeast, yoghurt");
        String str ="BBQ sause, bacon, basil, beans, beef, berry, broth, brussels sprouts, buckwheat, butter, cabbage, capers, carrot, cheese, chicken, chickpeas, chocolate, cocoa, couscous, cucumber, dill, dough, eggs, fish, flour, garlic, ginger, gnocchi, ham, kale, lemon, lentils, milk, mustard, noodles, nuts, olives, onion, parsley, parsnips, pasta, pastry, peanut, peas, pepper, potatoes, quinoa, rice, salmon, sardine, sausage, soy sauce, starch, sugar, toast, tomato, tuna, yeast, yoghurt";
        ingredients = new ArrayList<String>(List.of(str.split(", ")));

        Collections.sort(ingredients);
//        System.out.println(ingredients);


        for (String ing : ingredients) {
            HBox hbox = new HBox();
            hbox.setStyle("-fx-border:1px;-fx-border-color:lightgray;-fx-border-radius:10px;" +
                    "-fx-background-color:white;");
            hbox.setId(ing);

            Label label = new Label(ing);
            label.setStyle("-fx-padding: 5px;-fx-font-size: 16px;");



            Image image = new Image(new FileInputStream("src/main/java/IngredientIcons/"+ing+".png"));
            ImageView imageView = new ImageView(image);

            imageView.setFitHeight(20);
            imageView.setFitWidth(20);


            hbox.setAlignment(Pos.CENTER);



            //Registering the event filter
            hbox.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


            hbox.getChildren().addAll(imageView, label);

            allIngredients.getChildren().add(hbox);
        }

    }

    public ArrayList<Node> getSelected() {
        return selected;
    }
}
