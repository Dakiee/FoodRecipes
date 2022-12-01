package com.example.foodrecipes;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.foodrecipes.LoginController.favorites;
import static com.example.foodrecipes.LoginController.user;

/**
 * @author : Dalai
 * @version : 1.0.0
 * @date : 2022:11:20
 * @improvement : Icon, FXML Load, Link Login
 *
 * @version 1.0.1
 * @date : 2022:11:22
 * @improvement : List Delete
 *
 * @version 1.0.2
 * @date : 2022:11:24
 * @improvement query change
 *
 * @version 1.0.3
 * @date : 2022:11:27
 * @improvement Oldson hailtaa list hiideg bolov
 */

public class RecipeController {
    @FXML
    private HBox ingredients;
    @FXML
    private VBox vbContent;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox recipeBox;
    @FXML
    private HBox upBox;
    @FXML
    private Button btnFavorites;
    @FXML
    private Button btnAdd;
    @FXML
    private ScrollPane btmScrollPane;
    @FXML
    private Button btnLogin;
    @FXML
    private Button find_btn;
    private boolean spAdded = false;
    ArrayList<Recipes> recipes = new ArrayList<>();

    @FXML
    void initialize() {
        changeLoginBtn(user == null);
        System.out.println("bandi");
    }

    /**
     * @param event
     */
    @FXML
    void onOpenFavorites(ActionEvent event) throws IOException {
        if (user != null) {
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connectDB = connectNow.getConnection();
            try {
                Statement statement = connectDB.createStatement();
                String getRecipeIds = "SELECT recipe_ids FROM favorites WHERE fav_id = " + user.getFavId() + ";";
                ResultSet queryResult = statement.executeQuery(getRecipeIds);
                while (queryResult.next()) {
                    favorites.setRecipe((String) queryResult.getObject(1));
                }
            } catch (Exception exp) {
                exp.printStackTrace();
                exp.getCause();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("favorites.fxml"));
            Parent parent = fxmlLoader.load();
            FavoritesController favoritesController = fxmlLoader.getController();
            favoritesController.addContent();

            Scene scene = new Scene(parent, 800, 570);
            Stage stage = new Stage();
            stage.getIcons()
                    .add(new Image(Objects.requireNonNull(getClass().getResource("img/favicon.png")).openStream()));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            System.out.println(favorites);
        }
    }

    /**
     * @param event
     */
    @FXML
    void onFindClicked(ActionEvent event) {
        if (!(selected == null || selected.isEmpty())) {
            recipes.clear();
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connectDB = connectNow.getConnection();
            StringBuffer sb = new StringBuffer();
            for (Node obj : selected) {
                String verifyLogin = "SELECT ingredients_id FROM ingredients WHERE name =  '" + obj.getId() + "'";
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
                if (i == cond.size() - 1)
                    buf.append("ing_ids LIKE '% " + cond.get(i) + " %'");
                else
                    buf.append("ing_ids LIKE '% " + cond.get(i) + " %' OR ");
            }

            String food = "SELECT * FROM recipes WHERE " + buf;
            System.out.println(food);
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(food);
                while (queryResult.next()) {
                    Recipes recipe = new Recipes();
                    recipe.setRecipeId(queryResult.getObject(1).toString());
                    recipe.setRecipeName(queryResult.getObject(2).toString());
                    recipe.setCookTime(queryResult.getObject(3).toString());
                    recipe.setIngIds(queryResult.getObject(4).toString());
                    recipe.setDescription(queryResult.getObject(6).toString());

                    System.out.println(recipe);
                    recipes.add(recipe);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            System.out.println(sb);
            createRecipeBox();
        }

    }

    /**
     *
     */
    private void createRecipeBox() {
        vbContent.getChildren().clear();
        for (Recipes r : recipes) {
            Image[] icons = getImage(r);
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            Label rName = new Label(r.getRecipeName());
            rName.setFont(new Font(14));
            rName.setStyle("-fx-font-weight: bold");
            rName.setPrefWidth(500);
            hBox.getChildren().add(rName);
            ImageView[] imageView = new ImageView[icons.length];
            HBox imageHBox = new HBox();
            for (int i = 0; i < icons.length; i++) {
                imageView[i] = new ImageView(icons[i]);
                imageView[i].setFitWidth(25);
                imageView[i].setFitHeight(25);
                imageHBox.getChildren().add(imageView[i]);
            }
            hBox.getChildren().add(imageHBox);

            Text rDescription = new Text(r.getDescription());
            HBox desc = new HBox(rDescription);
            rDescription.setWrappingWidth(685);

            vBox.getChildren().add(hBox);
            vBox.getChildren().add(desc);

            vBox.setMinHeight(Region.USE_PREF_SIZE);
            vBox.setPadding(new Insets(5, 10, 5, 10));
            vBox.setStyle("-fx-background-color:#DAD9D9;");

            vBox.setPrefWidth(300);
            vBox.setMaxWidth(300);
            Image img = new Image(getClass().getResourceAsStream("img/star.png"));
            ImageView im = new ImageView(img);
            im.addEventFilter(MouseEvent.MOUSE_CLICKED, starClicked);
            im.setFitHeight(20);
            im.setFitWidth(20);
            im.setTranslateX(670);
            vBox.getChildren().add(im);
            final boolean bool = false;
            vBox.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                if (LoginController.user != null) {
                    VBox box = (VBox) e.getSource();
                    HBox box2 = (HBox) box.getChildren().get(0);
                    Label label = (Label) box2.getChildren().get(0);
                    String foodName = label.getText();
                    int id = findId(foodName);
                    if (id == -1) {
                        System.err.println("Cannot found name");
                    } else {
                        DataBaseConnection connectNow = new DataBaseConnection();
                        Connection connectDB = connectNow.getConnection();
                        try {
                            String recipeIds = "";
                            Statement statement = connectDB.createStatement();
                            String qry = "SELECT recipe_ids FROM favorites WHERE fav_id = " + user.getFavId() + ";";
                            ResultSet queryResult = statement.executeQuery(qry);
                            while (queryResult.next()) {
                                recipeIds += queryResult.getObject(1).toString();
                            }
                            if (recipeIds.contains(String.valueOf(id))) {
                                System.out.println("is already fav");
                            } else {
                                String query = "UPDATE favorites SET recipe_ids = CONCAT(recipe_ids, '" + id
                                        + " ') WHERE fav_id = " + user.getFavId() + ";";
                                statement.executeUpdate(query);
                            }
                        } catch (Exception exp) {
                            exp.printStackTrace();
                            exp.getCause();
                        }

                    }
                    String path = !bool ? "img/star-filled.png" : "img/star.png";
                    Image image = new Image(getClass().getResourceAsStream(path));
                    im.setImage(image);
                    im.setFitWidth(20);
                    im.setFitHeight(20);
                }
            });
            VBox vb = new VBox();
            vb.setStyle("-fx-border-radius:10px;-fx-border-width:5px;-fx-border-color:#DAD9D9;");
            vb.getChildren().add(vBox);
            vbContent.getChildren().add(vb);
            vbContent.setPadding(new Insets(10, 10, 0, 10));
            vbContent.setSpacing(10);

            vbContent.setMinHeight(2000);
            vbContent.setPrefWidth(730);
            vbContent.setMaxWidth(730);
        }
        btmScrollPane.setContent(vbContent);
        btmScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public int findId(String foodName) {
        int id = -1;
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT recipe_id FROM recipes WHERE recipe_name =  '" + foodName + "'";
        System.out.println(verifyLogin);
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                id = queryResult.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return id;
    }

    /**
     *
     * @param r
     * @return
     */
    public Image[] getImage(Recipes r) {
        String[] imageNum;
        imageNum = r.getIngIds().split(" ");
        Image[] images = new Image[imageNum.length];

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();
        for (int i = 0; i < imageNum.length; i++) {
            String verifyLogin = "SELECT name FROM ingredients WHERE ingredients_id =  '" + imageNum[i] + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);
                while (queryResult.next()) {
                    Image image = new Image(
                            new FileInputStream("src/main/java/IngredientIcons/" + queryResult.getObject(1) + ".png"));
                    images[i] = image;
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }

        return images;
    }

    ArrayList<Node> selected;

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onOpenLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, 800, 545);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/favicon.png")).openStream()));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     *
     * @param id
     * @return
     */
    private boolean isInList(String id) {

        for (int i = 0; i < ingredients.getChildren().size(); i++) {
            if (ingredients.getChildren().get(i).getId().equals(id))
                return true;
        }
        return false;
    }

    /**
     *
     */
    public EventHandler<MouseEvent> mouseEnter = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox) e.getSource();

            hbox.setStyle("-fx-border:1px;-fx-border-color:rgb(255, 102, 102);-fx-border-radius:10px;" +
                    "-fx-background-color:rgb(255, 102, 102);");
            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };

    /**
     *
     */
    public EventHandler<MouseEvent> starClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            ImageView imageView = (ImageView) e.getSource();
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/star-filled.png")));
            imageView.setImage(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
        }
    };

    /**
     *
     */
    public EventHandler<MouseEvent> mouseExit = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox) e.getSource();

            hbox.setStyle("-fx-border:1px;-fx-border-color:#E58412;-fx-border-radius:10px;" +
                    "-fx-background-color:white;");
        }
    };
    /**
     *
     */
    public EventHandler<MouseEvent> mouseClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox) e.getSource();
            ingredients.getChildren().remove(hbox);
            selected.remove(hbox);
        }
    };

    /**
     * author: Dalai
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    void addIngredient(ActionEvent event) throws IOException {
        selected = onOpenDialog();
        System.out.println(selected);
        if (!spAdded) {
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            spAdded = true;
        }

        for (Node ing : selected) {

            String id = ing.getId();
            HBox test = (HBox) ing;
            test.setMinWidth(Region.USE_PREF_SIZE);
            test.setPadding(new Insets(0, 2, 0, 2));
            if (!isInList(id)) {
                test.addEventFilter(MouseEvent.MOUSE_ENTERED, mouseEnter);
                test.addEventFilter(MouseEvent.MOUSE_EXITED, mouseExit);
                test.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClick);

                ingredients.getChildren().add(test);
                ingredients.setSpacing(1);
                ingredients.setPadding(new Insets(3, 0, 0, 3));

            } else
                System.out.println("Item is in list");

            scrollPane.setContent(ingredients);

        }

    }

    @FXML
    private ArrayList<Node> onOpenDialog() throws IOException {
        ingredients.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addIngredients.fxml"));
        Parent parent = fxmlLoader.load();
        SelectIngredients dialogController = fxmlLoader.getController();
        dialogController.FillIngredients();
        Scene scene = new Scene(parent, 700, 570);
        Stage stage = new Stage();
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        EventHandler handler = dialogController.getEventHandler();
        ArrayList<Node> temporary = new ArrayList<>(dialogController.getSelected());
        for (Node ing : temporary) {
            ing.removeEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        }
        return temporary;
    }

    void changeLoginBtn(boolean b) {
        Button button = (Button) upBox.getChildren().get(2);
        if (b) {
            button.setText("Login");
        } else {
            button.setText("Log out");
        }

    }
}
