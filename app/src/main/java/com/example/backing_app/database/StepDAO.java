package com.example.backing_app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.recipe.Step;

import java.util.List;

@Dao
public interface StepDAO {

    @Query("SELECT * FROM steps WHERE + recipeId = :recipe_index")
    List<Step> getSteps(int recipe_index);

    @Insert
    void insertStep(Step step);
}
