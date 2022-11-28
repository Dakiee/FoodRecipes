package com.example.foodrecipes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    @FXML
    private HBox ingredients;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button add;
    @FXML
    private Button login_btn;
    @FXML
    private Button find_btn;
    private boolean spAdded = false;

    @FXML
    void test(ActionEvent event) {
        System.out.println(selected);
    }
    @FXML
    void onFindClicked(ActionEvent event) {
        if(!(selected == null || selected.isEmpty())) {
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connectDB = connectNow.getConnection();
            StringBuffer sb = new StringBuffer();
            for (Node obj : selected) {
//            sb.append(obj.getId() + " ");

                String verifyLogin = "SELECT ingredientsId FROM ingredients WHERE name =  '" + obj.getId() + "'";
                try {
                    Statement statement = connectDB.createStatement();
                    ResultSet queryResult = statement.executeQuery(verifyLogin);
                    while (queryResult.next()) {
                        sb.append(queryResult.getInt(1) + " ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }
            }
            ArrayList<String> cond = new ArrayList<>(List.of(sb.toString().split(" ")));
            StringBuffer buf = new StringBuffer();


            for (int i = 0; i < cond.size(); i++) {
                if(i == cond.size()-1)
                    buf.append("ingIds LIKE '%" + cond.get(i) + "%'");
                else
                    buf.append("ingIds LIKE '%" + cond.get(i) + "%' OR ");
            }
            System.out.println(buf);


            String food = "SELECT RecipeName FROM recipes WHERE " + buf;
            System.out.println(food);
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(food);
                while (queryResult.next()) {
                    System.out.println( (queryResult.getObject(1)));
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            System.out.println(sb);
        }

    }
    ArrayList<Node> selected;

    @FXML
    void onOpenLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, 800, 545);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("favicon.png")).openStream()));


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    private boolean isInList(String id) {

        for (int i = 0; i < ingredients.getChildren().size(); i++) {
            if (ingredients.getChildren().get(i).getId().equals(id)) return true;
        }
        return false;
    }
    public EventHandler<MouseEvent> mouseEnter = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox)e.getSource();

            hbox.setStyle("-fx-border:1px;-fx-border-color:rgb(255, 102, 102);-fx-border-radius:10px;" +
                    "-fx-background-color:rgb(255, 102, 102);");
            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };
    public EventHandler<MouseEvent> mouseExit = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox)e.getSource();

            hbox.setStyle("-fx-border:1px;-fx-border-color:#E58412;-fx-border-radius:10px;" +
                    "-fx-background-color:white;");

            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };
    public EventHandler<MouseEvent> mouseClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox)e.getSource();
            ingredients.getChildren().remove(hbox);
            selected.remove(hbox);


            //System.out.println(hbox.getParent().getChildrenUnmodifiable().toString());
            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };
    @FXML
    void addIngredient(ActionEvent event) throws IOException {
        selected = onOpenDialog();
        System.out.println(selected);
        if(!spAdded) {
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            spAdded=true;
        }

        for (Node ing : selected) {

            String id = ing.getId();
            HBox test = (HBox) ing;
            test.setMinWidth(100);
            if (!isInList(id)) {
//                ing.minWidth(300);
//                ing.prefWidth(300);
//                ing.maxWidth(300);
//                ing.setStyle("-fx-padding: 2px;");


//                comp.addUserIngredient(ing.getId());
                test.addEventFilter(MouseEvent.MOUSE_ENTERED,mouseEnter);
                test.addEventFilter(MouseEvent.MOUSE_EXITED,mouseExit);
                test.addEventFilter(MouseEvent.MOUSE_CLICKED,mouseClick);

                ingredients.getChildren().add(test);

            } else System.out.println("Item is in list");

            scrollPane.setContent(ingredients);

        }

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


