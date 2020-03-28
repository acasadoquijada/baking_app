package com.example.backing_app.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.backing_app.R;

import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.FragmentHolder> {

    private List<String> mSteps;
    private final ItemClickListener mItemClickListener;

    private static final String TAG = MasterListAdapter.class.getSimpleName();

    public MasterListAdapter(List<String> steps, ItemClickListener itemClickListener){
        mSteps = steps;
        mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public FragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.step_layout;

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new FragmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentHolder holder, int position) {
        holder.bind (mSteps.get(position));
    }

    @Override
    public int getItemCount() {
        if(mSteps != null){
            return mSteps.size();
        }
        return -1;
    }

    class FragmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView itemView;

        FragmentHolder(@NonNull View view) {
            super(view);
            itemView = view.findViewById(R.id.step_short_description);
            itemView.setOnClickListener(this);
        }

        public void bind (String text){
            Log.d(TAG,text);
            itemView.setText(text);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos);
        }
    }
}
