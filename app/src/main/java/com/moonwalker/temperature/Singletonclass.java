package com.moonwalker.temperature;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

class Singletonclass
{
    private static Singletonclass ourInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private Singletonclass(Context context)
    {
        mContext = context;
        // Get the request queue
        mRequestQueue = getmRequestQueue();
    }

    private RequestQueue getmRequestQueue()
    {
        if(mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
     return mRequestQueue;
    }

    public void setmRequestQeue(RequestQueue mRequestQeue)
    {
        this.mRequestQueue = mRequestQeue;
    }

    public static synchronized Singletonclass getInstance(Context context)
    {
        if(ourInstance == null)
        {
            ourInstance = new Singletonclass(context);
        }

        return ourInstance;
    }


    void addToRequestQueue(JsonObjectRequest jsonObjectRequest)
    {
        getmRequestQueue().add( jsonObjectRequest );
    }
}

