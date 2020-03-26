package com.example.backing_app.viewmodel;

import android.app.Application;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.room.Room;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.utils.RecipesUtils;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private static final String TAG = RecipeViewModel.class.getSimpleName();
    private List<Recipe> mRecipes;
    private RecipeDataBase mDatabase;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mDatabase = RecipeDataBase.getInstance(application.getApplicationContext());
        Log.d(TAG,"I CREATE");
    }

    public List<Recipe> loadData(){

        String s = "AA";
        if(mRecipes == null){

            mRecipes = mDatabase.recipeDAO().getRecipes();

            if(mRecipes.size() == 0){
                // We don't have!!
                Log.d(TAG,"No recipe in DB");

                // We get the recipes and store them in the DataBase
                Log.d(TAG,"I request the recipes online");

                mRecipes = RecipesUtils.getRecipes();

                Log.d(TAG,"I store the recipes in the Database");

                for(int i = 0; i < mRecipes.size(); i++){
                    mDatabase.recipeDAO().insertRecipe(mRecipes.get(i));

                    // Store the steps
                    for(int j = 0; j < mRecipes.get(i).getSteps().size(); j++){
                        mDatabase.stepDAO().insertStep(mRecipes.get(i).getSteps().get(j));
                    }

                    // Store the ingredients
                    for(int j = 0; j < mRecipes.get(i).getIngredients().size(); j++){
                        mDatabase.ingredientDAO().insertIngredient(mRecipes.get(i).getIngredients().get(j));
                    }
                }

            }
        }
        return mRecipes;
    }
}
