package com.example.backing_app.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.example.backing_app.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * This Fragment class is in charge of representing the recipes in the MainActivity
 * */

public class RecipeListFragment extends Fragment implements RecipeListAdapter.ItemClickListener{

    public static final String RECIPE_ID_KEY = "recipe_index";
    private static final String RECIPES_NAME = "recipes_name";
    private static final String RECIPES_SERVING = "recipes_serving";

    private List<String> mRecipesName;
    private List<String> mRecipesServing;
    private onGridElementClick mCallback;

    public interface onGridElementClick{
        void onItemSelected(int pos);
    }
    public RecipeListFragment(){

    }

    public void setRecipesName(List<String> mRecipesName) {
        this.mRecipesName = mRecipesName;
    }

    public void setRecipesServing(List<String> mRecipesServing) {
        this.mRecipesServing = mRecipesServing;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (onGridElementClick) context;
        }catch (ClassCastException e){
            throw new ClassCastException(
                    context.toString() + "must implement onGridElementClick interface");
        }
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
            mRecipesServing = savedInstanceState.getStringArrayList(RECIPES_SERVING);
            mRecipesName = savedInstanceState.getStringArrayList(RECIPES_NAME);
        }

        int orientation = getResources().getConfiguration().orientation;

        int spanCount;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 2;
        } else{
            spanCount = 1;
        }

        final View rootView = inflater.inflate(R.layout.fragment_list_layout, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);

        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

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
        mCallback.onItemSelected(clickedItemIndex);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putStringArrayList(RECIPES_NAME,(ArrayList<String>) mRecipesName);
        outState.putStringArrayList(RECIPES_SERVING,(ArrayList<String>) mRecipesServing);
    }
}