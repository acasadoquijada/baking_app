package com.example.backing_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.backing_app.R;
import com.example.backing_app.StepDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class StepListFragment extends Fragment implements StepListAdapter.ItemClickListener {

    private static String TAG = StepListFragment.class.getSimpleName();

    private static final String orientation_token = "orientation";
    private static final String span_count_token = "span_count";

    public static final String STEP_INDEX_KEY = "step_index";
    public static final String RECIPE_INDEX_KEY = "recipe_index";
    public static final String STEP_SHORT_DESCRIPTION = "step_short_description";
    public static final String TWO_PANE="two_pane";

    private List<String> mStepsShortDescription;
    private int mSpanCount;
    private boolean mTwoPane;

    private onGridElementClick mCallback;

    public interface onGridElementClick{
        void onItemSelected(int pos);
    }

    public StepListFragment() {

    }

    public void setTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
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

    public void setSpanCount(int spanCount) {
        this.mSpanCount = spanCount;
    }

    public void setStepsShortDescription(List<String> stepsShortDescription) {
        this.mStepsShortDescription = stepsShortDescription;
    }

    /**
     * A RecyclerView is created and populated with the steps information. For this task, a
     * StepListAdapter is used
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState bundle
     * @return a view with a RecyclerView with the ingredients information
     */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mSpanCount = savedInstanceState.getInt(span_count_token);
            mStepsShortDescription = savedInstanceState.getStringArrayList(STEP_SHORT_DESCRIPTION);
            mTwoPane = savedInstanceState.getBoolean(TWO_PANE);
        }

        final View rootView = inflater.inflate(R.layout.fragment_list_layout, container, false);

        int orientation = getResources().getConfiguration().orientation;

        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);

        if(orientation == Configuration.ORIENTATION_LANDSCAPE && !mTwoPane){
            gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            mSpanCount = 3;
        } else {
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
            mSpanCount = 1;
        }

        gridLayoutManager.setSpanCount(mSpanCount);

        recyclerView.setLayoutManager(gridLayoutManager);

        StepListAdapter mAdapter = new StepListAdapter(mStepsShortDescription,this);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(int clickedItemIndex) {

        mCallback.onItemSelected(clickedItemIndex);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(span_count_token, mSpanCount);
        outState.putStringArrayList(STEP_SHORT_DESCRIPTION, (ArrayList<String>) mStepsShortDescription);
        outState.putBoolean(TWO_PANE,mTwoPane);
    }
}