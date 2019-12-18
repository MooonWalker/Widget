package com.moonwalker.temperature;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.StringRes;

/**
 * The configuration screen
 */
public class TempWidgetConfigureActivity extends Activity
{
    private static final String PREFS_NAME = "com.moonwalker.temperature.TempWidget";
    private static final String PREF_TITLE = "widgettitle";
    private static final String PREF_FRQ = "updfrq";


    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText; //widget content
    EditText mUpdateFrq;

    public TempWidgetConfigureActivity() //constructor
    {
        super();
    }
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate( bundle );
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean is= pm.isIgnoringBatteryOptimizations("com.moonwalker.temperature");
        Log.d("isIgnoringBatteryOptimizations??", String.valueOf(is));
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult( RESULT_CANCELED );

        setContentView( R.layout.temp_widget_configure );
        mAppWidgetText = findViewById( R.id.appwidget_text );
        mUpdateFrq = findViewById( R.id.etUpdFrq );
        mUpdateFrq.setText( String.valueOf(30) );  //30 minutes as default value
        mAppWidgetText.setText(R.string.appwidget_text);

        Log.d ("TempWidgetConfigureActivity.", "onCreate");

        findViewById( R.id.add_button ).setOnClickListener( mOnClickListener );

    }

    View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            final Context context = TempWidgetConfigureActivity.this;
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            Log.d ("TempWidgetConfigureActivity.", "after getbundle");

            if (bundle != null)
            {
                mAppWidgetId = bundle.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID );

                Log.d ("TempWidgetConfigureActivity.WIDGET_ID", String.valueOf(mAppWidgetId));
            }

            // If this activity was started with an intent without an app widget ID, finish with an error.
            if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
            {
                finish();
                return;
            }

            //mAppWidgetText.setText( loadPreferences( TempWidgetConfigureActivity.this, mAppWidgetId ) );
            // When the button inputStream clicked, store the string locally
            String widgetText = mAppWidgetText.getText().toString();
            int updFrq = Integer.parseInt( mUpdateFrq.getText().toString());
            savePreferences( context, mAppWidgetId, widgetText, updFrq );

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra( AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId );
            ComponentName serviceName = new ComponentName( context, FetchData.class );

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( context );
            TempWidget.updateAppWidget( context, appWidgetManager, mAppWidgetId );

            setResult( RESULT_OK, resultValue );
            finish();
        }
    };

    // Write the prefix to the SharedPreferences object for this widget
    static void savePreferences(Context context, int appWidgetId, String text, int updFrq)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences( PREFS_NAME, 0 ).edit();
        prefs.putString( PREF_TITLE + appWidgetId, text );
        prefs.putInt( PREF_FRQ , updFrq );
        Log.d("TempWidgetConfig.updFRQ", String.valueOf( updFrq ));
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there inputStream no preference saved, get the default from a resource
    static String loadPreferences(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences( PREFS_NAME, 0 );
        String titleValue = prefs.getString( PREF_TITLE + appWidgetId, null );
        int updFrq =        prefs.getInt( PREF_FRQ,60);

        if (titleValue != null)
        {
            return titleValue+" " + updFrq;
        }
        else
        {
            //TODO lifecycle
            return context.getString( R.string.nodata );
        }
    }

    static int loadPrefUpdFRQ(Context context,int mAppWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences( PREFS_NAME, 0 );
        return prefs.getInt( PREF_TITLE,60 );
    }

    static void deletePreferences(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences( PREFS_NAME, 0 ).edit();
        prefs.remove( PREF_TITLE + appWidgetId );
        prefs.remove( PREF_FRQ );
        prefs.apply();
    }
}

