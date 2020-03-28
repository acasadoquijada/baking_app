package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.ui.IngredientFragment;
import com.example.backing_app.ui.MasterListFragment;
import com.example.backing_app.ui.RecipeListFragment;
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

        recipe_index = intent.getIntExtra(RecipeListFragment.RECIPE_ID_KEY,0);

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
        setupIngredientsNew();
        setupSteps();
    }

    private void setupIngredientsNew(){
        FragmentManager fragmentManager = getSupportFragmentManager();




    }

    private void setupSteps(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        MasterListFragment masterListFragment = new MasterListFragment();
        IngredientFragment ingredientFragment = new IngredientFragment();


        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            masterListFragment.setOrientation(LinearLayout.HORIZONTAL);
            ingredientFragment.setOrientation(LinearLayout.HORIZONTAL);

            masterListFragment.setSpanCount(3);
            ingredientFragment.setSpanCount(3);
        } else{
            masterListFragment.setOrientation(LinearLayout.VERTICAL);
            ingredientFragment.setOrientation(LinearLayout.VERTICAL);

            masterListFragment.setSpanCount(1);
            ingredientFragment.setSpanCount(1);

        }

        masterListFragment.setStepsShortDescription(mStepsShortDescription);
        masterListFragment.setRecipeIndex(recipe_index);

        ingredientFragment.setIngredients(mIngredients);

        fragmentManager.beginTransaction().add(R.id.ingredients_frame_layout,ingredientFragment).commit();
        fragmentManager.beginTransaction().add(R.id.steps_frame_layout,masterListFragment).commit();

    }

}

































