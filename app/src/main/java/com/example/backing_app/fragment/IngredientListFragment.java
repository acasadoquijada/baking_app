package com.example.backing_app.fragment;

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

import java.util.List;

/**
 * This Fragment class is in charge of representing the ingredients in the RecipeDetailActivity
 * */

public class IngredientListFragment extends Fragment {

    private static final String orientation_token = "orientation";
    private static final String span_count_token = "span_count";

    private List<Ingredient> mIngredient;
    private int mOrientation;
    private int mSpanCount;

    public IngredientListFragment(){

    }

    public void setIngredients(List<Ingredient> ingredients){
        mIngredient = ingredients;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public void setSpanCount(int spanCount) {
        this.mSpanCount = spanCount;
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
            mOrientation = savedInstanceState.getInt(orientation_token);
            mSpanCount = savedInstanceState.getInt(span_count_token);
        }

        final View rootView = inflater.inflate(R.layout.fragment_list_layout, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), mSpanCount);

        gridLayoutManager.setOrientation(mOrientation);

        recyclerView.setLayoutManager(gridLayoutManager);

        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(mIngredient);

        recyclerView.setAdapter(ingredientListAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(orientation_token, mOrientation);
        outState.putInt(span_count_token, mSpanCount);
    }
}
