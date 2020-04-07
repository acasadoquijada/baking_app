package com.example.backing_app.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.backing_app.R;
import com.example.backing_app.recipe.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * This Fragment class is in charge of representing the ingredients in the RecipeDetailActivity
 * */

public class IngredientListFragment extends Fragment {

    private static final String INGREDIENTS = "ingredients";
    private static final String TWO_PANE = "two_pane";

    private List<Ingredient> mIngredient;
    private boolean mTwoPane;

    public IngredientListFragment(){

    }

    public void setTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    public void setIngredients(List<Ingredient> ingredients){
        mIngredient = ingredients;
    }


    /**
     * A RecyclerView is created and populated with the ingredients information. For this task, a
     * RecipeListAdapter is used
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState bundle
     * @return a view with a RecyclerView with the ingredients information
     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mIngredient = savedInstanceState.getParcelableArrayList(INGREDIENTS);
            mTwoPane = savedInstanceState.getBoolean(TWO_PANE);
        }

        final View rootView = inflater.inflate(R.layout.fragment_list_layout, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_list);

        int orientation = getResources().getConfiguration().orientation;

        int spanCount = 1;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);

        if(orientation == Configuration.ORIENTATION_LANDSCAPE && !mTwoPane){
            gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            spanCount = 3;
        } else{
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
            spanCount = 1;
        }

        gridLayoutManager.setSpanCount(spanCount);

        recyclerView.setLayoutManager(gridLayoutManager);

        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(mIngredient);

        recyclerView.setAdapter(ingredientListAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(INGREDIENTS,(ArrayList<Ingredient>)mIngredient);
        outState.putBoolean(TWO_PANE,mTwoPane);
    }
}
