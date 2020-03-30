package com.example.backing_app.viewmodel;

import android.app.Application;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Recipe;
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

    /**
     * First, we check in the DB if there is recipe data. If not, we get it from the internet and then
     * is stored.
     *
     * For this, we assume the data in the webpage is static and doesn't change. Otherwise, more
     * checking would be needed
     */
    public void loadData(){

        if(mRecipes == null){

            mRecipes = mDatabase.recipeDAO().getRecipes();

            // No data in DB
            if(mRecipes.size() == 0){

                mRecipes = RecipesUtils.getRecipes();

                // Store the recipes.
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
    }
}
