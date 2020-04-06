package com.example.backing_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.backing_app.recipe.SelectedRecipe;
import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.recipe.Step;

/**
 * Class representing the database. It has three tables:
 * - Recipes
 * - Steps
 * - Ingredients
 *
 * Steps and Ingredients have a foreign key to its recipe index.
 *
 * This design has been done in order to reduce the memory used by the application. By having the
 * recipe index and/or the step index each Activity is able to recover the necessary data from the
 * database avoiding passing object such as Recipe, Ingredient or Step using intents.
 */

@Database(entities = {Recipe.class, Step.class, Ingredient.class, SelectedRecipe.class}, version = 1, exportSchema = false)
@TypeConverters({StepListConverter.class, IngredientListConverter.class})
public abstract class RecipeDataBase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipes_database";
    private static RecipeDataBase sInstance;

    public static RecipeDataBase getInstance(Context context){

        if(sInstance == null){
            sInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RecipeDataBase.class,
                    RecipeDataBase.DATABASE_NAME)
                    .build();
        }

        return sInstance;
    }

    public abstract RecipeDAO recipeDAO();
    public abstract StepDAO stepDAO();
    public abstract IngredientDAO ingredientDAO();
}
