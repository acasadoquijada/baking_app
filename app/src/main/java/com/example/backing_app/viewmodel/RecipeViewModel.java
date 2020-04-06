package com.example.backing_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.utils.NetworkUtils;
import com.example.backing_app.utils.RecipesUtils;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private static final String TAG = RecipeViewModel.class.getSimpleName();
    private final RecipeDataBase mDatabase;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mDatabase = RecipeDataBase.getInstance(application.getApplicationContext());
    }

    /**
     * First, we check in the DB if there is recipe data. If not, we get it from the internet and then
     * is stored.
     *
     * For this, we assume the data in the webpage is static and doesn't change. Otherwise, more
     * checking would be needed
     *
     * If there is internet connection and the data is stored properly, return true
     *
     * Otherwise return false
     */
    public boolean loadData(){

        // First we try to load the recipe data form the DB
        List<Recipe> mRecipes = mDatabase.recipeDAO().getRecipes();

        // If there is no data, we get the info online
        if(mRecipes != null && mRecipes.size() == 0){

            if(NetworkUtils.internetConnectionAvailable()){
                mRecipes = RecipesUtils.getRecipes();

                if(mRecipes != null){
                    // Store the recipes.
                    for(int i = 0; i < mRecipes.size(); i++) {
                        mDatabase.recipeDAO().insertRecipe(mRecipes.get(i));

                        // Store the steps
                        for (int j = 0; j < mRecipes.get(i).getSteps().size(); j++) {
                            mDatabase.stepDAO().insertStep(mRecipes.get(i).getSteps().get(j));
                        }

                        // Store the ingredients
                        for (int j = 0; j < mRecipes.get(i).getIngredients().size(); j++) {
                            mDatabase.ingredientDAO().insertIngredient(mRecipes.get(i).getIngredients().get(j));
                        }

                    }
                    return true; // Data stored in database
                }
            }
            return false; // No internet connection and no data
        }
        return true; // There is data in the database
    }
}
