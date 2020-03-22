package com.example.backing_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.backing_app.recipe.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
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

}
