package com.example.backing_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.backing_app.R;
import com.example.backing_app.recipe.Recipe;

public class RecipeFragment extends Fragment {

    private static final String TAG = RecipeFragment.class.getSimpleName();

    private Recipe recipe;

    public RecipeFragment(){

    }

    public RecipeFragment(Recipe recipe){
        this.recipe = recipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
}
