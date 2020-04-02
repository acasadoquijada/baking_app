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
import com.example.backing_app.fragment.StepInstructionFragment;
import com.example.backing_app.fragment.VideoFragment;
import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.fragment.IngredientListFragment;
import com.example.backing_app.fragment.StepListFragment;
import com.example.backing_app.fragment.RecipeListFragment;
import com.example.backing_app.recipe.Step;
import com.example.backing_app.utils.AppExecutorUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.backing_app.fragment.StepListFragment.STEP_INDEX_KEY;

/**
 * This Activity presents more details about a recipe to the user. This are:
 * - Ingredients
 * - Steps. Is possible to click on them to obtain more info
 */
public class RecipeDetailActivity extends AppCompatActivity implements StepListFragment.onGridElementClick{

    private static final String RECIPE_INDEX_KEY = "recipe_index";
    private RecipeDataBase mDatabase;
    private List<Ingredient> mIngredients;
    private List<String> mStepsShortDescription;
    private int mOrientation;
    private int mRecipeIndex;
    private Step step;
    private boolean mTwoPane;
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    /**
     * Using the recipe_index the necessary info is loaded, in this case is:
     * - Ingredients
     * - Short description of the steps
     *
     * Later the UI is populated using a IngredientListFragment and a StepListFragment
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDatabase = RecipeDataBase.getInstance(this);

        mOrientation = getResources().getConfiguration().orientation;

        if(findViewById(R.id.two_pane_layout) != null){
            Log.d(TAG,"I should have created a two pane layout");

            mTwoPane = true;
            // Do the same as in StepDetail

            if(savedInstanceState == null){

                // First I need the first step for this recipe. Then populate the fragments

                AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        step = mDatabase.stepDAO().getStep(mRecipeIndex, 0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                FragmentManager fragmentManager = getSupportFragmentManager();

                                VideoFragment mVideoFragment = new VideoFragment();

                                // THIS IS DONE ONLY FOR TESTING UNTIL I GET MY OWN TABLET
                                // THIS IS DONE DUE TO THE LOW RESOURCES OF MY LAPTOP

                                mVideoFragment.setMediaURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffddf0_-intro-yellow-cake/-intro-yellow-cake.mp4");

                                StepInstructionFragment mStepInstructionFragment = new StepInstructionFragment();

                                mStepInstructionFragment.setStepInstruction("Recipe Introduction");

                                fragmentManager.beginTransaction().add(R.id.video_frame_layout, mVideoFragment).commit();

                                fragmentManager.beginTransaction().add(
                                        R.id.step_description_frame_layout,
                                        mStepInstructionFragment).commit();
                            }
                        });
                    }
                });

                // As we have the recipe index, we can obtain the info here, avoiding passing complex objects
                // such as Recipe or Step using intents
                Log.d(TAG, "Populating new stuff");
                AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {

                    @Override
                    public void run() {
                        mIngredients = mDatabase.ingredientDAO().getIngredients(mRecipeIndex);
                        mStepsShortDescription = mDatabase.stepDAO().getStepsShortDescription(mRecipeIndex);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI();
                                Log.d(TAG, "Populating new stuff");
                            }
                        });
                    }
                });
            }

        } else {
            mTwoPane = false;

            // We only care about the intent if we want to get recipe_index
            if(savedInstanceState != null){
                mRecipeIndex = savedInstanceState.getInt(RECIPE_INDEX_KEY);
            } else{
                Intent intent;
                intent = getIntent();
                mRecipeIndex = intent.getIntExtra(RecipeListFragment.RECIPE_ID_KEY, 0);
            }

            // As we have the recipe index, we can obtain the info here, avoiding passing complex objects
            // such as Recipe or Step using intents
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
    }

    /**
     * Two Fragments are created (StepListFragment and IngredientListFragment)
     * Only the necessary info is provided to them
     */

    private void populateUI(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        StepListFragment stepListFragment = new StepListFragment();
        IngredientListFragment ingredientListFragment = new IngredientListFragment();

        if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
            stepListFragment.setOrientation(LinearLayout.HORIZONTAL);
            ingredientListFragment.setOrientation(LinearLayout.HORIZONTAL);

            stepListFragment.setSpanCount(3);
            ingredientListFragment.setSpanCount(3);

        } else{
            stepListFragment.setOrientation(LinearLayout.VERTICAL);
            ingredientListFragment.setOrientation(LinearLayout.VERTICAL);

            stepListFragment.setSpanCount(1);
            ingredientListFragment.setSpanCount(1);
        }

        stepListFragment.setStepsShortDescription(mStepsShortDescription);
        stepListFragment.setRecipeIndex(mRecipeIndex);

        ingredientListFragment.setIngredients(mIngredients);

        fragmentManager.beginTransaction().add(R.id.ingredients_frame_layout, ingredientListFragment).commit();
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
        outState.putInt(RECIPE_INDEX_KEY, mRecipeIndex);
    }

    @Override
    public void onItemSelected(int pos) {

        Intent intent = new Intent(this, StepDetailActivity.class);

        intent.putExtra(STEP_INDEX_KEY, pos);
        intent.putExtra(RECIPE_INDEX_KEY, mRecipeIndex);
        startActivity(intent);
    }
}

































