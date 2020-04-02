package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.fragment.RecipeListFragment;
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.viewmodel.RecipeViewModel;

import java.util.List;

import static com.example.backing_app.fragment.RecipeListFragment.RECIPE_ID_KEY;


/**
 * Main class. It's in charge of downloading and storing the recipe data in the database
 * Once the data is available, it creates a RecipeListFragment providing just the necessary info
 * in this case, the recipe names and their serving number
 */

public class MainActivity extends AppCompatActivity implements RecipeListFragment.onGridElementClick{

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<String> mRecipesName;
    private List<String> mRecipesServing;
    private RecipeViewModel mRecipeViewModel;
    private RecipeDataBase mDatabase;

    private int orientation;


    /**
     * Loads the recipe data (if not in database, it's downloaded and stored) using a ViewModel
     * by doing so, we ensure there is no issues if this Activity get destroyed (rotation)
     *
     * Once we have the data, we populate the UI using a RecipeListFragment
     * @param savedInstanceState bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        orientation = getResources().getConfiguration().orientation;

        mDatabase = RecipeDataBase.getInstance(this);

        AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mRecipeViewModel.loadData();
                mRecipesName = mDatabase.recipeDAO().getRecipesName();
                mRecipesServing = mDatabase.recipeDAO().getRecipesServing();

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
     * Creates a RecipeListFragment providing the necessary information about the recipes:
     *
     * - name
     * - servings
     *
     * In addition, it provides the screen orientation and the spanCount to ensure a responsive
     * layout
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

    @Override
    public void onItemSelected(int pos) {

        Intent intent = new Intent(this, RecipeDetailActivity.class);

        intent.putExtra(RECIPE_ID_KEY,(pos+1)); // Recipe index starts in 1 instead of 0

        startActivity(intent);

    }
}