package com.example.backing_app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import static com.example.backing_app.RecipeDetailActivity.RECIPE_NAME;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String recipe_name,
                                int appWidgetId) {

        RemoteViews rv = getIngredientsGridRemoteView(context,recipe_name);

        Intent ingredientIntent = new Intent(context, RecipeDetailActivity.class);

        ingredientIntent.putExtra(RECIPE_NAME,recipe_name);

        PendingIntent pendingIntent =
                PendingIntent.getService(context,0,ingredientIntent,PendingIntent.FLAG_UPDATE_CURRENT);

//        rv.setOnClickPendingIntent(R.id.widget_grid_view, pendingIntent);

        String s = "";


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        IngredientUpdateService.startActionWaterPlants(context);
    }

    public static void updatePIngredientWidgets(Context context, AppWidgetManager appWidgetManager,String recipe_name,
                                                 int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe_name, appWidgetId);
        }
    }

    private static RemoteViews getIngredientsGridRemoteView(Context context,
                                                            String recipe_name){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);

        Intent intent = new Intent(context,GridWidgetService.class);

       // views.setTextViewText(R.id.recipe_name_widget,recipe_name);

        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        views.setEmptyView(R.id.widget_grid_view,R.id.recipe_name_widget);

        return views;

        // If I need to remove things from the Layout do it here
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

