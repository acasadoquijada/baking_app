package com.example.backing_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.backing_app.R;
import com.example.backing_app.recipe.Step;

import java.util.List;

public class MasterListFragment extends Fragment implements MasterListAdapter.ItemClickListener {

    private static String TAG = MasterListFragment.class.getSimpleName();

    private static final String orientation_token = "orientation";
    private static final String span_count_token = "span_count";

    private List<String> mStepsShortDescription;
    private List<Integer> mStepsIndex;
    private int mOrientation;
    private int mSpanCount;


    public MasterListFragment() {

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

    public void setStepsIndex(List<Integer> stepsIndex) {
        this.mStepsIndex = stepsIndex;
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mOrientation = savedInstanceState.getInt(orientation_token);
            mSpanCount = savedInstanceState.getInt(span_count_token);
        }

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);

        gridLayoutManager.setOrientation(mOrientation);

        gridLayoutManager.setSpanCount(mSpanCount);

        recyclerView.setLayoutManager(gridLayoutManager);

        MasterListAdapter mAdapter = new MasterListAdapter(mStepsShortDescription,this);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(int clickedItemIndex) {

        // Here I will call to an Activity containing 2 fragments
        // A fragment for the video/image and a another for the description

        // This is for the new Activity. Then it will load the step from the DB
        // mStepsIndex.get(clickedItemIndex) ;

        Log.d(TAG, mStepsShortDescription.get(clickedItemIndex));
        Log.d(TAG, String.valueOf(mStepsIndex.get(clickedItemIndex)));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt(orientation_token, mOrientation);
        outState.putInt(span_count_token, mSpanCount);
    }
}