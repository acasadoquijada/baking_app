package com.example.backing_app;

import com.example.backing_app.recipe.Ingredient;

import static org.junit.Assert.*;

import org.junit.Test;

public class IngredientUnitTest {

    @Test
    public void testIngredients(){

        Ingredient ingredient = new Ingredient();

        String name = "lemon";
        String measure = "G";
        int key = 2;
        float quantity = 2.5f;
        int recipe_id = 2;

        ingredient.setIngredientName(name);
        ingredient.setMeasure(measure);
        ingredient.setKey(key);
        ingredient.setQuantity(quantity);
        ingredient.setRecipeId(recipe_id);

        assertEquals(name, ingredient.getIngredientName());
        assertEquals(measure, ingredient.getMeasure());
        assertEquals(key, ingredient.getKey());
        assertEquals(quantity, ingredient.getQuantity(),0.001);
        assertEquals(recipe_id, ingredient.getRecipeId());
    }
}
