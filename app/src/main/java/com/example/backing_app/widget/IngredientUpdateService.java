package com.example.backing_app.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.backing_app.R;

public class IngredientUpdateService extends IntentService {


    private static final String ACTION_UPDATE_INGREDIENT_SHOWN =
            "com.example.baking_app.action.update_ingredients";

    public IngredientUpdateService() {
        super("IngredientUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(ACTION_UPDATE_INGREDIENT_SHOWN.equals(action)){
                handleUpdateIngredient();
            }
        }
    }

    public static void startActionUpdateIngredients(Context context){
        Intent intent = new Intent(context, IngredientUpdateService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT_SHOWN);
        context.startService(intent);
    }

    private void handleUpdateIngredient(){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, IngredientWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);

        IngredientWidgetProvider.updatePIngredientWidgets(this, appWidgetManager, appWidgetIds);
    }
}
