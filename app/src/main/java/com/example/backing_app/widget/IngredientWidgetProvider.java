package com.example.backing_app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.backing_app.R;
import com.example.backing_app.RecipeDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews rv = getIngredientsGridRemoteView(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        IngredientUpdateService.startActionUpdateIngredients(context);
    }

    public static void updatePIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                                 int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static RemoteViews getIngredientsGridRemoteView(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);

        Intent intent = new Intent(context,GridWidgetService.class);

        Intent appIntent = new Intent(context, RecipeDetailActivity.class);

        PendingIntent appPendingIntent = PendingIntent.getActivity(
                context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        views.setEmptyView(R.id.widget_grid_view,R.id.recipe_name_widget);

        return views;
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

