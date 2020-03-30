package com.example.backing_app.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.backing_app.R;
import com.example.backing_app.recipe.Ingredient;

import java.util.List;

/**
 * RecyclerView Adapter for the ingredient representation
 */

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientHolder> {

    private List<Ingredient> mIngredients;

    public IngredientListAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.ingredient_fragment;

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        holder.bind(
                mIngredients.get(position).getIngredientName(),
                mIngredients.get(position).getQuantity(),
                mIngredients.get(position).getMeasure()
        );
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        }
        return -1;
    }

    class IngredientHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemQuantity;
        TextView itemMeasure;

        public IngredientHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.ingredient_name);
            itemQuantity = itemView.findViewById(R.id.ingredient_quantity);
            itemMeasure = itemView.findViewById(R.id.ingredient_measure);
        }

        public void bind (String name, int quantity, String measure){
            itemName.setText(name);
            itemQuantity.setText(String.valueOf(quantity));
            itemMeasure.setText(measure);
        }
    }
}