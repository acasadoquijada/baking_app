package com.example.backing_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.backing_app.R;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.viewmodel.RecipeViewModel;


public class RecipeFragment extends Fragment {

    private static final String TAG = RecipeFragment.class.getSimpleName();

    public static final String RECIPE_KEY = "recipe";

    private Recipe recipe;
    private RecipeViewModel mRecipeViewModel;
    private int mRecipeId;
    public RecipeFragment(){
  //      mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
    }

    public RecipeFragment(Recipe recipe){
        this.recipe = recipe;
    }

    public RecipeFragment(int index){
        this.mRecipeId = index;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_KEY);
        }

        if(recipe != null)
            mRecipeId = recipe.getId();

        View rootView = inflater.inflate(R.layout.fragment_recipe,container,false);

        CardView cardView = rootView.findViewById(R.id.recipe_card_view);

        TextView recipeTextView = cardView.findViewById(R.id.recipe_name);

        TextView recipeServingView = cardView.findViewById(R.id.recipe_serving);

        recipeTextView.setText(recipe.getName());

        recipeServingView.setText(getString(R.string.serving_prefix));

        recipeServingView.append( " " + recipe.getServings() + " ");

        recipeServingView.append(getString(R.string.serving_suffix));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,recipe.getName());
            }
        });

        return rootView;
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(RECIPE_KEY,recipe);
    }
}
