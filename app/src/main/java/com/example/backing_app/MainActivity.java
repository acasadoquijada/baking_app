package com.example.backing_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import static androidx.appcompat.app.AlertDialog.*;
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

    private RecipeDataBase mDatabase;

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

        final RecipeViewModel mRecipeViewModel =
                new ViewModelProvider(this).get(RecipeViewModel.class);

        mDatabase = RecipeDataBase.getInstance(this);

        // Only create Fragment if need it
        if(savedInstanceState == null){

            AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {


                    if(mRecipeViewModel.loadData()){
                        mRecipesName = mDatabase.recipeDAO().getRecipesName();
                        mRecipesServing = mDatabase.recipeDAO().getRecipesServing();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showNoConnectionMessage();
                            }
                        });
                    }
                }
            });

        }
    }

    /**
     * Creates a RecipeListFragment providing the necessary information about the recipes:
     *
     * - name
     * - servings
     */

    private void populateUI(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeListFragment recipeListFragment = new RecipeListFragment();

        recipeListFragment.setRecipesName(mRecipesName);
        recipeListFragment.setRecipesServing(mRecipesServing);

        fragmentManager.beginTransaction().add(R.id.recipes_frame_layout,recipeListFragment).commit();
    }

    private void showNoConnectionMessage(){
        Builder builder = new Builder(this);
        builder.setMessage("There is no internet connection. " +
                "\nPlease connect to the internet and restart the application");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                        System.exit(0);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onItemSelected(int pos) {

        Intent intent = new Intent(this, RecipeDetailActivity.class);

        intent.putExtra(RECIPE_ID_KEY,(pos+1)); // Recipe index starts in 1 instead of 0

        startActivity(intent);

    }
}