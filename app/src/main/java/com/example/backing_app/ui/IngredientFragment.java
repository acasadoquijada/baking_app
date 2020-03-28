package com.example.backing_app.ui;

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

public class IngredientFragment extends Fragment {

    private static final String orientation_token = "orientation";
    private static final String span_count_token = "span_count";

    private List<Ingredient> mIngredient;
    private int mOrientation;
    private int mSpanCount;

    public IngredientFragment(){

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mOrientation = savedInstanceState.getInt(orientation_token);
            mSpanCount = savedInstanceState.getInt(span_count_token);
        }

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.step_list);

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
