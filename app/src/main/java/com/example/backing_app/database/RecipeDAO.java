package com.example.backing_app.database;

import android.graphics.Movie;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.recipe.Step;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM recipes")
    List<Recipe> getRecipes();

    @Query("SELECT * FROM recipes WHERE + recipes.id = :recipe_id ")
    Recipe getRecipe(int recipe_id);

    @Insert
    void insertRecipe(Recipe recipe);

}
