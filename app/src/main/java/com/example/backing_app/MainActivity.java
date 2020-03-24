package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.ui.RecipeFragment;
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.viewmodel.RecipeViewModel;

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
                        populateUI();
                    }
                });
            }
        });
    }

    /**
     * Creates all the RecipeFragment needed for the recipes
     */

    private void populateUI() {

        if (mRecipes.size() > 0) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            int recipes_number = mRecipes.size();
            List<Integer> ids = new ArrayList<>();

            ids.add(R.id.recipe_1);
            ids.add(R.id.recipe_2);
            ids.add(R.id.recipe_3);
            ids.add(R.id.recipe_4);

            for (int i = 0; i < recipes_number; i++) {
                RecipeFragment r = new RecipeFragment();
                r.setRecipeIndex(mRecipes.get(i).getId());
                r.setRecipeName(mRecipes.get(i).getName());
                r.setRecipeServing(mRecipes.get(i).getServings());

                fragmentManager.beginTransaction().add(ids.get(i), r).commit();
            }
        }
    }
}