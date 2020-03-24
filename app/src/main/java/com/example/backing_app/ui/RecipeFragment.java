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
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.viewmodel.RecipeViewModel;


public class RecipeFragment extends Fragment {

    private static final String TAG = RecipeFragment.class.getSimpleName();

    public static final String RECIPE_TOKEN_ID = "recipe_id";
    public static final String RECIPE_NAME_KEY = "recipe_name";
    public static final String RECIPE_ID_KEY = "recipe_id";
    public static final String RECIPE_SERVING_KEY = "recipe_serving";

    private int mRecipeIndex;
    private String mRecipeName;
    private String mRecipeServing;

    public RecipeFragment(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mRecipeName = savedInstanceState.getString(RECIPE_NAME_KEY);
            mRecipeIndex = savedInstanceState.getInt(RECIPE_ID_KEY);
            mRecipeServing = savedInstanceState.getString(RECIPE_SERVING_KEY);

        }

        View rootView = inflater.inflate(R.layout.fragment_recipe,container,false);

        CardView cardView = rootView.findViewById(R.id.recipe_card_view);

        TextView recipeTextView = cardView.findViewById(R.id.recipe_name);

        TextView recipeServingView = cardView.findViewById(R.id.recipe_serving);

        recipeTextView.setText(mRecipeName);

        recipeServingView.setText(getString(R.string.serving_prefix));

        recipeServingView.append( " " + mRecipeServing + " ");

        recipeServingView.append(getString(R.string.serving_suffix));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,mRecipeName);
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);

                intent.putExtra(RECIPE_ID_KEY, mRecipeIndex);

                startActivity(intent);
            }
        });

        return rootView;
    }

    public void setRecipeIndex(int mRecipeIndex) {
        this.mRecipeIndex = mRecipeIndex;
    }

    public void setRecipeName(String mRecipeName) {
        this.mRecipeName = mRecipeName;
    }

    public void setRecipeServing(String mRecipeServing) {
        this.mRecipeServing = mRecipeServing;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString(RECIPE_NAME_KEY,mRecipeName);
        currentState.putInt(RECIPE_ID_KEY,mRecipeIndex);
        currentState.putString(RECIPE_SERVING_KEY,mRecipeServing);
    }
}
