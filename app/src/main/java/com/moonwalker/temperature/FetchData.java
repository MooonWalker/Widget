package com.moonwalker.temperature;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class FetchData extends JobService
{
    public FetchData()
    {

    }

    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {
        Log.d("FetchData", "onStartJob");
        //if(hasConnection()) ioTData=pollWeb();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters)
    {
        Log.d("FetchData", "onStopJob");
        return false;
    }

    private IoTData pollWeb()
    {
        IoTData result= new IoTData();
        PHPCom getPHP =new PHPCom(this);
        getPHP.execute();

        return result;
    }

    private boolean hasConnection()
    {
        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected())
        {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected())
        {
            return true;        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
