package com.example.backing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Step;
import com.example.backing_app.fragment.StepInstructionFragment;
import com.example.backing_app.fragment.VideoFragment;
import com.example.backing_app.utils.AppExecutorUtils;

import static com.example.backing_app.fragment.StepListFragment.RECIPE_INDEX_KEY;
import static com.example.backing_app.fragment.StepListFragment.STEP_INDEX_KEY;

/**
 * This Activity provides to the user with more info of a step. This info is:
 * - Video (if available)
 * - Description
 * In addition, two navigation buttons are added to easily move between steps
 */
public class StepDetailActivity extends AppCompatActivity {

    private int mRecipeIndex;
    private int mStepIndex;
    private Step step;
    private RecipeDataBase mDatabase;
    private VideoFragment mVideoFragment;
    private StepInstructionFragment mStepInstructionFragment;

    private static String TAG = StepDetailActivity.class.getSimpleName();

    /**
     * First we setup the navigation buttons and get an instance of the RecipeDataBase
     * The VideoFragment and StepInstructionFragment are created if needed
     * @param savedInstanceState bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        setupNavigationButtons();

        mDatabase = RecipeDataBase.getInstance(this);

        // Create only new fragments when no previous instance saved

        if(savedInstanceState != null){
            mStepIndex = savedInstanceState.getInt(STEP_INDEX_KEY);
            mRecipeIndex = savedInstanceState.getInt(RECIPE_INDEX_KEY);
        } else {
            ActionBar actionBar = this.getSupportActionBar();

            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            Intent intent;

            intent = getIntent();

            mRecipeIndex = intent.getIntExtra(RECIPE_INDEX_KEY, 0);
            mStepIndex = intent.getIntExtra(STEP_INDEX_KEY, 0);

            AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    step = mDatabase.stepDAO().getStep(mRecipeIndex, mStepIndex);
                    Log.d(TAG, step.getShortDescription());

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

    private void setupNavigationButtons(){
        Button button = findViewById(R.id.next_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });

        Button button1 = findViewById(R.id.previous_button);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                previousStep();
            }
        });

    }
    private void populateUI(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        mVideoFragment = new VideoFragment();

        if(!step.getVideoURL().equals("")){
            mVideoFragment.setMediaURL(step.getVideoURL());
        } else if(!step.getThumbailURL().equals("")){
            mVideoFragment.setMediaURL(step.getThumbailURL());
        } else {
            mVideoFragment.setMediaURL(getString(R.string.step_no_video));
        }

        mStepInstructionFragment = new StepInstructionFragment();

        mStepInstructionFragment.setStepInstruction(step.getDescription());

        fragmentManager.beginTransaction().add(R.id.video_frame_layout,mVideoFragment).commit();

        fragmentManager.beginTransaction().add(
                R.id.step_description_frame_layout,
                mStepInstructionFragment).commit();
    }

    /**
     * Method run when the user clicks "previous button"
     */
    private void previousStep(){

        // We know steps start at index 0, so we take advantage of this
        final int previousStepIndex = mStepIndex - 1;

        if(previousStepIndex >= 0){
            Intent intent = new Intent(getApplicationContext(), StepDetailActivity.class);

            intent.putExtra(STEP_INDEX_KEY, previousStepIndex);
            intent.putExtra(RECIPE_INDEX_KEY, mRecipeIndex);
            startActivity(intent);
        } else {
            Toast.makeText(
                    StepDetailActivity.this,
                    "This is the first step. No previous steps",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Method run when the user clicks "next button"
     */

    private void nextStep(){
        AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                // We need to check in the DB if the step n+1 exists, if so a new StepDetailActivity
                // is launched. Otherwise a Toast is shown
                final int nextStepIndex = mStepIndex + 1;

                Step s = mDatabase.stepDAO().getStep(mRecipeIndex, nextStepIndex);

                if (s != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), StepDetailActivity.class);

                            intent.putExtra(STEP_INDEX_KEY, nextStepIndex);
                            intent.putExtra(RECIPE_INDEX_KEY, mRecipeIndex);
                            startActivity(intent);
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Make toast, no more steps
                            Toast.makeText(
                                    StepDetailActivity.this,
                                    "This is the last step. No more steps",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_INDEX_KEY, mStepIndex);
        outState.putInt(RECIPE_INDEX_KEY, mRecipeIndex);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}