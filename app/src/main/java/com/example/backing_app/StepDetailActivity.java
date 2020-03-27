package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Step;
import com.example.backing_app.ui.MasterListFragment;
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

        recipe_index = intent.getIntExtra(MasterListFragment.RECIPE_INDEX_KEY,0);
        step_index = intent.getIntExtra(MasterListFragment.STEP_INDEX_KEY,0);

        Log.d(TAG,"recipe_index: " + recipe_index);
        Log.d(TAG,"step_index: " + step_index);

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
        TextView text1 = findViewById(R.id.step_detail_video);
        TextView text2 = findViewById(R.id.step_detail_instruction);

        if(!step.getVideoURL().equals("")){
            text1.setText(step.getVideoURL());
        } else if(!step.getThumbailURL().equals("")){
            text1.setText(step.getThumbailURL());
        } else {
            text1.setText(R.string.step_no_video);
        }

        text2.setText(step.getDescription());

    }
}