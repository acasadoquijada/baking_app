package com.example.backing_app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.backing_app.R;

public class StepInstructionFragment extends Fragment {

    private String mStepInstruction;
    private onStepDescriptionChangedListener mCallback;


    public interface onStepDescriptionChangedListener{
        void onStepDescriptionChanged(String description);
    }

    public StepInstructionFragment(){

    }

    public void setStepInstruction(String mStepInstruction) {
        this.mStepInstruction = mStepInstruction;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_instruction_fragment,container,false);

        TextView step = rootView.findViewById(R.id.step_description);

        step.setText(mStepInstruction);
        
        return rootView;

    }
}
