package com.moonwalker.temperature;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.RemoteViews;

public class FetchData extends JobService
{
    public FetchData()
    {

    }

    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {
        PersistableBundle jobExtras = jobParameters.getExtras();
        Log.d ("Fetchdata.onStartJob", jobExtras.getString( "ID" ));

        if(hasConnection())
        {
            IoTData ioTData1;
            ioTData1=pollWeb();

            String lastUpdate = getString(R.string.Bedroom)+ ioTData1.getTemphalo()+"C"+
                    "  "+getString(R.string.Balcony)+ioTData1.getTempErkely()+"C"+"\n"+
                    ioTData1.getTimestampHalo();
            RemoteViews view = new RemoteViews(getPackageName(), R.layout.temp_widget);
            view.setTextViewText(R.id.appwidget_text, lastUpdate);
            ComponentName theWidget = new ComponentName(this, TempWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(theWidget, view);
            Log.d("Fetchdata.onStartJob","after setting text");
        }

       onStopJob( jobParameters );
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters)
    {
        Log.d("FetchData.", "onStopJob");

        return true;
    }

    private IoTData pollWeb()
    {
        IoTData result;
        PHPCom getPHP =new PHPCom(this);
        result=getPHP.execute();

        return result;
    }

    private boolean hasConnection()
    {
        boolean res;

        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork  = cm.getActiveNetworkInfo();
        if (activeNetwork != null)
        {
            // connected to the internet
            switch (activeNetwork.getType())
            {
                case ConnectivityManager.TYPE_WIFI:
                    // connected to wifi
                    res= true;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    // connected to mobile data
                    res= true;
                    break;
                default:
                    res=false;
                    break;
            }
        }
        else
        {
            // not connected to the internet
            res = false;
        }
        return res;
    }
}
