package com.moonwalker.temperature;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;

public class FetchData extends JobService
{
    private IoTData ioTData;

    public FetchData()
    {

    }

    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {
        PersistableBundle jobExtras = jobParameters.getExtras();
        Log.d ("Fetchdata.onStartJob", jobExtras.getString( "ID" ));
        IoTData ioTData1 = new IoTData();
       if(hasConnection()) ioTData1=pollWeb();

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
