package com.moonwalker.temperature;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;

public class FetchData extends JobService
{
    public FetchData()
    {

    }

    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters)
    {
        return false;
    }


}
