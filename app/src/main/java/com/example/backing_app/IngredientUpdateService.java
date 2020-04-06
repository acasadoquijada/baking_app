package com.example.backing_app;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.backing_app.recipe.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static com.example.backing_app.RecipeDetailActivity.RECIPE_INDEX;
import static com.example.backing_app.RecipeDetailActivity.RECIPE_NAME;

public class IngredientUpdateService extends IntentService {

    public static final String ACTION_INGREDIENT_PLANTS =
            "com.example.android.mygarden.action.water_plants";

    public static final String ACTION_UPDATE_INGREDIENT_SHOWN =
            "com.example.android.mygarden.action.water_W";

    public IngredientUpdateService() {
        super("IngredientUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INGREDIENT_PLANTS.equals(action)) {
                handleActionIngredient();
            } else if(ACTION_UPDATE_INGREDIENT_SHOWN.equals(action)){
                final String recipe_name = intent.getStringExtra(RECIPE_NAME);
                handleUpdateIngredient(recipe_name);
            }
        }
    }

    public static void startActionWaterPlants(Context context) {
        Intent intent = new Intent(context, IngredientUpdateService.class);
        intent.setAction(ACTION_INGREDIENT_PLANTS);
        context.startService(intent);
    }

    public static void startActionUpdateIngredients(Context context, String recipe_name){
        Intent intent = new Intent(context, IngredientUpdateService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT_SHOWN);
        intent.putExtra(RECIPE_NAME, recipe_name);
        context.startService(intent);
    }

    private void handleActionIngredient(){
        Log.d("LAYOUT:","Doing stuff");
    }

    private void handleUpdateIngredient(String recipe_name){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, IngredientWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_grid_view);

        IngredientWidgetProvider.updatePIngredientWidgets(this, appWidgetManager,recipe_name,appWidgetIds);
    }
}
