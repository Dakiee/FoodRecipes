package com.example.foodrecipes;

import java.util.Date;

public class Favorites {
    private Integer id;
    private String recipe;
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String  getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "id=" + id +
                ", recipe='" + recipe + '\'' +
                ", date=" + date +
                '}';
    }
}
