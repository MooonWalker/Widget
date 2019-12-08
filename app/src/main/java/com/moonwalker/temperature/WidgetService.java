package com.moonwalker.temperature;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService
{
    public WidgetService()
    {
        Log.d("Widgetservice", "constructor");
    }

    @Override
    public void onCreate()
    {
        Log.d("Widgetservice", "onCreate");
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.d("Widgetservice", "onStart");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent)
    {
        Log.d("Widgetservice", "onStart");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent)
    {
        Log.d("Widgetservice", "onStart");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {

        Log.w("Widget_Widgetservice", "service");
        return new DataProvider(this, intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("Widgetservice", "onStart");
        return super.onStartCommand(intent, flags, startId);
    }


}
