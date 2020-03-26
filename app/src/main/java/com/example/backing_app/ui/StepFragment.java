package com.example.backing_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.backing_app.R;

public class StepFragment extends Fragment {

    private String mShortDescription;

    private static String TAG = StepFragment.class.getSimpleName();

    public StepFragment(){

    }

    public void setShortDescription(String shortDescription){
        mShortDescription = shortDescription;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step,container,false);

        TextView text = rootView.findViewById(R.id.recipe_short_description);

        text.setText(mShortDescription);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, mShortDescription);
            }
        });
        return rootView;
    }
}
