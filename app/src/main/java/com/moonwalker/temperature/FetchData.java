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

            pollWeb();

            Log.d("Fetchdata.onStartJob","after pollWeb()");
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

    private void pollWeb()
    {
        IoTData result;
        PHPCom getPHP =new PHPCom(this);
        getPHP.execute();

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
