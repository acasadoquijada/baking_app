package com.example.backing_app.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.backing_app.R;
import com.example.backing_app.recipe.Step;

import java.util.List;

public class MasterListFragment extends Fragment implements MasterListAdapter.ItemClickListener {

    private static String TAG = MasterListFragment.class.getSimpleName();

    private List<Step> mStepsList;

    // Mandatory empty constructor
    public MasterListFragment() {

    }

    public void setStepsList(List<Step> stepsList) {
        this.mStepsList = stepsList;
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);

        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);

        MasterListAdapter mAdapter = new MasterListAdapter(getContext(), mStepsList,this);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    /*
        RecyclerView mMovieGrid = findViewById(R.id.MovieRecyclerView);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columns);
    mMovieGrid.setLayoutManager(gridLayoutManager);
    mAdapter = new MovieAdapter(this);
    mMovieGrid.setAdapter(mAdapter);

     */
    @Override
    public void onItemClick(int clickedItemIndex) {

        // I set all the Fragments as I want //Video, instructions etc..

        Log.d(TAG,mStepsList.get(clickedItemIndex).getShortDescription());
    }
}