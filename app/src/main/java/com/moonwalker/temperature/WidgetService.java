package com.moonwalker.temperature;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.Nullable;

public class WidgetService extends RemoteViewsService
{
    public WidgetService()
    {
        Log.d("Widgetservice", "constructor");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        Log.d("Widgetservice", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("Widgetservice", "onStartCommand");
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.temp_widget);
        view.setTextViewText(R.id.appwidget_text, "lastUpdate");
        ComponentName theWidget = new ComponentName(this, TempWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, view);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.d("Widgetservice", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent)
    {
        Log.d("Widgetservice", "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent)
    {
        Log.d("Widgetservice", "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {

        Log.w("Widgetservice", "onGetViewFactory");
        return new DataProvider(this, intent);

    }

}
