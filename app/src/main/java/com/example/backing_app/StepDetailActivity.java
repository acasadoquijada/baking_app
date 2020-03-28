package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Step;
import com.example.backing_app.ui.StepListFragment;
import com.example.backing_app.ui.VideoFragment;
import com.example.backing_app.utils.AppExecutorUtils;

public class StepDetailActivity extends AppCompatActivity {

    private int recipe_index;
    private int step_index;
    private Step step;

    private static String TAG = StepDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent intent;

        intent = getIntent();

        recipe_index = intent.getIntExtra(StepListFragment.RECIPE_INDEX_KEY,0);
        step_index = intent.getIntExtra(StepListFragment.STEP_INDEX_KEY,0);

        final RecipeDataBase mDatabase = RecipeDataBase.getInstance(this);

        AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                step = mDatabase.stepDAO().getStep(recipe_index,step_index);
                Log.d(TAG,step.getShortDescription());

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
        FragmentManager fragmentManager = getSupportFragmentManager();

        VideoFragment videoFragment = new VideoFragment();

        if(!step.getVideoURL().equals("")){
            videoFragment.setMediaURL(step.getVideoURL());
        } else if(!step.getThumbailURL().equals("")){
            videoFragment.setMediaURL(step.getThumbailURL());
        } else {
            videoFragment.setMediaURL(getString(R.string.step_no_video));
        }

        fragmentManager.beginTransaction().add(R.id.video_frame_layout,videoFragment).commit();



        /*
        TextView text1 = findViewById(R.id.step_detail_video);
        TextView text2 = findViewById(R.id.step_detail_instruction);

        if(!step.getVideoURL().equals("")){
            text1.setText(step.getVideoURL());
        } else if(!step.getThumbailURL().equals("")){
            text1.setText(step.getThumbailURL());
        } else {
            text1.setText(R.string.step_no_video);
        }

        text2.setText(step.getDescription());*/

    }
}