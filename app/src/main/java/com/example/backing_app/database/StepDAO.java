package com.example.backing_app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.backing_app.recipe.Step;

import java.util.List;

@Dao
public interface StepDAO {

    @Query("SELECT * FROM steps WHERE + recipeId = :recipe_index")
    List<Step> getSteps(int recipe_index);

    @Query("SELECT shortDescription FROM steps WHERE + recipeId = :recipe_index")
    List<String> getStepsShortDescription(int recipe_index);

    @Query("SELECT id FROM steps WHERE + recipeId = :recipe_index")
    List<Integer> getStepsIndex(int recipe_index);

    @Query("SELECT * FROM steps WHERE + recipeId = :recipe_index AND id = :step_index")
    Step getStep(int recipe_index, int step_index);

    @Insert
    void insertStep(Step step);
}
