package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.ui.RecipeFragment;
import com.example.backing_app.utils.AppExecutorUtils;

public class RecipeDetailActivity extends AppCompatActivity {

    private RecipeDataBase mDatabase;
    private Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent;

        intent = getIntent();

        final int recipe_index = intent.getIntExtra(RecipeFragment.RECIPE_TOKEN_ID,0);

        mDatabase = RecipeDataBase.getInstance(this);

        AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                recipe = mDatabase.recipeDAO().getRecipe(recipe_index);

                String s = "aa";
            }
        });
    }
}
