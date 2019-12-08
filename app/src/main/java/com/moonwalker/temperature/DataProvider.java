package com.moonwalker.temperature;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;




public class DataProvider implements RemoteViewsService.RemoteViewsFactory {

    Context mContext = null;


    public DataProvider(Context context, Intent intent)
    {
        mContext= context;
    }


    @Override
    public void onCreate()
    {
        initData();
    }

    @Override
    public void onDataSetChanged()
    {
        initData();
    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public int getCount()
    {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        RemoteViews view= new RemoteViews(mContext.getPackageName(), R.layout.temp_widget);
        view.setTextViewText(R.id.appwidget_text,"kaka");

        return view;
    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public int getViewTypeCount()
    {
        return 0;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    private void initData()
    {

    }
}
