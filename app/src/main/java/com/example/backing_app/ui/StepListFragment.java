package com.example.backing_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.backing_app.R;
import com.example.backing_app.StepDetailActivity;

import java.util.List;

public class StepListFragment extends Fragment implements StepListAdapter.ItemClickListener {

    private static String TAG = StepListFragment.class.getSimpleName();

    private static final String orientation_token = "orientation";
    private static final String span_count_token = "span_count";

    public static final String STEP_INDEX_KEY = "step_index";
    public static final String RECIPE_INDEX_KEY = "recipe_index";

    private List<String> mStepsShortDescription;
    private int mOrientation;
    private int mSpanCount;
    private int mRecipeIndex;


    public StepListFragment() {

    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public void setSpanCount(int spanCount) {
        this.mSpanCount = spanCount;
    }

    public void setStepsShortDescription(List<String> stepsShortDescription) {
        this.mStepsShortDescription = stepsShortDescription;
    }

    public void setRecipeIndex(int recipeIndex) {
        this.mRecipeIndex = recipeIndex;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mOrientation = savedInstanceState.getInt(orientation_token);
            mSpanCount = savedInstanceState.getInt(span_count_token);
        }

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.step_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), mSpanCount);

        gridLayoutManager.setOrientation(mOrientation);

        recyclerView.setLayoutManager(gridLayoutManager);

        StepListAdapter mAdapter = new StepListAdapter(mStepsShortDescription,this);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(int clickedItemIndex) {

        // Here I will call to an Activity containing 2 fragments
        // A fragment for the video/image and a another for the description

        // This is for the new Activity. Then it will load the step from the DB
        // mStepsIndex.get(clickedItemIndex);

        Intent intent = new Intent(getActivity(), StepDetailActivity.class);

        intent.putExtra(STEP_INDEX_KEY, clickedItemIndex);
        intent.putExtra(RECIPE_INDEX_KEY, mRecipeIndex);
        startActivity(intent);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt(orientation_token, mOrientation);
        outState.putInt(span_count_token, mSpanCount);
    }
}