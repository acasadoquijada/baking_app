package com.example.backing_app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.backing_app.recipe.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM recipes")
    List<Recipe> getRecipes();

    @Query("SELECT * FROM recipes WHERE + recipes.id = :recipe_id ")
    Recipe getRecipe(int recipe_id);

    @Query("SELECT name FROM recipes")
    List<String> getRecipesName();

    @Query("SELECT servings FROM recipes")
    List<String> getRecipesServing();

    @Insert
    void insertRecipe(Recipe recipe);

}
