package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.ui.RecipeListFragment;
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.viewmodel.RecipeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public List<String> mRecipesName;
    public List<String> mRecipesServing;
    private RecipeViewModel mRecipeViewModel;
    int orientation;

    private RecipeDataBase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "I CREATE");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        orientation = getResources().getConfiguration().orientation;

        mDatabase = RecipeDataBase.getInstance(this);

        AppExecutorUtils.getsInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                mRecipeViewModel.loadData();
                mRecipesName = mDatabase.recipeDAO().getRecipesName();
                mRecipesServing = mDatabase.recipeDAO().getRecipesServing();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // populateUINew();
                         populateUI();
                    }
                });
            }
        });


    }

    /**
     * Creates all the RecipeFragment needed for the recipes
     */

    private void populateUI(){


        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeListFragment recipeListFragment = new RecipeListFragment();

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            recipeListFragment.setSpanCount(2);
        } else{
            recipeListFragment.setSpanCount(1);
        }

        recipeListFragment.setOrientation(LinearLayout.VERTICAL);
        recipeListFragment.setRecipesName(mRecipesName);
        recipeListFragment.setRecipesServing(mRecipesServing);

        fragmentManager.beginTransaction().add(R.id.recipes_frame_layout,recipeListFragment).commit();
    }
    /*
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
    }*/
}