package com.example.foodrecipes;

import javafx.scene.image.Image;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeControllerTest {

    RecipeController rc = new RecipeController();

    @Test
    public void findId() {
        int id = rc.findId("Pulled BBQ Chicken Sandwiches");
        assertEquals(1, id);
    }

    @Test
    public void getImage() {
        Recipes recipes = new Recipes();
        recipes.setRecipeName("Kasha");
        recipes.setRecipeId(9);
        recipes.setIngIds(" 9 ");
        Image[] img = rc.getImage(recipes);
        boolean a = img.length > 0;
        assertTrue(a);
    }
}