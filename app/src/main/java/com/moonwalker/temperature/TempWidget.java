package com.moonwalker.temperature;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link TempWidgetConfigureActivity TempWidgetConfigureActivity}
 */
public class TempWidget extends AppWidgetProvider
{
    private static final String ACTION_CLICK = "ACTION_CLICK";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
    {
//        int number = (new Random().nextInt(100));
//
//        CharSequence widgetText = TempWidgetConfigureActivity.loadPreferences( context, appWidgetId );
//
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.temp_widget );
//
//        //Original example coding
//        //views.setTextViewText( R.id.appwidget_text, widgetText );
//        views.setTextViewText(R.id.appwidget_text,String.valueOf(number));
//
//        Intent intent= new Intent(context,TempWidget.class);
//        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget( appWidgetId, views );
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        ComponentName thisWidget = new ComponentName(context, TempWidget.class);
        int[]allWidgetIds=appWidgetManager.getAppWidgetIds(thisWidget);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds)
        {
            //updateAppWidget( context, appWidgetManager, appWidgetId );
            int number = (new Random().nextInt(100));

             CharSequence widgetText = TempWidgetConfigureActivity.loadPreferences( context, appWidgetId );

             // Construct the RemoteViews object
             RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.temp_widget );

             //Original example coding
             //views.setTextViewText( R.id.appwidget_text, widgetText );
             views.setTextViewText(R.id.appwidget_text,String.valueOf(number));

             Intent intent= new Intent(context,TempWidget.class);
             intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
             intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);

             PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent,
                     PendingIntent.FLAG_UPDATE_CURRENT);

             views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

             // Instruct the widget manager to update the widget
             appWidgetManager.updateAppWidget( appWidgetId, views );
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds)
        {
            TempWidgetConfigureActivity.deletePreferences( context, appWidgetId );
        }
    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }
}

