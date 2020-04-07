package com.example.backing_app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.backing_app.recipe.Ingredient;

import java.util.List;

@Dao
public interface IngredientDAO {

    @Query("SELECT * FROM ingredients WHERE + recipeId = :recipe_index")
    List<Ingredient> getIngredients(int recipe_index);

    @Query("SELECT ingredientName FROM ingredients WHERE + recipeId = :recipe_index")
    List<String> getIngredientsName(int recipe_index);

    @Insert
    void insertIngredient(Ingredient ingredient);


}
