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
import com.example.backing_app.recipe.Recipe;


import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {

    private static final String TAG = RecipeListAdapter.class.getSimpleName();

    private List<String> mRecipesName;
    private List<String> mRecipesServing;

    private final ItemClickListener mItemClickListener;

    public RecipeListAdapter(List<String> recipesName, List<String> recipesServing, ItemClickListener itemClickListener) {
        mRecipesName = recipesName;
        mRecipesServing = recipesServing;
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
        holder.bind (mRecipesName.get(position), mRecipesServing.get(position));
    }

    @Override
    public int getItemCount() {
        if(mRecipesServing != null){
            return mRecipesServing.size();
        }
        return -1;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView itemName;
        private TextView itemServing;

        RecipeHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.recipe_name);
            itemServing = itemView.findViewById(R.id.recipe_serving);

            itemView.setOnClickListener(this);
        }

        void bind(String recipeName, String recipeServing){
            itemName.setText(recipeName);
            itemServing.setText(recipeServing);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos);
        }
    }
}