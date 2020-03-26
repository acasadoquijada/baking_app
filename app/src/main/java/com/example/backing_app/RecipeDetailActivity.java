package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.recipe.Step;
import com.example.backing_app.ui.MasterListFragment;
import com.example.backing_app.ui.RecipeFragment;
import com.example.backing_app.ui.StepFragment;
import com.example.backing_app.utils.AppExecutorUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private RecipeDataBase mDatabase;

    private List<Step> mSteps;
    private List<Ingredient> mIngredients;

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
                mSteps = mDatabase.stepDAO().getSteps(recipe_index);
                mIngredients = mDatabase.ingredientDAO().getIngredients(recipe_index);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUpFragments();
                        //setupIngredients();
                        //setUpSteps();
                    }
                });
            }
        });
    }

    private void setUpFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        MasterListFragment masterListFragment = new MasterListFragment();

        masterListFragment.setStepsList(mSteps); // this can be an step index

        fragmentManager.beginTransaction().add(R.id.step_1,masterListFragment).commit();
    }

    private void setUpSteps(){

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            StepFragment stepFragment = new StepFragment();
            stepFragment.setShortDescription(mSteps.get(0).getShortDescription());
            fragmentManager.beginTransaction().add(R.id.step_1, stepFragment).commit();
        }
    }


    private TextView createIngredientsLabel(){

        TextView ingredientsLabelTextView = new TextView(this);

        ingredientsLabelTextView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ingredientsLabelTextView.setTextSize(32);
        ingredientsLabelTextView.setTypeface(Typeface.DEFAULT_BOLD);

        ingredientsLabelTextView.setText(getString(R.string.ingredients_label));

        return ingredientsLabelTextView;
    }

    private void setupIngredients(){

        //  We get the parent layout according to the screen orientation

        int orientation = getResources().getConfiguration().orientation;

        ViewGroup ingredientsLinearLayout;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ingredientsLinearLayout = findViewById(R.id.ingredients_grid_layout);
        } else {
            ingredientsLinearLayout = findViewById(R.id.ingredients_linear_layout);
        }


        // Create section label
        TextView ingredientsLabelTextView = createIngredientsLabel();

        ingredientsLinearLayout.addView(ingredientsLabelTextView, ingredientsLinearLayout.getChildCount());
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

            ingredientsLinearLayout.addView(ingredientLayout, ingredientsLinearLayout.getChildCount());
        }
    }

    private void setUpUI(){


    }

}

































