package com.example.backing_app.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.backing_app.R;
import java.util.List;


/**
 * RecyclerView Adapter for the recipe representation
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {

    private static final String TAG = RecipeListAdapter.class.getSimpleName();

    private final List<String> mRecipesName;
    private final List<String> mRecipesServing;
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
        int layoutIdForListItem = R.layout.recipe_fragment;

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {

        String prefix = holder.itemView.getContext().getString(R.string.serving_prefix);
        String suffix = holder.itemView.getContext().getString(R.string.serving_suffix);

        String serving = prefix + " " + mRecipesServing.get(position) + " " + suffix;

        holder.bind (mRecipesName.get(position), serving);
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

        private final TextView itemName;
        private final TextView itemServing;

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