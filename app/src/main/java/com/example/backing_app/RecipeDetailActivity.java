package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.ui.MasterListFragment;
import com.example.backing_app.ui.RecipeFragment;
import com.example.backing_app.utils.AppExecutorUtils;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private RecipeDataBase mDatabase;
    private List<Ingredient> mIngredients;
    private List<String> mStepsShortDescription;
    private int orientation;
    private int recipe_index;

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent;

        intent = getIntent();

        recipe_index = intent.getIntExtra(RecipeFragment.RECIPE_TOKEN_ID,0);

        mDatabase = RecipeDataBase.getInstance(this);

        orientation = getResources().getConfiguration().orientation;

        AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mIngredients = mDatabase.ingredientDAO().getIngredients(recipe_index);
                mStepsShortDescription = mDatabase.stepDAO().getStepsShortDescription(recipe_index);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateUI();
                    }
                });
            }
        });
    }

    private void populateUI(){
        setupIngredients();
        setupSteps();
    }

    private void setupIngredients(){

        //  We get the parent layout according to the screen orientation
        ViewGroup ingredientsLinearLayout;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ingredientsLinearLayout = findViewById(R.id.recipe_details_ingredients_grid_layout);
        } else {
            ingredientsLinearLayout = findViewById(R.id.recipe_details_ingredients_linear_layout);
        }

        // Create constrain layout per ingredient

        int ingredients_size = mIngredients.size();

        for(int i = 0; i < ingredients_size; i++){

            LayoutInflater inflater = LayoutInflater.from(this);

            View ingredientLayout = inflater.inflate(R.layout.ingredient_layout, null);

            TextView textView = ingredientLayout.findViewById(R.id.ingredient_name);
            TextView textView2 = ingredientLayout.findViewById(R.id.ingredient_measure);
            TextView textView3 = ingredientLayout.findViewById(R.id.ingredient_quantity);

            String bullet_point = "\u2022";

            textView.append(bullet_point + " " + mIngredients.get(i).getIngredientName());
            textView2.setText(mIngredients.get(i).getMeasure());
            textView3.setText(String.valueOf(mIngredients.get(i).getQuantity()));

            ConstraintLayout.LayoutParams l = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            ingredientLayout.setLayoutParams(l);

            ingredientsLinearLayout.addView(ingredientLayout, ingredientsLinearLayout.getChildCount() );

        }
    }

    private void setupSteps(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        MasterListFragment masterListFragment = new MasterListFragment();

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            masterListFragment.setOrientation(LinearLayout.HORIZONTAL);
        } else{
            masterListFragment.setOrientation(LinearLayout.VERTICAL);
        }

        masterListFragment.setSpanCount(3);
        masterListFragment.setStepsShortDescription(mStepsShortDescription); // this can be an step index
        masterListFragment.setRecipeIndex(recipe_index);

        fragmentManager.beginTransaction().add(R.id.steps_frame_layout,masterListFragment).commit();
    }

}

































