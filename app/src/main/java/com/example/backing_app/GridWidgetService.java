package com.example.backing_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.backing_app.database.RecipeDataBase;
import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.utils.AppExecutorUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.backing_app.RecipeDetailActivity.RECIPE_INDEX;

public class GridWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.d("LAYOUT", "I CREATE REMOTEVIEWSFACTORY");

        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}
class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredient> mIngredients;

    public GridRemoteViewsFactory(Context context){
        mContext = context;
        mIngredients = new ArrayList<>();
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
                int recipe_index = recipeDataBase.recipeDAO().getCurrentRecipe();
                Log.d("LAYOUT","RECIPE INDEX" + recipe_index);
              //  int recipe_index = recipeDataBase.recipeDAO().getCurrentRecipe();
                mIngredients.clear();
                mIngredients = recipeDataBase.ingredientDAO().getIngredients(recipe_index);
             //   mRecipeIndex = recipe_index;
            }
        });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients != null){
            Log.d("LAYOUT:" ,"Ingredients size: " + mIngredients.size());
            return mIngredients.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if(mIngredients != null && mIngredients.size() > 0){

            Log.d("LAYOUT::","I SHOULD BE CREATING THINGS");
            RemoteViews views = new RemoteViews(mContext.getPackageName(),R.layout.ingredient_widget);

            views.setTextViewText(R.id.ingredient_widget_name,mIngredients.get(position).getIngredientName());

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
