package com.moonwalker.temperature;

import android.app.Notification;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.Random;

import androidx.annotation.Nullable;

public class WidgetService extends RemoteViewsService
{
    public static final String TAG = "TempWidget";
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    Context context;
    private final String sendWasCorrect ="false";
    IoTData ioTData;

    public WidgetService()
    {
        Log.d("Widgetservice.", "constructor");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        Log.d("Widgetservice.", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("Widgetservice.", "onStartCommand");
        ioTData=new IoTData();

        int rndNumber = (new Random().nextInt(100));
        String lastUpdate = "R: "+rndNumber;

//        RemoteViews view = new RemoteViews(getPackageName(), R.layout.temp_widget);
//        view.setTextViewText(R.id.appwidget_text, lastUpdate);
//        ComponentName theWidget = new ComponentName(this, TempWidget.class);
//        AppWidgetManager manager = AppWidgetManager.getInstance(this);
//        manager.updateAppWidget(theWidget, view);

        stopSelf();

        final int i = 0;
        return i;
    }



    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.d("Widgetservice.", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent)
    {
        Log.d("Widgetservice.", "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent)
    {
        Log.d("Widgetservice.", "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {

        Log.d("Widgetservice.", "onGetViewFactory");
        return new DataProvider(this, intent);

    }

}
