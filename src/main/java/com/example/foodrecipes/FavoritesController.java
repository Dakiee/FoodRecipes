package com.example.foodrecipes;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.foodrecipes.LoginController.favorites;
import static com.example.foodrecipes.LoginController.user;

public class FavoritesController {
    @FXML
    private Label lblHome;

    @FXML
    private VBox vbxContent;
    ArrayList<Recipes> recipes = new ArrayList<>();

    @FXML
    void goToHome(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void addContent() {
        String ids = LoginController.favorites.getRecipe();
        String[] recipeIds = ids.split(" ");
        for (String str : recipeIds) {
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connectDB = connectNow.getConnection();
            String getRecipes = "SELECT * FROM recipes WHERE recipe_id =  '" + str + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(getRecipes);
                while (queryResult.next()) {
                    Recipes recipe = new Recipes();
                    recipe.setRecipeId(queryResult.getObject(1).toString());
                    recipe.setRecipeName(queryResult.getObject(2).toString());
                    recipe.setCookTime(queryResult.getObject(3).toString());
                    recipe.setIngIds(queryResult.getObject(4).toString());
                    recipe.setDescription(queryResult.getObject(6).toString());
                    recipes.add(recipe);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }

        RecipeController recipeController = new RecipeController();
        for (Recipes r : recipes) {
            Image[] icons = recipeController.getImage(r);
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            Label rName = new Label(r.getRecipeName());
            rName.setFont(new Font(14));
            rName.setStyle("-fx-font-weight: bold");
            rName.setPrefWidth(450);
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
            rDescription.setWrappingWidth(605);

            vBox.getChildren().add(hBox);
            vBox.getChildren().add(desc);

            vBox.setMinHeight(Region.USE_PREF_SIZE);
            vBox.setPadding(new Insets(5, 10, 5, 10));
            vBox.setStyle("-fx-background-color:#DAD9D9;");

            vBox.setPrefWidth(300);
            vBox.setMaxWidth(300);
            Image img = new Image(getClass().getResourceAsStream("img/star-filled.png"));
            ImageView im = new ImageView(img);
            im.setFitHeight(20);
            im.setFitWidth(20);
            im.setTranslateX(580);
            vBox.getChildren().add(im);
            final boolean bool = false;
            vBox.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                if (LoginController.user != null) {
                    VBox box = (VBox) e.getSource();
                    HBox box2 = (HBox) box.getChildren().get(0);
                    Label label = (Label) box2.getChildren().get(0);
                    String foodName = label.getText();
                    int id = recipeController.findId(foodName);
                    if (id == -1) {
                        System.err.println("Cannot found name");
                    } else {
                        DataBaseConnection connectNow = new DataBaseConnection();
                        Connection connectDB = connectNow.getConnection();
                        try {
                            Statement statement = connectDB.createStatement();
                            String query = "UPDATE favorites SET recipe_ids = REPLACE(recipe_ids,  ' " + id
                                    + " ', ' ') WHERE fav_id = " + user.getFavId() + ";";
                            statement.executeUpdate(query);
                            String getRecipeIds = "SELECT recipe_ids FROM favorites WHERE fav_id = " + user.getFavId()
                                    + ";";
                            ResultSet queryResult = statement.executeQuery(getRecipeIds);
                            while (queryResult.next()) {
                                favorites.setRecipe((String) queryResult.getObject(1));
                            }
                        } catch (Exception exp) {
                            exp.printStackTrace();
                            exp.getCause();
                        }

                    }
                    String path = !bool ? "img/star.png" : "img/star-filled.png";
                    Image image = new Image(getClass().getResourceAsStream(path));
                    im.setImage(image);
                    im.setFitWidth(20);
                    im.setFitHeight(20);
                }
            });
            VBox vb = new VBox();
            vb.setStyle("-fx-border-radius:10px;-fx-border-width:5px;-fx-border-color:#DAD9D9;");
            vb.getChildren().add(vBox);
            vbxContent.getChildren().add(vb);
            vbxContent.setPadding(new Insets(10, 10, 0, 10));
            vbxContent.setSpacing(10);

            vbxContent.setMinHeight(2000);
            vbxContent.setPrefWidth(600);
            vbxContent.setMaxWidth(600);
        }

    }

}
