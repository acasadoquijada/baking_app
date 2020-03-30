package com.example.backing_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.backing_app.R;

/**
 * This Fragment class is in charge of representing the recipes in the MainActivity
 * */

public class StepInstructionFragment extends Fragment {

    private String mStepInstruction;
    private static final String STEP_DESCRIPTION_KEY="step_description";

    public StepInstructionFragment(){

    }

    public void setStepInstruction(String mStepInstruction) {
        this.mStepInstruction = mStepInstruction;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mStepInstruction = savedInstanceState.getString(STEP_DESCRIPTION_KEY);
        }
        View rootView = inflater.inflate(R.layout.step_instruction_fragment,container,false);

        TextView step = rootView.findViewById(R.id.step_description);

        step.setText(mStepInstruction);
        
        return rootView;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STEP_DESCRIPTION_KEY,mStepInstruction);
    }
}
