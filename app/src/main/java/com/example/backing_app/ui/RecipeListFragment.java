package com.example.backing_app.ui;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mOrientation = savedInstanceState.getInt(orientation_token);
            mSpanCount = savedInstanceState.getInt(span_count_token);
        }

        final View rootView = inflater.inflate(R.layout.recipe_list, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), mSpanCount);

        gridLayoutManager.setOrientation(mOrientation);

        recyclerView.setLayoutManager(gridLayoutManager);

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(mRecipesName,mRecipesServing,this );

        recyclerView.setAdapter(recipeListAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(int clickedItemIndex) {

        // I start the activity

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