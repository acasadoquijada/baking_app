package com.example.backing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.ui.IngredientFragment;
import com.example.backing_app.ui.StepListFragment;
import com.example.backing_app.ui.RecipeListFragment;
import com.example.backing_app.utils.AppExecutorUtils;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String RECIPE_INDEX_KEY = "recipe_index";
    private RecipeDataBase mDatabase;
    private List<Ingredient> mIngredients;
    private List<String> mStepsShortDescription;
    private int mOrientation;
    private int mRecipeIndex;

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // We only care about the intent if we want to get recipe_index
        if(savedInstanceState != null){
            mRecipeIndex = savedInstanceState.getInt(RECIPE_INDEX_KEY);
            Log.d(TAG, "Recipe index when recovered: " + mRecipeIndex);
        } else{
            Intent intent;
            intent = getIntent();
            mRecipeIndex = intent.getIntExtra(RecipeListFragment.RECIPE_ID_KEY, 0);
        }

        mDatabase = RecipeDataBase.getInstance(this);

        mOrientation = getResources().getConfiguration().orientation;

        AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mIngredients = mDatabase.ingredientDAO().getIngredients(mRecipeIndex);
                mStepsShortDescription = mDatabase.stepDAO().getStepsShortDescription(mRecipeIndex);
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
        setupIngredientsNew();
        setupSteps();
    }

    private void setupIngredientsNew(){
        FragmentManager fragmentManager = getSupportFragmentManager();




    }

    private void setupSteps(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        Log.d(TAG,"Recipe index: " + String.valueOf(mRecipeIndex));

        StepListFragment stepListFragment = new StepListFragment();
        IngredientFragment ingredientFragment = new IngredientFragment();


        if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
            stepListFragment.setOrientation(LinearLayout.HORIZONTAL);
            ingredientFragment.setOrientation(LinearLayout.HORIZONTAL);

            stepListFragment.setSpanCount(3);
            ingredientFragment.setSpanCount(3);
        } else{
            stepListFragment.setOrientation(LinearLayout.VERTICAL);
            ingredientFragment.setOrientation(LinearLayout.VERTICAL);

            stepListFragment.setSpanCount(1);
            ingredientFragment.setSpanCount(1);

        }

        stepListFragment.setStepsShortDescription(mStepsShortDescription);
        stepListFragment.setRecipeIndex(mRecipeIndex);

        ingredientFragment.setIngredients(mIngredients);

        fragmentManager.beginTransaction().add(R.id.ingredients_frame_layout,ingredientFragment).commit();
        fragmentManager.beginTransaction().add(R.id.steps_frame_layout, stepListFragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"Recipe index when stored: "+ String.valueOf(mRecipeIndex));
        outState.putInt(RECIPE_INDEX_KEY, mRecipeIndex);
    }
}

































