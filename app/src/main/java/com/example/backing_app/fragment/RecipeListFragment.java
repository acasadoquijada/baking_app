package com.example.backing_app.fragment;

import android.content.Intent;
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
import com.example.backing_app.RecipeDetailActivity;
import java.util.List;

/**
 * This Fragment class is in charge of representing the recipes in the MainActivity
 * */

public class RecipeListFragment extends Fragment implements RecipeListAdapter.ItemClickListener{

    private static final String orientation_token = "orientation";
    private static final String span_count_token = "span_count";
    public static final String RECIPE_ID_KEY = "recipe_id";

    private List<String> mRecipesName;
    private List<String> mRecipesServing;

    private int mOrientation;
    private int mSpanCount;

    public RecipeListFragment(){

    }

    public void setRecipesName(List<String> mRecipesName) {
        this.mRecipesName = mRecipesName;
    }

    public void setRecipesServing(List<String> mRecipesServing) {
        this.mRecipesServing = mRecipesServing;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public void setSpanCount(int spanCount) {
        this.mSpanCount = spanCount;
    }


    /**
     * A RecyclerView is created and populated with the recipes information. For this task, a
     * RecipeListAdapter is used
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState bundle
     * @return a view with a RecyclerView with the recipes information
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

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(mRecipesName,mRecipesServing,this );

        recyclerView.setAdapter(recipeListAdapter);

        return rootView;
    }

    /**
     * When the user clicks a recipe, a RecipeDetailActivity is launched with the recipe_index of
     * the desired recipe. We take advantage that the recipe indexes start in 1 instead of 0
     * @param clickedItemIndex index of the recipe clicked
     */
    @Override
    public void onItemClick(int clickedItemIndex) {

        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);

        intent.putExtra(RECIPE_ID_KEY,(clickedItemIndex+1)); // Recipe index starts in 1 instead of 0

        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(orientation_token, mOrientation);
        outState.putInt(span_count_token, mSpanCount);
    }
}