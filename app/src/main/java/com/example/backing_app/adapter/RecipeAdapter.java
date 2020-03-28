package com.example.backing_app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.backing_app.R;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.ui.MasterListAdapter;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    List<Recipe> mRecipes;
    private final ItemClickListener mItemClickListener;

    public RecipeAdapter(List<Recipe> recipes, ItemClickListener itemClickListener) {
        mRecipes = recipes;
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.fragment_recipe;

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        holder.bind (mRecipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView item;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.recipe_name);
            item.setOnClickListener(this);
        }

        public void bind (String text){
            item.setText(text);
        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos);
        }
    }
}