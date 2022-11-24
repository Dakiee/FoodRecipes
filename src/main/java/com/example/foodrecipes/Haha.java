package com.example.foodrecipes;

import java.util.ArrayList;
import java.util.List;

public class Haha {
    public Haha(){
        String str ="BBQ sause, bacon, basil, beans, beef, berry, broth, brussels sprouts, buckwheat, butter, cabbage, capers, carrot, cheese, chicken, chickpeas, chocolate, cocoa, couscous, cucumber, dill, dough, eggs, fish, flour, garlic, ginger, gnocchi, ham, kale, lemon, lentils, milk, mustard, noodles, nuts, olives, onion, parsley, parsnips, pasta, pastry, peanut, peas, pepper, potatoes, quinoa, rice, salmon, sardine, sausage, soy sauce, starch, sugar, toast, tomato, tuna, yeast, yoghurt";
        ArrayList<String > arrayList = new ArrayList<>(List.of(str.split(", ")));
        for (Object obj: arrayList) {
            System.out.println("INSERT INTO ingredients(name , type , origin) VALUES(\""+ obj +"\",\"\",\"\");");
        }
    }
    public static void main(String[] args) {
        new Haha();
    }
}
