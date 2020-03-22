package com.example.backing_app.ui;

import android.content.Context;
import android.content.Intent;
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
import com.example.backing_app.RecipeDetailActivity;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.viewmodel.RecipeViewModel;


public class RecipeFragment extends Fragment {

    private static final String TAG = RecipeFragment.class.getSimpleName();

    public static final String RECIPE_KEY = "recipe";
    public static final String BUNDLE_TOKEN = "recipe_id";
    public static final String RECIPE_TOKEN_ID = "recipe_id";

    private Recipe mRecipe;
    private RecipeViewModel mRecipeViewModel;
    private int mRecipeIndex;

    public RecipeFragment(){
    }

    public RecipeFragment(Recipe recipe){
        this.mRecipe = recipe;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RECIPE_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_recipe,container,false);

        CardView cardView = rootView.findViewById(R.id.recipe_card_view);

        TextView recipeTextView = cardView.findViewById(R.id.recipe_name);

        TextView recipeServingView = cardView.findViewById(R.id.recipe_serving);

        recipeTextView.setText(mRecipe.getName());

        recipeServingView.setText(getString(R.string.serving_prefix));

        recipeServingView.append( " " + mRecipe.getServings() + " ");

        recipeServingView.append(getString(R.string.serving_suffix));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,mRecipe.getName());
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);

                intent.putExtra(RECIPE_TOKEN_ID, getRecipe().getId());

                startActivity(intent);
            }
        });

        return rootView;
    }


    private Recipe getRecipe() {
        return mRecipe;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(RECIPE_KEY,mRecipe);
    }
}
