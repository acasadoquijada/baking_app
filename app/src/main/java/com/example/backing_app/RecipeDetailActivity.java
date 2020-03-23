package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.ui.RecipeFragment;
import com.example.backing_app.utils.AppExecutorUtils;

public class RecipeDetailActivity extends AppCompatActivity {

    private RecipeDataBase mDatabase;
    private Recipe recipe;

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setupIngredients();

                        for(int i = 0; i < recipe.getIngredients().size(); i++){
                            Log.d(TAG,recipe.getIngredients().get(i).getIngredientName());
                            Log.d(TAG,String.valueOf(recipe.getIngredients().get(i).getQuantity()));
                            Log.d(TAG,recipe.getIngredients().get(i).getMeasure());
                        }
                    }
                });
            }
        });
    }

    private void setupIngredients(){

        //  We get the father Linear Layout

        int orientation = getResources().getConfiguration().orientation;

        ViewGroup ingredientsLinearLayout;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ingredientsLinearLayout = findViewById(R.id.ingredients_grid_layout);
        } else {
            ingredientsLinearLayout = findViewById(R.id.ingredients_linear_layout);
        }


        // Create constrain layout per ingredient

        int ingredients_size = recipe.getIngredients().size();

        for(int i = 0; i < ingredients_size; i++){

            LayoutInflater inflater = LayoutInflater.from(this);

            View ingredientLayout = inflater.inflate(R.layout.ingredient_layout, null);

            TextView textView = ingredientLayout.findViewById(R.id.ingredient_name);
            TextView textView2 = ingredientLayout.findViewById(R.id.ingredient_measure);
            TextView textView3 = ingredientLayout.findViewById(R.id.ingredient_quantity);

            String bullet_point = "\u2022";

            textView.append(bullet_point + " " + recipe.getIngredients().get(i).getIngredientName());
            textView2.setText(recipe.getIngredients().get(i).getMeasure());
            textView3.setText(String.valueOf(recipe.getIngredients().get(i).getQuantity()));

            ConstraintLayout.LayoutParams l = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            ingredientLayout.setLayoutParams(l);

            ingredientsLinearLayout.addView(ingredientLayout, ingredientsLinearLayout.getChildCount());
        }
    }

    private void setUpUI(){


    }

}

































