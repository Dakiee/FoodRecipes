package com.example.foodrecipes;

public class Recipes {
    private String recipeId;
    private String recipeName;
    private String cookTime;
    private String ingIds;
    private String description;

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getIngIds() {
        return ingIds;
    }

    public void setIngIds(String ingIds) {
        this.ingIds = ingIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "recipeId='" + recipeId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", cookTime='" + cookTime + '\'' +
                ", ingIds='" + ingIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
