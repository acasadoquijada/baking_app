package com.example.backing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.ui.RecipeFragment;
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.utils.NetworkUtils;
import com.example.backing_app.utils.RecipesUtils;
import com.example.backing_app.viewmodel.RecipeViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Recipe> mRecipes;
    private RecipeViewModel mRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        AppExecutorUtils.getsInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                mRecipes = mRecipeViewModel.getRecipes();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setFragments();
                    }
                });
            }
        });
    }

    private void setFragments() {

        RecipeFragment recipeFragment = new RecipeFragment(mRecipes.get(0));
        RecipeFragment recipeFragment2 = new RecipeFragment(mRecipes.get(1));
        RecipeFragment recipeFragment3 = new RecipeFragment(mRecipes.get(2));
        RecipeFragment recipeFragment4 = new RecipeFragment(mRecipes.get(3));
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.recipe_1, recipeFragment).commit();
        fragmentManager.beginTransaction().add(R.id.recipe_2, recipeFragment2).commit();
        fragmentManager.beginTransaction().add(R.id.recipe_3, recipeFragment3).commit();
        fragmentManager.beginTransaction().add(R.id.recipe_4, recipeFragment4).commit();
    }
}