package com.example.backing_app.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.backing_app.R;
import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.utils.AppExecutorUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.backing_app.RecipeDetailActivity.RECIPE_INDEX;

public class GridWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}
class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<String> mIngredients;
    private int mRecipeIndex;

    public GridRemoteViewsFactory(Context context){
        mContext = context;
        mIngredients = new ArrayList<>();
        mRecipeIndex = 0;
    }

    @Override
    public void onCreate() {
        onDataSetChanged();
    }

    @Override
    public void onDataSetChanged() {

        final RecipeDataBase recipeDataBase = RecipeDataBase.getInstance(mContext);

        AppExecutorUtils.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mRecipeIndex = recipeDataBase.recipeDAO().getCurrentRecipe();
                mIngredients.clear();
                mIngredients = recipeDataBase.ingredientDAO().getIngredientsName(mRecipeIndex);
            }
        });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients != null){
            return mIngredients.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if(mIngredients != null && mIngredients.size() > 0){

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget);

            views.setTextViewText(R.id.ingredient_widget_name,mIngredients.get(position));

            Bundle extras = new Bundle();
            extras.putInt(RECIPE_INDEX,mRecipeIndex);

            Intent fillIntent = new Intent();

            fillIntent.putExtras(extras);

            views.setOnClickFillInIntent(R.id.ingredient_widget_name, fillIntent);

            return views;
        }

        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
