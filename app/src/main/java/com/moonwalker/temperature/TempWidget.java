package com.moonwalker.temperature;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

import androidx.annotation.NonNull;

import static android.content.Context.JOB_SCHEDULER_SERVICE;


public class TempWidget extends AppWidgetProvider
{
    private static final String ACTION_CLICK = "ACTION_CLICK";
    private PendingIntent service;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        if (ACTION_CLICK.equals(intent.getAction()))
        {

            ComponentName thisWidget = new ComponentName(context, TempWidget.class);
            int[]allWidgetIds=AppWidgetManager.getInstance(context).getAppWidgetIds(thisWidget);

            for (int appWidgetId : allWidgetIds)
            {
                Log.d("TempWidget.", "onReceive");

                onUpdate(context,AppWidgetManager.getInstance(context),allWidgetIds);
            }
        }

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
    {
       Log.d("TempWidget.", "updateAppWidget");

        CharSequence widgetText = TempWidgetConfigureActivity.loadPreferences( context, appWidgetId );

        RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.temp_widget );
        views.setTextViewText( R.id.appwidget_text, widgetText );

        //TODO Jobscheduler

        Intent intent= new Intent(context,TempWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        Intent intent2 = new Intent(context, TempWidget.class);
        intent2.setAction(ACTION_CLICK);
        PendingIntent.getBroadcast(context, 0, intent2, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text,PendingIntent.getBroadcast(context,0,intent2,0));

         // Instruct the widget manager to update the widget
         appWidgetManager.updateAppWidget( appWidgetId, views );
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        Log.d("TempWidget.", "onUpdate");
        //TODO Jobscheduler
        ComponentName thisWidget = new ComponentName(context, TempWidget.class);
        ComponentName serviceName = new ComponentName( context, FetchData.class );
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceName);
        builder.setMinimumLatency( 1*1000 );
        builder.setOverrideDeadline(3*1000  );
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());

        int[]allWidgetIds=appWidgetManager.getAppWidgetIds(thisWidget);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : allWidgetIds)
        {
            //updateAppWidget( context, appWidgetManager, appWidgetId );
            int number = (new Random().nextInt(100));

             // Construct the RemoteViews object
             RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.temp_widget );
             views.setTextViewText(R.id.appwidget_text,String.valueOf(number));
             setRemoteAdapter(context,views,appWidgetId);

             Intent intent= new Intent(context,TempWidget.class);
             intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
             intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);

             PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

             views.setOnClickPendingIntent(R.id.appwidget_text,getPendingSelfIntent(context,ACTION_CLICK));
             // Instruct the widget manager to update the widget
             appWidgetManager.updateAppWidget( appWidgetId, views );
        }
        super.onUpdate(context, appWidgetManager,appWidgetIds);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action)
    {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
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
        // Enter relevant functionality for when the first widget inputStream created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget inputStream disabled
    }

    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views, int appWidgetId)
    {
        Log.w("TempWidget.", "setRemoteAdapter");
        Intent intent= new Intent(context,WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        views.setRemoteAdapter(R.id.appwidget_text, intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            context.startService(intent);
        }
        else
        {
            context.startService(intent);
        }

    }

}

