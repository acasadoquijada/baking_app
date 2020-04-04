package com.example.backing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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

import static com.example.backing_app.fragment.StepListFragment.STEP_INDEX_KEY;

/**
 * This Activity presents more details about a recipe to the user. This are:
 * - Ingredients
 * - Steps. Is possible to click on them to obtain more info
 */
public class RecipeDetailActivity extends AppCompatActivity implements StepListFragment.onGridElementClick{

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private static final String RECIPE_INDEX_KEY = "recipe_index";
    private RecipeDataBase mDatabase;
    private List<Ingredient> mIngredients;
    private List<String> mStepsShortDescription;
    private int mOrientation;
    private int mRecipeIndex;
    private int mStepIndex;
    private boolean mTwoPane;

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

        if (savedInstanceState != null) {
            mRecipeIndex = savedInstanceState.getInt(RECIPE_INDEX_KEY);
            mStepIndex = savedInstanceState.getInt(STEP_INDEX_KEY);
        } else {
            Intent intent;
            intent = getIntent();
            mRecipeIndex = intent.getIntExtra(RecipeListFragment.RECIPE_ID_KEY, 0);
        }

        // Action bar to be able to go to the parent activity with just a click
        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Setup the Database
        mDatabase = RecipeDataBase.getInstance(this);

        // Get the screen orientation
        mOrientation = getResources().getConfiguration().orientation;

        // Common things for mTwoPanel = true and false
        AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mIngredients = mDatabase.ingredientDAO().getIngredients(mRecipeIndex);
                mStepsShortDescription = mDatabase.stepDAO().getShortDescription(mRecipeIndex);
            }
        });

        // Check if we are in a phone or tablet

        if (findViewById(R.id.two_pane_layout) != null) {
            mTwoPane = true;

                // Now I should get the info. Steps
                // As we have the recipe index, we can obtain the info here, avoiding passing complex objects
                // such as Recipe or Step using intents
                AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final String videoUrl = mDatabase.stepDAO().getVideoURL(mRecipeIndex, mStepIndex);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI();
                                populateUITwoPane(videoUrl, mStepsShortDescription.get(mStepIndex));
                            }
                        });
                    }
                });
        } else {

            mTwoPane = false;
            // As we have the recipe index, we can obtain the info here, avoiding passing complex objects
            // such as Recipe or Step using intents
            AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mIngredients = mDatabase.ingredientDAO().getIngredients(mRecipeIndex);
                    mStepsShortDescription = mDatabase.stepDAO().getShortDescription(mRecipeIndex);
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

        if(mOrientation == Configuration.ORIENTATION_LANDSCAPE && !mTwoPane){
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

        ingredientListFragment.setIngredients(mIngredients);

        fragmentManager.beginTransaction().add(R.id.ingredients_frame_layout, ingredientListFragment).commit();
        fragmentManager.beginTransaction().add(R.id.steps_frame_layout, stepListFragment).commit();

    }

    /**
     * This method is call to populate the UI when running on a tablet
     */

    private void populateUITwoPane(String videoURL, String stepDescription){
        FragmentManager fragmentManager = getSupportFragmentManager();

        VideoFragment mVideoFragment = new VideoFragment();

        mVideoFragment.setMediaURL(videoURL);

        StepInstructionFragment mStepInstructionFragment = new StepInstructionFragment();

        mStepInstructionFragment.setStepInstruction(stepDescription);

        fragmentManager.beginTransaction().replace(R.id.video_frame_layout, mVideoFragment).commit();

        fragmentManager.beginTransaction().replace(
                R.id.step_description_frame_layout,
                mStepInstructionFragment).commit();
    }

    @Override
    public void onItemSelected(final int pos) {


        // If we are in tablet. We want to create new Video and StepDescriptionFragments and replace
        // the current one.
        // If we are in a phone, launch a StepDetailActivity

        if(mTwoPane) {

            //Update current step index for correct bevahour while rotation

            mStepIndex = pos;

            final FragmentManager fragmentManager = getSupportFragmentManager();

            final VideoFragment videoFragment = new VideoFragment();
            final StepInstructionFragment stepInstructionFragment = new StepInstructionFragment();

            AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final String videoUrl = mDatabase.stepDAO().getVideoURL(mRecipeIndex,mStepIndex);
                    final String stepDescription = mDatabase.stepDAO().getDescription(mRecipeIndex,mStepIndex);

                    videoFragment.setMediaURL(videoUrl);

                    stepInstructionFragment.setStepInstruction(stepDescription);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            fragmentManager.beginTransaction().
                                    replace(R.id.video_frame_layout, videoFragment).commit();

                            fragmentManager.beginTransaction().replace(
                                    R.id.step_description_frame_layout,
                                    stepInstructionFragment).commit();
                        }
                    });
                }
            });

        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);

            intent.putExtra(STEP_INDEX_KEY, pos);
            intent.putExtra(RECIPE_INDEX_KEY, mRecipeIndex);
            startActivity(intent);
        }

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
        outState.putInt(STEP_INDEX_KEY, mStepIndex);
    }
}

































